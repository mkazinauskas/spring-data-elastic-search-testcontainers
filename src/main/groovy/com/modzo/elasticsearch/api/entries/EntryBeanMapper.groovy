package com.modzo.elasticsearch.api.entries

import com.modzo.elasticsearch.domain.entry.Entry
import org.springframework.stereotype.Component

@Component
class EntryBeanMapper {

    EntryBean map(Entry entry) {
        return new EntryBean(
                uniqueId: entry.uniqueId,
                name: entry.name,
                value: entry.value
        )
    }
}
