organizationName := "Nicolas Rinaudo"
licenses         := Seq("Apache-2.0" → url("https://www.apache.org/licenses/LICENSE-2.0.html"))
startYear        := Some(2017)

enablePlugins(PublishedPlugin)

lazy val check = TaskKey[Unit]("check")

check := {
  val actual   = IO.readLines((scalaSource in Compile).value / "Foo.scala")
  val expected = IO.readLines(file("expected.txt"))

  if(expected != actual)
    sys.error(s"Output doesn't match expected: \n${actual.mkString("\n")}")
  ()
}
