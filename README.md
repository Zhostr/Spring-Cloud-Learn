# Spring-Cloud-Learn
学习 Spring Cloud 搭的架子，在 README.md 文件和 commit 日志中做一些笔记。

## Spring Cloud Config

### Config Server

```yaml
spring:  
  cloud:
    config:
      server:
        git:
          # 可以通过 HTTP 调用获取（直接在地址栏中写），/{application}/{profile}[/{label}] 或者 /{application}-{profile}.yml 等，https://cloud.spring.io/spring-cloud-static/Edgware.SR5/single/spring-cloud.html#_quick_start
          zuulconfig
          zuulconfig
          #       {label} --- commit id, branch name or tag
          # 如直接访问 http://localhost:8888/eureka-client/dev 获取此 Git 仓库 的 eureka-client 目录下的 eureka-client-dev.yml 文件
          uri: zuulconfig      # Git 仓库地址
          search-paths: /{application}                     # 仓库下的搜索目录（前面的 / 不能省略）
          force-pull: true
          username: Pliza
          password: 4fdsfs18@qqfdsfsafasdh111
```

在 [config-server](https://github.com/Pliza/config-server) 仓库中新增默认的配置文件（eureka-server.yml）后，仓库中的文件如下图所示：

![yml文件](http://my-personal-blog.oss-cn-beijing.aliyuncs.com/18-12-18/86367152.jpg)

再次在浏览器中访问，`http://localhost:8888/eureka-client/test` 或者 `http://localhost:8888/eureka-client/dev` ，都会显示默认的 eureka-client.yml 文件

![](http://my-personal-blog.oss-cn-beijing.aliyuncs.com/18-12-18/43058701.jpg)

### Config Client

```yaml
# 多环境通用配置
spring:
  application:
    name: eureka-server #first-cloud-server
  cloud:
    # 参考 http://localhost:xxxx/eureka-client/dev（格式 /{application}/{profile}）
    zuulconfig
    config:
      uri: http://localhost:7777  #指定 Spring Cloud Config 配置服务器
      label: master               #拉取内容的分支名，默认为 master
      profile: dev
      name: eureka-client
  profiles:
    active: ${profiles.active}  #指定生效的配置，可以通过 java -jar xxx.jar --spring.profiles.active=x 指定.

management:
  security:
    enabled: false


---
server:
  port: 8761
spring:
  profiles: dev
  # 服务在启动时，会把自己当做一个 Eureka 客户端去注册到 Eureka 服务器上，且从服务器上拉取信息
  # 而该服务本身就是一个 Eureka 服务器（现在是配置 Eureka 集群，需要彼此相互注册）
eureka:
  instance:
    hostname: localhost
  client:
    # 声明是否将自己的信息注册到 Eureka 服务器上
    register-with-eureka: false
    # 是否到 Eureka 服务器中抓取注册信息
    fetch-registry: false
#    serviceUrl:
#      defaultZone: http://localhost:8762/eureka/


---
server:
  port: 8762
spring:
  profiles: test
eureka:
  instance:
    hostname: Zhoust
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```

启动 Config Client 后，查看 env 端点，得到以下关于 ConfigService 的信息：

```yaml
"configService:configClient": {
  "config.client.version": "f2fc68892a49902d4a10f7e3eb486f3d46a717cb"
},
"configService:https://github.com/Pliza/config-server/eureka-client/eureka-client-dev.yml": {
  "info.name": "Pliza666",
  "info.gender": "girl",
  "info.age": 22
},
"configService:https://github.com/Pliza/config-server/eureka-client/eureka-client.yml": {
  "info.name": "default-name",
  "info.gender": "default-boy"
},
```

可以看到即使在 bootstrap.yml 文件中指定了 spring.cloud.config.profile=dev（**如果要是不配置的话，会使用 ${spring.application.name}，观察控制台输出「Located environment」**），仍旧会显示默认的 eureka-client.yml，但实际起作用的还是 eureka-client-dev.yml 中的配置信息，通过 /env/xxx 可查看具体某属性的值是多少。

```yaml
#访问 Config Client 的 http://localhost:8761/env/info.*
{
  "info.name": "Pliza666",
  "info.gender": "girl",
  "info.age": 22
}
```

## Hystrix

> Whether or not your command has a fallback, all of the usual Hystrix state and circuit-breaker state/metrics are updated to indicate the command failure.

## TODO
common-starter：测试包添加 again-java 内容
config-server：使用阿里 [Nacos](https://nacos.io/zh-cn/)
