package pt.upa.ca.ws;

import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;

import javax.xml.registry.JAXRException;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;
import java.util.Map;

import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;

import pt.upa.ca.ws.CaPort;
import pt.upa.ca.ws.CaPortType;
import pt.upa.ca.ws.CaPortService;

/**
 * Created by lolstorm on 11/05/16.
 */
public class CaClient implements CaPortType {
    private String _uddiUrl;
    private String _wsName;
    private String _wsUrl;
    private CaPortType port;
    private static CaPortService service;

    public CaClient(String wsUrl){
        _wsUrl = wsUrl;
        createStub();
    }

    public CaClient(String uddi, String name){
        _uddiUrl = uddi;
        _wsName = name;
        uddiLookup();
        createStub();
    }

    public void uddiLookup() {
        try {
            UDDINaming uddi = new UDDINaming(_uddiUrl);
            _wsUrl = uddi.lookup(_wsName);

        } catch (JAXRException jax) {
            jax.printStackTrace();
        }
    }

    public void createStub(){
        service = new CaPortService();
        port = service.getCaPortPort();
        BindingProvider bindingProvider = (BindingProvider) port;
        Map<String, Object> requestContext = bindingProvider.getRequestContext();
        requestContext.put(ENDPOINT_ADDRESS_PROPERTY, _wsUrl);
    }


    public String ping(String name){
        return port.ping(name);
    }


    public String requestCertificate(String entity) {
        String encodedCert = port.requestCertificate(entity);
    /*    byte[] certByte = parseBase64Binary(encodedCert);
        try {
            CertificateFactory cFactory = CertificateFactory.getInstance("X.509");
            InputStream inCert = new ByteArrayInputStream(certByte);
            Certificate cert = cFactory.generateCertificate(inCert);
            return certByte;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }*/
        return encodedCert;
    }

}
