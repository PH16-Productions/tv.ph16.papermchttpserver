# Bukkit Web Server

![Latest Release on GitHub](https://img.shields.io/github/v/release/ph16-productions/tv.ph16.bukkitwebserver?style=for-the-badge&label=Latest%20Release%20on%20GitHub)
![MIT License](https://img.shields.io/github/license/ph16-productions/tv.ph16.bukkitwebserver?style=for-the-badge)
![Minecraft API Version](https://img.shields.io/badge/dynamic/yaml?label=Minecraft%20API%20Version&query=%24%5B%27api-version%27%5D&url=https%3A%2F%2Fraw.githubusercontent.com%2FPH16-Productions%2Ftv.ph16.bukkitwebserver%2Fmain%2Fsrc%2Fmain%2Fresources%2Fplugin.yml&style=for-the-badge)
![Java Version](https://img.shields.io/badge/dynamic/xml?label=Java%20Version&query=%2F%2F%2A%5Blocal-name%28%29%20%3D%20%27java.version%27%5D&url=https%3A%2F%2Fraw.githubusercontent.com%2FPH16-Productions%2Ftv.ph16.bukkitwebserver%2Fmain%2Fpom.xml&style=for-the-badge)

Adds a HTTP Server to Bukkit. By default no servers are included. Public API
provides support for adding, getting, and remove servers.

## Dependencies

[![org.jetbrains:annotations:20.1.0](https://img.shields.io/badge/JetBrains%20Java%20Annotations-v20.1.0-blue?style=for-the-badge)](https://search.maven.org/artifact/org.jetbrains/annotations/20.1.0/jar)
[![org.eclipse.jetty:jetty-server:11.0.2](https://img.shields.io/badge/Jetty%20%3A%3A%20Server%20Core-v11.0.2-blue?style=for-the-badge)](https://search.maven.org/artifact/org.eclipse.jetty/jetty-server/11.0.2/jar)
[![org.slf4j:slf4j-jdk14:2.0.0-alpha1](https://img.shields.io/badge/SLF4J%20JDK14%20Binding-v2.0.0--alpha1-blue?style=for-the-badge)](https://search.maven.org/artifact/org.slf4j/slf4j-jdk14/2.0.0-alpha1/jar)

## Configuration Options

- `port`: the port the server listens on.
