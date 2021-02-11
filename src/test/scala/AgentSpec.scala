import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import bb.expstyla.exp.StructTerm
import infrastructure._
import org.scalatest.wordspec.AnyWordSpecLike

class AgentSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {

  override def beforeAll(): Unit = {
    val mas = testKit.spawn(MAS(), "MAS")
    mas ! AgentRequestMessage(
      Seq(
        AgentRequest(asl.greeter.Agent, "greeter", 1),
      ))
    Thread.sleep(3000)
  }


  "A greeter agent" should {
    "say greetings in response to a hello" in {
      val prob = testKit.createTestProbe[IMessage]()
      YellowPages.agents("greeter") ! GoalMessage(StructTerm("hello"),prob.ref)
      assert(prob.receiveMessage().asInstanceOf[GoalMessage].p_belief.toString  equals  "greetings")
    }
  }

  override def afterAll(): Unit = testKit.shutdownTestKit()
}