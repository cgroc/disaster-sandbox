import akka.actor.{ActorSystem, PoisonPill}

/**
  * Created by chris on 08/06/2017.
  */
object DisasterPredictorApp {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("DisasterPredictorApp")

    val modelConfigs = Map( "sensor1" -> ModelThresholds(10,20),
                            "sensor2" -> ModelThresholds(30, 40),
                            "sensor3" -> ModelThresholds(5, 15),
                            "sensor4" -> ModelThresholds(20, 25))


    val monitorConfigs = Map( "sensor3" -> SensorMonitorConfig(repeatReadings = 3, missedReadingsTimeout = 5),
                              "sensor1" -> SensorMonitorConfig(repeatReadings = 3, missedReadingsTimeout = 10))

        val monitorActors = monitorConfigs.map(n => (n._1, system.actorOf(SensorMonitorActor.props(n._2))))

    println(monitorActors)

    val disasterPredictor = system.actorOf(DisasterPredictorActor.props(modelConfigs, monitorActors), "DisasterPredictorActor")


    println("--LET'S GO!--")
    disasterPredictor ! Reading("sensor1", 12)
    disasterPredictor ! Reading("sensor2", 12)
    disasterPredictor ! Reading("sensor3", 12)
    disasterPredictor ! Reading("sensor4", 12)
    Thread.sleep(10000)
    disasterPredictor ! Reading("sensor1", 8)
    disasterPredictor ! Reading("sensor3", 5)
    Thread.sleep(5000)
    disasterPredictor ! Reading("sensor3", 6)
    disasterPredictor ! Reading("sensor3", 6)
    disasterPredictor ! Reading("sensor1", 12)
    disasterPredictor ! Reading("sensor3", 6)
    Thread.sleep(2000)
    system.stop(disasterPredictor)
    system.shutdown()
  }
}
