package Code;

import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class DictionaryManagement {
    protected Dictionary dict = new Dictionary();
    /**
     * data gốc từ file
     */
    protected ArrayList<Word> loadedData = dict.getData();
    /**
     * data thêm vào
     */
    protected ArrayList<Word> addToData = new ArrayList<>();
    private final File file = new File("EV.txt");

    /**
     * Đọc dữ liệu từ file EV.txt
     */
    public void insertFromFile() {
        try {
            Scanner scan = new Scanner(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String line;
            while (scan.hasNext()) {
                line = scan.nextLine();
                String target = "";
                String pronounce = "";
                String explain = "";
                /** Đọc cho đến dòng trắng, đọc xong add từ rồi đọc tiếp */
                while (line.trim().length() != 0) {
                    if (line.indexOf("@") == 0) {
                        line = line.replace("@", "");
                        target = line;
                        line = scan.nextLine();
                    } else if (line.indexOf("^") == 0) {
                        line = line.replace("^", "");
                        pronounce = line;
                        line = scan.nextLine();
                    } else if (line.indexOf("$") == 0) {
                        line = line.replace("$", "Cách viết/đọc khác: ");
                        explain = line;
                        line = scan.nextLine();
                    } else {
                        if (explain == "") {
                            explain = explain + line;
                        } else {
                            explain = explain + "\n" + line;
                        }
                        line = scan.nextLine();
                    }
                }
                dict.addWord(new Word(target, pronounce, explain));
            }
            scan.close();
        } catch (Exception ex) {
            System.out.println("Lỗi đọc file: " + ex);
        }
    }

    /**
     * thêm từ vào EV.txt
     */
    public void writeToFile() {
        try {
            FileWriter fw = new FileWriter(file, true);
            for (int i = 0; i < addToData.size(); i++) {
                Word w = addToData.get(i);
                fw.write(w.getWord_target() + "\t" + w.getWord_pronounce() + "\t" + w.getWord_explain() + "\n");
            }
            fw.close();
        } catch (IOException ex) {
            System.out.println("Lỗi ghi file: " + ex);
        }
    }

    /**
     * xóa từ có trong EV.txt
     */
    public boolean delete(String s) {
        boolean isDeleted = false;
        boolean isInLoadedDB = false;
        for (int i = 0; i < loadedData.size(); i++) {
            if (s.equals(loadedData.get(i).getWord_target())) {
                loadedData.remove(i);
                isDeleted = true;
                isInLoadedDB = true;
                break;
            }
        }
        if (!isInLoadedDB) {
            for (int i = 0; i < addToData.size(); i++) {
                if (s.equals(addToData.get(i).getWord_target())) {
                    addToData.remove(i);
                    isDeleted = true;
                    break;
                }
            }
        }
        try {
            FileWriter fw = new FileWriter(file);
            addToData.addAll(loadedData);
            for (int i = 0; i < addToData.size(); i++) {
                Word w = addToData.get(i);
                fw.write(w.getWord_target() + "\t" + w.getWord_pronounce() + "\t" + w.getWord_explain() + "\n");
            }
            addToData.clear();
            fw.close();
        } catch (IOException e) {
            System.out.println("Lỗi viết ra file: " + e);
        }
        return isDeleted;
    }

    /**
     * xóa từ có trong EV.txt
     */
    public boolean fix(String s, int n, String fix) {
        boolean isFix = false;
        boolean isInLoadedDB = false;
        for (int i = 0; i < loadedData.size(); i++) {
            if (s.equals(loadedData.get(i).getWord_target())) {
                switch (n) {
                    case 1:
                        loadedData.get(i).setWord_target(fix);
                        break;
                    case 2:
                        loadedData.get(i).setWord_pronounce(fix);
                        break;
                    case 3:
                        loadedData.get(i).setWord_explain(fix);
                        break;
                }
                isFix = true;
                isInLoadedDB = true;
                break;
            }
        }
        if (!isInLoadedDB) {
            for (int i = 0; i < addToData.size(); i++) {
                if (s.equals(addToData.get(i).getWord_target())) {
                    switch (n) {
                        case 1:
                            addToData.get(i).setWord_target(fix);
                            break;
                        case 2:
                            addToData.get(i).setWord_pronounce(fix);
                            break;
                        case 3:
                            addToData.get(i).setWord_explain(fix);
                            break;
                    }
                    isFix = true;
                    break;
                }
            }
        }
        try {
            FileWriter fw = new FileWriter(file);
            addToData.addAll(loadedData);
            for (int i = 0; i < addToData.size(); i++) {
                Word w = addToData.get(i);
                fw.write(w.getWord_target() + "\t" + w.getWord_pronounce() + "\t" + w.getWord_explain() + "\n");
            }
            addToData.clear();
            fw.close();
        } catch (IOException e) {
            System.out.println("Lỗi viết ra file: " + e);
        }
        return isFix;
    }

    /**
     * tìm kiếm trong EV.txt
     */
    public ArrayList<Word> dictionarySearch(String input) {
        ArrayList<Word> matchedWord = new ArrayList<Word>();
        for (int i = 0; i < loadedData.size(); i++) {
            if (loadedData.get(i).getWord_target().indexOf(input) == 0)
                matchedWord.add(loadedData.get(i));
        }
        return matchedWord;
    }

    public void showWords() {
        loadedData.sort(null);
        for (int i = 0; i < loadedData.size(); i++) {
            loadedData.get(i).wordPrint();
            System.out.println("");
        }
    }
}
