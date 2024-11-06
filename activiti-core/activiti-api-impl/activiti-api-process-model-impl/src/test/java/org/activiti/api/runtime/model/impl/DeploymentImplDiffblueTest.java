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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeploymentImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DeploymentImpl}
   *   <li>{@link DeploymentImpl#setId(String)}
   *   <li>{@link DeploymentImpl#setName(String)}
   *   <li>{@link DeploymentImpl#setProjectReleaseVersion(String)}
   *   <li>{@link DeploymentImpl#setVersion(Integer)}
   *   <li>{@link DeploymentImpl#getId()}
   *   <li>{@link DeploymentImpl#getName()}
   *   <li>{@link DeploymentImpl#getProjectReleaseVersion()}
   *   <li>{@link DeploymentImpl#getVersion()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    DeploymentImpl actualDeploymentImpl = new DeploymentImpl();
    actualDeploymentImpl.setId("42");
    actualDeploymentImpl.setName("Name");
    actualDeploymentImpl.setProjectReleaseVersion("1.0.2");
    actualDeploymentImpl.setVersion(1);
    String actualId = actualDeploymentImpl.getId();
    String actualName = actualDeploymentImpl.getName();
    String actualProjectReleaseVersion = actualDeploymentImpl.getProjectReleaseVersion();

    // Assert that nothing has changed
    assertEquals("1.0.2", actualProjectReleaseVersion);
    assertEquals("42", actualId);
    assertEquals("Name", actualName);
    assertEquals(1, actualDeploymentImpl.getVersion().intValue());
  }
}
