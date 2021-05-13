package wooteco.subway.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Line {
    private final Long id;
    private final String name;
    private final String color;
    private Sections sections;

    public static Line of(String name, String color) {
        return of(null, name, color, Sections.from());
    }

    public static Line of(Long id, String name, String color) {
        return of(id, name, color, Sections.from());
    }

    public static Line of(Long id, String name, String color, Sections sections) {
        return new Line(id, name, color, sections);
    }

    public void addSection(Section section) {
        sections.add(section);
    }

    public List<Station> stations() {
        return sections.asStations();
    }

    public boolean isSameName(String name) {
        return this.name.equals(name);
    }

    public boolean isSameColor(String color) {
        return this.color.equals(color);
    }

    public boolean isSameId(Long lineId) {
        return id.equals(lineId);
    }

    public void insertSections(Sections sections) {
        this.sections = sections;
    }
}
