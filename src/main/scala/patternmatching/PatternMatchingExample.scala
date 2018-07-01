package patternmatching

object PatternMatcingExample extends App {
  val x = 273
  case class Person(fname: String, lname: String, age: Int)
  val aPers = Person("raman", "shastri", 34)
  // wildcard patterns match everything
  val wcMatch = aPers match {
    case Person("lala", "ram", 10) => "We got a person match"
    case _                         => "The wild card will match everything"
  }
  println("Wildcard match " + wcMatch)
  //  // Wildcards can be used to ignore parts of an object not of interest
  println(aPers match {
    case Person(_, _, 34) => "We have a match on person on age"
  })
  //  A constant pattern matches only itself
  def describe(x: Any) = x match {
    case 5       => "five"
    case true    => "truth"
    case "hello" => "hi!"
    case Nil     => "the empty list"
    case _       => "something else"
  }
  println(describe(5))
  println(describe(Nil))
  println(describe(aPers))

  // variable pattern
  // like wildcard matches everything. unlike wildcard, value of variable is available to attach to the match
  val persmatch = aPers match {
    case Person(_, _, 20) => 200
    case yzq              => "aPers  is " + yzq
  }
  println(persmatch)

  //  // constructor pattern
  println(aPers match {
    case Person(_, _, _) => "We got the constructor match"
  })

  //  // Sequence patterns
  val anArray = Array(1, 2, 3, 4)
  println(anArray match {
    case Array(1, 2, _*) => "The array is  matched"
  })
  val alist = List(1, 2, 3, 4)
  println(alist match {
    case x :: xs => "The  list is matched and its head is " + x + " and its tail is " + xs
  })
  // variable binding - we can add  a variable and on a successful match the variable is set to the matched object
  println(anArray match {
    case Array(h @ _, t @ _*) => "The array is  matched and first element is " + h +
      " and the remaining are " + t
  })

  //  // tuple patterns
  println((1, 2, 3) match {
    case (x, y, z) => "tuple matched and first element is " + x
    case _         =>
  })

  //  // typed patterns
  val aPersAsAny = aPers.asInstanceOf[Any]
  println(aPersAsAny match {
    case ani: Int  => "aPers is an Integer ?"
    case s: String => s.length
    case p: Person => "we hae a person wih fname : " + p.fname
  })

  //  // pattern guards
  //  // A pattern guard comes after a pattern and starts with an if.
  //  // The guard can be an arbitrary boolean expression, which typically refers to variables in the pattern.
  val vFPG = 38
  def isPrime(x: Int): Boolean = if (x == 1) false else if (x == 2) true
  else (2 to (x / 2) + 1).forall(n => x % n != 0)
  println(vFPG match {
    case x if (isPrime(x)) => "vFPG is  a prme number"
    case _                 => " Not Prime"
  })

  //  // The Option type
  //  // Optional values are produced by some of the standard operations on Scala’s collections eg a Map
  val playerAndRunsMap = Map("Sachin" -> 15921, "Ricky" -> 13378, "Jacques" -> 13289,
    "Rahul" -> 13288)
    println(playerAndRunsMap.get("Virat"))
    def getRuns(player: Option[Int]) = player match {
      case Some(p) => p
      case None    => "?"
    }
    println(getRuns(playerAndRunsMap get "Sachin"))
    println(getRuns(playerAndRunsMap get "Vivian"))
  //
  //  // Sequence of cases as functions
  //  // A case sequence is amore general literal function with multiple entry points
  //  val withDefault: Option[Int] => Int = {
  //    case Some(x) => x
  //    case None    => 0
  //  }
  //  println(withDefault(playerAndRunsMap get "asterix"))
  //  // let us define factorial using  sequence of cases
  //  def factCase: Int => Int = {
  //    case 0 => 1
  //    case x => x * factCase(x - 1)
  //  }
  //  println(factCase(6))

  // Partial functions
  // A sequence of cases can define a partial function
  //  val second: List[Int] => Int = {
  //    case x :: y :: xs => y
  //  }
  //  println(second(List(1, 2, 3)))
  //  println(second(List(1)))

  //  val secondPF: PartialFunction[List[Int], Int] = {
  //    case x :: y :: _ => y
  //  }
  //  println(secondPF(List(1, 2, 3)))
  // Partial functions have a method isDefinedAt, which can be used to test
  // whether the function is defined at a particular value
  //  if (secondPF.isDefinedAt(List())) {
  //    println(secondPF(List(1)))
  //  }
}
