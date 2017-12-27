package com.modzo.elasticsearch.domain.entry

import groovy.transform.PackageScope
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldIndex
import org.springframework.data.elasticsearch.annotations.FieldType

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric

@Document(indexName = 'entry', type = 'entry', refreshInterval = '-1')
class Entry {

    @Id
    String id

    private String uniqueId = randomAlphanumeric(40)

    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    String name

    @Field(type = FieldType.String, index = FieldIndex.analyzed)
    String value

    @PackageScope
    Entry() {
    }

    Entry(String name, String value) {
        this.name = name
        this.value = value
    }

    String getUniqueId() {
        return uniqueId
    }
}
