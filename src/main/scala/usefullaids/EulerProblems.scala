package usefullaids

object EulerProblems extends App {
  import math.{ sqrt, round, ceil }
  def isPrime(n: Int): Boolean = {
    if (n == 1)
      false
    else if (n == 2)
      true
    else (2 to ceil(sqrt(n)).toInt).forall(n % _ != 0)
  }
  (2 to 40).filter(isPrime).foreach(println)

  def getSmallestNumberDivBy1To20: Long = {
    // find the lcm of all prime numbers till 20
    val multipleOfPrimesTill20 = (1 to 20).filter(isPrime).reduce(_ * _)
    println(multipleOfPrimesTill20)
    def isDivby1To20(n: Long): Boolean = (1 to 20).forall(n % _ == 0)
    def streamOfNosDivisibleBy1To20(n: Long): Stream[Long] =
      (n * multipleOfPrimesTill20) #:: streamOfNosDivisibleBy1To20(n + 1)
    streamOfNosDivisibleBy1To20(2).filter(isDivby1To20).take(1).toList(0)
  }
  println(getSmallestNumberDivBy1To20)

  def naturalNos(n: Int): Stream[Int] = n #:: naturalNos(n + 1)
  println("The 1001st prime number is: " + naturalNos(2).filter(isPrime).take(1001)(1000))

  // Euler problem 10
  //  The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
  //Find the sum of all the primes below two million.
  println("The sum of prime numbers below 10 is: " +
    (1 to 10).filter(isPrime).reduce(_ + _))
  println("The number and sum of prime numbers below two million respectively is: " +
    (1 to 2000000).filter(isPrime).map((1, _)).reduce((x, y) => (x._1 + y._1, x._2 + y._2)))

  val min500Divisors = (501 to 1000).map {
    x => (x, 1 to x)
  }.map {
    x => (x._1, x._2.reduce(_ + _))
  }.map {
    x => (x._1, x._2, (1 to math.sqrt(x._2).toInt).map(y => x._2 % y == 0).length)
  }.filter(
    _._3 > 500).reduce(
      (x, y) => if (x._3 < y._3) x else y)

  println("The least triangle number - its count, the number istelf and the divisors more than 500: " +
    min500Divisors)
  /*
     Euler Problem 3

    The prime factors of 13195 are 5, 7, 13 and 29.
    What is the largest prime factor of the number 600851475143 ?
*/
  val bigNo = 600851475143L
  val bigNoSqrt = math.sqrt(bigNo).toInt
  val largestPrimeFactorsOfBigNo = (1 to bigNoSqrt).filter(isPrime).filter(bigNo % _ == 0).max
  println(largestPrimeFactorsOfBigNo)
  val primeStream = bigNoSqrt #:: (bigNoSqrt to 1 by -1).filter(x => isPrime(x) && bigNo % x == 0).toStream
  val largestPrimeFactor = primeStream.take(2)(1)
  println("The biggest prime factor of 600851475143 is " + largestPrimeFactor + " and divieds our no by " +
    bigNo / largestPrimeFactor + " times")
}
