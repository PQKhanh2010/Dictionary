package Code;

import java.util.*;

public class Dictionary {
    private ArrayList<Word> data = new ArrayList<Word>();

    public void addWord(Word word) {
        data.add(word);
    }

    public ArrayList<Word> getData() {
        return data;
    }
}
