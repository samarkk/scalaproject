package pkgobj
import scala.util.Random

object PackageObjectTester extends App {

  (1 to 10).filter(packageobject.isPrime(_)).foreach(println)
  val anInt = scala.util.Random.nextInt(10)
  val perf = anInt match {
    case x if x < 3           => packageobject.EColor.Red
    case x if x >= 3 && x < 5 => packageobject.EColor.Orange
    case x if x >= 5 && x < 8 => packageobject.EColor.Red
    case y if y >= 8          => packageobject.EColor.Blue
  }
  println(perf)
}
