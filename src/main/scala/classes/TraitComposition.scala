package classes

object TraitComposition extends App {
  val ramanAddress = Address("Mirchi Street", "Delhi", "Delhi", "110060")
  println(ramanAddress)
  val raman = Person("Raman Shastri", Some(34), Some(Address("110060")))
  println(raman)
  val ceo = Employee("Sam CEO", Some(52), Some(ramanAddress), "CEO", manager = None)
  println(ceo)
  val ramanEmployee = Employee("Raman worker", Some(34), Some(ramanAddress), "worker",
    Some(ceo))
  println("ramanEmployee: " + ramanEmployee)
}

case class Address(street: String, city: String, state: String, zip: String)

object Address {
  def apply(zip: String): Address = Address(
    "[unknown]", Address.zipToCity(zip), Address.zipToState(zip), zip)
  def zipToCity(zip: String) = "unknown"
  def zipToState(zip: String) = "Delhi"
}

trait PersonState {
  val name: String
  val age: Option[Int]
  val address: Option[Address]
}

case class Person(
  name: String,
  age:  Option[Int], address: Option[Address]) extends PersonState

trait EmployeeState {
  val title: String
  val manager: Option[Employee]
}
case class Employee(
  name: String,
  age:  Option[Int], address: Option[Address],
  title: String, manager: Option[Employee] = None)
  extends PersonState with EmployeeState
