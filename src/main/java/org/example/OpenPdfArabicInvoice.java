package org.example;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


public class OpenPdfArabicInvoice {

    public static void main(String[] args) throws Exception {
        BigDecimal totalNumber = BigDecimal.valueOf(0);
        List<TableItem> items = Arrays.asList(
                new TableItem("14", "قرص صلب خارجي", "120"),
                new TableItem("3", "لوحة مفاتيح", "40"),
                new TableItem("19", "حاسوب محمول", "150"),
                new TableItem("8", "شاشة عرض", "90.5"),
                new TableItem("1", "فأرة لاسلكية", "30"),
                new TableItem("12", "سماعات رأس", "70"),
                new TableItem("6", "طابعة ليزر", "110"),
                new TableItem("20", "شاحن سريع", "20"),
                new TableItem("5", "ذاكرة متنقلة", "10"),
                new TableItem("11", "حاسوب محمول", "130")
        );

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, new FileOutputStream("invoice-openpdf.pdf"));

        document.open();

        BaseFont bf = BaseFont.createFont(
                "fonts/NotoNaskhArabic-Regular.ttf",
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED);

        Font titleFont = new Font(bf, 18);
        Font normalFont = new Font(bf, 12);

        Paragraph title = new Paragraph(ArabicUtil.shape("فاتورة ضريبية"),titleFont);

        title.setAlignment(Element.ALIGN_RIGHT);

        document.add(title);

        Paragraph customer = new Paragraph(ArabicUtil.shape("العميل: أحمد محمد"),normalFont);

        customer.setAlignment(Element.ALIGN_RIGHT);

        document.add(customer);

        // After document.open() and font setup, before the title paragraph

// Create a 2-column table for header info
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setSpacingAfter(20f);

// --- LEFT COLUMN: Company Info ---
        PdfPCell companyCell = new PdfPCell();
        companyCell.setBorder(Rectangle.NO_BORDER);
        companyCell.setPadding(5f);

        Font boldFont = new Font(bf, 13, Font.BOLD);

        companyCell.addElement(createRightAlignedParagraph(ArabicUtil.shape("منيرو"), boldFont));
        companyCell.addElement(createRightAlignedParagraph(ArabicUtil.shape("الإمارات العربية المتحدة"), normalFont));
        companyCell.addElement(createRightAlignedParagraph(ArabicUtil.shape("نيويورك، نيويورك"), normalFont));
        companyCell.addElement(new Paragraph(" "));
        companyCell.addElement(createRightAlignedParagraph(ArabicUtil.shape("رقم الفاتورة: INV-1001"), normalFont));
        companyCell.addElement(createRightAlignedParagraph(ArabicUtil.shape("التاريخ: 2026-06-10"), normalFont));

// --- RIGHT COLUMN: Bill To ---
        PdfPCell billToCell = new PdfPCell();
        billToCell.setBorder(Rectangle.NO_BORDER);
        billToCell.setPadding(5f);

        billToCell.addElement(createRightAlignedParagraph(ArabicUtil.shape("فاتورة إلى:"), boldFont));
        billToCell.addElement(createRightAlignedParagraph(ArabicUtil.shape("أحمد محمد"), normalFont));
        billToCell.addElement(createRightAlignedParagraph(ArabicUtil.shape("456 شارع العميل"), normalFont));

// Add columns — RIGHT column first (since RTL, right side goes first)
        headerTable.addCell(billToCell);
        headerTable.addCell(companyCell);

        document.add(headerTable);
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);




        addCell(table, "الكمية", normalFont);
        addCell(table, "السعر", normalFont);
        addCell(table, "الصنف", normalFont);


        for (TableItem item : items) {
            addCell(table, item.getQuantity(), normalFont);
            addCell(table, item.getPrice(), normalFont);
            addCell(table, item.getName(), normalFont);
            totalNumber = totalNumber.add(new BigDecimal(item.getPrice()));
        }

        document.add(table);

        document.add(new Paragraph(" "));

        Paragraph total = new Paragraph(
                ArabicUtil.shape("الإجمالي: "+ totalNumber +"جنيه"),
                normalFont);

        total.setAlignment(Element.ALIGN_RIGHT);

        document.add(total);

        document.close();
    }

    private static Paragraph createRightAlignedParagraph(String text, Font font) {
        Paragraph p = new Paragraph(text, font);
        p.setAlignment(Element.ALIGN_RIGHT);
        return p;
    }

    private static void addCell(
            PdfPTable table,
            String text,
            Font font) {

        PdfPCell cell = new PdfPCell(
                new Phrase(
                        text,
                        font));
        cell.setPadding(10f);

        cell.setRunDirection(
                PdfWriter.RUN_DIRECTION_RTL);

        cell.setHorizontalAlignment(
                Element.ALIGN_CENTER);

        table.addCell(cell);
    }

    public static class TableItem {
        private String quantity;
        private String name;
        private String price;

        // Constructor to initialize the object
        public TableItem(String quantity, String name, String price) {
            this.quantity = quantity;
            this.name = name;
            this.price = price;
        }

        // Getter for Quantity
        public String getQuantity() {
            return quantity;
        }

        // Getter for Name (Item)
        public String getName() {
            return name;
        }

        // Getter for Price
        public String getPrice() {
            return price;
        }

        // Optional: Useful for debugging/printing data in the console
        @Override
        public String toString() {
            return "TableItem{" +
                    "quantity='" + quantity + '\'' +
                    ", name='" + name + '\'' +
                    ", price='" + price + '\'' +
                    '}';
        }
    }
}