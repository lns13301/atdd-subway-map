package wooteco.subway.line.service.dao;

import wooteco.subway.line.Line;

import java.util.List;
import java.util.Optional;

public interface LineDao {

    Optional<Line> findByName(String name);

    Line save(Line line);

    List<Line> findAll();

    Optional<Line> findById(Long id);

    void remove(Long id);

    void removeAll();

    void update(Long id, String name, String color);
}
