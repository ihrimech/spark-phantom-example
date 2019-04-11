package soccer


import org.apache.spark.sql.SparkSession


object Foot extends App {

  val spark = SparkSession.
    builder.master("local[*]")
    .appName("soccer APP")
    .getOrCreate()


  val df = spark.read
    .format("com.databricks.spark.csv")
    .option("header", "true")
    .option("inferSchema", "true").load("soccer/card_detail.csv")

  df.show()


  val rf = spark
    .read
    .format("org.apache.spark.sql.cassandra")
    .options(Map("table" -> "card_detail", "keyspace" -> "soccer"))
    .load()
  rf.show()


  //.saveAsCassandraTable("soccer", "card_detail", SomeColumns("id", "elapsed", "event_incident_typefk", "match_id", "player1", "subtype", "team", "type"))


  //df.write.cassandraFormat.saveAsTable("card_detail")

  /*val x= df.write
     .cassandraFormat("card_detail", "test")
     .save()*/


}
