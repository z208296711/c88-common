package com.c88.common.web.aop;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.c88.admin.api.TagFeignClient;
import com.c88.admin.dto.TagDTO;
import com.c88.amqp.EventType;
import com.c88.amqp.producer.MessageProducer;
import com.c88.common.core.result.Result;
import com.c88.common.web.annotation.AnnoLog;
import com.c88.common.web.annotation.I18n;
import com.c88.common.web.log.LogDTO;
import com.c88.common.web.log.LogOpResponse;
import com.c88.common.web.log.OperationEnum;
import com.c88.common.web.util.JwtUtils;
import com.c88.common.web.util.UserUtils;
import com.c88.member.api.MemberFeignClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jsonpatch.diff.JsonDiff;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * author zry
 * date 2021-12-20 16:37
 */
@EnableFeignClients(basePackageClasses = {
        MemberFeignClient.class,
        TagFeignClient.class
})
@Component
@Aspect
@Slf4j
public class AnnoLogAspect {

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private MemberFeignClient memberFeignClient;

    @Autowired
    private TagFeignClient tagFeignClient;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * 创建ExpressionParser解析表达式
     */
    private final SpelExpressionParser parser = new SpelExpressionParser();
    private final DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
//	private static final ThreadLocal<List<logVo>> logVo_THREAD_LOCAL = new NamedThreadLocal<>("ThreadLocal logVoList");

    private final String[] ignorePaths = Stream.of("\"/gmtModified\"").toArray(String[]::new);

    private ConcurrentMap<Object, String> fieldNames = new ConcurrentHashMap<>(Map.of(
            Pattern.compile("\\d+/note"), "be_deposit_channel_group.message"// 提示訊息
    ));

    private Pattern INDEX = Pattern.compile("\\d+");

    private Map<Integer, String> vipNames;
    private Map<Integer, String> tagNames;

    private void loadTagNames() {
        tagNames = tagFeignClient.listTags().getData().parallelStream().collect(Collectors.toMap(TagDTO::getId, TagDTO::getName));
    }

    private void loadVipNames() {
        vipNames = memberFeignClient.findMemberVipConfigMap().getData();
    }

    private String getTagName(Integer id) {
        if (tagNames == null) {
            loadTagNames();
        }
        String name = tagNames.get(id);
        if (name == null) {// 只reload一次，以更新新的會員標籤名稱
            loadTagNames();
            name = tagNames.get(id);
            return name != null ? name : String.valueOf(id);
        }
        return name;
    }

    private String getVipName(Integer id) {
        if (vipNames == null) {
            loadVipNames();
        }
        String name = vipNames.get(id);
        if (name == null) {// 只reload一次，以更新新的會員等級名稱
            loadVipNames();
            name = vipNames.get(id);
            return name != null ? name : String.valueOf(id);
        }
        return name;
    }

