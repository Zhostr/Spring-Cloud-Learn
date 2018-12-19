# Spring-Cloud-Learn
学习 Spring Cloud 搭的架子，在 README.md 文件和 commit 日志中做一些笔记。

## ConfigServer

```yaml
spring:  
  cloud:
    config:
      server:
        git:
          # 可以通过 HTTP 调用获取（直接在地址栏中写），/{application}/{profile}[/{label}] 或者 /{application}-{profile}.yml 等，https://cloud.spring.io/spring-cloud-static/Edgware.SR5/single/spring-cloud.html#_quick_start
          # {application} --- 对应 config 客户端的 spring.application.name
          #     {profile} --- 对应 config 客户端的 spring.profiles.active
          #       {label} --- commit id, branch name or tag
          # 如直接访问 http://localhost:8888/eureka-client/dev 获取此 Git 仓库 的 eureka-client 目录下的 eureka-client-dev.yml 文件
          uri: https://github.com/Pliza/config-server      # Git 仓库地址
          search-paths: /{application}                     # 仓库下的搜索目录（前面的 / 不能省略）
          force-pull: true
          username: Pliza
          password: 4fdsfs18@qqfdsfsafasdh111
```

在 [config-server](https://github.com/Pliza/config-server) 仓库中新增默认的配置文件（eureka-server.yml）后，仓库中的文件如下图所示：

![yml文件](http://my-personal-blog.oss-cn-beijing.aliyuncs.com/18-12-18/86367152.jpg)

再次在浏览器中访问，`http://localhost:8888/eureka-client/test` 或者 `http://localhost:8888/eureka-client/dev` ，都会显示默认的 eureka-client.yml 文件

![](http://my-personal-blog.oss-cn-beijing.aliyuncs.com/18-12-18/43058701.jpg)

