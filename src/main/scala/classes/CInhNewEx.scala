package classes

// a common architectural approach that Scala promotes is to use FP for programming
// in the small and OOP for programming in the large

// good object-oriented design favors composition over inheritance

// avoid adding state in inheritance hierarchies

/*
  Recommended rules for inheritance
1. An abstract base class or trait is subclassed one level by concrete classes, including
case classes.
2. Concrete classes are never subclassed, except for two cases:
a. Classes that mix in other behaviors defined in traits. Ideally,
those behaviors should be orthogonal, i.e., not overlapping.
b. Test-only versions to promote automated unit testing.
3. When subclassing seems like the right approach, consider partitioning behaviors
into traits and mix in those traits instead.
4. Never split logical state across parent-child type boundaries.
 */
trait TStorageDevice {
  val manufacturer: String
  val storage: Double

  def fileHandlingCapacity(fileSize: Double): Double = storage * 1024 * 1024 / (fileSize * 1.2)
}

trait TCPU {
  val processorType: String
  val processingSpeed: Double
}

trait TRAM {
  val typeOfRam: String
  val capacity: Double
}

trait TGPC {
  val graphicsManufacturer: String
  val graphicsRAM: Double
}

case class PC(manufacturer: String, storage: Double, processorType: String, processingSpeed: Double,
              typeOfRam: String, capacity: Double)
  extends TStorageDevice with TCPU with TRAM

object PC {
  def apply(storage: Double, processorType: String, processingSpeed: Double,
            typeOfRam: String, capacity: Double): PC =
    PC("HP", storage, processorType, processingSpeed, typeOfRam, capacity)

  def apply(processorType: String, processingSpeed: Double,
            typeOfRam: String, capacity: Double): PC =
    PC("HP", 256.0, processorType, processingSpeed, typeOfRam, capacity)
}

case class GPC(manufacturer: String, storage: Double, processorType: String, processingSpeed: Double,
               typeOfRam: String, capacity: Double, graphicsManufacturer: String, graphicsRAM: Double)
  extends TStorageDevice with TCPU with TRAM with TGPC

object NClasses extends App {
  val aPc = PC("Dell", 256.0, "Xeon", 3.24, "DDR4", 16.0)
  println(aPc)
  println(s"Number of 100 MB files aPc can handle ${aPc.fileHandlingCapacity(100)}")
  val aGPC = GPC("Dell", 256.0, "Xeon", 3.24, "DDR4", 16.0, "NVIDIA", 8.0)
  println(aGPC)

  val anotherPC = PC(storage = 220, typeOfRam = "DDR4", manufacturer = "HP", capacity = 16.0,
    processingSpeed = 3.34, processorType = "Intel V 9")
  print(anotherPC)
}