package types

object TypeParameterSorterEx extends App {
  // We can use type parameters with classes
  class Sorter[T] {
    def msort[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {
      def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
        case (x, Nil)             => x
        case (Nil, y)             => y
        case (x :: xs1, y :: ys1) => if (ord.lt(x, y)) x :: merge(xs1, ys) else y :: merge(ys1, xs)
      }
      val n = xs.length / 2
      if (n == 0) xs
      else {
        val (fst, snd) = xs splitAt n
        merge(msort(fst), msort(snd))
      }
    }

    def isort[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {
      def insert(x: T, xs: List[T]): List[T] = if (xs == Nil ||
        ord.lt(x, xs.head)) x :: xs else xs.head :: insert(x, xs.tail)

      if (xs.isEmpty) Nil else {
        insert(xs.head, isort(xs.tail))

      }
    }
  }
  val intSorter = new Sorter
  val randSeed = new scala.util.Random(200)
  val intList = (for (x <- 1 to 10) yield randSeed.nextInt(200)).toList
  println("Usng isort: " + intSorter.isort(intList))
  println("Using msort " + intSorter.msort(intList))
}
