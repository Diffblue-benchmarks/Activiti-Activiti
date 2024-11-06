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
package org.activiti.test.assertions;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.test.matchers.OperationScopeMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AwaitSignalAssertionsDiffblueTest {
  /**
   * Test
   * {@link AwaitSignalAssertions#expectEventsOnProcessInstance(ProcessInstance, OperationScopeMatcher[])}.
   * <p>
   * Method under test:
   * {@link AwaitSignalAssertions#expectEventsOnProcessInstance(ProcessInstance, OperationScopeMatcher[])}
   */
  @Test
  @DisplayName("Test expectEventsOnProcessInstance(ProcessInstance, OperationScopeMatcher[])")
  void testExpectEventsOnProcessInstance() {
    // Arrange
    SignalAssertions signalAssertions = mock(SignalAssertions.class);
    when(signalAssertions.expectEventsOnProcessInstance(Mockito.<ProcessInstance>any(),
        isA(OperationScopeMatcher[].class))).thenReturn(mock(SignalAssertions.class));

    // Act
    SignalAssertions actualExpectEventsOnProcessInstanceResult = (new AwaitSignalAssertions(signalAssertions))
        .expectEventsOnProcessInstance(mock(ProcessInstance.class), mock(OperationScopeMatcher.class));
    SignalAssertions actualExpectEventsOnProcessInstanceResult2 = actualExpectEventsOnProcessInstanceResult
        .expectEventsOnProcessInstance(mock(ProcessInstance.class), mock(OperationScopeMatcher.class));

    // Assert
    verify(signalAssertions, atLeast(1)).expectEventsOnProcessInstance(Mockito.<ProcessInstance>any(),
        isA(OperationScopeMatcher[].class));
    assertTrue(actualExpectEventsOnProcessInstanceResult instanceof AwaitSignalAssertions);
    assertSame(actualExpectEventsOnProcessInstanceResult, actualExpectEventsOnProcessInstanceResult2);
  }
}
