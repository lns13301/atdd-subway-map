package wooteco.subway.section;

import wooteco.subway.station.Station;

import java.util.*;

public class Sections {

    private final List<Section> sections;

    public Sections(List<Section> sections) {
        this.sections = sections;
    }

    public void add(Section section) {
        sections.add(section);
    }

    public List<Station> findStations() {
        Optional<Section> section = Optional.of(findStartSection(sections.get(0)));
        List<Station> stations = new ArrayList<>(Collections.singletonList(section.get().getUpStation()));

        while (section.isPresent()) {
            stations.add(section.get().getDownStation());
            section = findSectionByUpStation(section.get().getDownStation());
        }

        return stations;
    }

    private Section findStartSection(Section section) {
        Optional<Section> result = Optional.of(section);
        Section startSection = section;

        while (result.isPresent()) {
            startSection = result.get();
            result = findSectionByDownStation(result.get().getUpStation());
        }

        return startSection;
    }

    private Optional<Section> findSectionByUpStation(Station station) {
        return sections.stream()
                .filter(section -> section.isStartStation(station))
                .findAny();
    }

    private Optional<Section> findSectionByDownStation(Station station) {
        return sections.stream()
                .filter(section -> section.isDownStation(station))
                .findAny();
    }
}
