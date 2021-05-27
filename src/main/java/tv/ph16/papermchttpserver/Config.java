package tv.ph16.papermchttpserver;

import java.util.Optional;

import org.bukkit.configuration.Configuration;
import org.jetbrains.annotations.NotNull;

/**
 * Configuration information for the server.
 */
final class Config {
    /**
     * Name of the port configuration.
     */
    private static final String PORT_CONFIG = "port";

    /**
     * Plugin configuration.
     */
    private final Configuration config;

    /**
     * Creates a new instance of the {@see Config} class.
     * @param config the plugin configuration.
     */
    public Config(@NotNull Configuration config) {
        this.config = config;
    }

    /**
     * Gets the TCP/IP port to listen on.
     *
     * @return the TCP/IP port to listen on if set and valid; otherwise empty.
     */
    @NotNull
    public Optional<Integer> getPort() {
        if (config.contains(PORT_CONFIG)) {
            int port = config.getInt(PORT_CONFIG, 0);
            if (port > 0) {
                return Optional.of(port);
            }
        }
        return Optional.empty();
    }
}
