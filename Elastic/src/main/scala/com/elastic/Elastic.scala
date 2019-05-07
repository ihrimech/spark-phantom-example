package com.elasticDVF_Analysis

import com.datastax.spark.connector.cql.CassandraConnectorConf
import com.datastax.spark.connector.rdd.ReadConf
import org.elasticsearch.spark.sql._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.cassandra._

object Elastic extends App {

  val spark = SparkSession.
    builder.master("local[*]")
    .appName("SoccerAPP")
    .getOrCreate()

  // Cassandra Configuration
  spark.setCassandraConf(CassandraConnectorConf.KeepAliveMillisParam.option(10000))
  spark.setCassandraConf("Cluster1", CassandraConnectorConf.ConnectionHostParam.option("127.0.0.1") ++ CassandraConnectorConf.ConnectionPortParam.option(12345))
  spark.setCassandraConf("Cluster1", "ks1", ReadConf.SplitSizeInMBParam.option(128))

  //Loading data from Cassandra
  val Df = spark
    .read
    .format("org.apache.spark.sql.cassandra")
    .options(Map("table" -> "valeursfonciereselem", "keyspace" -> "immobilier"))
    .load()

  //Save data in the index of elasticsearch
  Df.saveToEs("immobilier/docs")

}