package wooteco.subway.station.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.subway.exception.StationNameDuplicatedException;
import wooteco.subway.exception.StationNotFoundException;
import wooteco.subway.station.Station;
import wooteco.subway.station.service.dao.StationDao;

import java.util.List;
import java.util.Optional;

@Service
public class StationService {

    private final StationDao stationDao;

    public StationService(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    public List<Station> findAll() {
        return stationDao.findAll();
    }

    public Station findById(Long id) {
        return stationDao.findById(id).orElseThrow(StationNotFoundException::new);
    }

    public Optional<Station> findByName(String name) {
        return stationDao.finByName(name);
    }

    @Transactional
    public Station save(Station station) {
        if (stationDao.finByName(station.getName()).isPresent()) {
            throw new StationNameDuplicatedException();
        }
        return stationDao.save(station);
    }

    @Transactional
    public void remove(Long id) {
        findById(id);
        stationDao.remove(id);
    }

    @Transactional
    public void removeAll() {
        stationDao.removeAll();
    }
}
