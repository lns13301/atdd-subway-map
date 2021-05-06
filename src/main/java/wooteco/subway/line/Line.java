package wooteco.subway.line;

import wooteco.subway.section.Section;
import wooteco.subway.section.SectionPair;
import wooteco.subway.section.Sections;
import wooteco.subway.station.Station;

import java.util.ArrayList;
import java.util.List;

public class Line {

    private Long id;
    private String name;
    private String color;
    private Sections sections;

    public Line(String name, String color) {
        this(null, name, color, new ArrayList<>());
    }

    public Line(Long id, String name, String color) {
        this(id, name, color, new ArrayList<>());
    }

    public Line(Long id, String name, String color, List<Section> sections) {
        this(id, name, color, new Sections(sections));
    }

    public Line(Long id, String name, String color, Sections sections) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.sections = sections;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public boolean isSameName(String name) {
        return this.name.equals(name);
    }

    public boolean isSameId(Long id) {
        return this.id.equals(id);
    }

    public void changeInfo(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public boolean isNotSameId(Long id) {
        return !this.id.equals(id);
    }

    public void addSection(Section section) {
        sections.add(section);
    }

    public void addSection(SectionPair sectionPair) {
        sections.add(sectionPair);
    }

    public boolean isSameColor(String color) {
        return this.color.equals(color);
    }

    public List<Station> asStations() {
        return sections.findStations();
    }
}
