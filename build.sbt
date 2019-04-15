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
    //mainClass in assembly := Some("soccer.foot"),
    libraryDependencies ++= Seq("org.apache.spark" %% "spark-sql" % "2.4.1",
      // "com.datastax.spark" % "spark-cassandra-connector_2.11" % "2.4.1",
      "com.outworkers" %% "phantom-dsl" % "2.40.0"
    ),
    excludeDependencies += "org.slf4j" % "log4j-over-slf4j"
  )

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}