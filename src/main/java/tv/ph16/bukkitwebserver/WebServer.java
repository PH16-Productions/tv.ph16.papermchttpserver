package tv.ph16.bukkitwebserver;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.ph16.bukkitwebserver.AbstractServer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class WebServer extends AbstractServer {
    public WebServer(JavaPlugin plugin) {
        super(plugin);
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
        if (path.equals("")) {
            path = "index.html";
        }
        Path filePath = Paths.get("www").resolve(path);
        if (Files.exists(filePath)) {
            return filePath.toFile();
        }
        return null;
    }
}
