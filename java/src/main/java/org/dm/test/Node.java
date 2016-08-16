package org.dm.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mirage on 2016-08-15.
 */
public class Node {
    private int data;
    private Node parent;
    private Map<Integer,Node> children = new HashMap<>();
    private int count = 0;

    public void setData(int data) {
        this.data = data;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void addChild(Integer data,Node node){
        children.put(data,node);
    }

    public Node getChild(Integer data){
        return children.get(data);
    }

    public boolean hasChildes(){
        return children.size()!=0;
    }

    public int getData() {
        return data;
    }

    public Map<Integer,Node> getMap(){
        return children;
    }

    public void increse(){
        count++;
    }

    public int getCount() {
        return count;
    }
}
