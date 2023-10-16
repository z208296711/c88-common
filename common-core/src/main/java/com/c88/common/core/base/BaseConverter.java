package com.c88.common.core.base;

import java.util.List;

public interface BaseConverter<S, T> {

    T toVo(S entity);

    S toEntity(T vo);

    List<T> toVo(List<S> entity);

    List<S> toEntity(List<T> vo);

}
