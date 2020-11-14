name := "play_2048"

version := "1.0"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

lazy val `play_2048` = (project in file("."))
  .enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "org.scala-lang" % "scala-swing" % "2.11+",
  "org.scalafx" %% "scalafx" % "8.0.60-R9"
)

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")