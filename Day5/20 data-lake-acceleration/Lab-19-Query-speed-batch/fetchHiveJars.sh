#!/usr/bin/env bash
set -e
hiveVersion="2.3.2"
hadoopVersion="2.7.2"
GS_HOME=$1
echo "GS_HOME = ${GS_HOME}"
JARS_FOLDER=${GS_HOME}/insightedge/spark/jars/hive/

function download {
 wget -nv $1
}


mkdir -p ${JARS_FOLDER}
cd ${JARS_FOLDER}


download http://central.maven.org/maven2/org/apache/hive/hive-beeline/${hiveVersion}/hive-beeline-${hiveVersion}.jar
download http://central.maven.org/maven2/org/apache/hive/hive-cli/${hiveVersion}/hive-cli-${hiveVersion}.jar
download http://central.maven.org/maven2/org/apache/hive/hive-exec/${hiveVersion}/hive-exec-${hiveVersion}.jar
download http://central.maven.org/maven2/org/apache/hive/hive-jdbc/${hiveVersion}/hive-jdbc-${hiveVersion}.jar
download http://central.maven.org/maven2/org/apache/hive/hive-metastore/${hiveVersion}/hive-metastore-${hiveVersion}.jar
download http://central.maven.org/maven2/org/apache/hive/hive-serde/${hiveVersion}/hive-serde-${hiveVersion}.jar
download http://central.maven.org/maven2/org/apache/hadoop/hadoop-client/${hadoopVersion}/hadoop-client-${hadoopVersion}.jar