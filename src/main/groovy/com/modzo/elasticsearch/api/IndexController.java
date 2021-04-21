package com.modzo.elasticsearch.api;

import com.modzo.elasticsearch.api.entries.EntriesController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EntityModel<String>> index() {
        return ResponseEntity.ok(
                EntityModel.of("",
                        linkTo(methodOn(IndexController.class).index()).withSelfRel(),
                        linkTo(methodOn(EntriesController.class)
                                .entries(null))
                                .withRel("entries")
                )
        );
    }

}
