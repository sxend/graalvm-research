package gr

import java.net.InetAddress
import java.util.Locale
import java.util.concurrent.TimeUnit

import org.apache.http.{ ExceptionLogger, HttpRequest, HttpResponse, HttpStatus }
import org.apache.http.impl.nio.bootstrap.ServerBootstrap
import org.apache.http.impl.nio.reactor.IOReactorConfig
import org.apache.http.entity.ContentType
import org.apache.http.message.BasicHeader
import org.apache.http.nio.entity.{ NByteArrayEntity, NStringEntity }
import org.apache.http.nio.protocol._
import org.apache.http.protocol.{ HttpContext, HttpRequestHandler, UriPatternMatcher }

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{ ExecutionContextExecutor, Future }
import scala.util.{ Failure, Success }

trait UseCompoundHttpServer {
  self: UseSprayJson with UseTypesafeConfig =>

  protected def useCompoundHttpServer: Unit = {

    val reactorConfig = IOReactorConfig.custom
      .setSelectInterval(10)
      .setSoReuseAddress(true)
      .setSoKeepAlive(true)
      .setTcpNoDelay(true)
      .build

    val mapper = {
      val mapper = new UriHttpAsyncRequestHandlerMapper()
      val route = Map(
        "*" -> new HttpRequestHandler() {
          override def handle(httpRequest: HttpRequest, httpResponse: HttpResponse, httpContext: HttpContext): Unit = {
            httpResponse.setEntity(new NStringEntity("hi"))
          }
        }
      )
      route.foreach {
        case (p, h) => mapper.register(p, new BasicAsyncRequestHandler(h))
      }
      mapper
    }
    val server = ServerBootstrap.bootstrap
      .setLocalAddress(InetAddress.getByName("::"))
      .setListenerPort(config.getInt("gr.server.port"))
      .setServerInfo("Compound-Http")
      .setIOReactorConfig(reactorConfig)
      .setExceptionLogger(ExceptionLogger.STD_ERR)
      .setHandlerMapper(mapper)
      .create
    server.start()
    println(s"Use UseCompoundHttpServer. server: ${server.getEndpoint.getAddress.toString}")
    //    server.shutdown(5, TimeUnit.SECONDS)
  }
}