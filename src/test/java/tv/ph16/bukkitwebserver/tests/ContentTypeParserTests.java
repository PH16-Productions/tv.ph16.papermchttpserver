package tv.ph16.bukkitwebserver.tests;

import tv.ph16.bukkitwebserver.ContentTypeParser;
import org.apache.tika.mime.MediaType;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContentTypeParserTests {

    @Test
    public void utf8Html() throws IOException {
        Path filePath = Files.createTempFile(null, ".html");
        File file = filePath.toFile();
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            String html = "<html><head><title>Hello World</title></head><body><p>Hello</p><p>world</p></body></html>";
            byte[] data = StandardCharsets.UTF_8.encode(html).array();
            outputStream.write(data);
        }
        ContentTypeParser parser = new ContentTypeParser();
        MediaType mediaType = parser.detect(file);
        assertEquals("text", mediaType.getType());
        assertEquals("html", mediaType.getSubtype());
    }
}
