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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.test.EventSource;
import org.activiti.test.assertions.AwaitProcessInstanceAssertions;
import org.activiti.test.assertions.ProcessInstanceAssertions;
import org.activiti.test.assertions.ProcessInstanceAssertionsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AwaitableProcessOperationsDiffblueTest {
  /**
   * Test {@link AwaitableProcessOperations#start(StartProcessPayload)}.
   * <p>
   * Method under test:
   * {@link AwaitableProcessOperations#start(StartProcessPayload)}
   */
  @Test
  @DisplayName("Test start(StartProcessPayload)")
  void testStart() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessOperations processOperations = mock(ProcessOperations.class);
    EventSource eventSource = mock(EventSource.class);
    when(processOperations.start(Mockito.<StartProcessPayload>any())).thenReturn(new AwaitProcessInstanceAssertions(
        new ProcessInstanceAssertionsImpl(eventSource, new ArrayList<>(), mock(ProcessInstance.class))));
    AwaitableProcessOperations awaitableProcessOperations = new AwaitableProcessOperations(processOperations, true);

    // Act
    ProcessInstanceAssertions actualStartResult = awaitableProcessOperations.start(new StartProcessPayload());

    // Assert
    verify(processOperations).start(isA(StartProcessPayload.class));
    assertTrue(actualStartResult instanceof AwaitProcessInstanceAssertions);
  }

  /**
   * Test {@link AwaitableProcessOperations#start(StartProcessPayload)}.
   * <p>
   * Method under test:
   * {@link AwaitableProcessOperations#start(StartProcessPayload)}
   */
  @Test
  @DisplayName("Test start(StartProcessPayload)")
  void testStart2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessOperations processOperations = mock(ProcessOperations.class);
    EventSource eventSource = mock(EventSource.class);
    AwaitProcessInstanceAssertions awaitProcessInstanceAssertions = new AwaitProcessInstanceAssertions(
        new ProcessInstanceAssertionsImpl(eventSource, new ArrayList<>(), mock(ProcessInstance.class)));
    when(processOperations.start(Mockito.<StartProcessPayload>any())).thenReturn(awaitProcessInstanceAssertions);
    AwaitableProcessOperations awaitableProcessOperations = new AwaitableProcessOperations(processOperations, false);

    // Act
    ProcessInstanceAssertions actualStartResult = awaitableProcessOperations.start(new StartProcessPayload());

    // Assert
    verify(processOperations).start(isA(StartProcessPayload.class));
    assertTrue(actualStartResult instanceof AwaitProcessInstanceAssertions);
    assertSame(awaitProcessInstanceAssertions, actualStartResult);
  }
}
