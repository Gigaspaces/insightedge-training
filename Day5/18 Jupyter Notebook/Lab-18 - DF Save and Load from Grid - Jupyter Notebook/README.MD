# Lab: Jupyter Notebook 

# Instructions

**1.** Start InsightEdge in Demo mode:

    go to `gigaspaces-insightedge-enterprise-14.5.0/bin`

    run `./gs.sh demo`
    
**2.** `cd insightedge-training/Day5/18 Jupyter Notebook/Lab-18 - DF Save and Load from Grid - Jupyter Notebook`<br>
**3.** do `mvn clean install`<br>
**4.** `cd target`<br>
**5.** Register Product SpaceDocument:<br>
   `java -cp SpaceDocumentFeeder-1.0-SNAPSHOT.jar:gigaspaces-insightedge-enterprise-14.5.0/lib/required/*  RegisterSpaceDocument`<br>
   Write 1 record of Product SpaceDocument:<br>
   `java -cp SpaceDocumentFeeder-1.0-SNAPSHOT.jar:gigaspaces-insightedge-enterprise-14.5.0/lib/required/*  WriteSpaceDocument`

**6.** Run Jupyter Notebook:<br>
   Follow the instructions as appear in the lesson slides or at:<br>
   https://docs.gigaspaces.com/latest/started/insightedge-jupyter.html?Highlight=jupyter#UsingaJupyterNotebook
   
**7.** Load Lab 18 Exercise - Jupyter.ipynb and follow the instructions.