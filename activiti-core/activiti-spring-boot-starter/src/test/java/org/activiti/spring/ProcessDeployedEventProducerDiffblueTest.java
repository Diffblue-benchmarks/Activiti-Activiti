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
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.process.model.events.ProcessDeployedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.runtime.api.model.impl.APIProcessDefinitionConverter;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

class ProcessDeployedEventProducerDiffblueTest {
  /**
   * Method under test:
   * {@link ProcessDeployedEventProducer#ProcessDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  void testNewProcessDeployedEventProducer() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIProcessDefinitionConverter converter = new APIProcessDefinitionConverter(new RepositoryServiceImpl());

    // Act
    ProcessDeployedEventProducer actualProcessDeployedEventProducer = new ProcessDeployedEventProducer(
        repositoryService, converter, new ArrayList<>(), mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessDeployedEventProducer.isRunning());
    assertTrue(actualProcessDeployedEventProducer.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessDeployedEventProducer.getPhase());
  }

  /**
   * Method under test:
   * {@link ProcessDeployedEventProducer#ProcessDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  void testNewProcessDeployedEventProducer2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIProcessDefinitionConverter converter = new APIProcessDefinitionConverter(new RepositoryServiceImpl());

    ArrayList<ProcessRuntimeEventListener<ProcessDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessDeployedEventProducer actualProcessDeployedEventProducer = new ProcessDeployedEventProducer(
        repositoryService, converter, listeners, mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessDeployedEventProducer.isRunning());
    assertTrue(actualProcessDeployedEventProducer.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessDeployedEventProducer.getPhase());
  }

  /**
   * Method under test:
   * {@link ProcessDeployedEventProducer#ProcessDeployedEventProducer(RepositoryService, APIProcessDefinitionConverter, List, ApplicationEventPublisher)}
   */
  @Test
  void testNewProcessDeployedEventProducer3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    APIProcessDefinitionConverter converter = new APIProcessDefinitionConverter(new RepositoryServiceImpl());

    ArrayList<ProcessRuntimeEventListener<ProcessDeployedEvent>> listeners = new ArrayList<>();
    listeners.add(mock(ProcessRuntimeEventListener.class));
    listeners.add(mock(ProcessRuntimeEventListener.class));

    // Act
    ProcessDeployedEventProducer actualProcessDeployedEventProducer = new ProcessDeployedEventProducer(
        repositoryService, converter, listeners, mock(ApplicationEventPublisher.class));

    // Assert
    assertFalse(actualProcessDeployedEventProducer.isRunning());
    assertTrue(actualProcessDeployedEventProducer.isAutoStartup());
    assertEquals(Integer.MAX_VALUE, actualProcessDeployedEventProducer.getPhase());
  }
}
