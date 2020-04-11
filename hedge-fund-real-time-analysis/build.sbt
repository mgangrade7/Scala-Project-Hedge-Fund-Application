name := "HedgeFundRealTimeAnalysis"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.3.2"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.2"

libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.3.2"

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.6.0"

libraryDependencies += "org.mongodb.spark" %% "mongo-spark-connector" % "2.4.1"

libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.4.2"

libraryDependencies += "io.spray" %%  "spray-json" % "1.3.4"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

resolvers += Resolver.bintrayRepo("cakesolutions", "maven")
libraryDependencies += "net.cakesolutions" %% "scala-kafka-client" % "1.1.1"