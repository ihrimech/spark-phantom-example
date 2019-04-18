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
      "org.apache.kafka" %% "kafka" % "2.2.0"
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
  )


assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}