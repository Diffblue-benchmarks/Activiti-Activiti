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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.ProcessDefinitionQueryImpl;
import org.activiti.engine.impl.ProcessInstanceQueryImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
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

@ContextConfiguration(classes = {ProcessAdminRuntimeImpl.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ProcessAdminRuntimeImplDiffblueTest {
  @MockBean private APIProcessDefinitionConverter aPIProcessDefinitionConverter;

  @MockBean private APIProcessInstanceConverter aPIProcessInstanceConverter;

  @MockBean private APIVariableInstanceConverter aPIVariableInstanceConverter;

  @Autowired private ProcessAdminRuntimeImpl processAdminRuntimeImpl;

  @MockBean private ProcessVariablesPayloadValidator processVariablesPayloadValidator;

  @MockBean private RepositoryService repositoryService;

  @MockBean private RuntimeService runtimeService;

  /**
   * Test {@link ProcessAdminRuntimeImpl#processDefinition(String)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processDefinition(String)}
   */
  @Test
  @DisplayName("Test processDefinition(String); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessDefinition ProcessAdminRuntimeImpl.processDefinition(String)"
  })
  void testProcessDefinition_thenThrowIllegalStateException() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> processAdminRuntimeImpl.processDefinition("42"));
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable)} with {@code pageable}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable) with 'pageable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessAdminRuntimeImpl.processDefinitions(Pageable)"})
  void testProcessDefinitionsWithPageable() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.processDefinitions(Pageable.of(1, 3)));
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable)} with {@code pageable}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable) with 'pageable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessAdminRuntimeImpl.processDefinitions(Pageable)"})
  void testProcessDefinitionsWithPageable2() {
    // Arrange
    ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class);
    when(processDefinitionQuery.list()).thenReturn(new ArrayList<>());
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(aPIProcessDefinitionConverter.from(Mockito.<Collection<ProcessDefinition>>any()))
        .thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.processDefinitions(Pageable.of(1, 3)));
    verify(repositoryService).createProcessDefinitionQuery();
    verify(processDefinitionQuery).list();
    verify(aPIProcessDefinitionConverter).from(isA(Collection.class));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.processDefinitions(Pageable.of(1, 3), null));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload2() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery()).thenThrow(new IllegalStateException());
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            processAdminRuntimeImpl.processDefinitions(
                pageable, new GetProcessDefinitionsPayload()));
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload3() {
    // Arrange
    ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class);
    when(processDefinitionQuery.list()).thenReturn(new ArrayList<>());
    when(processDefinitionQuery.count()).thenReturn(3L);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(aPIProcessDefinitionConverter.from(Mockito.<Collection<ProcessDefinition>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);

    // Act
    Page<org.activiti.api.process.model.ProcessDefinition> actualProcessDefinitionsResult =
        processAdminRuntimeImpl.processDefinitions(pageable, new GetProcessDefinitionsPayload());

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
   * Test {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload4() {
    // Arrange
    ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class);
    when(processDefinitionQuery.list()).thenReturn(new ArrayList<>());
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(aPIProcessDefinitionConverter.from(Mockito.<Collection<ProcessDefinition>>any()))
        .thenThrow(new IllegalStateException());
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            processAdminRuntimeImpl.processDefinitions(
                pageable, new GetProcessDefinitionsPayload()));
    verify(repositoryService).createProcessDefinitionQuery();
    verify(processDefinitionQuery).list();
    verify(aPIProcessDefinitionConverter).from(isA(Collection.class));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload5() {
    // Arrange
    ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class);
    when(processDefinitionQuery.list()).thenReturn(new ArrayList<>());
    when(processDefinitionQuery.count()).thenReturn(3L);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(aPIProcessDefinitionConverter.from(Mockito.<Collection<ProcessDefinition>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);
    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        new GetProcessDefinitionsPayload("42", new HashSet<>());

    // Act
    Page<org.activiti.api.process.model.ProcessDefinition> actualProcessDefinitionsResult =
        processAdminRuntimeImpl.processDefinitions(pageable, getProcessDefinitionsPayload);

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
   * Test {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload6() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(mock(ProcessDefinitionQuery.class));
    Pageable pageable = Pageable.of(1, 3);

    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.hasDefinitionKeys()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.processDefinitions(pageable, getProcessDefinitionsPayload));
    verify(getProcessDefinitionsPayload).hasDefinitionKeys();
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload7() {
    // Arrange
    ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class);
    when(processDefinitionQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenReturn(new ProcessDefinitionQueryImpl());
    when(processDefinitionQuery.list()).thenReturn(new ArrayList<>());
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(aPIProcessDefinitionConverter.from(Mockito.<Collection<ProcessDefinition>>any()))
        .thenThrow(new NotFoundException("An error occurred"));
    Pageable pageable = Pageable.of(1, 3);

    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.getProcessDefinitionKeys()).thenReturn(new HashSet<>());
    when(getProcessDefinitionsPayload.hasDefinitionKeys()).thenReturn(true);

    // Act and Assert
    assertThrows(
        NotFoundException.class,
        () -> processAdminRuntimeImpl.processDefinitions(pageable, getProcessDefinitionsPayload));
    verify(getProcessDefinitionsPayload).getProcessDefinitionKeys();
    verify(getProcessDefinitionsPayload).hasDefinitionKeys();
    verify(repositoryService).createProcessDefinitionQuery();
    verify(processDefinitionQuery).list();
    verify(processDefinitionQuery).processDefinitionKeys(isA(Set.class));
    verify(aPIProcessDefinitionConverter).from(isA(Collection.class));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload8() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery())
        .thenReturn(mock(ProcessDefinitionQuery.class));
    Pageable pageable = Pageable.of(1, 3);

    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.getProcessDefinitionKeys())
        .thenThrow(new IllegalStateException());
    when(getProcessDefinitionsPayload.hasDefinitionKeys()).thenReturn(true);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.processDefinitions(pageable, getProcessDefinitionsPayload));
    verify(getProcessDefinitionsPayload).getProcessDefinitionKeys();
    verify(getProcessDefinitionsPayload).hasDefinitionKeys();
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload9() {
    // Arrange
    ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class);
    when(processDefinitionQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenThrow(new IllegalStateException());
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    Pageable pageable = Pageable.of(1, 3);

    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.getProcessDefinitionKeys()).thenReturn(new HashSet<>());
    when(getProcessDefinitionsPayload.hasDefinitionKeys()).thenReturn(true);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.processDefinitions(pageable, getProcessDefinitionsPayload));
    verify(getProcessDefinitionsPayload).getProcessDefinitionKeys();
    verify(getProcessDefinitionsPayload).hasDefinitionKeys();
    verify(repositoryService).createProcessDefinitionQuery();
    verify(processDefinitionQuery).processDefinitionKeys(isA(Set.class));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable, GetProcessDefinitionsPayload)}
   * with {@code pageable}, {@code getProcessDefinitionsPayload}.
   *
   * <ul>
   *   <li>Given {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable,
   * GetProcessDefinitionsPayload)}
   */
  @Test
  @DisplayName(
      "Test processDefinitions(Pageable, GetProcessDefinitionsPayload) with 'pageable', 'getProcessDefinitionsPayload'; given 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processDefinitions(Pageable, GetProcessDefinitionsPayload)"
  })
  void testProcessDefinitionsWithPageableGetProcessDefinitionsPayload_givenFalse() {
    // Arrange
    ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class);
    when(processDefinitionQuery.list()).thenReturn(new ArrayList<>());
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(aPIProcessDefinitionConverter.from(Mockito.<Collection<ProcessDefinition>>any()))
        .thenThrow(new NotFoundException("An error occurred"));
    Pageable pageable = Pageable.of(1, 3);

    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        mock(GetProcessDefinitionsPayload.class);
    when(getProcessDefinitionsPayload.hasDefinitionKeys()).thenReturn(false);

    // Act and Assert
    assertThrows(
        NotFoundException.class,
        () -> processAdminRuntimeImpl.processDefinitions(pageable, getProcessDefinitionsPayload));
    verify(getProcessDefinitionsPayload).hasDefinitionKeys();
    verify(repositoryService).createProcessDefinitionQuery();
    verify(processDefinitionQuery).list();
    verify(aPIProcessDefinitionConverter).from(isA(Collection.class));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable)} with {@code pageable}.
   *
   * <ul>
   *   <li>Then return {@link PageImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processDefinitions(Pageable)}
   */
  @Test
  @DisplayName("Test processDefinitions(Pageable) with 'pageable'; then return PageImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessAdminRuntimeImpl.processDefinitions(Pageable)"})
  void testProcessDefinitionsWithPageable_thenReturnPageImpl() {
    // Arrange
    ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class);
    when(processDefinitionQuery.list()).thenReturn(new ArrayList<>());
    when(processDefinitionQuery.count()).thenReturn(3L);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(aPIProcessDefinitionConverter.from(Mockito.<Collection<ProcessDefinition>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    Page<org.activiti.api.process.model.ProcessDefinition> actualProcessDefinitionsResult =
        processAdminRuntimeImpl.processDefinitions(Pageable.of(1, 3));

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
   * Test {@link ProcessAdminRuntimeImpl#start(StartMessagePayload)} with {@code messagePayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#start(StartMessagePayload)}
   */
  @Test
  @DisplayName("Test start(StartMessagePayload) with 'messagePayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessAdminRuntimeImpl.start(StartMessagePayload)"
  })
  void testStartWithMessagePayload() {
    // Arrange
    when(runtimeService.startProcessInstanceByMessage(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(aPIProcessInstanceConverter.from(Mockito.<ProcessInstance>any()))
        .thenThrow(new IllegalStateException());
    doNothing()
        .when(processVariablesPayloadValidator)
        .checkStartMessagePayloadVariables(
            Mockito.<StartMessagePayload>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.start(new StartMessagePayload()));
    verify(runtimeService).startProcessInstanceByMessage(isNull(), isNull(), isA(Map.class));
    verify(processVariablesPayloadValidator)
        .checkStartMessagePayloadVariables(isA(StartMessagePayload.class), isNull());
    verify(aPIProcessInstanceConverter).from(isA(ProcessInstance.class));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#start(StartMessagePayload)} with {@code messagePayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#start(StartMessagePayload)}
   */
  @Test
  @DisplayName("Test start(StartMessagePayload) with 'messagePayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessAdminRuntimeImpl.start(StartMessagePayload)"
  })
  void testStartWithMessagePayload2() {
    // Arrange
    when(runtimeService.startProcessInstanceByMessage(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<Map<String, Object>>any()))
        .thenThrow(new IllegalStateException());
    doNothing()
        .when(processVariablesPayloadValidator)
        .checkStartMessagePayloadVariables(
            Mockito.<StartMessagePayload>any(), Mockito.<String>any());
    StartMessagePayload messagePayload =
        new StartMessagePayload("Name", "Business Key", new HashMap<>());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> processAdminRuntimeImpl.start(messagePayload));
    verify(runtimeService)
        .startProcessInstanceByMessage(eq("Name"), eq("Business Key"), isA(Map.class));
    verify(processVariablesPayloadValidator)
        .checkStartMessagePayloadVariables(isA(StartMessagePayload.class), isNull());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#start(StartMessagePayload)} with {@code messagePayload}.
   *
   * <ul>
   *   <li>Given {@link RuntimeService}.
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#start(StartMessagePayload)}
   */
  @Test
  @DisplayName(
      "Test start(StartMessagePayload) with 'messagePayload'; given RuntimeService; then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessAdminRuntimeImpl.start(StartMessagePayload)"
  })
  void testStartWithMessagePayload_givenRuntimeService_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(processVariablesPayloadValidator)
        .checkStartMessagePayloadVariables(
            Mockito.<StartMessagePayload>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.start(new StartMessagePayload()));
    verify(processVariablesPayloadValidator)
        .checkStartMessagePayloadVariables(isA(StartMessagePayload.class), isNull());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#start(StartMessagePayload)} with {@code messagePayload}.
   *
   * <ul>
   *   <li>Then return {@link ProcessInstanceImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#start(StartMessagePayload)}
   */
  @Test
  @DisplayName(
      "Test start(StartMessagePayload) with 'messagePayload'; then return ProcessInstanceImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessAdminRuntimeImpl.start(StartMessagePayload)"
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
        processAdminRuntimeImpl.start(new StartMessagePayload());

    // Assert
    verify(runtimeService).startProcessInstanceByMessage(isNull(), isNull(), isA(Map.class));
    verify(processVariablesPayloadValidator)
        .checkStartMessagePayloadVariables(isA(StartMessagePayload.class), isNull());
    verify(aPIProcessInstanceConverter).from(isA(ProcessInstance.class));
    assertSame(processInstanceImpl, actualStartResult);
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#start(StartProcessPayload)} with {@code
   * startProcessPayload}.
   *
   * <ul>
   *   <li>Then calls {@link RepositoryService#createProcessDefinitionQuery()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#start(StartProcessPayload)}
   */
  @Test
  @DisplayName(
      "Test start(StartProcessPayload) with 'startProcessPayload'; then calls createProcessDefinitionQuery()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessAdminRuntimeImpl.start(StartProcessPayload)"
  })
  void testStartWithStartProcessPayload_thenCallsCreateProcessDefinitionQuery() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery()).thenThrow(new IllegalStateException());
    StartProcessPayload startProcessPayload =
        new StartProcessPayload(
            "42",
            "At least Process Definition Id or Key needs to be provided to start a process",
            "At least Process Definition Id or Key needs to be provided to start a process",
            "At least Process Definition Id or Key needs to be provided to start a process",
            new HashMap<>());

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> processAdminRuntimeImpl.start(startProcessPayload));
    verify(repositoryService).createProcessDefinitionQuery();
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#start(StartProcessPayload)} with {@code
   * startProcessPayload}.
   *
   * <ul>
   *   <li>When {@link StartProcessPayload#StartProcessPayload()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#start(StartProcessPayload)}
   */
  @Test
  @DisplayName(
      "Test start(StartProcessPayload) with 'startProcessPayload'; when StartProcessPayload()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessAdminRuntimeImpl.start(StartProcessPayload)"
  })
  void testStartWithStartProcessPayload_whenStartProcessPayload() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.start(new StartProcessPayload()));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processInstances(Pageable)} with {@code pageable}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processInstances(Pageable)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable) with 'pageable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessAdminRuntimeImpl.processInstances(Pageable)"})
  void testProcessInstancesWithPageable() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.processInstances(Pageable.of(1, 3)));
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processInstances(Pageable)} with {@code pageable}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processInstances(Pageable)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable) with 'pageable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessAdminRuntimeImpl.processInstances(Pageable)"})
  void testProcessInstancesWithPageable2() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.processInstances(Pageable.of(1, 3)));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).listPage(1, 3);
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery()).thenThrow(new IllegalStateException());
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.processInstances(pageable, getProcessInstancesPayload));
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload2() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult =
        processAdminRuntimeImpl.processInstances(pageable, getProcessInstancesPayload);

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).processInstanceBusinessKey("Business Key");
    verify(processInstanceQuery).superProcessInstanceId("42");
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload3() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenThrow(new IllegalStateException());
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.processInstances(pageable, getProcessInstancesPayload));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).processInstanceBusinessKey("Business Key");
    verify(processInstanceQuery).superProcessInstanceId("42");
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload4() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(false);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult =
        processAdminRuntimeImpl.processInstances(pageable, getProcessInstancesPayload);

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).processInstanceBusinessKey("Business Key");
    verify(processInstanceQuery).superProcessInstanceId("42");
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload5() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey(null);
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult =
        processAdminRuntimeImpl.processInstances(pageable, getProcessInstancesPayload);

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).superProcessInstanceId("42");
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload6() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId(null);
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult =
        processAdminRuntimeImpl.processInstances(pageable, getProcessInstancesPayload);

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).processInstanceBusinessKey("Business Key");
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload7() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("");

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(processDefinitionKeys);
    getProcessInstancesPayload.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult =
        processAdminRuntimeImpl.processInstances(pageable, getProcessInstancesPayload);

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).processDefinitionKeys(isA(Set.class));
    verify(processInstanceQuery).processInstanceBusinessKey("Business Key");
    verify(processInstanceQuery).superProcessInstanceId("42");
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload8() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenThrow(new IllegalStateException());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    Pageable pageable = Pageable.of(1, 3);

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("");

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(processDefinitionKeys);
    getProcessInstancesPayload.setSuspendedOnly(true);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.processInstances(pageable, getProcessInstancesPayload));
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).processDefinitionKeys(isA(Set.class));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload9() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.processDefinitionKeys(Mockito.<Set<String>>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.processInstanceBusinessKey(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("");

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(processDefinitionKeys);
    getProcessInstancesPayload.setSuspendedOnly(false);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult =
        processAdminRuntimeImpl.processInstances(pageable, getProcessInstancesPayload);

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).processDefinitionKeys(isA(Set.class));
    verify(processInstanceQuery).processInstanceBusinessKey("Business Key");
    verify(processInstanceQuery).superProcessInstanceId("42");
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processInstances(Pageable, GetProcessInstancesPayload)}
   * with {@code pageable}, {@code getProcessInstancesPayload}.
   *
   * <ul>
   *   <li>Given empty string.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processInstances(Pageable,
   * GetProcessInstancesPayload)}
   */
  @Test
  @DisplayName(
      "Test processInstances(Pageable, GetProcessInstancesPayload) with 'pageable', 'getProcessInstancesPayload'; given empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Page ProcessAdminRuntimeImpl.processInstances(Pageable, GetProcessInstancesPayload)"
  })
  void testProcessInstancesWithPageableGetProcessInstancesPayload_givenEmptyString() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(processInstanceQuery.active()).thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.superProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ProcessInstanceQueryImpl());
    when(processInstanceQuery.suspended()).thenReturn(new ProcessInstanceQueryImpl());
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);

    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult =
        processAdminRuntimeImpl.processInstances(pageable, getProcessInstancesPayload);

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(1, 3);
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).superProcessInstanceId("42");
    verify(processInstanceQuery).suspended();
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processInstances(Pageable)} with {@code pageable}.
   *
   * <ul>
   *   <li>Then return {@link PageImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processInstances(Pageable)}
   */
  @Test
  @DisplayName("Test processInstances(Pageable) with 'pageable'; then return PageImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page ProcessAdminRuntimeImpl.processInstances(Pageable)"})
  void testProcessInstancesWithPageable_thenReturnPageImpl() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(processInstanceQuery.count()).thenReturn(3L);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(aPIProcessInstanceConverter.from(Mockito.<Collection<ProcessInstance>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    Page<org.activiti.api.process.model.ProcessInstance> actualProcessInstancesResult =
        processAdminRuntimeImpl.processInstances(Pageable.of(1, 3));

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).count();
    verify(processInstanceQuery).listPage(1, 3);
    verify(aPIProcessInstanceConverter).from(isA(Collection.class));
    assertTrue(actualProcessInstancesResult instanceof PageImpl);
    assertEquals(3, actualProcessInstancesResult.getTotalItems());
    assertTrue(actualProcessInstancesResult.getContent().isEmpty());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#processInstance(String)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#processInstance(String)}
   */
  @Test
  @DisplayName("Test processInstance(String); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessAdminRuntimeImpl.processInstance(String)"
  })
  void testProcessInstance_thenThrowIllegalStateException() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> processAdminRuntimeImpl.processInstance("42"));
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#delete(DeleteProcessPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#delete(DeleteProcessPayload)}
   */
  @Test
  @DisplayName("Test delete(DeleteProcessPayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessAdminRuntimeImpl.delete(DeleteProcessPayload)"
  })
  void testDelete_thenThrowIllegalStateException() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.delete(new DeleteProcessPayload()));
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#signal(SignalPayload)}.
   *
   * <ul>
   *   <li>Then calls {@link
   *       ProcessVariablesPayloadValidator#checkSignalPayloadVariables(SignalPayload, String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#signal(SignalPayload)}
   */
  @Test
  @DisplayName(
      "Test signal(SignalPayload); then calls checkSignalPayloadVariables(SignalPayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessAdminRuntimeImpl.signal(SignalPayload)"})
  void testSignal_thenCallsCheckSignalPayloadVariables() {
    // Arrange
    doNothing()
        .when(processVariablesPayloadValidator)
        .checkSignalPayloadVariables(Mockito.<SignalPayload>any(), Mockito.<String>any());

    // Act
    processAdminRuntimeImpl.signal(new SignalPayload());

    // Assert
    verify(processVariablesPayloadValidator)
        .checkSignalPayloadVariables(isA(SignalPayload.class), isNull());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#signal(SignalPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#signal(SignalPayload)}
   */
  @Test
  @DisplayName("Test signal(SignalPayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessAdminRuntimeImpl.signal(SignalPayload)"})
  void testSignal_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(processVariablesPayloadValidator)
        .checkSignalPayloadVariables(Mockito.<SignalPayload>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> processAdminRuntimeImpl.signal(new SignalPayload()));
    verify(processVariablesPayloadValidator)
        .checkSignalPayloadVariables(isA(SignalPayload.class), isNull());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#suspend(SuspendProcessPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#suspend(SuspendProcessPayload)}
   */
  @Test
  @DisplayName("Test suspend(SuspendProcessPayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessAdminRuntimeImpl.suspend(SuspendProcessPayload)"
  })
  void testSuspend_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(runtimeService)
        .suspendProcessInstanceById(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.suspend(new SuspendProcessPayload()));
    verify(runtimeService).suspendProcessInstanceById(null);
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#resume(ResumeProcessPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#resume(ResumeProcessPayload)}
   */
  @Test
  @DisplayName("Test resume(ResumeProcessPayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessAdminRuntimeImpl.resume(ResumeProcessPayload)"
  })
  void testResume_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(runtimeService)
        .activateProcessInstanceById(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.resume(new ResumeProcessPayload()));
    verify(runtimeService).activateProcessInstanceById(null);
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#update(UpdateProcessPayload)}.
   *
   * <ul>
   *   <li>Then calls {@link RuntimeService#createProcessInstanceQuery()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#update(UpdateProcessPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateProcessPayload); then calls createProcessInstanceQuery()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessAdminRuntimeImpl.update(UpdateProcessPayload)"
  })
  void testUpdate_thenCallsCreateProcessInstanceQuery() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.update(new UpdateProcessPayload()));
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#update(UpdateProcessPayload)}.
   *
   * <ul>
   *   <li>Then calls {@link RuntimeService#setProcessInstanceName(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#update(UpdateProcessPayload)}
   */
  @Test
  @DisplayName(
      "Test update(UpdateProcessPayload); then calls setProcessInstanceName(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessAdminRuntimeImpl.update(UpdateProcessPayload)"
  })
  void testUpdate_thenCallsSetProcessInstanceName() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(runtimeService)
        .setProcessInstanceName(Mockito.<String>any(), Mockito.<String>any());

    UpdateProcessPayload updateProcessPayload =
        new UpdateProcessPayload(
            "42",
            "Process instance id is null",
            "The characteristics of someone or something",
            null);
    updateProcessPayload.setProcessInstanceId("Process Instance Id");

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> processAdminRuntimeImpl.update(updateProcessPayload));
    verify(runtimeService)
        .setProcessInstanceName("Process Instance Id", "Process instance id is null");
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#update(UpdateProcessPayload)}.
   *
   * <ul>
   *   <li>Then calls {@link RuntimeService#updateBusinessKey(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#update(UpdateProcessPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateProcessPayload); then calls updateBusinessKey(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.ProcessInstance ProcessAdminRuntimeImpl.update(UpdateProcessPayload)"
  })
  void testUpdate_thenCallsUpdateBusinessKey() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(runtimeService)
        .updateBusinessKey(Mockito.<String>any(), Mockito.<String>any());

    UpdateProcessPayload updateProcessPayload =
        new UpdateProcessPayload(
            "42",
            "Process instance id is null",
            "The characteristics of someone or something",
            "Process instance id is null");
    updateProcessPayload.setProcessInstanceId("Process Instance Id");

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> processAdminRuntimeImpl.update(updateProcessPayload));
    verify(runtimeService).updateBusinessKey("Process Instance Id", "Process instance id is null");
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#setVariables(SetProcessVariablesPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#setVariables(SetProcessVariablesPayload)}
   */
  @Test
  @DisplayName("Test setVariables(SetProcessVariablesPayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessAdminRuntimeImpl.setVariables(SetProcessVariablesPayload)"})
  void testSetVariables_thenThrowIllegalStateException() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.setVariables(new SetProcessVariablesPayload()));
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#variables(GetVariablesPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#variables(GetVariablesPayload)}
   */
  @Test
  @DisplayName("Test variables(GetVariablesPayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List ProcessAdminRuntimeImpl.variables(GetVariablesPayload)"})
  void testVariables_thenThrowIllegalStateException() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.variables(new GetVariablesPayload()));
    verify(runtimeService).createProcessInstanceQuery();
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}.
   *
   * <ul>
   *   <li>Then calls {@link RuntimeService#removeVariables(String, Collection)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessAdminRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}
   */
  @Test
  @DisplayName(
      "Test removeVariables(RemoveProcessVariablesPayload); then calls removeVariables(String, Collection)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessAdminRuntimeImpl.removeVariables(RemoveProcessVariablesPayload)"})
  void testRemoveVariables_thenCallsRemoveVariables() {
    // Arrange
    doNothing()
        .when(runtimeService)
        .removeVariables(Mockito.<String>any(), Mockito.<Collection<String>>any());

    // Act
    processAdminRuntimeImpl.removeVariables(new RemoveProcessVariablesPayload());

    // Assert
    verify(runtimeService).removeVariables(isNull(), isA(Collection.class));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessAdminRuntimeImpl#removeVariables(RemoveProcessVariablesPayload)}
   */
  @Test
  @DisplayName(
      "Test removeVariables(RemoveProcessVariablesPayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessAdminRuntimeImpl.removeVariables(RemoveProcessVariablesPayload)"})
  void testRemoveVariables_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(runtimeService)
        .removeVariables(Mockito.<String>any(), Mockito.<Collection<String>>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.removeVariables(new RemoveProcessVariablesPayload()));
    verify(runtimeService).removeVariables(isNull(), isA(Collection.class));
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#receive(ReceiveMessagePayload)}.
   *
   * <ul>
   *   <li>Then calls {@link
   *       ProcessVariablesPayloadValidator#checkReceiveMessagePayloadVariables(ReceiveMessagePayload,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#receive(ReceiveMessagePayload)}
   */
  @Test
  @DisplayName(
      "Test receive(ReceiveMessagePayload); then calls checkReceiveMessagePayloadVariables(ReceiveMessagePayload, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessAdminRuntimeImpl.receive(ReceiveMessagePayload)"})
  void testReceive_thenCallsCheckReceiveMessagePayloadVariables() {
    // Arrange
    doNothing()
        .when(processVariablesPayloadValidator)
        .checkReceiveMessagePayloadVariables(
            Mockito.<ReceiveMessagePayload>any(), Mockito.<String>any());

    // Act
    processAdminRuntimeImpl.receive(new ReceiveMessagePayload());

    // Assert
    verify(processVariablesPayloadValidator)
        .checkReceiveMessagePayloadVariables(isA(ReceiveMessagePayload.class), isNull());
  }

  /**
   * Test {@link ProcessAdminRuntimeImpl#receive(ReceiveMessagePayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessAdminRuntimeImpl#receive(ReceiveMessagePayload)}
   */
  @Test
  @DisplayName("Test receive(ReceiveMessagePayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessAdminRuntimeImpl.receive(ReceiveMessagePayload)"})
  void testReceive_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(processVariablesPayloadValidator)
        .checkReceiveMessagePayloadVariables(
            Mockito.<ReceiveMessagePayload>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> processAdminRuntimeImpl.receive(new ReceiveMessagePayload()));
    verify(processVariablesPayloadValidator)
        .checkReceiveMessagePayloadVariables(isA(ReceiveMessagePayload.class), isNull());
  }
}
