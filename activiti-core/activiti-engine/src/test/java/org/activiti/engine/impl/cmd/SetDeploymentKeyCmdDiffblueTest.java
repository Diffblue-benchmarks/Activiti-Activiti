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

public class SetDeploymentKeyCmdDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SetDeploymentKeyCmd#SetDeploymentKeyCmd(String, String)}
   *   <li>{@link SetDeploymentKeyCmd#setDeploymentId(String)}
   *   <li>{@link SetDeploymentKeyCmd#setKey(String)}
   *   <li>{@link SetDeploymentKeyCmd#getDeploymentId()}
   *   <li>{@link SetDeploymentKeyCmd#getKey()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    SetDeploymentKeyCmd actualSetDeploymentKeyCmd = new SetDeploymentKeyCmd("42", "Key");
    actualSetDeploymentKeyCmd.setDeploymentId("42");
    actualSetDeploymentKeyCmd.setKey("Key");
    String actualDeploymentId = actualSetDeploymentKeyCmd.getDeploymentId();

    // Assert that nothing has changed
    assertEquals("42", actualDeploymentId);
    assertEquals("Key", actualSetDeploymentKeyCmd.getKey());
  }
}
