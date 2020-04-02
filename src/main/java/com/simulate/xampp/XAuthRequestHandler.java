package com.simulate.xampp;

import org.jivesoftware.smack.iqrequest.IQRequestHandler;
import org.jivesoftware.smack.packet.IQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XAuthRequestHandler implements IQRequestHandler {

    private static final Logger log = LoggerFactory.getLogger(XAuthRequestHandler.class);

    @Override
    public IQ handleIQRequest(IQ iqRequest) {
        log.info("Xauth:handler:{}", iqRequest.toString());
        return null;
    }

    @Override
    public Mode getMode() {
        return null;
    }

    @Override
    public IQ.Type getType() {
        return IQ.Type.get;
    }

    @Override
    public String getElement() {
        return XAuthResult.ELEMENT;
    }

    @Override
    public String getNamespace() {
        return XAuthResult.NAMESPACE;
    }
}
