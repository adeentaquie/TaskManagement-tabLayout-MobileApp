package com.example.taskmanagement_tablayout_ass3;

public class NotificationModel {
    private String message;
    private String datetime;

    public NotificationModel(String message, String datetime) {
        this.message = message;
        this.datetime = datetime;
    }

    public String getMessage() {
        return message;
    }

    public String getDatetime() {
        return datetime;
    }
}
