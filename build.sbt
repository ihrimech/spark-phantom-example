import sbt.Keys.excludeDependencies
import sbtassembly.AssemblyPlugin.autoImport.assemblyMergeStrategy
import sbtassembly.MergeStrategy


lazy val commonSettings = Seq(
  version := "0.1-SNAPSHOT",
  organization := "com.example",
  scalaVersion := "2.12.8",
  test in assembly := {}
)


lazy val app = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql" % "2.4.1",
      "com.outworkers" %% "phantom-dsl" % "2.40.0",
      "org.apache.kafka" %% "kafka" % "2.2.0",
      "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.4.0",
      "org.apache.spark" %% "spark-streaming" % "2.4.1" % "provided"


    ),
    excludeDependencies ++= Seq(
      "org.slf4j" % "log4j-over-slf4j"
    ),
    dependencyOverrides ++= Seq(
      "com.google.guava" % "guava" % "16.0.1",
      "com.fasterxml.jackson.core" % "jackson-core" % "2.9.8",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.8",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.8"
    )
  ).aggregate(Producer, Consumer)


lazy val Producer = (project in file("Producer")).
  settings(commonSettings: _*)
  .settings(
    name := "Producer",
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql" % "2.4.1",
      "com.outworkers" %% "phantom-dsl" % "2.40.0",
      "org.apache.kafka" %% "kafka" % "2.2.0",
      "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.4.0",
      "org.apache.spark" %% "spark-streaming" % "2.4.1" % "provided"

    ),
    excludeDependencies ++= Seq(
      "org.slf4j" % "log4j-over-slf4j"
    ),
    dependencyOverrides ++= Seq(

      "com.google.guava" % "guava" % "16.0.1",
      "com.fasterxml.jackson.core" % "jackson-core" % "2.9.8",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.8",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.8"
    )

  
  ).dependsOn(
  Data_model
)



lazy val Data_model = (project in file("Data_model")).
  settings(commonSettings: _*)
  .settings(
    name := "Data_model",
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql" % "2.4.1",
      "com.outworkers" %% "phantom-dsl" % "2.40.0",
      "org.apache.kafka" %% "kafka" % "2.2.0",
      "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.4.0",
      "org.apache.spark" %% "spark-streaming" % "2.4.1" % "provided"


    ),

    dependencyOverrides ++= Seq(
      "com.google.guava" % "guava" % "16.0.1",
      "com.fasterxml.jackson.core" % "jackson-core" % "2.9.8",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.8",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.8"
    )

  )


lazy val Consumer = (project in file("Consumer")).
  settings(commonSettings: _*)
  .settings(
    name := "Consumer",
    libraryDependencies ++= Seq(
      "org.elasticsearch" % "elasticsearch-spark-20_2.11" % "7.0.0",
      "org.apache.spark" %% "spark-sql" % "2.4.1",
      "com.outworkers" %% "phantom-dsl" % "2.40.0",
      "org.apache.kafka" %% "kafka" % "2.2.0",
      "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.4.0",
      "org.apache.spark" %% "spark-streaming" % "2.4.1"



    ),
    excludeDependencies ++= Seq(
      "org.slf4j" % "log4j-over-slf4j"
    ),
    dependencyOverrides ++= Seq(
      "io.netty" % "netty-all" % "4.1.35.Final",
      "com.fasterxml.jackson.core" % "jackson-core" % "2.9.8",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.8",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.8"
    )

  )
  .dependsOn(
    Data_model
  )



assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}