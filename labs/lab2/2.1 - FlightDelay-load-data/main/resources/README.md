# Flight Delay Example
## Getting Started


Data for the demo
Link:  http://stat-computing.org/dataexpo/2009/the-data.html

Instruction:
1. Download and install insightedge (update pom with relevant version)
2. start grid and spark : gs-agent.bat --manager-local --gsc=2 --spark-master --spark-worker
3. start zeplin
4. Build the jar and Deploy space jar (2,0 or add more gsc)
5. Use xap-ui or web-ui to see progress 
6. Download from the Link, 2007 and 2008 csv files.
7. Open zeplin, import notebook (json in resorces)
8. Fix in dependencies section the location of the jar, so it will point to your local jar.
9. Fix location of 2007 and 2008 files, in Saving Flight delay RDD to the Grid after reading from csv paragraph and in simulate new data paragraph
10. Run the notebook.(see grid console to see delays were reported)
11. Pay attention that the graph setting are correct (settings see that key, group, and values are set , should be according to the order that appears in all fields)

