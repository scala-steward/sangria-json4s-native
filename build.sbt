name := "sangria-json4s-native"
organization := "org.sangria-graphql"
mimaPreviousArtifacts := Set("org.sangria-graphql" %% "sangria-json4s-native" % "1.0.1")

description := "Sangria json4s-native marshalling"
homepage := Some(url("http://sangria-graphql.org"))
licenses := Seq(
  "Apache License, ASL Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

ThisBuild / crossScalaVersions := Seq("2.12.13", "2.13.5")
ThisBuild / scalaVersion := crossScalaVersions.value.last
ThisBuild / githubWorkflowPublishTargetBranches := List()
ThisBuild / githubWorkflowBuildPreamble ++= List(
  WorkflowStep.Sbt(List("mimaReportBinaryIssues"), name = Some("Check binary compatibility")),
  WorkflowStep.Sbt(List("scalafmtCheckAll"), name = Some("Check formatting"))
)

scalacOptions ++= Seq("-deprecation", "-feature")

scalacOptions += "-target:jvm-1.8"
javacOptions ++= Seq("-source", "8", "-target", "8")

libraryDependencies ++= Seq(
  "org.sangria-graphql" %% "sangria-marshalling-api" % "1.0.5",
  "org.json4s" %% "json4s-native" % "3.6.11",
  "org.sangria-graphql" %% "sangria-marshalling-testkit" % "1.0.3" % Test,
  "org.scalatest" %% "scalatest" % "3.2.8" % Test
)

// Release
ThisBuild / githubWorkflowTargetTags ++= Seq("v*")
ThisBuild / githubWorkflowPublishTargetBranches :=
  Seq(RefPredicate.StartsWith(Ref.Tag("v")))
  ThisBuild / githubWorkflowPublish := Seq(
  WorkflowStep.Sbt(
    List("ci-release"),
    env = Map(
      "PGP_PASSPHRASE" -> "${{ secrets.PGP_PASSPHRASE }}",
      "PGP_SECRET" -> "${{ secrets.PGP_SECRET }}",
      "SONATYPE_PASSWORD" -> "${{ secrets.SONATYPE_PASSWORD }}",
      "SONATYPE_USERNAME" -> "${{ secrets.SONATYPE_USERNAME }}"
    )
  )
)

// Site and docs

enablePlugins(SiteScaladocPlugin)
enablePlugins(GhpagesPlugin)
git.remoteRepo := "git@github.com:org.sangria-graphql/sangria-json4s-native.git"

// nice *magenta* prompt!
ThisBuild / shellPrompt := { state =>
  scala.Console.MAGENTA + Project.extract(state).currentRef.project + "> " + scala.Console.RESET
}

// Additional meta-info
startYear := Some(2016)
organizationHomepage := Some(url("https://github.com/sangria-graphql"))
developers := Developer(
  "OlegIlyenko",
  "Oleg Ilyenko",
  "",
  url("https://github.com/OlegIlyenko")) :: Nil
scmInfo := Some(
  ScmInfo(
    browseUrl = url("https://github.com/sangria-graphql/sangria-json4s-native"),
    connection = "scm:git:git@github.com:sangria-graphql/sangria-json4s-native.git"
  ))
