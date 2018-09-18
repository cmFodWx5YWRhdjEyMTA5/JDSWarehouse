package com.jdswarehouse.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 04-07-2018.
 */

public class QuotationResponse implements Parcelable{

    @SerializedName("flag")
    @Expose
    public Integer flag;

    protected QuotationResponse(Parcel in) {
        if (in.readByte() == 0) {
            flag = null;
        } else {
            flag = in.readInt();
        }
        image = in.readString();
    }

    public static final Creator<QuotationResponse> CREATOR = new Creator<QuotationResponse>() {
        @Override
        public QuotationResponse createFromParcel(Parcel in) {
            return new QuotationResponse(in);
        }

        @Override
        public QuotationResponse[] newArray(int size) {
            return new QuotationResponse[size];
        }
    };

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<CustInfo> getCustInfoList() {
        return custInfoList;
    }

    public void setCustInfoList(List<CustInfo> custInfoList) {
        this.custInfoList = custInfoList;
    }

    public List<QuoteItem> getQuoteItemList() {
        return quoteItemList;
    }

    public void setQuoteItemList(List<QuoteItem> quoteItemList) {
        this.quoteItemList = quoteItemList;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @SerializedName("customer_info")
    @Expose
    List<CustInfo> custInfoList;

    @SerializedName("quotation_items")
    @Expose
    List<QuoteItem> quoteItemList;

    @SerializedName("image")
    @Expose
    public String image;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (flag == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(flag);
        }
        dest.writeString(image);
    }

    public static class CustInfo implements Parcelable{

        @SerializedName("id")
        @Expose
        public String id;

        protected CustInfo(Parcel in) {
            id = in.readString();
            name = in.readString();
            company = in.readString();
            address = in.readString();
            city = in.readString();
            state = in.readString();
            postalCode = in.readString();
            country = in.readString();
            email = in.readString();
            phone = in.readString();
            image = in.readString();
        }

