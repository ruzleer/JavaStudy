import org.json.JSONArray;
import org.json.JSONObject;

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
            String fileInputStream = Files.readString(Paths.get("C:\\Users\\fursova.v\\OpenideProjects\\untitled\\src\\students.txt"));

            JSONObject json = new JSONObject(fileInputStream);
            JSONArray students = json.getJSONArray("students");

            List<Student> studentsInfo = new ArrayList<>();

            for(int i = 0; i < students.length(); i++){
                JSONObject studentBlock = students.getJSONObject(i);
                String name = studentBlock.getString("name");

                List<Book> booksInfo = new ArrayList<>();

                JSONArray books = studentBlock.getJSONArray("books");
                for(int j = 0; j < books.length(); j++){
                    JSONObject booksBlock = books.getJSONObject(j);
                    String title = booksBlock.getString("title");
                    int pagesAmount = booksBlock.getInt("pagesAmount");
                    int publishYear = booksBlock.getInt("publishYear");

                    booksInfo.add(new Book(title, pagesAmount, publishYear));
                }

                studentsInfo.add(new Student(name, booksInfo));
            }

            studentsInfo.stream()
                    .peek(System.out::println) // Вывести в консоль каждого студента (переопределите toString)
                    .map(Student::getStudentsBooks) // Получить для каждого студента список книг
                    .flatMap(Collection::stream) //Получить книги
                    .sorted(Comparator.comparingInt(Book::getPagesAmount)) //Отсортировать книги по количеству страниц (Не забывайте про условия для сравнения объектов)
                    .distinct() //Оставить только уникальные книги
                    .filter(book -> book.getPublishYear() > 2000)//Отфильтровать книги, оставив только те, которые были выпущены после 2000 года
                    .limit(3) //Ограничить стрим на 3 элементах
                    .map(Book::getPublishYear) // Получить из книг годы выпуска
                    .findFirst() // При помощи методов короткого замыкания (почитайте самостоятельно что это такое) вернуть Optional от года
                    .ifPresentOrElse(  // При помощи методов получения значения из Optional вывести в консоль год выпуска найденной книги, либо запись о том, что такая книга отсутствует
                            year -> System.out.println("Год выпуска: " + year),
                            () -> System.out.println("Такая книга отсутствует")
                    );
                    //.forEach(System.out::println);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
