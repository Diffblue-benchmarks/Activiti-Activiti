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
import org.junit.jupiter.api.Test;

class ExtensionDiffblueTest {
  /**
   * Method under test: {@link Extension#getConstantForFlowElement(String)}
   */
  @Test
  void testGetConstantForFlowElement() {
    // Arrange, Act and Assert
    assertTrue((new Extension()).getConstantForFlowElement("01234567-89AB-CDEF-FEDC-BA9876543210").isEmpty());
  }

  /**
   * Method under test: {@link Extension#getConstantForFlowElement(String)}
   */
  @Test
  void testGetConstantForFlowElement2() {
    // Arrange
    HashMap<String, VariableDefinition> properties = new HashMap<>();
    properties.computeIfPresent("foo", mock(BiFunction.class));

    Extension extension = new Extension();
    extension.setProperties(properties);

    // Act and Assert
    assertTrue(extension.getConstantForFlowElement("01234567-89AB-CDEF-FEDC-BA9876543210").isEmpty());
  }

  /**
   * Method under test: {@link Extension#getMappingForFlowElement(String)}
   */
  @Test
  void testGetMappingForFlowElement() {
    // Arrange and Act
    ProcessVariablesMapping actualMappingForFlowElement = (new Extension())
        .getMappingForFlowElement("01234567-89AB-CDEF-FEDC-BA9876543210");

    // Assert
    assertNull(actualMappingForFlowElement.getMappingType());
    assertTrue(actualMappingForFlowElement.getInputs().isEmpty());
    assertTrue(actualMappingForFlowElement.getOutputs().isEmpty());
  }

  /**
   * Method under test: {@link Extension#getMappingForFlowElement(String)}
   */
  @Test
  void testGetMappingForFlowElement2() {
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
   * Method under test: {@link Extension#findAssigneeTemplateForTask(String)}
   */
  @Test
  void testFindAssigneeTemplateForTask() {
    // Arrange, Act and Assert
    assertFalse((new Extension()).findAssigneeTemplateForTask("01234567-89AB-CDEF-FEDC-BA9876543210").isPresent());
  }

  /**
   * Method under test: {@link Extension#findAssigneeTemplateForTask(String)}
   */
  @Test
  void testFindAssigneeTemplateForTask2() {
    // Arrange
    HashMap<String, VariableDefinition> properties = new HashMap<>();
    properties.computeIfPresent("foo", mock(BiFunction.class));

    Extension extension = new Extension();
    extension.setProperties(properties);

    // Act and Assert
    assertFalse(extension.findAssigneeTemplateForTask("01234567-89AB-CDEF-FEDC-BA9876543210").isPresent());
  }

  /**
   * Method under test: {@link Extension#findCandidateTemplateForTask(String)}
   */
  @Test
  void testFindCandidateTemplateForTask() {
    // Arrange, Act and Assert
    assertFalse((new Extension()).findCandidateTemplateForTask("01234567-89AB-CDEF-FEDC-BA9876543210").isPresent());
  }

  /**
   * Method under test: {@link Extension#findCandidateTemplateForTask(String)}
   */
  @Test
  void testFindCandidateTemplateForTask2() {
    // Arrange
    HashMap<String, VariableDefinition> properties = new HashMap<>();
    properties.computeIfPresent("foo", mock(BiFunction.class));

    Extension extension = new Extension();
    extension.setProperties(properties);

    // Act and Assert
    assertFalse(extension.findCandidateTemplateForTask("01234567-89AB-CDEF-FEDC-BA9876543210").isPresent());
  }

  /**
   * Method under test: {@link Extension#getProperty(String)}
   */
  @Test
  void testGetProperty() {
    // Arrange, Act and Assert
    assertNull((new Extension()).getProperty("01234567-89AB-CDEF-FEDC-BA9876543210"));
  }

  /**
   * Method under test: {@link Extension#getProperty(String)}
   */
  @Test
  void testGetProperty2() {
    // Arrange
    Extension extension = new Extension();
    extension.setProperties(null);

    // Act and Assert
    assertNull(extension.getProperty("01234567-89AB-CDEF-FEDC-BA9876543210"));
  }

  /**
   * Method under test: {@link Extension#getProperty(String)}
   */
  @Test
  void testGetProperty3() {
    // Arrange
    HashMap<String, ProcessVariablesMapping> mappings = new HashMap<>();
    mappings.computeIfPresent("foo", mock(BiFunction.class));

    Extension extension = new Extension();
    extension.setMappings(mappings);

    // Act and Assert
    assertNull(extension.getProperty("01234567-89AB-CDEF-FEDC-BA9876543210"));
  }

  /**
   * Method under test: {@link Extension#getPropertyByName(String)}
   */
  @Test
  void testGetPropertyByName() {
    // Arrange, Act and Assert
    assertNull((new Extension()).getPropertyByName("Name"));
  }

  /**
   * Method under test: {@link Extension#getPropertyByName(String)}
   */
  @Test
  void testGetPropertyByName2() {
    // Arrange
    Extension extension = new Extension();
    extension.setProperties(null);

    // Act and Assert
    assertNull(extension.getPropertyByName("Name"));
  }

  /**
   * Method under test: {@link Extension#getPropertyByName(String)}
   */
  @Test
  void testGetPropertyByName3() {
    // Arrange
    HashMap<String, ProcessVariablesMapping> mappings = new HashMap<>();
    mappings.computeIfPresent("foo", mock(BiFunction.class));

    Extension extension = new Extension();
    extension.setMappings(mappings);

    // Act and Assert
    assertNull(extension.getPropertyByName("Name"));
  }

  /**
   * Method under test: {@link Extension#getPropertyByName(String)}
   */
  @Test
  void testGetPropertyByName4() {
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
   * Method under test: {@link Extension#getPropertyByName(String)}
   */
  @Test
  void testGetPropertyByName5() {
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
   * Method under test: {@link Extension#getPropertyByName(String)}
   */
  @Test
  void testGetPropertyByName6() {
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
   * Method under test: {@link Extension#hasMapping(String)}
   */
  @Test
  void testHasMapping() {
    // Arrange, Act and Assert
    assertFalse((new Extension()).hasMapping("42"));
  }

  /**
   * Method under test: {@link Extension#hasMapping(String)}
   */
  @Test
  void testHasMapping2() {
    // Arrange
    HashMap<String, VariableDefinition> properties = new HashMap<>();
    properties.computeIfPresent("foo", mock(BiFunction.class));

    Extension extension = new Extension();
    extension.setProperties(properties);

    // Act and Assert
    assertFalse(extension.hasMapping("42"));
  }

  /**
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
   * Method under test: default or parameterless constructor of {@link Extension}
   */
  @Test
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
