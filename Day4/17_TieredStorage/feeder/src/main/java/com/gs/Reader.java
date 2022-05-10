package com.gs;

import com.gigaspaces.async.AsyncResult;
import com.gigaspaces.client.CountModifiers;
import com.gigaspaces.document.SpaceDocument;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.executor.DistributedTask;
import org.openspaces.core.space.SpaceProxyConfigurer;

import java.util.List;

public class Reader {
    GigaSpace gs;
    public static void main(String[] args) {
            GigaSpace gs1 = new GigaSpaceConfigurer(new SpaceProxyConfigurer("test1").lookupGroups("xap-16.2.0").lookupLocators()).gigaSpace();
            Reader reader = new Reader(gs1);
            reader.read();

    }

    public Reader(GigaSpace gs) {
        this.gs = gs;
    }

    public void read(){
        printMemoryAndTotalCount("Doc1");
        printMemoryAndTotalCount("Doc2");
        printMemoryAndTotalCount("Doc3");
        printMemoryAndTotalCount("Doc4");
        printMemoryAndTotalCount("Doc5");
    }

    public void printMemoryAndTotalCount(String type){
        SpaceDocument spaceDocument = new SpaceDocument(type);
        int memCount = gs.count(spaceDocument, CountModifiers.MEMORY_ONLY_SEARCH);
        int total = gs.count(spaceDocument);
        System.out.println("Counts of " + type + " memory:" + memCount + " total:" + total);
    }

    public static class ReaderTask implements DistributedTask<Integer, Integer>, java.io.Serializable {

        @Override
        public Integer reduce(List<AsyncResult<Integer>> list) throws Exception {
            return null;
        }

        @Override
        public Integer execute() throws Exception {
            return null;
        }
    }


}
