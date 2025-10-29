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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.model.shared.event.RuntimeEvent;
import org.activiti.api.runtime.shared.events.VariableEventListener;
import org.activiti.api.task.runtime.conf.TaskRuntimeConfiguration;
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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TaskRuntimeAutoConfigurationDiffblueTest {
  /**
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#taskVariablesValidator(DateFormatterProvider, VariableNameValidator)}
   */
  @Test
  void testTaskVariablesValidator() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");

    // Act and Assert
    assertNull(taskRuntimeAutoConfiguration.taskVariablesValidator(dateFormatterProvider, new VariableNameValidator())
        .handlePayloadVariables(null));
  }

  /**
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#taskVariablesValidator(DateFormatterProvider, VariableNameValidator)}
   */
  @Test
  void testTaskVariablesValidator2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    DateFormatterProvider dateFormatterProvider = mock(DateFormatterProvider.class);

    // Act and Assert
    assertNull(taskRuntimeAutoConfiguration.taskVariablesValidator(dateFormatterProvider, new VariableNameValidator())
        .handlePayloadVariables(null));
  }

  /**
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}
   */
  @Test
  void testTaskRuntimeConfiguration() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();

    // Act
    TaskRuntimeConfiguration actualTaskRuntimeConfigurationResult = taskRuntimeAutoConfiguration
        .taskRuntimeConfiguration(taskRuntimeEventListeners, new ArrayList<>());

    // Assert
    assertTrue(actualTaskRuntimeConfigurationResult instanceof TaskRuntimeConfigurationImpl);
    assertTrue(actualTaskRuntimeConfigurationResult.taskRuntimeEventListeners().isEmpty());
    assertTrue(actualTaskRuntimeConfigurationResult.variableEventListeners().isEmpty());
  }

  /**
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}
   */
  @Test
  void testTaskRuntimeConfiguration2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    TaskRuntimeConfiguration actualTaskRuntimeConfigurationResult = (new TaskRuntimeAutoConfiguration())
        .taskRuntimeConfiguration(null, null);

    // Assert
    assertTrue(actualTaskRuntimeConfigurationResult instanceof TaskRuntimeConfigurationImpl);
    assertTrue(actualTaskRuntimeConfigurationResult.taskRuntimeEventListeners().isEmpty());
    assertTrue(actualTaskRuntimeConfigurationResult.variableEventListeners().isEmpty());
  }

  /**
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}
   */
  @Test
  void testTaskRuntimeConfiguration3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    taskRuntimeEventListeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    TaskRuntimeConfiguration actualTaskRuntimeConfigurationResult = taskRuntimeAutoConfiguration
        .taskRuntimeConfiguration(taskRuntimeEventListeners, new ArrayList<>());

    // Assert
    assertTrue(actualTaskRuntimeConfigurationResult instanceof TaskRuntimeConfigurationImpl);
    assertEquals(1, actualTaskRuntimeConfigurationResult.taskRuntimeEventListeners().size());
    assertTrue(actualTaskRuntimeConfigurationResult.variableEventListeners().isEmpty());
  }

  /**
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}
   */
  @Test
  void testTaskRuntimeConfiguration4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();

    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    taskRuntimeEventListeners.add(mock(TaskRuntimeEventListener.class));
    taskRuntimeEventListeners.add(mock(TaskRuntimeEventListener.class));

    // Act
    TaskRuntimeConfiguration actualTaskRuntimeConfigurationResult = taskRuntimeAutoConfiguration
        .taskRuntimeConfiguration(taskRuntimeEventListeners, new ArrayList<>());

    // Assert
    assertTrue(actualTaskRuntimeConfigurationResult instanceof TaskRuntimeConfigurationImpl);
    assertTrue(actualTaskRuntimeConfigurationResult.variableEventListeners().isEmpty());
    assertEquals(taskRuntimeEventListeners, actualTaskRuntimeConfigurationResult.taskRuntimeEventListeners());
  }

  /**
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}
   */
  @Test
  void testTaskRuntimeConfiguration5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();

    ArrayList<VariableEventListener<?>> variableEventListeners = new ArrayList<>();
    variableEventListeners.add(mock(VariableEventListener.class));

    // Act
    TaskRuntimeConfiguration actualTaskRuntimeConfigurationResult = taskRuntimeAutoConfiguration
        .taskRuntimeConfiguration(taskRuntimeEventListeners, variableEventListeners);

    // Assert
    assertTrue(actualTaskRuntimeConfigurationResult instanceof TaskRuntimeConfigurationImpl);
    assertEquals(1, actualTaskRuntimeConfigurationResult.variableEventListeners().size());
    assertTrue(actualTaskRuntimeConfigurationResult.taskRuntimeEventListeners().isEmpty());
  }

  /**
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#taskRuntimeConfiguration(List, List)}
   */
  @Test
  void testTaskRuntimeConfiguration6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    TaskRuntimeAutoConfiguration taskRuntimeAutoConfiguration = new TaskRuntimeAutoConfiguration();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();

    ArrayList<VariableEventListener<?>> variableEventListeners = new ArrayList<>();
    variableEventListeners.add(mock(VariableEventListener.class));
    variableEventListeners.add(mock(VariableEventListener.class));

    // Act
    TaskRuntimeConfiguration actualTaskRuntimeConfigurationResult = taskRuntimeAutoConfiguration
        .taskRuntimeConfiguration(taskRuntimeEventListeners, variableEventListeners);

    // Assert
    assertTrue(actualTaskRuntimeConfigurationResult instanceof TaskRuntimeConfigurationImpl);
    assertTrue(actualTaskRuntimeConfigurationResult.taskRuntimeEventListeners().isEmpty());
    assertEquals(variableEventListeners, actualTaskRuntimeConfigurationResult.variableEventListeners());
  }

  /**
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#toAPITaskCandidateUserAddedEventConverter(APITaskCandidateUserConverter)}
   */
  @Test
  void testToAPITaskCandidateUserAddedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#toAPITaskCandidateUserAddedEventConverter(APITaskCandidateUserConverter)}
   */
  @Test
  void testToAPITaskCandidateUserAddedEventConverter2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    ToAPITaskCandidateUserAddedEventConverter actualToAPITaskCandidateUserAddedEventConverterResult = (new TaskRuntimeAutoConfiguration())
        .toAPITaskCandidateUserAddedEventConverter(mock(APITaskCandidateUserConverter.class));

    // Assert
    assertFalse(actualToAPITaskCandidateUserAddedEventConverterResult
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter)}
   */
  @Test
  void testRegisterTaskCandidateUserAddedEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter)}
   */
  @Test
  void testRegisterTaskCandidateUserAddedEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#registerTaskCandidateUserAddedEventListener(RuntimeService, List, ToAPITaskCandidateUserAddedEventConverter)}
   */
  @Test
  void testRegisterTaskCandidateUserAddedEventListener3() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter)}
   */
  @Test
  void testRegisterTaskCandidateUserRemovedEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter)}
   */
  @Test
  void testRegisterTaskCandidateUserRemovedEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#registerTaskCandidateUserRemovedEventListener(RuntimeService, List, APITaskCandidateUserConverter)}
   */
  @Test
  void testRegisterTaskCandidateUserRemovedEventListener3() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#toAPITaskCandidateGroupAddedEventConverter(APITaskCandidateGroupConverter)}
   */
  @Test
  void testToAPITaskCandidateGroupAddedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#toAPITaskCandidateGroupAddedEventConverter(APITaskCandidateGroupConverter)}
   */
  @Test
  void testToAPITaskCandidateGroupAddedEventConverter2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    ToAPITaskCandidateGroupAddedEventConverter actualToAPITaskCandidateGroupAddedEventConverterResult = (new TaskRuntimeAutoConfiguration())
        .toAPITaskCandidateGroupAddedEventConverter(mock(APITaskCandidateGroupConverter.class));

    // Assert
    assertFalse(actualToAPITaskCandidateGroupAddedEventConverterResult
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter)}
   */
  @Test
  void testRegisterTaskCandidateGroupAddedEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter)}
   */
  @Test
  void testRegisterTaskCandidateGroupAddedEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#registerTaskCandidateGroupAddedEventListener(RuntimeService, List, ToAPITaskCandidateGroupAddedEventConverter)}
   */
  @Test
  void testRegisterTaskCandidateGroupAddedEventListener3() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter)}
   */
  @Test
  void testRegisterTaskCandidateGroupRemovedEventListener() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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

  /**
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter)}
   */
  @Test
  void testRegisterTaskCandidateGroupRemovedEventListener2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link TaskRuntimeAutoConfiguration#registerTaskCandidateGroupRemovedEventListener(RuntimeService, List, APITaskCandidateGroupConverter)}
   */
  @Test
  void testRegisterTaskCandidateGroupRemovedEventListener3() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
}
