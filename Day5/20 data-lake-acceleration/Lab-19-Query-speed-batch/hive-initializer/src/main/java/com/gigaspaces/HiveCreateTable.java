package com.gigaspaces;


import java.sql.SQLException;


public class HiveCreateTable {

    public static void main(String[] args) throws SQLException {
        createPersonTable();
    }


    private static void createPersonTable() throws SQLException{
        Utils.dropTable("Person");
        Utils.query("CREATE TABLE Person " +
                        "(id int, " +
                        "joinDate timestamp, " +
                        "name string, " +
                        "age int, " +
                        "salary int) " +
                        "PARTITIONED BY (country string, city string)" +
                        "ROW FORMAT " +
                        "SERDE 'org.apache.hadoop.hive.serde2.avro.AvroSerDe' " +
                        "STORED AS " +
                        "INPUTFORMAT 'org.apache.hadoop.hive.ql.io.avro.AvroContainerInputFormat' " +
                        "OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.avro.AvroContainerOutputFormat'");
    }







}
