package com.jindal.auction.auctionServies;

public class Auctions_WSDLPortTypeProxy implements com.jindal.auction.auctionServies.Auctions_WSDLPortType {
  private String _endpoint = null;
  private com.jindal.auction.auctionServies.Auctions_WSDLPortType auctions_WSDLPortType = null;
  
  public Auctions_WSDLPortTypeProxy() {
    _initAuctions_WSDLPortTypeProxy();
  }
  
  public Auctions_WSDLPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initAuctions_WSDLPortTypeProxy();
  }
  
  private void _initAuctions_WSDLPortTypeProxy() {
    try {
      auctions_WSDLPortType = (new com.jindal.auction.auctionServies.Auctions_WSDLLocator()).getAuctions_WSDLHttpSoap11Endpoint();
      if (auctions_WSDLPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)auctions_WSDLPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)auctions_WSDLPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (auctions_WSDLPortType != null)
      ((javax.xml.rpc.Stub)auctions_WSDLPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.jindal.auction.auctionServies.Auctions_WSDLPortType getAuctions_WSDLPortType() {
    if (auctions_WSDLPortType == null)
      _initAuctions_WSDLPortTypeProxy();
    return auctions_WSDLPortType;
  }
  
  public void main(java.lang.String[] ar) throws java.rmi.RemoteException{
    if (auctions_WSDLPortType == null)
      _initAuctions_WSDLPortTypeProxy();
    auctions_WSDLPortType.main(ar);
  }
  
  public java.lang.String serviceConsumption(java.lang.String customerId, java.lang.String productCode, java.lang.Double quantity, java.lang.String uom, java.util.Date lastConsumptionDate) throws java.rmi.RemoteException{
    if (auctions_WSDLPortType == null)
      _initAuctions_WSDLPortTypeProxy();
    return auctions_WSDLPortType.serviceConsumption(customerId, productCode, quantity, uom, lastConsumptionDate);
  }
  
  public java.lang.String changeServiceStatus(java.lang.String customerId, java.lang.String productCode, java.lang.String status) throws java.rmi.RemoteException{
    if (auctions_WSDLPortType == null)
      _initAuctions_WSDLPortTypeProxy();
    return auctions_WSDLPortType.changeServiceStatus(customerId, productCode, status);
  }
  
  public com.jindal.auction.domain.xsd.CustomerServiceStatus[] getCustomerServiceStatus(java.lang.String customerId, java.lang.String productId, java.lang.String fromDate, java.lang.String toDate, java.lang.String status) throws java.rmi.RemoteException{
    if (auctions_WSDLPortType == null)
      _initAuctions_WSDLPortTypeProxy();
    return auctions_WSDLPortType.getCustomerServiceStatus(customerId, productId, fromDate, toDate, status);
  }
  
  public java.lang.Double getNoOfserviceAvailable(java.lang.String customerId, java.lang.String productCode) throws java.rmi.RemoteException{
    if (auctions_WSDLPortType == null)
      _initAuctions_WSDLPortTypeProxy();
    return auctions_WSDLPortType.getNoOfserviceAvailable(customerId, productCode);
  }
  
  public com.jindal.auction.domain.xsd.ProductConfigDetails[] getProductConfigList(java.lang.String configIds) throws java.rmi.RemoteException{
    if (auctions_WSDLPortType == null)
      _initAuctions_WSDLPortTypeProxy();
    return auctions_WSDLPortType.getProductConfigList(configIds);
  }
  
  
}