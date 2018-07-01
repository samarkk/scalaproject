package implicits

object ImplicitsExample extends App {
  class PreferredPrompt(val preference: String)
  class PreferredDrink(val preference: String)
  object Greeter {
    def greet(name: String)(implicit prompt: PreferredPrompt, drink: PreferredDrink) {
      println("Welcome " + name + ". The  system is ready")
      println("Why not enjoy  a cup of " + drink.preference + " while the system finishes initial checks")
      println(prompt.preference)
    }
  }
  object SamarPrefs {
    implicit val prompt = new PreferredPrompt("Hello")
    implicit val drink = new PreferredDrink("Tea")
  }
  // error- not find implicit value for parameter prompt: PreferredPrompt
  //   Greeter.greet("Samar")
  // explicits can be used for implicits
  println("Using explicits")
  Greeter.greet("Samar")(new PreferredPrompt("Explicit Hi"), new PreferredDrink("Tea"))
  import SamarPrefs._
  Greeter.greet("Samar")
}
