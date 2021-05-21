# Bukkit Web Server

![GitHub release (latest by date)](https://img.shields.io/github/v/release/ph16-productions/tv.ph16.bukkitwebserver?style=for-the-badge)
![GitHub](https://img.shields.io/github/license/ph16-productions/tv.ph16.bukkitwebserver?style=for-the-badge)
![API-Version](https://img.shields.io/badge/dynamic/yaml?label=API-Version&query=%24%5B%27api-version%27%5D&url=https%3A%2F%2Fraw.githubusercontent.com%2FPH16-Productions%2Ftv.ph16.bukkitwebserver%2Fmain%2Fsrc%2Fmain%2Fresources%2Fplugin.yml&style=for-the-badge)

Adds a HTTP Server to Bukkit. By default supports static web content and an API
for server information.

## Features

### Static Web Server

A HTTP server that serves content from a configured folder.

### API Server

A HTTP server that replies with information about the Minecraft Server.

### External Servers

Support for other plugins to add their own servers.

## Dependencies

[![org.jetbrains:annotations:20.1.0](https://img.shields.io/badge/JetBrains%20Java%20Annotations-v20.1.0-blue?style=for-the-badge)](https://search.maven.org/artifact/org.jetbrains/annotations/20.1.0/jar)
[![org.eclipse.jetty:jetty-server:11.0.2](https://img.shields.io/badge/Jetty%20%3A%3A%20Server%20Core-v11.0.2-blue?style=for-the-badge)](https://search.maven.org/artifact/org.eclipse.jetty/jetty-server/11.0.2/jar)
[![org.slf4j:slf4j-jdk14:2.0.0-alpha1](https://img.shields.io/badge/SLF4J%20JDK14%20Binding-v2.0.0--alpha1-blue?style=for-the-badge)](https://search.maven.org/artifact/org.slf4j/slf4j-jdk14/2.0.0-alpha1/jar)

## Configuration Options

- `port`: the port the server listens on.
- `staticPath`: the path to the static web content.
