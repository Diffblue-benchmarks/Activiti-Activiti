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
package org.activiti.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.process.runtime.events.ProcessCandidateStarterGroupAddedEvent;
import org.activiti.api.process.runtime.events.ProcessCandidateStarterUserAddedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.engine.RepositoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class ProcessCandidateStartersEventProducerDiffblueTest {
  @Mock private RepositoryService repositoryService;

  /**
   * Test {@link
   * ProcessCandidateStartersEventProducer#ProcessCandidateStartersEventProducer(RepositoryService,
   * List, List, ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessCandidateStartersEventProducer#ProcessCandidateStartersEventProducer(RepositoryService,
   * List, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test new ProcessCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessCandidateStartersEventProducer.<init>(RepositoryService, List, List, ApplicationEventPublisher)"
  })
  void testNewProcessCandidateStartersEventProducer_givenProcessRuntimeEventListener() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>>
        candidateStarterUserListeners = new ArrayList<>();
    candidateStarterUserListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessCandidateStartersEventProducer actualProcessCandidateStartersEventProducer =
        new ProcessCandidateStartersEventProducer(
            repositoryService,
            candidateStarterUserListeners,
            new ArrayList<>(),
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessCandidateStartersEventProducer.isRunning());
    assertTrue(actualProcessCandidateStartersEventProducer.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessCandidateStartersEventProducer.getPhase());
  }

  /**
   * Test {@link
   * ProcessCandidateStartersEventProducer#ProcessCandidateStartersEventProducer(RepositoryService,
   * List, List, ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessCandidateStartersEventProducer#ProcessCandidateStartersEventProducer(RepositoryService,
   * List, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test new ProcessCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessCandidateStartersEventProducer.<init>(RepositoryService, List, List, ApplicationEventPublisher)"
  })
  void testNewProcessCandidateStartersEventProducer_givenProcessRuntimeEventListener2() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>>
        candidateStarterUserListeners = new ArrayList<>();
    candidateStarterUserListeners.add(mock(ProcessRuntimeEventListener.class));
    candidateStarterUserListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessCandidateStartersEventProducer actualProcessCandidateStartersEventProducer =
        new ProcessCandidateStartersEventProducer(
            repositoryService,
            candidateStarterUserListeners,
            new ArrayList<>(),
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessCandidateStartersEventProducer.isRunning());
    assertTrue(actualProcessCandidateStartersEventProducer.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessCandidateStartersEventProducer.getPhase());
  }

  /**
   * Test {@link
   * ProcessCandidateStartersEventProducer#ProcessCandidateStartersEventProducer(RepositoryService,
   * List, List, ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessCandidateStartersEventProducer#ProcessCandidateStartersEventProducer(RepositoryService,
   * List, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test new ProcessCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessCandidateStartersEventProducer.<init>(RepositoryService, List, List, ApplicationEventPublisher)"
  })
  void testNewProcessCandidateStartersEventProducer_givenProcessRuntimeEventListener3() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>>
        candidateStarterUserListeners = new ArrayList<>();

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupAddedEvent>>
        candidateStarterGroupListeners = new ArrayList<>();
    candidateStarterGroupListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessCandidateStartersEventProducer actualProcessCandidateStartersEventProducer =
        new ProcessCandidateStartersEventProducer(
            repositoryService,
            candidateStarterUserListeners,
            candidateStarterGroupListeners,
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessCandidateStartersEventProducer.isRunning());
    assertTrue(actualProcessCandidateStartersEventProducer.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessCandidateStartersEventProducer.getPhase());
  }

  /**
   * Test {@link
   * ProcessCandidateStartersEventProducer#ProcessCandidateStartersEventProducer(RepositoryService,
   * List, List, ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessCandidateStartersEventProducer#ProcessCandidateStartersEventProducer(RepositoryService,
   * List, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test new ProcessCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessCandidateStartersEventProducer.<init>(RepositoryService, List, List, ApplicationEventPublisher)"
  })
  void testNewProcessCandidateStartersEventProducer_givenProcessRuntimeEventListener4() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>>
        candidateStarterUserListeners = new ArrayList<>();

    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterGroupAddedEvent>>
        candidateStarterGroupListeners = new ArrayList<>();
    candidateStarterGroupListeners.add(mock(ProcessRuntimeEventListener.class));
    candidateStarterGroupListeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessCandidateStartersEventProducer actualProcessCandidateStartersEventProducer =
        new ProcessCandidateStartersEventProducer(
            repositoryService,
            candidateStarterUserListeners,
            candidateStarterGroupListeners,
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessCandidateStartersEventProducer.isRunning());
    assertTrue(actualProcessCandidateStartersEventProducer.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessCandidateStartersEventProducer.getPhase());
  }

  /**
   * Test {@link
   * ProcessCandidateStartersEventProducer#ProcessCandidateStartersEventProducer(RepositoryService,
   * List, List, ApplicationEventPublisher)}.
   *
   * <ul>
   *   <li>When {@link ApplicationEventPublisher}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessCandidateStartersEventProducer#ProcessCandidateStartersEventProducer(RepositoryService,
   * List, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName(
      "Test new ProcessCandidateStartersEventProducer(RepositoryService, List, List, ApplicationEventPublisher); when ApplicationEventPublisher")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessCandidateStartersEventProducer.<init>(RepositoryService, List, List, ApplicationEventPublisher)"
  })
  void testNewProcessCandidateStartersEventProducer_whenApplicationEventPublisher() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCandidateStarterUserAddedEvent>>
        candidateStarterUserListeners = new ArrayList<>();

    // Act
    ProcessCandidateStartersEventProducer actualProcessCandidateStartersEventProducer =
        new ProcessCandidateStartersEventProducer(
            repositoryService,
            candidateStarterUserListeners,
            new ArrayList<>(),
            mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessCandidateStartersEventProducer.isRunning());
    assertTrue(actualProcessCandidateStartersEventProducer.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessCandidateStartersEventProducer.getPhase());
  }
}
