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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.junit.Test;

public class ProcessInstanceQueryImplDiffblueTest {
  /**
   * Test {@link ProcessInstanceQueryImpl#deploymentIdIn(List)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  public void testDeploymentIdIn() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();
    ArrayList<String> deploymentIds = new ArrayList<>();

    // Act
    ProcessInstanceQueryImpl actualDeploymentIdInResult = processInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, processInstanceQueryImpl.getDeploymentIds());
    assertSame(processInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#excludeSubprocesses(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessInstanceQueryImpl#excludeSubprocesses(boolean)}
   */
  @Test
  public void testExcludeSubprocesses() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualExcludeSubprocessesResult = processInstanceQueryImpl.excludeSubprocesses(true);

    // Assert
    assertTrue(processInstanceQueryImpl.isExcludeSubprocesses());
    assertSame(processInstanceQueryImpl, actualExcludeSubprocessesResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#active()}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#active()}
   */
  @Test
  public void testActive() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertSame(processInstanceQueryImpl, processInstanceQueryImpl.active());
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#endOr()}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#endOr()}
   */
  @Test
  public void testEndOr() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ProcessInstanceQueryImpl()).endOr());
  }
}
