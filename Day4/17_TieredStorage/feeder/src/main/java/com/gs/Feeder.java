package com.gs;

import com.gigaspaces.async.AsyncResult;
import com.gigaspaces.client.WriteModifiers;
import com.gigaspaces.document.SpaceDocument;
import com.gigaspaces.internal.server.space.tiered_storage.TieredStorageTableConfig;
import com.gigaspaces.metadata.SpaceTypeDescriptor;
import com.gigaspaces.metadata.SpaceTypeDescriptorBuilder;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.executor.DistributedTask;
import org.openspaces.core.space.SpaceProxyConfigurer;

import java.util.ArrayList;
import java.util.List;


public class Feeder {
    GigaSpace gs;
    final static int BATCH_SIZE = 10;
    final static long WAIT_TIME = 200;

    public Feeder(GigaSpace gs) {
        this.gs = gs;
    }

    public void feed(int amount) {

        SpaceTypeDescriptor typeDescriptorDoc1 = new SpaceTypeDescriptorBuilder("Doc1").supportsDynamicProperties(false)
                .idProperty("id")
                .addFixedProperty("info", String.class)
                .addFixedProperty("type", String.class)
                .addFixedProperty("id", Integer.class).create();

        SpaceTypeDescriptor typeDescriptorDoc2 = new SpaceTypeDescriptorBuilder("Doc2").supportsDynamicProperties(false)
                .idProperty("id")
                .addFixedProperty("info", String.class)
                .addFixedProperty("type", String.class)
                .addFixedProperty("id", Integer.class).create();

        // Example for setting criteria at registration time
        // This type doesn't have a criteria set in space config, without setting criteria here nothing will be cached.
        SpaceTypeDescriptor typeDescriptorDoc3 = new SpaceTypeDescriptorBuilder("Doc3").supportsDynamicProperties(false)
                .idProperty("id")
                .addFixedProperty("info", String.class)
                .addFixedProperty("type", String.class)
                .addFixedProperty("id", Integer.class)
                .setTieredStorageTableConfig(new TieredStorageTableConfig()
                        .setName("Doc3")
                        .setCriteria("type != '4' ")
                ).create();


        SpaceTypeDescriptor typeDescriptorDoc4 = new SpaceTypeDescriptorBuilder("Doc4").supportsDynamicProperties(false)
                .idProperty("id")
                .addFixedProperty("info", String.class)
                .addFixedProperty("type", String.class)
                .addFixedProperty("id", Integer.class).create();


        SpaceTypeDescriptor typeDescriptorDoc5 = new SpaceTypeDescriptorBuilder("Doc5").supportsDynamicProperties(false)
                .idProperty("id")
                .addFixedProperty("id", Integer.class)
                .addFixedProperty("expire", java.sql.Date.class)
                .addFixedProperty("info", String.class)
                .addFixedProperty("type", String.class).create();

        gs.getTypeManager().registerTypeDescriptor(typeDescriptorDoc1);
        gs.getTypeManager().registerTypeDescriptor(typeDescriptorDoc2);
        gs.getTypeManager().registerTypeDescriptor(typeDescriptorDoc3);
        gs.getTypeManager().registerTypeDescriptor(typeDescriptorDoc4);
        gs.getTypeManager().registerTypeDescriptor(typeDescriptorDoc5);

        ArrayList<SpaceDocument> batch = new ArrayList<>(BATCH_SIZE+5);

        for (int k = 0; k < amount; k++) {
            SpaceDocument doc1 = new SpaceDocument("Doc1");
            SpaceDocument doc2 = new SpaceDocument("Doc2");
            SpaceDocument doc3 = new SpaceDocument("Doc3");
            SpaceDocument doc4 = new SpaceDocument("Doc4");
            SpaceDocument doc5 = new SpaceDocument("Doc5");
            ;
            batch.add(getDoc(k, doc1));
            batch.add(getDoc(k, doc2));
            batch.add(getDoc(k, doc3));
            batch.add(getDoc(k, doc4));
            batch.add(getDoc(k, doc5));
            if (batch.size() >= BATCH_SIZE) {
                gs.writeMultiple(batch.toArray(new SpaceDocument[0]), WriteModifiers.ONE_WAY);
                batch.clear();
                try {
                    Thread.sleep(WAIT_TIME);
                }
                catch (Exception e){

                }
            }
        }


    }

    SpaceDocument getDoc(int k, SpaceDocument doc) {
        doc.setProperty("id", k);
        doc.setProperty("info", "info_" + k);
        doc.setProperty("type", String.valueOf(4 + (k % 5)));
        if (doc.getTypeName().equals("Doc5"))
            doc.setProperty("expire", new java.sql.Date(System.currentTimeMillis() - 24 * 60 * 60 * 10 * k));
        return doc;
    }


    public static void main(String[] args) throws InterruptedException {
        GigaSpace gs1 = new GigaSpaceConfigurer(new SpaceProxyConfigurer("test1").lookupGroups("xap-16.1.1")).gigaSpace();
        Feeder feeder = new Feeder(gs1);
        feeder.feed(100);

    }


}
