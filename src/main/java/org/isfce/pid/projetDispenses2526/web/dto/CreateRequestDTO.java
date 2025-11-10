package org.isfce.pid.projetDispenses2526.web.dto;

import jakarta.validation.constraints.*;

public record CreateRequestDTO(@NotBlank String section) { }