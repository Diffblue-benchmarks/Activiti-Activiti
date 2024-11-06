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
package org.activiti.spring.autodeployment;

import static org.junit.Assert.assertEquals;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.core.common.spring.project.ApplicationUpgradeContextService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class NeverFailAutoDeploymentStrategyDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link NeverFailAutoDeploymentStrategy#NeverFailAutoDeploymentStrategy(ApplicationUpgradeContextService)}
   *   <li>{@link NeverFailAutoDeploymentStrategy#getDeploymentMode()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act and Assert
    assertEquals(NeverFailAutoDeploymentStrategy.DEPLOYMENT_MODE, (new NeverFailAutoDeploymentStrategy(
        new ApplicationUpgradeContextService("Path", 1, true, objectMapper, new AnnotationConfigApplicationContext())))
        .getDeploymentMode());
  }
}
