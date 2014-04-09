package com.typesafe.sbt.gzip

import sbt._
import com.typesafe.sbt.web.SbtWeb
import com.typesafe.sbt.web.pipeline.Pipeline
import sbt.Keys._

object Import {

  object GzipKeys {
    val compress = TaskKey[Pipeline.Stage]("gzip-compress", "Add gzipped files to asset pipeline.")
  }

}

object SbtGzip extends AutoPlugin {

  override def requires = SbtWeb

  override def trigger = AllRequirements

  val autoImport = Import

  import SbtWeb.autoImport._
  import WebKeys._
  import autoImport.GzipKeys._

  override def projectSettings: Seq[Setting[_]] = Seq(
    includeFilter in compress := AllPassFilter,
    excludeFilter in compress := HiddenFileFilter,
    compress := gzipFiles.value,
    pipelineStages <+= compress
  )

  def gzipFiles: Def.Initialize[Task[Pipeline.Stage]] = Def.task {
    mappings =>
      val targetDir = webTarget.value / compress.key.label
      val include = (includeFilter in compress).value
      val exclude = (excludeFilter in compress).value
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
