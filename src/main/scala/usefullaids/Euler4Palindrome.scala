package usefullaids

/*
 A palindromic number reads the same both ways.
 The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.
  Find the largest palindrome made from the product of two 3-digit numbers.
 */
object Euler4Palindrome extends App {
  def isPalindrome(x: Int) = x.toString == x.toString().reverse
  val tuples = (999 to 1 by -1).flatMap(x => (999 to x by -1).map(y => (x, y)))

  tuples.filter(x => isPalindrome(x._1 * x._2)).map(x => (x._1, x._2, x._1 * x._2)).sortBy(
    -_._3).take(10).foreach(println)
}
