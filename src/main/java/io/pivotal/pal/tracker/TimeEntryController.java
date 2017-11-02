package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class TimeEntryController {

    private TimeEntryRepository repository;
    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.repository = timeEntryRepository;
    }

    @GetMapping
    public ResponseEntity create(TimeEntry timeEntry) {

        TimeEntry entry = repository.create(timeEntry);

        ResponseEntity<TimeEntry> e = new ResponseEntity<TimeEntry>(entry, HttpStatus.CREATED);
        return e;
    }
    @GetMapping
    public ResponseEntity<TimeEntry> read(long l) {
        TimeEntry e = repository.find(l);
        if(e != null){
            return new ResponseEntity<TimeEntry>(e, HttpStatus.OK);
        } else {
            return new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity update(long l, TimeEntry expected) {
        TimeEntry e = repository.update(l, expected);
        if(e != null){
            return new ResponseEntity(e, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<TimeEntry> delete(long id) {

        repository.delete(id);

        return new ResponseEntity<TimeEntry>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {

        return new ResponseEntity<List<TimeEntry>>(repository.list(), HttpStatus.OK);
    }
}
