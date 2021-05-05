package wooteco.subway.section;

import wooteco.subway.station.Station;

public class Section {

    private Long id;
    private Long lineId;
    private Station upStation;
    private Station downStation;
    private int distance;

    public Section() {}

    public Section(Long lineId, Station upStation, Station downStation, int distance) {
        this(null, lineId, upStation, downStation, distance);
    }

    public Section(Long id, Long lineId, Station upStation, Station downStation, int distance) {
        this.id = id;
        this.lineId = lineId;
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public boolean isStartStation(Station station) {
        return upStation.equals(station);
    }

    public boolean isDownStation(Station station) {
        return downStation.equals(station);
    }

    public Long getId() {
        return id;
    }

    public Long getLineId() {
        return lineId;
    }

    public int getDistance() {
        return distance;
    }

    public Station getUpStation() {
        return upStation;
    }

    public Station getDownStation() {
        return downStation;
    }

    public Long upStationId() {
        return upStation.getId();
    }

    public Long downStationId() {
        return downStation.getId();
    }
}
