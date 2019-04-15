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
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql" % "2.4.1",
      "com.outworkers" %% "phantom-dsl" % "2.40.0"
    ),
    excludeDependencies ++= Seq(
      "org.slf4j" % "log4j-over-slf4j"
    ),
    dependencyOverrides += "com.google.guava" % "guava" % "15.0"
  )

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}