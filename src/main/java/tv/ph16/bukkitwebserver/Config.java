package tv.ph16.bukkitwebserver;

import org.bukkit.configuration.Configuration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Configuration information for the server.
 */
final class Config {
    /**
     * Name of the port configuration.
     */
    private static final String PORT_CONFIG = "port";
    /**
     * Name of the static path configuration.
     */
    private static final String STATIC_PATH_CONFIG = "staticPath";

    /**
     * Plugin conversation.
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
     * Gets the static path.
     * @return the static path.
     */
    @Nullable
    public String getStaticPath() {
        return config.getString(STATIC_PATH_CONFIG);
    }

    /**
     * Gets the port.
     * @return the port.
     */
    public int getPort() {
        return config.getInt(PORT_CONFIG);
    }
}
