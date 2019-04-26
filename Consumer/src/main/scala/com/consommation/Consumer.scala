package com.consommation

import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import com.model.{TestDbProvider, Tmax}
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.{SparkConf, SparkContext}
import scala.concurrent.Await
import scala.concurrent.duration._


object Consumer extends App with TestDbProvider {

  val conf = new SparkConf(true)
    .setAppName("Consumer")
    .setMaster("local[*]")

  // Create the SparkContext and the StreamingContext
  val sc = new SparkContext(conf)
  val ssc = new StreamingContext(sc, Seconds(5))


  val preferredHosts = LocationStrategies.PreferConsistent
  val topics = List("test1")

  //SparkStreaming configuration for KAFKA
  val kafkaParams = Map(
    "bootstrap.servers" -> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[TmaxDeserializer],
    "group.id" -> "spark-streaming-notes",
    "auto.offset.reset" -> "earliest"
  )

  val offsets = Map(new TopicPartition("test1", 0) -> 2L)

  //Create the Dstream for SparkStreaming
  val dstream = KafkaUtils.createDirectStream[String, Tmax](
    ssc,
    preferredHosts,
    ConsumerStrategies.Subscribe[String, Tmax](topics, kafkaParams, offsets))

  dstream.foreachRDD { rdd =>
    // Get the offset ranges in the RDD

    rdd.foreachPartition { iter =>
      iter
        .map(record => record.value())
        .foreach { record =>

          // Set the record in cassandra
            Await.result(db.TmaxConnector.myStore(record), 10 seconds)
        }
      }


  }

  ssc.start
 //ssc.awaitTermination()

  // the above code is printing out topic details every 5 seconds
  // until you stop it.

  ssc.stop(stopSparkContext = false)

}
