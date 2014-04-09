sbtPlugin := true

organization := "com.typesafe.sbt"

name := "sbt-gzip"

version := "1.0.0-SNAPSHOT"

addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.0.0-M2")

publishMavenStyle := false

publishTo := {
  if (isSnapshot.value) Some(Classpaths.sbtPluginSnapshots)
  else Some(Classpaths.sbtPluginReleases)
}

scriptedSettings

scriptedLaunchOpts += ("-Dproject.version=" + version.value)
