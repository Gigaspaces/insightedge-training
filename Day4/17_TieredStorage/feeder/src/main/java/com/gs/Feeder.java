package com.gs;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;

public class Feeder {
    GigaSpace gs;

    public Feeder(GigaSpace gs) {
        this.gs = gs;
    }

    public void feed(int amount){
        Data1[] objects = new Data1[amount];
        for (int k=0; k<amount; k++){
            objects[k]= new Data1(k, "" + (4 + (k%2)));
            gs.write(new Data2(k, "" + (4 + (k%2))));
            gs.write(new Data3(k, "" + (4 + (k%2))));
            gs.write(new Data4(k, "" + (4 + (k%2))));
        }
        gs.writeMultiple(objects);
    }

    public void read(int amount){
        long start = System.currentTimeMillis();
        Data2[] res2 = gs.readMultiple(new Data2(),amount);
        System.out.println("read of Data2 took:" + (System.currentTimeMillis() - start) + " res:" + res2.length);
        long start1 = System.currentTimeMillis();
        //Data3[] res3 = gs.readMultiple(new Data3(),amount);
        //System.out.println("read of Data3 took:" + (System.currentTimeMillis() - start1) + " res:" + res3.length);
        long start2 = System.currentTimeMillis();
        Data1[] res4 = gs.readMultiple(new Data1(),amount);
        System.out.println("read of Data3 took:" + (System.currentTimeMillis() - start2) + " res:" + res4.length);
    }

    public static void main(String[] args) {
        GigaSpace gs1 = new GigaSpaceConfigurer(new SpaceProxyConfigurer("myspace")).gigaSpace();
        Feeder feeder = new Feeder(gs1);
        feeder.feed(1000);
        feeder.read(1000);
    }
}
