package com.soumya.quizapp.models;

import lombok.Data;

@Data
public class UserResponse {

    private Integer id;

    private String response;

    public UserResponse(Integer id, String response) {
        this.id = id;
        this.response = response;
    }
}
