package com.z.newsleak.data;

import java.io.Serializable;

import androidx.annotation.NonNull;

public enum Category implements Serializable {

    DARWIN_AWARDS(1, "Darwin Awards"),
    CRIMINAL(2, "Criminal"),
    ANIMALS(3, "Animals"),
    MUSIC(4, "Music");

    private final int id;
    @NonNull
    private final String name;

    Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }
}