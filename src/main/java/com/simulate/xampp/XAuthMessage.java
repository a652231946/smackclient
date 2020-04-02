package com.simulate.xampp;

import org.jivesoftware.smack.packet.Nonza;
import org.jivesoftware.smack.util.XmlStringBuilder;

public class XAuthMessage implements Nonza {

    public static final String NAMESPACE = "urn:ietf:params:xml:ns:xmpp-xauth";
    public static final String ELEMENT = "xauth";
    private String name;
    private String data;

    @Override
    public String getNamespace() {
        return NAMESPACE;
    }

    @Override
    public String getElementName() {
        return ELEMENT;
    }

    @Override
    public CharSequence toXML(String enclosingNamespace) {
        XmlStringBuilder xml = new XmlStringBuilder();
        xml.halfOpenElement(ELEMENT).xmlnsAttribute(NAMESPACE).attribute("name", getName()).rightAngleBracket();
        xml.optAppend(getData());
        xml.closeElement(ELEMENT);
        return xml;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
