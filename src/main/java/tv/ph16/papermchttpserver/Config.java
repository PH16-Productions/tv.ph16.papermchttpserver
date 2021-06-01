package tv.ph16.papermchttpserver;

import java.util.Optional;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import tv.ph16.paperplugin.ConfigurationManager;

/**
 * Configuration information for the server.
 */
final class Config extends ConfigurationManager {
    /**
     * Name of the port configuration.
     */
    private static final String PORT_CONFIG = "port";

    /**
     * Create a new instance of the {@link Config} class.
     * @param plugin The plugin to manage configuration for.
     */
    public Config(@NotNull Plugin plugin) {
        super(plugin);
    }

    /**
     * Gets the TCP/IP port to listen on.
     *
     * @return the TCP/IP port to listen on if set and valid; otherwise empty.
     */
    @NotNull
    public Optional<Integer> getPort() {
        return getInt(PORT_CONFIG).flatMap(port -> {
            if (port > 0) {
                return Optional.of(port);
            }
            return Optional.empty();
        });
    }
}
