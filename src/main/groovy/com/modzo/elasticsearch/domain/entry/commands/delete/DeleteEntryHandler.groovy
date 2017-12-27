package com.modzo.elasticsearch.domain.entry.commands.delete

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import com.modzo.elasticsearch.domain.entry.Entries
import com.modzo.elasticsearch.domain.entry.Entry

@Component
class DeleteEntryHandler {
    @Autowired
    private Entries repository

    @Autowired
    private DeleteEntryValidator validator

    @Transactional
    void handle(DeleteEntry command) {
        validator.validate(command)
        Entry entry = repository.findByUniqueId(command.uniqueId)
        repository.delete(entry)
    }
}
