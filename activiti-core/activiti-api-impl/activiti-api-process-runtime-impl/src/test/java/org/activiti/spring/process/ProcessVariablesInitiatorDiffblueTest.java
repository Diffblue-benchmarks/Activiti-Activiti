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
package org.activiti.spring.process;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.bpmn.behavior.MappingExecutionContext;
import org.activiti.engine.impl.bpmn.behavior.VariablesCalculator;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.runtime.api.impl.ExpressionEvaluator;
import org.activiti.runtime.api.impl.ExpressionResolver;
import org.activiti.spring.process.model.Extension;
import org.activiti.spring.process.model.VariableDefinition;
import org.activiti.spring.process.variable.VariableParsingService;
import org.activiti.spring.process.variable.VariableValidationService;
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

@ContextConfiguration(classes = {ProcessVariablesInitiator.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ProcessVariablesInitiatorDiffblueTest {
  @MockBean private ExpressionResolver expressionResolver;

  @MockBean private ProcessExtensionService processExtensionService;

  @Autowired private ProcessVariablesInitiator processVariablesInitiator;

  @MockBean private VariableParsingService variableParsingService;

  @MockBean private VariableValidationService variableValidationService;

  @MockBean private VariablesCalculator variablesCalculator;

  /**
   * Test {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition,
   * Map)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName("Test calculateVariablesFromExtensionFile(ProcessDefinition, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateVariablesFromExtensionFile(ProcessDefinition, Map)"
  })
  void testCalculateVariablesFromExtensionFile() {
    // Arrange
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenThrow(new ActivitiException("An error occurred"));
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesInitiator.calculateVariablesFromExtensionFile(
                processDefinition, new HashMap<>()));
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition,
   * Map)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName("Test calculateVariablesFromExtensionFile(ProcessDefinition, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateVariablesFromExtensionFile(ProcessDefinition, Map)"
  })
  void testCalculateVariablesFromExtensionFile2() {
    // Arrange
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(new Extension());
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(true);
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateVariablesFromExtensionFileResult =
        processVariablesInitiator.calculateVariablesFromExtensionFile(
            processDefinition, new HashMap<>());

    // Assert
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
    assertTrue(actualCalculateVariablesFromExtensionFileResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition,
   * Map)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName("Test calculateVariablesFromExtensionFile(ProcessDefinition, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateVariablesFromExtensionFile(ProcessDefinition, Map)"
  })
  void testCalculateVariablesFromExtensionFile3() {
    // Arrange
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenThrow(new ActivitiException("An error occurred"));
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(true);
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesInitiator.calculateVariablesFromExtensionFile(
                processDefinition, new HashMap<>()));
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition,
   * Map)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName("Test calculateVariablesFromExtensionFile(ProcessDefinition, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateVariablesFromExtensionFile(ProcessDefinition, Map)"
  })
  void testCalculateVariablesFromExtensionFile4() {
    // Arrange
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(false);
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateVariablesFromExtensionFileResult =
        processVariablesInitiator.calculateVariablesFromExtensionFile(
            processDefinition, new HashMap<>());

    // Assert
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
    assertTrue(actualCalculateVariablesFromExtensionFileResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition,
   * Map)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName("Test calculateVariablesFromExtensionFile(ProcessDefinition, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateVariablesFromExtensionFile(ProcessDefinition, Map)"
  })
  void testCalculateVariablesFromExtensionFile5() throws ActivitiException {
    // Arrange
    HashMap<String, VariableDefinition> stringVariableDefinitionMap = new HashMap<>();
    stringVariableDefinitionMap.put("Key", new VariableDefinition("Type", "Value"));

    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenReturn(stringVariableDefinitionMap);
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(extension);
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(true);
    when(variableParsingService.parse(Mockito.<VariableDefinition>any()))
        .thenThrow(new ActivitiException("An error occurred"));
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesInitiator.calculateVariablesFromExtensionFile(
                processDefinition, new HashMap<>()));
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
    verify(extension).getProperties();
    verify(variableParsingService).parse(isA(VariableDefinition.class));
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition,
   * Map)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName("Test calculateVariablesFromExtensionFile(ProcessDefinition, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateVariablesFromExtensionFile(ProcessDefinition, Map)"
  })
  void testCalculateVariablesFromExtensionFile6() throws ActivitiException {
    // Arrange
    HashMap<String, VariableDefinition> stringVariableDefinitionMap = new HashMap<>();
    stringVariableDefinitionMap.put("Key", new VariableDefinition("Type", "Value"));

    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenReturn(stringVariableDefinitionMap);
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(extension);
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(true);
    when(variableParsingService.parse(Mockito.<VariableDefinition>any())).thenReturn("Parse");
    when(expressionResolver.containsExpression(Mockito.<Object>any()))
        .thenThrow(new ActivitiException("An error occurred"));
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesInitiator.calculateVariablesFromExtensionFile(
                processDefinition, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
    verify(extension).getProperties();
    verify(variableParsingService).parse(isA(VariableDefinition.class));
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition,
   * Map)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName("Test calculateVariablesFromExtensionFile(ProcessDefinition, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateVariablesFromExtensionFile(ProcessDefinition, Map)"
  })
  void testCalculateVariablesFromExtensionFile7() throws ActivitiException {
    // Arrange
    HashMap<String, VariableDefinition> stringVariableDefinitionMap = new HashMap<>();
    stringVariableDefinitionMap.put("Key", new VariableDefinition("Type", "Value"));

    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenReturn(stringVariableDefinitionMap);
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(extension);
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(true);
    when(variableParsingService.parse(Mockito.<VariableDefinition>any())).thenReturn("Parse");
    when(expressionResolver.resolveExpressionsMap(
            Mockito.<ExpressionEvaluator>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(true);
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateVariablesFromExtensionFileResult =
        processVariablesInitiator.calculateVariablesFromExtensionFile(
            processDefinition, new HashMap<>());

    // Assert
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(expressionResolver)
        .resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
    verify(extension).getProperties();
    verify(variableParsingService).parse(isA(VariableDefinition.class));
    assertTrue(actualCalculateVariablesFromExtensionFileResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition,
   * Map)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName("Test calculateVariablesFromExtensionFile(ProcessDefinition, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateVariablesFromExtensionFile(ProcessDefinition, Map)"
  })
  void testCalculateVariablesFromExtensionFile8() throws ActivitiException {
    // Arrange
    HashMap<String, VariableDefinition> stringVariableDefinitionMap = new HashMap<>();
    stringVariableDefinitionMap.put("Key", new VariableDefinition("Type", "Value"));

    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenReturn(stringVariableDefinitionMap);
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(extension);
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(true);
    when(variableParsingService.parse(Mockito.<VariableDefinition>any())).thenReturn("Parse");
    when(expressionResolver.resolveExpressionsMap(
            Mockito.<ExpressionEvaluator>any(), Mockito.<Map<String, Object>>any()))
        .thenThrow(new ActivitiException("An error occurred"));
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(true);
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesInitiator.calculateVariablesFromExtensionFile(
                processDefinition, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(expressionResolver)
        .resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
    verify(extension).getProperties();
    verify(variableParsingService).parse(isA(VariableDefinition.class));
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition,
   * Map)}.
   *
   * <p>Method under test: {@link
   * ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName("Test calculateVariablesFromExtensionFile(ProcessDefinition, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateVariablesFromExtensionFile(ProcessDefinition, Map)"
  })
  void testCalculateVariablesFromExtensionFile9() throws ActivitiException {
    // Arrange
    VariableDefinition variableDefinition = new VariableDefinition("Type", "Value");
    variableDefinition.setRequired(true);

    HashMap<String, VariableDefinition> stringVariableDefinitionMap = new HashMap<>();
    stringVariableDefinitionMap.put("Key", variableDefinition);

    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenReturn(stringVariableDefinitionMap);
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(extension);
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(true);
    when(variableParsingService.parse(Mockito.<VariableDefinition>any())).thenReturn("Parse");
    when(expressionResolver.resolveExpressionsMap(
            Mockito.<ExpressionEvaluator>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(true);
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesInitiator.calculateVariablesFromExtensionFile(
                processDefinition, new HashMap<>()));
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(expressionResolver)
        .resolveExpressionsMap(isA(ExpressionEvaluator.class), isA(Map.class));
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
    verify(extension).getProperties();
    verify(variableParsingService).parse(isA(VariableDefinition.class));
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition,
   * Map)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link
   *       VariableDefinition#VariableDefinition()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateVariablesFromExtensionFile(ProcessDefinition, Map); given HashMap() '42' is VariableDefinition()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateVariablesFromExtensionFile(ProcessDefinition, Map)"
  })
  void testCalculateVariablesFromExtensionFile_givenHashMap42IsVariableDefinition() {
    // Arrange
    HashMap<String, VariableDefinition> stringVariableDefinitionMap = new HashMap<>();
    stringVariableDefinitionMap.put("42", new VariableDefinition());
    stringVariableDefinitionMap.put("Key", new VariableDefinition());

    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenReturn(stringVariableDefinitionMap);
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(extension);
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(true);
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateVariablesFromExtensionFileResult =
        processVariablesInitiator.calculateVariablesFromExtensionFile(
            processDefinition, new HashMap<>());

    // Assert
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
    verify(extension).getProperties();
    assertTrue(actualCalculateVariablesFromExtensionFileResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition,
   * Map)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code Key} is {@link
   *       VariableDefinition#VariableDefinition()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateVariablesFromExtensionFile(ProcessDefinition, Map); given HashMap() 'Key' is VariableDefinition()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateVariablesFromExtensionFile(ProcessDefinition, Map)"
  })
  void testCalculateVariablesFromExtensionFile_givenHashMapKeyIsVariableDefinition() {
    // Arrange
    HashMap<String, VariableDefinition> stringVariableDefinitionMap = new HashMap<>();
    stringVariableDefinitionMap.put("Key", new VariableDefinition());

    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenReturn(stringVariableDefinitionMap);
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(extension);
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(true);
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateVariablesFromExtensionFileResult =
        processVariablesInitiator.calculateVariablesFromExtensionFile(
            processDefinition, new HashMap<>());

    // Assert
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
    verify(extension).getProperties();
    assertTrue(actualCalculateVariablesFromExtensionFileResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition,
   * Map)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateVariablesFromExtensionFile(ProcessDefinition, Map); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateVariablesFromExtensionFile(ProcessDefinition, Map)"
  })
  void testCalculateVariablesFromExtensionFile_thenReturnEmpty() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenReturn(new HashMap<>());
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(extension);
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(true);
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateVariablesFromExtensionFileResult =
        processVariablesInitiator.calculateVariablesFromExtensionFile(
            processDefinition, new HashMap<>());

    // Assert
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
    verify(extension).getProperties();
    assertTrue(actualCalculateVariablesFromExtensionFileResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition,
   * Map)}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName(
      "Test calculateVariablesFromExtensionFile(ProcessDefinition, Map); then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateVariablesFromExtensionFile(ProcessDefinition, Map)"
  })
  void testCalculateVariablesFromExtensionFile_thenReturnSizeIsOne() throws ActivitiException {
    // Arrange
    HashMap<String, VariableDefinition> stringVariableDefinitionMap = new HashMap<>();
    stringVariableDefinitionMap.put("Key", new VariableDefinition("Type", "Value"));

    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenReturn(stringVariableDefinitionMap);
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(extension);
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(true);
    when(variableParsingService.parse(Mockito.<VariableDefinition>any())).thenReturn("Parse");
    when(variableValidationService.validate(
            Mockito.<Object>any(), Mockito.<VariableDefinition>any()))
        .thenReturn(true);
    when(expressionResolver.containsExpression(Mockito.<Object>any())).thenReturn(false);
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateVariablesFromExtensionFileResult =
        processVariablesInitiator.calculateVariablesFromExtensionFile(
            processDefinition, new HashMap<>());

    // Assert
    verify(expressionResolver).containsExpression(isA(Object.class));
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
    verify(extension).getProperties();
    verify(variableParsingService).parse(isA(VariableDefinition.class));
    verify(variableValidationService).validate(isA(Object.class), isA(VariableDefinition.class));
    assertEquals(1, actualCalculateVariablesFromExtensionFileResult.size());
    assertEquals("Parse", actualCalculateVariablesFromExtensionFileResult.get(null));
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition,
   * FlowElement)}.
   *
   * <p>Method under test: {@link ProcessVariablesInitiator#calculateOutputVariables(Map,
   * ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName("Test calculateOutputVariables(Map, ProcessDefinition, FlowElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateOutputVariables(Map, ProcessDefinition, FlowElement)"
  })
  void testCalculateOutputVariables() {
    // Arrange
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenThrow(new ActivitiException("An error occurred"));
    HashMap<String, Object> variables = new HashMap<>();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesInitiator.calculateOutputVariables(
                variables, processDefinition, new AdhocSubProcess()));
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition,
   * FlowElement)}.
   *
   * <p>Method under test: {@link ProcessVariablesInitiator#calculateOutputVariables(Map,
   * ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName("Test calculateOutputVariables(Map, ProcessDefinition, FlowElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateOutputVariables(Map, ProcessDefinition, FlowElement)"
  })
  void testCalculateOutputVariables2() {
    // Arrange
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(new Extension());
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(true);
    when(variablesCalculator.calculateOutPutVariables(
            Mockito.<MappingExecutionContext>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());
    HashMap<String, Object> variables = new HashMap<>();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateOutputVariablesResult =
        processVariablesInitiator.calculateOutputVariables(
            variables, processDefinition, new AdhocSubProcess());

    // Assert
    verify(variablesCalculator)
        .calculateOutPutVariables(isA(MappingExecutionContext.class), isA(Map.class));
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService, atLeast(1)).hasExtensionsFor(isA(ProcessDefinition.class));
    assertTrue(actualCalculateOutputVariablesResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition,
   * FlowElement)}.
   *
   * <p>Method under test: {@link ProcessVariablesInitiator#calculateOutputVariables(Map,
   * ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName("Test calculateOutputVariables(Map, ProcessDefinition, FlowElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateOutputVariables(Map, ProcessDefinition, FlowElement)"
  })
  void testCalculateOutputVariables3() {
    // Arrange
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(false);
    HashMap<String, Object> variables = new HashMap<>();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateOutputVariablesResult =
        processVariablesInitiator.calculateOutputVariables(
            variables, processDefinition, new AdhocSubProcess());

    // Assert
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
    assertTrue(actualCalculateOutputVariablesResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition,
   * FlowElement)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link
   *       VariableDefinition#VariableDefinition()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessVariablesInitiator#calculateOutputVariables(Map,
   * ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName(
      "Test calculateOutputVariables(Map, ProcessDefinition, FlowElement); given HashMap() '42' is VariableDefinition()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateOutputVariables(Map, ProcessDefinition, FlowElement)"
  })
  void testCalculateOutputVariables_givenHashMap42IsVariableDefinition() {
    // Arrange
    HashMap<String, VariableDefinition> stringVariableDefinitionMap = new HashMap<>();
    stringVariableDefinitionMap.put("42", new VariableDefinition());
    stringVariableDefinitionMap.put("Key", new VariableDefinition());

    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenReturn(stringVariableDefinitionMap);
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(extension);
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(true);
    when(variablesCalculator.calculateOutPutVariables(
            Mockito.<MappingExecutionContext>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());
    HashMap<String, Object> variables = new HashMap<>();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateOutputVariablesResult =
        processVariablesInitiator.calculateOutputVariables(
            variables, processDefinition, new AdhocSubProcess());

    // Assert
    verify(variablesCalculator)
        .calculateOutPutVariables(isA(MappingExecutionContext.class), isA(Map.class));
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService, atLeast(1)).hasExtensionsFor(isA(ProcessDefinition.class));
    verify(extension).getProperties();
    assertTrue(actualCalculateOutputVariablesResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition,
   * FlowElement)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code Key} is {@code Value}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link ProcessVariablesInitiator#calculateOutputVariables(Map,
   * ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName(
      "Test calculateOutputVariables(Map, ProcessDefinition, FlowElement); given HashMap() 'Key' is 'Value'; then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateOutputVariables(Map, ProcessDefinition, FlowElement)"
  })
  void testCalculateOutputVariables_givenHashMapKeyIsValue_thenReturnSizeIsOne() {
    // Arrange
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(new Extension());
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(true);

    HashMap<String, Object> stringObjectMap = new HashMap<>();
    stringObjectMap.put("Key", "Value");
    when(variablesCalculator.calculateOutPutVariables(
            Mockito.<MappingExecutionContext>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(stringObjectMap);
    HashMap<String, Object> variables = new HashMap<>();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateOutputVariablesResult =
        processVariablesInitiator.calculateOutputVariables(
            variables, processDefinition, new AdhocSubProcess());

    // Assert
    verify(variablesCalculator)
        .calculateOutPutVariables(isA(MappingExecutionContext.class), isA(Map.class));
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService, atLeast(1)).hasExtensionsFor(isA(ProcessDefinition.class));
    assertEquals(1, actualCalculateOutputVariablesResult.size());
    assertEquals("Value", actualCalculateOutputVariablesResult.get("Key"));
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition,
   * FlowElement)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code Key} is {@link
   *       VariableDefinition#VariableDefinition()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessVariablesInitiator#calculateOutputVariables(Map,
   * ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName(
      "Test calculateOutputVariables(Map, ProcessDefinition, FlowElement); given HashMap() 'Key' is VariableDefinition()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateOutputVariables(Map, ProcessDefinition, FlowElement)"
  })
  void testCalculateOutputVariables_givenHashMapKeyIsVariableDefinition() {
    // Arrange
    HashMap<String, VariableDefinition> stringVariableDefinitionMap = new HashMap<>();
    stringVariableDefinitionMap.put("Key", new VariableDefinition());

    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenReturn(stringVariableDefinitionMap);
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(extension);
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(true);
    when(variablesCalculator.calculateOutPutVariables(
            Mockito.<MappingExecutionContext>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());
    HashMap<String, Object> variables = new HashMap<>();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateOutputVariablesResult =
        processVariablesInitiator.calculateOutputVariables(
            variables, processDefinition, new AdhocSubProcess());

    // Assert
    verify(variablesCalculator)
        .calculateOutPutVariables(isA(MappingExecutionContext.class), isA(Map.class));
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService, atLeast(1)).hasExtensionsFor(isA(ProcessDefinition.class));
    verify(extension).getProperties();
    assertTrue(actualCalculateOutputVariablesResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition,
   * FlowElement)}.
   *
   * <ul>
   *   <li>Then calls {@link Extension#getProperties()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessVariablesInitiator#calculateOutputVariables(Map,
   * ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName(
      "Test calculateOutputVariables(Map, ProcessDefinition, FlowElement); then calls getProperties()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateOutputVariables(Map, ProcessDefinition, FlowElement)"
  })
  void testCalculateOutputVariables_thenCallsGetProperties() {
    // Arrange
    Extension extension = mock(Extension.class);
    when(extension.getProperties()).thenReturn(new HashMap<>());
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(extension);
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(true);
    when(variablesCalculator.calculateOutPutVariables(
            Mockito.<MappingExecutionContext>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());
    HashMap<String, Object> variables = new HashMap<>();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateOutputVariablesResult =
        processVariablesInitiator.calculateOutputVariables(
            variables, processDefinition, new AdhocSubProcess());

    // Assert
    verify(variablesCalculator)
        .calculateOutPutVariables(isA(MappingExecutionContext.class), isA(Map.class));
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService, atLeast(1)).hasExtensionsFor(isA(ProcessDefinition.class));
    verify(extension).getProperties();
    assertTrue(actualCalculateOutputVariablesResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition,
   * FlowElement)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessVariablesInitiator#calculateOutputVariables(Map,
   * ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName(
      "Test calculateOutputVariables(Map, ProcessDefinition, FlowElement); then throw ActivitiException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map ProcessVariablesInitiator.calculateOutputVariables(Map, ProcessDefinition, FlowElement)"
  })
  void testCalculateOutputVariables_thenThrowActivitiException() {
    // Arrange
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenReturn(true);
    when(variablesCalculator.calculateOutPutVariables(
            Mockito.<MappingExecutionContext>any(), Mockito.<Map<String, Object>>any()))
        .thenThrow(new ActivitiException("An error occurred"));
    HashMap<String, Object> variables = new HashMap<>();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            processVariablesInitiator.calculateOutputVariables(
                variables, processDefinition, new AdhocSubProcess()));
    verify(variablesCalculator)
        .calculateOutPutVariables(isA(MappingExecutionContext.class), isA(Map.class));
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
  }
}
