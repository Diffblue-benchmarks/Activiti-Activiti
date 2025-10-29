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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.behavior.MappingExecutionContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.spring.process.ProcessExtensionService;
import org.activiti.spring.process.model.ConstantDefinition;
import org.activiti.spring.process.model.Extension;
import org.activiti.spring.process.model.Mapping;
import org.activiti.spring.process.model.ProcessConstantsMapping;
import org.activiti.spring.process.model.ProcessVariablesMapping;
import org.activiti.spring.process.model.VariableDefinition;
import org.activiti.spring.process.variable.VariableParsingService;
import org.apache.commons.collections4.keyvalue.DefaultMapEntry;
import org.apache.commons.collections4.keyvalue.TiedMapEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ExtensionsVariablesMappingProvider.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ExtensionsVariablesMappingProviderDiffblueTest {
  @MockBean
  private ExpressionResolver expressionResolver;

  @Autowired
  private ExtensionsVariablesMappingProvider extensionsVariablesMappingProvider;

  @MockBean
  private ProcessExtensionService processExtensionService;

  @MockBean
  private VariableParsingService variableParsingService;

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping, DelegateExecution, Extension)}
   */
  @Test
  void testCalculateMappedValue() {
    // Arrange
    Mapping inputMapping = new Mapping();
    inputMapping.setType(Mapping.SourceMappingType.VARIABLE);
    inputMapping.setValue("Value");
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertFalse(
        extensionsVariablesMappingProvider.calculateMappedValue(inputMapping, execution, new Extension()).isPresent());
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping, DelegateExecution, Extension)}
   */
  @Test
  void testCalculateMappedValue2() {
    // Arrange
    Mapping inputMapping = mock(Mapping.class);
    when(inputMapping.getValue()).thenReturn("Value");
    when(inputMapping.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    doNothing().when(inputMapping).setType(Mockito.<Mapping.SourceMappingType>any());
    doNothing().when(inputMapping).setValue(Mockito.<Object>any());
    inputMapping.setType(Mapping.SourceMappingType.VARIABLE);
    inputMapping.setValue("Value");
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    Optional<Object> actualCalculateMappedValueResult = extensionsVariablesMappingProvider
        .calculateMappedValue(inputMapping, execution, new Extension());

    // Assert
    verify(inputMapping, atLeast(1)).getType();
    verify(inputMapping).getValue();
    verify(inputMapping).setType(eq(Mapping.SourceMappingType.VARIABLE));
    verify(inputMapping).setValue(isA(Object.class));
    assertFalse(actualCalculateMappedValueResult.isPresent());
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping, DelegateExecution, Extension)}
   */
  @Test
  void testCalculateMappedValue3() {
    // Arrange
    Mapping inputMapping = mock(Mapping.class);
    when(inputMapping.getValue()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(inputMapping.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    doNothing().when(inputMapping).setType(Mockito.<Mapping.SourceMappingType>any());
    doNothing().when(inputMapping).setValue(Mockito.<Object>any());
    inputMapping.setType(Mapping.SourceMappingType.VARIABLE);
    inputMapping.setValue("Value");
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateMappedValue(inputMapping, execution, new Extension()));
    verify(inputMapping, atLeast(1)).getType();
    verify(inputMapping).getValue();
    verify(inputMapping).setType(eq(Mapping.SourceMappingType.VARIABLE));
    verify(inputMapping).setValue(isA(Object.class));
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping, DelegateExecution, Extension)}
   */
  @Test
  void testCalculateMappedValue4() {
    // Arrange
    Mapping inputMapping = mock(Mapping.class);
    when(inputMapping.getType()).thenReturn(null);
    doNothing().when(inputMapping).setType(Mockito.<Mapping.SourceMappingType>any());
    doNothing().when(inputMapping).setValue(Mockito.<Object>any());
    inputMapping.setType(Mapping.SourceMappingType.VARIABLE);
    inputMapping.setValue("Value");
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    Optional<Object> actualCalculateMappedValueResult = extensionsVariablesMappingProvider
        .calculateMappedValue(inputMapping, execution, new Extension());

    // Assert
    verify(inputMapping, atLeast(1)).getType();
    verify(inputMapping).setType(eq(Mapping.SourceMappingType.VARIABLE));
    verify(inputMapping).setValue(isA(Object.class));
    assertFalse(actualCalculateMappedValueResult.isPresent());
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping, DelegateExecution, Extension)}
   */
  @Test
  void testCalculateMappedValue5() {
    // Arrange
    Mapping inputMapping = mock(Mapping.class);
    when(inputMapping.getValue()).thenReturn("Value");
    when(inputMapping.getType()).thenReturn(Mapping.SourceMappingType.VALUE);
    doNothing().when(inputMapping).setType(Mockito.<Mapping.SourceMappingType>any());
    doNothing().when(inputMapping).setValue(Mockito.<Object>any());
    inputMapping.setType(Mapping.SourceMappingType.VARIABLE);
    inputMapping.setValue("Value");
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    Optional<Object> actualCalculateMappedValueResult = extensionsVariablesMappingProvider
        .calculateMappedValue(inputMapping, execution, new Extension());

    // Assert
    verify(inputMapping).getType();
    verify(inputMapping).getValue();
    verify(inputMapping).setType(eq(Mapping.SourceMappingType.VARIABLE));
    verify(inputMapping).setValue(isA(Object.class));
    assertEquals("Value", actualCalculateMappedValueResult.get());
    assertTrue(actualCalculateMappedValueResult.isPresent());
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping, DelegateExecution, Extension)}
   */
  @Test
  void testCalculateMappedValue6() {
    // Arrange
    Mapping inputMapping = mock(Mapping.class);
    when(inputMapping.getValue()).thenReturn("Value");
    when(inputMapping.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    doNothing().when(inputMapping).setType(Mockito.<Mapping.SourceMappingType>any());
    doNothing().when(inputMapping).setValue(Mockito.<Object>any());
    inputMapping.setType(Mapping.SourceMappingType.VARIABLE);
    inputMapping.setValue("Value");

    // Act
    Optional<Object> actualCalculateMappedValueResult = extensionsVariablesMappingProvider
        .calculateMappedValue(inputMapping, null, new Extension());

    // Assert
    verify(inputMapping, atLeast(1)).getType();
    verify(inputMapping).getValue();
    verify(inputMapping).setType(eq(Mapping.SourceMappingType.VARIABLE));
    verify(inputMapping).setValue(isA(Object.class));
    assertFalse(actualCalculateMappedValueResult.isPresent());
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping, DelegateExecution, Extension)}
   */
  @Test
  void testCalculateMappedValue7() {
    // Arrange
    Mapping inputMapping = mock(Mapping.class);
    when(inputMapping.getValue()).thenReturn("Value");
    when(inputMapping.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    doNothing().when(inputMapping).setType(Mockito.<Mapping.SourceMappingType>any());
    doNothing().when(inputMapping).setValue(Mockito.<Object>any());
    inputMapping.setType(Mapping.SourceMappingType.VARIABLE);
    inputMapping.setValue("Value");
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariable(Mockito.<String>any())).thenReturn("Variable");

    // Act
    Optional<Object> actualCalculateMappedValueResult = extensionsVariablesMappingProvider
        .calculateMappedValue(inputMapping, execution, new Extension());

    // Assert
    verify(execution, atLeast(1)).getVariable(eq("Value"));
    verify(inputMapping, atLeast(1)).getType();
    verify(inputMapping).getValue();
    verify(inputMapping).setType(eq(Mapping.SourceMappingType.VARIABLE));
    verify(inputMapping).setValue(isA(Object.class));
    assertEquals("Variable", actualCalculateMappedValueResult.get());
    assertTrue(actualCalculateMappedValueResult.isPresent());
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateMappedValue(Mapping, DelegateExecution, Extension)}
   */
  @Test
  void testCalculateMappedValue8() {
    // Arrange
    Mapping inputMapping = mock(Mapping.class);
    when(inputMapping.getValue()).thenReturn("Value");
    when(inputMapping.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    doNothing().when(inputMapping).setType(Mockito.<Mapping.SourceMappingType>any());
    doNothing().when(inputMapping).setValue(Mockito.<Object>any());
    inputMapping.setType(Mapping.SourceMappingType.VARIABLE);
    inputMapping.setValue("Value");
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariable(Mockito.<String>any())).thenReturn("Variable");
    Extension extensions = mock(Extension.class);
    when(extensions.getPropertyByName(Mockito.<String>any())).thenReturn(new VariableDefinition());

    // Act
    Optional<Object> actualCalculateMappedValueResult = extensionsVariablesMappingProvider
        .calculateMappedValue(inputMapping, execution, extensions);

    // Assert
    verify(execution).getVariable(eq("Value"));
    verify(extensions).getPropertyByName(eq("Value"));
    verify(inputMapping, atLeast(1)).getType();
    verify(inputMapping).getValue();
    verify(inputMapping).setType(eq(Mapping.SourceMappingType.VARIABLE));
    verify(inputMapping).setValue(isA(Object.class));
    assertEquals("Variable", actualCalculateMappedValueResult.get());
    assertTrue(actualCalculateMappedValueResult.isPresent());
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  void testCalculateInputVariables() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(new Extension());

    // Act
    Map<String, Object> actualCalculateInputVariablesResult = extensionsVariablesMappingProvider
        .calculateInputVariables(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(processExtensionService).getExtensionsForId(isNull());
    assertTrue(actualCalculateInputVariablesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  void testCalculateInputVariables2() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllInputs(Mockito.<String>any())).thenReturn(true);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any())).thenReturn(new ProcessConstantsMapping());
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act
    Map<String, Object> actualCalculateInputVariablesResult = extensionsVariablesMappingProvider
        .calculateInputVariables(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(processExtensionService).getExtensionsForId(isNull());
    verify(extension).getConstantForFlowElement(isNull());
    verify(extension).hasMapping(isNull());
    verify(extension).shouldMapAllInputs(isNull());
    assertTrue(actualCalculateInputVariablesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  void testCalculateInputVariables3() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllInputs(Mockito.<String>any()))
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any())).thenReturn(new ProcessConstantsMapping());
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> extensionsVariablesMappingProvider
        .calculateInputVariables(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(processExtensionService).getExtensionsForId(isNull());
    verify(extension).getConstantForFlowElement(isNull());
    verify(extension).hasMapping(isNull());
    verify(extension).shouldMapAllInputs(isNull());
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  void testCalculateInputVariables4() {
    // Arrange
    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());
    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllInputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any())).thenReturn(new ProcessConstantsMapping());
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(expressionResolver.resolveExpressionsMap(Mockito.<ExpressionEvaluator>any(),
        Mockito.<Map<String, Object>>any())).thenReturn(stringObjectMap);

    // Act
    Map<String, Object> actualCalculateInputVariablesResult = extensionsVariablesMappingProvider
        .calculateInputVariables(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(expressionResolver).resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId(isNull());
    verify(extension).getConstantForFlowElement(isNull());
    verify(extension).getMappingForFlowElement(isNull());
    verify(extension).hasMapping(isNull());
    verify(extension).shouldMapAllInputs(isNull());
    assertTrue(actualCalculateInputVariablesResult.isEmpty());
    assertSame(stringObjectMap, actualCalculateInputVariablesResult);
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  void testCalculateInputVariables5() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    HashMap<String, Mapping> inputs = new HashMap<>();
    inputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(inputs);
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(new VariableDefinition());
    when(extension.shouldMapAllInputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any())).thenReturn(new ProcessConstantsMapping());
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(expressionResolver.resolveExpressionsMap(Mockito.<ExpressionEvaluator>any(),
        Mockito.<Map<String, Object>>any())).thenReturn(stringObjectMap);

    // Act
    Map<String, Object> actualCalculateInputVariablesResult = extensionsVariablesMappingProvider
        .calculateInputVariables(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(expressionResolver).resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId(isNull());
    verify(extension).getConstantForFlowElement(isNull());
    verify(extension).getMappingForFlowElement(isNull());
    verify(extension).getPropertyByName(eq("Value"));
    verify(extension).hasMapping(isNull());
    verify(extension).shouldMapAllInputs(isNull());
    assertTrue(actualCalculateInputVariablesResult.isEmpty());
    assertSame(stringObjectMap, actualCalculateInputVariablesResult);
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  void testCalculateInputVariables6() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    HashMap<String, Mapping> inputs = new HashMap<>();
    inputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(inputs);
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(null);
    when(extension.shouldMapAllInputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any())).thenReturn(new ProcessConstantsMapping());
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(expressionResolver.resolveExpressionsMap(Mockito.<ExpressionEvaluator>any(),
        Mockito.<Map<String, Object>>any())).thenReturn(stringObjectMap);

    // Act
    Map<String, Object> actualCalculateInputVariablesResult = extensionsVariablesMappingProvider
        .calculateInputVariables(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(expressionResolver).resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId(isNull());
    verify(extension).getConstantForFlowElement(isNull());
    verify(extension).getMappingForFlowElement(isNull());
    verify(extension).getPropertyByName(eq("Value"));
    verify(extension).hasMapping(isNull());
    verify(extension).shouldMapAllInputs(isNull());
    assertTrue(actualCalculateInputVariablesResult.isEmpty());
    assertSame(stringObjectMap, actualCalculateInputVariablesResult);
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  void testCalculateInputVariables7() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    Mapping mapping2 = new Mapping();
    mapping2.setType(Mapping.SourceMappingType.VALUE);
    mapping2.setValue("Value");

    HashMap<String, Mapping> inputs = new HashMap<>();
    inputs.put("42", mapping2);
    inputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(inputs);
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(new VariableDefinition());
    when(extension.shouldMapAllInputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any())).thenReturn(new ProcessConstantsMapping());
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(expressionResolver.resolveExpressionsMap(Mockito.<ExpressionEvaluator>any(),
        Mockito.<Map<String, Object>>any())).thenReturn(stringObjectMap);

    // Act
    Map<String, Object> actualCalculateInputVariablesResult = extensionsVariablesMappingProvider
        .calculateInputVariables(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(expressionResolver).resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId(isNull());
    verify(extension).getConstantForFlowElement(isNull());
    verify(extension).getMappingForFlowElement(isNull());
    verify(extension).getPropertyByName(eq("Value"));
    verify(extension).hasMapping(isNull());
    verify(extension).shouldMapAllInputs(isNull());
    assertTrue(actualCalculateInputVariablesResult.isEmpty());
    assertSame(stringObjectMap, actualCalculateInputVariablesResult);
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  void testCalculateInputVariables8() {
    // Arrange
    Mapping mapping = mock(Mapping.class);
    when(mapping.getValue()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(mapping.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    doNothing().when(mapping).setType(Mockito.<Mapping.SourceMappingType>any());
    doNothing().when(mapping).setValue(Mockito.<Object>any());
    mapping.setType(Mapping.SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    HashMap<String, Mapping> inputs = new HashMap<>();
    inputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(inputs);
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());
    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllInputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any())).thenReturn(new ProcessConstantsMapping());
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> extensionsVariablesMappingProvider
        .calculateInputVariables(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(processExtensionService).getExtensionsForId(isNull());
    verify(extension).getConstantForFlowElement(isNull());
    verify(extension).getMappingForFlowElement(isNull());
    verify(extension).hasMapping(isNull());
    verify(extension).shouldMapAllInputs(isNull());
    verify(mapping, atLeast(1)).getType();
    verify(mapping).getValue();
    verify(mapping).setType(eq(Mapping.SourceMappingType.VARIABLE));
    verify(mapping).setValue(isA(Object.class));
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  void testCalculateInputVariables9() {
    // Arrange
    Mapping mapping = mock(Mapping.class);
    when(mapping.getValue()).thenReturn("Value");
    when(mapping.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    doNothing().when(mapping).setType(Mockito.<Mapping.SourceMappingType>any());
    doNothing().when(mapping).setValue(Mockito.<Object>any());
    mapping.setType(Mapping.SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    Mapping mapping2 = new Mapping();
    mapping2.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping2.setValue("Value");

    HashMap<String, Mapping> inputs = new HashMap<>();
    inputs.put("", mapping2);
    inputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(inputs);
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(new VariableDefinition());
    when(extension.shouldMapAllInputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any())).thenReturn(new ProcessConstantsMapping());
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(expressionResolver.resolveExpressionsMap(Mockito.<ExpressionEvaluator>any(),
        Mockito.<Map<String, Object>>any())).thenReturn(stringObjectMap);

    // Act
    Map<String, Object> actualCalculateInputVariablesResult = extensionsVariablesMappingProvider
        .calculateInputVariables(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(expressionResolver).resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId(isNull());
    verify(extension).getConstantForFlowElement(isNull());
    verify(extension).getMappingForFlowElement(isNull());
    verify(extension).getPropertyByName(eq("Value"));
    verify(extension).hasMapping(isNull());
    verify(extension).shouldMapAllInputs(isNull());
    verify(mapping, atLeast(1)).getType();
    verify(mapping).getValue();
    verify(mapping).setType(eq(Mapping.SourceMappingType.VARIABLE));
    verify(mapping).setValue(isA(Object.class));
    assertTrue(actualCalculateInputVariablesResult.isEmpty());
    assertSame(stringObjectMap, actualCalculateInputVariablesResult);
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  void testCalculateInputVariables10() {
    // Arrange
    ConstantDefinition constantDefinition = new ConstantDefinition();
    constantDefinition.setValue("Value");

    ProcessConstantsMapping processConstantsMapping = new ProcessConstantsMapping();
    processConstantsMapping.put("foo", constantDefinition);
    Mapping mapping = mock(Mapping.class);
    when(mapping.getValue()).thenReturn("Value");
    when(mapping.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    doNothing().when(mapping).setType(Mockito.<Mapping.SourceMappingType>any());
    doNothing().when(mapping).setValue(Mockito.<Object>any());
    mapping.setType(Mapping.SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    HashMap<String, Mapping> inputs = new HashMap<>();
    inputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(inputs);
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(new VariableDefinition());
    when(extension.shouldMapAllInputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any())).thenReturn(processConstantsMapping);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(expressionResolver.resolveExpressionsMap(Mockito.<ExpressionEvaluator>any(),
        Mockito.<Map<String, Object>>any())).thenReturn(stringObjectMap);

    // Act
    Map<String, Object> actualCalculateInputVariablesResult = extensionsVariablesMappingProvider
        .calculateInputVariables(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(expressionResolver).resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId(isNull());
    verify(extension).getConstantForFlowElement(isNull());
    verify(extension).getMappingForFlowElement(isNull());
    verify(extension).getPropertyByName(eq("Value"));
    verify(extension).hasMapping(isNull());
    verify(extension).shouldMapAllInputs(isNull());
    verify(mapping, atLeast(1)).getType();
    verify(mapping).getValue();
    verify(mapping).setType(eq(Mapping.SourceMappingType.VARIABLE));
    verify(mapping).setValue(isA(Object.class));
    assertEquals(1, actualCalculateInputVariablesResult.size());
    assertEquals("Value", actualCalculateInputVariablesResult.get("foo"));
    assertSame(stringObjectMap, actualCalculateInputVariablesResult);
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateInputVariables(DelegateExecution)}
   */
  @Test
  void testCalculateInputVariables11() {
    // Arrange
    Mapping mapping = mock(Mapping.class);
    when(mapping.getValue()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(mapping.getType()).thenReturn(Mapping.SourceMappingType.VALUE);
    doNothing().when(mapping).setType(Mockito.<Mapping.SourceMappingType>any());
    doNothing().when(mapping).setValue(Mockito.<Object>any());
    mapping.setType(Mapping.SourceMappingType.VARIABLE);
    mapping.setValue("Value");

    HashMap<String, Mapping> inputs = new HashMap<>();
    inputs.put("foo", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(inputs);
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());
    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllInputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(extension.getConstantForFlowElement(Mockito.<String>any())).thenReturn(new ProcessConstantsMapping());
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> extensionsVariablesMappingProvider
        .calculateInputVariables(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(processExtensionService).getExtensionsForId(isNull());
    verify(extension).getConstantForFlowElement(isNull());
    verify(extension).getMappingForFlowElement(isNull());
    verify(extension).hasMapping(isNull());
    verify(extension).shouldMapAllInputs(isNull());
    verify(mapping).getType();
    verify(mapping).getValue();
    verify(mapping).setType(eq(Mapping.SourceMappingType.VARIABLE));
    verify(mapping).setValue(isA(Object.class));
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables() {
    // Arrange
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(new Extension());
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act
    Map<String, Object> actualCalculateOutPutVariablesResult = extensionsVariablesMappingProvider
        .calculateOutPutVariables(mappingExecutionContext, new HashMap<>());

    // Assert
    verify(processExtensionService).getExtensionsForId(eq("42"));
    assertTrue(actualCalculateOutPutVariablesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables2() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(true);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).hasMapping(eq("42"));
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables3() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any()))
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).hasMapping(eq("42"));
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables4() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(true);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act
    Map<String, Object> actualCalculateOutPutVariablesResult = extensionsVariablesMappingProvider
        .calculateOutPutVariables(mappingExecutionContext, new HashMap<>());

    // Assert
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    assertTrue(actualCalculateOutPutVariablesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables5() {
    // Arrange
    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());
    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(expressionResolver.resolveExpressionsMap(Mockito.<ExpressionEvaluator>any(),
        Mockito.<Map<String, Object>>any())).thenReturn(stringObjectMap);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act
    Map<String, Object> actualCalculateOutPutVariablesResult = extensionsVariablesMappingProvider
        .calculateOutPutVariables(mappingExecutionContext, new HashMap<>());

    // Assert
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(expressionResolver).resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    assertTrue(actualCalculateOutPutVariablesResult.isEmpty());
    assertSame(stringObjectMap, actualCalculateOutPutVariablesResult);
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables6() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(new VariableDefinition());
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables7() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(null);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(expressionResolver.resolveExpressionsMap(Mockito.<ExpressionEvaluator>any(),
        Mockito.<Map<String, Object>>any())).thenReturn(stringObjectMap);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act
    Map<String, Object> actualCalculateOutPutVariablesResult = extensionsVariablesMappingProvider
        .calculateOutPutVariables(mappingExecutionContext, new HashMap<>());

    // Assert
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(expressionResolver).resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    assertTrue(actualCalculateOutPutVariablesResult.isEmpty());
    assertSame(stringObjectMap, actualCalculateOutPutVariablesResult);
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables8() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(new VariableDefinition(
        "Error patching variable. Changes to apply: {}, Process variable current value: {}", "Value"));
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables9() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn("Value");
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(variableDefinition).getValue();
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables10() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);

    Mapping mapping2 = new Mapping();
    mapping2.setType(Mapping.SourceMappingType.VARIABLE);
    mapping2.setValue("Value");
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn(mapping2);
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(variableDefinition).getValue();
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables11() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn(new AbstractMap.SimpleEntry<>("42", "42"));
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(variableDefinition).getValue();
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables12() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(variableDefinition).getValue();
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables13() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(variableDefinition).getValue();
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables14() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn("");
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(variableDefinition).getValue();
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables15() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn(new DefaultMapEntry<>("Key", "Value"));
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(variableDefinition).getValue();
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables16() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn(new BinaryNode(new byte[]{'A', 1, 'A', 1, 'A', 1, 'A', 1}));
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(variableDefinition).getValue();
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables17() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    Mapping mapping2 = mock(Mapping.class);
    when(mapping2.getValue()).thenReturn("Value");
    when(mapping2.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn(mapping2);
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(mapping2).getType();
    verify(mapping2).getValue();
    verify(variableDefinition).getValue();
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables18() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);

    Mapping mapping2 = new Mapping();
    mapping2.setType(Mapping.SourceMappingType.VARIABLE);
    mapping2.setValue("Value");
    Mapping mapping3 = mock(Mapping.class);
    when(mapping3.getValue()).thenReturn(mapping2);
    when(mapping3.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn(mapping3);
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(mapping3).getType();
    verify(mapping3).getValue();
    verify(variableDefinition).getValue();
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables19() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    Mapping mapping2 = mock(Mapping.class);
    when(mapping2.getValue()).thenReturn(1);
    when(mapping2.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn(mapping2);
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(mapping2).getType();
    verify(mapping2).getValue();
    verify(variableDefinition).getValue();
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables20() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    Mapping mapping2 = mock(Mapping.class);
    when(mapping2.getValue()).thenReturn(new AbstractMap.SimpleEntry<>("42", "42"));
    when(mapping2.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn(mapping2);
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(mapping2).getType();
    verify(mapping2).getValue();
    verify(variableDefinition).getValue();
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables21() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    Mapping mapping2 = mock(Mapping.class);
    when(mapping2.getValue()).thenReturn(null);
    when(mapping2.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn(mapping2);
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(mapping2).getType();
    verify(mapping2).getValue();
    verify(variableDefinition).getValue();
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables22() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    Mapping mapping2 = mock(Mapping.class);
    when(mapping2.getValue()).thenReturn(new TiedMapEntry<>(new HashMap<>(), "Key"));
    when(mapping2.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn(mapping2);
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(mapping2).getType();
    verify(mapping2).getValue();
    verify(variableDefinition).getValue();
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables23() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    Mapping mapping2 = mock(Mapping.class);
    when(mapping2.getValue()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(mapping2.getType()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    Mapping mapping3 = mock(Mapping.class);
    when(mapping3.getValue()).thenReturn(mapping2);
    when(mapping3.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn(mapping3);
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(mapping3).getType();
    verify(mapping2).getType();
    verify(mapping3).getValue();
    verify(variableDefinition).getValue();
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables24() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    Mapping mapping2 = mock(Mapping.class);
    when(mapping2.getValue()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(mapping2.getType()).thenThrow(new ActivitiIllegalArgumentException(null));
    Mapping mapping3 = mock(Mapping.class);
    when(mapping3.getValue()).thenReturn(mapping2);
    when(mapping3.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn(mapping3);
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(mapping3).getType();
    verify(mapping2).getType();
    verify(mapping3).getValue();
    verify(variableDefinition).getValue();
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables25() {
    // Arrange
    Mapping mapping = new Mapping();
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    Mapping mapping2 = mock(Mapping.class);
    when(mapping2.getValue()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(mapping2.getType()).thenThrow(new ActivitiIllegalArgumentException(""));
    Mapping mapping3 = mock(Mapping.class);
    when(mapping3.getValue()).thenReturn(mapping2);
    when(mapping3.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn(mapping3);
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(mapping3).getType();
    verify(mapping2).getType();
    verify(mapping3).getValue();
    verify(variableDefinition).getValue();
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables26() {
    // Arrange
    Mapping mapping = mock(Mapping.class);
    when(mapping.getValue()).thenReturn("Value");
    when(mapping.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    doNothing().when(mapping).setType(Mockito.<Mapping.SourceMappingType>any());
    doNothing().when(mapping).setValue(Mockito.<Object>any());
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    Mapping mapping2 = mock(Mapping.class);
    when(mapping2.getValue()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(mapping2.getType()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    Mapping mapping3 = mock(Mapping.class);
    when(mapping3.getValue()).thenReturn(mapping2);
    when(mapping3.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn(mapping3);
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(expressionResolver.resolveExpressionsMap(Mockito.<ExpressionEvaluator>any(),
        Mockito.<Map<String, Object>>any())).thenReturn(stringObjectMap);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act
    Map<String, Object> actualCalculateOutPutVariablesResult = extensionsVariablesMappingProvider
        .calculateOutPutVariables(mappingExecutionContext, new HashMap<>());

    // Assert
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(expressionResolver).resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(mapping, atLeast(1)).getType();
    verify(mapping).getValue();
    verify(mapping).setType(eq(Mapping.SourceMappingType.JSONPATCH));
    verify(mapping).setValue(isA(Object.class));
    verify(variableDefinition).getValue();
    assertTrue(actualCalculateOutPutVariablesResult.isEmpty());
    assertSame(stringObjectMap, actualCalculateOutPutVariablesResult);
  }

  /**
   * Method under test:
   * {@link ExtensionsVariablesMappingProvider#calculateOutPutVariables(MappingExecutionContext, Map)}
   */
  @Test
  void testCalculateOutPutVariables27() {
    // Arrange
    Mapping mapping = mock(Mapping.class);
    when(mapping.getValue()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(mapping.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    doNothing().when(mapping).setType(Mockito.<Mapping.SourceMappingType>any());
    doNothing().when(mapping).setValue(Mockito.<Object>any());
    mapping.setType(Mapping.SourceMappingType.JSONPATCH);
    mapping.setValue("Value");

    HashMap<String, Mapping> outputs = new HashMap<>();
    outputs.put("", mapping);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(outputs);
    Mapping mapping2 = mock(Mapping.class);
    when(mapping2.getValue()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(mapping2.getType()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    Mapping mapping3 = mock(Mapping.class);
    when(mapping3.getValue()).thenReturn(mapping2);
    when(mapping3.getType()).thenReturn(Mapping.SourceMappingType.VARIABLE);
    VariableDefinition variableDefinition = mock(VariableDefinition.class);
    when(variableDefinition.getValue()).thenReturn(mapping3);
    Extension extension = mock(Extension.class);
    when(extension.getPropertyByName(Mockito.<String>any())).thenReturn(variableDefinition);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    MappingExecutionContext mappingExecutionContext = MappingExecutionContext.buildMappingExecutionContext("42", "42");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> extensionsVariablesMappingProvider.calculateOutPutVariables(mappingExecutionContext, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsForId(eq("42"));
    verify(extension).getMappingForFlowElement(eq("42"));
    verify(extension, atLeast(1)).getPropertyByName(eq(""));
    verify(extension).hasMapping(eq("42"));
    verify(extension).shouldMapAllOutputs(eq("42"));
    verify(mapping, atLeast(1)).getType();
    verify(mapping).getValue();
    verify(mapping).setType(eq(Mapping.SourceMappingType.JSONPATCH));
    verify(mapping).setValue(isA(Object.class));
    verify(variableDefinition).getValue();
  }
}
