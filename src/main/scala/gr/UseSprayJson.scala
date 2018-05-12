package gr

import spray.json._
import DefaultJsonProtocol._

trait UseSprayJson {
  implicit val metadataFormat = jsonFormat2(Metadata)
  implicit val paylaodFormat = jsonFormat1(Payload)
  implicit val entityFormat = jsonFormat2(Entity)
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
    val source =
      """{
        |  "payload": {
        |    "message": "JSON String"
        |  },
        |  "metadata": {
        |    "id": "1672231f-4064-4799-8f3e-e33ecaddb10b",
        |    "timestamp": 1526118398986
        |  }
        |}""".stripMargin
    val deserialized = source.parseJson.convertTo[Entity]
    val serialized = deserialized.toJson.compactPrint
    println(s"Use SprayJSON. source: $source, deserialized: $deserialized, serialized: $serialized")
  }
}
