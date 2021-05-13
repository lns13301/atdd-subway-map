package wooteco.subway.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.subway.domain.Line;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LineResponse {
    private Long id;
    private String name;
    private String color;
    private List<StationResponse> stations;

    public static LineResponse of(Line line) {
        List<StationResponse> stations = line.stations().stream()
                .map(StationResponse::of)
                .collect(Collectors.toList());
        return new LineResponse(line.getId(), line.getName(), line.getColor(), stations);
    }
}
