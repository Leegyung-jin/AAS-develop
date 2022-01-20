package kr.co.hulan.aas.common.model.req;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Condition {
    public static enum TYPE {
        NOT_EMPTY,
        LIKE,
        EQUALS,
        LESS,
        LESS_OR_EQUALS,
        GREATER,
        GREATER_OR_EQUALS,
        BETWEEN
    }

    private String name;
    private Object value;
    private TYPE type;

    public Condition(String name, Object value){
        this( TYPE.EQUALS, name , value );
    }

    public Condition(TYPE type, String name, Object value ){
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public Condition(TYPE type, String name, Object... value ){
        this.type = type;
        this.name = name;
        List<Object> valueList = new ArrayList();
        for (Object valueObj : value){
            valueList.add(valueObj);
        }
        this.value = valueList;
    }
}
