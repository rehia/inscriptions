import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "inscriptions"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
       "com.typesafe" % "play-plugins-inject" % "2.0.3",
       "org.hibernate" % "hibernate-entitymanager" % "4.1.7.Final",
       "postgresql" % "postgresql" % "9.1-901.jdbc4",
       "org.mockito" % "mockito-all" % "1.9.5",
       "pdf" % "pdf_2.9.1" % "0.3"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      resolvers += Resolver.url("My GitHub Play Repository", url("http://www.joergviola.de/releases/"))(Resolver.ivyStylePatterns),
      ebeanEnabled := false 
    )

}
