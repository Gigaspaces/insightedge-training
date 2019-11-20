package com.gigaspaces;


import model.Person;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Write to speed layer
 */
public class Client {
    private static GigaSpace speedSpace;
    private static List<Person> objects = new ArrayList<>(1000);

    public static void main(String[] args) {
        speedSpace = new GigaSpaceConfigurer(new UrlSpaceConfigurer("jini://*/*/speedSpace")).gigaSpace();
        Person[]  objects = Person.generate(2000, 1000, Duration.parse("pt1s"));
        speedSpace.writeMultiple(objects);
    }
}
