package com.consommation

import com.model.{TestDbProvider, ValeursFoncieres, ValeursFoncieresDeserializer}
import com.typesafe.scalalogging.Logger
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.{SparkConf, SparkContext}
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}

object KafkaToCassandra extends App with TestDbProvider {
  val logger = Logger("consumer-logger")
  val conf = new SparkConf(true)
    .setAppName("Consumer")
    .setMaster("local[*]")

  // Create the SparkContext and the StreamingContext
  val sc = new SparkContext(conf)
  val ssc = new StreamingContext(sc, Seconds(5))


  val preferredHosts = LocationStrategies.PreferConsistent
  val topics = List("immobilier")

  //SparkStreaming configuration for KAFKA
  val kafkaParams = Map(
    "bootstrap.servers" -> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[ValeursFoncieresDeserializer],
    "group.id" -> "use_a_separate_group_id_for_each_stream",
    "auto.offset.reset" -> "latest"
  )

  val offsets = Map(new TopicPartition("immobilier", 0) -> 2L)

  //Create the Dstream for SparkStreaming
  val dstream = KafkaUtils.createDirectStream[String, ValeursFoncieres](
    ssc,
    preferredHosts,
    ConsumerStrategies.Subscribe[String, ValeursFoncieres](topics, kafkaParams, offsets))

  implicit val ec = ExecutionContext.global

  dstream.foreachRDD { rdd =>
    // Get the offset ranges in the RDD

    rdd.foreachPartition { iter =>
      val futureResults = Future.sequence(iter
        .map { record =>
          // Set the record to cassandra
          db.TmaxConnector.myStore(record.value)
          //
        })
      try {
        Await.result(futureResults, 10 seconds)
      } catch {
        case e: Exception => println("erreur Exception")
        //case _ => println("erreur")
      }
    }
  }

  ssc.start
  ssc.awaitTermination()

  // the above code is printing out topic details every 5 seconds
  // until you stop it.

  ssc.stop(stopSparkContext = false)
}
