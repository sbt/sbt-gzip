sbt-gzip
==========

[sbt-web] plugin for gzip compressing web assets.

[![Build Status](https://github.com/sbt/sbt-gzip/actions/workflows/build-test.yml/badge.svg)](https://github.com/sbt/sbt-gzip/actions/workflows/build-test.yml)

Add plugin
----------

Add the plugin to `project/plugins.sbt`. For example:

```scala
addSbtPlugin("com.github.sbt" % "sbt-gzip" % "2.0.0")
```

Your project's build file also needs to enable sbt-web plugins. For example with build.sbt:

    lazy val root = (project in file(".")).enablePlugins(SbtWeb)

As with all sbt-web asset pipeline plugins you must declare their order of execution e.g.:

```scala
pipelineStages := Seq(gzip)
```

Configuration
-------------

### Filters

Include and exclude filters can be provided. For example, to only create
gzip files for `.js` files:

```scala
gzip / includeFilter := "*.js"
```

Or to exclude all `.js` files but include any other files:

```scala
gzip / excludeFilter := "*.js"
```

The default filter is to only include `.html`, `.css`, and `.js` files:

```scala
gzip / includeFilter := "*.html" || "*.css" || "*.js"
```


Contribution policy
-------------------

Contributions via GitHub pull requests are gladly accepted from their original author. 

License
-------

This code is licensed under the [Apache 2.0 License][apache].

[sbt-web]: https://github.com/sbt/sbt-web
[apache]: http://www.apache.org/licenses/LICENSE-2.0.html
