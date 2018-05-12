package gr

import java.nio.charset.Charset
import java.util.UUID

import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel._
import io.netty.handler.codec.http._
import HttpResponseStatus._
import HttpVersion._
import spray.json._
import DefaultJsonProtocol._

trait UseNetty {
  self: UseTypesafeConfig =>
  def useNetty: Unit = {
    new ServerBootstrap()
      .group(new NioEventLoopGroup())
      .channelFactory(new ChannelFactory[ServerChannel] {
        override def newChannel(): ServerChannel = new NioServerSocketChannel()
      })
      .childHandler(new ChannelInitializer[SocketChannel] {
        override def initChannel(ch: SocketChannel): Unit =
          ch.pipeline().addLast(new HttpServerCodec()).addLast(new MyHandler)
      })
      .bind(config.getInt("gr.server.port"))
  }
}

class MyHandler extends ChannelInboundHandlerAdapter {
  import MyHandler.entityFormat
  def entity: Entity = Entity(Metadata(UUID.randomUUID().toString, System.currentTimeMillis()), Payload("hello"))
  override def channelRead(ctx: ChannelHandlerContext, msg: scala.Any): Unit = {
    msg match {
      case req: HttpRequest =>
        if (HttpUtil.is100ContinueExpected(req)) ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE))
        val response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.copiedBuffer(entity.toJson.prettyPrint, Charset.defaultCharset()))
        response.headers.set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE)
      case _ =>
    }
  }
  override def channelReadComplete(ctx: ChannelHandlerContext): Unit = {
    ctx.flush()
  }
  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    cause.printStackTrace()
    ctx.close()
  }
}
object MyHandler extends UseSprayJson