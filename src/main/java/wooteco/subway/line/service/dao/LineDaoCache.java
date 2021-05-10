package wooteco.subway.line.service.dao;

import org.springframework.util.ReflectionUtils;
import wooteco.subway.line.Line;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LineDaoCache implements LineDao {

    private final List<Line> lines = new ArrayList<>();
    private Long seq = 0L;

    @Override
    public Optional<Line> findByName(String name) {
        return lines.stream()
                .filter(line -> line.isSameName(name))
                .findAny();
    }

    @Override
    public Line save(Line line) {
        Line persistLine = createNewObject(line);
        lines.add(persistLine);
        return persistLine;
    }

    private Line createNewObject(Line line) {
        Field field = ReflectionUtils.findField(Line.class, "id");
        field.setAccessible(true);
        ReflectionUtils.setField(field, line, ++seq);
        return line;
    }

    @Override
    public List<Line> findAll() {
        return lines;
    }

    @Override
    public Optional<Line> findById(Long id) {
        return lines.stream()
                .filter(line -> line.isSameId(id))
                .findAny();
    }

    @Override
    public void remove(Long id) {
        lines.removeIf(line -> line.isSameId(id));
    }

    @Override
    public void removeAll() {
        lines.clear();
    }

    @Override
    public void update(Long id, String name, String color) {
        findById(id).get().changeInfo(name, color);
    }
}
