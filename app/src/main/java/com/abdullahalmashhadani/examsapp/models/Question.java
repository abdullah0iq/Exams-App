package com.abdullahalmashhadani.examsapp.models;

public class Question {
    private int id;
    private String title;
    private int solution;

    public int getId() {
        return id;
    }

    private int answer;
    private int exam_id;

    public Question(String title, int solution, int answer, int exam_id, int id) {
        this.id = id;
        this.title = title;
        this.solution = solution;
        this.answer = answer;
        this.exam_id = exam_id;
    }

    public String getTitle() {
        return title;
    }

    public int getSolution() {
        return solution;
    }



    public int getAnswer() {
        return answer;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
