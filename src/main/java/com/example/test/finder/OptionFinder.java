package com.example.test.finder;

import lombok.Data;

/**
 * @author mpf
 * @date 2019/5/20
 */
@Data
public class OptionFinder {

    private String id;
    private String name;

    public OptionFinder() {

    }

    public OptionFinder(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

