# Flight Delay Example

Data for the demo
Link:  http://stat-computing.org/dataexpo/2009/the-data.html

Instruction:<br>
**1.** Download from the above link, `2007.csv` file. Save the file on your local machine.<br>
**2.** Review the csv file. Decide which fields to keep in space.<br>
**3.** In IDE, go to DelayRec class and declare the required fields.<br>
* Please note Space Routing field is set to flightNumber.<br>
* Space Id is flightNumber and time.<br>
   
**4.** Build the project jar (mvn package).<br>
**5.** Start the InsightEdge Manager<br>
   * `./gs.sh host run-agent --auto`
   
**6.** Open the web-ui in a browser - (`http://localhost:8099`)<br>
**7.** Start 2 GSC using web-ui (or cli)<br>
**8.** Open the Rest Manager in a browser - (`http://localhost:8090/v2/`) and deploy project jar (2,0 - 2 partitions with no backups).<br>
**9.** Open Zeppelin in a web browser (`http://localhost:9090`). Import `Lab 17.1 Example - Flight Delay Prediction Demo.json` notebook.<br>
**10.** Follow instructions in the notebook. Notifications will appear in the console output (gs.sh).<br>
**11.** Go to the web-ui to see the data in the space after each step.



