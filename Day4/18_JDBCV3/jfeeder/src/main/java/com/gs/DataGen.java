package com.gs;


import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;


public class DataGen {

    public static void main(String[] args) throws SQLException {
        GigaSpace gs = new GigaSpaceConfigurer(new SpaceProxyConfigurer("demo")).gigaSpace();
        DataGen dataGenerator = new DataGen();
        dataGenerator.writeData(gs);
    }

    public void writeData(GigaSpace gs){
        ArrayList<Department> departments = new ArrayList<>(4);
        departments.add(new Department(1, "toys"));
        departments.add(new Department(2, "food"));
        departments.add(new Department(3, "sport"));
        departments.add(new Department(4, "kitchen-aid"));
        departments.forEach(d->gs.write(d));
        ArrayList<Product> products = new ArrayList<>(8);
        products.add(new Product(1,"Train",11.1,1));
        products.add(new Product(2,"Doll",17.8,1));
        products.add(new Product(3,"Chocolate",10.0,2));
        products.add(new Product(4,"Kandy",5.0,2));
        products.add(new Product(5,"Basketball",150.0,3));
        products.add(new Product(6,"Football",180.0,3));
        products.add(new Product(7,"coffee-machine",999.0,4));
        products.add(new Product(8,"Ice-maker",555.0,4));
        products.forEach(p-> gs.write(p));
        ArrayList<Customer> customers = new ArrayList<>(4);
        customers.add(new Customer("Cohen","Avi", Date.valueOf("1980-02-01"),1));
        customers.add(new Customer("Cohen","Sara", Date.valueOf("1984-03-05"),2));
        customers.add(new Customer("Levi","Sara", Date.valueOf("1989-11-11"),3));
        customers.add(new Customer("Levi","Moshe", Date.valueOf("2000-02-03"),4));
        customers.forEach(c-> gs.write(c));
        ArrayList<Purchase> purchese = new ArrayList<>(8);
        purchese.add(new Purchase(1,1, 1));
        purchese.add(new Purchase(2,2, 5));
        purchese.add(new Purchase(3,3, 5));
        purchese.add(new Purchase(4,4, 4));
        purchese.add(new Purchase(5,2, 4));
        purchese.add(new Purchase(6,2, 1));
        purchese.add(new Purchase(7,3, 3));
        purchese.add(new Purchase(8,1, 2));
        purchese.forEach(p->gs.write(p))  ;

    }



}
