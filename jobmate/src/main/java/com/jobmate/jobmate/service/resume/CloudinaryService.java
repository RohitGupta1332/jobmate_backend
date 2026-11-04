package com.jobmate.jobmate.service.resume;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public Map uploadPdf(MultipartFile file) {

        try {
            return cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "image",
                            "folder", "jobmate/resumes",
                            "format", "pdf"
                    )
            );

        } catch (IOException e) {
            throw new RuntimeException("PDF upload failed", e);
        }
    }

    public void deletePdf(String publicId) {
        try {
            Map result = cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.asMap(
                            "resource_type", "image",
                            "type", "upload"
                    )
            );

            if (!"ok".equals(result.get("result"))) {
                throw new RuntimeException("Failed to delete PDF from Cloudinary");
            }

        } catch (Exception e) {
            throw new RuntimeException("PDF deletion failed", e);
        }
    }
}