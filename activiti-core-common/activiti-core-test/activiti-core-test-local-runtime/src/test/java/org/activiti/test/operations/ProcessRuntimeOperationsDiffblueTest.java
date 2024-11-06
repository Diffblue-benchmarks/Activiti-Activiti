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
package org.activiti.test.operations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.payloads.SignalPayload;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.test.EventSource;
import org.activiti.test.TaskSource;
import org.activiti.test.assertions.ProcessInstanceAssertions;
import org.activiti.test.assertions.ProcessInstanceAssertionsImpl;
import org.activiti.test.assertions.SignalAssertions;
import org.activiti.test.assertions.SignalAssertionsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProcessRuntimeOperations.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProcessRuntimeOperationsDiffblueTest {
  @MockBean
  private EventSource eventSource;

  @Autowired
  private List<TaskSource> list;

  @MockBean
  private ProcessRuntime processRuntime;

  @Autowired
  private ProcessRuntimeOperations processRuntimeOperations;

  @MockBean
  private TaskSource taskSource;

  /**
   * Test {@link ProcessRuntimeOperations#start(StartProcessPayload)}.
   * <p>
   * Method under test:
   * {@link ProcessRuntimeOperations#start(StartProcessPayload)}
   */
  @Test
  @DisplayName("Test start(StartProcessPayload)")
  void testStart() {
    // Arrange
    when(processRuntime.start(Mockito.<StartProcessPayload>any())).thenReturn(mock(ProcessInstance.class));

    // Act
    ProcessInstanceAssertions actualStartResult = processRuntimeOperations.start(new StartProcessPayload());

    // Assert
    verify(processRuntime).start(isA(StartProcessPayload.class));
    assertTrue(actualStartResult instanceof ProcessInstanceAssertionsImpl);
  }

  /**
   * Test {@link ProcessRuntimeOperations#signal(SignalPayload)}.
   * <p>
   * Method under test: {@link ProcessRuntimeOperations#signal(SignalPayload)}
   */
  @Test
  @DisplayName("Test signal(SignalPayload)")
  void testSignal() {
    // Arrange
    doNothing().when(processRuntime).signal(Mockito.<SignalPayload>any());

    // Act
    SignalAssertions actualSignalResult = processRuntimeOperations.signal(new SignalPayload());

    // Assert
    verify(processRuntime).signal(isA(SignalPayload.class));
    assertTrue(actualSignalResult instanceof SignalAssertionsImpl);
  }
}
