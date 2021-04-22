package com.modzo.elasticsearch.api;

import com.modzo.elasticsearch.api.entries.EntriesController;
import org.springframework.hateoas.Links;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<IndexLinksModel> index() {
        return ResponseEntity.ok(
                new IndexLinksModel(
                        linkTo(methodOn(IndexController.class).index()).withSelfRel(),
                        linkTo(methodOn(EntriesController.class)
                                .entries(null))
                                .withRel("entries")
                )
        );
    }
}
