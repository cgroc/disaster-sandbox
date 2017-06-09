import java.nio.file.{Files, Path, Paths}

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import scala.collection.mutable

/**
  * Created by chris on 08/06/2017.
  */
object MonitorUtils {

  val mapper = new ObjectMapper(new YAMLFactory)
  mapper.registerModule(DefaultScalaModule)

//  def getMonitorConfigs: Map[String, SensorMonitorConfig] = {
//    val path = Paths.get("monitors")
//  }

  def parseConfigFile(path: Path): SensorMonitorConfig = {
    mapper.readValue(path.toFile, classOf[SensorMonitorConfig])
  }

  def loadMonitorConfigs(): Map[String, SensorMonitorConfig] = {
    val path = Paths.get("monitors")
    println(path)
    val folderStream = Files.newDirectoryStream(path).iterator()
    println(folderStream)
    val map = mutable.Map[String, SensorMonitorConfig]()
    while (folderStream.hasNext) {
      val configFile = folderStream.next()
      println(configFile)
      if (configFile.toString.endsWith(".monitor.yaml")) {
//        logger.debug(s"Reading configuration from $configFile")
        println("Woop woop")
        val config = parseConfigFile(configFile)
        map(config.sensorName) = SensorMonitorConfig(
          sensorName = config.sensorName,
          monitorRepeatReadings = config.monitorRepeatReadings,
          repeatReadings = config.repeatReadings,
          monitorMissedReadings = config.monitorMissedReadings,
          missedReadingsTimeout = config.missedReadingsTimeout
        )
      }
    }
    map.toMap
  }

}
