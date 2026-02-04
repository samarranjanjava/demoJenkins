package com.sound.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sound.demo.model.PersonResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Person API", description = "Returns person details")
public class PersonController {

    @Operation(summary = "Get person name")
    @GetMapping("/api/person")
    public PersonResponse getPerson(
            @Parameter(description = "Name of the person", example = "Samar")
            @RequestParam String name) {

        return new PersonResponse(name);
    }
}

