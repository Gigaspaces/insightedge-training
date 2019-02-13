# Flight Delay Example

Data for the demo
Link:  http://stat-computing.org/dataexpo/2009/the-data.html

Instruction:
1. Download from the above link, 2007 csv files. Save the file on your local machine.
1. Review the csv file. Decide which fields to keep in space.
1. In IDE, go to DelayRec class and declare the required fields.  
   * Please note Space Routing field is set to flightNumber.
   * Space Id is flightNumber and time. 
1. Build the project jar (deployment is mentioned in step 8).
1. Start the InsightEdge Manager
   * ./insightedge host run-agent --auto    
1. Open the web-ui in a browser - (http://localhost:8099)
1. Start 2 GSC using web-ui (or cli)
1. Open the Rest Manager in a browser - (http://localhost:8090/v2/) and deploy project jar (2,0 - 2 partitions with no backups).
1. Open Zeppelin in a web browser (http://localhost:9090). Import 'Lab 17.1 Example - Flight Delay Prediction Demo.json' notebook.
1. Follow instructions in the notebook. Notifications will appear in the console output (gs-agent).
1. Go to the web-ui to see the data in the space after each step.



