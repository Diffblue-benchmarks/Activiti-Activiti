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
package org.activiti.examples;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.api.process.runtime.connector.Connector;
import org.activiti.api.process.runtime.events.ProcessCompletedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.model.impl.IntegrationContextImpl;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.runtime.api.event.impl.ProcessCompletedImpl;
import org.junit.jupiter.api.Test;

class DemoApplicationDiffblueTest {
  /**
   * Method under test: {@link DemoApplication#processTextConnector()}
   */
  @Test
  void testProcessTextConnector() {
    // Arrange and Act
    Connector actualProcessTextConnectorResult = (new DemoApplication()).processTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.addInBoundVariable("content", "Value");

    // Assert
    assertSame(integrationContextImpl, actualProcessTextConnectorResult.apply(integrationContextImpl));
  }

  /**
   * Method under test: {@link DemoApplication#tagTextConnector()}
   */
  @Test
  void testTagTextConnector() {
    // Arrange and Act
    Connector actualTagTextConnectorResult = (new DemoApplication()).tagTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();

    // Assert
    assertSame(integrationContextImpl, actualTagTextConnectorResult.apply(integrationContextImpl));
  }

  /**
   * Method under test: {@link DemoApplication#discardTextConnector()}
   */
  @Test
  void testDiscardTextConnector() {
    // Arrange and Act
    Connector actualDiscardTextConnectorResult = (new DemoApplication()).discardTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();

    // Assert
    assertSame(integrationContextImpl, actualDiscardTextConnectorResult.apply(integrationContextImpl));
  }

  /**
   * Method under test: {@link DemoApplication#processCompletedListener()}
   */
  @Test
  void testProcessCompletedListener() {
    // Arrange and Act
    ProcessRuntimeEventListener<ProcessCompletedEvent> actualProcessCompletedListenerResult = (new DemoApplication())
        .processCompletedListener();
    ProcessCompletedImpl processCompletedImpl = mock(ProcessCompletedImpl.class);
    when(processCompletedImpl.getEntity()).thenReturn(new ProcessInstanceImpl());
    actualProcessCompletedListenerResult.onEvent(processCompletedImpl);

    // Assert that nothing has changed
    verify(processCompletedImpl, atLeast(1)).getEntity();
  }
}
