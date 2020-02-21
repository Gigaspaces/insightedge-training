# Lab: SQL Query Benchmark Spark vs InsightEdge

# Instructions

1. Start InsightEdge in Demo mode:

    go to gigaspaces-insightedge-enterprise-14.5.0/bin

    run ./gs.sh demo

2. Open Zeppelin on http://localhost:9090

3. Import to Zeppelin the following Notebook:

    insightedge-training/Day2/8_InsightEdge/Lab-8.1-SQL_Query_Benchmark_Spark_vs_InsightEdge/Lab-8.1-Example-SQL_Query_Benchmark_Spark_vs_InsightEdge.json

4. Download 2007.csv from here: http://stat-computing.org/dataexpo/2009/the-data.html to your ../Data folder

5. Follow the instructions inside the Notebook paragraphs.

**In Step1 - IE Query Measurement (import dependencies) please verify the following:**

If the uber jar FlightDelayDemo-1.0.0-SNAPSHOT.jar is not already created, you can generate it by going to "Day5/17_Flight_Delay_Demo/Lab-17.1-Flight_Delay_Demo/flight-delay-prediction-demo" and running mvn package.

Please copy FlightDelayDemo-1.0.0-SNAPSHOT.jar to the Data directory.

**Note:** If running Zeppelin notebooks on Windows, you might need to change println to print and also add "%n" in order to display the output properly. 

If the Zepplin interpretor instance fails to work correctly on Windows, you will need to shutdown all browser windows and restart insightedge.
