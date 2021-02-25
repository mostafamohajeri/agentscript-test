import bb.expstyla.exp.{StringTerm, StructTerm}

import collection.mutable.Stack
import org.scalatest._
import flatspec._
import matchers._

class TestyTest extends AnyFlatSpec with should.Matchers {

  "A Test" should "be covered" in {
    val test = new Test()

    val m = test.create_goal_message("hello", "m")

    m should not be Option.empty

  }


  "A Test" should "be covered3" in {
    val test = new Test()
    val m = test.create_goal_message("heeelo","n")

    m should not be Option.empty


  }


}