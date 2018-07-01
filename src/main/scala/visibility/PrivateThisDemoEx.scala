package visibility

object PrivateThisDemoEx extends App {
  class TV {
    private val sockets = 4
    def printSockets() {
      println(this.sockets)
    }
    //    val atv = new TV

  }
  object TV {
    val aTV = new TV
    // if we put a [this] between private and val sockets = 4 the line below will not compile
    val aTVSockets = aTV.sockets
  }
  val aTV = new TV
  aTV.printSockets()

}
