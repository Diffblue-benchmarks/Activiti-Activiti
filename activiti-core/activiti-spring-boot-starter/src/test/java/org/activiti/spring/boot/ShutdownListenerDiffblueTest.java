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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ShutdownListener.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ShutdownListenerDiffblueTest {
  @MockBean private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  @Autowired private ShutdownListener shutdownListener;

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ShutdownListener#ShutdownListener(ProcessEngineConfigurationImpl)}
   *   <li>{@link ShutdownListener#getOrder()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ShutdownListener.<init>(ProcessEngineConfigurationImpl)",
    "int ShutdownListener.getOrder()"
  })
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(
        Integer.MIN_VALUE, new ShutdownListener(new JtaProcessEngineConfiguration()).getOrder());
  }

  /**
   * Test {@link ShutdownListener#onApplicationEvent(ContextClosedEvent)} with {@code
   * ContextClosedEvent}.
   *
   * <ul>
   *   <li>Then calls {@link ApplicationContext#getParent()}.
   * </ul>
   *
   * <p>Method under test: {@link ShutdownListener#onApplicationEvent(ContextClosedEvent)}
   */
  @Test
  @DisplayName(
      "Test onApplicationEvent(ContextClosedEvent) with 'ContextClosedEvent'; then calls getParent()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ShutdownListener.onApplicationEvent(ContextClosedEvent)"})
  void testOnApplicationEventWithContextClosedEvent_thenCallsGetParent() {
    // Arrange
    ApplicationContext source = mock(ApplicationContext.class);
    when(source.getParent()).thenReturn(mock(ApplicationContext.class));

    // Act
    shutdownListener.onApplicationEvent(new ContextClosedEvent(source));

    // Assert
    verify(source).getParent();
  }
}
