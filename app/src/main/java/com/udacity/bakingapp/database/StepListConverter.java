package com.udacity.bakingapp.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udacity.bakingapp.data.Step;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class StepListConverter implements Serializable {
    Type type = new TypeToken<List<Step>>() {
    }.getType();

    @TypeConverter
    public String fromStepList(List<Step> stepList) {
        if (stepList == null) {
            return (null);
        }
        Gson gson = new Gson();
        String json = gson.toJson(stepList, type);
        return json;
    }

    @TypeConverter
    public List<Step> toStepList(String stepListString) {
        if (stepListString == null) {
            return (null);
        }
        Gson gson = new Gson();
        List<Step> stepList = gson.fromJson(stepListString, type);
        return stepList;
    }
}
