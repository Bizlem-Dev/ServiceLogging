/**
 * Auctions_WSDLLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jindal.auction.auctionServies;

import java.util.ResourceBundle;

public class Auctions_WSDLLocator extends org.apache.axis.client.Service implements com.jindal.auction.auctionServies.Auctions_WSDL {

    private ResourceBundle bundle = ResourceBundle.getBundle("serverConfig");
    public Auctions_WSDLLocator() {
    }


    public Auctions_WSDLLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Auctions_WSDLLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Auctions_WSDLHttpSoap11Endpoint
    private java.lang.String Auctions_WSDLHttpSoap11Endpoint_address = bundle.getString("wsdlService");

    public java.lang.String getAuctions_WSDLHttpSoap11EndpointAddress() {
        return Auctions_WSDLHttpSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Auctions_WSDLHttpSoap11EndpointWSDDServiceName = "Auctions_WSDLHttpSoap11Endpoint";

    public java.lang.String getAuctions_WSDLHttpSoap11EndpointWSDDServiceName() {
        return Auctions_WSDLHttpSoap11EndpointWSDDServiceName;
    }

    public void setAuctions_WSDLHttpSoap11EndpointWSDDServiceName(java.lang.String name) {
        Auctions_WSDLHttpSoap11EndpointWSDDServiceName = name;
    }

    public com.jindal.auction.auctionServies.Auctions_WSDLPortType getAuctions_WSDLHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Auctions_WSDLHttpSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAuctions_WSDLHttpSoap11Endpoint(endpoint);
    }

    public com.jindal.auction.auctionServies.Auctions_WSDLPortType getAuctions_WSDLHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.jindal.auction.auctionServies.Auctions_WSDLSoap11BindingStub _stub = new com.jindal.auction.auctionServies.Auctions_WSDLSoap11BindingStub(portAddress, this);
            _stub.setPortName(getAuctions_WSDLHttpSoap11EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAuctions_WSDLHttpSoap11EndpointEndpointAddress(java.lang.String address) {
        Auctions_WSDLHttpSoap11Endpoint_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.jindal.auction.auctionServies.Auctions_WSDLPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.jindal.auction.auctionServies.Auctions_WSDLSoap11BindingStub _stub = new com.jindal.auction.auctionServies.Auctions_WSDLSoap11BindingStub(new java.net.URL(Auctions_WSDLHttpSoap11Endpoint_address), this);
                _stub.setPortName(getAuctions_WSDLHttpSoap11EndpointWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Auctions_WSDLHttpSoap11Endpoint".equals(inputPortName)) {
            return getAuctions_WSDLHttpSoap11Endpoint();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://auctionServies.auction.jindal.com", "Auctions_WSDL");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://auctionServies.auction.jindal.com", "Auctions_WSDLHttpSoap11Endpoint"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Auctions_WSDLHttpSoap11Endpoint".equals(portName)) {
            setAuctions_WSDLHttpSoap11EndpointEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
