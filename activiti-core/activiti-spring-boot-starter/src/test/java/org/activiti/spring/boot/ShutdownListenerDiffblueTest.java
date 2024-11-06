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
package org.activiti.spring.boot;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ShutdownListener.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ShutdownListenerDiffblueTest {
  @MockBean
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  @Autowired
  private ShutdownListener shutdownListener;

  /**
   * Test {@link ShutdownListener#onApplicationEvent(ContextClosedEvent)} with
   * {@code ContextClosedEvent}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfiguration#getAsyncExecutor()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ShutdownListener#onApplicationEvent(ContextClosedEvent)}
   */
  @Test
  @DisplayName("Test onApplicationEvent(ContextClosedEvent) with 'ContextClosedEvent'; then calls getAsyncExecutor()")
  void testOnApplicationEventWithContextClosedEvent_thenCallsGetAsyncExecutor() {
    // Arrange
    when(processEngineConfigurationImpl.getAsyncExecutor()).thenReturn(new DefaultAsyncJobExecutor());

    // Act
    shutdownListener.onApplicationEvent(new ContextClosedEvent(new AnnotationConfigReactiveWebApplicationContext()));

    // Assert
    verify(processEngineConfigurationImpl).getAsyncExecutor();
  }

  /**
   * Test {@link ShutdownListener#onApplicationEvent(ContextClosedEvent)} with
   * {@code ContextClosedEvent}.
   * <ul>
   *   <li>Then calls {@link AbstractApplicationContext#getParent()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ShutdownListener#onApplicationEvent(ContextClosedEvent)}
   */
  @Test
  @DisplayName("Test onApplicationEvent(ContextClosedEvent) with 'ContextClosedEvent'; then calls getParent()")
  void testOnApplicationEventWithContextClosedEvent_thenCallsGetParent() {
    // Arrange
    AnnotationConfigApplicationContext source = mock(AnnotationConfigApplicationContext.class);
    when(source.getParent()).thenReturn(new AnnotationConfigReactiveWebApplicationContext());

    // Act
    shutdownListener.onApplicationEvent(new ContextClosedEvent(source));

    // Assert that nothing has changed
    verify(source).getParent();
  }
}
