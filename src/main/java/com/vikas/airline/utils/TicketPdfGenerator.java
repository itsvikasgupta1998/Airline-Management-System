package com.vikas.airline.utils;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;

@Component
public class TicketPdfGenerator {

    public byte[] generate(String html) {

        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(out);
            builder.run();
            return out.toByteArray();

        }
        catch (Exception ex) {
            throw new RuntimeException("Unable to generate ticket pdf.", ex);
        }
    }
}