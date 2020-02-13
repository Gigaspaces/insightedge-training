# insightedge-training


# Instructions

1. Download or copy gigaspaces-insightedge-enterprise-14.5.0 package to your machine.

   download link: https://www.gigaspaces.com/product/insightedge-platform
   
2. Unzip the package.

3. cd $GS_HOME/bin
   ./gs.sh maven install

4. Clone this repository:
   git clone https://github.com/Gigaspaces/insightedge-training

5. Move the cloned Data folder from this repository. The Data folder and gigaspaces install directory should have the same parent directory.


Note regarding Zeppelin:
Occasionally you might need to restart the %spark interpreter with in Zeppelin.
1. Click on the gear icon (top right corner)
2. A Settings menu will pop up. Click the recycle icon next to the %spark interpreter
3. Click OK
4. Click Save to close the Settings menu.

Note regarding WebUI, Ops Manager
1. If you have run an older version of InsightEdge and 1) when running the webui, you get a blank screen or 2) the Ops Manager will not come up, the browser always gets directed to the REST manager, clear the browser cache or view the website in Incognito or Private mode.
