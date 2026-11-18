val root = (project in file("."))
  .enablePlugins(SbtWeb)
  .settings(
    // Classic target layout so scripted checks keep working on sbt 2.
    target := baseDirectory.value / "target"
  )

pipelineStages := Seq(gzip)

// set an include filter for js files only

gzip / includeFilter := "*.js"

// for checking that the produced pipeline mappings are correct

val expected = Set(
  "css", "css/a.css",
  "js", "js/a.js", "js/a.js.gz"
) map(_.replace("/", java.io.File.separator))

val checkMappings = taskKey[Unit]("check the pipeline mappings")

checkMappings := {
  val mappings = WebKeys.pipeline.value
  val paths = (mappings map (_._2)).toSet
  if (paths != expected) sys.error(s"Expected $expected but pipeline paths are $paths")
}

ThisBuild / scalaVersion := {
  if (sbtVersion.value.startsWith("1.")) "2.12.21" else "3.9.0"
}
