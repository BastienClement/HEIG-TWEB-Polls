name := """tweb-polls"""
version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"
scalacOptions ++= Seq(
	//"-Xlog-implicits",
	"-feature",
	"-deprecation",
	"-Xfatal-warnings",
	"-unchecked",
	"-language:reflectiveCalls",
	"-language:higherKinds"
)

libraryDependencies ++= Seq(cache)

routesGenerator := InjectedRoutesGenerator
