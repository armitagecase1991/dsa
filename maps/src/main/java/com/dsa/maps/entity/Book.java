package com.dsa.maps.entity;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String id;
    private String title;
    private String status;  // "available" or "borrowed"
    private List<String> history;
    private boolean reserved;

    
    
    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public Book(String id, String title) {
        this.id = id;
        this.title = title;
        this.status = "available";
        this.history = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    public void addHistory(String action) {
        history.add(action);
    }
}

