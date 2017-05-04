package pt.upa.broker.ws.domain;

import pt.upa.broker.ws.TransportStateView;

import javax.xml.bind.annotation.XmlSchemaType;

/**
 * Created by lolstorm on 24/03/16.
 */
public class TransportView {

    protected String id;
    protected String origin;
    protected String destination;
    protected Integer price;
    protected String transporterCompany;
    @XmlSchemaType(name = "string")
    protected TransportStateView state;

    /**
     *
     * @param id
     * @param origin
     * @param destination
     * @param price
     * @param transporterCompany
     */
    public TransportView(String id,
                         String origin,
                         String destination,
                         Integer price,
                         String transporterCompany) {
        setId(id);
        setOrigin(origin);
        setDestination(destination);
        setPrice(price);
        setTransporterCompany(transporterCompany);

    }

    /**
     * Gets the value of the id property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the origin property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the value of the origin property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOrigin(String value) {
        this.origin = value;
    }

    /**
     * Gets the value of the destination property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the value of the destination property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDestination(String value) {
        this.destination = value;
    }

    /**
     * Gets the value of the price property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setPrice(Integer value) {
        this.price = value;
    }

    /**
     * Gets the value of the transporterCompany property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTransporterCompany() {
        return transporterCompany;
    }

    /**
     * Sets the value of the transporterCompany property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTransporterCompany(String value) {
        this.transporterCompany = value;
    }

    /**
     * Gets the value of the state property.
     *
     *
     * @return
     *     possible object is
     *     {@link TransportStateView }
     *
     */
    public TransportStateView getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     *
     * @param value
     *     allowed object is
     *     {@link TransportStateView }
     *
     */
    public void setState(TransportStateView value) {
        this.state = value;
    }

}
