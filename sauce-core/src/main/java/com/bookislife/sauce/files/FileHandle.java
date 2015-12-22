package com.bookislife.sauce.files;

import com.bookislife.sauce.SourceHandle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * The class represents a util to handle the specified file source.
 *
 * @author SidneyXu
 */
public abstract class FileHandle extends SourceHandle {

    /**
     * The default buffer size that used when writing and reading.
     */
    public static final int DEFAULT_BUFFER_SIZE = 4098;

    /**
     * The default file encoding is utf-8.
     */
    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * Make directories if possible.
     *
     * @return True is success. False otherwise.
     */
    public abstract boolean mkdirs();

    /**
     * Check whether the source is directory or not.
     *
     * @return True if is directory. False otherwise.
     */
    public abstract boolean isDirectory();

    /**
     * Check whether the source is file or not.
     *
     * @return True if is file. False otherwise.
     */
    public abstract boolean isFile();

    /**
     * Check whether the source exists.
     *
     * @return True if exists. False otherwise.
     */
    public abstract boolean exists();

    /**
     * Check whether the source not exist.
     *
     * @return True if not exist. False otherwise.
     */
    public abstract boolean notExist();

    /**
     * Get the file represents the source.
     *
     * @return the file
     */
    public abstract File toFile();

    /**
     * List the files below the source.
     *
     * @return An array of files or null if nothing exists.
     */
    public abstract File[] listFiles();

    /**
     * List the handles below the source.
     *
     * @return An array of handles or null if nothing exists.
     */
    public abstract FileHandle[] listHandles();

    /**
     * Delete the source if source is a file.
     *
     * @return True if succeeded. False otherwise.
     */
    public abstract boolean delete();

    /**
     * Delete all children in the source if source is a directory.
     */
    public abstract void deleteChildren();

    /**
     * Delete the source and all its children. Available for both file and directory.
     */
    public abstract void deleteDirectory();

    /**
     * Get the inputStream of the source.
     *
     * @return the inputStream
     * @throws IOException
     */
    public abstract InputStream getInputStream() throws IOException;

    /**
     * Get the outputStream of the source.
     *
     * @param append True if want to append the previous content. False otherwise.
     * @return the outputStream
     * @throws IOException
     */
    public abstract OutputStream getOutputStream(boolean append) throws IOException;

    /**
     * Write some bytes to the source.
     *
     * @param data the bytes for writing
     * @throws IOException
     */
    public abstract void writeBytes(byte[] data) throws IOException;

    /**
     * Try to write some bytes to the source.
     *
     * @param data the bytes for writing
     * @return True if succeed. False otherwise.
     */
    public abstract boolean tryWriteBytes(byte[] data);

    /**
     * Try to read bytes from the source.
     *
     * @return the read bytes or null if source is empty or any error occurs.
     */
    public abstract byte[] tryReadBytes();

    /**
     * Write a string data to the source.
     *
     * @param data the string for writing
     * @throws IOException
     */
    public abstract void writeString(String data) throws IOException;

    /**
     * Write a string data by the specified encoding to the source.
     *
     * @param data     the string for writing
     * @param encoding the encoding of the string
     * @throws IOException
     */
    public abstract void writeString(String data, String encoding) throws IOException;

    /**
     * Try to write a string data to the source.
     *
     * @param data the string for writing
     * @return True if succeed. False otherwise.
     */
    public abstract boolean tryWriteString(String data);

    /**
     * Try to write a string data to the source.
     *
     * @param data     the string for writing
     * @param encoding the encoding of the string
     * @return True if succeed. False otherwise.
     */
    public abstract boolean tryWriteString(String data, String encoding);

    /**
     * Read string with the specified encoding from the source.
     *
     * @param encoding the encoding for parsing the string
     * @return the read string or null if source is empty
     * @throws IOException
     */
    public abstract String readString(String encoding) throws IOException;

    /**
     * Try to read string from the source with the specified encoding.
     *
     * @param encoding the encoding for parsing the string
     * @return the read string or null if source is empty or any error occurs.
     */
    public abstract String tryReadString(String encoding);

    public abstract void writeCSV(List<String[]> csv, char separator) throws IOException;

    public abstract List<String[]> readCSV(char separator) throws IOException;

    public abstract void copyLarge(FileHandle target) throws IOException;

    public abstract List<String> readLines() throws IOException;

    public abstract void writeLines(List<String> lines) throws IOException;

    public abstract void transferTo(OutputStream outputStream) throws IOException;

    public abstract void transferFrom(InputStream inputStream) throws IOException;

    public abstract boolean createNewFile();

    public abstract boolean renameTo(FileHandle target);

    public abstract boolean moveTo(FileHandle target);

    public abstract boolean moveRecursively(FileHandle target);

    public abstract boolean copyTo(FileHandle target);

    public abstract boolean copyRecursively(FileHandle target);

    public abstract String extension();

    public abstract String nameWithoutExtension();

}
