
package com.kubris.qiss.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rspSvc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rspSvc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="replyCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="replyDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rspSvc", propOrder = {
    "replyCode",
    "replyDescription"
})
@XmlSeeAlso({
    RspReqExecTestQuery.class,
    RspReqExecTestControllo.class,
    RspCancelExec.class,
    RspReqExecTestControlli.class,
    RspReqPlanDescriptionForTestQuery.class,
    RspCheckExecStatus.class,
    RspGetExecResult.class,
    RspReqExecControlli.class
})
public class RspSvc {

    protected int replyCode;
    protected String replyDescription;

    /**
     * Gets the value of the replyCode property.
     * 
     */
    public int getReplyCode() {
        return replyCode;
    }

    /**
     * Sets the value of the replyCode property.
     * 
     */
    public void setReplyCode(int value) {
        this.replyCode = value;
    }

    /**
     * Gets the value of the replyDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReplyDescription() {
        return replyDescription;
    }

    /**
     * Sets the value of the replyDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReplyDescription(String value) {
        this.replyDescription = value;
    }

}
