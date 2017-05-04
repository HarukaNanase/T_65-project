package pt.upa.broker.ws.cli.handler;


import java.util.*;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.*;
import javax.xml.ws.handler.MessageContext.Scope;
import javax.xml.ws.handler.soap.*;



public class FrontEndHandler implements SOAPHandler<SOAPMessageContext> {

    public static final String TAG_HEADER = "tag";

    public static final String REQUEST_PROPERTY = "sd.frontend.request";
    public static final String RESPONSE_PROPERTY = "sd.frontend.response";
    public static final String TAG_PROPERTY = "sd.frontend.tag";

    public static final String ACK_PROPERTY = "sd.frontend.ack";

    public static final String REQUEST_HEADER = "frontEndRequest";
    public static final String REQUEST_NS = "urn:sdfrontend";
    public static final String ACK_NS = "urn:ack";

    public static final String RESPONSE_HEADER = "frontEndResponse";
    public static final String RESPONSE_NS = REQUEST_NS;

    public static final String CLASS_NAME = FrontEndHandler.class
            .getSimpleName();

    public static final String ACK = "ACK";

    public boolean handleMessage(SOAPMessageContext smc) {
        Boolean outbound = (Boolean) smc
                .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (outbound) {
            // outbound message

            // *** #8 ***
            // get token from response context
            String tagInfo = (String) smc.get(TAG_HEADER);
            // System.out.printf("%s received '%s'%n", CLASS_NAME,
            // propertyValue);

            // put token in response SOAP header
            try {
                // get SOAP envelope
                SOAPMessage msg = smc.getMessage();
                SOAPPart sp = msg.getSOAPPart();
                SOAPEnvelope se = sp.getEnvelope();

                // add header
                SOAPHeader sh = se.getHeader();
                if (sh == null)
                    sh = se.addHeader();

                // add header element (name, namespace prefix, namespace)
                Name name = se.createName(RESPONSE_HEADER, "e", RESPONSE_NS);
                SOAPHeaderElement element = sh.addHeaderElement(name);

                // *** #9 ***
                // add header element value
                element.addTextNode(tagInfo);

                System.out.printf("put tag '%s' on response message header%n",
                        tagInfo);

            } catch (SOAPException e) {
                System.out.printf("Failed to add SOAP header because of %s%n",
                        e);
            }

        } else {
            // inbound message

            // get token from request SOAP header
            try {
                // get SOAP envelope header
                SOAPMessage msg = smc.getMessage();
                SOAPPart sp = msg.getSOAPPart();
                SOAPEnvelope se = sp.getEnvelope();
                SOAPHeader sh = se.getHeader();

                // check header
                if (sh == null) {
                    System.out.println("Header not found.");
                    return true;
                }

                // get first header element
                Name name = se.createName(REQUEST_HEADER, "e", REQUEST_NS);
                Iterator it = sh.getChildElements(name);
                // check header element
                if (!it.hasNext()) {
                    System.out.printf("Header element %s not found.%n",
                            REQUEST_HEADER);
                    return true;
                }
                SOAPElement element = (SOAPElement) it.next();

                // *** #4 ***
                // get header element value
                String tagInfo = element.getValue();
                System.out.printf("%s got '%s'%n", CLASS_NAME, tagInfo);

                // *** #5 ***
                // put token in request context
                smc.put(TAG_HEADER, tagInfo);
                // set property scope to application so that server class can
                // access property
                smc.setScope(TAG_HEADER, Scope.APPLICATION);

                SOAPElement elementAck = (SOAPElement) it.next();

                // *** #4 ***
                // get header element value
                String ack = element.getValue();
                System.out.printf("%s got '%s'%n", CLASS_NAME, ack);

                // *** #5 ***
                // put token in request context
                smc.put(ACK, ack);
                // set property scope to application so that server class can
                // access property
                smc.setScope(ACK, Scope.APPLICATION);

            } catch (SOAPException e) {
                System.out.printf("Failed to get SOAP header because of %s%n",
                        e);
            }

        }

        return true;
    }

    public boolean handleFault(SOAPMessageContext smc) {
        return true;
    }

    public Set<QName> getHeaders() {
        return null;
    }

    public void close(MessageContext messageContext) {
    }

}

