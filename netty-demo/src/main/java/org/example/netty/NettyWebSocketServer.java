package org.example.netty;

import com.naixue.lab.common.utils.SystemUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.internal.PlatformDependent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author yanzheng
 * @date 2021/10/26
 */
public class NettyWebSocketServer {
	private static final Logger logger = LoggerFactory.getLogger(NettyWebSocketServer.class);
	private ServerBootstrap server;
	private EventLoopGroup eventLoopGroupSelector;
	private EventLoopGroup eventLoopGroupBoss;

	@Autowired
	NettyServerConfig config;

	@Autowired
	ChannelInitializer channelInitializer;

	@PostConstruct
	private void init() {
		logger.info("[NettyWebSocketServer] 开始启动...");
		System.setProperty("org.jboss.netty.epollBugWorkaround", "true");
		new Thread(this::start).start();
	}

	private void start() {
		this.loadGroup();
		try {
			this.server = new ServerBootstrap();
			server.group(eventLoopGroupBoss, eventLoopGroupSelector);
			// 设置tcp缓冲区
			server.option(ChannelOption.SO_BACKLOG, 32768);
//			// 设置发送缓冲大小
//			server.option(ChannelOption.SO_SNDBUF, 32 * 1024);
			// 这是接收缓冲大小
			server.option(ChannelOption.SO_RCVBUF, 32 * 1024);
//			// 保持连接
//			server.option(ChannelOption.SO_KEEPALIVE, true);
			// 复用端口
			server.option(ChannelOption.SO_REUSEADDR, true);
			// 无延迟
			server.childOption(ChannelOption.TCP_NODELAY, true);
			server.childOption(ChannelOption.SO_KEEPALIVE, true);
			// 强行关闭等待中的连接
			server.childOption(ChannelOption.SO_LINGER, 0);
			server.childOption(ChannelOption.SO_SNDBUF, 32 * 1024);
			// 设置高低水位
			server.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, WriteBufferWaterMark.DEFAULT);
			//绑定服务端通道
			server.channel(useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class);
			server.localAddress(config.getListenPort());
			server.handler(new LoggingHandler(LogLevel.INFO));
			server.childOption(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(PlatformDependent.directBufferPreferred()));
			server.childHandler(channelInitializer);
			// TODO 内存泄漏检测开到最高级别,便于排查问题,一段时间没有内存泄漏日志的话,可以降低
			ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);
			ChannelFuture f = server.bind().sync();
			logger.info("[NettyWebSocketServer] 服务启动成功,端口:{}", config.getListenPort());
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			logger.error("[NettyWebSocketServer] 服务启动失败", e);
		} finally {
			this.eventLoopGroupBoss.shutdownGracefully();
			this.eventLoopGroupSelector.shutdownGracefully();
		}
	}

	private boolean useEpoll() {
		return SystemUtil.isLinuxPlatform() && Epoll.isAvailable();
	}

	private void loadGroup() {
		if (useEpoll()) {
			this.eventLoopGroupBoss = new EpollEventLoopGroup();

			this.eventLoopGroupSelector = new EpollEventLoopGroup();
		} else {
			this.eventLoopGroupBoss = new NioEventLoopGroup();

			this.eventLoopGroupSelector = new NioEventLoopGroup();
		}
	}

}
