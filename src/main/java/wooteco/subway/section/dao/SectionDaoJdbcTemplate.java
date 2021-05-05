package wooteco.subway.section.dao;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import wooteco.subway.line.Line;
import wooteco.subway.section.Section;

@Repository
public class SectionDaoJdbcTemplate implements SectionDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public SectionDaoJdbcTemplate(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;

        jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("SECTION").usingGeneratedKeyColumns("id");
    }

    @Override
    public Section save(Section section) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("distance", section.getDistance());
        parameters.put("down_station_id", section.downStationId());
        parameters.put("up_station_id", section.upStationId());
        parameters.put("line_id", section.getLineId());

        final long id = jdbcInsert.executeAndReturnKey(parameters).longValue();

        return new Section(id, section.getLineId(), section.getUpStation(), section.getDownStation(), section.getDistance());
    }
}
