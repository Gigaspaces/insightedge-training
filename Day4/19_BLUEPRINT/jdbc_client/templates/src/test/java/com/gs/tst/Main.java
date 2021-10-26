package com.gs.tst;

import com.gs.jdbc.inf.*;
import com.gs.jdbc.impl.*;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;

import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        String spaceName = "{{service.space}}";
        GigaSpace gigaSpace = new GigaSpaceConfigurer(new SpaceProxyConfigurer(spaceName)).gigaSpace();
        Main test = new Main();
        Feeder1 feeder1 = new Feeder1();
        feeder1.writeData(gigaSpace);
        test.example1(gigaSpace);
        test.example2(gigaSpace);
    }

    /**
     * Example with joins and jdbc v3
     * @param gigaSpace
     */
    public void example1(GigaSpace gigaSpace){
        String query = "Select P.name as product, sum(PU.amount) as pu_amount from \"com.gs.tst.Purchase\" as PU ";
        String condition = " LEFT JOIN \"com.gs.tst.Product\" as P ON PU.productId=P.id group by P.name";
        {{service.name}}Request request = new {{service.name}}Request(query+condition);
        Service service = new Service(gigaSpace, true);
        Collection<IResponse> responses = service.execute(request, new {{service.name}}ConvertResults());
        System.out.println("Example1");
        responses.forEach(response -> System.out.println(response.getResults()));
    }


    /**
     * Simple example with parameters and jdbc v1
     * @param gigaSpace
     */
    public void example2(GigaSpace gigaSpace){
        String query = "Select  P.name,  P.price  from com.gs.tst.Product as P  where  P.price > ? ";
        {{service.name}}Request request = new {{service.name}}Request(query, new Double(100.0));
        Service service = new Service(gigaSpace, false);
        Collection<IResponse> responses = service.execute(request, new {{service.name}}ConvertResults());
        System.out.println("Example2");
        responses.forEach(response -> System.out.println(response.getResults()));
    }
}
