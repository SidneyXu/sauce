package com.bookislife.sauce.files;

import android.content.Context;
import android.net.Uri;
import com.bookislife.sauce.SauceAndroid;
import com.bookislife.sauce.utils.AndroidIOUtils;
import com.bookislife.sauce.utils.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The class represents a util to handle the file source on the Android platform.
 *
 * @author SidneyXu
 */
public class AndroidFileHandle extends FileHandle {

    private AndroidFileHandles.FileType fileType;
    private File file;

    private static Context context = SauceAndroid.getInstance().context;

    /**
     * Construct the AndroidFileHandle instance.
     *
     * @param type     the specified type.@see AndroidFileHandles.FileType
     * @param fileName the full file name
     */
    AndroidFileHandle(AndroidFileHandles.FileType type, String fileName) {
        this.fileType = type;
        switch (type) {
            case SDCARD:
                File sdcard = AndroidIOUtils.getSDCardDir();
                if (null == sdcard)
                    throw new RuntimeException("Unable to find any sd card available.");
                file = new File(sdcard, fileName);
                break;
            case FILES:
                file = new File(context.getFilesDir(), fileName);
                break;
            case CACHE:
                file = new File(context.getCacheDir(), fileName);
                break;
            case ASSETS:
                //                file = new File("//assets/" + fileName);
                file = new File(fileName);
                break;
            default:
                file = new File(fileName);
                break;
        }
    }

    /**
     * Construct the AndroidFileHandle instance with absolute file type.
     *
     * @param file the file to be wrapped
     */
    AndroidFileHandle(File file) {
        this.fileType = AndroidFileHandles.FileType.ABSOLUTE;
        this.file = file;
    }

    /**
     * Make directories if possible.
     *
     * @return True is success. False otherwise.
     */
    @Override
    public boolean mkdirs() {
        return file.mkdirs();
    }

    /**
     * Check whether the source is directory or not.
     *
     * @return True if is directory. False otherwise.
     */
    @Override
    public boolean isDirectory() {
        return file.isDirectory();
    }

    /**
     * Check whether the source is file or not.
     *
     * @return True if is file. False otherwise.
     */
    @Override
    public boolean isFile() {
        return file.isFile();
    }

    /**
     * Check whether the source exists.
     *
     * @return True if exists. False otherwise.
     */
    @Override
    public boolean exists() {
        return file.exists();
    }

    /**
     * Check whether the source not exist.
     *
     * @return True if not exist. False otherwise.
     */
    @Override
    public boolean notExist() {
        return !exists();
    }

    /**
     * Get the file represents the source.
     *
     * @return the file
     */
    @Override
    public File toFile() {
        return file;
    }

    /**
     * List the files below the source.
     *
     * @return An array of files or null if nothing exists.
     */
    @Override
    public File[] listFiles() {
        return file.listFiles();
    }

    /**
     * List the handles below the source.
     *
     * @return An array of handles or null if nothing exists.
     */
    @Override
    public FileHandle[] listHandles() {
        File[] files = listFiles();
        if (files != null) {
            int len = files.length;
            FileHandle[] handles = new FileHandle[len];
            for (int i = 0; i < len; i++) {
                handles[i] = new AndroidFileHandle(files[i]);
            }
            return handles;
        }
        return null;
    }

    /**
     * Delete the source if source is a file.
     *
     * @return True if succeeded. False otherwise.
     */
    @Override
    public boolean delete() {
        switch (fileType) {
            case ASSETS:
                throw new UnsupportedOperationException("Location is read only.");
            default:
                return IOUtils.deleteFile(file);
        }
    }

    /**
     * Delete all children in the source if source is a directory.
     */
    @Override
    public void deleteChildren() {
        switch (fileType) {
            case ASSETS:
                throw new UnsupportedOperationException("Location is read only.");
            default:
                IOUtils.deleteChildren(file);
        }
    }

    /**
     * Delete the source and all its children. Available for both file and directory.
     */
    @Override
    public void deleteDirectory() {
        switch (fileType) {
            case ASSETS:
                throw new UnsupportedOperationException("Location is read only.");
            default:
                IOUtils.recursiveDelete(file);
        }
    }

