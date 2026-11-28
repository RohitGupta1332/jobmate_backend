package com.jobmate.jobmate.service.resume;

import com.jobmate.jobmate.dto.job.ResumeDraftDto;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;

@Service
public class ResumePdfService {

    private final TemplateEngine templateEngine;

    public ResumePdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generatePdf(ResumeDraftDto resume) {

        try {
            // Pass resume data to Thymeleaf
            Context context = new Context();
            context.setVariable("resume", resume);

            // Generate HTML from resume.html
            String html = templateEngine.process(
                    "resume",
                    context
            );

            // Convert HTML into PDF
            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            PdfRendererBuilder builder = new PdfRendererBuilder();

            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(outputStream);
            builder.run();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to generate resume PDF", e
            );
        }
    }
}