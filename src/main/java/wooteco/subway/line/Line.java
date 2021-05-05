package wooteco.subway.line;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import wooteco.subway.section.Section;
import wooteco.subway.station.Station;

public class Line {

    private Long id;
    private String name;
    private String color;
    private List<Section> sections; //일급컬렉션으로 만들자.

    public Line(String name, String color) {
        this(null, name, color, new ArrayList<>());
    }

    public Line(Long id, String name, String color) {
        this(id, name, color, new ArrayList<>());
    }

    public Line(Long id, String name, String color,
        List<Section> sections) {
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

    public boolean isSameColor(String color) {
        return this.color.equals(color);
    }

    public List<Station> asStations() {
        final Set<Station> stations = new HashSet<>();

        sections.stream().forEach(section -> {
            stations.add(section.getDownStation());
            stations.add(section.getUpStation());
        });
        //TODO Station의 순서 소팅
        return new ArrayList<>(stations);
    }
}
