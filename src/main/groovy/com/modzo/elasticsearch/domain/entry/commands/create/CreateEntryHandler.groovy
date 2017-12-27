package com.modzo.elasticsearch.domain.entry.commands.create

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import com.modzo.elasticsearch.domain.entry.Entries
import com.modzo.elasticsearch.domain.entry.Entry

@Component
class CreateEntryHandler {
    @Autowired
    private Entries repository


    @Transactional
    String handle(CreateEntry createEntry) {
        Entry entry = repository.save(new Entry(createEntry.name, createEntry.value))
        return entry.uniqueId
    }
}
