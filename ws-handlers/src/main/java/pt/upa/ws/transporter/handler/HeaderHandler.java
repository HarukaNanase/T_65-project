package pt.upa.ws.transporter.handler;

import example.crypto.X509CertificateCheck;
import example.crypto.X509DigitalSignature;
import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.ProtocolException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayInputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import pt.upa.ca.ws.CaPortType;
import pt.upa.ca.ws.CaClient;

import static javax.xml.bind.DatatypeConverter.parseBase64Binary;
import static javax.xml.bind.DatatypeConverter.printBase64Binary;

/**
 * Created by lolstorm on 13/05/16.
 */
public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {

    private String _uddiUrl;
    private String _wsName;
    private String _wsUrl;

    public static final String REQUEST_PROPERTY = "sd.broker.request";
    public static final String RESPONSE_PROPERTY = "sd.broker.response";

    public static final String RESPONSE_HEADER = "brokerSignature";
    public static final String RESPONSE_NS = "http://ws.broker.upa.pt/";

    public static final String REQUEST_HEADER = "transporterSignature";
    public static final String REQUEST_NS = "http://ws.transporter.upa.pt/";

    private Vector<String> receivedTags = new Vector<>();
    private Vector<String> generatedTags = new Vector<>();

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (outbound) {

            //System.out.printf("%s received '%s'%n", CLASS_NAME, propertyValue);

            // put token in response SOAP header
            try {
                // get SOAP envelope
                SOAPMessage msg = context.getMessage();
                SOAPPart sp = msg.getSOAPPart();
                SOAPEnvelope se = sp.getEnvelope();
                PrivateKey key = PKeyGeneration();

                // add header
                SOAPHeader sh = se.getHeader();
                if (sh == null) {
                    sh = se.addHeader();
                }

                // body
                SOAPBody sb = se.getBody();
                if(sb == null) {
                    throw new ProtocolException("Body not found.");
                }

                //body content
                Iterator it1 = sb.getChildElements();

                if(!it1.hasNext()){
                    throw new ProtocolException("Body not found.");
                }

                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

                final byte array[] = new byte[4];
                random.nextBytes(array);
                String converted = printBase64Binary(array);
                while(generatedTags.contains(converted)){
                    random.nextBytes(array);
                    converted = printBase64Binary(array);
                }
                generatedTags.add(converted);

                Name nameHeader = se.createName(RESPONSE_HEADER, "b", RESPONSE_NS);
                SOAPHeaderElement element = sh.addHeaderElement(nameHeader);

                SOAPElement se1 = (SOAPElement) it1.next();
                String toDigest = se1.getTextContent();

                //Concatenate body value and nonce
                toDigest=toDigest.concat(converted);


                //Creates signature with given digest
                Signature signature = Signature.getInstance("SHA1WithRSA");
                signature.initSign(key);
                signature.update(toDigest.getBytes());
                byte[] signatureBytes = signature.sign();

                String digestToAttach = printBase64Binary(signatureBytes);
                element.addTextNode(digestToAttach);
                msg.saveChanges();


            } catch (SOAPException e) {
                System.out.printf("Failed to add SOAP header because of %s%n", e);
            }
            catch (NoSuchAlgorithmException e) {
                System.out.printf(" %s%n", e.getMessage());
            }
            catch (InvalidKeyException e) {
                System.out.printf("InvalidKey: %s%n", e.getMessage());
            }
            catch (SignatureException e) {
                System.out.printf("Signature: %s%n", e.getMessage());
            }
            catch (Exception e) {
                System.out.printf("Signature: %s%n", e.getMessage());
            }
        }
        else {
            // inbound message

            // get token from request SOAP header
            try {

                // get SOAP envelope header
                SOAPMessage msg = context.getMessage();
                SOAPPart sp = msg.getSOAPPart();
                SOAPEnvelope se = sp.getEnvelope();

                // add header
                SOAPHeader sh = se.getHeader();
                if (sh == null) {
                    sh = se.addHeader();
                }

                //Gets signature
                Name name = se.createName(REQUEST_HEADER, "t", REQUEST_NS);

                Iterator it = sh.getChildElements(name);
                SOAPElement se1 = (SOAPElement) it.next();
                String id = se1.getTextContent();
                PublicKey key = publicKeyGeneration(id);

                if(!it.hasNext()){
                    throw new ProtocolException("Header element not found.");
                }
                String toDigest = "";

                toDigest= toDigest.concat(id);

                //Decrypting signature
                Signature sig = Signature.getInstance("SHA1WithRSA");
                sig.initVerify(key);
                sig.update(toDigest.getBytes());


            } catch (SOAPException e) {
                System.out.printf("Failed to add SOAP header because of %s%n", e);
            }
            catch (Exception e) {
                System.out.printf("Failed to add SOAP header because of %s%n", e);
            }


        }

        return false;
    }

    public PrivateKey PKeyGeneration() throws java.lang.Exception{
        PrivateKey priv = X509DigitalSignature.getPrivateKeyFromKeystore
                ("/Broker.jks", "ins3cur3".toCharArray(),
                        "Broker", "1nsecure".toCharArray());
        return priv;
    }

    public PublicKey publicKeyGeneration(String transporter) throws java.lang.Exception {
        CaPortType ca = new CaClient("http://localhost:9090", "UpaCA");
        String certificate = ca.requestCertificate(transporter);

        byte[] byteCertificate = parseBase64Binary(certificate);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate c = cf.generateCertificate(new ByteArrayInputStream(byteCertificate));
        KeyStore keystore = X509DigitalSignature.readKeystoreFile("key/Broker.jks", "ins3cur3".toCharArray());
        Certificate cstore = keystore.getCertificate("ca");

        if(!X509CertificateCheck.verifySignedCertificate(c, cstore.getPublicKey())){
            throw new ProtocolException("certificate is not signed by Ca-ws");
        }

        return X509DigitalSignature.getPublicKeyFromCertificate(c);
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    @Override
    public void close(MessageContext context) {

    }
}