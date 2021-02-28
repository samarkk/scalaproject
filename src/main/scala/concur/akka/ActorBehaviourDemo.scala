package adv.concur.akka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.util.Random

object BankAccount {

  case class Deposit(amount: Int)

  case class Withdraw(amount: Int)

  case object Statement

  case class TransactionSuccess(message: String)

  case class TransactionFailure(reason: String)

}

class BankAccount extends Actor {

  import BankAccount._

  var funds = 0

  override def receive: Receive = {
    case Deposit(amount) => {
      if (amount < 0) {
        sender() ! TransactionFailure(s"[$sender:] invalid deposit amount")
      } else {
        funds += amount
        sender() ! TransactionSuccess(s"[$sender:] successfully deposited $amount and new balance is $funds")
      }
    }
    case Withdraw(amount) => {
      if (amount < 0) {
        sender() ! TransactionFailure(s"[$sender:] invalid deposit amount")
      } else if (amount > funds) {
        sender() ! TransactionFailure(s"[$sender:] insufficient funds")
      } else {
        funds -= amount
        sender() ! TransactionSuccess(s"[$sender:] account debited for $amount and your new balance is $funds")
      }
    }
    case Statement => sender() ! TransactionSuccess(s"[$sender:] Your balance is $funds")
  }
}

object Person {

  case class ManageFinances(account: ActorRef)

  case class RandomFinances(account: ActorRef)

}

class Person extends Actor {

  import Person._
  import BankAccount._

  override def receive: Receive = {
    case ManageFinances(account) =>

      account ! Deposit(-500000)
      account ! Deposit(500000)
      account ! Withdraw(1000000)
      account ! Withdraw(100000)
      account ! Statement

    case RandomFinances(account) => {
      val transList = List(Deposit, Withdraw, Statement)
      val amounts = (100 to 1000 by 100)
      (1 to 5).foreach {
        x =>
          val trans = transList(new Random().nextInt(transList.length))
          trans match {
            case Statement => account ! Statement
            case Deposit => account ! Deposit(amounts(new Random().nextInt(amounts.length)))
            case Withdraw => account ! Withdraw(amounts(new Random().nextInt(amounts.length)))
          }
      }
    }
    case TransactionSuccess(message: String) => println(message)
    case TransactionFailure(message: String) => println(message)
  }
}

object ActorBehaviourDemo extends App {

  import Person._
  import BankAccount._

  val system = ActorSystem("BTS")
  val person = system.actorOf(Props[Person], "RichGuy")
  val account = system.actorOf(Props[BankAccount], "account")

  person ! ManageFinances(account)

  val richGuys = for (x <- 1 to 10) yield system.actorOf(Props[Person], s"RichGuy$x")
  val accountsForRichGuys = for (x <- 1 to 10) yield system.actorOf(Props[BankAccount], s"BankAccount$x")
  val transList = List(Deposit, Withdraw, Statement)
  val amounts = (100 to 1000 by 100)
  val richGuysAndAcounts = richGuys.zip(accountsForRichGuys)
  richGuysAndAcounts.foreach {
    rg => {
      rg._1 ! RandomFinances(rg._2)
    }
  }
  system.terminate()
}