        public static final Creator<CustInfo> CREATOR = new Creator<CustInfo>() {
            @Override
            public CustInfo createFromParcel(Parcel in) {
                return new CustInfo(in);
            }

            @Override
            public CustInfo[] newArray(int size) {
                return new CustInfo[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("company")
        @Expose
        public String company;

        @SerializedName("address")
        @Expose
        public String address;

        @SerializedName("city")
        @Expose
        public String city;

        @SerializedName("state")
        @Expose
        public String state;

        @SerializedName("postal_code")
        @Expose
        public String postalCode;

        @SerializedName("country")
        @Expose
        public String country;

        @SerializedName("email")
        @Expose
        public String email;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @SerializedName("phone")
        @Expose
        public String phone;

        @SerializedName("image")
        @Expose
        public String image;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeString(company);
            dest.writeString(address);
            dest.writeString(city);
            dest.writeString(state);
            dest.writeString(postalCode);
            dest.writeString(country);
            dest.writeString(email);
            dest.writeString(phone);
            dest.writeString(image);
        }
    }

    public static class QuoteItem implements Parcelable{

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("quote_id")
        @Expose
        public String quoteId;

        @SerializedName("product_id")
        @Expose
        public String productId;

        @SerializedName("product_code")
        @Expose
        public String productCode;

        @SerializedName("product_name")
        @Expose
        public String productName;


        @SerializedName("product_type")
        @Expose
        public String productType;

        @SerializedName("option_id")
        @Expose
        public String optionId;

        @SerializedName("net_unit_price")
        @Expose
        public String netUnitPrice;

        @SerializedName("unit_price")
        @Expose
        public String unitPrice;

        @SerializedName("quantity")
        @Expose
        public String quantity;


        @SerializedName("warehouse_id")
        @Expose
        public String warehouseId;

        @SerializedName("item_tax")
        @Expose
        public String itemTax;

        @SerializedName("tax_rate_id")
        @Expose
        public String taxRateId;

        @SerializedName("tax")
        @Expose
        public String tax;

        @SerializedName("discount")
        @Expose
        public String discount;

        @SerializedName("item_discount")
        @Expose
        public String itemDiscount;

        @SerializedName("subtotal")
        @Expose
        public String subTotal;


        @SerializedName("serial_no")
        @Expose
        public String serialNo;

        @SerializedName("real_unit_price")
        @Expose
        public String realUnitPrice;

        @SerializedName("product_unit_id")
        @Expose
        public String productUnitId;

        @SerializedName("product_unit_code")
        @Expose
        public String productUnitCode;

        @SerializedName("unit_quantity")
        @Expose
        public String unitQuantity;

        @SerializedName("single_product_quantity")
        @Expose
        public String singleProductQuantity;

        @SerializedName("packing_quantity")
        @Expose
        public String packingQuantity;

        @SerializedName("block_quantity_app")
        @Expose
        public String blockQuantityApp;


        @SerializedName("approved_for_app")
        @Expose
        public String approvedForApp;

        protected QuoteItem(Parcel in) {
            id = in.readString();
            quoteId = in.readString();
            productId = in.readString();
            productCode = in.readString();
            productName = in.readString();
            productType = in.readString();
            optionId = in.readString();
            netUnitPrice = in.readString();
            unitPrice = in.readString();
            quantity = in.readString();
            warehouseId = in.readString();
            itemTax = in.readString();
            taxRateId = in.readString();
            tax = in.readString();
            discount = in.readString();
            itemDiscount = in.readString();
            subTotal = in.readString();
            serialNo = in.readString();
            realUnitPrice = in.readString();
            productUnitId = in.readString();
            productUnitCode = in.readString();
            unitQuantity = in.readString();
            singleProductQuantity = in.readString();
            packingQuantity = in.readString();
            blockQuantityApp = in.readString();
            approvedForApp = in.readString();
            itemStatus = in.readString();
            itemLoadQuantity = in.readString();
            itemNotes = in.readString();
            isScan = in.readString();
        }

        public static final Creator<QuoteItem> CREATOR = new Creator<QuoteItem>() {
            @Override
            public QuoteItem createFromParcel(Parcel in) {
                return new QuoteItem(in);
            }

            @Override
            public QuoteItem[] newArray(int size) {
                return new QuoteItem[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuoteId() {
            return quoteId;
        }

        public void setQuoteId(String quoteId) {
            this.quoteId = quoteId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getOptionId() {
            return optionId;
        }

        public void setOptionId(String optionId) {
            this.optionId = optionId;
        }

        public String getNetUnitPrice() {
            return netUnitPrice;
        }

        public void setNetUnitPrice(String netUnitPrice) {
            this.netUnitPrice = netUnitPrice;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(String warehouseId) {
            this.warehouseId = warehouseId;
        }

        public String getItemTax() {
            return itemTax;
        }

        public void setItemTax(String itemTax) {
            this.itemTax = itemTax;
        }

        public String getTaxRateId() {
            return taxRateId;
        }

        public void setTaxRateId(String taxRateId) {
            this.taxRateId = taxRateId;
        }

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getItemDiscount() {
            return itemDiscount;
        }

        public void setItemDiscount(String itemDiscount) {
            this.itemDiscount = itemDiscount;
        }

        public String getSubTotal() {
            return subTotal;
        }

        public void setSubTotal(String subTotal) {
            this.subTotal = subTotal;
        }

        public String getSerialNo() {
            return serialNo;
        }

        public void setSerialNo(String serialNo) {
            this.serialNo = serialNo;
        }

        public String getRealUnitPrice() {
            return realUnitPrice;
        }

        public void setRealUnitPrice(String realUnitPrice) {
            this.realUnitPrice = realUnitPrice;
        }

        public String getProductUnitId() {
            return productUnitId;
        }

        public void setProductUnitId(String productUnitId) {
            this.productUnitId = productUnitId;
        }

        public String getProductUnitCode() {
            return productUnitCode;
        }

        public void setProductUnitCode(String productUnitCode) {
            this.productUnitCode = productUnitCode;
        }

        public String getUnitQuantity() {
            return unitQuantity;
        }

        public void setUnitQuantity(String unitQuantity) {
            this.unitQuantity = unitQuantity;
        }

        public String getSingleProductQuantity() {
            return singleProductQuantity;
        }

        public void setSingleProductQuantity(String singleProductQuantity) {
            this.singleProductQuantity = singleProductQuantity;
        }

        public String getPackingQuantity() {
            return packingQuantity;
        }

        public void setPackingQuantity(String packingQuantity) {
            this.packingQuantity = packingQuantity;
        }

        public String getBlockQuantityApp() {
            return blockQuantityApp;
        }

        public void setBlockQuantityApp(String blockQuantityApp) {
            this.blockQuantityApp = blockQuantityApp;
        }

        public String getApprovedForApp() {
            return approvedForApp;
        }

        public void setApprovedForApp(String approvedForApp) {
            this.approvedForApp = approvedForApp;
        }

        public String getItemStatus() {
            return itemStatus;
        }

        public void setItemStatus(String itemStatus) {
            this.itemStatus = itemStatus;
        }

        public String getItemLoadQuantity() {
            return itemLoadQuantity;
        }

        public void setItemLoadQuantity(String itemLoadQuantity) {
            this.itemLoadQuantity = itemLoadQuantity;
        }

        public String getItemNotes() {
            return itemNotes;
        }

        public void setItemNotes(String itemNotes) {
            this.itemNotes = itemNotes;
        }

        public String getIsScan() {
            return isScan;
        }

        public void setIsScan(String isScan) {
            this.isScan = isScan;
        }

        @SerializedName("item_status")
        @Expose
        public String itemStatus;

        @SerializedName("item_load_quantity")
        @Expose
        public String itemLoadQuantity;

        @SerializedName("item_notes")
        @Expose
        public String itemNotes;

        @SerializedName("is_scan")
        @Expose
        public String isScan;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(quoteId);
            dest.writeString(productId);
            dest.writeString(productCode);
            dest.writeString(productName);
            dest.writeString(productType);
            dest.writeString(optionId);
            dest.writeString(netUnitPrice);
            dest.writeString(unitPrice);
            dest.writeString(quantity);
            dest.writeString(warehouseId);
            dest.writeString(itemTax);
            dest.writeString(taxRateId);
            dest.writeString(tax);
            dest.writeString(discount);
            dest.writeString(itemDiscount);
            dest.writeString(subTotal);
            dest.writeString(serialNo);
            dest.writeString(realUnitPrice);
            dest.writeString(productUnitId);
            dest.writeString(productUnitCode);
            dest.writeString(unitQuantity);
            dest.writeString(singleProductQuantity);
            dest.writeString(packingQuantity);
            dest.writeString(blockQuantityApp);
            dest.writeString(approvedForApp);
            dest.writeString(itemStatus);
            dest.writeString(itemLoadQuantity);
            dest.writeString(itemNotes);
            dest.writeString(isScan);
        }
    }
}
