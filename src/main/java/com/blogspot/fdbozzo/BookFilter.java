package com.blogspot.fdbozzo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookFilter {
    private static final String FILE_PATH = "src/main/resources/books.json";

    public static void main(String[] args) {
        String filter;
        if (args.length == 0) {
            filter = "";
        } else {
            filter = args[0];
        }

        List<Book> books = getBookList();
        System.out.println("\n--- Lista de libros inicial");
        books.forEach(System.out::println);

        if (books.size() > 0) {
            Optional<BookDate> optionalBookDate = filter(filter, books);
        }

        System.out.println("\n--- Lista de libros final");
        books.forEach(System.out::println);

    }

    private static List<Book> getBookList() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(FILE_PATH);
            List<Book> books = mapper.readValue(file, new TypeReference<>() {
            });
            return books;

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static Optional<BookDate> filter(String filter, List<Book> books) {
        // 1.
        System.out.println("\n--- Libros sin fecha de publicación");
        showBookListWithoutPublishingDate(books);

        // 2.
        System.out.println("\n--- Libro filtrado con mayor fecha de publicación");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDateTime now = LocalDateTime.now();

        Optional<BookDate> filteredBook = books.stream()
                .filter(book -> {
                    String bookPubTimestamp = book.getPublicationTimestamp();
                    if (bookPubTimestamp == null || bookPubTimestamp.length() == 0) {
                        // Descarta libros sin fecha de publicación
                        return false;
                    } else if (filter == null || filter.isEmpty()) {
                        // Si no se indica filtro, se toman todos los libros
                        return true;
                    } else {
                        // Se filtra según la selección
                        return book.getTitle().contains(filter)
                                || book.getSummary().contains(filter)
                                || book.getAuthor().getBio().contains(filter);
                    }
                })
                .map(book -> new BookDate(book, now.format(formatter)))
                .max(Comparator.comparingLong(bookDate -> Long.parseLong(bookDate.getPublicationTimestamp())));

        filteredBook.ifPresent(System.out::println);

        // 3.
        books.sort(Comparator.comparingInt(book -> book.getAuthor().getBio().length()));

        // Se retorna el valor del punto 2.
        return filteredBook;
    }

    private static void showBookListWithoutPublishingDate(List<Book> books) {
        books.stream()
                .filter(book -> book.getPublicationTimestamp() == null)
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

}
