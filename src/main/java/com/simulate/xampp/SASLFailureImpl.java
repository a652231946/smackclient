package com.simulate.xampp;

import org.jivesoftware.smack.sasl.packet.SaslStreamElements;

public class SASLFailureImpl extends SaslStreamElements.SASLFailure {
    public SASLFailureImpl(String saslError) {
        super(saslError);
    }
}
