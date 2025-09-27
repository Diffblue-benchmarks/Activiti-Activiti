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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class BpmnMessagePayloadMappingProviderFactoryDiffblueTest {
  /**
   * Test {@link BpmnMessagePayloadMappingProviderFactory#create(Event, MessageEventDefinition,
   * ExpressionManager)}.
   *
   * <p>Method under test: {@link BpmnMessagePayloadMappingProviderFactory#create(Event,
   * MessageEventDefinition, ExpressionManager)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessagePayloadMappingProvider BpmnMessagePayloadMappingProviderFactory.create(Event, MessageEventDefinition, ExpressionManager)"
  })
  public void testCreate() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory =
        new BpmnMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);

    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager(new HashMap<>());
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    // Act
    MessagePayloadMappingProvider actualCreateResult =
        bpmnMessagePayloadMappingProviderFactory.create(
            bpmnEvent, messageEventDefinition, expressionManager);

    // Assert
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualCreateResult instanceof BpmnMessagePayloadMappingProvider);
    assertNull(bpmnEvent.getBehavior());
    assertNull(bpmnEvent.getId());
    assertNull(messageEventDefinition.getId());
    assertNull(bpmnEvent.getAttachedToRefId());
    assertNull(bpmnEvent.getDocumentation());
    assertNull(bpmnEvent.getName());
    assertNull(messageEventDefinition.getCorrelationKey());
    assertNull(messageEventDefinition.getMessageExpression());
    assertNull(messageEventDefinition.getMessageRef());
    assertNull(bpmnEvent.getAttachedToRef());
    assertNull(bpmnEvent.getParentContainer());
    assertNull(bpmnEvent.getSubProcess());
    assertEquals(0, bpmnEvent.getXmlColumnNumber());
    assertEquals(0, messageEventDefinition.getXmlColumnNumber());
    assertEquals(0, bpmnEvent.getXmlRowNumber());
    assertEquals(0, messageEventDefinition.getXmlRowNumber());
    List<FieldExtension> fieldExtensions2 = messageEventDefinition.getFieldExtensions();
    assertEquals(1, fieldExtensions2.size());
    List<CustomFunctionProvider> customFunctionProviders2 =
        expressionManager.getCustomFunctionProviders();
    assertEquals(1, customFunctionProviders2.size());
    assertFalse(bpmnEvent.isAsynchronous());
    assertFalse(bpmnEvent.isNotExclusive());
    assertTrue(bpmnEvent.getEventDefinitions().isEmpty());
    assertTrue(bpmnEvent.getExecutionListeners().isEmpty());
    assertTrue(bpmnEvent.getIncomingFlows().isEmpty());
    assertTrue(bpmnEvent.getOutgoingFlows().isEmpty());
    assertTrue(bpmnEvent.getAttributes().isEmpty());
    assertTrue(messageEventDefinition.getAttributes().isEmpty());
    assertTrue(bpmnEvent.getExtensionElements().isEmpty());
    assertTrue(messageEventDefinition.getExtensionElements().isEmpty());
    assertTrue(expressionManager.getBeans().isEmpty());
    assertTrue(bpmnEvent.isCancelActivity());
    assertTrue(bpmnEvent.isExclusive());
    assertSame(fieldExtensions, fieldExtensions2);
    assertSame(customFunctionProviders, customFunctionProviders2);
  }

  /**
   * Test {@link BpmnMessagePayloadMappingProviderFactory#create(Event, MessageEventDefinition,
   * ExpressionManager)}.
   *
   * <p>Method under test: {@link BpmnMessagePayloadMappingProviderFactory#create(Event,
   * MessageEventDefinition, ExpressionManager)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessagePayloadMappingProvider BpmnMessagePayloadMappingProviderFactory.create(Event, MessageEventDefinition, ExpressionManager)"
  })
  public void testCreate2() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory =
        new BpmnMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);

    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager(new HashMap<>());
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    // Act
    MessagePayloadMappingProvider actualCreateResult =
        bpmnMessagePayloadMappingProviderFactory.create(
            bpmnEvent, messageEventDefinition, expressionManager);

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualCreateResult instanceof BpmnMessagePayloadMappingProvider);
    assertNull(bpmnEvent.getBehavior());
    assertNull(bpmnEvent.getId());
    assertNull(messageEventDefinition.getId());
    assertNull(bpmnEvent.getAttachedToRefId());
    assertNull(bpmnEvent.getDocumentation());
    assertNull(bpmnEvent.getName());
    assertNull(messageEventDefinition.getCorrelationKey());
    assertNull(messageEventDefinition.getMessageExpression());
    assertNull(messageEventDefinition.getMessageRef());
    assertNull(bpmnEvent.getAttachedToRef());
    assertNull(bpmnEvent.getParentContainer());
    assertNull(bpmnEvent.getSubProcess());
    assertEquals(0, bpmnEvent.getXmlColumnNumber());
    assertEquals(0, messageEventDefinition.getXmlColumnNumber());
    assertEquals(0, bpmnEvent.getXmlRowNumber());
    assertEquals(0, messageEventDefinition.getXmlRowNumber());
    List<FieldExtension> fieldExtensions2 = messageEventDefinition.getFieldExtensions();
    assertEquals(1, fieldExtensions2.size());
    assertFalse(bpmnEvent.isAsynchronous());
    assertFalse(bpmnEvent.isNotExclusive());
    assertTrue(bpmnEvent.getEventDefinitions().isEmpty());
    assertTrue(bpmnEvent.getExecutionListeners().isEmpty());
    assertTrue(bpmnEvent.getIncomingFlows().isEmpty());
    assertTrue(bpmnEvent.getOutgoingFlows().isEmpty());
    assertTrue(bpmnEvent.getAttributes().isEmpty());
    assertTrue(messageEventDefinition.getAttributes().isEmpty());
    assertTrue(bpmnEvent.getExtensionElements().isEmpty());
    assertTrue(messageEventDefinition.getExtensionElements().isEmpty());
    assertTrue(expressionManager.getBeans().isEmpty());
    assertTrue(bpmnEvent.isCancelActivity());
    assertTrue(bpmnEvent.isExclusive());
    assertSame(fieldExtensions, fieldExtensions2);
    assertSame(customFunctionProviders, expressionManager.getCustomFunctionProviders());
  }

  /**
   * Test {@link BpmnMessagePayloadMappingProviderFactory#create(Event, MessageEventDefinition,
   * ExpressionManager)}.
   *
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) Expression is empty string.
   * </ul>
   *
   * <p>Method under test: {@link BpmnMessagePayloadMappingProviderFactory#create(Event,
   * MessageEventDefinition, ExpressionManager)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessagePayloadMappingProvider BpmnMessagePayloadMappingProviderFactory.create(Event, MessageEventDefinition, ExpressionManager)"
  })
  public void testCreate_givenFieldExtensionExpressionIsEmptyString() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory =
        new BpmnMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);

    ExpressionManager expressionManager = new ExpressionManager(new HashMap<>());
    expressionManager.setCustomFunctionProviders(null);

    // Act
    MessagePayloadMappingProvider actualCreateResult =
        bpmnMessagePayloadMappingProviderFactory.create(
            bpmnEvent, messageEventDefinition, expressionManager);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    Optional<Map<String, Object>> actualMessagePayload =
        actualCreateResult.getMessagePayload(execution);

    // Assert
    Object persistentState = execution.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof BpmnMessagePayloadMappingProvider);
    assertEquals("", execution.getTenantId());
    assertNull(execution.getCachedElContext());
    assertNull(execution.getAppVersion());
    assertNull(execution.getProcessDefinitionVersion());
    assertNull(bpmnEvent.getBehavior());
    assertNull(bpmnEvent.getId());
    assertNull(messageEventDefinition.getId());
    assertNull(bpmnEvent.getAttachedToRefId());
    assertNull(bpmnEvent.getDocumentation());
    assertNull(bpmnEvent.getName());
    assertNull(messageEventDefinition.getCorrelationKey());
    assertNull(messageEventDefinition.getMessageExpression());
    assertNull(messageEventDefinition.getMessageRef());
    assertNull(execution.getId());
    assertNull(execution.getActivityId());
    assertNull(execution.getActivityName());
    assertNull(execution.getBusinessKey());
    assertNull(execution.getCurrentActivityId());
    assertNull(execution.getDeleteReason());
    assertNull(execution.getDeploymentId());
    assertNull(execution.getDescription());
    assertNull(execution.getEventName());
    assertNull(execution.getLocalizedDescription());
    assertNull(execution.getLocalizedName());
    assertNull(execution.getName());
    assertNull(execution.getParentId());
    assertNull(execution.getParentProcessInstanceId());
    assertNull(execution.getProcessDefinitionId());
    assertNull(execution.getProcessDefinitionKey());
    assertNull(execution.getProcessDefinitionName());
    assertNull(execution.getProcessInstanceId());
    assertNull(execution.getRootProcessInstanceId());
    assertNull(execution.getStartUserId());
    assertNull(execution.getSuperExecutionId());
    assertNull(execution.getLockTime());
    assertNull(execution.getStartTime());
    assertNull(expressionManager.getCustomFunctionProviders());
    assertNull(execution.getQueryVariables());
    assertNull(execution.getCurrentActivitiListener());
    assertNull(bpmnEvent.getAttachedToRef());
    assertNull(execution.getCurrentFlowElement());
    assertNull(bpmnEvent.getParentContainer());
    assertNull(bpmnEvent.getSubProcess());
    assertNull(execution.getEngineServices());
    assertNull(execution.getParent());
    assertNull(execution.getProcessInstance());
    assertNull(execution.getSuperExecution());
    assertEquals(0, bpmnEvent.getXmlColumnNumber());
    assertEquals(0, messageEventDefinition.getXmlColumnNumber());
    assertEquals(0, bpmnEvent.getXmlRowNumber());
    assertEquals(0, messageEventDefinition.getXmlRowNumber());
    assertEquals(0, execution.getDeadLetterJobCount());
    assertEquals(0, execution.getEventSubscriptionCount());
    assertEquals(0, execution.getIdentityLinkCount());
    assertEquals(0, execution.getJobCount());
    assertEquals(0, execution.getSuspendedJobCount());
    assertEquals(0, execution.getTaskCount());
    assertEquals(0, execution.getTimerJobCount());
    assertEquals(0, execution.getVariableCount());
    List<FieldExtension> fieldExtensions2 = messageEventDefinition.getFieldExtensions();
    assertEquals(1, fieldExtensions2.size());
    assertEquals(1, execution.getRevision());
    assertEquals(1, execution.getSuspensionState());
    assertEquals(2, execution.getRevisionNext());
    assertEquals(23, ((Map<Object, Object>) persistentState).size());
    assertFalse(bpmnEvent.isAsynchronous());
    assertFalse(bpmnEvent.isNotExclusive());
    assertFalse(execution.isInserted());
    assertFalse(execution.isUpdated());
    assertFalse(execution.isConcurrent());
    assertFalse(execution.isCountEnabled());
    assertFalse(execution.isDeleted());
    assertFalse(execution.isEnded());
    assertFalse(execution.isEventScope());
    assertFalse(execution.isMultiInstanceRoot());
    assertFalse(execution.isRootExecution());
    assertFalse(execution.isSuspended());
    assertTrue(bpmnEvent.getEventDefinitions().isEmpty());
    assertTrue(bpmnEvent.getExecutionListeners().isEmpty());
    assertTrue(bpmnEvent.getIncomingFlows().isEmpty());
    assertTrue(bpmnEvent.getOutgoingFlows().isEmpty());
    assertTrue(execution.getEventSubscriptions().isEmpty());
    assertTrue(execution.getExecutions().isEmpty());
    assertTrue(execution.getIdentityLinks().isEmpty());
    assertTrue(execution.getJobs().isEmpty());
    assertTrue(execution.getTasks().isEmpty());
    assertTrue(execution.getTimerJobs().isEmpty());
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isEventScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("superExecution"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspendedJobCount"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspensionState"));
    assertTrue(bpmnEvent.getAttributes().isEmpty());
    assertTrue(messageEventDefinition.getAttributes().isEmpty());
    assertTrue(bpmnEvent.getExtensionElements().isEmpty());
    assertTrue(messageEventDefinition.getExtensionElements().isEmpty());
    assertTrue(expressionManager.getBeans().isEmpty());
    assertTrue(execution.getProcessVariables().isEmpty());
    assertTrue(execution.getTransientVariables().isEmpty());
    assertTrue(execution.getTransientVariablesLocal().isEmpty());
    assertTrue(execution.getUsedVariablesCache().isEmpty());
    assertTrue(execution.getVariableInstanceEntities().isEmpty());
    assertTrue(execution.getVariableInstances().isEmpty());
    assertTrue(execution.getVariableInstancesLocal().isEmpty());
    assertTrue(execution.getVariables().isEmpty());
    assertTrue(execution.getVariablesLocal().isEmpty());
    assertTrue(actualMessagePayload.isPresent());
    assertTrue(execution.getVariableNames().isEmpty());
    assertTrue(execution.getVariableNamesLocal().isEmpty());
    assertTrue(bpmnEvent.isCancelActivity());
    assertTrue(bpmnEvent.isExclusive());
    assertTrue(execution.isActive());
    assertTrue(execution.isProcessInstanceType());
    assertTrue(execution.isScope());
    assertSame(fieldExtensions, fieldExtensions2);
  }

  /**
   * Test {@link BpmnMessagePayloadMappingProviderFactory#create(Event, MessageEventDefinition,
   * ExpressionManager)}.
   *
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) Expression is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnMessagePayloadMappingProviderFactory#create(Event,
   * MessageEventDefinition, ExpressionManager)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessagePayloadMappingProvider BpmnMessagePayloadMappingProviderFactory.create(Event, MessageEventDefinition, ExpressionManager)"
  })
  public void testCreate_givenFieldExtensionExpressionIsNull() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory =
        new BpmnMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression(null);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);

    ExpressionManager expressionManager = new ExpressionManager(new HashMap<>());
    expressionManager.setCustomFunctionProviders(null);

    // Act
    MessagePayloadMappingProvider actualCreateResult =
        bpmnMessagePayloadMappingProviderFactory.create(
            bpmnEvent, messageEventDefinition, expressionManager);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    Optional<Map<String, Object>> actualMessagePayload =
        actualCreateResult.getMessagePayload(execution);

    // Assert
    Object persistentState = execution.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof BpmnMessagePayloadMappingProvider);
    assertEquals("", execution.getTenantId());
    assertNull(execution.getCachedElContext());
    assertNull(execution.getAppVersion());
    assertNull(execution.getProcessDefinitionVersion());
    assertNull(bpmnEvent.getBehavior());
    assertNull(bpmnEvent.getId());
    assertNull(messageEventDefinition.getId());
    assertNull(bpmnEvent.getAttachedToRefId());
    assertNull(bpmnEvent.getDocumentation());
    assertNull(bpmnEvent.getName());
    assertNull(messageEventDefinition.getCorrelationKey());
    assertNull(messageEventDefinition.getMessageExpression());
    assertNull(messageEventDefinition.getMessageRef());
    assertNull(execution.getId());
    assertNull(execution.getActivityId());
    assertNull(execution.getActivityName());
    assertNull(execution.getBusinessKey());
    assertNull(execution.getCurrentActivityId());
    assertNull(execution.getDeleteReason());
    assertNull(execution.getDeploymentId());
    assertNull(execution.getDescription());
    assertNull(execution.getEventName());
    assertNull(execution.getLocalizedDescription());
    assertNull(execution.getLocalizedName());
    assertNull(execution.getName());
    assertNull(execution.getParentId());
    assertNull(execution.getParentProcessInstanceId());
    assertNull(execution.getProcessDefinitionId());
    assertNull(execution.getProcessDefinitionKey());
    assertNull(execution.getProcessDefinitionName());
    assertNull(execution.getProcessInstanceId());
    assertNull(execution.getRootProcessInstanceId());
    assertNull(execution.getStartUserId());
    assertNull(execution.getSuperExecutionId());
    assertNull(execution.getLockTime());
    assertNull(execution.getStartTime());
    assertNull(expressionManager.getCustomFunctionProviders());
    assertNull(execution.getQueryVariables());
    assertNull(execution.getCurrentActivitiListener());
    assertNull(bpmnEvent.getAttachedToRef());
    assertNull(execution.getCurrentFlowElement());
    assertNull(bpmnEvent.getParentContainer());
    assertNull(bpmnEvent.getSubProcess());
    assertNull(execution.getEngineServices());
    assertNull(execution.getParent());
    assertNull(execution.getProcessInstance());
    assertNull(execution.getSuperExecution());
    assertEquals(0, bpmnEvent.getXmlColumnNumber());
    assertEquals(0, messageEventDefinition.getXmlColumnNumber());
    assertEquals(0, bpmnEvent.getXmlRowNumber());
    assertEquals(0, messageEventDefinition.getXmlRowNumber());
    assertEquals(0, execution.getDeadLetterJobCount());
    assertEquals(0, execution.getEventSubscriptionCount());
    assertEquals(0, execution.getIdentityLinkCount());
    assertEquals(0, execution.getJobCount());
    assertEquals(0, execution.getSuspendedJobCount());
    assertEquals(0, execution.getTaskCount());
    assertEquals(0, execution.getTimerJobCount());
    assertEquals(0, execution.getVariableCount());
    List<FieldExtension> fieldExtensions2 = messageEventDefinition.getFieldExtensions();
    assertEquals(1, fieldExtensions2.size());
    assertEquals(1, execution.getRevision());
    assertEquals(1, execution.getSuspensionState());
    assertEquals(2, execution.getRevisionNext());
    assertEquals(23, ((Map<Object, Object>) persistentState).size());
    assertFalse(bpmnEvent.isAsynchronous());
    assertFalse(bpmnEvent.isNotExclusive());
    assertFalse(execution.isInserted());
    assertFalse(execution.isUpdated());
    assertFalse(execution.isConcurrent());
    assertFalse(execution.isCountEnabled());
    assertFalse(execution.isDeleted());
    assertFalse(execution.isEnded());
    assertFalse(execution.isEventScope());
    assertFalse(execution.isMultiInstanceRoot());
    assertFalse(execution.isRootExecution());
    assertFalse(execution.isSuspended());
    assertTrue(bpmnEvent.getEventDefinitions().isEmpty());
    assertTrue(bpmnEvent.getExecutionListeners().isEmpty());
    assertTrue(bpmnEvent.getIncomingFlows().isEmpty());
    assertTrue(bpmnEvent.getOutgoingFlows().isEmpty());
    assertTrue(execution.getEventSubscriptions().isEmpty());
    assertTrue(execution.getExecutions().isEmpty());
    assertTrue(execution.getIdentityLinks().isEmpty());
    assertTrue(execution.getJobs().isEmpty());
    assertTrue(execution.getTasks().isEmpty());
    assertTrue(execution.getTimerJobs().isEmpty());
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isEventScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("superExecution"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspendedJobCount"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspensionState"));
    assertTrue(bpmnEvent.getAttributes().isEmpty());
    assertTrue(messageEventDefinition.getAttributes().isEmpty());
    assertTrue(bpmnEvent.getExtensionElements().isEmpty());
    assertTrue(messageEventDefinition.getExtensionElements().isEmpty());
    assertTrue(expressionManager.getBeans().isEmpty());
    assertTrue(execution.getProcessVariables().isEmpty());
    assertTrue(execution.getTransientVariables().isEmpty());
    assertTrue(execution.getTransientVariablesLocal().isEmpty());
    assertTrue(execution.getUsedVariablesCache().isEmpty());
    assertTrue(execution.getVariableInstanceEntities().isEmpty());
    assertTrue(execution.getVariableInstances().isEmpty());
    assertTrue(execution.getVariableInstancesLocal().isEmpty());
    assertTrue(execution.getVariables().isEmpty());
    assertTrue(execution.getVariablesLocal().isEmpty());
    assertTrue(actualMessagePayload.isPresent());
    assertTrue(execution.getVariableNames().isEmpty());
    assertTrue(execution.getVariableNamesLocal().isEmpty());
    assertTrue(bpmnEvent.isCancelActivity());
    assertTrue(bpmnEvent.isExclusive());
    assertTrue(execution.isActive());
    assertTrue(execution.isProcessInstanceType());
    assertTrue(execution.isScope());
    assertSame(fieldExtensions, fieldExtensions2);
  }

  /**
   * Test {@link BpmnMessagePayloadMappingProviderFactory#create(Event, MessageEventDefinition,
   * ExpressionManager)}.
   *
   * <ul>
   *   <li>Then {@link ExpressionManager#ExpressionManager()} CustomFunctionProviders is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnMessagePayloadMappingProviderFactory#create(Event,
   * MessageEventDefinition, ExpressionManager)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessagePayloadMappingProvider BpmnMessagePayloadMappingProviderFactory.create(Event, MessageEventDefinition, ExpressionManager)"
  })
  public void testCreate_thenExpressionManagerCustomFunctionProvidersIsNull() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory =
        new BpmnMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    ExpressionManager expressionManager = new ExpressionManager();

    // Act
    MessagePayloadMappingProvider actualCreateResult =
        bpmnMessagePayloadMappingProviderFactory.create(
            bpmnEvent, messageEventDefinition, expressionManager);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    Optional<Map<String, Object>> actualMessagePayload =
        actualCreateResult.getMessagePayload(execution);

    // Assert
    Object persistentState = execution.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof BpmnMessagePayloadMappingProvider);
    assertEquals("", execution.getTenantId());
    assertNull(execution.getCachedElContext());
    assertNull(execution.getAppVersion());
    assertNull(execution.getProcessDefinitionVersion());
    assertNull(bpmnEvent.getBehavior());
    assertNull(bpmnEvent.getId());
    assertNull(messageEventDefinition.getId());
    assertNull(bpmnEvent.getAttachedToRefId());
    assertNull(bpmnEvent.getDocumentation());
    assertNull(bpmnEvent.getName());
    assertNull(messageEventDefinition.getCorrelationKey());
    assertNull(messageEventDefinition.getMessageExpression());
    assertNull(messageEventDefinition.getMessageRef());
    assertNull(execution.getId());
    assertNull(execution.getActivityId());
    assertNull(execution.getActivityName());
    assertNull(execution.getBusinessKey());
    assertNull(execution.getCurrentActivityId());
    assertNull(execution.getDeleteReason());
    assertNull(execution.getDeploymentId());
    assertNull(execution.getDescription());
    assertNull(execution.getEventName());
    assertNull(execution.getLocalizedDescription());
    assertNull(execution.getLocalizedName());
    assertNull(execution.getName());
    assertNull(execution.getParentId());
    assertNull(execution.getParentProcessInstanceId());
    assertNull(execution.getProcessDefinitionId());
    assertNull(execution.getProcessDefinitionKey());
    assertNull(execution.getProcessDefinitionName());
    assertNull(execution.getProcessInstanceId());
    assertNull(execution.getRootProcessInstanceId());
    assertNull(execution.getStartUserId());
    assertNull(execution.getSuperExecutionId());
    assertNull(execution.getLockTime());
    assertNull(execution.getStartTime());
    assertNull(expressionManager.getCustomFunctionProviders());
    assertNull(execution.getQueryVariables());
    assertNull(expressionManager.getBeans());
    assertNull(execution.getCurrentActivitiListener());
    assertNull(bpmnEvent.getAttachedToRef());
    assertNull(execution.getCurrentFlowElement());
    assertNull(bpmnEvent.getParentContainer());
    assertNull(bpmnEvent.getSubProcess());
    assertNull(execution.getEngineServices());
    assertNull(execution.getParent());
    assertNull(execution.getProcessInstance());
    assertNull(execution.getSuperExecution());
    assertEquals(0, bpmnEvent.getXmlColumnNumber());
    assertEquals(0, messageEventDefinition.getXmlColumnNumber());
    assertEquals(0, bpmnEvent.getXmlRowNumber());
    assertEquals(0, messageEventDefinition.getXmlRowNumber());
    assertEquals(0, execution.getDeadLetterJobCount());
    assertEquals(0, execution.getEventSubscriptionCount());
    assertEquals(0, execution.getIdentityLinkCount());
    assertEquals(0, execution.getJobCount());
    assertEquals(0, execution.getSuspendedJobCount());
    assertEquals(0, execution.getTaskCount());
    assertEquals(0, execution.getTimerJobCount());
    assertEquals(0, execution.getVariableCount());
    assertEquals(1, execution.getRevision());
    assertEquals(1, execution.getSuspensionState());
    assertEquals(2, execution.getRevisionNext());
    assertEquals(23, ((Map<Object, Object>) persistentState).size());
    Optional<Map<String, Object>> messagePayload = actualCreateResult.getMessagePayload(null);
    assertFalse(messagePayload.isPresent());
    assertFalse(bpmnEvent.isAsynchronous());
    assertFalse(bpmnEvent.isNotExclusive());
    assertFalse(execution.isInserted());
    assertFalse(execution.isUpdated());
    assertFalse(execution.isConcurrent());
    assertFalse(execution.isCountEnabled());
    assertFalse(execution.isDeleted());
    assertFalse(execution.isEnded());
    assertFalse(execution.isEventScope());
    assertFalse(execution.isMultiInstanceRoot());
    assertFalse(execution.isRootExecution());
    assertFalse(execution.isSuspended());
    assertTrue(bpmnEvent.getEventDefinitions().isEmpty());
    assertTrue(bpmnEvent.getExecutionListeners().isEmpty());
    assertTrue(bpmnEvent.getIncomingFlows().isEmpty());
    assertTrue(bpmnEvent.getOutgoingFlows().isEmpty());
    assertTrue(messageEventDefinition.getFieldExtensions().isEmpty());
    assertTrue(execution.getEventSubscriptions().isEmpty());
    assertTrue(execution.getExecutions().isEmpty());
    assertTrue(execution.getIdentityLinks().isEmpty());
    assertTrue(execution.getJobs().isEmpty());
    assertTrue(execution.getTasks().isEmpty());
    assertTrue(execution.getTimerJobs().isEmpty());
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isEventScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("isScope"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("processDefinitionId"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("superExecution"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspendedJobCount"));
    assertTrue(((Map<Object, Object>) persistentState).containsKey("suspensionState"));
    assertTrue(bpmnEvent.getAttributes().isEmpty());
    assertTrue(messageEventDefinition.getAttributes().isEmpty());
    assertTrue(bpmnEvent.getExtensionElements().isEmpty());
    assertTrue(messageEventDefinition.getExtensionElements().isEmpty());
    assertTrue(execution.getProcessVariables().isEmpty());
    assertTrue(execution.getTransientVariables().isEmpty());
    assertTrue(execution.getTransientVariablesLocal().isEmpty());
    assertTrue(execution.getUsedVariablesCache().isEmpty());
    assertTrue(execution.getVariableInstanceEntities().isEmpty());
    assertTrue(execution.getVariableInstances().isEmpty());
    assertTrue(execution.getVariableInstancesLocal().isEmpty());
    assertTrue(execution.getVariables().isEmpty());
    assertTrue(execution.getVariablesLocal().isEmpty());
    assertTrue(execution.getVariableNames().isEmpty());
    assertTrue(execution.getVariableNamesLocal().isEmpty());
    assertTrue(bpmnEvent.isCancelActivity());
    assertTrue(bpmnEvent.isExclusive());
    assertTrue(execution.isActive());
    assertTrue(execution.isProcessInstanceType());
    assertTrue(execution.isScope());
    assertSame(messagePayload, actualMessagePayload);
  }

  /**
   * Test {@link BpmnMessagePayloadMappingProviderFactory#create(Event, MessageEventDefinition,
   * ExpressionManager)}.
   *
   * <ul>
   *   <li>Then {@link ExpressionManager#ExpressionManager(Map)} with beans is {@link
   *       HashMap#HashMap()} CustomFunctionProviders Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnMessagePayloadMappingProviderFactory#create(Event,
   * MessageEventDefinition, ExpressionManager)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessagePayloadMappingProvider BpmnMessagePayloadMappingProviderFactory.create(Event, MessageEventDefinition, ExpressionManager)"
  })
  public void testCreate_thenExpressionManagerWithBeansIsHashMapCustomFunctionProvidersEmpty() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory =
        new BpmnMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);

    ExpressionManager expressionManager = new ExpressionManager(new HashMap<>());
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

    // Act and Assert
    assertTrue(
        bpmnMessagePayloadMappingProviderFactory.create(
                bpmnEvent, messageEventDefinition, expressionManager)
            instanceof BpmnMessagePayloadMappingProvider);
    assertNull(bpmnEvent.getBehavior());
    assertNull(bpmnEvent.getId());
    assertNull(messageEventDefinition.getId());
    assertNull(bpmnEvent.getAttachedToRefId());
    assertNull(bpmnEvent.getDocumentation());
    assertNull(bpmnEvent.getName());
    assertNull(messageEventDefinition.getCorrelationKey());
    assertNull(messageEventDefinition.getMessageExpression());
    assertNull(messageEventDefinition.getMessageRef());
    assertNull(bpmnEvent.getAttachedToRef());
    assertNull(bpmnEvent.getParentContainer());
    assertNull(bpmnEvent.getSubProcess());
    assertEquals(0, bpmnEvent.getXmlColumnNumber());
    assertEquals(0, messageEventDefinition.getXmlColumnNumber());
    assertEquals(0, bpmnEvent.getXmlRowNumber());
    assertEquals(0, messageEventDefinition.getXmlRowNumber());
    List<FieldExtension> fieldExtensions2 = messageEventDefinition.getFieldExtensions();
    assertEquals(1, fieldExtensions2.size());
    assertFalse(bpmnEvent.isAsynchronous());
    assertFalse(bpmnEvent.isNotExclusive());
    assertTrue(bpmnEvent.getEventDefinitions().isEmpty());
    assertTrue(bpmnEvent.getExecutionListeners().isEmpty());
    assertTrue(bpmnEvent.getIncomingFlows().isEmpty());
    assertTrue(bpmnEvent.getOutgoingFlows().isEmpty());
    assertTrue(expressionManager.getCustomFunctionProviders().isEmpty());
    assertTrue(bpmnEvent.getAttributes().isEmpty());
    assertTrue(messageEventDefinition.getAttributes().isEmpty());
    assertTrue(bpmnEvent.getExtensionElements().isEmpty());
    assertTrue(messageEventDefinition.getExtensionElements().isEmpty());
    assertTrue(expressionManager.getBeans().isEmpty());
    assertTrue(bpmnEvent.isCancelActivity());
    assertTrue(bpmnEvent.isExclusive());
    assertSame(fieldExtensions, fieldExtensions2);
  }

  /**
   * Test {@link BpmnMessagePayloadMappingProviderFactory#create(Event, MessageEventDefinition,
   * ExpressionManager)}.
   *
   * <ul>
   *   <li>Then {@link ExpressionManager#ExpressionManager(Map)} with beans is {@link
   *       HashMap#HashMap()} CustomFunctionProviders is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnMessagePayloadMappingProviderFactory#create(Event,
   * MessageEventDefinition, ExpressionManager)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessagePayloadMappingProvider BpmnMessagePayloadMappingProviderFactory.create(Event, MessageEventDefinition, ExpressionManager)"
  })
  public void testCreate_thenExpressionManagerWithBeansIsHashMapCustomFunctionProvidersIsNull() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory =
        new BpmnMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);

    ExpressionManager expressionManager = new ExpressionManager(new HashMap<>());
    expressionManager.setCustomFunctionProviders(null);

    // Act and Assert
    assertTrue(
        bpmnMessagePayloadMappingProviderFactory.create(
                bpmnEvent, messageEventDefinition, expressionManager)
            instanceof BpmnMessagePayloadMappingProvider);
    assertNull(bpmnEvent.getBehavior());
    assertNull(bpmnEvent.getId());
    assertNull(messageEventDefinition.getId());
    assertNull(bpmnEvent.getAttachedToRefId());
    assertNull(bpmnEvent.getDocumentation());
    assertNull(bpmnEvent.getName());
    assertNull(messageEventDefinition.getCorrelationKey());
    assertNull(messageEventDefinition.getMessageExpression());
    assertNull(messageEventDefinition.getMessageRef());
    assertNull(expressionManager.getCustomFunctionProviders());
    assertNull(bpmnEvent.getAttachedToRef());
    assertNull(bpmnEvent.getParentContainer());
    assertNull(bpmnEvent.getSubProcess());
    assertEquals(0, bpmnEvent.getXmlColumnNumber());
    assertEquals(0, messageEventDefinition.getXmlColumnNumber());
    assertEquals(0, bpmnEvent.getXmlRowNumber());
    assertEquals(0, messageEventDefinition.getXmlRowNumber());
    List<FieldExtension> fieldExtensions2 = messageEventDefinition.getFieldExtensions();
    assertEquals(1, fieldExtensions2.size());
    assertFalse(bpmnEvent.isAsynchronous());
    assertFalse(bpmnEvent.isNotExclusive());
    assertTrue(bpmnEvent.getEventDefinitions().isEmpty());
    assertTrue(bpmnEvent.getExecutionListeners().isEmpty());
    assertTrue(bpmnEvent.getIncomingFlows().isEmpty());
    assertTrue(bpmnEvent.getOutgoingFlows().isEmpty());
    assertTrue(bpmnEvent.getAttributes().isEmpty());
    assertTrue(messageEventDefinition.getAttributes().isEmpty());
    assertTrue(bpmnEvent.getExtensionElements().isEmpty());
    assertTrue(messageEventDefinition.getExtensionElements().isEmpty());
    assertTrue(expressionManager.getBeans().isEmpty());
    assertTrue(bpmnEvent.isCancelActivity());
    assertTrue(bpmnEvent.isExclusive());
    assertSame(fieldExtensions, fieldExtensions2);
  }

  /**
   * Test {@link BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List,
   * ExpressionManager)}.
   *
   * <p>Method under test: {@link
   * BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List, ExpressionManager)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnMessagePayloadMappingProviderFactory.createFieldDeclarations(List, ExpressionManager)"
  })
  public void testCreateFieldDeclarations() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory =
        new BpmnMessagePayloadMappingProviderFactory();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    ExpressionManager expressionManager = new ExpressionManager(new HashMap<>());
    expressionManager.setCustomFunctionProviders(null);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult =
        bpmnMessagePayloadMappingProviderFactory.createFieldDeclarations(
            fieldList, expressionManager);

    // Assert
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    Object value = getResult.getValue();
    assertTrue(value instanceof JuelExpression);
    assertEquals("not empty", ((JuelExpression) value).getExpressionText());
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Test {@link BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List,
   * ExpressionManager)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List, ExpressionManager)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnMessagePayloadMappingProviderFactory.createFieldDeclarations(List, ExpressionManager)"
  })
  public void testCreateFieldDeclarations_givenArrayList() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory =
        new BpmnMessagePayloadMappingProviderFactory();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    ExpressionManager expressionManager = new ExpressionManager(new HashMap<>());
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult =
        bpmnMessagePayloadMappingProviderFactory.createFieldDeclarations(
            fieldList, expressionManager);

    // Assert
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    Object value = getResult.getValue();
    assertTrue(value instanceof JuelExpression);
    assertEquals("not empty", ((JuelExpression) value).getExpressionText());
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Test {@link BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List,
   * ExpressionManager)}.
   *
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) Expression is empty string.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List, ExpressionManager)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnMessagePayloadMappingProviderFactory.createFieldDeclarations(List, ExpressionManager)"
  })
  public void testCreateFieldDeclarations_givenFieldExtensionExpressionIsEmptyString() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory =
        new BpmnMessagePayloadMappingProviderFactory();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    ExpressionManager expressionManager = new ExpressionManager(new HashMap<>());
    expressionManager.setCustomFunctionProviders(null);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult =
        bpmnMessagePayloadMappingProviderFactory.createFieldDeclarations(
            fieldList, expressionManager);

    // Assert
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    assertTrue(getResult.getValue() instanceof FixedValue);
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Test {@link BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List,
   * ExpressionManager)}.
   *
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) Expression is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List, ExpressionManager)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnMessagePayloadMappingProviderFactory.createFieldDeclarations(List, ExpressionManager)"
  })
  public void testCreateFieldDeclarations_givenFieldExtensionExpressionIsNull() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory =
        new BpmnMessagePayloadMappingProviderFactory();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression(null);

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    ExpressionManager expressionManager = new ExpressionManager(new HashMap<>());
    expressionManager.setCustomFunctionProviders(null);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult =
        bpmnMessagePayloadMappingProviderFactory.createFieldDeclarations(
            fieldList, expressionManager);

    // Assert
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    assertTrue(getResult.getValue() instanceof FixedValue);
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Test {@link BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List,
   * ExpressionManager)}.
   *
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor).
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List, ExpressionManager)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnMessagePayloadMappingProviderFactory.createFieldDeclarations(List, ExpressionManager)"
  })
  public void testCreateFieldDeclarations_givenFieldExtension_thenReturnSizeIsTwo() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory =
        new BpmnMessagePayloadMappingProviderFactory();

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(new FieldExtension());
    fieldList.add(new FieldExtension());

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult =
        bpmnMessagePayloadMappingProviderFactory.createFieldDeclarations(
            fieldList, new ExpressionManager());

    // Assert
    assertEquals(2, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(1);
    assertTrue(getResult.getValue() instanceof FixedValue);
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Test {@link BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List,
   * ExpressionManager)}.
   *
   * <ul>
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List, ExpressionManager)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnMessagePayloadMappingProviderFactory.createFieldDeclarations(List, ExpressionManager)"
  })
  public void testCreateFieldDeclarations_thenCallsAddCustomFunctions() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory =
        new BpmnMessagePayloadMappingProviderFactory();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager(new HashMap<>());
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult =
        bpmnMessagePayloadMappingProviderFactory.createFieldDeclarations(
            fieldList, expressionManager);

    // Assert
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    Object value = getResult.getValue();
    assertTrue(value instanceof JuelExpression);
    assertEquals("not empty", ((JuelExpression) value).getExpressionText());
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Test {@link BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List,
   * ExpressionManager)}.
   *
   * <ul>
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List, ExpressionManager)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnMessagePayloadMappingProviderFactory.createFieldDeclarations(List, ExpressionManager)"
  })
  public void testCreateFieldDeclarations_thenCallsAddCustomFunctions2() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory =
        new BpmnMessagePayloadMappingProviderFactory();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager(new HashMap<>());
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult =
        bpmnMessagePayloadMappingProviderFactory.createFieldDeclarations(
            fieldList, expressionManager);

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    Object value = getResult.getValue();
    assertTrue(value instanceof JuelExpression);
    assertEquals("not empty", ((JuelExpression) value).getExpressionText());
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Test {@link BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List,
   * ExpressionManager)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnMessagePayloadMappingProviderFactory#createFieldDeclarations(List, ExpressionManager)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnMessagePayloadMappingProviderFactory.createFieldDeclarations(List, ExpressionManager)"
  })
  public void testCreateFieldDeclarations_whenArrayList_thenReturnEmpty() {
    // Arrange
    BpmnMessagePayloadMappingProviderFactory bpmnMessagePayloadMappingProviderFactory =
        new BpmnMessagePayloadMappingProviderFactory();
    ArrayList<FieldExtension> fieldList = new ArrayList<>();

    // Act and Assert
    assertTrue(
        bpmnMessagePayloadMappingProviderFactory
            .createFieldDeclarations(fieldList, new ExpressionManager())
            .isEmpty());
  }
}
