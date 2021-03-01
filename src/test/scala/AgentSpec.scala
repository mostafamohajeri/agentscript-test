//import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
//import bb.expstyla.exp.{StructTerm, StringTerm}
//import infrastructure._
//import org.scalatest.wordspec.AnyWordSpecLike
//import org.scalatest.Tag
//
//class AgentSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {
//
//  val mas = MAS()
//  val m = testKit.spawn(mas(), "MAS")
//
//  override def beforeAll(): Unit = {
//
//    val prob = testKit.createTestProbe[IMessage]()
//    m ! AgentRequestMessage(
//      Seq(
//        AgentRequest(new asl.greeter().agentBuilder, "greeter", 1),
//      ),
//      prob.ref)
//    Thread.sleep(3000)
//  }
//
//
//  "A greeter agent" should {
//    "say greet(Welcome) in response to a Hello message" in {
//      val prob = testKit.createTestProbe[IMessage]()
//      mas.yellowPages.getAgent("greeter").get.asInstanceOf[AkkaMessageSource].address() ! GoalMessage(StructTerm("accept_guest",Seq(StringTerm("Hello"))),AkkaMessageSource(prob.ref))
//      assert(prob.receiveMessage().asInstanceOf[GoalMessage].content.toString  equals  "greet(Welcome)")
//    }
//
//
//    "respond if there are no applicable plans for a goal" in {
//      val prob = testKit.createTestProbe[IMessage]()
//      mas.yellowPages.getAgent("greeter").get.asInstanceOf[AkkaMessageSource].address() ! GoalMessage(StructTerm("accept_guest",Seq(StringTerm("Hallo"))),AkkaMessageSource(prob.ref))
//      assert(prob.receiveMessage().isInstanceOf[IntentionErrorMessage])
//    }
//
//    "respond with a NoPlanMessage if there are no related plans for a goal" in {
//      val prob = testKit.createTestProbe[IMessage]()
//      mas.yellowPages.getAgent("greeter").get.asInstanceOf[AkkaMessageSource].address() ! GoalMessage(StructTerm("howdy"),AkkaMessageSource(prob.ref))
//      val message = prob.receiveMessage()
//      assert(message.isInstanceOf[IntentionErrorMessage])
//    }
//
//
//
//
//
//
//
//
//
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