package wooteco.subway.station;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.subway.exception.StationNotFoundException;
import wooteco.subway.station.dao.StationDao;

@Service
@Transactional(readOnly = true)
public class StationService {

    private final StationDao stationDao;

    public StationService(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    public Optional<Station> findStationById(Long id) {
        return stationDao.findStationById(id);
    }
}
