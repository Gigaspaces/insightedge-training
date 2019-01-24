#Lab 16.1

# Flight Delay Example

Data for the demo
Link:  http://stat-computing.org/dataexpo/2009/the-data.html

Instruction:
1. Download from the above Link, 2007 csv files.store the file on your local machine(TBD).
2. Have a look at the file decide which fields To keep in space,
3. In IDE, go to DelayRec class and decalre the required fields.  
   Please note Space Routing field is set to flightNumber
   Space Id is flightNumber and time. 
4. Build the project jar and store in your local machine(TBD)
2. Start insightedge Manager 
    insightedge host run-agent --auto    
3. Go to web-ui - (http://localhost:8099)
4. Strat 2 GSC using web-ui (can be done as part of cmd line as well)
6. Go to rest manager(http://localhost:8090/v2/) and Deploy project jar (2,0 )
7. Go to zeppelin (http://localhost:9090) , import Lab 16.1 Example - Flight Delay Prediction Demo.json notebook 
8. Follow instructions in the notebook, see notification in gs-agent console
9. Go to web-ui to see space data in each step



