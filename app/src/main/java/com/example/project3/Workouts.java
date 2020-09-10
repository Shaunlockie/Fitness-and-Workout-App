package com.example.project3;

public class Workouts {
    private String name;
    private String type;
    private int exercises;
    private int ex1Reps;
    private int ex1Sets;
    private int ex2Reps;
    private int ex2Sets;


    public Workouts() {
    }

    public Workouts(String name, String type, int exercises, int ex1Reps, int ex1Sets, int ex2Reps, int ex2Sets) {
        this.name = name;
        this.type = type;
        this.exercises = exercises;
        this.ex1Reps = ex1Reps;
        this.ex1Sets = ex1Sets;
        this.ex2Reps = ex2Reps;
        this.ex2Sets = ex2Sets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getExercises() {
        return exercises;
    }

    public void setExercises(int exercises) {
        this.exercises = exercises;
    }

    public int getEx1Reps() {
        return ex1Reps;
    }

    public void setEx1Reps(int ex1Reps) {
        this.ex1Reps = ex1Reps;
    }

    public int getEx1Sets() {
        return ex1Sets;
    }

    public void setEx1Sets(int ex1Sets) {
        this.ex1Sets = ex1Sets;
    }

    public int getEx2Reps() {
        return ex2Reps;
    }

    public void setEx2Reps(int ex2Reps) {
        this.ex2Reps = ex2Reps;
    }

    public int getEx2Sets() {
        return ex2Sets;
    }

    public void setEx2Sets(int ex2Sets) {
        this.ex2Sets = ex2Sets;
    }
}
