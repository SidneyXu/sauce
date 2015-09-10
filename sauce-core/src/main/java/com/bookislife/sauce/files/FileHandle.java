package com.bookislife.sauce.files;

import com.bookislife.sauce.SourceHandle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by mrseasons on 2015/09/08.
 */
public abstract class FileHandle extends SourceHandle {


    public static final int DEFAULT_BUFFER_SIZE = 4098;
    public static final String DEFAULT_ENCODING = "UTF-8";

    public abstract boolean mkdirs();

    public abstract boolean isDirectory();

    public abstract boolean isFile();

    public abstract boolean exists();

    public abstract boolean notExist();

    public abstract File toFile();

    public abstract File[] listFiles();

    public abstract boolean delete();

    public abstract void deleteChildren();

    public abstract void deleteDirectory();

    public enum Type {
        ABSOLUTE, INTERNAL, EXTERNAL, CACHE
    }

    public abstract InputStream getInputStream() throws IOException;

    public abstract OutputStream getOutputStream(boolean append) throws IOException;

    public abstract void writeBytes(byte[] data) throws IOException;

    public abstract byte[] readBytes() throws IOException;

    public abstract void writeString(String data) throws IOException;

    public abstract void writeString(String data, String encoding) throws IOException;

    public abstract boolean tryWriteString(String data);

    public abstract boolean tryWriteString(String data, String encoding);

    public abstract String readString() throws IOException;

    public abstract String readString(String encoding) throws IOException;

    public abstract String tryReadString();

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

    public abstract boolean moveDirectoryTo(FileHandle target);

    public abstract boolean copyTo(FileHandle target);

    public abstract boolean copyDirectoryTo(FileHandle target);
}
