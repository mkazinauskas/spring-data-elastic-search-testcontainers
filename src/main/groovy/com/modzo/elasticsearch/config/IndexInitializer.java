package com.modzo.elasticsearch.config;

import com.modzo.elasticsearch.domain.entry.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class IndexInitializer implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(IndexInitializer.class);

    private final ElasticsearchRestTemplate template;

    IndexInitializer(ElasticsearchRestTemplate template) {
        this.template = template;
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("Initializing indexes");

        List<Class> indexesToCreate = List.of(Entry.class);

        indexesToCreate.stream()
                .filter(documentClass -> !template.indexOps(documentClass).exists())
                .forEach(documentClass -> template.indexOps(documentClass).create());

        LOG.info("Index initialization has finished.");
    }

}