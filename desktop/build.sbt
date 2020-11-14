name:="2048"

version:="1.0"

scalaVersion:="2.11.7"

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.8" % "test",
  "org.specs2" %% "specs2-core" % "2.4.14" % "test",
  "org.specs2" % "specs2-junit_2.11" % "2.4.14",
  "org.scala-lang" % "scala-swing" % "2.11+",
  "org.scalafx" %% "scalafx" % "8.0.60-R9"
)
