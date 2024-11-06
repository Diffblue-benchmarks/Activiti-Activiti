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
package org.activiti.runtime.api.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.activiti.api.process.model.payloads.DeleteProcessPayload;
import org.activiti.api.process.model.payloads.GetProcessDefinitionsPayload;
import org.activiti.api.process.model.payloads.GetProcessInstancesPayload;
import org.activiti.api.process.model.payloads.GetVariablesPayload;
import org.activiti.api.process.model.payloads.ReceiveMessagePayload;
import org.activiti.api.process.model.payloads.RemoveProcessVariablesPayload;
import org.activiti.api.process.model.payloads.ResumeProcessPayload;
import org.activiti.api.process.model.payloads.SetProcessVariablesPayload;
import org.activiti.api.process.model.payloads.SignalPayload;
import org.activiti.api.process.model.payloads.StartMessagePayload;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.api.process.model.payloads.SuspendProcessPayload;
import org.activiti.api.process.model.payloads.UpdateProcessPayload;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.api.runtime.shared.NotFoundException;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.ProcessDefinitionQueryImpl;
import org.activiti.engine.impl.ProcessInstanceQueryImpl;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.runtime.api.model.impl.APIProcessDefinitionConverter;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.activiti.runtime.api.model.impl.APIVariableInstanceConverter;
import org.activiti.runtime.api.query.impl.PageImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {ProcessAdminRuntimeImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProcessAdminRuntimeImplDiffblueTest {
  @MockBean
  private APIProcessDefinitionConverter aPIProcessDefinitionConverter;

  @MockBean
  private APIProcessInstanceConverter aPIProcessInstanceConverter;

  @MockBean
  private APIVariableInstanceConverter aPIVariableInstanceConverter;

  @MockBean
  private ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  private ProcessAdminRuntimeImpl processAdminRuntimeImpl;

  @MockBean
  private ProcessVariablesPayloadValidator processVariablesPayloadValidator;

  @MockBean
  private RepositoryService repositoryService;

  @MockBean
  private RuntimeService runtimeService;

  /**
   * Test {@link ProcessAdminRuntimeImpl#processDefinition(String)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessAdminRuntimeImpl#processDefinition(String)}
   */
  @Test
  @DisplayName("Test processDefinition(String); then throw IllegalStateException")
  void testProcessDefinition_thenThrowIllegalStateException() {
    // Arrange
    when(repositoryService.createDeploymentQuery()).thenThrow(new IllegalStateException("foo"));
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(new ProcessDefinitionQueryImpl());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> processAdminRuntimeImpl.processDefinition("42"));
    verify(repositoryService).createDeploymentQuery();
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test
   * {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}.
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload() {
    // Arrange
    ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class);
    when(processDefinitionQuery.list()).thenReturn(new ArrayList<>());
    when(processDefinitionQuery.count()).thenReturn(3L);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(
        aPIProcessDefinitionConverter.from(Mockito.<Collection<org.activiti.engine.repository.ProcessDefinition>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);

    // Act
    Page<org.activiti.api.process.model.ProcessDefinition> actualProcessDefinitionsResult = processAdminRuntimeImpl
        .processDefinitions(pageable, new GetProcessDefinitionsPayload());

    // Assert
    verify(repositoryService).createProcessDefinitionQuery();
    verify(processDefinitionQuery).count();
    verify(processDefinitionQuery).list();
    verify(aPIProcessDefinitionConverter).from(isA(Collection.class));
    assertTrue(actualProcessDefinitionsResult instanceof PageImpl);
    assertEquals(3, actualProcessDefinitionsResult.getTotalItems());
    assertTrue(actualProcessDefinitionsResult.getContent().isEmpty());
  }

  /**
   * Test
   * {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}.
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload2() {
    // Arrange
    ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class);
    when(processDefinitionQuery.list()).thenReturn(new ArrayList<>());
    when(processDefinitionQuery.count()).thenReturn(3L);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(
        aPIProcessDefinitionConverter.from(Mockito.<Collection<org.activiti.engine.repository.ProcessDefinition>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);

    // Act
    Page<org.activiti.api.process.model.ProcessDefinition> actualProcessDefinitionsResult = processAdminRuntimeImpl
        .processDefinitions(pageable, new GetProcessDefinitionsPayload("42", new HashSet<>()));

    // Assert
    verify(repositoryService).createProcessDefinitionQuery();
    verify(processDefinitionQuery).count();
    verify(processDefinitionQuery).list();
    verify(aPIProcessDefinitionConverter).from(isA(Collection.class));
    assertTrue(actualProcessDefinitionsResult instanceof PageImpl);
    assertEquals(3, actualProcessDefinitionsResult.getTotalItems());
    assertTrue(actualProcessDefinitionsResult.getContent().isEmpty());
  }

  /**
   * Test
   * {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}.
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload3() {
    // Arrange
    ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class);
    when(processDefinitionQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenReturn(new ProcessDefinitionQueryImpl());
    when(processDefinitionQuery.list()).thenReturn(new ArrayList<>());
    when(processDefinitionQuery.count()).thenReturn(3L);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(
        aPIProcessDefinitionConverter.from(Mockito.<Collection<org.activiti.engine.repository.ProcessDefinition>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);
    GetProcessDefinitionsPayload getProcessDefinitionsPayload = mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.getProcessDefinitionKeys()).thenReturn(new HashSet<>());
    when(getProcessDefinitionsPayload.hasDefinitionKeys()).thenReturn(true);

    // Act
    Page<org.activiti.api.process.model.ProcessDefinition> actualProcessDefinitionsResult = processAdminRuntimeImpl
        .processDefinitions(pageable, getProcessDefinitionsPayload);

    // Assert
    verify(getProcessDefinitionsPayload).getProcessDefinitionKeys();
    verify(getProcessDefinitionsPayload).hasDefinitionKeys();
    verify(repositoryService).createProcessDefinitionQuery();
    verify(processDefinitionQuery).count();
    verify(processDefinitionQuery).list();
    verify(processDefinitionQuery).processDefinitionKeys(isA(Set.class));
    verify(aPIProcessDefinitionConverter).from(isA(Collection.class));
    assertTrue(actualProcessDefinitionsResult instanceof PageImpl);
    assertEquals(3, actualProcessDefinitionsResult.getTotalItems());
    assertTrue(actualProcessDefinitionsResult.getContent().isEmpty());
  }

  /**
   * Test
   * {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}.
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload4() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(mock(ProcessDefinitionQuery.class));
    Pageable pageable = Pageable.of(1, 3);
    GetProcessDefinitionsPayload getProcessDefinitionsPayload = mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.getProcessDefinitionKeys()).thenThrow(new IllegalStateException("foo"));
    when(getProcessDefinitionsPayload.hasDefinitionKeys()).thenReturn(true);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> processAdminRuntimeImpl.processDefinitions(pageable, getProcessDefinitionsPayload));
    verify(getProcessDefinitionsPayload).getProcessDefinitionKeys();
    verify(getProcessDefinitionsPayload).hasDefinitionKeys();
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test
   * {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'; when 'null'")
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload_whenNull() {
    // Arrange, Act and Assert
    assertThrows(IllegalStateException.class,
        () -> processAdminRuntimeImpl.processDefinitions(Pageable.of(1, 3), null));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable)} with
   * {@code pageable}.
   * <ul>
   *   <li>Then return {@link PageImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable) with 'pageable'; then return PageImpl")
  void testProcessDefinitionsWithPageable_thenReturnPageImpl() {
    // Arrange
    ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class);
    when(processDefinitionQuery.list()).thenReturn(new ArrayList<>());
    when(processDefinitionQuery.count()).thenReturn(3L);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(
        aPIProcessDefinitionConverter.from(Mockito.<Collection<org.activiti.engine.repository.ProcessDefinition>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    Page<org.activiti.api.process.model.ProcessDefinition> actualProcessDefinitionsResult = processAdminRuntimeImpl
        .processDefinitions(Pageable.of(1, 3));

    // Assert
    verify(repositoryService).createProcessDefinitionQuery();
    verify(processDefinitionQuery).count();
    verify(processDefinitionQuery).list();
    verify(aPIProcessDefinitionConverter).from(isA(Collection.class));
    assertTrue(actualProcessDefinitionsResult instanceof PageImpl);
    assertEquals(3, actualProcessDefinitionsResult.getTotalItems());
    assertTrue(actualProcessDefinitionsResult.getContent().isEmpty());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#start(StartMessagePayload)} with
   * {@code messagePayload}.
   * <ul>
   *   <li>Then return {@link ProcessInstanceImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessAdminRuntimeImpl#start(StartMessagePayload)}
   */
  @Test
  @DisplayName("Test start(StartMessagePayload) with 'messagePayload'; then return ProcessInstanceImpl (default constructor)")
  void testStartWithMessagePayload_thenReturnProcessInstanceImpl() {
    // Arrange
    when(runtimeService.startProcessInstanceByMessage(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<Map<String, Object>>any())).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);
    doNothing().when(processVariablesPayloadValidator)
        .checkStartMessagePayloadVariables(Mockito.<StartMessagePayload>any(), Mockito.<String>any());

    // Act
    org.activiti.api.process.model.ProcessInstance actualStartResult = processAdminRuntimeImpl
        .start(new StartMessagePayload());

    // Assert
    verify(runtimeService).startProcessInstanceByMessage(isNull(), isNull(), isA(Map.class));
    verify(processVariablesPayloadValidator).checkStartMessagePayloadVariables(isA(StartMessagePayload.class),
        isNull());
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
    assertSame(processInstanceImpl, actualStartResult);
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#start(StartMessagePayload)} with
   * {@code messagePayload}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessAdminRuntimeImpl#start(StartMessagePayload)}
   */
  @Test
  @DisplayName("Test start(StartMessagePayload) with 'messagePayload'; then throw IllegalStateException")
  void testStartWithMessagePayload_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException("foo")).when(processVariablesPayloadValidator)
        .checkStartMessagePayloadVariables(Mockito.<StartMessagePayload>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> processAdminRuntimeImpl.start(new StartMessagePayload()));
    verify(processVariablesPayloadValidator).checkStartMessagePayloadVariables(isA(StartMessagePayload.class),
        isNull());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#start(StartProcessPayload)} with
   * {@code startProcessPayload}.
   * <p>
   * Method under test: {@link ProcessAdminRuntimeImpl#start(StartProcessPayload)}
   */
  @Test
  @DisplayName("Test start(StartProcessPayload) with 'startProcessPayload'")
  void testStartWithStartProcessPayload() {
    // Arrange
    when(repositoryService.createDeploymentQuery()).thenThrow(new IllegalStateException("foo"));
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(new ProcessDefinitionQueryImpl());

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> processAdminRuntimeImpl.start(new StartProcessPayload(null,
            "At least Process Definition Id or Key needs to be provided to start a process",
            "At least Process Definition Id or Key needs to be provided to start a process",
            "At least Process Definition Id or Key needs to be provided to start a process", new HashMap<>())));
    verify(repositoryService).createDeploymentQuery();
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#start(StartProcessPayload)} with
   * {@code startProcessPayload}.
   * <ul>
   *   <li>Given {@link RepositoryService}.</li>
   *   <li>When {@link StartProcessPayload#StartProcessPayload()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessAdminRuntimeImpl#start(StartProcessPayload)}
   */
  @Test
  @DisplayName("Test start(StartProcessPayload) with 'startProcessPayload'; given RepositoryService; when StartProcessPayload()")
  void testStartWithStartProcessPayload_givenRepositoryService_whenStartProcessPayload() {
    // Arrange, Act and Assert
    assertThrows(IllegalStateException.class, () -> processAdminRuntimeImpl.start(new StartProcessPayload()));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#start(StartProcessPayload)} with
   * {@code startProcessPayload}.
   * <ul>
   *   <li>Then calls {@link RepositoryService#createDeploymentQuery()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessAdminRuntimeImpl#start(StartProcessPayload)}
   */
  @Test
  @DisplayName("Test start(StartProcessPayload) with 'startProcessPayload'; then calls createDeploymentQuery()")
  void testStartWithStartProcessPayload_thenCallsCreateDeploymentQuery() {
    // Arrange
    when(repositoryService.createDeploymentQuery()).thenThrow(new IllegalStateException("foo"));
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(new ProcessDefinitionQueryImpl());

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> processAdminRuntimeImpl.start(new StartProcessPayload("42",
            "At least Process Definition Id or Key needs to be provided to start a process",
            "At least Process Definition Id or Key needs to be provided to start a process",
            "At least Process Definition Id or Key needs to be provided to start a process", new HashMap<>())));
    verify(repositoryService).createDeploymentQuery();
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test
   * {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  void testProcessInstancesWithPageableGetProcessInstancesPayload() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any())).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<org.activiti.engine.runtime.ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult = processAdminRuntimeImpl
        .processInstances(pageable, getProcessInstancesPayload);

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(eq(1), eq(3));
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).processInstanceBusinessKey(eq("Business Key"));
    verify(processInstanceQuery).superProcessInstanceId(eq("42"));
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test
   * {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  void testProcessInstancesWithPageableGetProcessInstancesPayload2() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any())).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<org.activiti.engine.runtime.ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);
    GetProcessInstancesPayload getProcessInstancesPayload = mock(GetProcessInstancesPayload.class);
    when(getProcessInstancesPayload.isActiveOnly()).thenReturn(false);
    when(getProcessInstancesPayload.isSuspendedOnly()).thenReturn(true);
    when(getProcessInstancesPayload.getBusinessKey()).thenReturn("Business Key");
    when(getProcessInstancesPayload.getParentProcessInstanceId()).thenReturn("42");
    when(getProcessInstancesPayload.getProcessDefinitionKeys()).thenReturn(new HashSet<>());
    doNothing().when(getProcessInstancesPayload).setActiveOnly(anyBoolean());
    doNothing().when(getProcessInstancesPayload).setBusinessKey(Mockito.<String>any());
    doNothing().when(getProcessInstancesPayload).setParentProcessInstanceId(Mockito.<String>any());
    doNothing().when(getProcessInstancesPayload).setProcessDefinitionKeys(Mockito.<Set<String>>any());
    doNothing().when(getProcessInstancesPayload).setSuspendedOnly(anyBoolean());
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult = processAdminRuntimeImpl
        .processInstances(pageable, getProcessInstancesPayload);

    // Assert
    verify(getProcessInstancesPayload, atLeast(1)).getBusinessKey();
    verify(getProcessInstancesPayload, atLeast(1)).getParentProcessInstanceId();
    verify(getProcessInstancesPayload, atLeast(1)).getProcessDefinitionKeys();
    verify(getProcessInstancesPayload).isActiveOnly();
    verify(getProcessInstancesPayload).isSuspendedOnly();
    verify(getProcessInstancesPayload).setActiveOnly(eq(true));
    verify(getProcessInstancesPayload).setBusinessKey(eq("Business Key"));
    verify(getProcessInstancesPayload).setParentProcessInstanceId(eq("42"));
    verify(getProcessInstancesPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(getProcessInstancesPayload).setSuspendedOnly(eq(true));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(eq(1), eq(3));
    verify(processInstanceQuery).processInstanceBusinessKey(eq("Business Key"));
    verify(processInstanceQuery).superProcessInstanceId(eq("42"));
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test
   * {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  void testProcessInstancesWithPageableGetProcessInstancesPayload3() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any())).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<org.activiti.engine.runtime.ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);
    GetProcessInstancesPayload getProcessInstancesPayload = mock(GetProcessInstancesPayload.class);
    when(getProcessInstancesPayload.isActiveOnly()).thenReturn(true);
    when(getProcessInstancesPayload.isSuspendedOnly()).thenReturn(false);
    when(getProcessInstancesPayload.getBusinessKey()).thenReturn("Business Key");
    when(getProcessInstancesPayload.getParentProcessInstanceId()).thenReturn("42");
    when(getProcessInstancesPayload.getProcessDefinitionKeys()).thenReturn(new HashSet<>());
    doNothing().when(getProcessInstancesPayload).setActiveOnly(anyBoolean());
    doNothing().when(getProcessInstancesPayload).setBusinessKey(Mockito.<String>any());
    doNothing().when(getProcessInstancesPayload).setParentProcessInstanceId(Mockito.<String>any());
    doNothing().when(getProcessInstancesPayload).setProcessDefinitionKeys(Mockito.<Set<String>>any());
    doNothing().when(getProcessInstancesPayload).setSuspendedOnly(anyBoolean());
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult = processAdminRuntimeImpl
        .processInstances(pageable, getProcessInstancesPayload);

    // Assert
    verify(getProcessInstancesPayload, atLeast(1)).getBusinessKey();
    verify(getProcessInstancesPayload, atLeast(1)).getParentProcessInstanceId();
    verify(getProcessInstancesPayload, atLeast(1)).getProcessDefinitionKeys();
    verify(getProcessInstancesPayload).isActiveOnly();
    verify(getProcessInstancesPayload).isSuspendedOnly();
    verify(getProcessInstancesPayload).setActiveOnly(eq(true));
    verify(getProcessInstancesPayload).setBusinessKey(eq("Business Key"));
    verify(getProcessInstancesPayload).setParentProcessInstanceId(eq("42"));
    verify(getProcessInstancesPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(getProcessInstancesPayload).setSuspendedOnly(eq(true));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(eq(1), eq(3));
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).processInstanceBusinessKey(eq("Business Key"));
    verify(processInstanceQuery).superProcessInstanceId(eq("42"));
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test
   * {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  void testProcessInstancesWithPageableGetProcessInstancesPayload4() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any())).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<org.activiti.engine.runtime.ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);

    HashSet<String> stringSet = new HashSet<>();
    stringSet.add("foo");
    GetProcessInstancesPayload getProcessInstancesPayload = mock(GetProcessInstancesPayload.class);
    when(getProcessInstancesPayload.isActiveOnly()).thenReturn(true);
    when(getProcessInstancesPayload.isSuspendedOnly()).thenReturn(true);
    when(getProcessInstancesPayload.getBusinessKey()).thenReturn("Business Key");
    when(getProcessInstancesPayload.getParentProcessInstanceId()).thenReturn("42");
    when(getProcessInstancesPayload.getProcessDefinitionKeys()).thenReturn(stringSet);
    doNothing().when(getProcessInstancesPayload).setActiveOnly(anyBoolean());
    doNothing().when(getProcessInstancesPayload).setBusinessKey(Mockito.<String>any());
    doNothing().when(getProcessInstancesPayload).setParentProcessInstanceId(Mockito.<String>any());
    doNothing().when(getProcessInstancesPayload).setProcessDefinitionKeys(Mockito.<Set<String>>any());
    doNothing().when(getProcessInstancesPayload).setSuspendedOnly(anyBoolean());
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult = processAdminRuntimeImpl
        .processInstances(pageable, getProcessInstancesPayload);

    // Assert
    verify(getProcessInstancesPayload, atLeast(1)).getBusinessKey();
    verify(getProcessInstancesPayload, atLeast(1)).getParentProcessInstanceId();
    verify(getProcessInstancesPayload, atLeast(1)).getProcessDefinitionKeys();
    verify(getProcessInstancesPayload).isActiveOnly();
    verify(getProcessInstancesPayload).isSuspendedOnly();
    verify(getProcessInstancesPayload).setActiveOnly(eq(true));
    verify(getProcessInstancesPayload).setBusinessKey(eq("Business Key"));
    verify(getProcessInstancesPayload).setParentProcessInstanceId(eq("42"));
    verify(getProcessInstancesPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(getProcessInstancesPayload).setSuspendedOnly(eq(true));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(eq(1), eq(3));
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).processDefinitionKeys(isA(Set.class));
    verify(processInstanceQuery).processInstanceBusinessKey(eq("Business Key"));
    verify(processInstanceQuery).superProcessInstanceId(eq("42"));
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test
   * {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  void testProcessInstancesWithPageableGetProcessInstancesPayload5() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any())).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<org.activiti.engine.runtime.ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);

    HashSet<String> stringSet = new HashSet<>();
    stringSet.add("foo");
    GetProcessInstancesPayload getProcessInstancesPayload = mock(GetProcessInstancesPayload.class);
    when(getProcessInstancesPayload.isActiveOnly()).thenReturn(true);
    when(getProcessInstancesPayload.isSuspendedOnly()).thenReturn(true);
    when(getProcessInstancesPayload.getBusinessKey()).thenReturn(null);
    when(getProcessInstancesPayload.getParentProcessInstanceId()).thenReturn("42");
    when(getProcessInstancesPayload.getProcessDefinitionKeys()).thenReturn(stringSet);
    doNothing().when(getProcessInstancesPayload).setActiveOnly(anyBoolean());
    doNothing().when(getProcessInstancesPayload).setBusinessKey(Mockito.<String>any());
    doNothing().when(getProcessInstancesPayload).setParentProcessInstanceId(Mockito.<String>any());
    doNothing().when(getProcessInstancesPayload).setProcessDefinitionKeys(Mockito.<Set<String>>any());
    doNothing().when(getProcessInstancesPayload).setSuspendedOnly(anyBoolean());
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult = processAdminRuntimeImpl
        .processInstances(pageable, getProcessInstancesPayload);

    // Assert
    verify(getProcessInstancesPayload).getBusinessKey();
    verify(getProcessInstancesPayload, atLeast(1)).getParentProcessInstanceId();
    verify(getProcessInstancesPayload, atLeast(1)).getProcessDefinitionKeys();
    verify(getProcessInstancesPayload).isActiveOnly();
    verify(getProcessInstancesPayload).isSuspendedOnly();
    verify(getProcessInstancesPayload).setActiveOnly(eq(true));
    verify(getProcessInstancesPayload).setBusinessKey(eq("Business Key"));
    verify(getProcessInstancesPayload).setParentProcessInstanceId(eq("42"));
    verify(getProcessInstancesPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(getProcessInstancesPayload).setSuspendedOnly(eq(true));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(eq(1), eq(3));
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).processDefinitionKeys(isA(Set.class));
    verify(processInstanceQuery).superProcessInstanceId(eq("42"));
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test
   * {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  void testProcessInstancesWithPageableGetProcessInstancesPayload6() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<org.activiti.engine.runtime.ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);

    HashSet<String> stringSet = new HashSet<>();
    stringSet.add("foo");
    GetProcessInstancesPayload getProcessInstancesPayload = mock(GetProcessInstancesPayload.class);
    when(getProcessInstancesPayload.isActiveOnly()).thenReturn(true);
    when(getProcessInstancesPayload.isSuspendedOnly()).thenReturn(true);
    when(getProcessInstancesPayload.getBusinessKey()).thenReturn("Business Key");
    when(getProcessInstancesPayload.getParentProcessInstanceId()).thenReturn(null);
    when(getProcessInstancesPayload.getProcessDefinitionKeys()).thenReturn(stringSet);
    doNothing().when(getProcessInstancesPayload).setActiveOnly(anyBoolean());
    doNothing().when(getProcessInstancesPayload).setBusinessKey(Mockito.<String>any());
    doNothing().when(getProcessInstancesPayload).setParentProcessInstanceId(Mockito.<String>any());
    doNothing().when(getProcessInstancesPayload).setProcessDefinitionKeys(Mockito.<Set<String>>any());
    doNothing().when(getProcessInstancesPayload).setSuspendedOnly(anyBoolean());
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult = processAdminRuntimeImpl
        .processInstances(pageable, getProcessInstancesPayload);

    // Assert
    verify(getProcessInstancesPayload, atLeast(1)).getBusinessKey();
    verify(getProcessInstancesPayload).getParentProcessInstanceId();
    verify(getProcessInstancesPayload, atLeast(1)).getProcessDefinitionKeys();
    verify(getProcessInstancesPayload).isActiveOnly();
    verify(getProcessInstancesPayload).isSuspendedOnly();
    verify(getProcessInstancesPayload).setActiveOnly(eq(true));
    verify(getProcessInstancesPayload).setBusinessKey(eq("Business Key"));
    verify(getProcessInstancesPayload).setParentProcessInstanceId(eq("42"));
    verify(getProcessInstancesPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(getProcessInstancesPayload).setSuspendedOnly(eq(true));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(eq(1), eq(3));
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).processDefinitionKeys(isA(Set.class));
    verify(processInstanceQuery).processInstanceBusinessKey(eq("Business Key"));
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test
   * {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'; given empty string")
  void testProcessInstancesWithPageableGetProcessInstancesPayload_givenEmptyString() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any())).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<org.activiti.engine.runtime.ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);
    GetProcessInstancesPayload getProcessInstancesPayload = mock(GetProcessInstancesPayload.class);
    when(getProcessInstancesPayload.isActiveOnly()).thenReturn(true);
    when(getProcessInstancesPayload.isSuspendedOnly()).thenReturn(true);
    when(getProcessInstancesPayload.getBusinessKey()).thenReturn("");
    when(getProcessInstancesPayload.getParentProcessInstanceId()).thenReturn("42");
    when(getProcessInstancesPayload.getProcessDefinitionKeys()).thenReturn(new HashSet<>());
    doNothing().when(getProcessInstancesPayload).setActiveOnly(anyBoolean());
    doNothing().when(getProcessInstancesPayload).setBusinessKey(Mockito.<String>any());
    doNothing().when(getProcessInstancesPayload).setParentProcessInstanceId(Mockito.<String>any());
    doNothing().when(getProcessInstancesPayload).setProcessDefinitionKeys(Mockito.<Set<String>>any());
    doNothing().when(getProcessInstancesPayload).setSuspendedOnly(anyBoolean());
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult = processAdminRuntimeImpl
        .processInstances(pageable, getProcessInstancesPayload);

    // Assert
    verify(getProcessInstancesPayload, atLeast(1)).getBusinessKey();
    verify(getProcessInstancesPayload, atLeast(1)).getParentProcessInstanceId();
    verify(getProcessInstancesPayload, atLeast(1)).getProcessDefinitionKeys();
    verify(getProcessInstancesPayload).isActiveOnly();
    verify(getProcessInstancesPayload).isSuspendedOnly();
    verify(getProcessInstancesPayload).setActiveOnly(eq(true));
    verify(getProcessInstancesPayload).setBusinessKey(eq("Business Key"));
    verify(getProcessInstancesPayload).setParentProcessInstanceId(eq("42"));
    verify(getProcessInstancesPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(getProcessInstancesPayload).setSuspendedOnly(eq(true));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(eq(1), eq(3));
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).superProcessInstanceId(eq("42"));
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processInstances(Pageable)} with
   * {@code pageable}.
   * <ul>
   *   <li>Then return {@link PageImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessAdminRuntimeImpl#processInstances(Pageable)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable) with 'pageable'; then return PageImpl")
  void testProcessInstancesWithPageable_thenReturnPageImpl() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<org.activiti.engine.runtime.ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult = processAdminRuntimeImpl
        .processInstances(Pageable.of(1, 3));

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(eq(1), eq(3));
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processInstance(String)}.
   * <ul>
   *   <li>Then return {@link ProcessInstanceImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessAdminRuntimeImpl#processInstance(String)}
   */
  @Test
  @DisplayName("Test processInstance(String); then return ProcessInstanceImpl (default constructor)")
  void testProcessInstance_thenReturnProcessInstanceImpl() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl(
        new CommandExecutorImpl(new CommandConfig(), first));
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl);
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);

    // Act
    org.activiti.api.process.model.ProcessInstance actualProcessInstanceResult = processAdminRuntimeImpl
        .processInstance("42");

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
    assertSame(processInstanceImpl, actualProcessInstanceResult);
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processInstance(String)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessAdminRuntimeImpl#processInstance(String)}
   */
  @Test
  @DisplayName("Test processInstance(String); then throw IllegalStateException")
  void testProcessInstance_thenThrowIllegalStateException() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery()).thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> processAdminRuntimeImpl.processInstance("42"));
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processInstance(String)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessAdminRuntimeImpl#processInstance(String)}
   */
  @Test
  @DisplayName("Test processInstance(String); then throw NotFoundException")
  void testProcessInstance_thenThrowNotFoundException() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(null);
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl(
        new CommandExecutorImpl(new CommandConfig(), first));
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> processAdminRuntimeImpl.processInstance("42"));
    verify(runtimeService).createProcessInstanceQuery();
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#delete(DeleteProcessPayload)}.
   * <ul>
   *   <li>Given {@link APIProcessInstanceConverter}
   * {@link APIProcessInstanceConverter#from(ProcessInstance)} return
   * {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#delete(DeleteProcessPayload)}
   */
  @Test
  @DisplayName("Test delete(DeleteProcessPayload); given APIProcessInstanceConverter from(ProcessInstance) return 'null'; then return 'null'")
  void testDelete_givenAPIProcessInstanceConverterFromReturnNull_thenReturnNull() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    doNothing().when(runtimeService).deleteProcessInstance(Mockito.<String>any(), Mockito.<String>any());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any())).thenReturn(null);

    // Act
    org.activiti.api.process.model.ProcessInstance actualDeleteResult = processAdminRuntimeImpl
        .delete(new DeleteProcessPayload());

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    verify(runtimeService).deleteProcessInstance(isNull(), isNull());
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
    assertNull(actualDeleteResult);
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#delete(DeleteProcessPayload)}.
   * <ul>
   *   <li>Given {@link APIProcessInstanceConverter}
   * {@link APIProcessInstanceConverter#from(ProcessInstance)} throw
   * {@link IllegalStateException#IllegalStateException(String)} with
   * {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#delete(DeleteProcessPayload)}
   */
  @Test
  @DisplayName("Test delete(DeleteProcessPayload); given APIProcessInstanceConverter from(ProcessInstance) throw IllegalStateException(String) with 'foo'")
  void testDelete_givenAPIProcessInstanceConverterFromThrowIllegalStateExceptionWithFoo() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> processAdminRuntimeImpl.delete(new DeleteProcessPayload()));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#delete(DeleteProcessPayload)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessInstanceImpl#setStatus(ProcessInstanceStatus)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#delete(DeleteProcessPayload)}
   */
  @Test
  @DisplayName("Test delete(DeleteProcessPayload); then calls setStatus(ProcessInstanceStatus)")
  void testDelete_thenCallsSetStatus() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    doNothing().when(runtimeService).deleteProcessInstance(Mockito.<String>any(), Mockito.<String>any());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    ProcessInstanceImpl processInstanceImpl = mock(ProcessInstanceImpl.class);
    doThrow(new IllegalStateException("foo")).when(processInstanceImpl)
        .setStatus(Mockito.<org.activiti.api.process.model.ProcessInstance.ProcessInstanceStatus>any());
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> processAdminRuntimeImpl.delete(new DeleteProcessPayload()));
    verify(processInstanceImpl)
        .setStatus(eq(org.activiti.api.process.model.ProcessInstance.ProcessInstanceStatus.CANCELLED));
    verify(runtimeService).createProcessInstanceQuery();
    verify(runtimeService).deleteProcessInstance(isNull(), isNull());
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#delete(DeleteProcessPayload)}.
   * <ul>
   *   <li>Then return {@link ProcessInstanceImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#delete(DeleteProcessPayload)}
   */
  @Test
  @DisplayName("Test delete(DeleteProcessPayload); then return ProcessInstanceImpl (default constructor)")
  void testDelete_thenReturnProcessInstanceImpl() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    doNothing().when(runtimeService).deleteProcessInstance(Mockito.<String>any(), Mockito.<String>any());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);

    // Act
    org.activiti.api.process.model.ProcessInstance actualDeleteResult = processAdminRuntimeImpl
        .delete(new DeleteProcessPayload());

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    verify(runtimeService).deleteProcessInstance(isNull(), isNull());
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
    assertSame(processInstanceImpl, actualDeleteResult);
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#delete(DeleteProcessPayload)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#delete(DeleteProcessPayload)}
   */
  @Test
  @DisplayName("Test delete(DeleteProcessPayload); then throw NotFoundException")
  void testDelete_thenThrowNotFoundException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(null);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> processAdminRuntimeImpl.delete(new DeleteProcessPayload()));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#signal(SignalPayload)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload, String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessAdminRuntimeImpl#signal(SignalPayload)}
   */
  @Test
  @DisplayName("Test signal(SignalPayload); then calls checkSignalPayloadVariables(SignalPayload, String)")
  void testSignal_thenCallsCheckSignalPayloadVariables() {
    // Arrange
    doNothing().when(processVariablesPayloadValidator)
        .checkSignalPayloadVariables(Mockito.<SignalPayload>any(), Mockito.<String>any());

    // Act
    processAdminRuntimeImpl.signal(new SignalPayload());

    // Assert
    verify(processVariablesPayloadValidator).checkSignalPayloadVariables(isA(SignalPayload.class), isNull());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#signal(SignalPayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessAdminRuntimeImpl#signal(SignalPayload)}
   */
  @Test
  @DisplayName("Test signal(SignalPayload); then throw IllegalStateException")
  void testSignal_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException("foo")).when(processVariablesPayloadValidator)
        .checkSignalPayloadVariables(Mockito.<SignalPayload>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> processAdminRuntimeImpl.signal(new SignalPayload()));
    verify(processVariablesPayloadValidator).checkSignalPayloadVariables(isA(SignalPayload.class), isNull());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#suspend(SuspendProcessPayload)}.
   * <ul>
   *   <li>Then return {@link ProcessInstanceImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#suspend(SuspendProcessPayload)}
   */
  @Test
  @DisplayName("Test suspend(SuspendProcessPayload); then return ProcessInstanceImpl (default constructor)")
  void testSuspend_thenReturnProcessInstanceImpl() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    doNothing().when(runtimeService).suspendProcessInstanceById(Mockito.<String>any());
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);

    // Act
    org.activiti.api.process.model.ProcessInstance actualSuspendResult = processAdminRuntimeImpl
        .suspend(new SuspendProcessPayload());

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    verify(runtimeService).suspendProcessInstanceById(isNull());
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
    assertSame(processInstanceImpl, actualSuspendResult);
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#suspend(SuspendProcessPayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#suspend(SuspendProcessPayload)}
   */
  @Test
  @DisplayName("Test suspend(SuspendProcessPayload); then throw IllegalStateException")
  void testSuspend_thenThrowIllegalStateException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    doNothing().when(runtimeService).suspendProcessInstanceById(Mockito.<String>any());
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> processAdminRuntimeImpl.suspend(new SuspendProcessPayload()));
    verify(runtimeService).createProcessInstanceQuery();
    verify(runtimeService).suspendProcessInstanceById(isNull());
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#resume(ResumeProcessPayload)}.
   * <ul>
   *   <li>Then return {@link ProcessInstanceImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#resume(ResumeProcessPayload)}
   */
  @Test
  @DisplayName("Test resume(ResumeProcessPayload); then return ProcessInstanceImpl (default constructor)")
  void testResume_thenReturnProcessInstanceImpl() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    doNothing().when(runtimeService).activateProcessInstanceById(Mockito.<String>any());
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);

    // Act
    org.activiti.api.process.model.ProcessInstance actualResumeResult = processAdminRuntimeImpl
        .resume(new ResumeProcessPayload());

    // Assert
    verify(runtimeService).activateProcessInstanceById(isNull());
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
    assertSame(processInstanceImpl, actualResumeResult);
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#resume(ResumeProcessPayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#resume(ResumeProcessPayload)}
   */
  @Test
  @DisplayName("Test resume(ResumeProcessPayload); then throw IllegalStateException")
  void testResume_thenThrowIllegalStateException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    doNothing().when(runtimeService).activateProcessInstanceById(Mockito.<String>any());
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> processAdminRuntimeImpl.resume(new ResumeProcessPayload()));
    verify(runtimeService).activateProcessInstanceById(isNull());
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#update(UpdateProcessPayload)}.
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#update(UpdateProcessPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateProcessPayload)")
  void testUpdate() {
    // Arrange
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(runtimeService)
        .updateBusinessKey(Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processAdminRuntimeImpl
        .update(new UpdateProcessPayload("42", "Name", "The characteristics of someone or something", "Business Key")));
    verify(runtimeService).updateBusinessKey(eq("42"), eq("Business Key"));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#update(UpdateProcessPayload)}.
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#update(UpdateProcessPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateProcessPayload)")
  void testUpdate2() {
    // Arrange
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(runtimeService)
        .setProcessInstanceName(Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processAdminRuntimeImpl
        .update(new UpdateProcessPayload("42", "Name", "The characteristics of someone or something", null)));
    verify(runtimeService).setProcessInstanceName(eq("42"), eq("Name"));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#update(UpdateProcessPayload)}.
   * <ul>
   *   <li>Given {@link RuntimeService}
   * {@link RuntimeService#setProcessInstanceName(String, String)} does
   * nothing.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#update(UpdateProcessPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateProcessPayload); given RuntimeService setProcessInstanceName(String, String) does nothing")
  void testUpdate_givenRuntimeServiceSetProcessInstanceNameDoesNothing() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    doNothing().when(runtimeService).setProcessInstanceName(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(runtimeService).updateBusinessKey(Mockito.<String>any(), Mockito.<String>any());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);

    // Act
    org.activiti.api.process.model.ProcessInstance actualUpdateResult = processAdminRuntimeImpl
        .update(new UpdateProcessPayload("42", "Name", "The characteristics of someone or something", "Business Key"));

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    verify(runtimeService).setProcessInstanceName(eq("42"), eq("Name"));
    verify(runtimeService).updateBusinessKey(eq("42"), eq("Business Key"));
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(eq("42"));
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
    assertSame(processInstanceImpl, actualUpdateResult);
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#update(UpdateProcessPayload)}.
   * <ul>
   *   <li>Then return {@link ProcessInstanceImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#update(UpdateProcessPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateProcessPayload); then return ProcessInstanceImpl (default constructor)")
  void testUpdate_thenReturnProcessInstanceImpl() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);

    // Act
    org.activiti.api.process.model.ProcessInstance actualUpdateResult = processAdminRuntimeImpl
        .update(new UpdateProcessPayload());

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
    assertSame(processInstanceImpl, actualUpdateResult);
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#update(UpdateProcessPayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#update(UpdateProcessPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateProcessPayload); then throw IllegalStateException")
  void testUpdate_thenThrowIllegalStateException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> processAdminRuntimeImpl.update(new UpdateProcessPayload()));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
  }

  /**
   * Test
   * {@link ProcessAdminRuntimeImpl#setVariables(SetProcessVariablesPayload)}.
   * <ul>
   *   <li>Then calls {@link RuntimeService#setVariables(String, Map)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#setVariables(SetProcessVariablesPayload)}
   */
  @Test
  @DisplayName("Test setVariables(SetProcessVariablesPayload); then calls setVariables(String, Map)")
  void testSetVariables_thenCallsSetVariables() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    doNothing().when(runtimeService).setVariables(Mockito.<String>any(), Mockito.<Map<String, Object>>any());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(new ProcessInstanceImpl());
    doNothing().when(processVariablesPayloadValidator)
        .checkPayloadVariables(Mockito.<SetProcessVariablesPayload>any(), Mockito.<String>any());

    // Act
    processAdminRuntimeImpl.setVariables(new SetProcessVariablesPayload());

    // Assert that nothing has changed
    verify(runtimeService).createProcessInstanceQuery();
    verify(runtimeService).setVariables(isNull(), isA(Map.class));
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(processVariablesPayloadValidator).checkPayloadVariables(isA(SetProcessVariablesPayload.class), isNull());
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
  }

  /**
   * Test
   * {@link ProcessAdminRuntimeImpl#setVariables(SetProcessVariablesPayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#setVariables(SetProcessVariablesPayload)}
   */
  @Test
  @DisplayName("Test setVariables(SetProcessVariablesPayload); then throw IllegalStateException")
  void testSetVariables_thenThrowIllegalStateException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(new ProcessInstanceImpl());
    doThrow(new IllegalStateException("foo")).when(processVariablesPayloadValidator)
        .checkPayloadVariables(Mockito.<SetProcessVariablesPayload>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> processAdminRuntimeImpl.setVariables(new SetProcessVariablesPayload()));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(processVariablesPayloadValidator).checkPayloadVariables(isA(SetProcessVariablesPayload.class), isNull());
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
  }

  /**
   * Test
   * {@link ProcessAdminRuntimeImpl#setVariables(SetProcessVariablesPayload)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#setVariables(SetProcessVariablesPayload)}
   */
  @Test
  @DisplayName("Test setVariables(SetProcessVariablesPayload); then throw NotFoundException")
  void testSetVariables_thenThrowNotFoundException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(null);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> processAdminRuntimeImpl.setVariables(new SetProcessVariablesPayload()));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#variables(GetVariablesPayload)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#variables(GetVariablesPayload)}
   */
  @Test
  @DisplayName("Test variables(GetVariablesPayload); then return Empty")
  void testVariables_thenReturnEmpty() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.getVariableInstances(Mockito.<String>any())).thenReturn(new HashMap<>());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(new ProcessInstanceImpl());
    when(aPIVariableInstanceConverter
        .from(Mockito.<Collection<org.activiti.engine.impl.persistence.entity.VariableInstance>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<org.activiti.api.model.shared.model.VariableInstance> actualVariablesResult = processAdminRuntimeImpl
        .variables(new GetVariablesPayload());

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    verify(runtimeService).getVariableInstances(isNull());
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
    verify(aPIVariableInstanceConverter).from(isA(Collection.class));
    assertTrue(actualVariablesResult.isEmpty());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#variables(GetVariablesPayload)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#variables(GetVariablesPayload)}
   */
  @Test
  @DisplayName("Test variables(GetVariablesPayload); then throw NotFoundException")
  void testVariables_thenThrowNotFoundException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(null);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> processAdminRuntimeImpl.variables(new GetVariablesPayload()));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test
   * {@link ProcessAdminRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}.
   * <ul>
   *   <li>Then calls
   * {@link RuntimeService#removeVariables(String, Collection)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}
   */
  @Test
  @DisplayName("Test removeVariables(RemoveProcessVariablesPayload); then calls removeVariables(String, Collection)")
  void testRemoveVariables_thenCallsRemoveVariables() {
    // Arrange
    doNothing().when(runtimeService).removeVariables(Mockito.<String>any(), Mockito.<Collection<String>>any());

    // Act
    processAdminRuntimeImpl.removeVariables(new RemoveProcessVariablesPayload());

    // Assert that nothing has changed
    verify(runtimeService).removeVariables(isNull(), isA(Collection.class));
  }

  /**
   * Test
   * {@link ProcessAdminRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}
   */
  @Test
  @DisplayName("Test removeVariables(RemoveProcessVariablesPayload); then throw IllegalStateException")
  void testRemoveVariables_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException("foo")).when(runtimeService)
        .removeVariables(Mockito.<String>any(), Mockito.<Collection<String>>any());

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> processAdminRuntimeImpl.removeVariables(new RemoveProcessVariablesPayload()));
    verify(runtimeService).removeVariables(isNull(), isA(Collection.class));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#receive(ReceiveMessagePayload)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#receive(ReceiveMessagePayload)}
   */
  @Test
  @DisplayName("Test receive(ReceiveMessagePayload); then calls checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)")
  void testReceive_thenCallsCheckReceiveMessagePayloadVariables() {
    // Arrange
    doNothing().when(processVariablesPayloadValidator)
        .checkReceiveMessagePayloadVariables(Mockito.<ReceiveMessagePayload>any(), Mockito.<String>any());

    // Act
    processAdminRuntimeImpl.receive(new ReceiveMessagePayload());

    // Assert
    verify(processVariablesPayloadValidator).checkReceiveMessagePayloadVariables(isA(ReceiveMessagePayload.class),
        isNull());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#receive(ReceiveMessagePayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessAdminRuntimeImpl#receive(ReceiveMessagePayload)}
   */
  @Test
  @DisplayName("Test receive(ReceiveMessagePayload); then throw IllegalStateException")
  void testReceive_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException("foo")).when(processVariablesPayloadValidator)
        .checkReceiveMessagePayloadVariables(Mockito.<ReceiveMessagePayload>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> processAdminRuntimeImpl.receive(new ReceiveMessagePayload()));
    verify(processVariablesPayloadValidator).checkReceiveMessagePayloadVariables(isA(ReceiveMessagePayload.class),
        isNull());
  }
}
