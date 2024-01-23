package com.demoqa.models;

import lombok.Data;

@Data
public class DeleteSpecifiedBookRequestModel {
    String isbn;
    String userId;
}
