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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.bpmn.behavior.MappingExecutionContext;
import org.activiti.engine.impl.bpmn.behavior.VariablesCalculator;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.interceptor.DelegateInterceptor;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.runtime.api.impl.ExpressionResolver;
import org.activiti.runtime.api.impl.ExtensionsVariablesMappingProvider;
import org.activiti.spring.process.model.Extension;
import org.activiti.spring.process.model.ProcessExtensionModel;
import org.activiti.spring.process.model.ProcessVariablesMapping;
import org.activiti.spring.process.model.ProcessVariablesMapping.MappingType;
import org.activiti.spring.process.variable.VariableParsingService;
import org.activiti.spring.process.variable.VariableValidationService;
import org.activiti.spring.resources.DeploymentResourceLoader;
import org.activiti.spring.resources.ResourceReader;
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
   * Test {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}.
   * <p>
   * Method under test: {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName("Test calculateVariablesFromExtensionFile(ProcessDefinition, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProcessVariablesInitiator.calculateVariablesFromExtensionFile(ProcessDefinition, Map)"})
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
   * Test {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}.
   * <p>
   * Method under test: {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName("Test calculateVariablesFromExtensionFile(ProcessDefinition, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProcessVariablesInitiator.calculateVariablesFromExtensionFile(ProcessDefinition, Map)"})
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
   * Test {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}.
   * <p>
   * Method under test: {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName("Test calculateVariablesFromExtensionFile(ProcessDefinition, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProcessVariablesInitiator.calculateVariablesFromExtensionFile(ProcessDefinition, Map)"})
  void testCalculateVariablesFromExtensionFile3() {
    // Arrange
    ProcessExtensionModel processExtensionModel = new ProcessExtensionModel();
    processExtensionModel.setExtensions(new HashMap<>());
    processExtensionModel.setId("42");

    ArrayList<ProcessExtensionModel> processExtensionModelList = new ArrayList<>();
    processExtensionModelList.add(processExtensionModel);
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader = mock(DeploymentResourceLoader.class);
    when(processExtensionLoader.loadResourcesForDeployment(Mockito.<String>any(),
        Mockito.<ResourceReader<ProcessExtensionModel>>any())).thenReturn(processExtensionModelList);
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionService processExtensionService = new ProcessExtensionService(processExtensionLoader,
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>()));

    VariableParsingService variableParsingService = new VariableParsingService(new HashMap<>());
    VariableValidationService variableValidationService = new VariableValidationService(new HashMap<>());
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader2 = new DeploymentResourceLoader<>();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionService processExtensionService2 = new ProcessExtensionService(processExtensionLoader2,
        new ProcessExtensionResourceReader(objectMapper2, new HashMap<>()));

    ExpressionManager expressionManager = new ExpressionManager();
    ExpressionResolver expressionResolver = new ExpressionResolver(expressionManager,
        JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class));

    ExtensionsVariablesMappingProvider variablesCalculator = new ExtensionsVariablesMappingProvider(
        processExtensionService2, expressionResolver, new VariableParsingService(new HashMap<>()));

    ExpressionManager expressionManager2 = new ExpressionManager();
    ProcessVariablesInitiator processVariablesInitiator = new ProcessVariablesInitiator(processExtensionService,
        variableParsingService, variableValidationService, variablesCalculator, new ExpressionResolver(
            expressionManager2, JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class)));
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateVariablesFromExtensionFileResult = processVariablesInitiator
        .calculateVariablesFromExtensionFile(processDefinition, new HashMap<>());

    // Assert
    verify(processExtensionLoader).loadResourcesForDeployment(isNull(), isA(ResourceReader.class));
    assertTrue(actualCalculateVariablesFromExtensionFileResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}.
   * <ul>
   *   <li>Then calls {@link DeploymentResourceLoader#loadResourcesForDeployment(String, ResourceReader)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName("Test calculateVariablesFromExtensionFile(ProcessDefinition, Map); then calls loadResourcesForDeployment(String, ResourceReader)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProcessVariablesInitiator.calculateVariablesFromExtensionFile(ProcessDefinition, Map)"})
  void testCalculateVariablesFromExtensionFile_thenCallsLoadResourcesForDeployment() {
    // Arrange
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader = mock(DeploymentResourceLoader.class);
    when(processExtensionLoader.loadResourcesForDeployment(Mockito.<String>any(),
        Mockito.<ResourceReader<ProcessExtensionModel>>any())).thenReturn(new ArrayList<>());
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionService processExtensionService = new ProcessExtensionService(processExtensionLoader,
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>()));

    VariableParsingService variableParsingService = new VariableParsingService(new HashMap<>());
    VariableValidationService variableValidationService = new VariableValidationService(new HashMap<>());
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader2 = new DeploymentResourceLoader<>();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionService processExtensionService2 = new ProcessExtensionService(processExtensionLoader2,
        new ProcessExtensionResourceReader(objectMapper2, new HashMap<>()));

    ExpressionManager expressionManager = new ExpressionManager();
    ExpressionResolver expressionResolver = new ExpressionResolver(expressionManager,
        JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class));

    ExtensionsVariablesMappingProvider variablesCalculator = new ExtensionsVariablesMappingProvider(
        processExtensionService2, expressionResolver, new VariableParsingService(new HashMap<>()));

    ExpressionManager expressionManager2 = new ExpressionManager();
    ProcessVariablesInitiator processVariablesInitiator = new ProcessVariablesInitiator(processExtensionService,
        variableParsingService, variableValidationService, variablesCalculator, new ExpressionResolver(
            expressionManager2, JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class)));
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateVariablesFromExtensionFileResult = processVariablesInitiator
        .calculateVariablesFromExtensionFile(processDefinition, new HashMap<>());

    // Assert
    verify(processExtensionLoader).loadResourcesForDeployment(isNull(), isA(ResourceReader.class));
    assertTrue(actualCalculateVariablesFromExtensionFileResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariablesInitiator#calculateVariablesFromExtensionFile(ProcessDefinition, Map)}
   */
  @Test
  @DisplayName("Test calculateVariablesFromExtensionFile(ProcessDefinition, Map); then throw ActivitiException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProcessVariablesInitiator.calculateVariablesFromExtensionFile(ProcessDefinition, Map)"})
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
   * Test {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}.
   * <p>
   * Method under test: {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName("Test calculateOutputVariables(Map, ProcessDefinition, FlowElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProcessVariablesInitiator.calculateOutputVariables(Map, ProcessDefinition, FlowElement)"})
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
   * Test {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}.
   * <p>
   * Method under test: {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName("Test calculateOutputVariables(Map, ProcessDefinition, FlowElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProcessVariablesInitiator.calculateOutputVariables(Map, ProcessDefinition, FlowElement)"})
  void testCalculateOutputVariables2() {
    // Arrange
    ProcessExtensionService processExtensionService = mock(ProcessExtensionService.class);
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any())).thenReturn(new Extension());
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any())).thenReturn(true);
    ProcessExtensionService processExtensionService2 = mock(ProcessExtensionService.class);
    when(processExtensionService2.getExtensionsForId(Mockito.<String>any())).thenReturn(new Extension());
    ExpressionManager expressionManager = new ExpressionManager();
    ExpressionResolver expressionResolver = new ExpressionResolver(expressionManager,
        JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class));

    ExtensionsVariablesMappingProvider variablesCalculator = new ExtensionsVariablesMappingProvider(
        processExtensionService2, expressionResolver, new VariableParsingService(new HashMap<>()));

    VariableParsingService variableParsingService = new VariableParsingService(new HashMap<>());
    VariableValidationService variableValidationService = new VariableValidationService(new HashMap<>());
    ExpressionManager expressionManager2 = new ExpressionManager();
    ProcessVariablesInitiator processVariablesInitiator = new ProcessVariablesInitiator(processExtensionService,
        variableParsingService, variableValidationService, variablesCalculator, new ExpressionResolver(
            expressionManager2, JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class)));
    HashMap<String, Object> variables = new HashMap<>();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateOutputVariablesResult = processVariablesInitiator
        .calculateOutputVariables(variables, processDefinition, new AdhocSubProcess());

    // Assert
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService2).getExtensionsForId(isNull());
    verify(processExtensionService, atLeast(1)).hasExtensionsFor(isA(ProcessDefinition.class));
    assertTrue(actualCalculateOutputVariablesResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}.
   * <ul>
   *   <li>Given {@link Extension} {@link Extension#shouldMapAllOutputs(String)} return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName("Test calculateOutputVariables(Map, ProcessDefinition, FlowElement); given Extension shouldMapAllOutputs(String) return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProcessVariablesInitiator.calculateOutputVariables(Map, ProcessDefinition, FlowElement)"})
  void testCalculateOutputVariables_givenExtensionShouldMapAllOutputsReturnTrue() {
    // Arrange
    ProcessExtensionService processExtensionService = mock(ProcessExtensionService.class);
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any())).thenReturn(new Extension());
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any())).thenReturn(true);
    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(true);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    ProcessExtensionService processExtensionService2 = mock(ProcessExtensionService.class);
    when(processExtensionService2.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    ExpressionManager expressionManager = new ExpressionManager();
    ExpressionResolver expressionResolver = new ExpressionResolver(expressionManager,
        JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class));

    ExtensionsVariablesMappingProvider variablesCalculator = new ExtensionsVariablesMappingProvider(
        processExtensionService2, expressionResolver, new VariableParsingService(new HashMap<>()));

    VariableParsingService variableParsingService = new VariableParsingService(new HashMap<>());
    VariableValidationService variableValidationService = new VariableValidationService(new HashMap<>());
    ExpressionManager expressionManager2 = new ExpressionManager();
    ProcessVariablesInitiator processVariablesInitiator = new ProcessVariablesInitiator(processExtensionService,
        variableParsingService, variableValidationService, variablesCalculator, new ExpressionResolver(
            expressionManager2, JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class)));
    HashMap<String, Object> variables = new HashMap<>();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateOutputVariablesResult = processVariablesInitiator
        .calculateOutputVariables(variables, processDefinition, new AdhocSubProcess());

    // Assert
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService2).getExtensionsForId(isNull());
    verify(processExtensionService, atLeast(1)).hasExtensionsFor(isA(ProcessDefinition.class));
    verify(extension).hasMapping(isNull());
    verify(extension).shouldMapAllOutputs(isNull());
    assertTrue(actualCalculateOutputVariablesResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code 42}.</li>
   *   <li>Then return {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName("Test calculateOutputVariables(Map, ProcessDefinition, FlowElement); given 'foo'; when HashMap() 'foo' is '42'; then return HashMap()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProcessVariablesInitiator.calculateOutputVariables(Map, ProcessDefinition, FlowElement)"})
  void testCalculateOutputVariables_givenFoo_whenHashMapFooIs42_thenReturnHashMap() {
    // Arrange
    ProcessExtensionService processExtensionService = mock(ProcessExtensionService.class);
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any())).thenReturn(new Extension());
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any())).thenReturn(true);
    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(true);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    ProcessExtensionService processExtensionService2 = mock(ProcessExtensionService.class);
    when(processExtensionService2.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    ExpressionManager expressionManager = new ExpressionManager();
    ExpressionResolver expressionResolver = new ExpressionResolver(expressionManager,
        JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class));

    ExtensionsVariablesMappingProvider variablesCalculator = new ExtensionsVariablesMappingProvider(
        processExtensionService2, expressionResolver, new VariableParsingService(new HashMap<>()));

    VariableParsingService variableParsingService = new VariableParsingService(new HashMap<>());
    VariableValidationService variableValidationService = new VariableValidationService(new HashMap<>());
    ExpressionManager expressionManager2 = new ExpressionManager();
    ProcessVariablesInitiator processVariablesInitiator = new ProcessVariablesInitiator(processExtensionService,
        variableParsingService, variableValidationService, variablesCalculator, new ExpressionResolver(
            expressionManager2, JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class)));

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", "42");
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateOutputVariablesResult = processVariablesInitiator
        .calculateOutputVariables(variables, processDefinition, new AdhocSubProcess());

    // Assert
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService2).getExtensionsForId(isNull());
    verify(processExtensionService, atLeast(1)).hasExtensionsFor(isA(ProcessDefinition.class));
    verify(extension).hasMapping(isNull());
    verify(extension).shouldMapAllOutputs(isNull());
    assertEquals(variables, actualCalculateOutputVariablesResult);
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@code 42}.</li>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName("Test calculateOutputVariables(Map, ProcessDefinition, FlowElement); given HashMap() 'foo' is '42'; when HashMap(); then return size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProcessVariablesInitiator.calculateOutputVariables(Map, ProcessDefinition, FlowElement)"})
  void testCalculateOutputVariables_givenHashMapFooIs42_whenHashMap_thenReturnSizeIsOne() {
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
   * Test {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}.
   * <ul>
   *   <li>Given {@link ProcessExtensionModel} (default constructor) Extensions is {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName("Test calculateOutputVariables(Map, ProcessDefinition, FlowElement); given ProcessExtensionModel (default constructor) Extensions is HashMap()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProcessVariablesInitiator.calculateOutputVariables(Map, ProcessDefinition, FlowElement)"})
  void testCalculateOutputVariables_givenProcessExtensionModelExtensionsIsHashMap() {
    // Arrange
    ProcessExtensionModel processExtensionModel = new ProcessExtensionModel();
    processExtensionModel.setExtensions(new HashMap<>());
    processExtensionModel.setId("42");

    ArrayList<ProcessExtensionModel> processExtensionModelList = new ArrayList<>();
    processExtensionModelList.add(processExtensionModel);
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader = mock(DeploymentResourceLoader.class);
    when(processExtensionLoader.loadResourcesForDeployment(Mockito.<String>any(),
        Mockito.<ResourceReader<ProcessExtensionModel>>any())).thenReturn(processExtensionModelList);
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionService processExtensionService = new ProcessExtensionService(processExtensionLoader,
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>()));

    VariableParsingService variableParsingService = new VariableParsingService(new HashMap<>());
    VariableValidationService variableValidationService = new VariableValidationService(new HashMap<>());
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader2 = new DeploymentResourceLoader<>();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionService processExtensionService2 = new ProcessExtensionService(processExtensionLoader2,
        new ProcessExtensionResourceReader(objectMapper2, new HashMap<>()));

    ExpressionManager expressionManager = new ExpressionManager();
    ExpressionResolver expressionResolver = new ExpressionResolver(expressionManager,
        JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class));

    ExtensionsVariablesMappingProvider variablesCalculator = new ExtensionsVariablesMappingProvider(
        processExtensionService2, expressionResolver, new VariableParsingService(new HashMap<>()));

    ExpressionManager expressionManager2 = new ExpressionManager();
    ProcessVariablesInitiator processVariablesInitiator = new ProcessVariablesInitiator(processExtensionService,
        variableParsingService, variableValidationService, variablesCalculator, new ExpressionResolver(
            expressionManager2, JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class)));
    HashMap<String, Object> variables = new HashMap<>();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateOutputVariablesResult = processVariablesInitiator
        .calculateOutputVariables(variables, processDefinition, new AdhocSubProcess());

    // Assert
    verify(processExtensionLoader).loadResourcesForDeployment(isNull(), isA(ResourceReader.class));
    assertTrue(actualCalculateOutputVariablesResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}.
   * <ul>
   *   <li>Then calls {@link VariablesCalculator#calculateOutPutVariables(MappingExecutionContext, Map)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName("Test calculateOutputVariables(Map, ProcessDefinition, FlowElement); then calls calculateOutPutVariables(MappingExecutionContext, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProcessVariablesInitiator.calculateOutputVariables(Map, ProcessDefinition, FlowElement)"})
  void testCalculateOutputVariables_thenCallsCalculateOutPutVariables() {
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
   * Test {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}.
   * <ul>
   *   <li>Then calls {@link Extension#getMappingForFlowElement(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName("Test calculateOutputVariables(Map, ProcessDefinition, FlowElement); then calls getMappingForFlowElement(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProcessVariablesInitiator.calculateOutputVariables(Map, ProcessDefinition, FlowElement)"})
  void testCalculateOutputVariables_thenCallsGetMappingForFlowElement() {
    // Arrange
    ProcessExtensionService processExtensionService = mock(ProcessExtensionService.class);
    when(processExtensionService.getExtensionsFor(Mockito.<ProcessDefinition>any())).thenReturn(new Extension());
    when(processExtensionService.hasExtensionsFor(Mockito.<ProcessDefinition>any())).thenReturn(true);

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(new HashMap<>());
    processVariablesMapping.setMappingType(MappingType.MAP_ALL);
    processVariablesMapping.setOutputs(new HashMap<>());
    Extension extension = mock(Extension.class);
    when(extension.shouldMapAllOutputs(Mockito.<String>any())).thenReturn(false);
    when(extension.getMappingForFlowElement(Mockito.<String>any())).thenReturn(processVariablesMapping);
    when(extension.hasMapping(Mockito.<String>any())).thenReturn(true);
    ProcessExtensionService processExtensionService2 = mock(ProcessExtensionService.class);
    when(processExtensionService2.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);
    ExpressionManager expressionManager = new ExpressionManager();
    ExpressionResolver expressionResolver = new ExpressionResolver(expressionManager,
        JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class));

    ExtensionsVariablesMappingProvider variablesCalculator = new ExtensionsVariablesMappingProvider(
        processExtensionService2, expressionResolver, new VariableParsingService(new HashMap<>()));

    VariableParsingService variableParsingService = new VariableParsingService(new HashMap<>());
    VariableValidationService variableValidationService = new VariableValidationService(new HashMap<>());
    ExpressionManager expressionManager2 = new ExpressionManager();
    ProcessVariablesInitiator processVariablesInitiator = new ProcessVariablesInitiator(processExtensionService,
        variableParsingService, variableValidationService, variablesCalculator, new ExpressionResolver(
            expressionManager2, JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class)));
    HashMap<String, Object> variables = new HashMap<>();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateOutputVariablesResult = processVariablesInitiator
        .calculateOutputVariables(variables, processDefinition, new AdhocSubProcess());

    // Assert
    verify(processExtensionService).getExtensionsFor(isA(ProcessDefinition.class));
    verify(processExtensionService2).getExtensionsForId(isNull());
    verify(processExtensionService, atLeast(1)).hasExtensionsFor(isA(ProcessDefinition.class));
    verify(extension).getMappingForFlowElement(isNull());
    verify(extension).hasMapping(isNull());
    verify(extension).shouldMapAllOutputs(isNull());
    assertTrue(actualCalculateOutputVariablesResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}.
   * <ul>
   *   <li>Then calls {@link DeploymentResourceLoader#loadResourcesForDeployment(String, ResourceReader)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName("Test calculateOutputVariables(Map, ProcessDefinition, FlowElement); then calls loadResourcesForDeployment(String, ResourceReader)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProcessVariablesInitiator.calculateOutputVariables(Map, ProcessDefinition, FlowElement)"})
  void testCalculateOutputVariables_thenCallsLoadResourcesForDeployment() {
    // Arrange
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader = mock(DeploymentResourceLoader.class);
    when(processExtensionLoader.loadResourcesForDeployment(Mockito.<String>any(),
        Mockito.<ResourceReader<ProcessExtensionModel>>any())).thenReturn(new ArrayList<>());
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionService processExtensionService = new ProcessExtensionService(processExtensionLoader,
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>()));

    VariableParsingService variableParsingService = new VariableParsingService(new HashMap<>());
    VariableValidationService variableValidationService = new VariableValidationService(new HashMap<>());
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader2 = new DeploymentResourceLoader<>();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionService processExtensionService2 = new ProcessExtensionService(processExtensionLoader2,
        new ProcessExtensionResourceReader(objectMapper2, new HashMap<>()));

    ExpressionManager expressionManager = new ExpressionManager();
    ExpressionResolver expressionResolver = new ExpressionResolver(expressionManager,
        JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class));

    ExtensionsVariablesMappingProvider variablesCalculator = new ExtensionsVariablesMappingProvider(
        processExtensionService2, expressionResolver, new VariableParsingService(new HashMap<>()));

    ExpressionManager expressionManager2 = new ExpressionManager();
    ProcessVariablesInitiator processVariablesInitiator = new ProcessVariablesInitiator(processExtensionService,
        variableParsingService, variableValidationService, variablesCalculator, new ExpressionResolver(
            expressionManager2, JsonMapper.builder().findAndAddModules().build(), mock(DelegateInterceptor.class)));
    HashMap<String, Object> variables = new HashMap<>();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    Map<String, Object> actualCalculateOutputVariablesResult = processVariablesInitiator
        .calculateOutputVariables(variables, processDefinition, new AdhocSubProcess());

    // Assert
    verify(processExtensionLoader).loadResourcesForDeployment(isNull(), isA(ResourceReader.class));
    assertTrue(actualCalculateOutputVariablesResult.isEmpty());
  }

  /**
   * Test {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariablesInitiator#calculateOutputVariables(Map, ProcessDefinition, FlowElement)}
   */
  @Test
  @DisplayName("Test calculateOutputVariables(Map, ProcessDefinition, FlowElement); then throw ActivitiException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProcessVariablesInitiator.calculateOutputVariables(Map, ProcessDefinition, FlowElement)"})
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
