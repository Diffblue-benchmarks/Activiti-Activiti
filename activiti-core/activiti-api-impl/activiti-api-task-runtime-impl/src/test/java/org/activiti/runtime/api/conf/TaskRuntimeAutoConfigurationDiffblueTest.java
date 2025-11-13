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
package org.activiti.runtime.api.conf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.runtime.shared.events.VariableEventListener;
import org.activiti.api.runtime.shared.security.SecurityManager;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.api.task.runtime.conf.TaskRuntimeConfiguration;
import org.activiti.api.task.runtime.events.TaskActivatedEvent;
import org.activiti.api.task.runtime.events.TaskAssignedEvent;
import org.activiti.api.task.runtime.events.TaskCancelledEvent;
import org.activiti.api.task.runtime.events.TaskCandidateGroupAddedEvent;
import org.activiti.api.task.runtime.events.TaskCandidateGroupRemovedEvent;
import org.activiti.api.task.runtime.events.TaskCandidateUserAddedEvent;
import org.activiti.api.task.runtime.events.TaskCandidateUserRemovedEvent;
import org.activiti.api.task.runtime.events.TaskCompletedEvent;
import org.activiti.api.task.runtime.events.TaskCreatedEvent;
import org.activiti.api.task.runtime.events.TaskSuspendedEvent;
import org.activiti.api.task.runtime.events.TaskUpdatedEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.activiti.common.util.DateFormatterProvider;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.runtime.api.conf.impl.TaskRuntimeConfigurationImpl;
import org.activiti.runtime.api.event.impl.ToAPITaskAssignedEventConverter;
import org.activiti.runtime.api.event.impl.ToAPITaskCandidateGroupAddedEventConverter;
import org.activiti.runtime.api.event.impl.ToAPITaskCandidateUserAddedEventConverter;
import org.activiti.runtime.api.event.impl.ToAPITaskCreatedEventConverter;
import org.activiti.runtime.api.event.impl.ToAPITaskUpdatedEventConverter;
import org.activiti.runtime.api.impl.TaskAdminRuntimeImpl;
import org.activiti.runtime.api.impl.TaskRuntimeHelper;
import org.activiti.runtime.api.impl.TaskRuntimeImpl;
import org.activiti.runtime.api.impl.TaskVariablesPayloadValidator;
import org.activiti.runtime.api.impl.VariableNameValidator;
import org.activiti.runtime.api.model.impl.APITaskCandidateGroupConverter;
import org.activiti.runtime.api.model.impl.APITaskCandidateUserConverter;
import org.activiti.runtime.api.model.impl.APITaskConverter;
import org.activiti.runtime.api.model.impl.APIVariableInstanceConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TaskRuntimeAutoConfigurationDiffblueTest {
  /**
   * Test {@link TaskRuntimeAutoConfiguration#taskRuntime(TaskService, SecurityManager,
   * APITaskConverter, APIVariableInstanceConverter, TaskRuntimeConfiguration, TaskRuntimeHelper)}.
   *
   * <p>Method under test: {@link TaskRuntimeAutoConfiguration#taskRuntime(TaskService,
   * SecurityManager, APITaskConverter, APIVariableInstanceConverter, TaskRuntimeConfiguration,
   * TaskRuntimeHelper)}
   */
  @Test
  @DisplayName(
      "Test taskRuntime(TaskService, SecurityManager, APITaskConverter, APIVariableInstanceConverter, TaskRuntimeConfiguration, TaskRuntimeHelper)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TaskRuntime TaskRuntimeAutoConfiguration.taskRuntime(TaskService, SecurityManager, APITaskConverter, APIVariableInstanceConverter, TaskRuntimeConfiguration, TaskRuntimeHelper)"
  })
  void testTaskRuntime() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager = mock(SecurityManager.class);
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    SecurityManager securityManager2 = mock(SecurityManager.class);
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(
            taskService2, taskConverter2, securityManager2, taskVariablesValidator);

    // Act
    TaskRuntime actualTaskRuntimeResult =
        taskRuntimeAutoConfiguration.taskRuntime(
            taskService,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    // Assert
    TaskRuntimeConfiguration configurationResult = actualTaskRuntimeResult.configuration();
    assertTrue(configurationResult instanceof TaskRuntimeConfigurationImpl);
    assertTrue(actualTaskRuntimeResult instanceof TaskRuntimeImpl);
    assertSame(configuration, configurationResult);
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#taskAdminRuntime(TaskService, APITaskConverter,
   * APIVariableInstanceConverter, TaskRuntimeHelper, SecurityManager)}.
   *
   * <p>Method under test: {@link TaskRuntimeAutoConfiguration#taskAdminRuntime(TaskService,
   * APITaskConverter, APIVariableInstanceConverter, TaskRuntimeHelper, SecurityManager)}
   */
  @Test
  @DisplayName(
      "Test taskAdminRuntime(TaskService, APITaskConverter, APIVariableInstanceConverter, TaskRuntimeHelper, SecurityManager)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.runtime.TaskAdminRuntime TaskRuntimeAutoConfiguration.taskAdminRuntime(TaskService, APITaskConverter, APIVariableInstanceConverter, TaskRuntimeHelper, SecurityManager)"
  })
  void testTaskAdminRuntime() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    SecurityManager securityManager = mock(SecurityManager.class);
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(
            taskService2, taskConverter2, securityManager, taskVariablesValidator);

    // Act and Assert
    assertTrue(
        taskRuntimeAutoConfiguration.taskAdminRuntime(
                taskService,
                taskConverter,
                variableInstanceConverter,
                taskRuntimeHelper,
                mock(SecurityManager.class))
            instanceof TaskAdminRuntimeImpl);
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#taskVariablesValidator(DateFormatterProvider,
   * VariableNameValidator)}.
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#taskVariablesValidator(DateFormatterProvider,
   * VariableNameValidator)}
   */
  @Test
  @DisplayName("Test taskVariablesValidator(DateFormatterProvider, VariableNameValidator)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TaskVariablesPayloadValidator TaskRuntimeAutoConfiguration.taskVariablesValidator(DateFormatterProvider, VariableNameValidator)"
  })
  void testTaskVariablesValidator() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");

    // Act and Assert
    assertNull(
        taskRuntimeAutoConfiguration
            .taskVariablesValidator(dateFormatterProvider, new VariableNameValidator())
            .handlePayloadVariables(null));
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}.
   *
   * <ul>
   *   <li>Given {@link TaskRuntimeEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test taskRuntimeConfiguration(List, List); given TaskRuntimeEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TaskRuntimeConfiguration TaskRuntimeAutoConfiguration.taskRuntimeConfiguration(List, List)"
  })
  void testTaskRuntimeConfiguration_givenTaskRuntimeEventListener() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    taskRuntimeEventListeners.add(mock(TaskRuntimeEventListener.class));

    // Act and Assert
    assertTrue(
        taskRuntimeAutoConfiguration.taskRuntimeConfiguration(
                taskRuntimeEventListeners, new ArrayList<>())
            instanceof TaskRuntimeConfigurationImpl);
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}.
   *
   * <ul>
   *   <li>Given {@link TaskRuntimeEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test taskRuntimeConfiguration(List, List); given TaskRuntimeEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TaskRuntimeConfiguration TaskRuntimeAutoConfiguration.taskRuntimeConfiguration(List, List)"
  })
  void testTaskRuntimeConfiguration_givenTaskRuntimeEventListener2() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    taskRuntimeEventListeners.add(mock(TaskRuntimeEventListener.class));
    taskRuntimeEventListeners.add(mock(TaskRuntimeEventListener.class));

    // Act and Assert
    assertTrue(
        taskRuntimeAutoConfiguration.taskRuntimeConfiguration(
                taskRuntimeEventListeners, new ArrayList<>())
            instanceof TaskRuntimeConfigurationImpl);
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}.
   *
   * <ul>
   *   <li>Given {@link VariableEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test taskRuntimeConfiguration(List, List); given VariableEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TaskRuntimeConfiguration TaskRuntimeAutoConfiguration.taskRuntimeConfiguration(List, List)"
  })
  void testTaskRuntimeConfiguration_givenVariableEventListener() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();

    ArrayList<VariableEventListener<?>> variableEventListeners = new ArrayList<>();
    variableEventListeners.add(mock(VariableEventListener.class));

    // Act and Assert
    assertTrue(
        taskRuntimeAutoConfiguration.taskRuntimeConfiguration(
                taskRuntimeEventListeners, variableEventListeners)
            instanceof TaskRuntimeConfigurationImpl);
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}.
   *
   * <ul>
   *   <li>Given {@link VariableEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test taskRuntimeConfiguration(List, List); given VariableEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TaskRuntimeConfiguration TaskRuntimeAutoConfiguration.taskRuntimeConfiguration(List, List)"
  })
  void testTaskRuntimeConfiguration_givenVariableEventListener2() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();

    ArrayList<VariableEventListener<?>> variableEventListeners = new ArrayList<>();
    variableEventListeners.add(mock(VariableEventListener.class));
    variableEventListeners.add(mock(VariableEventListener.class));

    // Act and Assert
    assertTrue(
        taskRuntimeAutoConfiguration.taskRuntimeConfiguration(
                taskRuntimeEventListeners, variableEventListeners)
            instanceof TaskRuntimeConfigurationImpl);
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test taskRuntimeConfiguration(List, List); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TaskRuntimeConfiguration TaskRuntimeAutoConfiguration.taskRuntimeConfiguration(List, List)"
  })
  void testTaskRuntimeConfiguration_whenArrayList() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();

    // Act and Assert
    assertTrue(
        taskRuntimeAutoConfiguration.taskRuntimeConfiguration(
                taskRuntimeEventListeners, new ArrayList<>())
            instanceof TaskRuntimeConfigurationImpl);
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test taskRuntimeConfiguration(List, List); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TaskRuntimeConfiguration TaskRuntimeAutoConfiguration.taskRuntimeConfiguration(List, List)"
  })
  void testTaskRuntimeConfiguration_whenNull() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    // Act and Assert
    assertTrue(
        taskRuntimeAutoConfiguration.taskRuntimeConfiguration(new ArrayList<>(), null)
            instanceof TaskRuntimeConfigurationImpl);
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCreatedEventListener(RuntimeService, List,
   * ToAPITaskCreatedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCreatedEventListener(RuntimeService, List,
   * ToAPITaskCreatedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCreatedEventListener(RuntimeService, List, ToAPITaskCreatedEventConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCreatedEventListener(RuntimeService, List, ToAPITaskCreatedEventConverter)"
  })
  void testRegisterTaskCreatedEventListener_thenArrayListEmpty() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<TaskRuntimeEventListener<TaskCreatedEvent>> listeners = new ArrayList<>();
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCreatedEventListener(
            runtimeService, listeners, new ToAPITaskCreatedEventConverter(taskConverter))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCreatedEventListener(RuntimeService, List,
   * ToAPITaskCreatedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCreatedEventListener(RuntimeService, List,
   * ToAPITaskCreatedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCreatedEventListener(RuntimeService, List, ToAPITaskCreatedEventConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCreatedEventListener(RuntimeService, List, ToAPITaskCreatedEventConverter)"
  })
  void testRegisterTaskCreatedEventListener_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCreatedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCreatedEventListener(
            runtimeService, listeners, new ToAPITaskCreatedEventConverter(taskConverter))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, listeners.size());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCreatedEventListener(RuntimeService, List,
   * ToAPITaskCreatedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCreatedEventListener(RuntimeService, List,
   * ToAPITaskCreatedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCreatedEventListener(RuntimeService, List, ToAPITaskCreatedEventConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCreatedEventListener(RuntimeService, List, ToAPITaskCreatedEventConverter)"
  })
  void testRegisterTaskCreatedEventListener_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCreatedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));
    listeners.add(mock(TaskRuntimeEventListener.class));
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCreatedEventListener(
            runtimeService, listeners, new ToAPITaskCreatedEventConverter(taskConverter))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, listeners.size());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCreatedEventListener(RuntimeService, List,
   * ToAPITaskCreatedEventConverter)}.
   *
   * <ul>
   *   <li>When {@link RuntimeServiceImpl} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCreatedEventListener(RuntimeService, List,
   * ToAPITaskCreatedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCreatedEventListener(RuntimeService, List, ToAPITaskCreatedEventConverter); when RuntimeServiceImpl (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCreatedEventListener(RuntimeService, List, ToAPITaskCreatedEventConverter)"
  })
  void testRegisterTaskCreatedEventListener_whenRuntimeServiceImpl_thenArrayListEmpty() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<TaskRuntimeEventListener<TaskCreatedEvent>> listeners = new ArrayList<>();
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    // Act
    taskRuntimeAutoConfiguration.registerTaskCreatedEventListener(
        runtimeService, listeners, new ToAPITaskCreatedEventConverter(taskConverter));

    // Assert that nothing has changed
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskUpdatedEventListener(RuntimeService, List,
   * ToAPITaskUpdatedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskUpdatedEventListener(RuntimeService, List,
   * ToAPITaskUpdatedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskUpdatedEventListener(RuntimeService, List, ToAPITaskUpdatedEventConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskUpdatedEventListener(RuntimeService, List, ToAPITaskUpdatedEventConverter)"
  })
  void testRegisterTaskUpdatedEventListener_thenArrayListEmpty() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<TaskRuntimeEventListener<TaskUpdatedEvent>> listeners = new ArrayList<>();
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskUpdatedEventListener(
            runtimeService, listeners, new ToAPITaskUpdatedEventConverter(taskConverter))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskUpdatedEventListener(RuntimeService, List,
   * ToAPITaskUpdatedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskUpdatedEventListener(RuntimeService, List,
   * ToAPITaskUpdatedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskUpdatedEventListener(RuntimeService, List, ToAPITaskUpdatedEventConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskUpdatedEventListener(RuntimeService, List, ToAPITaskUpdatedEventConverter)"
  })
  void testRegisterTaskUpdatedEventListener_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskUpdatedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskUpdatedEventListener(
            runtimeService, listeners, new ToAPITaskUpdatedEventConverter(taskConverter))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, listeners.size());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskUpdatedEventListener(RuntimeService, List,
   * ToAPITaskUpdatedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskUpdatedEventListener(RuntimeService, List,
   * ToAPITaskUpdatedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskUpdatedEventListener(RuntimeService, List, ToAPITaskUpdatedEventConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskUpdatedEventListener(RuntimeService, List, ToAPITaskUpdatedEventConverter)"
  })
  void testRegisterTaskUpdatedEventListener_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskUpdatedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));
    listeners.add(mock(TaskRuntimeEventListener.class));
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskUpdatedEventListener(
            runtimeService, listeners, new ToAPITaskUpdatedEventConverter(taskConverter))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, listeners.size());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskUpdatedEventListener(RuntimeService, List,
   * ToAPITaskUpdatedEventConverter)}.
   *
   * <ul>
   *   <li>When {@link RuntimeServiceImpl} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskUpdatedEventListener(RuntimeService, List,
   * ToAPITaskUpdatedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskUpdatedEventListener(RuntimeService, List, ToAPITaskUpdatedEventConverter); when RuntimeServiceImpl (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskUpdatedEventListener(RuntimeService, List, ToAPITaskUpdatedEventConverter)"
  })
  void testRegisterTaskUpdatedEventListener_whenRuntimeServiceImpl_thenArrayListEmpty() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<TaskRuntimeEventListener<TaskUpdatedEvent>> listeners = new ArrayList<>();
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    // Act
    taskRuntimeAutoConfiguration.registerTaskUpdatedEventListener(
        runtimeService, listeners, new ToAPITaskUpdatedEventConverter(taskConverter));

    // Assert that nothing has changed
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskAssignedEventListener(RuntimeService,
   * List, ToAPITaskAssignedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskAssignedEventListener(RuntimeService, List,
   * ToAPITaskAssignedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskAssignedEventListener(RuntimeService, List, ToAPITaskAssignedEventConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskAssignedEventListener(RuntimeService, List, ToAPITaskAssignedEventConverter)"
  })
  void testRegisterTaskAssignedEventListener_thenArrayListEmpty() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<TaskRuntimeEventListener<TaskAssignedEvent>> listeners = new ArrayList<>();
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskAssignedEventListener(
            runtimeService, listeners, new ToAPITaskAssignedEventConverter(taskConverter))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskAssignedEventListener(RuntimeService,
   * List, ToAPITaskAssignedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskAssignedEventListener(RuntimeService, List,
   * ToAPITaskAssignedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskAssignedEventListener(RuntimeService, List, ToAPITaskAssignedEventConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskAssignedEventListener(RuntimeService, List, ToAPITaskAssignedEventConverter)"
  })
  void testRegisterTaskAssignedEventListener_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskAssignedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskAssignedEventListener(
            runtimeService, listeners, new ToAPITaskAssignedEventConverter(taskConverter))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, listeners.size());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskAssignedEventListener(RuntimeService,
   * List, ToAPITaskAssignedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskAssignedEventListener(RuntimeService, List,
   * ToAPITaskAssignedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskAssignedEventListener(RuntimeService, List, ToAPITaskAssignedEventConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskAssignedEventListener(RuntimeService, List, ToAPITaskAssignedEventConverter)"
  })
  void testRegisterTaskAssignedEventListener_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskAssignedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));
    listeners.add(mock(TaskRuntimeEventListener.class));
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskAssignedEventListener(
            runtimeService, listeners, new ToAPITaskAssignedEventConverter(taskConverter))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, listeners.size());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskAssignedEventListener(RuntimeService,
   * List, ToAPITaskAssignedEventConverter)}.
   *
   * <ul>
   *   <li>When {@link RuntimeServiceImpl} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskAssignedEventListener(RuntimeService, List,
   * ToAPITaskAssignedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskAssignedEventListener(RuntimeService, List, ToAPITaskAssignedEventConverter); when RuntimeServiceImpl (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskAssignedEventListener(RuntimeService, List, ToAPITaskAssignedEventConverter)"
  })
  void testRegisterTaskAssignedEventListener_whenRuntimeServiceImpl_thenArrayListEmpty() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<TaskRuntimeEventListener<TaskAssignedEvent>> listeners = new ArrayList<>();
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    // Act
    taskRuntimeAutoConfiguration.registerTaskAssignedEventListener(
        runtimeService, listeners, new ToAPITaskAssignedEventConverter(taskConverter));

    // Assert that nothing has changed
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCompletedEventListener(RuntimeService,
   * List, APITaskConverter, SecurityManager)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCompletedEventListener(RuntimeService, List,
   * APITaskConverter, SecurityManager)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCompletedEventListener(RuntimeService, List, APITaskConverter, SecurityManager); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCompletedEventListener(RuntimeService, List, APITaskConverter, SecurityManager)"
  })
  void testRegisterTaskCompletedEventListener_thenArrayListEmpty() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<TaskRuntimeEventListener<TaskCompletedEvent>> listeners = new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCompletedEventListener(
            runtimeService,
            listeners,
            new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration())),
            mock(SecurityManager.class))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCompletedEventListener(RuntimeService,
   * List, APITaskConverter, SecurityManager)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCompletedEventListener(RuntimeService, List,
   * APITaskConverter, SecurityManager)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCompletedEventListener(RuntimeService, List, APITaskConverter, SecurityManager); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCompletedEventListener(RuntimeService, List, APITaskConverter, SecurityManager)"
  })
  void testRegisterTaskCompletedEventListener_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCompletedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCompletedEventListener(
            runtimeService,
            listeners,
            new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration())),
            mock(SecurityManager.class))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, listeners.size());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCompletedEventListener(RuntimeService,
   * List, APITaskConverter, SecurityManager)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCompletedEventListener(RuntimeService, List,
   * APITaskConverter, SecurityManager)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCompletedEventListener(RuntimeService, List, APITaskConverter, SecurityManager); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCompletedEventListener(RuntimeService, List, APITaskConverter, SecurityManager)"
  })
  void testRegisterTaskCompletedEventListener_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCompletedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCompletedEventListener(
            runtimeService,
            listeners,
            new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration())),
            mock(SecurityManager.class))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, listeners.size());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCompletedEventListener(RuntimeService,
   * List, APITaskConverter, SecurityManager)}.
   *
   * <ul>
   *   <li>When {@link RuntimeServiceImpl} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCompletedEventListener(RuntimeService, List,
   * APITaskConverter, SecurityManager)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCompletedEventListener(RuntimeService, List, APITaskConverter, SecurityManager); when RuntimeServiceImpl (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCompletedEventListener(RuntimeService, List, APITaskConverter, SecurityManager)"
  })
  void testRegisterTaskCompletedEventListener_whenRuntimeServiceImpl_thenArrayListEmpty() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<TaskRuntimeEventListener<TaskCompletedEvent>> listeners = new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration.registerTaskCompletedEventListener(
        runtimeService,
        listeners,
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration())),
        mock(SecurityManager.class));

    // Assert that nothing has changed
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCancelledEventListener(RuntimeService,
   * List, APITaskConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCancelledEventListener(RuntimeService, List,
   * APITaskConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCancelledEventListener(RuntimeService, List, APITaskConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCancelledEventListener(RuntimeService, List, APITaskConverter)"
  })
  void testRegisterTaskCancelledEventListener_thenArrayListEmpty() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<TaskRuntimeEventListener<TaskCancelledEvent>> taskRuntimeEventListeners =
        new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCancelledEventListener(
            runtimeService,
            taskRuntimeEventListeners,
            new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration())))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(taskRuntimeEventListeners.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCancelledEventListener(RuntimeService,
   * List, APITaskConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCancelledEventListener(RuntimeService, List,
   * APITaskConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCancelledEventListener(RuntimeService, List, APITaskConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCancelledEventListener(RuntimeService, List, APITaskConverter)"
  })
  void testRegisterTaskCancelledEventListener_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCancelledEvent>> taskRuntimeEventListeners =
        new ArrayList<>();
    taskRuntimeEventListeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCancelledEventListener(
            runtimeService,
            taskRuntimeEventListeners,
            new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration())))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, taskRuntimeEventListeners.size());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCancelledEventListener(RuntimeService,
   * List, APITaskConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCancelledEventListener(RuntimeService, List,
   * APITaskConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCancelledEventListener(RuntimeService, List, APITaskConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCancelledEventListener(RuntimeService, List, APITaskConverter)"
  })
  void testRegisterTaskCancelledEventListener_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCancelledEvent>> taskRuntimeEventListeners =
        new ArrayList<>();
    taskRuntimeEventListeners.add(mock(TaskRuntimeEventListener.class));
    taskRuntimeEventListeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCancelledEventListener(
            runtimeService,
            taskRuntimeEventListeners,
            new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration())))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, taskRuntimeEventListeners.size());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCancelledEventListener(RuntimeService,
   * List, APITaskConverter)}.
   *
   * <ul>
   *   <li>When {@link RuntimeServiceImpl} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCancelledEventListener(RuntimeService, List,
   * APITaskConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCancelledEventListener(RuntimeService, List, APITaskConverter); when RuntimeServiceImpl (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCancelledEventListener(RuntimeService, List, APITaskConverter)"
  })
  void testRegisterTaskCancelledEventListener_whenRuntimeServiceImpl_thenArrayListEmpty() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<TaskRuntimeEventListener<TaskCancelledEvent>> taskRuntimeEventListeners =
        new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration.registerTaskCancelledEventListener(
        runtimeService,
        taskRuntimeEventListeners,
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration())));

    // Assert that nothing has changed
    assertTrue(taskRuntimeEventListeners.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskSuspendedListener(RuntimeService, List,
   * APITaskConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskSuspendedListener(RuntimeService, List,
   * APITaskConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskSuspendedListener(RuntimeService, List, APITaskConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskSuspendedListener(RuntimeService, List, APITaskConverter)"
  })
  void testRegisterTaskSuspendedListener_thenArrayListEmpty() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<TaskRuntimeEventListener<TaskSuspendedEvent>> listeners = new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskSuspendedListener(
            runtimeService,
            listeners,
            new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration())))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskSuspendedListener(RuntimeService, List,
   * APITaskConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskSuspendedListener(RuntimeService, List,
   * APITaskConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskSuspendedListener(RuntimeService, List, APITaskConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskSuspendedListener(RuntimeService, List, APITaskConverter)"
  })
  void testRegisterTaskSuspendedListener_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskSuspendedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskSuspendedListener(
            runtimeService,
            listeners,
            new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration())))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, listeners.size());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskSuspendedListener(RuntimeService, List,
   * APITaskConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskSuspendedListener(RuntimeService, List,
   * APITaskConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskSuspendedListener(RuntimeService, List, APITaskConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskSuspendedListener(RuntimeService, List, APITaskConverter)"
  })
  void testRegisterTaskSuspendedListener_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskSuspendedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskSuspendedListener(
            runtimeService,
            listeners,
            new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration())))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, listeners.size());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskSuspendedListener(RuntimeService, List,
   * APITaskConverter)}.
   *
   * <ul>
   *   <li>When {@link RuntimeServiceImpl} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskSuspendedListener(RuntimeService, List,
   * APITaskConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskSuspendedListener(RuntimeService, List, APITaskConverter); when RuntimeServiceImpl (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskSuspendedListener(RuntimeService, List, APITaskConverter)"
  })
  void testRegisterTaskSuspendedListener_whenRuntimeServiceImpl_thenArrayListEmpty() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<TaskRuntimeEventListener<TaskSuspendedEvent>> listeners = new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration.registerTaskSuspendedListener(
        runtimeService,
        listeners,
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration())));

    // Assert that nothing has changed
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskActivatedListener(RuntimeService, List,
   * APITaskConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskActivatedListener(RuntimeService, List,
   * APITaskConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskActivatedListener(RuntimeService, List, APITaskConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskActivatedListener(RuntimeService, List, APITaskConverter)"
  })
  void testRegisterTaskActivatedListener_thenArrayListEmpty() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<TaskRuntimeEventListener<TaskActivatedEvent>> listeners = new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskActivatedListener(
            runtimeService,
            listeners,
            new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration())))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskActivatedListener(RuntimeService, List,
   * APITaskConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskActivatedListener(RuntimeService, List,
   * APITaskConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskActivatedListener(RuntimeService, List, APITaskConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskActivatedListener(RuntimeService, List, APITaskConverter)"
  })
  void testRegisterTaskActivatedListener_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskActivatedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskActivatedListener(
            runtimeService,
            listeners,
            new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration())))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, listeners.size());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskActivatedListener(RuntimeService, List,
   * APITaskConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskActivatedListener(RuntimeService, List,
   * APITaskConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskActivatedListener(RuntimeService, List, APITaskConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskActivatedListener(RuntimeService, List, APITaskConverter)"
  })
  void testRegisterTaskActivatedListener_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskActivatedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskActivatedListener(
            runtimeService,
            listeners,
            new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration())))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, listeners.size());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskActivatedListener(RuntimeService, List,
   * APITaskConverter)}.
   *
   * <ul>
   *   <li>When {@link RuntimeServiceImpl} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskActivatedListener(RuntimeService, List,
   * APITaskConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskActivatedListener(RuntimeService, List, APITaskConverter); when RuntimeServiceImpl (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskActivatedListener(RuntimeService, List, APITaskConverter)"
  })
  void testRegisterTaskActivatedListener_whenRuntimeServiceImpl_thenArrayListEmpty() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<TaskRuntimeEventListener<TaskActivatedEvent>> listeners = new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration.registerTaskActivatedListener(
        runtimeService,
        listeners,
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration())));

    // Assert that nothing has changed
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#apiTaskUpdatedEventConverter(APITaskConverter)}.
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#apiTaskUpdatedEventConverter(APITaskConverter)}
   */
  @Test
  @DisplayName("Test apiTaskUpdatedEventConverter(APITaskConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ToAPITaskUpdatedEventConverter TaskRuntimeAutoConfiguration.apiTaskUpdatedEventConverter(APITaskConverter)"
  })
  void testApiTaskUpdatedEventConverter() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    // Act
    ToAPITaskUpdatedEventConverter actualApiTaskUpdatedEventConverterResult =
        taskRuntimeAutoConfiguration.apiTaskUpdatedEventConverter(
            new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration())));

    // Assert
    assertFalse(
        actualApiTaskUpdatedEventConverterResult
            .from(
                new ActivitiProcessCancelledEventImpl(
                    ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
            .isPresent());
  }

  /**
   * Test {@link
   * TaskRuntimeAutoConfiguration#toAPITaskCandidateUserAddedEventConverter(APITaskCandidateUserConverter)}.
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#toAPITaskCandidateUserAddedEventConverter(APITaskCandidateUserConverter)}
   */
  @Test
  @DisplayName("Test toAPITaskCandidateUserAddedEventConverter(APITaskCandidateUserConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ToAPITaskCandidateUserAddedEventConverter TaskRuntimeAutoConfiguration.toAPITaskCandidateUserAddedEventConverter(APITaskCandidateUserConverter)"
  })
  void testToAPITaskCandidateUserAddedEventConverter() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    // Act
    ToAPITaskCandidateUserAddedEventConverter
        actualToAPITaskCandidateUserAddedEventConverterResult =
            taskRuntimeAutoConfiguration.toAPITaskCandidateUserAddedEventConverter(
                new APITaskCandidateUserConverter());

    // Assert
    assertFalse(
        actualToAPITaskCandidateUserAddedEventConverterResult
            .from(
                new ActivitiProcessCancelledEventImpl(
                    ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
            .isPresent());
  }

  /**
   * Test {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateUserAddedEventListener(RuntimeService, List,
   * ToAPITaskCandidateUserAddedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateUserAddedEventListener(RuntimeService, List,
   * ToAPITaskCandidateUserAddedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter)"
  })
  void testRegisterTaskCandidateUserAddedEventListener_thenArrayListEmpty() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<TaskRuntimeEventListener<TaskCandidateUserAddedEvent>> listeners = new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration.registerTaskCandidateUserAddedEventListener(
        runtimeService,
        listeners,
        new ToAPITaskCandidateUserAddedEventConverter(new APITaskCandidateUserConverter()));

    // Assert that nothing has changed
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateUserAddedEventListener(RuntimeService, List,
   * ToAPITaskCandidateUserAddedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateUserAddedEventListener(RuntimeService, List,
   * ToAPITaskCandidateUserAddedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter)"
  })
  void testRegisterTaskCandidateUserAddedEventListener_thenArrayListEmpty2() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<TaskRuntimeEventListener<TaskCandidateUserAddedEvent>> listeners = new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateUserAddedEventListener(
            runtimeService,
            listeners,
            new ToAPITaskCandidateUserAddedEventConverter(new APITaskCandidateUserConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateUserAddedEventListener(RuntimeService, List,
   * ToAPITaskCandidateUserAddedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateUserAddedEventListener(RuntimeService, List,
   * ToAPITaskCandidateUserAddedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter)"
  })
  void testRegisterTaskCandidateUserAddedEventListener_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCandidateUserAddedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateUserAddedEventListener(
            runtimeService,
            listeners,
            new ToAPITaskCandidateUserAddedEventConverter(new APITaskCandidateUserConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, listeners.size());
  }

  /**
   * Test {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateUserAddedEventListener(RuntimeService, List,
   * ToAPITaskCandidateUserAddedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateUserAddedEventListener(RuntimeService, List,
   * ToAPITaskCandidateUserAddedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter)"
  })
  void testRegisterTaskCandidateUserAddedEventListener_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCandidateUserAddedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateUserAddedEventListener(
            runtimeService,
            listeners,
            new ToAPITaskCandidateUserAddedEventConverter(new APITaskCandidateUserConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, listeners.size());
  }

  /**
   * Test {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateUserRemovedEventListener(RuntimeService,
   * List, APITaskCandidateUserConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateUserRemovedEventListener(RuntimeService,
   * List, APITaskCandidateUserConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter)"
  })
  void testRegisterTaskCandidateUserRemovedEventListener_thenArrayListEmpty() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<TaskRuntimeEventListener<TaskCandidateUserRemovedEvent>> listeners =
        new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration.registerTaskCandidateUserRemovedEventListener(
        runtimeService, listeners, new APITaskCandidateUserConverter());

    // Assert that nothing has changed
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateUserRemovedEventListener(RuntimeService,
   * List, APITaskCandidateUserConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateUserRemovedEventListener(RuntimeService,
   * List, APITaskCandidateUserConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter)"
  })
  void testRegisterTaskCandidateUserRemovedEventListener_thenArrayListEmpty2() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<TaskRuntimeEventListener<TaskCandidateUserRemovedEvent>> listeners =
        new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateUserRemovedEventListener(
            runtimeService, listeners, new APITaskCandidateUserConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateUserRemovedEventListener(RuntimeService,
   * List, APITaskCandidateUserConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateUserRemovedEventListener(RuntimeService,
   * List, APITaskCandidateUserConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter)"
  })
  void testRegisterTaskCandidateUserRemovedEventListener_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCandidateUserRemovedEvent>> listeners =
        new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateUserRemovedEventListener(
            runtimeService, listeners, new APITaskCandidateUserConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, listeners.size());
  }

  /**
   * Test {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateUserRemovedEventListener(RuntimeService,
   * List, APITaskCandidateUserConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateUserRemovedEventListener(RuntimeService,
   * List, APITaskCandidateUserConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter)"
  })
  void testRegisterTaskCandidateUserRemovedEventListener_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCandidateUserRemovedEvent>> listeners =
        new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateUserRemovedEventListener(
            runtimeService, listeners, new APITaskCandidateUserConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, listeners.size());
  }

  /**
   * Test {@link
   * TaskRuntimeAutoConfiguration#toAPITaskCandidateGroupAddedEventConverter(APITaskCandidateGroupConverter)}.
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#toAPITaskCandidateGroupAddedEventConverter(APITaskCandidateGroupConverter)}
   */
  @Test
  @DisplayName("Test toAPITaskCandidateGroupAddedEventConverter(APITaskCandidateGroupConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ToAPITaskCandidateGroupAddedEventConverter TaskRuntimeAutoConfiguration.toAPITaskCandidateGroupAddedEventConverter(APITaskCandidateGroupConverter)"
  })
  void testToAPITaskCandidateGroupAddedEventConverter() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    // Act
    ToAPITaskCandidateGroupAddedEventConverter
        actualToAPITaskCandidateGroupAddedEventConverterResult =
            taskRuntimeAutoConfiguration.toAPITaskCandidateGroupAddedEventConverter(
                new APITaskCandidateGroupConverter());

    // Assert
    assertFalse(
        actualToAPITaskCandidateGroupAddedEventConverterResult
            .from(
                new ActivitiProcessCancelledEventImpl(
                    ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
            .isPresent());
  }

  /**
   * Test {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateGroupAddedEventListener(RuntimeService, List,
   * ToAPITaskCandidateGroupAddedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateGroupAddedEventListener(RuntimeService, List,
   * ToAPITaskCandidateGroupAddedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter)"
  })
  void testRegisterTaskCandidateGroupAddedEventListener_thenArrayListEmpty() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<TaskRuntimeEventListener<TaskCandidateGroupAddedEvent>> listeners = new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration.registerTaskCandidateGroupAddedEventListener(
        runtimeService,
        listeners,
        new ToAPITaskCandidateGroupAddedEventConverter(new APITaskCandidateGroupConverter()));

    // Assert that nothing has changed
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateGroupAddedEventListener(RuntimeService, List,
   * ToAPITaskCandidateGroupAddedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateGroupAddedEventListener(RuntimeService, List,
   * ToAPITaskCandidateGroupAddedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter)"
  })
  void testRegisterTaskCandidateGroupAddedEventListener_thenArrayListEmpty2() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<TaskRuntimeEventListener<TaskCandidateGroupAddedEvent>> listeners = new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateGroupAddedEventListener(
            runtimeService,
            listeners,
            new ToAPITaskCandidateGroupAddedEventConverter(new APITaskCandidateGroupConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateGroupAddedEventListener(RuntimeService, List,
   * ToAPITaskCandidateGroupAddedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateGroupAddedEventListener(RuntimeService, List,
   * ToAPITaskCandidateGroupAddedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter)"
  })
  void testRegisterTaskCandidateGroupAddedEventListener_thenArrayListSizeIsOne() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCandidateGroupAddedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateGroupAddedEventListener(
            runtimeService,
            listeners,
            new ToAPITaskCandidateGroupAddedEventConverter(new APITaskCandidateGroupConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, listeners.size());
  }

  /**
   * Test {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateGroupAddedEventListener(RuntimeService, List,
   * ToAPITaskCandidateGroupAddedEventConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateGroupAddedEventListener(RuntimeService, List,
   * ToAPITaskCandidateGroupAddedEventConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter)"
  })
  void testRegisterTaskCandidateGroupAddedEventListener_thenArrayListSizeIsTwo() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCandidateGroupAddedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateGroupAddedEventListener(
            runtimeService,
            listeners,
            new ToAPITaskCandidateGroupAddedEventConverter(new APITaskCandidateGroupConverter()))
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, listeners.size());
  }

  /**
   * Test {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateGroupRemovedEventListener(RuntimeService,
   * List, APITaskCandidateGroupConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateGroupRemovedEventListener(RuntimeService,
   * List, APITaskCandidateGroupConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter)"
  })
  void testRegisterTaskCandidateGroupRemovedEventListener_thenArrayListEmpty() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<TaskRuntimeEventListener<TaskCandidateGroupRemovedEvent>> listeners =
        new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration.registerTaskCandidateGroupRemovedEventListener(
        runtimeService, listeners, new APITaskCandidateGroupConverter());

    // Assert that nothing has changed
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateGroupRemovedEventListener(RuntimeService,
   * List, APITaskCandidateGroupConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateGroupRemovedEventListener(RuntimeService,
   * List, APITaskCandidateGroupConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter)"
  })
  void testRegisterTaskCandidateGroupRemovedEventListener_thenArrayListEmpty2() throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<TaskRuntimeEventListener<TaskCandidateGroupRemovedEvent>> listeners =
        new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateGroupRemovedEventListener(
            runtimeService, listeners, new APITaskCandidateGroupConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertTrue(listeners.isEmpty());
  }

  /**
   * Test {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateGroupRemovedEventListener(RuntimeService,
   * List, APITaskCandidateGroupConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateGroupRemovedEventListener(RuntimeService,
   * List, APITaskCandidateGroupConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter)"
  })
  void testRegisterTaskCandidateGroupRemovedEventListener_thenArrayListSizeIsOne()
      throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCandidateGroupRemovedEvent>> listeners =
        new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateGroupRemovedEventListener(
            runtimeService, listeners, new APITaskCandidateGroupConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(1, listeners.size());
  }

  /**
   * Test {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateGroupRemovedEventListener(RuntimeService,
   * List, APITaskCandidateGroupConverter)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * TaskRuntimeAutoConfiguration#registerTaskCandidateGroupRemovedEventListener(RuntimeService,
   * List, APITaskCandidateGroupConverter)}
   */
  @Test
  @DisplayName(
      "Test registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter)"
  })
  void testRegisterTaskCandidateGroupRemovedEventListener_thenArrayListSizeIsTwo()
      throws Exception {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing()
        .when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCandidateGroupRemovedEvent>> listeners =
        new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateGroupRemovedEventListener(
            runtimeService, listeners, new APITaskCandidateGroupConverter())
        .afterPropertiesSet();

    // Assert that nothing has changed
    verify(runtimeService)
        .addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
    assertEquals(2, listeners.size());
  }
}
