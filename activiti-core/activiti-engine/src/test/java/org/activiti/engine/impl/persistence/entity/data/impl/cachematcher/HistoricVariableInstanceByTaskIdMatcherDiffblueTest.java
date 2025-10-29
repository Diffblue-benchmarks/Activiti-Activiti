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
package org.activiti.engine.impl.persistence.entity.data.impl.cachematcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class HistoricVariableInstanceByTaskIdMatcherDiffblueTest {
  /**
   * Method under test:
   * {@link HistoricVariableInstanceByTaskIdMatcher#isRetained(HistoricVariableInstanceEntity, Object)}
   */
  @Test
  public void testIsRetained() {
    // Arrange
    HistoricVariableInstanceByTaskIdMatcher historicVariableInstanceByTaskIdMatcher = new HistoricVariableInstanceByTaskIdMatcher();

    // Act and Assert
    assertFalse(
        historicVariableInstanceByTaskIdMatcher.isRetained(new HistoricVariableInstanceEntityImpl(), JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceByTaskIdMatcher#isRetained(HistoricVariableInstanceEntity, Object)}
   */
  @Test
  public void testIsRetained2() {
    // Arrange
    HistoricVariableInstanceByTaskIdMatcher historicVariableInstanceByTaskIdMatcher = new HistoricVariableInstanceByTaskIdMatcher();
    HistoricVariableInstanceEntity historicVariableInstanceEntity = mock(HistoricVariableInstanceEntity.class);
    when(historicVariableInstanceEntity.getTaskId()).thenReturn("42");

    // Act
    boolean actualIsRetainedResult = historicVariableInstanceByTaskIdMatcher.isRetained(historicVariableInstanceEntity,
        "Parameter");

    // Assert
    verify(historicVariableInstanceEntity, atLeast(1)).getTaskId();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceByTaskIdMatcher#isRetained(HistoricVariableInstanceEntity, Object)}
   */
  @Test
  public void testIsRetained3() {
    // Arrange
    HistoricVariableInstanceByTaskIdMatcher historicVariableInstanceByTaskIdMatcher = new HistoricVariableInstanceByTaskIdMatcher();
    HistoricVariableInstanceEntity historicVariableInstanceEntity = mock(HistoricVariableInstanceEntity.class);
    when(historicVariableInstanceEntity.getTaskId()).thenReturn("42");

    // Act
    boolean actualIsRetainedResult = historicVariableInstanceByTaskIdMatcher.isRetained(historicVariableInstanceEntity,
        "42");

    // Assert
    verify(historicVariableInstanceEntity, atLeast(1)).getTaskId();
    assertTrue(actualIsRetainedResult);
  }
}
