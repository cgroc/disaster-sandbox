import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, Props}
import scala.concurrent.duration._

/**
  * Created by chris on 08/06/2017.
  */
class SensorMonitorActor(sensorMonitorConfig: SensorMonitorConfig) extends Actor with ActorLogging {

  import context.dispatcher
  val ticker = context.system.scheduler.schedule(0 milliseconds, 2000 milliseconds, self, MissedReadingsCheck)

  var lastSeenTime: Long = System.currentTimeMillis
  var lastSeen: Int = Int.MinValue // must be a better way to do this?
  var repeats: Int = 1

  override def receive: Receive = {
    case Reading(sensorName: String, value: Int) => {
      log.info(s"SensorMonitor saw $sensorName, $value")
      lastSeenTime = System.currentTimeMillis
      if(lastSeen == value) repeats += 1 else lastSeen = value
      if(repeats >= sensorMonitorConfig.repeatReadings) log.info(s"JAMMED SENSOR: $sensorName")
    }

    case MissedReadingsCheck => {
      if((System.currentTimeMillis - lastSeenTime) > (sensorMonitorConfig.missedReadingsTimeout * 1000)) {
        log.info("Missing reading!")
      }
      else {
        log.info("Checked for missing readings, all fine")
      }
    }
  }

}

object SensorMonitorActor {
  def props(sensorMonitorConfig: SensorMonitorConfig) = Props(classOf[SensorMonitorActor], sensorMonitorConfig)
}