package com.flyersoft.remainder.controller;

import com.flyersoft.remainder.model.Document;
import com.flyersoft.remainder.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DocumentControllerTest {

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private DocumentController documentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllDocuments() {
        List<Document> documents = new ArrayList<>();
        //

        when(documentService.getAllDocuments()).thenReturn(documents);

        ResponseEntity<List<Document>> responseEntity = documentController.getAllDocuments();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(documents, responseEntity.getBody());
    }

    @Test
    public void testGetDocumentById() {
        Long documentId = 1L;
        Document document = new Document();
        //

        when(documentService.getDocumentById(documentId)).thenReturn(Optional.of(document));

        ResponseEntity<?> responseEntity = documentController.getDocumentById(documentId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(document, responseEntity.getBody());
    }

    @Test
    public void testGetDocumentByIdNotFound() {
        Long documentId = 1L;
        //
        when(documentService.getDocumentById(documentId)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = documentController.getDocumentById(documentId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateDocument() {
        Document document = new Document();
        //

        when(documentService.saveDocument(document)).thenReturn(document);

        ResponseEntity<Document> responseEntity = documentController.createDocument(document);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(document, responseEntity.getBody());
    }

    @Test
    public void testUpdateDocument() {
        Long documentId = 1L;
        Document document = new Document();
        //

        when(documentService.saveDocument(document)).thenReturn(document);

        ResponseEntity<?> responseEntity = documentController.updateDocument(documentId, document);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(document, responseEntity.getBody());
    }

    @Test
    public void testDeleteDocument() {
        Long documentId = 1L;

        ResponseEntity<Void> responseEntity = documentController.deleteDocument(documentId);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(documentService, times(1)).deleteDocument(documentId);
    }
}
