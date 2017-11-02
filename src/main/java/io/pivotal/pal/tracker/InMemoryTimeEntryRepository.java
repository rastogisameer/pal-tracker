package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    private HashMap<Long, TimeEntry> entries = new HashMap<Long, TimeEntry>();
    @Override
    public TimeEntry create(TimeEntry t) {

        t.setId(entries.size() + 1);
        entries.put(t.getId(), t);
        return t;

    }

    @Override
    public TimeEntry find(Long id) {

        return entries.get(id);
    }

    @Override
    public List<TimeEntry> list() {

        return new ArrayList<TimeEntry>(entries.values());
    }

    @Override
    public TimeEntry update(Long id, TimeEntry t) {

       entries.replace(id, t);
       t.setId(id);
       return t;
    }

    @Override
    public void delete(Long id) {
        entries.remove(id);
    }
}
