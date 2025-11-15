package org.isfce.pid.projetDispenses2526.web;

import org.isfce.pid.projetDispenses2526.domain.*;
import org.isfce.pid.projetDispenses2526.service.ExemptionService;
import org.isfce.pid.projetDispenses2526.web.dto.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/** API Étudiant : créer/compléter/soumettre et consulter ses demandes. */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RequestController {

    private final ExemptionService svc;

    // NOTE : en attendant Keycloak, on simule l'email de l'utilisateur (HTTP Basic → username= email)
    private String currentEmail(org.springframework.security.core.Authentication auth) { return auth.getName(); }

    @PostMapping("/requests")
    public ResponseEntity<ExemptionRequest> create(@Valid @RequestBody CreateRequestDTO dto,
                                                   org.springframework.security.core.Authentication auth) {
        ExemptionRequest created = svc.createDraft(currentEmail(auth), dto.section());
        return ResponseEntity.created(URI.create("/api/requests/" + created.getId())).body(created);
    }

    @PostMapping("/requests/{id}/courses")
    public ExternalCourseDTO addCourse(@PathVariable UUID id, @Valid @RequestBody AddExternalCourseDTO dto) {
    	ExternalCourse c = svc.addExternalCourse(id, dto.etablissement(), dto.code(), dto.libelle(), dto.ects(), dto.urlProgramme());
    	return ExternalCourseDTO.of(c);
    }

    @PostMapping("/requests/{id}/documents")
    public SupportingDocumentDTO addDoc(@PathVariable UUID id, @Valid @RequestBody AddDocumentDTO dto) {
        TypeDocument type = TypeDocument.valueOf(dto.type().toUpperCase());
        var doc = svc.addDocument(id, type, dto.url());
        return SupportingDocumentDTO.of(doc);
    }

    @PostMapping("/requests/{id}/submit")
    public ExemptionRequest submit(@PathVariable UUID id) {
        return svc.submit(id);
    }

    @GetMapping("/requests/mine")
    public List<ExemptionRequest> mine(org.springframework.security.core.Authentication auth) {
        return svc.myRequests(currentEmail(auth));
    }

    @GetMapping("/requests/{id}")
    public ExemptionRequest one(@PathVariable UUID id) {
        return svc.get(id);
    }

    @GetMapping("/ue")
    public List<UEIsfce> allUE() { return svc.listUE(); }
}