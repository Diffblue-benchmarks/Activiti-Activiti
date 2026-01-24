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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.payloads.RemoveProcessVariablesPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RemoveVariablesPayloadBuilderDiffblueTest {
  /**
   * Test {@link RemoveVariablesPayloadBuilder#build()}.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link RemoveVariablesPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link RemoveVariablesPayloadBuilder}
   *   <li>{@link RemoveVariablesPayloadBuilder#withProcessInstanceId(String)}
   *   <li>{@link RemoveVariablesPayloadBuilder#withVariableNames(List)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RemoveVariablesPayloadBuilder.<init>()",
    "RemoveProcessVariablesPayload RemoveVariablesPayloadBuilder.build()",
    "RemoveVariablesPayloadBuilder RemoveVariablesPayloadBuilder.withProcessInstanceId(String)",
    "RemoveVariablesPayloadBuilder RemoveVariablesPayloadBuilder.withVariableNames(List)"
  })
  void testBuild() {
    // Arrange and Act
    RemoveVariablesPayloadBuilder actualWithVariableNamesResult =
        new RemoveVariablesPayloadBuilder()
            .withProcessInstance(mock(ProcessInstance.class))
            .withProcessInstanceId("42")
            .withVariableNames("Variable Name");
    ArrayList<String> variableNames = new ArrayList<>();
    RemoveProcessVariablesPayload actualRemoveProcessVariablesPayload =
        actualWithVariableNamesResult.withVariableNames(variableNames).build();

    // Assert
    assertEquals("42", actualRemoveProcessVariablesPayload.getProcessInstanceId());
    List<String> variableNames2 = actualRemoveProcessVariablesPayload.getVariableNames();
    assertTrue(variableNames2.isEmpty());
    assertSame(variableNames, variableNames2);
  }
}
