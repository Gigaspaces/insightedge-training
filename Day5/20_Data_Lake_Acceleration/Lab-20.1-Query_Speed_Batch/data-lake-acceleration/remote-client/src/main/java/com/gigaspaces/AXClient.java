package com.gigaspaces;

import com.gigaspaces.analytics_xtreme.AnalyticsXtremeService;
import com.gigaspaces.analytics_xtreme.batch_index.service.BatchIndexService;
import com.gigaspaces.analytics_xtreme.batch_index.service.BatchPartitionValues;
import com.gigaspaces.analytics_xtreme.batch_index.service.ValueDistributionInfo;
import com.gigaspaces.analytics_xtreme.batch_index.statistics.IndexColumnStatsDocument;
import com.gigaspaces.analytics_xtreme.batch_index.statistics.IndexStatistics;
import com.gigaspaces.analytics_xtreme.batch_index.statistics.IndexTableInfoDocument;
import com.gigaspaces.analytics_xtreme.internal.TimeProvider;

import com.j_spaces.core.client.SpaceURL;
import model.Person;
import org.hsqldb.lib.StopWatch;


import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import java.sql.*;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/*
see statistics and performance of various queries
 */
public class AXClient {

    private static GigaSpace speedSpace;
    private static List<Person> objects = new ArrayList<>(100);
    private static GigaSpace index;

    public static void main(String[] args) throws Exception {
        try {
            speedSpace = new GigaSpaceConfigurer(new UrlSpaceConfigurer("jini://*/*/speedSpace")).gigaSpace();
            index =  new GigaSpaceConfigurer(new UrlSpaceConfigurer("jini://*/*/batchIndex")).gigaSpace();

            speedSpace.getTypeManager().registerTypeDescriptor(Person.class);

            BatchIndexService indexService = BatchIndexService.proxy(index);
            while (!(indexService.isValid(Person.class.getSimpleName(), "name") && indexService.isValid(Person.class.getSimpleName(), "age"))) {
                System.out.println("waiting for indexes to initialize");
                Thread.sleep(30 * 1000);
            }


            Map<Object, ValueDistributionInfo> name = indexService.getIndexValuesDistribution(Person.class.getSimpleName(), "name");
            Map<Object, ValueDistributionInfo> age = indexService.getIndexValuesDistribution(Person.class.getSimpleName(), "age");

            System.out.println("name distribution:");
            for (Map.Entry<Object, ValueDistributionInfo> entry : name.entrySet()) {
                System.out.println("index value =" + entry.getKey());
                for (Map.Entry<Instant, Integer> distEntry : entry.getValue().getPartitionsPerSlice().entrySet()) {
                    System.out.println("slice " + distEntry.getKey() + " = " + distEntry.getValue());
                }
            }

            System.out.println("\nage distribution:");
            for (Map.Entry<Object, ValueDistributionInfo> entry : age.entrySet()) {
                System.out.println("index value =" + entry.getKey());
                for (Map.Entry<Instant, Integer> distEntry : entry.getValue().getPartitionsPerSlice().entrySet()) {
                    System.out.println("slice " + distEntry.getKey() + " = " + distEntry.getValue());
                }
            }

            objects.clear();

            TimeProvider timeProvider = TimeProvider.of("yyyy-MM-dd HH:mm:ss");

            queryAx("SELECT * FROM Person WHERE name = 'Alon'");
            printAll();

            Instant NOW = Clock.systemUTC().instant();

            objects.clear();
            //basic index
            Instant indexTh = NOW.minus(Duration.parse("pt9h59m"));
            queryAx("SELECT * FROM Person WHERE name = 'Alon' AND joinDate > '" + timeProvider.format(indexTh) + "'");
            printAll();

            objects.clear();
            //Granularity
            Instant end = NOW.minus(Duration.parse("pt5h"));
            Instant start = NOW.minus(Duration.parse("pt7h"));
            queryAx("SELECT * FROM Person WHERE name = 'Alon' " + "AND joinDate > '" + timeProvider.format(start) + "' AND joinDate < '" + timeProvider.format(end) + "'");
            printAll();

            objects.clear();
            //Ordered index
            queryAx("SELECT * FROM Person WHERE age > 40 AND joinDate > '" + timeProvider.format(indexTh) + "'");
            printAll();

        } catch (AssertionError e) {
            System.exit(1);
        }

        System.exit(0);
    }

    private static void printAll() throws SQLException {
        printAxQueryStats();
        printAxIndexStats(Person.class.getSimpleName(), "name");
        printAxIndexStats(Person.class.getSimpleName(), "age");
        for (Person object : objects) {
            System.out.println(object);
        }
    }

    private static void queryAx(String sql) throws SQLException {
        SpaceURL url = speedSpace.getSpace().getFinderURL();
        try (Connection connection = DriverManager.getConnection("jdbc:insightedge:url=" + url + ";analyticsXtreme.enabled=true")) {
            try (Statement statement = connection.createStatement()) {
                StopWatch watch = new StopWatch();
                watch.start();
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        objects.add(getObject(resultSet));
                    }
                }
            }
        }
    }

    private static void printAxQueryStats() throws SQLException {
        SpaceURL url = speedSpace.getSpace().getFinderURL();
        try (Connection connection = DriverManager.getConnection("jdbc:insightedge:url=" + url + ";analyticsXtreme.enabled=true")) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("SELECT * FROM AnalyticsXtremeQueryStats ORDER BY startTime DESC LIMIT 1")) {
                    while (resultSet.next()) {
                        System.out.println(
                                "------------------------------------------------------------------\n" +
                                        "speedDuration = " + resultSet.getInt("speedDuration") +
                                        "\nbatchDuration = " + resultSet.getInt("batchDuration") +
                                        "\noriginalQuery = " + resultSet.getString("originalQuery") +
                                        "\nspeedQuery = " + resultSet.getString("speedQuery") +
                                        "\nbatchQuery = " + resultSet.getString("batchQuery") +
                                        "\n------------------------------------------------------------------\n");
                    }
                }
            }
        }
    }

    private static void printAxIndexStats(String tableName, String indexName) {
        IndexTableInfoDocument tableStats = index.readById(IndexTableInfoDocument.getIdQuery(tableName));
        IndexColumnStatsDocument columnStats = index.readById(IndexColumnStatsDocument.getIdQuery(tableName, indexName));
        Set<BatchPartitionValues> currentPartitions = tableStats == null ? Collections.emptySet() : tableStats.getCurrentPartitions();
        IndexStatistics statistics = columnStats == null ? new IndexStatistics() : columnStats.getStatistics();
        System.out.println("------------------------------------------------------------------\n" +
                "Indexing Statistics:\n" +
                "index name = '" + indexName + "'\n" +
                "seen partitions = " + currentPartitions + "\n" +
                "total hits = " + statistics.getTotalHits() + "\n" +
                "last query efficiency = " + statistics.getLastQueryEfficiency() + "\n" +
                "average query efficiency = " + statistics.getAverageEfficiency() + "\n" +
                "------------------------------------------------------------------");
    }

    private static Person getObject(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getInt("id"),
                resultSet.getTimestamp("joinDate"),
                resultSet.getString("name"),
                resultSet.getString("country"),
                resultSet.getString("city"),
                resultSet.getInt("age"),
                resultSet.getInt("salary")
        );
    }

}
