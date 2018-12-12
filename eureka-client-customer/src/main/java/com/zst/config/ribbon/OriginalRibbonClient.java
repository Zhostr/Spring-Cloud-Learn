package com.zst.config.ribbon;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Zhoust
 * @date: 2018/11/29 下午10:32
 * @version: V1.0
 */
public class OriginalRibbonClient {

    public static void main(String[] args) {
        //创建负载均衡器
        ILoadBalancer iLoadBalancer = new BaseLoadBalancer();
        //添加服务器
        List<Server> serverList = new ArrayList<>(2);
        serverList.add(new Server("localhost",8848));
        serverList.add(new Server("localhost",8849));

        iLoadBalancer.addServers(serverList);
        for(int i = 0; i<10;i++) {
            Server server = iLoadBalancer.chooseServer(null);
            System.out.println(server);
        }
    }

}