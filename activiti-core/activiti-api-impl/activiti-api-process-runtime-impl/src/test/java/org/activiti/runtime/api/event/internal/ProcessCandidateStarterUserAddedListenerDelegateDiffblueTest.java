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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.activiti.api.process.runtime.events.ProcessCandidateStarterUserAddedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.event.impl.ProcessCandidateStarterUserAddedEventImpl;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.runtime.api.event.impl.ToAPIProcessCandidateStarterUserAddedEventConverter;
import org.activiti.runtime.api.model.impl.APIProcessCandidateStarterUserConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessCandidateStarterUserAddedListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       ProcessCandidateStarterUserAddedListenerDelegate#ProcessCandidateStarterUserAddedListenerDelegate(List,
   *       ToAPIProcessCandidateStarterUserAddedEventConverter)}
   *   <li>{@link ProcessCandidateStarterUserAddedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessCandidateStarterUserAddedListenerDelegate.<init>(List, ToAPIProcessCandidateStarterUserAddedEventConverter)",
    "boolean ProcessCandidateStarterUserAddedListenerDelegate.isFailOnException()"
  })
  void testGettersAndSetters() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>> listeners =
        new ArrayList<>();

    // Act
    ProcessCandidateStarterUserAddedListenerDelegate
        actualProcessCandidateStarterUserAddedListenerDelegate =
            new ProcessCandidateStarterUserAddedListenerDelegate(
                listeners,
                new ToAPIProcessCandidateStarterUserAddedEventConverter(
                    new APIProcessCandidateStarterUserConverter()));

    // Assert
    assertFalse(actualProcessCandidateStarterUserAddedListenerDelegate.isFailOnException());
  }

  /**
   * Test {@link ProcessCandidateStarterUserAddedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener} {@link
   *       ProcessRuntimeEventListener#onEvent(RuntimeEvent)} does nothing.
   *   <li>Then calls {@link ProcessRuntimeEventListener#onEvent(RuntimeEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessCandidateStarterUserAddedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName(
      "Test onEvent(ActivitiEvent); given ProcessRuntimeEventListener onEvent(RuntimeEvent) does nothing; then calls onEvent(RuntimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessCandidateStarterUserAddedListenerDelegate.onEvent(ActivitiEvent)"
  })
  void testOnEvent_givenProcessRuntimeEventListenerOnEventDoesNothing_thenCallsOnEvent()
      throws UnsupportedEncodingException {
    // Arrange
    ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent> processRuntimeEventListener =
        mock(ProcessRuntimeEventListener.class);
    doNothing()
        .when(processRuntimeEventListener)
        .onEvent(Mockito.<ProcessCandidateStarterUserAddedEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>> listeners =
        new ArrayList<>();
    listeners.add(processRuntimeEventListener);

    ToAPIProcessCandidateStarterUserAddedEventConverter
        processCandidateStarterUserAddedEventConverter =
            mock(ToAPIProcessCandidateStarterUserAddedEventConverter.class);
    Optional<ProcessCandidateStarterUserAddedEvent> ofResult =
        Optional.of(new ProcessCandidateStarterUserAddedEventImpl());
    when(processCandidateStarterUserAddedEventConverter.from(Mockito.<ActivitiEntityEvent>any()))
        .thenReturn(ofResult);

    ProcessCandidateStarterUserAddedListenerDelegate
        processCandidateStarterUserAddedListenerDelegate =
            new ProcessCandidateStarterUserAddedListenerDelegate(
                listeners, processCandidateStarterUserAddedEventConverter);

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setDetails("AXAXAXAX".getBytes("UTF-8"));
    identityLinkEntityImpl.setGroupId("42");
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setProcessInstanceId("42");
    identityLinkEntityImpl.setTaskId("42");
    identityLinkEntityImpl.setType("Type");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setProcessDefId("Entity");

    // Act
    processCandidateStarterUserAddedListenerDelegate.onEvent(
        new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessCandidateStarterUserAddedEvent.class));
    verify(processCandidateStarterUserAddedEventConverter).from(isA(ActivitiEntityEvent.class));
  }

  /**
   * Test {@link ProcessCandidateStarterUserAddedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link
   *       ToAPIProcessCandidateStarterUserAddedEventConverter#from(ActivitiEntityEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessCandidateStarterUserAddedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); then calls from(ActivitiEntityEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessCandidateStarterUserAddedListenerDelegate.onEvent(ActivitiEvent)"
  })
  void testOnEvent_thenCallsFrom() throws UnsupportedEncodingException {
    // Arrange
    ToAPIProcessCandidateStarterUserAddedEventConverter
        processCandidateStarterUserAddedEventConverter =
            mock(ToAPIProcessCandidateStarterUserAddedEventConverter.class);
    Optional<ProcessCandidateStarterUserAddedEvent> ofResult =
        Optional.of(new ProcessCandidateStarterUserAddedEventImpl());
    when(processCandidateStarterUserAddedEventConverter.from(Mockito.<ActivitiEntityEvent>any()))
        .thenReturn(ofResult);
    ProcessCandidateStarterUserAddedListenerDelegate
        processCandidateStarterUserAddedListenerDelegate =
            new ProcessCandidateStarterUserAddedListenerDelegate(
                new ArrayList<>(), processCandidateStarterUserAddedEventConverter);

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setDetails("AXAXAXAX".getBytes("UTF-8"));
    identityLinkEntityImpl.setGroupId("42");
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setProcessInstanceId("42");
    identityLinkEntityImpl.setTaskId("42");
    identityLinkEntityImpl.setType("Type");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setProcessDefId("Entity");

    // Act
    processCandidateStarterUserAddedListenerDelegate.onEvent(
        new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(processCandidateStarterUserAddedEventConverter).from(isA(ActivitiEntityEvent.class));
  }
}
