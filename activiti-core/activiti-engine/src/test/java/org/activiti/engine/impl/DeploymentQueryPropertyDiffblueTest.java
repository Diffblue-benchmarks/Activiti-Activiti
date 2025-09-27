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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DeploymentQueryPropertyDiffblueTest {
  /**
   * Test {@link DeploymentQueryProperty#DeploymentQueryProperty(String)}.
   *
   * <p>Method under test: {@link DeploymentQueryProperty#DeploymentQueryProperty(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeploymentQueryProperty.<init>(String)"})
  public void testNewDeploymentQueryProperty() {
    // Arrange, Act and Assert
    assertEquals("Name", new DeploymentQueryProperty("Name").getName());
  }

  /**
   * Test {@link DeploymentQueryProperty#getName()}.
   *
   * <p>Method under test: {@link DeploymentQueryProperty#getName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DeploymentQueryProperty.getName()"})
  public void testGetName() {
    // Arrange, Act and Assert
    assertEquals("Name", new DeploymentQueryProperty("Name").getName());
  }

  /**
   * Test {@link DeploymentQueryProperty#findByName(String)}.
   *
   * <ul>
   *   <li>When {@code Property Name}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryProperty#findByName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryProperty DeploymentQueryProperty.findByName(String)"})
  public void testFindByName_whenPropertyName_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(DeploymentQueryProperty.findByName("Property Name"));
  }

  /**
   * Test {@link DeploymentQueryProperty#findByName(String)}.
   *
   * <ul>
   *   <li>When {@code RES.DEPLOY_TIME_}.
   *   <li>Then return Name is {@code RES.DEPLOY_TIME_}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryProperty#findByName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryProperty DeploymentQueryProperty.findByName(String)"})
  public void testFindByName_whenResDeployTime_thenReturnNameIsResDeployTime() {
    // Arrange, Act and Assert
    assertEquals(
        "RES.DEPLOY_TIME_", DeploymentQueryProperty.findByName("RES.DEPLOY_TIME_").getName());
  }
}
