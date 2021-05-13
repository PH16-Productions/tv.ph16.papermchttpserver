package tv.ph16.bukkitwebserver;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.ph16.bukkitwebserver.AbstractServer;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class ApiServer extends AbstractServer {
    private final JavaPlugin plugin_;

    public ApiServer(@NotNull JavaPlugin plugin) {
        super(plugin);
        plugin_ = plugin;
    }

    @Override
    @NotNull
    public List<String> getAllowedMethods(@NotNull String path) {
        ArrayList<String> al = new ArrayList<>();
        al.add("GET");
        return al;
    }

    @Override
    @Nullable
    public Object handleRequest(@NotNull String path) {
        if (path.equalsIgnoreCase("server-version")) {
            return plugin_.getServer().getVersion();
        } else if (path.equalsIgnoreCase("player-cap")) {
            return Integer.toString(plugin_.getServer().getMaxPlayers());
        } else {
            Path parsedPath = Path.of(path);
            if (parsedPath.getNameCount() == 2 &&
                parsedPath.getName(0).toString().equals("difficulty")) {
                World world = plugin_.getServer().getWorld(parsedPath.getName(1).toString());
                if (world != null) {
                    return world.getDifficulty().toString();
                }
            }
            return null;
        }
    }
}
