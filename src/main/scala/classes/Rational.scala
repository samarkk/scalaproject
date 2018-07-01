package classes

// objects and classes with the same name defined in the same file are each other's companions
// Here object Rational is the companion object of class Rational and class Rational is the companion class of object Rational
// Companion objects are a good place to define static methods, utility methods etc
/// Companion objects and classes have access to each other's private members
object Rational {
  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

}

class Rational(val n: Int, val d: Int) {
  require(d != 0)
  /// the members of the companion object are brought in scope by explicit imports
  // import Rational._
  private val g = Rational.gcd(n.abs, d.abs)
  val numer = n / g
  val denom = d / g

  def this(n: Int) = this(n, 1)

  def +(that: Rational): Rational =
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom)

  def +(i: Int): Rational =
    new Rational(numer + i * denom, denom)

  def -(that: Rational): Rational =
    new Rational(
      numer * that.denom - that.numer * denom,
      denom * that.denom)

  def -(i: Int): Rational =
    new Rational(numer - i * denom, denom)

  def *(that: Rational): Rational =
    new Rational(numer * that.numer, denom * that.denom)

  def *(i: Int): Rational =
    new Rational(numer * i, denom)

  def /(that: Rational): Rational =
    new Rational(numer * that.denom, denom * that.numer)

  def /(i: Int): Rational =
    new Rational(numer, denom * i)

  override def toString = numer + "/" + denom

}

// Main here is a standalone object
// Scala applications can be executed by either extending App - a trait which incorporates hte main method but does not take arguments
// or by invoking the main method which can take arguments
object MainWhateverName {
  def main(args: Array[String]) {
    val x = new Rational(20, 30)
    println("x [" + x + "]")
    println("x * x [" + (x * x) + "]")
    println("x * 2 [" + (x * 2) + "]")

    // implicit fields, methods are defined with implicit keyword
    implicit def intToRational(x: Int) = new Rational(x)
    val r = new Rational(2, 3)
    //  here we are calling * on 2 , an Integer which does not have the * method defined for a Rational
    // however the compiler will search in scope for a method that can satisfy the requirements
    // *  with a  Rational argument is defined for Rational
    // so if  there were present in scope a method which would convert the integer into a rational the call would succeed
    println("2 * r [" + (2 * r) + "]")
  }
}
