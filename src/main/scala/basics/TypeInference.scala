package basics

object TypeInference extends App {
  val x = 1 + 2
  println("x is " + x + " and its inferred type is Int " + x.isInstanceOf[Int])
  val y = x.toString
  println("y is " + y + " and its inferred type is String " + y.isInstanceOf[String])
  // omitting the result type in a recursive method will raise error
  def fact(n: Int): Int = if (n == 0) 1 else n * fact(n - 1)

  import scala.reflect.ClassTag
  println("Verify the type of fact : " + (fact _).isInstanceOf[Int => Int])
  println("Verify the type of fact : " + (fact _).isInstanceOf[Function1[Int, Int]])

  case class MyPair[A, B](x: A, y: B)
  def id[T](x: T) = x
  val p = MyPair(1, "q")
  val pn = MyPair[Int, String](1, "q")
  println(ClassTag(p.getClass))
  println(ClassTag(pn.getClass))
  val q = id(1)
  val qn = id[Int](1)

}
