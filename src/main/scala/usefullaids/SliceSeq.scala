package usefullaids

object SliceSeq extends App {
  class SeqWithSlicing[A](val aseq: Seq[A]) extends Seq[A] {
    def sliceRange(fm: Int, trgt: Int, step: Int): Seq[A] =
      //  (((fm - 1) to trgt).filter(x => x % step == 0)).map(x => aseq(x))
      (0 to (trgt - fm - 1)).filter(_ % step == 0).map(_ + fm).map(x => aseq(x))
    def apply(idx: Int): A = aseq(idx)
    def length: Int = aseq.length
    def iterator: Iterator[A] = aseq.iterator
  }
  val aseq_w_slicing = new SeqWithSlicing(0 to 100)
  println(aseq_w_slicing.sliceRange(0, 23, 3))

  val strList = new SeqWithSlicing(List("sachin", "roger", "virat", "diane", "emma", "brad", "ram",
    "rohan", "sohan", "ramanujan", "sarojini", "anne"))

  println(strList.sliceRange(3, 10, 3))
}
