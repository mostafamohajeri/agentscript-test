import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import bb.expstyla.exp.{StructTerm, StringTerm}
import infrastructure._
import org.scalatest.wordspec.AnyWordSpecLike

class AgentSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {

  override def beforeAll(): Unit = {
    val mas = testKit.spawn(MAS(), "MAS")
    val prob = testKit.createTestProbe[IMessage]()
    mas ! AgentRequestMessage(
      Seq(
        AgentRequest(asl.greeter.Agent, "greeter", 1),
      ),
      prob.ref)
    Thread.sleep(3000)
  }


  "A greeter agent" should {
    "say greetings(Sir) in response to a hello when needed" in {
      val prob = testKit.createTestProbe[IMessage]()
      YellowPages.agents("greeter") ! GoalMessage(StructTerm("hello",Seq(StringTerm("Mr. Bob"))),AkkaMessageSource(prob.ref))
      assert(prob.receiveMessage().asInstanceOf[GoalMessage].content.toString  equals  "greetings(Sir)")
    }

    "say greetings(Madam) in response to a hello when needed" in {
      val prob = testKit.createTestProbe[IMessage]()
      YellowPages.agents("greeter") ! GoalMessage(StructTerm("hello",Seq(StringTerm("Ms. Alice"))),AkkaMessageSource(prob.ref))
      assert(prob.receiveMessage().asInstanceOf[GoalMessage].content.toString  equals  "greetings(Madam)")
    }

    "respond if there are no applicable plans for a goal" in {
      val prob = testKit.createTestProbe[IMessage]()
      YellowPages.agents("greeter") ! GoalMessage(StructTerm("hello",Seq(StringTerm("Charlie"))),AkkaMessageSource(prob.ref))
      assert(prob.receiveMessage().isInstanceOf[IntentionErrorMessage])
    }

    "respond if there are no related plans for a goal" in {
      val prob = testKit.createTestProbe[IMessage]()
      YellowPages.agents("greeter") ! GoalMessage(StructTerm("howdy"),AkkaMessageSource(prob.ref))
      assert(prob.receiveMessage().isInstanceOf[IntentionErrorMessage])
    }


    "say greetings in response to a hi" in {
      val prob = testKit.createTestProbe[IMessage]()
      YellowPages.agents("greeter") ! GoalMessage(StructTerm("hi"),AkkaMessageSource(prob.ref))
      assert(prob.receiveMessage().asInstanceOf[GoalMessage].content.toString  equals  "greetings")
    }

  }

  
  override def afterAll(): Unit = testKit.shutdownTestKit()
}