package com.aziaka.donavan.todoapp;

public class Task {
    private String Name;
    private String Date;
    private String Category;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) { Date = date; }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
