package tv.ph16.bukkitwebserver;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public final class ContentTypeParser {
    private final Tika tika = new Tika();
    private final DefaultHandler handler = new DefaultHandler();

    @Nullable
    public MediaType detect(@NotNull File file) throws IOException {
        Metadata metadata = new Metadata();
        metadata.set(Metadata.RESOURCE_NAME_KEY, file.getName());
        metadata.set(Metadata.CONTENT_LENGTH, Long.toString(file.length()));
        try (TikaInputStream inputStream = TikaInputStream.get(file.toPath(), metadata)) {
            return detect(metadata, inputStream);
        }
    }

    @Nullable
    public MediaType detect(@NotNull InputStream inputStream) throws IOException {
        Metadata metadata = new Metadata();
        try (TikaInputStream tikaInputStream = TikaInputStream.get(inputStream)) {
            return detect(metadata, tikaInputStream);
        }
    }

    @Nullable
    private MediaType detect(@NotNull Metadata metadata, @NotNull TikaInputStream tikaInputStream) throws IOException {
        try {
            tika.getParser().parse(tikaInputStream, handler, metadata, new ParseContext());
            return MediaType.parse(metadata.get(Metadata.CONTENT_TYPE));
        } catch (TikaException | SAXException e) {
            // Fallback
        }
        return tika.getDetector().detect(tikaInputStream, metadata);
    }
}
