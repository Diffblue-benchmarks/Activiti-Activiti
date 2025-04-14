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
package org.activiti.engine.impl.bpmn.deployer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class BpmnDeploymentHelperDiffblueTest {
  /**
   * Test {@link BpmnDeploymentHelper#verifyProcessDefinitionsDoNotShareKeys(Collection)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnDeploymentHelper#verifyProcessDefinitionsDoNotShareKeys(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BpmnDeploymentHelper.verifyProcessDefinitionsDoNotShareKeys(Collection)"})
  public void testVerifyProcessDefinitionsDoNotShareKeys_thenThrowActivitiException() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(new ProcessDefinitionEntityImpl());
    processDefinitions.add(new ProcessDefinitionEntityImpl());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> bpmnDeploymentHelper.verifyProcessDefinitionsDoNotShareKeys(processDefinitions));
  }

  /**
   * Test {@link BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)}.
   * <p>
   * Method under test: {@link BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BpmnDeploymentHelper.copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)"})
  public void testCopyDeploymentValuesToProcessDefinitions() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    deployment.setEngineVersion(null);
    deployment.setTenantId("Deployment");

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(new ProcessDefinitionEntityImpl());

    // Act
    bpmnDeploymentHelper.copyDeploymentValuesToProcessDefinitions(deployment, processDefinitions);

    // Assert
    assertEquals(1, processDefinitions.size());
    ProcessDefinitionEntity getResult = processDefinitions.get(0);
    assertTrue(getResult instanceof ProcessDefinitionEntityImpl);
    assertEquals("Deployment", getResult.getTenantId());
    assertNull(getResult.getEngineVersion());
  }

  /**
   * Test {@link BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)}.
   * <p>
   * Method under test: {@link BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BpmnDeploymentHelper.copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)"})
  public void testCopyDeploymentValuesToProcessDefinitions2() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    deployment.setEngineVersion("Deployment");
    deployment.setTenantId(null);

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(new ProcessDefinitionEntityImpl());

    // Act
    bpmnDeploymentHelper.copyDeploymentValuesToProcessDefinitions(deployment, processDefinitions);

    // Assert
    assertEquals(1, processDefinitions.size());
    ProcessDefinitionEntity getResult = processDefinitions.get(0);
    assertTrue(getResult instanceof ProcessDefinitionEntityImpl);
    assertEquals("", getResult.getTenantId());
    assertEquals("Deployment", getResult.getEngineVersion());
  }

  /**
   * Test {@link BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BpmnDeploymentHelper.copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)"})
  public void testCopyDeploymentValuesToProcessDefinitions_thenArrayListSizeIsOne() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    deployment.setEngineVersion(null);
    deployment.setTenantId(null);

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(new ProcessDefinitionEntityImpl());

    // Act
    bpmnDeploymentHelper.copyDeploymentValuesToProcessDefinitions(deployment, processDefinitions);

    // Assert that nothing has changed
    assertEquals(1, processDefinitions.size());
    ProcessDefinitionEntity getResult = processDefinitions.get(0);
    assertTrue(getResult instanceof ProcessDefinitionEntityImpl);
    assertEquals("", getResult.getTenantId());
  }

  /**
   * Test {@link BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BpmnDeploymentHelper.copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)"})
  public void testCopyDeploymentValuesToProcessDefinitions_thenArrayListSizeIsTwo() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(new ProcessDefinitionEntityImpl());
    processDefinitions.add(new ProcessDefinitionEntityImpl());

    // Act
    bpmnDeploymentHelper.copyDeploymentValuesToProcessDefinitions(deployment, processDefinitions);

    // Assert that nothing has changed
    assertEquals(2, processDefinitions.size());
    ProcessDefinitionEntity getResult = processDefinitions.get(0);
    assertTrue(getResult instanceof ProcessDefinitionEntityImpl);
    assertEquals("", getResult.getTenantId());
  }

  /**
   * Test {@link BpmnDeploymentHelper#setResourceNamesOnProcessDefinitions(ParsedDeployment)}.
   * <p>
   * Method under test: {@link BpmnDeploymentHelper#setResourceNamesOnProcessDefinitions(ParsedDeployment)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BpmnDeploymentHelper.setResourceNamesOnProcessDefinitions(ParsedDeployment)"})
  public void testSetResourceNamesOnProcessDefinitions() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    ArrayList<ProcessDefinitionEntity> processDefinitionEntityList = new ArrayList<>();
    processDefinitionEntityList.add(new ProcessDefinitionEntityImpl());
    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getResourceForProcessDefinition(Mockito.<ProcessDefinitionEntity>any()))
        .thenReturn(new ResourceEntityImpl());
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(processDefinitionEntityList);

    // Act
    bpmnDeploymentHelper.setResourceNamesOnProcessDefinitions(parsedDeployment);

    // Assert
    verify(parsedDeployment).getAllProcessDefinitions();
    verify(parsedDeployment).getResourceForProcessDefinition(isA(ProcessDefinitionEntity.class));
  }

  /**
   * Test {@link BpmnDeploymentHelper#setResourceNamesOnProcessDefinitions(ParsedDeployment)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnDeploymentHelper#setResourceNamesOnProcessDefinitions(ParsedDeployment)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BpmnDeploymentHelper.setResourceNamesOnProcessDefinitions(ParsedDeployment)"})
  public void testSetResourceNamesOnProcessDefinitions_givenArrayList() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();
    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(new ArrayList<>());

    // Act
    bpmnDeploymentHelper.setResourceNamesOnProcessDefinitions(parsedDeployment);

    // Assert
    verify(parsedDeployment).getAllProcessDefinitions();
  }

  /**
   * Test {@link BpmnDeploymentHelper#setResourceNamesOnProcessDefinitions(ParsedDeployment)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnDeploymentHelper#setResourceNamesOnProcessDefinitions(ParsedDeployment)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BpmnDeploymentHelper.setResourceNamesOnProcessDefinitions(ParsedDeployment)"})
  public void testSetResourceNamesOnProcessDefinitions_thenThrowActivitiException() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    ArrayList<ProcessDefinitionEntity> processDefinitionEntityList = new ArrayList<>();
    processDefinitionEntityList.add(new ProcessDefinitionEntityImpl());
    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getResourceForProcessDefinition(Mockito.<ProcessDefinitionEntity>any()))
        .thenThrow(new ActivitiException("An error occurred"));
    when(parsedDeployment.getAllProcessDefinitions()).thenReturn(processDefinitionEntityList);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> bpmnDeploymentHelper.setResourceNamesOnProcessDefinitions(parsedDeployment));
    verify(parsedDeployment).getAllProcessDefinitions();
    verify(parsedDeployment).getResourceForProcessDefinition(isA(ProcessDefinitionEntity.class));
  }

  /**
   * Test {@link BpmnDeploymentHelper#getPersistedInstanceOfProcessDefinition(ProcessDefinitionEntity)}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnDeploymentHelper#getPersistedInstanceOfProcessDefinition(ProcessDefinitionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessDefinitionEntity BpmnDeploymentHelper.getPersistedInstanceOfProcessDefinition(ProcessDefinitionEntity)"})
  public void testGetPersistedInstanceOfProcessDefinition_givenEmptyString() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    processDefinition.setDeploymentId("");
    processDefinition.setTenantId(null);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> bpmnDeploymentHelper.getPersistedInstanceOfProcessDefinition(processDefinition));
  }

  /**
   * Test {@link BpmnDeploymentHelper#getPersistedInstanceOfProcessDefinition(ProcessDefinitionEntity)}.
   * <ul>
   *   <li>When {@link ProcessDefinitionEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnDeploymentHelper#getPersistedInstanceOfProcessDefinition(ProcessDefinitionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessDefinitionEntity BpmnDeploymentHelper.getPersistedInstanceOfProcessDefinition(ProcessDefinitionEntity)"})
  public void testGetPersistedInstanceOfProcessDefinition_whenProcessDefinitionEntityImpl() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> bpmnDeploymentHelper.getPersistedInstanceOfProcessDefinition(new ProcessDefinitionEntityImpl()));
  }

  /**
   * Test {@link BpmnDeploymentHelper#updateTimersAndEvents(ProcessDefinitionEntity, ProcessDefinitionEntity, ParsedDeployment)}.
   * <ul>
   *   <li>Then calls {@link EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity, Process, BpmnModel)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnDeploymentHelper#updateTimersAndEvents(ProcessDefinitionEntity, ProcessDefinitionEntity, ParsedDeployment)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void BpmnDeploymentHelper.updateTimersAndEvents(ProcessDefinitionEntity, ProcessDefinitionEntity, ParsedDeployment)"})
  public void testUpdateTimersAndEvents_thenCallsAddMessageEventSubscriptions() {
    // Arrange
    EventSubscriptionManager eventSubscriptionManager = mock(EventSubscriptionManager.class);
    doNothing().when(eventSubscriptionManager)
        .addMessageEventSubscriptions(Mockito.<ProcessDefinitionEntity>any(), Mockito.<Process>any(),
            Mockito.<BpmnModel>any());
    doNothing().when(eventSubscriptionManager)
        .addSignalEventSubscriptions(Mockito.<CommandContext>any(), Mockito.<ProcessDefinitionEntity>any(),
            Mockito.<Process>any(), Mockito.<BpmnModel>any());
    doNothing().when(eventSubscriptionManager)
        .removeObsoleteMessageEventSubscriptions(Mockito.<ProcessDefinitionEntity>any());
    doNothing().when(eventSubscriptionManager)
        .removeObsoleteSignalEventSubScription(Mockito.<ProcessDefinitionEntity>any());
    TimerManager timerManager = mock(TimerManager.class);
    doNothing().when(timerManager).removeObsoleteTimers(Mockito.<ProcessDefinitionEntity>any());
    doNothing().when(timerManager).scheduleTimers(Mockito.<ProcessDefinitionEntity>any(), Mockito.<Process>any());

    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();
    bpmnDeploymentHelper.setTimerManager(timerManager);
    bpmnDeploymentHelper.setEventSubscriptionManager(eventSubscriptionManager);
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    ProcessDefinitionEntityImpl previousProcessDefinition = new ProcessDefinitionEntityImpl();
    ParsedDeployment parsedDeployment = mock(ParsedDeployment.class);
    when(parsedDeployment.getBpmnModelForProcessDefinition(Mockito.<ProcessDefinitionEntity>any()))
        .thenReturn(new BpmnModel());
    when(parsedDeployment.getProcessModelForProcessDefinition(Mockito.<ProcessDefinitionEntity>any()))
        .thenReturn(new Process());

    // Act
    bpmnDeploymentHelper.updateTimersAndEvents(processDefinition, previousProcessDefinition, parsedDeployment);

    // Assert
    verify(eventSubscriptionManager).addMessageEventSubscriptions(isA(ProcessDefinitionEntity.class),
        isA(Process.class), isA(BpmnModel.class));
    verify(eventSubscriptionManager).addSignalEventSubscriptions(isNull(), isA(ProcessDefinitionEntity.class),
        isA(Process.class), isA(BpmnModel.class));
    verify(eventSubscriptionManager).removeObsoleteMessageEventSubscriptions(isA(ProcessDefinitionEntity.class));
    verify(eventSubscriptionManager).removeObsoleteSignalEventSubScription(isA(ProcessDefinitionEntity.class));
    verify(parsedDeployment).getBpmnModelForProcessDefinition(isA(ProcessDefinitionEntity.class));
    verify(parsedDeployment).getProcessModelForProcessDefinition(isA(ProcessDefinitionEntity.class));
    verify(timerManager).removeObsoleteTimers(isA(ProcessDefinitionEntity.class));
    verify(timerManager).scheduleTimers(isA(ProcessDefinitionEntity.class), isA(Process.class));
  }

  /**
   * Test {@link BpmnDeploymentHelper#addAuthorizationsForNewProcessDefinition(Process, ProcessDefinitionEntity)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnDeploymentHelper#addAuthorizationsForNewProcessDefinition(Process, ProcessDefinitionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void BpmnDeploymentHelper.addAuthorizationsForNewProcessDefinition(Process, ProcessDefinitionEntity)"})
  public void testAddAuthorizationsForNewProcessDefinition_thenThrowIllegalStateException() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();
    Process process = mock(Process.class);
    when(process.getCandidateStarterUsers()).thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> bpmnDeploymentHelper
        .addAuthorizationsForNewProcessDefinition(process, new ProcessDefinitionEntityImpl()));
    verify(process).getCandidateStarterUsers();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BpmnDeploymentHelper}
   *   <li>{@link BpmnDeploymentHelper#setEventSubscriptionManager(EventSubscriptionManager)}
   *   <li>{@link BpmnDeploymentHelper#setTimerManager(TimerManager)}
   *   <li>{@link BpmnDeploymentHelper#getEventSubscriptionManager()}
   *   <li>{@link BpmnDeploymentHelper#getTimerManager()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BpmnDeploymentHelper.<init>()",
      "EventSubscriptionManager BpmnDeploymentHelper.getEventSubscriptionManager()",
      "TimerManager BpmnDeploymentHelper.getTimerManager()",
      "void BpmnDeploymentHelper.setEventSubscriptionManager(EventSubscriptionManager)",
      "void BpmnDeploymentHelper.setTimerManager(TimerManager)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    BpmnDeploymentHelper actualBpmnDeploymentHelper = new BpmnDeploymentHelper();
    EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
    actualBpmnDeploymentHelper.setEventSubscriptionManager(eventSubscriptionManager);
    TimerManager timerManager = new TimerManager();
    actualBpmnDeploymentHelper.setTimerManager(timerManager);
    EventSubscriptionManager actualEventSubscriptionManager = actualBpmnDeploymentHelper.getEventSubscriptionManager();

    // Assert
    assertSame(eventSubscriptionManager, actualEventSubscriptionManager);
    assertSame(timerManager, actualBpmnDeploymentHelper.getTimerManager());
  }
}
