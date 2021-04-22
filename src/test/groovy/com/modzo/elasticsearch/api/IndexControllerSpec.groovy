package com.modzo.elasticsearch.api

import com.modzo.elasticsearch.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class IndexControllerSpec extends IntegrationSpec {

    @Autowired
    TestRestTemplate restTemplate

    void 'should load index page'() {
        when:
            ResponseEntity<String> response = restTemplate.getForEntity('/', String)
        then:
            response.statusCode == HttpStatus.OK
        and:
            response.body.contains('"self"')
            response.body.contains('"entries"')
    }
}