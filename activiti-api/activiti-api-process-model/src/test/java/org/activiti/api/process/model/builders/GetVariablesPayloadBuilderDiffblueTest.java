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
package org.activiti.api.process.model.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.api.process.model.ProcessInstance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GetVariablesPayloadBuilderDiffblueTest {
  /**
   * Test {@link GetVariablesPayloadBuilder#withProcessInstance(ProcessInstance)}.
   * <p>
   * Method under test:
   * {@link GetVariablesPayloadBuilder#withProcessInstance(ProcessInstance)}
   */
  @Test
  @DisplayName("Test withProcessInstance(ProcessInstance)")
  void testWithProcessInstance() {
    // Arrange
    GetVariablesPayloadBuilder variablesResult = ProcessPayloadBuilder.variables();
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenReturn("42");

    // Act
    GetVariablesPayloadBuilder actualWithProcessInstanceResult = variablesResult.withProcessInstance(processInstance);

    // Assert
    verify(processInstance).getId();
    assertEquals("42", actualWithProcessInstanceResult.build().getProcessInstanceId());
    assertEquals("42", variablesResult.build().getProcessInstanceId());
    assertSame(variablesResult, actualWithProcessInstanceResult);
  }

  /**
   * Test {@link GetVariablesPayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetVariablesPayloadBuilder#build()}
   *   <li>default or parameterless constructor of
   * {@link GetVariablesPayloadBuilder}
   *   <li>{@link GetVariablesPayloadBuilder#withProcessInstanceId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  void testBuild() {
    // Arrange, Act and Assert
    assertEquals("42",
        (new GetVariablesPayloadBuilder()).withProcessInstance(mock(ProcessInstance.class))
            .withProcessInstanceId("42")
            .build()
            .getProcessInstanceId());
  }
}
