package implicits

object ImplicitsExample2 extends App {
  abstract class MyFirstAbstractClass[A] {
    def addition(x: A, y: A): A
  }
  abstract class MySecondAbstractClass[A] extends MyFirstAbstractClass[A] {
    def unit: A
  }
  implicit object StringClass extends MySecondAbstractClass[String] {
    override def addition(x: String, y: String) = x concat y
    override def unit: String = ""
  }
  implicit object IntClass extends MySecondAbstractClass[Int] {
    override def addition(x: Int, y: Int) = x + y
    override def unit = 0
  }
  def sum[A](xs: List[A])(implicit m: MySecondAbstractClass[A]): A =
    if (xs.isEmpty) m.unit
    else m.addition(xs.head, sum(xs.tail))
  println(sum(List(1, 2, 3, 4)))
  println(sum(List("a", "b", "c", "d")))
}
