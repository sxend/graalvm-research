package gr

import java.nio.charset.Charset

import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.{ ChannelFactory, ChannelFuture, ChannelFutureListener, ChannelHandlerContext, ChannelInboundHandlerAdapter, ChannelInitializer, ServerChannel }
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel

trait UseNetty {
  self: UseTypesafeConfig =>
  def useNetty: Unit = {
    val worker = new NioEventLoopGroup()
    val bootstrap = new ServerBootstrap()
      .group(worker)
      .channelFactory(new ChannelFactory[ServerChannel] {
        override def newChannel(): ServerChannel = new NioServerSocketChannel()
      })
      .childHandler(new ChannelInitializer[SocketChannel] {
        override def initChannel(ch: SocketChannel): Unit = {
          ch.writeAndFlush(Unpooled.copiedBuffer("Bye\n", Charset.defaultCharset))
            .addListener(ChannelFutureListener.CLOSE)
        }
      })
    val server = bootstrap.bind(config.getInt("gr.server.port")).sync()
    worker.shutdownGracefully()
  }
}
