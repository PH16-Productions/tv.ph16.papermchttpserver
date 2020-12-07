package net.englard.shmuelie.bukkitwebserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.tika.mime.MediaType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.List;

public abstract class AbstractServer implements HttpHandler {
    private final JavaPlugin plugin_;
    private final ContentTypeParser contentTypeParser = new ContentTypeParser();
    private final InstantParser instantParser = new InstantParser();
    private final MediaType icoType = MediaType.image("x-icon");

    public AbstractServer(@NotNull JavaPlugin plugin) {
        plugin_ = plugin;
    }

    @Override
    public final void handle(@NotNull HttpExchange httpExchange) throws IOException {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.add("X-Powered-By", plugin_.getDescription().getFullName());
        responseHeaders.add("Tk", "N");
        responseHeaders.add("X-Content-Type-Options", "nosniff");
        Headers requestHeaders = httpExchange.getRequestHeaders();
        String path = httpExchange.getRequestURI().toASCIIString().substring(httpExchange.getHttpContext().getPath().length());
        List<String> allowedMethods = getAllowedMethods(path);
        if (!allowedMethods.contains(httpExchange.getRequestMethod())) {
            responseHeaders.add("Allow", String.join(", ", allowedMethods));
            httpExchange.sendResponseHeaders(405, -1);
            return;
        }
        Object response = handleRequest(path);
        if (response == null) {
            httpExchange.sendResponseHeaders(404, -1);
        } else if (response instanceof File) {
            handleFile(httpExchange, responseHeaders, requestHeaders, (File)response);
        } else if (response instanceof String) {
            handleString(httpExchange, responseHeaders, (String)response);
        } else if (response instanceof InputStream) {
            handleInputStream(httpExchange, responseHeaders, (InputStream)response);
        }
    }

    private void handleInputStream(@NotNull HttpExchange httpExchange, @NotNull Headers responseHeaders, @NotNull InputStream inputStream) throws IOException {
        String contentType = contentTypeParser.detect(inputStream).toString();
        responseHeaders.add("Content-Type", contentType);
        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream outputStream = httpExchange.getResponseBody()) {
            inputStream.transferTo(outputStream);
        }
    }

    private void handleString(@NotNull HttpExchange httpExchange, @NotNull Headers responseHeaders, @NotNull String str) throws IOException {
        byte[] data = StandardCharsets.UTF_8.encode(str).array();
        responseHeaders.add("Content-Type", "text/plain; charset=utf-8");
        httpExchange.sendResponseHeaders(200, data.length);
        try (OutputStream outputStream = httpExchange.getResponseBody()) {
            outputStream.write(data);
        }
    }

    private void handleFile(@NotNull HttpExchange httpExchange, @NotNull Headers responseHeaders, @NotNull Headers requestHeaders, @NotNull File file) throws IOException {
        FileTime lastModified = Files.getLastModifiedTime(file.toPath());
        String ifModifiedSince = requestHeaders.getFirst("If-Modified-Since");
        Instant modifiedSince = instantParser.getInstant(ifModifiedSince);
        if (modifiedSince != null) {
            if (lastModified.toInstant().compareTo(modifiedSince) <= 0) {
                httpExchange.sendResponseHeaders(304, -1);
                return;
            }
        }
        responseHeaders.add("Last-Modified", instantParser.fromInstant(lastModified.toInstant()));
        MediaType contentType;
        if (file.getName().toLowerCase().endsWith(".ico")) {
            contentType = icoType;
        } else {
            contentType = contentTypeParser.detect(file);
        }
        responseHeaders.add("Content-Type", contentType.toString());
        try  (FileInputStream inputStream = new FileInputStream(file)) {
            httpExchange.sendResponseHeaders(200, inputStream.available());
            try (OutputStream outputStream = httpExchange.getResponseBody()) {
                inputStream.transferTo(outputStream);
            }
        }
    }

    @NotNull
    public abstract List<String> getAllowedMethods(@NotNull String path);

    @Nullable
    public abstract Object handleRequest(@NotNull String path) throws IOException;
}
