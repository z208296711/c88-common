package com.c88.common.mybatis.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.c88.common.core.vo.AssignPlatformGameVO;

public class AssignPlatformGameTypeHandler extends AbstractJsonTypeHandler<Object> {
    @Override
    protected Object parse(String json) {
        return JSON.parseArray(json, AssignPlatformGameVO.class);
    }

    @Override
    protected String toJson(Object obj) {
        return JSON.toJSONString(obj, new SerializerFeature[]{SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty});
    }
}
