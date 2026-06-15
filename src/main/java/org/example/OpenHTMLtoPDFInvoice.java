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

        context.setVariable("invoiceNo", invoice.getInvoiceNo());
        context.setVariable("invoiceDate", invoice.getInvoiceDate());
        context.setVariable("waybillNo", invoice.getWaybillNo());
        context.setVariable("vatNumber", invoice.getVatNumber());

        context.setVariable("senderName", invoice.getSenderName());
        context.setVariable("senderCompany", invoice.getSenderCompany());
        context.setVariable("senderAddress", invoice.getSenderAddress());
        context.setVariable("senderCity", invoice.getSenderCity());
        context.setVariable("senderCountry", invoice.getSenderCountry());
        context.setVariable("senderPhone", invoice.getSenderPhone());
        context.setVariable("senderFax", invoice.getSenderFax());

        context.setVariable("receiverName", invoice.getReceiverName());
        context.setVariable("receiverCompany", invoice.getReceiverCompany());
        context.setVariable("receiverAddress", invoice.getReceiverAddress());
        context.setVariable("receiverCity", invoice.getReceiverCity());
        context.setVariable("receiverPhone", invoice.getReceiverPhone());
        context.setVariable("receiverFax", invoice.getReceiverFax());

        context.setVariable("contactName", invoice.getContactName());

        context.setVariable("description", invoice.getDescription());
        context.setVariable("reference", invoice.getReference());
        context.setVariable("hsCode", invoice.getHsCode());

        context.setVariable("qty", invoice.getQty());
        context.setVariable("unit", invoice.getUnit());
        context.setVariable("price", invoice.getPrice());
        context.setVariable("currency", invoice.getCurrency());
        context.setVariable("origin", invoice.getOrigin());
        context.setVariable("total", invoice.getTotal());

        context.setVariable("totalPackages", invoice.getTotalPackages());
        context.setVariable("totalValue", invoice.getTotalValue());
        context.setVariable("exportReason", invoice.getExportReason());

        context.setVariable("printedDate", invoice.getPrintedDate());

        String html = templateEngine.process(templateName, context);

        try (OutputStream os = new FileOutputStream(outputFile)) {

            PdfRendererBuilder builder = new PdfRendererBuilder();

            builder.useFastMode();

            builder.useUnicodeBidiSplitter(new ICUBidiSplitter.ICUBidiSplitterFactory());
            builder.useUnicodeBidiReorderer(new ICUBidiReorderer());
            builder.defaultTextDirection(TextDirection.RTL);

            builder.withHtmlContent(
                    html,
                    OpenHTMLtoPDFInvoice.class
                            .getResource("/templates/")
                            .toExternalForm()
            );

            /*
             * English Font
             */
            builder.useFont(
                    () -> OpenHTMLtoPDFInvoice.class
                            .getResourceAsStream(
                                    "/fonts/NotoSans-Regular.ttf"),
                    "Noto Sans"
            );

            /*
             * Arabic Font
             */
            builder.useFont(
                    () -> OpenHTMLtoPDFInvoice.class
                            .getResourceAsStream(
                                    "/fonts/NotoNaskhArabic-Regular.ttf"),
                    "Noto Naskh Arabic"
            );

            builder.toStream(os);

            builder.run();
        }
    }

    public static void main(String[] args) throws Exception {

        // 1. Instantiate using the empty constructor
        InvoiceData invoice = new InvoiceData();

        // 2. Populate fields using standard setter methods
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

        invoice.setDescription("Gift Apple iPad 11-inch A16 11th Gen Wi-Fi 256GB Silver");
        invoice.setReference("Gift Apple");
        invoice.setHsCode("8471.30");
        invoice.setQty(1);
        invoice.setUnit("PCS");
        invoice.setPrice("430.52");
        invoice.setCurrency("USD");
        invoice.setOrigin("United Arab Emirates");
        invoice.setTotal("430.52");

        invoice.setTotalPackages("1");
        invoice.setTotalValue("430.52");
        invoice.setExportReason("Gift");
        invoice.setPrintedDate("09/06/2026 10:06");

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