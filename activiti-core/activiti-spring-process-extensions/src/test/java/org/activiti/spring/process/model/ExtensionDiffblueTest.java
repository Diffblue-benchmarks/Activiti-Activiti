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
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExtensionDiffblueTest {
  /**
   * Test {@link Extension#getConstantForFlowElement(String)}.
   * <ul>
   *   <li>Given {@link Extension} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#getConstantForFlowElement(String)}
   */
  @Test
  @DisplayName("Test getConstantForFlowElement(String); given Extension (default constructor)")
  void testGetConstantForFlowElement_givenExtension() {
    // Arrange, Act and Assert
    assertTrue((new Extension()).getConstantForFlowElement("01234567-89AB-CDEF-FEDC-BA9876543210").isEmpty());
  }

  /**
   * Test {@link Extension#getConstantForFlowElement(String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#getConstantForFlowElement(String)}
   */
  @Test
  @DisplayName("Test getConstantForFlowElement(String); given HashMap() computeIfPresent 'foo' and BiFunction")
  void testGetConstantForFlowElement_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, VariableDefinition> properties = new HashMap<>();
    properties.computeIfPresent("foo", mock(BiFunction.class));

    Extension extension = new Extension();
    extension.setProperties(properties);

    // Act and Assert
    assertTrue(extension.getConstantForFlowElement("01234567-89AB-CDEF-FEDC-BA9876543210").isEmpty());
  }

  /**
   * Test {@link Extension#getMappingForFlowElement(String)}.
   * <ul>
   *   <li>Given {@link Extension} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#getMappingForFlowElement(String)}
   */
  @Test
  @DisplayName("Test getMappingForFlowElement(String); given Extension (default constructor)")
  void testGetMappingForFlowElement_givenExtension() {
    // Arrange and Act
    ProcessVariablesMapping actualMappingForFlowElement = (new Extension())
        .getMappingForFlowElement("01234567-89AB-CDEF-FEDC-BA9876543210");

    // Assert
    assertNull(actualMappingForFlowElement.getMappingType());
    assertTrue(actualMappingForFlowElement.getInputs().isEmpty());
    assertTrue(actualMappingForFlowElement.getOutputs().isEmpty());
  }

  /**
   * Test {@link Extension#getMappingForFlowElement(String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#getMappingForFlowElement(String)}
   */
  @Test
  @DisplayName("Test getMappingForFlowElement(String); given HashMap() computeIfPresent 'foo' and BiFunction")
  void testGetMappingForFlowElement_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, VariableDefinition> properties = new HashMap<>();
    properties.computeIfPresent("foo", mock(BiFunction.class));

    Extension extension = new Extension();
    extension.setProperties(properties);

    // Act
    ProcessVariablesMapping actualMappingForFlowElement = extension
        .getMappingForFlowElement("01234567-89AB-CDEF-FEDC-BA9876543210");

    // Assert
    assertNull(actualMappingForFlowElement.getMappingType());
    assertTrue(actualMappingForFlowElement.getInputs().isEmpty());
    assertTrue(actualMappingForFlowElement.getOutputs().isEmpty());
  }

  /**
   * Test {@link Extension#findAssigneeTemplateForTask(String)}.
   * <ul>
   *   <li>Given {@link Extension} (default constructor).</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#findAssigneeTemplateForTask(String)}
   */
  @Test
  @DisplayName("Test findAssigneeTemplateForTask(String); given Extension (default constructor); then return not Present")
  void testFindAssigneeTemplateForTask_givenExtension_thenReturnNotPresent() {
    // Arrange, Act and Assert
    assertFalse((new Extension()).findAssigneeTemplateForTask("01234567-89AB-CDEF-FEDC-BA9876543210").isPresent());
  }

  /**
   * Test {@link Extension#findAssigneeTemplateForTask(String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#findAssigneeTemplateForTask(String)}
   */
  @Test
  @DisplayName("Test findAssigneeTemplateForTask(String); given HashMap() computeIfPresent 'foo' and BiFunction")
  void testFindAssigneeTemplateForTask_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, VariableDefinition> properties = new HashMap<>();
    properties.computeIfPresent("foo", mock(BiFunction.class));

    Extension extension = new Extension();
    extension.setProperties(properties);

    // Act and Assert
    assertFalse(extension.findAssigneeTemplateForTask("01234567-89AB-CDEF-FEDC-BA9876543210").isPresent());
  }

  /**
   * Test {@link Extension#findCandidateTemplateForTask(String)}.
   * <ul>
   *   <li>Given {@link Extension} (default constructor).</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#findCandidateTemplateForTask(String)}
   */
  @Test
  @DisplayName("Test findCandidateTemplateForTask(String); given Extension (default constructor); then return not Present")
  void testFindCandidateTemplateForTask_givenExtension_thenReturnNotPresent() {
    // Arrange, Act and Assert
    assertFalse((new Extension()).findCandidateTemplateForTask("01234567-89AB-CDEF-FEDC-BA9876543210").isPresent());
  }

  /**
   * Test {@link Extension#findCandidateTemplateForTask(String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#findCandidateTemplateForTask(String)}
   */
  @Test
  @DisplayName("Test findCandidateTemplateForTask(String); given HashMap() computeIfPresent 'foo' and BiFunction")
  void testFindCandidateTemplateForTask_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, VariableDefinition> properties = new HashMap<>();
    properties.computeIfPresent("foo", mock(BiFunction.class));

    Extension extension = new Extension();
    extension.setProperties(properties);

    // Act and Assert
    assertFalse(extension.findCandidateTemplateForTask("01234567-89AB-CDEF-FEDC-BA9876543210").isPresent());
  }

  /**
   * Test {@link Extension#getProperty(String)}.
   * <ul>
   *   <li>Given {@link Extension} (default constructor) Properties is
   * {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#getProperty(String)}
   */
  @Test
  @DisplayName("Test getProperty(String); given Extension (default constructor) Properties is 'null'; then return 'null'")
  void testGetProperty_givenExtensionPropertiesIsNull_thenReturnNull() {
    // Arrange
    Extension extension = new Extension();
    extension.setProperties(null);

    // Act and Assert
    assertNull(extension.getProperty("01234567-89AB-CDEF-FEDC-BA9876543210"));
  }

  /**
   * Test {@link Extension#getProperty(String)}.
   * <ul>
   *   <li>Given {@link Extension} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#getProperty(String)}
   */
  @Test
  @DisplayName("Test getProperty(String); given Extension (default constructor); then return 'null'")
  void testGetProperty_givenExtension_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new Extension()).getProperty("01234567-89AB-CDEF-FEDC-BA9876543210"));
  }

  /**
   * Test {@link Extension#getProperty(String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#getProperty(String)}
   */
  @Test
  @DisplayName("Test getProperty(String); given HashMap() computeIfPresent 'foo' and BiFunction; then return 'null'")
  void testGetProperty_givenHashMapComputeIfPresentFooAndBiFunction_thenReturnNull() {
    // Arrange
    HashMap<String, ProcessVariablesMapping> mappings = new HashMap<>();
    mappings.computeIfPresent("foo", mock(BiFunction.class));

    Extension extension = new Extension();
    extension.setMappings(mappings);

    // Act and Assert
    assertNull(extension.getProperty("01234567-89AB-CDEF-FEDC-BA9876543210"));
  }

  /**
   * Test {@link Extension#getPropertyByName(String)}.
   * <ul>
   *   <li>Given {@link Extension} (default constructor) Properties is
   * {@code null}.</li>
   *   <li>When {@code Name}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#getPropertyByName(String)}
   */
  @Test
  @DisplayName("Test getPropertyByName(String); given Extension (default constructor) Properties is 'null'; when 'Name'; then return 'null'")
  void testGetPropertyByName_givenExtensionPropertiesIsNull_whenName_thenReturnNull() {
    // Arrange
    Extension extension = new Extension();
    extension.setProperties(null);

    // Act and Assert
    assertNull(extension.getPropertyByName("Name"));
  }

  /**
   * Test {@link Extension#getPropertyByName(String)}.
   * <ul>
   *   <li>Given {@link Extension} (default constructor).</li>
   *   <li>When {@code Name}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#getPropertyByName(String)}
   */
  @Test
  @DisplayName("Test getPropertyByName(String); given Extension (default constructor); when 'Name'; then return 'null'")
  void testGetPropertyByName_givenExtension_whenName_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new Extension()).getPropertyByName("Name"));
  }

  /**
   * Test {@link Extension#getPropertyByName(String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#getPropertyByName(String)}
   */
  @Test
  @DisplayName("Test getPropertyByName(String); given HashMap() computeIfPresent 'foo' and BiFunction")
  void testGetPropertyByName_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, ProcessVariablesMapping> mappings = new HashMap<>();
    mappings.computeIfPresent("foo", mock(BiFunction.class));

    Extension extension = new Extension();
    extension.setMappings(mappings);

    // Act and Assert
    assertNull(extension.getPropertyByName("Name"));
  }

  /**
   * Test {@link Extension#getPropertyByName(String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@code null}.</li>
   *   <li>When {@code Name}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#getPropertyByName(String)}
   */
  @Test
  @DisplayName("Test getPropertyByName(String); given HashMap() 'foo' is 'null'; when 'Name'; then return 'null'")
  void testGetPropertyByName_givenHashMapFooIsNull_whenName_thenReturnNull() {
    // Arrange
    HashMap<String, ProcessVariablesMapping> mappings = new HashMap<>();
    mappings.computeIfPresent("foo", mock(BiFunction.class));

    HashMap<String, VariableDefinition> properties = new HashMap<>();
    properties.put("foo", null);

    Extension extension = new Extension();
    extension.setProperties(properties);
    extension.setMappings(mappings);

    // Act and Assert
    assertNull(extension.getPropertyByName("Name"));
  }

  /**
   * Test {@link Extension#getPropertyByName(String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is
   * {@link VariableDefinition#VariableDefinition()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#getPropertyByName(String)}
   */
  @Test
  @DisplayName("Test getPropertyByName(String); given HashMap() 'foo' is VariableDefinition(); then return 'null'")
  void testGetPropertyByName_givenHashMapFooIsVariableDefinition_thenReturnNull() {
    // Arrange
    HashMap<String, ProcessVariablesMapping> mappings = new HashMap<>();
    mappings.computeIfPresent("foo", mock(BiFunction.class));

    HashMap<String, VariableDefinition> properties = new HashMap<>();
    properties.put("foo", new VariableDefinition());

    Extension extension = new Extension();
    extension.setProperties(properties);
    extension.setMappings(mappings);

    // Act and Assert
    assertNull(extension.getPropertyByName("Name"));
  }

  /**
   * Test {@link Extension#getPropertyByName(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link VariableDefinition#VariableDefinition()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#getPropertyByName(String)}
   */
  @Test
  @DisplayName("Test getPropertyByName(String); when 'null'; then return VariableDefinition()")
  void testGetPropertyByName_whenNull_thenReturnVariableDefinition() {
    // Arrange
    HashMap<String, ProcessVariablesMapping> mappings = new HashMap<>();
    mappings.computeIfPresent("foo", mock(BiFunction.class));

    HashMap<String, VariableDefinition> properties = new HashMap<>();
    VariableDefinition variableDefinition = new VariableDefinition();
    properties.put("foo", variableDefinition);

    Extension extension = new Extension();
    extension.setProperties(properties);
    extension.setMappings(mappings);

    // Act and Assert
    assertSame(variableDefinition, extension.getPropertyByName(null));
  }

  /**
   * Test {@link Extension#hasMapping(String)}.
   * <ul>
   *   <li>Given {@link Extension} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#hasMapping(String)}
   */
  @Test
  @DisplayName("Test hasMapping(String); given Extension (default constructor)")
  void testHasMapping_givenExtension() {
    // Arrange, Act and Assert
    assertFalse((new Extension()).hasMapping("42"));
  }

  /**
   * Test {@link Extension#hasMapping(String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Extension#hasMapping(String)}
   */
  @Test
  @DisplayName("Test hasMapping(String); given HashMap() computeIfPresent 'foo' and BiFunction")
  void testHasMapping_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, VariableDefinition> properties = new HashMap<>();
    properties.computeIfPresent("foo", mock(BiFunction.class));

    Extension extension = new Extension();
    extension.setProperties(properties);

    // Act and Assert
    assertFalse(extension.hasMapping("42"));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
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

    // Assert that nothing has changed
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
   * <p>
   * Method under test: default or parameterless constructor of {@link Extension}
   */
  @Test
  @DisplayName("Test new Extension (default constructor)")
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
