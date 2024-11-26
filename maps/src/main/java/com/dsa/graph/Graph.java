package com.dsa.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Graph {
    private HashMap<String, ArrayList<String>> adjList = new HashMap<>();

    public boolean addVertex(String vertex){

        if(this.adjList.containsKey(vertex)) return false;

        this.adjList.put(vertex, new ArrayList<>());

        return true;
    }

    public boolean removeVertex(String vertex){
        if(this.adjList.isEmpty() || !this.adjList.containsKey(vertex)) return false;

        this.adjList.get(vertex).stream().forEach(item -> {
            this.adjList.get(item).remove(vertex);
        });

        this.adjList.remove(vertex);
        return true;
    }

    public void printGraph(){
        for(Entry<String, ArrayList<String>> data:this.adjList.entrySet()){
            System.out.println(data);
        }

    }

    public boolean removeEdge(String vertex1, String vertex2){
        if(this.adjList.isEmpty() || !this.adjList.containsKey(vertex1) || !this.adjList.containsKey(vertex2)) return false;

        this.adjList.get(vertex1).removeIf(item-> item.equals(vertex2));
        this.adjList.get(vertex2).removeIf(item-> item.equals(vertex1));

        return false;
        
    }

    public boolean addEdge(String vertex1, String vertex2){

        if(this.adjList.isEmpty() || !this.adjList.containsKey(vertex1) || !this.adjList.containsKey(vertex2)) return false;

        this.adjList.get(vertex1).add(vertex2);
        this.adjList.get(vertex2).add(vertex1);

        return true;
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "C");

        graph.printGraph();
        graph.removeVertex("C");
        graph.printGraph();

    }

    
}
