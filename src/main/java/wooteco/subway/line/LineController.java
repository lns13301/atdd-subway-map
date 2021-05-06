package wooteco.subway.line;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import wooteco.subway.exception.LineNameDuplicatedException;
import wooteco.subway.exception.LineNotFoundException;
import wooteco.subway.exception.StationNotFoundException;
import wooteco.subway.line.dao.LineDao;
import wooteco.subway.station.Station;
import wooteco.subway.station.StationResponse;
import wooteco.subway.station.StationService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class LineController {

    private final LineDao lineDao;
    private final LineService lineService;
    private final LineRequestValidator lineRequestValidator;
    private final StationService stationService;

    public LineController(LineDao lineDao,
        LineService lineService, LineRequestValidator lineRequestValidator,
        StationService stationService) {
        this.lineDao = lineDao;
        this.lineService = lineService;
        this.lineRequestValidator = lineRequestValidator;
        this.stationService = stationService;
    }

    @InitBinder("lineRequest")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(lineRequestValidator);
    }

    @PostMapping("/lines")
    public ResponseEntity<LineResponse> createStation(@RequestBody @Valid LineRequest lineRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        final Long upStationId = lineRequest.getUpStationId();
        final Long downStationId = lineRequest.getDownStationId();

        final Station upStation = stationService.findStationById(upStationId)
            .orElseThrow(StationNotFoundException::new);
        final Station downStation = stationService.findStationById(downStationId)
            .orElseThrow(StationNotFoundException::new);

        Line createdLine = lineService.createLine(
            lineRequest.getName(),
            lineRequest.getColor(),
            upStation,
            downStation,
            lineRequest.getDistance()
        );

        final List<StationResponse> stationResponses =
            createdLine.asStations()
                .stream()
                .map(StationResponse::new)
                .collect(Collectors.toList());

        final LineResponse lineResponse = new LineResponse(createdLine.getId(),
            createdLine.getName(), createdLine.getColor(), stationResponses);

        return ResponseEntity.created(URI.create("/lines/" + createdLine.getId()))
            .body(lineResponse);
    }

    @GetMapping(value = "/lines", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LineResponse>> showLines() {
        List<Line> lines = lineDao.findAll();
        List<LineResponse> lineResponses = lines.stream()
            .map(it -> new LineResponse(it.getId(), it.getName(), it.getColor()))
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(lineResponses);
    }

    @GetMapping(value = "/lines/{id}")
    public ResponseEntity<LineResponse> findLineById(@PathVariable Long id) {
        Line line = lineDao.findLineById(id).orElseThrow(LineNotFoundException::new);
        return ResponseEntity.ok().body(new LineResponse(line.getId(), line.getName(),
            line.getColor()));
    }

    @PutMapping(value = "/lines/{id}")
    public ResponseEntity updateLine(@PathVariable Long id, @RequestBody LineRequest lineRequest) {
        lineDao.findLineById(id).orElseThrow(LineNotFoundException::new);

        final Optional<Line> lineByName = lineDao.findLineByName(lineRequest.getName());
        if (lineByName.isPresent() && lineByName.get().isNotSameId(id)) {
            throw new LineNameDuplicatedException();
        }

        lineDao.update(id, lineRequest.getName(), lineRequest.getColor());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/lines/{id}")
    public ResponseEntity removeLine(@PathVariable Long id) {
        lineDao.removeLine(id);
        return ResponseEntity.noContent().build();
    }
}
