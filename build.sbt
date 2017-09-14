import scala.util.Try

name := "pot_hadoop"

version := "1.0"

scalaVersion := "2.10.6"

val buildNumber = Try(sys.env("BUILD_NUMBER")).getOrElse("0000")

scalacOptions := Seq("-deprecation", "-unchecked", "-feature")

mainClass in Compile := Some("rojosam.ExampleApp")

resolvers += "cloudera" at "https://repository.cloudera.com/artifactory/cloudera-repos/"

libraryDependencies ++= {
  val akkaVersion       = "2.3.16"
  val log4jVersion      = "2.6.2"
  val clouderaVersion   = "1.6.0-cdh5.7.1"
  val kafkaVersion      = "0.9.0-kafka-2.0.1"
  Seq(
    "org.apache.spark"         %% "spark-core"         % clouderaVersion  % "provided",
    "org.apache.spark"         %% "spark-sql"          % clouderaVersion % "provided",
    "org.apache.spark"         %% "spark-hive"          % clouderaVersion % "provided",
    "com.holdenkarau"          %% "spark-testing-base" % "1.6.1_0.3.3",
    "org.apache.thrift"        %  "libthrift"          % "0.8.0",
    "com.twitter"              %% "scrooge-core"       % "4.6.0",
    "com.twitter"              %% "finagle-thrift"     % "6.34.0",
    "org.apache.zookeeper"     %  "zookeeper"          % "3.4.5-cdh5.7.1",
    "org.apache.kafka"         %% "kafka"              % kafkaVersion,
    "org.apache.kafka"         %  "kafka-clients"      % kafkaVersion,
    "com.typesafe.akka"        %% "akka-actor"         % akkaVersion,
    "com.typesafe.akka"        %% "akka-testkit"       % akkaVersion % Test,
    "com.typesafe.akka"        %% "akka-slf4j"         % akkaVersion,
    "org.apache.logging.log4j" %  "log4j-core"         % log4jVersion,
    "org.apache.logging.log4j" %  "log4j-api"          % log4jVersion,
    "org.apache.logging.log4j" %  "log4j-slf4j-impl"   % log4jVersion,
    "org.apache.flume"         %  "flume-ng-core"      % clouderaVersion,
    "org.apache.flume"         %  "flume-ng-sdk"       % clouderaVersion,
    "com.typesafe"             %  "config"             % "1.3.1",
    "com.github.scopt"         %% "scopt"              % "3.7.0"
  )
}

assemblyShadeRules in assembly := Seq(
  ShadeRule.rename("com.google.**" -> "shadeio.@1").inAll
)

assemblyJarName in assembly := s"${name.value}-${version.value}-${"%04d".format(buildNumber.toInt)}.jar"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
  case _ => MergeStrategy.first
}

coverageEnabled.in(Test, test) := true

test in assembly := {}
        