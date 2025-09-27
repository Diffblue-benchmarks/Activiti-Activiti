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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ExtensionDiffblueTest {
  /**
   * Test {@link Extension#getConstantForFlowElement(String)}.
   *
   * <p>Method under test: {@link Extension#getConstantForFlowElement(String)}
   */
  @Test
  @DisplayName("Test getConstantForFlowElement(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessConstantsMapping Extension.getConstantForFlowElement(String)"})
  void testGetConstantForFlowElement() {
    // Arrange, Act and Assert
    assertTrue(
        new Extension()
            .getConstantForFlowElement("01234567-89AB-CDEF-FEDC-BA9876543210")
            .isEmpty());
  }

  /**
   * Test {@link Extension#getMappingForFlowElement(String)}.
   *
   * <p>Method under test: {@link Extension#getMappingForFlowElement(String)}
   */
  @Test
  @DisplayName("Test getMappingForFlowElement(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessVariablesMapping Extension.getMappingForFlowElement(String)"})
  void testGetMappingForFlowElement() {
    // Arrange and Act
    ProcessVariablesMapping actualMappingForFlowElement =
        new Extension().getMappingForFlowElement("01234567-89AB-CDEF-FEDC-BA9876543210");

    // Assert
    assertNull(actualMappingForFlowElement.getMappingType());
    assertTrue(actualMappingForFlowElement.getInputs().isEmpty());
    assertTrue(actualMappingForFlowElement.getOutputs().isEmpty());
  }

  /**
   * Test {@link Extension#findAssigneeTemplateForTask(String)}.
   *
   * <ul>
   *   <li>Given {@link Extension} (default constructor).
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link Extension#findAssigneeTemplateForTask(String)}
   */
  @Test
  @DisplayName(
      "Test findAssigneeTemplateForTask(String); given Extension (default constructor); then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Optional Extension.findAssigneeTemplateForTask(String)"})
  void testFindAssigneeTemplateForTask_givenExtension_thenReturnNotPresent() {
    // Arrange, Act and Assert
    assertFalse(
        new Extension()
            .findAssigneeTemplateForTask("01234567-89AB-CDEF-FEDC-BA9876543210")
            .isPresent());
  }

  /**
   * Test {@link Extension#findCandidateTemplateForTask(String)}.
   *
   * <ul>
   *   <li>Given {@link Extension} (default constructor).
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link Extension#findCandidateTemplateForTask(String)}
   */
  @Test
  @DisplayName(
      "Test findCandidateTemplateForTask(String); given Extension (default constructor); then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Optional Extension.findCandidateTemplateForTask(String)"})
  void testFindCandidateTemplateForTask_givenExtension_thenReturnNotPresent() {
    // Arrange, Act and Assert
    assertFalse(
        new Extension()
            .findCandidateTemplateForTask("01234567-89AB-CDEF-FEDC-BA9876543210")
            .isPresent());
  }

  /**
   * Test {@link Extension#getProperty(String)}.
   *
   * <ul>
   *   <li>Given {@link Extension} (default constructor) Properties is {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Extension#getProperty(String)}
   */
  @Test
  @DisplayName(
      "Test getProperty(String); given Extension (default constructor) Properties is 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"VariableDefinition Extension.getProperty(String)"})
  void testGetProperty_givenExtensionPropertiesIsNull_thenReturnNull() {
    // Arrange
    Extension extension = new Extension();
    extension.setProperties(null);

    // Act and Assert
    assertNull(extension.getProperty("01234567-89AB-CDEF-FEDC-BA9876543210"));
  }

  /**
   * Test {@link Extension#getProperty(String)}.
   *
   * <ul>
   *   <li>Given {@link Extension} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Extension#getProperty(String)}
   */
  @Test
  @DisplayName(
      "Test getProperty(String); given Extension (default constructor); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"VariableDefinition Extension.getProperty(String)"})
  void testGetProperty_givenExtension_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new Extension().getProperty("01234567-89AB-CDEF-FEDC-BA9876543210"));
  }

  /**
   * Test {@link Extension#getPropertyByName(String)}.
   *
   * <ul>
   *   <li>Given {@link Extension} (default constructor) Properties is {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Extension#getPropertyByName(String)}
   */
  @Test
  @DisplayName(
      "Test getPropertyByName(String); given Extension (default constructor) Properties is 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"VariableDefinition Extension.getPropertyByName(String)"})
  void testGetPropertyByName_givenExtensionPropertiesIsNull_thenReturnNull() {
    // Arrange
    Extension extension = new Extension();
    extension.setProperties(null);

    // Act and Assert
    assertNull(extension.getPropertyByName("Name"));
  }

  /**
   * Test {@link Extension#getPropertyByName(String)}.
   *
   * <ul>
   *   <li>Given {@link Extension} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Extension#getPropertyByName(String)}
   */
  @Test
  @DisplayName(
      "Test getPropertyByName(String); given Extension (default constructor); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"VariableDefinition Extension.getPropertyByName(String)"})
  void testGetPropertyByName_givenExtension_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new Extension().getPropertyByName("Name"));
  }

  /**
   * Test {@link Extension#getPropertyByName(String)}.
   *
   * <ul>
   *   <li>Given {@link TreeMap#TreeMap()} {@code foo} is {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Extension#getPropertyByName(String)}
   */
  @Test
  @DisplayName(
      "Test getPropertyByName(String); given TreeMap() 'foo' is 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"VariableDefinition Extension.getPropertyByName(String)"})
  void testGetPropertyByName_givenTreeMapFooIsNull_thenReturnNull() {
    // Arrange
    TreeMap<String, VariableDefinition> properties = new TreeMap<>();
    properties.put("foo", null);

    Extension extension = new Extension();
    extension.setProperties(properties);

    // Act and Assert
    assertNull(extension.getPropertyByName("Name"));
  }

  /**
   * Test {@link Extension#getPropertyByName(String)}.
   *
   * <ul>
   *   <li>Given {@link TreeMap#TreeMap()} {@code foo} is {@link
   *       VariableDefinition#VariableDefinition()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Extension#getPropertyByName(String)}
   */
  @Test
  @DisplayName(
      "Test getPropertyByName(String); given TreeMap() 'foo' is VariableDefinition(); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"VariableDefinition Extension.getPropertyByName(String)"})
  void testGetPropertyByName_givenTreeMapFooIsVariableDefinition_thenReturnNull() {
    // Arrange
    TreeMap<String, VariableDefinition> properties = new TreeMap<>();
    properties.put("foo", new VariableDefinition());

    Extension extension = new Extension();
    extension.setProperties(properties);

    // Act and Assert
    assertNull(extension.getPropertyByName("Name"));
  }

  /**
   * Test {@link Extension#getPropertyByName(String)}.
   *
   * <ul>
   *   <li>Then return {@link VariableDefinition#VariableDefinition()}.
   * </ul>
   *
   * <p>Method under test: {@link Extension#getPropertyByName(String)}
   */
  @Test
  @DisplayName("Test getPropertyByName(String); then return VariableDefinition()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"VariableDefinition Extension.getPropertyByName(String)"})
  void testGetPropertyByName_thenReturnVariableDefinition() {
    // Arrange
    VariableDefinition variableDefinition = new VariableDefinition();
    variableDefinition.setName("Name");

    TreeMap<String, VariableDefinition> properties = new TreeMap<>();
    properties.put("foo", variableDefinition);

    Extension extension = new Extension();
    extension.setProperties(properties);

    // Act and Assert
    assertSame(variableDefinition, extension.getPropertyByName("Name"));
  }

  /**
   * Test {@link Extension#hasMapping(String)}.
   *
   * <p>Method under test: {@link Extension#hasMapping(String)}
   */
  @Test
  @DisplayName("Test hasMapping(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Extension.hasMapping(String)"})
  void testHasMapping() {
    // Arrange, Act and Assert
    assertFalse(new Extension().hasMapping("42"));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Extension#setAssignments(Map)}
   *   <li>{@link Extension#setConstants(Map)}
   *   <li>{@link Extension#setMappings(Map)}
   *   <li>{@link Extension#setProperties(Map)}
   *   <li>{@link Extension#setTemplates(TemplatesDefinition)}
   *   <li>{@link Extension#getAssignments()}
   *   <li>{@link Extension#getConstants()}
   *   <li>{@link Extension#getMappings()}
   *   <li>{@link Extension#getProperties()}
   *   <li>{@link Extension#getTemplates()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map Extension.getAssignments()",
    "Map Extension.getConstants()",
    "Map Extension.getMappings()",
    "Map Extension.getProperties()",
    "TemplatesDefinition Extension.getTemplates()",
    "void Extension.setAssignments(Map)",
    "void Extension.setConstants(Map)",
    "void Extension.setMappings(Map)",
    "void Extension.setProperties(Map)",
    "void Extension.setTemplates(TemplatesDefinition)"
  })
  void testGettersAndSetters() {
    // Arrange
    Extension extension = new Extension();
    HashMap<String, AssignmentDefinition> assignments = new HashMap<>();

    // Act
    extension.setAssignments(assignments);
    HashMap<String, ProcessConstantsMapping> constants = new HashMap<>();
    extension.setConstants(constants);
    HashMap<String, ProcessVariablesMapping> mappings = new HashMap<>();
    extension.setMappings(mappings);
    HashMap<String, VariableDefinition> properties = new HashMap<>();
    extension.setProperties(properties);
    TemplatesDefinition templates = new TemplatesDefinition();
    extension.setTemplates(templates);
    Map<String, AssignmentDefinition> actualAssignments = extension.getAssignments();
    Map<String, ProcessConstantsMapping> actualConstants = extension.getConstants();
    Map<String, ProcessVariablesMapping> actualMappings = extension.getMappings();
    Map<String, VariableDefinition> actualProperties = extension.getProperties();
    TemplatesDefinition actualTemplates = extension.getTemplates();

    // Assert
    assertTrue(actualAssignments.isEmpty());
    assertTrue(actualConstants.isEmpty());
    assertTrue(actualMappings.isEmpty());
    assertTrue(actualProperties.isEmpty());
    assertSame(assignments, actualAssignments);
    assertSame(constants, actualConstants);
    assertSame(mappings, actualMappings);
    assertSame(properties, actualProperties);
    assertSame(templates, actualTemplates);
  }

  /**
   * Test new {@link Extension} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link Extension}
   */
  @Test
  @DisplayName("Test new Extension (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void Extension.<init>()"})
  void testNewExtension() {
    // Arrange and Act
    Extension actualExtension = new Extension();

    // Assert
    TemplatesDefinition templates = actualExtension.getTemplates();
    assertNull(templates.getDefaultTemplate());
    assertTrue(actualExtension.getAssignments().isEmpty());
    assertTrue(actualExtension.getConstants().isEmpty());
    assertTrue(actualExtension.getMappings().isEmpty());
    assertTrue(actualExtension.getProperties().isEmpty());
    assertTrue(templates.getTasks().isEmpty());
  }
}
