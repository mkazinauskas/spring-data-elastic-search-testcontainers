package com.modzo.elasticsearch.domain.entry;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface Entries extends ElasticsearchRepository<Entry, String> {
    List<Entry> findByName(String name);

    Entry findByUniqueId(String uniqueId);
}
