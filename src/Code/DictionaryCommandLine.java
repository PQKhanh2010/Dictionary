package Code;

import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryCommandLine extends DictionaryManagement {
    private Scanner scan = new Scanner(System.in);

    public void insertFromCommandLine() {
        try {
            System.out.print("Nhập số lượng từ muốn thêm: ");
            int numberOfWord = scan.nextInt();
            scan.nextLine();
            for (int i = 0; i < numberOfWord; i++) {
                System.out.print("Nhập từ: ");
                String word = scan.nextLine();
                boolean isInLoadedDB = false;
                boolean isInAddingDB = false;
                System.out.print("Nhập phát âm từ bạn muốn thêm: ");
                String pronounce = scan.nextLine();
                System.out.print("Nhập nghĩa của từ bạn muốn thêm: ");
                String meaning = scan.nextLine();
                addToData.add(new Word(word, pronounce, meaning));
                System.out.println("Thêm từ thành công!");
            }
        } catch (Exception ex) {
            System.out.println("Lỗi đọc từ command line: " + ex);
        }
    }

    public void showAllWord() {
        loadedData.sort(null);
        System.out.format("%-10s%-32s%-32s%-32s%n", "No.", "English", "Pronunciation", "Vietnamese");
        for (int i = 0; i < loadedData.size(); i++) {
            System.out.format("%-10d", i + 1);
            loadedData.get(i).printWord();
        }
    }

    public void fixWord() {
        try {
            String cont = "y";
            do {
                System.out.print("Nhập từ bạn muốn sửa: ");
                String input = scan.nextLine();
                System.out.println("Lựa chọn mục bạn muốn sửa: (Nhập số để chọn) ");
                System.out.println("1. Từ");
                System.out.println("2. Phát âm");
                System.out.println("3. Nghĩa");
                System.out.println("0. Dừng");
                int choice = scan.nextInt();
                scan.nextLine();
                if (choice != 0) {
                    System.out.print("Bạn muốn sửa thành: ");
                    String fix = scan.nextLine();
                    if (fix(input, choice, fix)) {
                        System.out.println("Từ đã được sửa!");
                    } else {
                        System.out.println("Từ không tồn tại.");
                    }
                    System.out.print("Bạn có muốn sửa tiếp không? (y/n): ");
                    cont = scan.nextLine().toLowerCase();
                }
            } while (cont.equals("y"));
        } catch (Exception ex) {
            System.out.println("Lỗi đọc từ command line: " + ex);
        }
    }

    public void deleteWord() {
        try {
            String cont = "y";
            do {
                System.out.print("Nhập từ bạn muốn xóa: ");
                String input = scan.nextLine();
                boolean isDeleted = delete(input);
                if (isDeleted) {
                    System.out.println("Xóa từ thành công!");
                } else {
                    System.out.println("Từ không tồn tại.");
                }
                System.out.print("Bạn có muốn xóa tiếp không? (y/n): ");
                cont = scan.nextLine().toLowerCase();
            } while (cont.equals("y"));
        } catch (Exception ex) {
            System.out.println("Lỗi đọc từ command line: " + ex);
        }
    }

    public void dictionaryLookup() {
        try {
            System.out.print("Viết từ mà bạn cần tra: ");
            String lookup = scan.nextLine();
            lookup.toLowerCase();
            boolean exist = false;
            StringBuilder corrected = new StringBuilder();
            for (int i = 0; i < lookup.length(); i++)
                if (Character.isAlphabetic(lookup.charAt(i))) corrected.append(lookup.charAt(i));
            lookup = corrected.toString();
            for (int i = 0; i < loadedData.size(); i++) {
                if (loadedData.get(i).getWord_target().equals(lookup)) {
                    loadedData.get(i).printWord();
                    exist = true;
                    break;
                }
            }
            if (!exist) System.out.println("Không tồn tại từ đó trong từ điển");
        } catch (Exception ex) {
            System.out.println("Lỗi đọc command line: " + ex);
        }
    }

    public ArrayList<Word> dictionarySearch() {
        loadedData.sort(null);
        ArrayList<Word> matchedWord = new ArrayList<Word>();
        try {
            System.out.print("Nhập từ bạn muốn tìm trong database: ");
            String input = scan.nextLine();
            for (int i = 0; i < loadedData.size(); i++)
                if (loadedData.get(i).getWord_target().indexOf(input) == 0)
                    matchedWord.add(loadedData.get(i));
        } catch (Exception ex) {
            System.out.println("Lỗi đọc từ command line: " + ex);
        }
        return matchedWord;
    }

    public void dictionaryAdvanced() {
        try {
            String cont = "y";
            insertFromFile();
            loadedData.sort(null);
            do {
                System.out.println("Lựa chọn việc bạn muốn làm (nhập số để chọn): ");
                System.out.println("1. Hiện tất cả từ của từ điển (showAllWord)");
                System.out.println("2. Tìm một từ trong từ điển (dictionaryLookup)");
                System.out.println("3. Tìm những từ bắt đầu bằng input trong từ điển (dictionarySearch)");
                System.out.println("4. Xóa một từ trong từ điển (deleteWord)");
                System.out.println("5. Sửa từ có trong từ điển (fixWord)");
                System.out.println("6. Thêm từ mới vào từ điển (insertFromCommandline)");
                System.out.println("7. Thoát khỏi chương trình");
                System.out.print("Lựa chọn: ");
                int choice = scan.nextInt();
                scan.nextLine();
                switch (choice) {
                    case (1):
                        showAllWord();
                        break;
                    case (2):
                        dictionaryLookup();
                        break;
                    case (3): {
                        ArrayList<Word> searchResult = dictionarySearch();
                        for (int i = 0; i < searchResult.size(); i++)
                            searchResult.get(i).printWord();
                        if (searchResult.isEmpty())
                            System.out.println("Không tìm được từ đó!");
                        break;
                    }
                    case (4):
                        deleteWord();
                        break;
                    case (5):
                        fixWord();
                        break;
                    case (6):
                        insertFromCommandLine();
                        writeToFile();
                        break;
                    case (7):
                        System.exit(0);
                    default:
                        break;
                }
                System.out.println();
            } while (cont.equals("y"));
        } catch (Exception ex) {
            System.out.println("Lỗi đọc từ command line: " + ex);
        } finally {
            scan.close();
        }
    }
}
