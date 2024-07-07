package dev.atomixsoft.solar_eclipse.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import dev.atomixsoft.solar_eclipse.server.util.logging.Logger;


public class ConnectionHandler extends ChannelInboundHandlerAdapter {
    private final Logger logger;

    
    public ConnectionHandler(Logger logger) {
        super();

        this.logger = logger;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String message = (String) msg;
        logger.info("Received: " + msg);

        String response = processMessage(message);
        ctx.writeAndFlush(response);
        logger.info("Sent: " + response);
    }

    private String processMessage(String message) {
        // TODO:  But actual game server logic here.
        //        For now, this is just an echo test.
        return "Server Received: " + message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Exception caught: " + cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}
