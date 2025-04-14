package org.activiti.spring.process.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ConstantDefinitionDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ConstantDefinition}
   *   <li>{@link ConstantDefinition#setValue(Object)}
   *   <li>{@link ConstantDefinition#getValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ConstantDefinition.<init>()", "Object ConstantDefinition.getValue()",
      "void ConstantDefinition.setValue(Object)"})
  void testGettersAndSetters() {
    // Arrange and Act
    ConstantDefinition actualConstantDefinition = new ConstantDefinition();
    actualConstantDefinition.setValue("Value");

    // Assert
    assertEquals("Value", actualConstantDefinition.getValue());
  }
}
