package com.example.vaccinationstart.controller;

import com.example.vaccinationstart.model.Person;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PersonProcessor implements RepresentationModelProcessor<EntityModel<Person>> {

    @Override
    public EntityModel<Person> process(EntityModel<Person> model) {
        Person person = model.getContent();
        if(person == null) {
            return model;
        }
        if(person.getVaccinations().size() != 2) {
            model.add(linkTo(methodOn(VaccinationController.class).scheduleVaccination(person.getId(), null)).withRel("schedule vaccination"));
        } else if(person.getVaccinations().size() == 2 && person.isFullyVaccinated()) {
            model.add(linkTo(methodOn(VaccinationController.class).generateCertificate(person.getId())).withRel("get certificate"));
        }
        return model;
    }
}
