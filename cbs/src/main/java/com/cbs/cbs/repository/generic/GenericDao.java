package com.cbs.cbs.repository.generic;

import java.util.List;
import java.util.Map;

public interface GenericDao {
    <T> List<T> executeTypedQuery(String query, Map<String, Object> parameters , Class<T> pClass);
}
