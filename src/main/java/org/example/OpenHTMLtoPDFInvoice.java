package org.example;

import com.openhtmltopdf.bidi.support.ICUBidiReorderer;
import com.openhtmltopdf.bidi.support.ICUBidiSplitter;
import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder.TextDirection;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class OpenHTMLtoPDFInvoice {

    private final TemplateEngine templateEngine;

    public OpenHTMLtoPDFInvoice() {

        ClassLoaderTemplateResolver resolver =
                new ClassLoaderTemplateResolver();

        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setTemplateMode("HTML");

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
    }

    public void generatePdf(
            InvoiceData invoice,
            String templateName,
            String outputFile
    ) throws Exception {

        Context context = new Context();

        context.setVariable("invoiceNo",      invoice.getInvoiceNo());
        context.setVariable("invoiceDate",    invoice.getInvoiceDate());
        context.setVariable("waybillNo",      invoice.getWaybillNo());
        context.setVariable("vatNumber",      invoice.getVatNumber());

        context.setVariable("senderName",     invoice.getSenderName());
        context.setVariable("senderCompany",  invoice.getSenderCompany());
        context.setVariable("senderAddress",  invoice.getSenderAddress());
        context.setVariable("senderCity",     invoice.getSenderCity());
        context.setVariable("senderCountry",  invoice.getSenderCountry());
        context.setVariable("senderPhone",    invoice.getSenderPhone());
        context.setVariable("senderFax",      invoice.getSenderFax());

        context.setVariable("receiverName",    invoice.getReceiverName());
        context.setVariable("receiverCompany", invoice.getReceiverCompany());
        context.setVariable("receiverAddress", invoice.getReceiverAddress());
        context.setVariable("receiverCity",    invoice.getReceiverCity());
        context.setVariable("receiverPhone",   invoice.getReceiverPhone());
        context.setVariable("receiverFax",     invoice.getReceiverFax());
        context.setVariable("contactName",     invoice.getContactName());


        context.setVariable("items",           invoice.getItems());

        context.setVariable("totalPackages",   invoice.getTotalPackages());
        context.setVariable("totalValue",      invoice.getTotalValue()); // calculated
        context.setVariable("exportReason",    invoice.getExportReason());
        context.setVariable("printedDate",     invoice.getPrintedDate());

        String html = templateEngine.process(templateName, context);

        try (OutputStream os = new FileOutputStream(outputFile)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.useUnicodeBidiSplitter(new ICUBidiSplitter.ICUBidiSplitterFactory());
            builder.useUnicodeBidiReorderer(new ICUBidiReorderer());
            builder.defaultTextDirection(TextDirection.RTL);
            builder.withHtmlContent(
                    html,
                    OpenHTMLtoPDFInvoice.class.getResource("/templates/").toExternalForm()
            );
            builder.useFont(
                    () -> OpenHTMLtoPDFInvoice.class.getResourceAsStream("/fonts/NotoSans-Regular.ttf"),
                    "Noto Sans"
            );
            builder.useFont(
                    () -> OpenHTMLtoPDFInvoice.class.getResourceAsStream("/fonts/NotoNaskhArabic-Regular.ttf"),
                    "Noto Naskh Arabic"
            );
            builder.toStream(os);
            builder.run();
        }
    }

    public static void main(String[] args) throws Exception {

        InvoiceData invoice = new InvoiceData();

        invoice.setInvoiceNo("INV-2026-001");
        invoice.setInvoiceDate("08/06/2026");
        invoice.setWaybillNo("34334124705");
        invoice.setVatNumber("00000Dubai");

        invoice.setSenderName("Munero Global Loyalty FZCO");
        invoice.setSenderCompany("Munero Global Loyalty FZCO");
        invoice.setSenderAddress("602 Abraj Center Baniyas Deira");
        invoice.setSenderCity("Dubai");
        invoice.setSenderCountry("United Arab Emirates");
        invoice.setSenderPhone("042323251");

        invoice.setReceiverName("PRAPULLA REDDY PALLE");
        invoice.setReceiverAddress("MUSCAT OASIS RESIDENCE 4th Floor Bldg 4629 Way 4550");
        invoice.setReceiverCity("Muscat");
        invoice.setReceiverCountry("Oman");
        invoice.setReceiverPhone("96879473133");

        invoice.setExportReason("Gift");
        invoice.setTotalPackages("6");
        invoice.setPrintedDate("09/06/2026 10:06");

// ── Items ──────────────────────────────────────────────────────────────────────

        InvoiceData.InvoiceItem item1 = new InvoiceData.InvoiceItem();
        item1.setDescription("Apple iPad 11-inch A16 Wi-Fi 256GB Silver");
        item1.setReference("Gift Apple");
        item1.setHsCode("8471.30");
        item1.setQty(1);
        item1.setUnit("PCS");
        item1.setPrice("430.52");
        item1.setCurrency("USD");
        item1.setOrigin("United Arab Emirates");

        InvoiceData.InvoiceItem item2 = new InvoiceData.InvoiceItem();
        item2.setDescription("Apple AirPods Pro 2nd Generation");
        item2.setReference("Gift AirPods");
        item2.setHsCode("8518.30");
        item2.setQty(2);
        item2.setUnit("PCS");
        item2.setPrice("249.00");
        item2.setCurrency("USD");
        item2.setOrigin("United Arab Emirates");

        InvoiceData.InvoiceItem item3 = new InvoiceData.InvoiceItem();
        item3.setDescription("Apple Watch Series 9 GPS 45mm Midnight");
        item3.setReference("Gift Watch");
        item3.setHsCode("9102.12");
        item3.setQty(1);
        item3.setUnit("PCS");
        item3.setPrice("399.00");
        item3.setCurrency("USD");
        item3.setOrigin("United Arab Emirates");

        InvoiceData.InvoiceItem item4 = new InvoiceData.InvoiceItem();
        item4.setDescription("Samsung 65-inch QLED 4K Smart TV");
        item4.setReference("Gift TV");
        item4.setHsCode("8528.72");
        item4.setQty(1);
        item4.setUnit("PCS");
        item4.setPrice("1200.00");
        item4.setCurrency("USD");
        item4.setOrigin("South Korea");

        InvoiceData.InvoiceItem item5 = new InvoiceData.InvoiceItem();
        item5.setDescription("Sony PlayStation 5 Console Digital Edition");
        item5.setReference("Gift PS5");
        item5.setHsCode("9504.50");
        item5.setQty(1);
        item5.setUnit("PCS");
        item5.setPrice("399.99");
        item5.setCurrency("USD");
        item5.setOrigin("Japan");

        InvoiceData.InvoiceItem item6 = new InvoiceData.InvoiceItem();
        item6.setDescription("Dyson V15 Detect Cordless Vacuum Cleaner");
        item6.setReference("Gift Dyson");
        item6.setHsCode("8508.11");
        item6.setQty(1);
        item6.setUnit("PCS");
        item6.setPrice("749.00");
        item6.setCurrency("USD");
        item6.setOrigin("United Kingdom");

        invoice.setItems(Arrays.asList  (item1, item2, item3, item4, item5, item6));

        // 3. The rest of your generation logic stays exactly the same
        OpenHTMLtoPDFInvoice generator = new OpenHTMLtoPDFInvoice();

        generator.generatePdf(
                invoice,
                "invoice-en",
                "invoice-en.pdf"
        );

        generator.generatePdf(
                invoice,
                "invoice-ar",
                "invoice-ar.pdf"
        );

        System.out.println("PDFs generated successfully.");
    }
}