package com.z.newsleak.data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;

public enum Category implements Serializable {

    HOME("Home"),
    OPINION("Opinion"),
    WORLD("World"),
    NATIONAL("National"),
    POLITICS("Politics"),
    UPSHOT("Upshot"),
    NYREGION("NY region"),
    BUSINESS("Business"),
    TECHNOLOGY("Technology"),
    SCIENCE("Science"),
    HEALTH("Health"),
    SPORTS("Sports"),
    ARTS("Arts"),
    BOOKS("Books"),
    MOVIES("Movies"),
    THEATER("Theater"),
    SUNDAYREVIEW("Sunday review"),
    FASHION("Fashion"),
    TMAGAZINE("T Magazine"),
    FOOD("Food"),
    TRAVEL("Travel"),
    MAGAZINE("Magazine"),
    REALESTATE("Real estate"),
    AUTOMOBILES("Automobiles"),
    OBITUARIES("Obituaries"),
    INSIDER("Insider");

    @NonNull
    private final String name;

    Category(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getSection() {
        return getName().replaceAll("\\s+","").toLowerCase();
    }

    @Override
    public String toString() {
        return getName();
    }
}