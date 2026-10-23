package com.nestor.utilities.property;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyRepository repository;

    public PropertyController(PropertyRepository repository) {
        this.repository = repository;
    }

    public record CreatePropertyRequest(
            String name,
            @NotBlank String streetAddress,
            @NotBlank String city,
            @NotBlank @Size(min = 2, max = 2) String state,
            @NotBlank @Size(max = 10) String zip,
            String neighborhood,
            @NotBlank @Pattern(regexp = "apartment|house|duplex|townhome|condo|other") String type
    ) {}

    // Street address is deliberately left out of public responses.
    public record PropertyResponse(
            UUID id, String name, String city, String state,
            String zip, String neighborhood, String type
    ) {
        static PropertyResponse from(Property p) {
            return new PropertyResponse(p.getId(), p.getName(), p.getCity(), p.getState(),
                    p.getZip(), p.getNeighborhood(), p.getType());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyResponse create(@Valid @RequestBody CreatePropertyRequest req) {
        Property saved = repository.save(new Property(
                req.name(), req.streetAddress(), req.city(), req.state(),
                req.zip(), req.neighborhood(), req.type()));
        return PropertyResponse.from(saved);
    }

    @GetMapping
    public List<PropertyResponse> list() {
        return repository.findAll().stream().map(PropertyResponse::from).toList();
    }
}