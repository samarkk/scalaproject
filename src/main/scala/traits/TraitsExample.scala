package traits

object TraitsExample extends App {
  // traits are analogous to java interfaces - they can have concrete implemented methods
  // traits are the right place for parking implementations likely to be required by different components
  trait SecReq {
    def secPersRequired(crowd: Int, density: Double) = crowd / 300 * density * 2
  }
  trait WaterReq {
    def qtyWaterReqd(nopeople: Int, durationInHours: Double): Double = nopeople * durationInHours / 0.2
  }
  // traits can get extended by classes
  class MeetingPlace(val npeople: Int, val pdense: Double,
                     val durhr: Double) extends SecReq {
    override def toString = "This meeting place hosts " + npeople + " people , has  an area/people density of  " + pdense +
      " and a session here  lasts for " + durhr + " hours"
    def printSecPersReqd() { println(secPersRequired(npeople, pdense)) }
  }

  val aCinema = new MeetingPlace(300, 1.3, 2.2)
  println(aCinema)
  aCinema printSecPersReqd ()

  // multiple traits can be mixed  into classes
  class Gathering(val openOrCovered: String, override val npeople: Int,
                  override val pdense: Double, override val durhr: Double)
      extends MeetingPlace(npeople, pdense, durhr) with SecReq with WaterReq {
    override def qtyWaterReqd(nopeople: Int, durationInHours: Double): Double =
      openOrCovered match {
        case "OPEN"    => nopeople * durationInHours / 0.1
        case "COVERED" => nopeople * durationInHours / 0.5
      }
  }
  val aRally = new Gathering("OPEN", 10000, 7.8, 1.5)
  println(aRally)
  println(" The rally will  approx need " + aRally.qtyWaterReqd(aRally.npeople, aRally.durhr) +
    " litres of water")

  val aReading = new Gathering("COVERED", 200, 3000 / 200, 0.5)
  println(aReading)
  aReading.printSecPersReqd()
  println(" The reading will  approx need " +
    aReading.qtyWaterReqd(aReading.npeople, aReading.durhr)
    + " litres of water")
}
