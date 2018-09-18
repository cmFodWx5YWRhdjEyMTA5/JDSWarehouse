package com.jdswarehouse.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 02-07-2018.
 */

public class DispatchResponse {

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<DispatchData> getDispatchDataList() {
        return dispatchDataList;
    }

    public void setDispatchDataList(List<DispatchData> dispatchDataList) {
        this.dispatchDataList = dispatchDataList;
    }

    @SerializedName("flag")
    @Expose
    public Integer flag;

    @SerializedName("data")
    @Expose
    List<DispatchData> dispatchDataList;

    public class DispatchData {

        @SerializedName("id")
        @Expose
        public String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getReferenceNo() {
            return referenceNo;
        }

        public void setReferenceNo(String referenceNo) {
            this.referenceNo = referenceNo;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public String getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(String warehouseId) {
            this.warehouseId = warehouseId;
        }

        public String getBillerId() {
            return billerId;
        }

        public void setBillerId(String billerId) {
            this.billerId = billerId;
        }

        public String getBiller() {
            return biller;
        }

        public void setBiller(String biller) {
            this.biller = biller;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getInternalNote() {
            return internalNote;
        }

        public void setInternalNote(String internalNote) {
            this.internalNote = internalNote;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getProductDiscount() {
            return productDiscount;
        }

        public void setProductDiscount(String productDiscount) {
            this.productDiscount = productDiscount;
        }

        public String getOrderDiscount() {
            return orderDiscount;
        }

        public void setOrderDiscount(String orderDiscount) {
            this.orderDiscount = orderDiscount;
        }

        public String getOrderDiscountId() {
            return orderDiscountId;
        }

        public void setOrderDiscountId(String orderDiscountId) {
            this.orderDiscountId = orderDiscountId;
        }

        public String getTotalDiscount() {
            return totalDiscount;
        }

        public void setTotalDiscount(String totalDiscount) {
            this.totalDiscount = totalDiscount;
        }

        public String getProductTax() {
            return productTax;
        }

        public void setProductTax(String productTax) {
            this.productTax = productTax;
        }

        public String getOrderTaxId() {
            return orderTaxId;
        }

        public void setOrderTaxId(String orderTaxId) {
            this.orderTaxId = orderTaxId;
        }

        public String getOrderTax() {
            return orderTax;
        }

        public void setOrderTax(String orderTax) {
            this.orderTax = orderTax;
        }

        public String getTotalTax() {
            return totalTax;
        }

        public void setTotalTax(String totalTax) {
            this.totalTax = totalTax;
        }

        public String getShipping() {
            return shipping;
        }

        public void setShipping(String shipping) {
            this.shipping = shipping;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public String getSupplier() {
            return supplier;
        }

        public void setSupplier(String supplier) {
            this.supplier = supplier;
        }

        public String getQuoteFromApp() {
            return quoteFromApp;
        }

        public void setQuoteFromApp(String quoteFromApp) {
            this.quoteFromApp = quoteFromApp;
        }

        public String getIsQuoteFromApp() {
            return isQuoteFromApp;
        }

        public void setIsQuoteFromApp(String isQuoteFromApp) {
            this.isQuoteFromApp = isQuoteFromApp;
        }

        public String getIsUserAgree() {
            return isUserAgree;
        }

        public void setIsUserAgree(String isUserAgree) {
            this.isUserAgree = isUserAgree;
        }

        public String getReviewByAdmin() {
            return reviewByAdmin;
        }

        public void setReviewByAdmin(String reviewByAdmin) {
            this.reviewByAdmin = reviewByAdmin;
        }

        public String getIsDelivered() {
            return isDelivered;
        }

        public void setIsDelivered(String isDelivered) {
            this.isDelivered = isDelivered;
        }

        public String getDeliveryVehicle() {
            return deliveryVehicle;
        }

        public void setDeliveryVehicle(String deliveryVehicle) {
            this.deliveryVehicle = deliveryVehicle;
        }

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public String getLoadBy() {
            return loadBy;
        }

        public void setLoadBy(String loadBy) {
            this.loadBy = loadBy;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getDeliveryDay() {
            return deliveryDay;
        }

        public void setDeliveryDay(String deliveryDay) {
            this.deliveryDay = deliveryDay;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @SerializedName("date")
        @Expose
        public String date;

        @SerializedName("reference_no")
        @Expose
        public String referenceNo;

        @SerializedName("customer_id")
        @Expose
        public String customerId;

        @SerializedName("customer")
        @Expose
        public String customer;

        @SerializedName("warehouse_id")
        @Expose
        public String warehouseId;

        @SerializedName("biller_id")
        @Expose
        public String billerId;

        @SerializedName("biller")
        @Expose
        public String biller;

        @SerializedName("note")
        @Expose
        public String note;

        @SerializedName("internal_note")
        @Expose
        public String internalNote;

        @SerializedName("total")
        @Expose
        public String total;

        @SerializedName("product_discount")
        @Expose
        public String productDiscount;


        @SerializedName("order_discount")
        @Expose
        public String orderDiscount;

        @SerializedName("order_discount_id")
        @Expose
        public String orderDiscountId;

        @SerializedName("total_discount")
        @Expose
        public String totalDiscount;

        @SerializedName("product_tax")
        @Expose
        public String productTax;

        @SerializedName("order_tax_id")
        @Expose
        public String orderTaxId;

        @SerializedName("order_tax")
        @Expose
        public String orderTax;

        @SerializedName("total_tax")
        @Expose
        public String totalTax;


        @SerializedName("shipping")
        @Expose
        public String shipping;

        @SerializedName("grand_total")
        @Expose
        public String grandTotal;

        @SerializedName("status")
        @Expose
        public String status;

        @SerializedName("created_by")
        @Expose
        public String createdBy;

        @SerializedName("updated_by")
        @Expose
        public String updatedBy;

        @SerializedName("updated_at")
        @Expose
        public String updatedAt;


        @SerializedName("attachment")
        @Expose
        public String attachment;

        @SerializedName("supplier_id")
        @Expose
        public String supplierId;

        @SerializedName("supplier")
        @Expose
        public String supplier;

        @SerializedName("quote_from_app")
        @Expose
        public String quoteFromApp;

        @SerializedName("is_quote_from_app")
        @Expose
        public String isQuoteFromApp;

        @SerializedName("is_user_agree")
        @Expose
        public String isUserAgree;


        @SerializedName("review_by_admin")
        @Expose
        public String reviewByAdmin;

        @SerializedName("is_delivered")
        @Expose
        public String isDelivered;

        @SerializedName("delivery_vehicle")
        @Expose
        public String deliveryVehicle;

        @SerializedName("delivery_time")
        @Expose
        public String deliveryTime;

        @SerializedName("load_by")
        @Expose
        public String loadBy;

        @SerializedName("orderStatus")
        @Expose
        public String orderStatus;

        @SerializedName("delivery_day")
        @Expose
        public String deliveryDay;

        @SerializedName("username")
        @Expose
        public String username;


    }
}
