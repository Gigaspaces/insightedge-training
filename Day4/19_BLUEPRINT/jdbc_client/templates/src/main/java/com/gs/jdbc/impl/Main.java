package com.gs.jdbc.impl;

import com.gs.jdbc.inf.IResponse;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;

import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        String spaceName = "{{service.space}}";
        GigaSpace gigaSpace = new GigaSpaceConfigurer(new SpaceProxyConfigurer(spaceName)).gigaSpace();
        Main test = new Main();
        test.example1(gigaSpace);
        test.example2(gigaSpace);
    }

    /**
     * Example with joins and jdbc v3
     * @param gigaSpace
     */
    public void example1(GigaSpace gigaSpace){
        String query = "Select P.name as product, sum(PU.amount) as pu_amount from \"com.gs.Purchase\" as PU ";
        String condition = " LEFT JOIN \"com.gs.Product\" as P ON PU.productId=P.id group by P.name";
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
        String query = "Select  P.name,  P.price  from com.gs.Product as P  where  P.price > ? ";
        {{service.name}}Request request = new {{service.name}}Request(query, new Double(100.0));
        Service service = new Service(gigaSpace, false);
        Collection<IResponse> responses = service.execute(request, new {{service.name}}ConvertResults());
        System.out.println("Example2");
        responses.forEach(response -> System.out.println(response.getResults()));
    }
}
