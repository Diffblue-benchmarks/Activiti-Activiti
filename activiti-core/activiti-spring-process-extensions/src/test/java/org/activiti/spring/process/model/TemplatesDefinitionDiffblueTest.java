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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TemplatesDefinitionDiffblueTest {
  /**
   * Test {@link TemplatesDefinition#findAssigneeTemplateForTask(String)}.
   * <ul>
   *   <li>Given {@link TemplatesDefinition} (default constructor).</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TemplatesDefinition#findAssigneeTemplateForTask(String)}
   */
  @Test
  @DisplayName("Test findAssigneeTemplateForTask(String); given TemplatesDefinition (default constructor); then return not Present")
  void testFindAssigneeTemplateForTask_givenTemplatesDefinition_thenReturnNotPresent() {
    // Arrange, Act and Assert
    assertFalse(
        (new TemplatesDefinition()).findAssigneeTemplateForTask("01234567-89AB-CDEF-FEDC-BA9876543210").isPresent());
  }

  /**
   * Test {@link TemplatesDefinition#findAssigneeTemplateForTask(String)}.
   * <ul>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TemplatesDefinition#findAssigneeTemplateForTask(String)}
   */
  @Test
  @DisplayName("Test findAssigneeTemplateForTask(String); then return not Present")
  void testFindAssigneeTemplateForTask_thenReturnNotPresent() {
    // Arrange
    HashMap<String, TaskTemplateDefinition> tasks = new HashMap<>();
    tasks.computeIfPresent("foo", mock(BiFunction.class));

    TemplatesDefinition templatesDefinition = new TemplatesDefinition();
    templatesDefinition.setTasks(tasks);

    // Act and Assert
    assertFalse(templatesDefinition.findAssigneeTemplateForTask("01234567-89AB-CDEF-FEDC-BA9876543210").isPresent());
  }

  /**
   * Test {@link TemplatesDefinition#findAssigneeTemplateForTask(String)}.
   * <ul>
   *   <li>Then return Present.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TemplatesDefinition#findAssigneeTemplateForTask(String)}
   */
  @Test
  @DisplayName("Test findAssigneeTemplateForTask(String); then return Present")
  void testFindAssigneeTemplateForTask_thenReturnPresent() {
    // Arrange
    TaskTemplateDefinition taskTemplateDefinition = new TaskTemplateDefinition();
    TemplateDefinition assignee = new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42");

    taskTemplateDefinition.setAssignee(assignee);
    taskTemplateDefinition.setCandidate(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));

    HashMap<String, TaskTemplateDefinition> tasks = new HashMap<>();
    tasks.put("42", taskTemplateDefinition);
    tasks.computeIfPresent("foo", mock(BiFunction.class));

    TemplatesDefinition templatesDefinition = new TemplatesDefinition();
    templatesDefinition.setTasks(tasks);

    // Act
    Optional<TemplateDefinition> actualFindAssigneeTemplateForTaskResult = templatesDefinition
        .findAssigneeTemplateForTask("42");

    // Assert
    assertTrue(actualFindAssigneeTemplateForTaskResult.isPresent());
    assertSame(assignee, actualFindAssigneeTemplateForTaskResult.get());
  }

  /**
   * Test {@link TemplatesDefinition#findCandidateTemplateForTask(String)}.
   * <ul>
   *   <li>Given {@link TemplatesDefinition} (default constructor).</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TemplatesDefinition#findCandidateTemplateForTask(String)}
   */
  @Test
  @DisplayName("Test findCandidateTemplateForTask(String); given TemplatesDefinition (default constructor); then return not Present")
  void testFindCandidateTemplateForTask_givenTemplatesDefinition_thenReturnNotPresent() {
    // Arrange, Act and Assert
    assertFalse(
        (new TemplatesDefinition()).findCandidateTemplateForTask("01234567-89AB-CDEF-FEDC-BA9876543210").isPresent());
  }

  /**
   * Test {@link TemplatesDefinition#findCandidateTemplateForTask(String)}.
   * <ul>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TemplatesDefinition#findCandidateTemplateForTask(String)}
   */
  @Test
  @DisplayName("Test findCandidateTemplateForTask(String); then return not Present")
  void testFindCandidateTemplateForTask_thenReturnNotPresent() {
    // Arrange
    HashMap<String, TaskTemplateDefinition> tasks = new HashMap<>();
    tasks.computeIfPresent("foo", mock(BiFunction.class));

    TemplatesDefinition templatesDefinition = new TemplatesDefinition();
    templatesDefinition.setTasks(tasks);

    // Act and Assert
    assertFalse(templatesDefinition.findCandidateTemplateForTask("01234567-89AB-CDEF-FEDC-BA9876543210").isPresent());
  }

  /**
   * Test {@link TemplatesDefinition#findCandidateTemplateForTask(String)}.
   * <ul>
   *   <li>Then return Present.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TemplatesDefinition#findCandidateTemplateForTask(String)}
   */
  @Test
  @DisplayName("Test findCandidateTemplateForTask(String); then return Present")
  void testFindCandidateTemplateForTask_thenReturnPresent() {
    // Arrange
    TaskTemplateDefinition taskTemplateDefinition = new TaskTemplateDefinition();
    taskTemplateDefinition.setAssignee(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));
    TemplateDefinition candidate = new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42");

    taskTemplateDefinition.setCandidate(candidate);

    HashMap<String, TaskTemplateDefinition> tasks = new HashMap<>();
    tasks.put("42", taskTemplateDefinition);
    tasks.computeIfPresent("foo", mock(BiFunction.class));

    TemplatesDefinition templatesDefinition = new TemplatesDefinition();
    templatesDefinition.setTasks(tasks);

    // Act
    Optional<TemplateDefinition> actualFindCandidateTemplateForTaskResult = templatesDefinition
        .findCandidateTemplateForTask("42");

    // Assert
    assertTrue(actualFindCandidateTemplateForTaskResult.isPresent());
    assertSame(candidate, actualFindCandidateTemplateForTaskResult.get());
  }

  /**
   * Test {@link TemplatesDefinition#equals(Object)}, and
   * {@link TemplatesDefinition#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TemplatesDefinition#equals(Object)}
   *   <li>{@link TemplatesDefinition#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    TemplatesDefinition templatesDefinition = new TemplatesDefinition();
    TemplatesDefinition templatesDefinition2 = new TemplatesDefinition();

    // Act and Assert
    assertEquals(templatesDefinition, templatesDefinition2);
    int expectedHashCodeResult = templatesDefinition.hashCode();
    assertEquals(expectedHashCodeResult, templatesDefinition2.hashCode());
  }

  /**
   * Test {@link TemplatesDefinition#equals(Object)}, and
   * {@link TemplatesDefinition#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TemplatesDefinition#equals(Object)}
   *   <li>{@link TemplatesDefinition#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    TemplatesDefinition templatesDefinition = new TemplatesDefinition();

    // Act and Assert
    assertEquals(templatesDefinition, templatesDefinition);
    int expectedHashCodeResult = templatesDefinition.hashCode();
    assertEquals(expectedHashCodeResult, templatesDefinition.hashCode());
  }

  /**
   * Test {@link TemplatesDefinition#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplatesDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TemplatesDefinition(), 1);
  }

  /**
   * Test {@link TemplatesDefinition#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplatesDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    TaskTemplateDefinition defaultTemplate = new TaskTemplateDefinition();
    defaultTemplate.setAssignee(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));
    defaultTemplate.setCandidate(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));

    TemplatesDefinition templatesDefinition = new TemplatesDefinition();
    templatesDefinition.setDefaultTemplate(defaultTemplate);

    // Act and Assert
    assertNotEquals(templatesDefinition, new TemplatesDefinition());
  }

  /**
   * Test {@link TemplatesDefinition#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplatesDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    TaskTemplateDefinition defaultTemplate = mock(TaskTemplateDefinition.class);
    doNothing().when(defaultTemplate).setAssignee(Mockito.<TemplateDefinition>any());
    doNothing().when(defaultTemplate).setCandidate(Mockito.<TemplateDefinition>any());
    defaultTemplate.setAssignee(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));
    defaultTemplate.setCandidate(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));

    TemplatesDefinition templatesDefinition = new TemplatesDefinition();
    templatesDefinition.setDefaultTemplate(defaultTemplate);

    // Act and Assert
    assertNotEquals(templatesDefinition, new TemplatesDefinition());
  }

  /**
   * Test {@link TemplatesDefinition#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplatesDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TemplatesDefinition(), null);
  }

  /**
   * Test {@link TemplatesDefinition#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TemplatesDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TemplatesDefinition(), "Different type to TemplatesDefinition");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link TemplatesDefinition}
   *   <li>{@link TemplatesDefinition#setDefaultTemplate(TaskTemplateDefinition)}
   *   <li>{@link TemplatesDefinition#setTasks(Map)}
   *   <li>{@link TemplatesDefinition#getDefaultTemplate()}
   *   <li>{@link TemplatesDefinition#getTasks()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    TemplatesDefinition actualTemplatesDefinition = new TemplatesDefinition();
    TaskTemplateDefinition defaultTemplate = new TaskTemplateDefinition();
    defaultTemplate.setAssignee(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));
    defaultTemplate.setCandidate(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));
    actualTemplatesDefinition.setDefaultTemplate(defaultTemplate);
    HashMap<String, TaskTemplateDefinition> tasks = new HashMap<>();
    actualTemplatesDefinition.setTasks(tasks);
    TaskTemplateDefinition actualDefaultTemplate = actualTemplatesDefinition.getDefaultTemplate();
    Map<String, TaskTemplateDefinition> actualTasks = actualTemplatesDefinition.getTasks();

    // Assert that nothing has changed
    assertTrue(actualTasks.isEmpty());
    assertSame(tasks, actualTasks);
    assertSame(defaultTemplate, actualDefaultTemplate);
  }
}
