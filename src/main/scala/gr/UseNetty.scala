package gr

import java.nio.charset.Charset

import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.{ ChannelFactory, ChannelFutureListener, ChannelInitializer, ServerChannel }

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
          ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE)
      })
    //      .bind(config.getInt("gr.server.port"))
  }
}
