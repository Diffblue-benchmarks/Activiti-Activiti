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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.model.shared.event.RuntimeEvent;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.test.EventSource;
import org.activiti.test.TaskSource;
import org.activiti.test.matchers.ProcessResultMatcher;
import org.activiti.test.matchers.ProcessTaskMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AwaitProcessInstanceAssertionsDiffblueTest {
  /**
   * Test
   * {@link AwaitProcessInstanceAssertions#expectFields(ProcessResultMatcher[])}.
   * <p>
   * Method under test:
   * {@link AwaitProcessInstanceAssertions#expectFields(ProcessResultMatcher[])}
   */
  @Test
  @DisplayName("Test expectFields(ProcessResultMatcher[])")
  void testExpectFields() {
    // Arrange
    EventSource eventSource = mock(EventSource.class);
    Mockito.<List<RuntimeEvent<?, ?>>>when(eventSource.getEvents()).thenReturn(new ArrayList<>());
    AwaitProcessInstanceAssertions awaitProcessInstanceAssertions = new AwaitProcessInstanceAssertions(
        new ProcessInstanceAssertionsImpl(eventSource, new ArrayList<>(), mock(ProcessInstance.class)));
    ProcessResultMatcher processResultMatcher = mock(ProcessResultMatcher.class);
    doNothing().when(processResultMatcher).match(Mockito.<ProcessInstance>any());

    // Act
    ProcessInstanceAssertions actualExpectFieldsResult = awaitProcessInstanceAssertions
        .expectFields(processResultMatcher);

    // Assert
    verify(eventSource).getEvents();
    verify(processResultMatcher).match(isA(ProcessInstance.class));
    assertTrue(actualExpectFieldsResult instanceof AwaitProcessInstanceAssertions);
    assertSame(awaitProcessInstanceAssertions, actualExpectFieldsResult);
  }

  /**
   * Test
   * {@link AwaitProcessInstanceAssertions#expectFields(ProcessResultMatcher[])}.
   * <p>
   * Method under test:
   * {@link AwaitProcessInstanceAssertions#expectFields(ProcessResultMatcher[])}
   */
  @Test
  @DisplayName("Test expectFields(ProcessResultMatcher[])")
  void testExpectFields2() {
    // Arrange
    EventSource eventSource = mock(EventSource.class);
    Mockito.<List<RuntimeEvent<?, ?>>>when(eventSource.getEvents()).thenReturn(new ArrayList<>());
    AwaitProcessInstanceAssertions awaitProcessInstanceAssertions = new AwaitProcessInstanceAssertions(
        new AwaitProcessInstanceAssertions(
            new ProcessInstanceAssertionsImpl(eventSource, new ArrayList<>(), mock(ProcessInstance.class))));
    ProcessResultMatcher processResultMatcher = mock(ProcessResultMatcher.class);
    doNothing().when(processResultMatcher).match(Mockito.<ProcessInstance>any());

    // Act
    ProcessInstanceAssertions actualExpectFieldsResult = awaitProcessInstanceAssertions
        .expectFields(processResultMatcher);

    // Assert
    verify(eventSource).getEvents();
    verify(processResultMatcher).match(isA(ProcessInstance.class));
    assertTrue(actualExpectFieldsResult instanceof AwaitProcessInstanceAssertions);
    assertSame(awaitProcessInstanceAssertions, actualExpectFieldsResult);
  }

  /**
   * Test {@link AwaitProcessInstanceAssertions#expect(ProcessTaskMatcher[])}.
   * <p>
   * Method under test:
   * {@link AwaitProcessInstanceAssertions#expect(ProcessTaskMatcher[])}
   */
  @Test
  @DisplayName("Test expect(ProcessTaskMatcher[])")
  void testExpect() {
    // Arrange
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenReturn("42");
    EventSource eventSource = mock(EventSource.class);
    AwaitProcessInstanceAssertions awaitProcessInstanceAssertions = new AwaitProcessInstanceAssertions(
        new ProcessInstanceAssertionsImpl(eventSource, new ArrayList<>(), processInstance));
    ProcessTaskMatcher processTaskMatcher = mock(ProcessTaskMatcher.class);
    doNothing().when(processTaskMatcher).match(Mockito.<String>any(), Mockito.<List<TaskSource>>any());

    // Act
    ProcessInstanceAssertions actualExpectResult = awaitProcessInstanceAssertions.expect(processTaskMatcher);

    // Assert
    verify(processInstance).getId();
    verify(processTaskMatcher).match(eq("42"), isA(List.class));
    assertTrue(actualExpectResult instanceof AwaitProcessInstanceAssertions);
    assertSame(awaitProcessInstanceAssertions, actualExpectResult);
  }

  /**
   * Test {@link AwaitProcessInstanceAssertions#expect(ProcessTaskMatcher[])}.
   * <p>
   * Method under test:
   * {@link AwaitProcessInstanceAssertions#expect(ProcessTaskMatcher[])}
   */
  @Test
  @DisplayName("Test expect(ProcessTaskMatcher[])")
  void testExpect2() {
    // Arrange
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenReturn("42");
    EventSource eventSource = mock(EventSource.class);
    AwaitProcessInstanceAssertions awaitProcessInstanceAssertions = new AwaitProcessInstanceAssertions(
        new AwaitProcessInstanceAssertions(
            new ProcessInstanceAssertionsImpl(eventSource, new ArrayList<>(), processInstance)));
    ProcessTaskMatcher processTaskMatcher = mock(ProcessTaskMatcher.class);
    doNothing().when(processTaskMatcher).match(Mockito.<String>any(), Mockito.<List<TaskSource>>any());

    // Act
    ProcessInstanceAssertions actualExpectResult = awaitProcessInstanceAssertions.expect(processTaskMatcher);

    // Assert
    verify(processInstance).getId();
    verify(processTaskMatcher).match(eq("42"), isA(List.class));
    assertTrue(actualExpectResult instanceof AwaitProcessInstanceAssertions);
    assertSame(awaitProcessInstanceAssertions, actualExpectResult);
  }
}
