package usefullaids

object SortingEx extends App {
  // for( n <- 1 to 10 ) println ("Hello for the " + n + " time in scala")
  // Creating a custom ordering class extending Ordering
  // we need to just code in the compare method
  class CustOrdering extends Ordering[(String, String, Int)] {
    def compare(x: (String, String, Int), y: (String, String, Int)): Int = {
      val compare2 = x._2.compareTo(y._2)
      if (compare2 != 0) return -1 * compare2
      val compare1 = x._1.compareTo(y._1)
      if (compare1 != 0) return 1 * compare1
      return -x._3.compareTo(y._3)
    }
  }

  val alist = List(
    ("sachin", "tendulkar", 102),
    ("sachin", "tendulkar", 95), ("arjun", "tendulkar", 5), ("arjun", "vendulkar", 5),
    ("arjun", "vendulkar", 10), ("sachin", "vendulkar", 12),
    ("sachin", "vendulkar", 102), ("arjun", "tendulkar", 1))
  //  println(bstring.compareTo(bstring))
  val aco = new CustOrdering
  println("\ncalling sorted using the custom ordering\n")
  alist.sorted(aco).foreach(println)
  println("\nUsing the custom ordering to replace the implicit second parameter required\n")
  alist.sortBy(x => x)(aco).foreach(println)
  println("\nUsing the function straight inside - but we have no way to specify descending " +
    "string ordering\n")
  alist.sortBy(x => (x._1, x._3)).foreach(println)
  // we are creating a new ordering here
  // for the method type parameter we are saying a function from
  // tuple of (String, String, Int ) to Int
  val oBy = Ordering.by[(String, String, Int), Int](x => -x._3)
  println("\n using the oBy to sort the list\n")
  alist.sortBy(x => x)(oBy).foreach(println)
  println("\n Using on to create the explicit ordering that will be used to sort the list")
  // here again we have no way to specify the descending order for String
  alist.sortBy(x => x)(Ordering[(Int, String, String)].on(x => (-x._3, x._1, x._2)))
  // we can create a custom tuple ordering using the Ordering.Type already available
  // and plug in the reverse to get the descending string ordering we are interested in
  val customOrderingUsingReverse = Ordering.Tuple3(
    Ordering.String,
    Ordering.String.reverse, Ordering.Int)
  println("\nUsing the custom ordering that utilizes reverse to achive our goal\n")
  alist.sorted(customOrderingUsingReverse).foreach(println)
  // plugging in the custom ordering which we had created ourselves to sort
  println("\nUsing the custom ordering which we had created ourselves to sort\n")
  alist.sortBy(x => x)(aco).foreach(println)

  def custom_compare(x: (String, String, Int), y: (String, String, Int)): Boolean = {
    //    implicit def int2Boolean(x: Int): Boolean = if (x < 0) true else false
    val compare2 = x._2.compareTo(y._2)
    //    if (compare2 != 0) return -compare2
    if (compare2 != 0) if (compare2 < 0) return false else return true
    val compare1 = x._1.compareTo(y._1)
    //    if (compare1 != 0) return compare1
    if (compare1 != 0) if (compare1 < 0) return true else return false
    if (x._3.compareTo(y._3) < 0) return true else return false

  }
  println("\nUsing the custom_compare function in the sortwith method for the list to sort\n")
  alist.sortWith(custom_compare).foreach(println)
}
