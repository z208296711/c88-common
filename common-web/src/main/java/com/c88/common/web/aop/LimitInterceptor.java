package com.c88.common.web.aop;

import com.c88.common.core.result.ResultCode;
import com.c88.common.redis.annotation.Limit;
import com.c88.common.redis.enums.LimitType;
import com.c88.common.redis.utils.RedisUtils;
import com.c88.common.web.exception.BizException;
import com.c88.common.web.util.MemberUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LimitInterceptor {

    private final String REDIS_SCRIPT = buildLuaScript();

    private final RedisUtils redisUtils;

    /**
     * SpEL expression for parsing.
     */
    private SpelExpressionParser parser = new SpelExpressionParser();

    /**
     * The method used to obtain the parameters defined name.
     */
    private DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Around("execution(public * *(..)) && @annotation(com.c88.common.redis.annotation.Limit)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Limit limitAnno = method.getAnnotation(Limit.class);

        LimitType limitType = limitAnno.limitType();
        String name = limitAnno.name();
        int limitPeriod = limitAnno.period();
        int limitCount = limitAnno.count();
        String keyValue;
        String key;
        switch (limitType) {
            case IP:
                key = RedisUtils.buildKey("apiLimitation", limitAnno.prefix(), ipReformat(getIpAddress()));
                break;
            case CUSTOM:
                keyValue = generateKeyBySpEL(limitAnno.key(), pjp);
                key = RedisUtils.buildKey("apiLimitation", limitAnno.prefix(), keyValue);
                break;
            case BALANCE:
            case PLATFORM_TRANSFER:
                key = RedisUtils.buildKey("apiLimitation", limitAnno.prefix(),MemberUtils.getMemberId());
                break;
            default:
                throw new BizException(ResultCode.FLOW_LIMITING);
        }

        try {
            Long count = redisUtils.executeLua(REDIS_SCRIPT,
                    Long.class,
                    List.of(key),
                    limitCount,
                    limitPeriod);

            if (StringUtils.isNotEmpty(name)) {
                log.warn("Access count: {}, name: {}, key: {}", count, name, key);
            } else {
                log.warn("Access count: {}, key: {}", count, key);
            }

            if (count != null && count.intValue() <= limitCount) {
                return pjp.proceed();
            } else {
                switch (limitType) {
                    case BALANCE:
                        throw new BizException(ResultCode.REFRESH_LIMIT);
                    case PLATFORM_TRANSFER:
                        throw new BizException(ResultCode.PLATFORM_TRANSFER_LIMIT);
                    default:
                        throw new BizException(ResultCode.FLOW_LIMITING);
                }
            }
        } catch (BizException bse) {
            throw bse;
        } catch (Throwable e) {
            log.error(ExceptionUtils.getMessage(e), e);
            throw new BizException(ResultCode.SYSTEM_EXECUTION_ERROR);
        }
    }

    /**
     * 限流 脚本
     *
     * @return lua脚本
     */
    private String buildLuaScript() {
        StringBuilder lua = new StringBuilder();
        lua.append("local c")
                .append("\nc = redis.call('get', KEYS[1])")
                // 调用不超过最大值，则直接返回
                .append("\nif c and tonumber(c) > tonumber(ARGV[1]) then")
                .append("\nreturn c;")
                .append("\nend")
                // 执行计算器自加
                .append("\nc = redis.call('incr', KEYS[1])")
                .append("\nif tonumber(c) == 1 then")
                // 从第一次调用开始限流，设置对应键值的过期
                .append("\nredis.call('expire', KEYS[1], ARGV[2])")
                .append("\nend")
                .append("\nreturn c");
        return lua.toString();
    }

    /**
     * 限流 脚本（处理临界时间大量请求的情况）
     *
     * @return lua脚本
     */
    private String buildLuaScript2() {
        StringBuilder lua = new StringBuilder();
        lua.append("redis.replicate_commands(); local listLen, time")
                .append("\n listLen = redis.call('LLEN', KEYS[1])")
                // 不超过最大值，则直接写入时间
                .append("\n if listLen and tonumber(listLen) < tonumber(ARGV[1]) then")
                .append("\n local a = redis.call('TIME');")
                .append("\n redis.call('LPUSH', KEYS[1], a[1]*1000000+a[2])")
                .append("\n else")
                // 取出现存的最早的那个时间，和当前时间比较，看是小于时间间隔
                .append("\n time = redis.call('LINDEX', KEYS[1], -1)")
                .append("\n local a = redis.call('TIME');")
                .append("\n if a[1]*1000000+a[2] - time < tonumber(ARGV[2])*1000000 then")
                // 访问频率超过了限制，返回0表示失败
                .append("\n return 0;")
                .append("\n else")
                .append("\n redis.call('LPUSH', KEYS[1], a[1]*1000000+a[2])")
                .append("\n redis.call('LTRIM', KEYS[1], 0, tonumber(ARGV[1])-1)")
                .append("\n end")
                .append("\n end")
                .append("\n return 1;");
        return lua.toString();
    }

    private String ipReformat(String ipStr) {
        return StringUtils.contains(ipStr, ":") ? StringUtils.replace(ipStr, ":", "-") //ipv6
                : StringUtils.replace(ipStr, ".", "-"); // ipv4
    }


    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    public static String getIpAddress() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "0.0.0.0";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        for (String header : IP_HEADER_CANDIDATES) {
            String ipList = request.getHeader(header);
            if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                String ip = ipList.split(",")[0];
                return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
            }
        }

        String remoteAddr = request.getRemoteAddr();
        String[] split = StringUtils.split(remoteAddr, ",");
        if (split.length > 1)
            return split[0];
        return request.getRemoteAddr();
    }


    public String generateKeyBySpEL(String spELString, ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
        Expression expression = parser.parseExpression(spELString);
        EvaluationContext context = new StandardEvaluationContext();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        return expression.getValue(context).toString();
    }

}
