package tv.ph16.bukkitwebserver;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public final class InstantParser {
    private final DateTimeFormatter imfFixedDateFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH).withZone(ZoneOffset.UTC);
    private final DateTimeFormatter rfc850Format = DateTimeFormatter.ofPattern("EEEE, dd-MMM-yy HH:mm:ss z", Locale.ENGLISH).withZone(ZoneOffset.UTC);
    private final DateTimeFormatter ascFormat = DateTimeFormatter.ofPattern("EEE MMM dd hh:mm:ss yyyy\n", Locale.ENGLISH);
    private final DateTimeFormatter[] formatters = { imfFixedDateFormat, rfc850Format, ascFormat };

    @Nullable
    public Instant getInstant(@Nullable String datetime) {
        if (datetime == null || datetime.length() == 0) {
            return null;
        }
        for (DateTimeFormatter formatter : formatters) {
            try {
                return formatter.parse(datetime, Instant::from);
            }
            catch (DateTimeParseException e) {
                //continue
            }
        }
        return null;
    }

    @NotNull
    public String fromInstant(@NotNull Instant instant) {
        return imfFixedDateFormat.format(instant);
    }
}
