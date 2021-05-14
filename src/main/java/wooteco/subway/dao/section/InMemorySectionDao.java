package wooteco.subway.dao.section;

import org.springframework.util.ReflectionUtils;
import wooteco.subway.domain.Section;
import wooteco.subway.domain.Sections;
import wooteco.subway.exception.section.SectionNotFoundException;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class InMemorySectionDao implements SectionDao {

    private Long seq = 0L;
    private final Map<Long, List<Section>> sections = new HashMap<>();

    private Section createNewObject(Section section) {
        Field field = ReflectionUtils.findField(Section.class, "id");
        field.setAccessible(true);
        ReflectionUtils.setField(field, section, ++seq);
        return section;
    }

    @Override
    public Section save(Section section, Long lineId) {
        List<Section> sectionsByLineId = sections.getOrDefault(lineId, new ArrayList<>());
        Section createdSection = createNewObject(section);
        sectionsByLineId.add(createdSection);
        sections.put(lineId, sectionsByLineId);
        return createdSection;
    }

    @Override
    public Sections findById(Long lineId) {
        final List<Section> sections = new ArrayList<>(this.sections.get(lineId));
        return Sections.from(sections);
    }

    @Override
    public Map<Long, Sections> findAll() {
        Map<Long, Sections> sectionDictionary = new HashMap<>();
        for (Long key : sections.keySet()) {
            sectionDictionary.put(key, Sections.from(this.sections.get(key)));
        }
        return sectionDictionary;
    }

    @Override
    public List<Section> findContainsStationId(Long lineId, Long stationId) {
        return sections.get(lineId)
                .stream()
                .filter(section -> section.hasStation(stationId))
                .collect(
                        Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        Section target = sections.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(section -> section.isSameId(id))
                .findAny()
                .orElseThrow(SectionNotFoundException::new);
        sections.get(target.getLineId()).removeIf(section -> section.isSameId(id));
    }

    @Override
    public void deleteStations(Long lineId, List<Section> sections) {
        for (Section section : sections) {
            this.sections.get(lineId).removeIf(sec -> sec.isSameSection(section));
        }
    }


    @Override
    public void deleteByLineId(Long lineId) {
        sections.remove(lineId);
    }

    @Override
    public void insertSection(Section affectedSection, Long lineId) {
        sections.get(lineId).add(affectedSection);
    }
}
