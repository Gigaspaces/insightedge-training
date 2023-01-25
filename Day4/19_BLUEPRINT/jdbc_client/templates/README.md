# Lab: JDBC V3

### Aim:
### 1.Learn how to build new blueprint
### 2.Generate blueprint and run it


## Instructions
### About this blueprint:
### This Blueprint assumes a service that request some data from a space
### Do Some manipulation on the received data and send back Results
### The idea is that using this template Servise developer will create spesific service
### In Service Request - add specific parameter validations
### In convertor do the required manipulation to give the required response

## JDBC Query on Space Data
### 1. copy the blueprint - folder jdbc_client to gs-home/config/blueprints
### 2. start a space (gs-home/bin/gs.sh demo)
### 2. generate the blueprint override parameters - using gs-home/bin/gs.sh blueprint generate
### 3. open the created project in ide

## In the created project

### This step acn be done by running ./gs.sh demo (gs-home/bin)
### 1. Build and Run test Main, The code in the test does the following:
###     Create a proxy to the space
###     write data
###     create new service,execute the request and print results
###     see code & output of the test in the ide
#### If time Permits
### 2. Change Request to expect specific parameters and validate them
### 3. Change ConverResults to return some manipulations on results
### 4. Change the test, to verify expected results