    @AfterReturning(value = "@annotation(com.c88.common.web.annotation.AnnoLog) || @annotation(com.c88.common.web.annotation.AnnoLogs)", returning = "returnValue")
    public void afterReturn(JoinPoint joinPoint, Object returnValue) {
        log.info("------afterReturning---------");
        try {
            List<LogDTO> logDtoList = new ArrayList<>();
//			logVo_THREAD_LOCAL.set(logVoList);
            Object[] arguments = joinPoint.getArgs();
            //获得方法对象
            Method method = getMethod(joinPoint);
            //获取方法的批量注解
            AnnoLog[] annotations = method.getAnnotationsByType(AnnoLog.class);
            for (AnnoLog annotation : annotations) {
                LogDTO logDto = new LogDTO();
                logDto.setType(OperationEnum.DEFAULT);
                logDtoList.add(logDto);
                String uuIdSpEL = annotation.uuId();
                String contentSpEL = annotation.content();
                String uuId = uuIdSpEL;
                String content = contentSpEL;
                String additionSpEL = annotation.addition();
                String addition = additionSpEL;
                String ipSpEl = annotation.ip();
                String ip = ipSpEl;
                String i18nKey = annotation.i18nKey();
                String menuPage = annotation.menuPage();
                String menu = annotation.menu();
                List<String> data = new ArrayList<>();
                try {
                    String[] params = discoverer.getParameterNames(method);
                    EvaluationContext context = new StandardEvaluationContext();
                    context.setVariable("dateTimeFormatter", dateTimeFormatter);
                    context.setVariable("timeFormatter", timeFormatter);
                    if (params != null) {
                        for (int len = 0; len < params.length; len++) {
                            context.setVariable(params[len], arguments[len]);
                        }
                    }

                    // uuId 处理：直接传入字符串会抛出异常，写入默认传入的字符串
                    if (StringUtils.isNotBlank(uuIdSpEL)) {
                        //表达式放置
                        Expression bizIdExpression = parser.parseExpression(uuId);
                        //执行表达式，默认容器是spring本身的容器：ApplicationContext
                        uuId = bizIdExpression.getValue(context, String.class);
                    }

                    // content 处理，写入默认传入的字符串
                    if (StringUtils.isNotBlank(contentSpEL)) {
                        Expression msgExpression = parser.parseExpression(contentSpEL);
                        Object msgObj = msgExpression.getValue(context, Object.class);
                        data.addAll(new ArrayList<>(Arrays.asList((String[]) msgObj)));
                        log.info("取得的入參內容:{}", content);
                    }

                    if (StringUtils.isNotBlank(ipSpEl)) {
                        Expression msgExpression = parser.parseExpression(ipSpEl, new TemplateParserContext());
                        ip = msgExpression.getValue(context, String.class);
                    }

                    if (i18nKey.contains("#")) {
                        Expression msgExpression = parser.parseExpression(i18nKey);
                        i18nKey = msgExpression.getValue(context, String.class);
                    }

                    if(menuPage.contains("#")){
                        Expression msgExpression = parser.parseExpression(menuPage);
                        menuPage = msgExpression.getValue(context, String.class);
                    }

                    if(menu.contains("#")){
                        Expression msgExpression = parser.parseExpression(menu);
                        menu = msgExpression.getValue(context, String.class);
                    }

                    ObjectMapper om = new ObjectMapper();
                    logDto.setType(annotation.operationEnum());
                    switch (annotation.operationEnum()) {
                        case INSERT:
                        case DELETE:
                            break;
                        case UPDATE:
                            if (!(returnValue instanceof Result)
                                    || !(((Result<?>) returnValue).getData() instanceof LogOpResponse)
                                    || ((LogOpResponse<?, ?>) ((Result<?>) returnValue).getData()).getBefore() == null
                                    || ((LogOpResponse<?, ?>) ((Result<?>) returnValue).getData()).getAfter() == null) {
                                log.debug("程式沒有回傳原始資料或變動資料，無法紀錄變動項,入參{}", content);
                            } else {
                                LogOpResponse logOpResponse = ((LogOpResponse) ((Result) returnValue).getData());
                                Class beforeClass = logOpResponse.getBefore().getClass();
                                JsonNode beforeJson = om.readTree(logOpResponse.getBeforeStr());
                                JsonNode afterJson = om.readTree(logOpResponse.getAfterStr());
                                JsonNode patch = JsonDiff.asJson(beforeJson, afterJson);
                                JSONArray array = new JSONArray();
                                ObjectMapper mapper = new ObjectMapper();
                                Map<String, Object> beforeData = new HashMap<>();
                                Map<String, Object> afterData = new HashMap<>();
                                for (JsonNode node : patch) {
                                    for (String ignore : ignorePaths) {
                                        if (!node.get("path").toString().equals(ignore)) {
                                            JSONObject jsonObject = JSON.parseObject(mapper.writeValueAsString(node));
                                            String path = jsonObject.getString("path");
                                            String field = path.replaceFirst("^/", "").replaceFirst("/(-|\\d+)", "");
                                            Object beforeObject = beforeJson.get(field);
                                            if (beforeObject == null) {// find by path
                                                beforeObject = beforeJson.at(path);
                                            }
                                            if ("replace".equals(jsonObject.get("op"))) {
                                                String fieldName = adjustFieldName(afterJson, path, field, i18nKey, beforeClass);
                                                if (beforeObject instanceof TextNode) {
                                                    beforeData.put(fieldName, adjustValue(field, ((TextNode) beforeObject).textValue()));// 紀錄所有有異動欄位名及欄位值(異動前的值)
                                                } else {
                                                    beforeData.put(fieldName, adjustValue(field, beforeObject.toString()));
                                                }
                                                afterData.put(fieldName, adjustValue(field, jsonObject.get("value").toString()));// 紀錄所有有異動欄位名及欄位值(異動後的值)
                                            } else if (beforeObject instanceof ArrayNode) {// op:add 跟 op:remove 只處理 ArrayNode
                                                String fieldName = adjustFieldName(afterJson, path, field, i18nKey, beforeClass);
                                                beforeData.put(fieldName, adjustValue(field, (ArrayNode) beforeObject).toString());
                                                Object afterObject = afterJson.get(field);
                                                if (afterObject != null) {
                                                    afterData.put(fieldName, adjustValue(field, (ArrayNode) afterObject).toString());
                                                }
                                            }
                                            array.add(jsonObject);
                                        }
                                    }
                                }
                                if (!beforeData.isEmpty() && StringUtils.isEmpty(addition)) {
                                    data.add(JSONUtil.toJsonStr(beforeData));
                                }
                                if (!afterData.isEmpty()) {
                                    if (StringUtils.isNotEmpty(addition)) {
                                        data.add(afterData.get(addition).toString());
                                    } else {
                                        data.add(JSONUtil.toJsonStr(afterData));
                                    }
                                }

                                if (array.size() == 0) return;

                                log.info("修改了內容{}", array);
                            }
                            break;
                        default:
                    }

                    logDto.setSuccess(true);
                } catch (Exception e) {
                    log.error("SystemLogAspect doBefore error", e);
                    logDto.setSuccess(false);
                } finally {
                    logDto.setContent(JSONUtil.toJsonStr(data));
                    logDto.setMenu(menu);
                    logDto.setMenuPage(menuPage);
                    logDto.setOperationDate(LocalDateTime.now());
                    logDto.setOperator(this.getUserName());
                    logDto.setOperatorLoginIp(ip);
                    logDto.setI18nKey(i18nKey);

                    messageProducer.sendMessage(EventType.ANNO_LOG, logDto);
                }
            }
        } catch (Exception e) {
            log.error("SystemLogAspect error", e);
        }
    }

