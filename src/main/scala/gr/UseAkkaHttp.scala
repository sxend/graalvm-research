package gr

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

trait UseAkkaHttp {
  //  implicit val system = ActorSystem("gr-system")
  //  implicit val materializer = ActorMaterializer()
  //  implicit val executionContext = system.dispatcher

  //  private val http = Http() // コンパイル時にコケる。
  /*
    error: Error encountered while parsing akka.actor.dungeon.Dispatch.swapMailbox(akka.dispatch.Mailbox)
      Parsing context:
	      parsing akka.actor.dungeon.Dispatch.swapMailbox$(Dispatch.scala:33)
   */
  protected def useAkkaHttp {
    // implicit val system = ActorSystem("gr-system")
    // implicit val materializer = ActorMaterializer()
    // implicit val executionContext = system.dispatcher // ココでは定義出来ない。コンパイル時に下記みたいなぬるぽでこける。
    /*
    Caused by: java.lang.NullPointerException
	    at com.oracle.svm.hosted.ameta.AnalysisConstantReflectionProvider.readFieldValue(AnalysisConstantReflectionProvider.java:70)
	    at com.oracle.graal.pointsto.ObjectScanner.scanField(ObjectScanner.java:111)
    */
    //    println(s"Use AkkaHttp: system: $system, materializer: $materializer") // そも、ActorSystemの初期化でコンパイル時にコケる
    //    def route = complete("hello akka-http")

    // val bindingFuture = Http().bindAndHandleAsync(Route.asyncHandler(route), config.getString("gr.server.host"), config.getInt("gr.server.port"))
    // コンパイル時にコケる。
    /*
      error: Error encountered while parsing akka.actor.dungeon.Children.swapChildrenRefs(akka.actor.dungeon.ChildrenContainer, akka.actor.dungeon.ChildrenContainer)
        Parsing context:
	      parsing akka.actor.dungeon.Children.reserveChild(Children.scala:133)
    */
  }
}
