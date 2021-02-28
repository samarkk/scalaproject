package options

import scala.util.Random

object OptionsExample extends App {
  // options are used in scala instead of null values
  // options can hel us avoid NullPointerexceptions

  // options take a type paramenter
  // and support functional operations and compositions

  // Options have two subclasses Some and None

  // we declare a method that instead of returning null provides an option of None
  def someNoneMethod: Option[String] = None

  // we can chain it with a backup method that will
  // be plugged in to handle value not existing scenarios
  def backupMethod: Option[String] = Some("backup value")

  println(someNoneMethod.orElse(backupMethod))

  // we declare a map here
  // on maps we can call getOrElse to get back options
  val config: Map[String, String] = Map(
    "host" -> "10.173.212.89",
    "port" -> "674"
  )

  // we have a class connection
  class Connection {
    def connect: String = "connected"
  }

  // and the companion object Connection where we
  // declare the apply mehtod

  object Connection {
    val r = new Random()

    def apply(host: String, port: String): Option[Connection] =
      if (r.nextBoolean()) Some(new Connection)
      else None
  }

  // from config we may, may not have a host or a port
  // and from the apply method in connection, we may, may not have a connection

  // we use functional composition to chain all these possibilities together

  config.get("host").
    flatMap(host => config.get("port").
      flatMap(port => Connection(host, port))
      .map(connection => connection.connect))
    .foreach(println)

  // same operations as above but carried out using for comprehensions
  val forConnectionStatus = for {
    host <- config.get("host")
    port <- config.get("port")
    connection <- Connection(host, port)
  } yield connection.connect
  println(s"forConnectionStatus $forConnectionStatus")
}
