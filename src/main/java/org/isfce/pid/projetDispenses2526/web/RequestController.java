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

    // ——— CREATE REQUEST ———
    @PostMapping("/requests")
    public ResponseEntity<ExemptionRequestDTO> create(
            @Valid @RequestBody CreateRequestDTO dto,
            org.springframework.security.core.Authentication auth
    ) {
        ExemptionRequest created = svc.createDraft(auth.getName(), dto.section());
        return ResponseEntity
                .created(URI.create("/api/requests/" + created.getId()))
                .body(ExemptionRequestDTO.of(created));
    }
    
    // ——— ADD COURSE ———
    @PostMapping("/requests/{id}/courses")
    public ExternalCourseDTO addCourse(
            @PathVariable UUID id,
            @Valid @RequestBody AddExternalCourseDTO dto
    ) {
        ExternalCourse c = svc.addExternalCourse(id, dto.etablissement(), dto.code(), dto.libelle(), dto.ects(), dto.urlProgramme());
        return ExternalCourseDTO.of(c);
    }
    
    // ——— ADD DOCUMENT ———
    @PostMapping("/requests/{id}/documents")
    public SupportingDocumentDTO addDoc(
            @PathVariable UUID id,
            @Valid @RequestBody AddDocumentDTO dto
    ) {
    	TypeDocument type = TypeDocument.valueOf(dto.type().toUpperCase());
        SupportingDocument doc = svc.addDocument(id, type, dto.url());
        return SupportingDocumentDTO.of(doc);
    }

    // ——— SUBMIT ———
    @PostMapping("/requests/{id}/submit")
    public ExemptionRequestDTO submit(@PathVariable UUID id) {
    	return svc.submit(id);
    }

	// ——— GET MINE ———
    @GetMapping("/requests/mine")
    public List<ExemptionRequestDTO> mine(org.springframework.security.core.Authentication auth) {
        return svc.myRequests(auth.getName());
    }
    
    // ——— GET ONE ———
    @GetMapping("/requests/{id}")
    public ExemptionRequestDTO one(@PathVariable UUID id) {
    	ExemptionRequest r = svc.get(id);
        return ExemptionRequestDTO.of(r);
    }


    // ——— UE reference ———
    @GetMapping("/ue")
    public List<UEIsfce> allUE() {
        return svc.listUE();
    }
}