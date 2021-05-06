package wooteco.subway.section;

import wooteco.subway.station.Station;

public class SectionResponse {

    private Long id;
    private Long lineId;
    private Station upStation;
    private Station downStation;
    private int distance;

    public SectionResponse(Long id, Long lineId, Station upStation, Station downStation, int distance) {
        this.id = id;
        this.lineId = lineId;
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public Long getLineId() {
        return lineId;
    }

    public Station getUpStation() {
        return upStation;
    }

    public Station getDownStation() {
        return downStation;
    }

    public int getDistance() {
        return distance;
    }
}
