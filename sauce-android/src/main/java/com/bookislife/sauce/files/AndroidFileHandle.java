package com.bookislife.sauce.files;

import android.content.Context;
import com.bookislife.sauce.SauceAndroid;
import com.bookislife.sauce.utils.AndroidIOUtils;
import com.bookislife.sauce.utils.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrseasons on 2015/09/08.
 */
public class AndroidFileHandle extends FileHandle {

    private AndroidFileHandles.FileType fileType;
    private File file;

    private static Context context = SauceAndroid.getInstance().context;

    AndroidFileHandle(AndroidFileHandles.FileType type, String fileName) {
        this.fileType = type;
        switch (type) {
            case SDCARD:
                File sdcard = AndroidIOUtils.getSDCardDir();
                if (null == sdcard)
                    throw new RuntimeException("Cannot found any sd card available.");
                file = new File(sdcard, fileName);
                break;
            case FILES:
                file = new File(context.getFilesDir(), fileName);
                break;
            case CACHE:
                file = new File(context.getCacheDir(), fileName);
                break;
            case ASSETS:
                file = new File("//assets/" + fileName);
                break;
            default:
                file = new File(fileName);
                break;
        }
    }

    AndroidFileHandle(File file) {
        this.fileType = AndroidFileHandles.FileType.ABSOLUTE;
        this.file = file;
    }

    @Override
    public boolean mkdirs() {
        return file.mkdirs();
    }

    @Override
    public boolean isDirectory() {
        return file.isDirectory();
    }

    @Override
    public boolean isFile() {
        return file.isFile();
    }

    @Override
    public boolean exists() {
        return file.exists();
    }

    @Override
    public boolean notExist() {
        return !exists();
    }

    @Override
    public File toFile() {
        return file;
    }

    @Override
    public File[] listFiles() {
        return file.listFiles();
    }

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

    @Override
    public boolean delete() {
        switch (fileType) {
            case ASSETS:
                throw new RuntimeException("Location is read only.");
            default:
                return IOUtils.deleteFile(file);
        }
    }

    @Override
    public void deleteChildren() {
        switch (fileType) {
            case ASSETS:
                throw new RuntimeException("Location is read only.");
            default:
                IOUtils.deleteChildren(file);
        }
    }

    @Override
    public void deleteDirectory() {
        switch (fileType) {
            case ASSETS:
                throw new RuntimeException("Location is read only.");
            default:
                IOUtils.recursiveDelete(file);
        }
    }

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

    @Override
    public OutputStream getOutputStream(boolean append) throws IOException {
        switch (fileType) {
            case ASSETS:
                throw new RuntimeException("Location is read only.");
            default:
                return new FileOutputStream(file, append);
        }
    }

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

    @Override
    public void writeString(String data) throws IOException {
        writeString(data, DEFAULT_ENCODING);
    }

    @Override
    public void writeString(String data, String encoding) throws IOException {
        byte[] bytes = data.getBytes(encoding);
        writeBytes(bytes);
    }

    @Override
    public boolean tryWriteString(String data) {
        return tryWriteString(data, DEFAULT_ENCODING);
    }

    @Override
    public boolean tryWriteString(String data, String encoding) {
        try {
            writeString(data, encoding);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String readString() throws IOException {
        return readString(DEFAULT_ENCODING);
    }

    @Override
    public String readString(String encoding) throws IOException {
        byte[] data = readBytes();

        if (data != null) {
            return new String(data, encoding);
        }
        return null;
    }

    @Override
    public String tryReadString() {
        return tryReadString(DEFAULT_ENCODING);
    }

    @Override
    public String tryReadString(String encoding) {
        try {
            return readString(encoding);
        } catch (IOException e) {
            return null;
        }
    }

    public void writeJSONObject(JSONObject jsonObject) throws IOException {
        writeString(jsonObject.toString());
    }

    public boolean tryWriteJSONObject(JSONObject jsonObject) {
        try {
            writeJSONObject(jsonObject);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public JSONObject readJSONObject() throws IOException, JSONException {
        String content = readString();
        if (null != content) {
            return new JSONObject(content);
        }
        return null;
    }

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
}
