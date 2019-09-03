package com.example.wordstudy;

public class WordData {

    private long id;
    private String name;
    private String mean;
    private int count;


    public WordData() {
        this(0, null);
    }

    public WordData(String name) {
        this(0, name);
    }

    public WordData(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

