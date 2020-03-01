enablePlugins(SbtNativePackager)
enablePlugins(JavaAppPackaging)
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")

val protoSettings = Seq(
  PB.targets in Compile := Seq(
    scalapb.gen(flatPackage = true) -> (sourceManaged in Compile).value
  )
)
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full) // simulacrum in 2.12
addCompilerPlugin(scalafixSemanticdb)

lazy val `hackathon-backend` = (project in file("."))
  .settings(
    organization := "com.guys.coding",
    name := "hackathon-backend",
    scalaVersion := "2.12.10",
    resolvers ++= Dependencies.additionalResolvers,
    libraryDependencies ++= Dependencies.all,
    scalacOptions ++= CompilerOps.all,
    parallelExecution in Test := false
  )
  .settings(protoSettings: _*)

PB.protoSources in Compile :=
  Seq.empty
// Seq(
//   "user-proto"
// ).map(baseDirectory.value / "hackathon-backend-proto" / _)
