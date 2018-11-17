package com.z.newsleak.data.db;

import com.z.newsleak.model.Category;

import java.util.Date;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long dateLong) {
        return dateLong == null ? null : new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Category toCategory(@Nullable String section) {

        if (section == null) {
            return Category.HOME;
        }

        for (Category category : Category.values()) {
            if (section.equals(category.getSection())) {
                return category;
            }
        }

        return Category.HOME;
    }

    @TypeConverter
    public static String fromCategory(Category category) {
        return category == null ? null : category.getSection();
    }
}
