package tv.ph16.bukkitwebserver;

import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.logging.Level;

/**
 * Web server plugin for Bukkit.
 */
public final class Plugin extends JavaPlugin {
    private final Server server;
    private final ServerConnector connector;
    private final GzipHandler gzipHandler;
    private final ContextHandlerCollection contexts;
    private final Config config;

    /**
     * Create a new instance of the {@see Plugin} class.
     */
    public Plugin() {
        server = new Server();
        connector = new ServerConnector(server);
        server.addConnector(connector);
        gzipHandler = new GzipHandler();
        gzipHandler.setMinGzipSize(1024);
        gzipHandler.addIncludedMethods("POST");
        contexts = new ContextHandlerCollection();
        gzipHandler.setHandler(contexts);
        server.setHandler(gzipHandler);
        config = new Config(getConfig());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnable() {
        startWebServer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDisable() {
        stopWebServer();
    }

    /**
     * Start the web server if the server is not running.
     */
    private void startWebServer() {
        if (server.isRunning()) {
            return;
        }
        int port = config.getPort();
        if (port == 0) {
            getLogger().warning("No port set, unable to start server");
            return;
        }
        try {
            connector.setPort(port);
            server.start();
            getLogger().info("Started web server on port '" + port + "'.");
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Failed to start web server on port '" + port + "'.", e);
        }
    }

    /**
     * Stops the web server if server is running.
     */
    private void stopWebServer() {
        if (server.isRunning()) {
            try {
                server.stop();
                getLogger().info("Server stopped");
            } catch (Exception ex) {
                getLogger().log(Level.SEVERE, "Error stopping server", ex);
            }
        }
    }

    /**
     * Gets a {@see ContextHandler} for a given path.
     * @param path the path to search for.
     * @return The desired handler or an empty Optional.
     */
    @NotNull
    public Optional<ContextHandler> getHandler(@NotNull String path) {
        for (Handler existingHandler : contexts.getHandlers()) {
            if (existingHandler instanceof ContextHandler) {
                ContextHandler existingContextHandler = (ContextHandler)existingHandler;
                if (existingContextHandler.getContextPath().equalsIgnoreCase(path)) {
                    return Optional.of(existingContextHandler);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Add a new {@see Handler} at a path or get the existing one.
     *
     * @param path the root URI path to associate the handle with.
     * @param handler the handler to invoke for incoming requests.
     * @return The {@see ContextHandler} created for the handler.
     */
    @NotNull
    public ContextHandler addHandler(@NotNull String path, @NotNull Handler handler) {
        Optional<ContextHandler> existingHandler = getHandler(path);
        if (existingHandler.isPresent()) {
            return existingHandler.get();
        }
        ContextHandler contextHandler = new ContextHandler(path);
        contextHandler.setHandler(handler);
        contexts.addHandler(contextHandler);
        return contextHandler;
    }

    /**
     * Remove a {@see ContextHandler} for a given path.
     * @param path the path to remove the handler for.
     */
    public void removeHandler(@NotNull String path) {
        Optional<ContextHandler> existingHandler = getHandler(path);
        if (existingHandler.isPresent()) {
            contexts.removeHandler(existingHandler.get());
        }
    }
}
