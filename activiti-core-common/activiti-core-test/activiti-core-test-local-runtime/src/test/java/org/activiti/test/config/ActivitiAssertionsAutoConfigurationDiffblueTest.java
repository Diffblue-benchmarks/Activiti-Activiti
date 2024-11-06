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
package org.activiti.test.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.model.shared.event.RuntimeEvent;
import org.activiti.api.model.shared.event.VariableCreatedEvent;
import org.activiti.api.model.shared.event.VariableDeletedEvent;
import org.activiti.api.model.shared.event.VariableUpdatedEvent;
import org.activiti.api.process.model.events.BPMNActivityCancelledEvent;
import org.activiti.api.process.model.events.BPMNActivityCompletedEvent;
import org.activiti.api.process.model.events.BPMNActivityStartedEvent;
import org.activiti.api.process.model.events.BPMNErrorReceivedEvent;
import org.activiti.api.process.model.events.BPMNSequenceFlowTakenEvent;
import org.activiti.api.process.model.events.BPMNSignalReceivedEvent;
import org.activiti.api.process.model.events.BPMNTimerCancelledEvent;
import org.activiti.api.process.model.events.BPMNTimerExecutedEvent;
import org.activiti.api.process.model.events.BPMNTimerFailedEvent;
import org.activiti.api.process.model.events.BPMNTimerFiredEvent;
import org.activiti.api.process.model.events.BPMNTimerScheduledEvent;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.process.runtime.events.ProcessCancelledEvent;
import org.activiti.api.process.runtime.events.ProcessCompletedEvent;
import org.activiti.api.process.runtime.events.ProcessCreatedEvent;
import org.activiti.api.process.runtime.events.ProcessResumedEvent;
import org.activiti.api.process.runtime.events.ProcessStartedEvent;
import org.activiti.api.process.runtime.events.ProcessSuspendedEvent;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.api.task.runtime.events.TaskAssignedEvent;
import org.activiti.api.task.runtime.events.TaskCancelledEvent;
import org.activiti.api.task.runtime.events.TaskCompletedEvent;
import org.activiti.api.task.runtime.events.TaskCreatedEvent;
import org.activiti.api.task.runtime.events.TaskSuspendedEvent;
import org.activiti.api.task.runtime.events.TaskUpdatedEvent;
import org.activiti.test.EventSource;
import org.activiti.test.LocalEventSource;
import org.activiti.test.LocalTaskSource;
import org.activiti.test.TaskSource;
import org.activiti.test.assertions.ProcessInstanceAssertionsImpl;
import org.activiti.test.assertions.SignalAssertionsImpl;
import org.activiti.test.assertions.TaskAssertionsImpl;
import org.activiti.test.operations.ProcessRuntimeOperations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ActivitiAssertionsAutoConfigurationDiffblueTest {
  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#handledEvents()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#handledEvents()}
   */
  @Test
  @DisplayName("Test handledEvents()")
  void testHandledEvents() {
    // Arrange and Act
    LocalEventSource actualHandledEventsResult = (new ActivitiAssertionsAutoConfiguration()).handledEvents();

    // Assert
    assertTrue(actualHandledEventsResult.getEvents().isEmpty());
    assertTrue(actualHandledEventsResult.getProcessInstanceEvents().isEmpty());
    assertTrue(actualHandledEventsResult.getTaskEvents().isEmpty());
    assertTrue(actualHandledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(actualHandledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(actualHandledEventsResult.getTimerScheduledEvents().isEmpty());
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#localTaskProvider(TaskRuntime)}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#localTaskProvider(TaskRuntime)}
   */
  @Test
  @DisplayName("Test localTaskProvider(TaskRuntime)")
  void testLocalTaskProvider() {
    // Arrange and Act
    TaskSource actualLocalTaskProviderResult = (new ActivitiAssertionsAutoConfiguration())
        .localTaskProvider(mock(TaskRuntime.class));

    // Assert
    assertTrue(actualLocalTaskProviderResult instanceof LocalTaskSource);
    assertTrue(actualLocalTaskProviderResult.canHandle(Task.TaskStatus.CREATED));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#processRuntimeOperations(ProcessRuntime, EventSource, List)}.
   * <ul>
   *   <li>Given {@link LocalTaskSource#LocalTaskSource(TaskRuntime)} with
   * {@link TaskRuntime}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#processRuntimeOperations(ProcessRuntime, EventSource, List)}
   */
  @Test
  @DisplayName("Test processRuntimeOperations(ProcessRuntime, EventSource, List); given LocalTaskSource(TaskRuntime) with TaskRuntime")
  void testProcessRuntimeOperations_givenLocalTaskSourceWithTaskRuntime() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    EventSource eventSource = mock(EventSource.class);

    ArrayList<TaskSource> taskSources = new ArrayList<>();
    taskSources.add(new LocalTaskSource(mock(TaskRuntime.class)));

    // Act
    ProcessRuntimeOperations actualProcessRuntimeOperationsResult = activitiAssertionsAutoConfiguration
        .processRuntimeOperations(processRuntime, eventSource, taskSources);

    // Assert
    assertTrue(actualProcessRuntimeOperationsResult.start(null) instanceof ProcessInstanceAssertionsImpl);
    assertTrue(actualProcessRuntimeOperationsResult.signal(null) instanceof SignalAssertionsImpl);
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#processRuntimeOperations(ProcessRuntime, EventSource, List)}.
   * <ul>
   *   <li>Given {@link LocalTaskSource#LocalTaskSource(TaskRuntime)} with
   * {@link TaskRuntime}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#processRuntimeOperations(ProcessRuntime, EventSource, List)}
   */
  @Test
  @DisplayName("Test processRuntimeOperations(ProcessRuntime, EventSource, List); given LocalTaskSource(TaskRuntime) with TaskRuntime")
  void testProcessRuntimeOperations_givenLocalTaskSourceWithTaskRuntime2() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    EventSource eventSource = mock(EventSource.class);

    ArrayList<TaskSource> taskSources = new ArrayList<>();
    taskSources.add(new LocalTaskSource(mock(TaskRuntime.class)));
    taskSources.add(new LocalTaskSource(mock(TaskRuntime.class)));

    // Act
    ProcessRuntimeOperations actualProcessRuntimeOperationsResult = activitiAssertionsAutoConfiguration
        .processRuntimeOperations(processRuntime, eventSource, taskSources);

    // Assert
    assertTrue(actualProcessRuntimeOperationsResult.start(null) instanceof ProcessInstanceAssertionsImpl);
    assertTrue(actualProcessRuntimeOperationsResult.signal(null) instanceof SignalAssertionsImpl);
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#processRuntimeOperations(ProcessRuntime, EventSource, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#processRuntimeOperations(ProcessRuntime, EventSource, List)}
   */
  @Test
  @DisplayName("Test processRuntimeOperations(ProcessRuntime, EventSource, List); when ArrayList()")
  void testProcessRuntimeOperations_whenArrayList() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    EventSource eventSource = mock(EventSource.class);

    // Act
    ProcessRuntimeOperations actualProcessRuntimeOperationsResult = activitiAssertionsAutoConfiguration
        .processRuntimeOperations(processRuntime, eventSource, new ArrayList<>());

    // Assert
    assertTrue(actualProcessRuntimeOperationsResult.start(null) instanceof ProcessInstanceAssertionsImpl);
    assertTrue(actualProcessRuntimeOperationsResult.signal(null) instanceof SignalAssertionsImpl);
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#taskRuntimeOperations(TaskRuntime, EventSource, List)}.
   * <ul>
   *   <li>Given {@link LocalTaskSource#LocalTaskSource(TaskRuntime)} with
   * {@link TaskRuntime}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#taskRuntimeOperations(TaskRuntime, EventSource, List)}
   */
  @Test
  @DisplayName("Test taskRuntimeOperations(TaskRuntime, EventSource, List); given LocalTaskSource(TaskRuntime) with TaskRuntime")
  void testTaskRuntimeOperations_givenLocalTaskSourceWithTaskRuntime() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    TaskRuntime taskRuntime = mock(TaskRuntime.class);
    EventSource eventSource = mock(EventSource.class);

    ArrayList<TaskSource> taskSources = new ArrayList<>();
    taskSources.add(new LocalTaskSource(mock(TaskRuntime.class)));

    // Act and Assert
    assertTrue(activitiAssertionsAutoConfiguration.taskRuntimeOperations(taskRuntime, eventSource, taskSources)
        .claim(null) instanceof TaskAssertionsImpl);
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#taskRuntimeOperations(TaskRuntime, EventSource, List)}.
   * <ul>
   *   <li>Given {@link LocalTaskSource#LocalTaskSource(TaskRuntime)} with
   * {@link TaskRuntime}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#taskRuntimeOperations(TaskRuntime, EventSource, List)}
   */
  @Test
  @DisplayName("Test taskRuntimeOperations(TaskRuntime, EventSource, List); given LocalTaskSource(TaskRuntime) with TaskRuntime")
  void testTaskRuntimeOperations_givenLocalTaskSourceWithTaskRuntime2() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    TaskRuntime taskRuntime = mock(TaskRuntime.class);
    EventSource eventSource = mock(EventSource.class);

    ArrayList<TaskSource> taskSources = new ArrayList<>();
    taskSources.add(new LocalTaskSource(mock(TaskRuntime.class)));
    taskSources.add(new LocalTaskSource(mock(TaskRuntime.class)));

    // Act and Assert
    assertTrue(activitiAssertionsAutoConfiguration.taskRuntimeOperations(taskRuntime, eventSource, taskSources)
        .claim(null) instanceof TaskAssertionsImpl);
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#taskRuntimeOperations(TaskRuntime, EventSource, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#taskRuntimeOperations(TaskRuntime, EventSource, List)}
   */
  @Test
  @DisplayName("Test taskRuntimeOperations(TaskRuntime, EventSource, List); when ArrayList()")
  void testTaskRuntimeOperations_whenArrayList() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    TaskRuntime taskRuntime = mock(TaskRuntime.class);
    EventSource eventSource = mock(EventSource.class);

    // Act and Assert
    assertTrue(activitiAssertionsAutoConfiguration.taskRuntimeOperations(taskRuntime, eventSource, new ArrayList<>())
        .claim(null) instanceof TaskAssertionsImpl);
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnActivityStartedListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnActivityStartedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryBpmnActivityStartedListener()")
  void testKeepInMemoryBpmnActivityStartedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNActivityStartedEvent bpmnActivityStartedEvent = mock(BPMNActivityStartedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryBpmnActivityStartedListener().onEvent(bpmnActivityStartedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnActivityStartedEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnActivityCompletedListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnActivityCompletedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryBpmnActivityCompletedListener()")
  void testKeepInMemoryBpmnActivityCompletedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNActivityCompletedEvent bpmnActivityCompletedEvent = mock(BPMNActivityCompletedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryBpmnActivityCompletedListener().onEvent(bpmnActivityCompletedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnActivityCompletedEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnActivityCancelledListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnActivityCancelledListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryBpmnActivityCancelledListener()")
  void testKeepInMemoryBpmnActivityCancelledListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNActivityCancelledEvent bpmnActivityCancelledEvent = mock(BPMNActivityCancelledEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryBpmnActivityCancelledListener().onEvent(bpmnActivityCancelledEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnActivityCancelledEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnSequenceFlowTakenListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnSequenceFlowTakenListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryBpmnSequenceFlowTakenListener()")
  void testKeepInMemoryBpmnSequenceFlowTakenListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNSequenceFlowTakenEvent bpmnSequenceFlowTakenEvent = mock(BPMNSequenceFlowTakenEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryBpmnSequenceFlowTakenListener().onEvent(bpmnSequenceFlowTakenEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnSequenceFlowTakenEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessCreatedListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessCreatedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryProcessCreatedListener()")
  void testKeepInMemoryProcessCreatedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    ProcessCreatedEvent processCreatedEvent = mock(ProcessCreatedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryProcessCreatedListener().onEvent(processCreatedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(processCreatedEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessStartedListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessStartedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryProcessStartedListener()")
  void testKeepInMemoryProcessStartedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    ProcessStartedEvent processStartedEvent = mock(ProcessStartedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryProcessStartedListener().onEvent(processStartedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(processStartedEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessCompletedListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessCompletedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryProcessCompletedListener()")
  void testKeepInMemoryProcessCompletedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    ProcessCompletedEvent processCompletedEvent = mock(ProcessCompletedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryProcessCompletedListener().onEvent(processCompletedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(processCompletedEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessResumedListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessResumedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryProcessResumedListener()")
  void testKeepInMemoryProcessResumedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    ProcessResumedEvent processResumedEvent = mock(ProcessResumedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryProcessResumedListener().onEvent(processResumedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(processResumedEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessSuspendedListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessSuspendedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryProcessSuspendedListener()")
  void testKeepInMemoryProcessSuspendedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    ProcessSuspendedEvent processSuspendedEvent = mock(ProcessSuspendedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryProcessSuspendedListener().onEvent(processSuspendedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(processSuspendedEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessCancelledListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessCancelledListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryProcessCancelledListener()")
  void testKeepInMemoryProcessCancelledListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    ProcessCancelledEvent processCancelledEvent = mock(ProcessCancelledEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryProcessCancelledListener().onEvent(processCancelledEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(processCancelledEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryVariableCreatedEventListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryVariableCreatedEventListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryVariableCreatedEventListener()")
  void testKeepInMemoryVariableCreatedEventListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    VariableCreatedEvent variableCreatedEvent = mock(VariableCreatedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryVariableCreatedEventListener().onEvent(variableCreatedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(variableCreatedEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryVariableDeletedEventListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryVariableDeletedEventListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryVariableDeletedEventListener()")
  void testKeepInMemoryVariableDeletedEventListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    VariableDeletedEvent variableDeletedEvent = mock(VariableDeletedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryVariableDeletedEventListener().onEvent(variableDeletedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(variableDeletedEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryVariableUpdatedEventListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryVariableUpdatedEventListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryVariableUpdatedEventListener()")
  void testKeepInMemoryVariableUpdatedEventListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    VariableUpdatedEvent variableUpdatedEvent = mock(VariableUpdatedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryVariableUpdatedEventListener().onEvent(variableUpdatedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(variableUpdatedEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskCreatedEventListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskCreatedEventListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTaskCreatedEventListener()")
  void testKeepInMemoryTaskCreatedEventListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    TaskCreatedEvent taskCreatedEvent = mock(TaskCreatedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTaskCreatedEventListener().onEvent(taskCreatedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(taskCreatedEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskUpdatedEventListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskUpdatedEventListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTaskUpdatedEventListener()")
  void testKeepInMemoryTaskUpdatedEventListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    TaskUpdatedEvent taskUpdatedEvent = mock(TaskUpdatedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTaskUpdatedEventListener().onEvent(taskUpdatedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(taskUpdatedEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskCompletedEventListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskCompletedEventListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTaskCompletedEventListener()")
  void testKeepInMemoryTaskCompletedEventListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    TaskCompletedEvent taskCompletedEvent = mock(TaskCompletedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTaskCompletedEventListener().onEvent(taskCompletedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(taskCompletedEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskSuspendedEventListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskSuspendedEventListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTaskSuspendedEventListener()")
  void testKeepInMemoryTaskSuspendedEventListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    TaskSuspendedEvent taskSuspendedEvent = mock(TaskSuspendedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTaskSuspendedEventListener().onEvent(taskSuspendedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(taskSuspendedEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskAssignedEventListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskAssignedEventListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTaskAssignedEventListener()")
  void testKeepInMemoryTaskAssignedEventListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    TaskAssignedEvent taskAssignedEvent = mock(TaskAssignedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTaskAssignedEventListener().onEvent(taskAssignedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(taskAssignedEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskCancelledEventListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskCancelledEventListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTaskCancelledEventListener()")
  void testKeepInMemoryTaskCancelledEventListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    TaskCancelledEvent taskCancelledEvent = mock(TaskCancelledEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTaskCancelledEventListener().onEvent(taskCancelledEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(taskCancelledEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnSignalReceivedListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnSignalReceivedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryBpmnSignalReceivedListener()")
  void testKeepInMemoryBpmnSignalReceivedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNSignalReceivedEvent bpmnSignalReceivedEvent = mock(BPMNSignalReceivedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryBpmnSignalReceivedListener().onEvent(bpmnSignalReceivedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnSignalReceivedEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerScheduledListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerScheduledListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTimerScheduledListener()")
  void testKeepInMemoryTimerScheduledListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNTimerScheduledEvent bpmnTimerScheduledEvent = mock(BPMNTimerScheduledEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTimerScheduledListener().onEvent(bpmnTimerScheduledEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertEquals(events, handledEventsResult.getTimerScheduledEvents());
    assertSame(bpmnTimerScheduledEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerFiredListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerFiredListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTimerFiredListener()")
  void testKeepInMemoryTimerFiredListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNTimerFiredEvent bpmnTimerFiredEvent = mock(BPMNTimerFiredEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTimerFiredListener().onEvent(bpmnTimerFiredEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertEquals(events, handledEventsResult.getTimerFiredEvents());
    assertSame(bpmnTimerFiredEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerExecutedListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerExecutedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTimerExecutedListener()")
  void testKeepInMemoryTimerExecutedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNTimerExecutedEvent bpmnTimerExecutedEvent = mock(BPMNTimerExecutedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTimerExecutedListener().onEvent(bpmnTimerExecutedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnTimerExecutedEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerFailedListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerFailedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTimerFailedListener()")
  void testKeepInMemoryTimerFailedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNTimerFailedEvent bpmnTimerFailedEvent = mock(BPMNTimerFailedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTimerFailedListener().onEvent(bpmnTimerFailedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnTimerFailedEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerCancelledListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerCancelledListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTimerCancelledListener()")
  void testKeepInMemoryTimerCancelledListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNTimerCancelledEvent bpmnTimerCancelledEvent = mock(BPMNTimerCancelledEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTimerCancelledListener().onEvent(bpmnTimerCancelledEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertEquals(events, handledEventsResult.getTimerCancelledEvents());
    assertSame(bpmnTimerCancelledEvent, events.get(0));
  }

  /**
   * Test
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryErrorReceivedListener()}.
   * <p>
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryErrorReceivedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryErrorReceivedListener()")
  void testKeepInMemoryErrorReceivedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNErrorReceivedEvent bpmnErrorReceivedEvent = mock(BPMNErrorReceivedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryErrorReceivedListener().onEvent(bpmnErrorReceivedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events = activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnErrorReceivedEvent, events.get(0));
  }

  /**
   * Test new {@link ActivitiAssertionsAutoConfiguration} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link ActivitiAssertionsAutoConfiguration}
   */
  @Test
  @DisplayName("Test new ActivitiAssertionsAutoConfiguration (default constructor)")
  void testNewActivitiAssertionsAutoConfiguration() {
    // Arrange, Act and Assert
    LocalEventSource handledEventsResult = (new ActivitiAssertionsAutoConfiguration()).handledEvents();
    assertTrue(handledEventsResult.getEvents().isEmpty());
    assertTrue(handledEventsResult.getProcessInstanceEvents().isEmpty());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
  }
}
