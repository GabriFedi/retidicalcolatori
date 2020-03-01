/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reti;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author work
 */
public class NetworkDevice implements NetworkItem{
    
   private int id;
   private int x;
   private int y;
   private int priority;
   private ArrayList<NetworkNode> nodes;
   private ArrayList<Integer> sortedNodes;
   private ArrayList<Integer> askedNodes;
   private int lastAsked = 0;
   public ArrayList<NetworkNode> bindResources;
   private final int resourceNeeded = 2;


    public NetworkDevice(int id, int x, int y, int priority) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.priority = priority;
        this.bindResources = new ArrayList<>();
        this.askedNodes = new ArrayList<>();
    }
    
   

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getPriotity() {
        return priority;
    }

    public void setNodes(ArrayList<NetworkNode> nodes) {
        this.nodes = nodes;
        this.sortedNodes = new ArrayList<Integer> (this.nodes.stream().sorted((a,b) -> {
            double dist = this.getNodeDistance(a) - this.getNodeDistance(b);
            return (int) (dist < 0 ? Math.floor(dist) : Math.ceil(dist)); 
        }).map(NetworkItem::getId).collect(Collectors.toList()));
    }
    
    public double getNodeDistance(NetworkItem other) {
        return Math.sqrt(Math.pow(this.getX() - other.getX(), 2) + Math.pow(this.getY() - other.getY(), 2));
    }
    
    public boolean bindResource(NetworkNode node) {
        if(this.bindResources.size() < this.resourceNeeded && !this.bindResources.contains(node)){
            this.bindResources.add(node);
            return true;
        }
        return false;
    }
    
    public void requestTask() {
        for(int i = this.bindResources.size(); i < this.resourceNeeded; i++){
            int index = getNextNodeIndex();
            if(lastAsked < this.sortedNodes.size()) {
                this.nodes.get(index).addToQueue(this);
                this.askedNodes.add(index);
            }
        }
    }
    
    public boolean canRequest() {
        return (this.bindResources.size() < this.resourceNeeded && this.askedNodes.size() < this.nodes.size());
    }
    
    public boolean bindingDone() {
        return this.bindResources.size() == this.resourceNeeded;
    }
    
    public int getNextNodeIndex() {
        for(int index : sortedNodes) {
               if(!this.askedNodes.contains(index))
                   return index;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "NetworkDevice:" + " id=" + id + " - R1: " + this.bindResources.get(0).getId() + " - R2: " + this.bindResources.get(1).getId() ;
    }
    
   
    
}
