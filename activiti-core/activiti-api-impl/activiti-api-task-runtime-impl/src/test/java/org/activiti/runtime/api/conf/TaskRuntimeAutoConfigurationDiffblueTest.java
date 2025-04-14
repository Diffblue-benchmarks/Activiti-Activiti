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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.runtime.shared.events.VariableEventListener;
import org.activiti.api.task.runtime.events.TaskCandidateGroupAddedEvent;
import org.activiti.api.task.runtime.events.TaskCandidateGroupRemovedEvent;
import org.activiti.api.task.runtime.events.TaskCandidateUserAddedEvent;
import org.activiti.api.task.runtime.events.TaskCandidateUserRemovedEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.activiti.common.util.DateFormatterProvider;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.runtime.api.conf.impl.TaskRuntimeConfigurationImpl;
import org.activiti.runtime.api.event.impl.ToAPITaskCandidateGroupAddedEventConverter;
import org.activiti.runtime.api.event.impl.ToAPITaskCandidateUserAddedEventConverter;
import org.activiti.runtime.api.impl.VariableNameValidator;
import org.activiti.runtime.api.model.impl.APITaskCandidateGroupConverter;
import org.activiti.runtime.api.model.impl.APITaskCandidateUserConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TaskRuntimeAutoConfigurationDiffblueTest {
  /**
   * Test {@link TaskRuntimeAutoConfiguration#taskVariablesValidator(DateFormatterProvider, VariableNameValidator)}.
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#taskVariablesValidator(DateFormatterProvider, VariableNameValidator)}
   */
  @Test
  @DisplayName("Test taskVariablesValidator(DateFormatterProvider, VariableNameValidator)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.runtime.api.impl.TaskVariablesPayloadValidator TaskRuntimeAutoConfiguration.taskVariablesValidator(DateFormatterProvider, VariableNameValidator)"})
  void testTaskVariablesValidator() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");

    // Act and Assert
    assertNull(taskRuntimeAutoConfiguration.taskVariablesValidator(dateFormatterProvider, new VariableNameValidator())
        .handlePayloadVariables(null));
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}.
   * <ul>
   *   <li>Given {@link TaskRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test taskRuntimeConfiguration(List, List); given TaskRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.api.task.runtime.conf.TaskRuntimeConfiguration TaskRuntimeAutoConfiguration.taskRuntimeConfiguration(List, List)"})
  void testTaskRuntimeConfiguration_givenTaskRuntimeEventListener() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    taskRuntimeEventListeners.add(mock(TaskRuntimeEventListener.class));

    // Act and Assert
    assertTrue(taskRuntimeAutoConfiguration.taskRuntimeConfiguration(taskRuntimeEventListeners,
        new ArrayList<>()) instanceof TaskRuntimeConfigurationImpl);
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}.
   * <ul>
   *   <li>Given {@link TaskRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test taskRuntimeConfiguration(List, List); given TaskRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.api.task.runtime.conf.TaskRuntimeConfiguration TaskRuntimeAutoConfiguration.taskRuntimeConfiguration(List, List)"})
  void testTaskRuntimeConfiguration_givenTaskRuntimeEventListener2() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    taskRuntimeEventListeners.add(mock(TaskRuntimeEventListener.class));
    taskRuntimeEventListeners.add(mock(TaskRuntimeEventListener.class));

    // Act and Assert
    assertTrue(taskRuntimeAutoConfiguration.taskRuntimeConfiguration(taskRuntimeEventListeners,
        new ArrayList<>()) instanceof TaskRuntimeConfigurationImpl);
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}.
   * <ul>
   *   <li>Given {@link VariableEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test taskRuntimeConfiguration(List, List); given VariableEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.api.task.runtime.conf.TaskRuntimeConfiguration TaskRuntimeAutoConfiguration.taskRuntimeConfiguration(List, List)"})
  void testTaskRuntimeConfiguration_givenVariableEventListener() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();

    ArrayList<VariableEventListener<?>> variableEventListeners = new ArrayList<>();
    variableEventListeners.add(mock(VariableEventListener.class));

    // Act and Assert
    assertTrue(taskRuntimeAutoConfiguration.taskRuntimeConfiguration(taskRuntimeEventListeners,
        variableEventListeners) instanceof TaskRuntimeConfigurationImpl);
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}.
   * <ul>
   *   <li>Given {@link VariableEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test taskRuntimeConfiguration(List, List); given VariableEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.api.task.runtime.conf.TaskRuntimeConfiguration TaskRuntimeAutoConfiguration.taskRuntimeConfiguration(List, List)"})
  void testTaskRuntimeConfiguration_givenVariableEventListener2() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();

    ArrayList<VariableEventListener<?>> variableEventListeners = new ArrayList<>();
    variableEventListeners.add(mock(VariableEventListener.class));
    variableEventListeners.add(mock(VariableEventListener.class));

    // Act and Assert
    assertTrue(taskRuntimeAutoConfiguration.taskRuntimeConfiguration(taskRuntimeEventListeners,
        variableEventListeners) instanceof TaskRuntimeConfigurationImpl);
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test taskRuntimeConfiguration(List, List); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.api.task.runtime.conf.TaskRuntimeConfiguration TaskRuntimeAutoConfiguration.taskRuntimeConfiguration(List, List)"})
  void testTaskRuntimeConfiguration_whenArrayList() {
    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();

    // Act and Assert
    assertTrue(taskRuntimeAutoConfiguration.taskRuntimeConfiguration(taskRuntimeEventListeners,
        new ArrayList<>()) instanceof TaskRuntimeConfigurationImpl);
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}
   */
  @Test
  @DisplayName("Test taskRuntimeConfiguration(List, List); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.api.task.runtime.conf.TaskRuntimeConfiguration TaskRuntimeAutoConfiguration.taskRuntimeConfiguration(List, List)"})
  void testTaskRuntimeConfiguration_whenNull() {
    // Arrange, Act and Assert
    assertTrue((new TaskRuntimeAutoConfiguration()).taskRuntimeConfiguration(null,
        null) instanceof TaskRuntimeConfigurationImpl);
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#toAPITaskCandidateUserAddedEventConverter(APITaskCandidateUserConverter)}.
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#toAPITaskCandidateUserAddedEventConverter(APITaskCandidateUserConverter)}
   */
  @Test
  @DisplayName("Test toAPITaskCandidateUserAddedEventConverter(APITaskCandidateUserConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ToAPITaskCandidateUserAddedEventConverter TaskRuntimeAutoConfiguration.toAPITaskCandidateUserAddedEventConverter(APITaskCandidateUserConverter)"})
  void testToAPITaskCandidateUserAddedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    // Act
    ToAPITaskCandidateUserAddedEventConverter actualToAPITaskCandidateUserAddedEventConverterResult = taskRuntimeAutoConfiguration
        .toAPITaskCandidateUserAddedEventConverter(new APITaskCandidateUserConverter());

    // Assert
    assertFalse(actualToAPITaskCandidateUserAddedEventConverterResult
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter)}.
   * <ul>
   *   <li>Given {@link TaskRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter)}
   */
  @Test
  @DisplayName("Test registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter); given TaskRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter)"})
  void testRegisterTaskCandidateUserAddedEventListener_givenTaskRuntimeEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCandidateUserAddedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateUserAddedEventListener(runtimeService, listeners,
            new ToAPITaskCandidateUserAddedEventConverter(new APITaskCandidateUserConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter)}.
   * <ul>
   *   <li>Given {@link TaskRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter)}
   */
  @Test
  @DisplayName("Test registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter); given TaskRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter)"})
  void testRegisterTaskCandidateUserAddedEventListener_givenTaskRuntimeEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCandidateUserAddedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateUserAddedEventListener(runtimeService, listeners,
            new ToAPITaskCandidateUserAddedEventConverter(new APITaskCandidateUserConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter)}
   */
  @Test
  @DisplayName("Test registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter)"})
  void testRegisterTaskCandidateUserAddedEventListener_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<TaskRuntimeEventListener<TaskCandidateUserAddedEvent>> listeners = new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateUserAddedEventListener(runtimeService, listeners,
            new ToAPITaskCandidateUserAddedEventConverter(new APITaskCandidateUserConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter)}.
   * <ul>
   *   <li>Given {@link TaskRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter)}
   */
  @Test
  @DisplayName("Test registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter); given TaskRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter)"})
  void testRegisterTaskCandidateUserRemovedEventListener_givenTaskRuntimeEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCandidateUserRemovedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateUserRemovedEventListener(runtimeService, listeners, new APITaskCandidateUserConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter)}.
   * <ul>
   *   <li>Given {@link TaskRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter)}
   */
  @Test
  @DisplayName("Test registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter); given TaskRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter)"})
  void testRegisterTaskCandidateUserRemovedEventListener_givenTaskRuntimeEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCandidateUserRemovedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateUserRemovedEventListener(runtimeService, listeners, new APITaskCandidateUserConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter)}
   */
  @Test
  @DisplayName("Test registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter)"})
  void testRegisterTaskCandidateUserRemovedEventListener_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<TaskRuntimeEventListener<TaskCandidateUserRemovedEvent>> listeners = new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateUserRemovedEventListener(runtimeService, listeners, new APITaskCandidateUserConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#toAPITaskCandidateGroupAddedEventConverter(APITaskCandidateGroupConverter)}.
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#toAPITaskCandidateGroupAddedEventConverter(APITaskCandidateGroupConverter)}
   */
  @Test
  @DisplayName("Test toAPITaskCandidateGroupAddedEventConverter(APITaskCandidateGroupConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ToAPITaskCandidateGroupAddedEventConverter TaskRuntimeAutoConfiguration.toAPITaskCandidateGroupAddedEventConverter(APITaskCandidateGroupConverter)"})
  void testToAPITaskCandidateGroupAddedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    // Act
    ToAPITaskCandidateGroupAddedEventConverter actualToAPITaskCandidateGroupAddedEventConverterResult = taskRuntimeAutoConfiguration
        .toAPITaskCandidateGroupAddedEventConverter(new APITaskCandidateGroupConverter());

    // Assert
    assertFalse(actualToAPITaskCandidateGroupAddedEventConverterResult
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter)}.
   * <ul>
   *   <li>Given {@link TaskRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter)}
   */
  @Test
  @DisplayName("Test registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter); given TaskRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter)"})
  void testRegisterTaskCandidateGroupAddedEventListener_givenTaskRuntimeEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCandidateGroupAddedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateGroupAddedEventListener(runtimeService, listeners,
            new ToAPITaskCandidateGroupAddedEventConverter(new APITaskCandidateGroupConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter)}.
   * <ul>
   *   <li>Given {@link TaskRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter)}
   */
  @Test
  @DisplayName("Test registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter); given TaskRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter)"})
  void testRegisterTaskCandidateGroupAddedEventListener_givenTaskRuntimeEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCandidateGroupAddedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateGroupAddedEventListener(runtimeService, listeners,
            new ToAPITaskCandidateGroupAddedEventConverter(new APITaskCandidateGroupConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter)}
   */
  @Test
  @DisplayName("Test registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter)"})
  void testRegisterTaskCandidateGroupAddedEventListener_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<TaskRuntimeEventListener<TaskCandidateGroupAddedEvent>> listeners = new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateGroupAddedEventListener(runtimeService, listeners,
            new ToAPITaskCandidateGroupAddedEventConverter(new APITaskCandidateGroupConverter()))
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter)}.
   * <ul>
   *   <li>Given {@link TaskRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter)}
   */
  @Test
  @DisplayName("Test registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter); given TaskRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter)"})
  void testRegisterTaskCandidateGroupRemovedEventListener_givenTaskRuntimeEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCandidateGroupRemovedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateGroupRemovedEventListener(runtimeService, listeners, new APITaskCandidateGroupConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter)}.
   * <ul>
   *   <li>Given {@link TaskRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter)}
   */
  @Test
  @DisplayName("Test registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter); given TaskRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter)"})
  void testRegisterTaskCandidateGroupRemovedEventListener_givenTaskRuntimeEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));

    ArrayList<TaskRuntimeEventListener<TaskCandidateGroupRemovedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(TaskRuntimeEventListener.class));
    listeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateGroupRemovedEventListener(runtimeService, listeners, new APITaskCandidateGroupConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }

  /**
   * Test {@link TaskRuntimeAutoConfiguration#registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeAutoConfiguration#registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter)}
   */
  @Test
  @DisplayName("Test registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.beans.factory.InitializingBean TaskRuntimeAutoConfiguration.registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter)"})
  void testRegisterTaskCandidateGroupRemovedEventListener_whenArrayList() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    RuntimeServiceImpl runtimeService = mock(RuntimeServiceImpl.class);
    doNothing().when(runtimeService)
        .addEventListener(Mockito.<ActivitiEventListener>any(), isA(ActivitiEventType[].class));
    ArrayList<TaskRuntimeEventListener<TaskCandidateGroupRemovedEvent>> listeners = new ArrayList<>();

    // Act
    taskRuntimeAutoConfiguration
        .registerTaskCandidateGroupRemovedEventListener(runtimeService, listeners, new APITaskCandidateGroupConverter())
        .afterPropertiesSet();

    // Assert
    verify(runtimeService).addEventListener(isA(ActivitiEventListener.class), isA(ActivitiEventType[].class));
  }
}
