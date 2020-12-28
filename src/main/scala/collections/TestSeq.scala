package collections

object TestSeq extends App {
  def processSeq(xs: Seq[Int]): Long = {
    val t1 = System.currentTimeMillis()
    xs.map(x => x + 1).sum
    System.currentTimeMillis() - t1
  }

  var rangeTime = 0L
  var listTime = 0L
  val boundary = 10000000
  for (x <- 1 to 2) {
    rangeTime += processSeq(1 to boundary)
    println("range/vector: " + rangeTime)
    listTime += processSeq((1 to boundary).toList)
    println("list: " + listTime)
  }

  for (x <- 1 to 3) {
    val t1parv = System.currentTimeMillis()
    val parVector = (1 to boundary).par
    parVector.map(_ + 1).sum
    println("Parallel vector time: " + (System.currentTimeMillis() - t1parv))

    val t1par = System.currentTimeMillis()
    val parList = (1 to boundary).toList.par
    parList.map(_ + 1).sum
    println("Parallel list time: " + (System.currentTimeMillis() - t1par))
  }

}
