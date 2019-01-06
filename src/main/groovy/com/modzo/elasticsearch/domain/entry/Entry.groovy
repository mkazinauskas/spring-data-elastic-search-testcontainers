package com.modzo.elasticsearch.domain.entry

import groovy.transform.PackageScope
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = 'entry', type = 'entry', refreshInterval = '-1')
class Entry {

    @Id
    String id

    private String uniqueId = UUID.randomUUID().toString()

    @Field(type = FieldType.Text)
    String name

    @Field(type = FieldType.Text)
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
