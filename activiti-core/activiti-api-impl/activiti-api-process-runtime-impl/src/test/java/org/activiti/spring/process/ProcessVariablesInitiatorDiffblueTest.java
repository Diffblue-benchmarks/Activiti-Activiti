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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.bpmn.behavior.MappingExecutionContext;
import org.activiti.engine.impl.bpmn.behavior.VariablesCalculator;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.runtime.api.impl.ExpressionResolver;
import org.activiti.spring.process.model.Extension;
import org.activiti.spring.process.variable.VariableParsingService;
import org.activiti.spring.process.variable.VariableValidationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProcessVariablesInitiator.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProcessVariablesInitiatorDiffblueTest {
  @MockBean
  private ExpressionResolver expressionResolver;

  @MockBean
  private ProcessExtensionService processExtensionService;

  @Autowired
  private ProcessVariablesInitiator processVariablesInitiator;

  @MockBean
  private VariableParsingService variableParsingService;

  @MockBean
  private VariableValidationService variableValidationService;

  @MockBean
  private VariablesCalculator variablesCalculator;

  /**
   * Test
   * {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}.
   * <p>
   * Method under test:
   * {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName("Test calculateVariablesFromExtensionFile(ProcessDefinition, Map)")
  void testCalculateVariablesFromExtensionFile() {
    // Arrange
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any())).thenReturn(new Extension());
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any())).thenReturn(true);
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateVariablesFromExtensionFileResult = processVariablesInitiator
        .calculateVariablesFromExtensionFile(processDefinition, new HashMap<>());

    // Assert
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
    assertTrue(actualCalculateVariablesFromExtensionFileResult.isEmpty());
  }

  /**
   * Test
   * {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}.
   * <p>
   * Method under test:
   * {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName("Test calculateVariablesFromExtensionFile(ProcessDefinition, Map)")
  void testCalculateVariablesFromExtensionFile2() {
    // Arrange
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any())).thenReturn(false);
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateVariablesFromExtensionFileResult = processVariablesInitiator
        .calculateVariablesFromExtensionFile(processDefinition, new HashMap<>());

    // Assert
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
    assertTrue(actualCalculateVariablesFromExtensionFileResult.isEmpty());
  }

  /**
   * Test
   * {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName("Test calculateVariablesFromExtensionFile(ProcessDefinition, Map); then throw ActivitiException")
  void testCalculateVariablesFromExtensionFile_thenThrowActivitiException() {
    // Arrange
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any()))
        .thenThrow(new ActivitiException("An error occurred"));
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any())).thenReturn(true);
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> processVariablesInitiator.calculateVariablesFromExtensionFile(processDefinition, new HashMap<>()));
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
  }

  /**
   * Test
   * {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}.
   * <p>
   * Method under test:
   * {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName("Test calculateOutputVariables(Map, ProcessDefinition, FlowElement)")
  void testCalculateOutputVariables() {
    // Arrange
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any())).thenReturn(false);
    HashMap<String, Object> variables = new HashMap<>();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateOutputVariablesResult = processVariablesInitiator
        .calculateOutputVariables(variables, processDefinition, new AdhocSubProcess());

    // Assert
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
    assertTrue(actualCalculateOutputVariablesResult.isEmpty());
  }

  /**
   * Test
   * {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@code 42}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName("Test calculateOutputVariables(Map, ProcessDefinition, FlowElement); given HashMap() 'foo' is '42'; then return size is one")
  void testCalculateOutputVariables_givenHashMapFooIs42_thenReturnSizeIsOne() {
    // Arrange
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any())).thenReturn(new Extension());
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any())).thenReturn(true);

    HashMap<String, Object> stringObjectMap = new HashMap<>();
    stringObjectMap.put("foo", "42");
    when(variablesCalculator.calculateOutPutVariables(Mockito.<MappingExecutionContext>any(),
        Mockito.<Map<String, Object>>any())).thenReturn(stringObjectMap);
    HashMap<String, Object> variables = new HashMap<>();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateOutputVariablesResult = processVariablesInitiator
        .calculateOutputVariables(variables, processDefinition, new AdhocSubProcess());

    // Assert
    verify(variablesCalculator).calculateOutPutVariables(isA(MappingExecutionContext.class), isA(Map.class));
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService, atLeast(1)).hasExtensionsFor(isA(ProcessDefinition.class));
    assertEquals(1, actualCalculateOutputVariablesResult.size());
    assertEquals("42", actualCalculateOutputVariablesResult.get("foo"));
  }

  /**
   * Test
   * {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName("Test calculateOutputVariables(Map, ProcessDefinition, FlowElement); then return Empty")
  void testCalculateOutputVariables_thenReturnEmpty() {
    // Arrange
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any())).thenReturn(new Extension());
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any())).thenReturn(true);
    when(variablesCalculator.calculateOutPutVariables(Mockito.<MappingExecutionContext>any(),
        Mockito.<Map<String, Object>>any())).thenReturn(new HashMap<>());
    HashMap<String, Object> variables = new HashMap<>();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateOutputVariablesResult = processVariablesInitiator
        .calculateOutputVariables(variables, processDefinition, new AdhocSubProcess());

    // Assert
    verify(variablesCalculator).calculateOutPutVariables(isA(MappingExecutionContext.class), isA(Map.class));
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService, atLeast(1)).hasExtensionsFor(isA(ProcessDefinition.class));
    assertTrue(actualCalculateOutputVariablesResult.isEmpty());
  }

  /**
   * Test
   * {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName("Test calculateOutputVariables(Map, ProcessDefinition, FlowElement); then throw ActivitiException")
  void testCalculateOutputVariables_thenThrowActivitiException() {
    // Arrange
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any())).thenReturn(true);
    when(variablesCalculator.calculateOutPutVariables(Mockito.<MappingExecutionContext>any(),
        Mockito.<Map<String, Object>>any())).thenThrow(new ActivitiException("An error occurred"));
    HashMap<String, Object> variables = new HashMap<>();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> processVariablesInitiator.calculateOutputVariables(variables, processDefinition, new AdhocSubProcess()));
    verify(variablesCalculator).calculateOutPutVariables(isA(MappingExecutionContext.class), isA(Map.class));
    verify(processExtensionService).hasExtensionsFor(isA(ProcessDefinition.class));
  }
}
