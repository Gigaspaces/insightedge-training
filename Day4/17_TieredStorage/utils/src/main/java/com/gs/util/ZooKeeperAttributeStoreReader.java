package com.gs.util;


import com.gigaspaces.internal.utils.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;

import org.apache.curator.retry.RetryOneTime;



import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.function.Consumer;


public class ZooKeeperAttributeStoreReader implements Serializable {
    String name;
    CuratorFramework client;

    public ZooKeeperAttributeStoreReader(String name, CuratorFramework client) throws InterruptedException {
        this.name = name;
        this.client = client;
        client.start();
        client.blockUntilConnected();
    }

    public static void main(String[] args) throws Exception {

        String    connectionString = "localhost";
        ZooKeeperAttributeStoreReader reader = new ZooKeeperAttributeStoreReader("/xap", CuratorFrameworkFactory.newClient(connectionString, new RetryOneTime(2000)));
        long startTime = System.currentTimeMillis();
        reader.dump(System.out);
        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println("ZooKeeper data report completed (duration=" + elapsed + "ms)");
    }

    public void dump(PrintStream printStream) throws Exception {
        dump(printStream::println);
    }

    public void dump(Consumer<String> output) throws Exception {
        if (!name.isEmpty()) {
            dump(name, "", output);
        } else {
            for (String key : client.getChildren().forPath("/")) {
                dump(toPath(key), "", output);
            }
        }
    }

    private String toPath(String key) {
        return name + (key.startsWith("/") ? key : "/" + key);
    }

    private static String toString(byte[] bytes) {
        return bytes != null ? new String(bytes) : null;
    }


    private void dump(String path, String prefix, Consumer<String> output) throws IOException {
        try {
            byte[] bytes = client.getData().forPath(path);
            String key = StringUtils.getSuffix(path, "/");
            output.accept(prefix + key + ": " + toString(bytes));
            prefix += "   ";
            for (String subKey : client.getChildren().forPath(path)) {
                dump(path + "/" + subKey, prefix, output);
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
    }


}
