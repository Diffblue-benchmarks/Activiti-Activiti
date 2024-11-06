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
import org.activiti.api.process.model.payloads.CreateProcessInstancePayload;
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
import org.activiti.api.process.runtime.conf.ProcessRuntimeConfiguration;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.api.runtime.shared.NotFoundException;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.runtime.shared.security.SecurityManager;
import org.activiti.core.common.spring.security.policies.ActivitiForbiddenException;
import org.activiti.core.common.spring.security.policies.ProcessSecurityPoliciesManager;
import org.activiti.core.common.spring.security.policies.SecurityPoliciesManager;
import org.activiti.core.common.spring.security.policies.SecurityPolicyAccess;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.ProcessDefinitionQueryImpl;
import org.activiti.engine.impl.ProcessInstanceQueryImpl;
import org.activiti.engine.impl.TaskQueryImpl;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.runtime.api.model.impl.APIDeploymentConverter;
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
@ContextConfiguration(classes = {ProcessRuntimeImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProcessRuntimeImplDiffblueTest {
  @MockBean
  private APIDeploymentConverter aPIDeploymentConverter;

  @MockBean
  private APIProcessDefinitionConverter aPIProcessDefinitionConverter;

  @MockBean
  private APIProcessInstanceConverter aPIProcessInstanceConverter;

  @MockBean
  private APIVariableInstanceConverter aPIVariableInstanceConverter;

  @MockBean
  private ApplicationEventPublisher applicationEventPublisher;

  @MockBean
  private ProcessRuntimeConfiguration processRuntimeConfiguration;

  @Autowired
  private ProcessRuntimeImpl processRuntimeImpl;

  @MockBean
  private ProcessSecurityPoliciesManager processSecurityPoliciesManager;

  @MockBean
  private ProcessVariablesPayloadValidator processVariablesPayloadValidator;

  @MockBean
  private RepositoryService repositoryService;

  @MockBean
  private RuntimeService runtimeService;

  @MockBean
  private SecurityManager securityManager;

  @MockBean
  private TaskService taskService;

  /**
   * Test {@link ProcessRuntimeImpl#processDefinition(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#processDefinition(String)}
   */
  @Test
  @DisplayName("Test processDefinition(String); then throw ActivitiObjectNotFoundException")
  void testProcessDefinition_thenThrowActivitiObjectNotFoundException() throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(new ProcessDefinitionQueryImpl());
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.processDefinition("42"));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload() throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, new GetProcessDefinitionsPayload()));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(eq(SecurityPolicyAccess.READ));
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload2() {
    // Arrange
    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");
    GetProcessDefinitionsPayload getProcessDefinitionsPayload = mock(GetProcessDefinitionsPayload.class);
    doNothing().when(getProcessDefinitionsPayload).setProcessDefinitionKeys(Mockito.<Set<String>>any());
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> processRuntimeImpl.processDefinitions(Pageable.of(1, 3), (GetProcessDefinitionsPayload) null));
    verify(getProcessDefinitionsPayload).setProcessDefinitionKeys(isA(Set.class));
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload, List)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}, {@code include}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload, List)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable, GetProcessDefinitionsPayload, List) with 'pageable', 'getProcessDefinitionsPayload', 'include'")
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayloadInclude() throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);
    GetProcessDefinitionsPayload getProcessDefinitionsPayload2 = new GetProcessDefinitionsPayload();

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, getProcessDefinitionsPayload2, new ArrayList<>()));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(eq(SecurityPolicyAccess.READ));
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload, List)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}, {@code include}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload, List)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable, GetProcessDefinitionsPayload, List) with 'pageable', 'getProcessDefinitionsPayload', 'include'")
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayloadInclude2() {
    // Arrange
    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");
    GetProcessDefinitionsPayload getProcessDefinitionsPayload = mock(GetProcessDefinitionsPayload.class);
    doNothing().when(getProcessDefinitionsPayload).setProcessDefinitionKeys(Mockito.<Set<String>>any());
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, null, new ArrayList<>()));
    verify(getProcessDefinitionsPayload).setProcessDefinitionKeys(isA(Set.class));
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, List)} with
   * {@code pageable}, {@code include}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#processDefinitions(Pageable, List)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable, List) with 'pageable', 'include'")
  void testProcessDefinitionsWithPageableInclude() throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, new ArrayList<>()));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(eq(SecurityPolicyAccess.READ));
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable)} with
   * {@code pageable}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable) with 'pageable'; then throw ActivitiObjectNotFoundException")
  void testProcessDefinitionsWithPageable_thenThrowActivitiObjectNotFoundException() throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.processDefinitions(Pageable.of(1, 3)));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(eq(SecurityPolicyAccess.READ));
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable)} with
   * {@code pageable}.
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable) with 'pageable'")
  void testProcessInstancesWithPageable() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery()).thenReturn(new ProcessInstanceQueryImpl());

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(securityManager.getAuthenticatedUserId()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.processInstances(Pageable.of(1, 3)));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(eq(SecurityPolicyAccess.READ));
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable)} with
   * {@code pageable}.
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable) with 'pageable'")
  void testProcessInstancesWithPageable2() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(processInstanceQuery.involvedUser(Mockito.<String>any())).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    HashSet<String> stringSet = new HashSet<>();
    stringSet.add("foo");
    GetProcessInstancesPayload getProcessInstancesPayload = mock(GetProcessInstancesPayload.class);
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
    when(processSecurityPoliciesManager.restrictProcessInstQuery(Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.processInstances(Pageable.of(1, 3)));
    verify(getProcessInstancesPayload, atLeast(1)).getProcessDefinitionKeys();
    verify(getProcessInstancesPayload).setActiveOnly(eq(true));
    verify(getProcessInstancesPayload).setBusinessKey(eq("Business Key"));
    verify(getProcessInstancesPayload).setParentProcessInstanceId(eq("42"));
    verify(getProcessInstancesPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(getProcessInstancesPayload).setSuspendedOnly(eq(true));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(eq(SecurityPolicyAccess.READ));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).involvedUser(eq("42"));
    verify(processInstanceQuery).processDefinitionKeys(isA(Set.class));
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  void testProcessInstancesWithPageableGetProcessInstancesPayload() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery()).thenReturn(new ProcessInstanceQueryImpl());

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(securityManager.getAuthenticatedUserId()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload2 = new GetProcessInstancesPayload();
    getProcessInstancesPayload2.setActiveOnly(true);
    getProcessInstancesPayload2.setBusinessKey("Business Key");
    getProcessInstancesPayload2.setParentProcessInstanceId("42");
    getProcessInstancesPayload2.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload2.setSuspendedOnly(true);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processInstances(pageable, getProcessInstancesPayload2));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(eq(SecurityPolicyAccess.READ));
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  void testProcessInstancesWithPageableGetProcessInstancesPayload2() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.involvedUser(Mockito.<String>any())).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any())).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<org.activiti.engine.runtime.ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload2 = new GetProcessInstancesPayload();
    getProcessInstancesPayload2.setActiveOnly(true);
    getProcessInstancesPayload2.setBusinessKey("Business Key");
    getProcessInstancesPayload2.setParentProcessInstanceId("42");
    getProcessInstancesPayload2.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload2.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult = processRuntimeImpl
        .processInstances(pageable, getProcessInstancesPayload2);

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(eq(SecurityPolicyAccess.READ));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(eq(1), eq(3));
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).involvedUser(eq("42"));
    verify(processInstanceQuery).processInstanceBusinessKey(eq("Business Key"));
    verify(processInstanceQuery).superProcessInstanceId(eq("42"));
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
    assertTrue(getProcessInstancesPayload2.getProcessDefinitionKeys().isEmpty());
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  void testProcessInstancesWithPageableGetProcessInstancesPayload3() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.involvedUser(Mockito.<String>any())).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any())).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    GetProcessInstancesPayload getProcessInstancesPayload = mock(GetProcessInstancesPayload.class);
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
    when(processSecurityPoliciesManager.restrictProcessInstQuery(Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<org.activiti.engine.runtime.ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload2 = new GetProcessInstancesPayload();
    getProcessInstancesPayload2.setActiveOnly(true);
    getProcessInstancesPayload2.setBusinessKey("Business Key");
    getProcessInstancesPayload2.setParentProcessInstanceId("42");
    getProcessInstancesPayload2.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload2.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult = processRuntimeImpl
        .processInstances(pageable, getProcessInstancesPayload2);

    // Assert
    verify(getProcessInstancesPayload).getProcessDefinitionKeys();
    verify(getProcessInstancesPayload).setActiveOnly(eq(true));
    verify(getProcessInstancesPayload).setBusinessKey(eq("Business Key"));
    verify(getProcessInstancesPayload).setParentProcessInstanceId(eq("42"));
    verify(getProcessInstancesPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(getProcessInstancesPayload).setSuspendedOnly(eq(true));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(eq(SecurityPolicyAccess.READ));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(eq(1), eq(3));
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).involvedUser(eq("42"));
    verify(processInstanceQuery).processInstanceBusinessKey(eq("Business Key"));
    verify(processInstanceQuery).superProcessInstanceId(eq("42"));
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
    assertTrue(getProcessInstancesPayload2.getProcessDefinitionKeys().isEmpty());
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
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
    when(processInstanceQuery.involvedUser(Mockito.<String>any())).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any())).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    HashSet<String> stringSet = new HashSet<>();
    stringSet.add("foo");
    GetProcessInstancesPayload getProcessInstancesPayload = mock(GetProcessInstancesPayload.class);
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
    when(processSecurityPoliciesManager.restrictProcessInstQuery(Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<org.activiti.engine.runtime.ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload2 = new GetProcessInstancesPayload();
    getProcessInstancesPayload2.setActiveOnly(true);
    getProcessInstancesPayload2.setBusinessKey("Business Key");
    getProcessInstancesPayload2.setParentProcessInstanceId("42");
    getProcessInstancesPayload2.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload2.setSuspendedOnly(true);

    // Act
    processRuntimeImpl.processInstances(pageable, getProcessInstancesPayload2);

    // Assert
    verify(getProcessInstancesPayload, atLeast(1)).getProcessDefinitionKeys();
    verify(getProcessInstancesPayload).setActiveOnly(eq(true));
    verify(getProcessInstancesPayload).setBusinessKey(eq("Business Key"));
    verify(getProcessInstancesPayload).setParentProcessInstanceId(eq("42"));
    verify(getProcessInstancesPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(getProcessInstancesPayload).setSuspendedOnly(eq(true));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(eq(SecurityPolicyAccess.READ));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(eq(1), eq(3));
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).involvedUser(eq("42"));
    verify(processInstanceQuery).processDefinitionKeys(isA(Set.class));
    verify(processInstanceQuery).processInstanceBusinessKey(eq("Business Key"));
    verify(processInstanceQuery).superProcessInstanceId(eq("42"));
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    Set<String> processDefinitionKeys = getProcessInstancesPayload2.getProcessDefinitionKeys();
    assertEquals(1, processDefinitionKeys.size());
    assertTrue(processDefinitionKeys.contains("foo"));
    assertSame(stringSet, processDefinitionKeys);
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  void testProcessInstancesWithPageableGetProcessInstancesPayload5() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.involvedUser(Mockito.<String>any())).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any())).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    HashSet<String> stringSet = new HashSet<>();
    stringSet.add("foo");
    GetProcessInstancesPayload getProcessInstancesPayload = mock(GetProcessInstancesPayload.class);
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
    when(processSecurityPoliciesManager.restrictProcessInstQuery(Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<org.activiti.engine.runtime.ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);
    GetProcessInstancesPayload getProcessInstancesPayload2 = mock(GetProcessInstancesPayload.class);
    when(getProcessInstancesPayload2.isActiveOnly()).thenReturn(true);
    when(getProcessInstancesPayload2.isSuspendedOnly()).thenReturn(true);
    when(getProcessInstancesPayload2.getBusinessKey()).thenReturn("Business Key");
    when(getProcessInstancesPayload2.getParentProcessInstanceId()).thenReturn("42");
    when(getProcessInstancesPayload2.getProcessDefinitionKeys()).thenReturn(new HashSet<>());
    doNothing().when(getProcessInstancesPayload2).setActiveOnly(anyBoolean());
    doNothing().when(getProcessInstancesPayload2).setBusinessKey(Mockito.<String>any());
    doNothing().when(getProcessInstancesPayload2).setParentProcessInstanceId(Mockito.<String>any());
    doNothing().when(getProcessInstancesPayload2).setProcessDefinitionKeys(Mockito.<Set<String>>any());
    doNothing().when(getProcessInstancesPayload2).setSuspendedOnly(anyBoolean());
    getProcessInstancesPayload2.setActiveOnly(true);
    getProcessInstancesPayload2.setBusinessKey("Business Key");
    getProcessInstancesPayload2.setParentProcessInstanceId("42");
    getProcessInstancesPayload2.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload2.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult = processRuntimeImpl
        .processInstances(pageable, getProcessInstancesPayload2);

    // Assert
    verify(getProcessInstancesPayload2, atLeast(1)).getBusinessKey();
    verify(getProcessInstancesPayload2, atLeast(1)).getParentProcessInstanceId();
    verify(getProcessInstancesPayload, atLeast(1)).getProcessDefinitionKeys();
    verify(getProcessInstancesPayload2, atLeast(1)).getProcessDefinitionKeys();
    verify(getProcessInstancesPayload2).isActiveOnly();
    verify(getProcessInstancesPayload2).isSuspendedOnly();
    verify(getProcessInstancesPayload).setActiveOnly(eq(true));
    verify(getProcessInstancesPayload2).setActiveOnly(eq(true));
    verify(getProcessInstancesPayload).setBusinessKey(eq("Business Key"));
    verify(getProcessInstancesPayload2).setBusinessKey(eq("Business Key"));
    verify(getProcessInstancesPayload).setParentProcessInstanceId(eq("42"));
    verify(getProcessInstancesPayload2).setParentProcessInstanceId(eq("42"));
    verify(getProcessInstancesPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(getProcessInstancesPayload2, atLeast(1)).setProcessDefinitionKeys(Mockito.<Set<String>>any());
    verify(getProcessInstancesPayload).setSuspendedOnly(eq(true));
    verify(getProcessInstancesPayload2).setSuspendedOnly(eq(true));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(eq(SecurityPolicyAccess.READ));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(eq(1), eq(3));
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).involvedUser(eq("42"));
    verify(processInstanceQuery).processInstanceBusinessKey(eq("Business Key"));
    verify(processInstanceQuery).superProcessInstanceId(eq("42"));
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable)} with
   * {@code pageable}.
   * <ul>
   *   <li>Then calls {@link ProcessInstanceQuery#processDefinitionKeys(Set)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable) with 'pageable'; then calls processDefinitionKeys(Set)")
  void testProcessInstancesWithPageable_thenCallsProcessDefinitionKeys() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.involvedUser(Mockito.<String>any())).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    HashSet<String> stringSet = new HashSet<>();
    stringSet.add("foo");
    GetProcessInstancesPayload getProcessInstancesPayload = mock(GetProcessInstancesPayload.class);
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
    when(processSecurityPoliciesManager.restrictProcessInstQuery(Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<org.activiti.engine.runtime.ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult = processRuntimeImpl
        .processInstances(Pageable.of(1, 3));

    // Assert
    verify(getProcessInstancesPayload, atLeast(1)).getProcessDefinitionKeys();
    verify(getProcessInstancesPayload).setActiveOnly(eq(true));
    verify(getProcessInstancesPayload).setBusinessKey(eq("Business Key"));
    verify(getProcessInstancesPayload).setParentProcessInstanceId(eq("42"));
    verify(getProcessInstancesPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(getProcessInstancesPayload).setSuspendedOnly(eq(true));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(eq(SecurityPolicyAccess.READ));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(eq(1), eq(3));
    verify(processInstanceQuery).involvedUser(eq("42"));
    verify(processInstanceQuery).processDefinitionKeys(isA(Set.class));
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable)} with
   * {@code pageable}.
   * <ul>
   *   <li>Then return {@link PageImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable) with 'pageable'; then return PageImpl")
  void testProcessInstancesWithPageable_thenReturnPageImpl() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.involvedUser(Mockito.<String>any())).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<org.activiti.engine.runtime.ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult = processRuntimeImpl
        .processInstances(Pageable.of(1, 3));

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(eq(SecurityPolicyAccess.READ));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(eq(1), eq(3));
    verify(processInstanceQuery).involvedUser(eq("42"));
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable)} with
   * {@code pageable}.
   * <ul>
   *   <li>Then return {@link PageImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable) with 'pageable'; then return PageImpl")
  void testProcessInstancesWithPageable_thenReturnPageImpl2() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.involvedUser(Mockito.<String>any())).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    GetProcessInstancesPayload getProcessInstancesPayload = mock(GetProcessInstancesPayload.class);
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
    when(processSecurityPoliciesManager.restrictProcessInstQuery(Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<org.activiti.engine.runtime.ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult = processRuntimeImpl
        .processInstances(Pageable.of(1, 3));

    // Assert
    verify(getProcessInstancesPayload).getProcessDefinitionKeys();
    verify(getProcessInstancesPayload).setActiveOnly(eq(true));
    verify(getProcessInstancesPayload).setBusinessKey(eq("Business Key"));
    verify(getProcessInstancesPayload).setParentProcessInstanceId(eq("42"));
    verify(getProcessInstancesPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(getProcessInstancesPayload).setSuspendedOnly(eq(true));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(eq(SecurityPolicyAccess.READ));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(eq(1), eq(3));
    verify(processInstanceQuery).involvedUser(eq("42"));
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test {@link ProcessRuntimeImpl#start(StartMessagePayload)} with
   * {@code messagePayload}.
   * <ul>
   *   <li>Then return {@link ProcessInstanceImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#start(StartMessagePayload)}
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
    org.activiti.api.process.model.ProcessInstance actualStartResult = processRuntimeImpl
        .start(new StartMessagePayload());

    // Assert
    verify(runtimeService).startProcessInstanceByMessage(isNull(), isNull(), isA(Map.class));
    verify(processVariablesPayloadValidator).checkStartMessagePayloadVariables(isA(StartMessagePayload.class),
        isNull());
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
    assertSame(processInstanceImpl, actualStartResult);
  }

  /**
   * Test {@link ProcessRuntimeImpl#start(StartMessagePayload)} with
   * {@code messagePayload}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#start(StartMessagePayload)}
   */
  @Test
  @DisplayName("Test start(StartMessagePayload) with 'messagePayload'; then throw ActivitiObjectNotFoundException")
  void testStartWithMessagePayload_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(processVariablesPayloadValidator)
        .checkStartMessagePayloadVariables(Mockito.<StartMessagePayload>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.start(new StartMessagePayload()));
    verify(processVariablesPayloadValidator).checkStartMessagePayloadVariables(isA(StartMessagePayload.class),
        isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#start(StartProcessPayload)} with
   * {@code startProcessPayload}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#start(StartProcessPayload)}
   */
  @Test
  @DisplayName("Test start(StartProcessPayload) with 'startProcessPayload'; then throw ActivitiObjectNotFoundException")
  void testStartWithStartProcessPayload_thenThrowActivitiObjectNotFoundException() throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(new ProcessDefinitionQueryImpl());
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.start(new StartProcessPayload()));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#startCreatedProcess(String, StartProcessPayload)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#startCreatedProcess(String, StartProcessPayload)}
   */
  @Test
  @DisplayName("Test startCreatedProcess(String, StartProcessPayload)")
  void testStartCreatedProcess() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl(
        new CommandExecutorImpl(new CommandConfig(), first));
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserId()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.startCreatedProcess("42", new StartProcessPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(isNull());
    verify(runtimeService).createProcessInstanceQuery();
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#startCreatedProcess(String, StartProcessPayload)}.
   * <ul>
   *   <li>Given {@link SecurityManager}
   * {@link SecurityManager#getAuthenticatedUserId()} return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#startCreatedProcess(String, StartProcessPayload)}
   */
  @Test
  @DisplayName("Test startCreatedProcess(String, StartProcessPayload); given SecurityManager getAuthenticatedUserId() return '42'")
  void testStartCreatedProcess_givenSecurityManagerGetAuthenticatedUserIdReturn42() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl(
        new CommandExecutorImpl(new CommandConfig(), first));
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.startCreatedProcess("42", new StartProcessPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(isNull());
    verify(runtimeService).createProcessInstanceQuery();
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#startCreatedProcess(String, StartProcessPayload)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#startCreatedProcess(String, StartProcessPayload)}
   */
  @Test
  @DisplayName("Test startCreatedProcess(String, StartProcessPayload); then throw NotFoundException")
  void testStartCreatedProcess_thenThrowNotFoundException() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(null);
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl(
        new CommandExecutorImpl(new CommandConfig(), first));
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl);

    // Act and Assert
    assertThrows(NotFoundException.class,
        () -> processRuntimeImpl.startCreatedProcess("42", new StartProcessPayload()));
    verify(runtimeService).createProcessInstanceQuery();
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test {@link ProcessRuntimeImpl#create(CreateProcessInstancePayload)}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#create(CreateProcessInstancePayload)}
   */
  @Test
  @DisplayName("Test create(CreateProcessInstancePayload); then throw ActivitiObjectNotFoundException")
  void testCreate_thenThrowActivitiObjectNotFoundException() throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(new ProcessDefinitionQueryImpl());
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.create(new CreateProcessInstancePayload()));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#suspend(SuspendProcessPayload)}.
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#suspend(SuspendProcessPayload)}
   */
  @Test
  @DisplayName("Test suspend(SuspendProcessPayload)")
  void testSuspend() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserId()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.suspend(new SuspendProcessPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(isNull());
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#suspend(SuspendProcessPayload)}.
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#suspend(SuspendProcessPayload)}
   */
  @Test
  @DisplayName("Test suspend(SuspendProcessPayload)")
  void testSuspend2() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getStartUserId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    doNothing().when(runtimeService).suspendProcessInstanceById(Mockito.<String>any());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.suspend(new SuspendProcessPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService, atLeast(1)).createProcessInstanceQuery();
    verify(runtimeService).suspendProcessInstanceById(isNull());
    verify(processInstanceQueryImpl, atLeast(1)).singleResult();
    verify(processInstanceQueryImpl2, atLeast(1)).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getStartUserId();
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
  }

  /**
   * Test {@link ProcessRuntimeImpl#suspend(SuspendProcessPayload)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getProcessInstanceId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#suspend(SuspendProcessPayload)}
   */
  @Test
  @DisplayName("Test suspend(SuspendProcessPayload); then calls getProcessInstanceId()")
  void testSuspend_thenCallsGetProcessInstanceId() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(false);

    // Act and Assert
    assertThrows(ActivitiForbiddenException.class, () -> processRuntimeImpl.suspend(new SuspendProcessPayload()));
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getProcessInstanceId();
  }

  /**
   * Test {@link ProcessRuntimeImpl#suspend(SuspendProcessPayload)}.
   * <ul>
   *   <li>Then return {@link ProcessInstanceImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#suspend(SuspendProcessPayload)}
   */
  @Test
  @DisplayName("Test suspend(SuspendProcessPayload); then return ProcessInstanceImpl (default constructor)")
  void testSuspend_thenReturnProcessInstanceImpl() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getStartUserId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    doNothing().when(runtimeService).suspendProcessInstanceById(Mockito.<String>any());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act
    org.activiti.api.process.model.ProcessInstance actualSuspendResult = processRuntimeImpl
        .suspend(new SuspendProcessPayload());

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService, atLeast(1)).createProcessInstanceQuery();
    verify(runtimeService).suspendProcessInstanceById(isNull());
    verify(processInstanceQueryImpl, atLeast(1)).singleResult();
    verify(processInstanceQueryImpl2, atLeast(1)).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getStartUserId();
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
    assertSame(processInstanceImpl, actualSuspendResult);
  }

  /**
   * Test {@link ProcessRuntimeImpl#suspend(SuspendProcessPayload)}.
   * <ul>
   *   <li>Then throw {@link ActivitiForbiddenException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#suspend(SuspendProcessPayload)}
   */
  @Test
  @DisplayName("Test suspend(SuspendProcessPayload); then throw ActivitiForbiddenException")
  void testSuspend_thenThrowActivitiForbiddenException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiForbiddenException.class, () -> processRuntimeImpl.suspend(new SuspendProcessPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(isNull());
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#suspend(SuspendProcessPayload)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#suspend(SuspendProcessPayload)}
   */
  @Test
  @DisplayName("Test suspend(SuspendProcessPayload); then throw NotFoundException")
  void testSuspend_thenThrowNotFoundException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(null);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> processRuntimeImpl.suspend(new SuspendProcessPayload()));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#resume(ResumeProcessPayload)}.
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#resume(ResumeProcessPayload)}
   */
  @Test
  @DisplayName("Test resume(ResumeProcessPayload)")
  void testResume() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserId()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.resume(new ResumeProcessPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(isNull());
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#resume(ResumeProcessPayload)}.
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#resume(ResumeProcessPayload)}
   */
  @Test
  @DisplayName("Test resume(ResumeProcessPayload)")
  void testResume2() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getStartUserId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    doNothing().when(runtimeService).activateProcessInstanceById(Mockito.<String>any());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.resume(new ResumeProcessPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService).activateProcessInstanceById(isNull());
    verify(runtimeService, atLeast(1)).createProcessInstanceQuery();
    verify(processInstanceQueryImpl, atLeast(1)).singleResult();
    verify(processInstanceQueryImpl2, atLeast(1)).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getStartUserId();
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
  }

  /**
   * Test {@link ProcessRuntimeImpl#resume(ResumeProcessPayload)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getProcessInstanceId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#resume(ResumeProcessPayload)}
   */
  @Test
  @DisplayName("Test resume(ResumeProcessPayload); then calls getProcessInstanceId()")
  void testResume_thenCallsGetProcessInstanceId() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(false);

    // Act and Assert
    assertThrows(ActivitiForbiddenException.class, () -> processRuntimeImpl.resume(new ResumeProcessPayload()));
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getProcessInstanceId();
  }

  /**
   * Test {@link ProcessRuntimeImpl#resume(ResumeProcessPayload)}.
   * <ul>
   *   <li>Then return {@link ProcessInstanceImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#resume(ResumeProcessPayload)}
   */
  @Test
  @DisplayName("Test resume(ResumeProcessPayload); then return ProcessInstanceImpl (default constructor)")
  void testResume_thenReturnProcessInstanceImpl() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getStartUserId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    doNothing().when(runtimeService).activateProcessInstanceById(Mockito.<String>any());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act
    org.activiti.api.process.model.ProcessInstance actualResumeResult = processRuntimeImpl
        .resume(new ResumeProcessPayload());

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService).activateProcessInstanceById(isNull());
    verify(runtimeService, atLeast(1)).createProcessInstanceQuery();
    verify(processInstanceQueryImpl, atLeast(1)).singleResult();
    verify(processInstanceQueryImpl2, atLeast(1)).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getStartUserId();
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
    assertSame(processInstanceImpl, actualResumeResult);
  }

  /**
   * Test {@link ProcessRuntimeImpl#resume(ResumeProcessPayload)}.
   * <ul>
   *   <li>Then throw {@link ActivitiForbiddenException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#resume(ResumeProcessPayload)}
   */
  @Test
  @DisplayName("Test resume(ResumeProcessPayload); then throw ActivitiForbiddenException")
  void testResume_thenThrowActivitiForbiddenException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiForbiddenException.class, () -> processRuntimeImpl.resume(new ResumeProcessPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(isNull());
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#resume(ResumeProcessPayload)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#resume(ResumeProcessPayload)}
   */
  @Test
  @DisplayName("Test resume(ResumeProcessPayload); then throw NotFoundException")
  void testResume_thenThrowNotFoundException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(null);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> processRuntimeImpl.resume(new ResumeProcessPayload()));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#delete(DeleteProcessPayload)}.
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#delete(DeleteProcessPayload)}
   */
  @Test
  @DisplayName("Test delete(DeleteProcessPayload)")
  void testDelete() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserId()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.delete(new DeleteProcessPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(isNull());
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#delete(DeleteProcessPayload)}.
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#delete(DeleteProcessPayload)}
   */
  @Test
  @DisplayName("Test delete(DeleteProcessPayload)")
  void testDelete2() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getStartUserId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    doNothing().when(runtimeService).deleteProcessInstance(Mockito.<String>any(), Mockito.<String>any());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.delete(new DeleteProcessPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService).createProcessInstanceQuery();
    verify(runtimeService).deleteProcessInstance(isNull(), isNull());
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getStartUserId();
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
  }

  /**
   * Test {@link ProcessRuntimeImpl#delete(DeleteProcessPayload)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getProcessInstanceId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#delete(DeleteProcessPayload)}
   */
  @Test
  @DisplayName("Test delete(DeleteProcessPayload); then calls getProcessInstanceId()")
  void testDelete_thenCallsGetProcessInstanceId() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(false);

    // Act and Assert
    assertThrows(ActivitiForbiddenException.class, () -> processRuntimeImpl.delete(new DeleteProcessPayload()));
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getProcessInstanceId();
  }

  /**
   * Test {@link ProcessRuntimeImpl#delete(DeleteProcessPayload)}.
   * <ul>
   *   <li>Then return {@link ProcessInstanceImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#delete(DeleteProcessPayload)}
   */
  @Test
  @DisplayName("Test delete(DeleteProcessPayload); then return ProcessInstanceImpl (default constructor)")
  void testDelete_thenReturnProcessInstanceImpl() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getStartUserId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    doNothing().when(runtimeService).deleteProcessInstance(Mockito.<String>any(), Mockito.<String>any());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act
    org.activiti.api.process.model.ProcessInstance actualDeleteResult = processRuntimeImpl
        .delete(new DeleteProcessPayload());

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService).createProcessInstanceQuery();
    verify(runtimeService).deleteProcessInstance(isNull(), isNull());
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getStartUserId();
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
    assertSame(processInstanceImpl, actualDeleteResult);
  }

  /**
   * Test {@link ProcessRuntimeImpl#delete(DeleteProcessPayload)}.
   * <ul>
   *   <li>Then throw {@link ActivitiForbiddenException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#delete(DeleteProcessPayload)}
   */
  @Test
  @DisplayName("Test delete(DeleteProcessPayload); then throw ActivitiForbiddenException")
  void testDelete_thenThrowActivitiForbiddenException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiForbiddenException.class, () -> processRuntimeImpl.delete(new DeleteProcessPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(isNull());
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#delete(DeleteProcessPayload)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#delete(DeleteProcessPayload)}
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
    assertThrows(NotFoundException.class, () -> processRuntimeImpl.delete(new DeleteProcessPayload()));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#variables(GetVariablesPayload)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl}
   * {@link ExecutionEntityImpl#getStartUserId()} return {@code 42}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#variables(GetVariablesPayload)}
   */
  @Test
  @DisplayName("Test variables(GetVariablesPayload); given ExecutionEntityImpl getStartUserId() return '42'; then return Empty")
  void testVariables_givenExecutionEntityImplGetStartUserIdReturn42_thenReturnEmpty() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getStartUserId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.getVariableInstances(Mockito.<String>any())).thenReturn(new HashMap<>());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canRead(Mockito.<String>any())).thenReturn(true);
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(new ProcessInstanceImpl());
    when(aPIVariableInstanceConverter
        .from(Mockito.<Collection<org.activiti.engine.impl.persistence.entity.VariableInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act
    List<org.activiti.api.model.shared.model.VariableInstance> actualVariablesResult = processRuntimeImpl
        .variables(new GetVariablesPayload());

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canRead(eq("Process Definition Key"));
    verify(runtimeService).createProcessInstanceQuery();
    verify(runtimeService).getVariableInstances(isNull());
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getStartUserId();
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
    verify(aPIVariableInstanceConverter).from(isA(Collection.class));
    assertTrue(actualVariablesResult.isEmpty());
  }

  /**
   * Test {@link ProcessRuntimeImpl#variables(GetVariablesPayload)}.
   * <ul>
   *   <li>Given {@link ProcessSecurityPoliciesManager}
   * {@link SecurityPoliciesManager#canRead(String)} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#variables(GetVariablesPayload)}
   */
  @Test
  @DisplayName("Test variables(GetVariablesPayload); given ProcessSecurityPoliciesManager canRead(String) return 'false'")
  void testVariables_givenProcessSecurityPoliciesManagerCanReadReturnFalse() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canRead(Mockito.<String>any())).thenReturn(false);

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.variables(new GetVariablesPayload()));
    verify(processSecurityPoliciesManager).canRead(eq("Process Definition Key"));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionKey();
  }

  /**
   * Test {@link ProcessRuntimeImpl#variables(GetVariablesPayload)}.
   * <ul>
   *   <li>Then calls {@link SecurityManager#getAuthenticatedUserGroups()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#variables(GetVariablesPayload)}
   */
  @Test
  @DisplayName("Test variables(GetVariablesPayload); then calls getAuthenticatedUserGroups()")
  void testVariables_thenCallsGetAuthenticatedUserGroups() throws SecurityException {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(taskService.createTaskQuery()).thenReturn(new TaskQueryImpl());
    when(processSecurityPoliciesManager.canRead(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.variables(new GetVariablesPayload()));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager, atLeast(1)).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canRead(isNull());
    verify(runtimeService).createProcessInstanceQuery();
    verify(taskService).createTaskQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#variables(GetVariablesPayload)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#variables(GetVariablesPayload)}
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
    assertThrows(NotFoundException.class, () -> processRuntimeImpl.variables(new GetVariablesPayload()));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}
   */
  @Test
  @DisplayName("Test removeVariables(RemoveProcessVariablesPayload)")
  void testRemoveVariables() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserId()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.removeVariables(new RemoveProcessVariablesPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(isNull());
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}
   */
  @Test
  @DisplayName("Test removeVariables(RemoveProcessVariablesPayload)")
  void testRemoveVariables2() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getStartUserId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(runtimeService)
        .removeVariables(Mockito.<String>any(), Mockito.<Collection<String>>any());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.removeVariables(new RemoveProcessVariablesPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService).createProcessInstanceQuery();
    verify(runtimeService).removeVariables(isNull(), isA(Collection.class));
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getStartUserId();
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getProcessInstanceId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}
   */
  @Test
  @DisplayName("Test removeVariables(RemoveProcessVariablesPayload); then calls getProcessInstanceId()")
  void testRemoveVariables_thenCallsGetProcessInstanceId() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(false);

    // Act and Assert
    assertThrows(ActivitiForbiddenException.class,
        () -> processRuntimeImpl.removeVariables(new RemoveProcessVariablesPayload()));
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getProcessInstanceId();
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}.
   * <ul>
   *   <li>Then calls
   * {@link RuntimeService#removeVariables(String, Collection)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}
   */
  @Test
  @DisplayName("Test removeVariables(RemoveProcessVariablesPayload); then calls removeVariables(String, Collection)")
  void testRemoveVariables_thenCallsRemoveVariables() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getStartUserId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    doNothing().when(runtimeService).removeVariables(Mockito.<String>any(), Mockito.<Collection<String>>any());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act
    processRuntimeImpl.removeVariables(new RemoveProcessVariablesPayload());

    // Assert that nothing has changed
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService).createProcessInstanceQuery();
    verify(runtimeService).removeVariables(isNull(), isA(Collection.class));
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getStartUserId();
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}.
   * <ul>
   *   <li>Then throw {@link ActivitiForbiddenException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}
   */
  @Test
  @DisplayName("Test removeVariables(RemoveProcessVariablesPayload); then throw ActivitiForbiddenException")
  void testRemoveVariables_thenThrowActivitiForbiddenException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiForbiddenException.class,
        () -> processRuntimeImpl.removeVariables(new RemoveProcessVariablesPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(isNull());
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}
   */
  @Test
  @DisplayName("Test removeVariables(RemoveProcessVariablesPayload); then throw NotFoundException")
  void testRemoveVariables_thenThrowNotFoundException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(null);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);

    // Act and Assert
    assertThrows(NotFoundException.class,
        () -> processRuntimeImpl.removeVariables(new RemoveProcessVariablesPayload()));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#setVariables(SetProcessVariablesPayload)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#setVariables(SetProcessVariablesPayload)}
   */
  @Test
  @DisplayName("Test setVariables(SetProcessVariablesPayload)")
  void testSetVariables() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserId()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.setVariables(new SetProcessVariablesPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(isNull());
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#setVariables(SetProcessVariablesPayload)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#setVariables(SetProcessVariablesPayload)}
   */
  @Test
  @DisplayName("Test setVariables(SetProcessVariablesPayload)")
  void testSetVariables2() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getStartUserId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(processVariablesPayloadValidator)
        .checkPayloadVariables(Mockito.<SetProcessVariablesPayload>any(), Mockito.<String>any());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.setVariables(new SetProcessVariablesPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getStartUserId();
    verify(processVariablesPayloadValidator).checkPayloadVariables(isA(SetProcessVariablesPayload.class), eq("42"));
  }

  /**
   * Test {@link ProcessRuntimeImpl#setVariables(SetProcessVariablesPayload)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getProcessInstanceId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#setVariables(SetProcessVariablesPayload)}
   */
  @Test
  @DisplayName("Test setVariables(SetProcessVariablesPayload); then calls getProcessInstanceId()")
  void testSetVariables_thenCallsGetProcessInstanceId() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(false);

    // Act and Assert
    assertThrows(ActivitiForbiddenException.class,
        () -> processRuntimeImpl.setVariables(new SetProcessVariablesPayload()));
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getProcessInstanceId();
  }

  /**
   * Test {@link ProcessRuntimeImpl#setVariables(SetProcessVariablesPayload)}.
   * <ul>
   *   <li>Then calls {@link RuntimeService#setVariables(String, Map)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#setVariables(SetProcessVariablesPayload)}
   */
  @Test
  @DisplayName("Test setVariables(SetProcessVariablesPayload); then calls setVariables(String, Map)")
  void testSetVariables_thenCallsSetVariables() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getStartUserId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    doNothing().when(runtimeService).setVariables(Mockito.<String>any(), Mockito.<Map<String, Object>>any());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    doNothing().when(processVariablesPayloadValidator)
        .checkPayloadVariables(Mockito.<SetProcessVariablesPayload>any(), Mockito.<String>any());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act
    processRuntimeImpl.setVariables(new SetProcessVariablesPayload());

    // Assert that nothing has changed
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService).createProcessInstanceQuery();
    verify(runtimeService).setVariables(isNull(), isA(Map.class));
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getStartUserId();
    verify(processVariablesPayloadValidator).checkPayloadVariables(isA(SetProcessVariablesPayload.class), eq("42"));
  }

  /**
   * Test {@link ProcessRuntimeImpl#setVariables(SetProcessVariablesPayload)}.
   * <ul>
   *   <li>Then throw {@link ActivitiForbiddenException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#setVariables(SetProcessVariablesPayload)}
   */
  @Test
  @DisplayName("Test setVariables(SetProcessVariablesPayload); then throw ActivitiForbiddenException")
  void testSetVariables_thenThrowActivitiForbiddenException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiForbiddenException.class,
        () -> processRuntimeImpl.setVariables(new SetProcessVariablesPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(isNull());
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#setVariables(SetProcessVariablesPayload)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#setVariables(SetProcessVariablesPayload)}
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
    assertThrows(NotFoundException.class, () -> processRuntimeImpl.setVariables(new SetProcessVariablesPayload()));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#signal(SignalPayload)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload, String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#signal(SignalPayload)}
   */
  @Test
  @DisplayName("Test signal(SignalPayload); then calls checkSignalPayloadVariables(SignalPayload, String)")
  void testSignal_thenCallsCheckSignalPayloadVariables() {
    // Arrange
    doNothing().when(processVariablesPayloadValidator)
        .checkSignalPayloadVariables(Mockito.<SignalPayload>any(), Mockito.<String>any());

    // Act
    processRuntimeImpl.signal(new SignalPayload());

    // Assert
    verify(processVariablesPayloadValidator).checkSignalPayloadVariables(isA(SignalPayload.class), isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#signal(SignalPayload)}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#signal(SignalPayload)}
   */
  @Test
  @DisplayName("Test signal(SignalPayload); then throw ActivitiObjectNotFoundException")
  void testSignal_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(processVariablesPayloadValidator)
        .checkSignalPayloadVariables(Mockito.<SignalPayload>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.signal(new SignalPayload()));
    verify(processVariablesPayloadValidator).checkSignalPayloadVariables(isA(SignalPayload.class), isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitionMeta(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#processDefinitionMeta(String)}
   */
  @Test
  @DisplayName("Test processDefinitionMeta(String); then throw ActivitiObjectNotFoundException")
  void testProcessDefinitionMeta_thenThrowActivitiObjectNotFoundException() throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(new ProcessDefinitionQueryImpl());
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitionMeta("Process Definition Key"));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}.
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateProcessPayload)")
  void testUpdate() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiForbiddenException.class, () -> processRuntimeImpl.update(new UpdateProcessPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(isNull());
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}.
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateProcessPayload)")
  void testUpdate2() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserId()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.update(new UpdateProcessPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(isNull());
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}.
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateProcessPayload)")
  void testUpdate3() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getStartUserId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.update(new UpdateProcessPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService, atLeast(1)).createProcessInstanceQuery();
    verify(processInstanceQueryImpl, atLeast(1)).singleResult();
    verify(processInstanceQueryImpl2, atLeast(1)).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getStartUserId();
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
  }

  /**
   * Test {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}.
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateProcessPayload)")
  void testUpdate4() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getStartUserId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    doThrow(new ActivitiForbiddenException("An error occurred")).when(runtimeService)
        .updateBusinessKey(Mockito.<String>any(), Mockito.<String>any());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiForbiddenException.class, () -> processRuntimeImpl
        .update(new UpdateProcessPayload("42", "42", "The characteristics of someone or something", "42")));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService).createProcessInstanceQuery();
    verify(runtimeService).updateBusinessKey(eq("42"), eq("42"));
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(eq("42"));
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getStartUserId();
  }

  /**
   * Test {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}.
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateProcessPayload)")
  void testUpdate5() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getStartUserId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    doThrow(new ActivitiForbiddenException("An error occurred")).when(runtimeService)
        .setProcessInstanceName(Mockito.<String>any(), Mockito.<String>any());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiForbiddenException.class, () -> processRuntimeImpl
        .update(new UpdateProcessPayload("42", "42", "The characteristics of someone or something", null)));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService).createProcessInstanceQuery();
    verify(runtimeService).setProcessInstanceName(eq("42"), eq("42"));
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(eq("42"));
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getStartUserId();
  }

  /**
   * Test {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}.
   * <ul>
   *   <li>Given {@link RuntimeService}
   * {@link RuntimeService#setProcessInstanceName(String, String)} does
   * nothing.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateProcessPayload); given RuntimeService setProcessInstanceName(String, String) does nothing")
  void testUpdate_givenRuntimeServiceSetProcessInstanceNameDoesNothing() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getStartUserId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    doNothing().when(runtimeService).setProcessInstanceName(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(runtimeService).updateBusinessKey(Mockito.<String>any(), Mockito.<String>any());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act
    org.activiti.api.process.model.ProcessInstance actualUpdateResult = processRuntimeImpl
        .update(new UpdateProcessPayload("42", "42", "The characteristics of someone or something", "42"));

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService, atLeast(1)).createProcessInstanceQuery();
    verify(runtimeService).setProcessInstanceName(eq("42"), eq("42"));
    verify(runtimeService).updateBusinessKey(eq("42"), eq("42"));
    verify(processInstanceQueryImpl, atLeast(1)).singleResult();
    verify(processInstanceQueryImpl2, atLeast(1)).processInstanceId(eq("42"));
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getStartUserId();
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
    assertSame(processInstanceImpl, actualUpdateResult);
  }

  /**
   * Test {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getProcessInstanceId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateProcessPayload); then calls getProcessInstanceId()")
  void testUpdate_thenCallsGetProcessInstanceId() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getProcessInstanceId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(false);

    // Act and Assert
    assertThrows(ActivitiForbiddenException.class, () -> processRuntimeImpl.update(new UpdateProcessPayload()));
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getProcessInstanceId();
  }

  /**
   * Test {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}.
   * <ul>
   *   <li>Then return {@link ProcessInstanceImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateProcessPayload); then return ProcessInstanceImpl (default constructor)")
  void testUpdate_thenReturnProcessInstanceImpl() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getStartUserId()).thenReturn("42");
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(executionEntityImpl);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);
    when(processSecurityPoliciesManager.canWrite(Mockito.<String>any())).thenReturn(true);
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act
    org.activiti.api.process.model.ProcessInstance actualUpdateResult = processRuntimeImpl
        .update(new UpdateProcessPayload());

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).canWrite(eq("Process Definition Key"));
    verify(runtimeService, atLeast(1)).createProcessInstanceQuery();
    verify(processInstanceQueryImpl, atLeast(1)).singleResult();
    verify(processInstanceQueryImpl2, atLeast(1)).processInstanceId(isNull());
    verify(executionEntityImpl).getProcessDefinitionKey();
    verify(executionEntityImpl).getStartUserId();
    verify(aPIProcessInstanceConverter).from(isA(org.activiti.engine.runtime.ProcessInstance.class));
    assertSame(processInstanceImpl, actualUpdateResult);
  }

  /**
   * Test {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateProcessPayload); then throw NotFoundException")
  void testUpdate_thenThrowNotFoundException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl.singleResult()).thenReturn(null);
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = mock(ProcessInstanceQueryImpl.class);
    when(processInstanceQueryImpl2.processInstanceId(Mockito.<String>any())).thenReturn(processInstanceQueryImpl);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl2);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> processRuntimeImpl.update(new UpdateProcessPayload()));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQueryImpl).singleResult();
    verify(processInstanceQueryImpl2).processInstanceId(isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#receive(ReceiveMessagePayload)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#receive(ReceiveMessagePayload)}
   */
  @Test
  @DisplayName("Test receive(ReceiveMessagePayload); then calls checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)")
  void testReceive_thenCallsCheckReceiveMessagePayloadVariables() {
    // Arrange
    doNothing().when(processVariablesPayloadValidator)
        .checkReceiveMessagePayloadVariables(Mockito.<ReceiveMessagePayload>any(), Mockito.<String>any());

    // Act
    processRuntimeImpl.receive(new ReceiveMessagePayload());

    // Assert
    verify(processVariablesPayloadValidator).checkReceiveMessagePayloadVariables(isA(ReceiveMessagePayload.class),
        isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#receive(ReceiveMessagePayload)}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#receive(ReceiveMessagePayload)}
   */
  @Test
  @DisplayName("Test receive(ReceiveMessagePayload); then throw ActivitiObjectNotFoundException")
  void testReceive_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    doThrow(new ActivitiObjectNotFoundException("An error occurred")).when(processVariablesPayloadValidator)
        .checkReceiveMessagePayloadVariables(Mockito.<ReceiveMessagePayload>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.receive(new ReceiveMessagePayload()));
    verify(processVariablesPayloadValidator).checkReceiveMessagePayloadVariables(isA(ReceiveMessagePayload.class),
        isNull());
  }

  /**
   * Test
   * {@link ProcessRuntimeImpl#getProcessDefinitionAndCheckUserHasRights(String, String)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeImpl#getProcessDefinitionAndCheckUserHasRights(String, String)}
   */
  @Test
  @DisplayName("Test getProcessDefinitionAndCheckUserHasRights(String, String)")
  void testGetProcessDefinitionAndCheckUserHasRights() throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(new ProcessDefinitionQueryImpl());
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.getProcessDefinitionAndCheckUserHasRights("42", "Process Definition Key"));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#selectLatestDeployment()}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#selectLatestDeployment()}
   */
  @Test
  @DisplayName("Test selectLatestDeployment(); then throw ActivitiObjectNotFoundException")
  void testSelectLatestDeployment_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    when(repositoryService.createDeploymentQuery()).thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.selectLatestDeployment());
    verify(repositoryService).createDeploymentQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#internalProcessInstance(String)}.
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#internalProcessInstance(String)}
   */
  @Test
  @DisplayName("Test internalProcessInstance(String); then return createWithEmptyRelationshipCollections")
  void testInternalProcessInstance_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl(
        new CommandExecutorImpl(new CommandConfig(), first));
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl);

    // Act
    ProcessInstance actualInternalProcessInstanceResult = processRuntimeImpl.internalProcessInstance("42");

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
    assertSame(createWithEmptyRelationshipCollectionsResult, actualInternalProcessInstanceResult);
  }

  /**
   * Test {@link ProcessRuntimeImpl#internalProcessInstance(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#internalProcessInstance(String)}
   */
  @Test
  @DisplayName("Test internalProcessInstance(String); then throw ActivitiObjectNotFoundException")
  void testInternalProcessInstance_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.internalProcessInstance("42"));
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#internalProcessInstance(String)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessRuntimeImpl#internalProcessInstance(String)}
   */
  @Test
  @DisplayName("Test internalProcessInstance(String); then throw NotFoundException")
  void testInternalProcessInstance_thenThrowNotFoundException() {
    // Arrange
    CommandContextInterceptor first = mock(CommandContextInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any())).thenReturn(null);
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl(
        new CommandExecutorImpl(new CommandConfig(), first));
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQueryImpl);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> processRuntimeImpl.internalProcessInstance("42"));
    verify(runtimeService).createProcessInstanceQuery();
    verify(first).execute(isA(CommandConfig.class), isA(Command.class));
  }
}
