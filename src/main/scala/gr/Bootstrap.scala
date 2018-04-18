package gr

import com.typesafe.config.ConfigFactory

object Bootstrap {
  val config = ConfigFactory.load
  def main(args: Array[String]): Unit = {
    println(s"hello $config")
  }
}
