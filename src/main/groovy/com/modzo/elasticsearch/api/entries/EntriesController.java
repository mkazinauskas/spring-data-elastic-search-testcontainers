package com.modzo.elasticsearch.api.entries;

import com.modzo.elasticsearch.domain.entry.Entries;
import com.modzo.elasticsearch.domain.entry.Entry;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/entries")
public class EntriesController {

    private final Entries entries;

    private final EntryBeanMapper mapper;

    private final PagedResourcesAssembler<EntryBean> entryBeanPagedResourcesAssembler;

    EntriesController(Entries entries, EntryBeanMapper mapper,
                      PagedResourcesAssembler<EntryBean> entryBeanPagedResourcesAssembler) {
        this.entries = entries;
        this.mapper = mapper;
        this.entryBeanPagedResourcesAssembler = entryBeanPagedResourcesAssembler;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PagedModel<EntityModel<EntryBean>>> entries(Pageable pageable) {
        Page<Entry> foundEntries = entries.findAll(pageable);
        return ResponseEntity.ok(
                entryBeanPagedResourcesAssembler.toModel(
                        foundEntries.map(mapper::map),
                        linkTo(methodOn(EntriesController.class).entries(null)).withSelfRel()
                )
        );
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public ResponseEntity<EntityModel<Page<EntryBean>>> searchEntries(
            @Param(value = "query") String query,
            Pageable pageable
    ) {
        BoolQueryBuilder builder =
                boolQuery()
                        .should(QueryBuilders.queryStringQuery(query).field("name"))
                        .should(QueryBuilders.queryStringQuery(query).field("value"));

        Page<Entry> foundEntries = entries.search(builder, pageable);
        return ResponseEntity.ok(
                EntityModel.of(
                        foundEntries.map(mapper::map),
                        linkTo(methodOn(EntriesController.class).entries(null)).withSelfRel()
                )
        );
    }

}