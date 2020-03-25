package com.simulate;

import java.io.Serializable;

public class ChannelConfig implements Serializable {

    private String domainName;
    private String host;
    private int port = 5222;
    // conference.xcom0015
    private String groupSubDomain;
    // codm_1080031967
    private String groupID;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
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

    public String getGroupSubDomain() {
        return groupSubDomain;
    }

    public void setGroupSubDomain(String groupSubDomain) {
        this.groupSubDomain = groupSubDomain;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}
