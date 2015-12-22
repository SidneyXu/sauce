//package com.bookislife.sauce.web;
//
//import com.bookislife.sauce.SourceHandle;
//import com.bookislife.sauce.exception.SauceException;
//import com.bookislife.sauce.exception.SauceServerException;
//import com.bookislife.sauce.exception.SauceTimeoutException;
//import com.bookislife.sauce.utils.Capture;
//import com.bookislife.sauce.utils.Closer;
//import com.bookislife.sauce.utils.IOUtils;
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.SocketTimeoutException;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//
///**
// * Created by mrseasons on 2015/09/10.
// */
//public class HttpHandle extends SourceHandle {
//
//    private interface StreamCallback {
//        Object handleStream(int totalLen, InputStream inputStream) throws SauceException;
//    }
//
//    private URL url;
//    private String path;
//    private String method;
//    private int connectTimeout;
//    private int readTimeout;
//    private Map<String, String> headers;
//    private Map<String, String> query;
//    private Map<String, String> params;
//
//    private static final int DEFAULT_TIMEOUT = 15 * 1000;
//    private static final int DEFAULT_BUFFER_SIZE = 4098;
//    private int bufferSize = DEFAULT_BUFFER_SIZE;
//
//    public static final String CONTENT_TYPE_JSON = "application/json";
//
//    public static final String METHOD_GET = "GET";
//    public static final String METHOD_POST = "POST";
//    public static final String METHOD_PUT = "PUT";
//    public static final String METHOD_DELETE = "DELETE";
//
//    private StreamCallback streamCallback;
//
//    private HttpHandle(Builder builder) {
//        url = builder.url;
//        path = builder.path;
//        method = builder.method;
//        connectTimeout = builder.connectTimeout;
//        readTimeout = builder.readTimeout;
//        headers = builder.headers;
//        query = builder.query;
//        params = builder.params;
//        bufferSize = builder.bufferSize;
//    }
//
//    public static Builder from(String url) {
//        return new Builder(url);
//    }
//
//    public static Builder from(URL url) {
//        return new Builder(url);
//    }
//
//    abstract class RequestHandler {
//        abstract void handleResponse(InputStream responseStream,
//                                     int statusCode,
//                                     Map<String, String> headers,
//                                     String message) throws IOException;
//
//        abstract void handleError(SauceException e);
//
//        void handleRequest(InputStream bodyStream, OutputStream serverStream) throws IOException {
//        }
//    }
//
//    public String readString(String charset) throws SauceException {
//        byte[] data = readBytes();
//        if (null == data) return null;
//        try {
//            return new String(data, 0, data.length, charset);
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public byte[] readBytes() throws SauceException {
//        final Capture<SauceException> exceptionCapture = new Capture<>();
//        final Capture<byte[]> resultCapture = new Capture<>();
//        request(null, new RequestHandler() {
//            @Override
//            void handleResponse(final InputStream responseStream, final int statusCode,
//                                final Map<String, String> headers,
//                                final String message) throws IOException {
//                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                IOUtils.copyTo(responseStream, outputStream, bufferSize);
//                resultCapture.set(outputStream.toByteArray());
//            }
//
//            @Override
//            void handleError(final SauceException e) {
//                exceptionCapture.set(e);
//            }
//        });
//        if (exceptionCapture.isNotNull()) {
//            throw exceptionCapture.get();
//        }
//        return resultCapture.get();
//    }
//
//    private void request(
//            InputStream bodyStream,
//            RequestHandler requestHandler) {
//        HttpURLConnection connection = null;
//        OutputStream outputStream;
//        InputStream responseStream;
//        Closer closer = new Closer(bodyStream);
//        try {
//            connection = createConnection();
//            if (bodyStream != null && (METHOD_POST.equals(method) || METHOD_PUT.equals(method))) {
//                outputStream = closer.wrap(connection.getOutputStream());
//                requestHandler.handleRequest(bodyStream, outputStream);
//            }
//
//            responseStream = closer.wrap(getResponseStream(connection));
//
//            Map<String, String> headers = new HashMap<>();
//            for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
//                if (null != entry.getKey() && !entry.getValue().isEmpty())
//                    headers.put(entry.getKey(), entry.getValue().get(0));
//            }
//            int statusCode = connection.getResponseCode();
//            String message = connection.getResponseMessage();
//            requestHandler.handleResponse(
//                    responseStream,
//                    statusCode,
//                    headers,
//                    message
//            );
//
//        } catch (SocketTimeoutException e) {
//            requestHandler.handleError(new SauceTimeoutException());
//        } catch (IOException e) {
//            int statusCode = -1;
//            if (null != connection) {
//                try {
//                    statusCode = connection.getResponseCode();
//                } catch (IOException ignored) {
//                }
//            }
//            requestHandler.handleError(new SauceServerException(statusCode, e));
//        } catch (SauceException e) {
//            requestHandler.handleError(e);
//        } finally {
//            closer.close();
//        }
//    }
//
//    public byte[] readBytes2() throws SauceException {
//        HttpURLConnection connection = null;
//        InputStream inputStream = null;
//        try {
//            connection = createConnection();
//            inputStream = getInputStream(connection);
//            int totalLen = connection.getHeaderFieldInt("Content-Length", 0);
//            return handleStream(totalLen, inputStream);
//        } catch (SocketTimeoutException e) {
//            throw new SauceTimeoutException();
//        } catch (IOException e) {
//            throw new SauceException(e);
//        } catch (SauceException e) {
//            throw e;
//        } finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException ignored) {
//                }
//            }
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }
//    }
//
//    private HttpURLConnection createConnection() throws IOException {
//        URL url;
//        if (null == this.url) {
//            url = new URL(getRealPath(path, query));
//        } else {
//            url = this.url;
//        }
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setConnectTimeout(connectTimeout);
//        connection.setReadTimeout(readTimeout);
//        connection.setDoInput(true);
//        if (METHOD_POST.equals(method.toUpperCase(Locale.ENGLISH)) ||
//                METHOD_PUT.equals(method.toUpperCase(Locale.ENGLISH))) {
//            connection.setDoOutput(true);
//            connection.setUseCaches(false);
//        }
//        connection.setRequestMethod(method);
//        if (null != headers) {
//            for (Map.Entry<String, String> entry : headers.entrySet()) {
//                connection.setRequestProperty(entry.getKey(), entry.getValue());
//            }
//        }
//        return connection;
//    }
//
//    private InputStream getResponseStream(
//            HttpURLConnection connection) throws IOException, SauceException {
//        InputStream inputStream = connection.getErrorStream();
//        if (inputStream != null) {
//            int len;
//            byte[] buff = new byte[1024];
//            StringBuilder sb = new StringBuilder();
//            while ((len = inputStream.read(buff)) > 0) {
//                sb.append(new String(buff, 0, len));
//            }
//            throw new SauceServerException(connection.getResponseCode(), sb.toString());
//        }
//        inputStream = connection.getInputStream();
//        return inputStream;
//    }
//
//    private InputStream getInputStream(
//            HttpURLConnection connection) throws IOException, SauceException {
//        InputStream inputStream = connection.getErrorStream();
//        if (inputStream != null) {
//            int len = -1;
//            byte[] buff = new byte[1024];
//            StringBuilder sb = new StringBuilder();
//            while ((len = inputStream.read(buff)) > 0) {
//                sb.append(new String(buff, 0, len));
//            }
//            throw new SauceException(connection.getResponseCode(), sb.toString());
//        }
//        inputStream = connection.getInputStream();
//        return inputStream;
//    }
//
//    private String getRealPath(String basePath, Map<String, String> query) {
//        String tempPath = basePath;
//        if (null != query && !query.isEmpty()) {
//            StringBuilder sb = new StringBuilder();
//            for (Map.Entry<String, String> entry : query.entrySet()) {
//                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
//            }
//            sb.deleteCharAt(sb.length() - 1);
//            tempPath = basePath + "?" + sb.toString();
//        }
//        return tempPath;
//    }
//
//    private byte[] handleStream(int length, InputStream inputStream) {
//        //TODO
//
//        return null;
//    }
//
//    public static class Builder {
//        private URL url;
//        private String path;
//        private String method = METHOD_GET;
//        private int connectTimeout = DEFAULT_TIMEOUT;
//        private int readTimeout = DEFAULT_TIMEOUT;
//        private Map<String, String> headers;
//        private Map<String, String> query;
//        private Map<String, String> params;
//        private int bufferSize = DEFAULT_BUFFER_SIZE;
//
//        public Builder(URL url) {
//            this.url = url;
//        }
//
//        public Builder(String url) {
//            this.path = url;
//        }
//
//        public static Builder from(URL url) {
//            return new Builder(url);
//        }
//
//        public Builder connectTimeout(int connectTimeout) {
//            if (connectTimeout > 0) this.connectTimeout = connectTimeout;
//            return this;
//        }
//
//        public Builder readTimeout(int readTimeout) {
//            if (readTimeout > 0) this.readTimeout = readTimeout;
//            return this;
//        }
//
//        public Builder method(String method) {
//            this.method = method;
//            return this;
//        }
//
//        public Builder bufferSize(int bufferSize) {
//            this.bufferSize = bufferSize;
//            return this;
//        }
//
//        public Builder contentType(String contentType) {
//            appendHeader("Content-Type", contentType);
//            return this;
//        }
//
//        public Builder contentLength(int contentLength) {
//            appendHeader("Content-Length", "" + contentLength);
//            return this;
//        }
//
//        public Builder headers(Map<String, String> headers) {
//            this.headers = headers;
//            return this;
//        }
//
//        public Builder appendHeader(String key, String value) {
//            if (headers == null) headers = new HashMap<String, String>();
//            headers.put(key, value);
//            return this;
//        }
//
//        public Builder path(String subPath) {
//            path += subPath;
//            return this;
//        }
//
//        public Builder query(String key, String value) {
//            if (query == null) {
//                query = new HashMap<String, String>();
//            }
//            query.put(key, value);
//            return this;
//        }
//
//        public Builder queryWithUTF8(String key, String value) {
//            try {
//                return query(key, URLEncoder.encode(value, "UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        public Builder query(Map<String, String> query) {
//            this.query = query;
//            return this;
//        }
//
//        public Builder params(String key, String value) {
//            if (params == null) {
//                params = new HashMap<String, String>();
//            }
//            params.put(key, value);
//            return this;
//        }
//
//        public Builder params(Map<String, String> params) {
//            this.params = params;
//            return this;
//        }
//
//        public HttpHandle build() {
//            return new HttpHandle(this);
//        }
//
//    }
//}
