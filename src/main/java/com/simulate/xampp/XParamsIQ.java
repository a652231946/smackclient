package com.simulate.xampp;

import org.jivesoftware.smack.packet.IQ;

public class XParamsIQ extends IQ {

    public static final String ELEMENT = "xparams";

    private String data;

    public XParamsIQ() {
        super(ELEMENT);
    }

    @Override
    protected IQChildElementXmlStringBuilder getIQChildElementBuilder(IQChildElementXmlStringBuilder xml) {
        xml.rightAngleBracket();
        xml.optAppend(getData());
        return xml;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
