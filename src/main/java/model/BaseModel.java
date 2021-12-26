package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class BaseModel implements Cloneable {
    public Map<String, Object> toMap() {
        Map<String, Object> map = new ObjectMapper().convertValue(this, Map.class);
        return map;
    }

    @Override
    public Object clone() {
        try {
            Object o = super.clone();
            return o;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        String string = null;
        try {
            string = new ObjectMapper().writeValueAsString(this.toMap());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return string;
    }
}
