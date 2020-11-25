package ehu.isad.model;

public class Server {

    private String target;
    private String server;

    public Server(String target, String server) {
        this.target = target;
        this.server = server;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
