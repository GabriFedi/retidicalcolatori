/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reti;

import java.util.ArrayList;

/**
 *
 * @author work
 */
public class NetworkNode implements NetworkItem{
    
    private int id;
    private int x;
    private int y;
    private ArrayList<NetworkItem> devices;
    private ArrayList<NetworkDevice> deviceQueue;
    private ArrayList<NetworkDevice> bindedDevices;
    

    public NetworkNode(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;        
        deviceQueue = new ArrayList<>();
        bindedDevices = new ArrayList<>();

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
    public int getId() {
        return id;
    }

    @Override
    public int getPriotity() {
        throw new UnsupportedOperationException("Not supported on Network node"); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void addToQueue(NetworkDevice device) {
        deviceQueue.add(device);
    }
    
    public void acceptDevices() {
        if(deviceQueue.size() > 0) {
            deviceQueue.sort((a,b) -> b.getPriotity() - a.getPriotity());
            this.bindedDevices.add(deviceQueue.get(0));
            deviceQueue.get(0).bindResource(this);
            deviceQueue.clear();
        }
    }
    
    public int getNAllocatedDevices() {
        return this.bindedDevices.size();
    }
    
    public void allocate(NetworkDevice device) {
        if(device.bindResource(this))
            this.bindedDevices.add(device);
    }

    @Override
    public String toString() {
        return "NetworkNode{" + "id=" + id + ", x=" + x + ", y=" + y + '}';
    }
    
    
    
    
    
}
