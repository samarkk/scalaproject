package concur

// Futures require an execution context
// we provide the line below to provide a global ExecutionContext
// import scala.concurrent.ExecutionContext.Implicits.global

import java.util.concurrent.ForkJoinPool
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Random, Success}
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object FutureDemo {

  def square(x: Int): Int = {
    Thread.sleep(2000)
    x * x
  }

  def simpleFutureDemo(): Unit = {
    val aFuture = Future {
      square(10)
    }
    aFuture onComplete {
      case Success(value) => println(s"The square from the future is $value")
      case Failure(exception) => println(s"Got exception $exception")
    }
  }

  // which exector service, pool
  // https://jessitron.com/2014/01/29/choosing-an-executorservice/

  def futureDemoUsingCreatedEC(): Unit = {
    val myTP = Executors.newFixedThreadPool(2);
    val myEC = ExecutionContext.fromExecutor(myTP)

    val ecFut = Future(10)(myEC)
    ecFut.onComplete {
      case Success(x) => println(s"Future using its own execution context completed with value $x")
      case Failure(ex) => println(s"Future running its own ec failed with execption ${ex.getMessage}")
    }
    myTP.shutdown()
    myTP.awaitTermination(1000, TimeUnit.MILLISECONDS)
  }

  def futureCompositionDemo(): Unit = {
    case class Movie(id: Int, title: String, dexcription: String)
    // we have a map of ids to movies
    val moviesIdMap = Map(
      1 -> "Sholay",
      2 -> "Dangal",
      3 -> "12 Men",
      4 -> "Fight Club",
      5 -> "JFK",
      6 -> "Just Mercy",
      7 -> "Man on Fire",
      8 -> "Crimson  Tide",
      9 -> "The Martian",
      10 -> "Batman Begins"
    )

    // a method to get a random id between 1 and  10
    def getRandomId = {
      val random = new Random()
      1 + random.nextInt(10)
    }

    // a future to get a random id
    def getIdFut = Future {
      getRandomId
    }

    // future composition we  convert Future[Int] to Future[String]
    val futureMapped = getIdFut.map(x => moviesIdMap(x))
    futureMapped.onComplete {
      case Success(movieName) => println(s"mapping the id an into to the string, got movie $movieName")
      case Failure(e) => println(s"")
    }
    // future composition - we flatmap and return a future of string
    val futureFlatMapped = getIdFut.flatMap(x => Future((x, moviesIdMap(x))))
    futureFlatMapped.onComplete {
      case Success((movieid, moviename)) =>
        println(s"The movie name gotten for the id $movieid is $moviename")
      case Failure(e) =>
        println(s"got  exception $e")
    }

    def getMovieNameFuture(id: Int): Future[String] = Future {
      moviesIdMap(id)
    }

    for {
      movieid <- getIdFut
      moviename <- getMovieNameFuture(movieid)
    } {
      println(s"The movie  gotten using the for comprehension for random id $movieid is $moviename")
    }

    def futureForRecoveryDemo(id: Int): Future[String] = Future {
      moviesIdMap(id)
    }

    futureForRecoveryDemo(15).recover {
      case e: Throwable => println("Hey! we do not have a movie with that id but we'd recommend an all time great: GolMaal")
    }
    val futureRecoverWith = futureForRecoveryDemo(15).recoverWith {
      case e: Throwable => futureForRecoveryDemo(10)
    }
    futureRecoverWith onComplete {
      case Success(mname) => println(s"Future with id not in the movie map recovered with movie name $mname")
      case Failure(e) => println("We should have ensured to use an id that was there in the map")
    }
  }

  def main(args: Array[String]): Unit = {
    //    simpleFutureDemo
    //    Thread.sleep(3000)
    futureDemoUsingCreatedEC()
    futureCompositionDemo()
    Thread.sleep(3000)
  }
}
