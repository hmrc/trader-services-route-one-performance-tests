lazy val root = (project in file("."))
  .enablePlugins(GatlingPlugin)
  .enablePlugins(SbtAutoBuildPlugin)
  .settings(
      name := "trader-services-route-one-performance-tests",
      version := "0.1.0-SNAPSHOT",
      scalaVersion := "2.12.12",
      //implicitConversions & postfixOps are Gatling recommended -language settings
      scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-language:postfixOps"),
      // Enabling sbt-auto-build plugin provides DefaultBuildSettings with default `testOptions` from `sbt-settings` plugin.
      // These testOptions are not compatible with `sbt gatling:test`. So we have to override testOptions here.
      Test / testOptions := Seq.empty,
      libraryDependencies ++= Dependencies.test
  )

val hmrcTypesafeReleases = "typesafe-releases" at "https://nexus-preview.tax.service.gov.uk/content/repositories/typesafe-releases/"