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
package org.activiti.runtime.api.event.internal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.activiti.api.process.runtime.events.ProcessCompletedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.runtime.api.event.impl.ProcessCompletedImpl;
import org.activiti.runtime.api.event.impl.ToProcessCompletedConverter;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessCompletedListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ProcessCompletedListenerDelegate#ProcessCompletedListenerDelegate(List,
   *       ToProcessCompletedConverter)}
   *   <li>{@link ProcessCompletedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessCompletedListenerDelegate.<init>(List, ToProcessCompletedConverter)",
    "boolean ProcessCompletedListenerDelegate.isFailOnException()"
  })
  void testGettersAndSetters() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> processRuntimeEventListeners =
        new ArrayList<>();

    // Act
    ProcessCompletedListenerDelegate actualProcessCompletedListenerDelegate =
        new ProcessCompletedListenerDelegate(
            processRuntimeEventListeners,
            new ToProcessCompletedConverter(new APIProcessInstanceConverter()));

    // Assert
    assertFalse(actualProcessCompletedListenerDelegate.isFailOnException());
  }

  /**
   * Test {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <p>Method under test: {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessCompletedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent() {
    // Arrange
    ProcessRuntimeEventListener<ProcessCompletedEvent> processRuntimeEventListener =
        mock(ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessCompletedEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> processRuntimeEventListeners =
        new ArrayList<>();
    processRuntimeEventListeners.add(processRuntimeEventListener);

    ToProcessCompletedConverter processCompletedConverter = mock(ToProcessCompletedConverter.class);
    Optional<ProcessCompletedEvent> ofResult =
        Optional.of(new ProcessCompletedImpl(new ProcessInstanceImpl()));
    when(processCompletedConverter.from(Mockito.<ActivitiEntityEvent>any())).thenReturn(ofResult);

    ProcessCompletedListenerDelegate processCompletedListenerDelegate =
        new ProcessCompletedListenerDelegate(
            processRuntimeEventListeners, processCompletedConverter);

    // Act
    processCompletedListenerDelegate.onEvent(
        new ActivitiEntityEventImpl(
            mock(ExecutionEntityImpl.class), ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessCompletedEvent.class));
    verify(processCompletedConverter).from(isA(ActivitiEntityEvent.class));
  }

  /**
   * Test {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); given createWithEmptyRelationshipCollections")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessCompletedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_givenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> processRuntimeEventListeners =
        new ArrayList<>();
    ProcessCompletedListenerDelegate processCompletedListenerDelegate =
        new ProcessCompletedListenerDelegate(
            processRuntimeEventListeners,
            new ToProcessCompletedConverter(new APIProcessInstanceConverter()));

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessInstance())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    processCompletedListenerDelegate.onEvent(
        new ActivitiEntityEventImpl(executionEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(executionEntityImpl).getProcessInstance();
  }

  /**
   * Test {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); given createWithEmptyRelationshipCollections")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessCompletedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_givenCreateWithEmptyRelationshipCollections2() {
    // Arrange
    ProcessRuntimeEventListener<ProcessCompletedEvent> processRuntimeEventListener =
        mock(ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessCompletedEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> processRuntimeEventListeners =
        new ArrayList<>();
    processRuntimeEventListeners.add(processRuntimeEventListener);
    ProcessCompletedListenerDelegate processCompletedListenerDelegate =
        new ProcessCompletedListenerDelegate(
            processRuntimeEventListeners,
            new ToProcessCompletedConverter(new APIProcessInstanceConverter()));

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessInstance())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    processCompletedListenerDelegate.onEvent(
        new ActivitiEntityEventImpl(executionEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessCompletedEvent.class));
    verify(executionEntityImpl).getProcessInstance();
  }

  /**
   * Test {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#isSuspended()} return {@code
   *       true}.
   *   <li>Then calls {@link ExecutionEntityImpl#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName(
      "Test onEvent(ActivitiEvent); given ExecutionEntityImpl isSuspended() return 'true'; then calls getId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessCompletedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_givenExecutionEntityImplIsSuspendedReturnTrue_thenCallsGetId() {
    // Arrange
    ProcessRuntimeEventListener<ProcessCompletedEvent> processRuntimeEventListener =
        mock(ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessCompletedEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> processRuntimeEventListeners =
        new ArrayList<>();
    processRuntimeEventListeners.add(processRuntimeEventListener);
    ProcessCompletedListenerDelegate processCompletedListenerDelegate =
        new ProcessCompletedListenerDelegate(
            processRuntimeEventListeners,
            new ToProcessCompletedConverter(new APIProcessInstanceConverter()));

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.isSuspended()).thenReturn(true);
    when(executionEntityImpl.getAppVersion()).thenReturn(1);
    when(executionEntityImpl.getProcessDefinitionVersion()).thenReturn(1);
    when(executionEntityImpl.getId()).thenReturn("42");
    when(executionEntityImpl.getBusinessKey()).thenReturn("Business Key");
    when(executionEntityImpl.getName()).thenReturn("Name");
    when(executionEntityImpl.getParentProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getProcessDefinitionName()).thenReturn("Process Definition Name");
    when(executionEntityImpl.getStartUserId()).thenReturn("42");
    when(executionEntityImpl.getStartTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    ExecutionEntityImpl executionEntityImpl2 = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl2.getProcessInstance()).thenReturn(executionEntityImpl);

    // Act
    processCompletedListenerDelegate.onEvent(
        new ActivitiEntityEventImpl(executionEntityImpl2, ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessCompletedEvent.class));
    verify(executionEntityImpl).getId();
    verify(executionEntityImpl).getAppVersion();
    verify(executionEntityImpl).getBusinessKey();
    verify(executionEntityImpl).getName();
    verify(executionEntityImpl).getParentProcessInstanceId();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl, atLeast(1)).getProcessDefinitionKey();
    verify(executionEntityImpl).getProcessDefinitionName();
    verify(executionEntityImpl, atLeast(1)).getProcessDefinitionVersion();
    verify(executionEntityImpl2).getProcessInstance();
    verify(executionEntityImpl).getStartTime();
    verify(executionEntityImpl).getStartUserId();
    verify(executionEntityImpl).isSuspended();
  }

  /**
   * Test {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@link ToProcessCompletedConverter} {@link
   *       ToProcessCompletedConverter#from(ActivitiEntityEvent)} return empty.
   *   <li>Then calls {@link ToProcessCompletedConverter#from(ActivitiEntityEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName(
      "Test onEvent(ActivitiEvent); given ToProcessCompletedConverter from(ActivitiEntityEvent) return empty; then calls from(ActivitiEntityEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessCompletedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_givenToProcessCompletedConverterFromReturnEmpty_thenCallsFrom() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> processRuntimeEventListeners =
        new ArrayList<>();
    processRuntimeEventListeners.add(mock(ProcessRuntimeEventListener.class));

    ToProcessCompletedConverter processCompletedConverter = mock(ToProcessCompletedConverter.class);
    Optional<ProcessCompletedEvent> emptyResult = Optional.empty();
    when(processCompletedConverter.from(Mockito.<ActivitiEntityEvent>any()))
        .thenReturn(emptyResult);

    ProcessCompletedListenerDelegate processCompletedListenerDelegate =
        new ProcessCompletedListenerDelegate(
            processRuntimeEventListeners, processCompletedConverter);

    // Act
    processCompletedListenerDelegate.onEvent(
        new ActivitiEntityEventImpl(
            mock(ExecutionEntityImpl.class), ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(processCompletedConverter).from(isA(ActivitiEntityEvent.class));
  }

  /**
   * Test {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link APIProcessInstanceConverter#from(ProcessInstance)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); then calls from(ProcessInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessCompletedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_thenCallsFrom() {
    // Arrange
    ProcessRuntimeEventListener<ProcessCompletedEvent> processRuntimeEventListener =
        mock(ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessCompletedEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> processRuntimeEventListeners =
        new ArrayList<>();
    processRuntimeEventListeners.add(processRuntimeEventListener);

    APIProcessInstanceConverter processInstanceConverter = mock(APIProcessInstanceConverter.class);
    when(processInstanceConverter.from(Mockito.<ProcessInstance>any()))
        .thenReturn(new ProcessInstanceImpl());
    ToProcessCompletedConverter processCompletedConverter =
        new ToProcessCompletedConverter(processInstanceConverter);

    ProcessCompletedListenerDelegate processCompletedListenerDelegate =
        new ProcessCompletedListenerDelegate(
            processRuntimeEventListeners, processCompletedConverter);

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessInstance()).thenReturn(mock(ExecutionEntityImpl.class));

    // Act
    processCompletedListenerDelegate.onEvent(
        new ActivitiEntityEventImpl(executionEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessCompletedEvent.class));
    verify(executionEntityImpl).getProcessInstance();
    verify(processInstanceConverter).from(isA(ProcessInstance.class));
  }
}
