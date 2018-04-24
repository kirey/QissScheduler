
package com.kubris.qiss.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rspGetExecResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rspGetExecResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://engine.webservices.qengine/}rspSvc">
 *       &lt;sequence>
 *         &lt;element name="execResult" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rspGetExecResult", propOrder = {
    "execResult"
})
public class RspGetExecResult
    extends RspSvc
{

    protected byte[] execResult;

    /**
     * Gets the value of the execResult property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getExecResult() {
        return execResult;
    }

    /**
     * Sets the value of the execResult property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setExecResult(byte[] value) {
        this.execResult = value;
    }

}
