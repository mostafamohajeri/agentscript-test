import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import bb.{BeliefBaseStyla, IBeliefBase, IBeliefBaseFactory}
import bb.expstyla.exp.{GenericTerm, IntTerm, StringTerm, StructTerm}
import infrastructure._
import org.scalatest.wordspec.AnyWordSpecLike
import org.mockito.scalatest.IdiomaticMockito
import org.mockito.IdiomaticMockito.WithExpect
class TokenRingThreadSpec extends ScalaTestWithActorTestKit with IdiomaticMockito with AnyWordSpecLike {

  val mas = MAS()
  val m = testKit.spawn(mas(), "MAS")

  val verifiableBB  = new BeliefBaseStyla()

  val bbf = new IBeliefBaseFactory {
    override def apply(): IBeliefBase[GenericTerm] = verifiableBB
  }

  override def beforeAll(): Unit = {
    val prob = testKit.createTestProbe[IMessage]()
    m ! AgentRequestMessage(
      Seq(
        AgentRequest(new asl.worker(beliefBaseFactory = bbf).agentBuilder, "thread1", 1),
        AgentRequest(new asl.worker().agentBuilder, "thread33", 1),
      ),
      prob.ref)
    Thread.sleep(1000)
  }


  "A worker agent" should {

    "fail if it gets token(N) but has no neighbor" in {
      val prob = testKit.createTestProbe[IMessage]()
      mas.yellowPages.getAgent("thread1").get.asInstanceOf[AkkaMessageSource].address() ! GoalMessage(StructTerm("token",Seq(IntTerm(10))),AkkaMessageSource(prob.ref))
      assert(prob.receiveMessage().isInstanceOf[IntentionErrorMessage])
    }

    "assert the value of its neighbor in response to `init(N)` goal" in {
      val prob = testKit.createTestProbe[IMessage]()
      mas.yellowPages.getAgent("thread1").get.asInstanceOf[AkkaMessageSource].address() ! GoalMessage(StructTerm("init",Seq(IntTerm(10))),AkkaMessageSource(prob.ref))
      assert(prob.receiveMessage().isInstanceOf[IntentionDoneMessage])
      assert(verifiableBB.query(StructTerm("neighbor", Seq(StringTerm("thread2")))).result)
    }

    "try to send a done message to master if receives a token(0)" in {
      val mockedMaster = testKit.createTestProbe[IMessage]()
      val mockedNeighbor = testKit.createTestProbe[IMessage]("thread0")
      val agent = mas.yellowPages.getAgent("thread1").get
      mas.yellowPages.putOne("master",AkkaMessageSource(mockedMaster.ref))
      println(mas.yellowPages.getAll())
      agent.asInstanceOf[AkkaMessageSource].address() ! GoalMessage(StructTerm("token",Seq(IntTerm(0))),AkkaMessageSource(mockedNeighbor.ref))
      mockedMaster.expectMessage(GoalMessage(StructTerm("done"),agent))
    }

    "send a Token(N-1) message to its neighbor if it receives a token(N)" in {
      assert(verifiableBB.query(StructTerm("neighbor", Seq(StringTerm("thread2")))).result)
      val mockedNeighborBefore = testKit.createTestProbe[IMessage]()
      val mockedNeighborNext = testKit.createTestProbe[IMessage]()
      mas.yellowPages.putOne("thread2",AkkaMessageSource(mockedNeighborNext.ref))
      val agent = mas.yellowPages.getAgent("thread1").get
      agent.asInstanceOf[AkkaMessageSource].address() ! GoalMessage(StructTerm("token",Seq(IntTerm(10))),AkkaMessageSource(mockedNeighborBefore.ref))
      mockedNeighborNext.expectMessage(GoalMessage(StructTerm("token",Seq(IntTerm(9))),agent))
    }


    "respond with IntentionErrorMessage if there it is required to init with N < 1" in {
      val prob = testKit.createTestProbe[IMessage]()
      mas.yellowPages.getAgent("thread1").get.asInstanceOf[AkkaMessageSource].address() ! GoalMessage(StructTerm("init",Seq(IntTerm(-10))),AkkaMessageSource(prob.ref))
      assert(prob.receiveMessage().isInstanceOf[IntentionErrorMessage])
    }

    "respond with a NoPlanMessage if there are no related plans for a goal" in {
      val prob = testKit.createTestProbe[IMessage]()
      mas.yellowPages.getAgent("thread1").get.asInstanceOf[AkkaMessageSource].address() ! GoalMessage(StructTerm("howdy"),AkkaMessageSource(prob.ref))
      assert(prob.receiveMessage().isInstanceOf[IntentionErrorMessage])
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