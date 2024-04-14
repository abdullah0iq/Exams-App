package com.abdullahalmashhadani.examsapp.models;

import java.util.ArrayList;

public class Exam {

    private int id;
    private String title;
    private int is_taken = 0;

    public int getId() {
        return id;
    }

    private ArrayList<Question> questions;

    public Exam(int id,String title, ArrayList<Question> questions,int is_taken) {
        this.is_taken = is_taken;
        this.id = id;
        this.title = title;
        this.questions = questions;


    }



    public String getTitle() {
        return title;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public int getIs_taken() {
        return is_taken;
    }

    public void setIs_taken(int is_taken) {
        this.is_taken = is_taken;
    }
}
