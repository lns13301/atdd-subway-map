package wooteco.subway.exception;

public class WrongSectionException extends RuntimeException {

    private static final String MESSAGE = "추가할 수 없는 구간입니다.";

    public WrongSectionException() {
        super(MESSAGE);
    }
}
