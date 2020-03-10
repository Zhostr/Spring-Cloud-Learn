package com.zst.order.es;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @description: ES Java 客户端
 * @author: Zhoust
 * @date: 2020/02/12 下午1:22
 * @version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/es/option")
public class ESController {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    private Client esClient;

    @PostConstruct
    public void init() {
        esClient = elasticsearchTemplate.getClient();
    }


    public void selectIndex() {
        ActionFuture<ClusterHealthResponse> health = esClient.admin().cluster().health(new ClusterHealthRequest());
        ClusterHealthResponse clusterHealthResponse = health.actionGet();
        log.info("health = {}", clusterHealthResponse);
    }

    @GetMapping("getAlias")
    public void createIndexTemplate(String aliasName) {
        ActionFuture<GetAliasesResponse> aliasesFuture = esClient.admin().indices().getAliases(new GetAliasesRequest().aliases(aliasName));
        ImmutableOpenMap<String, List<AliasMetaData>> aliases = aliasesFuture.actionGet().getAliases();
        log.info("aliases = {}", aliases);
    }

}