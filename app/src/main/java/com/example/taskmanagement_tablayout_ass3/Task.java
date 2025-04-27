package com.example.taskmanagement_tablayout_ass3;

public class Task {
    private String title, description, datetime;

    public Task(String title, String description, String datetime) {
        this.title = title;
        this.description = description;
        this.datetime = datetime;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDatetime() {
        return datetime;
    }
}
