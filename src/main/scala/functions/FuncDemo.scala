package functions

object FuncDemo extends App {
  def sumInts(a: Int, b: Int): Int = if (a > b) 0 else a + sumInts(a + 1, b)
  println(sumInts(1, 20))
  def mapRed(f: Int => Int, c: (Int, Int) => Int, acc: Int)(a: Int, b: Int): Int = if (a > b) acc
  else c(f(a), mapRed(f, c, acc)(a + 1, b))
  println(mapRed(x => x * x, (x, y) => x + y, 0)(1, 5))
  val aAnF = (x: Int) => x * x
  print(aAnF(3))
}
