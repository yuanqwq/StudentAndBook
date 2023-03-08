package com.test.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Student {
    private int sid;
    private String name;
    private String sex;
}
