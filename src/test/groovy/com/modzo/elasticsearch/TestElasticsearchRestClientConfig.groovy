package com.modzo.elasticsearch

import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.RestClients
import org.testcontainers.elasticsearch.ElasticsearchContainer

@Configuration
class TestElasticsearchRestClientConfig {

    @Bean
    @Primary
    RestHighLevelClient testElasticSearchClient() {
        ElasticsearchContainer container = new ElasticsearchContainer()
        container.start()

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(container.getHttpHostAddress())
                .build()

        return RestClients.create(clientConfiguration).rest()
    }

}