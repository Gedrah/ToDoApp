package eu.epitech.todolist.POJO;

public class Task {
    private int id;
    private String Title;
    private String Description;
    private String Date;
    private String Time;
    private String Category;
    private String State;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String dueDate) {
        this.Date = dueDate;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String hours) {
        Time = hours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
