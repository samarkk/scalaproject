package implicits
import math.Ordering
object ImplicitlyExample extends App {
  case class MyList[A](list: List[A]) {
    def sortBy[B](f: A => B)(implicit ord: Ordering[B]) = list.sortBy(f)

    /*
    Programming in Scala - 2nd edition - Dean Wampler
    The type parameter B : Ordering is called a context
		bound. It implies the second, implicit argument list that takes an Ordering[B] instance

		However, we need to access this Ordering instance in the method, but we no longer
		have a name for it, because it’s no longer explicitly declared in the source code. That’s
		Implicit Arguments

		what Predef.implicitly does for us. Whatever instance is passed to the method for
		the implicit argument is resolved by implicitly. Note the type signature that it requires,
		Ordering[B] in this case

		The combination of a context bound and the implicitly method is
		a shorthand for the special case where we need an implicit argument
		of a parameterized type, where the type parameter is one of the
		other types in scope (for example, [B : Ordering] for an implicit
		Ordering[B] parameter
 */

    def sortByImplicitly[B: Ordering](f: A => B): List[A] = list.sortBy(f)(implicitly[Ordering[B]])
  }
  val alist = MyList(List(20, 32, 41))
  println(alist.sortBy(x => x))
  println(alist.sortByImplicitly(x => x))
}
