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
package org.activiti.runtime.api.impl;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import java.util.Map;
import org.activiti.api.process.model.payloads.SignalPayload;
import org.activiti.engine.RuntimeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RuntimeSignalPayloadEventListener.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RuntimeSignalPayloadEventListenerDiffblueTest {
  @MockBean
  private RuntimeService runtimeService;

  @Autowired
  private RuntimeSignalPayloadEventListener runtimeSignalPayloadEventListener;

  /**
   * Test {@link RuntimeSignalPayloadEventListener#sendSignal(SignalPayload)}.
   * <ul>
   *   <li>When {@link SignalPayload#SignalPayload()}.</li>
   *   <li>Then calls {@link RuntimeService#signalEventReceived(String, Map)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RuntimeSignalPayloadEventListener#sendSignal(SignalPayload)}
   */
  @Test
  @DisplayName("Test sendSignal(SignalPayload); when SignalPayload(); then calls signalEventReceived(String, Map)")
  void testSendSignal_whenSignalPayload_thenCallsSignalEventReceived() {
    // Arrange
    doNothing().when(runtimeService).signalEventReceived(Mockito.<String>any(), Mockito.<Map<String, Object>>any());

    // Act
    runtimeSignalPayloadEventListener.sendSignal(new SignalPayload());

    // Assert that nothing has changed
    verify(runtimeService).signalEventReceived((String) isNull(), isA(Map.class));
  }
}
