package pt.upa.ca.ws;

/**
 * Created by lolstorm on 11/05/16.
 */

import pt.upa.ca.ws.crypto.X509CertificateCheck;

import static javax.xml.bind.DatatypeConverter.printBase64Binary;

import javax.jws.WebService;
import java.security.cert.Certificate;


@WebService(endpointInterface = "pt.upa.ca.ws.CaPortType")
public class CaPort implements CaPortType {
    private static final String pathToCerts = "certs/";
    private static final String certType = ".cer";
    private String _wsName;

    @Override
    public String ping(String name){
        return "I'm" + name;
    }

    @Override
    public String requestCertificate(String entity){
        String certPath = pathToCerts + entity + certType;
        try {
            Certificate cert = X509CertificateCheck.readCertificateFile(certPath);
            String encodedCert = printBase64Binary(cert.getEncoded());
            return encodedCert;
        }catch(Exception e){
            return null;
        }
    }




}
