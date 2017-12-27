package com.modzo.elasticsearch.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import com.modzo.elasticsearch.domain.entry.Entries
import com.modzo.elasticsearch.domain.entry.commands.create.CreateEntry
import com.modzo.elasticsearch.domain.entry.commands.create.CreateEntryHandler
import com.modzo.elasticsearch.domain.entry.commands.delete.DeleteEntry
import com.modzo.elasticsearch.domain.entry.commands.delete.DeleteEntryHandler
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration
class EntryRepositorySpec extends Specification {

    @Autowired
    Entries repository

    @Autowired
    CreateEntryHandler createEntryHandler

    @Autowired
    DeleteEntryHandler deleteEntryHandler

    def 'should find saved entry by name'() {
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
