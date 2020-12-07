package net.englard.shmuelie.bukkitwebserver;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;

public final class Plugin extends JavaPlugin {
    private HttpServer server = null;
    private int port = 0;
    private WebServer webServer;
    private ApiServer apiServer;
    private HttpContext webContext;
    private HttpContext apiContext;

    @Override
    public void onLoad() {
        getConfig().addDefault("port", 0);
        getConfig().options().copyDefaults(true);
        saveConfig();
        port = getConfig().getInt("port");
        webServer = new WebServer(this);
        apiServer = new ApiServer(this);
    }

    @Override
    public void onEnable() {
        startWebServer();
    }

    @Override
    public void onDisable() {
        stopWebServer();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("web") && sender.isOp()) {
            switch (args.length) {
                case 1:
                    if (args[0].equalsIgnoreCase("start")) {
                        startWebServer();
                    }
                    else if (args[0].equalsIgnoreCase("stop")) {
                        stopWebServer();
                    }
                    break;
                case 2:
                    if (args[0].equalsIgnoreCase("port")) {
                        try
                        {
                            port = Integer.parseInt(args[1]);
                            getConfig().set("port", port);
                            saveConfig();
                            getLogger().info("Changed port to " + port);
                            if (server != null) {
                                stopWebServer();
                                startWebServer();
                            }
                        }
                        catch (NumberFormatException e) {
                            getLogger().warning("Invalid port");
                        }
                    }
                    else if (args[0].equalsIgnoreCase("start")) {
                        try
                        {
                            port = Integer.parseInt(args[1]);
                            getConfig().set("port", port);
                            saveConfig();
                            getLogger().info("Changed port to " + port);
                        }
                        catch (NumberFormatException e) {
                            getLogger().warning("Invalid port");
                        }
                        startWebServer();
                    }
                    break;
                default:
                    getLogger().warning("Unknown sub command");
                    break;
            }
            return true;
        }
        return false;
    }

    private void startWebServer() {
        if (server != null) {
            return;
        }
        if (port == 0)
        {
            getLogger().warning("No port set, unable to start server");
            return;
        }
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            webContext = server.createContext("/", webServer);
            apiContext = server.createContext("/api/", apiServer);
            server.setExecutor(null);
            server.start();
            getLogger().info("Started web server on port '" + port + "'.");
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Failed to start web server on port '" + port + "'.", e);
        }
    }

    private void stopWebServer() {
        if (server != null) {
            server.stop(0);
            server.removeContext(webContext);
            server.removeContext(apiContext);
            server = null;
            webContext = null;
            apiContext = null;
            getLogger().info("Server stopped");
        }
    }
}
