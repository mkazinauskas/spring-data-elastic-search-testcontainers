package com.modzo.elasticsearch.api

import com.modzo.elasticsearch.IntegrationSpec
import com.modzo.elasticsearch.domain.entry.commands.create.CreateEntry
import com.modzo.elasticsearch.domain.entry.commands.create.CreateEntryHandler
import com.modzo.elasticsearch.domain.entry.commands.delete.DeleteEntry
import com.modzo.elasticsearch.domain.entry.commands.delete.DeleteEntryHandler
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils
import spock.lang.Unroll

class EntriesControllerSpec extends IntegrationSpec {

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    CreateEntryHandler createEntryHandler

    @Autowired
    DeleteEntryHandler deleteEntryHandler

    void 'should find entries'() {
        given:
            String uniqueId = createEntryHandler.handle(new CreateEntry('random name', 'random value'))
        when:
            ResponseEntity<String> response = restTemplate.getForEntity('/entries', String)
        then:
            response.statusCode == HttpStatus.OK
        and:
            def body = new JsonSlurper().parseText(response.body)
            def item = body._embedded.entryBeanList.find { it.uniqueId == uniqueId }
            item.name == 'random name'
            item.value == 'random value'
        cleanup:
            deleteEntryHandler.handle(new DeleteEntry(uniqueId))
    }

    @Unroll
    void 'should find entry by query = `#query` that matches entry with name = `#name` and value =`#value`'() {
        given:
            String uniqueId = createEntryHandler.handle(new CreateEntry(name, value))
        when:
            ResponseEntity<String> response = restTemplate.getForEntity("/entries/search?query=${query}", String)
        then:
            response.statusCode == HttpStatus.OK
        and:
            def body = new JsonSlurper().parseText(response.body)
            def item = body.content.find { it.uniqueId == uniqueId }
            item.name == name
            item.value == value
        cleanup:
            deleteEntryHandler.handle(new DeleteEntry(uniqueId))
        where:
            name                         | value                        | query
            "name ${random(10)}"         | ''                           | 'name'
            "${random(10)} name"         | ''                           | 'name'
            "first ${random(10)} second" | ''                           | 'first second'
            "first ${random(10)} second" | ''                           | 'first third'
            ''                           | "value ${random(10)}"        | 'value'
            ''                           | "${random(10)} value"        | 'value'
            ''                           | "first ${random(10)} second" | 'first second'
            ''                           | "first ${random(10)} second" | 'first third'
    }

    private static String random(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }
}
