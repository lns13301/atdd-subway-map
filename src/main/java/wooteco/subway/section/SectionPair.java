package wooteco.subway.section;

public class SectionPair {

    private Section originalSection;
    private Section newSection;

    public SectionPair(Section originalSection, Section newSection) {
        this.originalSection = originalSection;
        this.newSection = newSection;
    }

    public Section getOriginalSection() {
        return originalSection;
    }

    public Section getNewSection() {
        return newSection;
    }
}
