package com.c88.common.mybatis.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListLongTypeHandler extends BaseTypeHandler<List<Long>> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<Long> longs, JdbcType jdbcType) throws SQLException {
        if (longs.isEmpty()) {
            preparedStatement.setString(i, null);
        } else {
            preparedStatement.setString(i, longs.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }
    }

    @Override
    public List<Long> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        if (StringUtils.isNotBlank(resultSet.getString(s))) {
            return Arrays.stream(resultSet.getString(s).split(",")).map(Long::valueOf).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Long> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return Arrays.asList(resultSet.getString(i).split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
    }

    @Override
    public List<Long> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return Arrays.asList(callableStatement.getString(i).split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
    }
}
