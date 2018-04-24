
package com.kubris.qiss.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rspReqExecTestControllo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rspReqExecTestControllo">
 *   &lt;complexContent>
 *     &lt;extension base="{http://engine.webservices.qengine/}rspSvc">
 *       &lt;sequence>
 *         &lt;element name="idExec" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rspReqExecTestControllo", propOrder = {
    "idExec"
})
public class RspReqExecTestControllo
    extends RspSvc
{

    protected int idExec;

    /**
     * Gets the value of the idExec property.
     * 
     */
    public int getIdExec() {
        return idExec;
    }

    /**
     * Sets the value of the idExec property.
     * 
     */
    public void setIdExec(int value) {
        this.idExec = value;
    }

}
