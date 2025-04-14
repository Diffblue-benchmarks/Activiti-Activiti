package org.activiti.spring.process.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.spring.process.model.Mapping.SourceMappingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MappingDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Mapping}
   *   <li>{@link Mapping#setType(SourceMappingType)}
   *   <li>{@link Mapping#setValue(Object)}
   *   <li>{@link Mapping#getType()}
   *   <li>{@link Mapping#getValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Mapping.<init>()", "SourceMappingType Mapping.getType()", "Object Mapping.getValue()",
      "void Mapping.setType(SourceMappingType)", "void Mapping.setValue(Object)"})
  void testGettersAndSetters() {
    // Arrange and Act
    Mapping actualMapping = new Mapping();
    actualMapping.setType(SourceMappingType.VARIABLE);
    actualMapping.setValue("Value");
    SourceMappingType actualType = actualMapping.getType();

    // Assert
    assertEquals("Value", actualMapping.getValue());
    assertEquals(SourceMappingType.VARIABLE, actualType);
  }
}
