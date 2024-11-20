package queue_dequeue;

import java.util.ArrayDeque;
import java.util.Deque;

public class BrowserHistoryQueue {
    private Deque<String> history;
    private Deque<String> future;

    public BrowserHistoryQueue() {
        this.history = new ArrayDeque<>();
        this.future = new ArrayDeque<>();
    }

    public void visit(String url) {
        this.history.addLast(url);

        if(!this.future.isEmpty()) this.future.removeLast();
    }

    public String back(int steps) {
        String currentPage = this.history.peekLast();
        if(!this.history.isEmpty()){
            while(steps < 0){
                this.future.addFirst((this.history.removeLast()));
                steps--;
            }
        } 
        
        return currentPage;
        
    }

    public String forward(int steps) {
        if(!this.future.isEmpty()){
            while(steps > 0 && !this.future.isEmpty()){
                this.history.addLast(this.future.removeFirst());
                steps--;
            }
        }

        return this.history.peekLast();
    }

    public static void main(String[] args) {
        BrowserHistoryQueue browser = new BrowserHistoryQueue();
        browser.visit("example.com");
        browser.visit("wikipedia.org");
        browser.visit("twitter.com");
        System.out.println("Went back, current page: " + browser.back(2));
        System.out.println("Went forward, current page: " + browser.forward(1));
    }
}