    /**
     * Get the inputStream of the source.
     *
     * @return the inputStream
     * @throws IOException
     */
    @Override
    public InputStream getInputStream() throws IOException {
        switch (fileType) {
            case ASSETS:
                return context.getAssets().open(
                        file.getCanonicalPath().replaceFirst("/", "")
                );
            default:
                return new FileInputStream(file);
        }
    }

    /**
     * Get the outputStream of the source.
     *
     * @param append True if want to append the previous content. False otherwise.
     * @return the outputStream
     * @throws IOException
     */
    @Override
    public OutputStream getOutputStream(boolean append) throws IOException {
        switch (fileType) {
            case ASSETS:
                throw new RuntimeException("Location is read only.");
            default:
                return new FileOutputStream(file, append);
        }
    }

    /**
     * Write some bytes to the source.
     *
     * @param data the bytes for writing
     * @throws IOException
     */
    @Override
    public void writeBytes(byte[] data) throws IOException {
        OutputStream out = null;
        try {
            out = getOutputStream(false);
            out.write(data);
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * Read bytes from the source.
     *
     * @return the read bytes or null if source is empty
     * @throws IOException
     */
    @Override
    public byte[] readBytes() throws IOException {
        InputStream in = null;
        byte[] buffer = new byte[1024];
        int len;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            in = getInputStream();
            while ((len = in.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
        } finally {
            if (null != in) {
                in.close();
            }
        }
    }

    /**
     * Try to write some bytes to the source.
     *
     * @param data the bytes for writing
     * @return True if succeed. False otherwise.
     */
    @Override
    public boolean tryWriteBytes(final byte[] data) {
        try {
            writeBytes(data);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Try to read bytes from the source.
     *
     * @return the read bytes or null if source is empty or any error occurs.
     */
    @Override
    public byte[] tryReadBytes() {
        try {
            return readBytes();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Write a string data to the source.
     *
     * @param data the string for writing
     * @throws IOException
     */
    @Override
    public void writeString(String data) throws IOException {
        writeString(data, DEFAULT_ENCODING);
    }

    /**
     * Write a string data by the specified encoding to the source.
     *
     * @param data     the string for writing
     * @param encoding the encoding of the string
     * @throws IOException
     */
    @Override
    public void writeString(String data, String encoding) throws IOException {
        byte[] bytes = data.getBytes(encoding);
        writeBytes(bytes);
    }

    /**
     * Try to write a string data to the source.
     *
     * @param data the string for writing
     * @return True if succeed. False otherwise.
     */
    @Override
    public boolean tryWriteString(String data) {
        return tryWriteString(data, DEFAULT_ENCODING);
    }

    /**
     * Try to write a string data to the source.
     *
     * @param data     the string for writing
     * @param encoding the encoding of the string
     * @return True if succeed. False otherwise.
     */
    @Override
    public boolean tryWriteString(String data, String encoding) {
        try {
            writeString(data, encoding);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Read string from the source.
     *
     * @return the read string or null if source is empty
     * @throws IOException
     */
    @Override
    public String readString() throws IOException {
        return readString(DEFAULT_ENCODING);
    }

    /**
     * Read string with the specified encoding from the source.
     *
     * @param encoding the encoding for parsing the string
     * @return the read string or null if source is empty
     * @throws IOException
     */
    @Override
    public String readString(String encoding) throws IOException {
        byte[] data = readBytes();

        if (data != null) {
            return new String(data, encoding);
        }
        return null;
    }

    /**
     * Try to read string from the source.
     *
     * @return the read string or null if source is empty or any error occurs.
     */
    @Override
    public String tryReadString() {
        return tryReadString(DEFAULT_ENCODING);
    }

    /**
     * Try to read string from the source with the specified encoding.
     *
     * @param encoding the encoding for parsing the string
     * @return the read string or null if source is empty or any error occurs.
     */
    @Override
    public String tryReadString(String encoding) {
        try {
            return readString(encoding);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Write a json to the source.
     *
     * @param jsonObject the json for writing
     * @throws IOException
     */
    public void writeJSONObject(JSONObject jsonObject) throws IOException {
        writeString(jsonObject.toString());
    }

    /**
     * Try to write a json to the source.
     *
     * @param jsonObject the json for writing
     * @return True if succeed. False otherwise.
     */
    public boolean tryWriteJSONObject(JSONObject jsonObject) {
        try {
            writeJSONObject(jsonObject);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Read json from the source.
     *
     * @return the read json or null if source is empty
     * @throws IOException
     */
    public JSONObject readJSONObject() throws IOException, JSONException {
        String content = readString();
        if (null != content) {
            return new JSONObject(content);
        }
        return null;
    }

    /**
     * Try to read json from the source.
     *
     * @return the read json or null if source is empty or any error occurs.
     */
    public JSONObject tryReadJSONObject() {
        try {
            return readJSONObject();
        } catch (IOException e) {
            return null;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public void writeCSV(List<String[]> csv, char separator) throws IOException {

    }

    @Override
    public List<String[]> readCSV(char separator) throws IOException {
        return null;
    }

    @Override
    public void copyLarge(FileHandle target) throws IOException {
        InputStream in = getInputStream();
        OutputStream out = target.getOutputStream(false);
        if (in instanceof FileInputStream
                && out instanceof FileOutputStream) {
            IOUtils.copyLarge((FileInputStream) in, (FileOutputStream) out);
        } else {
            throw new IllegalArgumentException("Illegal InputStream" +
                    " or OutputStream");
        }
    }

    @Override
    public List<String> readLines() throws IOException {
        InputStream in = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;
        List<String> lines = new ArrayList<>();
        try {
            in = getInputStream();
            isr = new InputStreamReader(in);
            reader = new BufferedReader(isr);
            String s;
            while ((s = reader.readLine()) != null) {
                lines.add(s);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (in != null) {
                in.close();
            }
        }
        return lines;
    }

    @Override
    public void writeLines(List<String> lines) throws IOException {
        OutputStream out = null;
        OutputStreamWriter osw = null;
        BufferedWriter writer = null;
        try {
            out = getOutputStream(false);
            osw = new OutputStreamWriter(out);
            writer = new BufferedWriter(osw);
            int size = lines.size();
            for (int i = 0; i < size; i++) {
                writer.write(lines.get(i));
                if (i != size - 1) {
                    writer.newLine();
                }
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (osw != null) {
                osw.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    @Override
    public void transferTo(OutputStream outputStream) throws IOException {
        InputStream inputStream = getInputStream();
        IOUtils.copyTo(inputStream, outputStream, DEFAULT_BUFFER_SIZE);
    }

    @Override
    public void transferFrom(InputStream inputStream) throws IOException {
        OutputStream outputStream = getOutputStream(false);
        IOUtils.copyTo(inputStream, outputStream, DEFAULT_BUFFER_SIZE);
    }

    @Override
    public boolean createNewFile() {
        try {
            return file.createNewFile();
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean renameTo(FileHandle target) {
        boolean success = file.renameTo(target.toFile());
        if (success) {
            file = target.toFile();
        }
        return success;
    }

    @Override
    public boolean moveTo(FileHandle target) {
        if (target.exists()) {
            return false;
        }
        boolean success = copyTo(target);
        if (success) {
            delete();
            file = target.toFile();
        }
        return success;
    }

    @Override
    public boolean moveDirectoryTo(FileHandle target) {
        if (target.exists()) {
            return false;
        }
        boolean success = copyDirectoryTo(target);
        if (success) {
            deleteDirectory();
            file = target.toFile();
        }
        return success;
    }

    @Override
    public boolean copyTo(FileHandle target) {
        if (target.exists()) {
            throw new RuntimeException("File already exists.");
        }
        try {
            transferTo(target.getOutputStream(false));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean copyDirectoryTo(FileHandle target) {
        if (isDirectory() && exists()) {
            target.mkdirs();
            FileHandle[] handles = listHandles();
            for (FileHandle fh : handles) {
                FileHandle newHandle = new AndroidFileHandle(new File(target.toFile(),
                        fh.toFile().getName()));
                if (fh.isDirectory()) {
                    fh.copyDirectoryTo(newHandle);
                } else {
                    fh.copyTo(newHandle);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String extension() {
        String name = file.getName();
        int dotIndex = name.lastIndexOf('.');
        if (dotIndex == -1) return name;
        return name.substring(0, dotIndex);
    }

    public Uri toUri() {
        switch (fileType) {
            case ASSETS:
                return Uri.parse("file:///android_asset/" + file.getPath());
            default:
                return Uri.fromFile(file);
        }
    }
}
