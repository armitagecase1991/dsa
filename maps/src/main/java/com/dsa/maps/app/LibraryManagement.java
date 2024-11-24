package com.dsa.maps.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class LibraryManagement {
    private final Map<String, String> books = new HashMap<>();
    private final Map<String, Boolean> borrowStatus = new HashMap<>();
    private final Map<String, List<String>> borrowHistory = new HashMap<>();
    private final Map<String, Queue<String>> reservations = new HashMap<>();
    Queue<String> queue;

    public boolean addBook(String bookId, String title){
        if (!books.containsKey(bookId)) {
            books.put(bookId, title);
            borrowStatus.put(bookId, false);
            return true;
        }
        return false;
    }

    public String checkAvailability(String bookId) {
        if (books.containsKey(bookId) && !borrowStatus.get(bookId)) {
            return books.get(bookId);
        }
        return null;
    }

    public boolean borrowBook(String userId, String bookId) {
        if (books.containsKey(bookId) && !borrowStatus.get(bookId)) {
            borrowStatus.put(bookId, true);
            updateBorrowHistory(userId, bookId);
            return true;
        }
        return false;
    }

    public boolean returnBook(String bookId) {
        if (borrowStatus.containsKey(bookId) && borrowStatus.get(bookId)) {
            borrowStatus.put(bookId, false);

            if(this.reservations.containsKey(bookId) && !reservations.get(bookId).isEmpty()){
                String nextUser = reservations.get(bookId).poll();
                this.borrowBook(nextUser, bookId);
            }

            return true;
        }
        return false;
    }

     public List<String> getBorrowHistory(String bookId) {
        return borrowHistory.getOrDefault(bookId, new ArrayList<>());
    }

    private void updateBorrowHistory(String userId, String bookId) {
        if (!borrowHistory.containsKey(bookId)) {
            borrowHistory.put(bookId, new ArrayList<>());
        }
        borrowHistory.get(bookId).add(userId);
    }

    public boolean reserveBook(String userId, String bookId) {
        if (books.containsKey(bookId) && borrowStatus.get(bookId)) {
            reservations.putIfAbsent(bookId, new LinkedList<>());
            Queue<String> queue = reservations.get(bookId);
            if (!queue.contains(userId)) {
                queue.add(userId);
                return true;
            }
        }
        return false;
    }

    public String notifyAvailability(String bookId){
        if(this.reservations.containsKey(bookId)){
            if(!this.reservations.get(bookId).isEmpty()){
                return this.reservations.get(bookId).peek();
            }
        }

        return null;
    }

    public String checkReservation(String bookId) {
        if(this.reservations.get(bookId) == null) return null;

        if(!this.reservations.get(bookId).isEmpty()){
            return this.reservations.get(bookId).peek();
        }

        return null;
    }


}
