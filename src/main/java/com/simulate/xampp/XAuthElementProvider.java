package com.simulate.xampp;

import org.jivesoftware.smack.provider.ExtensionElementProvider;
import org.xmlpull.v1.XmlPullParser;

public class XAuthElementProvider extends ExtensionElementProvider<XAuthMessage> {
    @Override
    public XAuthMessage parse(XmlPullParser parser, int initialDepth) throws Exception {
        return null;
    }
}
