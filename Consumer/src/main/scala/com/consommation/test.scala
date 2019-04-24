package com.consommation

import java.util.UUID

import com.model.{TestDbProvider, Tmax}
import org.apache.spark.{SparkConf, SparkContext}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object test extends App with TestDbProvider {

  val conf = new SparkConf(true)
    .setAppName("Seifapp")
    //.set("spark.cassandra.connection.host", "127.0.0.1")
    .setMaster("local[*]")

  val sc = new SparkContext(conf)


  val tmax_raw = sc.textFile("soccer/card_detail.csv")
  val tmax_c10 = tmax_raw.map(x => x.split(",")).map(x => Tmax(UUID.fromString(x(0)), x(1), x(2), x(3), x(4), x(5), x(6), x(7)))

  // update database with new elements :
  import com.outworkers.phantom.dsl._

  tmax_c10.foreachPartition { partition =>

    val toto = Future.sequence(partition.collect {
      case tmax: Tmax =>
        db.TmaxConnector.myStore(tmax)
    })

    Await.result(toto, 10 seconds)
  }
}
