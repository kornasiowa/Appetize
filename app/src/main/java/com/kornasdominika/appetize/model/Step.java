package com.kornasdominika.appetize.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Step implements Serializable {

    @SerializedName("step")
    @Expose
    private int step;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("duration")
    @Expose
    private int duration;

    public Step() {
    }

    public Step(int step, String description, int duration) {
        this.step = step;
        this.description = description;
        this.duration = duration;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "RecipeStep{" +
                "step=" + step +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                '}';
    }
}
