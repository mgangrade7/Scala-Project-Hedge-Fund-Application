name := "HedgeFundApplication"

version := "0.1"

scalaVersion := "2.11.8"

val sparkVersion = "2.4.4"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.6.0",
  "org.apache.kafka" %% "kafka" % "2.1.0",
  "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime,
  "org.scalaj" %% "scalaj-http" % "2.4.2",
  "io.spray" %%  "spray-json" % "1.3.4"
)