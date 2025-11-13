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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ActivitiAssertionsAutoConfiguration.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ActivitiAssertionsAutoConfigurationDiffblueTest {
  @Autowired private ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration;

  @MockBean private ProcessRuntime processRuntime;

  @MockBean private TaskRuntime taskRuntime;

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#handledEvents()}.
   *
   * <ul>
   *   <li>Then return Events Empty.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiAssertionsAutoConfiguration#handledEvents()}
   */
  @Test
  @DisplayName("Test handledEvents(); then return Events Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"LocalEventSource ActivitiAssertionsAutoConfiguration.handledEvents()"})
  void testHandledEvents_thenReturnEventsEmpty() {
    // Arrange and Act
    LocalEventSource actualHandledEventsResult =
        activitiAssertionsAutoConfiguration.handledEvents();

    // Assert
    assertTrue(actualHandledEventsResult.getEvents().isEmpty());
    assertTrue(actualHandledEventsResult.getProcessInstanceEvents().isEmpty());
    assertTrue(actualHandledEventsResult.getTaskEvents().isEmpty());
    assertTrue(actualHandledEventsResult.getTimerCancelledEvents().isEmpty());
    assertTrue(actualHandledEventsResult.getTimerFiredEvents().isEmpty());
    assertTrue(actualHandledEventsResult.getTimerScheduledEvents().isEmpty());
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#localTaskProvider(TaskRuntime)}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#localTaskProvider(TaskRuntime)}
   */
  @Test
  @DisplayName("Test localTaskProvider(TaskRuntime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TaskSource ActivitiAssertionsAutoConfiguration.localTaskProvider(TaskRuntime)"
  })
  void testLocalTaskProvider() {
    // Arrange, Act and Assert
    assertTrue(
        new ActivitiAssertionsAutoConfiguration().localTaskProvider(mock(TaskRuntime.class))
            instanceof LocalTaskSource);
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#processRuntimeOperations(ProcessRuntime,
   * EventSource, List)}.
   *
   * <ul>
   *   <li>Given {@link LocalTaskSource#LocalTaskSource(TaskRuntime)} with {@link TaskRuntime}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#processRuntimeOperations(ProcessRuntime, EventSource,
   * List)}
   */
  @Test
  @DisplayName(
      "Test processRuntimeOperations(ProcessRuntime, EventSource, List); given LocalTaskSource(TaskRuntime) with TaskRuntime")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessRuntimeOperations ActivitiAssertionsAutoConfiguration.processRuntimeOperations(ProcessRuntime, EventSource, List)"
  })
  void testProcessRuntimeOperations_givenLocalTaskSourceWithTaskRuntime() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    EventSource eventSource = mock(EventSource.class);

    ArrayList<TaskSource> taskSources = new ArrayList<>();
    taskSources.add(new LocalTaskSource(mock(TaskRuntime.class)));

    // Act
    ProcessRuntimeOperations actualProcessRuntimeOperationsResult =
        activitiAssertionsAutoConfiguration.processRuntimeOperations(
            processRuntime, eventSource, taskSources);

    // Assert
    assertTrue(
        actualProcessRuntimeOperationsResult.start(null) instanceof ProcessInstanceAssertionsImpl);
    assertTrue(actualProcessRuntimeOperationsResult.signal(null) instanceof SignalAssertionsImpl);
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#processRuntimeOperations(ProcessRuntime,
   * EventSource, List)}.
   *
   * <ul>
   *   <li>Given {@link LocalTaskSource#LocalTaskSource(TaskRuntime)} with {@link TaskRuntime}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#processRuntimeOperations(ProcessRuntime, EventSource,
   * List)}
   */
  @Test
  @DisplayName(
      "Test processRuntimeOperations(ProcessRuntime, EventSource, List); given LocalTaskSource(TaskRuntime) with TaskRuntime")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessRuntimeOperations ActivitiAssertionsAutoConfiguration.processRuntimeOperations(ProcessRuntime, EventSource, List)"
  })
  void testProcessRuntimeOperations_givenLocalTaskSourceWithTaskRuntime2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    EventSource eventSource = mock(EventSource.class);

    ArrayList<TaskSource> taskSources = new ArrayList<>();
    taskSources.add(new LocalTaskSource(mock(TaskRuntime.class)));
    taskSources.add(new LocalTaskSource(mock(TaskRuntime.class)));

    // Act
    ProcessRuntimeOperations actualProcessRuntimeOperationsResult =
        activitiAssertionsAutoConfiguration.processRuntimeOperations(
            processRuntime, eventSource, taskSources);

    // Assert
    assertTrue(
        actualProcessRuntimeOperationsResult.start(null) instanceof ProcessInstanceAssertionsImpl);
    assertTrue(actualProcessRuntimeOperationsResult.signal(null) instanceof SignalAssertionsImpl);
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#processRuntimeOperations(ProcessRuntime,
   * EventSource, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#processRuntimeOperations(ProcessRuntime, EventSource,
   * List)}
   */
  @Test
  @DisplayName("Test processRuntimeOperations(ProcessRuntime, EventSource, List); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessRuntimeOperations ActivitiAssertionsAutoConfiguration.processRuntimeOperations(ProcessRuntime, EventSource, List)"
  })
  void testProcessRuntimeOperations_whenArrayList() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    EventSource eventSource = mock(EventSource.class);

    // Act
    ProcessRuntimeOperations actualProcessRuntimeOperationsResult =
        activitiAssertionsAutoConfiguration.processRuntimeOperations(
            processRuntime, eventSource, new ArrayList<>());

    // Assert
    assertTrue(
        actualProcessRuntimeOperationsResult.start(null) instanceof ProcessInstanceAssertionsImpl);
    assertTrue(actualProcessRuntimeOperationsResult.signal(null) instanceof SignalAssertionsImpl);
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#taskRuntimeOperations(TaskRuntime, EventSource,
   * List)}.
   *
   * <ul>
   *   <li>Given {@link LocalTaskSource#LocalTaskSource(TaskRuntime)} with {@link TaskRuntime}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#taskRuntimeOperations(TaskRuntime, EventSource, List)}
   */
  @Test
  @DisplayName(
      "Test taskRuntimeOperations(TaskRuntime, EventSource, List); given LocalTaskSource(TaskRuntime) with TaskRuntime")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.test.operations.TaskRuntimeOperations ActivitiAssertionsAutoConfiguration.taskRuntimeOperations(TaskRuntime, EventSource, List)"
  })
  void testTaskRuntimeOperations_givenLocalTaskSourceWithTaskRuntime() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    TaskRuntime taskRuntime = mock(TaskRuntime.class);
    EventSource eventSource = mock(EventSource.class);

    ArrayList<TaskSource> taskSources = new ArrayList<>();
    taskSources.add(new LocalTaskSource(mock(TaskRuntime.class)));

    // Act and Assert
    assertTrue(
        activitiAssertionsAutoConfiguration
                .taskRuntimeOperations(taskRuntime, eventSource, taskSources)
                .claim(null)
            instanceof TaskAssertionsImpl);
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#taskRuntimeOperations(TaskRuntime, EventSource,
   * List)}.
   *
   * <ul>
   *   <li>Given {@link LocalTaskSource#LocalTaskSource(TaskRuntime)} with {@link TaskRuntime}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#taskRuntimeOperations(TaskRuntime, EventSource, List)}
   */
  @Test
  @DisplayName(
      "Test taskRuntimeOperations(TaskRuntime, EventSource, List); given LocalTaskSource(TaskRuntime) with TaskRuntime")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.test.operations.TaskRuntimeOperations ActivitiAssertionsAutoConfiguration.taskRuntimeOperations(TaskRuntime, EventSource, List)"
  })
  void testTaskRuntimeOperations_givenLocalTaskSourceWithTaskRuntime2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    TaskRuntime taskRuntime = mock(TaskRuntime.class);
    EventSource eventSource = mock(EventSource.class);

    ArrayList<TaskSource> taskSources = new ArrayList<>();
    taskSources.add(new LocalTaskSource(mock(TaskRuntime.class)));
    taskSources.add(new LocalTaskSource(mock(TaskRuntime.class)));

    // Act and Assert
    assertTrue(
        activitiAssertionsAutoConfiguration
                .taskRuntimeOperations(taskRuntime, eventSource, taskSources)
                .claim(null)
            instanceof TaskAssertionsImpl);
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#taskRuntimeOperations(TaskRuntime, EventSource,
   * List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#taskRuntimeOperations(TaskRuntime, EventSource, List)}
   */
  @Test
  @DisplayName("Test taskRuntimeOperations(TaskRuntime, EventSource, List); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.test.operations.TaskRuntimeOperations ActivitiAssertionsAutoConfiguration.taskRuntimeOperations(TaskRuntime, EventSource, List)"
  })
  void testTaskRuntimeOperations_whenArrayList() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    TaskRuntime taskRuntime = mock(TaskRuntime.class);
    EventSource eventSource = mock(EventSource.class);

    // Act and Assert
    assertTrue(
        activitiAssertionsAutoConfiguration
                .taskRuntimeOperations(taskRuntime, eventSource, new ArrayList<>())
                .claim(null)
            instanceof TaskAssertionsImpl);
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnActivityStartedListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnActivityStartedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryBpmnActivityStartedListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.events.listener.BPMNElementEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryBpmnActivityStartedListener()"
  })
  void testKeepInMemoryBpmnActivityStartedListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    BPMNActivityStartedEvent bpmnActivityStartedEvent = mock(BPMNActivityStartedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryBpmnActivityStartedListener()
        .onEvent(bpmnActivityStartedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnActivityStartedEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnActivityCompletedListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnActivityCompletedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryBpmnActivityCompletedListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.events.listener.BPMNElementEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryBpmnActivityCompletedListener()"
  })
  void testKeepInMemoryBpmnActivityCompletedListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    BPMNActivityCompletedEvent bpmnActivityCompletedEvent = mock(BPMNActivityCompletedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryBpmnActivityCompletedListener()
        .onEvent(bpmnActivityCompletedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnActivityCompletedEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnActivityCancelledListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnActivityCancelledListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryBpmnActivityCancelledListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.events.listener.BPMNElementEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryBpmnActivityCancelledListener()"
  })
  void testKeepInMemoryBpmnActivityCancelledListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    BPMNActivityCancelledEvent bpmnActivityCancelledEvent = mock(BPMNActivityCancelledEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryBpmnActivityCancelledListener()
        .onEvent(bpmnActivityCancelledEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnActivityCancelledEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnSequenceFlowTakenListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnSequenceFlowTakenListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryBpmnSequenceFlowTakenListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.events.listener.BPMNElementEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryBpmnSequenceFlowTakenListener()"
  })
  void testKeepInMemoryBpmnSequenceFlowTakenListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    BPMNSequenceFlowTakenEvent bpmnSequenceFlowTakenEvent = mock(BPMNSequenceFlowTakenEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryBpmnSequenceFlowTakenListener()
        .onEvent(bpmnSequenceFlowTakenEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnSequenceFlowTakenEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessCreatedListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryProcessCreatedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryProcessCreatedListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryProcessCreatedListener()"
  })
  void testKeepInMemoryProcessCreatedListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    ProcessCreatedEvent processCreatedEvent = mock(ProcessCreatedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryProcessCreatedListener()
        .onEvent(processCreatedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(processCreatedEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessStartedListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryProcessStartedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryProcessStartedListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryProcessStartedListener()"
  })
  void testKeepInMemoryProcessStartedListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    ProcessStartedEvent processStartedEvent = mock(ProcessStartedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryProcessStartedListener()
        .onEvent(processStartedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(processStartedEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessCompletedListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryProcessCompletedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryProcessCompletedListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryProcessCompletedListener()"
  })
  void testKeepInMemoryProcessCompletedListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    ProcessCompletedEvent processCompletedEvent = mock(ProcessCompletedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryProcessCompletedListener()
        .onEvent(processCompletedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(processCompletedEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessResumedListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryProcessResumedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryProcessResumedListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryProcessResumedListener()"
  })
  void testKeepInMemoryProcessResumedListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    ProcessResumedEvent processResumedEvent = mock(ProcessResumedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryProcessResumedListener()
        .onEvent(processResumedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(processResumedEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessSuspendedListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryProcessSuspendedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryProcessSuspendedListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryProcessSuspendedListener()"
  })
  void testKeepInMemoryProcessSuspendedListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    ProcessSuspendedEvent processSuspendedEvent = mock(ProcessSuspendedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryProcessSuspendedListener()
        .onEvent(processSuspendedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(processSuspendedEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryProcessCancelledListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryProcessCancelledListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryProcessCancelledListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryProcessCancelledListener()"
  })
  void testKeepInMemoryProcessCancelledListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    ProcessCancelledEvent processCancelledEvent = mock(ProcessCancelledEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryProcessCancelledListener()
        .onEvent(processCancelledEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(processCancelledEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryVariableCreatedEventListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryVariableCreatedEventListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryVariableCreatedEventListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.events.VariableEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryVariableCreatedEventListener()"
  })
  void testKeepInMemoryVariableCreatedEventListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    VariableCreatedEvent variableCreatedEvent = mock(VariableCreatedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryVariableCreatedEventListener()
        .onEvent(variableCreatedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(variableCreatedEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryVariableDeletedEventListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryVariableDeletedEventListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryVariableDeletedEventListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.events.VariableEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryVariableDeletedEventListener()"
  })
  void testKeepInMemoryVariableDeletedEventListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    VariableDeletedEvent variableDeletedEvent = mock(VariableDeletedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryVariableDeletedEventListener()
        .onEvent(variableDeletedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(variableDeletedEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryVariableUpdatedEventListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryVariableUpdatedEventListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryVariableUpdatedEventListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.events.VariableEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryVariableUpdatedEventListener()"
  })
  void testKeepInMemoryVariableUpdatedEventListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    VariableUpdatedEvent variableUpdatedEvent = mock(VariableUpdatedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryVariableUpdatedEventListener()
        .onEvent(variableUpdatedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(variableUpdatedEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskCreatedEventListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryTaskCreatedEventListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTaskCreatedEventListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.runtime.events.listener.TaskEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryTaskCreatedEventListener()"
  })
  void testKeepInMemoryTaskCreatedEventListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    TaskCreatedEvent taskCreatedEvent = mock(TaskCreatedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryTaskCreatedEventListener()
        .onEvent(taskCreatedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(taskCreatedEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskUpdatedEventListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryTaskUpdatedEventListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTaskUpdatedEventListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.runtime.events.listener.TaskEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryTaskUpdatedEventListener()"
  })
  void testKeepInMemoryTaskUpdatedEventListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    TaskUpdatedEvent taskUpdatedEvent = mock(TaskUpdatedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryTaskUpdatedEventListener()
        .onEvent(taskUpdatedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(taskUpdatedEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskCompletedEventListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryTaskCompletedEventListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTaskCompletedEventListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.runtime.events.listener.TaskEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryTaskCompletedEventListener()"
  })
  void testKeepInMemoryTaskCompletedEventListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    TaskCompletedEvent taskCompletedEvent = mock(TaskCompletedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryTaskCompletedEventListener()
        .onEvent(taskCompletedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(taskCompletedEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskSuspendedEventListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryTaskSuspendedEventListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTaskSuspendedEventListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.runtime.events.listener.TaskEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryTaskSuspendedEventListener()"
  })
  void testKeepInMemoryTaskSuspendedEventListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    TaskSuspendedEvent taskSuspendedEvent = mock(TaskSuspendedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryTaskSuspendedEventListener()
        .onEvent(taskSuspendedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(taskSuspendedEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskAssignedEventListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryTaskAssignedEventListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTaskAssignedEventListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.runtime.events.listener.TaskEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryTaskAssignedEventListener()"
  })
  void testKeepInMemoryTaskAssignedEventListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    TaskAssignedEvent taskAssignedEvent = mock(TaskAssignedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryTaskAssignedEventListener()
        .onEvent(taskAssignedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(taskAssignedEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTaskCancelledEventListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryTaskCancelledEventListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTaskCancelledEventListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.runtime.events.listener.TaskEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryTaskCancelledEventListener()"
  })
  void testKeepInMemoryTaskCancelledEventListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    TaskCancelledEvent taskCancelledEvent = mock(TaskCancelledEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryTaskCancelledEventListener()
        .onEvent(taskCancelledEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(taskCancelledEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnSignalReceivedListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryBpmnSignalReceivedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryBpmnSignalReceivedListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.events.listener.BPMNElementEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryBpmnSignalReceivedListener()"
  })
  void testKeepInMemoryBpmnSignalReceivedListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    BPMNSignalReceivedEvent bpmnSignalReceivedEvent = mock(BPMNSignalReceivedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryBpmnSignalReceivedListener()
        .onEvent(bpmnSignalReceivedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnSignalReceivedEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerScheduledListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryTimerScheduledListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTimerScheduledListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.events.listener.BPMNElementEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryTimerScheduledListener()"
  })
  void testKeepInMemoryTimerScheduledListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    BPMNTimerScheduledEvent bpmnTimerScheduledEvent = mock(BPMNTimerScheduledEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryTimerScheduledListener()
        .onEvent(bpmnTimerScheduledEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnTimerScheduledEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerFiredListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryTimerFiredListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTimerFiredListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.events.listener.BPMNElementEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryTimerFiredListener()"
  })
  void testKeepInMemoryTimerFiredListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    BPMNTimerFiredEvent bpmnTimerFiredEvent = mock(BPMNTimerFiredEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryTimerFiredListener()
        .onEvent(bpmnTimerFiredEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnTimerFiredEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerExecutedListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryTimerExecutedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTimerExecutedListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.events.listener.BPMNElementEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryTimerExecutedListener()"
  })
  void testKeepInMemoryTimerExecutedListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    BPMNTimerExecutedEvent bpmnTimerExecutedEvent = mock(BPMNTimerExecutedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryTimerExecutedListener()
        .onEvent(bpmnTimerExecutedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnTimerExecutedEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerFailedListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryTimerFailedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTimerFailedListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.events.listener.BPMNElementEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryTimerFailedListener()"
  })
  void testKeepInMemoryTimerFailedListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    BPMNTimerFailedEvent bpmnTimerFailedEvent = mock(BPMNTimerFailedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryTimerFailedListener()
        .onEvent(bpmnTimerFailedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnTimerFailedEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryTimerCancelledListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryTimerCancelledListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryTimerCancelledListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.events.listener.BPMNElementEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryTimerCancelledListener()"
  })
  void testKeepInMemoryTimerCancelledListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    BPMNTimerCancelledEvent bpmnTimerCancelledEvent = mock(BPMNTimerCancelledEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryTimerCancelledListener()
        .onEvent(bpmnTimerCancelledEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnTimerCancelledEvent, events.get(0));
  }

  /**
   * Test {@link ActivitiAssertionsAutoConfiguration#keepInMemoryErrorReceivedListener()}.
   *
   * <p>Method under test: {@link
   * ActivitiAssertionsAutoConfiguration#keepInMemoryErrorReceivedListener()}
   */
  @Test
  @DisplayName("Test keepInMemoryErrorReceivedListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.runtime.events.listener.BPMNElementEventListener ActivitiAssertionsAutoConfiguration.keepInMemoryErrorReceivedListener()"
  })
  void testKeepInMemoryErrorReceivedListener() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivitiAssertionsAutoConfiguration activitiAssertionsAutoConfiguration =
        new ActivitiAssertionsAutoConfiguration();
    BPMNErrorReceivedEvent bpmnErrorReceivedEvent = mock(BPMNErrorReceivedEvent.class);

    // Act
    activitiAssertionsAutoConfiguration
        .keepInMemoryErrorReceivedListener()
        .onEvent(bpmnErrorReceivedEvent);

    // Assert
    List<RuntimeEvent<?, ?>> events =
        activitiAssertionsAutoConfiguration.handledEvents().getEvents();
    assertEquals(1, events.size());
    assertSame(bpmnErrorReceivedEvent, events.get(0));
  }
}
