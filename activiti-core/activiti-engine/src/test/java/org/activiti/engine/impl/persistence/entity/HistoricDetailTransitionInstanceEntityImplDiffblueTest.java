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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class HistoricDetailTransitionInstanceEntityImplDiffblueTest {
  /**
   * Test new {@link HistoricDetailTransitionInstanceEntityImpl} (default
   * constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link HistoricDetailTransitionInstanceEntityImpl}
   */
  @Test
  public void testNewHistoricDetailTransitionInstanceEntityImpl() {
    // Arrange and Act
    HistoricDetailTransitionInstanceEntityImpl actualHistoricDetailTransitionInstanceEntityImpl = new HistoricDetailTransitionInstanceEntityImpl();

    // Assert
    assertNull(actualHistoricDetailTransitionInstanceEntityImpl.getId());
    assertNull(actualHistoricDetailTransitionInstanceEntityImpl.getActivityInstanceId());
    assertNull(actualHistoricDetailTransitionInstanceEntityImpl.getDetailType());
    assertNull(actualHistoricDetailTransitionInstanceEntityImpl.getExecutionId());
    assertNull(actualHistoricDetailTransitionInstanceEntityImpl.getProcessInstanceId());
    assertNull(actualHistoricDetailTransitionInstanceEntityImpl.getTaskId());
    assertNull(actualHistoricDetailTransitionInstanceEntityImpl.getTime());
    assertFalse(actualHistoricDetailTransitionInstanceEntityImpl.isDeleted());
    assertFalse(actualHistoricDetailTransitionInstanceEntityImpl.isInserted());
    assertFalse(actualHistoricDetailTransitionInstanceEntityImpl.isUpdated());
  }
}