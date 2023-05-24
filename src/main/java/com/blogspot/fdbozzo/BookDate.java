package com.blogspot.fdbozzo;

class BookDate extends Book {
    private final String timestamp;

    public BookDate(Book book, String timestamp) {
        this.setId(book.getId());
        this.setTitle(book.getTitle());
        this.setPublicationTimestamp(book.getPublicationTimestamp());
        this.setPages(book.getPages());
        this.setSummary(book.getSummary());
        this.setAuthor(book.getAuthor());
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return super.toString() + " timestamp='" + timestamp + '\'';
    }
}