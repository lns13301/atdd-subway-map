package wooteco.subway.line;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import wooteco.subway.line.dao.LineDao;
import wooteco.subway.station.dao.StationDao;

@Component
public class LineRequestValidator implements Validator {

    private final LineDao lineDao;

    public LineRequestValidator(LineDao lineDao) {
        this.lineDao = lineDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return LineRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final LineRequest lineRequest = (LineRequest) target;

        validateDuplicatedName(errors, lineRequest.getName());
        validateDuplicatedColor(errors, lineRequest.getColor());
    }

    private void validateDuplicatedName(Errors errors, String name) {
        if (lineDao.findLineByName(name).isPresent()) {
            errors.rejectValue("name", "duplicatedName", "이미 존재하는 노선 이름입니다.");
        }
    }

    private void validateDuplicatedColor(Errors errors, String color) {
        if (lineDao.findLineByColor(color).isPresent()) {
            errors.rejectValue("color", "duplicatedColor", "이미 존재하는 노선 색입니다.");
        }
    }
}
