package wooteco.subway.section;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.subway.AcceptanceTest;
import wooteco.subway.line.Line;
import wooteco.subway.line.dao.LineDao;
import wooteco.subway.station.Station;
import wooteco.subway.station.dao.StationDao;

import java.util.HashMap;
import java.util.Map;

@DisplayName("섹션 테스트")
class SectionControllerTest extends AcceptanceTest {

    @Autowired
    private StationDao stationDao;

    @Autowired
    private LineDao lineDao;


    @Test
    @DisplayName("라인에 섹션을 추가할 시")
    public void sectionInsert_success() throws Exception {
        //given
        final Long id_강남역 = stationDao.save(new Station("강남역")).getId();
        final Long id_양재역 = stationDao.save(new Station("양재역")).getId();

        final Long lineId = lineDao.save(new Line("2호선", "random")).getId();

        Map<String, String> params = new HashMap<>();
        params.put("upStationId", id_강남역 + "");
        params.put("downStationId", id_양재역 + "");
        params.put("distance", "10");

        //when
        RestAssured.given().log().all()
            .body(params)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/lines/" + lineId + "/sections")
            .then().log().all()
            .statusCode(HttpStatus.CREATED.value())
            .header("Location", Matchers.notNullValue());
    }
}
