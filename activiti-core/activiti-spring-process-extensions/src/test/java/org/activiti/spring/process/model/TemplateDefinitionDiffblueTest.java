/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.spring.process.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.spring.process.model.TemplateDefinition.TemplateType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TemplateDefinitionDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link TemplateDefinition#TemplateDefinition()}
   *   <li>{@link TemplateDefinition#setFrom(String)}
   *   <li>{@link TemplateDefinition#setSubject(String)}
   *   <li>{@link TemplateDefinition#setType(TemplateType)}
   *   <li>{@link TemplateDefinition#setValue(String)}
   *   <li>{@link TemplateDefinition#toString()}
   *   <li>{@link TemplateDefinition#getFrom()}
   *   <li>{@link TemplateDefinition#getSubject()}
   *   <li>{@link TemplateDefinition#getType()}
   *   <li>{@link TemplateDefinition#getValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TemplateDefinition.<init>()",
    "void TemplateDefinition.<init>(TemplateType, String)",
    "String TemplateDefinition.getFrom()",
    "String TemplateDefinition.getSubject()",
    "TemplateType TemplateDefinition.getType()",
    "String TemplateDefinition.getValue()",
    "void TemplateDefinition.setFrom(String)",
    "void TemplateDefinition.setSubject(String)",
    "void TemplateDefinition.setType(TemplateType)",
    "void TemplateDefinition.setValue(String)",
    "String TemplateDefinition.toString()"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    TemplateDefinition actualTemplateDefinition = new TemplateDefinition();
    actualTemplateDefinition.setFrom("jane.doe@example.org");
    actualTemplateDefinition.setSubject("Hello from the Dreaming Spires");
    actualTemplateDefinition.setType(TemplateType.VARIABLE);
    actualTemplateDefinition.setValue("42");
    String actualToStringResult = actualTemplateDefinition.toString();
    String actualFrom = actualTemplateDefinition.getFrom();
    String actualSubject = actualTemplateDefinition.getSubject();
    TemplateType actualType = actualTemplateDefinition.getType();

    // Assert
    assertEquals("42", actualTemplateDefinition.getValue());
    assertEquals("Hello from the Dreaming Spires", actualSubject);
    assertEquals(
        "TemplateDefinition{from='jane.doe@example.org', subject='Hello from the Dreaming Spires', type=VARIABLE,"
            + " value='42'}",
        actualToStringResult);
    assertEquals("jane.doe@example.org", actualFrom);
    assertEquals(TemplateType.VARIABLE, actualType);
  }

  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@code VARIABLE}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link TemplateDefinition#TemplateDefinition(TemplateType, String)}
   *   <li>{@link TemplateDefinition#setFrom(String)}
   *   <li>{@link TemplateDefinition#setSubject(String)}
   *   <li>{@link TemplateDefinition#setType(TemplateType)}
   *   <li>{@link TemplateDefinition#setValue(String)}
   *   <li>{@link TemplateDefinition#toString()}
   *   <li>{@link TemplateDefinition#getFrom()}
   *   <li>{@link TemplateDefinition#getSubject()}
   *   <li>{@link TemplateDefinition#getType()}
   *   <li>{@link TemplateDefinition#getValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when 'VARIABLE'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TemplateDefinition.<init>()",
    "void TemplateDefinition.<init>(TemplateType, String)",
    "String TemplateDefinition.getFrom()",
    "String TemplateDefinition.getSubject()",
    "TemplateType TemplateDefinition.getType()",
    "String TemplateDefinition.getValue()",
    "void TemplateDefinition.setFrom(String)",
    "void TemplateDefinition.setSubject(String)",
    "void TemplateDefinition.setType(TemplateType)",
    "void TemplateDefinition.setValue(String)",
    "String TemplateDefinition.toString()"
  })
  void testGettersAndSetters_whenVariable() {
    // Arrange and Act
    TemplateDefinition actualTemplateDefinition =
        new TemplateDefinition(TemplateType.VARIABLE, "42");
    actualTemplateDefinition.setFrom("jane.doe@example.org");
    actualTemplateDefinition.setSubject("Hello from the Dreaming Spires");
    actualTemplateDefinition.setType(TemplateType.VARIABLE);
    actualTemplateDefinition.setValue("42");
    String actualToStringResult = actualTemplateDefinition.toString();
    String actualFrom = actualTemplateDefinition.getFrom();
    String actualSubject = actualTemplateDefinition.getSubject();
    TemplateType actualType = actualTemplateDefinition.getType();

    // Assert
    assertEquals("42", actualTemplateDefinition.getValue());
    assertEquals("Hello from the Dreaming Spires", actualSubject);
    assertEquals(
        "TemplateDefinition{from='jane.doe@example.org', subject='Hello from the Dreaming Spires', type=VARIABLE,"
            + " value='42'}",
        actualToStringResult);
    assertEquals("jane.doe@example.org", actualFrom);
    assertEquals(TemplateType.VARIABLE, actualType);
  }

  /**
   * Test {@link TemplateDefinition#equals(Object)}, and {@link TemplateDefinition#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link TemplateDefinition#equals(Object)}
   *   <li>{@link TemplateDefinition#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TemplateDefinition.equals(Object)",
    "int TemplateDefinition.hashCode()"
  })
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    TemplateDefinition templateDefinition = new TemplateDefinition(TemplateType.VARIABLE, "42");
    TemplateDefinition templateDefinition2 = new TemplateDefinition(TemplateType.VARIABLE, "42");

    // Act and Assert
    assertEquals(templateDefinition, templateDefinition2);
    assertEquals(templateDefinition.hashCode(), templateDefinition2.hashCode());
  }

  /**
   * Test {@link TemplateDefinition#equals(Object)}, and {@link TemplateDefinition#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link TemplateDefinition#equals(Object)}
   *   <li>{@link TemplateDefinition#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TemplateDefinition.equals(Object)",
    "int TemplateDefinition.hashCode()"
  })
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    TemplateDefinition templateDefinition = new TemplateDefinition(TemplateType.VARIABLE, "42");

    // Act and Assert
    assertEquals(templateDefinition, templateDefinition);
    int expectedHashCodeResult = templateDefinition.hashCode();
    assertEquals(expectedHashCodeResult, templateDefinition.hashCode());
  }

  /**
   * Test {@link TemplateDefinition#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link TemplateDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TemplateDefinition.equals(Object)",
    "int TemplateDefinition.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    TemplateDefinition templateDefinition = new TemplateDefinition(null, "42");

    // Act and Assert
    assertNotEquals(templateDefinition, new TemplateDefinition(TemplateType.VARIABLE, "42"));
  }

  /**
   * Test {@link TemplateDefinition#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link TemplateDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TemplateDefinition.equals(Object)",
    "int TemplateDefinition.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    TemplateDefinition templateDefinition = new TemplateDefinition(TemplateType.VARIABLE, "Value");

    // Act and Assert
    assertNotEquals(templateDefinition, new TemplateDefinition(TemplateType.VARIABLE, "42"));
  }

  /**
   * Test {@link TemplateDefinition#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link TemplateDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TemplateDefinition.equals(Object)",
    "int TemplateDefinition.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    TemplateDefinition templateDefinition = new TemplateDefinition(TemplateType.VARIABLE, "42");
    templateDefinition.setFrom("jane.doe@example.org");

    // Act and Assert
    assertNotEquals(templateDefinition, new TemplateDefinition(TemplateType.VARIABLE, "42"));
  }

  /**
   * Test {@link TemplateDefinition#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link TemplateDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TemplateDefinition.equals(Object)",
    "int TemplateDefinition.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    TemplateDefinition templateDefinition = new TemplateDefinition(TemplateType.VARIABLE, "42");
    templateDefinition.setSubject("Hello from the Dreaming Spires");

    // Act and Assert
    assertNotEquals(templateDefinition, new TemplateDefinition(TemplateType.VARIABLE, "42"));
  }

  /**
   * Test {@link TemplateDefinition#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link TemplateDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TemplateDefinition.equals(Object)",
    "int TemplateDefinition.hashCode()"
  })
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TemplateDefinition(TemplateType.VARIABLE, "42"), null);
  }

  /**
   * Test {@link TemplateDefinition#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link TemplateDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TemplateDefinition.equals(Object)",
    "int TemplateDefinition.hashCode()"
  })
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(
        new TemplateDefinition(TemplateType.VARIABLE, "42"),
        "Different type to TemplateDefinition");
  }
}
