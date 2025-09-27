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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
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
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ActivitiEventBuilderDiffblueTest {
  /**
   * Test {@link ActivitiEventBuilder#createGlobalEvent(ActivitiEventType)}.
   *
   * <ul>
   *   <li>When {@code ENTITY_CREATED}.
   *   <li>Then return {@link ActivitiEventImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createGlobalEvent(ActivitiEventType)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ActivitiEvent ActivitiEventBuilder.createGlobalEvent(ActivitiEventType)"})
  public void testCreateGlobalEvent_whenEntityCreated_thenReturnActivitiEventImpl() {
    // Arrange and Act
    ActivitiEvent actualCreateGlobalEventResult =
        ActivitiEventBuilder.createGlobalEvent(ActivitiEventType.ENTITY_CREATED);

    // Assert
    assertTrue(actualCreateGlobalEventResult instanceof ActivitiEventImpl);
    assertNull(actualCreateGlobalEventResult.getExecutionId());
    assertNull(actualCreateGlobalEventResult.getProcessDefinitionId());
    assertNull(actualCreateGlobalEventResult.getProcessInstanceId());
    assertNull(((ActivitiEventImpl) actualCreateGlobalEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateGlobalEventResult.getType());
  }

  /**
   * Test {@link ActivitiEventBuilder#createEvent(ActivitiEventType, String, String, String)}.
   *
   * <ul>
   *   <li>When {@code ENTITY_CREATED}.
   *   <li>Then return {@link ActivitiEventImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createEvent(ActivitiEventType, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiEvent ActivitiEventBuilder.createEvent(ActivitiEventType, String, String, String)"
  })
  public void testCreateEvent_whenEntityCreated_thenReturnActivitiEventImpl() {
    // Arrange and Act
    ActivitiEvent actualCreateEventResult =
        ActivitiEventBuilder.createEvent(ActivitiEventType.ENTITY_CREATED, "42", "42", "42");

    // Assert
    assertTrue(actualCreateEventResult instanceof ActivitiEventImpl);
    assertEquals("42", actualCreateEventResult.getExecutionId());
    assertEquals("42", actualCreateEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateEventResult.getProcessInstanceId());
    assertNull(((ActivitiEventImpl) actualCreateEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateEventResult.getType());
  }

  /**
   * Test {@link ActivitiEventBuilder#createEntityEvent(ActivitiEventType, Object, String, String,
   * String)} with {@code type}, {@code entity}, {@code executionId}, {@code processInstanceId},
   * {@code processDefinitionId}.
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createEntityEvent(ActivitiEventType, Object,
   * String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiEntityEvent ActivitiEventBuilder.createEntityEvent(ActivitiEventType, Object, String, String, String)"
  })
  public void testCreateEntityEventWithTypeEntityExecutionIdProcessInstanceIdProcessDefinitionId() {
    // Arrange
    Object object = JSONObject.NULL;

    // Act
    ActivitiEntityEvent actualCreateEntityEventResult =
        ActivitiEventBuilder.createEntityEvent(
            ActivitiEventType.ENTITY_CREATED, object, "42", "42", "42");

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
   * Test {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity,
   * ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}.
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity,
   * ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiSequenceFlowTakenEvent ActivitiEventBuilder.createSequenceFlowTakenEvent(ExecutionEntity, ActivitiEventType, String, String, String, String, Object, String, String, String, Object)"
  })
  public void testCreateSequenceFlowTakenEvent() {
    // Arrange and Act
    ActivitiSequenceFlowTakenEvent actualCreateSequenceFlowTakenEventResult =
        ActivitiEventBuilder.createSequenceFlowTakenEvent(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
            ActivitiEventType.ENTITY_CREATED,
            "42",
            "42",
            "Source Activity Name",
            "Source Activity Type",
            JSONObject.NULL,
            "42",
            "Target Activity Name",
            "Target Activity Type",
            JSONObject.NULL);

    // Assert
    assertTrue(
        actualCreateSequenceFlowTakenEventResult instanceof ActivitiSequenceFlowTakenEventImpl);
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getSourceActivityId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getTargetActivityId());
    assertEquals(
        "Source Activity Name", actualCreateSequenceFlowTakenEventResult.getSourceActivityName());
    assertEquals(
        "Source Activity Type", actualCreateSequenceFlowTakenEventResult.getSourceActivityType());
    assertEquals(
        "Target Activity Name", actualCreateSequenceFlowTakenEventResult.getTargetActivityName());
    assertEquals(
        "Target Activity Type", actualCreateSequenceFlowTakenEventResult.getTargetActivityType());
    assertEquals(
        "org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateSequenceFlowTakenEventResult.getSourceActivityBehaviorClass());
    assertEquals(
        "org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateSequenceFlowTakenEventResult.getTargetActivityBehaviorClass());
    assertNull(actualCreateSequenceFlowTakenEventResult.getExecutionId());
    assertNull(actualCreateSequenceFlowTakenEventResult.getProcessDefinitionId());
    assertNull(actualCreateSequenceFlowTakenEventResult.getProcessInstanceId());
    assertNull(
        ((ActivitiSequenceFlowTakenEventImpl) actualCreateSequenceFlowTakenEventResult)
            .getReason());
    assertEquals(
        ActivitiEventType.ENTITY_CREATED, actualCreateSequenceFlowTakenEventResult.getType());
  }

  /**
   * Test {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity,
   * ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}.
   *
   * <ul>
   *   <li>Then return SourceActivityBehaviorClass is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity,
   * ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiSequenceFlowTakenEvent ActivitiEventBuilder.createSequenceFlowTakenEvent(ExecutionEntity, ActivitiEventType, String, String, String, String, Object, String, String, String, Object)"
  })
  public void testCreateSequenceFlowTakenEvent_thenReturnSourceActivityBehaviorClassIsNull() {
    // Arrange and Act
    ActivitiSequenceFlowTakenEvent actualCreateSequenceFlowTakenEventResult =
        ActivitiEventBuilder.createSequenceFlowTakenEvent(
            null,
            ActivitiEventType.ENTITY_CREATED,
            "42",
            "42",
            "Source Activity Name",
            "Source Activity Type",
            null,
            "42",
            "Target Activity Name",
            "Target Activity Type",
            null);

    // Assert
    assertTrue(
        actualCreateSequenceFlowTakenEventResult instanceof ActivitiSequenceFlowTakenEventImpl);
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getSourceActivityId());
    assertEquals("42", actualCreateSequenceFlowTakenEventResult.getTargetActivityId());
    assertEquals(
        "Source Activity Name", actualCreateSequenceFlowTakenEventResult.getSourceActivityName());
    assertEquals(
        "Source Activity Type", actualCreateSequenceFlowTakenEventResult.getSourceActivityType());
    assertEquals(
        "Target Activity Name", actualCreateSequenceFlowTakenEventResult.getTargetActivityName());
    assertEquals(
        "Target Activity Type", actualCreateSequenceFlowTakenEventResult.getTargetActivityType());
    assertNull(actualCreateSequenceFlowTakenEventResult.getExecutionId());
    assertNull(actualCreateSequenceFlowTakenEventResult.getProcessDefinitionId());
    assertNull(actualCreateSequenceFlowTakenEventResult.getProcessInstanceId());
    assertNull(actualCreateSequenceFlowTakenEventResult.getSourceActivityBehaviorClass());
    assertNull(actualCreateSequenceFlowTakenEventResult.getTargetActivityBehaviorClass());
    assertNull(
        ((ActivitiSequenceFlowTakenEventImpl) actualCreateSequenceFlowTakenEventResult)
            .getReason());
    assertEquals(
        ActivitiEventType.ENTITY_CREATED, actualCreateSequenceFlowTakenEventResult.getType());
  }

  /**
   * Test {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity,
   * ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity,
   * ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiSequenceFlowTakenEvent ActivitiEventBuilder.createSequenceFlowTakenEvent(ExecutionEntity, ActivitiEventType, String, String, String, String, Object, String, String, String, Object)"
  })
  public void testCreateSequenceFlowTakenEvent_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getId()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            ActivitiEventBuilder.createSequenceFlowTakenEvent(
                executionEntity,
                ActivitiEventType.ENTITY_CREATED,
                "42",
                "42",
                "Source Activity Name",
                "Source Activity Type",
                JSONObject.NULL,
                "42",
                "Target Activity Name",
                "Target Activity Type",
                JSONObject.NULL));
    verify(executionEntity).getId();
  }

  /**
   * Test {@link ActivitiEventBuilder#createEntityExceptionEvent(ActivitiEventType, Object,
   * Throwable, String, String, String)} with {@code type}, {@code entity}, {@code cause}, {@code
   * executionId}, {@code processInstanceId}, {@code processDefinitionId}.
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createEntityExceptionEvent(ActivitiEventType,
   * Object, Throwable, String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiEntityEvent ActivitiEventBuilder.createEntityExceptionEvent(ActivitiEventType, Object, Throwable, String, String, String)"
  })
  public void
      testCreateEntityExceptionEventWithTypeEntityCauseExecutionIdProcessInstanceIdProcessDefinitionId() {
    // Arrange
    Object object = JSONObject.NULL;
    Throwable cause = new Throwable();

    // Act
    ActivitiEntityEvent actualCreateEntityExceptionEventResult =
        ActivitiEventBuilder.createEntityExceptionEvent(
            ActivitiEventType.ENTITY_CREATED, object, cause, "42", "42", "42");

    // Assert
    assertTrue(actualCreateEntityExceptionEventResult instanceof ActivitiEntityExceptionEventImpl);
    assertEquals("42", actualCreateEntityExceptionEventResult.getExecutionId());
    assertEquals("42", actualCreateEntityExceptionEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateEntityExceptionEventResult.getProcessInstanceId());
    assertNull(actualCreateEntityExceptionEventResult.getReason());
    assertEquals(
        ActivitiEventType.ENTITY_CREATED, actualCreateEntityExceptionEventResult.getType());
    assertSame(
        cause,
        ((ActivitiEntityExceptionEventImpl) actualCreateEntityExceptionEventResult).getCause());
    assertSame(object, actualCreateEntityExceptionEventResult.getEntity());
  }

  /**
   * Test {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, String, String, String,
   * String, String, FlowElement)} with {@code type}, {@code activityId}, {@code activityName},
   * {@code executionId}, {@code processInstanceId}, {@code processDefinitionId}, {@code
   * flowElement}.
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType,
   * String, String, String, String, String, FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiActivityEvent ActivitiEventBuilder.createActivityEvent(ActivitiEventType, String, String, String, String, String, FlowElement)"
  })
  public void
      testCreateActivityEventWithTypeActivityIdActivityNameExecutionIdProcessInstanceIdProcessDefinitionIdFlowElement() {
    // Arrange and Act
    ActivitiActivityEvent actualCreateActivityEventResult =
        ActivitiEventBuilder.createActivityEvent(
            ActivitiEventType.ENTITY_CREATED,
            "42",
            "Activity Name",
            "42",
            "42",
            "42",
            new AdhocSubProcess());

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
   * Test {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, String, String, String,
   * String, String, FlowElement)} with {@code type}, {@code activityId}, {@code activityName},
   * {@code executionId}, {@code processInstanceId}, {@code processDefinitionId}, {@code
   * flowElement}.
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType,
   * String, String, String, String, String, FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiActivityEvent ActivitiEventBuilder.createActivityEvent(ActivitiEventType, String, String, String, String, String, FlowElement)"
  })
  public void
      testCreateActivityEventWithTypeActivityIdActivityNameExecutionIdProcessInstanceIdProcessDefinitionIdFlowElement2() {
    // Arrange
    AdhocSubProcess flowElement = new AdhocSubProcess();
    flowElement.setBehavior(JSONObject.NULL);

    // Act
    ActivitiActivityEvent actualCreateActivityEventResult =
        ActivitiEventBuilder.createActivityEvent(
            ActivitiEventType.ENTITY_CREATED, "42", "Activity Name", "42", "42", "42", flowElement);

    // Assert
    assertTrue(actualCreateActivityEventResult instanceof ActivitiActivityEventImpl);
    assertEquals("42", actualCreateActivityEventResult.getActivityId());
    assertEquals("42", actualCreateActivityEventResult.getExecutionId());
    assertEquals("42", actualCreateActivityEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateActivityEventResult.getProcessInstanceId());
    assertEquals("Activity Name", actualCreateActivityEventResult.getActivityName());
    assertEquals("adhocSubProcess", actualCreateActivityEventResult.getActivityType());
    assertEquals(
        "org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateActivityEventResult.getBehaviorClass());
    assertNull(((ActivitiActivityEventImpl) actualCreateActivityEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateActivityEventResult.getType());
  }

  /**
   * Test {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, String, String, String,
   * String, String, FlowElement)} with {@code type}, {@code activityId}, {@code activityName},
   * {@code executionId}, {@code processInstanceId}, {@code processDefinitionId}, {@code
   * flowElement}.
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType,
   * String, String, String, String, String, FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiActivityEvent ActivitiEventBuilder.createActivityEvent(ActivitiEventType, String, String, String, String, String, FlowElement)"
  })
  public void
      testCreateActivityEventWithTypeActivityIdActivityNameExecutionIdProcessInstanceIdProcessDefinitionIdFlowElement3() {
    // Arrange and Act
    ActivitiActivityEvent actualCreateActivityEventResult =
        ActivitiEventBuilder.createActivityEvent(
            ActivitiEventType.ENTITY_CREATED, "42", "Activity Name", "42", "42", "42", null);

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
   * Test {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, String, String, String,
   * String, String, FlowElement)} with {@code type}, {@code activityId}, {@code activityName},
   * {@code executionId}, {@code processInstanceId}, {@code processDefinitionId}, {@code
   * flowElement}.
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType,
   * String, String, String, String, String, FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiActivityEvent ActivitiEventBuilder.createActivityEvent(ActivitiEventType, String, String, String, String, String, FlowElement)"
  })
  public void
      testCreateActivityEventWithTypeActivityIdActivityNameExecutionIdProcessInstanceIdProcessDefinitionIdFlowElement4() {
    // Arrange
    AdhocSubProcess flowElement = mock(AdhocSubProcess.class);
    when(flowElement.getBehavior()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            ActivitiEventBuilder.createActivityEvent(
                ActivitiEventType.ENTITY_CREATED,
                "42",
                "Activity Name",
                "42",
                "42",
                "42",
                flowElement));
    verify(flowElement).getBehavior();
  }

  /**
   * Test {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, DelegateExecution,
   * FlowElement)} with {@code type}, {@code execution}, {@code flowElement}.
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType,
   * DelegateExecution, FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiActivityEvent ActivitiEventBuilder.createActivityEvent(ActivitiEventType, DelegateExecution, FlowElement)"
  })
  public void testCreateActivityEventWithTypeExecutionFlowElement() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ActivitiActivityEvent actualCreateActivityEventResult =
        ActivitiEventBuilder.createActivityEvent(
            ActivitiEventType.ENTITY_CREATED, execution, new AdhocSubProcess());

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
   * Test {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, DelegateExecution,
   * FlowElement)} with {@code type}, {@code execution}, {@code flowElement}.
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType,
   * DelegateExecution, FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiActivityEvent ActivitiEventBuilder.createActivityEvent(ActivitiEventType, DelegateExecution, FlowElement)"
  })
  public void testCreateActivityEventWithTypeExecutionFlowElement2() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    AdhocSubProcess flowElement = new AdhocSubProcess();
    flowElement.setBehavior(JSONObject.NULL);

    // Act
    ActivitiActivityEvent actualCreateActivityEventResult =
        ActivitiEventBuilder.createActivityEvent(
            ActivitiEventType.ENTITY_CREATED, execution, flowElement);

    // Assert
    assertTrue(actualCreateActivityEventResult instanceof ActivitiActivityEventImpl);
    assertEquals("adhocSubProcess", actualCreateActivityEventResult.getActivityType());
    assertEquals(
        "org.activiti.engine.impl.util.json.JSONObject.Null",
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
   * Test {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, DelegateExecution,
   * FlowElement)} with {@code type}, {@code execution}, {@code flowElement}.
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType,
   * DelegateExecution, FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiActivityEvent ActivitiEventBuilder.createActivityEvent(ActivitiEventType, DelegateExecution, FlowElement)"
  })
  public void testCreateActivityEventWithTypeExecutionFlowElement3() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    SequenceFlow flowElement = new SequenceFlow("Source Ref", "Target Ref");
    flowElement.setName("");

    // Act
    ActivitiActivityEvent actualCreateActivityEventResult =
        ActivitiEventBuilder.createActivityEvent(
            ActivitiEventType.ENTITY_CREATED, execution, flowElement);

    // Assert
    assertTrue(actualCreateActivityEventResult instanceof ActivitiActivityEventImpl);
    assertEquals("", actualCreateActivityEventResult.getActivityName());
    assertNull(actualCreateActivityEventResult.getActivityId());
    assertNull(actualCreateActivityEventResult.getActivityType());
    assertNull(actualCreateActivityEventResult.getBehaviorClass());
    assertNull(actualCreateActivityEventResult.getExecutionId());
    assertNull(actualCreateActivityEventResult.getProcessDefinitionId());
    assertNull(actualCreateActivityEventResult.getProcessInstanceId());
    assertNull(((ActivitiActivityEventImpl) actualCreateActivityEventResult).getReason());
    assertEquals(ActivitiEventType.ENTITY_CREATED, actualCreateActivityEventResult.getType());
  }

  /**
   * Test {@link ActivitiEventBuilder#mayBeResolveExpression(String, DelegateExecution)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#mayBeResolveExpression(String,
   * DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ActivitiEventBuilder.mayBeResolveExpression(String, DelegateExecution)"
  })
  public void testMayBeResolveExpression_whenEmptyString_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals(
        "",
        ActivitiEventBuilder.mayBeResolveExpression(
            "", ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ActivitiEventBuilder#mayBeResolveExpression(String, DelegateExecution)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#mayBeResolveExpression(String,
   * DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ActivitiEventBuilder.mayBeResolveExpression(String, DelegateExecution)"
  })
  public void testMayBeResolveExpression_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        ActivitiEventBuilder.mayBeResolveExpression(
            null, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ActivitiEventBuilder#parseActivityType(FlowNode)}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   *   <li>Then return {@code adhocSubProcess}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#parseActivityType(FlowNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ActivitiEventBuilder.parseActivityType(FlowNode)"})
  public void testParseActivityType_whenAdhocSubProcess_thenReturnAdhocSubProcess() {
    // Arrange, Act and Assert
    assertEquals("adhocSubProcess", ActivitiEventBuilder.parseActivityType(new AdhocSubProcess()));
  }

  /**
   * Test {@link ActivitiEventBuilder#parseActivityBehavior(FlowNode)}.
   *
   * <ul>
   *   <li>Then return {@code JSONObject.Null}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#parseActivityBehavior(FlowNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ActivitiEventBuilder.parseActivityBehavior(FlowNode)"})
  public void testParseActivityBehavior_thenReturnOrgActivitiEngineImplUtilJsonJSONObjectNull() {
    // Arrange
    AdhocSubProcess flowNode = new AdhocSubProcess();
    flowNode.setBehavior(JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "org.activiti.engine.impl.util.json.JSONObject.Null",
        ActivitiEventBuilder.parseActivityBehavior(flowNode));
  }

  /**
   * Test {@link ActivitiEventBuilder#parseActivityBehavior(FlowNode)}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#parseActivityBehavior(FlowNode)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ActivitiEventBuilder.parseActivityBehavior(FlowNode)"})
  public void testParseActivityBehavior_whenAdhocSubProcess_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ActivitiEventBuilder.parseActivityBehavior(new AdhocSubProcess()));
  }

  /**
   * Test {@link ActivitiEventBuilder#createActivityCancelledEvent(String, String, String, String,
   * String, String, Object)} with {@code activityId}, {@code activityName}, {@code executionId},
   * {@code processInstanceId}, {@code processDefinitionId}, {@code activityType}, {@code cause}.
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createActivityCancelledEvent(String, String,
   * String, String, String, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiActivityCancelledEvent ActivitiEventBuilder.createActivityCancelledEvent(String, String, String, String, String, String, Object)"
  })
  public void
      testCreateActivityCancelledEventWithActivityIdActivityNameExecutionIdProcessInstanceIdProcessDefinitionIdActivityTypeCause() {
    // Arrange
    Object object = JSONObject.NULL;

    // Act
    ActivitiActivityCancelledEvent actualCreateActivityCancelledEventResult =
        ActivitiEventBuilder.createActivityCancelledEvent(
            "42", "Activity Name", "42", "42", "42", "Activity Type", object);

    // Assert
    assertTrue(
        actualCreateActivityCancelledEventResult instanceof ActivitiActivityCancelledEventImpl);
    assertEquals("42", actualCreateActivityCancelledEventResult.getActivityId());
    assertEquals("42", actualCreateActivityCancelledEventResult.getExecutionId());
    assertEquals("42", actualCreateActivityCancelledEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateActivityCancelledEventResult.getProcessInstanceId());
    assertEquals("Activity Name", actualCreateActivityCancelledEventResult.getActivityName());
    assertEquals("Activity Type", actualCreateActivityCancelledEventResult.getActivityType());
    assertNull(actualCreateActivityCancelledEventResult.getBehaviorClass());
    assertNull(
        ((ActivitiActivityCancelledEventImpl) actualCreateActivityCancelledEventResult)
            .getReason());
    assertEquals(
        ActivitiEventType.ACTIVITY_CANCELLED, actualCreateActivityCancelledEventResult.getType());
    assertSame(object, actualCreateActivityCancelledEventResult.getCause());
  }

  /**
   * Test {@link ActivitiEventBuilder#createActivityCancelledEvent(ExecutionEntity, Object)} with
   * {@code execution}, {@code cause}.
   *
   * <ul>
   *   <li>Then return ActivityName is {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createActivityCancelledEvent(ExecutionEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiActivityCancelledEvent ActivitiEventBuilder.createActivityCancelledEvent(ExecutionEntity, Object)"
  })
  public void testCreateActivityCancelledEventWithExecutionCause_thenReturnActivityNameIsName() {
    // Arrange
    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getBehavior()).thenReturn(JSONObject.NULL);
    when(adhocSubProcess.getName()).thenReturn("Name");

    ExecutionEntity execution = mock(ExecutionEntity.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getActivityId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(adhocSubProcess);
    Object object = JSONObject.NULL;

    // Act
    ActivitiActivityCancelledEvent actualCreateActivityCancelledEventResult =
        ActivitiEventBuilder.createActivityCancelledEvent(execution, object);

    // Assert
    verify(adhocSubProcess).getName();
    verify(adhocSubProcess).getBehavior();
    verify(execution).getCurrentFlowElement();
    verify(execution).getId();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceId();
    verify(execution).getActivityId();
    assertTrue(
        actualCreateActivityCancelledEventResult instanceof ActivitiActivityCancelledEventImpl);
    assertEquals("42", actualCreateActivityCancelledEventResult.getActivityId());
    assertEquals("42", actualCreateActivityCancelledEventResult.getExecutionId());
    assertEquals("42", actualCreateActivityCancelledEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateActivityCancelledEventResult.getProcessInstanceId());
    assertEquals("Name", actualCreateActivityCancelledEventResult.getActivityName());
    assertEquals("adhocSubProcess", actualCreateActivityCancelledEventResult.getActivityType());
    assertEquals(
        "org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateActivityCancelledEventResult.getBehaviorClass());
    assertNull(
        ((ActivitiActivityCancelledEventImpl) actualCreateActivityCancelledEventResult)
            .getReason());
    assertEquals(
        ActivitiEventType.ACTIVITY_CANCELLED, actualCreateActivityCancelledEventResult.getType());
    assertSame(object, actualCreateActivityCancelledEventResult.getCause());
  }

  /**
   * Test {@link ActivitiEventBuilder#createActivityCancelledEvent(ExecutionEntity, Object)} with
   * {@code execution}, {@code cause}.
   *
   * <ul>
   *   <li>Then return ActivityName is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createActivityCancelledEvent(ExecutionEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiActivityCancelledEvent ActivitiEventBuilder.createActivityCancelledEvent(ExecutionEntity, Object)"
  })
  public void testCreateActivityCancelledEventWithExecutionCause_thenReturnActivityNameIsNull() {
    // Arrange
    ExecutionEntity execution = mock(ExecutionEntity.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getActivityId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    Object object = JSONObject.NULL;

    // Act
    ActivitiActivityCancelledEvent actualCreateActivityCancelledEventResult =
        ActivitiEventBuilder.createActivityCancelledEvent(execution, object);

    // Assert
    verify(execution).getCurrentFlowElement();
    verify(execution).getId();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceId();
    verify(execution).getActivityId();
    assertTrue(
        actualCreateActivityCancelledEventResult instanceof ActivitiActivityCancelledEventImpl);
    assertEquals("42", actualCreateActivityCancelledEventResult.getActivityId());
    assertEquals("42", actualCreateActivityCancelledEventResult.getExecutionId());
    assertEquals("42", actualCreateActivityCancelledEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateActivityCancelledEventResult.getProcessInstanceId());
    assertEquals("adhocSubProcess", actualCreateActivityCancelledEventResult.getActivityType());
    assertNull(actualCreateActivityCancelledEventResult.getActivityName());
    assertNull(actualCreateActivityCancelledEventResult.getBehaviorClass());
    assertNull(
        ((ActivitiActivityCancelledEventImpl) actualCreateActivityCancelledEventResult)
            .getReason());
    assertEquals(
        ActivitiEventType.ACTIVITY_CANCELLED, actualCreateActivityCancelledEventResult.getType());
    assertSame(object, actualCreateActivityCancelledEventResult.getCause());
  }

  /**
   * Test {@link ActivitiEventBuilder#createActivityCancelledEvent(ExecutionEntity, Object)} with
   * {@code execution}, {@code cause}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createActivityCancelledEvent(ExecutionEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiActivityCancelledEvent ActivitiEventBuilder.createActivityCancelledEvent(ExecutionEntity, Object)"
  })
  public void testCreateActivityCancelledEventWithExecutionCause_thenThrowActivitiException() {
    // Arrange
    ExecutionEntity execution = mock(ExecutionEntity.class);
    when(execution.getId()).thenThrow(new ActivitiException("An error occurred"));
    when(execution.getActivityId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> ActivitiEventBuilder.createActivityCancelledEvent(execution, JSONObject.NULL));
    verify(execution).getCurrentFlowElement();
    verify(execution).getId();
    verify(execution).getActivityId();
  }

  /**
   * Test {@link ActivitiEventBuilder#createProcessCancelledEvent(ProcessInstance, Object)}.
   *
   * <ul>
   *   <li>Then return {@link ActivitiProcessCancelledEventImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createProcessCancelledEvent(ProcessInstance,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiProcessCancelledEvent ActivitiEventBuilder.createProcessCancelledEvent(ProcessInstance, Object)"
  })
  public void testCreateProcessCancelledEvent_thenReturnActivitiProcessCancelledEventImpl() {
    // Arrange
    ExecutionEntityImpl processInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    Object object = JSONObject.NULL;

    // Act
    ActivitiProcessCancelledEvent actualCreateProcessCancelledEventResult =
        ActivitiEventBuilder.createProcessCancelledEvent(processInstance, object);

    // Assert
    assertTrue(
        actualCreateProcessCancelledEventResult instanceof ActivitiProcessCancelledEventImpl);
    Object entity = actualCreateProcessCancelledEventResult.getEntity();
    assertTrue(entity instanceof ExecutionEntityImpl);
    assertNull(actualCreateProcessCancelledEventResult.getReason());
    assertNull(actualCreateProcessCancelledEventResult.getExecutionId());
    assertNull(actualCreateProcessCancelledEventResult.getProcessDefinitionId());
    assertNull(actualCreateProcessCancelledEventResult.getProcessInstanceId());
    assertEquals(
        ActivitiEventType.PROCESS_CANCELLED, actualCreateProcessCancelledEventResult.getType());
    assertSame(processInstance, entity);
    assertSame(object, actualCreateProcessCancelledEventResult.getCause());
  }

  /**
   * Test {@link ActivitiEventBuilder#createProcessCancelledEvent(ProcessInstance, Object)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createProcessCancelledEvent(ProcessInstance,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiProcessCancelledEvent ActivitiEventBuilder.createProcessCancelledEvent(ProcessInstance, Object)"
  })
  public void testCreateProcessCancelledEvent_thenThrowActivitiException() {
    // Arrange
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> ActivitiEventBuilder.createProcessCancelledEvent(processInstance, JSONObject.NULL));
    verify(processInstance).getId();
  }

  /**
   * Test {@link ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String,
   * Object)}.
   *
   * <p>Method under test: {@link
   * ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiSignalEvent ActivitiEventBuilder.createActivitiySignalledEvent(DelegateExecution, String, Object)"
  })
  public void testCreateActivitiySignalledEvent() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setBehavior(JSONObject.NULL);

    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentFlowElement()).thenReturn(adhocSubProcess);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceId()).thenReturn("42");

    // Act
    ActivitiSignalEvent actualCreateActivitiySignalledEventResult =
        ActivitiEventBuilder.createActivitiySignalledEvent(
            execution, "Signal Name", JSONObject.NULL);

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
    assertEquals("adhocSubProcess", actualCreateActivitiySignalledEventResult.getActivityType());
    assertEquals(
        "org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateActivitiySignalledEventResult.getBehaviorClass());
  }

  /**
   * Test {@link ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String,
   * Object)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   *   <li>Then return ActivityId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiSignalEvent ActivitiEventBuilder.createActivitiySignalledEvent(DelegateExecution, String, Object)"
  })
  public void testCreateActivitiySignalledEvent_givenAdhocSubProcess_thenReturnActivityIdIs42() {
    // Arrange
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceId()).thenReturn("42");

    // Act
    ActivitiSignalEvent actualCreateActivitiySignalledEventResult =
        ActivitiEventBuilder.createActivitiySignalledEvent(
            execution, "Signal Name", JSONObject.NULL);

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
    assertEquals("adhocSubProcess", actualCreateActivitiySignalledEventResult.getActivityType());
  }

  /**
   * Test {@link ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String,
   * Object)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiSignalEvent ActivitiEventBuilder.createActivitiySignalledEvent(DelegateExecution, String, Object)"
  })
  public void testCreateActivitiySignalledEvent_thenThrowActivitiException() {
    // Arrange
    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getBehavior()).thenThrow(new ActivitiException("An error occurred"));

    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentFlowElement()).thenReturn(adhocSubProcess);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceId()).thenReturn("42");

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            ActivitiEventBuilder.createActivitiySignalledEvent(
                execution, "Signal Name", JSONObject.NULL));
    verify(adhocSubProcess).getBehavior();
    verify(execution).getCurrentActivityId();
    verify(execution, atLeast(1)).getCurrentFlowElement();
    verify(execution).getId();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceId();
  }

  /**
   * Test {@link ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String,
   * Object)}.
   *
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiSignalEvent ActivitiEventBuilder.createActivitiySignalledEvent(DelegateExecution, String, Object)"
  })
  public void testCreateActivitiySignalledEvent_whenCreateWithEmptyRelationshipCollections() {
    // Arrange and Act
    ActivitiSignalEvent actualCreateActivitiySignalledEventResult =
        ActivitiEventBuilder.createActivitiySignalledEvent(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
            "Signal Name",
            JSONObject.NULL);

    // Assert
    assertTrue(actualCreateActivitiySignalledEventResult instanceof ActivitiSignalEventImpl);
    assertNull(actualCreateActivitiySignalledEventResult.getActivityId());
    assertNull(actualCreateActivitiySignalledEventResult.getActivityType());
    assertNull(actualCreateActivitiySignalledEventResult.getExecutionId());
    assertNull(actualCreateActivitiySignalledEventResult.getProcessDefinitionId());
    assertNull(actualCreateActivitiySignalledEventResult.getProcessInstanceId());
  }

  /**
   * Test {@link ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String,
   * Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return ActivityId is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiSignalEvent ActivitiEventBuilder.createActivitiySignalledEvent(DelegateExecution, String, Object)"
  })
  public void testCreateActivitiySignalledEvent_whenNull_thenReturnActivityIdIsNull() {
    // Arrange and Act
    ActivitiSignalEvent actualCreateActivitiySignalledEventResult =
        ActivitiEventBuilder.createActivitiySignalledEvent(null, "Signal Name", JSONObject.NULL);

    // Assert
    assertTrue(actualCreateActivitiySignalledEventResult instanceof ActivitiSignalEventImpl);
    assertNull(actualCreateActivitiySignalledEventResult.getActivityId());
    assertNull(actualCreateActivitiySignalledEventResult.getActivityType());
    assertNull(actualCreateActivitiySignalledEventResult.getExecutionId());
    assertNull(actualCreateActivitiySignalledEventResult.getProcessDefinitionId());
    assertNull(actualCreateActivitiySignalledEventResult.getProcessInstanceId());
  }

  /**
   * Test {@link ActivitiEventBuilder#createMessageReceivedEvent(DelegateExecution, String, String,
   * Object)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createMessageReceivedEvent(DelegateExecution,
   * String, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiMessageEvent ActivitiEventBuilder.createMessageReceivedEvent(DelegateExecution, String, String, Object)"
  })
  public void testCreateMessageReceivedEvent_givenAdhocSubProcess() {
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
    ActivitiMessageEvent actualCreateMessageReceivedEventResult =
        ActivitiEventBuilder.createMessageReceivedEvent(
            execution, "Message Name", "Correlation Key", object);

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
    assertEquals(
        "Correlation Key", actualCreateMessageReceivedEventResult.getMessageCorrelationKey());
    assertEquals("Message Name", actualCreateMessageReceivedEventResult.getMessageName());
    assertEquals(
        "Process Instance Business Key",
        actualCreateMessageReceivedEventResult.getMessageBusinessKey());
    assertEquals("adhocSubProcess", actualCreateMessageReceivedEventResult.getActivityType());
    assertNull(actualCreateMessageReceivedEventResult.getActivityName());
    assertNull(actualCreateMessageReceivedEventResult.getBehaviorClass());
    assertNull(((ActivitiMessageEventImpl) actualCreateMessageReceivedEventResult).getReason());
    assertEquals(
        ActivitiEventType.ACTIVITY_MESSAGE_RECEIVED,
        actualCreateMessageReceivedEventResult.getType());
    assertSame(object, actualCreateMessageReceivedEventResult.getMessageData());
  }

  /**
   * Test {@link ActivitiEventBuilder#createMessageReceivedEvent(DelegateExecution, String, String,
   * Object)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then return ActivityType is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createMessageReceivedEvent(DelegateExecution,
   * String, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiMessageEvent ActivitiEventBuilder.createMessageReceivedEvent(DelegateExecution, String, String, Object)"
  })
  public void testCreateMessageReceivedEvent_givenNull_thenReturnActivityTypeIsNull() {
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
    ActivitiMessageEvent actualCreateMessageReceivedEventResult =
        ActivitiEventBuilder.createMessageReceivedEvent(
            execution, "Message Name", "Correlation Key", object);

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
    assertEquals(
        "Correlation Key", actualCreateMessageReceivedEventResult.getMessageCorrelationKey());
    assertEquals("Message Name", actualCreateMessageReceivedEventResult.getMessageName());
    assertEquals(
        "Process Instance Business Key",
        actualCreateMessageReceivedEventResult.getMessageBusinessKey());
    assertNull(actualCreateMessageReceivedEventResult.getActivityName());
    assertNull(actualCreateMessageReceivedEventResult.getActivityType());
    assertNull(actualCreateMessageReceivedEventResult.getBehaviorClass());
    assertNull(((ActivitiMessageEventImpl) actualCreateMessageReceivedEventResult).getReason());
    assertEquals(
        ActivitiEventType.ACTIVITY_MESSAGE_RECEIVED,
        actualCreateMessageReceivedEventResult.getType());
    assertSame(object, actualCreateMessageReceivedEventResult.getMessageData());
  }

  /**
   * Test {@link ActivitiEventBuilder#createMessageReceivedEvent(DelegateExecution, String, String,
   * Object)}.
   *
   * <ul>
   *   <li>Then return ActivityName is {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createMessageReceivedEvent(DelegateExecution,
   * String, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiMessageEvent ActivitiEventBuilder.createMessageReceivedEvent(DelegateExecution, String, String, Object)"
  })
  public void testCreateMessageReceivedEvent_thenReturnActivityNameIsName() {
    // Arrange
    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getBehavior()).thenReturn(JSONObject.NULL);
    when(adhocSubProcess.getName()).thenReturn("Name");

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(adhocSubProcess);
    Object object = JSONObject.NULL;

    // Act
    ActivitiMessageEvent actualCreateMessageReceivedEventResult =
        ActivitiEventBuilder.createMessageReceivedEvent(
            execution, "Message Name", "Correlation Key", object);

    // Assert
    verify(adhocSubProcess).getName();
    verify(adhocSubProcess).getBehavior();
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
    assertEquals(
        "Correlation Key", actualCreateMessageReceivedEventResult.getMessageCorrelationKey());
    assertEquals("Message Name", actualCreateMessageReceivedEventResult.getMessageName());
    assertEquals("Name", actualCreateMessageReceivedEventResult.getActivityName());
    assertEquals(
        "Process Instance Business Key",
        actualCreateMessageReceivedEventResult.getMessageBusinessKey());
    assertEquals("adhocSubProcess", actualCreateMessageReceivedEventResult.getActivityType());
    assertEquals(
        "org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateMessageReceivedEventResult.getBehaviorClass());
    assertNull(((ActivitiMessageEventImpl) actualCreateMessageReceivedEventResult).getReason());
    assertEquals(
        ActivitiEventType.ACTIVITY_MESSAGE_RECEIVED,
        actualCreateMessageReceivedEventResult.getType());
    assertSame(object, actualCreateMessageReceivedEventResult.getMessageData());
  }

  /**
   * Test {@link ActivitiEventBuilder#createMessageReceivedEvent(DelegateExecution, String, String,
   * Object)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createMessageReceivedEvent(DelegateExecution,
   * String, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiMessageEvent ActivitiEventBuilder.createMessageReceivedEvent(DelegateExecution, String, String, Object)"
  })
  public void testCreateMessageReceivedEvent_thenThrowActivitiException() {
    // Arrange
    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getBehavior()).thenThrow(new ActivitiException("An error occurred"));

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(adhocSubProcess);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            ActivitiEventBuilder.createMessageReceivedEvent(
                execution, "Message Name", "Correlation Key", JSONObject.NULL));
    verify(adhocSubProcess).getBehavior();
    verify(execution).getId();
    verify(execution).getCurrentActivityId();
    verify(execution, atLeast(1)).getCurrentFlowElement();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceBusinessKey();
    verify(execution).getProcessInstanceId();
  }

  /**
   * Test {@link ActivitiEventBuilder#createMessageWaitingEvent(DelegateExecution, String, String)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createMessageWaitingEvent(DelegateExecution,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiMessageEvent ActivitiEventBuilder.createMessageWaitingEvent(DelegateExecution, String, String)"
  })
  public void testCreateMessageWaitingEvent_givenAdhocSubProcess() {
    // Arrange
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    // Act
    ActivitiMessageEvent actualCreateMessageWaitingEventResult =
        ActivitiEventBuilder.createMessageWaitingEvent(
            execution, "Message Name", "Correlation Key");

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
    assertEquals(
        "Correlation Key", actualCreateMessageWaitingEventResult.getMessageCorrelationKey());
    assertEquals("Message Name", actualCreateMessageWaitingEventResult.getMessageName());
    assertEquals(
        "Process Instance Business Key",
        actualCreateMessageWaitingEventResult.getMessageBusinessKey());
    assertEquals("adhocSubProcess", actualCreateMessageWaitingEventResult.getActivityType());
    assertNull(actualCreateMessageWaitingEventResult.getMessageData());
    assertNull(actualCreateMessageWaitingEventResult.getActivityName());
    assertNull(actualCreateMessageWaitingEventResult.getBehaviorClass());
    assertNull(((ActivitiMessageEventImpl) actualCreateMessageWaitingEventResult).getReason());
    assertEquals(
        ActivitiEventType.ACTIVITY_MESSAGE_WAITING,
        actualCreateMessageWaitingEventResult.getType());
  }

  /**
   * Test {@link ActivitiEventBuilder#createMessageWaitingEvent(DelegateExecution, String, String)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then return ActivityType is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createMessageWaitingEvent(DelegateExecution,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiMessageEvent ActivitiEventBuilder.createMessageWaitingEvent(DelegateExecution, String, String)"
  })
  public void testCreateMessageWaitingEvent_givenNull_thenReturnActivityTypeIsNull() {
    // Arrange
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(null);

    // Act
    ActivitiMessageEvent actualCreateMessageWaitingEventResult =
        ActivitiEventBuilder.createMessageWaitingEvent(
            execution, "Message Name", "Correlation Key");

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
    assertEquals(
        "Correlation Key", actualCreateMessageWaitingEventResult.getMessageCorrelationKey());
    assertEquals("Message Name", actualCreateMessageWaitingEventResult.getMessageName());
    assertEquals(
        "Process Instance Business Key",
        actualCreateMessageWaitingEventResult.getMessageBusinessKey());
    assertNull(actualCreateMessageWaitingEventResult.getMessageData());
    assertNull(actualCreateMessageWaitingEventResult.getActivityName());
    assertNull(actualCreateMessageWaitingEventResult.getActivityType());
    assertNull(actualCreateMessageWaitingEventResult.getBehaviorClass());
    assertNull(((ActivitiMessageEventImpl) actualCreateMessageWaitingEventResult).getReason());
    assertEquals(
        ActivitiEventType.ACTIVITY_MESSAGE_WAITING,
        actualCreateMessageWaitingEventResult.getType());
  }

  /**
   * Test {@link ActivitiEventBuilder#createMessageWaitingEvent(DelegateExecution, String, String)}.
   *
   * <ul>
   *   <li>Then return ActivityName is {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createMessageWaitingEvent(DelegateExecution,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiMessageEvent ActivitiEventBuilder.createMessageWaitingEvent(DelegateExecution, String, String)"
  })
  public void testCreateMessageWaitingEvent_thenReturnActivityNameIsName() {
    // Arrange
    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getBehavior()).thenReturn(JSONObject.NULL);
    when(adhocSubProcess.getName()).thenReturn("Name");

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(adhocSubProcess);

    // Act
    ActivitiMessageEvent actualCreateMessageWaitingEventResult =
        ActivitiEventBuilder.createMessageWaitingEvent(
            execution, "Message Name", "Correlation Key");

    // Assert
    verify(adhocSubProcess).getName();
    verify(adhocSubProcess).getBehavior();
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
    assertEquals(
        "Correlation Key", actualCreateMessageWaitingEventResult.getMessageCorrelationKey());
    assertEquals("Message Name", actualCreateMessageWaitingEventResult.getMessageName());
    assertEquals("Name", actualCreateMessageWaitingEventResult.getActivityName());
    assertEquals(
        "Process Instance Business Key",
        actualCreateMessageWaitingEventResult.getMessageBusinessKey());
    assertEquals("adhocSubProcess", actualCreateMessageWaitingEventResult.getActivityType());
    assertEquals(
        "org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateMessageWaitingEventResult.getBehaviorClass());
    assertNull(actualCreateMessageWaitingEventResult.getMessageData());
    assertNull(((ActivitiMessageEventImpl) actualCreateMessageWaitingEventResult).getReason());
    assertEquals(
        ActivitiEventType.ACTIVITY_MESSAGE_WAITING,
        actualCreateMessageWaitingEventResult.getType());
  }

  /**
   * Test {@link ActivitiEventBuilder#createMessageWaitingEvent(DelegateExecution, String, String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createMessageWaitingEvent(DelegateExecution,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiMessageEvent ActivitiEventBuilder.createMessageWaitingEvent(DelegateExecution, String, String)"
  })
  public void testCreateMessageWaitingEvent_thenThrowActivitiException() {
    // Arrange
    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getBehavior()).thenThrow(new ActivitiException("An error occurred"));

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(adhocSubProcess);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            ActivitiEventBuilder.createMessageWaitingEvent(
                execution, "Message Name", "Correlation Key"));
    verify(adhocSubProcess).getBehavior();
    verify(execution).getId();
    verify(execution).getCurrentActivityId();
    verify(execution, atLeast(1)).getCurrentFlowElement();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceBusinessKey();
    verify(execution).getProcessInstanceId();
  }

  /**
   * Test {@link ActivitiEventBuilder#createMessageSentEvent(DelegateExecution, String, String,
   * Object)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createMessageSentEvent(DelegateExecution,
   * String, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiMessageEvent ActivitiEventBuilder.createMessageSentEvent(DelegateExecution, String, String, Object)"
  })
  public void testCreateMessageSentEvent_givenAdhocSubProcess() {
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
    ActivitiMessageEvent actualCreateMessageSentEventResult =
        ActivitiEventBuilder.createMessageSentEvent(
            execution, "Message Name", "Correlation Key", object);

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
    assertEquals(
        "Process Instance Business Key",
        actualCreateMessageSentEventResult.getMessageBusinessKey());
    assertEquals("adhocSubProcess", actualCreateMessageSentEventResult.getActivityType());
    assertNull(actualCreateMessageSentEventResult.getActivityName());
    assertNull(actualCreateMessageSentEventResult.getBehaviorClass());
    assertNull(((ActivitiMessageEventImpl) actualCreateMessageSentEventResult).getReason());
    assertEquals(
        ActivitiEventType.ACTIVITY_MESSAGE_SENT, actualCreateMessageSentEventResult.getType());
    assertSame(object, actualCreateMessageSentEventResult.getMessageData());
  }

  /**
   * Test {@link ActivitiEventBuilder#createMessageSentEvent(DelegateExecution, String, String,
   * Object)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then return ActivityType is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createMessageSentEvent(DelegateExecution,
   * String, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiMessageEvent ActivitiEventBuilder.createMessageSentEvent(DelegateExecution, String, String, Object)"
  })
  public void testCreateMessageSentEvent_givenNull_thenReturnActivityTypeIsNull() {
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
    ActivitiMessageEvent actualCreateMessageSentEventResult =
        ActivitiEventBuilder.createMessageSentEvent(
            execution, "Message Name", "Correlation Key", object);

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
    assertEquals(
        "Process Instance Business Key",
        actualCreateMessageSentEventResult.getMessageBusinessKey());
    assertNull(actualCreateMessageSentEventResult.getActivityName());
    assertNull(actualCreateMessageSentEventResult.getActivityType());
    assertNull(actualCreateMessageSentEventResult.getBehaviorClass());
    assertNull(((ActivitiMessageEventImpl) actualCreateMessageSentEventResult).getReason());
    assertEquals(
        ActivitiEventType.ACTIVITY_MESSAGE_SENT, actualCreateMessageSentEventResult.getType());
    assertSame(object, actualCreateMessageSentEventResult.getMessageData());
  }

  /**
   * Test {@link ActivitiEventBuilder#createMessageSentEvent(DelegateExecution, String, String,
   * Object)}.
   *
   * <ul>
   *   <li>Then return ActivityName is {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createMessageSentEvent(DelegateExecution,
   * String, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiMessageEvent ActivitiEventBuilder.createMessageSentEvent(DelegateExecution, String, String, Object)"
  })
  public void testCreateMessageSentEvent_thenReturnActivityNameIsName() {
    // Arrange
    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getBehavior()).thenReturn(JSONObject.NULL);
    when(adhocSubProcess.getName()).thenReturn("Name");

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(adhocSubProcess);
    Object object = JSONObject.NULL;

    // Act
    ActivitiMessageEvent actualCreateMessageSentEventResult =
        ActivitiEventBuilder.createMessageSentEvent(
            execution, "Message Name", "Correlation Key", object);

    // Assert
    verify(adhocSubProcess).getName();
    verify(adhocSubProcess).getBehavior();
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
    assertEquals("Name", actualCreateMessageSentEventResult.getActivityName());
    assertEquals(
        "Process Instance Business Key",
        actualCreateMessageSentEventResult.getMessageBusinessKey());
    assertEquals("adhocSubProcess", actualCreateMessageSentEventResult.getActivityType());
    assertEquals(
        "org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateMessageSentEventResult.getBehaviorClass());
    assertNull(((ActivitiMessageEventImpl) actualCreateMessageSentEventResult).getReason());
    assertEquals(
        ActivitiEventType.ACTIVITY_MESSAGE_SENT, actualCreateMessageSentEventResult.getType());
    assertSame(object, actualCreateMessageSentEventResult.getMessageData());
  }

  /**
   * Test {@link ActivitiEventBuilder#createMessageSentEvent(DelegateExecution, String, String,
   * Object)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createMessageSentEvent(DelegateExecution,
   * String, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiMessageEvent ActivitiEventBuilder.createMessageSentEvent(DelegateExecution, String, String, Object)"
  })
  public void testCreateMessageSentEvent_thenThrowActivitiException() {
    // Arrange
    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getBehavior()).thenThrow(new ActivitiException("An error occurred"));

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(adhocSubProcess);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            ActivitiEventBuilder.createMessageSentEvent(
                execution, "Message Name", "Correlation Key", JSONObject.NULL));
    verify(adhocSubProcess).getBehavior();
    verify(execution).getId();
    verify(execution).getCurrentActivityId();
    verify(execution, atLeast(1)).getCurrentFlowElement();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceBusinessKey();
    verify(execution).getProcessInstanceId();
  }

  /**
   * Test {@link ActivitiEventBuilder#createErrorEvent(ActivitiEventType, String, String, String,
   * String, String, String)}.
   *
   * <ul>
   *   <li>When {@code ENTITY_CREATED}.
   *   <li>Then return {@link ActivitiErrorEventImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createErrorEvent(ActivitiEventType, String,
   * String, String, String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiErrorEvent ActivitiEventBuilder.createErrorEvent(ActivitiEventType, String, String, String, String, String, String)"
  })
  public void testCreateErrorEvent_whenEntityCreated_thenReturnActivitiErrorEventImpl() {
    // Arrange and Act
    ActivitiErrorEvent actualCreateErrorEventResult =
        ActivitiEventBuilder.createErrorEvent(
            ActivitiEventType.ENTITY_CREATED,
            "42",
            "An error occurred",
            "An error occurred",
            "42",
            "42",
            "42");

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
   * Test {@link ActivitiEventBuilder#createVariableEvent(ActivitiEventType, String, Object,
   * VariableType, String, String, String, String)}.
   *
   * <ul>
   *   <li>When {@code ENTITY_CREATED}.
   *   <li>Then return {@link ActivitiVariableEventImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createVariableEvent(ActivitiEventType,
   * String, Object, VariableType, String, String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiVariableEvent ActivitiEventBuilder.createVariableEvent(ActivitiEventType, String, Object, VariableType, String, String, String, String)"
  })
  public void testCreateVariableEvent_whenEntityCreated_thenReturnActivitiVariableEventImpl() {
    // Arrange
    Object object = JSONObject.NULL;
    BigDecimalType variableType = new BigDecimalType();

    // Act
    ActivitiVariableEvent actualCreateVariableEventResult =
        ActivitiEventBuilder.createVariableEvent(
            ActivitiEventType.ENTITY_CREATED,
            "Variable Name",
            object,
            variableType,
            "42",
            "42",
            "42",
            "42");

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
   * Test {@link ActivitiEventBuilder#createVariableUpdateEvent(VariableInstanceEntity, Object,
   * String, String)}.
   *
   * <ul>
   *   <li>Then return ProcessDefinitionId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiEventBuilder#createVariableUpdateEvent(VariableInstanceEntity, Object, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiVariableUpdatedEventImpl ActivitiEventBuilder.createVariableUpdateEvent(VariableInstanceEntity, Object, String, String)"
  })
  public void testCreateVariableUpdateEvent_thenReturnProcessDefinitionIdIs42() {
    // Arrange
    VariableInstanceEntityImpl variableInstance = new VariableInstanceEntityImpl();
    BigDecimalType type = new BigDecimalType();
    variableInstance.setType(type);
    Object object = JSONObject.NULL;

    // Act
    ActivitiVariableUpdatedEventImpl actualCreateVariableUpdateEventResult =
        ActivitiEventBuilder.createVariableUpdateEvent(variableInstance, object, "42", "42");

    // Assert
    assertEquals("42", actualCreateVariableUpdateEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateVariableUpdateEventResult.getProcessInstanceId());
    assertNull(actualCreateVariableUpdateEventResult.getVariableValue());
    assertNull(actualCreateVariableUpdateEventResult.getExecutionId());
    assertNull(actualCreateVariableUpdateEventResult.getReason());
    assertNull(actualCreateVariableUpdateEventResult.getTaskId());
    assertNull(actualCreateVariableUpdateEventResult.getVariableName());
    assertEquals(
        ActivitiEventType.VARIABLE_UPDATED, actualCreateVariableUpdateEventResult.getType());
    assertSame(type, actualCreateVariableUpdateEventResult.getVariableType());
    assertSame(object, actualCreateVariableUpdateEventResult.getVariablePreviousValue());
  }

  /**
   * Test {@link ActivitiEventBuilder#createMembershipEvent(ActivitiEventType, String, String)}.
   *
   * <ul>
   *   <li>Then return {@link ActivitiMembershipEventImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventBuilder#createMembershipEvent(ActivitiEventType,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiMembershipEvent ActivitiEventBuilder.createMembershipEvent(ActivitiEventType, String, String)"
  })
  public void testCreateMembershipEvent_thenReturnActivitiMembershipEventImpl() {
    // Arrange and Act
    ActivitiMembershipEvent actualCreateMembershipEventResult =
        ActivitiEventBuilder.createMembershipEvent(ActivitiEventType.ENTITY_CREATED, "42", "42");

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
   * Test {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}.
   *
   * <p>Method under test: {@link
   * ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventBuilder.populateEventWithCurrentContext(ActivitiEventImpl)"
  })
  public void testPopulateEventWithCurrentContext() {
    // Arrange
    ActivitiEntityEventImpl event = mock(ActivitiEntityEventImpl.class);
    when(event.getEntity()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> ActivitiEventBuilder.populateEventWithCurrentContext(event));
    verify(event).getEntity();
  }

  /**
   * Test {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventBuilder.populateEventWithCurrentContext(ActivitiEventImpl)"
  })
  public void testPopulateEventWithCurrentContext_givenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ActivitiEntityEventImpl event = mock(ActivitiEntityEventImpl.class);
    doThrow(new ActivitiException("An error occurred"))
        .when(event)
        .setExecutionId(Mockito.<String>any());
    when(event.getEntity())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> ActivitiEventBuilder.populateEventWithCurrentContext(event));
    verify(event).getEntity();
    verify(event).setExecutionId(null);
  }

  /**
   * Test {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}.
   *
   * <ul>
   *   <li>Given {@link DeadLetterJobEntityImpl} (default constructor) Deleted is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventBuilder.populateEventWithCurrentContext(ActivitiEventImpl)"
  })
  public void testPopulateEventWithCurrentContext_givenDeadLetterJobEntityImplDeletedIsTrue() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDeleted(true);
    deadLetterJobEntityImpl.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    deadLetterJobEntityImpl.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
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
    doThrow(new ActivitiException("An error occurred"))
        .when(event)
        .setExecutionId(Mockito.<String>any());
    when(event.getEntity()).thenReturn(deadLetterJobEntityImpl);

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> ActivitiEventBuilder.populateEventWithCurrentContext(event));
    verify(event).getEntity();
    verify(event).setExecutionId("42");
  }

  /**
   * Test {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}.
   *
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventBuilder.populateEventWithCurrentContext(ActivitiEventImpl)"
  })
  public void testPopulateEventWithCurrentContext_givenIdentityLinkEntityImpl() {
    // Arrange
    ActivitiEntityEventImpl event = mock(ActivitiEntityEventImpl.class);
    when(event.getEntity()).thenReturn(new IdentityLinkEntityImpl());

    // Act
    ActivitiEventBuilder.populateEventWithCurrentContext(event);

    // Assert
    verify(event).getEntity();
  }

  /**
   * Test {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventBuilder.populateEventWithCurrentContext(ActivitiEventImpl)"
  })
  public void testPopulateEventWithCurrentContext_givenNull() {
    // Arrange
    ActivitiEntityEventImpl event = mock(ActivitiEntityEventImpl.class);
    when(event.getEntity()).thenReturn(JSONObject.NULL);

    // Act
    ActivitiEventBuilder.populateEventWithCurrentContext(event);

    // Assert
    verify(event).getEntity();
  }

  /**
   * Test {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}.
   *
   * <ul>
   *   <li>Then calls {@link DeadLetterJobEntityImpl#getExecutionId()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventBuilder.populateEventWithCurrentContext(ActivitiEventImpl)"
  })
  public void testPopulateEventWithCurrentContext_thenCallsGetExecutionId() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = mock(DeadLetterJobEntityImpl.class);
    when(deadLetterJobEntityImpl.getExecutionId())
        .thenThrow(new ActivitiException("An error occurred"));

    ActivitiEntityEventImpl event = mock(ActivitiEntityEventImpl.class);
    when(event.getEntity()).thenReturn(deadLetterJobEntityImpl);

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> ActivitiEventBuilder.populateEventWithCurrentContext(event));
    verify(event).getEntity();
    verify(deadLetterJobEntityImpl).getExecutionId();
  }

  /**
   * Test {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventBuilder.populateEventWithCurrentContext(ActivitiEventImpl)"
  })
  public void testPopulateEventWithCurrentContext_thenCallsGetId() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenThrow(new ActivitiException("An error occurred"));

    ActivitiEntityEventImpl event = mock(ActivitiEntityEventImpl.class);
    when(event.getEntity()).thenReturn(executionEntityImpl);

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> ActivitiEventBuilder.populateEventWithCurrentContext(event));
    verify(event).getEntity();
    verify(executionEntityImpl).getId();
  }

  /**
   * Test {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEntityEventImpl#setProcessDefinitionId(String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventBuilder.populateEventWithCurrentContext(ActivitiEventImpl)"
  })
  public void testPopulateEventWithCurrentContext_thenCallsSetProcessDefinitionId() {
    // Arrange
    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDeleted(true);
    deadLetterJobEntityImpl.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    deadLetterJobEntityImpl.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
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

    // Assert
    verify(event).getEntity();
    verify(event).setExecutionId("42");
    verify(event).setProcessDefinitionId("42");
    verify(event).setProcessInstanceId("42");
  }

  /**
   * Test {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEntityEventImpl#setProcessDefinitionId(String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventBuilder.populateEventWithCurrentContext(ActivitiEventImpl)"
  })
  public void testPopulateEventWithCurrentContext_thenCallsSetProcessDefinitionId2() {
    // Arrange
    ActivitiEntityEventImpl event = mock(ActivitiEntityEventImpl.class);
    doNothing().when(event).setExecutionId(Mockito.<String>any());
    doNothing().when(event).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(event).setProcessInstanceId(Mockito.<String>any());
    when(event.getEntity())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    ActivitiEventBuilder.populateEventWithCurrentContext(event);

    // Assert
    verify(event).getEntity();
    verify(event).setExecutionId(null);
    verify(event).setProcessDefinitionId(null);
    verify(event).setProcessInstanceId(null);
  }
}
