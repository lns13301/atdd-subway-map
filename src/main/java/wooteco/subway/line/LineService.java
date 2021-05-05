package wooteco.subway.line;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.subway.line.dao.LineDao;
import wooteco.subway.section.Section;
import wooteco.subway.section.dao.SectionDao;
import wooteco.subway.station.Station;

@Service
@Transactional(readOnly = true)
public class LineService {

    private final LineDao lineDao;
    private final SectionDao sectionDao;

    public LineService(LineDao lineDao, SectionDao sectionDao) {
        this.lineDao = lineDao;
        this.sectionDao = sectionDao;
    }

    @Transactional
    public Line createLine(String name, String color,
        Station upStation, Station downStation, int distance) {
        final Line savedLine = lineDao.save(new Line(name, color));
        final Section savedSection = sectionDao.save(new Section(savedLine.getId(), upStation, downStation, distance));

        savedLine.addSection(savedSection);
        return savedLine;
    }
}
