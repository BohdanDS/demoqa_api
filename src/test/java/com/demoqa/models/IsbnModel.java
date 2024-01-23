package com.demoqa.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class IsbnModel {
    private String isbn;
    public IsbnModel(String isbn) {
        this.isbn = isbn;
    }

}
