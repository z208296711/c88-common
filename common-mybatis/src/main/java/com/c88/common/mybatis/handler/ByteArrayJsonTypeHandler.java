package com.c88.common.mybatis.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

/**
 * Byte 数组类型转换 json
 * <a href="https://www.jianshu.com/p/ab832f3fe81c">https://www.jianshu.com/p/ab832f3fe81c</a>
 *
 * @author Gadfly
 * @since 2021-06-30 15:19
 */
@Slf4j
@Component
@MappedTypes(value = {Byte[].class})
@MappedJdbcTypes(value = {JdbcType.VARCHAR}, includeNullJdbcType = true)
public class ByteArrayJsonTypeHandler extends ArrayObjectJsonTypeHandler<Byte> {
    public ByteArrayJsonTypeHandler() {
        super(Byte[].class);
    }
}
