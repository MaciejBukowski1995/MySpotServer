package com.myspot.myspot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/{logId}/logs")
class LogRestController {

    private final LogRepository logRepository;

    private final AccountRepository accountRepository;

    @Autowired
    LogRestController(LogRepository logRepository, AccountRepository accountRepository) {
        this.logRepository = logRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Log> readLogs(@PathVariable String userId) {
        this.validateUser(userId);
        return this.logRepository.findByAccountUsername(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Log input) {
        this.validateUser(userId);

        return this.accountRepository.findByUsername(userId).map(account -> {
                    Log result = logRepository.save(new Log(account, input.getUri(), input.getDescription()));

                    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();

                    return ResponseEntity.created(location).build();
                }).orElse(ResponseEntity.noContent().build());

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{logId}")
    Log readLog(@PathVariable String userId, @PathVariable Long logId) {
        this.validateUser(userId);
        return this.logRepository.getOne(logId);
    }

    private void validateUser(String userId) {
        this.accountRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }
}