package template

import org.specs2.mutable._
import akka.http.scaladsl.testkit.Specs2RouteTest
import com.typesafe.config.ConfigFactory

class HelloSpec extends Specification with Specs2RouteTest {
  "This is a specification for the '/hello' route".p

  private val config = ConfigFactory.load

  "GET /hello" >> {
    "respond hello json" >> {
      Get("/hello") ~> Routes(config) ~> check {
        responseAs[String] must be_==("""{"message":"hello"}""")
      }
    }
  }
}
