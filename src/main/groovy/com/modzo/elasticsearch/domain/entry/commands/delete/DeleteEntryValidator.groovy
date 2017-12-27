package com.modzo.elasticsearch.domain.entry.commands.delete

import org.springframework.stereotype.Component

@Component
class DeleteEntryValidator {

    void validate(DeleteEntry command) {
        if (!command.uniqueId) {
            throw new RuntimeException('ENTRY_UNIQUE_ID_IS_NOT_SET')
        }
    }
}
