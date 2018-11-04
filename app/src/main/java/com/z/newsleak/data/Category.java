package com.z.newsleak.data;

import java.io.Serializable;

import androidx.annotation.NonNull;

public enum Category implements Serializable {

    DARWIN_AWARDS(1, "Darwin Awards"),
    CRIMINAL(2, "Criminal"),
    ANIMALS(3, "Animals"),
    MUSIC(4, "Music"),

    HOME(4, "Home"),
    OPINION(4, "Opinion"),
    WORLD(4, "World"),
    NATIONAL(4, "National"),
    POLITICS(4, "Politics"),
    UPSHOT(4, "Upshot"),
    NYREGION(4, "NY region"),
    BUSINESS(4, "Business"),
    TECHNOLOGY(4, "Technology"),
    SCIENCE(4, "Science"),
    HEALTH(4, "Health"),
    SPORTS(4, "Sports"),
    ARTS(4, "Arts"),
    BOOKS(4, "Books"),
    MOVIES(4, "Movies"),
    THEATER(4, "Theater"),
    SUNDAYREVIEW(4, "Sunday review"),
    FASHION(4, "Fashion"),
    TMAGAZINE(4, "T Magazine"),
    FOOD(4, "Food"),
    TRAVEL(4, "Travel"),
    MAGAZINE(4, "Magazine"),
    REALESTATE(4, "Real estate"),
    AUTOMOBILES(4, "Automobiles"),
    OBITUARIES(4, "Obituaries"),
    INSIDER(4, "Insider");

    private final int id;
    @NonNull
    private final String name;

    Category(int id, @NonNull String name) {
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