package com.git.wuqf.xiaokuo.remoting.netty;

import com.git.wuqf.xiaokuo.remoting.Server;
import com.git.wuqf.xiaokuo.remoting.transport.AbstractServer;
import com.git.wuqf.xiaokuo.common.URL;
import com.git.wuqf.xiaokuo.common.utils.NetUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by wuqf on 17-2-25.
 */
public class NettyServer<T> extends AbstractServer implements Server {

    private Map<String, com.git.wuqf.xiaokuo.remoting.Channel> channels;

    private ServerBootstrap bootstrap;


    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private ChannelFuture f;

    public NettyServer(URL url, com.git.wuqf.xiaokuo.remoting.ChannelHandler channelHandler) {
        super(url, channelHandler);
    }

    public void doOpen() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        bootstrap = new ServerBootstrap();
        NettyHandler nettyHandler = new NettyHandler(getUrl(), this);

        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new LoggingHandler(LogLevel.TRACE));
                        p.addLast(
                                new ObjectEncoder(),
                                new ObjectDecoder(ClassResolvers.cacheDisabled(null)), nettyHandler);
                    }
                });

        f = bootstrap.bind(getBindAddress());
    }

    public void doClose() {
        f.channel().closeFuture();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public Collection<com.git.wuqf.xiaokuo.remoting.Channel> getChannels() {
        Collection<com.git.wuqf.xiaokuo.remoting.Channel> chs = new HashSet<>();
        for (com.git.wuqf.xiaokuo.remoting.Channel channel : channels.values()) {
            chs.add(channel);
        }
        return chs;
    }

    @Override
    public com.git.wuqf.xiaokuo.remoting.Channel getChannel(InetSocketAddress remoteAddress) {
        return channels.get(NetUtils.toAddressString(remoteAddress));
    }

}
