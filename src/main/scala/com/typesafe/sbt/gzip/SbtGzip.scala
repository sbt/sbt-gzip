package com.typesafe.sbt.gzip

import sbt._
import com.typesafe.sbt.PluginCompat
import com.typesafe.sbt.web.SbtWeb
import com.typesafe.sbt.web.pipeline.Pipeline
import sbt.Keys._
import xsbti.FileConverter

object Import {

  @transient
  val gzip = TaskKey[Pipeline.Stage]("gzip-compress", "Add gzipped files to asset pipeline.")

}

object SbtGzip extends AutoPlugin {

  override def requires = SbtWeb

  override def trigger = AllRequirements

  val autoImport = Import

  import SbtWeb.autoImport._
  import WebKeys._
  import autoImport._

  override def projectSettings: Seq[Setting[?]] = Seq(
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
    implicit val converter: FileConverter = fileConverter.value
    mappings =>
      val gzipMappings = for {
        (fileRef, path) <- mappings
        file = PluginCompat.toFile(fileRef)
        if !file.isDirectory && include.accept(file) && !exclude.accept(file)
      } yield {
        val gzipPath = path + ".gz"
        val gzipFile = targetDir / gzipPath
        IO.gzip(file, gzipFile)
        (PluginCompat.toFileRef(gzipFile), gzipPath)
      }
      mappings ++ gzipMappings
  }
}
