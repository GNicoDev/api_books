package com.books.books.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenreTest {

    @Test
    void isEnumValue_when_the_string_doesnt_match() {
        String value = "COMEDY";
        Exception exception = assertThrows(IllegalArgumentException.class,()->{
            Genre.valueOf(value);
        });
        String expectedMessage = "No enum constant com.books.books.entity.Genre.COMEDY";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void isEnumValue_when_the_string_is_match() {
        String value = "DRAMA";
        assertTrue(Genre.isEnumValue(value));
    }
////////////////////////////////////////////////////////////////////////////
    @Test
    void convertValue_return_Genre_becose_Value_exist() {
        String value = "DRAMA";
        Genre genre = Genre.convertValue(value);
        assertEquals(Genre.DRAMA,genre);
    }

    @Test
    void convertValue_return_null_because_Value_not_exist() {
        String value = "COMEDY";
        Genre genre = Genre.convertValue(value);
        assertNull(genre);
    }
}