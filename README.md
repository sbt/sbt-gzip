sbt-gzip
==========

[sbt-web] plugin for gzip compressing web assets.

[![Build Status](https://travis-ci.org/sbt/sbt-gzip.png?branch=master)](https://travis-ci.org/sbt/sbt-gzip)


Add plugin
----------

Add the plugin to `project/plugins.sbt`. For example:

```scala
addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.1")
```

Your project's build file also needs to enable sbt-web plugins. For example with build.sbt:

    lazy val root = (project.in file(".")).enablePlugins(SbtWeb)

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
includeFilter in gzip := "*.js"
```

Or to exclude all `.js` files but include any other files:

```scala
excludeFilter in gzip := "*.js"
```

The default filter is to only include `.html`, `.css`, and `.js` files:

```scala
includeFilter in gzip := "*.html" || "*.css" || "*.js"
```


Contribution policy
-------------------

Contributions via GitHub pull requests are gladly accepted from their original
author. Before we can accept pull requests, you will need to agree to the
[Typesafe Contributor License Agreement][cla] online, using your GitHub account.


License
-------

This code is licensed under the [Apache 2.0 License][apache].


[sbt-web]: https://github.com/sbt/sbt-web
[cla]: http://www.typesafe.com/contribute/cla
[apache]: http://www.apache.org/licenses/LICENSE-2.0.html
