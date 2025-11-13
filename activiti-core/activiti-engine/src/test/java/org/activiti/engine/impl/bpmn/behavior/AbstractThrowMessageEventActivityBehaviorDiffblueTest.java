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
package org.activiti.engine.impl.bpmn.behavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import java.util.Optional;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.ThrowEvent;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContext;
import org.activiti.engine.impl.bpmn.parser.factory.MessageExecutionContext;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
import org.activiti.engine.impl.delegate.ThrowMessage;
import org.activiti.engine.impl.delegate.ThrowMessageDelegate;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class AbstractThrowMessageEventActivityBehaviorDiffblueTest {
  /**
   * Test {@link AbstractThrowMessageEventActivityBehavior#getMessageEventDefinition()}.
   *
   * <p>Method under test: {@link
   * AbstractThrowMessageEventActivityBehavior#getMessageEventDefinition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventDefinition AbstractThrowMessageEventActivityBehavior.getMessageEventDefinition()"
  })
  public void testGetMessageEventDefinition() {
    // Arrange
    ThrowEvent throwEvent = new ThrowEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    ThrowMessageDelegate delegate = mock(ThrowMessageDelegate.class);
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    DefaultMessageExecutionContext messageExecutionContext =
        new DefaultMessageExecutionContext(
            messageEventDefinition2,
            new ExpressionManager(),
            mock(MessagePayloadMappingProvider.class));

    IntermediateThrowMessageEventActivityBehavior intermediateThrowMessageEventActivityBehavior =
        new IntermediateThrowMessageEventActivityBehavior(
            throwEvent, messageEventDefinition, delegate, messageExecutionContext);

    // Act and Assert
    assertSame(
        messageEventDefinition,
        intermediateThrowMessageEventActivityBehavior.getMessageEventDefinition());
  }

  /**
   * Test {@link AbstractThrowMessageEventActivityBehavior#getDelegate()}.
   *
   * <p>Method under test: {@link AbstractThrowMessageEventActivityBehavior#getDelegate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ThrowMessageDelegate AbstractThrowMessageEventActivityBehavior.getDelegate()"
  })
  public void testGetDelegate() {
    // Arrange
    ThrowEvent throwEvent = new ThrowEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    ThrowMessageDelegate delegate = mock(ThrowMessageDelegate.class);
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    DefaultMessageExecutionContext messageExecutionContext =
        new DefaultMessageExecutionContext(
            messageEventDefinition2,
            new ExpressionManager(),
            mock(MessagePayloadMappingProvider.class));

    IntermediateThrowMessageEventActivityBehavior intermediateThrowMessageEventActivityBehavior =
        new IntermediateThrowMessageEventActivityBehavior(
            throwEvent, messageEventDefinition, delegate, messageExecutionContext);

    // Act
    intermediateThrowMessageEventActivityBehavior.getDelegate();

    // Assert that nothing has changed
    MessageExecutionContext messageExecutionContext2 =
        intermediateThrowMessageEventActivityBehavior.getMessageExecutionContext();
    assertTrue(messageExecutionContext2 instanceof DefaultMessageExecutionContext);
    assertSame(
        messageEventDefinition,
        intermediateThrowMessageEventActivityBehavior.getMessageEventDefinition());
    assertSame(throwEvent, intermediateThrowMessageEventActivityBehavior.getThrowEvent());
    assertSame(messageExecutionContext, messageExecutionContext2);
  }

  /**
   * Test {@link AbstractThrowMessageEventActivityBehavior#getDelegate()}.
   *
   * <p>Method under test: {@link AbstractThrowMessageEventActivityBehavior#getDelegate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ThrowMessageDelegate AbstractThrowMessageEventActivityBehavior.getDelegate()"
  })
  public void testGetDelegate2() {
    // Arrange
    ThrowMessageDelegate delegate = mock(ThrowMessageDelegate.class);
    when(delegate.send(Mockito.<DelegateExecution>any(), Mockito.<ThrowMessage>any()))
        .thenReturn(true);
    ThrowEvent throwEvent = new ThrowEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    DefaultMessageExecutionContext messageExecutionContext =
        new DefaultMessageExecutionContext(
            messageEventDefinition2,
            new ExpressionManager(),
            mock(MessagePayloadMappingProvider.class));

    IntermediateThrowMessageEventActivityBehavior intermediateThrowMessageEventActivityBehavior =
        new IntermediateThrowMessageEventActivityBehavior(
            throwEvent, messageEventDefinition, delegate, messageExecutionContext);

    // Act
    ThrowMessageDelegate actualDelegate =
        intermediateThrowMessageEventActivityBehavior.getDelegate();
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ThrowMessage message = new ThrowMessage("Name");
    boolean actualSendResult = actualDelegate.send(execution, message);

    // Assert
    verify(delegate).send(isA(DelegateExecution.class), isA(ThrowMessage.class));
    Object persistentState = execution.getPersistentState();
    assertTrue(persistentState instanceof Map);
    MessageExecutionContext messageExecutionContext2 =
        intermediateThrowMessageEventActivityBehavior.getMessageExecutionContext();
    assertTrue(messageExecutionContext2 instanceof DefaultMessageExecutionContext);
    assertEquals("", execution.getTenantId());
    assertEquals("Name", message.getName());
    assertNull(execution.getCachedElContext());
    assertNull(execution.getAppVersion());
    assertNull(execution.getProcessDefinitionVersion());
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
    assertNull(execution.getQueryVariables());
    assertNull(execution.getCurrentActivitiListener());
    assertNull(execution.getCurrentFlowElement());
    assertNull(execution.getEngineServices());
    assertNull(execution.getParent());
    assertNull(execution.getProcessInstance());
    assertNull(execution.getSuperExecution());
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
    Optional<String> businessKey = message.getBusinessKey();
    assertFalse(businessKey.isPresent());
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
    assertTrue(actualSendResult);
    assertTrue(execution.isActive());
    assertTrue(execution.isProcessInstanceType());
    assertTrue(execution.isScope());
    assertSame(
        messageEventDefinition,
        intermediateThrowMessageEventActivityBehavior.getMessageEventDefinition());
    assertSame(throwEvent, intermediateThrowMessageEventActivityBehavior.getThrowEvent());
    assertSame(messageExecutionContext, messageExecutionContext2);
    assertSame(businessKey, message.getCorrelationKey());
    assertSame(businessKey, message.getPayload());
  }

  /**
   * Test {@link AbstractThrowMessageEventActivityBehavior#getDelegate()}.
   *
   * <p>Method under test: {@link AbstractThrowMessageEventActivityBehavior#getDelegate()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ThrowMessageDelegate AbstractThrowMessageEventActivityBehavior.getDelegate()"
  })
  public void testGetDelegate3() {
    // Arrange
    ThrowMessageDelegate delegate = mock(ThrowMessageDelegate.class);
    when(delegate.send(Mockito.<DelegateExecution>any(), Mockito.<ThrowMessage>any()))
        .thenReturn(false);
    ThrowEvent throwEvent = new ThrowEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    DefaultMessageExecutionContext messageExecutionContext =
        new DefaultMessageExecutionContext(
            messageEventDefinition2,
            new ExpressionManager(),
            mock(MessagePayloadMappingProvider.class));

    IntermediateThrowMessageEventActivityBehavior intermediateThrowMessageEventActivityBehavior =
        new IntermediateThrowMessageEventActivityBehavior(
            throwEvent, messageEventDefinition, delegate, messageExecutionContext);

    // Act
    ThrowMessageDelegate actualDelegate =
        intermediateThrowMessageEventActivityBehavior.getDelegate();
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ThrowMessage message = new ThrowMessage("Name");
    boolean actualSendResult = actualDelegate.send(execution, message);

    // Assert
    verify(delegate).send(isA(DelegateExecution.class), isA(ThrowMessage.class));
    Object persistentState = execution.getPersistentState();
    assertTrue(persistentState instanceof Map);
    MessageExecutionContext messageExecutionContext2 =
        intermediateThrowMessageEventActivityBehavior.getMessageExecutionContext();
    assertTrue(messageExecutionContext2 instanceof DefaultMessageExecutionContext);
    assertEquals("", execution.getTenantId());
    assertEquals("Name", message.getName());
    assertNull(execution.getCachedElContext());
    assertNull(execution.getAppVersion());
    assertNull(execution.getProcessDefinitionVersion());
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
    assertNull(execution.getQueryVariables());
    assertNull(execution.getCurrentActivitiListener());
    assertNull(execution.getCurrentFlowElement());
    assertNull(execution.getEngineServices());
    assertNull(execution.getParent());
    assertNull(execution.getProcessInstance());
    assertNull(execution.getSuperExecution());
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
    Optional<String> businessKey = message.getBusinessKey();
    assertFalse(businessKey.isPresent());
    assertFalse(actualSendResult);
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
    assertTrue(execution.isActive());
    assertTrue(execution.isProcessInstanceType());
    assertTrue(execution.isScope());
    assertSame(
        messageEventDefinition,
        intermediateThrowMessageEventActivityBehavior.getMessageEventDefinition());
    assertSame(throwEvent, intermediateThrowMessageEventActivityBehavior.getThrowEvent());
    assertSame(messageExecutionContext, messageExecutionContext2);
    assertSame(businessKey, message.getCorrelationKey());
    assertSame(businessKey, message.getPayload());
  }

  /**
   * Test {@link AbstractThrowMessageEventActivityBehavior#getMessageExecutionContext()}.
   *
   * <p>Method under test: {@link
   * AbstractThrowMessageEventActivityBehavior#getMessageExecutionContext()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageExecutionContext AbstractThrowMessageEventActivityBehavior.getMessageExecutionContext()"
  })
  public void testGetMessageExecutionContext() {
    // Arrange
    ThrowEvent throwEvent = new ThrowEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    ThrowMessageDelegate delegate = mock(ThrowMessageDelegate.class);
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    DefaultMessageExecutionContext messageExecutionContext =
        new DefaultMessageExecutionContext(
            messageEventDefinition2,
            new ExpressionManager(),
            mock(MessagePayloadMappingProvider.class));

    IntermediateThrowMessageEventActivityBehavior intermediateThrowMessageEventActivityBehavior =
        new IntermediateThrowMessageEventActivityBehavior(
            throwEvent, messageEventDefinition, delegate, messageExecutionContext);

    // Act and Assert
    assertSame(
        messageExecutionContext,
        intermediateThrowMessageEventActivityBehavior.getMessageExecutionContext());
  }
}
