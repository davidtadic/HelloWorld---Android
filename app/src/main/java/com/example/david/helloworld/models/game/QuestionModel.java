package com.example.david.helloworld.models.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by david on 11.2.2018..
 */

public class QuestionModel {
    private int id;
    private String questionName;
    private String correctAnswer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String wrongAnswer3;
    private Category category;
    private String imageThumbnail;
    private QuestionLevel levelQuestion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getWrongAnswer1() {
        return wrongAnswer1;
    }

    public void setWrongAnswer1(String wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return wrongAnswer2;
    }

    public void setWrongAnswer2(String wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }

    public String getWrongAnswer3() {
        return wrongAnswer3;
    }

    public void setWrongAnswer3(String wrongAnswer3) {
        this.wrongAnswer3 = wrongAnswer3;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public QuestionLevel getLevelQuestion() {
        return levelQuestion;
    }

    public void setLevelQuestion(QuestionLevel levelQuestion) {
        this.levelQuestion = levelQuestion;
    }

    public List<String> shuffleAnswers(){
        List<String> shuffle = new ArrayList<String>();

        shuffle.add(String.valueOf(correctAnswer));
        shuffle.add(String.valueOf(wrongAnswer1));
        shuffle.add(String.valueOf(wrongAnswer2));
        shuffle.add(String.valueOf(wrongAnswer3));

        Collections.shuffle(shuffle);

        return shuffle;

    }
}
