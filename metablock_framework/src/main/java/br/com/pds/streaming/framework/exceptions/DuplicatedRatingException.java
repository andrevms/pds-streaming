package br.com.pds.streaming.framework.exceptions;

public class DuplicatedRatingException extends Exception {

    public DuplicatedRatingException() {
        super("It's not possible to create more than one rating with the same user for the same movie or tv show.");
    }
}
