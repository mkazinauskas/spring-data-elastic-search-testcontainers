package com.modzo.elasticsearch;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import java.net.InetSocketAddress;
import java.util.Properties;

@Component
public class ElasticSearchEnvironmentPostProcessor implements EnvironmentPostProcessor, DisposableBean {
    private static final String TCP_HOST = "elasticSearchTcpHost";
    private static final String PROPERTY_SOURCE_NAME = "test-containers-elastic-search";
    private static final ElasticsearchContainer CONTAINER = new ElasticsearchContainer();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (!CONTAINER.isRunning()) {
            CONTAINER.start();
        }

        Properties props = new Properties();
        props.put(TCP_HOST, toHostAddress(CONTAINER.getTcpHost()));

        environment.getPropertySources()
                .addFirst(new PropertiesPropertySource(PROPERTY_SOURCE_NAME, props));
    }

    private String toHostAddress(InetSocketAddress address) {
        return address.getHostName() + ":" + address.getPort();
    }

    @Override
    public void destroy() {
        CONTAINER.stop();
    }
}
