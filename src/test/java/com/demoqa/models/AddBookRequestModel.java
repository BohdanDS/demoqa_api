package com.demoqa.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@Data
public class AddBookRequestModel {
    private String userId;
    private List<IsbnModel> collectionOfIsbns;

    public AddBookRequestModel(String userId, List<IsbnModel> collectionOfIsbns) {
        this.userId = userId;
        this.collectionOfIsbns = collectionOfIsbns;
    }

}

