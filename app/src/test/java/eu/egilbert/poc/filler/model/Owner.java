package eu.egilbert.poc.filler.model;

import lombok.Data;

import java.util.List;

@Data
public class Owner {
    
    private String name;

    private List<Pet> pets;
}
