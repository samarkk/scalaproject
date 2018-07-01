package collections

object MapsExample extends App {
  val courses = Map("course1" -> "Hadoop-BigData", "course2" -> "Apache Spark")
  println(courses)
  val coursesMapAsPairs = Map(("course1", "Hadoop-BigData"), ("course2", "Apache Spark"))
  println("using pairs " + coursesMapAsPairs)
  var ncourses: scala.collection.mutable.Map[String, String] = scala.collection.mutable.Map.empty[String, String] ++ courses
  ncourses("course3") = "Advanced Machine Learning"
  println(ncourses)
  ncourses += Tuple2("course4", "Mean Stack")
  println(ncourses)
  ncourses.remove("course1")
  println(ncourses)
  ncourses -= "course2"
  println(ncourses)
  println(ncourses.keys.map(x => (x, x.length)))
  println(ncourses.map(x => (x._1, x._1.length, x._2, x._2.length)))
  println(ncourses.map { case (cname, course) => (cname, cname.length, course, course.length) })
}
