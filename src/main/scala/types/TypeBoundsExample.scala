package types

object TypeBoundsExample extends App {
  class Vehicle {
    override def toString = "A Vehicle"
  }
  class Car extends Vehicle {
    override def toString() = "A car"
  }
  class RacingCar extends Car {
    override def toString() = "A Racing Car"
  }

  //  class CarSeller {
  //    def display[T <: Car](t: T) {
  //      println(t)
  //    }
  //  }
  //  val aVehicle = new Vehicle
  //  val aCar = new Car
  //  val aRacingCar = new RacingCar
  //
  //  val aCarSeller = new CarSeller
  //
  //  aCarSeller.display(aCar)
  //  aCarSeller.display(aRacingCar)
  //  //  aCarSeller.display(aVehicle)

  //  class CarSeller {
  //    def display[T >: Car](t: T) {
  //      println(t)
  //    }
  //  }
  //  val aVehicle = new Vehicle
  //  val aCar = new Car
  //  val aRacingCar = new RacingCar
  //
  //  val aCarSeller = new CarSeller
  //
  //  aCarSeller.display[Car](aCar)
  //  // here we have asked for a super type of Car but since RacingCar inherits finally from Vehicle the method works
  //  aCarSeller.display(aRacingCar)
  //  // when we explicitly put in method type argument RacingCar, the compilation will fail
  //  //      aCarSeller.display[RacingCar](aRacingCar)
  //  aCarSeller.display[Vehicle](aVehicle)

  // View Bounds
  class Person[T <% Ordered[T]](val fname: T, val lname: T) {
    def greater = if (fname > lname) fname else lname
  }

  val p1 = new Person("Raman", "Shastri")
  val p2 = new Person("Chiiman", "Verma")

  println(p1.greater)
  println(p2.greater)

}
