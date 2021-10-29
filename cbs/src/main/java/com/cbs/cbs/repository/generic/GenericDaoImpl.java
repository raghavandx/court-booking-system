package com.cbs.cbs.repository.generic;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GenericDaoImpl implements GenericDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public <T> List<T> executeTypedQuery(String query, Map<String, Object> parameters, Class<T> pClass) {
        var typedQuery = entityManager.createNamedQuery(query, pClass);
        setParameters(typedQuery, parameters);
        return typedQuery.getResultList();
    }

    private void setParameters(Query query, Map<String, Object> paramMap){

        var finalParamMap = paramMap.entrySet().stream().map(entry -> {
            return Map.entry(entry.getKey().toUpperCase(), entry.getValue());
        }).collect(Collectors.toMap(k -> k, v -> v));

        for (var param : query.getParameters()) {
            var value = finalParamMap.get(param.getName().toUpperCase());
            query.setParameter(param.getName(), value);
        }
    }
}
