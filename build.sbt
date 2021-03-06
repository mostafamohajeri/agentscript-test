
// The simplest possible sbt build file is just one line:

scalaVersion := "2.13.3"
// That is, to create a valid sbt build, all you've got to do is define the
// version of Scala you'd like your project to use.

// ============================================================================

// Lines like the above defining `scalaVersion` are called "settings". Settings
// are key/value pairs. In the case of `scalaVersion`, the key is "scalaVersion"
// and the value is "2.13.3"

// It's possible to define many kinds of settings, such as:

name := "asc-test"
organization := "nl.uva.sne.cci"
version := "1.0"
lazy val AkkaVersion = "2.6.10"

resolvers += Resolver.bintrayRepo("uva-cci","script-cc-grammars")
resolvers += Resolver.bintrayRepo("uva-cci","agent-script-playgrounds")
resolvers += Resolver.bintrayRepo("uva-cci","styla-prolog")

// Want to use a published library in your project?
// You can define other libraries as dependencies in your build like this:

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
//resolvers += Resolver.mavenLocal
libraryDependencies += "nl.uva.sne.cci" %% "agent_script_commons" % "0.2.17.SNAP7"
libraryDependencies += "nl.uva.sne.cci" %% "agent_script_grounds" % "0.2.17.SNAP7"
//libraryDependencies += "nl.uva.sne.cci" % "parser" % "0.2.14.SNAP3"
//libraryDependencies += "nl.uva.sne.cci" % "scala-generator" % "0.2.14.SNAP3"
libraryDependencies += "nl.uva.sne.cci" %% "stylaport" % "0.1.2"

//libraryDependencies += "org.scalamock" %% "scalamock" % "5.1.0" % Test
// https://mvnrepository.com/artifact/org.mockito/mockito-scala-scalatest
libraryDependencies += "org.mockito" %% "mockito-scala-scalatest" % "1.16.29" % Test

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.3" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test
libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion

//enablePlugins(JacocoCoverallsPlugin)
enablePlugins(AgentScriptCCPlugin)

(agentScriptCC / agentScriptCCPath) in Compile := (baseDirectory.value / "src" / "test" / "asl")
 Compile / sourceGenerators += (Compile / agentScriptCC).taskValue
classLoaderLayeringStrategy in Test := ClassLoaderLayeringStrategy.ScalaLibrary
parallelExecution in Test := false

//jacocoExcludes in Test := Seq(
//  "*lzycompute*",
//)
//
//jacocoReportSettings := JacocoReportSettings(
//  "Jacoco Coverage Report",
//  None,
//  JacocoThresholds(),
//  Seq(JacocoReportFormats.ScalaHTML, JacocoReportFormats.XML),
//  "utf-8")
//



