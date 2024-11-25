package com.dsa.maps.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group {
    private final String operator;
    private final List<String> members;

    public Group(String operator) {
        this.operator = operator;
        this.members = new ArrayList<>(Collections.singletonList(operator));
    }

    public String getOperator() {
        return operator;
    }

    public List<String> getMembers() {
        return members;
    }

    public void addMember(String userId){
        this.members.add(userId);
    }
    
}
