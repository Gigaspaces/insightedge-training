# Lab 19 Exercise: Tableau Integration

In this lab you will learn how to integrate Tableau with InsightEdge using the InsightEdge JDBC connector to retrieve data for visualization and business intelligence needs.

# Instructions
### 1. Configure IE_HOME variable to InsightEdge latest version
For example (Linux/mac): export IE_HOME=/Users/yuval/XAP-Builds/gigaspaces-insightedge-enterprise-14.5.0

### 2. Start InsightEdge in Demo mode
    cd $IE_HOME/bin/
    
#### Linux/Mac <br /> 
    ./gs.sh demo 
#### Windows
    gs.bat demo 
    
web-ui will lunched on http://localhost:8099 <br />
zeppelin will lunched on http://localhost:9090 <br />
REST Manager API will lunched on http://localhost:8090 <br /> 

### 3. Write some data
 Go to Lab 18 Jupyter Notebook <br />
 
    cd insightedge-training/Day5/18 Jupyter Notebook/Lab-18 - DF Save and Load from Grid - Jupyter Notebook
    
 ** Add lookupLocators and lookupGroups to SpaceProxyConfigurer in RegisterSpaceDocument and WriteSpaceDocument classes   
    
 Compile the source code <br />
 
    mvn clean install
    
 Register Product SpaceDocument <br />
 
    cd target
    java -cp SpaceDocumentFeeder-1.0-SNAPSHOT.jar:$IE_HOME/lib/required/*  RegisterSpaceDocument
 
 Write 100 records of Product SpaceDocument to demo space
    
    java -cp SpaceDocumentFeeder-1.0-SNAPSHOT.jar:$IE_HOME/lib/required/*  WriteSpaceDocument

 
### 4. Starting Tableau
#### Linux / Mac

1. Download Tableau 2019.1 or higher from [here](https://www.tableau.com/trial/tableau-software?utm_campaign_id=2017049&utm_campaign=Prospecting-CORE-ALL-ALL-ALL-ALL&utm_medium=Paid+Search&utm_source=Google+Search&utm_language=EN&utm_country=MED&kw=%2Btableau&adgroup=CTX-Brand-Core-B&adused=336806490197&matchtype=b&placement=&gclid=CjwKCAjw-ITqBRB7EiwAZ1c5U3qli3LyKpES9OFiSueG8rd3s9zRIfUzDga4Ui9uzsARROZUyqqqzhoC5zMQAvD_BwE&gclsrc=aw.ds)
   and install it in the default location. 
2. Install the Maven artifacts <br />

         $IE_HOME/bin/gs.sh maven install
    

3. Create InsightEdge jdbc drive <br />

          cd $IE_HOME/tools/jdbc

          ./build-jdbc-client.sh

      insightedge-jdbc-client.jar will be created under $IE_HOME/tools/jdbc <br />
      
4. Copy insightedge-jdbc-client.jar to ~/Library/Tableau/Drivers/ <br />

        cp $IE_HOME/tools/jdbc/insightedge-jdbc-client.jar ~/Library/Tableau/Drivers/
        
5. Run the Tableau application with -DConnectPluginsPath system property which point to the InsightEdge tableau folder. <br />

        "/Applications/Tableau Desktop 2019.2.app/Contents/MacOS/Tableau" -DConnectPluginsPath=$IE_HOME/tools/tableau 

#### Windows

1. Download Tableau 2019.1 or higher from [here](https://www.tableau.com/trial/tableau-software?utm_campaign_id=2017049&utm_campaign=Prospecting-CORE-ALL-ALL-ALL-ALL&utm_medium=Paid+Search&utm_source=Google+Search&utm_language=EN&utm_country=MED&kw=%2Btableau&adgroup=CTX-Brand-Core-B&adused=336806490197&matchtype=b&placement=&gclid=CjwKCAjw-ITqBRB7EiwAZ1c5U3qli3LyKpES9OFiSueG8rd3s9zRIfUzDga4Ui9uzsARROZUyqqqzhoC5zMQAvD_BwE&gclsrc=aw.ds)
   and install it in the default location. 

2. Start Tableau which include the InsightEdge integration: <br />

          $IE_HOME/tools/tableau/gs-tableau.bat

 
 ### 5. Connecting to the Space 
 
 Click Connect in the Tableau desktop, and select Gigaspaces InsightEdge as the data source. <br />
 Fill the "Server" and the "Space" fields and click on "Sign in" <br />
 For more information/trableshooting see [here](https://docs.gigaspaces.com/latest/dev-java/tableau-integration.html#ConnectingtotheSpace)   
 
 ![Screenshot](../Pictures/connectToSpace.png) <br />
 
  ### 6. Create an Histogram
 
 Create an Histogram chart using the Product CatalogNumber and price. 
 
 
 
  