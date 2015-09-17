package com.bookislife.sauce.files;

import com.bookislife.sauce.BaseTestCase;
import com.bookislife.sauce.Sauce;
import org.assertj.core.api.iterable.Extractor;
import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by SidneyXu on 2015/09/10.
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
        rootHandle.deleteChildren();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        rootHandle.delete();
    }

    public void testMkdirs() throws Exception {
        String path = testDirectory + "/" + "tempDirectory";
        FileHandle handle = files.sdcard(path);

        assertThat(handle.notExist()).isEqualTo(true);
        assertThat(handle.isDirectory()).isEqualTo(false);
        assertThat(handle.isFile()).isEqualTo(false);

        handle.mkdirs();

        assertThat(handle.exists()).isEqualTo(true);
        assertThat(handle.isDirectory()).isEqualTo(true);
        assertThat(handle.isFile()).isEqualTo(false);

        handle.deleteDirectory();

        assertThat(handle.notExist()).isEqualTo(true);
        assertThat(handle.isDirectory()).isEqualTo(false);
        assertThat(handle.isFile()).isEqualTo(false);
    }

    public void testCreateNewFile() throws Exception {
        String path = testDirectory + "/" + "temp";
        FileHandle handle = files.sdcard(path);

        assertThat(handle.notExist()).isEqualTo(true);
        assertThat(handle.isDirectory()).isEqualTo(false);
        assertThat(handle.isFile()).isEqualTo(false);

        handle.createNewFile();

        assertThat(handle.exists()).isEqualTo(true);
        assertThat(handle.isDirectory()).isEqualTo(false);
        assertThat(handle.isFile()).isEqualTo(true);

        handle.delete();

        assertThat(handle.notExist()).isEqualTo(true);
        assertThat(handle.isDirectory()).isEqualTo(false);
        assertThat(handle.isFile()).isEqualTo(false);
    }

    public void testToFile() throws Exception {
        String path = testDirectory + "/" + "foo.txt";
        FileHandle handle = files.sdcard(path);
        assertThat(handle.toFile().getName()).isEqualTo("foo.txt");
    }

    public void testListFiles() throws Exception {
        /*
            root/
                dir1/
                    dir1_1/
                        text1_1.txt
                    text1.txt
                dir2/
                    text2.txt
         */
        String path1 = testDirectory + "/" + "dir1";
        String path1_1 = testDirectory + "/" + "dir1_1";
        String path2 = testDirectory + "/" + "dir2";
        String file1 = "text1.txt";
        String file1_1 = "text1_1.txt";
        String file2 = "text2.txt";

        rootHandle.deleteChildren();

        FileHandle handle1 = files.sdcard(path1);
        FileHandle handle1_1 = files.absolute(handle1, path1_1);
        FileHandle handle2 = files.sdcard(path2);
        handle1_1.mkdirs();
        handle2.mkdirs();

        FileHandle fileHandle1 = files.absolute(handle1, file1);
        FileHandle fileHandle1_1 = files.absolute(handle1_1, file1_1);
        FileHandle fileHandle2 = files.absolute(handle2, file2);
        fileHandle1.createNewFile();
        fileHandle1_1.createNewFile();
        fileHandle2.createNewFile();

        assertThat(rootHandle.listFiles()).hasSize(2)
                .extracting(new Extractor<File, String>() {
                    @Override
                    public String extract(final File file) {
                        return file.getName();
                    }
                }).containsExactly("dir1", "dir2");
        assertThat(handle1.listFiles()).hasSize(2);
        assertThat(handle1_1.listFiles()).hasSize(1);
        assertThat(handle2.listFiles()).hasSize(1);

        handle1.deleteChildren();
        assertThat(rootHandle.listFiles()).hasSize(2)
                .extracting(new Extractor<File, String>() {
                    @Override
                    public String extract(final File file) {
                        return file.getName();
                    }
                }).containsExactly("dir1", "dir2");

        handle2.delete();
        assertThat(rootHandle.listFiles()).hasSize(2)
                .extracting(new Extractor<File, String>() {
                    @Override
                    public String extract(final File file) {
                        return file.getName();
                    }
                }).containsExactly("dir1", "dir2");

        handle2.deleteDirectory();
        assertThat(rootHandle.listFiles()).hasSize(1)
                .extracting(new Extractor<File, String>() {
                    @Override
                    public String extract(final File file) {
                        return file.getName();
                    }
                }).containsExactly("dir1");

        rootHandle.deleteChildren();

        assertThat(rootHandle.listFiles()).hasSize(0);
    }

    public void testListHandles() throws Exception {
        /*
            root/
                dir1/
                    dir1_1/
                        text1_1.txt
                    text1.txt
                dir2/
                    text2.txt
         */
        String path1 = testDirectory + "/" + "dir1";
        String path1_1 = testDirectory + "/" + "dir1_1";
        String path2 = testDirectory + "/" + "dir2";
        String file1 = "text1.txt";
        String file1_1 = "text1_1.txt";
        String file2 = "text2.txt";

        rootHandle.deleteChildren();

        FileHandle handle1 = files.sdcard(path1);
        FileHandle handle1_1 = files.absolute(handle1, path1_1);
        FileHandle handle2 = files.sdcard(path2);
        handle1_1.mkdirs();
        handle2.mkdirs();

        FileHandle fileHandle1 = files.absolute(handle1, file1);
        FileHandle fileHandle1_1 = files.absolute(handle1_1, file1_1);
        FileHandle fileHandle2 = files.absolute(handle2, file2);
        fileHandle1.createNewFile();
        fileHandle1_1.createNewFile();
        fileHandle2.createNewFile();

        assertThat(rootHandle.listHandles()).hasSize(2)
                .extracting(new Extractor<FileHandle, String>() {
                    @Override
                    public String extract(final FileHandle handle) {
                        return handle.toFile().getName();
                    }
                }).containsExactly("dir1", "dir2");
        assertThat(handle1.listHandles()).hasSize(2);
        assertThat(handle1_1.listHandles()).hasSize(1);
        assertThat(handle2.listHandles()).hasSize(1);

        handle1.deleteChildren();
        assertThat(rootHandle.listHandles()).hasSize(2)
                .extracting(new Extractor<FileHandle, String>() {
                    @Override
                    public String extract(final FileHandle handle) {
                        return handle.toFile().getName();
                    }
                }).containsExactly("dir1", "dir2");

        handle2.delete();
        assertThat(rootHandle.listHandles()).hasSize(2)
                .extracting(new Extractor<FileHandle, String>() {
                    @Override
                    public String extract(final FileHandle handle) {
                        return handle.toFile().getName();
                    }
                }).containsExactly("dir1", "dir2");

        handle2.deleteDirectory();
        assertThat(rootHandle.listHandles()).hasSize(1)
                .extracting(new Extractor<FileHandle, String>() {
                    @Override
                    public String extract(final FileHandle handle) {
                        return handle.toFile().getName();
                    }
                }).containsExactly("dir1");

        rootHandle.deleteChildren();

        assertThat(rootHandle.listHandles()).hasSize(0);
    }

    public void testListEmpty() throws Exception {
        String path = testDirectory + "/" + "empty";
        FileHandle handle = files.sdcard(path);
        assertThat(handle.listFiles()).isNull();
        assertThat(handle.listHandles()).isNull();
    }

    public void testDeleteReadOnlyLocation() throws Exception {
        String path = testDirectory + "/" + "foo.txt";
        FileHandle handle = files.assets(path);
        try {
            handle.delete();
            fail("Assets directory is read only.");
        } catch (RuntimeException e) {
            assertThat(e).isNotNull().hasMessageContaining("read only");
        }

        try {
            handle.deleteChildren();
            fail("Assets directory is read only.");
        } catch (RuntimeException e) {
            assertThat(e).isNotNull().hasMessageContaining("read only");
        }

        try {
            handle.deleteDirectory();
            fail("Assets directory is read only.");
        } catch (RuntimeException e) {
            assertThat(e).isNotNull().hasMessageContaining("read only");
        }
    }

    public void testWriteReadOnlyLocation() throws Exception {
        String path = testDirectory + "/" + "foo.txt";
        FileHandle handle = files.assets(path);
        try {
            handle.writeString("foo");
            fail("Assets directory is read only.");
        } catch (RuntimeException e) {
            assertThat(e).isNotNull().hasMessageContaining("read only");
        }

        try {
            handle.writeBytes("foo".getBytes());
            fail("Assets directory is read only.");
        } catch (RuntimeException e) {
            assertThat(e).isNotNull().hasMessageContaining("read only");
        }

        try {
            handle.writeLines(Arrays.asList("foo"));
            fail("Assets directory is read only.");
        } catch (RuntimeException e) {
            assertThat(e).isNotNull().hasMessageContaining("read only");
        }
    }

    public void testWriteAndReadBytes() throws Exception {
        String path = testDirectory + "/" + "foo.data";
        byte[] expected = "hello world".getBytes();
        FileHandle writeHandle = files.sdcard(path);
        writeHandle.writeBytes(expected);

        FileHandle readHandle = files.sdcard(path);
        assertThat(readHandle.readBytes()).isEqualTo(expected);
    }

    public void testTryWriteAndReadBytes() throws Exception {
        String folderNotCreatedPath = testDirectory + "/notExist/foo.data";
        String path = testDirectory + "/foo.data";

        FileHandle writeHandle = files.sdcard(folderNotCreatedPath);
        assertThat(writeHandle.tryWriteBytes("hello world".getBytes()))
                .isFalse();

        FileHandle writeHandle2 = files.sdcard(path);
        assertThat(writeHandle2.tryWriteBytes("hello world".getBytes()))
                .isTrue();

        FileHandle readHandle = files.sdcard(folderNotCreatedPath);
        assertThat(readHandle.tryReadBytes()).isNull();


        FileHandle readHandle2 = files.sdcard(path);
        assertThat(readHandle2.tryReadBytes()).isNotNull();
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

    public void testTryWriteAndReadString() throws Exception {
        String folderNotCreatedPath = testDirectory + "/notExist/foo.txt";
        String path = testDirectory + "/foo.txt";

        FileHandle writeHandle = files.sdcard(folderNotCreatedPath);
        assertThat(writeHandle.tryWriteString("hello world"))
                .isFalse();

        FileHandle writeHandle2 = files.sdcard(path);
        assertThat(writeHandle2.tryWriteString("hello world"))
                .isTrue();

        FileHandle readHandle = files.sdcard(folderNotCreatedPath);
        assertThat(readHandle.tryReadString()).isNull();


        FileHandle readHandle2 = files.sdcard(path);
        assertThat(readHandle2.tryReadString()).isNotNull();
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

    public void testTryWriteAndReadJSONObject() throws Exception {
        String folderNotCreatedPath = testDirectory + "/notExist/foo.json";
        String path = testDirectory + "/foo.json";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("foo", "bar");
        AndroidFileHandle writeHandle = (AndroidFileHandle) files.sdcard(folderNotCreatedPath);
        assertThat(writeHandle.tryWriteJSONObject(jsonObject))
                .isFalse();

        AndroidFileHandle writeHandle2 = (AndroidFileHandle) files.sdcard(path);
        assertThat(writeHandle2.tryWriteJSONObject(jsonObject))
                .isTrue();

        AndroidFileHandle readHandle = (AndroidFileHandle) files.sdcard(folderNotCreatedPath);
        assertThat(readHandle.tryReadJSONObject()).isNull();


        AndroidFileHandle readHandle2 = (AndroidFileHandle) files.sdcard(path);
        assertThat(readHandle2.tryReadJSONObject()).isNotNull();
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

    public void testRenameTo() throws Exception {
        String sourcePath = testDirectory + "/" + "source.txt";
        String targetPath = testDirectory + "/" + "target.txt";

        String expected = "this is a test sentence";
        FileHandle sourceHandle = files.sdcard(sourcePath);
        sourceHandle.writeString(expected);

        FileHandle targetHandle = files.sdcard(targetPath);
        sourceHandle.renameTo(targetHandle);

        assertThat(targetHandle.exists()).isTrue();
        assertThat(targetHandle.isFile()).isTrue();
        assertThat(targetHandle.readString()).isEqualTo(expected);

        sourceHandle = files.sdcard(sourcePath);
        assertThat(sourceHandle.exists()).isFalse();
        assertThat(sourceHandle.isFile()).isFalse();
        assertThat(sourceHandle.tryReadString()).isNull();
    }

    public void testMoveTo() throws Exception {
        String sourcePath = testDirectory + "/" + "source.txt";
        String targetPath = testDirectory + "/" + "target.txt";

        String expected = "this is a test sentence";
        FileHandle sourceHandle = files.sdcard(sourcePath);
        sourceHandle.writeString(expected);

        FileHandle targetHandle = files.sdcard(targetPath);
        assertThat(targetHandle.notExist()).isTrue();
        assertThat(targetHandle.tryReadString()).isNull();
        sourceHandle.moveTo(targetHandle);

        assertThat(targetHandle.exists()).isTrue();
        assertThat(targetHandle.isFile()).isTrue();
        assertThat(targetHandle.readString()).isEqualTo(expected);

        sourceHandle = files.sdcard(sourcePath);
        assertThat(sourceHandle.exists()).isFalse();
        assertThat(sourceHandle.isFile()).isFalse();
        assertThat(sourceHandle.tryReadString()).isNull();
    }

    public void testMoveDirectoryTo() throws Exception {

    }

    public void testCopyTo() throws Exception {
        String sourcePath = testDirectory + "/" + "source.txt";
        String targetPath = testDirectory + "/" + "target.txt";

        String expected = "this is a test sentence";
        FileHandle sourceHandle = files.sdcard(sourcePath);
        sourceHandle.writeString(expected);

        FileHandle targetHandle = files.sdcard(targetPath);
        assertThat(targetHandle.notExist()).isTrue();
        assertThat(targetHandle.tryReadString()).isNull();
        sourceHandle.copyTo(targetHandle);

        assertThat(targetHandle.exists()).isTrue();
        assertThat(targetHandle.isFile()).isTrue();
        assertThat(targetHandle.readString()).isEqualTo(expected);

        sourceHandle = files.sdcard(sourcePath);
        assertThat(sourceHandle.exists()).isTrue();
        assertThat(sourceHandle.isFile()).isTrue();
        assertThat(sourceHandle.readString()).isEqualTo(expected);
    }

    public void testCopyDirectoryTo() throws Exception {

    }

    public void vtestTransferTo() throws Exception {
        String sourcePath = testDirectory + "/" + "source.txt";
        String targetPath = testDirectory + "/" + "target.txt";

        String expected = "this is a test sentence";
        FileHandle sourceHandle = files.sdcard(sourcePath);
        sourceHandle.writeString(expected);

        FileHandle targetHandle = files.absolute(targetPath);
        targetHandle.createNewFile();
        sourceHandle.transferTo(targetHandle.getOutputStream(false));

        assertThat(targetHandle.readString()).isEqualTo(expected);
    }

    public void testCreateNewFileTwice() throws Exception {
        files.sdcard(testDirectory).deleteChildren();
        String path = testDirectory + "/" + "foo.txt";
        FileHandle handle = files.sdcard(path);
        assertThat(handle.createNewFile()).isTrue();
        assertThat(handle.createNewFile()).isFalse();
    }
}