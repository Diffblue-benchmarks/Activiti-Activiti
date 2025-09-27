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
package org.activiti.runtime.api.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.behavior.MappingExecutionContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.spring.process.ProcessExtensionService;
import org.activiti.spring.process.model.Extension;
import org.activiti.spring.process.model.Mapping;
import org.activiti.spring.process.model.Mapping.SourceMappingType;
import org.activiti.spring.process.model.ProcessConstantsMapping;
import org.activiti.spring.process.model.ProcessVariablesMapping;
import org.activiti.spring.process.model.ProcessVariablesMapping.MappingType;
import org.activiti.spring.process.model.VariableDefinition;
import org.activiti.spring.process.variable.VariableParsingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ExtensionsVariablesMappingProvider.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ExtensionsVariablesMappingProviderDiffblueTest {
  @MockBean private ExpressionResolver expressionResolver;

  @Autowired private ExtensionsVariablesMappingProvider extensionsVariablesMappingProvider;

  @MockBean private ProcessExtensionService processExtensionService;

  @MockBean private VariableParsingService variableParsingService;

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping, DelegateExecution,
   * Extension)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link Mapping} (default constructor) Type is {@code null}.
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping,
   * DelegateExecution, Extension)}
   */
  @Test
  @DisplayName(
      "Test calculateMappedValue(Mapping, DelegateExecution, Extension); given 'null'; when Mapping (default constructor) Type is 'null'; then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Optional ExtensionsVariablesMappingProvider.calculateMappedValue(Mapping, DelegateExecution, Extension)"
  })
  void testCalculateMappedValue_givenNull_whenMappingTypeIsNull_thenReturnNotPresent() {
    // Arrange
    Mapping inputMapping = new Mapping();
    inputMapping.setType(null);
    inputMapping.setValue("Value");
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertFalse(
        extensionsVariablesMappingProvider
            .calculateMappedValue(inputMapping, execution, new Extension())
            .isPresent());
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping, DelegateExecution,
   * Extension)}.
   *
   * <ul>
   *   <li>Given {@code VALUE}.
   *   <li>When {@link Mapping} (default constructor) Type is {@code VALUE}.
   *   <li>Then return {@link Optional#get()} is {@code Value}.
   * </ul>
   *
   * <p>Method under test: {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping,
   * DelegateExecution, Extension)}
   */
  @Test
  @DisplayName(
      "Test calculateMappedValue(Mapping, DelegateExecution, Extension); given 'VALUE'; when Mapping (default constructor) Type is 'VALUE'; then return get() is 'Value'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Optional ExtensionsVariablesMappingProvider.calculateMappedValue(Mapping, DelegateExecution, Extension)"
  })
  void testCalculateMappedValue_givenValue_whenMappingTypeIsValue_thenReturnGetIsValue() {
    // Arrange
    Mapping inputMapping = new Mapping();
    inputMapping.setType(SourceMappingType.VALUE);
    inputMapping.setValue("Value");
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    Optional<Object> actualCalculateMappedValueResult =
        extensionsVariablesMappingProvider.calculateMappedValue(
            inputMapping, execution, new Extension());

    // Assert
    assertEquals("Value", actualCalculateMappedValueResult.get());
    assertTrue(actualCalculateMappedValueResult.isPresent());
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping, DelegateExecution,
   * Extension)}.
   *
   * <ul>
   *   <li>Given {@link VariableDefinition#VariableDefinition()}.
   * </ul>
   *
   * <p>Method under test: {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping,
   * DelegateExecution, Extension)}
   */
  @Test
  @DisplayName(
      "Test calculateMappedValue(Mapping, DelegateExecution, Extension); given VariableDefinition()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Optional ExtensionsVariablesMappingProvider.calculateMappedValue(Mapping, DelegateExecution, Extension)"
  })
  void testCalculateMappedValue_givenVariableDefinition() {
    // Arrange
    Mapping inputMapping = new Mapping();
    inputMapping.setType(SourceMappingType.VARIABLE);
    inputMapping.setValue("Value");

    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariable(Mockito.<String>any())).thenReturn("Variable");

    Extension extensions = mock(Extension.class);
    when(extensions.getPropertyByName(Mockito.<String>any())).thenReturn(new VariableDefinition());

    // Act
    Optional<Object> actualCalculateMappedValueResult =
        extensionsVariablesMappingProvider.calculateMappedValue(
            inputMapping, execution, extensions);

    // Assert
    verify(execution).getVariable("Value");
    verify(extensions).getPropertyByName("Value");
    assertEquals("Variable", actualCalculateMappedValueResult.get());
    assertTrue(actualCalculateMappedValueResult.isPresent());
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping, DelegateExecution,
   * Extension)}.
   *
   * <ul>
   *   <li>Given {@code Variable}.
   *   <li>Then return {@link Optional#get()} is {@code Variable}.
   * </ul>
   *
   * <p>Method under test: {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping,
   * DelegateExecution, Extension)}
   */
  @Test
  @DisplayName(
      "Test calculateMappedValue(Mapping, DelegateExecution, Extension); given 'Variable'; then return get() is 'Variable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Optional ExtensionsVariablesMappingProvider.calculateMappedValue(Mapping, DelegateExecution, Extension)"
  })
  void testCalculateMappedValue_givenVariable_thenReturnGetIsVariable() {
    // Arrange
    Mapping inputMapping = new Mapping();
    inputMapping.setType(SourceMappingType.VARIABLE);
    inputMapping.setValue("Value");

    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariable(Mockito.<String>any())).thenReturn("Variable");

    // Act
    Optional<Object> actualCalculateMappedValueResult =
        extensionsVariablesMappingProvider.calculateMappedValue(
            inputMapping, execution, new Extension());

    // Assert
    verify(execution, atLeast(1)).getVariable("Value");
    assertEquals("Variable", actualCalculateMappedValueResult.get());
    assertTrue(actualCalculateMappedValueResult.isPresent());
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping, DelegateExecution,
   * Extension)}.
   *
   * <ul>
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping,
   * DelegateExecution, Extension)}
   */
  @Test
  @DisplayName(
      "Test calculateMappedValue(Mapping, DelegateExecution, Extension); then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Optional ExtensionsVariablesMappingProvider.calculateMappedValue(Mapping, DelegateExecution, Extension)"
  })
  void testCalculateMappedValue_thenReturnNotPresent() {
    // Arrange
    Mapping inputMapping = new Mapping();
    inputMapping.setType(SourceMappingType.VARIABLE);
    inputMapping.setValue("Value");
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertFalse(
        extensionsVariablesMappingProvider
            .calculateMappedValue(inputMapping, execution, new Extension())
            .isPresent());
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping, DelegateExecution,
   * Extension)}.
   *
   * <ul>
   *   <li>When {@link Extension} {@link Extension#getPropertyByName(String)} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping,
   * DelegateExecution, Extension)}
   */
  @Test
  @DisplayName(
      "Test calculateMappedValue(Mapping, DelegateExecution, Extension); when Extension getPropertyByName(String) return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Optional ExtensionsVariablesMappingProvider.calculateMappedValue(Mapping, DelegateExecution, Extension)"
  })
  void testCalculateMappedValue_whenExtensionGetPropertyByNameReturnNull() {
    // Arrange
    Mapping inputMapping = new Mapping();
    inputMapping.setType(SourceMappingType.VARIABLE);
    inputMapping.setValue("Value");

    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariable(Mockito.<String>any())).thenReturn("Variable");

    Extension extensions = mock(Extension.class);
    when(extensions.getPropertyByName(Mockito.<String>any())).thenReturn(null);

    // Act
    Optional<Object> actualCalculateMappedValueResult =
        extensionsVariablesMappingProvider.calculateMappedValue(
            inputMapping, execution, extensions);

    // Assert
    verify(execution, atLeast(1)).getVariable("Value");
    verify(extensions).getPropertyByName("Value");
    assertEquals("Variable", actualCalculateMappedValueResult.get());
    assertTrue(actualCalculateMappedValueResult.isPresent());
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping, DelegateExecution,
   * Extension)}.
   *
   * <ul>
   *   <li>When {@link Extension} (default constructor) Properties is {@code null}.
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping,
   * DelegateExecution, Extension)}
   */
  @Test
  @DisplayName(
      "Test calculateMappedValue(Mapping, DelegateExecution, Extension); when Extension (default constructor) Properties is 'null'; then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Optional ExtensionsVariablesMappingProvider.calculateMappedValue(Mapping, DelegateExecution, Extension)"
  })
  void testCalculateMappedValue_whenExtensionPropertiesIsNull_thenReturnNotPresent() {
    // Arrange
    Extension extensions = new Extension();
    extensions.setProperties(null);

    // Act and Assert
    assertFalse(
        extensionsVariablesMappingProvider
            .calculateMappedValue(null, null, extensions)
            .isPresent());
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping, DelegateExecution,
   * Extension)}.
   *
   * <ul>
   *   <li>When {@link Extension} (default constructor) Properties is {@code null}.
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping,
   * DelegateExecution, Extension)}
   */
  @Test
  @DisplayName(
      "Test calculateMappedValue(Mapping, DelegateExecution, Extension); when Extension (default constructor) Properties is 'null'; then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Optional ExtensionsVariablesMappingProvider.calculateMappedValue(Mapping, DelegateExecution, Extension)"
  })
  void testCalculateMappedValue_whenExtensionPropertiesIsNull_thenReturnNotPresent2() {
    // Arrange
    Mapping inputMapping = new Mapping();
    inputMapping.setType(SourceMappingType.VARIABLE);
    inputMapping.setValue("Value");

    Extension extensions = new Extension();
    extensions.setProperties(null);

    // Act and Assert
    assertFalse(
        extensionsVariablesMappingProvider
            .calculateMappedValue(inputMapping, null, extensions)
            .isPresent());
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)} with
   * {@code execution}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  @DisplayName("Test calculateInputVariables(DelegateExecution) with 'execution'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateInputVariables(DelegateExecution)"
  })
  void testCalculateInputVariablesWithExecution() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());

    // Act
    Map<String, Object> actualCalculateInputVariablesResult =
        extensionsVariablesMappingProvider.calculateInputVariables(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(processExtensionService).getExtensionsForId(null);
    assertTrue(actualCalculateInputVariablesResult.isEmpty());
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)} with
   * {@code execution}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  @DisplayName("Test calculateInputVariables(DelegateExecution) with 'execution'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateInputVariables(DelegateExecution)"
  })
  void testCalculateInputVariablesWithExecution2() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            extensionsVariablesMappingProvider.calculateInputVariables(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(processExtensionService).getExtensionsForId(null);
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)} with
   * {@code execution}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  @DisplayName("Test calculateInputVariables(DelegateExecution) with 'execution'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateInputVariables(DelegateExecution)"
  })
  void testCalculateInputVariablesWithExecution3() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.hasMapping(Mockito.<String>any()))
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(extension.getConstantForFlowElement(Mockito.<String>any()))
        .thenReturn(new ProcessConstantsMapping());
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            extensionsVariablesMappingProvider.calculateInputVariables(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(processExtensionService).getExtensionsForId(null);
    verify(extension).getConstantForFlowElement(null);
    verify(extension).hasMapping(null);
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)} with
   * {@code execution}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  @DisplayName("Test calculateInputVariables(DelegateExecution) with 'execution'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateInputVariables(DelegateExecution)"
  })
  void testCalculateInputVariablesWithExecution4() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllInputs(Mockito.<String>any())).thenReturn(true);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any()))
        .thenReturn(new ProcessConstantsMapping());
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act
    Map<String, Object> actualCalculateInputVariablesResult =
        extensionsVariablesMappingProvider.calculateInputVariables(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(processExtensionService).getExtensionsForId(null);
    verify(extension).getConstantForFlowElement(null);
    verify(extension).hasMapping(null);
    verify(extension).shouldMapAllInputs(null);
    assertTrue(actualCalculateInputVariablesResult.isEmpty());
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)} with
   * {@code execution}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  @DisplayName("Test calculateInputVariables(DelegateExecution) with 'execution'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateInputVariables(DelegateExecution)"
  })
  void testCalculateInputVariablesWithExecution5() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllInputs(Mockito.<String>any()))
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any()))
        .thenReturn(new ProcessConstantsMapping());
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            extensionsVariablesMappingProvider.calculateInputVariables(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(processExtensionService).getExtensionsForId(null);
    verify(extension).getConstantForFlowElement(null);
    verify(extension).hasMapping(null);
    verify(extension).shouldMapAllInputs(null);
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)} with
   * {@code execution}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  @DisplayName("Test calculateInputVariables(DelegateExecution) with 'execution'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateInputVariables(DelegateExecution)"
  })
  void testCalculateInputVariablesWithExecution6() {
    // Arrange
    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());

    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllInputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any()))
        .thenReturn(new ProcessConstantsMapping());
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.resolveExpressionsMap(
            Mockito.<ExpressionEvaluator>any(), Mockito.<Map<String, Object>>any()))
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            extensionsVariablesMappingProvider.calculateInputVariables(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(expressionResolver)
        .resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId(null);
    verify(extension).getConstantForFlowElement(null);
    verify(extension).getMappingForFlowElement(null);
    verify(extension).hasMapping(null);
    verify(extension).shouldMapAllInputs(null);
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)} with
   * {@code execution}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  @DisplayName("Test calculateInputVariables(DelegateExecution) with 'execution'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateInputVariables(DelegateExecution)"
  })
  void testCalculateInputVariablesWithExecution7() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    HashMap<String, Mapping> inputs = new HashMap<>();
    inputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(inputs);
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());

    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any()))
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(extension.shouldMapAllInputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any()))
        .thenReturn(new ProcessConstantsMapping());
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            extensionsVariablesMappingProvider.calculateInputVariables(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(processExtensionService).getExtensionsForId(null);
    verify(extension).getConstantForFlowElement(null);
    verify(extension).getMappingForFlowElement(null);
    verify(extension).getPropertyByName("Value");
    verify(extension).hasMapping(null);
    verify(extension).shouldMapAllInputs(null);
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)} with
   * {@code execution}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  @DisplayName("Test calculateInputVariables(DelegateExecution) with 'execution'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateInputVariables(DelegateExecution)"
  })
  void testCalculateInputVariablesWithExecution8() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(SourceMappingType.VALUE);
    mapping.setValue("Value");

    Mapping mapping2 = new Mapping();
    mapping2.setType(SourceMappingType.JSONPATCH);
    mapping2.setValue("Value");

    Mapping mapping3 = new Mapping();
    mapping3.setType(SourceMappingType.VARIABLE);
    mapping3.setValue("Value");

    HashMap<String, Mapping> inputs = new HashMap<>();
    inputs.put("${U}", mapping3);
    inputs.put("foo", mapping2);
    inputs.put("42", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(inputs);
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());

    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(null);
    when(extension.shouldMapAllInputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any()))
        .thenReturn(new ProcessConstantsMapping());

    ProcessExtensionService processExtensionService = mock(ProcessExtensionService.class);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    ExpressionResolver expressionResolver = mock(ExpressionResolver.class);
    when(expressionResolver.resolveExpressionsMap(
            Mockito.<ExpressionEvaluator>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());

    ExtensionsVariablesMappingProvider extensionsVariablesMappingProvider =
        new ExtensionsVariablesMappingProvider(
            processExtensionService,
            expressionResolver,
            new VariableParsingService(new HashMap<>()));
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    Map<String, Object> actualCalculateInputVariablesResult =
        extensionsVariablesMappingProvider.calculateInputVariables(execution);

    // Assert
    verify(expressionResolver)
        .resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId(null);
    verify(extension).getConstantForFlowElement(null);
    verify(extension).getMappingForFlowElement(null);
    verify(extension).getPropertyByName("Value");
    verify(extension).hasMapping(null);
    verify(extension).shouldMapAllInputs(null);
    assertNull(execution.getCachedElContext());
    assertTrue(actualCalculateInputVariablesResult.isEmpty());
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)} with
   * {@code execution}.
   *
   * <ul>
   *   <li>Given {@link Mapping} (default constructor) Type is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  @DisplayName(
      "Test calculateInputVariables(DelegateExecution) with 'execution'; given Mapping (default constructor) Type is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateInputVariables(DelegateExecution)"
  })
  void testCalculateInputVariablesWithExecution_givenMappingTypeIsNull() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(null);
    mapping.setValue("Value");

    HashMap<String, Mapping> inputs = new HashMap<>();
    inputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(inputs);
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());

    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllInputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any()))
        .thenReturn(new ProcessConstantsMapping());
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.resolveExpressionsMap(
            Mockito.<ExpressionEvaluator>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());

    // Act
    Map<String, Object> actualCalculateInputVariablesResult =
        extensionsVariablesMappingProvider.calculateInputVariables(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(expressionResolver)
        .resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId(null);
    verify(extension).getConstantForFlowElement(null);
    verify(extension).getMappingForFlowElement(null);
    verify(extension).hasMapping(null);
    verify(extension).shouldMapAllInputs(null);
    assertTrue(actualCalculateInputVariablesResult.isEmpty());
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)} with
   * {@code execution}.
   *
   * <ul>
   *   <li>Then calls {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  @DisplayName(
      "Test calculateInputVariables(DelegateExecution) with 'execution'; then calls resolveExpressionsMap(ExpressionEvaluator, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateInputVariables(DelegateExecution)"
  })
  void testCalculateInputVariablesWithExecution_thenCallsResolveExpressionsMap() {
    // Arrange
    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());

    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllInputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any()))
        .thenReturn(new ProcessConstantsMapping());
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.resolveExpressionsMap(
            Mockito.<ExpressionEvaluator>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());

    // Act
    Map<String, Object> actualCalculateInputVariablesResult =
        extensionsVariablesMappingProvider.calculateInputVariables(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(expressionResolver)
        .resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId(null);
    verify(extension).getConstantForFlowElement(null);
    verify(extension).getMappingForFlowElement(null);
    verify(extension).hasMapping(null);
    verify(extension).shouldMapAllInputs(null);
    assertTrue(actualCalculateInputVariablesResult.isEmpty());
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)} with
   * {@code execution}.
   *
   * <ul>
   *   <li>Then calls {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  @DisplayName(
      "Test calculateInputVariables(DelegateExecution) with 'execution'; then calls resolveExpressionsMap(ExpressionEvaluator, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateInputVariables(DelegateExecution)"
  })
  void testCalculateInputVariablesWithExecution_thenCallsResolveExpressionsMap2() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    HashMap<String, Mapping> inputs = new HashMap<>();
    inputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(inputs);
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());

    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(new VariableDefinition());
    when(extension.shouldMapAllInputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any()))
        .thenReturn(new ProcessConstantsMapping());
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.resolveExpressionsMap(
            Mockito.<ExpressionEvaluator>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());

    // Act
    Map<String, Object> actualCalculateInputVariablesResult =
        extensionsVariablesMappingProvider.calculateInputVariables(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(expressionResolver)
        .resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId(null);
    verify(extension).getConstantForFlowElement(null);
    verify(extension).getMappingForFlowElement(null);
    verify(extension).getPropertyByName("Value");
    verify(extension).hasMapping(null);
    verify(extension).shouldMapAllInputs(null);
    assertTrue(actualCalculateInputVariablesResult.isEmpty());
  }

  /**
   * Test {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)} with
   * {@code execution}.
   *
   * <ul>
   *   <li>Then calls {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  @DisplayName(
      "Test calculateInputVariables(DelegateExecution) with 'execution'; then calls resolveExpressionsMap(ExpressionEvaluator, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateInputVariables(DelegateExecution)"
  })
  void testCalculateInputVariablesWithExecution_thenCallsResolveExpressionsMap3() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    Mapping mapping2 = new Mapping();
    mapping2.setType(SourceMappingType.VALUE);
    mapping2.setValue("Value");

    HashMap<String, Mapping> inputs = new HashMap<>();
    inputs.put("42", mapping2);
    inputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(inputs);
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());

    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(new VariableDefinition());
    when(extension.shouldMapAllInputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any()))
        .thenReturn(new ProcessConstantsMapping());
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.resolveExpressionsMap(
            Mockito.<ExpressionEvaluator>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());

    // Act
    Map<String, Object> actualCalculateInputVariablesResult =
        extensionsVariablesMappingProvider.calculateInputVariables(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(expressionResolver)
        .resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId(null);
    verify(extension).getConstantForFlowElement(null);
    verify(extension).getMappingForFlowElement(null);
    verify(extension).getPropertyByName("Value");
    verify(extension).hasMapping(null);
    verify(extension).shouldMapAllInputs(null);
    assertTrue(actualCalculateInputVariablesResult.isEmpty());
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenReturn(new Extension());
    MappingExecutionContext mappingExecutionContext =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act
    Map<String, Object> actualCalculateOutPutVariablesResult =
        extensionsVariablesMappingProvider.calculateOutPutVariables(
            mappingExecutionContext, new HashMap<>());

    // Assert
    verify(processExtensionService).getExtensionsForId("42");
    assertTrue(actualCalculateOutPutVariablesResult.isEmpty());
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables2() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any()))
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    MappingExecutionContext mappingExecutionContext =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            extensionsVariablesMappingProvider.calculateOutPutVariables(
                mappingExecutionContext, new HashMap<>()));
    verify(processExtensionService).getExtensionsForId("42");
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables3() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(true);
    MappingExecutionContext mappingExecutionContext =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            extensionsVariablesMappingProvider.calculateOutPutVariables(
                mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).hasMapping("42");
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables4() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any()))
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    MappingExecutionContext mappingExecutionContext =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            extensionsVariablesMappingProvider.calculateOutPutVariables(
                mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).hasMapping("42");
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables5() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(true);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act
    Map<String, Object> actualCalculateOutPutVariablesResult =
        extensionsVariablesMappingProvider.calculateOutPutVariables(
            mappingExecutionContext, new HashMap<>());

    // Assert
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).hasMapping("42");
    verify(extension).shouldMapAllOutputs("42");
    assertTrue(actualCalculateOutPutVariablesResult.isEmpty());
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables6() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllOutputs(Mockito.<String>any()))
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            extensionsVariablesMappingProvider.calculateOutPutVariables(
                mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).hasMapping("42");
    verify(extension).shouldMapAllOutputs("42");
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables7() {
    // Arrange
    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());

    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.resolveExpressionsMap(
            Mockito.<ExpressionEvaluator>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act
    Map<String, Object> actualCalculateOutPutVariablesResult =
        extensionsVariablesMappingProvider.calculateOutPutVariables(
            mappingExecutionContext, new HashMap<>());

    // Assert
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(expressionResolver)
        .resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).getMappingForFlowElement("42");
    verify(extension).hasMapping("42");
    verify(extension).shouldMapAllOutputs("42");
    assertTrue(actualCalculateOutPutVariablesResult.isEmpty());
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables8() {
    // Arrange
    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());

    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.resolveExpressionsMap(
            Mockito.<ExpressionEvaluator>any(), Mockito.<Map<String, Object>>any()))
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            extensionsVariablesMappingProvider.calculateOutPutVariables(
                mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(expressionResolver)
        .resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).getMappingForFlowElement("42");
    verify(extension).hasMapping("42");
    verify(extension).shouldMapAllOutputs("42");
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables9() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);

    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(new VariableDefinition());
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.resolveExpressionsMap(
            Mockito.<ExpressionEvaluator>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act
    Map<String, Object> actualCalculateOutPutVariablesResult =
        extensionsVariablesMappingProvider.calculateOutPutVariables(
            mappingExecutionContext, new HashMap<>());

    // Assert
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(expressionResolver)
        .resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).getMappingForFlowElement("42");
    verify(extension, atLeast(1)).getPropertyByName("foo");
    verify(extension).hasMapping("42");
    verify(extension).shouldMapAllOutputs("42");
    assertTrue(actualCalculateOutPutVariablesResult.isEmpty());
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables10() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);

    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any()))
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            extensionsVariablesMappingProvider.calculateOutPutVariables(
                mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).getMappingForFlowElement("42");
    verify(extension).getPropertyByName("foo");
    verify(extension).hasMapping("42");
    verify(extension).shouldMapAllOutputs("42");
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables11() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);

    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(null);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.resolveExpressionsMap(
            Mockito.<ExpressionEvaluator>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act
    Map<String, Object> actualCalculateOutPutVariablesResult =
        extensionsVariablesMappingProvider.calculateOutPutVariables(
            mappingExecutionContext, new HashMap<>());

    // Assert
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(expressionResolver)
        .resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).getMappingForFlowElement("42");
    verify(extension).getPropertyByName("foo");
    verify(extension).hasMapping("42");
    verify(extension).shouldMapAllOutputs("42");
    assertTrue(actualCalculateOutPutVariablesResult.isEmpty());
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables12() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    Mapping mapping2 = new Mapping();
    mapping2.setType(SourceMappingType.VALUE);
    mapping2.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("42", mapping2);
    outputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);

    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenReturn(new HashMap<>());
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(new VariableDefinition());
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.resolveExpressionsMap(
            Mockito.<ExpressionEvaluator>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act
    Map<String, Object> actualCalculateOutPutVariablesResult =
        extensionsVariablesMappingProvider.calculateOutPutVariables(
            mappingExecutionContext, new HashMap<>());

    // Assert
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(expressionResolver)
        .resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).getMappingForFlowElement("42");
    verify(extension).getProperties();
    verify(extension, atLeast(1)).getPropertyByName(Mockito.<String>any());
    verify(extension).hasMapping("42");
    verify(extension).shouldMapAllOutputs("42");
    assertTrue(actualCalculateOutPutVariablesResult.isEmpty());
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables13() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    Mapping mapping2 = new Mapping();
    mapping2.setType(SourceMappingType.VALUE);
    mapping2.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("42", mapping2);
    outputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);

    Extension extension = mock(Extension.class);
    when(extension.getProperties())
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(new VariableDefinition());
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            extensionsVariablesMappingProvider.calculateOutPutVariables(
                mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).getMappingForFlowElement("42");
    verify(extension).getProperties();
    verify(extension, atLeast(1)).getPropertyByName(Mockito.<String>any());
    verify(extension).hasMapping("42");
    verify(extension).shouldMapAllOutputs("42");
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables14() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    Mapping mapping2 = new Mapping();
    mapping2.setType(SourceMappingType.VALUE);
    mapping2.setValue("Value");

    Mapping mapping3 = new Mapping();
    mapping3.setType(SourceMappingType.JSONPATCH);
    mapping3.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping3);
    outputs.put("42", mapping2);
    outputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);

    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(new VariableDefinition());
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);

    ProcessExtensionService processExtensionService = mock(ProcessExtensionService.class);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    ExpressionResolver expressionResolver = mock(ExpressionResolver.class);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);

    ExtensionsVariablesMappingProvider extensionsVariablesMappingProvider =
        new ExtensionsVariablesMappingProvider(
            processExtensionService,
            expressionResolver,
            new VariableParsingService(new HashMap<>()));
    MappingExecutionContext mappingExecutionContext =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            extensionsVariablesMappingProvider.calculateOutPutVariables(
                mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).getMappingForFlowElement("42");
    verify(extension, atLeast(1)).getPropertyByName("");
    verify(extension).hasMapping("42");
    verify(extension).shouldMapAllOutputs("42");
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables15() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    Mapping mapping2 = new Mapping();
    mapping2.setType(null);
    mapping2.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("42", mapping2);
    outputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);

    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(new VariableDefinition());
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);

    ProcessExtensionService processExtensionService = mock(ProcessExtensionService.class);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    ExpressionResolver expressionResolver = mock(ExpressionResolver.class);
    when(expressionResolver.resolveExpressionsMap(
            Mockito.<ExpressionEvaluator>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);

    ExtensionsVariablesMappingProvider extensionsVariablesMappingProvider =
        new ExtensionsVariablesMappingProvider(
            processExtensionService,
            expressionResolver,
            new VariableParsingService(new HashMap<>()));
    MappingExecutionContext mappingExecutionContext =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act
    Map<String, Object> actualCalculateOutPutVariablesResult =
        extensionsVariablesMappingProvider.calculateOutPutVariables(
            mappingExecutionContext, new HashMap<>());

    // Assert
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(expressionResolver)
        .resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).getMappingForFlowElement("42");
    verify(extension, atLeast(1)).getPropertyByName(Mockito.<String>any());
    verify(extension).hasMapping("42");
    verify(extension).shouldMapAllOutputs("42");
    assertTrue(actualCalculateOutPutVariablesResult.isEmpty());
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables16() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    Mapping mapping2 = new Mapping();
    mapping2.setType(SourceMappingType.VALUE);
    mapping2.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("42", mapping2);
    outputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);

    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenReturn(new HashMap<>());
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(new VariableDefinition());
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);

    ProcessExtensionService processExtensionService = mock(ProcessExtensionService.class);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    ExpressionResolver expressionResolver = mock(ExpressionResolver.class);
    when(expressionResolver.resolveExpressionsMap(
            Mockito.<ExpressionEvaluator>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);

    ExtensionsVariablesMappingProvider extensionsVariablesMappingProvider =
        new ExtensionsVariablesMappingProvider(
            processExtensionService,
            expressionResolver,
            new VariableParsingService(new HashMap<>()));
    MappingExecutionContext mappingExecutionContext =
        new MappingExecutionContext(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    Map<String, Object> actualCalculateOutPutVariablesResult =
        extensionsVariablesMappingProvider.calculateOutPutVariables(
            mappingExecutionContext, new HashMap<>());

    // Assert
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(expressionResolver)
        .resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId(null);
    verify(extension).getMappingForFlowElement(null);
    verify(extension).getProperties();
    verify(extension, atLeast(1)).getPropertyByName(Mockito.<String>any());
    verify(extension).hasMapping(null);
    verify(extension).shouldMapAllOutputs(null);
    assertTrue(actualCalculateOutPutVariablesResult.isEmpty());
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables17() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    Mapping mapping2 = new Mapping();
    mapping2.setType(SourceMappingType.VALUE);
    mapping2.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("42", mapping2);
    outputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);

    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn("Value");

    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenReturn(new HashMap<>());
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);

    ProcessExtensionService processExtensionService = mock(ProcessExtensionService.class);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    ExpressionResolver expressionResolver = mock(ExpressionResolver.class);
    when(expressionResolver.resolveExpressionsMap(
            Mockito.<ExpressionEvaluator>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);

    ExtensionsVariablesMappingProvider extensionsVariablesMappingProvider =
        new ExtensionsVariablesMappingProvider(
            processExtensionService,
            expressionResolver,
            new VariableParsingService(new HashMap<>()));
    MappingExecutionContext mappingExecutionContext =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act
    Map<String, Object> actualCalculateOutPutVariablesResult =
        extensionsVariablesMappingProvider.calculateOutPutVariables(
            mappingExecutionContext, new HashMap<>());

    // Assert
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(expressionResolver)
        .resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).getMappingForFlowElement("42");
    verify(extension).getProperties();
    verify(extension, atLeast(1)).getPropertyByName(Mockito.<String>any());
    verify(extension).hasMapping("42");
    verify(extension).shouldMapAllOutputs("42");
    verify(variableDefinition, atLeast(1)).getValue();
    assertTrue(actualCalculateOutPutVariablesResult.isEmpty());
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables18() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    Mapping mapping2 = new Mapping();
    mapping2.setType(SourceMappingType.VALUE);
    mapping2.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("42", mapping2);
    outputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);

    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue())
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));

    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);

    ProcessExtensionService processExtensionService = mock(ProcessExtensionService.class);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    ExpressionResolver expressionResolver = mock(ExpressionResolver.class);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);

    ExtensionsVariablesMappingProvider extensionsVariablesMappingProvider =
        new ExtensionsVariablesMappingProvider(
            processExtensionService,
            expressionResolver,
            new VariableParsingService(new HashMap<>()));
    MappingExecutionContext mappingExecutionContext =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            extensionsVariablesMappingProvider.calculateOutPutVariables(
                mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).getMappingForFlowElement("42");
    verify(extension, atLeast(1)).getPropertyByName("foo");
    verify(extension).hasMapping("42");
    verify(extension).shouldMapAllOutputs("42");
    verify(variableDefinition).getValue();
  }

  /**
   * Test {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)} with
   * {@code mappingExecutionContext}, {@code availableVariables}.
   *
   * <p>Method under test: {@link
   * ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateOutPutVariables(MappingExecutionContext, Map) with 'mappingExecutionContext', 'availableVariables'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ExtensionsVariablesMappingProvider.calculateOutPutVariables(MappingExecutionContext, Map)"
  })
  void testCalculateOutPutVariablesWithMappingExecutionContextAvailableVariables19() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    Mapping mapping2 = new Mapping();
    mapping2.setType(SourceMappingType.JSONPATCH);
    mapping2.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("42", mapping2);
    outputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);

    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn("Value");

    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any()))
        .thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);

    ProcessExtensionService processExtensionService = mock(ProcessExtensionService.class);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    ExpressionResolver expressionResolver = mock(ExpressionResolver.class);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);

    ExtensionsVariablesMappingProvider extensionsVariablesMappingProvider =
        new ExtensionsVariablesMappingProvider(
            processExtensionService,
            expressionResolver,
            new VariableParsingService(new HashMap<>()));
    MappingExecutionContext mappingExecutionContext =
        MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            extensionsVariablesMappingProvider.calculateOutPutVariables(
                mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId("42");
    verify(extension).getMappingForFlowElement("42");
    verify(extension, atLeast(1)).getPropertyByName(Mockito.<String>any());
    verify(extension).hasMapping("42");
    verify(extension).shouldMapAllOutputs("42");
    verify(variableDefinition, atLeast(1)).getValue();
  }
}
