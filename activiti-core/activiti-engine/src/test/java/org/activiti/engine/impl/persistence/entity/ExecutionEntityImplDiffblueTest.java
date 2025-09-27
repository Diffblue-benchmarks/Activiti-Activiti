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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.junit.experimental.categories.Category;

public class ExecutionEntityImplDiffblueTest {
  /**
   * Test {@link ExecutionEntityImpl#getPersistentState()}.
   *
   * <ul>
   *   <li>Then return size is {@link Float#PRECISION}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getPersistentState()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ExecutionEntityImpl.getPersistentState()"})
  public void testGetPersistentState_thenReturnSizeIsPrecision() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.forceUpdate();

    // Act
    Object actualPersistentState =
        createWithEmptyRelationshipCollectionsResult.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(Float.PRECISION, ((Map<Object, Object>) actualPersistentState).size());
    assertNull(((Map<Object, Object>) actualPersistentState).get("parentId"));
    assertNull(((Map<Object, Object>) actualPersistentState).get("processDefinitionId"));
    assertNull(((Map<Object, Object>) actualPersistentState).get("superExecution"));
    assertEquals(
        0,
        ((Integer) ((Map<Object, Object>) actualPersistentState).get("suspendedJobCount"))
            .intValue());
    assertEquals(
        0,
        ((Integer) ((Map<Object, Object>) actualPersistentState).get("timerJobCount")).intValue());
    assertEquals(
        1,
        ((Integer) ((Map<Object, Object>) actualPersistentState).get("suspensionState"))
            .intValue());
    assertFalse((Boolean) ((Map<Object, Object>) actualPersistentState).get("isConcurrent"));
    assertFalse((Boolean) ((Map<Object, Object>) actualPersistentState).get("isEventScope"));
    assertTrue((Boolean) ((Map<Object, Object>) actualPersistentState).get("forcedUpdate"));
    assertTrue((Boolean) ((Map<Object, Object>) actualPersistentState).get("isActive"));
    assertTrue((Boolean) ((Map<Object, Object>) actualPersistentState).get("isScope"));
  }

  /**
   * Test {@link ExecutionEntityImpl#getPersistentState()}.
   *
   * <ul>
   *   <li>Then return size is twenty-three.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getPersistentState()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ExecutionEntityImpl.getPersistentState()"})
  public void testGetPersistentState_thenReturnSizeIsTwentyThree() {
    // Arrange and Act
    Object actualPersistentState =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections().getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(23, ((Map<Object, Object>) actualPersistentState).size());
    assertNull(((Map<Object, Object>) actualPersistentState).get("activityId"));
    assertNull(((Map<Object, Object>) actualPersistentState).get("parentId"));
    assertNull(((Map<Object, Object>) actualPersistentState).get("processDefinitionId"));
    assertNull(((Map<Object, Object>) actualPersistentState).get("superExecution"));
    assertEquals(
        0,
        ((Integer) ((Map<Object, Object>) actualPersistentState).get("suspendedJobCount"))
            .intValue());
    assertEquals(
        0,
        ((Integer) ((Map<Object, Object>) actualPersistentState).get("timerJobCount")).intValue());
    assertEquals(
        1,
        ((Integer) ((Map<Object, Object>) actualPersistentState).get("suspensionState"))
            .intValue());
    assertFalse((Boolean) ((Map<Object, Object>) actualPersistentState).get("isConcurrent"));
    assertFalse((Boolean) ((Map<Object, Object>) actualPersistentState).get("isEventScope"));
    assertTrue((Boolean) ((Map<Object, Object>) actualPersistentState).get("isActive"));
    assertTrue((Boolean) ((Map<Object, Object>) actualPersistentState).get("isScope"));
  }

  /**
   * Test {@link ExecutionEntityImpl#getCurrentFlowElement()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getCurrentFlowElement()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement ExecutionEntityImpl.getCurrentFlowElement()"})
  public void testGetCurrentFlowElement_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections().getCurrentFlowElement());
  }

  /**
   * Test {@link ExecutionEntityImpl#setCurrentFlowElement(FlowElement)}.
   *
   * <p>Method under test: {@link ExecutionEntityImpl#setCurrentFlowElement(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityImpl.setCurrentFlowElement(FlowElement)"})
  public void testSetCurrentFlowElement() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    AdhocSubProcess currentFlowElement = new AdhocSubProcess();

    // Act
    createWithEmptyRelationshipCollectionsResult.setCurrentFlowElement(currentFlowElement);

    // Assert
    assertTrue(
        createWithEmptyRelationshipCollectionsResult.currentFlowElement instanceof AdhocSubProcess);
    assertSame(
        currentFlowElement, createWithEmptyRelationshipCollectionsResult.getCurrentFlowElement());
  }

  /**
   * Test {@link ExecutionEntityImpl#setCurrentFlowElement(FlowElement)}.
   *
   * <p>Method under test: {@link ExecutionEntityImpl#setCurrentFlowElement(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityImpl.setCurrentFlowElement(FlowElement)"})
  public void testSetCurrentFlowElement2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setCurrentFlowElement(null);

    // Assert that nothing has changed
    assertNull(createWithEmptyRelationshipCollectionsResult.getCurrentFlowElement());
    assertNull(createWithEmptyRelationshipCollectionsResult.currentFlowElement);
  }

  /**
   * Test {@link ExecutionEntityImpl#getExecutions()}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getExecutions()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ExecutionEntityImpl.getExecutions()"})
  public void testGetExecutions_givenCreateWithEmptyRelationshipCollections_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections().getExecutions().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getProcessInstance()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getProcessInstance()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionEntityImpl ExecutionEntityImpl.getProcessInstance()"})
  public void testGetProcessInstance_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getProcessInstance());
  }

  /**
   * Test {@link ExecutionEntityImpl#setProcessInstance(ExecutionEntity)}.
   *
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#setProcessInstance(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityImpl.setProcessInstance(ExecutionEntity)"})
  public void testSetProcessInstance_whenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ExecutionEntityImpl processInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setProcessInstance(processInstance);

    // Assert
    ExecutionEntityImpl actualSourceActivityExecution =
        processInstance.getSourceActivityExecution();
    assertSame(processInstance, actualSourceActivityExecution);
  }

  /**
   * Test {@link ExecutionEntityImpl#setProcessInstance(ExecutionEntity)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#setProcessInstance(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityImpl.setProcessInstance(ExecutionEntity)"})
  public void testSetProcessInstance_whenNull() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setProcessInstance(null);

    // Assert that nothing has changed
    ExecutionEntityImpl actualSourceActivityExecution =
        createWithEmptyRelationshipCollectionsResult.getSourceActivityExecution();
    assertSame(createWithEmptyRelationshipCollectionsResult, actualSourceActivityExecution);
  }

  /**
   * Test {@link ExecutionEntityImpl#isProcessInstanceType()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#isProcessInstanceType()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExecutionEntityImpl.isProcessInstanceType()"})
  public void testIsProcessInstanceType_thenReturnFalse() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setParentId("foo");

    // Act and Assert
    assertFalse(createWithEmptyRelationshipCollectionsResult.isProcessInstanceType());
  }

  /**
   * Test {@link ExecutionEntityImpl#isProcessInstanceType()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#isProcessInstanceType()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExecutionEntityImpl.isProcessInstanceType()"})
  public void testIsProcessInstanceType_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections().isProcessInstanceType());
  }

  /**
   * Test {@link ExecutionEntityImpl#getParent()}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getParent()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionEntityImpl ExecutionEntityImpl.getParent()"})
  public void testGetParent_givenCreateWithEmptyRelationshipCollections_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getParent());
  }

  /**
   * Test {@link ExecutionEntityImpl#setParent(ExecutionEntity)}.
   *
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#setParent(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityImpl.setParent(ExecutionEntity)"})
  public void testSetParent_whenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ExecutionEntityImpl parent = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setParent(parent);

    // Assert
    ExecutionEntityImpl actualSourceActivityExecution = parent.getSourceActivityExecution();
    assertSame(parent, actualSourceActivityExecution);
  }

  /**
   * Test {@link ExecutionEntityImpl#setParent(ExecutionEntity)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#setParent(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityImpl.setParent(ExecutionEntity)"})
  public void testSetParent_whenNull() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setParent(null);

    // Assert that nothing has changed
    ExecutionEntityImpl actualSourceActivityExecution =
        createWithEmptyRelationshipCollectionsResult.getSourceActivityExecution();
    assertSame(createWithEmptyRelationshipCollectionsResult, actualSourceActivityExecution);
  }

  /**
   * Test {@link ExecutionEntityImpl#getSuperExecution()}.
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getSuperExecution()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionEntityImpl ExecutionEntityImpl.getSuperExecution()"})
  public void testGetSuperExecution() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getSuperExecution());
  }

  /**
   * Test {@link ExecutionEntityImpl#setSuperExecution(ExecutionEntity)}.
   *
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#setSuperExecution(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityImpl.setSuperExecution(ExecutionEntity)"})
  public void testSetSuperExecution_whenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ExecutionEntityImpl superExecution =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setSuperExecution(superExecution);

    // Assert
    ExecutionEntityImpl actualSourceActivityExecution = superExecution.getSourceActivityExecution();
    assertSame(superExecution, actualSourceActivityExecution);
  }

  /**
   * Test {@link ExecutionEntityImpl#setSuperExecution(ExecutionEntity)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#setSuperExecution(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityImpl.setSuperExecution(ExecutionEntity)"})
  public void testSetSuperExecution_whenNull() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setSuperExecution(null);

    // Assert that nothing has changed
    ExecutionEntityImpl actualSourceActivityExecution =
        createWithEmptyRelationshipCollectionsResult.getSourceActivityExecution();
    assertSame(createWithEmptyRelationshipCollectionsResult, actualSourceActivityExecution);
  }

  /**
   * Test {@link ExecutionEntityImpl#getSubProcessInstance()}.
   *
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getSubProcessInstance()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionEntityImpl ExecutionEntityImpl.getSubProcessInstance()"})
  public void testGetSubProcessInstance_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ExecutionEntityImpl subProcessInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setSubProcessInstance(subProcessInstance);

    // Act and Assert
    assertSame(
        subProcessInstance, createWithEmptyRelationshipCollectionsResult.getSubProcessInstance());
  }

  /**
   * Test {@link ExecutionEntityImpl#setRootProcessInstance(ExecutionEntity)}.
   *
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#setRootProcessInstance(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityImpl.setRootProcessInstance(ExecutionEntity)"})
  public void testSetRootProcessInstance_whenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ExecutionEntityImpl rootProcessInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setRootProcessInstance(rootProcessInstance);

    // Assert
    ExecutionEntityImpl actualSourceActivityExecution =
        rootProcessInstance.getSourceActivityExecution();
    assertSame(rootProcessInstance, actualSourceActivityExecution);
  }

  /**
   * Test {@link ExecutionEntityImpl#setRootProcessInstance(ExecutionEntity)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#setRootProcessInstance(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionEntityImpl.setRootProcessInstance(ExecutionEntity)"})
  public void testSetRootProcessInstance_whenNull() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setRootProcessInstance(null);

    // Assert that nothing has changed
    ExecutionEntityImpl actualSourceActivityExecution =
        createWithEmptyRelationshipCollectionsResult.getSourceActivityExecution();
    assertSame(createWithEmptyRelationshipCollectionsResult, actualSourceActivityExecution);
  }

  /**
   * Test {@link ExecutionEntityImpl#isRootExecution()}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#isRootExecution()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExecutionEntityImpl.isRootExecution()"})
  public void testIsRootExecution_givenCreateWithEmptyRelationshipCollections() {
    // Arrange, Act and Assert
    assertFalse(ExecutionEntityImpl.createWithEmptyRelationshipCollections().isRootExecution());
  }

  /**
   * Test {@link ExecutionEntityImpl#isRootExecution()}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections Id is {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#isRootExecution()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExecutionEntityImpl.isRootExecution()"})
  public void testIsRootExecution_givenCreateWithEmptyRelationshipCollectionsIdIsFoo() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setId("foo");

    // Act and Assert
    assertFalse(createWithEmptyRelationshipCollectionsResult.isRootExecution());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityImpl.forceUpdate()",
    "String ExecutionEntityImpl.getActivityId()",
    "String ExecutionEntityImpl.getActivityName()",
    "Integer ExecutionEntityImpl.getAppVersion()",
    "String ExecutionEntityImpl.getBusinessKey()",
    "ActivitiListener ExecutionEntityImpl.getCurrentActivitiListener()",
    "String ExecutionEntityImpl.getCurrentActivityId()",
    "int ExecutionEntityImpl.getDeadLetterJobCount()",
    "String ExecutionEntityImpl.getDeleteReason()",
    "String ExecutionEntityImpl.getDeploymentId()",
    "String ExecutionEntityImpl.getEventName()",
    "int ExecutionEntityImpl.getEventSubscriptionCount()",
    "int ExecutionEntityImpl.getIdentityLinkCount()",
    "int ExecutionEntityImpl.getJobCount()",
    "String ExecutionEntityImpl.getLocalizedDescription()",
    "String ExecutionEntityImpl.getLocalizedName()",
    "Date ExecutionEntityImpl.getLockTime()",
    "String ExecutionEntityImpl.getParentId()",
    "String ExecutionEntityImpl.getParentProcessInstanceId()",
    "String ExecutionEntityImpl.getProcessDefinitionId()",
    "String ExecutionEntityImpl.getProcessDefinitionKey()",
    "String ExecutionEntityImpl.getProcessDefinitionName()",
    "Integer ExecutionEntityImpl.getProcessDefinitionVersion()",
    "String ExecutionEntityImpl.getProcessInstanceId()",
    "String ExecutionEntityImpl.getRootProcessInstanceId()",
    "Date ExecutionEntityImpl.getStartTime()",
    "String ExecutionEntityImpl.getStartUserId()",
    "String ExecutionEntityImpl.getSuperExecutionId()",
    "int ExecutionEntityImpl.getSuspendedJobCount()",
    "int ExecutionEntityImpl.getSuspensionState()",
    "int ExecutionEntityImpl.getTaskCount()",
    "String ExecutionEntityImpl.getTenantId()",
    "int ExecutionEntityImpl.getTimerJobCount()",
    "int ExecutionEntityImpl.getVariableCount()",
    "void ExecutionEntityImpl.inactivate()",
    "boolean ExecutionEntityImpl.isActive()",
    "boolean ExecutionEntityImpl.isConcurrent()",
    "boolean ExecutionEntityImpl.isCountEnabled()",
    "boolean ExecutionEntityImpl.isDeleted()",
    "boolean ExecutionEntityImpl.isEnded()",
    "boolean ExecutionEntityImpl.isEventScope()",
    "boolean ExecutionEntityImpl.isMultiInstanceRoot()",
    "boolean ExecutionEntityImpl.isScope()",
    "void ExecutionEntityImpl.setActive(boolean)",
    "void ExecutionEntityImpl.setAppVersion(Integer)",
    "void ExecutionEntityImpl.setBusinessKey(String)",
    "void ExecutionEntityImpl.setConcurrent(boolean)",
    "void ExecutionEntityImpl.setCountEnabled(boolean)",
    "void ExecutionEntityImpl.setCurrentActivitiListener(ActivitiListener)",
    "void ExecutionEntityImpl.setDeadLetterJobCount(int)",
    "void ExecutionEntityImpl.setDeleteReason(String)",
    "void ExecutionEntityImpl.setDeleted(boolean)",
    "void ExecutionEntityImpl.setDeploymentId(String)",
    "void ExecutionEntityImpl.setDescription(String)",
    "void ExecutionEntityImpl.setEnded(boolean)",
    "void ExecutionEntityImpl.setEventName(String)",
    "void ExecutionEntityImpl.setEventScope(boolean)",
    "void ExecutionEntityImpl.setEventSubscriptionCount(int)",
    "void ExecutionEntityImpl.setIdentityLinkCount(int)",
    "void ExecutionEntityImpl.setJobCount(int)",
    "void ExecutionEntityImpl.setLocalizedDescription(String)",
    "void ExecutionEntityImpl.setLocalizedName(String)",
    "void ExecutionEntityImpl.setLockTime(Date)",
    "void ExecutionEntityImpl.setMultiInstanceRoot(boolean)",
    "void ExecutionEntityImpl.setName(String)",
    "void ExecutionEntityImpl.setParentId(String)",
    "void ExecutionEntityImpl.setParentProcessInstanceId(String)",
    "void ExecutionEntityImpl.setProcessDefinitionId(String)",
    "void ExecutionEntityImpl.setProcessDefinitionKey(String)",
    "void ExecutionEntityImpl.setProcessDefinitionName(String)",
    "void ExecutionEntityImpl.setProcessDefinitionVersion(Integer)",
    "void ExecutionEntityImpl.setProcessInstanceId(String)",
    "void ExecutionEntityImpl.setQueryVariables(List)",
    "void ExecutionEntityImpl.setRootProcessInstanceId(String)",
    "void ExecutionEntityImpl.setScope(boolean)",
    "void ExecutionEntityImpl.setStartTime(Date)",
    "void ExecutionEntityImpl.setStartUserId(String)",
    "void ExecutionEntityImpl.setSubProcessInstance(ExecutionEntity)",
    "void ExecutionEntityImpl.setSuspendedJobCount(int)",
    "void ExecutionEntityImpl.setSuspensionState(int)",
    "void ExecutionEntityImpl.setTaskCount(int)",
    "void ExecutionEntityImpl.setTenantId(String)",
    "void ExecutionEntityImpl.setTimerJobCount(int)",
    "void ExecutionEntityImpl.setVariableCount(int)"
  })
  public void testGettersAndSetters() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.forceUpdate();
    createWithEmptyRelationshipCollectionsResult.setActive(true);
    createWithEmptyRelationshipCollectionsResult.setAppVersion(1);
    createWithEmptyRelationshipCollectionsResult.setBusinessKey("Business Key");
    createWithEmptyRelationshipCollectionsResult.setConcurrent(true);
    createWithEmptyRelationshipCollectionsResult.setCountEnabled(true);
    ActivitiListener currentActivitiListener = new ActivitiListener();
    createWithEmptyRelationshipCollectionsResult.setCurrentActivitiListener(
        currentActivitiListener);
    createWithEmptyRelationshipCollectionsResult.setDeadLetterJobCount(3);
    createWithEmptyRelationshipCollectionsResult.setDeleteReason("Just cause");
    createWithEmptyRelationshipCollectionsResult.setDeleted(true);
    createWithEmptyRelationshipCollectionsResult.setDeploymentId("42");
    createWithEmptyRelationshipCollectionsResult.setDescription(
        "The characteristics of someone or something");
    createWithEmptyRelationshipCollectionsResult.setEnded(true);
    createWithEmptyRelationshipCollectionsResult.setEventName("Event Name");
    createWithEmptyRelationshipCollectionsResult.setEventScope(true);
    createWithEmptyRelationshipCollectionsResult.setEventSubscriptionCount(3);
    createWithEmptyRelationshipCollectionsResult.setIdentityLinkCount(1);
    createWithEmptyRelationshipCollectionsResult.setJobCount(3);
    createWithEmptyRelationshipCollectionsResult.setLocalizedDescription("Localized Description");
    createWithEmptyRelationshipCollectionsResult.setLocalizedName("Localized Name");
    Date lockTime =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    createWithEmptyRelationshipCollectionsResult.setLockTime(lockTime);
    createWithEmptyRelationshipCollectionsResult.setMultiInstanceRoot(true);
    createWithEmptyRelationshipCollectionsResult.setName("Name");
    createWithEmptyRelationshipCollectionsResult.setParentId("42");
    createWithEmptyRelationshipCollectionsResult.setParentProcessInstanceId("42");
    createWithEmptyRelationshipCollectionsResult.setProcessDefinitionId("42");
    createWithEmptyRelationshipCollectionsResult.setProcessDefinitionKey("Process Definition Key");
    createWithEmptyRelationshipCollectionsResult.setProcessDefinitionName(
        "Process Definition Name");
    createWithEmptyRelationshipCollectionsResult.setProcessDefinitionVersion(1);
    createWithEmptyRelationshipCollectionsResult.setProcessInstanceId("42");
    createWithEmptyRelationshipCollectionsResult.setQueryVariables(new ArrayList<>());
    createWithEmptyRelationshipCollectionsResult.setRootProcessInstanceId("42");
    createWithEmptyRelationshipCollectionsResult.setScope(true);
    Date startTime =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    createWithEmptyRelationshipCollectionsResult.setStartTime(startTime);
    createWithEmptyRelationshipCollectionsResult.setStartUserId("42");
    createWithEmptyRelationshipCollectionsResult.setSubProcessInstance(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    createWithEmptyRelationshipCollectionsResult.setSuspendedJobCount(3);
    createWithEmptyRelationshipCollectionsResult.setSuspensionState(1);
    createWithEmptyRelationshipCollectionsResult.setTaskCount(3);
    createWithEmptyRelationshipCollectionsResult.setTenantId("42");
    createWithEmptyRelationshipCollectionsResult.setTimerJobCount(3);
    createWithEmptyRelationshipCollectionsResult.setVariableCount(3);
    createWithEmptyRelationshipCollectionsResult.inactivate();
    String actualActivityId = createWithEmptyRelationshipCollectionsResult.getActivityId();
    String actualActivityName = createWithEmptyRelationshipCollectionsResult.getActivityName();
    Integer actualAppVersion = createWithEmptyRelationshipCollectionsResult.getAppVersion();
    String actualBusinessKey = createWithEmptyRelationshipCollectionsResult.getBusinessKey();
    ActivitiListener actualCurrentActivitiListener =
        createWithEmptyRelationshipCollectionsResult.getCurrentActivitiListener();
    String actualCurrentActivityId =
        createWithEmptyRelationshipCollectionsResult.getCurrentActivityId();
    int actualDeadLetterJobCount =
        createWithEmptyRelationshipCollectionsResult.getDeadLetterJobCount();
    String actualDeleteReason = createWithEmptyRelationshipCollectionsResult.getDeleteReason();
    String actualDeploymentId = createWithEmptyRelationshipCollectionsResult.getDeploymentId();
    String actualEventName = createWithEmptyRelationshipCollectionsResult.getEventName();
    int actualEventSubscriptionCount =
        createWithEmptyRelationshipCollectionsResult.getEventSubscriptionCount();
    int actualIdentityLinkCount =
        createWithEmptyRelationshipCollectionsResult.getIdentityLinkCount();
    int actualJobCount = createWithEmptyRelationshipCollectionsResult.getJobCount();
    String actualLocalizedDescription =
        createWithEmptyRelationshipCollectionsResult.getLocalizedDescription();
    String actualLocalizedName = createWithEmptyRelationshipCollectionsResult.getLocalizedName();
    Date actualLockTime = createWithEmptyRelationshipCollectionsResult.getLockTime();
    String actualParentId = createWithEmptyRelationshipCollectionsResult.getParentId();
    String actualParentProcessInstanceId =
        createWithEmptyRelationshipCollectionsResult.getParentProcessInstanceId();
    String actualProcessDefinitionId =
        createWithEmptyRelationshipCollectionsResult.getProcessDefinitionId();
    String actualProcessDefinitionKey =
        createWithEmptyRelationshipCollectionsResult.getProcessDefinitionKey();
    String actualProcessDefinitionName =
        createWithEmptyRelationshipCollectionsResult.getProcessDefinitionName();
    Integer actualProcessDefinitionVersion =
        createWithEmptyRelationshipCollectionsResult.getProcessDefinitionVersion();
    String actualProcessInstanceId =
        createWithEmptyRelationshipCollectionsResult.getProcessInstanceId();
    String actualRootProcessInstanceId =
        createWithEmptyRelationshipCollectionsResult.getRootProcessInstanceId();
    Date actualStartTime = createWithEmptyRelationshipCollectionsResult.getStartTime();
    String actualStartUserId = createWithEmptyRelationshipCollectionsResult.getStartUserId();
    String actualSuperExecutionId =
        createWithEmptyRelationshipCollectionsResult.getSuperExecutionId();
    int actualSuspendedJobCount =
        createWithEmptyRelationshipCollectionsResult.getSuspendedJobCount();
    int actualSuspensionState = createWithEmptyRelationshipCollectionsResult.getSuspensionState();
    int actualTaskCount = createWithEmptyRelationshipCollectionsResult.getTaskCount();
    String actualTenantId = createWithEmptyRelationshipCollectionsResult.getTenantId();
    int actualTimerJobCount = createWithEmptyRelationshipCollectionsResult.getTimerJobCount();
    int actualVariableCount = createWithEmptyRelationshipCollectionsResult.getVariableCount();
    boolean actualIsActiveResult = createWithEmptyRelationshipCollectionsResult.isActive();
    boolean actualIsConcurrentResult = createWithEmptyRelationshipCollectionsResult.isConcurrent();
    boolean actualIsCountEnabledResult =
        createWithEmptyRelationshipCollectionsResult.isCountEnabled();
    boolean actualIsDeletedResult = createWithEmptyRelationshipCollectionsResult.isDeleted();
    boolean actualIsEndedResult = createWithEmptyRelationshipCollectionsResult.isEnded();
    boolean actualIsEventScopeResult = createWithEmptyRelationshipCollectionsResult.isEventScope();
    boolean actualIsMultiInstanceRootResult =
        createWithEmptyRelationshipCollectionsResult.isMultiInstanceRoot();
    boolean actualIsScopeResult = createWithEmptyRelationshipCollectionsResult.isScope();

    // Assert
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
    assertNull(actualActivityId);
    assertNull(actualActivityName);
    assertNull(actualCurrentActivityId);
    assertNull(actualSuperExecutionId);
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
   * Test {@link ExecutionEntityImpl#initializeVariableInstanceBackPointer(VariableInstanceEntity)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityImpl#initializeVariableInstanceBackPointer(VariableInstanceEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityImpl.initializeVariableInstanceBackPointer(VariableInstanceEntity)"
  })
  public void testInitializeVariableInstanceBackPointer() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    VariableInstanceEntityImpl variableInstance = new VariableInstanceEntityImpl();

    // Act
    createWithEmptyRelationshipCollectionsResult.initializeVariableInstanceBackPointer(
        variableInstance);

    // Assert that nothing has changed
    assertNull(variableInstance.getProcessInstanceId());
  }

  /**
   * Test {@link ExecutionEntityImpl#initializeVariableInstanceBackPointer(VariableInstanceEntity)}.
   *
   * <p>Method under test: {@link
   * ExecutionEntityImpl#initializeVariableInstanceBackPointer(VariableInstanceEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionEntityImpl.initializeVariableInstanceBackPointer(VariableInstanceEntity)"
  })
  public void testInitializeVariableInstanceBackPointer2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setProcessInstanceId("foo");
    VariableInstanceEntityImpl variableInstance = new VariableInstanceEntityImpl();

    // Act
    createWithEmptyRelationshipCollectionsResult.initializeVariableInstanceBackPointer(
        variableInstance);

    // Assert
    assertEquals("foo", variableInstance.getProcessInstanceId());
  }

  /**
   * Test {@link ExecutionEntityImpl#getParentVariableScope()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getParentVariableScope()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.VariableScopeImpl ExecutionEntityImpl.getParentVariableScope()"
  })
  public void testGetParentVariableScope_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections().getParentVariableScope());
  }

  /**
   * Test {@link ExecutionEntityImpl#getSourceActivityExecution()}.
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getSourceActivityExecution()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionEntityImpl ExecutionEntityImpl.getSourceActivityExecution()"})
  public void testGetSourceActivityExecution() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    ExecutionEntityImpl actualSourceActivityExecution =
        createWithEmptyRelationshipCollectionsResult.getSourceActivityExecution();

    // Assert
    assertSame(createWithEmptyRelationshipCollectionsResult, actualSourceActivityExecution);
    assertSame(
        actualSourceActivityExecution.eventSubscriptions,
        createWithEmptyRelationshipCollectionsResult.getEventSubscriptions());
    assertSame(
        actualSourceActivityExecution.executions,
        createWithEmptyRelationshipCollectionsResult.getExecutions());
    assertSame(
        actualSourceActivityExecution.identityLinks,
        createWithEmptyRelationshipCollectionsResult.getIdentityLinks());
    assertSame(
        actualSourceActivityExecution.jobs, createWithEmptyRelationshipCollectionsResult.getJobs());
    assertSame(
        actualSourceActivityExecution.tasks,
        createWithEmptyRelationshipCollectionsResult.getTasks());
    assertSame(
        actualSourceActivityExecution.timerJobs,
        createWithEmptyRelationshipCollectionsResult.getTimerJobs());
  }

  /**
   * Test {@link ExecutionEntityImpl#getSpecificVariable(String)}.
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getSpecificVariable(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"VariableInstanceEntity ExecutionEntityImpl.getSpecificVariable(String)"})
  public void testGetSpecificVariable() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            ExecutionEntityImpl.createWithEmptyRelationshipCollections()
                .getSpecificVariable("Variable Name"));
  }

  /**
   * Test {@link ExecutionEntityImpl#getSpecificVariables(Collection)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getSpecificVariables(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ExecutionEntityImpl.getSpecificVariables(Collection)"})
  public void testGetSpecificVariables_givenFoo_whenArrayListAddFoo() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");
    variableNames.add("lazy loading outside command context");

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> createWithEmptyRelationshipCollectionsResult.getSpecificVariables(variableNames));
  }

  /**
   * Test {@link ExecutionEntityImpl#getSpecificVariables(Collection)}.
   *
   * <ul>
   *   <li>Given {@code lazy loading outside command context}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getSpecificVariables(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ExecutionEntityImpl.getSpecificVariables(Collection)"})
  public void testGetSpecificVariables_givenLazyLoadingOutsideCommandContext() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("lazy loading outside command context");

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> createWithEmptyRelationshipCollectionsResult.getSpecificVariables(variableNames));
  }

  /**
   * Test {@link ExecutionEntityImpl#getSpecificVariables(Collection)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getSpecificVariables(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ExecutionEntityImpl.getSpecificVariables(Collection)"})
  public void testGetSpecificVariables_whenArrayList() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> createWithEmptyRelationshipCollectionsResult.getSpecificVariables(new ArrayList<>()));
  }

  /**
   * Test {@link ExecutionEntityImpl#getEventSubscriptions()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getEventSubscriptions()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ExecutionEntityImpl.getEventSubscriptions()"})
  public void testGetEventSubscriptions_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()
            .getEventSubscriptions()
            .isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getJobs()}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getJobs()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ExecutionEntityImpl.getJobs()"})
  public void testGetJobs_givenCreateWithEmptyRelationshipCollections_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getJobs().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getTimerJobs()}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getTimerJobs()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ExecutionEntityImpl.getTimerJobs()"})
  public void testGetTimerJobs_givenCreateWithEmptyRelationshipCollections_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections().getTimerJobs().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getTasks()}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getTasks()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ExecutionEntityImpl.getTasks()"})
  public void testGetTasks_givenCreateWithEmptyRelationshipCollections_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getTasks().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getIdentityLinks()}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getIdentityLinks()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ExecutionEntityImpl.getIdentityLinks()"})
  public void testGetIdentityLinks_givenCreateWithEmptyRelationshipCollections_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections().getIdentityLinks().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#isSuspended()}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#isSuspended()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExecutionEntityImpl.isSuspended()"})
  public void testIsSuspended_givenCreateWithEmptyRelationshipCollections_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(ExecutionEntityImpl.createWithEmptyRelationshipCollections().isSuspended());
  }

  /**
   * Test {@link ExecutionEntityImpl#isSuspended()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#isSuspended()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExecutionEntityImpl.isSuspended()"})
  public void testIsSuspended_thenReturnTrue() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setSuspensionState(2);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.isSuspended());
  }

  /**
   * Test {@link ExecutionEntityImpl#getName()}.
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionEntityImpl.getName()"})
  public void testGetName() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLocalizedName("");

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getName());
  }

  /**
   * Test {@link ExecutionEntityImpl#getName()}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionEntityImpl.getName()"})
  public void testGetName_givenCreateWithEmptyRelationshipCollections_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getName());
  }

  /**
   * Test {@link ExecutionEntityImpl#getName()}.
   *
   * <ul>
   *   <li>Then return {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionEntityImpl.getName()"})
  public void testGetName_thenReturnFoo() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLocalizedName("foo");

    // Act and Assert
    assertEquals("foo", createWithEmptyRelationshipCollectionsResult.getName());
  }

  /**
   * Test {@link ExecutionEntityImpl#getDescription()}.
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getDescription()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionEntityImpl.getDescription()"})
  public void testGetDescription() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLocalizedDescription("");

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getDescription());
  }

  /**
   * Test {@link ExecutionEntityImpl#getDescription()}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getDescription()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionEntityImpl.getDescription()"})
  public void testGetDescription_givenCreateWithEmptyRelationshipCollections_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getDescription());
  }

  /**
   * Test {@link ExecutionEntityImpl#getDescription()}.
   *
   * <ul>
   *   <li>Then return {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getDescription()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionEntityImpl.getDescription()"})
  public void testGetDescription_thenReturnFoo() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLocalizedDescription("foo");

    // Act and Assert
    assertEquals("foo", createWithEmptyRelationshipCollectionsResult.getDescription());
  }

  /**
   * Test {@link ExecutionEntityImpl#getProcessVariables()}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getProcessVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ExecutionEntityImpl.getProcessVariables()"})
  public void testGetProcessVariables_givenCreateWithEmptyRelationshipCollections() {
    // Arrange, Act and Assert
    assertTrue(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()
            .getProcessVariables()
            .isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getProcessVariables()}.
   *
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor) Id is {@code null}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getProcessVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ExecutionEntityImpl.getProcessVariables()"})
  public void testGetProcessVariables_givenVariableInstanceEntityImplIdIsNull_thenReturnEmpty() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setId(null);
    variableInstanceEntityImpl.setTaskId(null);

    ArrayList<VariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(variableInstanceEntityImpl);

    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setQueryVariables(queryVariables);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getProcessVariables().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getProcessVariables()}.
   *
   * <ul>
   *   <li>Given {@link VariableInstanceEntityImpl} (default constructor) TaskId is {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getProcessVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ExecutionEntityImpl.getProcessVariables()"})
  public void testGetProcessVariables_givenVariableInstanceEntityImplTaskIdIsFoo() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setId("foo");
    variableInstanceEntityImpl.setTaskId("foo");

    ArrayList<VariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(variableInstanceEntityImpl);

    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setQueryVariables(queryVariables);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getProcessVariables().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getProcessVariables()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getProcessVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ExecutionEntityImpl.getProcessVariables()"})
  public void testGetProcessVariables_thenReturnEmpty() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setQueryVariables(new ArrayList<>());

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getProcessVariables().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#getProcessVariables()}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getProcessVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ExecutionEntityImpl.getProcessVariables()"})
  public void testGetProcessVariables_thenReturnSizeIsOne() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(new BigDecimalType());
    variableInstanceEntityImpl.setId("foo");
    variableInstanceEntityImpl.setTaskId(null);

    ArrayList<VariableInstanceEntity> queryVariables = new ArrayList<>();
    queryVariables.add(variableInstanceEntityImpl);

    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setQueryVariables(queryVariables);

    // Act
    Map<String, Object> actualProcessVariables =
        createWithEmptyRelationshipCollectionsResult.getProcessVariables();

    // Assert
    assertEquals(1, actualProcessVariables.size());
    assertNull(actualProcessVariables.get(null));
  }

  /**
   * Test {@link ExecutionEntityImpl#getQueryVariables()}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getQueryVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ExecutionEntityImpl.getQueryVariables()"})
  public void testGetQueryVariables_givenCreateWithEmptyRelationshipCollections_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getQueryVariables());
  }

  /**
   * Test {@link ExecutionEntityImpl#getQueryVariables()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#getQueryVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ExecutionEntityImpl.getQueryVariables()"})
  public void testGetQueryVariables_thenReturnEmpty() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setQueryVariables(new ArrayList<>());

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getQueryVariables().isEmpty());
  }

  /**
   * Test {@link ExecutionEntityImpl#toString()}.
   *
   * <ul>
   *   <li>Then return {@code Execution[ id 'null' ] - parent '42'}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionEntityImpl.toString()"})
  public void testToString_thenReturnExecutionIdNullParent42() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setScope(false);
    createWithEmptyRelationshipCollectionsResult.setParentId("42");

    // Act and Assert
    assertEquals(
        "Execution[ id 'null' ] - parent '42'",
        createWithEmptyRelationshipCollectionsResult.toString());
  }

  /**
   * Test {@link ExecutionEntityImpl#toString()}.
   *
   * <ul>
   *   <li>Then return {@code Multi instance root execution[ id 'null' ] - parent '42'}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionEntityImpl.toString()"})
  public void testToString_thenReturnMultiInstanceRootExecutionIdNullParent42() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setMultiInstanceRoot(true);
    createWithEmptyRelationshipCollectionsResult.setScope(false);
    createWithEmptyRelationshipCollectionsResult.setParentId("42");

    // Act and Assert
    assertEquals(
        "Multi instance root execution[ id 'null' ] - parent '42'",
        createWithEmptyRelationshipCollectionsResult.toString());
  }

  /**
   * Test {@link ExecutionEntityImpl#toString()}.
   *
   * <ul>
   *   <li>Then return {@code ProcessInstance[null]}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionEntityImpl.toString()"})
  public void testToString_thenReturnProcessInstanceNull() {
    // Arrange, Act and Assert
    assertEquals(
        "ProcessInstance[null]",
        ExecutionEntityImpl.createWithEmptyRelationshipCollections().toString());
  }

  /**
   * Test {@link ExecutionEntityImpl#toString()}.
   *
   * <ul>
   *   <li>Then return {@code Scoped execution[ id 'null' ] - parent '42'}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionEntityImpl.toString()"})
  public void testToString_thenReturnScopedExecutionIdNullParent42() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setParentId("42");

    // Act and Assert
    assertEquals(
        "Scoped execution[ id 'null' ] - parent '42'",
        createWithEmptyRelationshipCollectionsResult.toString());
  }
}
