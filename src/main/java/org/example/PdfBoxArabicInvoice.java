package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.InputStream;

public class PdfBoxArabicInvoice {

    public static void main(String[] args) throws Exception {

        PDDocument document = new PDDocument();

        PDPage page = new PDPage();
        document.addPage(page);

        InputStream fontStream = PdfBoxArabicInvoice.class.getResourceAsStream( "/fonts/NotoNaskhArabic-Regular.ttf");

        PDType0Font font = PDType0Font.load(document, fontStream);


        PDPageContentStream cs =
                new PDPageContentStream(document, page);

        float pageWidth = page.getMediaBox().getWidth();

        // Title
        cs.beginText();
        cs.setFont(font, 20);
        cs.newLineAtOffset(pageWidth - 180, 760);
        cs.showText(ArabicUtil.shape("فاتورة ضريبية"));
        cs.endText();

        // Customer
        cs.beginText();
        cs.setFont(font, 14);
        cs.newLineAtOffset(pageWidth - 250, 720);
        cs.showText(ArabicUtil.shape("العميل: أحمد محمد"));
        cs.endText();

        // Table coordinates
        float startX = 50;
        float startY = 650;
        float tableWidth = 500;
        float rowHeight = 30;

        // Draw table grid
        for (int i = 0; i <= 4; i++) {
            cs.moveTo(startX, startY - (i * rowHeight));
            cs.lineTo(startX + tableWidth,
                    startY - (i * rowHeight));
        }

        float[] cols = {100, 250, 400, 550};

        for (float x : cols) {
            cs.moveTo(x, startY);
            cs.lineTo(x, startY - (4 * rowHeight));
        }

        cs.stroke();

        // Headers
        writeCell(cs, font, "الكمية", 460, 630);
        writeCell(cs, font, "السعر", 320, 630);
        writeCell(cs, font, "الصنف", 150, 630);

        // Row 1
        writeCell(cs, font, "2", 470, 600);
        writeCell(cs, font, "150", 330, 600);
        writeCell(cs, font, "حاسوب محمول", 130, 600);

        // Row 2
        writeCell(cs, font, "1", 470, 570);
        writeCell(cs, font, "50", 330, 570);
        writeCell(cs, font, "فأرة لاسلكية", 130, 570);

        // Total
        cs.beginText();
        cs.setFont(font, 16);
        cs.newLineAtOffset(pageWidth - 220, 450);
        cs.showText(ArabicUtil.shape("الإجمالي: 350 جنيه"));
        cs.endText();

        cs.close();

        document.save("invoice-pdfbox.pdf");
        document.close();
    }

    private static void writeCell(
            PDPageContentStream cs,
            PDType0Font font,
            String text,
            float x,
            float y) throws Exception {

        cs.beginText();
        cs.setFont(font, 12);
        cs.newLineAtOffset(x, y);
        cs.showText(ArabicUtil.shape(text));
        cs.endText();
    }
}