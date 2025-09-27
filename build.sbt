lazy val `sbt-gzip` = project in file(".")

enablePlugins(SbtWebBase)

description := "sbt-web plugin for gzipping assets"

developers += Developer(
  "playframework",
  "The Play Framework Team",
  "contact@playframework.com",
  url("https://github.com/playframework")
)

addSbtWeb("1.6.0-M1")

crossScalaVersions += "3.7.3"

pluginCrossBuild / sbtVersion := {
  scalaBinaryVersion.value match {
    case "2.12" =>
      sbtVersion.value
    case _ =>
      "2.0.0-RC4"
  }
}

// Customise sbt-dynver's behaviour to make it work with tags which aren't v-prefixed
ThisBuild / dynverVTagPrefix := false

// Sanity-check: assert that version comes from a tag (e.g. not a too-shallow clone)
// https://github.com/dwijnand/sbt-dynver/#sanity-checking-the-version
Global / onLoad := (Global / onLoad).value.andThen { s =>
  dynverAssertTagVersion.value
  s
}
