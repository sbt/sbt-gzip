package com.typesafe.sbt.gzip

import sbt._
import com.typesafe.sbt.web.SbtWeb
import com.typesafe.sbt.web.pipeline.Pipeline
import sbt.Keys._

object Import {

  val gzip = TaskKey[Pipeline.Stage]("gzip-compress", "Add gzipped files to asset pipeline.")

}

object SbtGzip extends AutoPlugin {

  override def requires = SbtWeb

  override def trigger = AllRequirements

  val autoImport = Import

  import SbtWeb.autoImport._
  import WebKeys._
  import autoImport._

  override def projectSettings: Seq[Setting[_]] = Seq(
    gzip / includeFilter := "*.html" || "*.css" || "*.js",
    gzip / excludeFilter := HiddenFileFilter,
    gzip / target := webTarget.value / gzip.key.label,
    deduplicators += SbtWeb.selectFileFrom((gzip / target).value),
    gzip := gzipFiles.value
  )

  def gzipFiles: Def.Initialize[Task[Pipeline.Stage]] = Def.task {
    val targetDir = (gzip / target).value
    val include = (gzip / includeFilter).value
    val exclude = (gzip / excludeFilter).value
    mappings =>
      val gzipMappings = for {
        (file, path) <- mappings if !file.isDirectory && include.accept(file) && !exclude.accept(file)
      } yield {
        val gzipPath = path + ".gz"
        val gzipFile = targetDir / gzipPath
        IO.gzip(file, gzipFile)
        (gzipFile, gzipPath)
      }
      mappings ++ gzipMappings
  }
}
