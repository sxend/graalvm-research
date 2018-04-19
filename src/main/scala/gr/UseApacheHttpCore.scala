package gr

import java.io.File
import java.io.IOException
import java.net.URL
import java.net.URLDecoder
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import org.apache.http.ExceptionLogger
import org.apache.http.HttpConnection
import org.apache.http.HttpException
import org.apache.http.HttpRequest
import org.apache.http.HttpResponse
import org.apache.http.HttpStatus
import org.apache.http.MethodNotSupportedException
import org.apache.http.entity.ContentType
import org.apache.http.impl.nio.bootstrap.HttpServer
import org.apache.http.impl.nio.bootstrap.ServerBootstrap
import org.apache.http.impl.nio.reactor.IOReactorConfig
import org.apache.http.nio.entity.NFileEntity
import org.apache.http.nio.entity.NStringEntity
import org.apache.http.nio.protocol.BasicAsyncRequestConsumer
import org.apache.http.nio.protocol.BasicAsyncResponseProducer
import org.apache.http.nio.protocol.HttpAsyncExchange
import org.apache.http.nio.protocol.HttpAsyncRequestConsumer
import org.apache.http.nio.protocol.HttpAsyncRequestHandler
import org.apache.http.protocol.HttpContext
import org.apache.http.protocol.HttpCoreContext
import org.apache.http.ssl.SSLContexts

trait UseApacheHttpCore {
  self: UseTypesafeConfig =>
  protected def useApacheHttpCore: Unit = {
    val reactorConfig = IOReactorConfig.custom.setSoTimeout(15000).setTcpNoDelay(true).build
    val server = ServerBootstrap.bootstrap
      .setListenerPort(config.getInt("gr.server.port"))
      .setServerInfo("GraalVM-NativeImage")
      .setIOReactorConfig(reactorConfig)
      .setExceptionLogger(ExceptionLogger.STD_ERR)
      .registerHandler("*", new HttpHandler()).create
    server.start()
    println(s"Use ApacheHttpCore Server. server: ${server.getEndpoint.getAddress.toString}")
    server.shutdown(5, TimeUnit.SECONDS)
    //    server.awaitTermination(Long.MaxValue, TimeUnit.DAYS)
    //    Runtime.getRuntime.addShutdownHook(new Thread() {
    //      override def run(): Unit = {
    //        server.shutdown(5, TimeUnit.SECONDS)
    //      }
    //    })
  }
}
class HttpHandler extends HttpAsyncRequestHandler[HttpRequest] {
  override def processRequest(httpRequest: HttpRequest, httpContext: HttpContext): HttpAsyncRequestConsumer[HttpRequest] = {
    new BasicAsyncRequestConsumer
  }

  override def handle(req: HttpRequest, ex: HttpAsyncExchange, ctx: HttpContext): Unit = {
    val response = ex.getResponse
    handleInternal(req, response, ctx)
    ex.submitResponse(new BasicAsyncResponseProducer(response))
  }
  private def handleInternal(request: HttpRequest, response: HttpResponse, context: HttpContext): Unit = {
    val method = request.getRequestLine.getMethod.toUpperCase(Locale.ENGLISH)
    response.setStatusCode(HttpStatus.SC_OK)
    val entity = new NStringEntity("hi", ContentType.create("text/html", "UTF-8"))
    response.setEntity(entity)
  }
}