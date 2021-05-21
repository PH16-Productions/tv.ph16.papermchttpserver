package tv.ph16.bukkitwebserver;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.jetbrains.annotations.NotNull;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * HTTP Server that provides information about a Minecraft server.
 */
final class ApiServer extends AbstractHandler {
    private final JavaPlugin plugin_;

    /**
     * Creates a new instance of the {@see ApiServer} class.
     * @param plugin the plugin hosting this server.
     */
    public ApiServer(@NotNull JavaPlugin plugin) {
        plugin_ = plugin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(@NotNull String target, @NotNull Request baseRequest, @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws IOException, ServletException {
        String path = baseRequest.getPathInContext();
        String responseText = null;
        if (path.equalsIgnoreCase("server-version")) {
            responseText = plugin_.getServer().getVersion();
        } else if (path.equalsIgnoreCase("player-cap")) {
            responseText = Integer.toString(plugin_.getServer().getMaxPlayers());
        } else {
            String[] pathParts = path.split("\\|/");
            if (pathParts.length == 2 &&
                pathParts[0].equals("difficulty")) {
                World world = plugin_.getServer().getWorld(pathParts[1]);
                if (world != null) {
                    responseText = world.getDifficulty().toString();
                }
            }
        }
        if (responseText != null) {
            baseRequest.setHandled(true);
            response.setStatus(200);
            response.setContentType("text/plain");
            response.getWriter().write(responseText);
        }
    }
}
