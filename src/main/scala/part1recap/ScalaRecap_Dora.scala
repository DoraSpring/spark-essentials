package part1recap

import scala.concurrent.Future
import scala.util.{Failure, Success}

object ScalaRecap_Dora extends App {
  val aBoolean:Boolean=false
  val anIfExpression=if (2>3) "bigger" else "smaller"
  val theUnit=println("Hello,Scala")  //Unit = "no meaningful value" = void in other languages

  def myFunction(x:Int)=42
  //OOP
  class Animal
  class Cat extends Animal
  trait Carnivore {
    def eat(animal:Animal):Unit
  }

  class Crocodile extends Animal with Carnivore{
    override def eat(animal: Animal): Unit = println("Crunch")
  }

  object MySingleton

  //companions
  object Carnivore

  //generics

  trait MyList[A]

  //method notation
  val x=1+2
  val y=1.+(2)

  //Function Programming, working with functions
  // instance of a trait

  val incrementer:Function1[Int,Int]=x=>x+1  //instantiante functions / traits
  val incremented=incrementer(42)

  // map, flatMap, filter
  val processedList=List(1,2,3).map(incrementer)

  // pattern matching
  val unknown:Any = 45
  val ordinal=unknown match {
    case 1 => "first"
    case 2 => "second"
    case _ => "unknown"
  }

  // try-catch
  try {
    throw new NullPointerException()
  }catch {
    case e:NullPointerException => "some returned value"
    case _ =>"something else"
  }

  //Future
  import scala.concurrent.ExecutionContext.Implicits.global
  val aFuture=Future {
    //some expensive computation, runs on another thread
    42
  }

  aFuture.onComplete{
    case Success(meaningOfLife) => println(s"I've found $meaningOfLife")
    case Failure(ex) => println(s"I have failed:$ex")
  }

  //partial functions
  val aPartialFunction:PartialFunction[Int,Int]={
    case 1 => 43
    case 8 => 56
    case _ => 999
  }

  // implicits
  // auto-injection by the compiler
  def methodWithImplicitArgument(implicit x:Int)= x+43
  implicit  val implicitInt=67
  val implicitCall=methodWithImplicitArgument

  //implicit conversions - implicit defs
  case class Person(name:String){
    def great=println(s"Hi, My name is $name")
  }
  implicit def fromStringToPerson(name:String)= Person(name)

  "Bob".great //fromStringToPerson("Bob").great

  //implicit class
  implicit class Dog(name:String) {
    def bark =println("Bark")
  }

  "Lassie".bark

  /*
  - local scope
  - imported scope
  - companion objects of the type involved in the method call
   */
  List(1,2,3).sorted
}
