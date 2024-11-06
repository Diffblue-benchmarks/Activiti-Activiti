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
   * Test {@link ActivitiEventBuilder#createGlobalEvent(ActivitiEventType)}.
   * <ul>
   *   <li>When {@code ENTITY_CREATED}.</li>
   *   <li>Then return {@link ActivitiEventImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createGlobalEvent(ActivitiEventType)}
   */
  @Test
  public void testCreateGlobalEvent_whenEntityCreated_thenReturnActivitiEventImpl() {
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
   * Test
   * {@link ActivitiEventBuilder#createEvent(ActivitiEventType, String, String, String)}.
   * <p>
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
   * Test
   * {@link ActivitiEventBuilder#createEntityEvent(ActivitiEventType, Object, String, String, String)}
   * with {@code type}, {@code entity}, {@code executionId},
   * {@code processInstanceId}, {@code processDefinitionId}.
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createEntityEvent(ActivitiEventType, Object, String, String, String)}
   */
  @Test
  public void testCreateEntityEventWithTypeEntityExecutionIdProcessInstanceIdProcessDefinitionId() {
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
   * Test
   * {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity, ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then return ExecutionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity, ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}
   */
  @Test
  public void testCreateSequenceFlowTakenEvent_given42_thenReturnExecutionIdIs42() {
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
    assertEquals("org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateSequenceFlowTakenEventResult.getSourceActivityBehaviorClass());
    assertEquals("org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateSequenceFlowTakenEventResult.getTargetActivityBehaviorClass());
  }

  /**
   * Test
   * {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity, ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}.
   * <ul>
   *   <li>Then return SourceActivityBehaviorClass is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity, ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}
   */
  @Test
  public void testCreateSequenceFlowTakenEvent_thenReturnSourceActivityBehaviorClassIsNull() {
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
    assertNull(actualCreateSequenceFlowTakenEventResult.getSourceActivityBehaviorClass());
  }

  /**
   * Test
   * {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity, ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}.
   * <ul>
   *   <li>Then return TargetActivityBehaviorClass is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity, ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}
   */
  @Test
  public void testCreateSequenceFlowTakenEvent_thenReturnTargetActivityBehaviorClassIsNull() {
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
    assertNull(actualCreateSequenceFlowTakenEventResult.getTargetActivityBehaviorClass());
  }

  /**
   * Test
   * {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity, ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}.
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity, ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}
   */
  @Test
  public void testCreateSequenceFlowTakenEvent_whenCreateWithEmptyRelationshipCollections() {
    // Arrange and Act
    ActivitiSequenceFlowTakenEvent actualCreateSequenceFlowTakenEventResult = ActivitiEventBuilder
        .createSequenceFlowTakenEvent(ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
            ActivitiEventType.ENTITY_CREATED, "42", "42", "Source Activity Name", "Source Activity Type",
            JSONObject.NULL, "42", "Target Activity Name", "Target Activity Type", JSONObject.NULL);

    // Assert
    assertTrue(actualCreateSequenceFlowTakenEventResult instanceof ActivitiSequenceFlowTakenEventImpl);
    assertEquals("org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateSequenceFlowTakenEventResult.getSourceActivityBehaviorClass());
    assertEquals("org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateSequenceFlowTakenEventResult.getTargetActivityBehaviorClass());
    assertNull(actualCreateSequenceFlowTakenEventResult.getExecutionId());
    assertNull(actualCreateSequenceFlowTakenEventResult.getProcessDefinitionId());
    assertNull(actualCreateSequenceFlowTakenEventResult.getProcessInstanceId());
  }

  /**
   * Test
   * {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity, ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return ExecutionId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createSequenceFlowTakenEvent(ExecutionEntity, ActivitiEventType, String, String, String, String, Object, String, String, String, Object)}
   */
  @Test
  public void testCreateSequenceFlowTakenEvent_whenNull_thenReturnExecutionIdIsNull() {
    // Arrange and Act
    ActivitiSequenceFlowTakenEvent actualCreateSequenceFlowTakenEventResult = ActivitiEventBuilder
        .createSequenceFlowTakenEvent(null, ActivitiEventType.ENTITY_CREATED, "42", "42", "Source Activity Name",
            "Source Activity Type", JSONObject.NULL, "42", "Target Activity Name", "Target Activity Type",
            JSONObject.NULL);

    // Assert
    assertTrue(actualCreateSequenceFlowTakenEventResult instanceof ActivitiSequenceFlowTakenEventImpl);
    assertEquals("org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateSequenceFlowTakenEventResult.getSourceActivityBehaviorClass());
    assertEquals("org.activiti.engine.impl.util.json.JSONObject.Null",
        actualCreateSequenceFlowTakenEventResult.getTargetActivityBehaviorClass());
    assertNull(actualCreateSequenceFlowTakenEventResult.getExecutionId());
    assertNull(actualCreateSequenceFlowTakenEventResult.getProcessDefinitionId());
    assertNull(actualCreateSequenceFlowTakenEventResult.getProcessInstanceId());
  }

  /**
   * Test
   * {@link ActivitiEventBuilder#createEntityExceptionEvent(ActivitiEventType, Object, Throwable, String, String, String)}
   * with {@code type}, {@code entity}, {@code cause}, {@code executionId},
   * {@code processInstanceId}, {@code processDefinitionId}.
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createEntityExceptionEvent(ActivitiEventType, Object, Throwable, String, String, String)}
   */
  @Test
  public void testCreateEntityExceptionEventWithTypeEntityCauseExecutionIdProcessInstanceIdProcessDefinitionId() {
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
   * Test
   * {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, String, String, String, String, String, FlowElement)}
   * with {@code type}, {@code activityId}, {@code activityName},
   * {@code executionId}, {@code processInstanceId}, {@code processDefinitionId},
   * {@code flowElement}.
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, String, String, String, String, String, FlowElement)}
   */
  @Test
  public void testCreateActivityEventWithTypeActivityIdActivityNameExecutionIdProcessInstanceIdProcessDefinitionIdFlowElement() {
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
   * Test
   * {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, String, String, String, String, String, FlowElement)}
   * with {@code type}, {@code activityId}, {@code activityName},
   * {@code executionId}, {@code processInstanceId}, {@code processDefinitionId},
   * {@code flowElement}.
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, String, String, String, String, String, FlowElement)}
   */
  @Test
  public void testCreateActivityEventWithTypeActivityIdActivityNameExecutionIdProcessInstanceIdProcessDefinitionIdFlowElement2() {
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
   * Test
   * {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, DelegateExecution, FlowElement)}
   * with {@code type}, {@code execution}, {@code flowElement}.
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, DelegateExecution, FlowElement)}
   */
  @Test
  public void testCreateActivityEventWithTypeExecutionFlowElement() {
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
   * Test
   * {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, DelegateExecution, FlowElement)}
   * with {@code type}, {@code execution}, {@code flowElement}.
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, DelegateExecution, FlowElement)}
   */
  @Test
  public void testCreateActivityEventWithTypeExecutionFlowElement2() {
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
   * Test
   * {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, DelegateExecution, FlowElement)}
   * with {@code type}, {@code execution}, {@code flowElement}.
   * <ul>
   *   <li>Then return ActivityType is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createActivityEvent(ActivitiEventType, DelegateExecution, FlowElement)}
   */
  @Test
  public void testCreateActivityEventWithTypeExecutionFlowElement_thenReturnActivityTypeIsNull() {
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
   * Test
   * {@link ActivitiEventBuilder#mayBeResolveExpression(String, DelegateExecution)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#mayBeResolveExpression(String, DelegateExecution)}
   */
  @Test
  public void testMayBeResolveExpression_whenEmptyString_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("",
        ActivitiEventBuilder.mayBeResolveExpression("", ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ActivitiEventBuilder#parseActivityType(FlowNode)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code adhocSubProcess}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiEventBuilder#parseActivityType(FlowNode)}
   */
  @Test
  public void testParseActivityType_whenAdhocSubProcess_thenReturnAdhocSubProcess() {
    // Arrange, Act and Assert
    assertEquals("adhocSubProcess", ActivitiEventBuilder.parseActivityType(new AdhocSubProcess()));
  }

  /**
   * Test {@link ActivitiEventBuilder#parseActivityBehavior(FlowNode)}.
   * <ul>
   *   <li>Then return
   * {@code org.activiti.engine.impl.util.json.JSONObject.Null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#parseActivityBehavior(FlowNode)}
   */
  @Test
  public void testParseActivityBehavior_thenReturnOrgActivitiEngineImplUtilJsonJSONObjectNull() {
    // Arrange
    AdhocSubProcess flowNode = new AdhocSubProcess();
    flowNode.setBehavior(JSONObject.NULL);

    // Act and Assert
    assertEquals("org.activiti.engine.impl.util.json.JSONObject.Null",
        ActivitiEventBuilder.parseActivityBehavior(flowNode));
  }

  /**
   * Test {@link ActivitiEventBuilder#parseActivityBehavior(FlowNode)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#parseActivityBehavior(FlowNode)}
   */
  @Test
  public void testParseActivityBehavior_whenAdhocSubProcess_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ActivitiEventBuilder.parseActivityBehavior(new AdhocSubProcess()));
  }

  /**
   * Test
   * {@link ActivitiEventBuilder#createActivityCancelledEvent(String, String, String, String, String, String, Object)}
   * with {@code activityId}, {@code activityName}, {@code executionId},
   * {@code processInstanceId}, {@code processDefinitionId}, {@code activityType},
   * {@code cause}.
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createActivityCancelledEvent(String, String, String, String, String, String, Object)}
   */
  @Test
  public void testCreateActivityCancelledEventWithActivityIdActivityNameExecutionIdProcessInstanceIdProcessDefinitionIdActivityTypeCause() {
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
   * Test
   * {@link ActivitiEventBuilder#createActivityCancelledEvent(ExecutionEntity, Object)}
   * with {@code execution}, {@code cause}.
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createActivityCancelledEvent(ExecutionEntity, Object)}
   */
  @Test
  public void testCreateActivityCancelledEventWithExecutionCause() {
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
   * Test
   * {@link ActivitiEventBuilder#createActivityCancelledEvent(ExecutionEntity, Object)}
   * with {@code execution}, {@code cause}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createActivityCancelledEvent(ExecutionEntity, Object)}
   */
  @Test
  public void testCreateActivityCancelledEventWithExecutionCause_thenThrowActivitiException() {
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
   * Test
   * {@link ActivitiEventBuilder#createProcessCancelledEvent(ProcessInstance, Object)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then return ExecutionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createProcessCancelledEvent(ProcessInstance, Object)}
   */
  @Test
  public void testCreateProcessCancelledEvent_given42_thenReturnExecutionIdIs42() {
    // Arrange
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenReturn("42");
    when(processInstance.getProcessInstanceId()).thenReturn("42");
    when(processInstance.getProcessDefinitionId()).thenReturn("42");

    // Act
    ActivitiProcessCancelledEvent actualCreateProcessCancelledEventResult = ActivitiEventBuilder
        .createProcessCancelledEvent(processInstance, JSONObject.NULL);

    // Assert
    verify(processInstance).getId();
    verify(processInstance).getProcessInstanceId();
    verify(processInstance).getProcessDefinitionId();
    assertTrue(actualCreateProcessCancelledEventResult instanceof ActivitiProcessCancelledEventImpl);
    assertEquals("42", actualCreateProcessCancelledEventResult.getExecutionId());
    assertEquals("42", actualCreateProcessCancelledEventResult.getProcessDefinitionId());
    assertEquals("42", actualCreateProcessCancelledEventResult.getProcessInstanceId());
    assertSame(processInstance, actualCreateProcessCancelledEventResult.getEntity());
  }

  /**
   * Test
   * {@link ActivitiEventBuilder#createProcessCancelledEvent(ProcessInstance, Object)}.
   * <ul>
   *   <li>Then return ExecutionId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createProcessCancelledEvent(ProcessInstance, Object)}
   */
  @Test
  public void testCreateProcessCancelledEvent_thenReturnExecutionIdIsNull() {
    // Arrange
    ExecutionEntityImpl processInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ActivitiProcessCancelledEvent actualCreateProcessCancelledEventResult = ActivitiEventBuilder
        .createProcessCancelledEvent(processInstance, JSONObject.NULL);

    // Assert
    assertTrue(actualCreateProcessCancelledEventResult instanceof ActivitiProcessCancelledEventImpl);
    assertNull(actualCreateProcessCancelledEventResult.getExecutionId());
    assertNull(actualCreateProcessCancelledEventResult.getProcessDefinitionId());
    assertNull(actualCreateProcessCancelledEventResult.getProcessInstanceId());
    assertSame(processInstance, actualCreateProcessCancelledEventResult.getEntity());
  }

  /**
   * Test
   * {@link ActivitiEventBuilder#createProcessCancelledEvent(ProcessInstance, Object)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createProcessCancelledEvent(ProcessInstance, Object)}
   */
  @Test
  public void testCreateProcessCancelledEvent_thenThrowActivitiException() {
    // Arrange
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> ActivitiEventBuilder.createProcessCancelledEvent(processInstance, JSONObject.NULL));
    verify(processInstance).getId();
  }

  /**
   * Test
   * {@link ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String, Object)}.
   * <ul>
   *   <li>Then return ActivityType is {@code adhocSubProcess}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String, Object)}
   */
  @Test
  public void testCreateActivitiySignalledEvent_thenReturnActivityTypeIsAdhocSubProcess() {
    // Arrange
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    // Act
    ActivitiSignalEvent actualCreateActivitiySignalledEventResult = ActivitiEventBuilder
        .createActivitiySignalledEvent(execution, "Signal Name", JSONObject.NULL);

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
   * Test
   * {@link ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String, Object)}.
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String, Object)}
   */
  @Test
  public void testCreateActivitiySignalledEvent_whenCreateWithEmptyRelationshipCollections() {
    // Arrange and Act
    ActivitiSignalEvent actualCreateActivitiySignalledEventResult = ActivitiEventBuilder.createActivitiySignalledEvent(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Signal Name", JSONObject.NULL);

    // Assert
    assertTrue(actualCreateActivitiySignalledEventResult instanceof ActivitiSignalEventImpl);
    assertNull(actualCreateActivitiySignalledEventResult.getActivityId());
    assertNull(actualCreateActivitiySignalledEventResult.getActivityType());
    assertNull(actualCreateActivitiySignalledEventResult.getExecutionId());
    assertNull(actualCreateActivitiySignalledEventResult.getProcessDefinitionId());
    assertNull(actualCreateActivitiySignalledEventResult.getProcessInstanceId());
  }

  /**
   * Test
   * {@link ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String, Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return ActivityId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createActivitiySignalledEvent(DelegateExecution, String, Object)}
   */
  @Test
  public void testCreateActivitiySignalledEvent_whenNull_thenReturnActivityIdIsNull() {
    // Arrange and Act
    ActivitiSignalEvent actualCreateActivitiySignalledEventResult = ActivitiEventBuilder
        .createActivitiySignalledEvent(null, "Signal Name", JSONObject.NULL);

    // Assert
    assertTrue(actualCreateActivitiySignalledEventResult instanceof ActivitiSignalEventImpl);
    assertNull(actualCreateActivitiySignalledEventResult.getActivityId());
    assertNull(actualCreateActivitiySignalledEventResult.getActivityType());
    assertNull(actualCreateActivitiySignalledEventResult.getExecutionId());
    assertNull(actualCreateActivitiySignalledEventResult.getProcessDefinitionId());
    assertNull(actualCreateActivitiySignalledEventResult.getProcessInstanceId());
  }

  /**
   * Test
   * {@link ActivitiEventBuilder#createMessageReceivedEvent(DelegateExecution, String, String, Object)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then return ActivityType is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createMessageReceivedEvent(DelegateExecution, String, String, Object)}
   */
  @Test
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
   * Test
   * {@link ActivitiEventBuilder#createMessageReceivedEvent(DelegateExecution, String, String, Object)}.
   * <ul>
   *   <li>Then return ActivityType is {@code adhocSubProcess}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createMessageReceivedEvent(DelegateExecution, String, String, Object)}
   */
  @Test
  public void testCreateMessageReceivedEvent_thenReturnActivityTypeIsAdhocSubProcess() {
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
   * Test
   * {@link ActivitiEventBuilder#createMessageWaitingEvent(DelegateExecution, String, String)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then return ActivityType is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createMessageWaitingEvent(DelegateExecution, String, String)}
   */
  @Test
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
   * Test
   * {@link ActivitiEventBuilder#createMessageWaitingEvent(DelegateExecution, String, String)}.
   * <ul>
   *   <li>Then return ActivityType is {@code adhocSubProcess}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createMessageWaitingEvent(DelegateExecution, String, String)}
   */
  @Test
  public void testCreateMessageWaitingEvent_thenReturnActivityTypeIsAdhocSubProcess() {
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
   * Test
   * {@link ActivitiEventBuilder#createMessageSentEvent(DelegateExecution, String, String, Object)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then return ActivityType is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createMessageSentEvent(DelegateExecution, String, String, Object)}
   */
  @Test
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
   * Test
   * {@link ActivitiEventBuilder#createMessageSentEvent(DelegateExecution, String, String, Object)}.
   * <ul>
   *   <li>Then return ActivityType is {@code adhocSubProcess}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createMessageSentEvent(DelegateExecution, String, String, Object)}
   */
  @Test
  public void testCreateMessageSentEvent_thenReturnActivityTypeIsAdhocSubProcess() {
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
   * Test
   * {@link ActivitiEventBuilder#createErrorEvent(ActivitiEventType, String, String, String, String, String, String)}.
   * <p>
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
   * Test
   * {@link ActivitiEventBuilder#createVariableEvent(ActivitiEventType, String, Object, VariableType, String, String, String, String)}.
   * <ul>
   *   <li>Then return VariableType is {@link BigDecimalType} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createVariableEvent(ActivitiEventType, String, Object, VariableType, String, String, String, String)}
   */
  @Test
  public void testCreateVariableEvent_thenReturnVariableTypeIsBigDecimalType() {
    // Arrange
    BigDecimalType variableType = new BigDecimalType();

    // Act
    ActivitiVariableEvent actualCreateVariableEventResult = ActivitiEventBuilder.createVariableEvent(
        ActivitiEventType.ENTITY_CREATED, "Variable Name", JSONObject.NULL, variableType, "42", "42", "42", "42");

    // Assert
    assertTrue(actualCreateVariableEventResult instanceof ActivitiVariableEventImpl);
    assertSame(variableType, actualCreateVariableEventResult.getVariableType());
  }

  /**
   * Test
   * {@link ActivitiEventBuilder#createVariableEvent(ActivitiEventType, String, Object, VariableType, String, String, String, String)}.
   * <ul>
   *   <li>When {@link BigDecimalType}.</li>
   *   <li>Then return ProcessDefinitionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createVariableEvent(ActivitiEventType, String, Object, VariableType, String, String, String, String)}
   */
  @Test
  public void testCreateVariableEvent_whenBigDecimalType_thenReturnProcessDefinitionIdIs42() {
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
   * Test
   * {@link ActivitiEventBuilder#createVariableUpdateEvent(VariableInstanceEntity, Object, String, String)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>Then return ExecutionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createVariableUpdateEvent(VariableInstanceEntity, Object, String, String)}
   */
  @Test
  public void testCreateVariableUpdateEvent_givenNull_thenReturnExecutionIdIs42() {
    // Arrange
    VariableInstanceEntityImpl variableInstance = mock(VariableInstanceEntityImpl.class);
    when(variableInstance.getValue()).thenReturn(JSONObject.NULL);
    when(variableInstance.getExecutionId()).thenReturn("42");
    when(variableInstance.getName()).thenReturn("Name");
    when(variableInstance.getTaskId()).thenReturn("42");
    when(variableInstance.getType()).thenReturn(new BigDecimalType());
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
    assertEquals("42", actualCreateVariableUpdateEventResult.getTaskId());
    assertEquals("Name", actualCreateVariableUpdateEventResult.getVariableName());
    assertSame(object, actualCreateVariableUpdateEventResult.getVariableValue());
  }

  /**
   * Test
   * {@link ActivitiEventBuilder#createVariableUpdateEvent(VariableInstanceEntity, Object, String, String)}.
   * <ul>
   *   <li>Then return VariableValue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#createVariableUpdateEvent(VariableInstanceEntity, Object, String, String)}
   */
  @Test
  public void testCreateVariableUpdateEvent_thenReturnVariableValueIsNull() {
    // Arrange
    VariableInstanceEntityImpl variableInstance = new VariableInstanceEntityImpl();
    variableInstance.setType(new BigDecimalType());

    // Act
    ActivitiVariableUpdatedEventImpl actualCreateVariableUpdateEventResult = ActivitiEventBuilder
        .createVariableUpdateEvent(variableInstance, JSONObject.NULL, "42", "42");

    // Assert
    assertNull(actualCreateVariableUpdateEventResult.getVariableValue());
    assertNull(actualCreateVariableUpdateEventResult.getExecutionId());
    assertNull(actualCreateVariableUpdateEventResult.getTaskId());
    assertNull(actualCreateVariableUpdateEventResult.getVariableName());
  }

  /**
   * Test
   * {@link ActivitiEventBuilder#createMembershipEvent(ActivitiEventType, String, String)}.
   * <p>
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
   * Test
   * {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}
   */
  @Test
  public void testPopulateEventWithCurrentContext_givenCreateWithEmptyRelationshipCollections() {
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

  /**
   * Test
   * {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}.
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}
   */
  @Test
  public void testPopulateEventWithCurrentContext_givenIdentityLinkEntityImpl() {
    // Arrange
    ActivitiEntityEventImpl event = mock(ActivitiEntityEventImpl.class);
    when(event.getEntity()).thenReturn(new IdentityLinkEntityImpl());

    // Act
    ActivitiEventBuilder.populateEventWithCurrentContext(event);

    // Assert that nothing has changed
    verify(event).getEntity();
  }

  /**
   * Test
   * {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}
   */
  @Test
  public void testPopulateEventWithCurrentContext_givenNull() {
    // Arrange
    ActivitiEntityEventImpl event = mock(ActivitiEntityEventImpl.class);
    when(event.getEntity()).thenReturn(JSONObject.NULL);

    // Act
    ActivitiEventBuilder.populateEventWithCurrentContext(event);

    // Assert that nothing has changed
    verify(event).getEntity();
  }

  /**
   * Test
   * {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventImpl#setExecutionId(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEventBuilder#populateEventWithCurrentContext(ActivitiEventImpl)}
   */
  @Test
  public void testPopulateEventWithCurrentContext_thenCallsSetExecutionId() {
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
}
