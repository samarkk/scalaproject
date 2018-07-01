package patternmatching

object PartialFunctionExample extends App {
  def a_partial_function: PartialFunction[Double, Double] = { case d if d != 0 => 1 / d }
  println(a_partial_function(33))
  //  println(a_partial_function(0))
  def apf_isdefat(x: Double, apf: PartialFunction[Double, Double]) = if (apf.isDefinedAt(x)) apf(x) else null
  println(apf_isdefat(0, a_partial_function))
  println(apf_isdefat(20, a_partial_function).isInstanceOf[Double])
  println(if (apf_isdefat(0, a_partial_function) == null) "ok" else "null")
}
