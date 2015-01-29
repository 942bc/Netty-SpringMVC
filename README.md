# Netty-SpringMVC 是一个netty、SpingMVC集成是例子。这种框架主要应用于SOA的架构中充当服务提供者，
用传统的springMVC+web容器也可以做。这套框架中netty就扮演了容器的角色，Netty采用了NIO的方式，
在IO密集型业务中TPS要优于tomcat。

此例子下载到本地可以运行org.kurt.netty.Main 然后浏览器访问127.0.0.1：8070/test

