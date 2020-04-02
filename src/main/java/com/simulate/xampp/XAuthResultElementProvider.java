package com.simulate.xampp;

import org.jivesoftware.smack.provider.ExtensionElementProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParser;

public class XAuthResultElementProvider extends ExtensionElementProvider<XAuthResult> {

    private static final Logger log = LoggerFactory.getLogger(XAuthResultElementProvider.class);

    @Override
    public XAuthResult parse(XmlPullParser parser, int initialDepth) throws Exception {
        log.info("XAUTH:RESULT: {}", parser.getText());
        return null;
    }
}
