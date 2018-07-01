package types

object TypeParametersEx extends App {
  //  def isort(xs: List[Int]): List[Int] = {
  //    def insert(x: Int, xs: List[Int]): List[Int] = if (xs == Nil || x < xs.head)
  //      x :: xs else xs.head :: insert(x, xs.tail)
  //    xs match {
  //      case Nil      => xs
  //      case x :: xs1 => insert(x, isort(xs1))
  //    }
  //  }
  //  val anIntList = List(4, 3, 2, 7, 9)
  //  println(isort(anIntList))
  // let us make isort generic
  //  def isortGen[T](xs: List[T], ord: Ordering[T]): List[T] = {
  //    def insert(x: T, xs: List[T]): List[T] = if (xs == Nil || ord.lt(x, xs.head))
  //      x :: xs else xs.head :: insert(x, xs.tail)
  //    xs match {
  //      case Nil      => xs
  //      case x :: xs1 => insert(x, isortGen(xs1, ord))
  //    }
  //  }
  val astrList = List("Sachin", "Ricky", "Jacques", "Rahul")
  //  println(isortGen(astrList, Ordering[String]))
  //  //  let us use implicits and currying to knock out the need to specify Ordering
  def isortGenImp[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {
    def insert(x: T, xs: List[T]): List[T] = if (xs == Nil || ord.lt(x, xs.head)) x :: xs else xs.head :: insert(x, xs.tail)
    xs match {
      case Nil      => xs
      case x :: xs1 => insert(x, isortGenImp(xs1))
    }
  }
  println("Using generic and implement to sort aStrList: " + isortGenImp(astrList))
}
