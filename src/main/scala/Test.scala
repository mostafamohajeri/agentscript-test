
import bb.expstyla.exp.StructTerm
import infrastructure.{ExecutionContext, IMessageSource, Parameters, SubGoalMessage}

class Test() {
  def create_goal_message(t  :String, i: String) = {
    if(t equals "hello" ) {
      println(i)
    } else  {
      println(i * 2)
    }
  }


}
