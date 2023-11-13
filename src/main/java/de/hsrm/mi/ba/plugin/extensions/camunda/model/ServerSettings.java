package de.hsrm.mi.ba.plugin.extensions.camunda.model;

import java.io.Serializable;
import java.util.Objects;

public class ServerSettings implements Serializable {

    private boolean activated = false;
    private boolean ssl = false;
    private String host = "localhost";
    private int port = 8081;
    private boolean auth = false;
    private String user = "";
    private String password = "";
    private CamundaService service = CamundaService.OPERATE;

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CamundaService getService() {
        return service;
    }

    public void setService(CamundaService service) {
        this.service = service;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerSettings that = (ServerSettings) o;
        return activated == that.activated && ssl == that.ssl && port == that.port && auth == that.auth && Objects.equals(host, that.host) && Objects.equals(user, that.user) && Objects.equals(password, that.password) && service == that.service;
    }

    @Override
    public int hashCode() {
        return Objects.hash(activated, ssl, host, port, auth, user, password, service);
    }
}
