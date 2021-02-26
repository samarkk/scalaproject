package variance

object UnderstandingVariance extends App {

  // what is variance
  // type substitution of generics

  // C - Covariant, I - Invariant, N - Contravariant

  // we have a simple class hierarchy
  trait Vehicle

  class Bike extends Vehicle

  class Car extends Vehicle

  class Truck extends Vehicle

  class CTypedVehicle[+T]

  // covariance
  // replacing a general instance of  a vehicle with a more specific type of vehicle
  val covTV: CTypedVehicle[Vehicle] = new CTypedVehicle[Bike]

  // invariant
  class ITypedVehicle[T]

  // try replacing Bike in new ITypedVehicle with anything else or
  // Car in new ITYpedVehicle with something else
  val aITypedVehicle: ITypedVehicle[Bike] = new ITypedVehicle[Bike]
  val invTV: ITypedVehicle[Car] = new ITypedVehicle[Car]

  // contravariance
  class NTypedVehicle[-T]

  // replacing a specific type with a more generalized type
  // replacing a bike with a vehicle which could also be a bike
  val contraTV: NTypedVehicle[Bike] = new NTypedVehicle[Vehicle]
  // the same class can be used as a super class of itself in contra variance
  val contraSameTypeTV: NTypedVehicle[Bike] = new NTypedVehicle[Bike]

  // WP - with parameter

  // #############################################
  // ## class fields are a covariant position ####
  // #############################################
  class CTypedVehicleWP[+T](val vehicle: T)

  // bike is assigned to a more generalized type - Vehicle
  val aCTypedVehicleWP: CTypedVehicleWP[Vehicle] = new CTypedVehicleWP(new Bike)

  /*
  Contravariant type T occurs in covariant position in type T of value vehicle
    class ContraTypedVehicleWP[-T](val vehicle: T)

    if it were possible we could as we see below use a Truck
    to meet the requirement to provide a Vehicle and we could
    be populating a specific requirement with any random type that extends
    ths super class Vehicle
    val acontraTypedVehicle: ContraTypedVehicle[Bike] = new ContraTypedVehicle[Vehicle](new Truck)
   */

  // #############################################################
  // ########## variable types can be only invariant #############
  // #############################################################
  /*
  Covariant type T occurs in contravariant position in type T of value vehicle
  types of vars are contravariant
    class COVVariableTypedVehcileWP[+T](var vehicle: T)
    if it were possible we could do the following below

    val aCOVVariableTypedVehicleWP: Vehicle = new COVVariableTypedVehicle(new Truck)
    aCOVVariableTypedVehicleWP.vehicle = new Car

    replace Truck with Car?
  */

  /*
  Contravariant type T occurs in covariant position in type T of value vehicle
  class ContraVariableTypedVehicleWP[-T](var vehicle: T)
   */
  // variables can be only invariant

  // ####################################################
  // ### method arguments can be only contravariant #####
  // ####################################################
  /*
  trait CovariantTypedVehicle[+T]{
    //Covariant type T occurs in contravariant position in type T of value vehicle
    def add(vehicle: T)
    // method arguments have to be contravariant
  }
  // we could do the following
  val acvt: CovariantTypedVehicle[Vehicle] = new CovariantTypedVehicle[Bike]
  acvt.add(new Truck)
  */

  // ####################################################################################
  // #####     method arguments position is contravariant                        ########
  // #### are we to be constrained to creating only contravariant classes with no fields #
  // ##### solution is to provide method types that are supertypes of class parameters ###
  // #####################################################################################
  //  class AnotherNTypedVehicle[-T] {
  //    def add[S <: T](vehicle: T) = new AnotherCTypedVehicle[S]
  //  }
  //
  //  val anvt: AnotherNTypedVehicle[Bike] = new AnotherNTypedVehicle[Vehicle]
  //
  //  val anvtWithBike = anvt.add(new Bike)
  //val anvtWitSuperBike = anvt.add(new RacingBike)
  //
  //
  //  anvt.add(new SuperBike)

  // ##### - add a super type method parameter  ############
  class AnotherCTypedVehicle[+A] {
    // Covariant type A occurs in contravariant position in type A of value vehicle
    // def addWithoutSuperType(vehicle: A) = vehicle
    def add[B >: A](vehicle: B): AnotherCTypedVehicle[B] = new AnotherCTypedVehicle[B]
  }

  class SuperBike extends Bike

  val acvtList: AnotherCTypedVehicle[Bike] = new AnotherCTypedVehicle[SuperBike]
  // try Bike instead of Vehicle in the line below and run the app
  // we will have an error
  val acvtListWidened: AnotherCTypedVehicle[Vehicle] = acvtList.add(new Vehicle {}) // type widened
  // try replacing Bike with SuperBike in AnotherCTypedVehicle[Bike]
  val actvListMinBike: AnotherCTypedVehicle[Bike] = acvtList.add(new SuperBike)

  class RacingBike extends SuperBike

  // try replacing Bike with SuperBike or RacingBike
  val actvListWithRacingBikeAdded: AnotherCTypedVehicle[Bike] = acvtList.add(new RacingBike) // widest type that will work

  // ##########################################################################
  // #######  Method Return Types - Covariant Position    #####################
  // ##########################################################################

  //    abstract class VehicleShop[-T]{
  //   Contravariant type T occurs in covariant position in type T of value get
  //      def get(isItARacingBike: Boolean): T
  //    }
  /*
  it would be possible to do the following
  since VehicleShop has a contravariant type parameter so
  to return a T we could return a parent class of T

  val aRacingBikeShop = new VehicleShop[RacingBike]{
  def get(isItARacingBike: Boolean): Vehicle = new RacingBike
  }
  Car's parent type is Vehicle and os is RacingBike's so we can
  assign racingBikeShop to a parametrized VehicleShop[Car]

  val aCarShop: VehicleShop[Car] = racingBikeShop

  // and car shop would return racing bikes
  aCarShop.get(true)
   */
  // ###########################################################################
  // ######  meeting the method result type covariant requirement   #########
  // ######## introudce a subtype as a method parameter     ##################
  // #########################################################################

  class VehicleShop[-T] {
    //    def add(av: T): T = av
    def add[S <: T](defaultVehicle: S): S = defaultVehicle
  }

  val aBikeShop: VehicleShop[Bike] = new VehicleShop[Bike]
  // this below will fail because Vehicle >: Bike and not <: Bike
  // we do not get a compiler error but if we run without commenting this we will have an error
//  aBikeShop.add(new Vehicle {})
  aBikeShop.add(new RacingBike)

}
