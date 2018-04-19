package gr

import spray.json.DefaultJsonProtocol.jsonFormat1
import spray.json._
import DefaultJsonProtocol._

trait UseSprayJson {
  implicit protected val colorFormat = jsonFormat1(Entity)
  protected def useSprayJson {
    // implicit val colorFormat = jsonFormat1(Entity) // ここでformat定義は出来ない。下記のような実行時エラー。
    /*
    Caused by: scala.MatchError: [Ljava.lang.String;@7f2dea602430 (of class [Ljava.lang.String;)
	    at java.lang.Throwable.<init>(Throwable.java:250)
	    at java.lang.Exception.<init>(Exception.java:54)
	    at java.lang.RuntimeException.<init>(RuntimeException.java:51)
	    at scala.MatchError.<init>(MatchError.scala:22)
	    at spray.json.ProductFormatsInstances.jsonFormat1(ProductFormatsInstances.scala:23)
	    at spray.json.ProductFormatsInstances.jsonFormat1$(ProductFormatsInstances.scala:22)
	    at spray.json.DefaultJsonProtocol$.jsonFormat1(DefaultJsonProtocol.scala:30)
	    at gr.Bootstrap$.useSprayJson(Bootstrap.scala:22)
	    at gr.Bootstrap$.main(Bootstrap.scala:11)
	    at gr.Bootstrap.main(Bootstrap.scala)
    */
    val source = """{ "payload": "JSON String" }"""
    val deserialized = source.parseJson.convertTo[Entity]
    val serialized = deserialized.toJson.compactPrint
    println(s"Use SprayJSON. source: $source, deserialized: $deserialized, serialized: $serialized")
  }
}
