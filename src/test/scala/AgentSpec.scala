import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import bb.expstyla.exp.{StructTerm, StringTerm}
import infrastructure._
import org.scalatest.wordspec.AnyWordSpecLike

class AgentSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {

  val mas = MAS()
  val m = testKit.spawn(mas(), "MAS")

  override def beforeAll(): Unit = {

    val prob = testKit.createTestProbe[IMessage]()
    m ! AgentRequestMessage(
      Seq(
        AgentRequest(asl.greeter.Agent, "greeter", 1),
      ),
      prob.ref)
    Thread.sleep(3000)
  }


  "A greeter agent" should {
    "say greetings(Sir) in response to a hello when needed" in {
      val prob = testKit.createTestProbe[IMessage]()
      mas.getYellowPages.agents("greeter") ! GoalMessage(StructTerm("hello",Seq(StringTerm("Mr. Bob"))),AkkaMessageSource(prob.ref))
      assert(prob.receiveMessage().asInstanceOf[GoalMessage].content.toString  equals  "greetings(Sir)")
    }

    "say greetings(Madam) in response to a hello when needed" in {
      val prob = testKit.createTestProbe[IMessage]()
      mas.getYellowPages.agents("greeter") ! GoalMessage(StructTerm("hello",Seq(StringTerm("Ms. Alice"))),AkkaMessageSource(prob.ref))
      assert(prob.receiveMessage().asInstanceOf[GoalMessage].content.toString  equals  "greetings(Madam)")
    }

    "say greetings(John) in response to a hello when needed" in {
      val prob = testKit.createTestProbe[IMessage]()
      mas.getYellowPages.agents("greeter") ! GoalMessage(StructTerm("hello",Seq(StringTerm("John"))),AkkaMessageSource(prob.ref))
      assert(prob.receiveMessage().asInstanceOf[GoalMessage].content.toString  equals  "greetings(John)")
    }

    "respond if there are no applicable plans for a goal" in {
      val prob = testKit.createTestProbe[IMessage]()
      mas.getYellowPages.agents("greeter") ! GoalMessage(StructTerm("hello",Seq(StringTerm("Charlie"))),AkkaMessageSource(prob.ref))
      assert(prob.receiveMessage().isInstanceOf[IntentionErrorMessage])
    }

    "respond with a NoPlanMessage if there are no related plans for a goal" in {
      val prob = testKit.createTestProbe[IMessage]()
      mas.getYellowPages.agents("greeter") ! GoalMessage(StructTerm("howdy"),AkkaMessageSource(prob.ref))
      val message = prob.receiveMessage()
      assert(message.isInstanceOf[IntentionErrorMessage])
      assert(message.asInstanceOf[IntentionErrorMessage].cause.isInstanceOf[NoPlanMessage])
    }


    "say greetings in response to a hi" in {
      val prob = testKit.createTestProbe[IMessage]()
      m ! ActorSubscribeMessage("mocked",prob.ref)
      mas.getYellowPages.agents("greeter") ! GoalMessage(StructTerm("hi"),AkkaMessageSource(prob.ref))
      assert(prob.receiveMessage().asInstanceOf[GoalMessage].content.toString  equals  "greetings")
    }



  }

  
  override def afterAll(): Unit = testKit.shutdownTestKit()
}

/*

if(plan_1 is applicable) {
  add plan 1 to app_list
}

if(plan_2 is applicable) {
  add plan 2 to app_list
}

if(list is not empty) {
  selected = select_plan(list)

  if(plan_1 is selected)
    execute plan_1
  else if (plan_2 is selected)
    execute plan_2

} else {

}




 */