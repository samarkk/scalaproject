package strreams

object StreamsExample extends App {
  def from(n: Int): Stream[Int] = n #:: from(n + 1)
  def isPrime(n: Int) = (2 to (scala.math.sqrt(n).toInt + 1)) forall (x => n % x != 0)
  from(2) take (200) filter (isPrime) foreach (println)
}
