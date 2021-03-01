//import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
//import bb.expstyla.exp.{StringTerm, StructTerm}
//import infrastructure._
//import org.scalatest.wordspec.AnyWordSpecLike
//
//class SimpleAgentSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {
//
//
//  val mas = MAS()
//  val m = testKit.spawn(mas(), "MAS")
//
//
//  override def beforeAll(): Unit = {
//
//    val prob = testKit.createTestProbe[IMessage]()
//    m ! AgentRequestMessage(
//      Seq(
//        AgentRequest(new asl.greeter_simple().agentBuilder, "greeter", 1),
//      ),
//      prob.ref)
//    Thread.sleep(3000)
//  }
//
//
//  "A simple greeter agent" should {
//    "say greetings in response to a hello when needed" in {
//      val prob = testKit.createTestProbe[IMessage]()
//      mas.yellowPages.getAgent("greeter").get.asInstanceOf[AkkaMessageSource].address() ! GoalMessage(StructTerm("receive",Seq(StringTerm("Hi"))),AkkaMessageSource(prob.ref))
//      assert(prob.receiveMessage().asInstanceOf[GoalMessage].content.toString  equals  "greetings")
//    }
//
//    "return an error if there are no applicable plans" in {
//      val prob = testKit.createTestProbe[IMessage]()
//      mas.yellowPages.getAgent("greeter").get.asInstanceOf[AkkaMessageSource].address() ! GoalMessage(StructTerm("receive",Seq(StringTerm("Howdy"))),AkkaMessageSource(prob.ref))
//      assert(prob.receiveMessage().isInstanceOf[IntentionErrorMessage])
//    }
//
//     "return an error if there are no relevant plans 1" in {
//      val prob = testKit.createTestProbe[IMessage]()
//       mas.yellowPages.getAgent("greeter").get.asInstanceOf[AkkaMessageSource].address() ! GoalMessage(StructTerm("recieve"),AkkaMessageSource(prob.ref))
//      assert(prob.receiveMessage().isInstanceOf[IntentionErrorMessage])
//    }
//
//
//     "return an error if there are no relevant plans 2" in {
//      val prob = testKit.createTestProbe[IMessage]()
//       mas.yellowPages.getAgent("greeter").get.asInstanceOf[AkkaMessageSource].address() ! GoalMessage(StructTerm("bad"),AkkaMessageSource(prob.ref))
//      assert(prob.receiveMessage().isInstanceOf[IntentionErrorMessage])
//    }
//
//  }
//
//
//  override def afterAll(): Unit = testKit.shutdownTestKit()
//}
//
///*
//
//if(plan_1 is applicable) {
//  add plan 1 to app_list
//}
//
//if(plan_2 is applicable) {
//  add plan 2 to app_list
//}
//
//if(list is not empty) {
//  selected = select_plan(list)
//
//  if(plan_1 is selected)
//    execute plan_1
//  else if (plan_2 is selected)
//    execute plan_2
//
//} else {
//
//}
//
//
//
//
// */