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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.payloads.SetProcessVariablesPayload;
import org.junit.jupiter.api.Test;

class SetVariablesPayloadBuilderDiffblueTest {
  /**
   * Method under test:
   * {@link SetVariablesPayloadBuilder#withProcessInstance(ProcessInstance)}
   */
  @Test
  void testWithProcessInstance() {
    // Arrange
    SetVariablesPayloadBuilder setVariablesResult = ProcessPayloadBuilder.setVariables();
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenReturn("42");

    // Act
    SetVariablesPayloadBuilder actualWithProcessInstanceResult = setVariablesResult
        .withProcessInstance(processInstance);

    // Assert
    verify(processInstance).getId();
    assertEquals("42", setVariablesResult.build().getProcessInstanceId());
    assertSame(setVariablesResult, actualWithProcessInstanceResult);
  }

  /**
   * Method under test:
   * {@link SetVariablesPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  void testWithVariable() {
    // Arrange
    SetVariablesPayloadBuilder setVariablesResult = ProcessPayloadBuilder.setVariables();

    // Act and Assert
    assertSame(setVariablesResult, setVariablesResult.withVariable("Name", "Value"));
  }

  /**
   * Method under test:
   * {@link SetVariablesPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  void testWithVariable2() {
    // Arrange
    SetVariablesPayloadBuilder setVariablesResult = ProcessPayloadBuilder.setVariables();
    setVariablesResult.withVariables(null);

    // Act and Assert
    assertSame(setVariablesResult, setVariablesResult.withVariable("Name", "Value"));
  }

  /**
   * Method under test:
   * {@link SetVariablesPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  void testWithVariable3() {
    // Arrange
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenReturn("42");
    SetVariablesPayloadBuilder setVariablesResult = ProcessPayloadBuilder.setVariables();
    setVariablesResult.withProcessInstance(processInstance);

    // Act
    SetVariablesPayloadBuilder actualWithVariableResult = setVariablesResult.withVariable("Name", "Value");

    // Assert
    verify(processInstance).getId();
    assertSame(setVariablesResult, actualWithVariableResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SetVariablesPayloadBuilder#build()}
   *   <li>{@link SetVariablesPayloadBuilder#SetVariablesPayloadBuilder(String)}
   *   <li>{@link SetVariablesPayloadBuilder#withProcessInstanceId(String)}
   *   <li>{@link SetVariablesPayloadBuilder#withVariables(Map)}
   * </ul>
   */
  @Test
  void testBuild() {
    // Arrange
    SetVariablesPayloadBuilder withVariableResult = (new SetVariablesPayloadBuilder("42"))
        .withProcessInstance(mock(ProcessInstance.class))
        .withProcessInstanceId("42")
        .withVariable("Name", "Value");
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    SetProcessVariablesPayload actualBuildResult = withVariableResult.withVariables(variables).build();

    // Assert
    assertEquals("42", actualBuildResult.getProcessInstanceId());
    Map<String, Object> variables2 = actualBuildResult.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }

  /**
   * Method under test:
   * {@link SetVariablesPayloadBuilder#SetVariablesPayloadBuilder(ProcessInstance)}
   */
  @Test
  void testNewSetVariablesPayloadBuilder() {
    // Arrange
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenReturn("42");

    // Act
    SetVariablesPayloadBuilder actualSetVariablesPayloadBuilder = new SetVariablesPayloadBuilder(processInstance);

    // Assert
    verify(processInstance).getId();
    SetProcessVariablesPayload buildResult = actualSetVariablesPayloadBuilder.build();
    assertEquals("42", buildResult.getProcessInstanceId());
    assertTrue(buildResult.getVariables().isEmpty());
  }
}
