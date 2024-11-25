package com.dsa.maps.entity;

import java.util.HashMap;
import java.util.Map;

public class Post {
    private final String content;
    private final Map<String, String> reactions;

    public Post(String content) {
        this.content = content;
        this.reactions = new HashMap<>();
    }

    public boolean addReaction(String userId, String reactionType) {
        if (reactions.containsKey(userId)) return false;
        reactions.put(userId, reactionType);
        return true;
    }

    public Map<String, Integer> getReactionSummary() {
        Map<String, Integer> summary = new HashMap<>();
        for (String reaction : reactions.values()) {
            summary.put(reaction, summary.getOrDefault(reaction, 0) + 1);
        }
        return summary;
    }

}
