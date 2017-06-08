import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, ActorRef, Props}

/**
  * Created by chris on 08/06/2017.
  */
class DisasterPredictorActor(val modelConfig: Map[String, ModelThresholds], val sensorMonitors: Map[String, ActorRef]) extends Actor with ActorLogging {

  override def receive: Receive = {
    case Reading(sensorName: String, value: Int) => {
      val modelconf = modelConfig.get(sensorName)
      if(modelconf.nonEmpty) {
        if(value > modelconf.get.level2Threshold) log.info(s"Level 2 alert for $sensorName, $value")
        else if(value > modelconf.get.level1Threshold) log.info(s"Level 1 alert for $sensorName, $value")
        else log.info(s"No anomaly for $sensorName, $value")
      }
      else {
        log.info(s"No model found for $sensorName, $value")
      }
      sensorMonitors.get(sensorName).map(monitor => monitor ! Reading(sensorName, value))
    }
  }
}

object DisasterPredictorActor {
  def props(modelConfig: Map[String, ModelThresholds], sensorMonitors: Map[String, ActorRef]) = Props(classOf[DisasterPredictorActor], modelConfig, sensorMonitors)
}
