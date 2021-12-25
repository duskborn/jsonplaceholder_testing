package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.BaseModel;

public class Post extends BaseModel {
    @JsonProperty("id")
    public Integer id;

    @JsonProperty("userId")
    public Integer userId;

    @JsonProperty("title")
    public Integer title;

    @JsonProperty("body")
    public Integer body;
}
