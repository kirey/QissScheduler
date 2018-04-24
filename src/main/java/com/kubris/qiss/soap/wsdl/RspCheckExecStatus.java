
package com.kubris.qiss.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rspCheckExecStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rspCheckExecStatus">
 *   &lt;complexContent>
 *     &lt;extension base="{http://engine.webservices.qengine/}rspSvc">
 *       &lt;sequence>
 *         &lt;element name="annullamentoRichiesto" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="descStatoLivello1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descStatoLivello2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statoEsecuzione" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rspCheckExecStatus", propOrder = {
    "annullamentoRichiesto",
    "descStatoLivello1",
    "descStatoLivello2",
    "statoEsecuzione"
})
public class RspCheckExecStatus
    extends RspSvc
{

    protected boolean annullamentoRichiesto;
    protected String descStatoLivello1;
    protected String descStatoLivello2;
    protected int statoEsecuzione;

    /**
     * Gets the value of the annullamentoRichiesto property.
     * 
     */
    public boolean isAnnullamentoRichiesto() {
        return annullamentoRichiesto;
    }

    /**
     * Sets the value of the annullamentoRichiesto property.
     * 
     */
    public void setAnnullamentoRichiesto(boolean value) {
        this.annullamentoRichiesto = value;
    }

    /**
     * Gets the value of the descStatoLivello1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescStatoLivello1() {
        return descStatoLivello1;
    }

    /**
     * Sets the value of the descStatoLivello1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescStatoLivello1(String value) {
        this.descStatoLivello1 = value;
    }

    /**
     * Gets the value of the descStatoLivello2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescStatoLivello2() {
        return descStatoLivello2;
    }

    /**
     * Sets the value of the descStatoLivello2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescStatoLivello2(String value) {
        this.descStatoLivello2 = value;
    }

    /**
     * Gets the value of the statoEsecuzione property.
     * 
     */
    public int getStatoEsecuzione() {
        return statoEsecuzione;
    }

    /**
     * Sets the value of the statoEsecuzione property.
     * 
     */
    public void setStatoEsecuzione(int value) {
        this.statoEsecuzione = value;
    }

}
