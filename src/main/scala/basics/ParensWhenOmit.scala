package practice.parenomits

object ParensWhenOmit extends App {
  val slist = List("raman", "shaman", "waman", "chaman", "baman", "naman", "gaman")

  // infix - object followed by a method that takes parameters
  // if its a single parameter
  object ArithOps {
    def square(x: Int) = x * x
  }

  println(ArithOps square 10)

  def isquare(x: Int) = x * x
  // isquare 10 will not work because not an object

  // if its a single parameter that requires a function and uses a named function
  def filterLongWords(s: String) = s.length <= 5

  println(slist filter filterLongWords)
  // anonymous functions will not work because function boundaries are not known
  //  slist filter x => x.length <=5
  println(slist filter (x => x.length <= 5))
  println(slist filter (_.length <= 5))

  def mapf(s: String) = s toUpperCase()
  println(slist filter filterLongWords map mapf mkString " ")
}
