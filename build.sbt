name := "wth-sjp"

version := "0.0.1"

organization in ThisBuild := "io.funkyminds"

scalaVersion := "2.13.4"

scalacOptions := Seq("-unchecked", "-deprecation")

//@formatter:off
libraryDependencies ++= Seq(
  "io.funkyminds"       %%  "wth-core"            % "0.0.1",
  "dev.zio"             %%  "zio"                 % "1.0.3",
  "org.apache.commons"  %   "commons-text"        % "1.9",
  "org.jsoup"           %   "jsoup"               % "1.13.1"
)
//@formatter:on