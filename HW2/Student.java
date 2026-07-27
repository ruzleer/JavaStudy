import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class Book {
    private String title;
    private int pagesAmount;
    private int publishYear;

    public Book(String title, int pagesAmount, int publishYear){
        this.title = title;
        this.pagesAmount = pagesAmount;
        this.publishYear = publishYear;
    }

    public String getTitle(){
        return title;
    }

    public int getPagesAmount(){
        return pagesAmount;
    }

    public int getPublishYear(){
        return publishYear;
    }

    @Override
    public String toString() {
        return title + " (" + pagesAmount + " стр., " + publishYear + " г.)";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return pagesAmount == book.pagesAmount && publishYear == book.publishYear && Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, pagesAmount, publishYear);
    }
}

class Student {
    private String name;
    private List<Book> books;

    Student(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public List<Book> getStudentsBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "Студент - " + name;
    }
}

class StudentReader {
    public static void main(String[] args) {

        try {
            String fileInputStream = Files.readString(Paths.get("..\\HW2\\students.txt"));
            String json = fileInputStream.trim();

            List<Student> studentsInfo = parseStudents(json);
            studentsInfo.forEach(System.out::println);

            studentsInfo.stream()
                    .peek(System.out::println)
                    .map(Student::getStudentsBooks)
                    .flatMap(Collection::stream)
                    .sorted(Comparator.comparingInt(Book::getPagesAmount))
                    .distinct()
                    .filter(book -> book.getPublishYear() > 2000)
                    .limit(3)
                    .map(Book::getPublishYear)
                    .findFirst()
                    .ifPresentOrElse(
                            year -> System.out.println("Год выпуска: " + year),
                            () -> System.out.println("Такая книга отсутствует")
                    );
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Student> parseStudents(String json) {
        List<Student> students = new ArrayList<>();

        int studentsStart = json.indexOf("\"students\"");
        if (studentsStart == -1) return students;

        int arrayStart = json.indexOf("[", studentsStart);
        if (arrayStart == -1) return students;

        int arrayEnd = findMatchingBrackets(json, arrayStart);
        if (arrayEnd == -1) return students;

        String studentsArray = json.substring(arrayStart + 1, arrayEnd);

        int pos = 0;
        while (pos < studentsArray.length()) {
            while (pos < studentsArray.length() && (studentsArray.charAt(pos) == ' ' || studentsArray.charAt(pos) == ',')) {
                pos++;
            }

            if (pos >= studentsArray.length()) break;

            if (studentsArray.charAt(pos) == '{') {
                int studentEnd = findMatchingBrackets(studentsArray, pos);
                if (studentEnd == -1) break;

                String studentObj = studentsArray.substring(pos, studentEnd + 1);
                Student student = parseStudent(studentObj);
                if (student != null) {
                    students.add(student);
                }

                pos = studentEnd + 1;
            } else {
                pos++;
            }
        }

        return students;
    }

    private static Student parseStudent(String studentObj) {
        String name = parseStringValue(studentObj, "name");
        if (name == null) return null;

        List<Book> books = parseBooks(studentObj);
        return new Student(name, books);
    }

    private static List<Book> parseBooks(String studentObj) {
        List<Book> books = new ArrayList<>();

        int booksStart = studentObj.indexOf("\"books\"");
        if (booksStart == -1) return books;

        int arrayStart = studentObj.indexOf("[", booksStart);
        if (arrayStart == -1) return books;

        int arrayEnd = findMatchingBrackets(studentObj, arrayStart);
        if (arrayEnd == -1) return books;

        String booksArray = studentObj.substring(arrayStart + 1, arrayEnd);

        int pos = 0;
        while (pos < booksArray.length()) {
            while (pos < booksArray.length() && (booksArray.charAt(pos) == ' ' || booksArray.charAt(pos) == ',')) {
                pos++;
            }

            if (pos >= booksArray.length()) break;

            if (booksArray.charAt(pos) == '{') {
                int bookEnd = findMatchingBrackets(booksArray, pos);
                if (bookEnd == -1) break;

                String bookObj = booksArray.substring(pos, bookEnd + 1);
                Book book = parseBook(bookObj);
                if (book != null) {
                    books.add(book);
                }

                pos = bookEnd + 1;
            } else {
                pos++;
            }
        }

        return books;
    }

    private static Book parseBook(String bookObj) {
        String title = parseStringValue(bookObj, "title");
        Integer pagesAmount = parseIntValue(bookObj, "pagesAmount");
        Integer publishYear = parseIntValue(bookObj, "publishYear");

        if (title == null || pagesAmount == null || publishYear == null) {
            return null;
        }

        return new Book(title, pagesAmount, publishYear);
    }

    private static Integer parseIntValue(String bookObj, String pagesAmount) {
        String searchKey = "\"" + pagesAmount + "\"";
        int keyIndex = bookObj.indexOf(searchKey);
        if (keyIndex == -1) return null;

        int colonIndex = bookObj.indexOf(":", keyIndex);
        if (colonIndex == -1) return null;

        int start = colonIndex + 1;
        while (start < bookObj.length() && bookObj.charAt(start) == ' ') {
            start++;
        }

        int end = start;
        while (end < bookObj.length() && (Character.isDigit(bookObj.charAt(end)) || bookObj.charAt(end) == '-')) {
            end++;
        }

        if (start == end) return null;

        try {
            return Integer.parseInt(bookObj.substring(start, end));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String parseStringValue(String studentObj, String name) {
        String searchKey = "\"" + name + "\"";
        int keyIndex = studentObj.indexOf(searchKey);
        if (keyIndex == -1) return null;

        int colonIndex = studentObj.indexOf(":", keyIndex);
        if (colonIndex == -1) return null;

        int startQuote = studentObj.indexOf("\"", colonIndex);
        if (startQuote == -1) return null;

        int endQuote = studentObj.indexOf("\"", startQuote + 1);
        if (endQuote == -1) return null;

        return studentObj.substring(startQuote + 1, endQuote);
    }

    private static int findMatchingBrackets(String json, int openPos) {
        if (openPos < 0 || openPos >= json.length()) return -1;

        char openBracket = json.charAt(openPos);
        if (openBracket != '{' && openBracket != '[') return -1;

        int count = 1;
        boolean inString = false;

        for (int i = openPos + 1; i < json.length(); i++) {
            char c = json.charAt(i);

            if (c == '\\' && inString) {
                i++;
                continue;
            }

            if (c == '"') {
                inString = !inString;
                continue;
            }

            if (inString) continue;

            if (c == '{' || c == '[') {
                count++;
            } else if (c == '}' || c == ']') {
                count--;
                if (count == 0) {
                    return i;
                }
            }
        }

        return -1;
    }
}
