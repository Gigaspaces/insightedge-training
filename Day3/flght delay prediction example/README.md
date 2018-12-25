#Lab2

# Flight Delay Example
## Getting Started


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
3.Go to web-ui - (http://localhost:8099)
4.  Strat 2 GSC using web-ui (can be done as part of cmd line as well)
6. Go to rest manager(http://localhost:8090/v2/) and Deploy project jar (2,0 )
7. Go to zeplin (http://localhost:9090) , import Flight Delay lab2 notebook (json in resorces)
8. Add in dependencies section the location of the jar, so it will point to your local jar.
9. In  Saving Flight delay RDD paragraph, put location of teh csv file,  and in simulate new data paragraph put the location of DelayRec jar.
10. Run the notebook.(see grid console to see delays were reported)
11. Go to web-ui to see if the data is saved correctly.


