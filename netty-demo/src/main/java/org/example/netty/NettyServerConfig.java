package org.example.netty;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author   yanzheng
 * @date     2021/10/26
 */
@Data
@Configuration
public class NettyServerConfig {
	/**
	* 长连接服务端口
	* */
	private @Value("${netty-websocket.port}")
	Integer listenPort;

	/**
	 * 读超时时间
	 * */
	private @Value("${netty-websocket.idle.reader-time}")
	Integer readerIdleTime;

	/**
	 * 写超时时间
	 * */
	private @Value("${netty-websocket.idle.writer-time}")
	Integer writerIdleTime;

	/**
	 * 读写超时时间
	 * */
	private @Value("${netty-websocket.idle.all-time}")
	Integer allIdleTime;

	/**
	 * 盐值，用于token加密
	 * */
	private @Value("${netty-websocket.salt}")
	String salt;
}
