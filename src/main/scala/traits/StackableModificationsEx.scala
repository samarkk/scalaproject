package traits

object StackableModificationEx extends App {
  import scala.collection.mutable.ArrayBuffer

  abstract class IntQueue {
    def get(): Int
    def put(x: Int)
  }
  class BasicIntQueue extends IntQueue {
    val buf = new ArrayBuffer[Int]
    override def get() = buf.remove(0)
    override def put(x: Int) { buf += x }
  }

  // The trait  calls the super put method and extends IntQueue. IntQueue does not have an implementation of put
  //. Thus the abstract override
  // Incrementing thus has to be mixed in after a class that has a concrete implementation of put method
  trait Incrementing extends IntQueue {
    abstract override def put(x: Int) { super.put(x + 1) }
  }

  trait Doubling extends IntQueue {
    abstract override def put(x: Int) { super.put(2 * x) }
  }

  trait Filtering extends IntQueue {
    abstract override def put(x: Int) {
      if (x % 2 == 0) super.put(x)
    }
  }

  val abIntIncrDblng = new BasicIntQueue with Incrementing with Doubling
  abIntIncrDblng.put(10)
  println(abIntIncrDblng.get())

  val abIntWDblngWIncr = new BasicIntQueue with Doubling with Incrementing with Filtering
  for (x <- 1 to 10) abIntWDblngWIncr.put(x)
  abIntWDblngWIncr.buf.foreach(println)
  //  for (a <- 1 to 10) println(abIntWDblngWIncr.get())
}
