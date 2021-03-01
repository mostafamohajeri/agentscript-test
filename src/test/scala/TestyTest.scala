import bb.expstyla.exp.{StringTerm, StructTerm}

import collection.mutable.Stack
import org.scalatest._
import flatspec._
import infrastructure.IntentionalAgentFactory
import matchers._
import std.{AgentCommunicationLayer, DefaultCommunications}
import java.lang.reflect.Constructor

import bb.{IBeliefBaseFactory, StylaBeliefBaseFactory}

import scala.reflect.internal.util.ScalaClassLoader
import scala.reflect.runtime.{universe => ru}

class TestyTest extends AnyFlatSpec with should.Matchers {

  "A class" should "loaded" in {

    val a : Class[_] = getClass.getClassLoader.loadClass("asl.greeter2")

    val ctor = a.getConstructor(classOf[AgentCommunicationLayer],classOf[IBeliefBaseFactory])

    val factory  = ctor.newInstance(new DefaultCommunications, new StylaBeliefBaseFactory)

    println(factory.asInstanceOf[IntentionalAgentFactory].agentBuilder)
//    println(builder)
  }


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