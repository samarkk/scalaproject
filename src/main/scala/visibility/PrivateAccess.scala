package visibility

class Outer {
  class Inner {
    private[Outer] def f() { println("f") }
    class InnerMost {
      f() // OK
    }
  }
  //  Private members are visible only inside the class or object that contains the member definition
  // if we put the [Outer] qualifier for f the error will go away - we can specify the scope of the access modified
  (new Inner).f() // error: f is not accessible
}
object PrivateAccess extends App {
  //  println("bb")
  val aoi = new Outer()
  val aoin = new aoi.Inner()
  val inmst = new aoin.InnerMost()

}
