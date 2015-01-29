package org.kurt.netty.channel;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

import javax.servlet.ServletException;

import org.kurt.netty.handler.ServletHandler;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @ClassName: DispatcherServletChannelInitializer
 * @Description: 服务启动控制器
 * @author 张佳俊
 * @date 2015年01月27日17:10:14
 * @version V1.0
 *
 */
public class DispatcherServletChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final DispatcherServlet dispatcherServlet;

    private final String charset;

    /**
     * 构造器，初始化上下文
     * @author 张佳俊
     * @date 2015年01月27日17:13:44
     * @param charset
     * @param env
     * @param configLocation
     * @throws ServletException
     */
    public DispatcherServletChannelInitializer (String charset, String env, String ... configLocation) throws ServletException{
        this.charset = charset;

        MockServletContext servletContext = new MockServletContext();
        MockServletConfig servletConfig = new MockServletConfig(servletContext);

        XmlWebApplicationContext wac = new XmlWebApplicationContext();
        wac.setServletContext(servletContext);
        wac.setServletConfig(servletConfig);
        wac.setConfigLocations(configLocation);
        wac.getEnvironment().addActiveProfile(env);
        this.dispatcherServlet = new DispatcherServlet(wac);
        this.dispatcherServlet.init(servletConfig);
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());
        pipeline.addLast("handler", new ServletHandler(dispatcherServlet, charset));
    }

}
