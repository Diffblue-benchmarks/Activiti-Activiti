package org.activiti.spring.process.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.spring.process.model.TemplateDefinition.TemplateType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TaskTemplateDefinitionDiffblueTest {
  /**
   * Test {@link TaskTemplateDefinition#equals(Object)}, and {@link TaskTemplateDefinition#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TaskTemplateDefinition#equals(Object)}
   *   <li>{@link TaskTemplateDefinition#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean TaskTemplateDefinition.equals(Object)", "int TaskTemplateDefinition.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    TaskTemplateDefinition taskTemplateDefinition = new TaskTemplateDefinition();
    taskTemplateDefinition.setAssignee(new TemplateDefinition(TemplateType.VARIABLE, "42"));
    taskTemplateDefinition.setCandidate(new TemplateDefinition(TemplateType.VARIABLE, "42"));

    TaskTemplateDefinition taskTemplateDefinition2 = new TaskTemplateDefinition();
    taskTemplateDefinition2.setAssignee(new TemplateDefinition(TemplateType.VARIABLE, "42"));
    taskTemplateDefinition2.setCandidate(new TemplateDefinition(TemplateType.VARIABLE, "42"));

    // Act and Assert
    assertEquals(taskTemplateDefinition, taskTemplateDefinition2);
    int expectedHashCodeResult = taskTemplateDefinition.hashCode();
    assertEquals(expectedHashCodeResult, taskTemplateDefinition2.hashCode());
  }

  /**
   * Test {@link TaskTemplateDefinition#equals(Object)}, and {@link TaskTemplateDefinition#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TaskTemplateDefinition#equals(Object)}
   *   <li>{@link TaskTemplateDefinition#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean TaskTemplateDefinition.equals(Object)", "int TaskTemplateDefinition.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    TaskTemplateDefinition taskTemplateDefinition = new TaskTemplateDefinition();
    taskTemplateDefinition.setAssignee(new TemplateDefinition(TemplateType.VARIABLE, "42"));
    taskTemplateDefinition.setCandidate(new TemplateDefinition(TemplateType.VARIABLE, "42"));

    // Act and Assert
    assertEquals(taskTemplateDefinition, taskTemplateDefinition);
    int expectedHashCodeResult = taskTemplateDefinition.hashCode();
    assertEquals(expectedHashCodeResult, taskTemplateDefinition.hashCode());
  }

  /**
   * Test {@link TaskTemplateDefinition#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskTemplateDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean TaskTemplateDefinition.equals(Object)", "int TaskTemplateDefinition.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    TaskTemplateDefinition taskTemplateDefinition = new TaskTemplateDefinition();
    taskTemplateDefinition.setAssignee(new TemplateDefinition(null, "42"));
    taskTemplateDefinition.setCandidate(new TemplateDefinition(TemplateType.VARIABLE, "42"));

    TaskTemplateDefinition taskTemplateDefinition2 = new TaskTemplateDefinition();
    taskTemplateDefinition2.setAssignee(new TemplateDefinition(TemplateType.VARIABLE, "42"));
    taskTemplateDefinition2.setCandidate(new TemplateDefinition(TemplateType.VARIABLE, "42"));

    // Act and Assert
    assertNotEquals(taskTemplateDefinition, taskTemplateDefinition2);
  }

  /**
   * Test {@link TaskTemplateDefinition#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskTemplateDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean TaskTemplateDefinition.equals(Object)", "int TaskTemplateDefinition.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    TaskTemplateDefinition taskTemplateDefinition = new TaskTemplateDefinition();
    taskTemplateDefinition.setAssignee(new TemplateDefinition(TemplateType.VARIABLE, "42"));
    taskTemplateDefinition.setCandidate(new TemplateDefinition(null, "42"));

    TaskTemplateDefinition taskTemplateDefinition2 = new TaskTemplateDefinition();
    taskTemplateDefinition2.setAssignee(new TemplateDefinition(TemplateType.VARIABLE, "42"));
    taskTemplateDefinition2.setCandidate(new TemplateDefinition(TemplateType.VARIABLE, "42"));

    // Act and Assert
    assertNotEquals(taskTemplateDefinition, taskTemplateDefinition2);
  }

  /**
   * Test {@link TaskTemplateDefinition#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskTemplateDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean TaskTemplateDefinition.equals(Object)", "int TaskTemplateDefinition.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    TaskTemplateDefinition taskTemplateDefinition = new TaskTemplateDefinition();
    taskTemplateDefinition.setAssignee(new TemplateDefinition(TemplateType.VARIABLE, "42"));
    taskTemplateDefinition.setCandidate(new TemplateDefinition(TemplateType.VARIABLE, "42"));

    // Act and Assert
    assertNotEquals(taskTemplateDefinition, null);
  }

  /**
   * Test {@link TaskTemplateDefinition#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskTemplateDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean TaskTemplateDefinition.equals(Object)", "int TaskTemplateDefinition.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    TaskTemplateDefinition taskTemplateDefinition = new TaskTemplateDefinition();
    taskTemplateDefinition.setAssignee(new TemplateDefinition(TemplateType.VARIABLE, "42"));
    taskTemplateDefinition.setCandidate(new TemplateDefinition(TemplateType.VARIABLE, "42"));

    // Act and Assert
    assertNotEquals(taskTemplateDefinition, "Different type to TaskTemplateDefinition");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link TaskTemplateDefinition}
   *   <li>{@link TaskTemplateDefinition#setAssignee(TemplateDefinition)}
   *   <li>{@link TaskTemplateDefinition#setCandidate(TemplateDefinition)}
   *   <li>{@link TaskTemplateDefinition#getAssignee()}
   *   <li>{@link TaskTemplateDefinition#getCandidate()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TaskTemplateDefinition.<init>()", "TemplateDefinition TaskTemplateDefinition.getAssignee()",
      "TemplateDefinition TaskTemplateDefinition.getCandidate()",
      "void TaskTemplateDefinition.setAssignee(TemplateDefinition)",
      "void TaskTemplateDefinition.setCandidate(TemplateDefinition)"})
  void testGettersAndSetters() {
    // Arrange and Act
    TaskTemplateDefinition actualTaskTemplateDefinition = new TaskTemplateDefinition();
    TemplateDefinition assignee = new TemplateDefinition(TemplateType.VARIABLE, "42");

    actualTaskTemplateDefinition.setAssignee(assignee);
    TemplateDefinition candidate = new TemplateDefinition(TemplateType.VARIABLE, "42");

    actualTaskTemplateDefinition.setCandidate(candidate);
    TemplateDefinition actualAssignee = actualTaskTemplateDefinition.getAssignee();

    // Assert
    assertSame(assignee, actualAssignee);
    assertSame(candidate, actualTaskTemplateDefinition.getCandidate());
  }
}
