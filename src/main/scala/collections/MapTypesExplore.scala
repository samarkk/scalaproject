package collections

import scala.collection.SortedMap
import scala.collection.immutable.{HashMap, TreeMap}

object MapTypesExplore extends App {
  val monthsMap = SortedMap(
    1 -> "JAN", 2 -> "FEB", 3 -> "MAR", 4 -> "APR", 5 -> "MAY", 6 -> "JUN",
    7 -> "JUL", 8 -> "AUG", 9 -> "SEP", 10 -> "OCT", 11 -> "NOV", 12 -> "DEC"
  )
  monthsMap.range(1, 4).foreach(println)
  val monthsTreeMap = TreeMap(
    1 -> "JAN", 2 -> "FEB", 3 -> "MAR", 4 -> "APR", 5 -> "MAY", 6 -> "JUN",
    7 -> "JUL", 8 -> "AUG", 9 -> "SEP", 10 -> "OCT", 11 -> "NOV", 12 -> "DEC"
  )
  monthsTreeMap.range(4, 9).foreach(println)

  val hmap = HashMap[Int, Int](1 -> 1, 2 -> 4, 10 -> 100, 6 -> 36)
}
