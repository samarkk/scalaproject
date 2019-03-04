package basics

object BasicTypesAndOps extends App {

  // scala statements semi-colons are optional,
  // if we want multiple statements on one line we need to use semi-colon
  //  val aval = 3
  //  println("aval is " + aval)
  //  //
  //  //  //    vals are immutable cannot be reassigned to
  //  //  aval = 4
  //  //
  //  // we can reassign to vars
  //  var avar = 4 //> avar  : Int = 4
  //  avar = 34
  //  println("avar after reassingment is " + 34)

  // basic types part of package scala - correspond to java primitves - with initial letter capitalized
  // scala and java.lang packges are automatically imported

  // literals
  //  val hex = 0xAB
  //  val along = 1L
  //  val afloat = 3.24671e2
  //  val achar = 'A'
  //  val acharLit = '\101'
  //  val aRawString = """one can / put ' anything"""
  //  println(aRawString)
  //  operators are methods
  //  println((1).+(2))

  // object equality == is used to compare objects value equality for both value and reference types
  //  println((1 to 34) == (1 to 34))
  //  println(List(1, 2, 3) == List(1, 2, 4))

  // to compare reference equality eq and ne are used
  //  val al = List(1, 2, 3)
  //  var b = al
  //  println(al eq b)
  //  println(al ne b)

  // scala statement blocks are a list of expressions. the last expression is the value returned from
  // the block
  val a = {
    val x = 100; val y = 37; x + y
  }
  println(a)

  // scala if control block is an expression that returns a value
  val ifRes = if ("abc".length == 3)
    "equal to 3 " else "not equal to 3"
  //  println(ifRes)

  /// scala for control sructure - we use generators
  //  println("first for expression")
  for (x <- 1 to 3) println(x)
  //  // we specify step using the by kewyord
  //  println("using by to specify for steps")
  for (x <- 10 to 5 by -2) println(x)
  //  // we can use multiple generators
  //  println("for block with multiple generators")
  for (x <- 1 to 3; y <- 1 to 2) println(x, y, x * y)
  //  // we can put conditions on the generator
  //  println("for block with multiple generators and conditions")
  for (x <- 1 to 3 if x % 2 == 0; y <- 1 to 2) println(x, y, x * y)
  //  // we can introduce variables
  //  println("for block with multiple generators and variables")
  for (x <- 1 to 3; z = x * 2; y <- 1 to 2 if x % 2 == 0) println(x, z, y, x * y)

  //   scala for and yield
  //   when we use yield with a for block we get a collection which we can assign to a variable
  val forYield = for (x <- 1 to 7) yield x
  //  println(forYield, forYield.sum)
  //  //  yield can be given a list of expressions - the last statement will be the return value
  val forYieldBlock = for (x <- 1 to 7) yield {
    val rand = new scala.util.Random()
    val y = rand.nextInt(100)
    x * y
  }
  //  println(forYieldBlock, forYieldBlock.sum)

  // scala has the while control structure - it is not used much in functional programming
  var xforwhile = 0
  while (xforwhile < 3) {
    println(xforwhile)
    xforwhile += 1
  }

}
