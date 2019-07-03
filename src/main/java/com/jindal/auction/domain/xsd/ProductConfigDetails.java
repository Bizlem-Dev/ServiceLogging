/**
 * ProductConfigDetails.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jindal.auction.domain.xsd;

public class ProductConfigDetails  implements java.io.Serializable {
    private java.lang.String categoryName;

    private java.lang.String configId;

    private java.lang.String configItemId;

    private java.lang.String configOptionId;

    private java.lang.String productId;

    private java.lang.String productName;

    private java.lang.Double quantity;

    private java.lang.Integer seqNo;

    private java.lang.String uomId;

    private java.lang.String uomName;

    public ProductConfigDetails() {
    }

    public ProductConfigDetails(
           java.lang.String categoryName,
           java.lang.String configId,
           java.lang.String configItemId,
           java.lang.String configOptionId,
           java.lang.String productId,
           java.lang.String productName,
           java.lang.Double quantity,
           java.lang.Integer seqNo,
           java.lang.String uomId,
           java.lang.String uomName) {
           this.categoryName = categoryName;
           this.configId = configId;
           this.configItemId = configItemId;
           this.configOptionId = configOptionId;
           this.productId = productId;
           this.productName = productName;
           this.quantity = quantity;
           this.seqNo = seqNo;
           this.uomId = uomId;
           this.uomName = uomName;
    }


    /**
     * Gets the categoryName value for this ProductConfigDetails.
     * 
     * @return categoryName
     */
    public java.lang.String getCategoryName() {
        return categoryName;
    }


    /**
     * Sets the categoryName value for this ProductConfigDetails.
     * 
     * @param categoryName
     */
    public void setCategoryName(java.lang.String categoryName) {
        this.categoryName = categoryName;
    }


    /**
     * Gets the configId value for this ProductConfigDetails.
     * 
     * @return configId
     */
    public java.lang.String getConfigId() {
        return configId;
    }


    /**
     * Sets the configId value for this ProductConfigDetails.
     * 
     * @param configId
     */
    public void setConfigId(java.lang.String configId) {
        this.configId = configId;
    }


    /**
     * Gets the configItemId value for this ProductConfigDetails.
     * 
     * @return configItemId
     */
    public java.lang.String getConfigItemId() {
        return configItemId;
    }


    /**
     * Sets the configItemId value for this ProductConfigDetails.
     * 
     * @param configItemId
     */
    public void setConfigItemId(java.lang.String configItemId) {
        this.configItemId = configItemId;
    }


    /**
     * Gets the configOptionId value for this ProductConfigDetails.
     * 
     * @return configOptionId
     */
    public java.lang.String getConfigOptionId() {
        return configOptionId;
    }


    /**
     * Sets the configOptionId value for this ProductConfigDetails.
     * 
     * @param configOptionId
     */
    public void setConfigOptionId(java.lang.String configOptionId) {
        this.configOptionId = configOptionId;
    }


    /**
     * Gets the productId value for this ProductConfigDetails.
     * 
     * @return productId
     */
    public java.lang.String getProductId() {
        return productId;
    }


    /**
     * Sets the productId value for this ProductConfigDetails.
     * 
     * @param productId
     */
    public void setProductId(java.lang.String productId) {
        this.productId = productId;
    }


    /**
     * Gets the productName value for this ProductConfigDetails.
     * 
     * @return productName
     */
    public java.lang.String getProductName() {
        return productName;
    }


    /**
     * Sets the productName value for this ProductConfigDetails.
     * 
     * @param productName
     */
    public void setProductName(java.lang.String productName) {
        this.productName = productName;
    }


    /**
     * Gets the quantity value for this ProductConfigDetails.
     * 
     * @return quantity
     */
    public java.lang.Double getQuantity() {
        return quantity;
    }


    /**
     * Sets the quantity value for this ProductConfigDetails.
     * 
     * @param quantity
     */
    public void setQuantity(java.lang.Double quantity) {
        this.quantity = quantity;
    }


    /**
     * Gets the seqNo value for this ProductConfigDetails.
     * 
     * @return seqNo
     */
    public java.lang.Integer getSeqNo() {
        return seqNo;
    }


    /**
     * Sets the seqNo value for this ProductConfigDetails.
     * 
     * @param seqNo
     */
    public void setSeqNo(java.lang.Integer seqNo) {
        this.seqNo = seqNo;
    }


    /**
     * Gets the uomId value for this ProductConfigDetails.
     * 
     * @return uomId
     */
    public java.lang.String getUomId() {
        return uomId;
    }


    /**
     * Sets the uomId value for this ProductConfigDetails.
     * 
     * @param uomId
     */
    public void setUomId(java.lang.String uomId) {
        this.uomId = uomId;
    }


    /**
     * Gets the uomName value for this ProductConfigDetails.
     * 
     * @return uomName
     */
    public java.lang.String getUomName() {
        return uomName;
    }


    /**
     * Sets the uomName value for this ProductConfigDetails.
     * 
     * @param uomName
     */
    public void setUomName(java.lang.String uomName) {
        this.uomName = uomName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProductConfigDetails)) return false;
        ProductConfigDetails other = (ProductConfigDetails) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.categoryName==null && other.getCategoryName()==null) || 
             (this.categoryName!=null &&
              this.categoryName.equals(other.getCategoryName()))) &&
            ((this.configId==null && other.getConfigId()==null) || 
             (this.configId!=null &&
              this.configId.equals(other.getConfigId()))) &&
            ((this.configItemId==null && other.getConfigItemId()==null) || 
             (this.configItemId!=null &&
              this.configItemId.equals(other.getConfigItemId()))) &&
            ((this.configOptionId==null && other.getConfigOptionId()==null) || 
             (this.configOptionId!=null &&
              this.configOptionId.equals(other.getConfigOptionId()))) &&
            ((this.productId==null && other.getProductId()==null) || 
             (this.productId!=null &&
              this.productId.equals(other.getProductId()))) &&
            ((this.productName==null && other.getProductName()==null) || 
             (this.productName!=null &&
              this.productName.equals(other.getProductName()))) &&
            ((this.quantity==null && other.getQuantity()==null) || 
             (this.quantity!=null &&
              this.quantity.equals(other.getQuantity()))) &&
            ((this.seqNo==null && other.getSeqNo()==null) || 
             (this.seqNo!=null &&
              this.seqNo.equals(other.getSeqNo()))) &&
            ((this.uomId==null && other.getUomId()==null) || 
             (this.uomId!=null &&
              this.uomId.equals(other.getUomId()))) &&
            ((this.uomName==null && other.getUomName()==null) || 
             (this.uomName!=null &&
              this.uomName.equals(other.getUomName())));
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
        if (getCategoryName() != null) {
            _hashCode += getCategoryName().hashCode();
        }
        if (getConfigId() != null) {
            _hashCode += getConfigId().hashCode();
        }
        if (getConfigItemId() != null) {
            _hashCode += getConfigItemId().hashCode();
        }
        if (getConfigOptionId() != null) {
            _hashCode += getConfigOptionId().hashCode();
        }
        if (getProductId() != null) {
            _hashCode += getProductId().hashCode();
        }
        if (getProductName() != null) {
            _hashCode += getProductName().hashCode();
        }
        if (getQuantity() != null) {
            _hashCode += getQuantity().hashCode();
        }
        if (getSeqNo() != null) {
            _hashCode += getSeqNo().hashCode();
        }
        if (getUomId() != null) {
            _hashCode += getUomId().hashCode();
        }
        if (getUomName() != null) {
            _hashCode += getUomName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProductConfigDetails.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "ProductConfigDetails"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoryName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "categoryName"));
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
        elemField.setFieldName("configItemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "configItemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("configOptionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "configOptionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "productId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "productName"));
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
        elemField.setFieldName("seqNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "seqNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uomId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "uomId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uomName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.auction.jindal.com/xsd", "uomName"));
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
