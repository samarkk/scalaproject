package classes

// we can define parametric fields by prefixing class parameters with val or var
// in such a case the parameter is both a parameter and a class field
class StorageDevice(val manufacturer: String, val storage: Double) {
  override def toString = "The brand of hdd is " + manufacturer + " and storage " +
    " provided in GB is " + storage
  def fileHandlingCapacity = storage * 10000
}
class CPU(val processorType: String, val processingSpeed: Double) {
  override def toString = "The cpu is of type " + processorType + " and has processing speed of " +
    processingSpeed
  def instructionsPerSecond = processingSpeed * 100000
}
class RAM(val typeOfRam: String, val capacity: Double) {
  override def toString = "Type of ram " + typeOfRam + " and capacity is " + capacity
  def fileHandlingCapacity = capacity * 1000000 / 300
}
/// we compose a Computer out of ram, cpu and storage
class Computer(val ram: RAM, val cpu: CPU, val storage: StorageDevice) {
  override def toString = "This computer has " + ram.toString + "\n" + cpu.toString +
    "\n" + storage.toString
}
// we specialize the computer by adding on a graphics processor
// we have to in the class definition override and include the Computer parameters and extend Computer giving those arguments
class GraphicsComputer(val graphicsProcessor: String,
                       override val ram: RAM, override val cpu: CPU,
                       override val storage: StorageDevice)
    extends Computer(ram, cpu, storage) {
  override def toString = "This is a graphics computer with a powerful graphics processor from " +
    graphicsProcessor + "\n" + super.toString
}
object TestObj {
  def main(args: Array[String]) {
    val ram = new RAM("DDR4", 4)
    //    println(ram.toString)
    //    println("No of files a device with this type of ram can handle " +
    //      ram.fileHandlingCapacity.toInt)
    val cpu = new CPU("X86", 2.54)
    val sdev = new StorageDevice("Transcend", 1500)
    val aComputer = new Computer(ram, cpu, sdev)
    //    println(aComputer)
    val aGC = new GraphicsComputer("NVIDIA", ram, cpu, sdev)
    println(aGC)
  }
}
