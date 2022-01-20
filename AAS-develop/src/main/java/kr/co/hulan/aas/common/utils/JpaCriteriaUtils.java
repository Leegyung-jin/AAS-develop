package kr.co.hulan.aas.common.utils;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.req.Condition;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JpaCriteriaUtils {

    public static Map<ParameterExpression,Object> setPredicate(CriteriaBuilder criteriaBuilder, CriteriaQuery query, Root root,  List<Condition> conditionList ){
        Map<ParameterExpression,Object> paramsMap = new HashMap();
        List<Predicate> predicates = new ArrayList<Predicate>();
        for( Condition condition : conditionList){
            Object value = condition.getValue();
            if( condition.getType() == Condition.TYPE.LIKE ){
                ParameterExpression paramExpr = criteriaBuilder.parameter(value.getClass());
                predicates.add(criteriaBuilder.like(root.get(condition.getName()), paramExpr));
                paramsMap.put(paramExpr , value);
            }
            else if( condition.getType() == Condition.TYPE.EQUALS ){
                ParameterExpression paramExpr = criteriaBuilder.parameter(value.getClass());
                predicates.add(criteriaBuilder.equal(root.get(condition.getName()), paramExpr));
                paramsMap.put(paramExpr , value);
            }
            else if( condition.getType() == Condition.TYPE.GREATER ){
                ParameterExpression paramExpr = criteriaBuilder.parameter(value.getClass());
                predicates.add(criteriaBuilder.greaterThan(root.get(condition.getName()), paramExpr));
                paramsMap.put(paramExpr , value);
            }
            else if( condition.getType() == Condition.TYPE.GREATER_OR_EQUALS ){
                ParameterExpression paramExpr = criteriaBuilder.parameter(value.getClass());
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(condition.getName()), paramExpr));
                paramsMap.put(paramExpr , value);
            }
            else if( condition.getType() == Condition.TYPE.LESS ){
                ParameterExpression paramExpr = criteriaBuilder.parameter(value.getClass());
                predicates.add(criteriaBuilder.lessThan(root.get(condition.getName()), paramExpr));
                paramsMap.put(paramExpr , value);
            }
            else if( condition.getType() == Condition.TYPE.LESS_OR_EQUALS ){
                ParameterExpression paramExpr = criteriaBuilder.parameter(value.getClass());
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(condition.getName()), paramExpr));
                paramsMap.put(paramExpr , value);
            }
            else if( condition.getType() == Condition.TYPE.NOT_EMPTY ){
                //predicates.add(criteriaBuilder.isNotEmpty(root.get(condition.getName())));
                predicates.add(criteriaBuilder.isNotNull(root.get(condition.getName())));
                predicates.add(criteriaBuilder.notEqual(root.get(condition.getName()), ""));
            }
            else if( condition.getType() == Condition.TYPE.BETWEEN ){
                if( value instanceof Map ){
                    Map<String,String> valueMap = (Map<String,String> ) value;
                    Object value1 = valueMap.get("start");
                    Object value2 = valueMap.get("end");
                    ParameterExpression paramExpr1 = criteriaBuilder.parameter(value1.getClass());
                    ParameterExpression paramExpr2 = criteriaBuilder.parameter(value2.getClass());
                    predicates.add(criteriaBuilder.between(root.get(condition.getName()), paramExpr1, paramExpr2));
                    paramsMap.put(paramExpr1 , value1);
                    paramsMap.put(paramExpr2 , value2);
                }
                else {
                    throw new CommonException(BaseCode.ERR_ETC_EXCEPTION.code(), "Invalid Parmas");
                }
            }
        }
        query.where(predicates.toArray(new Predicate[predicates.size()]));
        return paramsMap;
    }
}
