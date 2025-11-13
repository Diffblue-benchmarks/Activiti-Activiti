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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.runtime.ProcessInstanceBuilderImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CreateProcessInstanceCmdDiffblueTest {
  /**
   * Test {@link CreateProcessInstanceCmd#CreateProcessInstanceCmd(String, String, String, Map)}.
   *
   * <p>Method under test: {@link CreateProcessInstanceCmd#CreateProcessInstanceCmd(String, String,
   * String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CreateProcessInstanceCmd.<init>(String, String, String, Map)",
    "void CreateProcessInstanceCmd.<init>(String, String, String, Map, String)"
  })
  public void testNewCreateProcessInstanceCmd() {
    // Arrange and Act
    CreateProcessInstanceCmd actualCreateProcessInstanceCmd =
        new CreateProcessInstanceCmd(
            "Process Definition Key", "42", "Business Key", new HashMap<>());

    // Assert
    assertTrue(actualCreateProcessInstanceCmd.variables.isEmpty());
  }

  /**
   * Test {@link CreateProcessInstanceCmd#CreateProcessInstanceCmd(String, String, String, Map,
   * String)}.
   *
   * <p>Method under test: {@link CreateProcessInstanceCmd#CreateProcessInstanceCmd(String, String,
   * String, Map, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CreateProcessInstanceCmd.<init>(String, String, String, Map)",
    "void CreateProcessInstanceCmd.<init>(String, String, String, Map, String)"
  })
  public void testNewCreateProcessInstanceCmd2() {
    // Arrange and Act
    CreateProcessInstanceCmd actualCreateProcessInstanceCmd =
        new CreateProcessInstanceCmd(
            "Process Definition Key", "42", "Business Key", new HashMap<>(), "42");

    // Assert
    assertTrue(actualCreateProcessInstanceCmd.variables.isEmpty());
  }

  /**
   * Test {@link CreateProcessInstanceCmd#CreateProcessInstanceCmd(ProcessInstanceBuilderImpl)}.
   *
   * <ul>
   *   <li>Then return {@link CreateProcessInstanceCmd#businessKey} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * CreateProcessInstanceCmd#CreateProcessInstanceCmd(ProcessInstanceBuilderImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateProcessInstanceCmd.<init>(ProcessInstanceBuilderImpl)"})
  public void testNewCreateProcessInstanceCmd_thenReturnBusinessKeyIsNull() {
    // Arrange and Act
    CreateProcessInstanceCmd actualCreateProcessInstanceCmd =
        new CreateProcessInstanceCmd(new ProcessInstanceBuilderImpl(new RuntimeServiceImpl()));

    // Assert
    assertNull(actualCreateProcessInstanceCmd.businessKey);
    assertNull(actualCreateProcessInstanceCmd.processDefinitionId);
    assertNull(actualCreateProcessInstanceCmd.processDefinitionKey);
    assertNull(actualCreateProcessInstanceCmd.processInstanceName);
    assertNull(actualCreateProcessInstanceCmd.tenantId);
    assertNull(actualCreateProcessInstanceCmd.transientVariables);
    assertNull(actualCreateProcessInstanceCmd.variables);
    assertNull(actualCreateProcessInstanceCmd.processInstanceHelper);
  }
}
