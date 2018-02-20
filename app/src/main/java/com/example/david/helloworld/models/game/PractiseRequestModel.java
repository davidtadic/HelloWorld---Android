package com.example.david.helloworld.models.game;

import java.io.Serializable;

/**
 * Created by david on 11.2.2018..
 */

public class PractiseRequestModel implements Serializable{
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public QuestionLevel getLevel() {
        return level;
    }

    public void setLevel(QuestionLevel level) {
        this.level = level;
    }

    private Category category;
    private QuestionLevel level;
}
