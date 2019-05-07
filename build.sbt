import sbt.Keys.excludeDependencies
import sbtassembly.AssemblyPlugin.autoImport.assemblyMergeStrategy
import sbtassembly.MergeStrategy

lazy val assemblyStrategy = assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

lazy val commonSettings = Seq(
  version := "0.1-SNAPSHOT",
  organization := "com.example",
  scalaVersion := "2.11.12",
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
  .settings(
  assemblyStrategy
)

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
      "io.netty" % "netty-all" % "4.1.35.Final",
      "com.google.guava" % "guava" % "16.0.1",
      "com.fasterxml.jackson.core" % "jackson-core" % "2.9.8",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.8",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.8"
    )
  ).dependsOn(
  Data_model
).settings(
  assemblyStrategy
)

lazy val ElasticwithCassandra= (project in file("Elastic"))
  .settings(commonSettings: _*)
  .settings(
    name := "Elastic",
    libraryDependencies ++= Seq("org.apache.spark" %% "spark-sql" % "2.4.1",
      "com.datastax.spark" %% "spark-cassandra-connector" % "2.4.1",
      "org.elasticsearch" %% "elasticsearch-spark-20" % "7.0.0"
    )
  ).settings(
  assemblyStrategy
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
  .settings(
    assemblyStrategy
  )

lazy val Consumer = (project in file("Consumer")).
  settings(commonSettings: _*)
  .settings(
    name := "Consumer",
    libraryDependencies ++= Seq(
      "com.datastax.spark" %% "spark-cassandra-connector" % "2.4.1",
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
      "com.google.guava" % "guava" % "19.0",
      "com.fasterxml.jackson.core" % "jackson-core" % "2.9.8",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.8",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.8"
    )
  )
  .settings(
    assemblyStrategy
  )
  .dependsOn(
    Data_model
  )