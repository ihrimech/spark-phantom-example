package com.production

/**
  * author @seifbentanfous
  */

import java.util.Properties
import com.model.ValeursFoncieres
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.spark.{SparkConf, SparkContext}
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}

object CsvToKafka extends App {

  val topic = "immobilier"
  val conf = new SparkConf(true)
    .setAppName("CsvToKafka")
    .setMaster("local[*]")

  //KAFKA producer configuration
  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "com.model.ValeursFoncieresSerializer")

  //Create the KAFKA producer
  val producer = new KafkaProducer[String, ValeursFoncieres](props)

  // Create the SparkContext and the SparkContext
  val sc = new SparkContext(conf)

  // Read the CSV file with the SparkContext
  val immoRdd = sc.textFile(getClass.getResource("/soccer/fulltest.csv").getPath)
  // TODO optimize with spark
  implicit val ec = ExecutionContext.global

  immoRdd
    .mapPartitions(_.map { stringElement =>
      val separatedElements = stringElement.split(",").toList
      Unmarshaller.unmarshallValeursFoncieres(separatedElements)
    })
    .foreachPartition(partition => {
      val futureResults = partition.map { valeursFoncieresElemt => {
        //Send the record to the KAFKA consumer
        val record = new ProducerRecord(topic, "key", valeursFoncieresElemt)
        Future(producer.send(record).get)
      }
      }
//      Await.result(Future.sequence(futureResults), 10 seconds)
    })

  producer.close()
}
