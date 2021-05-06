package wooteco.subway.section;

import wooteco.subway.exception.WrongSectionException;
import wooteco.subway.station.Station;

import java.util.*;
import java.util.stream.Collectors;

public class Sections {

    private static final int SECTION_LIMIT = 1;
    private static final int FIRST_ELEMENT = 0;

    private final List<Section> sections;

    public Sections(List<Section> sections) {
        this.sections = sections;
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

    private List<Section> createSections(Section section) {
        Optional<Section> result = Optional.of(section);
        LinkedList<Section> sortedSections = new LinkedList<>();

        // 피봇부터 시작지점까지 구간을 찾으면서 sortedSections 에 저장하기
        while (result.isPresent()) {
            sortedSections.addFirst(result.get());
            result = findSectionByDownStation(result.get().getUpStation());
        }

        result = findSectionByUpStation(section.getDownStation());

        // 피봇부터 끝지점까지 구간을 찾으면서 sortedSections 에 저장하기
        while (result.isPresent()) {
            sortedSections.add(result.get());
            result = findSectionByUpStation(result.get().getDownStation());
        }

        return sortedSections;
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

    public SectionPair findSectionBySection(Section newSection) {
        List<Section> collect = sections.stream()
                .filter(sec ->
                        sec.isStartStation(newSection.getUpStation()) ||
                                sec.isDownStation(newSection.getDownStation()) ||
                                sec.isStartStation(newSection.getDownStation()) ||
                                sec.isDownStation(newSection.getUpStation()))
                .collect(Collectors.toList());

        if (collect.size() != SECTION_LIMIT) {
            throw new WrongSectionException();
        }

        Section originalSection = collect.get(FIRST_ELEMENT);

        return new SectionPair(originalSection, newSection);
    }

    public void add(Section section) {
        sections.add(section);
    }

    public void add(SectionPair sectionPair) {
        Section originalSection = sectionPair.getOriginalSection();
        Section newSection = sectionPair.getNewSection();

        if (originalSection.isStartStation(newSection.getUpStation())) {
            originalSection.updateUpStation(newSection);
        }
        if (originalSection.isDownStation(newSection.getDownStation())) {
            originalSection.updateDownStation(newSection);
        }
        sections.add(newSection);
    }

    private void updateSectionByUpStation(Section originalSection, Section section) {
        originalSection.updateUpStation(section);
    }

    private void updateSectionByDownStation(Section originalSection, Section section) {
        originalSection.updateDownStation(section);
    }
}
