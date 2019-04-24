//package com.consommation
//
//import java.sql.ResultSet
//import java.util.UUID
//
//import com.datastax.driver.core.Row
//import com.model.{DatabaseConnector, Tmax}
//import org.apache.spark.rdd.RDD
//import org.apache.spark.sql.DataFrame
//import org.apache.spark.{SparkConf, SparkContext}
//import org.elasticsearch.spark._
//import org.apache.spark.sql.SQLContext
//
//import scala.collection.JavaConverters._
//import scala.concurrent.Future
//
//object Elastic extends App {
//
//
//  val conf = new SparkConf()
//    .setAppName("elasticApp")
//    .setMaster("local[*]")
//    .set("es.index.auto.create", "true")
//
//  val sc = new SparkContext(conf)
//  val sqlContext = new SQLContext(sc)
//
//  import sqlContext.implicits._
//
//  val results = DatabaseConnector.res
//
//  results.forEach(println)
//
////  val rdd: RDD[ResultSet] = sc.parallelize(results.all().toSeq)
//
////
////      sc.makeRDD(
////        Seq("1",  resultSet)
////      ).saveToEs("spark/docs")
//
//}
