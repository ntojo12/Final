package com.auca.sms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.auca.sms.repository.DocumentRepository;
import com.auca.sms.entity.Document;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public void saveDocument(MultipartFile file) throws Exception {
        // Process and save the document to the database
        String fileName = file.getOriginalFilename();
        byte[] fileData = file.getBytes();
        documentRepository.save(new Document(fileName, fileData));
    }

    public List<Document> getAllDocuments() {
        List<Document> documents = documentRepository.findAll();
        // Add logging or debugging statements here
        return documents;
    }

    public Document updateDocument(Long documentId, MultipartFile file) {
        Optional<Document> optionalDocument = documentRepository.findById(documentId);

        if (optionalDocument.isPresent()) {
            Document existingDocument = optionalDocument.get();
            try {
                // Update the existing document with new data
                existingDocument.setName(file.getOriginalFilename());
                existingDocument.setContent(file.getBytes());

                // Save the updated document
                return documentRepository.save(existingDocument);
            } catch (Exception e) {
                // Handle the exception as needed
                return null;
            }
        } else {
            // Document with the given ID not found
            return null;
        }
    }

    public void deleteDocument(Long documentId) {
        documentRepository.deleteById(documentId);
    }
}
