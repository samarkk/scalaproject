package concur

class BAcc(var bal: Double)

class BAccVol(@volatile var bal: Double)

object InitConcur {

  def seqAndParellelPurchasesRace() {
    def buy(stuff: String, price: Double, fmacc: BAcc): Unit = {
      println(s"bought $stuff for price $price using account balance ${fmacc.bal}")
      println(s"my new balance should be ${fmacc.bal - price}")
      fmacc.bal -= price
    }

    def seqPurchases() {
      val acc = new BAcc(100)
      buy("tomatoes", 40, acc)
      buy("onions", 50, acc)
    }

    println("With no thread ordering should prevail and there should be correct balances")
    seqPurchases()

    println("with multiple thread we have race conditions and incorrect ops")
    def parallelPurchases(): Unit = {
      val acc = new BAcc(100)
      val buy1 = new Thread(() => buy("tomatoes", 40, acc))
      val buy2 = new Thread(() => buy("onions", 50, acc))
      buy1.start()
      buy2.start()
    }

    parallelPurchases()

  }

  def synchronizedDemo() {
    def buySafe(stuff: String, price: Double, fmacc: BAcc): Unit = {
      fmacc.synchronized({
        println(s"bought $stuff for price $price using account balance ${fmacc.bal}")
        println(s"my new balance should be ${fmacc.bal - price}")
        fmacc.bal -= price
      })
    }

    def parallelPurchasesWithSynchroized(): Unit = {
      val acc = new BAcc(100)
      val buy1 = new Thread(() => buySafe("tomatoes", 40, acc))
      val buy2 = new Thread(() => buySafe("onions", 50, acc))
      buy1.start()
      buy2.start()
    }

    parallelPurchasesWithSynchroized()
    for (_ <- 1 to 10)
      parallelPurchasesWithSynchroized()
  }

  def volatileVarDemo() {
    def buySafeVolatile(stuff: String, price: Double, fmacc: BAccVol): Unit = {
      println(s"bought $stuff for price $price using account balance ${fmacc.bal}")
      println(s"my new balance should be ${fmacc.bal - price}")
      fmacc.bal -= price
    }

    def parallelPurchasesWithVolatile(): Unit = {
      val acc = new BAccVol(100)
      val buy1 = new Thread(() => buySafeVolatile("tomatoes", 40, acc))
      val buy2 = new Thread(() => buySafeVolatile("onions", 50, acc))
      buy1.start()
      buy2.start()
    }

    println("safe purchases with volatile variable")
    parallelPurchasesWithVolatile()
    for (_ <- 1 to 3) parallelPurchasesWithVolatile()
  }

  def inceptionThreads(tno: Int, i: Int): Thread = new Thread(() => {
    if (i < tno) {
      val athread = inceptionThreads(tno, i + 1)
      athread.start()
      athread.join()
    }
    println(s"Hello from $i")
  })

  def mthreadsDemo: Int ={
    var x = 0
    val multipleThreads = (1 to 100).map(_ => new Thread(() => x += 1))
    multipleThreads.foreach(_.start)
    x
  }

  def main(args: Array[String]): Unit = {
    seqAndParellelPurchasesRace()
    //    synchronizedDemo()
    //    volatileVarDemo()
    //    inceptionThreads(10, 1).start()
//    (1 to 10).foreach { _ =>
//      println((1 to 100).map(_ => mthreadsDemo).filter(_ != 99).length)
//    }
  }
}
