package com.example.david.helloworld.models.game;

import java.util.Date;

/**
 * Created by david on 20.2.2018..
 */

public class PractiseModel {
    private int id;
    private Category category;
    private QuestionLevel level;
    private int userId;
    private int points;
    private String datePlayed;

    public int getId() {
        return id;
    }

    public QuestionLevel getLevel() {
        return level;
    }

    public void setLevel(QuestionLevel level) {
        this.level = level;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDatePlayed() {
        return datePlayed;
    }

    public void setDatePlayed(String datePlayed) {
        this.datePlayed = datePlayed;
    }
}
