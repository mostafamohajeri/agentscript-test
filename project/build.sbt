resolvers += Resolver.bintrayRepo("uva-cci","script-cc-grammars")

addSbtPlugin("nl.uva.sne.cci" % "sbt-scriptcc" % "0.4.17.SNAP1")

//addSbtPlugin("com.github.sbt" % "sbt-jacoco" % "3.3.0")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.6.1")

addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.2.7")