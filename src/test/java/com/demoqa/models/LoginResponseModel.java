package com.demoqa.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponseModel {
    @JsonProperty("created_date")
    String createdDate;
    String expires;
    String isActive;
    String password;
    String token;
    String userId;
    String username;
}
