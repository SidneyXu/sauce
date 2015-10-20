package com.bookislife.sauce.web;

import com.bookislife.sauce.SourceHandle;
import com.bookislife.sauce.exception.SauceException;
import com.bookislife.sauce.exception.SauceTimeoutException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mrseasons on 2015/09/10.
 */
public class HttpHandle extends SourceHandle {

    private interface StreamCallback {
        Object handleStream(int totalLen, InputStream inputStream) throws SauceException;
    }

    private URL url;
    private String path;
    private String method;
    private int connectTimeout;
    private int readTimeout;
    private Map<String, String> headers;
    private Map<String, String> params;

    private static final int DEFAULT_TIMEOUT = 15 * 1000;
    private static final int DEFAULT_BUFFER_SIZE = 4098;
    private int bufferSize = DEFAULT_BUFFER_SIZE;

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";

    private StreamCallback streamCallback;

    public HttpHandle(Builder builder) {
    }

    abstract class RequestHandler {
        abstract String beforeRequest(String basePath, Map<String, String> query);

        abstract ResponseHandler handleResponse(byte[] response);
    }

    abstract class ResponseHandler {

    }

    public void test() {
        //tdd develop
        //        HttpHandle httpHandle = new HttpHandle(new HttpHandle.Builder());
        //        httpHandle.request("http://jsonplaceholder.typicode.com",
        //                METHOD_GET, JSONDocument.class, new RequestHandler() {
        //
        //                    @Override
        //                    public String beforeRequest(String basePath, Map<String, String> query) {
        //                        query.put("v", "1.0");
        //                        query.put("q", "Calvin and Hobbes");
        //                        return basePath + "/posts/1";
        //                    }
        //
        //                    @Override
        //                    ResponseHandler handleResponse(byte[] response) {
        //                        return null;
        //                    }
        //                });
    }

    public void request(String url, String method, Class<?> returnType,
                        RequestHandler requestHandler) {

    }

    public byte[] readBytes() throws SauceException {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            connection = buildConnection();
            inputStream = getInputStream(connection);
            int totalLen = connection.getHeaderFieldInt("Content-Length", 0);
            return handleStream(totalLen, inputStream);
        } catch (SocketTimeoutException e) {
            throw new SauceTimeoutException();
        } catch (IOException e) {
            throw new SauceException(e);
        } catch (SauceException e) {
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private HttpURLConnection buildConnection() throws IOException {
        URL url;
        if (this.url == null) {
            url = new URL(getRealPath());
        } else {
            url = this.url;
        }
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        connection.setDoInput(true);
        if (METHOD_POST.equals(method.toUpperCase(Locale.ENGLISH)) ||
                METHOD_PUT.equals(method.toUpperCase(Locale.ENGLISH))) {
            connection.setDoOutput(true);
        }
        connection.setRequestMethod(method);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return connection;
    }

    private InputStream getInputStream(
            HttpURLConnection connection) throws IOException, SauceException {
        InputStream inputStream = connection.getErrorStream();
        if (inputStream != null) {
            int len = -1;
            byte[] buff = new byte[1024];
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(buff)) > 0) {
                sb.append(new String(buff, 0, len));
            }
            throw new SauceException(connection.getResponseCode(), sb.toString());
        }
        inputStream = connection.getInputStream();
        return inputStream;
    }

    private String getRealPath() {
        String tempPath = path;
        if (params != null && !params.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
            tempPath = path + "?" + sb.toString();
        }
        return tempPath;
    }

    private byte[] handleStream(int length, InputStream inputStream) {
        //TODO

        return null;
    }

    public static class Builder {
        private URL url;
        private String path;
        private String method;
        private int connectTimeout;
        private int readTimeout;
        private Map<String, String> headers;
        private Map<String, String> params;
        private int bufferSize;

        public Builder connectTimeout(int connectTimeout) {
            if (connectTimeout > 0) this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder readTimeout(int readTimeout) {
            if (readTimeout > 0) this.readTimeout = readTimeout;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder bufferSize(int bufferSize) {
            this.bufferSize = bufferSize;
            return this;
        }

        public Builder contentType(String contentType) {
            appendHeader("Content-Type", contentType);
            return this;
        }

        public Builder contentLength(int contentLength) {
            appendHeader("Content-Length", "" + contentLength);
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder appendHeader(String key, String value) {
            if (headers == null) headers = new HashMap<String, String>();
            headers.put(key, value);
            return this;
        }

        public Builder param(String key, String value) {
            if (params == null) {
                params = new HashMap<String, String>();
            }
            params.put(key, value);
            return this;
        }

        public Builder params(Map<String, String> params) {
            this.params = params;
            return this;
        }

    }
}
