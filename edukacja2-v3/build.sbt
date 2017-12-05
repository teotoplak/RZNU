name := """edukacja2-v3"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "org.webjars" % "bootstrap" % "3.3.7-1",
  "io.swagger" %% "swagger-play2" % "1.5.3"
)

fork in run := false

includeFilter in (Assets, LessKeys.less) := "footer.less"
