# Lab: DataFrame Save And Load DataFrame From Grid

In this lab you will learn how to connect the grid,
save a DataFrame to the grid
and then load the DataFrame from the grid.

# Instructions

1. Start InsightEdge in Demo mode:

    go to gigaspaces-insightedge-enterprise-14.0.0-ga-b20000/bin

    run ./insightedge demo

2. run SaveLoadDataframeFromGrid.scala class.


# How to verify dataframe persisted results through XAP Web UI

1. Open web ui http://localhost:8099/
2. Click Spaces from Top Menu
3. Click on Types next to Queries from center menu buttons
4. You can see People Types. Click on this.
5. It will open up Queries and you can verify both entries for john and sam.

# Troubleshooting
## Problem 1:

Error: Could not find or load main class com.gigaspaces.ietraining.SaveLoadDataframeFromGrid

Solution:

Create a separate project by pointing to the project pom.xml file.

e.g:

From Intelij menu click on: File -> New -> Project from existing Sources, and point to the following pom file:
.../Lab-6.5 - DataFrame Save And Load DataFrame From Grid/save-load-dataframe-from-grid/pom.xml


## Problem 2:

Error: "Failed to locate the winutils binary in the hadoop binary path"

Solution:

Add HADOOP_HOME environment variable in IntelliJ Editor

Steps:
1. Go to Run -> Edit Configuration -> Environment variables text box at right side
2. Enter HADOOP_HOME=C:\gigaspaces-insightedge-enterprise-14.0.0-ga-b20000\tools\winutils
3. Press Ok and rebuild the project, then run SaveLoadDataframeFromGrid.scala class.

## Problem 3:
How run from command line.
Edit the pom.xml to change the scope insightedge core dependencies to provided.

Generate jar:
cd ./save-load-dataframe-from-grid
mvn clean package

<INSIGHTEDGE_HOME>\insightedge\bin\insightedge-submit --class com.gigaspaces.ietraining.SaveLoadDataframeFromGrid --master spark://127.0.0.1:7077  ./target/load-dataframe-from-grid-1.0-SNAPSHOT.jar



