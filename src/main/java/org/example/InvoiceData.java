package org.example;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceData {

    private String invoiceNo;
    private String invoiceDate;
    private String waybillNo;
    private String vatNumber;

    private String senderName;
    private String senderCompany;
    private String senderAddress;
    private String senderCity;
    private String senderCountry;
    private String senderPhone;
    private String senderFax;

    private String receiverName;
    private String receiverCompany;
    private String receiverAddress;
    private String receiverCity;
    private String receiverCountry;
    private String receiverPhone;
    private String receiverFax;
    private String contactName;

    private List<InvoiceItem> items;

    private String totalPackages;
    private String exportReason;
    private String printedDate;

    // ── Calculated ─────────────────────────────────────────────────────────────

    public String getTotalValue() {
        if (items == null) return "0.00";
        return String.format("%.2f",
                items.stream()
                        .map(i -> new BigDecimal(i.getTotal()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

    // ── Nested Item ────────────────────────────────────────────────────────────

    public static class InvoiceItem {
        private String description;
        private String reference;
        private String hsCode;
        private Integer qty;
        private String unit;
        private String price;
        private String currency;
        private String origin;

        public InvoiceItem() {}

        // Calculated — no field, no setter
        public String getTotal() {
            if (price == null || qty == null) return "0.00";
            return String.format("%.2f",
                    new BigDecimal(price).multiply(BigDecimal.valueOf(qty))
            );
        }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getReference() { return reference; }
        public void setReference(String reference) { this.reference = reference; }

        public String getHsCode() { return hsCode; }
        public void setHsCode(String hsCode) { this.hsCode = hsCode; }

        public Integer getQty() { return qty; }
        public void setQty(Integer qty) { this.qty = qty; }

        public String getUnit() { return unit; }
        public void setUnit(String unit) { this.unit = unit; }

        public String getPrice() { return price; }
        public void setPrice(String price) { this.price = price; }

        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }

        public String getOrigin() { return origin; }
        public void setOrigin(String origin) { this.origin = origin; }
    }

    // ── Getters & Setters ──────────────────────────────────────────────────────

    public InvoiceData() {}

    public String getInvoiceNo() { return invoiceNo; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }

    public String getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(String invoiceDate) { this.invoiceDate = invoiceDate; }

    public String getWaybillNo() { return waybillNo; }
    public void setWaybillNo(String waybillNo) { this.waybillNo = waybillNo; }

    public String getVatNumber() { return vatNumber; }
    public void setVatNumber(String vatNumber) { this.vatNumber = vatNumber; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getSenderCompany() { return senderCompany; }
    public void setSenderCompany(String senderCompany) { this.senderCompany = senderCompany; }

    public String getSenderAddress() { return senderAddress; }
    public void setSenderAddress(String senderAddress) { this.senderAddress = senderAddress; }

    public String getSenderCity() { return senderCity; }
    public void setSenderCity(String senderCity) { this.senderCity = senderCity; }

    public String getSenderCountry() { return senderCountry; }
    public void setSenderCountry(String senderCountry) { this.senderCountry = senderCountry; }

    public String getSenderPhone() { return senderPhone; }
    public void setSenderPhone(String senderPhone) { this.senderPhone = senderPhone; }

    public String getSenderFax() { return senderFax; }
    public void setSenderFax(String senderFax) { this.senderFax = senderFax; }

    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

    public String getReceiverCompany() { return receiverCompany; }
    public void setReceiverCompany(String receiverCompany) { this.receiverCompany = receiverCompany; }

    public String getReceiverAddress() { return receiverAddress; }
    public void setReceiverAddress(String receiverAddress) { this.receiverAddress = receiverAddress; }

    public String getReceiverCity() { return receiverCity; }
    public void setReceiverCity(String receiverCity) { this.receiverCity = receiverCity; }

    public String getReceiverCountry() { return receiverCountry; }
    public void setReceiverCountry(String receiverCountry) { this.receiverCountry = receiverCountry; }

    public String getReceiverPhone() { return receiverPhone; }
    public void setReceiverPhone(String receiverPhone) { this.receiverPhone = receiverPhone; }

    public String getReceiverFax() { return receiverFax; }
    public void setReceiverFax(String receiverFax) { this.receiverFax = receiverFax; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public List<InvoiceItem> getItems() { return items; }
    public void setItems(List<InvoiceItem> items) { this.items = items; }

    public String getTotalPackages() { return totalPackages; }
    public void setTotalPackages(String totalPackages) { this.totalPackages = totalPackages; }

    public String getExportReason() { return exportReason; }
    public void setExportReason(String exportReason) { this.exportReason = exportReason; }

    public String getPrintedDate() { return printedDate; }
    public void setPrintedDate(String printedDate) { this.printedDate = printedDate; }
}