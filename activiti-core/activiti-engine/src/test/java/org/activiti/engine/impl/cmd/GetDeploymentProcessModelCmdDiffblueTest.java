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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class GetDeploymentProcessModelCmdDiffblueTest {
  /**
   * Test {@link GetDeploymentProcessModelCmd#GetDeploymentProcessModelCmd(String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link GetDeploymentProcessModelCmd#GetDeploymentProcessModelCmd(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetDeploymentProcessModelCmd.<init>(String)"})
  public void testNewGetDeploymentProcessModelCmd_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> new GetDeploymentProcessModelCmd(null));
  }

  /**
   * Test {@link GetDeploymentProcessModelCmd#GetDeploymentProcessModelCmd(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return {@link GetDeploymentProcessModelCmd#processDefinitionId} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link GetDeploymentProcessModelCmd#GetDeploymentProcessModelCmd(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetDeploymentProcessModelCmd.<init>(String)"})
  public void testNewGetDeploymentProcessModelCmd_when42_thenReturnProcessDefinitionIdIs42() {
    // Arrange, Act and Assert
    assertEquals("42", new GetDeploymentProcessModelCmd("42").processDefinitionId);
  }
}
