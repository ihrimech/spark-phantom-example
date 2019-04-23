package com.production

import java.util.{Properties, UUID}

import com.model.Tmax
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.spark.{SparkConf, SparkContext}



object Producer extends App {

  val topic = "test1"
  val conf = new SparkConf(true)
    .setAppName("CsvToKafka")
    .setMaster("local[*]")

  val sc = new SparkContext(conf)
  val cross = sc.textFile(getClass.getResource("/soccer/card.csv").getPath)
  val crossDetail = cross
    .map(x => x.split(","))
    .map(x =>Tmax(UUID.randomUUID(), x(1), x(2), x(3), x(4), x(5), x(6), x(7)))


  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "com.production.TmaxSerializer")

  val producer = new KafkaProducer[String, Tmax](props)

  crossDetail.foreach(tmaxElement => {
    val record = new ProducerRecord(topic, "key", tmaxElement)
    producer.send(record)
  })

  producer.close()
}
