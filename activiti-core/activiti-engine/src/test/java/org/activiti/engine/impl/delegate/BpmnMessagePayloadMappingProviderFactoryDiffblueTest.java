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
package org.activiti.engine.impl.delegate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.core.el.ActivitiElContext;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.mockito.Mockito;

public class BpmnMessagePayloadMappingProviderFactoryDiffblueTest {
  /**
   * Method under test:
   * {@link BpmnMessagePayloadMappingProviderFactory#create(Event, MessageEventDefinition, ExpressionManager)}
   */
  @Test
  public void testCreate() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory = new BpmnMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();

    // Act
    MessagePayloadMappingProvider actualCreateResult = bpmnMessagePayloadMappingProviderFactory.create(bpmnEvent,
        messageEventDefinition, new ExpressionManager());
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    Optional<Map<String, Object>> actualMessagePayload = actualCreateResult.getMessagePayload(execution);

    // Assert
    assertTrue(actualCreateResult instanceof BpmnMessagePayloadMappingProvider);
    Optional<Map<String, Object>> messagePayload = actualCreateResult.getMessagePayload(null);
    assertFalse(messagePayload.isPresent());
    assertTrue(execution.getEventSubscriptions().isEmpty());
    assertTrue(execution.getExecutions().isEmpty());
    assertTrue(execution.getIdentityLinks().isEmpty());
    assertTrue(execution.getJobs().isEmpty());
    assertTrue(execution.getTasks().isEmpty());
    assertTrue(execution.getTimerJobs().isEmpty());
    assertTrue(execution.getProcessVariables().isEmpty());
    assertTrue(execution.getTransientVariables().isEmpty());
    assertTrue(execution.getTransientVariablesLocal().isEmpty());
    assertTrue(execution.getUsedVariablesCache().isEmpty());
    assertTrue(execution.getVariableInstanceEntities().isEmpty());
    assertTrue(execution.getVariableInstances().isEmpty());
    assertTrue(execution.getVariableInstancesLocal().isEmpty());
    assertTrue(execution.getVariables().isEmpty());
    assertTrue(execution.getVariablesLocal().isEmpty());
    assertSame(messagePayload, actualMessagePayload);
  }

  /**
   * Method under test:
   * {@link BpmnMessagePayloadMappingProviderFactory#create(Event, MessageEventDefinition, ExpressionManager)}
   */
  @Test
  public void testCreate2() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory = new BpmnMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression(null);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    // Act
    MessagePayloadMappingProvider actualCreateResult = bpmnMessagePayloadMappingProviderFactory.create(bpmnEvent,
        messageEventDefinition, expressionManager);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    Optional<Map<String, Object>> actualMessagePayload = actualCreateResult.getMessagePayload(execution);

    // Assert
    assertTrue(actualCreateResult instanceof BpmnMessagePayloadMappingProvider);
    Optional<Map<String, Object>> messagePayload = actualCreateResult.getMessagePayload(null);
    Map<String, Object> getResult = messagePayload.get();
    assertEquals(1, getResult.size());
    assertNull(getResult.get(null));
    assertTrue(execution.getEventSubscriptions().isEmpty());
    assertTrue(execution.getExecutions().isEmpty());
    assertTrue(execution.getIdentityLinks().isEmpty());
    assertTrue(execution.getJobs().isEmpty());
    assertTrue(execution.getTasks().isEmpty());
    assertTrue(execution.getTimerJobs().isEmpty());
    assertTrue(execution.getProcessVariables().isEmpty());
    assertTrue(execution.getTransientVariables().isEmpty());
    assertTrue(execution.getTransientVariablesLocal().isEmpty());
    assertTrue(execution.getUsedVariablesCache().isEmpty());
    assertTrue(execution.getVariableInstanceEntities().isEmpty());
    assertTrue(execution.getVariableInstances().isEmpty());
    assertTrue(execution.getVariableInstancesLocal().isEmpty());
    assertTrue(execution.getVariables().isEmpty());
    assertTrue(execution.getVariablesLocal().isEmpty());
    assertTrue(messagePayload.isPresent());
    assertEquals(messagePayload, actualMessagePayload);
  }

  /**
   * Method under test:
   * {@link BpmnMessagePayloadMappingProviderFactory#create(Event, MessageEventDefinition, ExpressionManager)}
   */
  @Test
  public void testCreate3() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory = new BpmnMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression(null);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

    // Act
    MessagePayloadMappingProvider actualCreateResult = bpmnMessagePayloadMappingProviderFactory.create(bpmnEvent,
        messageEventDefinition, expressionManager);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    Optional<Map<String, Object>> actualMessagePayload = actualCreateResult.getMessagePayload(execution);

    // Assert
    assertTrue(actualCreateResult instanceof BpmnMessagePayloadMappingProvider);
    Optional<Map<String, Object>> messagePayload = actualCreateResult.getMessagePayload(null);
    Map<String, Object> getResult = messagePayload.get();
    assertEquals(1, getResult.size());
    assertNull(getResult.get(null));
    assertTrue(execution.getEventSubscriptions().isEmpty());
    assertTrue(execution.getExecutions().isEmpty());
    assertTrue(execution.getIdentityLinks().isEmpty());
    assertTrue(execution.getJobs().isEmpty());
    assertTrue(execution.getTasks().isEmpty());
    assertTrue(execution.getTimerJobs().isEmpty());
    assertTrue(execution.getProcessVariables().isEmpty());
    assertTrue(execution.getTransientVariables().isEmpty());
    assertTrue(execution.getTransientVariablesLocal().isEmpty());
    assertTrue(execution.getUsedVariablesCache().isEmpty());
    assertTrue(execution.getVariableInstanceEntities().isEmpty());
    assertTrue(execution.getVariableInstances().isEmpty());
    assertTrue(execution.getVariableInstancesLocal().isEmpty());
    assertTrue(execution.getVariables().isEmpty());
    assertTrue(execution.getVariablesLocal().isEmpty());
    assertTrue(messagePayload.isPresent());
    assertEquals(messagePayload, actualMessagePayload);
  }

  /**
   * Method under test:
   * {@link BpmnMessagePayloadMappingProviderFactory#create(Event, MessageEventDefinition, ExpressionManager)}
   */
  @Test
  public void testCreate4() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory = new BpmnMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression(null);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(mock(CustomFunctionProvider.class));

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    // Act
    MessagePayloadMappingProvider actualCreateResult = bpmnMessagePayloadMappingProviderFactory.create(bpmnEvent,
        messageEventDefinition, expressionManager);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    Optional<Map<String, Object>> actualMessagePayload = actualCreateResult.getMessagePayload(execution);

    // Assert
    assertTrue(actualCreateResult instanceof BpmnMessagePayloadMappingProvider);
    Optional<Map<String, Object>> messagePayload = actualCreateResult.getMessagePayload(null);
    Map<String, Object> getResult = messagePayload.get();
    assertEquals(1, getResult.size());
    assertNull(getResult.get(null));
    assertTrue(execution.getEventSubscriptions().isEmpty());
    assertTrue(execution.getExecutions().isEmpty());
    assertTrue(execution.getIdentityLinks().isEmpty());
    assertTrue(execution.getJobs().isEmpty());
    assertTrue(execution.getTasks().isEmpty());
    assertTrue(execution.getTimerJobs().isEmpty());
    assertTrue(execution.getProcessVariables().isEmpty());
    assertTrue(execution.getTransientVariables().isEmpty());
    assertTrue(execution.getTransientVariablesLocal().isEmpty());
    assertTrue(execution.getUsedVariablesCache().isEmpty());
    assertTrue(execution.getVariableInstanceEntities().isEmpty());
    assertTrue(execution.getVariableInstances().isEmpty());
    assertTrue(execution.getVariableInstancesLocal().isEmpty());
    assertTrue(execution.getVariables().isEmpty());
    assertTrue(execution.getVariablesLocal().isEmpty());
    assertTrue(messagePayload.isPresent());
    assertEquals(messagePayload, actualMessagePayload);
  }

  /**
   * Method under test:
   * {@link BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List, ExpressionManager)}
   */
  @Test
  public void testCreateFieldDeclarations() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory = new BpmnMessagePayloadMappingProviderFactory();
    ArrayList<FieldExtension> fieldList = new ArrayList<>();

    // Act and Assert
    assertTrue(
        bpmnMessagePayloadMappingProviderFactory.createFieldDeclarations(fieldList, new ExpressionManager()).isEmpty());
  }

  /**
   * Method under test:
   * {@link BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List, ExpressionManager)}
   */
  @Test
  public void testCreateFieldDeclarations2() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory = new BpmnMessagePayloadMappingProviderFactory();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression(null);

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult = bpmnMessagePayloadMappingProviderFactory
        .createFieldDeclarations(fieldList, expressionManager);

    // Assert
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    assertTrue(getResult.getValue() instanceof FixedValue);
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Method under test:
   * {@link BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List, ExpressionManager)}
   */
  @Test
  public void testCreateFieldDeclarations3() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory = new BpmnMessagePayloadMappingProviderFactory();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("Field List");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult = bpmnMessagePayloadMappingProviderFactory
        .createFieldDeclarations(fieldList, expressionManager);

    // Assert
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    Object value = getResult.getValue();
    assertTrue(value instanceof JuelExpression);
    assertEquals("Field List", ((JuelExpression) value).getExpressionText());
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Method under test:
   * {@link BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List, ExpressionManager)}
   */
  @Test
  public void testCreateFieldDeclarations4() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory = new BpmnMessagePayloadMappingProviderFactory();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("Field List");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult = bpmnMessagePayloadMappingProviderFactory
        .createFieldDeclarations(fieldList, expressionManager);

    // Assert
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    Object value = getResult.getValue();
    assertTrue(value instanceof JuelExpression);
    assertEquals("Field List", ((JuelExpression) value).getExpressionText());
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Method under test:
   * {@link BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List, ExpressionManager)}
   */
  @Test
  public void testCreateFieldDeclarations5() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory = new BpmnMessagePayloadMappingProviderFactory();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult = bpmnMessagePayloadMappingProviderFactory
        .createFieldDeclarations(fieldList, expressionManager);

    // Assert
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    assertTrue(getResult.getValue() instanceof FixedValue);
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Method under test:
   * {@link BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List, ExpressionManager)}
   */
  @Test
  public void testCreateFieldDeclarations6() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory = new BpmnMessagePayloadMappingProviderFactory();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("Field List");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult = bpmnMessagePayloadMappingProviderFactory
        .createFieldDeclarations(fieldList, expressionManager);

    // Assert
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    Object value = getResult.getValue();
    assertTrue(value instanceof JuelExpression);
    assertEquals("Field List", ((JuelExpression) value).getExpressionText());
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Method under test:
   * {@link BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List, ExpressionManager)}
   */
  @Test
  public void testCreateFieldDeclarations7() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory = new BpmnMessagePayloadMappingProviderFactory();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("Field List");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult = bpmnMessagePayloadMappingProviderFactory
        .createFieldDeclarations(fieldList, expressionManager);

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    Object value = getResult.getValue();
    assertTrue(value instanceof JuelExpression);
    assertEquals("Field List", ((JuelExpression) value).getExpressionText());
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Method under test:
   * {@link BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List, ExpressionManager)}
   */
  @Test
  public void testCreateFieldDeclarations8() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory = new BpmnMessagePayloadMappingProviderFactory();

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(new FieldExtension());
    fieldList.add(new FieldExtension());

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult = bpmnMessagePayloadMappingProviderFactory
        .createFieldDeclarations(fieldList, new ExpressionManager());

    // Assert
    assertEquals(2, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    assertTrue(getResult.getValue() instanceof FixedValue);
    FieldDeclaration getResult2 = actualCreateFieldDeclarationsResult.get(1);
    assertTrue(getResult2.getValue() instanceof FixedValue);
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertEquals("org.activiti.engine.delegate.Expression", getResult2.getType());
    assertNull(getResult.getName());
    assertNull(getResult2.getName());
  }
}
