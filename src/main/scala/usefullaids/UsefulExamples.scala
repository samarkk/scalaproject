package usefullaids

object UsefulExamples extends App {
  // print table of tens with alignment
  def printMultiplesTable() {
    def tenMultiples(a: Int) = for (n <- 1 to 10) yield {
      val prod = a * n
      val pad = " " * (4 - prod.toString().length())
      pad + prod
    }
    def tenMultiplesLine(n: Int) = tenMultiples(n) mkString ""
    val tableToPrint = for (n <- 1 to 10) yield tenMultiplesLine(n)
    println(tableToPrint mkString "\n")
  }
  printMultiplesTable()
  val fibs: Stream[BigInt] = BigInt(0) #:: BigInt(1) #:: fibs.zip(fibs.tail).map(n => n._1 + n._2)
  fibs.take(20).foreach(x => print(x + " "))
}
