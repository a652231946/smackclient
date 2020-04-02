package com.simulate.xampp;

import org.jivesoftware.smack.packet.Nonza;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smack.util.XmlStringBuilder;

public class StreamOpenImpl implements Nonza {

    public static final String ELEMENT = "stream:auth";

    public static final String CLIENT_NAMESPACE = "jabber:client";
    public static final String SERVER_NAMESPACE = "jabber:server";

    /**
     * RFC 6120 § 4.7.5.
     */
    public static final String VERSION = "1.0";

    /**
     * RFC 6120 § 4.7.1.
     */
    private final String from;

    /**
     * RFC 6120 § 4.7.2.
     */
    private final String to;

    /**
     * RFC 6120 § 4.7.3.
     */
    private final String id;

    /**
     * RFC 6120 § 4.7.4.
     */
    private final String lang;

    /**
     * RFC 6120 § 4.8.2.
     */
    private final String contentNamespace;

    private String data;

    public StreamOpenImpl(CharSequence to) {
        this(to, null, null, null, org.jivesoftware.smack.packet.StreamOpen.StreamContentNamespace.client);
    }

    public StreamOpenImpl(CharSequence to, CharSequence from, String id) {
        this(to, from, id, "en", org.jivesoftware.smack.packet.StreamOpen.StreamContentNamespace.client);
    }

    public StreamOpenImpl(CharSequence to, CharSequence from, String id, String lang, org.jivesoftware.smack.packet.StreamOpen.StreamContentNamespace ns) {
        this.to = StringUtils.maybeToString(to);
        this.from = StringUtils.maybeToString(from);
        this.id = id;
        this.lang = lang;
        switch (ns) {
            case client:
                this.contentNamespace = CLIENT_NAMESPACE;
                break;
            case server:
                this.contentNamespace = SERVER_NAMESPACE;
                break;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public String getNamespace() {
        return contentNamespace;
    }

    @Override
    public String getElementName() {
        return ELEMENT;
    }

    @Override
    public XmlStringBuilder toXML(String enclosingNamespace) {
        XmlStringBuilder xml = new XmlStringBuilder();
        xml.halfOpenElement(getElementName());
        // We always want to state 'xmlns' for stream open tags.
        xml.attribute("xmlns", enclosingNamespace);

        xml.attribute("to", to);
        xml.attribute("xmlns:stream", "http://etherx.jabber.org/streams");
        xml.attribute("version", VERSION);
        xml.optAttribute("from", from);
        xml.optAttribute("id", id);
        xml.optAttribute("xhoo","1");
        xml.xmllangAttribute(lang);
        xml.rightAngleBracket();
        xml.append(getData());
        xml.closeElement(getElementName());
        return xml;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public enum StreamContentNamespace {
        client,
        server;
    }

//    public static void main(String[] args) {
//        StreamOpenImpl streamOpen = new StreamOpenImpl("fsdfsd");
//        System.out.println(streamOpen.toXML("fsdfds").toXML("fsdfds"));
//    }
}
