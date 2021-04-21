package com.modzo.elasticsearch

import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.RestClients
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories
import org.testcontainers.elasticsearch.ElasticsearchContainer

@Configuration
@EnableElasticsearchRepositories
class RestClientConfig {

    @Bean
    RestHighLevelClient client() {
        ElasticsearchContainer container = new ElasticsearchContainer()
        container.start()

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(container.getHttpHostAddress())
                .build()

        return RestClients.create(clientConfiguration).rest()
    }

    @Bean
    ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client())
    }
}