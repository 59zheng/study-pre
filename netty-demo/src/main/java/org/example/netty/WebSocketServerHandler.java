package org.example.netty;

import com.alibaba.fastjson.JSON;
import com.naixue.lab.service.shell.NettyWebShellService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.netty.handler.codec.http.HttpHeaderNames.HOST;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author yanzheng
 * @date 2021/10/26
 */
@Service
@ChannelHandler.Sharable
@Slf4j
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {


	private static final String WEBSOCKET_PATH = "/shell";



	private WebSocketServerHandshaker handshaker;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		// log.info("{} 默认读 isActive:{}", ctx.channel().remoteAddress(),
		if (ctx.channel().isActive()) {
			// 第一次握手由HTTP协议承载，所以是一个HTTP消息，根据消息头中是否包含"Upgrade"字段来判断是否是websocket。
			// 接收处理消息
			if (msg instanceof FullHttpRequest) {
				// 处理HTTP请求
				// 获取真实客户端ip,从http请求头获取.
				FullHttpRequest httpRequest = (FullHttpRequest) msg;
				HttpHeaders headers = httpRequest.headers();
				String ip = headers.get("X-Real-IP");
				log.info("X-Real-IP:{}", ip);
				ctx.channel().attr(AttributeKey.valueOf("ip")).set(ip);
				handleHttpRequest(ctx, httpRequest);
			} else if (msg instanceof WebSocketFrame) {
				// 处理WebSocket
				handleWebSocketFrame(ctx, (WebSocketFrame) msg);
			} else {
				// log.warn("channelRead0 else {}", msg);
			}
		} else {
			// log.info("{} Channel not Active", ctx.channel().remoteAddress());
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	/**
	 * 处理Http请求
	 *
	 * @param ctx
	 * @param req
	 */
	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
		// log.info("{} 处理Http请求", ctx.channel().remoteAddress());

		// Handle a bad request.
		if (!req.decoderResult().isSuccess()) {
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
			return;
		}

		// Allow only GET methods.
		if (req.method() != GET) {
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
			return;
		}

		if ("/favicon.ico".equals(req.uri()) || ("/".equals(req.uri()))) {
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND));
			return;
		}

		/**
		 * 当服务端接收到协议由 HTTP 握手升级协议 WebSocket 时， 我们需要通过 WebSocketServerHandshakerFactory 类新建
		 * WebSocketHandshaker， 此时会判断 WebSocket 协议版本以及相关信息进行校验。
		 */
		// Handshake 构造握手响应返回，
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(req), null, true);
		handshaker = wsFactory.newHandshaker(req);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
		} else {
			/**
			 * 协议升级: Http协议 --> Websocket协议
			 * 底层源码其实就是移除handler中Http协议的编解码handler, 添加Websocket协议的编解码Handler
			 * @see WebSocketServerHandshaker#handshake(Channel, FullHttpRequest, HttpHeaders, ChannelPromise)
			 */
			ChannelFuture channelFuture = handshaker.handshake(ctx.channel(), req);
			// 握手成功之后,业务逻辑
			if (channelFuture.isSuccess()) {
//				log.info("{} 握手成功", ctx.channel().remoteAddress());
			}
		}
	}

	/**
	 * 处理WebSocket框架
	 *
	 * @param ctx
	 * @param frame
	 */
	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
		// log.info("{} 处理WebSocket", ctx.channel().remoteAddress());
		if (frame instanceof TextWebSocketFrame) {
			ctx.fireChannelRead(((TextWebSocketFrame) frame).text());
		}
		// 判断是否是关闭链路的指令
		else if (frame instanceof CloseWebSocketFrame) {
//			关闭链路指令
			log.info("关闭链路指令{}", JSON.toJSONString(frame));
			service.exit(ctx, handshaker, (CloseWebSocketFrame) frame.retain());
		} else { // 仅支持文本消息，不支持二进制消息
			throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
		}
	}

	private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
		// 返回应答给客户端
		if (res.status().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
			HttpUtil.setContentLength(res, res.content().readableBytes());
			// HttpHeaderUtil.setContentLength(res, res.content().readableBytes());
		}
		// 如果是非Keep-Alive，关闭连接
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	/**
	 * 发生异常时，关闭连接（channel），随后将channel从ChannelGroup中移除
	 *
	 * @param ctx
	 * @param cause
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.fireExceptionCaught(cause);
	}

	/**
	 * 当客户端连接服务端之后(打开连接)
	 *
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		log.info("通道连接:{}", ctx.channel().id().asShortText());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		log.info("断开连接:{}", ctx.channel().id().asShortText());
//		ctx.handler().handlerRemoved(ctx);
	}

	private static String getWebSocketLocation(FullHttpRequest req) {
		String location = req.headers().get(HOST) + WEBSOCKET_PATH;
		return "ws://" + location;
	}
}