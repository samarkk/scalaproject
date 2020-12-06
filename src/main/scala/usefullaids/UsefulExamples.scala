package usefullaids

object UsefulExamples {
  def main(args: Array[String]): Unit = {
    //    printMultiplesTable()
    //    fibs.take(50).foreach(println)
    // print sum of numbers
    println(sumSeries(Seq(2, 3, 4), (x: Int) => x))
    // print sum of squares of doubles
    println(msumSeries(Seq(2, 3.4, 5), (x: Double) => x * x))
  }

  // print table of tens with alignment
  def printMultiplesTable() {
    def tenMultiples(a: Int) = for (n <- 1 to 10) yield {
      val prod = a * n
      val pad = " " * (4 - prod.toString.length())
      pad + prod
    }

    def tenMultiplesLine(n: Int) = tenMultiples(n) mkString ""

    val tableToPrint = for (n <- 1 to 10) yield tenMultiplesLine(n)
    println(tableToPrint mkString "\n")
  }

  // generate fibonacci numbers
  val fibs: Stream[BigInt] = BigInt(0) #:: BigInt(1) #:: fibs.zip(fibs.tail).map(x => x._1 + x._2)

  // use numeric trait to implement generic ops

  def ncadd[T: Numeric](a: T, b: T)(implicit nv: Numeric[T]): T = nv.plus(a, b)

  def sumSeries[T: Numeric](seq: Seq[T], f: T => T): T = seq.map(f).reduce(ncadd(_, _))

  def msumSeries[T: Numeric](seq: Seq[T], f: T => T): T = {
    def cadd(a: T, b: T) = implicitly[Numeric[T]].plus(a, b)

    seq.map(f).reduce(cadd(_, _))
  }
}

