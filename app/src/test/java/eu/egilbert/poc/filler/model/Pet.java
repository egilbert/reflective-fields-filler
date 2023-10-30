package eu.egilbert.poc.filler.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Pet {

    private String name;

    private Species species;

    private LocalDate dateOfBirth;
}