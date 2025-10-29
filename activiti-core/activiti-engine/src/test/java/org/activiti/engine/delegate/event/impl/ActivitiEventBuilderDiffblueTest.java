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
package org.activiti.engine.delegate.event.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.event.ActivitiActivityCancelledEvent;
import org.activiti.engine.delegate.event.ActivitiActivityEvent;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiErrorEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.ActivitiMembershipEvent;
import org.activiti.engine.delegate.event.ActivitiMessageEvent;
import org.activiti.engine.delegate.event.ActivitiProcessCancelledEvent;
import org.activiti.engine.delegate.event.ActivitiSequenceFlowTakenEvent;
import org.activiti.engine.delegate.event.ActivitiSignalEvent;
import org.activiti.engine.delegate.event.ActivitiVariableEvent;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntity;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.activiti.engine.impl.variable.VariableType;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ActivitiEventBuilderDiffblueTest {
  @InjectMocks
  private ActivitiEventBuilder activitiEventBuilder;

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createGlobalEvent(ActivitiEventType)}
   */
  @Test
  public void testCreateGlobalEvent() {
    // Arrange and Act
    ActivitiEvent actualCreateGlobalEventResult = ActivitiEventBuilder
        .createGlobalEvent(ActivitiEventType.ENTITY_CREATED);

    // Assert
    assertTrue(actualCreateGlobalEventResult instanceof ActivitiEventImpl);
    assertNull(actualCreateGlobalEventResult.getExecutionId());
    assertNull(actualCreateGlobalEventResult.getProcessDefinitionId());
    assertNull(actualCreateGlobalEventResult.getProcessInstanceId());
    assertNull(((ActivitiEventImpl) actualCreateGlobalEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateGlobalEventResult.getType());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createEvent(ActivitiEventType, String, String, String)}
   */
  @Test
  public void testCreateEvent() {
    // Arrange and Act
    ActivitiEvent actualCreateEventResult = ActivitiEventBuilder.createEvent(ActivitiEventType.ENTITY_CREATED, "42",
        "42", "42");

    // Assert
    assertTrue(actualCreateEventResult instanceof ActivitiEventImpl);
    assertEquals("42", actualCreateEventResult.getExecutionId());
    assertEquals("42", actualCreateEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateEventResult.getProcessInstanceId());
    assertNull(((ActivitiEventImpl) actualCreateEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateEventResult.getType());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createEntityEvent(ActivitiEventType, Object, String, String, String)}
   */
  @Test
  public void testCreateEntityEvent() {
    // Arrange
    Object object = JSONObject.NULL;

    // Act
    ActivitiEntityEvent actualCreateEntityEventResult = ActivitiEventBuilder
        .createEntityEvent(ActivitiEventType.ENTITY_CREATED, object, "42", "42", "42");

    // Assert
    assertTrue(actualCreateEntityEventResult instanceof ActivitiEntityEventImpl);
    assertEquals("42", actualCreateEntityEventResult.getExecutionId());
    assertEquals("42", actualCreateEntityEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateEntityEventResult.getProcessInstanceId());
    assertNull(actualCreateEntityEventResult.getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateEntityEventResult.getType());
    assertSame(object, actualCreateEntityEventResult.getEntity());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity, ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}
   */
  @Test
  public void testCreateSequenceFlowTakenEvent() {
    // Arrange and Act
    ActivitiSequenceFlowTakenEvent actualCreateSequenceFlowTakenEventResult = ActivitiEventBuilder
        .createSequenceFlowTakenEvent(ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
            ActivitiEventType.ENTITY_CREATED, "42", "42", "Source Activity Name", "Source Activity Type",
            JSONObject.NULL, "42", "Target Activity Name", "Target Activity Type", JSONObject.NULL);

    // Assert
    assertTrue(actualCreateSequenceFlowTakenEventResult instanceof ActivitiSequenceFlowTakenEventImpl);
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getSourceActivityId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getTargetActivityId());
    assertEquals("Source Activity Name", actualCreateSequenceFlowTakenEventResult.getSourceActivityName());
    assertEquals("Source Activity Type", actualCreateSequenceFlowTakenEventResult.getSourceActivityType());
    assertEquals("Target Activity Name", actualCreateSequenceFlowTakenEventResult.getTargetActivityName());
    assertEquals("Target Activity Type", actualCreateSequenceFlowTakenEventResult.getTargetActivityType());
    assertEquals("org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateSequenceFlowTakenEventResult.getSourceActivityBehaviorClass());
    assertEquals("org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateSequenceFlowTakenEventResult.getTargetActivityBehaviorClass());
    assertNull(actualCreateSequenceFlowTakenEventResult.getExecutionId());
    assertNull(actualCreateSequenceFlowTakenEventResult.getProcessDefinitionId());
    assertNull(actualCreateSequenceFlowTakenEventResult.getProcessInstanceId());
    assertNull(((ActivitiSequenceFlowTakenEventImpl) actualCreateSequenceFlowTakenEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateSequenceFlowTakenEventResult.getType());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity, ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}
   */
  @Test
  public void testCreateSequenceFlowTakenEvent2() {
    // Arrange and Act
    ActivitiSequenceFlowTakenEvent actualCreateSequenceFlowTakenEventResult = ActivitiEventBuilder
        .createSequenceFlowTakenEvent(null, ActivitiEventType.ENTITY_CREATED, "42", "42", "Source Activity Name",
            "Source Activity Type", JSONObject.NULL, "42", "Target Activity Name", "Target Activity Type",
            JSONObject.NULL);

    // Assert
    assertTrue(actualCreateSequenceFlowTakenEventResult instanceof ActivitiSequenceFlowTakenEventImpl);
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getSourceActivityId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getTargetActivityId());
    assertEquals("Source Activity Name", actualCreateSequenceFlowTakenEventResult.getSourceActivityName());
    assertEquals("Source Activity Type", actualCreateSequenceFlowTakenEventResult.getSourceActivityType());
    assertEquals("Target Activity Name", actualCreateSequenceFlowTakenEventResult.getTargetActivityName());
    assertEquals("Target Activity Type", actualCreateSequenceFlowTakenEventResult.getTargetActivityType());
    assertEquals("org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateSequenceFlowTakenEventResult.getSourceActivityBehaviorClass());
    assertEquals("org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateSequenceFlowTakenEventResult.getTargetActivityBehaviorClass());
    assertNull(actualCreateSequenceFlowTakenEventResult.getExecutionId());
    assertNull(actualCreateSequenceFlowTakenEventResult.getProcessDefinitionId());
    assertNull(actualCreateSequenceFlowTakenEventResult.getProcessInstanceId());
    assertNull(((ActivitiSequenceFlowTakenEventImpl) actualCreateSequenceFlowTakenEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateSequenceFlowTakenEventResult.getType());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity, ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}
   */
  @Test
  public void testCreateSequenceFlowTakenEvent3() {
    // Arrange
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessDefinitionId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");

    // Act
    ActivitiSequenceFlowTakenEvent actualCreateSequenceFlowTakenEventResult = ActivitiEventBuilder
        .createSequenceFlowTakenEvent(executionEntity, ActivitiEventType.ENTITY_CREATED, "42", "42",
            "Source Activity Name", "Source Activity Type", JSONObject.NULL, "42", "Target Activity Name",
            "Target Activity Type", JSONObject.NULL);

    // Assert
    verify(executionEntity).getId();
    verify(executionEntity).getProcessDefinitionId();
    verify(executionEntity).getProcessInstanceId();
    assertTrue(actualCreateSequenceFlowTakenEventResult instanceof ActivitiSequenceFlowTakenEventImpl);
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getExecutionId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getProcessInstanceId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getSourceActivityId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getTargetActivityId());
    assertEquals("Source Activity Name", actualCreateSequenceFlowTakenEventResult.getSourceActivityName());
    assertEquals("Source Activity Type", actualCreateSequenceFlowTakenEventResult.getSourceActivityType());
    assertEquals("Target Activity Name", actualCreateSequenceFlowTakenEventResult.getTargetActivityName());
    assertEquals("Target Activity Type", actualCreateSequenceFlowTakenEventResult.getTargetActivityType());
    assertEquals("org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateSequenceFlowTakenEventResult.getSourceActivityBehaviorClass());
    assertEquals("org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateSequenceFlowTakenEventResult.getTargetActivityBehaviorClass());
    assertNull(((ActivitiSequenceFlowTakenEventImpl) actualCreateSequenceFlowTakenEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateSequenceFlowTakenEventResult.getType());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity, ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}
   */
  @Test
  public void testCreateSequenceFlowTakenEvent4() {
    // Arrange
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessDefinitionId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");

    // Act
    ActivitiSequenceFlowTakenEvent actualCreateSequenceFlowTakenEventResult = ActivitiEventBuilder
        .createSequenceFlowTakenEvent(executionEntity, ActivitiEventType.ENTITY_CREATED, "42", "42",
            "Source Activity Name", "Source Activity Type", null, "42", "Target Activity Name", "Target Activity Type",
            JSONObject.NULL);

    // Assert
    verify(executionEntity).getId();
    verify(executionEntity).getProcessDefinitionId();
    verify(executionEntity).getProcessInstanceId();
    assertTrue(actualCreateSequenceFlowTakenEventResult instanceof ActivitiSequenceFlowTakenEventImpl);
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getExecutionId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getProcessInstanceId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getSourceActivityId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getTargetActivityId());
    assertEquals("Source Activity Name", actualCreateSequenceFlowTakenEventResult.getSourceActivityName());
    assertEquals("Source Activity Type", actualCreateSequenceFlowTakenEventResult.getSourceActivityType());
    assertEquals("Target Activity Name", actualCreateSequenceFlowTakenEventResult.getTargetActivityName());
    assertEquals("Target Activity Type", actualCreateSequenceFlowTakenEventResult.getTargetActivityType());
    assertEquals("org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateSequenceFlowTakenEventResult.getTargetActivityBehaviorClass());
    assertNull(actualCreateSequenceFlowTakenEventResult.getSourceActivityBehaviorClass());
    assertNull(((ActivitiSequenceFlowTakenEventImpl) actualCreateSequenceFlowTakenEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateSequenceFlowTakenEventResult.getType());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity, ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}
   */
  @Test
  public void testCreateSequenceFlowTakenEvent5() {
    // Arrange
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getProcessDefinitionId()).thenReturn("42");
    when(executionEntity.getProcessInstanceId()).thenReturn("42");

    // Act
    ActivitiSequenceFlowTakenEvent actualCreateSequenceFlowTakenEventResult = ActivitiEventBuilder
        .createSequenceFlowTakenEvent(executionEntity, ActivitiEventType.ENTITY_CREATED, "42", "42",
            "Source Activity Name", "Source Activity Type", JSONObject.NULL, "42", "Target Activity Name",
            "Target Activity Type", null);

    // Assert
    verify(executionEntity).getId();
    verify(executionEntity).getProcessDefinitionId();
    verify(executionEntity).getProcessInstanceId();
    assertTrue(actualCreateSequenceFlowTakenEventResult instanceof ActivitiSequenceFlowTakenEventImpl);
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getExecutionId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getProcessInstanceId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getSourceActivityId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getTargetActivityId());
    assertEquals("Source Activity Name", actualCreateSequenceFlowTakenEventResult.getSourceActivityName());
    assertEquals("Source Activity Type", actualCreateSequenceFlowTakenEventResult.getSourceActivityType());
    assertEquals("Target Activity Name", actualCreateSequenceFlowTakenEventResult.getTargetActivityName());
    assertEquals("Target Activity Type", actualCreateSequenceFlowTakenEventResult.getTargetActivityType());
    assertEquals("org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateSequenceFlowTakenEventResult.getSourceActivityBehaviorClass());
    assertNull(actualCreateSequenceFlowTakenEventResult.getTargetActivityBehaviorClass());
    assertNull(((ActivitiSequenceFlowTakenEventImpl) actualCreateSequenceFlowTakenEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateSequenceFlowTakenEventResult.getType());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createEntityExceptionEvent(ActivitiEventType, Object, Throwable, String, String, String)}
   */
  @Test
  public void testCreateEntityExceptionEvent() {
    // Arrange
    Object object = JSONObject.NULL;
    Throwable cause = new Throwable();

    // Act
    ActivitiEntityEvent actualCreateEntityExceptionEventResult = ActivitiEventBuilder
        .createEntityExceptionEvent(ActivitiEventType.ENTITY_CREATED, object, cause, "42", "42", "42");

    // Assert
    assertTrue(actualCreateEntityExceptionEventResult instanceof ActivitiEntityExceptionEventImpl);
    assertEquals("42", actualCreateEntityExceptionEventResult.getExecutionId());
    assertEquals("42", actualCreateEntityExceptionEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateEntityExceptionEventResult.getProcessInstanceId());
    assertNull(actualCreateEntityExceptionEventResult.getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateEntityExceptionEventResult.getType());
    assertSame(cause, ((ActivitiEntityExceptionEventImpl) actualCreateEntityExceptionEventResult).getCause());
    assertSame(object, actualCreateEntityExceptionEventResult.getEntity());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, DelegateExecution, FlowElement)}
   */
  @Test
  public void testCreateActivityEvent() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ActivitiActivityEvent actualCreateActivityEventResult = ActivitiEventBuilder
        .createActivityEvent(ActivitiEventType.ENTITY_CREATED, execution, new AdhocSubProcess());

    // Assert
    assertTrue(actualCreateActivityEventResult instanceof ActivitiActivityEventImpl);
    assertEquals("adhocSubProcess", actualCreateActivityEventResult.getActivityType());
    assertNull(actualCreateActivityEventResult.getActivityId());
    assertNull(actualCreateActivityEventResult.getActivityName());
    assertNull(actualCreateActivityEventResult.getBehaviorClass());
    assertNull(actualCreateActivityEventResult.getExecutionId());
    assertNull(actualCreateActivityEventResult.getProcessDefinitionId());
    assertNull(actualCreateActivityEventResult.getProcessInstanceId());
    assertNull(((ActivitiActivityEventImpl) actualCreateActivityEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateActivityEventResult.getType());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, DelegateExecution, FlowElement)}
   */
  @Test
  public void testCreateActivityEvent2() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    AdhocSubProcess flowElement = new AdhocSubProcess();
    flowElement.setBehavior(JSONObject.NULL);

    // Act
    ActivitiActivityEvent actualCreateActivityEventResult = ActivitiEventBuilder
        .createActivityEvent(ActivitiEventType.ENTITY_CREATED, execution, flowElement);

    // Assert
    assertTrue(actualCreateActivityEventResult instanceof ActivitiActivityEventImpl);
    assertEquals("adhocSubProcess", actualCreateActivityEventResult.getActivityType());
    assertEquals("org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateActivityEventResult.getBehaviorClass());
    assertNull(actualCreateActivityEventResult.getActivityId());
    assertNull(actualCreateActivityEventResult.getActivityName());
    assertNull(actualCreateActivityEventResult.getExecutionId());
    assertNull(actualCreateActivityEventResult.getProcessDefinitionId());
    assertNull(actualCreateActivityEventResult.getProcessInstanceId());
    assertNull(((ActivitiActivityEventImpl) actualCreateActivityEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateActivityEventResult.getType());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, DelegateExecution, FlowElement)}
   */
  @Test
  public void testCreateActivityEvent3() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ActivitiActivityEvent actualCreateActivityEventResult = ActivitiEventBuilder
        .createActivityEvent(ActivitiEventType.ENTITY_CREATED, execution, new BooleanDataObject());

    // Assert
    assertTrue(actualCreateActivityEventResult instanceof ActivitiActivityEventImpl);
    assertNull(actualCreateActivityEventResult.getActivityId());
    assertNull(actualCreateActivityEventResult.getActivityName());
    assertNull(actualCreateActivityEventResult.getActivityType());
    assertNull(actualCreateActivityEventResult.getBehaviorClass());
    assertNull(actualCreateActivityEventResult.getExecutionId());
    assertNull(actualCreateActivityEventResult.getProcessDefinitionId());
    assertNull(actualCreateActivityEventResult.getProcessInstanceId());
    assertNull(((ActivitiActivityEventImpl) actualCreateActivityEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateActivityEventResult.getType());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, String, String, String, String, String, FlowElement)}
   */
  @Test
  public void testCreateActivityEvent4() {
    // Arrange and Act
    ActivitiActivityEvent actualCreateActivityEventResult = ActivitiEventBuilder.createActivityEvent(
        ActivitiEventType.ENTITY_CREATED, "42", "Activity Name", "42", "42", "42", new AdhocSubProcess());

    // Assert
    assertTrue(actualCreateActivityEventResult instanceof ActivitiActivityEventImpl);
    assertEquals("42", actualCreateActivityEventResult.getActivityId());
    assertEquals("42", actualCreateActivityEventResult.getExecutionId());
    assertEquals("42", actualCreateActivityEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateActivityEventResult.getProcessInstanceId());
    assertEquals("Activity Name", actualCreateActivityEventResult.getActivityName());
    assertEquals("adhocSubProcess", actualCreateActivityEventResult.getActivityType());
    assertNull(actualCreateActivityEventResult.getBehaviorClass());
    assertNull(((ActivitiActivityEventImpl) actualCreateActivityEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateActivityEventResult.getType());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, String, String, String, String, String, FlowElement)}
   */
  @Test
  public void testCreateActivityEvent5() {
    // Arrange and Act
    ActivitiActivityEvent actualCreateActivityEventResult = ActivitiEventBuilder
        .createActivityEvent(ActivitiEventType.ENTITY_CREATED, "42", "Activity Name", "42", "42", "42", null);

    // Assert
    assertTrue(actualCreateActivityEventResult instanceof ActivitiActivityEventImpl);
    assertEquals("42", actualCreateActivityEventResult.getActivityId());
    assertEquals("42", actualCreateActivityEventResult.getExecutionId());
    assertEquals("42", actualCreateActivityEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateActivityEventResult.getProcessInstanceId());
    assertEquals("Activity Name", actualCreateActivityEventResult.getActivityName());
    assertNull(actualCreateActivityEventResult.getActivityType());
    assertNull(actualCreateActivityEventResult.getBehaviorClass());
    assertNull(((ActivitiActivityEventImpl) actualCreateActivityEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateActivityEventResult.getType());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#mayBeResolveExpression(String, DelegateExecution)}
   */
  @Test
  public void testMayBeResolveExpression() {
    // Arrange, Act and Assert
    assertEquals("",
        ActivitiEventBuilder.mayBeResolveExpression("", ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test: {@link ActivitiEventBuilder#parseActivityType(FlowNode)}
   */
  @Test
  public void testParseActivityType() {
    // Arrange, Act and Assert
    assertEquals("adhocSubProcess", ActivitiEventBuilder.parseActivityType(new AdhocSubProcess()));
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#parseActivityBehavior(FlowNode)}
   */
  @Test
  public void testParseActivityBehavior() {
    // Arrange, Act and Assert
    assertNull(ActivitiEventBuilder.parseActivityBehavior(new AdhocSubProcess()));
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#parseActivityBehavior(FlowNode)}
   */
  @Test
  public void testParseActivityBehavior2() {
    // Arrange
    AdhocSubProcess flowNode = new AdhocSubProcess();
    flowNode.setBehavior(JSONObject.NULL);

    // Act and Assert
    assertEquals("org.activiti.engine.impl.util.json.JSONObject.Null",
        ActivitiEventBuilder.parseActivityBehavior(flowNode));
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createActivityCancelledEvent(ExecutionEntity, Object)}
   */
  @Test
  public void testCreateActivityCancelledEvent() {
    // Arrange
    ExecutionEntity execution = mock(ExecutionEntity.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getActivityId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    Object object = JSONObject.NULL;

    // Act
    ActivitiActivityCancelledEvent actualCreateActivityCancelledEventResult = ActivitiEventBuilder
        .createActivityCancelledEvent(execution, object);

    // Assert
    verify(execution).getCurrentFlowElement();
    verify(execution).getId();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceId();
    verify(execution).getActivityId();
    assertTrue(actualCreateActivityCancelledEventResult instanceof ActivitiActivityCancelledEventImpl);
    assertEquals("42", actualCreateActivityCancelledEventResult.getActivityId());
    assertEquals("42", actualCreateActivityCancelledEventResult.getExecutionId());
    assertEquals("42", actualCreateActivityCancelledEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateActivityCancelledEventResult.getProcessInstanceId());
    assertEquals("adhocSubProcess", actualCreateActivityCancelledEventResult.getActivityType());
    assertNull(actualCreateActivityCancelledEventResult.getActivityName());
    assertNull(actualCreateActivityCancelledEventResult.getBehaviorClass());
    assertNull(((ActivitiActivityCancelledEventImpl) actualCreateActivityCancelledEventResult).getReason());
    assertEquals(ActivitiEventType.ACTIVITY_CANCELLED, actualCreateActivityCancelledEventResult.getType());
    assertSame(object, actualCreateActivityCancelledEventResult.getCause());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createActivityCancelledEvent(ExecutionEntity, Object)}
   */
  @Test
  public void testCreateActivityCancelledEvent2() {
    // Arrange
    ExecutionEntity execution = mock(ExecutionEntity.class);
    when(execution.getId()).thenThrow(new ActivitiException("An error occurred"));
    when(execution.getActivityId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> ActivitiEventBuilder.createActivityCancelledEvent(execution, JSONObject.NULL));
    verify(execution).getCurrentFlowElement();
    verify(execution).getId();
    verify(execution).getActivityId();
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createActivityCancelledEvent(String, String, String, String, String, String, Object)}
   */
  @Test
  public void testCreateActivityCancelledEvent3() {
    // Arrange
    Object object = JSONObject.NULL;

    // Act
    ActivitiActivityCancelledEvent actualCreateActivityCancelledEventResult = ActivitiEventBuilder
        .createActivityCancelledEvent("42", "Activity Name", "42", "42", "42", "Activity Type", object);

    // Assert
    assertTrue(actualCreateActivityCancelledEventResult instanceof ActivitiActivityCancelledEventImpl);
    assertEquals("42", actualCreateActivityCancelledEventResult.getActivityId());
    assertEquals("42", actualCreateActivityCancelledEventResult.getExecutionId());
    assertEquals("42", actualCreateActivityCancelledEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateActivityCancelledEventResult.getProcessInstanceId());
    assertEquals("Activity Name", actualCreateActivityCancelledEventResult.getActivityName());
    assertEquals("Activity Type", actualCreateActivityCancelledEventResult.getActivityType());
    assertNull(actualCreateActivityCancelledEventResult.getBehaviorClass());
    assertNull(((ActivitiActivityCancelledEventImpl) actualCreateActivityCancelledEventResult).getReason());
    assertEquals(ActivitiEventType.ACTIVITY_CANCELLED, actualCreateActivityCancelledEventResult.getType());
    assertSame(object, actualCreateActivityCancelledEventResult.getCause());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createProcessCancelledEvent(ProcessInstance, Object)}
   */
  @Test
  public void testCreateProcessCancelledEvent() {
    // Arrange
    ExecutionEntityImpl processInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    Object object = JSONObject.NULL;

    // Act
    ActivitiProcessCancelledEvent actualCreateProcessCancelledEventResult = ActivitiEventBuilder
        .createProcessCancelledEvent(processInstance, object);

    // Assert
    assertTrue(actualCreateProcessCancelledEventResult instanceof ActivitiProcessCancelledEventImpl);
    assertNull(actualCreateProcessCancelledEventResult.getReason());
    assertNull(actualCreateProcessCancelledEventResult.getExecutionId());
    assertNull(actualCreateProcessCancelledEventResult.getProcessDefinitionId());
    assertNull(actualCreateProcessCancelledEventResult.getProcessInstanceId());
    assertEquals(ActivitiEventType.PROCESS_CANCELLED, actualCreateProcessCancelledEventResult.getType());
    assertSame(processInstance, actualCreateProcessCancelledEventResult.getEntity());
    assertSame(object, actualCreateProcessCancelledEventResult.getCause());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createProcessCancelledEvent(ProcessInstance, Object)}
   */
  @Test
  public void testCreateProcessCancelledEvent2() {
    // Arrange
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenReturn("42");
    when(processInstance.getProcessInstanceId()).thenReturn("42");
    when(processInstance.getProcessDefinitionId()).thenReturn("42");
    Object object = JSONObject.NULL;

    // Act
    ActivitiProcessCancelledEvent actualCreateProcessCancelledEventResult = ActivitiEventBuilder
        .createProcessCancelledEvent(processInstance, object);

    // Assert
    verify(processInstance).getId();
    verify(processInstance).getProcessInstanceId();
    verify(processInstance).getProcessDefinitionId();
    assertTrue(actualCreateProcessCancelledEventResult instanceof ActivitiProcessCancelledEventImpl);
    assertEquals("42", actualCreateProcessCancelledEventResult.getExecutionId());
    assertEquals("42", actualCreateProcessCancelledEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateProcessCancelledEventResult.getProcessInstanceId());
    assertNull(actualCreateProcessCancelledEventResult.getReason());
    assertEquals(ActivitiEventType.PROCESS_CANCELLED, actualCreateProcessCancelledEventResult.getType());
    assertSame(object, actualCreateProcessCancelledEventResult.getCause());
    assertSame(processInstance, actualCreateProcessCancelledEventResult.getEntity());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createProcessCancelledEvent(ProcessInstance, Object)}
   */
  @Test
  public void testCreateProcessCancelledEvent3() {
    // Arrange
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> ActivitiEventBuilder.createProcessCancelledEvent(processInstance, JSONObject.NULL));
    verify(processInstance).getId();
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String, Object)}
   */
  @Test
  public void testCreateActivitiySignalledEvent() {
    // Arrange
    Object object = JSONObject.NULL;

    // Act
    ActivitiSignalEvent actualCreateActivitiySignalledEventResult = ActivitiEventBuilder.createActivitiySignalledEvent(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Signal Name", object);

    // Assert
    assertTrue(actualCreateActivitiySignalledEventResult instanceof ActivitiSignalEventImpl);
    assertEquals("Signal Name", actualCreateActivitiySignalledEventResult.getSignalName());
    assertNull(actualCreateActivitiySignalledEventResult.getActivityId());
    assertNull(actualCreateActivitiySignalledEventResult.getActivityName());
    assertNull(actualCreateActivitiySignalledEventResult.getActivityType());
    assertNull(actualCreateActivitiySignalledEventResult.getBehaviorClass());
    assertNull(actualCreateActivitiySignalledEventResult.getExecutionId());
    assertNull(actualCreateActivitiySignalledEventResult.getProcessDefinitionId());
    assertNull(actualCreateActivitiySignalledEventResult.getProcessInstanceId());
    assertNull(((ActivitiSignalEventImpl) actualCreateActivitiySignalledEventResult).getReason());
    assertEquals(ActivitiEventType.ACTIVITY_SIGNALED, actualCreateActivitiySignalledEventResult.getType());
    assertSame(object, actualCreateActivitiySignalledEventResult.getSignalData());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String, Object)}
   */
  @Test
  public void testCreateActivitiySignalledEvent2() {
    // Arrange
    Object object = JSONObject.NULL;

    // Act
    ActivitiSignalEvent actualCreateActivitiySignalledEventResult = ActivitiEventBuilder
        .createActivitiySignalledEvent(null, "Signal Name", object);

    // Assert
    assertTrue(actualCreateActivitiySignalledEventResult instanceof ActivitiSignalEventImpl);
    assertEquals("Signal Name", actualCreateActivitiySignalledEventResult.getSignalName());
    assertNull(actualCreateActivitiySignalledEventResult.getActivityId());
    assertNull(actualCreateActivitiySignalledEventResult.getActivityName());
    assertNull(actualCreateActivitiySignalledEventResult.getActivityType());
    assertNull(actualCreateActivitiySignalledEventResult.getBehaviorClass());
    assertNull(actualCreateActivitiySignalledEventResult.getExecutionId());
    assertNull(actualCreateActivitiySignalledEventResult.getProcessDefinitionId());
    assertNull(actualCreateActivitiySignalledEventResult.getProcessInstanceId());
    assertNull(((ActivitiSignalEventImpl) actualCreateActivitiySignalledEventResult).getReason());
    assertEquals(ActivitiEventType.ACTIVITY_SIGNALED, actualCreateActivitiySignalledEventResult.getType());
    assertSame(object, actualCreateActivitiySignalledEventResult.getSignalData());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String, Object)}
   */
  @Test
  public void testCreateActivitiySignalledEvent3() {
    // Arrange
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    Object object = JSONObject.NULL;

    // Act
    ActivitiSignalEvent actualCreateActivitiySignalledEventResult = ActivitiEventBuilder
        .createActivitiySignalledEvent(execution, "Signal Name", object);

    // Assert
    verify(execution).getCurrentActivityId();
    verify(execution, atLeast(1)).getCurrentFlowElement();
    verify(execution).getId();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceId();
    assertTrue(actualCreateActivitiySignalledEventResult instanceof ActivitiSignalEventImpl);
    assertEquals("42", actualCreateActivitiySignalledEventResult.getActivityId());
    assertEquals("42", actualCreateActivitiySignalledEventResult.getExecutionId());
    assertEquals("42", actualCreateActivitiySignalledEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateActivitiySignalledEventResult.getProcessInstanceId());
    assertEquals("Signal Name", actualCreateActivitiySignalledEventResult.getSignalName());
    assertEquals("adhocSubProcess", actualCreateActivitiySignalledEventResult.getActivityType());
    assertNull(actualCreateActivitiySignalledEventResult.getActivityName());
    assertNull(actualCreateActivitiySignalledEventResult.getBehaviorClass());
    assertNull(((ActivitiSignalEventImpl) actualCreateActivitiySignalledEventResult).getReason());
    assertEquals(ActivitiEventType.ACTIVITY_SIGNALED, actualCreateActivitiySignalledEventResult.getType());
    assertSame(object, actualCreateActivitiySignalledEventResult.getSignalData());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createMessageReceivedEvent(DelegateExecution, String, String, Object)}
   */
  @Test
  public void testCreateMessageReceivedEvent() {
    // Arrange
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    Object object = JSONObject.NULL;

    // Act
    ActivitiMessageEvent actualCreateMessageReceivedEventResult = ActivitiEventBuilder
        .createMessageReceivedEvent(execution, "Message Name", "Correlation Key", object);

    // Assert
    verify(execution).getId();
    verify(execution).getCurrentActivityId();
    verify(execution, atLeast(1)).getCurrentFlowElement();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceBusinessKey();
    verify(execution).getProcessInstanceId();
    assertTrue(actualCreateMessageReceivedEventResult instanceof ActivitiMessageEventImpl);
    assertEquals("42", actualCreateMessageReceivedEventResult.getActivityId());
    assertEquals("42", actualCreateMessageReceivedEventResult.getExecutionId());
    assertEquals("42", actualCreateMessageReceivedEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateMessageReceivedEventResult.getProcessInstanceId());
    assertEquals("Correlation Key", actualCreateMessageReceivedEventResult.getMessageCorrelationKey());
    assertEquals("Message Name", actualCreateMessageReceivedEventResult.getMessageName());
    assertEquals("Process Instance Business Key", actualCreateMessageReceivedEventResult.getMessageBusinessKey());
    assertEquals("adhocSubProcess", actualCreateMessageReceivedEventResult.getActivityType());
    assertNull(actualCreateMessageReceivedEventResult.getActivityName());
    assertNull(actualCreateMessageReceivedEventResult.getBehaviorClass());
    assertNull(((ActivitiMessageEventImpl) actualCreateMessageReceivedEventResult).getReason());
    assertEquals(ActivitiEventType.ACTIVITY_MESSAGE_RECEIVED, actualCreateMessageReceivedEventResult.getType());
    assertSame(object, actualCreateMessageReceivedEventResult.getMessageData());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createMessageReceivedEvent(DelegateExecution, String, String, Object)}
   */
  @Test
  public void testCreateMessageReceivedEvent2() {
    // Arrange
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(null);
    Object object = JSONObject.NULL;

    // Act
    ActivitiMessageEvent actualCreateMessageReceivedEventResult = ActivitiEventBuilder
        .createMessageReceivedEvent(execution, "Message Name", "Correlation Key", object);

    // Assert
    verify(execution).getId();
    verify(execution).getCurrentActivityId();
    verify(execution).getCurrentFlowElement();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceBusinessKey();
    verify(execution).getProcessInstanceId();
    assertTrue(actualCreateMessageReceivedEventResult instanceof ActivitiMessageEventImpl);
    assertEquals("42", actualCreateMessageReceivedEventResult.getActivityId());
    assertEquals("42", actualCreateMessageReceivedEventResult.getExecutionId());
    assertEquals("42", actualCreateMessageReceivedEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateMessageReceivedEventResult.getProcessInstanceId());
    assertEquals("Correlation Key", actualCreateMessageReceivedEventResult.getMessageCorrelationKey());
    assertEquals("Message Name", actualCreateMessageReceivedEventResult.getMessageName());
    assertEquals("Process Instance Business Key", actualCreateMessageReceivedEventResult.getMessageBusinessKey());
    assertNull(actualCreateMessageReceivedEventResult.getActivityName());
    assertNull(actualCreateMessageReceivedEventResult.getActivityType());
    assertNull(actualCreateMessageReceivedEventResult.getBehaviorClass());
    assertNull(((ActivitiMessageEventImpl) actualCreateMessageReceivedEventResult).getReason());
    assertEquals(ActivitiEventType.ACTIVITY_MESSAGE_RECEIVED, actualCreateMessageReceivedEventResult.getType());
    assertSame(object, actualCreateMessageReceivedEventResult.getMessageData());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createMessageWaitingEvent(DelegateExecution, String, String)}
   */
  @Test
  public void testCreateMessageWaitingEvent() {
    // Arrange
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    // Act
    ActivitiMessageEvent actualCreateMessageWaitingEventResult = ActivitiEventBuilder
        .createMessageWaitingEvent(execution, "Message Name", "Correlation Key");

    // Assert
    verify(execution).getId();
    verify(execution).getCurrentActivityId();
    verify(execution, atLeast(1)).getCurrentFlowElement();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceBusinessKey();
    verify(execution).getProcessInstanceId();
    assertTrue(actualCreateMessageWaitingEventResult instanceof ActivitiMessageEventImpl);
    assertEquals("42", actualCreateMessageWaitingEventResult.getActivityId());
    assertEquals("42", actualCreateMessageWaitingEventResult.getExecutionId());
    assertEquals("42", actualCreateMessageWaitingEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateMessageWaitingEventResult.getProcessInstanceId());
    assertEquals("Correlation Key", actualCreateMessageWaitingEventResult.getMessageCorrelationKey());
    assertEquals("Message Name", actualCreateMessageWaitingEventResult.getMessageName());
    assertEquals("Process Instance Business Key", actualCreateMessageWaitingEventResult.getMessageBusinessKey());
    assertEquals("adhocSubProcess", actualCreateMessageWaitingEventResult.getActivityType());
    assertNull(actualCreateMessageWaitingEventResult.getMessageData());
    assertNull(actualCreateMessageWaitingEventResult.getActivityName());
    assertNull(actualCreateMessageWaitingEventResult.getBehaviorClass());
    assertNull(((ActivitiMessageEventImpl) actualCreateMessageWaitingEventResult).getReason());
    assertEquals(ActivitiEventType.ACTIVITY_MESSAGE_WAITING, actualCreateMessageWaitingEventResult.getType());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createMessageWaitingEvent(DelegateExecution, String, String)}
   */
  @Test
  public void testCreateMessageWaitingEvent2() {
    // Arrange
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(null);

    // Act
    ActivitiMessageEvent actualCreateMessageWaitingEventResult = ActivitiEventBuilder
        .createMessageWaitingEvent(execution, "Message Name", "Correlation Key");

    // Assert
    verify(execution).getId();
    verify(execution).getCurrentActivityId();
    verify(execution).getCurrentFlowElement();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceBusinessKey();
    verify(execution).getProcessInstanceId();
    assertTrue(actualCreateMessageWaitingEventResult instanceof ActivitiMessageEventImpl);
    assertEquals("42", actualCreateMessageWaitingEventResult.getActivityId());
    assertEquals("42", actualCreateMessageWaitingEventResult.getExecutionId());
    assertEquals("42", actualCreateMessageWaitingEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateMessageWaitingEventResult.getProcessInstanceId());
    assertEquals("Correlation Key", actualCreateMessageWaitingEventResult.getMessageCorrelationKey());
    assertEquals("Message Name", actualCreateMessageWaitingEventResult.getMessageName());
    assertEquals("Process Instance Business Key", actualCreateMessageWaitingEventResult.getMessageBusinessKey());
    assertNull(actualCreateMessageWaitingEventResult.getMessageData());
    assertNull(actualCreateMessageWaitingEventResult.getActivityName());
    assertNull(actualCreateMessageWaitingEventResult.getActivityType());
    assertNull(actualCreateMessageWaitingEventResult.getBehaviorClass());
    assertNull(((ActivitiMessageEventImpl) actualCreateMessageWaitingEventResult).getReason());
    assertEquals(ActivitiEventType.ACTIVITY_MESSAGE_WAITING, actualCreateMessageWaitingEventResult.getType());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createMessageSentEvent(DelegateExecution, String, String, Object)}
   */
  @Test
  public void testCreateMessageSentEvent() {
    // Arrange
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    Object object = JSONObject.NULL;

    // Act
    ActivitiMessageEvent actualCreateMessageSentEventResult = ActivitiEventBuilder.createMessageSentEvent(execution,
        "Message Name", "Correlation Key", object);

    // Assert
    verify(execution).getId();
    verify(execution).getCurrentActivityId();
    verify(execution, atLeast(1)).getCurrentFlowElement();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceBusinessKey();
    verify(execution).getProcessInstanceId();
    assertTrue(actualCreateMessageSentEventResult instanceof ActivitiMessageEventImpl);
    assertEquals("42", actualCreateMessageSentEventResult.getActivityId());
    assertEquals("42", actualCreateMessageSentEventResult.getExecutionId());
    assertEquals("42", actualCreateMessageSentEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateMessageSentEventResult.getProcessInstanceId());
    assertEquals("Correlation Key", actualCreateMessageSentEventResult.getMessageCorrelationKey());
    assertEquals("Message Name", actualCreateMessageSentEventResult.getMessageName());
    assertEquals("Process Instance Business Key", actualCreateMessageSentEventResult.getMessageBusinessKey());
    assertEquals("adhocSubProcess", actualCreateMessageSentEventResult.getActivityType());
    assertNull(actualCreateMessageSentEventResult.getActivityName());
    assertNull(actualCreateMessageSentEventResult.getBehaviorClass());
    assertNull(((ActivitiMessageEventImpl) actualCreateMessageSentEventResult).getReason());
    assertEquals(ActivitiEventType.ACTIVITY_MESSAGE_SENT, actualCreateMessageSentEventResult.getType());
    assertSame(object, actualCreateMessageSentEventResult.getMessageData());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createMessageSentEvent(DelegateExecution, String, String, Object)}
   */
  @Test
  public void testCreateMessageSentEvent2() {
    // Arrange
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(null);
    Object object = JSONObject.NULL;

    // Act
    ActivitiMessageEvent actualCreateMessageSentEventResult = ActivitiEventBuilder.createMessageSentEvent(execution,
        "Message Name", "Correlation Key", object);

    // Assert
    verify(execution).getId();
    verify(execution).getCurrentActivityId();
    verify(execution).getCurrentFlowElement();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceBusinessKey();
    verify(execution).getProcessInstanceId();
    assertTrue(actualCreateMessageSentEventResult instanceof ActivitiMessageEventImpl);
    assertEquals("42", actualCreateMessageSentEventResult.getActivityId());
    assertEquals("42", actualCreateMessageSentEventResult.getExecutionId());
    assertEquals("42", actualCreateMessageSentEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateMessageSentEventResult.getProcessInstanceId());
    assertEquals("Correlation Key", actualCreateMessageSentEventResult.getMessageCorrelationKey());
    assertEquals("Message Name", actualCreateMessageSentEventResult.getMessageName());
    assertEquals("Process Instance Business Key", actualCreateMessageSentEventResult.getMessageBusinessKey());
    assertNull(actualCreateMessageSentEventResult.getActivityName());
    assertNull(actualCreateMessageSentEventResult.getActivityType());
    assertNull(actualCreateMessageSentEventResult.getBehaviorClass());
    assertNull(((ActivitiMessageEventImpl) actualCreateMessageSentEventResult).getReason());
    assertEquals(ActivitiEventType.ACTIVITY_MESSAGE_SENT, actualCreateMessageSentEventResult.getType());
    assertSame(object, actualCreateMessageSentEventResult.getMessageData());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createErrorEvent(ActivitiEventType, String, String, String, String, String, String)}
   */
  @Test
  public void testCreateErrorEvent() {
    // Arrange and Act
    ActivitiErrorEvent actualCreateErrorEventResult = ActivitiEventBuilder.createErrorEvent(
        ActivitiEventType.ENTITY_CREATED, "42", "An error occurred", "An error occurred", "42", "42", "42");

    // Assert
    assertTrue(actualCreateErrorEventResult instanceof ActivitiErrorEventImpl);
    assertEquals("42", actualCreateErrorEventResult.getActivityId());
    assertEquals("42", actualCreateErrorEventResult.getExecutionId());
    assertEquals("42", actualCreateErrorEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateErrorEventResult.getProcessInstanceId());
    assertEquals("An error occurred", actualCreateErrorEventResult.getErrorCode());
    assertEquals("An error occurred", actualCreateErrorEventResult.getErrorId());
    assertNull(actualCreateErrorEventResult.getActivityName());
    assertNull(actualCreateErrorEventResult.getActivityType());
    assertNull(actualCreateErrorEventResult.getBehaviorClass());
    assertNull(((ActivitiErrorEventImpl) actualCreateErrorEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateErrorEventResult.getType());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createVariableEvent(ActivitiEventType, String, Object, VariableType, String, String, String, String)}
   */
  @Test
  public void testCreateVariableEvent() {
    // Arrange
    Object object = JSONObject.NULL;
    BigDecimalType variableType = new BigDecimalType();

    // Act
    ActivitiVariableEvent actualCreateVariableEventResult = ActivitiEventBuilder.createVariableEvent(
        ActivitiEventType.ENTITY_CREATED, "Variable Name", object, variableType, "42", "42", "42", "42");

    // Assert
    assertTrue(actualCreateVariableEventResult instanceof ActivitiVariableEventImpl);
    assertEquals("42", actualCreateVariableEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateVariableEventResult.getProcessInstanceId());
    assertEquals("42", actualCreateVariableEventResult.getExecutionId());
    assertEquals("42", actualCreateVariableEventResult.getTaskId());
    assertEquals("Variable Name", actualCreateVariableEventResult.getVariableName());
    assertNull(((ActivitiVariableEventImpl) actualCreateVariableEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateVariableEventResult.getType());
    assertSame(variableType, actualCreateVariableEventResult.getVariableType());
    assertSame(object, actualCreateVariableEventResult.getVariableValue());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createVariableEvent(ActivitiEventType, String, Object, VariableType, String, String, String, String)}
   */
  @Test
  public void testCreateVariableEvent2() {
    // Arrange
    Object object = JSONObject.NULL;
    BigDecimalType variableType = mock(BigDecimalType.class);

    // Act
    ActivitiVariableEvent actualCreateVariableEventResult = ActivitiEventBuilder.createVariableEvent(
        ActivitiEventType.ENTITY_CREATED, "Variable Name", object, variableType, "42", "42", "42", "42");

    // Assert
    assertTrue(actualCreateVariableEventResult instanceof ActivitiVariableEventImpl);
    assertEquals("42", actualCreateVariableEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateVariableEventResult.getProcessInstanceId());
    assertEquals("42", actualCreateVariableEventResult.getExecutionId());
    assertEquals("42", actualCreateVariableEventResult.getTaskId());
    assertEquals("Variable Name", actualCreateVariableEventResult.getVariableName());
    assertNull(((ActivitiVariableEventImpl) actualCreateVariableEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateVariableEventResult.getType());
    assertSame(object, actualCreateVariableEventResult.getVariableValue());
    assertSame(variableType, actualCreateVariableEventResult.getVariableType());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createVariableUpdateEvent(VariableInstanceEntity, Object, String, String)}
   */
  @Test
  public void testCreateVariableUpdateEvent() {
    // Arrange
    VariableInstanceEntityImpl variableInstance = new VariableInstanceEntityImpl();
    BigDecimalType type = new BigDecimalType();
    variableInstance.setType(type);
    Object object = JSONObject.NULL;

    // Act
    ActivitiVariableUpdatedEventImpl actualCreateVariableUpdateEventResult = ActivitiEventBuilder
        .createVariableUpdateEvent(variableInstance, object, "42", "42");

    // Assert
    assertEquals("42", actualCreateVariableUpdateEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateVariableUpdateEventResult.getProcessInstanceId());
    assertNull(actualCreateVariableUpdateEventResult.getVariableValue());
    assertNull(actualCreateVariableUpdateEventResult.getExecutionId());
    assertNull(actualCreateVariableUpdateEventResult.getReason());
    assertNull(actualCreateVariableUpdateEventResult.getTaskId());
    assertNull(actualCreateVariableUpdateEventResult.getVariableName());
    assertEquals(ActivitiEventType.VARIABLE_UPDATED, actualCreateVariableUpdateEventResult.getType());
    assertSame(type, actualCreateVariableUpdateEventResult.getVariableType());
    assertSame(object, actualCreateVariableUpdateEventResult.getVariablePreviousValue());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createVariableUpdateEvent(VariableInstanceEntity, Object, String, String)}
   */
  @Test
  public void testCreateVariableUpdateEvent2() {
    // Arrange
    VariableInstanceEntityImpl variableInstance = mock(VariableInstanceEntityImpl.class);
    when(variableInstance.getValue()).thenReturn(JSONObject.NULL);
    when(variableInstance.getExecutionId()).thenReturn("42");
    when(variableInstance.getName()).thenReturn("Name");
    when(variableInstance.getTaskId()).thenReturn("42");
    BigDecimalType bigDecimalType = new BigDecimalType();
    when(variableInstance.getType()).thenReturn(bigDecimalType);
    Object object = JSONObject.NULL;

    // Act
    ActivitiVariableUpdatedEventImpl actualCreateVariableUpdateEventResult = ActivitiEventBuilder
        .createVariableUpdateEvent(variableInstance, object, "42", "42");

    // Assert
    verify(variableInstance).getExecutionId();
    verify(variableInstance).getName();
    verify(variableInstance).getTaskId();
    verify(variableInstance).getType();
    verify(variableInstance).getValue();
    assertEquals("42", actualCreateVariableUpdateEventResult.getExecutionId());
    assertEquals("42", actualCreateVariableUpdateEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateVariableUpdateEventResult.getProcessInstanceId());
    assertEquals("42", actualCreateVariableUpdateEventResult.getTaskId());
    assertEquals("Name", actualCreateVariableUpdateEventResult.getVariableName());
    assertNull(actualCreateVariableUpdateEventResult.getReason());
    assertEquals(ActivitiEventType.VARIABLE_UPDATED, actualCreateVariableUpdateEventResult.getType());
    assertSame(bigDecimalType, actualCreateVariableUpdateEventResult.getVariableType());
    assertSame(object, actualCreateVariableUpdateEventResult.getVariableValue());
    assertSame(object, actualCreateVariableUpdateEventResult.getVariablePreviousValue());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#createMembershipEvent(ActivitiEventType, String, String)}
   */
  @Test
  public void testCreateMembershipEvent() {
    // Arrange and Act
    ActivitiMembershipEvent actualCreateMembershipEventResult = ActivitiEventBuilder
        .createMembershipEvent(ActivitiEventType.ENTITY_CREATED, "42", "42");

    // Assert
    assertTrue(actualCreateMembershipEventResult instanceof ActivitiMembershipEventImpl);
    assertEquals("42", actualCreateMembershipEventResult.getGroupId());
    assertEquals("42", actualCreateMembershipEventResult.getUserId());
    assertNull(actualCreateMembershipEventResult.getExecutionId());
    assertNull(actualCreateMembershipEventResult.getProcessDefinitionId());
    assertNull(actualCreateMembershipEventResult.getProcessInstanceId());
    assertNull(((ActivitiMembershipEventImpl) actualCreateMembershipEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateMembershipEventResult.getType());
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}
   */
  @Test
  public void testPopulateEventWithCurrentContext() {
    // Arrange
    ActivitiEntityEventImpl event = mock(ActivitiEntityEventImpl.class);
    when(event.getEntity()).thenReturn(JSONObject.NULL);

    // Act
    ActivitiEventBuilder.populateEventWithCurrentContext(event);

    // Assert that nothing has changed
    verify(event).getEntity();
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}
   */
  @Test
  public void testPopulateEventWithCurrentContext2() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDeleted(true);
    deadLetterJobEntityImpl
        .setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    deadLetterJobEntityImpl
        .setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    deadLetterJobEntityImpl.setExceptionMessage("An error occurred");
    deadLetterJobEntityImpl.setExclusive(true);
    deadLetterJobEntityImpl.setExecutionId("42");
    deadLetterJobEntityImpl.setId("42");
    deadLetterJobEntityImpl.setInserted(true);
    deadLetterJobEntityImpl.setJobHandlerConfiguration("Job Handler Configuration");
    deadLetterJobEntityImpl.setJobHandlerType("Job Handler Type");
    deadLetterJobEntityImpl.setJobType("Job Type");
    deadLetterJobEntityImpl.setMaxIterations(3);
    deadLetterJobEntityImpl.setProcessDefinitionId("42");
    deadLetterJobEntityImpl.setProcessInstanceId("42");
    deadLetterJobEntityImpl.setRepeat("Repeat");
    deadLetterJobEntityImpl.setRetries(1);
    deadLetterJobEntityImpl.setRevision(1);
    deadLetterJobEntityImpl.setTenantId("42");
    deadLetterJobEntityImpl.setUpdated(true);
    ActivitiEntityEventImpl event = mock(ActivitiEntityEventImpl.class);
    doNothing().when(event).setExecutionId(Mockito.<String>any());
    doNothing().when(event).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(event).setProcessInstanceId(Mockito.<String>any());
    when(event.getEntity()).thenReturn(deadLetterJobEntityImpl);

    // Act
    ActivitiEventBuilder.populateEventWithCurrentContext(event);

    // Assert that nothing has changed
    verify(event).getEntity();
    verify(event).setExecutionId(eq("42"));
    verify(event).setProcessDefinitionId(eq("42"));
    verify(event).setProcessInstanceId(eq("42"));
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}
   */
  @Test
  public void testPopulateEventWithCurrentContext3() {
    // Arrange
    ActivitiEntityEventImpl event = mock(ActivitiEntityEventImpl.class);
    when(event.getEntity()).thenReturn(new IdentityLinkEntityImpl());

    // Act
    ActivitiEventBuilder.populateEventWithCurrentContext(event);

    // Assert that nothing has changed
    verify(event).getEntity();
  }

  /**
   * Method under test:
   * {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}
   */
  @Test
  public void testPopulateEventWithCurrentContext4() {
    // Arrange
    ActivitiEntityEventImpl event = mock(ActivitiEntityEventImpl.class);
    doNothing().when(event).setExecutionId(Mockito.<String>any());
    doNothing().when(event).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(event).setProcessInstanceId(Mockito.<String>any());
    when(event.getEntity()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    ActivitiEventBuilder.populateEventWithCurrentContext(event);

    // Assert that nothing has changed
    verify(event).getEntity();
    verify(event).setExecutionId(isNull());
    verify(event).setProcessDefinitionId(isNull());
    verify(event).setProcessInstanceId(isNull());
  }
}
