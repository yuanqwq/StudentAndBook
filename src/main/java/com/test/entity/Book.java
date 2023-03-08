package com.test.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Book {
    private int bid;
    private String name;
    private String author;
    private double price;
}
