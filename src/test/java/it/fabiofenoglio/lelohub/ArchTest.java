package it.fabiofenoglio.lelohub;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("it.fabiofenoglio.lelohub");

        noClasses()
            .that()
                .resideInAnyPackage("it.fabiofenoglio.lelohub.service..")
            .or()
                .resideInAnyPackage("it.fabiofenoglio.lelohub.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..it.fabiofenoglio.lelohub.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
