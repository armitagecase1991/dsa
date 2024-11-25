package com.dsa.maps.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dsa.maps.entity.Group;
import com.dsa.maps.entity.Post;


public class SocialMedia{
    private final Map<String, Post> posts;
    private final Map<String, List<String>> groupPosts;
    private final Map<String, Group> groups;

    public SocialMedia() {
        posts = new HashMap<>();
        groupPosts = new HashMap<>();
        this.groups = new HashMap<>();
    }

    public boolean createPost(String postId, String content) {
        if (posts.containsKey(postId)) return false;
        posts.put(postId, new Post(content));
        return true;
    }

    public boolean deletePost(String postId) {
        if (!posts.containsKey(postId)) return false;
        posts.remove(postId);
        return true;
    }

    public boolean reactToPost(String userId, String postId, String reactionType) {
        Post post = posts.get(postId);
        if (post == null) return false;
        return post.addReaction(userId, reactionType);
    }

    public Map<String, Integer> getReactionSummary(String postId) {
        Post post = posts.get(postId);
        return post == null ? null : post.getReactionSummary();
    }

    boolean createGroup(String groupId, String operatorId){
        if(this.groups.get(groupId) != null) return false;

        this.groups.put(groupId, new Group(operatorId));
        return true;
    }

    public boolean addMember(String groupId, String userId, String operatorId){

        if(!this.groups.containsKey(groupId))
            return false;

        Group group = this.groups.get(groupId);

        if(!group.getOperator().equals(operatorId)) return false;

        boolean memberAlreadyAdded = group.getMembers().stream().filter(item -> item.equals(userId)).findFirst().isPresent();

        if(memberAlreadyAdded) return false;

        group.addMember(userId);

        return true;
    }

    public boolean sharePostToGroup(String postId, String groupId, String userId){

        if(!posts.containsKey(postId)) return false;

        if(!groups.containsKey(groupId)) return false;

        Group group = groups.get(groupId);

        boolean isMember = group.getMembers().stream().filter(item -> item.equals(userId)).findAny().isPresent();

        if(!isMember) return false;

        groupPosts.computeIfAbsent(groupId, k -> new ArrayList<>()).add(postId);

        return true;
    }


    public List<String> getGroupPosts(String groupId){
        return groupPosts.getOrDefault(groupId, new ArrayList<>());

    }



}