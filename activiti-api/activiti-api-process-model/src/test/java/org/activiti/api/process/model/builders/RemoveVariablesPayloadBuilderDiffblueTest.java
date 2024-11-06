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
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.payloads.RemoveProcessVariablesPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RemoveVariablesPayloadBuilderDiffblueTest {
  /**
   * Test
   * {@link RemoveVariablesPayloadBuilder#withProcessInstance(ProcessInstance)}.
   * <p>
   * Method under test:
   * {@link RemoveVariablesPayloadBuilder#withProcessInstance(ProcessInstance)}
   */
  @Test
  @DisplayName("Test withProcessInstance(ProcessInstance)")
  void testWithProcessInstance() {
    // Arrange
    RemoveVariablesPayloadBuilder removeVariablesResult = ProcessPayloadBuilder.removeVariables();
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenReturn("42");

    // Act
    RemoveVariablesPayloadBuilder actualWithProcessInstanceResult = removeVariablesResult
        .withProcessInstance(processInstance);

    // Assert
    verify(processInstance).getId();
    assertEquals("42", removeVariablesResult.build().getProcessInstanceId());
    assertSame(removeVariablesResult, actualWithProcessInstanceResult);
  }

  /**
   * Test {@link RemoveVariablesPayloadBuilder#withVariableNames(String)} with
   * {@code variableName}.
   * <ul>
   *   <li>Given removeVariables.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RemoveVariablesPayloadBuilder#withVariableNames(String)}
   */
  @Test
  @DisplayName("Test withVariableNames(String) with 'variableName'; given removeVariables")
  void testWithVariableNamesWithVariableName_givenRemoveVariables() {
    // Arrange
    RemoveVariablesPayloadBuilder removeVariablesResult = ProcessPayloadBuilder.removeVariables();

    // Act and Assert
    assertSame(removeVariablesResult, removeVariablesResult.withVariableNames("Variable Name"));
  }

  /**
   * Test {@link RemoveVariablesPayloadBuilder#withVariableNames(String)} with
   * {@code variableName}.
   * <ul>
   *   <li>Then calls {@link ProcessInstance#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RemoveVariablesPayloadBuilder#withVariableNames(String)}
   */
  @Test
  @DisplayName("Test withVariableNames(String) with 'variableName'; then calls getId()")
  void testWithVariableNamesWithVariableName_thenCallsGetId() {
    // Arrange
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenReturn("42");
    RemoveVariablesPayloadBuilder removeVariablesResult = ProcessPayloadBuilder.removeVariables();
    removeVariablesResult.withProcessInstance(processInstance);

    // Act
    RemoveVariablesPayloadBuilder actualWithVariableNamesResult = removeVariablesResult
        .withVariableNames("Variable Name");

    // Assert
    verify(processInstance).getId();
    assertSame(removeVariablesResult, actualWithVariableNamesResult);
  }

  /**
   * Test {@link RemoveVariablesPayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveVariablesPayloadBuilder#build()}
   *   <li>default or parameterless constructor of
   * {@link RemoveVariablesPayloadBuilder}
   *   <li>{@link RemoveVariablesPayloadBuilder#withProcessInstanceId(String)}
   *   <li>{@link RemoveVariablesPayloadBuilder#withVariableNames(List)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  void testBuild() {
    // Arrange
    RemoveVariablesPayloadBuilder withVariableNamesResult = (new RemoveVariablesPayloadBuilder())
        .withProcessInstance(mock(ProcessInstance.class))
        .withProcessInstanceId("42")
        .withVariableNames("Variable Name");
    ArrayList<String> variableNames = new ArrayList<>();

    // Act
    RemoveProcessVariablesPayload actualBuildResult = withVariableNamesResult.withVariableNames(variableNames).build();

    // Assert
    assertEquals("42", actualBuildResult.getProcessInstanceId());
    List<String> variableNames2 = actualBuildResult.getVariableNames();
    assertTrue(variableNames2.isEmpty());
    assertSame(variableNames, variableNames2);
  }
}
