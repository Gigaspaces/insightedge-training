# Lab: Structured_Streaming 

### Reading popular Tweets and saving to the grid
A modified version of Spark's example that saves tags (all and popular) to Data Grid.<br/>
To run the example, you have to create application tokens at https://apps.twitter.com/<br/>
Make sure you set "Callback URL" to any valid URL, e.g. http://insightedge.io/, otherwise Twitter4j may not work

# Instructions

1. Start InsightEdge in Demo mode:

    go to gigaspaces-insightedge-enterprise-14.5.0/bin

    run ./gs.sh demo

2. Build and run TwitterPopularTags  

The below application keys can be used:
consumerKey = "aXbCpq9Zru7UimHAfnzVA"
consumerSecret = "NlXxNAv1hHR9RUxm5o9WMiSBnzNJV7RpWGzffixKQ"
accessToken = "28462935-f9vhPSCfDd6BlMefL4hbReDgbLp1c6FME72nJIlIi"
accessTokenSecret = "nELm7MW28sBhJt7F3lqQr0pWVnCzrewMmx3WFlq2W0"


3. See the data in the space
