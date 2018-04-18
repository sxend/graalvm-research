package gr

import com.typesafe.config.ConfigFactory
import spray.json._
import DefaultJsonProtocol._
import akka.actor.ActorSystem
import gr.Bootstrap.pw
//import akka.http.scaladsl.Http
//import akka.http.scaladsl.model._
//import akka.http.scaladsl.server.Directives._
//import akka.http.scaladsl.server.Route
//import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.stream.ActorMaterializer

//import lol.http._

import scala.io.StdIn

object Bootstrap extends AnyRef //  with SprayJsonSupport with DefaultJsonProtocol
{
   val f = new java.io.File("/tmp/sample.txt")
   val pw = new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.FileWriter(f)))
   pw.append("write:" + System.currentTimeMillis()) // ここはnative-image時にのみ評価される。
   pw.close()
   println("print file.") // ここもnative-image時にのみ評価される。実行時には評価されない。

  def main(args: Array[String]): Unit = {
    useTypesafeConfig
    useSprayJson
    startAkkaHttpServer
    startLolHttpServer
  }
  private val config = ConfigFactory.load

  private def useTypesafeConfig: Unit = {
    // val config = ConfigFactory.load // ここでロードは出来ない。下記のような実行時エラー。
    // Caused by: com.typesafe.config.ConfigException$BugOrBroken: Context class loader is not set for the current thread; if Thread.currentThread().getContextClassLoader() returns null, you must pass a ClassLoader explicitly to ConfigFactory.load

    println(s"Use Typesafe Config. ${config.getConfig("gr")}")
  }
  implicit val colorFormat = jsonFormat1(Entity)
  private def useSprayJson {
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
  implicit val system = ActorSystem("gr-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  //  private val http = Http() // コンパイル時にコケる。
  /*
    error: Error encountered while parsing akka.actor.dungeon.Dispatch.swapMailbox(akka.dispatch.Mailbox)
      Parsing context:
	      parsing akka.actor.dungeon.Dispatch.swapMailbox$(Dispatch.scala:33)
   */
  private def startAkkaHttpServer {
    // implicit val system = ActorSystem("gr-system")
    // implicit val materializer = ActorMaterializer()
    // implicit val executionContext = system.dispatcher // ココでは定義出来ない。コンパイル時に下記みたいなぬるぽでこける。
    /*
    Caused by: java.lang.NullPointerException
	    at com.oracle.svm.hosted.ameta.AnalysisConstantReflectionProvider.readFieldValue(AnalysisConstantReflectionProvider.java:70)
	    at com.oracle.graal.pointsto.ObjectScanner.scanField(ObjectScanner.java:111)
    */

    //    def route = complete(Entity("hello"))

    // val bindingFuture = Http().bindAndHandleAsync(Route.asyncHandler(route), config.getString("gr.server.host"), config.getInt("gr.server.port"))
    // コンパイル時にコケる。
    /*
      error: Error encountered while parsing akka.actor.dungeon.Children.swapChildrenRefs(akka.actor.dungeon.ChildrenContainer, akka.actor.dungeon.ChildrenContainer)
        Parsing context:
	      parsing akka.actor.dungeon.Children.reserveChild(Children.scala:133)
    */
  }
  private def startLolHttpServer: Unit = {
    // Server.listen(config.getInt("gr.server.port")) { req =>
    //   Ok("Hello world!")
    // }
    // lolhttp内部でnettyを初期化する際、リフレクションを使うメソッドを使用している。そこで実行時エラー
  }
}

case class Entity(payload: String)