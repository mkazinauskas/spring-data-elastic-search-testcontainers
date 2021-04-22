package com.modzo.elasticsearch.api

import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.hateoas.Link
import org.springframework.hateoas.RepresentationModel

class IndexLinksModel extends RepresentationModel<IndexLinksModel> {

    @JsonCreator
    IndexLinksModel(Link... initialLinks) {
        super(Arrays.asList(initialLinks));
    }
}
