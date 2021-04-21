package com.modzo.elasticsearch.domain

import com.modzo.elasticsearch.IntegrationSpec
import com.modzo.elasticsearch.domain.entry.Entries
import com.modzo.elasticsearch.domain.entry.commands.create.CreateEntry
import com.modzo.elasticsearch.domain.entry.commands.create.CreateEntryHandler
import com.modzo.elasticsearch.domain.entry.commands.delete.DeleteEntry
import com.modzo.elasticsearch.domain.entry.commands.delete.DeleteEntryHandler
import org.springframework.beans.factory.annotation.Autowired

class EntryRepositorySpec extends IntegrationSpec {

    @Autowired
    Entries repository

    @Autowired
    CreateEntryHandler createEntryHandler

    @Autowired
    DeleteEntryHandler deleteEntryHandler

    void 'should find saved entry by name'() {
        given:
            String entryId = createEntryHandler.handle(
                    new CreateEntry(name: 'myName', value: 'myValue')
            )
        expect:
            repository.findByName('myName').first().uniqueId == entryId
        cleanup:
            deleteEntryHandler.handle(new DeleteEntry(uniqueId: entryId))
    }
}
