package kr.co.hulan.aas.common.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConditionRequest {

    @JsonIgnore
    public List<Condition> getConditionList() {
        return new ArrayList<>();
    }

    @JsonIgnore
    public Map<String, Object> getConditionMap() {
        return new HashMap<>();
    }
}
