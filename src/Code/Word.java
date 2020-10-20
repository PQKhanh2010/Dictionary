package Code;

public class Word implements Comparable<Word> {
    private String word_target;
    private String word_explain;
    private String word_pronounce;

    public Word(Word other) {
        this.word_target = other.word_target;
        this.word_pronounce = other.word_pronounce;
        this.word_explain = other.word_explain;
    }

    public Word(String word_target, String word_pronounce, String word_explain) {
        this.word_target = word_target;
        this.word_pronounce = word_pronounce;
        this.word_explain = word_explain;
    }

    public String getWord_target() {
        return word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public String getWord_pronounce() {
        return word_pronounce;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public void setWord_pronounce(String word_pronounce) {
        this.word_pronounce = word_pronounce;
    }

    public void printWord() {
        System.out.format("%-32s%-32s%-32s%n", this.word_target, this.word_pronounce, this.word_explain);
    }

    public void wordPrint() {
        System.out.println(this.word_target + "\n" + this.word_pronounce + "\n" + this.word_explain);
    }

    @Override
    public int compareTo(Word other) {
        return this.word_target.compareTo(other.word_target);
    }

    public String toString() {
        return this.word_target;
    }
}
