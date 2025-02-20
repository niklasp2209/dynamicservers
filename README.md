# Dynamic Server Management System for Minecraft with Bungeecord

This system allows you to dynamically start and stop Minecraft servers based on player presence and subdomain requests. When a player accesses a subdomain (e.g., `test.bukkit.news`), the corresponding server is automatically started, and the player is redirected to it. Once there are no players left on the subdomain, the server will automatically shut down. It ensures that servers remain online even after the proxy server restarts and supports a functional MOTD (Message of the Day), even if the backend server is completely offline.

The system uses a **Minecraft Spigot server** for the backend game servers, with **Bungeecord** as the proxy to manage player connections. The server management system itself is built with **Java (Spring Boot)** to provide flexibility and scalability, and it optionally supports **MySQL** for tracking and storing data such as server usage statistics. The deployment is handled using **Docker** for easy containerization and environment management.

## Features

- **User Accounts**: 
  - Users can log in and register.
  - Normal users can see and manage their own servers.
  - Admins can view and control all servers.
- **Dynamic Server Start/Stop**: The system automatically starts the corresponding Minecraft server when a player connects to a subdomain, and it stops the server when the last player leaves. This is achieved using the **Bungeecord** proxy to handle connections and routing.
- **Automatic Player Assignment**: Players are automatically assigned to the correct dynamic server based on the subdomain they connect to. For instance, accessing `test.bukkit.news` would route the player to the `test` server, automatically starting it if necessary.
- **Live Server Status**: Displays online servers along with the current player count.
- **MOTD Support**: The Message of the Day (MOTD) will be shown to players even if the backend server is offline. This ensures that players can still see a welcoming message or an alert while waiting for the server to come online.
- **Overload Protection**: The system can reject new subdomain requests if the hardware is under heavy load (CPU or RAM usage exceeds a configured threshold). This helps prevent overloading your servers.
- **Web Interface for Server Control**: Users can start and stop servers directly from a web interface.

## Technologie-Stack

- **Frontend**: Thymeleaf or React (Configurable)
- **Backend**: Java (Spring Boot & Spring Security)
- **Minecraft Server**: **Spigot** - a high-performance Minecraft server software.
- **Proxy**: **BungeeCord** - a proxy server that allows the dynamic connection of players to various Minecraft servers.
- **Database (optional for Tracking)**: **MySQL** - used for tracking player connections, server uptime, and other usage statistics (optional for your use case).
- **Deployment**: **Docker** - for containerized deployment, ensuring easy setup and portability.

## Prerequisites

- **Bungeecord**: The proxy server that manages connections between players and the dynamic Minecraft servers.
- **Java 8 or higher**: Minimum requirement for running Minecraft, Bungeecord, and the dynamic server manager.
- **Minecraft Servers**: One or more backend Minecraft servers (using **Spigot**) for players to connect to.
- **DNS Management**: The ability to manage subdomains for automatic server creation, typically handled via wildcard DNS (`*.bukkit.news`).
- **Docker**: For deploying the dynamic server management system.

## Installation

### 1. Set Up Bungeecord

1. Download and configure **Bungeecord** on your proxy server. You can follow the official [Bungeecord installation guide](https://www.spigotmc.org/wiki/bungeecord-setup/) if you haven't set it up yet.
2. Make sure **Bungeecord** is running and accessible by players.

### 2. Configure DNS for Dynamic Subdomains

1. Set up wildcard DNS on your domain provider (e.g., `*.bukkit.news`) so that every subdomain request automatically resolves to the IP address of your proxy server. This is important for routing subdomain requests (e.g., `test.bukkit.news`) correctly to your proxy.
2. This ensures that requests like `test.bukkit.news` are properly routed to your **Bungeecord** server.

### 3. Set Up the Dynamic Server Manager

1. Clone this repository to your server:

   ```bash
   git clone https://github.com/yourusername/dynamic-server-management.git
   cd dynamic-server-management
   
