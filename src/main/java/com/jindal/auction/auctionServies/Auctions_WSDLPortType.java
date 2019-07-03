/**
 * Auctions_WSDLPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jindal.auction.auctionServies;

public interface Auctions_WSDLPortType extends java.rmi.Remote {
    public void main(java.lang.String[] ar) throws java.rmi.RemoteException;
    public java.lang.String serviceConsumption(java.lang.String customerId, java.lang.String productCode, java.lang.Double quantity, java.lang.String uom, java.util.Date lastConsumptionDate) throws java.rmi.RemoteException;
    public java.lang.String changeServiceStatus(java.lang.String customerId, java.lang.String productCode, java.lang.String status) throws java.rmi.RemoteException;
    public com.jindal.auction.domain.xsd.CustomerServiceStatus[] getCustomerServiceStatus(java.lang.String customerId, java.lang.String productId, java.lang.String fromDate, java.lang.String toDate, java.lang.String status) throws java.rmi.RemoteException;
    public java.lang.Double getNoOfserviceAvailable(java.lang.String customerId, java.lang.String productCode) throws java.rmi.RemoteException;
    public com.jindal.auction.domain.xsd.ProductConfigDetails[] getProductConfigList(java.lang.String configIds) throws java.rmi.RemoteException;
}
