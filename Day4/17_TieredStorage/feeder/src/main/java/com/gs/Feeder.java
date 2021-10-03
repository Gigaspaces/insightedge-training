package com.gs;

import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;
import org.openspaces.core.space.cache.LocalViewSpaceConfigurer;

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

        long start2 = System.currentTimeMillis();
        Data1[] res4 = gs.readMultiple(new Data1(),amount);
        System.out.println("read of Data1 took:" + (System.currentTimeMillis() - start2) + " res:" + res4.length);
    }

    public static void main(String[] args) throws InterruptedException {
        GigaSpace gs1 = new GigaSpaceConfigurer(new SpaceProxyConfigurer("test1")).gigaSpace();
        LocalViewSpaceConfigurer localViewConfigurer = new LocalViewSpaceConfigurer(new SpaceProxyConfigurer("test1"))
                .batchSize(1000)
                .batchTimeout(100)
                .maxDisconnectionDuration(1000*60*60)
                .addProperty("space-config.engine.memory_usage.high_watermark_percentage", "90")
                .addProperty("space-config.engine.memory_usage.write_only_block_percentage", "88")
                .addProperty("space-config.engine.memory_usage.write_only_check_percentage", "86")
                .addProperty("space-config.engine.memory_usage.retry_count", "5")
                .addProperty("space-config.engine.memory_usage.explicit", "false")
                .addProperty("space-config.engine.memory_usage.retry_yield_time", "50")
                .addViewQuery(new SQLQuery(com.gs.Data1.class, ""));
// Create local view:
        GigaSpace localView = new GigaSpaceConfigurer(localViewConfigurer).gigaSpace();

        Feeder feeder = new Feeder(gs1);
        feeder.feed(1000);
        feeder.read(1000);
        Thread.sleep(1000);
        Data1 obj = localView.read(new Data1());
        System.out.println("got object:" + obj);
    }
}
