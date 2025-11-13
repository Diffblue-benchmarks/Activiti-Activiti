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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.runtime.shared.security.SecurityManager;
import org.activiti.api.task.model.impl.TaskImpl;
import org.activiti.api.task.model.payloads.CandidateGroupsPayload;
import org.activiti.api.task.model.payloads.CandidateUsersPayload;
import org.activiti.api.task.model.payloads.CreateTaskVariablePayload;
import org.activiti.api.task.model.payloads.GetTaskVariablesPayload;
import org.activiti.api.task.model.payloads.GetTasksPayload;
import org.activiti.api.task.model.payloads.UpdateTaskPayload;
import org.activiti.api.task.model.payloads.UpdateTaskVariablePayload;
import org.activiti.common.util.DateFormatterProvider;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.runtime.api.model.impl.APITaskConverter;
import org.activiti.runtime.api.model.impl.APIVariableInstanceConverter;
import org.activiti.runtime.api.query.impl.PageImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TaskAdminRuntimeImpl.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class TaskAdminRuntimeImplDiffblueTest {
  @MockBean private APITaskConverter aPITaskConverter;

  @MockBean private APIVariableInstanceConverter aPIVariableInstanceConverter;

  @MockBean private SecurityManager securityManager;

  @Autowired private TaskAdminRuntimeImpl taskAdminRuntimeImpl;

  @MockBean private TaskRuntimeHelper taskRuntimeHelper;

  @MockBean private TaskService taskService;

  /**
   * Test {@link TaskAdminRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable},
   * {@code getTasksPayload}.
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page TaskAdminRuntimeImpl.tasks(Pageable, GetTasksPayload)"})
  void testTasksWithPageableGetTasksPayload() {
    // Arrange
    when(taskService.createTaskQuery()).thenThrow(new IllegalStateException());
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskAdminRuntimeImpl.tasks(pageable, new GetTasksPayload()));
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable},
   * {@code getTasksPayload}.
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page TaskAdminRuntimeImpl.tasks(Pageable, GetTasksPayload)"})
  void testTasksWithPageableGetTasksPayload2() {
    // Arrange
    TaskQuery taskQuery = mock(TaskQuery.class);
    when(taskQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(taskService.createTaskQuery()).thenReturn(taskQuery);
    when(aPITaskConverter.from(Mockito.<Collection<Task>>any()))
        .thenThrow(new IllegalStateException());
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskAdminRuntimeImpl.tasks(pageable, new GetTasksPayload()));
    verify(taskService).createTaskQuery();
    verify(taskQuery).listPage(1, 3);
    verify(aPITaskConverter).from(isA(Collection.class));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable},
   * {@code getTasksPayload}.
   *
   * <ul>
   *   <li>Then calls {@link TaskQuery#processInstanceId(String)}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName(
      "Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'; then calls processInstanceId(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page TaskAdminRuntimeImpl.tasks(Pageable, GetTasksPayload)"})
  void testTasksWithPageableGetTasksPayload_thenCallsProcessInstanceId() {
    // Arrange
    TaskQuery taskQuery = mock(TaskQuery.class);
    when(taskQuery.processInstanceId(Mockito.<String>any())).thenThrow(new IllegalStateException());
    when(taskService.createTaskQuery()).thenReturn(taskQuery);
    Pageable pageable = Pageable.of(1, 3);
    GetTasksPayload getTasksPayload = new GetTasksPayload("42", new ArrayList<>(), "42", "42");

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskAdminRuntimeImpl.tasks(pageable, getTasksPayload));
    verify(taskService).createTaskQuery();
    verify(taskQuery).processInstanceId("42");
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable},
   * {@code getTasksPayload}.
   *
   * <ul>
   *   <li>Then return {@link PageImpl}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName(
      "Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'; then return PageImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page TaskAdminRuntimeImpl.tasks(Pageable, GetTasksPayload)"})
  void testTasksWithPageableGetTasksPayload_thenReturnPageImpl() {
    // Arrange
    TaskQuery taskQuery = mock(TaskQuery.class);
    when(taskQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(taskQuery.count()).thenReturn(3L);
    when(taskService.createTaskQuery()).thenReturn(taskQuery);
    when(aPITaskConverter.from(Mockito.<Collection<Task>>any())).thenReturn(new ArrayList<>());
    Pageable pageable = Pageable.of(1, 3);

    // Act
    Page<org.activiti.api.task.model.Task> actualTasksResult =
        taskAdminRuntimeImpl.tasks(pageable, new GetTasksPayload());

    // Assert
    verify(taskService).createTaskQuery();
    verify(taskQuery).count();
    verify(taskQuery).listPage(1, 3);
    verify(aPITaskConverter).from(isA(Collection.class));
    assertTrue(actualTasksResult instanceof PageImpl);
    assertEquals(3, actualTasksResult.getTotalItems());
    assertTrue(actualTasksResult.getContent().isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#tasks(Pageable)} with {@code pageable}.
   *
   * <ul>
   *   <li>Given {@link APITaskConverter} {@link APITaskConverter#from(Collection)} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#tasks(Pageable)}
   */
  @Test
  @DisplayName(
      "Test tasks(Pageable) with 'pageable'; given APITaskConverter from(Collection) throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page TaskAdminRuntimeImpl.tasks(Pageable)"})
  void testTasksWithPageable_givenAPITaskConverterFromThrowIllegalStateException() {
    // Arrange
    TaskQuery taskQuery = mock(TaskQuery.class);
    when(taskQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(taskService.createTaskQuery()).thenReturn(taskQuery);
    when(aPITaskConverter.from(Mockito.<Collection<Task>>any()))
        .thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.tasks(Pageable.of(1, 3)));
    verify(taskService).createTaskQuery();
    verify(taskQuery).listPage(1, 3);
    verify(aPITaskConverter).from(isA(Collection.class));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#tasks(Pageable)} with {@code pageable}.
   *
   * <ul>
   *   <li>Given {@link TaskQuery} {@link TaskQuery#count()} return three.
   *   <li>Then return {@link PageImpl}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#tasks(Pageable)}
   */
  @Test
  @DisplayName(
      "Test tasks(Pageable) with 'pageable'; given TaskQuery count() return three; then return PageImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page TaskAdminRuntimeImpl.tasks(Pageable)"})
  void testTasksWithPageable_givenTaskQueryCountReturnThree_thenReturnPageImpl() {
    // Arrange
    TaskQuery taskQuery = mock(TaskQuery.class);
    when(taskQuery.listPage(anyInt(), anyInt())).thenReturn(new ArrayList<>());
    when(taskQuery.count()).thenReturn(3L);
    when(taskService.createTaskQuery()).thenReturn(taskQuery);
    when(aPITaskConverter.from(Mockito.<Collection<Task>>any())).thenReturn(new ArrayList<>());

    // Act
    Page<org.activiti.api.task.model.Task> actualTasksResult =
        taskAdminRuntimeImpl.tasks(Pageable.of(1, 3));

    // Assert
    verify(taskService).createTaskQuery();
    verify(taskQuery).count();
    verify(taskQuery).listPage(1, 3);
    verify(aPITaskConverter).from(isA(Collection.class));
    assertTrue(actualTasksResult instanceof PageImpl);
    assertEquals(3, actualTasksResult.getTotalItems());
    assertTrue(actualTasksResult.getContent().isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#tasks(Pageable)} with {@code pageable}.
   *
   * <ul>
   *   <li>Given {@link TaskService} {@link TaskService#createTaskQuery()} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#tasks(Pageable)}
   */
  @Test
  @DisplayName(
      "Test tasks(Pageable) with 'pageable'; given TaskService createTaskQuery() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page TaskAdminRuntimeImpl.tasks(Pageable)"})
  void testTasksWithPageable_givenTaskServiceCreateTaskQueryThrowIllegalStateException() {
    // Arrange
    when(taskService.createTaskQuery()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.tasks(Pageable.of(1, 3)));
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#update(UpdateTaskPayload)}.
   *
   * <ul>
   *   <li>Then return {@link TaskImpl#TaskImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#update(UpdateTaskPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateTaskPayload); then return TaskImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskAdminRuntimeImpl.update(UpdateTaskPayload)"
  })
  void testUpdate_thenReturnTaskImpl() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl();
    when(taskRuntimeHelper.applyUpdateTaskPayload(anyBoolean(), Mockito.<UpdateTaskPayload>any()))
        .thenReturn(taskImpl);

    // Act
    org.activiti.api.task.model.Task actualUpdateResult =
        taskAdminRuntimeImpl.update(new UpdateTaskPayload());

    // Assert
    verify(taskRuntimeHelper).applyUpdateTaskPayload(eq(true), isA(UpdateTaskPayload.class));
    assertSame(taskImpl, actualUpdateResult);
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#update(UpdateTaskPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#update(UpdateTaskPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateTaskPayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskAdminRuntimeImpl.update(UpdateTaskPayload)"
  })
  void testUpdate_thenThrowIllegalStateException() {
    // Arrange
    when(taskRuntimeHelper.applyUpdateTaskPayload(anyBoolean(), Mockito.<UpdateTaskPayload>any()))
        .thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskAdminRuntimeImpl.update(new UpdateTaskPayload()));
    verify(taskRuntimeHelper).applyUpdateTaskPayload(eq(true), isA(UpdateTaskPayload.class));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#variables(GetTaskVariablesPayload)}.
   *
   * <ul>
   *   <li>Given {@link APIVariableInstanceConverter} {@link
   *       APIVariableInstanceConverter#from(Collection)} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  @DisplayName(
      "Test variables(GetTaskVariablesPayload); given APIVariableInstanceConverter from(Collection) throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.variables(GetTaskVariablesPayload)"})
  void testVariables_givenAPIVariableInstanceConverterFromThrowIllegalStateException() {
    // Arrange
    when(aPIVariableInstanceConverter.from(Mockito.<Collection<VariableInstance>>any()))
        .thenThrow(new IllegalStateException());
    when(taskRuntimeHelper.getInternalTaskVariables(Mockito.<String>any()))
        .thenReturn(new HashMap<>());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskAdminRuntimeImpl.variables(new GetTaskVariablesPayload()));
    verify(taskRuntimeHelper).getInternalTaskVariables(null);
    verify(aPIVariableInstanceConverter).from(isA(Collection.class));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#variables(GetTaskVariablesPayload)}.
   *
   * <ul>
   *   <li>Given {@link APIVariableInstanceConverter}.
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  @DisplayName(
      "Test variables(GetTaskVariablesPayload); given APIVariableInstanceConverter; then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.variables(GetTaskVariablesPayload)"})
  void testVariables_givenAPIVariableInstanceConverter_thenThrowIllegalStateException() {
    // Arrange
    when(taskRuntimeHelper.getInternalTaskVariables(Mockito.<String>any()))
        .thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskAdminRuntimeImpl.variables(new GetTaskVariablesPayload()));
    verify(taskRuntimeHelper).getInternalTaskVariables(null);
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#variables(GetTaskVariablesPayload)}.
   *
   * <ul>
   *   <li>Then calls {@link TaskServiceImpl#getVariableInstancesLocal(String)}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  @DisplayName(
      "Test variables(GetTaskVariablesPayload); then calls getVariableInstancesLocal(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.variables(GetTaskVariablesPayload)"})
  void testVariables_thenCallsGetVariableInstancesLocal() {
    // Arrange
    TaskServiceImpl taskService = mock(TaskServiceImpl.class);
    when(taskService.getVariableInstancesLocal(Mockito.<String>any())).thenReturn(new HashMap<>());
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    SecurityManager securityManager = mock(SecurityManager.class);
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(taskService, taskConverter, securityManager, taskVariablesValidator);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    TaskAdminRuntimeImpl taskAdminRuntimeImpl =
        new TaskAdminRuntimeImpl(
            taskService2,
            taskConverter2,
            new APIVariableInstanceConverter(),
            taskRuntimeHelper,
            mock(SecurityManager.class));

    // Act
    List<org.activiti.api.model.shared.model.VariableInstance> actualVariablesResult =
        taskAdminRuntimeImpl.variables(new GetTaskVariablesPayload());

    // Assert
    verify(taskService).getVariableInstancesLocal(null);
    assertTrue(actualVariablesResult.isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#variables(GetTaskVariablesPayload)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  @DisplayName("Test variables(GetTaskVariablesPayload); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.variables(GetTaskVariablesPayload)"})
  void testVariables_thenReturnEmpty() {
    // Arrange
    when(aPIVariableInstanceConverter.from(Mockito.<Collection<VariableInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(taskRuntimeHelper.getInternalTaskVariables(Mockito.<String>any()))
        .thenReturn(new HashMap<>());

    // Act
    List<org.activiti.api.model.shared.model.VariableInstance> actualVariablesResult =
        taskAdminRuntimeImpl.variables(new GetTaskVariablesPayload());

    // Assert
    verify(taskRuntimeHelper).getInternalTaskVariables(null);
    verify(aPIVariableInstanceConverter).from(isA(Collection.class));
    assertTrue(actualVariablesResult.isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#createVariable(CreateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskRuntimeHelper} {@link TaskRuntimeHelper#createVariable(boolean,
   *       CreateTaskVariablePayload)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#createVariable(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test createVariable(CreateTaskVariablePayload); given TaskRuntimeHelper createVariable(boolean, CreateTaskVariablePayload) does nothing")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.createVariable(CreateTaskVariablePayload)"})
  void testCreateVariable_givenTaskRuntimeHelperCreateVariableDoesNothing() {
    // Arrange
    doNothing()
        .when(taskRuntimeHelper)
        .createVariable(anyBoolean(), Mockito.<CreateTaskVariablePayload>any());

    // Act
    taskAdminRuntimeImpl.createVariable(new CreateTaskVariablePayload());

    // Assert
    verify(taskRuntimeHelper).createVariable(eq(true), isA(CreateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#createVariable(CreateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Given {@code Value}.
   *   <li>When {@link CreateTaskVariablePayload#CreateTaskVariablePayload()} Value is {@code
   *       Value}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#createVariable(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test createVariable(CreateTaskVariablePayload); given 'Value'; when CreateTaskVariablePayload() Value is 'Value'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.createVariable(CreateTaskVariablePayload)"})
  void testCreateVariable_givenValue_whenCreateTaskVariablePayloadValueIsValue() {
    // Arrange
    TaskService taskService = mock(TaskService.class);
    when(taskService.getVariableInstancesLocal(Mockito.<String>any())).thenReturn(new HashMap<>());
    doNothing()
        .when(taskService)
        .setVariableLocal(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Object>any());

    VariableNameValidator variableNameValidator = mock(VariableNameValidator.class);
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(
            new DateFormatterProvider("2020-03-01"), variableNameValidator);
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(
            taskService, taskConverter, mock(SecurityManager.class), taskVariablesValidator);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    TaskAdminRuntimeImpl taskAdminRuntimeImpl =
        new TaskAdminRuntimeImpl(
            taskService2,
            taskConverter2,
            new APIVariableInstanceConverter(),
            taskRuntimeHelper,
            mock(SecurityManager.class));

    CreateTaskVariablePayload createTaskVariablePayload = new CreateTaskVariablePayload();
    createTaskVariablePayload.setValue("Value");

    // Act
    taskAdminRuntimeImpl.createVariable(createTaskVariablePayload);

    // Assert
    verify(taskService).getVariableInstancesLocal(null);
    verify(taskService).setVariableLocal(isNull(), isNull(), isA(Object.class));
    verify(variableNameValidator).validate(null);
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#createVariable(CreateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Then calls {@link TaskService#getVariableInstancesLocal(String)}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#createVariable(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test createVariable(CreateTaskVariablePayload); then calls getVariableInstancesLocal(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.createVariable(CreateTaskVariablePayload)"})
  void testCreateVariable_thenCallsGetVariableInstancesLocal() {
    // Arrange
    TaskService taskService = mock(TaskService.class);
    when(taskService.getVariableInstancesLocal(Mockito.<String>any())).thenReturn(new HashMap<>());
    doNothing()
        .when(taskService)
        .setVariableLocal(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Object>any());

    VariableNameValidator variableNameValidator = mock(VariableNameValidator.class);
    when(variableNameValidator.validate(Mockito.<String>any())).thenReturn(true);
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(
            new DateFormatterProvider("2020-03-01"), variableNameValidator);
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(
            taskService, taskConverter, mock(SecurityManager.class), taskVariablesValidator);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    TaskAdminRuntimeImpl taskAdminRuntimeImpl =
        new TaskAdminRuntimeImpl(
            taskService2,
            taskConverter2,
            new APIVariableInstanceConverter(),
            taskRuntimeHelper,
            mock(SecurityManager.class));

    // Act
    taskAdminRuntimeImpl.createVariable(new CreateTaskVariablePayload());

    // Assert
    verify(taskService).getVariableInstancesLocal(null);
    verify(taskService).setVariableLocal(isNull(), isNull(), isNull());
    verify(variableNameValidator).validate(null);
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#createVariable(CreateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#createVariable(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test createVariable(CreateTaskVariablePayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.createVariable(CreateTaskVariablePayload)"})
  void testCreateVariable_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(taskRuntimeHelper)
        .createVariable(anyBoolean(), Mockito.<CreateTaskVariablePayload>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskAdminRuntimeImpl.createVariable(new CreateTaskVariablePayload()));
    verify(taskRuntimeHelper).createVariable(eq(true), isA(CreateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskRuntimeHelper} {@link TaskRuntimeHelper#updateVariable(boolean,
   *       UpdateTaskVariablePayload)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test updateVariable(UpdateTaskVariablePayload); given TaskRuntimeHelper updateVariable(boolean, UpdateTaskVariablePayload) does nothing")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.updateVariable(UpdateTaskVariablePayload)"})
  void testUpdateVariable_givenTaskRuntimeHelperUpdateVariableDoesNothing() {
    // Arrange
    doNothing()
        .when(taskRuntimeHelper)
        .updateVariable(anyBoolean(), Mockito.<UpdateTaskVariablePayload>any());

    // Act
    taskAdminRuntimeImpl.updateVariable(new UpdateTaskVariablePayload());

    // Assert
    verify(taskRuntimeHelper).updateVariable(eq(true), isA(UpdateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test updateVariable(UpdateTaskVariablePayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.updateVariable(UpdateTaskVariablePayload)"})
  void testUpdateVariable_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(taskRuntimeHelper)
        .updateVariable(anyBoolean(), Mockito.<UpdateTaskVariablePayload>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskAdminRuntimeImpl.updateVariable(new UpdateTaskVariablePayload()));
    verify(taskRuntimeHelper).updateVariable(eq(true), isA(UpdateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskService} {@link TaskService#addCandidateUser(String, String)} does
   *       nothing.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName(
      "Test addCandidateUsers(CandidateUsersPayload); given TaskService addCandidateUser(String, String) does nothing")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.addCandidateUsers(CandidateUsersPayload)"})
  void testAddCandidateUsers_givenTaskServiceAddCandidateUserDoesNothing() {
    // Arrange
    doNothing().when(taskService).addCandidateUser(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("2020-03-01");
    CandidateUsersPayload candidateUsersPayload = new CandidateUsersPayload("42", candidateUsers);

    // Act
    taskAdminRuntimeImpl.addCandidateUsers(candidateUsersPayload);

    // Assert
    verify(taskService).addCandidateUser("42", "2020-03-01");
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskService}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName(
      "Test addCandidateUsers(CandidateUsersPayload); given TaskService; then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.addCandidateUsers(CandidateUsersPayload)"})
  void testAddCandidateUsers_givenTaskService_thenDoesNotThrow() {
    // Arrange
    CandidateUsersPayload candidateUsersPayload =
        new CandidateUsersPayload("42", new ArrayList<>());

    // Act and Assert
    assertDoesNotThrow(() -> taskAdminRuntimeImpl.addCandidateUsers(candidateUsersPayload));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName("Test addCandidateUsers(CandidateUsersPayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.addCandidateUsers(CandidateUsersPayload)"})
  void testAddCandidateUsers_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(taskService)
        .addCandidateUser(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("2020-03-01");
    CandidateUsersPayload candidateUsersPayload = new CandidateUsersPayload("42", candidateUsers);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskAdminRuntimeImpl.addCandidateUsers(candidateUsersPayload));
    verify(taskService).addCandidateUser("42", "2020-03-01");
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}.
   *
   * <ul>
   *   <li>When {@link CandidateUsersPayload#CandidateUsersPayload()}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#addCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName(
      "Test addCandidateUsers(CandidateUsersPayload); when CandidateUsersPayload(); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.addCandidateUsers(CandidateUsersPayload)"})
  void testAddCandidateUsers_whenCandidateUsersPayload_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(() -> taskAdminRuntimeImpl.addCandidateUsers(new CandidateUsersPayload()));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskService} {@link TaskService#deleteCandidateUser(String, String)} does
   *       nothing.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName(
      "Test deleteCandidateUsers(CandidateUsersPayload); given TaskService deleteCandidateUser(String, String) does nothing")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.deleteCandidateUsers(CandidateUsersPayload)"})
  void testDeleteCandidateUsers_givenTaskServiceDeleteCandidateUserDoesNothing() {
    // Arrange
    doNothing().when(taskService).deleteCandidateUser(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("2020-03-01");
    CandidateUsersPayload candidateUsersPayload = new CandidateUsersPayload("42", candidateUsers);

    // Act
    taskAdminRuntimeImpl.deleteCandidateUsers(candidateUsersPayload);

    // Assert
    verify(taskService).deleteCandidateUser("42", "2020-03-01");
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskService}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName(
      "Test deleteCandidateUsers(CandidateUsersPayload); given TaskService; then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.deleteCandidateUsers(CandidateUsersPayload)"})
  void testDeleteCandidateUsers_givenTaskService_thenDoesNotThrow() {
    // Arrange
    CandidateUsersPayload candidateUsersPayload =
        new CandidateUsersPayload("42", new ArrayList<>());

    // Act and Assert
    assertDoesNotThrow(() -> taskAdminRuntimeImpl.deleteCandidateUsers(candidateUsersPayload));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName("Test deleteCandidateUsers(CandidateUsersPayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.deleteCandidateUsers(CandidateUsersPayload)"})
  void testDeleteCandidateUsers_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(taskService)
        .deleteCandidateUser(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("2020-03-01");
    CandidateUsersPayload candidateUsersPayload = new CandidateUsersPayload("42", candidateUsers);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskAdminRuntimeImpl.deleteCandidateUsers(candidateUsersPayload));
    verify(taskService).deleteCandidateUser("42", "2020-03-01");
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}.
   *
   * <ul>
   *   <li>When {@link CandidateUsersPayload#CandidateUsersPayload()}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#deleteCandidateUsers(CandidateUsersPayload)}
   */
  @Test
  @DisplayName(
      "Test deleteCandidateUsers(CandidateUsersPayload); when CandidateUsersPayload(); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.deleteCandidateUsers(CandidateUsersPayload)"})
  void testDeleteCandidateUsers_whenCandidateUsersPayload_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(
        () -> taskAdminRuntimeImpl.deleteCandidateUsers(new CandidateUsersPayload()));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskService} {@link TaskService#addCandidateGroup(String, String)} does
   *       nothing.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName(
      "Test addCandidateGroups(CandidateGroupsPayload); given TaskService addCandidateGroup(String, String) does nothing")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.addCandidateGroups(CandidateGroupsPayload)"})
  void testAddCandidateGroups_givenTaskServiceAddCandidateGroupDoesNothing() {
    // Arrange
    doNothing().when(taskService).addCandidateGroup(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("2020-03-01");
    CandidateGroupsPayload candidateGroupsPayload =
        new CandidateGroupsPayload("42", candidateGroups);

    // Act
    taskAdminRuntimeImpl.addCandidateGroups(candidateGroupsPayload);

    // Assert
    verify(taskService).addCandidateGroup("42", "2020-03-01");
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskService}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName(
      "Test addCandidateGroups(CandidateGroupsPayload); given TaskService; then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.addCandidateGroups(CandidateGroupsPayload)"})
  void testAddCandidateGroups_givenTaskService_thenDoesNotThrow() {
    // Arrange
    CandidateGroupsPayload candidateGroupsPayload =
        new CandidateGroupsPayload("42", new ArrayList<>());

    // Act and Assert
    assertDoesNotThrow(() -> taskAdminRuntimeImpl.addCandidateGroups(candidateGroupsPayload));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName("Test addCandidateGroups(CandidateGroupsPayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.addCandidateGroups(CandidateGroupsPayload)"})
  void testAddCandidateGroups_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(taskService)
        .addCandidateGroup(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("2020-03-01");
    CandidateGroupsPayload candidateGroupsPayload =
        new CandidateGroupsPayload("42", candidateGroups);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskAdminRuntimeImpl.addCandidateGroups(candidateGroupsPayload));
    verify(taskService).addCandidateGroup("42", "2020-03-01");
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}.
   *
   * <ul>
   *   <li>When {@link CandidateGroupsPayload#CandidateGroupsPayload()}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#addCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName(
      "Test addCandidateGroups(CandidateGroupsPayload); when CandidateGroupsPayload(); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.addCandidateGroups(CandidateGroupsPayload)"})
  void testAddCandidateGroups_whenCandidateGroupsPayload_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(() -> taskAdminRuntimeImpl.addCandidateGroups(new CandidateGroupsPayload()));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskService} {@link TaskService#deleteCandidateGroup(String, String)} does
   *       nothing.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName(
      "Test deleteCandidateGroups(CandidateGroupsPayload); given TaskService deleteCandidateGroup(String, String) does nothing")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.deleteCandidateGroups(CandidateGroupsPayload)"})
  void testDeleteCandidateGroups_givenTaskServiceDeleteCandidateGroupDoesNothing() {
    // Arrange
    doNothing()
        .when(taskService)
        .deleteCandidateGroup(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("2020-03-01");
    CandidateGroupsPayload candidateGroupsPayload =
        new CandidateGroupsPayload("42", candidateGroups);

    // Act
    taskAdminRuntimeImpl.deleteCandidateGroups(candidateGroupsPayload);

    // Assert
    verify(taskService).deleteCandidateGroup("42", "2020-03-01");
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskService}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName(
      "Test deleteCandidateGroups(CandidateGroupsPayload); given TaskService; then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.deleteCandidateGroups(CandidateGroupsPayload)"})
  void testDeleteCandidateGroups_givenTaskService_thenDoesNotThrow() {
    // Arrange
    CandidateGroupsPayload candidateGroupsPayload =
        new CandidateGroupsPayload("42", new ArrayList<>());

    // Act and Assert
    assertDoesNotThrow(() -> taskAdminRuntimeImpl.deleteCandidateGroups(candidateGroupsPayload));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName(
      "Test deleteCandidateGroups(CandidateGroupsPayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.deleteCandidateGroups(CandidateGroupsPayload)"})
  void testDeleteCandidateGroups_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(taskService)
        .deleteCandidateGroup(Mockito.<String>any(), Mockito.<String>any());

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("2020-03-01");
    CandidateGroupsPayload candidateGroupsPayload =
        new CandidateGroupsPayload("42", candidateGroups);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskAdminRuntimeImpl.deleteCandidateGroups(candidateGroupsPayload));
    verify(taskService).deleteCandidateGroup("42", "2020-03-01");
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}.
   *
   * <ul>
   *   <li>When {@link CandidateGroupsPayload#CandidateGroupsPayload()}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskAdminRuntimeImpl#deleteCandidateGroups(CandidateGroupsPayload)}
   */
  @Test
  @DisplayName(
      "Test deleteCandidateGroups(CandidateGroupsPayload); when CandidateGroupsPayload(); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskAdminRuntimeImpl.deleteCandidateGroups(CandidateGroupsPayload)"})
  void testDeleteCandidateGroups_whenCandidateGroupsPayload_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(
        () -> taskAdminRuntimeImpl.deleteCandidateGroups(new CandidateGroupsPayload()));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#userCandidates(String)}.
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.userCandidates(String)"})
  void testUserCandidates() {
    // Arrange
    when(taskService.getIdentityLinksForTask(Mockito.<String>any()))
        .thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.userCandidates("42"));
    verify(taskService).getIdentityLinksForTask("42");
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#userCandidates(String)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl} (default
   *       constructor).
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName(
      "Test userCandidates(String); given ArrayList() add IdentityLinkEntityImpl (default constructor); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.userCandidates(String)"})
  void testUserCandidates_givenArrayListAddIdentityLinkEntityImpl_thenReturnEmpty() {
    // Arrange
    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);

    // Act
    List<String> actualUserCandidatesResult = taskAdminRuntimeImpl.userCandidates("42");

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    assertTrue(actualUserCandidatesResult.isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#userCandidates(String)}.
   *
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} {@link IdentityLinkEntityImpl#getType()} return
   *       {@code Type}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName(
      "Test userCandidates(String); given IdentityLinkEntityImpl getType() return 'Type'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.userCandidates(String)"})
  void testUserCandidates_givenIdentityLinkEntityImplGetTypeReturnType_thenReturnEmpty() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = mock(IdentityLinkEntityImpl.class);
    when(identityLinkEntityImpl.getType()).thenReturn("Type");
    when(identityLinkEntityImpl.getUserId()).thenReturn("42");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(identityLinkEntityImpl);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);

    // Act
    List<String> actualUserCandidatesResult = taskAdminRuntimeImpl.userCandidates("42");

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    verify(identityLinkEntityImpl).getType();
    verify(identityLinkEntityImpl).getUserId();
    assertTrue(actualUserCandidatesResult.isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#userCandidates(String)}.
   *
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} {@link IdentityLinkEntityImpl#getType()} throw
   *       {@link IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName(
      "Test userCandidates(String); given IdentityLinkEntityImpl getType() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.userCandidates(String)"})
  void testUserCandidates_givenIdentityLinkEntityImplGetTypeThrowIllegalStateException() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = mock(IdentityLinkEntityImpl.class);
    when(identityLinkEntityImpl.getType()).thenThrow(new IllegalStateException());
    when(identityLinkEntityImpl.getUserId()).thenReturn("42");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(identityLinkEntityImpl);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.userCandidates("42"));
    verify(taskService).getIdentityLinksForTask("42");
    verify(identityLinkEntityImpl).getType();
    verify(identityLinkEntityImpl).getUserId();
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#userCandidates(String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.userCandidates(String)"})
  void testUserCandidates_thenReturnEmpty() {
    // Arrange
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<String> actualUserCandidatesResult = taskAdminRuntimeImpl.userCandidates("42");

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    assertTrue(actualUserCandidatesResult.isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#userCandidates(String)}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String); then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.userCandidates(String)"})
  void testUserCandidates_thenReturnSizeIsOne() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = mock(IdentityLinkEntityImpl.class);
    when(identityLinkEntityImpl.getType()).thenReturn("candidate");
    when(identityLinkEntityImpl.getUserId()).thenReturn("42");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(identityLinkEntityImpl);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);

    // Act
    List<String> actualUserCandidatesResult = taskAdminRuntimeImpl.userCandidates("42");

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    verify(identityLinkEntityImpl).getType();
    verify(identityLinkEntityImpl, atLeast(1)).getUserId();
    assertEquals(1, actualUserCandidatesResult.size());
    assertEquals("42", actualUserCandidatesResult.get(0));
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#groupCandidates(String)}.
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName("Test groupCandidates(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates() {
    // Arrange
    when(taskService.getIdentityLinksForTask(Mockito.<String>any()))
        .thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.groupCandidates("42"));
    verify(taskService).getIdentityLinksForTask("42");
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#groupCandidates(String)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl} (default
   *       constructor).
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName(
      "Test groupCandidates(String); given ArrayList() add IdentityLinkEntityImpl (default constructor); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates_givenArrayListAddIdentityLinkEntityImpl_thenReturnEmpty() {
    // Arrange
    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);

    // Act
    List<String> actualGroupCandidatesResult = taskAdminRuntimeImpl.groupCandidates("42");

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    assertTrue(actualGroupCandidatesResult.isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#groupCandidates(String)}.
   *
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} {@link IdentityLinkEntityImpl#getType()} return
   *       {@code Type}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName(
      "Test groupCandidates(String); given IdentityLinkEntityImpl getType() return 'Type'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates_givenIdentityLinkEntityImplGetTypeReturnType_thenReturnEmpty() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = mock(IdentityLinkEntityImpl.class);
    when(identityLinkEntityImpl.getType()).thenReturn("Type");
    when(identityLinkEntityImpl.getGroupId()).thenReturn("42");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(identityLinkEntityImpl);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);

    // Act
    List<String> actualGroupCandidatesResult = taskAdminRuntimeImpl.groupCandidates("42");

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    verify(identityLinkEntityImpl).getGroupId();
    verify(identityLinkEntityImpl).getType();
    assertTrue(actualGroupCandidatesResult.isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#groupCandidates(String)}.
   *
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} {@link IdentityLinkEntityImpl#getType()} throw
   *       {@link IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName(
      "Test groupCandidates(String); given IdentityLinkEntityImpl getType() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates_givenIdentityLinkEntityImplGetTypeThrowIllegalStateException() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = mock(IdentityLinkEntityImpl.class);
    when(identityLinkEntityImpl.getType()).thenThrow(new IllegalStateException());
    when(identityLinkEntityImpl.getGroupId()).thenReturn("42");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(identityLinkEntityImpl);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskAdminRuntimeImpl.groupCandidates("42"));
    verify(taskService).getIdentityLinksForTask("42");
    verify(identityLinkEntityImpl).getGroupId();
    verify(identityLinkEntityImpl).getType();
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#groupCandidates(String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName("Test groupCandidates(String); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates_thenReturnEmpty() {
    // Arrange
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<String> actualGroupCandidatesResult = taskAdminRuntimeImpl.groupCandidates("42");

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    assertTrue(actualGroupCandidatesResult.isEmpty());
  }

  /**
   * Test {@link TaskAdminRuntimeImpl#groupCandidates(String)}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link TaskAdminRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName("Test groupCandidates(String); then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskAdminRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates_thenReturnSizeIsOne() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = mock(IdentityLinkEntityImpl.class);
    when(identityLinkEntityImpl.getType()).thenReturn("candidate");
    when(identityLinkEntityImpl.getGroupId()).thenReturn("42");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(identityLinkEntityImpl);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);

    // Act
    List<String> actualGroupCandidatesResult = taskAdminRuntimeImpl.groupCandidates("42");

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    verify(identityLinkEntityImpl, atLeast(1)).getGroupId();
    verify(identityLinkEntityImpl).getType();
    assertEquals(1, actualGroupCandidatesResult.size());
    assertEquals("42", actualGroupCandidatesResult.get(0));
  }
}
