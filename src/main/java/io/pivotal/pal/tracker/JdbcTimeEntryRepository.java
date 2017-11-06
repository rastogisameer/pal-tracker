package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository{

    private JdbcTemplate template;

    public JdbcTimeEntryRepository(DataSource ds){
        template = new JdbcTemplate(ds);
    }
    @Override
    public TimeEntry create(TimeEntry e){

        KeyHolder genKeyHolder = new GeneratedKeyHolder();

        template.update(con -> {

            PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO time_entries (project_id, user_id, date, hours)" +
                            "VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setLong(1, e.getProjectId());
            statement.setLong(2, e.getUserId());
            statement.setDate(3, Date.valueOf(e.getDate()));
            statement.setInt(4, e.getHours());

            return statement;
                }, genKeyHolder);
        return find(genKeyHolder.getKey().longValue());
    }
    private final RowMapper<TimeEntry> mapper =
            (rs, rowNum) -> new TimeEntry(
                    rs.getLong("id"),
                    rs.getLong("project_id"),
                    rs.getLong("user_id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getInt("hours")
                    );
    private final ResultSetExtractor<TimeEntry> extractor =
            (rs)->rs.next()? mapper.mapRow(rs, 1): null;

    @Override
    public TimeEntry find(Long id) {
        return template.query(
                "SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id = ?",
                new Object[]{id},
                extractor
        );
    }

    @Override
    public List<TimeEntry> list() {
        return template.query(
                "SELECT id, project_id, user_id, date, hours FROM time_entries",
                mapper);
    }

    @Override
    public TimeEntry update(Long id, TimeEntry t) {

        template.update(
                "UPDATE time_entries " +
                        "SET project_id = ?, user_id = ?, date = ?, hours = ? " +
                        "WHERE id = ? ",
                t.getProjectId(),
                t.getUserId(),
                Date.valueOf(t.getDate()),
                t.getHours(),
                id);

        return find(id);
    }

    @Override
    public void delete(Long id) {
        template.update(
                "DELETE FROM time_entries WHERE id = ?",
                id);
    }
}
