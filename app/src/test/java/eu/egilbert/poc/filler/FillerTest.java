package eu.egilbert.poc.filler;

import eu.egilbert.poc.filler.model.Pet;
import eu.egilbert.poc.filler.model.Species;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FillerTest {

    Filler filler = new Filler();

    @Test
    void fill_whenTargetFieldIsDirectSubfieldOfTarget_shouldFillTheSubfield() {
        // GIVEN
        Pet jonesy = new Pet();

        FieldPath path = FieldPath.fromFields("name");

        // WHEN
        filler.fill(jonesy, path, "Jonesy");

        // THEN
        assertThat(jonesy.getName()).isEqualTo("Jonesy");
    }

    @Test
    void fill_whenIntermediateObjectsAlreadyExist_shouldFollowThePathAndFillTheSubfield() {
        // GIVEN
        Pet jonesy = new Pet();
        Species cat = new Species();
        jonesy.setSpecies(cat);

        FieldPath path = FieldPath.fromFields("species", "name");

        // WHEN
        filler.fill(jonesy, path, "cat");

        // THEN
        assertThat(jonesy.getSpecies().getName()).isEqualTo("cat");
    }

    @Test
    void fill_whenIntermediateObjectsAreNull_shouldCreateTheIntermediateObjectsAndFillTheSubfield() {
        // GIVEN
        Pet jonesy = new Pet();

        FieldPath path = FieldPath.fromFields("species", "name");

        // WHEN
        filler.fill(jonesy, path, "cat");

        // THEN
        assertThat(jonesy.getSpecies().getName()).isEqualTo("cat");
    }

    @Test
    void fill_whenIntermediateObjectsAlreadyExist_shouldNotChangeUntargetedValues() {
        // GIVEN
        Pet jonesy = new Pet();
        Species cat = new Species();
        cat.setScientificName("felis catus");
        jonesy.setSpecies(cat);

        FieldPath path = FieldPath.fromFields("species", "name");

        // WHEN
        filler.fill(jonesy, path, "cat");

        // THEN
        assertThat(jonesy.getSpecies().getScientificName()).isEqualTo("felis catus");
    }

}