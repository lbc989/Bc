**Tomcat体系结构**

Apache Tomcat 是一款非常著名的开源 Servlet/JSP 容器，被用做 Java Servlet 和 JavaServer Pages 技术的官方参考实现。

Tomcat 体系结构中的六个主要概念：

- [Server](http://tomcat.apache.org/tomcat-8.5-doc/config/server.html)

  Server代表整个容器(container)。它可以包含一个或多个Service，还可以包含一个GlobalNamingResources。

- [Service](http://tomcat.apache.org/tomcat-8.5-doc/config/service.html)

  Service中可以含有一个或多个Connector，但只能含有一个Engine。这使得不同的Connector可以共享同一个Engine。同一个Server中的多个Service之间没有相关性。

- [Engine](http://tomcat.apache.org/tomcat-8.5-doc/config/engine.html)

  Engine负责接收和处理来自它所属的Service中的所有Connector的请求。

- [Host](http://tomcat.apache.org/tomcat-8.5-doc/config/host.html)

  Host表示一个虚拟主机，并和一个服务器的网络名关联。注意Engine中必须有一个Host的名字和Engine的defaultHost属性匹配。

- Connector

  Connector负责接收来自客户端(Client)的请求。比较常见的两个是[HTTP Connector](http://tomcat.apache.org/tomcat-8.5-doc/config/http.html)和[AJP Connector](http://tomcat.apache.org/tomcat-8.5-doc/config/ajp.html)。

- [Context](http://tomcat.apache.org/tomcat-8.5-doc/config/context.html)

  Context表示在虚拟主机中运行的web应用程序。一个虚拟主机中能够运行多个Context，它们通过各自的Context Path进行相互区分。如果Context Path为""，那么该web应用为该虚拟主机的默认的web应用。

- Wrapper

  每一Wrapper封装着一个Servlet。

  

  结构图：

![9d2ba6f4fab343c9b00725b5cabf2096](C:\Users\Bc\Desktop\9d2ba6f4fab343c9b00725b5cabf2096.jpeg)

![e1e1765392e3409da6747f69e9373899](C:\Users\Bc\Desktop\e1e1765392e3409da6747f69e9373899.jpeg)