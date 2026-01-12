package com.mongodb.id.generator;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

class ArchUnitEnforcementTest {

    @Test
    @DisplayName("Should detect repository methods lacking UserId parameter")
    void shouldDetectMissingUserId() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.mongodb.id.generator");

        ArchRule rule = methods()
            .that().areDeclaredInClassesThat().areAssignableTo(MongoRepository.class)
            .should().haveRawParameterTypes(new DescribedPredicate<>("contain UserId") {
                @Override
                public boolean test(List<JavaClass> input) {
                    return input.stream().anyMatch(t -> t.getSimpleName().equals("UserId"));
                }
            })
            .because("All repository methods must include a UserId to enforce RLS and prevent IDOR vulnerabilities");

        // This should fail because we have NonCompliantRepository
        rule.check(importedClasses);
    }
}
