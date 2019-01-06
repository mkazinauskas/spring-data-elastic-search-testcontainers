package com.modzo.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.TransportClientFactoryBean;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import java.net.InetSocketAddress;
import java.util.Properties;

@Configuration
public class ElasticSearchWithTestContainersConfiguration implements DisposableBean {

    private final static String CLUSTER_NAME = "docker-cluster";

    private final static ElasticsearchContainer CONTAINER = new ElasticsearchContainer();

    private final ElasticsearchProperties properties;

    public ElasticSearchWithTestContainersConfiguration(ElasticsearchProperties properties) {
        this.properties = properties;
        CONTAINER.start();
    }

    @Bean
    public TransportClient testContainersElasticsearchClient() throws Exception {
        TransportClientFactoryBean factory = new TransportClientFactoryBean();
        factory.setClusterNodes(toHostAddress(CONTAINER.getTcpHost()));
        factory.setProperties(createProperties());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    private Properties createProperties() {
        Properties properties = new Properties();
        properties.put("cluster.name", CLUSTER_NAME);
        properties.putAll(this.properties.getProperties());
        return properties;
    }

    private String toHostAddress(InetSocketAddress address) {

        return address.getHostName() + ":" + address.getPort();
    }

    @Override
    public void destroy() throws Exception {
        CONTAINER.stop();
    }
}
