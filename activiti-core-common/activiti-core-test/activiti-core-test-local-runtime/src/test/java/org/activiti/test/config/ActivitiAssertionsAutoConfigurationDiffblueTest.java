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
import org.junit.jupiter.api.Test;

class ActivitiAssertionsAutoConfigurationDiffblueTest {
  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#handledEvents()}
   */
  @Test
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
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#localTaskProvider(TaskRuntime)}
   */
  @Test
  void testLocalTaskProvider() {
    // Arrange and Act
    TaskSource actualLocalTaskProviderResult = (new ActivitiAssertionsAutoConfiguration())
        .localTaskProvider(mock(TaskRuntime.class));

    // Assert
    assertTrue(actualLocalTaskProviderResult instanceof LocalTaskSource);
    assertTrue(actualLocalTaskProviderResult.canHandle(Task.TaskStatus.CREATED));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#processRuntimeOperations(ProcessRuntime, EventSource, List)}
   */
  @Test
  void testProcessRuntimeOperations() {
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
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#processRuntimeOperations(ProcessRuntime, EventSource, List)}
   */
  @Test
  void testProcessRuntimeOperations2() {
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
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#processRuntimeOperations(ProcessRuntime, EventSource, List)}
   */
  @Test
  void testProcessRuntimeOperations3() {
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
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#taskRuntimeOperations(TaskRuntime, EventSource, List)}
   */
  @Test
  void testTaskRuntimeOperations() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    TaskRuntime taskRuntime = mock(TaskRuntime.class);
    EventSource eventSource = mock(EventSource.class);

    // Act and Assert
    assertTrue(activitiAssertionsAutoConfiguration.taskRuntimeOperations(taskRuntime, eventSource, new ArrayList<>())
        .claim(null) instanceof TaskAssertionsImpl);
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#taskRuntimeOperations(TaskRuntime, EventSource, List)}
   */
  @Test
  void testTaskRuntimeOperations2() {
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
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#taskRuntimeOperations(TaskRuntime, EventSource, List)}
   */
  @Test
  void testTaskRuntimeOperations3() {
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
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnActivityStartedListener()}
   */
  @Test
  void testKeepInMemoryBpmnActivityStartedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNActivityStartedEvent bpmnActivityStartedEvent = mock(BPMNActivityStartedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryBpmnActivityStartedListener().onEvent(bpmnActivityStartedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(bpmnActivityStartedEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnActivityCompletedListener()}
   */
  @Test
  void testKeepInMemoryBpmnActivityCompletedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNActivityCompletedEvent bpmnActivityCompletedEvent = mock(BPMNActivityCompletedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryBpmnActivityCompletedListener().onEvent(bpmnActivityCompletedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(bpmnActivityCompletedEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnActivityCancelledListener()}
   */
  @Test
  void testKeepInMemoryBpmnActivityCancelledListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNActivityCancelledEvent bpmnActivityCancelledEvent = mock(BPMNActivityCancelledEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryBpmnActivityCancelledListener().onEvent(bpmnActivityCancelledEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(bpmnActivityCancelledEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnSequenceFlowTakenListener()}
   */
  @Test
  void testKeepInMemoryBpmnSequenceFlowTakenListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNSequenceFlowTakenEvent bpmnSequenceFlowTakenEvent = mock(BPMNSequenceFlowTakenEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryBpmnSequenceFlowTakenListener().onEvent(bpmnSequenceFlowTakenEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(bpmnSequenceFlowTakenEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessCreatedListener()}
   */
  @Test
  void testKeepInMemoryProcessCreatedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    ProcessCreatedEvent processCreatedEvent = mock(ProcessCreatedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryProcessCreatedListener().onEvent(processCreatedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(processCreatedEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessStartedListener()}
   */
  @Test
  void testKeepInMemoryProcessStartedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    ProcessStartedEvent processStartedEvent = mock(ProcessStartedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryProcessStartedListener().onEvent(processStartedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(processStartedEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessCompletedListener()}
   */
  @Test
  void testKeepInMemoryProcessCompletedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    ProcessCompletedEvent processCompletedEvent = mock(ProcessCompletedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryProcessCompletedListener().onEvent(processCompletedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(processCompletedEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessResumedListener()}
   */
  @Test
  void testKeepInMemoryProcessResumedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    ProcessResumedEvent processResumedEvent = mock(ProcessResumedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryProcessResumedListener().onEvent(processResumedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(processResumedEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessSuspendedListener()}
   */
  @Test
  void testKeepInMemoryProcessSuspendedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    ProcessSuspendedEvent processSuspendedEvent = mock(ProcessSuspendedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryProcessSuspendedListener().onEvent(processSuspendedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(processSuspendedEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessCancelledListener()}
   */
  @Test
  void testKeepInMemoryProcessCancelledListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    ProcessCancelledEvent processCancelledEvent = mock(ProcessCancelledEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryProcessCancelledListener().onEvent(processCancelledEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(processCancelledEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryVariableCreatedEventListener()}
   */
  @Test
  void testKeepInMemoryVariableCreatedEventListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    VariableCreatedEvent variableCreatedEvent = mock(VariableCreatedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryVariableCreatedEventListener().onEvent(variableCreatedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(variableCreatedEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryVariableDeletedEventListener()}
   */
  @Test
  void testKeepInMemoryVariableDeletedEventListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    VariableDeletedEvent variableDeletedEvent = mock(VariableDeletedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryVariableDeletedEventListener().onEvent(variableDeletedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(variableDeletedEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryVariableUpdatedEventListener()}
   */
  @Test
  void testKeepInMemoryVariableUpdatedEventListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    VariableUpdatedEvent variableUpdatedEvent = mock(VariableUpdatedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryVariableUpdatedEventListener().onEvent(variableUpdatedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(variableUpdatedEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskCreatedEventListener()}
   */
  @Test
  void testKeepInMemoryTaskCreatedEventListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    TaskCreatedEvent taskCreatedEvent = mock(TaskCreatedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTaskCreatedEventListener().onEvent(taskCreatedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(taskCreatedEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskUpdatedEventListener()}
   */
  @Test
  void testKeepInMemoryTaskUpdatedEventListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    TaskUpdatedEvent taskUpdatedEvent = mock(TaskUpdatedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTaskUpdatedEventListener().onEvent(taskUpdatedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(taskUpdatedEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskCompletedEventListener()}
   */
  @Test
  void testKeepInMemoryTaskCompletedEventListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    TaskCompletedEvent taskCompletedEvent = mock(TaskCompletedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTaskCompletedEventListener().onEvent(taskCompletedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(taskCompletedEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskSuspendedEventListener()}
   */
  @Test
  void testKeepInMemoryTaskSuspendedEventListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    TaskSuspendedEvent taskSuspendedEvent = mock(TaskSuspendedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTaskSuspendedEventListener().onEvent(taskSuspendedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(taskSuspendedEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskAssignedEventListener()}
   */
  @Test
  void testKeepInMemoryTaskAssignedEventListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    TaskAssignedEvent taskAssignedEvent = mock(TaskAssignedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTaskAssignedEventListener().onEvent(taskAssignedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(taskAssignedEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskCancelledEventListener()}
   */
  @Test
  void testKeepInMemoryTaskCancelledEventListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    TaskCancelledEvent taskCancelledEvent = mock(TaskCancelledEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTaskCancelledEventListener().onEvent(taskCancelledEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(taskCancelledEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnSignalReceivedListener()}
   */
  @Test
  void testKeepInMemoryBpmnSignalReceivedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNSignalReceivedEvent bpmnSignalReceivedEvent = mock(BPMNSignalReceivedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryBpmnSignalReceivedListener().onEvent(bpmnSignalReceivedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(bpmnSignalReceivedEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerScheduledListener()}
   */
  @Test
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
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertEquals(events, handledEventsResult.getTimerScheduledEvents());
    assertSame(bpmnTimerScheduledEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerFiredListener()}
   */
  @Test
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
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertEquals(events, handledEventsResult.getTimerFiredEvents());
    assertSame(bpmnTimerFiredEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerExecutedListener()}
   */
  @Test
  void testKeepInMemoryTimerExecutedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNTimerExecutedEvent bpmnTimerExecutedEvent = mock(BPMNTimerExecutedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTimerExecutedListener().onEvent(bpmnTimerExecutedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(bpmnTimerExecutedEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerFailedListener()}
   */
  @Test
  void testKeepInMemoryTimerFailedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNTimerFailedEvent bpmnTimerFailedEvent = mock(BPMNTimerFailedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryTimerFailedListener().onEvent(bpmnTimerFailedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(bpmnTimerFailedEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerCancelledListener()}
   */
  @Test
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
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertEquals(events, handledEventsResult.getTimerCancelledEvents());
    assertSame(bpmnTimerCancelledEvent, events.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiAssertionsAutoConfiguration#keepInMemoryErrorReceivedListener()}
   */
  @Test
  void testKeepInMemoryErrorReceivedListener() {
    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration = new ActivitiAssertionsAutoConfiguration();
    BPMNErrorReceivedEvent bpmnErrorReceivedEvent = mock(BPMNErrorReceivedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration.keepInMemoryErrorReceivedListener().onEvent(bpmnErrorReceivedEvent);

    // Assert
    LocalEventSource handledEventsResult = activitiAssertionsAutoConfiguration.handledEvents();
    List<RuntimeEvent<?, ?>> events = handledEventsResult.getEvents();
    assertEquals(1, events.size());
    assertTrue(handledEventsResult.getTaskEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(handledEventsResult.getTimerScheduledEvents().isEmpty());
    assertSame(bpmnErrorReceivedEvent, events.get(0));
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link ActivitiAssertionsAutoConfiguration}
   */
  @Test
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
