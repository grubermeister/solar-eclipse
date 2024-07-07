package dev.atomixsoft.solar_eclipse.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import dev.atomixsoft.solar_eclipse.server.config.Configuration;
import dev.atomixsoft.solar_eclipse.server.config.Configuration.SupportedConfigFileTypes;
import dev.atomixsoft.solar_eclipse.server.util.logging.Logger;
import dev.atomixsoft.solar_eclipse.server.util.logging.Logger.SupportedLogHandlerTypes;


public class Server {
    private final int port;
    
    private final EventLoopGroup incomingConnectionGroup;
    private final EventLoopGroup clientWorkerGroup;

    private final Configuration configInfo;
    private final Logger logger;


    public Server() {
        this.configInfo = new Configuration(SupportedConfigFileTypes.INI, "server/server.ini"); 
        this.logger = new Logger(Server.class.getSimpleName(), 
                                   SupportedLogHandlerTypes.ASYNC_CONSOLE, 
                                   configInfo.getLogLevel(),
                                   configInfo.getLogPattern());
        this.port = configInfo.getPort();
        incomingConnectionGroup = new NioEventLoopGroup();
        clientWorkerGroup = new NioEventLoopGroup();
    }

    public void run() throws Exception {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(incomingConnectionGroup, clientWorkerGroup)
                     .channel(NioServerSocketChannel.class)
                     .childHandler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         public void initChannel(SocketChannel ch) {
                             ChannelPipeline pipeline = ch.pipeline();
                             pipeline.addLast(new StringDecoder());
                             pipeline.addLast(new StringEncoder());
                             pipeline.addLast(new ConnectionHandler(new Logger(Server.class.getSimpleName(), 
                                                                              SupportedLogHandlerTypes.ASYNC_CONSOLE, 
                                                                              configInfo.getLogLevel(),
                                                                              configInfo.getLogPattern())));
                         }
                     })
                     .option(ChannelOption.SO_BACKLOG, 128)
                     .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.bind(this.port).sync();
            logger.info(configInfo.getGameName() + " server instance accepting RPC on port " + this.port + ".");

            future.channel().closeFuture().sync();
        } finally {
            incomingConnectionGroup.shutdownGracefully();
            clientWorkerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            new Server().run();
        } catch (Exception e) {
            throw new RuntimeException("Server failed to start: " + e.getMessage());
        }
    }
}
