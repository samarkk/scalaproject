
package object packageobject {
  def isPrime(n: Int): Boolean = if (n == 1) false else if (n == 2) true
  else (2 to math.sqrt(n).toInt).forall(y => n % y != 0)

  def sumUsingFunctions(f: Int => Int, a: Int, b: Int) = {
    def loop(x: Int, acc: Int): Int = {
      if (x > b) acc else loop(x + 1, f(x))
    }
    loop(a, 0)
  }
  object EColor extends Enumeration {
    val Red, Green, Blue, Orange = Value
  }
}
