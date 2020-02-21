package com.gigaspaces;

import model.Person;

import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HiveWriteToTable {

    public static void main(String[] args) throws SQLException {
        /*generate data before and in the index period*/
        List<Person> objects = new ArrayList<>(100);
        objects.addAll(Arrays.asList(Person.generate(1, 10, Duration.parse("pt15h"))));
        objects.addAll(Arrays.asList(Person.generate(11, 10, Duration.parse("pt14h"))));
        objects.addAll(Arrays.asList(Person.generate(21, 10, Duration.parse("pt13h"))));
        objects.addAll(Arrays.asList(Person.generate(31, 10, Duration.parse("pt12h"))));
        objects.addAll(Arrays.asList(Person.generate(41, 10, Duration.parse("pt11h"))));
        objects.addAll(Arrays.asList(Person.generate(51, 10, Duration.parse("pt9h"))));
        objects.addAll(Arrays.asList(Person.generate(61, 10, Duration.parse("pt8h"))));
        objects.addAll(Arrays.asList(Person.generate(71, 10, Duration.parse("pt7h"))));
        objects.addAll(Arrays.asList(Person.generate(81, 10, Duration.parse("pt6h"))));
        objects.addAll(Arrays.asList(Person.generate(91, 10, Duration.parse("pt5h"))));
        for (Person example : objects) {
            Utils.query(createInsertQuery(example));
        }
    }


    private static String createInsertQuery(Person person) {
        /*sql = "INSERT INTO " + tableName + " PARTITION (" +
                partitionArguments + ") VALUES (" + arguments + ")";*/
        StringBuilder sb = new StringBuilder("INSERT INTO Person PARTITION ( ");
        sb.append("country='").append(person.getCountry()).append("',city='").append(person.getCity()).append("') ");
        sb.append("VALUES (");
        sb.append("'").append(person.getId()).append("', ");
        sb.append("'").append(person.getJoinDate()).append("', ");
        sb.append("'").append(person.getName()).append("', ");
        sb.append("'").append(person.getAge()).append("', ");
        sb.append("'").append(person.getSalary());
        sb.append("')");
        return sb.toString();
    }


}
