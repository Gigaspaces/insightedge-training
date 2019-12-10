#Lab 8.2:
##Exploring GS Ops Manager by using gs ui demo utility

**1.** Download GigaSpaces InsightEdge:<br>
        https://gigaspaces-releases-eu.s3.amazonaws.com/insightedge/15.0.0/gigaspaces-insightedge-enterprise-15.0.0.zip<br>
**2.** Unzip the downloaded file to a folder. We will reference it with GS_DIR<br>
**3.** Open the GS_DIR folder, edit gs-license.txt file<br>
    Use a license if you have or "tryme" if you don't.<br>
**4.** Go to GS_DIR/bin folder and edit setenv-overrides.sh/bat file. Add the following:<br>
Linux/Mac:<br>
    export GS_GSC_OPTIONS="-Xmx256m"<br>
Windows:<br>
    set GS_GSC_OPTIONS=-Xmx256m<br>
**5.** Go to GS_DIR/bin folder and run ./gs.sh host run-agent --auto --gsc=9<br>
    This will start the Service Grid and the New UI will be available at http://localhost:8090/<br>
**6.** Download the current version of the UI-Demo Project: <br>
https://github.com/Gigaspaces/ui-demo/archive/1.0.zip<br>
**7.** Unzip it and run the following from within the ui-demo folder:<br>
Linux/Mac:<br>
`mvn clean package`<br>
`export GS_DIR=<PATH TO PRODUCT INSTALLATION>`<br>
`./deploy_all.sh`<br>
Windows:<br>
`mvn clean package`<br>
`set GS_DIR=<PATH TO PRODUCT INSTALLATION>`<br>
`deploy_all.bat`<br>


