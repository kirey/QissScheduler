
package com.kubris.qiss.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rspReqPlanDescriptionForTestQuery complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rspReqPlanDescriptionForTestQuery">
 *   &lt;complexContent>
 *     &lt;extension base="{http://engine.webservices.qengine/}rspSvc">
 *       &lt;sequence>
 *         &lt;element name="planDebugLog" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="planDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rspReqPlanDescriptionForTestQuery", propOrder = {
    "planDebugLog",
    "planDescription"
})
public class RspReqPlanDescriptionForTestQuery
    extends RspSvc
{

    protected String planDebugLog;
    protected String planDescription;

    /**
     * Gets the value of the planDebugLog property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanDebugLog() {
        return planDebugLog;
    }

    /**
     * Sets the value of the planDebugLog property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanDebugLog(String value) {
        this.planDebugLog = value;
    }

    /**
     * Gets the value of the planDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanDescription() {
        return planDescription;
    }

    /**
     * Sets the value of the planDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanDescription(String value) {
        this.planDescription = value;
    }

}
