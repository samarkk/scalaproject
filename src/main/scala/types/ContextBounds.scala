package types

import scala.util.Random

object ContextBounds extends App {
  // Context bounds were introduced in Scala 2.8.0, and are typically used with the
  // so-called type class pattern, a pattern of code that emulates the functionality
  // provided by Haskell type classes, though in a more verbose manner.
  //  def f[A: Ordering](a: A, b: A): Int = implicitly[Ordering[A]].compare(a, b)

  def implicitlySort[C, D: Ordering](seq: Seq[C])(f: C => D): Seq[C] = seq.sortBy(f)(implicitly[Ordering[D]])

  //    [D:Ordering ] is a context bound - the parametrized implicitlylSort method expects
  //  an implicit argument of type Ordering[D].
  //  It is not there in the parameter list, so no named argument that we can pick up.
  //  Thus we can use implicitly in combination with a context bound.
  //  Whatever instance is passed to the method for the implicit parameter is resolved by implicitly - hence implicitly[Ordering[D]]

  // apply it to a list of integers and string
  println(implicitlySort(for (x <- 1 to 10) yield new Random().nextInt(100))(x => -x))
  println(implicitlySort(List("raman", "shaman", "chaman", "aman", "waman"))(x => x))

  // what if we want to sort strings descending
  def implicitlySortReverse[C, D: Ordering](seq: Seq[C])(f: C => D): Seq[C] = seq.sortBy(f)(implicitly[Ordering[D]].reverse)

  println(implicitlySortReverse(List("raman", "shaman", "chaman", "aman", "waman"))(x => x))

  val tupleList = List(("raman", "shastri", 200), ("raman", "mistri", 210),
    ("waman", "mistri", 190), ("waman", "shastri", 220))

  println("using implicitly sort with tuples")
  implicitlySort(tupleList)((x: (String, String, Int)) => (x._1, x._2, x._3)).foreach(println)

  println("\nSorting tuples by random elements random order")
  tupleList.sorted(Ordering.Tuple3(Ordering.String.reverse, Ordering.String.reverse, Ordering.Int)).foreach(println)

  println("\nSorting tuples using sort by and a function sorting strings using the head")
  tupleList.sortBy(x => (x._2.head,-x._3, x._1.head)).foreach(println)
  
  /*
   Q Explain implicitly
 implicitly is a keyword for shortening the signature. when a method takes a single implicit parameter of a parametrized type then we can use view bounds to shorten the signautre
 thus if we have
 case class MyList[A](list : List[A]){
 def msb[B](f:A=>B)( implicit ord : Ordering[B]):List[A] = list.sortBy(f)(ord)
 then we can instead write
 def msb[B:Ordering](f : A => B ):List[A] = list.sortBy(f)(implicitly[Ordering[B]])
 }
   */

}
