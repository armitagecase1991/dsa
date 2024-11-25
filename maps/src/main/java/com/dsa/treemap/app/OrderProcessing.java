package com.dsa.treemap.app;

import java.util.Map.Entry;
import java.util.TreeMap;

public class OrderProcessing {

    private TreeMap<Integer,String> orders = new TreeMap<>();

    public void addOrder(String customer, int priority) {
        this.orders.put(priority, customer);
    }

    public String processOrder() throws Exception {

        if(this.orders.isEmpty()) throw new Exception(" the orders cannot be empt ");

        Entry<Integer, String> firstEntry = this.orders.pollFirstEntry();
        String firstRecord = firstEntry.getKey() + "," + firstEntry.getValue();
        return firstRecord;
    }
}
