package com.example.entity;

import lombok.Data;

@Data
public class Trip {
    private Integer id;
    private String name;
    private String type;
    private String beginTime;
    private String endTime;
    private String notes;

    public Trip() {
    }

    public Trip(Integer id, String name, String beginTime, String endTime, String notes) {
        this.id = id;
        this.name = name;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.notes = notes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "trip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
