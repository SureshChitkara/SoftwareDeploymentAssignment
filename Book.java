/****************************************SC**********************************
 *****************
 * Name: Suresh Chitkara *
 * Course: Software Development I CEN-3024C-24667 *
 * Date: 4/6/2024 *
 * Description: The Book class represents a book object in the library management system.
 *****************************************SC**********************************
 ******************/

import java.util.Date;

/**
 * The Book class represents a book object in the library management system.
 * It contains information such as title, author, barcode, checkout status, and due date.
 *
 * @author Suresh Chitkara
 * @version 1.0
 */
public class Book {

    private String title;        // SC The title of the book
    private String author;       // SC The author of the book
    private int barcode;         // SC The barcode of the book
    private boolean checkedOut;  // SC Indicates if the book is checked out
    private Date dueDate;        // SC The due date of the book (if checked out)

    /**
     * Constructs a new Book object with the specified title, author, and barcode.
     *
     * @param title   The title of the book
     * @param author  The author of the book
     * @param barcode The barcode of the book
     */
    public Book(String title, String author, int barcode) { // SC Constructor method for book class.
        this.title = title; // SC These lines initialize the title, author, and barcode fields of the Book object being created with the values passed as arguments to the constructor.
        this.author = author;
        this.barcode = barcode;
        this.checkedOut = false;  // SC By default, the book is not checked out
        this.dueDate = null;      // SC By default, there is no due date
    }

    /**
     * Retrieves the title of the book.
     *
     * @return The title of the book
     */
    public String getTitle() { // SC Retrieves the title of the book.

        return title;
    }

    /**
     * Retrieves the author of the book.
     *
     * @return The author of the book
     */
    public String getAuthor() { // SC It retrieves the author of the book.

        return author;
    }

    /**
     * Retrieves the barcode of the book.
     *
     * @return The barcode of the book
     */
    public int getBarcode() { // SC It retrieves the barcode of the book.

        return barcode;
    }

    /**
     * Determines if the book is checked out.
     *
     * @return true if the book is checked out, false otherwise
     */
    public boolean isCheckedOut() { // SC It determines if book is checked out.

        return checkedOut;
    }

    /**
     * Sets the checkout status of the book.
     *
     * @param checkedOut true if the book is checked out, false otherwise
     */
    public void setCheckedOut(boolean checkedOut) { // SC It determines if book is not checked out.

        this.checkedOut = checkedOut;
    }

    /**
     * Retrieves the due date of the book.
     *
     * @return The due date of the book
     */
    public Date getDueDate() { // SC  It retrieves the due date of the book.

        return dueDate;
    }

    /**
     * Sets the due date of the book.
     *
     * @param dueDate The due date of the book
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Returns a string representation of the Book object.
     *
     * @return A string containing the title, author, barcode, checkout status, and due date of the book
     */
    @Override
    public String toString() {
        String dueDateString = (dueDate != null) ? dueDate.toString() : "null";
        return "Title: " + title + ", Author: " + author + ", Barcode: " + barcode + ", Checked Out: " + checkedOut + ", Due Date: " + dueDateString;
    }
}
