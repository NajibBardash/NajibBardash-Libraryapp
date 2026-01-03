package se.yrgo.integrations.viewmodel;

import java.util.Objects;

public class BookView {
    private String title;
    private String author;
    private String isbn;
    private String category;

    public BookView(String title, String author, String isbn, String category) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BookView)) return false;
        BookView bookView = (BookView) o;
        return Objects.equals(title, bookView.title)
                && Objects.equals(author, bookView.author)
                && Objects.equals(isbn, bookView.isbn)
                && Objects.equals(category, bookView.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, isbn, category);
    }

    @Override
    public String toString() {
        return "BookView{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
