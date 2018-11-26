#Lab2

# Flight Delay Example
## Getting Started


Data for the demo
Link:  http://stat-computing.org/dataexpo/2009/the-data.html

Instruction:
1. Start insightedge Manager 
    insightedge host run-agent --auto
2. Go to web-ui - (http://localhost:8099)
3. Strat 2 GSC using web-ui (can be done as part of cmd line as well)
4. Download from the above Link, 2007 csv files.
   Have a look at the file decide which fields  To keep in space, 
   Define DelayRec class accordingly.
   Space Routing field can be set to Flight Number
   Space Id a field containing flight number and time.  
5. Build the space jar 
6. Go to rest manager(http://localhost:8090/v2/) and Deploy space jar (2,0 or start more gsc)
(Can be done using cmd line or in web-ui as well)
5. Download from the above Link, 2007 csv files.
7. Go to zeplin (http://localhost:9090) , import lab notebook (json in resorces)
8. Fix in dependencies section the location of the jar, so it will point to your local jar.
9. Fix location of 2007 in Saving Flight delay RDD to the Grid after reading from csv paragraph and in simulate new data paragraph
10. Run the notebook.(see grid console to see delays were reported)
11. Go to web-ui to see if the data is saved correctly.


