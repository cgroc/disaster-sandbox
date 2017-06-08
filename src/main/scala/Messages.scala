/**
  * Created by chris on 08/06/2017.
  */
case object StartMonitoring
case object StopMonitoring
case object MissedReadingsCheck
case class Reading(sensorName: String, value: Int)

