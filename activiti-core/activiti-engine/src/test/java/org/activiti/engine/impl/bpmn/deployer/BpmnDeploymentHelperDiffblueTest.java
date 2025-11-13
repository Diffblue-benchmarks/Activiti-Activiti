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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeploymentHelper.ExpressionType;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntityImpl;
import org.activiti.engine.test.util.TestProcessUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class BpmnDeploymentHelperDiffblueTest {
  /**
   * Test {@link BpmnDeploymentHelper#verifyProcessDefinitionsDoNotShareKeys(Collection)}.
   *
   * <p>Method under test: {@link
   * BpmnDeploymentHelper#verifyProcessDefinitionsDoNotShareKeys(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeploymentHelper.verifyProcessDefinitionsDoNotShareKeys(Collection)"
  })
  public void testVerifyProcessDefinitionsDoNotShareKeys() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    LinkedHashSet<ProcessDefinitionEntity> processDefinitions = new LinkedHashSet<>();
    processDefinitions.add(new ProcessDefinitionEntityImpl());

    // Act and Assert
    bpmnDeploymentHelper.verifyProcessDefinitionsDoNotShareKeys(processDefinitions);
  }

  /**
   * Test {@link BpmnDeploymentHelper#verifyProcessDefinitionsDoNotShareKeys(Collection)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnDeploymentHelper#verifyProcessDefinitionsDoNotShareKeys(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeploymentHelper.verifyProcessDefinitionsDoNotShareKeys(Collection)"
  })
  public void testVerifyProcessDefinitionsDoNotShareKeys_thenThrowActivitiException() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(new ProcessDefinitionEntityImpl());
    processDefinitions.add(new ProcessDefinitionEntityImpl());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> bpmnDeploymentHelper.verifyProcessDefinitionsDoNotShareKeys(processDefinitions));
  }

  /**
   * Test {@link BpmnDeploymentHelper#verifyProcessDefinitionsDoNotShareKeys(Collection)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnDeploymentHelper#verifyProcessDefinitionsDoNotShareKeys(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeploymentHelper.verifyProcessDefinitionsDoNotShareKeys(Collection)"
  })
  public void testVerifyProcessDefinitionsDoNotShareKeys_whenArrayList_thenDoesNotThrow() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    // Act and Assert
    bpmnDeploymentHelper.verifyProcessDefinitionsDoNotShareKeys(new ArrayList<>());
  }

  /**
   * Test {@link BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity,
   * List)}.
   *
   * <p>Method under test: {@link
   * BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeploymentHelper.copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)"
  })
  public void testCopyDeploymentValuesToProcessDefinitions() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    deployment.setEngineVersion("Deployment");
    deployment.setTenantId("Deployment");

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(new ProcessDefinitionEntityImpl());

    // Act
    bpmnDeploymentHelper.copyDeploymentValuesToProcessDefinitions(deployment, processDefinitions);

    // Assert
    assertEquals(1, processDefinitions.size());
    ProcessDefinitionEntity getResult = processDefinitions.get(0);
    assertTrue(getResult instanceof ProcessDefinitionEntityImpl);
    assertEquals("Deployment", getResult.getEngineVersion());
    assertEquals("Deployment", getResult.getTenantId());
  }

  /**
   * Test {@link BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity,
   * List)}.
   *
   * <p>Method under test: {@link
   * BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeploymentHelper.copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)"
  })
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
   * Test {@link BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity,
   * List)}.
   *
   * <p>Method under test: {@link
   * BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeploymentHelper.copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)"
  })
  public void testCopyDeploymentValuesToProcessDefinitions3() {
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
   * Test {@link BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity,
   * List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeploymentHelper.copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)"
  })
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
   *
   * <p>Method under test: {@link
   * BpmnDeploymentHelper#setResourceNamesOnProcessDefinitions(ParsedDeployment)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeploymentHelper.setResourceNamesOnProcessDefinitions(ParsedDeployment)"
  })
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
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnDeploymentHelper#setResourceNamesOnProcessDefinitions(ParsedDeployment)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeploymentHelper.setResourceNamesOnProcessDefinitions(ParsedDeployment)"
  })
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
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnDeploymentHelper#setResourceNamesOnProcessDefinitions(ParsedDeployment)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeploymentHelper.setResourceNamesOnProcessDefinitions(ParsedDeployment)"
  })
  public void testSetResourceNamesOnProcessDefinitions_thenDoesNotThrow() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();
    DeploymentEntityImpl entity = new DeploymentEntityImpl();
    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    HashMap<ProcessDefinitionEntity, BpmnParse> mapProcessDefinitionsToParses = new HashMap<>();

    ParsedDeployment parsedDeployment =
        new ParsedDeployment(
            entity, processDefinitions, mapProcessDefinitionsToParses, new HashMap<>());

    // Act and Assert
    bpmnDeploymentHelper.setResourceNamesOnProcessDefinitions(parsedDeployment);
  }

  /**
   * Test {@link BpmnDeploymentHelper#setResourceNamesOnProcessDefinitions(ParsedDeployment)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnDeploymentHelper#setResourceNamesOnProcessDefinitions(ParsedDeployment)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeploymentHelper.setResourceNamesOnProcessDefinitions(ParsedDeployment)"
  })
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
    assertThrows(
        ActivitiException.class,
        () -> bpmnDeploymentHelper.setResourceNamesOnProcessDefinitions(parsedDeployment));
    verify(parsedDeployment).getAllProcessDefinitions();
    verify(parsedDeployment).getResourceForProcessDefinition(isA(ProcessDefinitionEntity.class));
  }

  /**
   * Test {@link
   * BpmnDeploymentHelper#getPersistedInstanceOfProcessDefinition(ProcessDefinitionEntity)}.
   *
   * <ul>
   *   <li>Given empty string.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnDeploymentHelper#getPersistedInstanceOfProcessDefinition(ProcessDefinitionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinitionEntity BpmnDeploymentHelper.getPersistedInstanceOfProcessDefinition(ProcessDefinitionEntity)"
  })
  public void testGetPersistedInstanceOfProcessDefinition_givenEmptyString() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    processDefinition.setDeploymentId("");
    processDefinition.setTenantId("");

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> bpmnDeploymentHelper.getPersistedInstanceOfProcessDefinition(processDefinition));
  }

  /**
   * Test {@link
   * BpmnDeploymentHelper#getPersistedInstanceOfProcessDefinition(ProcessDefinitionEntity)}.
   *
   * <ul>
   *   <li>When {@link ProcessDefinitionEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnDeploymentHelper#getPersistedInstanceOfProcessDefinition(ProcessDefinitionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinitionEntity BpmnDeploymentHelper.getPersistedInstanceOfProcessDefinition(ProcessDefinitionEntity)"
  })
  public void testGetPersistedInstanceOfProcessDefinition_whenProcessDefinitionEntityImpl() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            bpmnDeploymentHelper.getPersistedInstanceOfProcessDefinition(
                new ProcessDefinitionEntityImpl()));
  }

  /**
   * Test {@link BpmnDeploymentHelper#addAuthorizationsForNewProcessDefinition(Process,
   * ProcessDefinitionEntity)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnDeploymentHelper#addAuthorizationsForNewProcessDefinition(Process,
   * ProcessDefinitionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeploymentHelper.addAuthorizationsForNewProcessDefinition(Process, ProcessDefinitionEntity)"
  })
  public void testAddAuthorizationsForNewProcessDefinition_givenNull() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    Process process = TestProcessUtil.createOneTaskProcessWithId("42");
    process.setCandidateStarterUsers(new ArrayList<>());
    process.setCandidateStarterGroups(null);

    // Act and Assert
    bpmnDeploymentHelper.addAuthorizationsForNewProcessDefinition(
        process, new ProcessDefinitionEntityImpl());
  }

  /**
   * Test {@link BpmnDeploymentHelper#addAuthorizationsForNewProcessDefinition(Process,
   * ProcessDefinitionEntity)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnDeploymentHelper#addAuthorizationsForNewProcessDefinition(Process,
   * ProcessDefinitionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeploymentHelper.addAuthorizationsForNewProcessDefinition(Process, ProcessDefinitionEntity)"
  })
  public void testAddAuthorizationsForNewProcessDefinition_thenThrowActivitiException() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    Process process = mock(Process.class);
    when(process.getCandidateStarterUsers()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            bpmnDeploymentHelper.addAuthorizationsForNewProcessDefinition(
                process, new ProcessDefinitionEntityImpl()));
    verify(process).getCandidateStarterUsers();
  }

  /**
   * Test {@link BpmnDeploymentHelper#addAuthorizationsForNewProcessDefinition(Process,
   * ProcessDefinitionEntity)}.
   *
   * <ul>
   *   <li>When createOneTaskProcessWithId {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnDeploymentHelper#addAuthorizationsForNewProcessDefinition(Process,
   * ProcessDefinitionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeploymentHelper.addAuthorizationsForNewProcessDefinition(Process, ProcessDefinitionEntity)"
  })
  public void testAddAuthorizationsForNewProcessDefinition_whenCreateOneTaskProcessWithId42() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();
    Process process = TestProcessUtil.createOneTaskProcessWithId("42");

    // Act and Assert
    bpmnDeploymentHelper.addAuthorizationsForNewProcessDefinition(
        process, new ProcessDefinitionEntityImpl());
  }

  /**
   * Test {@link BpmnDeploymentHelper#addAuthorizationsForNewProcessDefinition(Process,
   * ProcessDefinitionEntity)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link
   * BpmnDeploymentHelper#addAuthorizationsForNewProcessDefinition(Process,
   * ProcessDefinitionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeploymentHelper.addAuthorizationsForNewProcessDefinition(Process, ProcessDefinitionEntity)"
  })
  public void testAddAuthorizationsForNewProcessDefinition_whenNull_thenDoesNotThrow() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    // Act and Assert
    bpmnDeploymentHelper.addAuthorizationsForNewProcessDefinition(
        null, new ProcessDefinitionEntityImpl());
  }

  /**
   * Test {@link BpmnDeploymentHelper#addAuthorizationsFromIterator(CommandContext, List,
   * ProcessDefinitionEntity, ExpressionType)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContext#getIdentityLinkEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnDeploymentHelper#addAuthorizationsFromIterator(CommandContext,
   * List, ProcessDefinitionEntity, ExpressionType)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeploymentHelper.addAuthorizationsFromIterator(CommandContext, List, ProcessDefinitionEntity, ExpressionType)"
  })
  public void testAddAuthorizationsFromIterator_thenCallsGetIdentityLinkEntityManager() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    IdentityLinkEntityManager identityLinkEntityManager = mock(IdentityLinkEntityManager.class);
    doNothing().when(identityLinkEntityManager).insert(Mockito.<IdentityLinkEntity>any());
    when(identityLinkEntityManager.create()).thenReturn(new IdentityLinkEntityImpl());

    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getIdentityLinkEntityManager()).thenReturn(identityLinkEntityManager);

    ArrayList<String> expressions = new ArrayList<>();
    expressions.add("foo");

    // Act
    bpmnDeploymentHelper.addAuthorizationsFromIterator(
        commandContext, expressions, new ProcessDefinitionEntityImpl(), ExpressionType.USER);

    // Assert
    verify(commandContext, atLeast(1)).getIdentityLinkEntityManager();
    verify(identityLinkEntityManager).create();
    verify(identityLinkEntityManager).insert(isA(IdentityLinkEntity.class));
  }

  /**
   * Test {@link BpmnDeploymentHelper#addAuthorizationsFromIterator(CommandContext, List,
   * ProcessDefinitionEntity, ExpressionType)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContext#getIdentityLinkEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnDeploymentHelper#addAuthorizationsFromIterator(CommandContext,
   * List, ProcessDefinitionEntity, ExpressionType)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeploymentHelper.addAuthorizationsFromIterator(CommandContext, List, ProcessDefinitionEntity, ExpressionType)"
  })
  public void testAddAuthorizationsFromIterator_thenCallsGetIdentityLinkEntityManager2() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    IdentityLinkEntityManager identityLinkEntityManager = mock(IdentityLinkEntityManager.class);
    doNothing().when(identityLinkEntityManager).insert(Mockito.<IdentityLinkEntity>any());
    when(identityLinkEntityManager.create()).thenReturn(new IdentityLinkEntityImpl());

    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getIdentityLinkEntityManager()).thenReturn(identityLinkEntityManager);

    ArrayList<String> expressions = new ArrayList<>();
    expressions.add("foo");

    // Act
    bpmnDeploymentHelper.addAuthorizationsFromIterator(
        commandContext, expressions, new ProcessDefinitionEntityImpl(), ExpressionType.GROUP);

    // Assert
    verify(commandContext, atLeast(1)).getIdentityLinkEntityManager();
    verify(identityLinkEntityManager).create();
    verify(identityLinkEntityManager).insert(isA(IdentityLinkEntity.class));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link BpmnDeploymentHelper}
   *   <li>{@link BpmnDeploymentHelper#setEventSubscriptionManager(EventSubscriptionManager)}
   *   <li>{@link BpmnDeploymentHelper#setTimerManager(TimerManager)}
   *   <li>{@link BpmnDeploymentHelper#getEventSubscriptionManager()}
   *   <li>{@link BpmnDeploymentHelper#getTimerManager()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnDeploymentHelper.<init>()",
    "EventSubscriptionManager BpmnDeploymentHelper.getEventSubscriptionManager()",
    "TimerManager BpmnDeploymentHelper.getTimerManager()",
    "void BpmnDeploymentHelper.setEventSubscriptionManager(EventSubscriptionManager)",
    "void BpmnDeploymentHelper.setTimerManager(TimerManager)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    BpmnDeploymentHelper actualBpmnDeploymentHelper = new BpmnDeploymentHelper();
    EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
    actualBpmnDeploymentHelper.setEventSubscriptionManager(eventSubscriptionManager);
    TimerManager timerManager = new TimerManager();
    actualBpmnDeploymentHelper.setTimerManager(timerManager);
    EventSubscriptionManager actualEventSubscriptionManager =
        actualBpmnDeploymentHelper.getEventSubscriptionManager();

    // Assert
    assertSame(eventSubscriptionManager, actualEventSubscriptionManager);
    assertSame(timerManager, actualBpmnDeploymentHelper.getTimerManager());
  }
}
