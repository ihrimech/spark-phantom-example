package com.consommation

import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import com.model.Tmax
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer

import org.apache.spark.{SparkConf, SparkContext}
import scala.concurrent.{Await, Future}

object Consumer extends App {

  val conf = new SparkConf(true)
    .setAppName("seifapp")
    .setMaster("local[*]")

  val sc = new SparkContext(conf)
  val ssc = new StreamingContext(sc, Seconds(5))


  val preferredHosts = LocationStrategies.PreferConsistent
  val topics = List("test1")

  val kafkaParams = Map(
    "bootstrap.servers" -> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[TmaxDeserializer],
    "group.id" -> "spark-streaming-notes",
    "auto.offset.reset" -> "latest"
  )

  val offsets = Map(new TopicPartition("test1", 0) -> 2L)

  val dstream = KafkaUtils.createDirectStream[String, Tmax](
    ssc,
    preferredHosts,
    ConsumerStrategies.Subscribe[String, Tmax](topics, kafkaParams, offsets))

  dstream.foreachRDD { rdd =>
    // Get the offset ranges in the RDD
    val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges

    rdd.foreachPartition { iter =>
      iter.foreach {
        record => println(record.value())
//        val toto = Future.sequence(partition.collect {
//          case tmax: Tmax =>
//            db.TmaxConnector.myStore(tmax)
//        })
      }

    }
  }

  ssc.start
 // ssc.awaitTermination()

  // the above code is printing out topic details every 5 seconds
  // until you stop it.

  ssc.stop(stopSparkContext = false)

}
