
package com.kubris.qiss.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reqExecTestControlliResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reqExecTestControlliResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://engine.webservices.qengine/}rspReqExecTestControlli" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reqExecTestControlliResponse", propOrder = {
    "_return"
})
public class ReqExecTestControlliResponse {

    @XmlElement(name = "return")
    protected RspReqExecTestControlli _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RspReqExecTestControlli }
     *     
     */
    public RspReqExecTestControlli getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RspReqExecTestControlli }
     *     
     */
    public void setReturn(RspReqExecTestControlli value) {
        this._return = value;
    }

}
