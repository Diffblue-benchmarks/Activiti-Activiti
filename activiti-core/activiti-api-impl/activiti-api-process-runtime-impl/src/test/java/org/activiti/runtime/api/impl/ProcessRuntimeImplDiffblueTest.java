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
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.activiti.api.process.model.payloads.CreateProcessInstancePayload;
import org.activiti.api.process.model.payloads.DeleteProcessPayload;
import org.activiti.api.process.model.payloads.GetProcessDefinitionsPayload;
import org.activiti.api.process.model.payloads.GetProcessInstancesPayload;
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
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.runtime.shared.security.SecurityManager;
import org.activiti.core.common.spring.security.policies.ProcessSecurityPoliciesManager;
import org.activiti.core.common.spring.security.policies.SecurityPolicyAccess;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.ProcessDefinitionQueryImpl;
import org.activiti.engine.impl.ProcessInstanceQueryImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.runtime.api.model.impl.APIDeploymentConverter;
import org.activiti.runtime.api.model.impl.APIProcessDefinitionConverter;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.activiti.runtime.api.model.impl.APIVariableInstanceConverter;
import org.activiti.runtime.api.query.impl.PageImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProcessRuntimeImpl.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ProcessRuntimeImplDiffblueTest {
  @MockBean private APIDeploymentConverter aPIDeploymentConverter;

  @MockBean private APIProcessDefinitionConverter aPIProcessDefinitionConverter;

  @MockBean private APIProcessInstanceConverter aPIProcessInstanceConverter;

  @MockBean private APIVariableInstanceConverter aPIVariableInstanceConverter;

  @MockBean private ProcessRuntimeConfiguration processRuntimeConfiguration;

  @Autowired private ProcessRuntimeImpl processRuntimeImpl;

  @MockBean private ProcessSecurityPoliciesManager processSecurityPoliciesManager;

  @MockBean private ProcessVariablesPayloadValidator processVariablesPayloadValidator;

  @MockBean private RepositoryService repositoryService;

  @MockBean private RuntimeService runtimeService;

  @MockBean private SecurityManager securityManager;

  @MockBean private TaskService taskService;

  /**
   * Test {@link ProcessRuntimeImpl#processDefinition(String)}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinition(String)}
   */
  @Test
  @DisplayName("Test processDefinition(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessDefinition ProcessRuntimeImpl.processDefinition(String)"
  })
  void testProcessDefinition() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.processDefinition("42"));
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinition(String)}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinition(String)}
   */
  @Test
  @DisplayName("Test processDefinition(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessDefinition ProcessRuntimeImpl.processDefinition(String)"
  })
  void testProcessDefinition2() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());
    when(securityManager.getAuthenticatedUserId())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.processDefinition("42"));
    verify(securityManager).getAuthenticatedUserId();
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinition(String)}.
   *
   * <ul>
   *   <li>Then calls {@link SecurityManager#getAuthenticatedUserGroups()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinition(String)}
   */
  @Test
  @DisplayName("Test processDefinition(String); then calls getAuthenticatedUserGroups()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessDefinition ProcessRuntimeImpl.processDefinition(String)"
  })
  void testProcessDefinition_thenCallsGetAuthenticatedUserGroups() throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.processDefinition("42"));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable)} with {@code pageable}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable) with 'pageable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processDefinitions(Pageable)"})
  void testProcessDefinitionsWithPageable() {
    // Arrange
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(Pageable.of(1, 3)));
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable)} with {@code pageable}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable) with 'pageable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processDefinitions(Pageable)"})
  void testProcessDefinitionsWithPageable2() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(Pageable.of(1, 3)));
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable)} with {@code pageable}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable) with 'pageable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processDefinitions(Pageable)"})
  void testProcessDefinitionsWithPageable3() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserId())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(Pageable.of(1, 3)));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)} with
   * {@code pageable}, {@code getProcessDefinitionsPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            processRuntimeImpl.processDefinitions(
                Pageable.of(1, 3), (GetProcessDefinitionsPayload) null));
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)} with
   * {@code pageable}, {@code getProcessDefinitionsPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload2() {
    // Arrange
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, new GetProcessDefinitionsPayload()));
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)} with
   * {@code pageable}, {@code getProcessDefinitionsPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload3() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, new GetProcessDefinitionsPayload()));
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)} with
   * {@code pageable}, {@code getProcessDefinitionsPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload4() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserId())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, new GetProcessDefinitionsPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)} with
   * {@code pageable}, {@code getProcessDefinitionsPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload5() throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, new GetProcessDefinitionsPayload()));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)} with
   * {@code pageable}, {@code getProcessDefinitionsPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload6() throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.getProcessDefinitionKeys()).thenReturn(new HashSet<>());
    doNothing()
        .when(getProcessDefinitionsPayload)
        .setProcessDefinitionKeys(Mockito.<Set<String>>any());
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, new GetProcessDefinitionsPayload()));
    verify(getProcessDefinitionsPayload).getProcessDefinitionKeys();
    verify(getProcessDefinitionsPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)} with
   * {@code pageable}, {@code getProcessDefinitionsPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload7() throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    HashSet<String> stringSet = new HashSet<>();
    stringSet.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.getProcessDefinitionKeys()).thenReturn(stringSet);
    doNothing()
        .when(getProcessDefinitionsPayload)
        .setProcessDefinitionKeys(Mockito.<Set<String>>any());
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, new GetProcessDefinitionsPayload()));
    verify(getProcessDefinitionsPayload, atLeast(1)).getProcessDefinitionKeys();
    verify(getProcessDefinitionsPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload,
   * List)} with {@code pageable}, {@code getProcessDefinitionsPayload}, {@code include}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload, List)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload, List) with 'pageable', 'getProcessDefinitionsPayload', 'include'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload, List)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayloadInclude() {
    // Arrange
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, null, new ArrayList<>()));
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload,
   * List)} with {@code pageable}, {@code getProcessDefinitionsPayload}, {@code include}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload, List)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload, List) with 'pageable', 'getProcessDefinitionsPayload', 'include'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload, List)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayloadInclude2() {
    // Arrange
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    Pageable pageable = Pageable.of(1, 3);
    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            processRuntimeImpl.processDefinitions(
                pageable, getProcessDefinitionsPayload, new ArrayList<>()));
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload,
   * List)} with {@code pageable}, {@code getProcessDefinitionsPayload}, {@code include}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload, List)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload, List) with 'pageable', 'getProcessDefinitionsPayload', 'include'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload, List)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayloadInclude3() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    Pageable pageable = Pageable.of(1, 3);
    GetProcessDefinitionsPayload getProcessDefinitionsPayload2 = new GetProcessDefinitionsPayload();

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            processRuntimeImpl.processDefinitions(
                pageable, getProcessDefinitionsPayload2, new ArrayList<>()));
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload,
   * List)} with {@code pageable}, {@code getProcessDefinitionsPayload}, {@code include}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload, List)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload, List) with 'pageable', 'getProcessDefinitionsPayload', 'include'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload, List)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayloadInclude4() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserId())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    Pageable pageable = Pageable.of(1, 3);
    GetProcessDefinitionsPayload getProcessDefinitionsPayload2 = new GetProcessDefinitionsPayload();

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            processRuntimeImpl.processDefinitions(
                pageable, getProcessDefinitionsPayload2, new ArrayList<>()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload,
   * List)} with {@code pageable}, {@code getProcessDefinitionsPayload}, {@code include}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload, List)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload, List) with 'pageable', 'getProcessDefinitionsPayload', 'include'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload, List)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayloadInclude5()
      throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);
    GetProcessDefinitionsPayload getProcessDefinitionsPayload2 = new GetProcessDefinitionsPayload();

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            processRuntimeImpl.processDefinitions(
                pageable, getProcessDefinitionsPayload2, new ArrayList<>()));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload,
   * List)} with {@code pageable}, {@code getProcessDefinitionsPayload}, {@code include}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload, List)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload, List) with 'pageable', 'getProcessDefinitionsPayload', 'include'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload, List)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayloadInclude6()
      throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.getProcessDefinitionKeys()).thenReturn(new HashSet<>());
    doNothing()
        .when(getProcessDefinitionsPayload)
        .setProcessDefinitionKeys(Mockito.<Set<String>>any());
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);
    GetProcessDefinitionsPayload getProcessDefinitionsPayload2 = new GetProcessDefinitionsPayload();

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            processRuntimeImpl.processDefinitions(
                pageable, getProcessDefinitionsPayload2, new ArrayList<>()));
    verify(getProcessDefinitionsPayload).getProcessDefinitionKeys();
    verify(getProcessDefinitionsPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload,
   * List)} with {@code pageable}, {@code getProcessDefinitionsPayload}, {@code include}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload, List)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload, List) with 'pageable', 'getProcessDefinitionsPayload', 'include'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload, List)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayloadInclude7()
      throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    HashSet<String> stringSet = new HashSet<>();
    stringSet.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.getProcessDefinitionKeys()).thenReturn(stringSet);
    doNothing()
        .when(getProcessDefinitionsPayload)
        .setProcessDefinitionKeys(Mockito.<Set<String>>any());
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);
    GetProcessDefinitionsPayload getProcessDefinitionsPayload2 = new GetProcessDefinitionsPayload();

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            processRuntimeImpl.processDefinitions(
                pageable, getProcessDefinitionsPayload2, new ArrayList<>()));
    verify(getProcessDefinitionsPayload, atLeast(1)).getProcessDefinitionKeys();
    verify(getProcessDefinitionsPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload,
   * List)} with {@code pageable}, {@code getProcessDefinitionsPayload}, {@code include}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload, List)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload, List) with 'pageable', 'getProcessDefinitionsPayload', 'include'; given '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload, List)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayloadInclude_given42()
      throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.getProcessDefinitionKeys()).thenReturn(new HashSet<>());
    doNothing()
        .when(getProcessDefinitionsPayload)
        .setProcessDefinitionKeys(Mockito.<Set<String>>any());
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);
    GetProcessDefinitionsPayload getProcessDefinitionsPayload2 = new GetProcessDefinitionsPayload();

    ArrayList<String> include = new ArrayList<>();
    include.add("42");
    include.add("foo");

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            processRuntimeImpl.processDefinitions(
                pageable, getProcessDefinitionsPayload2, include));
    verify(getProcessDefinitionsPayload).getProcessDefinitionKeys();
    verify(getProcessDefinitionsPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload,
   * List)} with {@code pageable}, {@code getProcessDefinitionsPayload}, {@code include}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload, List)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload, List) with 'pageable', 'getProcessDefinitionsPayload', 'include'; given 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload, List)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayloadInclude_givenFoo()
      throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.getProcessDefinitionKeys()).thenReturn(new HashSet<>());
    doNothing()
        .when(getProcessDefinitionsPayload)
        .setProcessDefinitionKeys(Mockito.<Set<String>>any());
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);
    GetProcessDefinitionsPayload getProcessDefinitionsPayload2 = new GetProcessDefinitionsPayload();

    ArrayList<String> include = new ArrayList<>();
    include.add("foo");

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            processRuntimeImpl.processDefinitions(
                pageable, getProcessDefinitionsPayload2, include));
    verify(getProcessDefinitionsPayload).getProcessDefinitionKeys();
    verify(getProcessDefinitionsPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, List)} with {@code pageable},
   * {@code include}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable, List)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable, List) with 'pageable', 'include'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processDefinitions(Pageable, List)"})
  void testProcessDefinitionsWithPageableInclude() {
    // Arrange
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, new ArrayList<>()));
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, List)} with {@code pageable},
   * {@code include}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable, List)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable, List) with 'pageable', 'include'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processDefinitions(Pageable, List)"})
  void testProcessDefinitionsWithPageableInclude2() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, new ArrayList<>()));
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, List)} with {@code pageable},
   * {@code include}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable, List)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable, List) with 'pageable', 'include'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processDefinitions(Pageable, List)"})
  void testProcessDefinitionsWithPageableInclude3() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserId())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, new ArrayList<>()));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, List)} with {@code pageable},
   * {@code include}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable, List)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, List) with 'pageable', 'include'; given '42'; when ArrayList() add '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processDefinitions(Pageable, List)"})
  void testProcessDefinitionsWithPageableInclude_given42_whenArrayListAdd42()
      throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.getProcessDefinitionKeys()).thenReturn(new HashSet<>());
    doNothing()
        .when(getProcessDefinitionsPayload)
        .setProcessDefinitionKeys(Mockito.<Set<String>>any());
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    ArrayList<String> include = new ArrayList<>();
    include.add("42");
    include.add("foo");

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, include));
    verify(getProcessDefinitionsPayload).getProcessDefinitionKeys();
    verify(getProcessDefinitionsPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, List)} with {@code pageable},
   * {@code include}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable, List)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, List) with 'pageable', 'include'; given 'foo'; when ArrayList() add 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processDefinitions(Pageable, List)"})
  void testProcessDefinitionsWithPageableInclude_givenFoo_whenArrayListAddFoo()
      throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.getProcessDefinitionKeys()).thenReturn(new HashSet<>());
    doNothing()
        .when(getProcessDefinitionsPayload)
        .setProcessDefinitionKeys(Mockito.<Set<String>>any());
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    ArrayList<String> include = new ArrayList<>();
    include.add("foo");

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, include));
    verify(getProcessDefinitionsPayload).getProcessDefinitionKeys();
    verify(getProcessDefinitionsPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, List)} with {@code pageable},
   * {@code include}.
   *
   * <ul>
   *   <li>Then calls {@link SecurityManager#getAuthenticatedUserGroups()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable, List)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, List) with 'pageable', 'include'; then calls getAuthenticatedUserGroups()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processDefinitions(Pageable, List)"})
  void testProcessDefinitionsWithPageableInclude_thenCallsGetAuthenticatedUserGroups()
      throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, new ArrayList<>()));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, List)} with {@code pageable},
   * {@code include}.
   *
   * <ul>
   *   <li>Then calls {@link GetProcessDefinitionsPayload#getProcessDefinitionKeys()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable, List)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, List) with 'pageable', 'include'; then calls getProcessDefinitionKeys()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processDefinitions(Pageable, List)"})
  void testProcessDefinitionsWithPageableInclude_thenCallsGetProcessDefinitionKeys()
      throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.getProcessDefinitionKeys()).thenReturn(new HashSet<>());
    doNothing()
        .when(getProcessDefinitionsPayload)
        .setProcessDefinitionKeys(Mockito.<Set<String>>any());
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, new ArrayList<>()));
    verify(getProcessDefinitionsPayload).getProcessDefinitionKeys();
    verify(getProcessDefinitionsPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable, List)} with {@code pageable},
   * {@code include}.
   *
   * <ul>
   *   <li>Then calls {@link GetProcessDefinitionsPayload#getProcessDefinitionKeys()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable, List)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, List) with 'pageable', 'include'; then calls getProcessDefinitionKeys()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processDefinitions(Pageable, List)"})
  void testProcessDefinitionsWithPageableInclude_thenCallsGetProcessDefinitionKeys2()
      throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    HashSet<String> stringSet = new HashSet<>();
    stringSet.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.getProcessDefinitionKeys()).thenReturn(stringSet);
    doNothing()
        .when(getProcessDefinitionsPayload)
        .setProcessDefinitionKeys(Mockito.<Set<String>>any());
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(pageable, new ArrayList<>()));
    verify(getProcessDefinitionsPayload, atLeast(1)).getProcessDefinitionKeys();
    verify(getProcessDefinitionsPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable)} with {@code pageable}.
   *
   * <ul>
   *   <li>Then calls {@link SecurityManager#getAuthenticatedUserGroups()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable) with 'pageable'; then calls getAuthenticatedUserGroups()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processDefinitions(Pageable)"})
  void testProcessDefinitionsWithPageable_thenCallsGetAuthenticatedUserGroups()
      throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(Pageable.of(1, 3)));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable)} with {@code pageable}.
   *
   * <ul>
   *   <li>Then calls {@link GetProcessDefinitionsPayload#getProcessDefinitionKeys()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable) with 'pageable'; then calls getProcessDefinitionKeys()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processDefinitions(Pageable)"})
  void testProcessDefinitionsWithPageable_thenCallsGetProcessDefinitionKeys()
      throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.getProcessDefinitionKeys()).thenReturn(new HashSet<>());
    doNothing()
        .when(getProcessDefinitionsPayload)
        .setProcessDefinitionKeys(Mockito.<Set<String>>any());
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(Pageable.of(1, 3)));
    verify(getProcessDefinitionsPayload).getProcessDefinitionKeys();
    verify(getProcessDefinitionsPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processDefinitions(Pageable)} with {@code pageable}.
   *
   * <ul>
   *   <li>Then calls {@link GetProcessDefinitionsPayload#getProcessDefinitionKeys()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processDefinitions(Pageable)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable) with 'pageable'; then calls getProcessDefinitionKeys()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processDefinitions(Pageable)"})
  void testProcessDefinitionsWithPageable_thenCallsGetProcessDefinitionKeys2()
      throws SecurityException {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    HashSet<String> stringSet = new HashSet<>();
    stringSet.add("foo");

    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.getProcessDefinitionKeys()).thenReturn(stringSet);
    doNothing()
        .when(getProcessDefinitionsPayload)
        .setProcessDefinitionKeys(Mockito.<Set<String>>any());
    getProcessDefinitionsPayload.setProcessDefinitionKeys(processDefinitionKeys);
    when(processSecurityPoliciesManager.restrictProcessDefQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessDefinitionsPayload);
    when(securityManager.getAuthenticatedUserGroups())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processDefinitions(Pageable.of(1, 3)));
    verify(getProcessDefinitionsPayload, atLeast(1)).getProcessDefinitionKeys();
    verify(getProcessDefinitionsPayload).setProcessDefinitionKeys(isA(Set.class));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessDefQuery(SecurityPolicyAccess.READ);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable)} with {@code pageable}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable) with 'pageable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processInstances(Pageable)"})
  void testProcessInstancesWithPageable() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery()).thenReturn(new ProcessInstanceQueryImpl());

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(securityManager.getAuthenticatedUserId())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processInstances(Pageable.of(1, 3)));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(SecurityPolicyAccess.READ);
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable)} with {@code pageable}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable) with 'pageable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processInstances(Pageable)"})
  void testProcessInstancesWithPageable2() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.involvedUser(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processInstances(Pageable.of(1, 3)));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(SecurityPolicyAccess.READ);
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).involvedUser("42");
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable)} with {@code pageable}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable) with 'pageable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processInstances(Pageable)"})
  void testProcessInstancesWithPageable3() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(processInstanceQuery.involvedUser(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("");

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(processDefinitionKeys);
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processInstances(Pageable.of(1, 3)));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(SecurityPolicyAccess.READ);
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).involvedUser("42");
    verify(processInstanceQuery).processDefinitionKeys(isA(Set.class));
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)} with
   * {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processRuntimeImpl.processInstances(Pageable.of(1, 3), null));
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)} with
   * {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload2() {
    // Arrange
    when(processSecurityPoliciesManager.restrictProcessInstQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processInstances(pageable, getProcessInstancesPayload));
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(SecurityPolicyAccess.READ);
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)} with
   * {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload3() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery()).thenReturn(new ProcessInstanceQueryImpl());

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(securityManager.getAuthenticatedUserId())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload2 = new GetProcessInstancesPayload();
    getProcessInstancesPayload2.setActiveOnly(true);
    getProcessInstancesPayload2.setBusinessKey("Business Key");
    getProcessInstancesPayload2.setParentProcessInstanceId("42");
    getProcessInstancesPayload2.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload2.setSuspendedOnly(true);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processInstances(pageable, getProcessInstancesPayload2));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(SecurityPolicyAccess.READ);
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)} with
   * {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload4() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.involvedUser(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
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
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult =
        processRuntimeImpl.processInstances(pageable, getProcessInstancesPayload2);

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(SecurityPolicyAccess.READ);
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).involvedUser("42");
    verify(processInstanceQuery).processInstanceBusinessKey("Business Key");
    verify(processInstanceQuery).superProcessInstanceId("42");
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
    assertTrue(getProcessInstancesPayload2.getProcessDefinitionKeys().isEmpty());
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)} with
   * {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload5() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.involvedUser(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload2 = new GetProcessInstancesPayload();
    getProcessInstancesPayload2.setActiveOnly(true);
    getProcessInstancesPayload2.setBusinessKey("Business Key");
    getProcessInstancesPayload2.setParentProcessInstanceId("42");
    getProcessInstancesPayload2.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload2.setSuspendedOnly(true);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processInstances(pageable, getProcessInstancesPayload2));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(SecurityPolicyAccess.READ);
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).involvedUser("42");
    verify(processInstanceQuery).processInstanceBusinessKey("Business Key");
    verify(processInstanceQuery).superProcessInstanceId("42");
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)} with
   * {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload6() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.involvedUser(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("");

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(processDefinitionKeys);
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
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
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult =
        processRuntimeImpl.processInstances(pageable, getProcessInstancesPayload2);

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(SecurityPolicyAccess.READ);
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).involvedUser("42");
    verify(processInstanceQuery).processDefinitionKeys(isA(Set.class));
    verify(processInstanceQuery).processInstanceBusinessKey("Business Key");
    verify(processInstanceQuery).superProcessInstanceId("42");
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    Set<String> processDefinitionKeys2 = getProcessInstancesPayload2.getProcessDefinitionKeys();
    assertEquals(1, processDefinitionKeys2.size());
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
    assertTrue(processDefinitionKeys2.contains(""));
    assertSame(processDefinitionKeys, processDefinitionKeys2);
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)} with
   * {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload7() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    when(processInstanceQuery.involvedUser(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("");

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(processDefinitionKeys);
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload2 = new GetProcessInstancesPayload();
    getProcessInstancesPayload2.setActiveOnly(true);
    getProcessInstancesPayload2.setBusinessKey("Business Key");
    getProcessInstancesPayload2.setParentProcessInstanceId("42");
    getProcessInstancesPayload2.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload2.setSuspendedOnly(true);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processInstances(pageable, getProcessInstancesPayload2));
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(SecurityPolicyAccess.READ);
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).involvedUser("42");
    verify(processInstanceQuery).processDefinitionKeys(isA(Set.class));
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)} with
   * {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload8() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.involvedUser(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("");

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(processDefinitionKeys);
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload2 = new GetProcessInstancesPayload();
    getProcessInstancesPayload2.setActiveOnly(false);
    getProcessInstancesPayload2.setBusinessKey("Business Key");
    getProcessInstancesPayload2.setParentProcessInstanceId("42");
    getProcessInstancesPayload2.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload2.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult =
        processRuntimeImpl.processInstances(pageable, getProcessInstancesPayload2);

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(SecurityPolicyAccess.READ);
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).involvedUser("42");
    verify(processInstanceQuery).processDefinitionKeys(isA(Set.class));
    verify(processInstanceQuery).processInstanceBusinessKey("Business Key");
    verify(processInstanceQuery).superProcessInstanceId("42");
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    Set<String> processDefinitionKeys2 = getProcessInstancesPayload2.getProcessDefinitionKeys();
    assertEquals(1, processDefinitionKeys2.size());
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
    assertTrue(processDefinitionKeys2.contains(""));
    assertSame(processDefinitionKeys, processDefinitionKeys2);
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)} with
   * {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload9() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.involvedUser(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("");

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(processDefinitionKeys);
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload2 = new GetProcessInstancesPayload();
    getProcessInstancesPayload2.setActiveOnly(true);
    getProcessInstancesPayload2.setBusinessKey(null);
    getProcessInstancesPayload2.setParentProcessInstanceId("42");
    getProcessInstancesPayload2.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload2.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult =
        processRuntimeImpl.processInstances(pageable, getProcessInstancesPayload2);

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(SecurityPolicyAccess.READ);
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).involvedUser("42");
    verify(processInstanceQuery).processDefinitionKeys(isA(Set.class));
    verify(processInstanceQuery).superProcessInstanceId("42");
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    Set<String> processDefinitionKeys2 = getProcessInstancesPayload2.getProcessDefinitionKeys();
    assertEquals(1, processDefinitionKeys2.size());
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
    assertTrue(processDefinitionKeys2.contains(""));
    assertSame(processDefinitionKeys, processDefinitionKeys2);
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)} with
   * {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload10() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.involvedUser(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("");

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(processDefinitionKeys);
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload2 = new GetProcessInstancesPayload();
    getProcessInstancesPayload2.setActiveOnly(true);
    getProcessInstancesPayload2.setBusinessKey("Business Key");
    getProcessInstancesPayload2.setParentProcessInstanceId(null);
    getProcessInstancesPayload2.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload2.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult =
        processRuntimeImpl.processInstances(pageable, getProcessInstancesPayload2);

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(SecurityPolicyAccess.READ);
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).involvedUser("42");
    verify(processInstanceQuery).processDefinitionKeys(isA(Set.class));
    verify(processInstanceQuery).processInstanceBusinessKey("Business Key");
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    Set<String> processDefinitionKeys2 = getProcessInstancesPayload2.getProcessDefinitionKeys();
    assertEquals(1, processDefinitionKeys2.size());
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
    assertTrue(processDefinitionKeys2.contains(""));
    assertSame(processDefinitionKeys, processDefinitionKeys2);
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)} with
   * {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload11() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.involvedUser(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("");

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(processDefinitionKeys);
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload2 = new GetProcessInstancesPayload();
    getProcessInstancesPayload2.setActiveOnly(true);
    getProcessInstancesPayload2.setBusinessKey("Business Key");
    getProcessInstancesPayload2.setParentProcessInstanceId("42");
    getProcessInstancesPayload2.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload2.setSuspendedOnly(false);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult =
        processRuntimeImpl.processInstances(pageable, getProcessInstancesPayload2);

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(SecurityPolicyAccess.READ);
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).involvedUser("42");
    verify(processInstanceQuery).processDefinitionKeys(isA(Set.class));
    verify(processInstanceQuery).processInstanceBusinessKey("Business Key");
    verify(processInstanceQuery).superProcessInstanceId("42");
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    Set<String> processDefinitionKeys2 = getProcessInstancesPayload2.getProcessDefinitionKeys();
    assertEquals(1, processDefinitionKeys2.size());
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
    assertTrue(processDefinitionKeys2.contains(""));
    assertSame(processDefinitionKeys, processDefinitionKeys2);
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)} with
   * {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <ul>
   *   <li>Given empty string.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'; given empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload_givenEmptyString() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.involvedUser(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("");

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(processDefinitionKeys);
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload2 = new GetProcessInstancesPayload();
    getProcessInstancesPayload2.setActiveOnly(true);
    getProcessInstancesPayload2.setBusinessKey("");
    getProcessInstancesPayload2.setParentProcessInstanceId("42");
    getProcessInstancesPayload2.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload2.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult =
        processRuntimeImpl.processInstances(pageable, getProcessInstancesPayload2);

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(SecurityPolicyAccess.READ);
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).involvedUser("42");
    verify(processInstanceQuery).processDefinitionKeys(isA(Set.class));
    verify(processInstanceQuery).superProcessInstanceId("42");
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    Set<String> processDefinitionKeys2 = getProcessInstancesPayload2.getProcessDefinitionKeys();
    assertEquals(1, processDefinitionKeys2.size());
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
    assertTrue(processDefinitionKeys2.contains(""));
    assertSame(processDefinitionKeys, processDefinitionKeys2);
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable)} with {@code pageable}.
   *
   * <ul>
   *   <li>Given {@link RuntimeService}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable) with 'pageable'; given RuntimeService")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processInstances(Pageable)"})
  void testProcessInstancesWithPageable_givenRuntimeService() {
    // Arrange
    when(processSecurityPoliciesManager.restrictProcessInstQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.processInstances(Pageable.of(1, 3)));
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(SecurityPolicyAccess.READ);
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable)} with {@code pageable}.
   *
   * <ul>
   *   <li>Then return {@link PageImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable) with 'pageable'; then return PageImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processInstances(Pageable)"})
  void testProcessInstancesWithPageable_thenReturnPageImpl() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.involvedUser(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult =
        processRuntimeImpl.processInstances(Pageable.of(1, 3));

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(SecurityPolicyAccess.READ);
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).involvedUser("42");
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test {@link ProcessRuntimeImpl#processInstances(Pageable)} with {@code pageable}.
   *
   * <ul>
   *   <li>Then return {@link PageImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#processInstances(Pageable)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable) with 'pageable'; then return PageImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessRuntimeImpl.processInstances(Pageable)"})
  void testProcessInstancesWithPageable_thenReturnPageImpl2() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.involvedUser(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("");

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(processDefinitionKeys);
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(processSecurityPoliciesManager.restrictProcessInstQuery(
            Mockito.<SecurityPolicyAccess>any()))
        .thenReturn(getProcessInstancesPayload);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult =
        processRuntimeImpl.processInstances(Pageable.of(1, 3));

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(processSecurityPoliciesManager).restrictProcessInstQuery(SecurityPolicyAccess.READ);
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).involvedUser("42");
    verify(processInstanceQuery).processDefinitionKeys(isA(Set.class));
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test {@link ProcessRuntimeImpl#start(StartMessagePayload)} with {@code messagePayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#start(StartMessagePayload)}
   */
  @Test
  @DisplayName("Test start(StartMessagePayload) with 'messagePayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessRuntimeImpl.start(StartMessagePayload)"
  })
  void testStartWithMessagePayload() {
    // Arrange
    when(runtimeService.startProcessInstanceByMessage(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(aPIProcessInstanceConverter.from(Mockito.<ProcessInstance>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    doNothing()
        .when(processVariablesPayloadValidator)
        .checkStartMessagePayloadVariables(
            Mockito.<StartMessagePayload>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.start(new StartMessagePayload()));
    verify(runtimeService).startProcessInstanceByMessage(isNull(), isNull(), isA(Map.class));
    verify(processVariablesPayloadValidator)
        .checkStartMessagePayloadVariables(isA(StartMessagePayload.class), isNull());
    verify(aPIProcessInstanceConverter).from(isA(ProcessInstance.class));
  }

  /**
   * Test {@link ProcessRuntimeImpl#start(StartMessagePayload)} with {@code messagePayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#start(StartMessagePayload)}
   */
  @Test
  @DisplayName("Test start(StartMessagePayload) with 'messagePayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessRuntimeImpl.start(StartMessagePayload)"
  })
  void testStartWithMessagePayload2() {
    // Arrange
    when(runtimeService.startProcessInstanceByMessage(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<Map<String, Object>>any()))
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));
    doNothing()
        .when(processVariablesPayloadValidator)
        .checkStartMessagePayloadVariables(
            Mockito.<StartMessagePayload>any(), Mockito.<String>any());
    StartMessagePayload messagePayload = new StartMessagePayload("Name", "Business Key", null);

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.start(messagePayload));
    verify(runtimeService).startProcessInstanceByMessage(eq("Name"), eq("Business Key"), isNull());
    verify(processVariablesPayloadValidator)
        .checkStartMessagePayloadVariables(isA(StartMessagePayload.class), isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#start(StartMessagePayload)} with {@code messagePayload}.
   *
   * <ul>
   *   <li>Given {@link RuntimeService}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#start(StartMessagePayload)}
   */
  @Test
  @DisplayName("Test start(StartMessagePayload) with 'messagePayload'; given RuntimeService")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessRuntimeImpl.start(StartMessagePayload)"
  })
  void testStartWithMessagePayload_givenRuntimeService() {
    // Arrange
    doThrow(new ActivitiObjectNotFoundException("An error occurred"))
        .when(processVariablesPayloadValidator)
        .checkStartMessagePayloadVariables(
            Mockito.<StartMessagePayload>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.start(new StartMessagePayload()));
    verify(processVariablesPayloadValidator)
        .checkStartMessagePayloadVariables(isA(StartMessagePayload.class), isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#start(StartMessagePayload)} with {@code messagePayload}.
   *
   * <ul>
   *   <li>Then return {@link ProcessInstanceImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#start(StartMessagePayload)}
   */
  @Test
  @DisplayName(
      "Test start(StartMessagePayload) with 'messagePayload'; then return ProcessInstanceImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessRuntimeImpl.start(StartMessagePayload)"
  })
  void testStartWithMessagePayload_thenReturnProcessInstanceImpl() {
    // Arrange
    when(runtimeService.startProcessInstanceByMessage(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(aPIProcessInstanceConverter.from(Mockito.<ProcessInstance>any()))
        .thenReturn(processInstanceImpl);
    doNothing()
        .when(processVariablesPayloadValidator)
        .checkStartMessagePayloadVariables(
            Mockito.<StartMessagePayload>any(), Mockito.<String>any());

    // Act
    org.activiti.api.process.model.ProcessInstance actualStartResult =
        processRuntimeImpl.start(new StartMessagePayload());

    // Assert
    verify(runtimeService).startProcessInstanceByMessage(isNull(), isNull(), isA(Map.class));
    verify(processVariablesPayloadValidator)
        .checkStartMessagePayloadVariables(isA(StartMessagePayload.class), isNull());
    verify(aPIProcessInstanceConverter).from(isA(ProcessInstance.class));
    assertSame(processInstanceImpl, actualStartResult);
  }

  /**
   * Test {@link ProcessRuntimeImpl#start(StartProcessPayload)} with {@code startProcessPayload}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#start(StartProcessPayload)}
   */
  @Test
  @DisplayName("Test start(StartProcessPayload) with 'startProcessPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessRuntimeImpl.start(StartProcessPayload)"
  })
  void testStartWithStartProcessPayload() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.start(new StartProcessPayload()));
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#start(StartProcessPayload)} with {@code startProcessPayload}.
   *
   * <ul>
   *   <li>Then calls {@link SecurityManager#getAuthenticatedUserId()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#start(StartProcessPayload)}
   */
  @Test
  @DisplayName(
      "Test start(StartProcessPayload) with 'startProcessPayload'; then calls getAuthenticatedUserId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessRuntimeImpl.start(StartProcessPayload)"
  })
  void testStartWithStartProcessPayload_thenCallsGetAuthenticatedUserId() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());
    when(securityManager.getAuthenticatedUserId())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.start(new StartProcessPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#create(CreateProcessInstancePayload)}.
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#create(CreateProcessInstancePayload)}
   */
  @Test
  @DisplayName("Test create(CreateProcessInstancePayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessRuntimeImpl.create(CreateProcessInstancePayload)"
  })
  void testCreate() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.create(new CreateProcessInstancePayload()));
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#create(CreateProcessInstancePayload)}.
   *
   * <ul>
   *   <li>Then calls {@link SecurityManager#getAuthenticatedUserId()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#create(CreateProcessInstancePayload)}
   */
  @Test
  @DisplayName("Test create(CreateProcessInstancePayload); then calls getAuthenticatedUserId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessRuntimeImpl.create(CreateProcessInstancePayload)"
  })
  void testCreate_thenCallsGetAuthenticatedUserId() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());
    when(securityManager.getAuthenticatedUserId())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.create(new CreateProcessInstancePayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#suspend(SuspendProcessPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#suspend(SuspendProcessPayload)}
   */
  @Test
  @DisplayName("Test suspend(SuspendProcessPayload); then throw ActivitiObjectNotFoundException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessRuntimeImpl.suspend(SuspendProcessPayload)"
  })
  void testSuspend_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.suspend(new SuspendProcessPayload()));
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#resume(ResumeProcessPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#resume(ResumeProcessPayload)}
   */
  @Test
  @DisplayName("Test resume(ResumeProcessPayload); then throw ActivitiObjectNotFoundException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessRuntimeImpl.resume(ResumeProcessPayload)"
  })
  void testResume_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.resume(new ResumeProcessPayload()));
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#delete(DeleteProcessPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#delete(DeleteProcessPayload)}
   */
  @Test
  @DisplayName("Test delete(DeleteProcessPayload); then throw ActivitiObjectNotFoundException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessRuntimeImpl.delete(DeleteProcessPayload)"
  })
  void testDelete_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.delete(new DeleteProcessPayload()));
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}
   */
  @Test
  @DisplayName(
      "Test removeVariables(RemoveProcessVariablesPayload); then throw ActivitiObjectNotFoundException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessRuntimeImpl.removeVariables(RemoveProcessVariablesPayload)"})
  void testRemoveVariables_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.removeVariables(new RemoveProcessVariablesPayload()));
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#setVariables(SetProcessVariablesPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#setVariables(SetProcessVariablesPayload)}
   */
  @Test
  @DisplayName(
      "Test setVariables(SetProcessVariablesPayload); then throw ActivitiObjectNotFoundException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessRuntimeImpl.setVariables(SetProcessVariablesPayload)"})
  void testSetVariables_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.setVariables(new SetProcessVariablesPayload()));
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#signal(SignalPayload)}.
   *
   * <ul>
   *   <li>Then calls {@link
   *       ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload, String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#signal(SignalPayload)}
   */
  @Test
  @DisplayName(
      "Test signal(SignalPayload); then calls checkSignalPayloadVariables(SignalPayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessRuntimeImpl.signal(SignalPayload)"})
  void testSignal_thenCallsCheckSignalPayloadVariables() {
    // Arrange
    doNothing()
        .when(processVariablesPayloadValidator)
        .checkSignalPayloadVariables(Mockito.<SignalPayload>any(), Mockito.<String>any());

    // Act
    processRuntimeImpl.signal(new SignalPayload());

    // Assert
    verify(processVariablesPayloadValidator)
        .checkSignalPayloadVariables(isA(SignalPayload.class), isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#signal(SignalPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#signal(SignalPayload)}
   */
  @Test
  @DisplayName("Test signal(SignalPayload); then throw ActivitiObjectNotFoundException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessRuntimeImpl.signal(SignalPayload)"})
  void testSignal_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    doThrow(new ActivitiObjectNotFoundException("An error occurred"))
        .when(processVariablesPayloadValidator)
        .checkSignalPayloadVariables(Mockito.<SignalPayload>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.signal(new SignalPayload()));
    verify(processVariablesPayloadValidator)
        .checkSignalPayloadVariables(isA(SignalPayload.class), isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#update(UpdateProcessPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateProcessPayload); then throw ActivitiObjectNotFoundException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessRuntimeImpl.update(UpdateProcessPayload)"
  })
  void testUpdate_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.update(new UpdateProcessPayload()));
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#receive(ReceiveMessagePayload)}.
   *
   * <ul>
   *   <li>Then calls {@link
   *       ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#receive(ReceiveMessagePayload)}
   */
  @Test
  @DisplayName(
      "Test receive(ReceiveMessagePayload); then calls checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessRuntimeImpl.receive(ReceiveMessagePayload)"})
  void testReceive_thenCallsCheckReceiveMessagePayloadVariables() {
    // Arrange
    doNothing()
        .when(processVariablesPayloadValidator)
        .checkReceiveMessagePayloadVariables(
            Mockito.<ReceiveMessagePayload>any(), Mockito.<String>any());

    // Act
    processRuntimeImpl.receive(new ReceiveMessagePayload());

    // Assert
    verify(processVariablesPayloadValidator)
        .checkReceiveMessagePayloadVariables(isA(ReceiveMessagePayload.class), isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#receive(ReceiveMessagePayload)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#receive(ReceiveMessagePayload)}
   */
  @Test
  @DisplayName("Test receive(ReceiveMessagePayload); then throw ActivitiObjectNotFoundException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessRuntimeImpl.receive(ReceiveMessagePayload)"})
  void testReceive_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    doThrow(new ActivitiObjectNotFoundException("An error occurred"))
        .when(processVariablesPayloadValidator)
        .checkReceiveMessagePayloadVariables(
            Mockito.<ReceiveMessagePayload>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.receive(new ReceiveMessagePayload()));
    verify(processVariablesPayloadValidator)
        .checkReceiveMessagePayloadVariables(isA(ReceiveMessagePayload.class), isNull());
  }

  /**
   * Test {@link ProcessRuntimeImpl#getProcessDefinitionAndCheckUserHasRights(String, String)}.
   *
   * <p>Method under test: {@link
   * ProcessRuntimeImpl#getProcessDefinitionAndCheckUserHasRights(String, String)}
   */
  @Test
  @DisplayName("Test getProcessDefinitionAndCheckUserHasRights(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessDefinition ProcessRuntimeImpl.getProcessDefinitionAndCheckUserHasRights(String, String)"
  })
  void testGetProcessDefinitionAndCheckUserHasRights() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            processRuntimeImpl.getProcessDefinitionAndCheckUserHasRights(
                "42", "Process Definition Key"));
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#getProcessDefinitionAndCheckUserHasRights(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link SecurityManager#getAuthenticatedUserId()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessRuntimeImpl#getProcessDefinitionAndCheckUserHasRights(String, String)}
   */
  @Test
  @DisplayName(
      "Test getProcessDefinitionAndCheckUserHasRights(String, String); then calls getAuthenticatedUserId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessDefinition ProcessRuntimeImpl.getProcessDefinitionAndCheckUserHasRights(String, String)"
  })
  void testGetProcessDefinitionAndCheckUserHasRights_thenCallsGetAuthenticatedUserId() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(new ProcessDefinitionQueryImpl());
    when(securityManager.getAuthenticatedUserId())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () ->
            processRuntimeImpl.getProcessDefinitionAndCheckUserHasRights(
                "42", "Process Definition Key"));
    verify(securityManager).getAuthenticatedUserId();
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#selectLatestDeployment()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#selectLatestDeployment()}
   */
  @Test
  @DisplayName("Test selectLatestDeployment(); then throw ActivitiObjectNotFoundException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.Deployment ProcessRuntimeImpl.selectLatestDeployment()"
  })
  void testSelectLatestDeployment_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    when(repositoryService.createDeploymentQuery())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class, () -> processRuntimeImpl.selectLatestDeployment());
    verify(repositoryService).createDeploymentQuery();
  }

  /**
   * Test {@link ProcessRuntimeImpl#internalProcessInstance(String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiObjectNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessRuntimeImpl#internalProcessInstance(String)}
   */
  @Test
  @DisplayName("Test internalProcessInstance(String); then throw ActivitiObjectNotFoundException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstance ProcessRuntimeImpl.internalProcessInstance(String)"})
  void testInternalProcessInstance_thenThrowActivitiObjectNotFoundException() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery())
        .thenThrow(new ActivitiObjectNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiObjectNotFoundException.class,
        () -> processRuntimeImpl.internalProcessInstance("42"));
    verify(runtimeService).createProcessInstanceQuery();
  }
}
