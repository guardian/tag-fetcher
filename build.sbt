organization := "com.gu"

name := "tag-fetcher"

version := "0.1-SNAPSHOT"

libraryDependencies ++= Seq(
    "net.databinder.dispatch" %% "dispatch-core" % "0.9.5",
    "org.specs2" %% "specs2" % "2.2.3"
)

publishTo <<= (version) { version: String =>
  val publishType = if (version.endsWith("SNAPSHOT")) "snapshots" else "releases"
  Some(
    Resolver.file(
      "guardian github " + publishType,
      file(System.getProperty("user.home") + "/guardian.github.com/maven/repo-" + publishType)
    )
  )
}