    /**
     * 取代欄位名為對應的{{i18nKey}}，給前端可以顯示中文欄位名，
     * 若找不到對應欄位，會更新一次對應修改前的 Entity Class 所有屬性的 i18n
     *
     * @param afterJson
     * @param path
     * @param fieldName
     * @param i18nKey
     * @param clazz      Entity Class
     * @return
     */
    private String adjustFieldName(JsonNode afterJson, String path, String fieldName, String i18nKey, Class clazz) {
        String newFieldName = fieldName;
        if ("be_deposit_channel_group.operation_log01".equals(i18nKey)) {// 會員通道群組支付標籤特殊處理
            return "{{" + afterJson.at(path.replaceFirst("label", "rechargeTypeName")).textValue() + "}}";// recharge type name 為 i18n，需加{{}}給前端取代
        }
        newFieldName = replaceFieldName(fieldName);
        if (newFieldName == null) {// 只會更新一次物件屬性 i18n
            Arrays.stream(clazz.getDeclaredFields()).parallel().forEach(f -> {
                I18n i18n = f.getAnnotation(I18n.class);
                if (i18n != null) {
                    fieldNames.put(f.getName(), i18n.value());
                }
            });
            newFieldName = replaceFieldName(fieldName);
            if (newFieldName == null) {
                return fieldName;
            }
        }
        return newFieldName;
    }

    /**
     * 取代欄位名為對應的{{i18nKey}}
     *
     * @param fieldName
     * @return
     */
    private String replaceFieldName(String fieldName) {
        String newFieldName = fieldName;
        String finalFieldName = fieldName;
        Optional<Map.Entry<Object, String>> optional = fieldNames.entrySet().parallelStream()
                .filter(e -> {
                    if (e.getKey() instanceof Pattern) {
                        return ((Pattern) e.getKey()).matcher(finalFieldName).matches();
                    }
                    return e.getKey().equals(finalFieldName);
                }).findFirst();
        if (optional.isPresent()) {
            Map.Entry<Object, String> entry = optional.get();
            newFieldName = "{{" + optional.get().getValue() + "}}";// 取代為{{i8nKey}}給前端顯示
            if (entry.getKey().toString().startsWith("\\d+")) {// 處理陣列多索引欄位編輯
                Matcher matcher = INDEX.matcher(fieldName);
                newFieldName += (matcher.find() ? Integer.parseInt(matcher.group()) + 1 : "");// 取代索引
            }
        } else {
            return null;
        }
        return newFieldName;
    }

    /**
     * 取代陣列中的id值為對應資料名稱
     *
     * @param field
     * @param value
     * @return
     */
    private Object adjustValue(String field, ArrayNode value) {
        List<String> list = new ArrayList<>();
        switch (field) {
            case "vipIds":
                value.forEach(v -> list.add(getVipName(v.intValue())));
                break;
            case "memberTags":
                value.forEach(v -> list.add(getTagName(v.intValue())));
                break;
        }
        return list.isEmpty() ? value : list;
    }

    /**
     * 取代數字對應的狀態名稱
     *
     * @param field
     * @param value
     * @return
     */
    private String adjustValue(String field, String value) {
        String newValue = value;
        switch (field) {
            case "status":
                newValue = "0".equals(newValue) ? "{{be_deposit_channel.dropdownd08}}"// 停用
                        : "{{be_deposit_channel.dropdownd09}}";// 啟用
                break;
        }
        return newValue;
    }

    private Method getMethod(JoinPoint joinPoint) {
        Method method = null;
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Object target = joinPoint.getTarget();
        try {
            method = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        } catch (NoSuchMethodException e) {
            log.error("SystemLogAspect getMethod error", e);
        }
        return method;
    }

    private HttpServletRequest getServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return request;
        } else {
            return null;
        }
    }

    private String getUserName() {
        return (JwtUtils.getJwtPayload() != null && StringUtils.isNotBlank(UserUtils.getUsername()))
                ? UserUtils.getUsername() : null;
    }

}
