/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reti;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;


/**
 *
 * @author work
 */
public class Network {
    
    private ArrayList<NetworkNode> nodes;
    private ArrayList<NetworkDevice> devices;
    
    private static int Nnodes = 10;
    private static int Kdevices = 30;

    public Network() {
        nodes = new ArrayList<>(Nnodes);
        Random random = new Random();
        for(int i = 0; i<Nnodes;i++) {
            int x = random.nextInt(100);
            int y = random.nextInt(100);
            nodes.add(new NetworkNode(i, x, y));
           // int  = ((int) Math.random() * 10) % 5;
        }
        
        
        devices = new ArrayList<>(Kdevices);
        for(int i = 0; i<Kdevices;i++) {
            int x = random.nextInt(100);
            int y = random.nextInt(100);
            int priority = (x % 5) + 1;
            devices.add(new NetworkDevice(i, x, y, priority));
        }
        
        devices.forEach(item -> item.setNodes(nodes));
        devices.sort((a,b) -> b.getPriotity() - a.getPriotity());
        
        
    }
    
    public void runNetwork() {
        /*while(this.devices.stream().filter(e -> e.canRequest()).count() > 0){
            this.devices.forEach(NetworkDevice :: requestTask);
            this.nodes.forEach(NetworkNode :: acceptDevices);
        }*/
        while(this.devices.stream().filter(e -> e.canRequest()).count() > 0){
            this.devices.stream().filter(e -> e.canRequest()).forEach(NetworkDevice :: requestTask);
            this.nodes.forEach(NetworkNode :: acceptDevices);
        }
        Random random = new Random();
        ArrayList<NetworkDevice> notAllocated = getNotAllocated();
        while(notAllocated.size() > 0){
            int index = random.nextInt(notAllocated.size());
            NetworkNode node =this.nodes.stream().min((a,b) -> a.getNAllocatedDevices() - b.getNAllocatedDevices()).orElseThrow(NoSuchElementException::new);
            node.allocate(notAllocated.get(index));
            notAllocated = getNotAllocated();
            //System.out.println(notAllocated.size());
        }
        
           // System.out.println(this.devices.stream().filter(e -> e.bindingDone()).count());
           devices.forEach(item -> System.out.println(item));
    }
    
    
    private ArrayList<NetworkDevice> getNotAllocated() {
        ArrayList<NetworkDevice> notAllocated = new ArrayList<>();
        this.devices.forEach(device -> {
            if(!device.bindingDone())
                notAllocated.add(device);
        });
        return notAllocated;
                    
    }
    
}
