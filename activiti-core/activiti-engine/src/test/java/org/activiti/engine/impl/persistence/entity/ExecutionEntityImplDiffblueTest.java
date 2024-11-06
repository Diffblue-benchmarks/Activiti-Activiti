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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.junit.Test;

public class ExecutionEntityImplDiffblueTest {
  /**
   * Test {@link ExecutionEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_givenCreateWithEmptyRelationshipCollections() {
    // Arrange and Act
    Object actualPersistentState = ExecutionEntityImpl.createWithEmptyRelationshipCollections().getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(23, ((Map<Object, Object>) actualPersistentState).size());
    assertNull(((Map<Object, Object>) actualPersistentState).get("activityId"));
    assertNull(((Map<Object, Object>) actualPersistentState).get("parentId"));
    assertNull(((Map<Object, Object>) actualPersistentState).get("processDefinitionId"));
    assertNull(((Map<Object, Object>) actualPersistentState).get("superExecution"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("isActive"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("isConcurrent"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("isEventScope"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("isScope"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("suspendedJobCount"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("suspensionState"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("timerJobCount"));
  }

  /**
   * Test {@link ExecutionEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections LockTime is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_givenCreateWithEmptyRelationshipCollectionsLockTimeIsDate() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(java.sql.Date.class));

    // Act
    Object actualPersistentState = createWithEmptyRelationshipCollectionsResult.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(23, ((Map<Object, Object>) actualPersistentState).size());
    assertNull(((Map<Object, Object>) actualPersistentState).get("activityId"));
    assertNull(((Map<Object, Object>) actualPersistentState).get("parentId"));
    assertNull(((Map<Object, Object>) actualPersistentState).get("processDefinitionId"));
    assertNull(((Map<Object, Object>) actualPersistentState).get("superExecution"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("isActive"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("isConcurrent"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("isEventScope"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("isScope"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("suspendedJobCount"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("suspensionState"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("timerJobCount"));
  }

  /**
   * Test {@link ExecutionEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Then return size is {@link Float#PRECISION}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_thenReturnSizeIsPrecision() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.forceUpdate();

    // Act
    Object actualPersistentState = createWithEmptyRelationshipCollectionsResult.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(Float.PRECISION, ((Map<Object, Object>) actualPersistentState).size());
    assertNull(((Map<Object, Object>) actualPersistentState).get("parentId"));
    assertNull(((Map<Object, Object>) actualPersistentState).get("processDefinitionId"));
    assertNull(((Map<Object, Object>) actualPersistentState).get("superExecution"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("forcedUpdate"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("isActive"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("isConcurrent"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("isEventScope"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("isScope"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("suspendedJobCount"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("suspensionState"));
    assertTrue(((Map<Object, Object>) actualPersistentState).containsKey("timerJobCount"));
  }

  /**
   * Test {@link ExecutionEntityImpl#getCurrentFlowElement()}.
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getCurrentFlowElement()}
   */
  @Test
  public void testGetCurrentFlowElement() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getCurrentFlowElement());
  }

  /**
   * Test {@link ExecutionEntityImpl#getCurrentFlowElement()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getCurrentFlowElement()}
   */
  @Test
  public void testGetCurrentFlowElement_givenCreateWithEmptyRelationshipCollections() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getCurrentFlowElement());
  }

  /**
   * Test {@link ExecutionEntityImpl#setCurrentFlowElement(FlowElement)}.
   * <p>
   * Method under test:
   * {@link ExecutionEntityImpl#setCurrentFlowElement(FlowElement)}
   */
  @Test
  public void testSetCurrentFlowElement() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    AdhocSubProcess currentFlowElement = new AdhocSubProcess();

    // Act
    createWithEmptyRelationshipCollectionsResult.setCurrentFlowElement(currentFlowElement);

    // Assert
    FlowElement flowElement = createWithEmptyRelationshipCollectionsResult.currentFlowElement;
    assertTrue(flowElement instanceof AdhocSubProcess);
    assertEquals("Parallel", ((AdhocSubProcess) flowElement).getOrdering());
    assertNull(((AdhocSubProcess) flowElement).getBehavior());
    assertNull(((AdhocSubProcess) flowElement).getDefaultFlow());
    assertNull(((AdhocSubProcess) flowElement).getFailedJobRetryTimeCycleValue());
    assertNull(((AdhocSubProcess) flowElement).getCompletionCondition());
    assertNull(flowElement.getId());
    assertNull(flowElement.getDocumentation());
    assertNull(flowElement.getName());
    assertNull(flowElement.getParentContainer());
    assertNull(((AdhocSubProcess) flowElement).getIoSpecification());
    assertNull(((AdhocSubProcess) flowElement).getLoopCharacteristics());
    assertNull(flowElement.getSubProcess());
    assertEquals(0, flowElement.getXmlColumnNumber());
    assertEquals(0, flowElement.getXmlRowNumber());
    assertFalse(((AdhocSubProcess) flowElement).hasMultiInstanceLoopCharacteristics());
    assertFalse(((AdhocSubProcess) flowElement).isForCompensation());
    assertFalse(((AdhocSubProcess) flowElement).hasSequentialOrdering());
    assertFalse(((AdhocSubProcess) flowElement).isAsynchronous());
    assertFalse(((AdhocSubProcess) flowElement).isNotExclusive());
    assertTrue(((AdhocSubProcess) flowElement).hasParallelOrdering());
    assertTrue(((AdhocSubProcess) flowElement).isCancelRemainingInstances());
    assertTrue(((AdhocSubProcess) flowElement).isExclusive());
    assertSame(currentFlowElement, createWithEmptyRelationshipCollectionsResult.getCurrentFlowElement());
  }

  /**
   * Test {@link ExecutionEntityImpl#setCurrentFlowElement(FlowElement)}.
   * <p>
   * Method under test:
   * {@link ExecutionEntityImpl#setCurrentFlowElement(FlowElement)}
   */
  @Test
  public void testSetCurrentFlowElement2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setCurrentFlowElement(null);

    // Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getCurrentFlowElement());
    assertNull(createWithEmptyRelationshipCollectionsResult.currentFlowElement);
  }

  /**
   * Test {@link ExecutionEntityImpl#getExecutions()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections LockTime is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getExecutions()}
   */
  @Test
  public void testGetExecutions_givenCreateWithEmptyRelationshipCollectionsLockTimeIsDate() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(java.sql.Date.class));

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getExecutions().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getExecutions()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getExecutions()}
   */
  @Test
  public void testGetExecutions_givenCreateWithEmptyRelationshipCollections_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getExecutions().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getProcessInstance()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getProcessInstance()}
   */
  @Test
  public void testGetProcessInstance_givenCreateWithEmptyRelationshipCollections() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getProcessInstance());
  }

  /**
   * Test {@link ExecutionEntityImpl#getProcessInstance()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections LockTime is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getProcessInstance()}
   */
  @Test
  public void testGetProcessInstance_givenCreateWithEmptyRelationshipCollectionsLockTimeIsDate() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getProcessInstance());
  }

  /**
   * Test {@link ExecutionEntityImpl#setProcessInstance(ExecutionEntity)}.
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionEntityImpl#setProcessInstance(ExecutionEntity)}
   */
  @Test
  public void testSetProcessInstance_whenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    ExecutionEntityImpl processInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setProcessInstance(processInstance);

    // Assert
    assertSame(processInstance, processInstance.getSourceActivityExecution());
  }

  /**
   * Test {@link ExecutionEntityImpl#setProcessInstance(ExecutionEntity)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionEntityImpl#setProcessInstance(ExecutionEntity)}
   */
  @Test
  public void testSetProcessInstance_whenNull() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setProcessInstance(null);

    // Assert
    assertSame(createWithEmptyRelationshipCollectionsResult,
        createWithEmptyRelationshipCollectionsResult.getSourceActivityExecution());
  }

  /**
   * Test {@link ExecutionEntityImpl#isProcessInstanceType()}.
   * <p>
   * Method under test: {@link ExecutionEntityImpl#isProcessInstanceType()}
   */
  @Test
  public void testIsProcessInstanceType() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(java.sql.Date.class));

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.isProcessInstanceType());
  }

  /**
   * Test {@link ExecutionEntityImpl#isProcessInstanceType()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#isProcessInstanceType()}
   */
  @Test
  public void testIsProcessInstanceType_givenCreateWithEmptyRelationshipCollections() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().isProcessInstanceType());
  }

  /**
   * Test {@link ExecutionEntityImpl#isProcessInstanceType()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#isProcessInstanceType()}
   */
  @Test
  public void testIsProcessInstanceType_thenReturnFalse() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setParentId("foo");

    // Act and Assert
    assertFalse(createWithEmptyRelationshipCollectionsResult.isProcessInstanceType());
  }

  /**
   * Test {@link ExecutionEntityImpl#getParent()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections LockTime is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getParent()}
   */
  @Test
  public void testGetParent_givenCreateWithEmptyRelationshipCollectionsLockTimeIsDate() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getParent());
  }

  /**
   * Test {@link ExecutionEntityImpl#getParent()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getParent()}
   */
  @Test
  public void testGetParent_givenCreateWithEmptyRelationshipCollections_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getParent());
  }

  /**
   * Test {@link ExecutionEntityImpl#setParent(ExecutionEntity)}.
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#setParent(ExecutionEntity)}
   */
  @Test
  public void testSetParent_whenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    ExecutionEntityImpl parent = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setParent(parent);

    // Assert
    assertSame(parent, parent.getSourceActivityExecution());
  }

  /**
   * Test {@link ExecutionEntityImpl#setParent(ExecutionEntity)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#setParent(ExecutionEntity)}
   */
  @Test
  public void testSetParent_whenNull() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setParent(null);

    // Assert
    assertSame(createWithEmptyRelationshipCollectionsResult,
        createWithEmptyRelationshipCollectionsResult.getSourceActivityExecution());
  }

  /**
   * Test {@link ExecutionEntityImpl#getSuperExecution()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getSuperExecution()}
   */
  @Test
  public void testGetSuperExecution_givenCreateWithEmptyRelationshipCollections() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getSuperExecution());
  }

  /**
   * Test {@link ExecutionEntityImpl#getSuperExecution()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections LockTime is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getSuperExecution()}
   */
  @Test
  public void testGetSuperExecution_givenCreateWithEmptyRelationshipCollectionsLockTimeIsDate() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getSuperExecution());
  }

  /**
   * Test {@link ExecutionEntityImpl#setSuperExecution(ExecutionEntity)}.
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionEntityImpl#setSuperExecution(ExecutionEntity)}
   */
  @Test
  public void testSetSuperExecution_whenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    ExecutionEntityImpl superExecution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setSuperExecution(superExecution);

    // Assert
    assertSame(superExecution, superExecution.getSourceActivityExecution());
  }

  /**
   * Test {@link ExecutionEntityImpl#setSuperExecution(ExecutionEntity)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionEntityImpl#setSuperExecution(ExecutionEntity)}
   */
  @Test
  public void testSetSuperExecution_whenNull() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setSuperExecution(null);

    // Assert
    assertSame(createWithEmptyRelationshipCollectionsResult,
        createWithEmptyRelationshipCollectionsResult.getSourceActivityExecution());
  }

  /**
   * Test {@link ExecutionEntityImpl#getSubProcessInstance()}.
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getSubProcessInstance()}
   */
  @Test
  public void testGetSubProcessInstance_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    ExecutionEntityImpl subProcessInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setSubProcessInstance(subProcessInstance);

    // Act
    ExecutionEntityImpl actualSubProcessInstance = createWithEmptyRelationshipCollectionsResult.getSubProcessInstance();

    // Assert
    assertSame(subProcessInstance, actualSubProcessInstance);
    List<EventSubscriptionEntity> eventSubscriptionEntityList = actualSubProcessInstance.eventSubscriptions;
    ExecutionEntityImpl executionEntityImpl = createWithEmptyRelationshipCollectionsResult.subProcessInstance;
    assertSame(eventSubscriptionEntityList, executionEntityImpl.getEventSubscriptions());
    assertSame(eventSubscriptionEntityList, executionEntityImpl.eventSubscriptions);
    List<ExecutionEntityImpl> executionEntityImplList = actualSubProcessInstance.executions;
    assertSame(executionEntityImplList, executionEntityImpl.getExecutions());
    assertSame(executionEntityImplList, executionEntityImpl.executions);
    List<IdentityLinkEntity> identityLinkEntityList = actualSubProcessInstance.identityLinks;
    assertSame(identityLinkEntityList, executionEntityImpl.getIdentityLinks());
    assertSame(identityLinkEntityList, executionEntityImpl.identityLinks);
    List<JobEntity> jobEntityList = actualSubProcessInstance.jobs;
    assertSame(jobEntityList, executionEntityImpl.getJobs());
    assertSame(jobEntityList, executionEntityImpl.jobs);
    List<TaskEntity> taskEntityList = actualSubProcessInstance.tasks;
    assertSame(taskEntityList, executionEntityImpl.getTasks());
    assertSame(taskEntityList, executionEntityImpl.tasks);
    List<TimerJobEntity> timerJobEntityList = actualSubProcessInstance.timerJobs;
    assertSame(timerJobEntityList, executionEntityImpl.getTimerJobs());
    assertSame(timerJobEntityList, executionEntityImpl.timerJobs);
    assertSame(actualSubProcessInstance.variableInstances, executionEntityImpl.variableInstances);
  }

  /**
   * Test {@link ExecutionEntityImpl#setRootProcessInstance(ExecutionEntity)}.
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionEntityImpl#setRootProcessInstance(ExecutionEntity)}
   */
  @Test
  public void testSetRootProcessInstance_whenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    ExecutionEntityImpl rootProcessInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setRootProcessInstance(rootProcessInstance);

    // Assert
    assertSame(rootProcessInstance, rootProcessInstance.getSourceActivityExecution());
  }

  /**
   * Test {@link ExecutionEntityImpl#setRootProcessInstance(ExecutionEntity)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionEntityImpl#setRootProcessInstance(ExecutionEntity)}
   */
  @Test
  public void testSetRootProcessInstance_whenNull() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setRootProcessInstance(null);

    // Assert
    assertSame(createWithEmptyRelationshipCollectionsResult,
        createWithEmptyRelationshipCollectionsResult.getSourceActivityExecution());
  }

  /**
   * Test {@link ExecutionEntityImpl#isRootExecution()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#isRootExecution()}
   */
  @Test
  public void testIsRootExecution_givenCreateWithEmptyRelationshipCollections_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(ExecutionEntityImpl.createWithEmptyRelationshipCollections().isRootExecution());
  }

  /**
   * Test {@link ExecutionEntityImpl#isRootExecution()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#isRootExecution()}
   */
  @Test
  public void testIsRootExecution_thenReturnFalse() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setId("42");

    // Act and Assert
    assertFalse(createWithEmptyRelationshipCollectionsResult.isRootExecution());
  }

  /**
   * Test {@link ExecutionEntityImpl#isRootExecution()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#isRootExecution()}
   */
  @Test
  public void testIsRootExecution_thenReturnTrue() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setRootProcessInstanceId("42");
    createWithEmptyRelationshipCollectionsResult.setId("42");

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.isRootExecution());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExecutionEntityImpl#forceUpdate()}
   *   <li>{@link ExecutionEntityImpl#setActive(boolean)}
   *   <li>{@link ExecutionEntityImpl#setAppVersion(Integer)}
   *   <li>{@link ExecutionEntityImpl#setBusinessKey(String)}
   *   <li>{@link ExecutionEntityImpl#setConcurrent(boolean)}
   *   <li>{@link ExecutionEntityImpl#setCountEnabled(boolean)}
   *   <li>{@link ExecutionEntityImpl#setCurrentActivitiListener(ActivitiListener)}
   *   <li>{@link ExecutionEntityImpl#setDeadLetterJobCount(int)}
   *   <li>{@link ExecutionEntityImpl#setDeleteReason(String)}
   *   <li>{@link ExecutionEntityImpl#setDeleted(boolean)}
   *   <li>{@link ExecutionEntityImpl#setDeploymentId(String)}
   *   <li>{@link ExecutionEntityImpl#setDescription(String)}
   *   <li>{@link ExecutionEntityImpl#setEnded(boolean)}
   *   <li>{@link ExecutionEntityImpl#setEventName(String)}
   *   <li>{@link ExecutionEntityImpl#setEventScope(boolean)}
   *   <li>{@link ExecutionEntityImpl#setEventSubscriptionCount(int)}
   *   <li>{@link ExecutionEntityImpl#setIdentityLinkCount(int)}
   *   <li>{@link ExecutionEntityImpl#setJobCount(int)}
   *   <li>{@link ExecutionEntityImpl#setLocalizedDescription(String)}
   *   <li>{@link ExecutionEntityImpl#setLocalizedName(String)}
   *   <li>{@link ExecutionEntityImpl#setLockTime(Date)}
   *   <li>{@link ExecutionEntityImpl#setMultiInstanceRoot(boolean)}
   *   <li>{@link ExecutionEntityImpl#setName(String)}
   *   <li>{@link ExecutionEntityImpl#setParentId(String)}
   *   <li>{@link ExecutionEntityImpl#setParentProcessInstanceId(String)}
   *   <li>{@link ExecutionEntityImpl#setProcessDefinitionId(String)}
   *   <li>{@link ExecutionEntityImpl#setProcessDefinitionKey(String)}
   *   <li>{@link ExecutionEntityImpl#setProcessDefinitionName(String)}
   *   <li>{@link ExecutionEntityImpl#setProcessDefinitionVersion(Integer)}
   *   <li>{@link ExecutionEntityImpl#setProcessInstanceId(String)}
   *   <li>{@link ExecutionEntityImpl#setQueryVariables(List)}
   *   <li>{@link ExecutionEntityImpl#setRootProcessInstanceId(String)}
   *   <li>{@link ExecutionEntityImpl#setScope(boolean)}
   *   <li>{@link ExecutionEntityImpl#setStartTime(Date)}
   *   <li>{@link ExecutionEntityImpl#setStartUserId(String)}
   *   <li>{@link ExecutionEntityImpl#setSubProcessInstance(ExecutionEntity)}
   *   <li>{@link ExecutionEntityImpl#setSuspendedJobCount(int)}
   *   <li>{@link ExecutionEntityImpl#setSuspensionState(int)}
   *   <li>{@link ExecutionEntityImpl#setTaskCount(int)}
   *   <li>{@link ExecutionEntityImpl#setTenantId(String)}
   *   <li>{@link ExecutionEntityImpl#setTimerJobCount(int)}
   *   <li>{@link ExecutionEntityImpl#setVariableCount(int)}
   *   <li>{@link ExecutionEntityImpl#inactivate()}
   *   <li>{@link ExecutionEntityImpl#getActivityId()}
   *   <li>{@link ExecutionEntityImpl#getActivityName()}
   *   <li>{@link ExecutionEntityImpl#getAppVersion()}
   *   <li>{@link ExecutionEntityImpl#getBusinessKey()}
   *   <li>{@link ExecutionEntityImpl#getCurrentActivitiListener()}
   *   <li>{@link ExecutionEntityImpl#getCurrentActivityId()}
   *   <li>{@link ExecutionEntityImpl#getDeadLetterJobCount()}
   *   <li>{@link ExecutionEntityImpl#getDeleteReason()}
   *   <li>{@link ExecutionEntityImpl#getDeploymentId()}
   *   <li>{@link ExecutionEntityImpl#getEventName()}
   *   <li>{@link ExecutionEntityImpl#getEventSubscriptionCount()}
   *   <li>{@link ExecutionEntityImpl#getIdentityLinkCount()}
   *   <li>{@link ExecutionEntityImpl#getJobCount()}
   *   <li>{@link ExecutionEntityImpl#getLocalizedDescription()}
   *   <li>{@link ExecutionEntityImpl#getLocalizedName()}
   *   <li>{@link ExecutionEntityImpl#getLockTime()}
   *   <li>{@link ExecutionEntityImpl#getParentId()}
   *   <li>{@link ExecutionEntityImpl#getParentProcessInstanceId()}
   *   <li>{@link ExecutionEntityImpl#getProcessDefinitionId()}
   *   <li>{@link ExecutionEntityImpl#getProcessDefinitionKey()}
   *   <li>{@link ExecutionEntityImpl#getProcessDefinitionName()}
   *   <li>{@link ExecutionEntityImpl#getProcessDefinitionVersion()}
   *   <li>{@link ExecutionEntityImpl#getProcessInstanceId()}
   *   <li>{@link ExecutionEntityImpl#getRootProcessInstanceId()}
   *   <li>{@link ExecutionEntityImpl#getStartTime()}
   *   <li>{@link ExecutionEntityImpl#getStartUserId()}
   *   <li>{@link ExecutionEntityImpl#getSuperExecutionId()}
   *   <li>{@link ExecutionEntityImpl#getSuspendedJobCount()}
   *   <li>{@link ExecutionEntityImpl#getSuspensionState()}
   *   <li>{@link ExecutionEntityImpl#getTaskCount()}
   *   <li>{@link ExecutionEntityImpl#getTenantId()}
   *   <li>{@link ExecutionEntityImpl#getTimerJobCount()}
   *   <li>{@link ExecutionEntityImpl#getVariableCount()}
   *   <li>{@link ExecutionEntityImpl#isActive()}
   *   <li>{@link ExecutionEntityImpl#isConcurrent()}
   *   <li>{@link ExecutionEntityImpl#isCountEnabled()}
   *   <li>{@link ExecutionEntityImpl#isDeleted()}
   *   <li>{@link ExecutionEntityImpl#isEnded()}
   *   <li>{@link ExecutionEntityImpl#isEventScope()}
   *   <li>{@link ExecutionEntityImpl#isMultiInstanceRoot()}
   *   <li>{@link ExecutionEntityImpl#isScope()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.forceUpdate();
    createWithEmptyRelationshipCollectionsResult.setActive(true);
    createWithEmptyRelationshipCollectionsResult.setAppVersion(1);
    createWithEmptyRelationshipCollectionsResult.setBusinessKey("Business Key");
    createWithEmptyRelationshipCollectionsResult.setConcurrent(true);
    createWithEmptyRelationshipCollectionsResult.setCountEnabled(true);
    ActivitiListener currentActivitiListener = new ActivitiListener();
    createWithEmptyRelationshipCollectionsResult.setCurrentActivitiListener(currentActivitiListener);
    createWithEmptyRelationshipCollectionsResult.setDeadLetterJobCount(3);
    createWithEmptyRelationshipCollectionsResult.setDeleteReason("Just cause");
    createWithEmptyRelationshipCollectionsResult.setDeleted(true);
    createWithEmptyRelationshipCollectionsResult.setDeploymentId("42");
    createWithEmptyRelationshipCollectionsResult.setDescription("The characteristics of someone or something");
    createWithEmptyRelationshipCollectionsResult.setEnded(true);
    createWithEmptyRelationshipCollectionsResult.setEventName("Event Name");
    createWithEmptyRelationshipCollectionsResult.setEventScope(true);
    createWithEmptyRelationshipCollectionsResult.setEventSubscriptionCount(3);
    createWithEmptyRelationshipCollectionsResult.setIdentityLinkCount(1);
    createWithEmptyRelationshipCollectionsResult.setJobCount(3);
    createWithEmptyRelationshipCollectionsResult.setLocalizedDescription("Localized Description");
    createWithEmptyRelationshipCollectionsResult.setLocalizedName("Localized Name");
    Date lockTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    createWithEmptyRelationshipCollectionsResult.setLockTime(lockTime);
    createWithEmptyRelationshipCollectionsResult.setMultiInstanceRoot(true);
    createWithEmptyRelationshipCollectionsResult.setName("Name");
    createWithEmptyRelationshipCollectionsResult.setParentId("42");
    createWithEmptyRelationshipCollectionsResult.setParentProcessInstanceId("42");
    createWithEmptyRelationshipCollectionsResult.setProcessDefinitionId("42");
    createWithEmptyRelationshipCollectionsResult.setProcessDefinitionKey("Process Definition Key");
    createWithEmptyRelationshipCollectionsResult.setProcessDefinitionName("Process Definition Name");
    createWithEmptyRelationshipCollectionsResult.setProcessDefinitionVersion(1);
    createWithEmptyRelationshipCollectionsResult.setProcessInstanceId("42");
    createWithEmptyRelationshipCollectionsResult.setQueryVariables(new ArrayList<>());
    createWithEmptyRelationshipCollectionsResult.setRootProcessInstanceId("42");
    createWithEmptyRelationshipCollectionsResult.setScope(true);
    Date startTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    createWithEmptyRelationshipCollectionsResult.setStartTime(startTime);
    createWithEmptyRelationshipCollectionsResult.setStartUserId("42");
    createWithEmptyRelationshipCollectionsResult
        .setSubProcessInstance(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    createWithEmptyRelationshipCollectionsResult.setSuspendedJobCount(3);
    createWithEmptyRelationshipCollectionsResult.setSuspensionState(1);
    createWithEmptyRelationshipCollectionsResult.setTaskCount(3);
    createWithEmptyRelationshipCollectionsResult.setTenantId("42");
    createWithEmptyRelationshipCollectionsResult.setTimerJobCount(3);
    createWithEmptyRelationshipCollectionsResult.setVariableCount(3);
    createWithEmptyRelationshipCollectionsResult.inactivate();
    createWithEmptyRelationshipCollectionsResult.getActivityId();
    createWithEmptyRelationshipCollectionsResult.getActivityName();
    Integer actualAppVersion = createWithEmptyRelationshipCollectionsResult.getAppVersion();
    String actualBusinessKey = createWithEmptyRelationshipCollectionsResult.getBusinessKey();
    ActivitiListener actualCurrentActivitiListener = createWithEmptyRelationshipCollectionsResult
        .getCurrentActivitiListener();
    createWithEmptyRelationshipCollectionsResult.getCurrentActivityId();
    int actualDeadLetterJobCount = createWithEmptyRelationshipCollectionsResult.getDeadLetterJobCount();
    String actualDeleteReason = createWithEmptyRelationshipCollectionsResult.getDeleteReason();
    String actualDeploymentId = createWithEmptyRelationshipCollectionsResult.getDeploymentId();
    String actualEventName = createWithEmptyRelationshipCollectionsResult.getEventName();
    int actualEventSubscriptionCount = createWithEmptyRelationshipCollectionsResult.getEventSubscriptionCount();
    int actualIdentityLinkCount = createWithEmptyRelationshipCollectionsResult.getIdentityLinkCount();
    int actualJobCount = createWithEmptyRelationshipCollectionsResult.getJobCount();
    String actualLocalizedDescription = createWithEmptyRelationshipCollectionsResult.getLocalizedDescription();
    String actualLocalizedName = createWithEmptyRelationshipCollectionsResult.getLocalizedName();
    Date actualLockTime = createWithEmptyRelationshipCollectionsResult.getLockTime();
    String actualParentId = createWithEmptyRelationshipCollectionsResult.getParentId();
    String actualParentProcessInstanceId = createWithEmptyRelationshipCollectionsResult.getParentProcessInstanceId();
    String actualProcessDefinitionId = createWithEmptyRelationshipCollectionsResult.getProcessDefinitionId();
    String actualProcessDefinitionKey = createWithEmptyRelationshipCollectionsResult.getProcessDefinitionKey();
    String actualProcessDefinitionName = createWithEmptyRelationshipCollectionsResult.getProcessDefinitionName();
    Integer actualProcessDefinitionVersion = createWithEmptyRelationshipCollectionsResult.getProcessDefinitionVersion();
    String actualProcessInstanceId = createWithEmptyRelationshipCollectionsResult.getProcessInstanceId();
    String actualRootProcessInstanceId = createWithEmptyRelationshipCollectionsResult.getRootProcessInstanceId();
    Date actualStartTime = createWithEmptyRelationshipCollectionsResult.getStartTime();
    String actualStartUserId = createWithEmptyRelationshipCollectionsResult.getStartUserId();
    createWithEmptyRelationshipCollectionsResult.getSuperExecutionId();
    int actualSuspendedJobCount = createWithEmptyRelationshipCollectionsResult.getSuspendedJobCount();
    int actualSuspensionState = createWithEmptyRelationshipCollectionsResult.getSuspensionState();
    int actualTaskCount = createWithEmptyRelationshipCollectionsResult.getTaskCount();
    String actualTenantId = createWithEmptyRelationshipCollectionsResult.getTenantId();
    int actualTimerJobCount = createWithEmptyRelationshipCollectionsResult.getTimerJobCount();
    int actualVariableCount = createWithEmptyRelationshipCollectionsResult.getVariableCount();
    boolean actualIsActiveResult = createWithEmptyRelationshipCollectionsResult.isActive();
    boolean actualIsConcurrentResult = createWithEmptyRelationshipCollectionsResult.isConcurrent();
    boolean actualIsCountEnabledResult = createWithEmptyRelationshipCollectionsResult.isCountEnabled();
    boolean actualIsDeletedResult = createWithEmptyRelationshipCollectionsResult.isDeleted();
    boolean actualIsEndedResult = createWithEmptyRelationshipCollectionsResult.isEnded();
    boolean actualIsEventScopeResult = createWithEmptyRelationshipCollectionsResult.isEventScope();
    boolean actualIsMultiInstanceRootResult = createWithEmptyRelationshipCollectionsResult.isMultiInstanceRoot();
    boolean actualIsScopeResult = createWithEmptyRelationshipCollectionsResult.isScope();

    // Assert that nothing has changed
    assertEquals("42", actualDeploymentId);
    assertEquals("42", actualParentId);
    assertEquals("42", actualParentProcessInstanceId);
    assertEquals("42", actualProcessDefinitionId);
    assertEquals("42", actualProcessInstanceId);
    assertEquals("42", actualRootProcessInstanceId);
    assertEquals("42", actualStartUserId);
    assertEquals("42", actualTenantId);
    assertEquals("Business Key", actualBusinessKey);
    assertEquals("Event Name", actualEventName);
    assertEquals("Just cause", actualDeleteReason);
    assertEquals("Localized Description", actualLocalizedDescription);
    assertEquals("Localized Name", actualLocalizedName);
    assertEquals("Process Definition Key", actualProcessDefinitionKey);
    assertEquals("Process Definition Name", actualProcessDefinitionName);
    assertEquals(1, actualAppVersion.intValue());
    assertEquals(1, actualProcessDefinitionVersion.intValue());
    assertEquals(1, actualIdentityLinkCount);
    assertEquals(1, actualSuspensionState);
    assertEquals(3, actualDeadLetterJobCount);
    assertEquals(3, actualEventSubscriptionCount);
    assertEquals(3, actualJobCount);
    assertEquals(3, actualSuspendedJobCount);
    assertEquals(3, actualTaskCount);
    assertEquals(3, actualTimerJobCount);
    assertEquals(3, actualVariableCount);
    assertFalse(actualIsActiveResult);
    assertTrue(actualIsConcurrentResult);
    assertTrue(actualIsCountEnabledResult);
    assertTrue(actualIsDeletedResult);
    assertTrue(actualIsEndedResult);
    assertTrue(actualIsEventScopeResult);
    assertTrue(actualIsMultiInstanceRootResult);
    assertTrue(actualIsScopeResult);
    assertSame(currentActivitiListener, actualCurrentActivitiListener);
    assertSame(lockTime, actualLockTime);
    assertSame(startTime, actualStartTime);
  }

  /**
   * Test
   * {@link ExecutionEntityImpl#initializeVariableInstanceBackPointer(VariableInstanceEntity)}.
   * <p>
   * Method under test:
   * {@link ExecutionEntityImpl#initializeVariableInstanceBackPointer(VariableInstanceEntity)}
   */
  @Test
  public void testInitializeVariableInstanceBackPointer() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    VariableInstanceEntityImpl variableInstance = new VariableInstanceEntityImpl();

    // Act
    createWithEmptyRelationshipCollectionsResult.initializeVariableInstanceBackPointer(variableInstance);

    // Assert
    assertNull(variableInstance.getProcessInstanceId());
  }

  /**
   * Test
   * {@link ExecutionEntityImpl#initializeVariableInstanceBackPointer(VariableInstanceEntity)}.
   * <p>
   * Method under test:
   * {@link ExecutionEntityImpl#initializeVariableInstanceBackPointer(VariableInstanceEntity)}
   */
  @Test
  public void testInitializeVariableInstanceBackPointer2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setProcessInstanceId("foo");
    VariableInstanceEntityImpl variableInstance = new VariableInstanceEntityImpl();

    // Act
    createWithEmptyRelationshipCollectionsResult.initializeVariableInstanceBackPointer(variableInstance);

    // Assert
    assertEquals("foo", variableInstance.getProcessInstanceId());
  }

  /**
   * Test {@link ExecutionEntityImpl#getParentVariableScope()}.
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getParentVariableScope()}
   */
  @Test
  public void testGetParentVariableScope() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getParentVariableScope());
  }

  /**
   * Test {@link ExecutionEntityImpl#getParentVariableScope()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getParentVariableScope()}
   */
  @Test
  public void testGetParentVariableScope_givenCreateWithEmptyRelationshipCollections() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getParentVariableScope());
  }

  /**
   * Test {@link ExecutionEntityImpl#getSourceActivityExecution()}.
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getSourceActivityExecution()}
   */
  @Test
  public void testGetSourceActivityExecution() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(java.sql.Date.class));

    // Act
    ExecutionEntityImpl actualSourceActivityExecution = createWithEmptyRelationshipCollectionsResult
        .getSourceActivityExecution();

    // Assert
    assertSame(createWithEmptyRelationshipCollectionsResult, actualSourceActivityExecution);
    List<EventSubscriptionEntity> expectedEventSubscriptions = actualSourceActivityExecution.eventSubscriptions;
    assertSame(expectedEventSubscriptions, createWithEmptyRelationshipCollectionsResult.getEventSubscriptions());
    List<ExecutionEntityImpl> expectedExecutions = actualSourceActivityExecution.executions;
    assertSame(expectedExecutions, createWithEmptyRelationshipCollectionsResult.getExecutions());
    List<IdentityLinkEntity> expectedIdentityLinks = actualSourceActivityExecution.identityLinks;
    assertSame(expectedIdentityLinks, createWithEmptyRelationshipCollectionsResult.getIdentityLinks());
    List<JobEntity> expectedJobs = actualSourceActivityExecution.jobs;
    assertSame(expectedJobs, createWithEmptyRelationshipCollectionsResult.getJobs());
    List<TaskEntity> expectedTasks = actualSourceActivityExecution.tasks;
    assertSame(expectedTasks, createWithEmptyRelationshipCollectionsResult.getTasks());
    List<TimerJobEntity> expectedTimerJobs = actualSourceActivityExecution.timerJobs;
    assertSame(expectedTimerJobs, createWithEmptyRelationshipCollectionsResult.getTimerJobs());
  }

  /**
   * Test {@link ExecutionEntityImpl#getSourceActivityExecution()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getSourceActivityExecution()}
   */
  @Test
  public void testGetSourceActivityExecution_givenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    ExecutionEntityImpl actualSourceActivityExecution = createWithEmptyRelationshipCollectionsResult
        .getSourceActivityExecution();

    // Assert
    assertSame(createWithEmptyRelationshipCollectionsResult, actualSourceActivityExecution);
    List<EventSubscriptionEntity> expectedEventSubscriptions = actualSourceActivityExecution.eventSubscriptions;
    assertSame(expectedEventSubscriptions, createWithEmptyRelationshipCollectionsResult.getEventSubscriptions());
    List<ExecutionEntityImpl> expectedExecutions = actualSourceActivityExecution.executions;
    assertSame(expectedExecutions, createWithEmptyRelationshipCollectionsResult.getExecutions());
    List<IdentityLinkEntity> expectedIdentityLinks = actualSourceActivityExecution.identityLinks;
    assertSame(expectedIdentityLinks, createWithEmptyRelationshipCollectionsResult.getIdentityLinks());
    List<JobEntity> expectedJobs = actualSourceActivityExecution.jobs;
    assertSame(expectedJobs, createWithEmptyRelationshipCollectionsResult.getJobs());
    List<TaskEntity> expectedTasks = actualSourceActivityExecution.tasks;
    assertSame(expectedTasks, createWithEmptyRelationshipCollectionsResult.getTasks());
    List<TimerJobEntity> expectedTimerJobs = actualSourceActivityExecution.timerJobs;
    assertSame(expectedTimerJobs, createWithEmptyRelationshipCollectionsResult.getTimerJobs());
  }

  /**
   * Test {@link ExecutionEntityImpl#getSpecificVariable(String)}.
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getSpecificVariable(String)}
   */
  @Test
  public void testGetSpecificVariable() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> ExecutionEntityImpl.createWithEmptyRelationshipCollections().getSpecificVariable("Variable Name"));
  }

  /**
   * Test {@link ExecutionEntityImpl#getSpecificVariables(Collection)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionEntityImpl#getSpecificVariables(Collection)}
   */
  @Test
  public void testGetSpecificVariables_givenFoo_whenArrayListAddFoo() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");
    variableNames.add("lazy loading outside command context");

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> createWithEmptyRelationshipCollectionsResult.getSpecificVariables(variableNames));
  }

  /**
   * Test {@link ExecutionEntityImpl#getSpecificVariables(Collection)}.
   * <ul>
   *   <li>Given {@code lazy loading outside command context}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionEntityImpl#getSpecificVariables(Collection)}
   */
  @Test
  public void testGetSpecificVariables_givenLazyLoadingOutsideCommandContext() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("lazy loading outside command context");

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> createWithEmptyRelationshipCollectionsResult.getSpecificVariables(variableNames));
  }

  /**
   * Test {@link ExecutionEntityImpl#getSpecificVariables(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionEntityImpl#getSpecificVariables(Collection)}
   */
  @Test
  public void testGetSpecificVariables_whenArrayList() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> createWithEmptyRelationshipCollectionsResult.getSpecificVariables(new ArrayList<>()));
  }

  /**
   * Test {@link ExecutionEntityImpl#getEventSubscriptions()}.
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getEventSubscriptions()}
   */
  @Test
  public void testGetEventSubscriptions() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(java.sql.Date.class));

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getEventSubscriptions().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getEventSubscriptions()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getEventSubscriptions()}
   */
  @Test
  public void testGetEventSubscriptions_givenCreateWithEmptyRelationshipCollections() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getEventSubscriptions().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getJobs()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections LockTime is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getJobs()}
   */
  @Test
  public void testGetJobs_givenCreateWithEmptyRelationshipCollectionsLockTimeIsDate() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(java.sql.Date.class));

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getJobs().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getJobs()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getJobs()}
   */
  @Test
  public void testGetJobs_givenCreateWithEmptyRelationshipCollections_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getJobs().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getTimerJobs()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections LockTime is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getTimerJobs()}
   */
  @Test
  public void testGetTimerJobs_givenCreateWithEmptyRelationshipCollectionsLockTimeIsDate() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(java.sql.Date.class));

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTimerJobs().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getTimerJobs()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getTimerJobs()}
   */
  @Test
  public void testGetTimerJobs_givenCreateWithEmptyRelationshipCollections_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getTimerJobs().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getTasks()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections LockTime is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getTasks()}
   */
  @Test
  public void testGetTasks_givenCreateWithEmptyRelationshipCollectionsLockTimeIsDate() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(java.sql.Date.class));

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTasks().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getTasks()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getTasks()}
   */
  @Test
  public void testGetTasks_givenCreateWithEmptyRelationshipCollections_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getTasks().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getIdentityLinks()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections LockTime is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getIdentityLinks()}
   */
  @Test
  public void testGetIdentityLinks_givenCreateWithEmptyRelationshipCollectionsLockTimeIsDate() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(java.sql.Date.class));

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getIdentityLinks().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getIdentityLinks()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getIdentityLinks()}
   */
  @Test
  public void testGetIdentityLinks_givenCreateWithEmptyRelationshipCollections_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getIdentityLinks().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#isSuspended()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections LockTime is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#isSuspended()}
   */
  @Test
  public void testIsSuspended_givenCreateWithEmptyRelationshipCollectionsLockTimeIsDate() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(java.sql.Date.class));

    // Act and Assert
    assertFalse(createWithEmptyRelationshipCollectionsResult.isSuspended());
  }

  /**
   * Test {@link ExecutionEntityImpl#isSuspended()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#isSuspended()}
   */
  @Test
  public void testIsSuspended_givenCreateWithEmptyRelationshipCollections_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(ExecutionEntityImpl.createWithEmptyRelationshipCollections().isSuspended());
  }

  /**
   * Test {@link ExecutionEntityImpl#isSuspended()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#isSuspended()}
   */
  @Test
  public void testIsSuspended_thenReturnTrue() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setSuspensionState(2);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.isSuspended());
  }

  /**
   * Test {@link ExecutionEntityImpl#getName()}.
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getName()}
   */
  @Test
  public void testGetName() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLocalizedName("");

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getName());
  }

  /**
   * Test {@link ExecutionEntityImpl#getName()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getName()}
   */
  @Test
  public void testGetName_givenCreateWithEmptyRelationshipCollections_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getName());
  }

  /**
   * Test {@link ExecutionEntityImpl#getName()}.
   * <ul>
   *   <li>Then return {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getName()}
   */
  @Test
  public void testGetName_thenReturnFoo() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLocalizedName("foo");

    // Act and Assert
    assertEquals("foo", createWithEmptyRelationshipCollectionsResult.getName());
  }

  /**
   * Test {@link ExecutionEntityImpl#getDescription()}.
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getDescription()}
   */
  @Test
  public void testGetDescription() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLocalizedDescription("");

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getDescription());
  }

  /**
   * Test {@link ExecutionEntityImpl#getDescription()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getDescription()}
   */
  @Test
  public void testGetDescription_givenCreateWithEmptyRelationshipCollections_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getDescription());
  }

  /**
   * Test {@link ExecutionEntityImpl#getDescription()}.
   * <ul>
   *   <li>Then return {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getDescription()}
   */
  @Test
  public void testGetDescription_thenReturnFoo() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLocalizedDescription("foo");

    // Act and Assert
    assertEquals("foo", createWithEmptyRelationshipCollectionsResult.getDescription());
  }

  /**
   * Test {@link ExecutionEntityImpl#getProcessVariables()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getProcessVariables()}
   */
  @Test
  public void testGetProcessVariables_givenCreateWithEmptyRelationshipCollections() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getProcessVariables().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getProcessVariables()}.
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor) Id is
   * {@code null}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getProcessVariables()}
   */
  @Test
  public void testGetProcessVariables_givenVariableInstanceEntityImplIdIsNull_thenReturnEmpty() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setId(null);
    variableInstanceEntityImpl.setTaskId(null);

    ArrayList<VariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(variableInstanceEntityImpl);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setQueryVariables(queryVariables);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getProcessVariables().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getProcessVariables()}.
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor) TaskId is
   * {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getProcessVariables()}
   */
  @Test
  public void testGetProcessVariables_givenVariableInstanceEntityImplTaskIdIsFoo() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setId("foo");
    variableInstanceEntityImpl.setTaskId("foo");

    ArrayList<VariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(variableInstanceEntityImpl);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setQueryVariables(queryVariables);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getProcessVariables().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getProcessVariables()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getProcessVariables()}
   */
  @Test
  public void testGetProcessVariables_thenReturnEmpty() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setQueryVariables(new ArrayList<>());

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getProcessVariables().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getProcessVariables()}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getProcessVariables()}
   */
  @Test
  public void testGetProcessVariables_thenReturnSizeIsOne() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(new BigDecimalType());
    variableInstanceEntityImpl.setId("foo");
    variableInstanceEntityImpl.setTaskId(null);

    ArrayList<VariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(variableInstanceEntityImpl);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setQueryVariables(queryVariables);

    // Act
    Map<String, Object> actualProcessVariables = createWithEmptyRelationshipCollectionsResult.getProcessVariables();

    // Assert
    assertEquals(1, actualProcessVariables.size());
    assertNull(actualProcessVariables.get(null));
  }

  /**
   * Test {@link ExecutionEntityImpl#getQueryVariables()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections LockTime is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getQueryVariables()}
   */
  @Test
  public void testGetQueryVariables_givenCreateWithEmptyRelationshipCollectionsLockTimeIsDate() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getQueryVariables());
  }

  /**
   * Test {@link ExecutionEntityImpl#getQueryVariables()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getQueryVariables()}
   */
  @Test
  public void testGetQueryVariables_givenCreateWithEmptyRelationshipCollections_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getQueryVariables());
  }

  /**
   * Test {@link ExecutionEntityImpl#getQueryVariables()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#getQueryVariables()}
   */
  @Test
  public void testGetQueryVariables_thenReturnEmpty() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setQueryVariables(new ArrayList<>());

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getQueryVariables().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#toString()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#toString()}
   */
  @Test
  public void testToString_givenCreateWithEmptyRelationshipCollections() {
    // Arrange, Act and Assert
    assertEquals("ProcessInstance[null]", ExecutionEntityImpl.createWithEmptyRelationshipCollections().toString());
  }

  /**
   * Test {@link ExecutionEntityImpl#toString()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections LockTime is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#toString()}
   */
  @Test
  public void testToString_givenCreateWithEmptyRelationshipCollectionsLockTimeIsDate() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(java.sql.Date.class));

    // Act and Assert
    assertEquals("ProcessInstance[null]", createWithEmptyRelationshipCollectionsResult.toString());
  }

  /**
   * Test {@link ExecutionEntityImpl#toString()}.
   * <ul>
   *   <li>Then return {@code Scoped execution[ id 'null' ] - parent '42'}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionEntityImpl#toString()}
   */
  @Test
  public void testToString_thenReturnScopedExecutionIdNullParent42() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setParentId("42");

    // Act and Assert
    assertEquals("Scoped execution[ id 'null' ] - parent '42'",
        createWithEmptyRelationshipCollectionsResult.toString());
  }
}
