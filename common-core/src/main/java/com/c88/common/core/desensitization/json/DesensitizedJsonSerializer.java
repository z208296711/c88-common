package com.c88.common.core.desensitization.json;

import com.c88.common.core.desensitization.annotation.Sensitive;
import com.c88.common.core.desensitization.utils.EntityUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;

public class DesensitizedJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private Sensitive sensitive;
    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(EntityUtils.getDesensitizedValue(sensitive,s));

    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {

        sensitive = beanProperty.getAnnotation(Sensitive.class);

        if(!ObjectUtils.isEmpty(sensitive) && String.class.isAssignableFrom(beanProperty.getType().getRawClass())){
            return this;
        }
        return serializerProvider.findValueSerializer(beanProperty.getType(),beanProperty);
    }
}