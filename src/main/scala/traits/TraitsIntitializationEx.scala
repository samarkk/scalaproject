package traits
object TraitsIntitializationEx extends App {
  trait RationalTrait {
    val numerArg: Int
    val denomArg: Int
    require(denomArg != 0)
    private val g = gcd(numerArg, denomArg)
    val numer = numerArg / g
    val denom = denomArg / g
    private def gcd(a: Int, b: Int): Int =
      if (b == 0) a else gcd(b, a % b)
    override def toString = numer + "/" + denom
  }
  // keyword new appears in front of a trait name -
  // an anonymous class is going to be created and the RationalTrait will be mixed into it
  // The anonymous class is going to be intialized with numerArg and denomAarg
  // However first the RationalTrait will be initialized and then the anonymous class
  // so we will have an error from this - divide by zero exception as the two fields have not been initialized
  //  val arft = new RationalTrait { override val numerArg = 20; override val denomArg = 27 }
  //  println(arft)

  /// we can use  pre initialized fields to get around this
  // pre-initialized fields  initialize a field of a subclass before the superclass is called
  val aRatNFromTrait = new { override val numerArg = 24; override val denomArg = 84 } with RationalTrait
  println(aRatNFromTrait)

  /// we can use lazy values which are not evaluated until they are called upon

  trait LazyRationalTrait {
    val numerArg: Int
    val denomArg: Int
    lazy val numer = numerArg / g
    lazy val denom = denomArg / g
    override def toString = numer + "/" + denom
    private lazy val g = {
      require(denomArg != 0)
      gcd(numerArg, denomArg)
    }
    private def gcd(a: Int, b: Int): Int =
      if (b == 0) a else gcd(b, a % b)
  }

  // instance of LazyRationalTrait is initialized, primary constructor of the  anonymous class created by the expression is executed
  // toString method is invoked on the constructed object.  numer field is accessed . which uses g which is evaluated
  // numerArg and denomArg are available and the lazy values can be successfully initialized
  val aLRT = new LazyRationalTrait {
    override val numerArg = 125
    override val denomArg = 256
  }
  println(aLRT)
}
