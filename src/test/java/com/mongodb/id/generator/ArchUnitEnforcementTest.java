package com.mongodb.id.generator;

import com.mongodb.archunit.NonCompliantRepository;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.equivalentTo;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

class ArchUnitEnforcementTest {

    @Test
    @DisplayName("Should detect repository methods lacking UserId parameter")
    void shouldDetectMissingUserId() {
        // Import both packages
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.mongodb.id.generator", "com.mongodb.archunit");

        DescribedPredicate<List<JavaClass>> containUserId = 
            new DescribedPredicate<List<JavaClass>>("contain UserId") {
                @Override
                public boolean test(List<JavaClass> input) {
                    return input.stream().anyMatch(t -> t.getSimpleName().equals("UserId"));
                }
            };

        ArchRule rule = methods()
            .that().areDeclaredInClassesThat().areAssignableTo(MongoRepository.class)
            .and().areDeclaredInClassesThat(DescribedPredicate.not(equivalentTo(NonCompliantRepository.class)))
            .should().haveRawParameterTypes(containUserId)
            .because("All repository methods must include a UserId to enforce RLS and prevent IDOR vulnerabilities");

        rule.check(importedClasses);
    }
}
