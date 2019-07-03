/**
 * CustomerServiceStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jindal.auction.domain.xsd;

public class CustomerServiceStatus  implements java.io.Serializable {
    private java.lang.String blog;

    private java.lang.String comment;

    private java.lang.String configId;

    private java.lang.Double consumptionQuantity;

    private java.lang.String customerId;

    private java.lang.String customerName;

    private java.util.Date lastConsumptionDate;

    private java.lang.String likeDislike;

    private java.lang.String orderId;

    private java.lang.String productCode;

    private com.jindal.auction.domain.xsd.ProductConfigDetails[] productConfigList;

    private java.lang.String productDescription;

    private java.lang.Double quantity;

    private java.lang.String rating;

    private java.lang.String rfq;

    private java.util.Date serviceEndDate;

    private java.util.Date serviceStartDate;

    private java.lang.String share;

    private java.lang.String shipmentId;

    private java.lang.String status;

    private java.lang.String uom;
    private java.lang.Integer serviceId;

    public CustomerServiceStatus() {
    }

    public CustomerServiceStatus(
    		java.lang.Integer serviceId,
    		java.lang.String blog,
           java.lang.String comment,
           java.lang.String configId,
           java.lang.Double consumptionQuantity,
           java.lang.String customerId,
           java.lang.String customerName,
           java.util.Date lastConsumptionDate,
           java.lang.String likeDislike,
           java.lang.String orderId,
           java.lang.String productCode,
           com.jindal.auction.domain.xsd.ProductConfigDetails[] productConfigList,
           java.lang.String productDescription,
           java.lang.Double quantity,
           java.lang.String rating,
           java.lang.String rfq,
           java.util.Date serviceEndDate,
           java.util.Date serviceStartDate,
           java.lang.String share,
           java.lang.String shipmentId,
           java.lang.String status,
           java.lang.String uom) {
           this.blog = blog;
           this.comment = comment;
           this.configId = configId;
           this.consumptionQuantity = consumptionQuantity;
           this.customerId = customerId;
           this.customerName = customerName;
           this.lastConsumptionDate = lastConsumptionDate;
           this.likeDislike = likeDislike;
           this.orderId = orderId;
           this.productCode = productCode;
           this.productConfigList = productConfigList;
           this.productDescription = productDescription;
           this.quantity = quantity;
           this.rating = rating;
           this.rfq = rfq;
           this.serviceEndDate = serviceEndDate;
           this.serviceStartDate = serviceStartDate;
           this.share = share;
           this.shipmentId = shipmentId;
           this.status = status;
           this.uom = uom;
           this.serviceId = serviceId;
    }


    /**
     * Gets the blog value for this CustomerServiceStatus.
     * 
     * @return blog
     */
    public java.lang.String getBlog() {
        return blog;
    }

    public java.lang.Integer getserviceId() {
        return serviceId;
    }

    /**
     * Sets the blog value for this CustomerServiceStatus.
     * 
     * @param blog
     */
    public void setBlog(java.lang.String blog) {
        this.blog = blog;
    }

    public void setserviceId(java.lang.Integer serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * Gets the comment value for this CustomerServiceStatus.
     * 
     * @return comment
     */
    public java.lang.String getComment() {
        return comment;
    }


    /**
     * Sets the comment value for this CustomerServiceStatus.
     * 
     * @param comment
     */
    public void setComment(java.lang.String comment) {
        this.comment = comment;
    }


    /**
     * Gets the configId value for this CustomerServiceStatus.
     * 
     * @return configId
     */
    public java.lang.String getConfigId() {
        return configId;
    }


    /**
     * Sets the configId value for this CustomerServiceStatus.
     * 
     * @param configId
     */
    public void setConfigId(java.lang.String configId) {
        this.configId = configId;
    }


    /**
     * Gets the consumptionQuantity value for this CustomerServiceStatus.
     * 
     * @return consumptionQuantity
     */
    public java.lang.Double getConsumptionQuantity() {
        return consumptionQuantity;
    }


    /**
     * Sets the consumptionQuantity value for this CustomerServiceStatus.
     * 
     * @param consumptionQuantity
     */
    public void setConsumptionQuantity(java.lang.Double consumptionQuantity) {
        this.consumptionQuantity = consumptionQuantity;
    }


    /**
     * Gets the customerId value for this CustomerServiceStatus.
     * 
     * @return customerId
     */
    public java.lang.String getCustomerId() {
        return customerId;
    }


    /**
     * Sets the customerId value for this CustomerServiceStatus.
     * 
     * @param customerId
     */
    public void setCustomerId(java.lang.String customerId) {
        this.customerId = customerId;
    }


    /**
     * Gets the customerName value for this CustomerServiceStatus.
     * 
     * @return customerName
     */
    public java.lang.String getCustomerName() {
        return customerName;
    }


    /**
     * Sets the customerName value for this CustomerServiceStatus.
     * 
     * @param customerName
     */
    public void setCustomerName(java.lang.String customerName) {
        this.customerName = customerName;
    }


    /**
     * Gets the lastConsumptionDate value for this CustomerServiceStatus.
     * 
     * @return lastConsumptionDate
     */
    public java.util.Date getLastConsumptionDate() {
        return lastConsumptionDate;
    }


    /**
     * Sets the lastConsumptionDate value for this CustomerServiceStatus.
     * 
     * @param lastConsumptionDate
     */
    public void setLastConsumptionDate(java.util.Date lastConsumptionDate) {
        this.lastConsumptionDate = lastConsumptionDate;
    }


    /**
     * Gets the likeDislike value for this CustomerServiceStatus.
     * 
     * @return likeDislike
     */
    public java.lang.String getLikeDislike() {
        return likeDislike;
    }


    /**
     * Sets the likeDislike value for this CustomerServiceStatus.
     * 
     * @param likeDislike
     */
    public void setLikeDislike(java.lang.String likeDislike) {
        this.likeDislike = likeDislike;
    }


    /**
     * Gets the orderId value for this CustomerServiceStatus.
     * 
     * @return orderId
     */
    public java.lang.String getOrderId() {
        return orderId;
    }


    /**
     * Sets the orderId value for this CustomerServiceStatus.
     * 
     * @param orderId
     */
    public void setOrderId(java.lang.String orderId) {
        this.orderId = orderId;
    }


    /**
     * Gets the productCode value for this CustomerServiceStatus.
     * 
     * @return productCode
     */
    public java.lang.String getProductCode() {
        return productCode;
    }


    /**
     * Sets the productCode value for this CustomerServiceStatus.
     * 
     * @param productCode
     */
    public void setProductCode(java.lang.String productCode) {
        this.productCode = productCode;
    }


    /**
     * Gets the productConfigList value for this CustomerServiceStatus.
     * 
     * @return productConfigList
     */
    public com.jindal.auction.domain.xsd.ProductConfigDetails[] getProductConfigList() {
        return productConfigList;
    }


    /**
     * Sets the productConfigList value for this CustomerServiceStatus.
     * 
     * @param productConfigList
     */
    public void setProductConfigList(com.jindal.auction.domain.xsd.ProductConfigDetails[] productConfigList) {
        this.productConfigList = productConfigList;
    }

    public com.jindal.auction.domain.xsd.ProductConfigDetails getProductConfigList(int i) {
        return this.productConfigList[i];
    }

    public void setProductConfigList(int i, com.jindal.auction.domain.xsd.ProductConfigDetails _value) {
        this.productConfigList[i] = _value;
    }


    /**
     * Gets the productDescription value for this CustomerServiceStatus.
     * 
     * @return productDescription
     */
    public java.lang.String getProductDescription() {
        return productDescription;
    }


    /**
     * Sets the productDescription value for this CustomerServiceStatus.
     * 
     * @param productDescription
     */
    public void setProductDescription(java.lang.String productDescription) {
        this.productDescription = productDescription;
    }


    /**
     * Gets the quantity value for this CustomerServiceStatus.
     * 
     * @return quantity
     */
    public java.lang.Double getQuantity() {
        return quantity;
    }


    /**
     * Sets the quantity value for this CustomerServiceStatus.
     * 
     * @param quantity
     */
    public void setQuantity(java.lang.Double quantity) {
        this.quantity = quantity;
    }


    /**
     * Gets the rating value for this CustomerServiceStatus.
     * 
     * @return rating
     */
    public java.lang.String getRating() {
        return rating;
    }


    /**
     * Sets the rating value for this CustomerServiceStatus.
     * 
     * @param rating
     */
    public void setRating(java.lang.String rating) {
        this.rating = rating;
    }


    /**
     * Gets the rfq value for this CustomerServiceStatus.
     * 
     * @return rfq
     */
    public java.lang.String getRfq() {
        return rfq;
    }


    /**
     * Sets the rfq value for this CustomerServiceStatus.
     * 
     * @param rfq
     */
    public void setRfq(java.lang.String rfq) {
        this.rfq = rfq;
    }


    /**
     * Gets the serviceEndDate value for this CustomerServiceStatus.
     * 
     * @return serviceEndDate
     */
    public java.util.Date getServiceEndDate() {
        return serviceEndDate;
    }


    /**
     * Sets the serviceEndDate value for this CustomerServiceStatus.
     * 
     * @param serviceEndDate
     */
    public void setServiceEndDate(java.util.Date serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }


    /**
     * Gets the serviceStartDate value for this CustomerServiceStatus.
     * 
     * @return serviceStartDate
     */
    public java.util.Date getServiceStartDate() {
        return serviceStartDate;
    }


    /**
     * Sets the serviceStartDate value for this CustomerServiceStatus.
     * 
     * @param serviceStartDate
     */
    public void setServiceStartDate(java.util.Date serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }


    /**
     * Gets the share value for this CustomerServiceStatus.
     * 
     * @return share
     */
    public java.lang.String getShare() {
        return share;
    }


    /**
     * Sets the share value for this CustomerServiceStatus.
     * 
     * @param share
     */
    public void setShare(java.lang.String share) {
        this.share = share;
    }


    /**
     * Gets the shipmentId value for this CustomerServiceStatus.
     * 
     * @return shipmentId
     */
    public java.lang.String getShipmentId() {
        return shipmentId;
    }


    /**
     * Sets the shipmentId value for this CustomerServiceStatus.
     * 
     * @param shipmentId
     */
    public void setShipmentId(java.lang.String shipmentId) {
        this.shipmentId = shipmentId;
    }


    /**
     * Gets the status value for this CustomerServiceStatus.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this CustomerServiceStatus.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the uom value for this CustomerServiceStatus.
     * 
     * @return uom
     */
    public java.lang.String getUom() {
        return uom;
    }


    /**
     * Sets the uom value for this CustomerServiceStatus.
     * 
     * @param uom
     */
    public void setUom(java.lang.String uom) {
        this.uom = uom;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustomerServiceStatus)) return false;
        CustomerServiceStatus other = (CustomerServiceStatus) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.blog==null && other.getBlog()==null) || 
             (this.blog!=null &&
              this.blog.equals(other.getBlog()))) &&
            ((this.comment==null && other.getComment()==null) || 
             (this.comment!=null &&
              this.comment.equals(other.getComment()))) &&
            ((this.configId==null && other.getConfigId()==null) || 
             (this.configId!=null &&
              this.configId.equals(other.getConfigId()))) &&
            ((this.consumptionQuantity==null && other.getConsumptionQuantity()==null) || 
             (this.consumptionQuantity!=null &&
              this.consumptionQuantity.equals(other.getConsumptionQuantity()))) &&
            ((this.customerId==null && other.getCustomerId()==null) || 
             (this.customerId!=null &&
              this.customerId.equals(other.getCustomerId()))) &&
            ((this.customerName==null && other.getCustomerName()==null) || 
             (this.customerName!=null &&
              this.customerName.equals(other.getCustomerName()))) &&
            ((this.lastConsumptionDate==null && other.getLastConsumptionDate()==null) || 
             (this.lastConsumptionDate!=null &&
              this.lastConsumptionDate.equals(other.getLastConsumptionDate()))) &&
            ((this.likeDislike==null && other.getLikeDislike()==null) || 
             (this.likeDislike!=null &&
              this.likeDislike.equals(other.getLikeDislike()))) &&
            ((this.orderId==null && other.getOrderId()==null) || 
             (this.orderId!=null &&
              this.orderId.equals(other.getOrderId()))) &&
            ((this.productCode==null && other.getProductCode()==null) || 
             (this.productCode!=null &&
              this.productCode.equals(other.getProductCode()))) &&
            ((this.productConfigList==null && other.getProductConfigList()==null) || 
             (this.productConfigList!=null &&
              java.util.Arrays.equals(this.productConfigList, other.getProductConfigList()))) &&
            ((this.productDescription==null && other.getProductDescription()==null) || 
             (this.productDescription!=null &&
              this.productDescription.equals(other.getProductDescription()))) &&
            ((this.quantity==null && other.getQuantity()==null) || 
             (this.quantity!=null &&
              this.quantity.equals(other.getQuantity()))) &&
            ((this.rating==null && other.getRating()==null) || 
             (this.rating!=null &&
              this.rating.equals(other.getRating()))) &&
            ((this.rfq==null && other.getRfq()==null) || 
             (this.rfq!=null &&
              this.rfq.equals(other.getRfq()))) &&
            ((this.serviceEndDate==null && other.getServiceEndDate()==null) || 
             (this.serviceEndDate!=null &&
              this.serviceEndDate.equals(other.getServiceEndDate()))) &&
            ((this.serviceStartDate==null && other.getServiceStartDate()==null) || 
             (this.serviceStartDate!=null &&
              this.serviceStartDate.equals(other.getServiceStartDate()))) &&
            ((this.share==null && other.getShare()==null) || 
             (this.share!=null &&
              this.share.equals(other.getShare()))) &&
            ((this.shipmentId==null && other.getShipmentId()==null) || 
             (this.shipmentId!=null &&
              this.shipmentId.equals(other.getShipmentId()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.uom==null && other.getUom()==null) || 
             (this.uom!=null &&
              this.uom.equals(other.getUom())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getBlog() != null) {
            _hashCode += getBlog().hashCode();
        }
        if (getComment() != null) {
            _hashCode += getComment().hashCode();
        }
        if (getConfigId() != null) {
            _hashCode += getConfigId().hashCode();
        }
        if (getConsumptionQuantity() != null) {
            _hashCode += getConsumptionQuantity().hashCode();
        }
        if (getCustomerId() != null) {
            _hashCode += getCustomerId().hashCode();
        }
        if (getCustomerName() != null) {
            _hashCode += getCustomerName().hashCode();
        }
        if (getLastConsumptionDate() != null) {
            _hashCode += getLastConsumptionDate().hashCode();
        }
        if (getLikeDislike() != null) {
            _hashCode += getLikeDislike().hashCode();
        }
        if (getOrderId() != null) {
            _hashCode += getOrderId().hashCode();
        }
        if (getProductCode() != null) {
            _hashCode += getProductCode().hashCode();
        }
        if (getProductConfigList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProductConfigList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProductConfigList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProductDescription() != null) {
            _hashCode += getProductDescription().hashCode();
        }
        if (getQuantity() != null) {
            _hashCode += getQuantity().hashCode();
        }
        if (getRating() != null) {
            _hashCode += getRating().hashCode();
        }
        if (getRfq() != null) {
            _hashCode += getRfq().hashCode();
        }
        if (getServiceEndDate() != null) {
            _hashCode += getServiceEndDate().hashCode();
        }
        if (getServiceStartDate() != null) {
            _hashCode += getServiceStartDate().hashCode();
        }
        if (getShare() != null) {
            _hashCode += getShare().hashCode();
        }
        if (getShipmentId() != null) {
            _hashCode += getShipmentId().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getUom() != null) {
            _hashCode += getUom().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomerServiceStatus.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "CustomerServiceStatus"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blog");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "blog"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "comment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("configId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "configId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumptionQuantity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "consumptionQuantity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "customerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "customerName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastConsumptionDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "lastConsumptionDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("likeDislike");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "likeDislike"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "orderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "productCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productConfigList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "productConfigList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "ProductConfigDetails"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "productDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "quantity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rating");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "rating"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rfq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "rfq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceEndDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "serviceEndDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceStartDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "serviceStartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("share");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "share"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "shipmentId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "uom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
