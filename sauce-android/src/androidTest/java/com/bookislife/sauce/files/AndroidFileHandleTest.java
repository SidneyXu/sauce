package com.bookislife.sauce.files;

import com.bookislife.sauce.BaseTestCase;
import com.bookislife.sauce.Sauce;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mrseasons on 2015/09/10.
 */
public class AndroidFileHandleTest extends BaseTestCase {

    private AndroidFileHandles files;

    private String testDirectory = "sauceTest";
    private FileHandle rootHandle;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        files = (AndroidFileHandles) Sauce.files;
        rootHandle = files.sdcard(testDirectory);
        rootHandle.mkdirs();
    }

    public void testMkdirs() throws Exception {

    }

    public void testIsDirectory() throws Exception {

    }

    public void testIsFile() throws Exception {

    }

    public void testExists() throws Exception {

    }

    public void testNotExist() throws Exception {

    }

    public void testToFile() throws Exception {

    }

    public void testListFiles() throws Exception {

    }

    public void testListHandles() throws Exception {

    }

    public void testDelete() throws Exception {

    }

    public void testDeleteChildren() throws Exception {

    }

    public void testDeleteDirectory() throws Exception {

    }

    public void testGetInputStream() throws Exception {

    }

    public void testGetOutputStream() throws Exception {

    }

    public void testWriteAndReadBytes() throws Exception {
        String path = testDirectory + "/" + "foo.data";
        byte[] expected = "hello world".getBytes();
        FileHandle writeHandle = files.sdcard(path);
        writeHandle.writeBytes(expected);

        FileHandle readHandle = files.sdcard(path);
        assertThat(readHandle.readBytes()).isEqualTo(expected);
    }

    public void testWriteAndReadString() throws Exception {
        String path = testDirectory + "/" + "foo.txt";

        String expected = "hello world, 你好，こんにちは";
        FileHandle writeHandle = files.sdcard(path);
        writeHandle.writeString(expected);

        FileHandle readHandle = files.sdcard(path);
        assertThat(readHandle.readString()).isEqualTo(expected);
    }

    public void testWriteAndReadStringWithEncoding() throws Exception {
        String path = testDirectory + "/" + "foo.txt";

        String expected = "こんにちは";
        FileHandle writeHandle = files.sdcard(path);
        writeHandle.writeString(expected, "shift-jis");

        FileHandle readHandle = files.sdcard(path);
        assertThat(readHandle.readString()).isNotEqualTo(expected);
        assertThat(readHandle.readString("gb2312")).isNotEqualTo(expected);
        assertThat(readHandle.readString("shift-jis")).isEqualTo(expected);
    }

    public void testTryWriteString() throws Exception {

    }

    public void testTryWriteString1() throws Exception {

    }


    public void testTryReadString() throws Exception {

    }

    public void testTryReadString1() throws Exception {

    }

    public void testWriteAndReadJSONObject() throws Exception {
        String path = testDirectory + "/" + "foo.json";

        JSONObject expected = new JSONObject();
        expected.put("foo", "bar");
        expected.put("x", 1);
        AndroidFileHandle writeHandle = (AndroidFileHandle) files.sdcard(path);
        writeHandle.writeJSONObject(expected);

        AndroidFileHandle readHandle = (AndroidFileHandle) files.sdcard(path);
        JSONObject actual = readHandle.readJSONObject();
        assertThat(actual.getString("foo")).isEqualTo("bar");
        assertThat(actual.getInt("x")).isEqualTo(1);
    }

    public void testTryWriteJSONObject() throws Exception {

    }

    public void testTryReadJSONObject() throws Exception {

    }

    public void testWriteCSV() throws Exception {

    }

    public void testReadCSV() throws Exception {

    }

    public void atestCopyLarge() throws Exception {
        String path = testDirectory + "/" + "source.txt";

        String expected = "hello world";
        FileHandle sourceHandle = files.sdcard(path);
        sourceHandle.writeString(expected);

        FileHandle targetHandle = files.absolute(testDirectory + "/" + "target.txt");
        targetHandle.createNewFile();
        sourceHandle.copyLarge(targetHandle);

        assertThat(targetHandle.readString()).isEqualTo(expected);
    }

    public void testWriteAndReadLines() throws Exception {
        String path = testDirectory + "/" + "foo.txt";

        List<String> lines = Arrays.asList("line1", "line2", "line3", "", "line4");
        FileHandle writeHandle = files.sdcard(path);
        writeHandle.writeLines(lines);

        FileHandle readHandle = files.sdcard(path);
        List<String> actual = readHandle.readLines();
        assertThat(actual).hasSize(lines.size()).isEqualTo(lines);
    }

    public void atestTransferTo() throws Exception {
        String path = testDirectory + "/" + "source.txt";

        String expected = "hello world";
        FileHandle sourceHandle = files.sdcard(path);
        sourceHandle.writeString(expected);

        FileHandle targetHandle = files.absolute(testDirectory + "/" + "target.txt");
        targetHandle.createNewFile();
        sourceHandle.transferTo(targetHandle.getOutputStream(false));

        assertThat(targetHandle.readString()).isEqualTo(expected);
    }

    public void atestTransferFrom() throws Exception {
        String path = testDirectory + "/" + "source.txt";

        String expected = "hello world";
        FileHandle sourceHandle = files.sdcard(path);
        sourceHandle.writeString(expected);

        FileHandle targetHandle = files.absolute(testDirectory + "/" + "target.txt");
        targetHandle.createNewFile();
        targetHandle.transferFrom(sourceHandle.getInputStream());

        assertThat(targetHandle.readString()).isEqualTo(expected);
    }

    public void testCreateNewFile() throws Exception {

    }

    public void testRenameTo() throws Exception {

    }

    public void testMoveTo() throws Exception {

    }

    public void testMoveDirectoryTo() throws Exception {

    }

    public void testCopyTo() throws Exception {

    }

    public void testCopyDirectoryTo() throws Exception {

    }
}