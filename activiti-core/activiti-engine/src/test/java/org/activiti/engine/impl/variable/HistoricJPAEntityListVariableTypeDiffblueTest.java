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
package org.activiti.engine.impl.variable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class HistoricJPAEntityListVariableTypeDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link HistoricJPAEntityListVariableType#getSharedInstance()}
   *   <li>{@link HistoricJPAEntityListVariableType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    HistoricJPAEntityListVariableType actualSharedInstance = HistoricJPAEntityListVariableType.getSharedInstance();
    HistoricJPAEntityListVariableType actualSharedInstance2 = actualSharedInstance.getSharedInstance();

    // Assert
    assertTrue(actualSharedInstance.isCachable());
    assertSame(actualSharedInstance, actualSharedInstance2);
  }

  /**
   * Test new {@link HistoricJPAEntityListVariableType} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link HistoricJPAEntityListVariableType}
   */
  @Test
  public void testNewHistoricJPAEntityListVariableType() {
    // Arrange and Act
    HistoricJPAEntityListVariableType actualHistoricJPAEntityListVariableType = new HistoricJPAEntityListVariableType();

    // Assert
    assertTrue(actualHistoricJPAEntityListVariableType.isCachable());
    assertEquals(JPAEntityListVariableType.TYPE_NAME, actualHistoricJPAEntityListVariableType.getTypeName());
  }
}
