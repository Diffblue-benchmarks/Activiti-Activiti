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
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.process.model.events.ApplicationDeployedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.engine.RepositoryService;
import org.activiti.runtime.api.model.impl.APIDeploymentConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class ApplicationDeployedEventProducerDiffblueTest {
  @Mock
  private APIDeploymentConverter aPIDeploymentConverter;

  @Mock
  private RepositoryService repositoryService;

  /**
   * Test {@link ApplicationDeployedEventProducer#ApplicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ApplicationDeployedEventProducer#ApplicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test new ApplicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void ApplicationDeployedEventProducer.<init>(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)"})
  void testNewApplicationDeployedEventProducer_givenProcessRuntimeEventListener() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ApplicationDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ApplicationDeployedEventProducer actualApplicationDeployedEventProducer = new ApplicationDeployedEventProducer(
        repositoryService, aPIDeploymentConverter, listeners, mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualApplicationDeployedEventProducer.isRunning());
    assertTrue(actualApplicationDeployedEventProducer.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualApplicationDeployedEventProducer.getPhase());
  }

  /**
   * Test {@link ApplicationDeployedEventProducer#ApplicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ApplicationDeployedEventProducer#ApplicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test new ApplicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher); given ProcessRuntimeEventListener")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void ApplicationDeployedEventProducer.<init>(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)"})
  void testNewApplicationDeployedEventProducer_givenProcessRuntimeEventListener2() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ApplicationDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ApplicationDeployedEventProducer actualApplicationDeployedEventProducer = new ApplicationDeployedEventProducer(
        repositoryService, aPIDeploymentConverter, listeners, mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualApplicationDeployedEventProducer.isRunning());
    assertTrue(actualApplicationDeployedEventProducer.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualApplicationDeployedEventProducer.getPhase());
  }

  /**
   * Test {@link ApplicationDeployedEventProducer#ApplicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ApplicationDeployedEventProducer#ApplicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)}
   */
  @Test
  @DisplayName("Test new ApplicationDeployedEventProducer(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void ApplicationDeployedEventProducer.<init>(RepositoryService, APIDeploymentConverter, List, ApplicationEventPublisher)"})
  void testNewApplicationDeployedEventProducer_whenArrayList() {
    // Arrange and Act
    ApplicationDeployedEventProducer actualApplicationDeployedEventProducer = new ApplicationDeployedEventProducer(
        repositoryService, aPIDeploymentConverter, new ArrayList<>(), mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualApplicationDeployedEventProducer.isRunning());
    assertTrue(actualApplicationDeployedEventProducer.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualApplicationDeployedEventProducer.getPhase());
  }
}
