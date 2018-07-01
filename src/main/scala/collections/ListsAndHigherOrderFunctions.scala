package collections

object ListAndHihgerOrderFunctionsEx extends App {
  // lists are constructed using the cons :: operator and Nil which represents the empty List
  // Lists are homogeneous. All the elements of  lists have the same type
  val alist = List(1, 2, 3, 4)
  println(alist.isInstanceOf[List[Int]])
  val alistFCons = 1 :: 2 :: 3 :: 4 :: Nil
  println(alist, alistFCons, alist == alistFCons)
  // All operators in Scala which have colon associate to the tright
  //  val alistFConsCalls = 1 :: (2 :: (3 :: (4 :: (Nil))))
  //  println(alistFConsCalls)
  //
  //  // basic operations on lists
  println("head of list " + alist.head)
  println("tail of list " + alist.tail)
  println("is list empty " + alist.isEmpty)

  // first order methods on lists
  println("Lists are concatenated by the ::: triple colon operator " + (alist ::: List(5, 6, 7, 8)))
  println("last and init are analogous to head and taill but from the end so last -  " +
    alist.last + " and init - " + alist.init)
  println("Reversing the list " + alist.reverse)
  println("drop - will drop elements " + alist.drop(2) + " take will take " + alist.take(2) + " splitAt will split - " + alist.splitAt(2))
  println("List elements can be accessed by indices zero based, hence first element - " + alist(0) +
    " and the third one " + alist(2))

  // first order operations on multiple lists
  //  val blist = List(3, 4, 5, 6, 7, 8)
  val blist = List("one", "two", "three")
  println("flattern will flatten a list of lists " + List(alist, blist).flatten)
  println("zipping two lists will give us a list with tuples pairing elements from first and second list " +
    alist.zip(blist))
  // If the two lists are of different length, any unmatched elements are dropped
  println("unzip will chage the zipped list of tubles back to the individual lists " + alist.zip(blist).unzip)

  // displaying lists
  println("we can make a string of the list elements using mkString for example - " + alist.mkString("!"))
  println("mkstring provides for a pre, separator, post invocation - " + alist.mkString("[", ",", "]"))

  // higher order methods on lists
  println("Map operation applied to each individual member " + alist.map(x => x * 2))
  println("FlatMap  return value should be an iterable - values will be flattened " +
    alist.flatMap { x => (1 to x) })
  println("Use flatmap and map to generate tuples combining 1 to 4 with 1 to 3  such that ( 1 <= x < y <= 4")
  val alistFlatMappedAndMapped = alist.flatMap(x => (1 to x).map(y => (x, y)))
  println(alistFlatMappedAndMapped)
  println("Use a  for comprehension to generate the tuples")
  println(for (x <- alist; y <- 1 to x) yield (x, y))

  // filter, partition, dropwhile, takewhile, span
  println("filter alist to even numbers " + alist.filter(x => x % 2 == 0))
  println("partition similar to filter generates two lists " + alist.partition { x => x % 2 == 0 })
  println("dropwhile alist is less than 2 " + alist.dropWhile { x => x < 2 })
  println("takewhile alistt is less than 2 " + alist.takeWhile { x => x < 2 })
  println("span is combination of ttakewhile and dropwhile " + alist.span { x => x < 2 })

  // folding lists
  //  fold left the slash is to the left of the colon
  println("alist before the fold operations is  " + alist)
  println((0 /: alist)((x, y) => x + y))
  println("alist fold left " + alist.foldLeft(2)(_ + _))
  //  fold right the slash is to the right of the column
  println("alist fold right " + (alist :\ 0)(_ + _))
  println(alist.foldRight(1)(_ * _))

  // reducing list
  println(alist.reduce(_ + _))
  // sorting lists
  println(List(3, 2, -1, 9, -17, 15, 4).sortWith(_ < _))

  // tail recursive factorial
  //  import scala.annotation.tailrec
  //  def tailFact(x: Int): Int = {
  //    def insf(a: Int, acc: Int): Int = if (a == 0) acc else insf(a - 1, a * acc)
  //    insf(x, 1)
  //    //    @tailrec
  //    //    def fact(a: Int): Int = if (a == 0) 1 else a * factf(a - 1)
  //    //    fact(x)
  //  }
  //  println(tailFact(10))

  // curried functions - we can  have parameter lists
  def sumCurried(x: Int)(y: Int): Int = x + y
  println("sum from curried function is " + sumCurried(3)(4))
  def sumFirst(x: Int) = (y: Int) => x + y
  val asmF = sumFirst(3)
  println("asmF(4) is " + asmF(4))

  //mapreduce using higher order functions
  //  def mapReduce(f: Int => Int, c: (Int, Int) => Int, acc: Int)(a: Int, b: Int): Int =
  //    if (a > b) acc else c(f(a), mapReduce(f, c, acc)(a + 1, b))
  //  println(mapReduce(x => x * x, (x, y) => x + y, 0)(1, 3))

  def mapReduceDet(f: Int => Int, c: (Int, Int) => Int, acc: Int)(a: Int, b: Int): Int = {
    if (a > b) {
      println("a is " + a + " and b is " + b + " and acc is " + acc)
      acc
    } else {
      println("Inside else block calling c(f(" + a + "),mapReduceDet(f,c,"
        + acc + ")(" + (a + 1) + "," + b + ")")
      c(f(a), mapReduceDet(f, c, acc)(a + 1, b))
    }
  }
  println(mapReduceDet(x => x * x, (x, y) => x + y, 0)(1, 5))

  // partially applied functions
  // in functional programming we apply functions to arguments
  /// if we leave some of the arguments we get a  partially applied function
  // which we can assign to values
  //  def wrap(pre: String, html: String, post: String) = pre + html + post
  //  val wrapWithDiv = wrap("<div>", _: String, "</div")
  //  println((wrapWithDiv).isInstanceOf[String => String])
  //  println(wrapWithDiv("Scala Partially Applied Function"))
  //
  //  def sum(a: Int, b: Int, c: Int) = a + b + c
  //  val asumPF = sum _
  //  println(asumPF.isInstanceOf[(Int, Int, Int) => Int])
}
