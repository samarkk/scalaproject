package exceptions
import java.net.HttpURLConnection
import java.io.InputStream
import java.io.BufferedReader

object FunctionalTry extends App {
  import scala.util.{ Try, Success, Failure }
  import java.net.URL
  def parseURL(url: String): Try[URL] = Try(new URL(url))
  val malformedURL = "monattp://sparksimple.website"
  //val demoURL = malformedURL
  val demoURL = "https://ocw.mit.edu/ans7870/6/6.006/s08/lecturenotes/files/t8.shakespeare.txt"
  println(parseURL(malformedURL).getOrElse("http://duckduckgo.com"))
  val url = parseURL(demoURL).getOrElse("http://duckduckgo.com")
  //println(url.getClass)
  val conn = url.asInstanceOf[java.net.URL].openConnection()

  /*
   Mapping a Try[A] that is a Success[A] to a Try[B] results in a Success[B].
   If it’s a Failure[A], the resulting Try[B] will be a Failure[B],
   on the other hand, containing the same exception as the Failure[A]

   If you chain multiple map operations, this will result in a nested Try structure,
   which is usually not what you want.
   Consider this method that returns an input stream for a given URL:
   */
  //we can chain operations on Try values and catch any exceptions that might occur
  def inputStreamForURL(url: String): Try[Try[Try[InputStream]]] = parseURL(url).map { u =>
    Try(u.openConnection()).map(connab => Try(connab.getInputStream))
  }

  /*
   The flatMap method on a Try[A] expects to be passed a function that receives an A
   and returns a Try[B]. If our Try[A] instance is already a Failure[A],
   that failure is returned as a Failure[B],
   simply passing along the wrapped exception along the chain. I
   f our Try[A] is a Success[A], flatMap unpacks the A value in it
   and maps it to a Try[B] by passing this value to the mapping function.

  This means that we can basically create a pipeline of operations
  that require the values carried over in Success instances
  by chaining an arbitrary number of flatMap calls
   */
  val urlStream: Try[InputStream] = parseURL(demoURL).flatMap {
    u =>
      Try(u.openConnection()).flatMap(conn => Try(conn.getInputStream))
  }
  val strm = urlStream match {
    case istr: Success[InputStream] => istr
    case _                          => null
  }
  scala.io.Source.fromInputStream(urlStream.get).getLines.foreach(println)
}
