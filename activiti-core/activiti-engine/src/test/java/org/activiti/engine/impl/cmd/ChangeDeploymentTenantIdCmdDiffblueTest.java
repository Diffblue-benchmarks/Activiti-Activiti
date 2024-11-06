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
import org.junit.Test;

public class ChangeDeploymentTenantIdCmdDiffblueTest {
  /**
   * Test
   * {@link ChangeDeploymentTenantIdCmd#ChangeDeploymentTenantIdCmd(String, String)}.
   * <p>
   * Method under test:
   * {@link ChangeDeploymentTenantIdCmd#ChangeDeploymentTenantIdCmd(String, String)}
   */
  @Test
  public void testNewChangeDeploymentTenantIdCmd() {
    // Arrange and Act
    ChangeDeploymentTenantIdCmd actualChangeDeploymentTenantIdCmd = new ChangeDeploymentTenantIdCmd("42", "42");

    // Assert
    assertEquals("42", actualChangeDeploymentTenantIdCmd.deploymentId);
    assertEquals("42", actualChangeDeploymentTenantIdCmd.newTenantId);
  }
}
