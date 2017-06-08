/**
  * Created by chris on 08/06/2017.
  */
case class SensorMonitorConfig(val repeatReadings: Int, missedReadingsTimeout: Int)
case class ModelThresholds(val level1Threshold: Int, val level2Threshold: Int) // is this overkill YAGNI
