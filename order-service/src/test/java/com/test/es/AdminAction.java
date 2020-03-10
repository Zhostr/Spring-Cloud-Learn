package com.test.es;

import com.zst.order.OrderApplication;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: ES Admin 操作
 * @author: Zhoust
 * @date: 2020/02/12 下午2:03
 * @version: V1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AdminAction {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    private Client esClient;

    @PostConstruct
    public void init() {
        esClient = elasticsearchTemplate.getClient();
    }

    //查询索引名称
    @Test
    public void selectAllIndexName() {
        ActionFuture<ClusterHealthResponse> health = esClient.admin().cluster().health(new ClusterHealthRequest());
        Map<String, ClusterIndexHealth> indices = health.actionGet().getIndices();
        for (String indexName : indices.keySet()) {
            log.info("index name {}, health is {}", indexName, indices.get(indexName));
        }
    }

    public void indexTemplate() {
        String templateName = "StudentOnlineAnalysisTemplate";
        IndicesAdminClient indicesAdminClient = esClient.admin().indices();
        Map<String, Object> templateSource = new HashMap<>();

        templateSource.put("index_patterns", "cum_analysis*");
        indicesAdminClient.preparePutTemplate(templateName)
                .setSource(templateSource);
    }


}