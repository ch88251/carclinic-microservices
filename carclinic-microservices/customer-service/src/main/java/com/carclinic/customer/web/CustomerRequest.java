package com.carclinic.customer.web;

import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank String email
) {
}
