package org.kurt.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.kurt.netty.Environment;
import org.kurt.netty.channel.DispatcherServletChannelInitializer;

/**
 *
 * @ClassName: NettyServer
 * @Description: netty服务端
 * @author 张佳俊
 * @date 2015年01月27日17:10:14
 * @version V1.0
 *
 */
public class NettyServer {
    //服务端口
    private final int port;
    //内容字符编码
    private final String charset;
    //环境类型（开发、测试、压测、生产）
    private final String env;
    //配置文件路径
    private final String[] configLocation;

    /**
     *
     * 初始化服务端
     * @author 张佳俊
     * @date  2015年01月27日14:13:18
     * @param port     服务端口
     * @param charset  字符编码
     * @param env      系统环境类型
     * @param configLocation 配置文件路径* @author 张佳俊
     */
    public NettyServer(int port, String charset, Environment env, String ... configLocation) {
        this.port = port;
        this.configLocation = configLocation;
        this.charset = charset;
        this.env = env.toString();
    }

    /**
     * 启动服务
     * @author 张佳俊
     * @date  2015年01月27日14:13:18
     * @throws Exception
     */
    public void start() throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup());
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.localAddress(port);
            bootstrap.childHandler(new DispatcherServletChannelInitializer(charset, env, configLocation));
            bootstrap.bind().sync().channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
