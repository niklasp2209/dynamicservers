# Dynamic Server Management System for Minecraft with Bungeecord

This system allows you to dynamically start and stop Minecraft servers based on player presence and subdomain requests. When a player accesses a subdomain (e.g., `test.bukkit.news`), the corresponding server is automatically started, and the player is redirected to it. Once there are no players left on the subdomain, the server will automatically shut down. It ensures that servers remain online even after the proxy server restarts and supports a functional MOTD (Message of the Day), even if the backend server is completely offline.

## Features

- **Dynamic Server Start/Stop**: The server is automatically started when a player accesses the subdomain and is stopped when the last player leaves.
- **Automatic Player Assignment**: Players are automatically assigned to the appropriate server based on the subdomain they visit.
- **MOTD Support**: The MOTD for the server remains functional even if the backend server is offline.
- **Overload Protection**: The system can reject subdomains and user requests if the hardware is under heavy load to avoid overloading the system.

## Technologie-Stack

- **Minecraft Server**: Spigot
- **Proxy**: BungeeCord
- **Server-Management**: Java (Spring Boot)
- **Datenbank (optional für Tracking)**: MySQL
- **Deployment**: Docker

## Prerequisites

- **Bungeecord**: The proxy server that manages connections between players and the dynamic Minecraft servers.
- **Java 8 or higher**: Minimum requirement for running Minecraft and Bungeecord.
- **Minecraft Servers**: One or more backend Minecraft servers for players to connect to.
- **DNS Management**: The ability to manage subdomains for automatic server creation.

## Installation

### 1. Set Up Bungeecord

1. Download and configure Bungeecord on your proxy server. Follow the official [Bungeecord installation guide](https://www.spigotmc.org/wiki/bungeecord-setup/) if you haven't set it up yet.
2. Ensure Bungeecord is running and accessible by players.

### 2. Configure DNS for Dynamic Subdomains

1. Set up wildcard DNS on your domain provider (e.g., `*.bukkit.news`) so that every subdomain request automatically resolves to the IP address of your proxy server.
2. This ensures that subdomains like `test.bukkit.news` are routed to your proxy server correctly.

### 3. Set Up the Dynamic Server Manager

1. Clone this repository to your server:

   ```bash
   git clone https://github.com/yourusername/dynamic-server-management.git
   cd dynamic-server-management
