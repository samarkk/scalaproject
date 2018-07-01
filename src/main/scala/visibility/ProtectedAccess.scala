package visibility

package p {
  class Super {
    protected def f() { println("f") }
  }
  // protected members are accessible only inside the sub classes
  class Sub extends Super {
    f()
  }
  class Other {
    //        (new Super).f()  // error: f is not accessible
  }
}

object ProtectedAccess extends App {
  //  val apsi = new p.Super()
  val apsisub = new p.Sub()
}
