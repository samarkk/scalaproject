package exceptions
import scala.io.Source
import java.io.FileNotFoundException
import scala.collection.Iterator

object SimpleExceptionExample extends App {
  try {
    Source.fromFile("D:/ufdata/shakespeares.txt").getLines.map(_.trim()).
      filter(_ != "").take(2).foreach(println)
  } catch {
    case ex: FileNotFoundException => println("Not Found")
    case ex: Exception             => println(ex.getMessage)
  }

  val exVal: Iterator[String] = try {
    Source.fromFile("D:/ufdata/shakespeare.txt").getLines.map(_.trim()).
      filter(_ != "")
  } catch {
    case ex: FileNotFoundException => Iterator.empty
    case ex: Exception             => Iterator.empty
  }
  exVal.take(3).map("val from try block " + _).foreach(println)

  val n = scala.util.Random.nextInt(900)
  val half: Int = if (n % 2 == 0) n else throw new RuntimeException("didn't get en even number")
  println(half)
}
