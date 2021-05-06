package wooteco.subway.section;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wooteco.subway.section.dao.SectionDao;
import wooteco.subway.station.dao.StationDao;

@RestController
public class SectionController {

    private StationDao stationDao;
    private SectionDao sectionDao;

    public SectionController(StationDao stationDao, SectionDao sectionDao) {
        this.stationDao = stationDao;
        this.sectionDao = sectionDao;
    }

    @PostMapping("/lines/{id}/sections")
    public ResponseEntity<SectionResponse> createSection(@PathVariable Long id, @RequestBody SectionRequest sectionRequest) {
        Section section = sectionDao.save(new Section(id,
                stationDao.findStationById(sectionRequest.getUpStationId()).get(),
                stationDao.findStationById(sectionRequest.getDownStationId()).get(),
                sectionRequest.getDistance())
        );
        SectionResponse sectionResponse = new SectionResponse(section.getId(),
                section.getLineId(),
                section.getUpStation(),
                section.getDownStation(),
                section.getDistance()
        );
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(sectionResponse);
    }
}
