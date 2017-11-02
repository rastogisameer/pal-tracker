package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {
    TimeEntry create(TimeEntry t);
    TimeEntry find(Long id);
    List<TimeEntry> list();
    TimeEntry update(Long id, TimeEntry t);
    void delete(Long id);

}
