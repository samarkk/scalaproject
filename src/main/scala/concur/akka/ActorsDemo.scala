package concur.akka

import akka.actor.{Actor, ActorPath, ActorRef, ActorRefProvider, ActorSystem, ActorSystemImpl, Extension, ExtensionId, InternalActorRef, Props, Scheduler, Terminated}
import akka.dispatch.{Dispatchers, Mailboxes}
import akka.event.{EventStream, LoggingAdapter}

import java.util.concurrent.CompletionStage
import scala.concurrent.{ExecutionContextExecutor, Future}

object ActorsDemo extends App {
  // ##########################################################################
  // ######### Begin by creating an actor system and actors for that system ###
  // ##########################################################################
  val system = ActorSystem("ActorsDemo")
  val simpleActor = system.actorOf(Props[SimpleActor], "simpleActor")

  // ###########################################################################
  // ###Create actors by extending class Actor and implementing receive ########
  // ###########################################################################
  class SimpleActor extends Actor {
    override def receive: Receive = {
      case "Hi!" => sender() ! "Hello, there"
      // Actors can receive messages of any kind
      case message: String => println(s"[$self]  Received message: $message")
      case number: Int => println(s"[simpleActor]  Got the code $number. What does it mean")
      // messages must be immutable and serializable
      // compile time verification of immutability is not there
      // its a design direction and violating it will lead to problems
      case CustomMessage(contents) => println(s"[simpleActor] received the special message $contents")
      // actor has reference to context - the system, itself as self
      // so below we are dong sefl tell content and this gets dispatched to case message: string
      case SendMessageToYourself(content) => self ! content

      // ram says hi to shyam
      // and we shuld see shyam receiving the message
      case SayHiTo(ref) => ref ! "Hi"

        // if we add an ! then its going to be handled by case "Hi!" at the top
        // and shyam would be replying back to the sender
        // and we shoudl see Hello, there received by Ram

        shyam ! "Hi!"

        // shyam should be saying he received the forwarded message
      case ForwardMessage(contents, someactor) => someactor forward (contents)
    }
  }

  simpleActor ! "hi there, what's going on"
  simpleActor ! 31
  simpleActor ! CustomMessage("whatever")


  case class SendMessageToYourself(content: String)

  simpleActor ! SendMessageToYourself("Reggie calling Reggie - I can live happily with me, myself and me")

  case class SayHiTo(ref: ActorRef)

  val ram = system.actorOf(Props[SimpleActor], "Ram")
  val shyam = system.actorOf(Props[SimpleActor], "Shyam")
  ram ! SayHiTo(shyam)

  case class ForwardMessage(message: String, ref: ActorRef)

  ram ! ForwardMessage("a forwarded message from wheover", shyam)
}
