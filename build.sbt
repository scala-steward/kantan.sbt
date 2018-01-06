lazy val root = Project(id = "kantan-sbt", base = file("."))
  .settings(moduleName := "root")
  .settings(
    publish         := {},
    publishLocal    := {},
    publishArtifact := false
  )
  .aggregate(core, kantan, release, scalastyle)

lazy val core = project
  .settings(
    moduleName := "kantan.sbt",
    name       := "core",
    sbtPlugin  := true
  )
  .enablePlugins(AutomateHeaderPlugin)
  .settings(
    addSbtPlugin("com.eed3si9n"        % "sbt-unidoc"      % Versions.sbtUnidoc),
    addSbtPlugin("com.github.tkawachi" % "sbt-doctest"     % Versions.sbtDoctest),
    addSbtPlugin("com.geirsson"        % "sbt-scalafmt"    % Versions.sbtScalafmt),
    addSbtPlugin("com.typesafe.sbt"    % "sbt-ghpages"     % Versions.sbtGhPages),
    addSbtPlugin("com.typesafe.sbt"    % "sbt-site"        % Versions.sbtSite),
    addSbtPlugin("de.heikoseeberger"   % "sbt-header"      % Versions.sbtHeader),
    addSbtPlugin("io.spray"            % "sbt-boilerplate" % Versions.boilerplate),
    addSbtPlugin("org.scoverage"       %% "sbt-scoverage"  % Versions.scoverage),
    addSbtPlugin("org.tpolecat"        % "tut-plugin"      % Versions.tut),
    addSbtPlugin("org.wartremover"     % "sbt-wartremover" % Versions.wartRemover)
  )

lazy val release = project
  .settings(
    moduleName := "kantan.sbt-release",
    name       := "release",
    sbtPlugin  := true
  )
  .enablePlugins(AutomateHeaderPlugin)
  .settings(
    addSbtPlugin("com.github.gseitz" % "sbt-release" % Versions.sbtRelease)
  )
  .dependsOn(core)

lazy val scalastyle = project
  .settings(
    moduleName := "kantan.sbt-scalastyle",
    name       := "scalastyle",
    sbtPlugin  := true
  )
  .enablePlugins(AutomateHeaderPlugin)
  .settings(
    addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % Versions.scalastyle)
  )
  .dependsOn(core)

lazy val kantan = project
  .settings(
    moduleName := "kantan.sbt-kantan",
    name       := "kantan",
    sbtPlugin  := true
  )
  .enablePlugins(AutomateHeaderPlugin)
  .settings(
    addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % Versions.sbtSonatype),
    addSbtPlugin("com.jsuereth"   % "sbt-pgp"      % Versions.sbtPgp)
  )
  .dependsOn(core, release, scalastyle)

addCommandAlias(
  "validate",
  ";clean;scalastyle;test:scalastyle;scalafmtCheck;test:scalafmtCheck;scalafmtSbtCheck;compile;scripted"
)
