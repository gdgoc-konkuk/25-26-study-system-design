package chapter05;

import java.util.Objects;

public class ServerNode {
    private String id;
    private String host;
    private int port;

    public ServerNode(String id, String host, int port) {
        this.id = id;
        this.host = host;
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return id + "@" + host + ":" + port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerNode that = (ServerNode) o;
        return port == that.port && Objects.equals(id, that.id) && Objects.equals(host, that.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, host, port);
    }

    public void connect() {
        System.out.println("Connecting to server: " + toString());
    }

    public void disconnect() {
        System.out.println("Disconnecting from server: " + toString());
    }
}
