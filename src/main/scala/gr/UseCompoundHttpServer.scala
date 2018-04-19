package gr

import java.net.InetAddress
import java.util.Locale
import java.util.concurrent.TimeUnit

import org.apache.http.{ ExceptionLogger, HttpRequest, HttpResponse, HttpStatus }
import org.apache.http.impl.nio.bootstrap.ServerBootstrap
import org.apache.http.impl.nio.reactor.IOReactorConfig
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import org.apache.http.entity.ContentType
import org.apache.http.nio.entity.NStringEntity
import org.apache.http.nio.protocol.{ BasicAsyncRequestConsumer, BasicAsyncResponseProducer, HttpAsyncExchange, HttpAsyncRequestConsumer, HttpAsyncRequestHandler }
import org.apache.http.protocol.HttpContext

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{ Failure, Success }

trait UseCompoundHttpServer {
  self: UseSprayJson with UseTypesafeConfig =>

  private def route: Route = path("/")(complete("hi")) ~ complete("other")

  protected def useCompoundHttpServer: Unit = {

    val reactorConfig = IOReactorConfig.custom
      .setSelectInterval(10)
      .setSoReuseAddress(true)
      .setSoKeepAlive(true)
      .setTcpNoDelay(true)
      .build
    val server = ServerBootstrap.bootstrap
      .setLocalAddress(InetAddress.getByName("::"))
      .setListenerPort(config.getInt("gr.server.port"))
      .setServerInfo("Compound-Http")
      .setIOReactorConfig(reactorConfig)
      .setExceptionLogger(ExceptionLogger.STD_ERR)
      .registerHandler("*", new CompoundHandler(route)).create
    server.start()
    println(s"Use UseCompoundHttpServer. server: ${server.getEndpoint.getAddress.toString}")
    //    server.shutdown(5, TimeUnit.SECONDS)
  }
}

class CompoundHandler(route: Route) extends HttpAsyncRequestHandler[HttpRequest] {

  override def processRequest(httpRequest: HttpRequest, httpContext: HttpContext): HttpAsyncRequestConsumer[HttpRequest] = {
    new BasicAsyncRequestConsumer
  }

  override def handle(req: HttpRequest, ex: HttpAsyncExchange, ctx: HttpContext): Unit = {
    handleInternal(req, ex, ctx).onComplete {
      case Success(response) => ex.submitResponse(new BasicAsyncResponseProducer(response))
      case Failure(t)        => t.printStackTrace()
    }
  }
  private def handleInternal(request: HttpRequest, ex: HttpAsyncExchange, context: HttpContext): Future[HttpResponse] = {
    Future {
      val method = request.getRequestLine.getMethod.toUpperCase(Locale.ENGLISH)
      val response = ex.getResponse
      response.setStatusCode(HttpStatus.SC_OK)
      val entity = new NStringEntity("hi", ContentType.create("text/html", "UTF-8"))
      response.setEntity(entity)
      response
    }
  }
}