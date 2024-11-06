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
package org.activiti.engine.logging;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class LogMDCDiffblueTest {
  /**
   * Test {@link LogMDC#putMDCExecution(ExecutionEntity)}.
   * <p>
   * Method under test: {@link LogMDC#putMDCExecution(ExecutionEntity)}
   */
  @Test
  public void testPutMDCExecution() {
    // Arrange
    ExecutionEntityImpl e = mock(ExecutionEntityImpl.class);
    when(e.getId()).thenReturn("42");
    when(e.getProcessDefinitionId()).thenReturn("42");
    when(e.getProcessInstanceBusinessKey()).thenReturn(null);
    when(e.getProcessInstanceId()).thenReturn("42");

    // Act
    LogMDC.putMDCExecution(e);

    // Assert
    verify(e, atLeast(1)).getId();
    verify(e, atLeast(1)).getProcessDefinitionId();
    verify(e).getProcessInstanceBusinessKey();
    verify(e, atLeast(1)).getProcessInstanceId();
  }

  /**
   * Test {@link LogMDC#putMDCExecution(ExecutionEntity)}.
   * <ul>
   *   <li>Given {@code Process Instance Business Key}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LogMDC#putMDCExecution(ExecutionEntity)}
   */
  @Test
  public void testPutMDCExecution_givenProcessInstanceBusinessKey() {
    // Arrange
    ExecutionEntityImpl e = mock(ExecutionEntityImpl.class);
    when(e.getId()).thenReturn("42");
    when(e.getProcessDefinitionId()).thenReturn("42");
    when(e.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");
    when(e.getProcessInstanceId()).thenReturn("42");

    // Act
    LogMDC.putMDCExecution(e);

    // Assert
    verify(e, atLeast(1)).getId();
    verify(e, atLeast(1)).getProcessDefinitionId();
    verify(e, atLeast(1)).getProcessInstanceBusinessKey();
    verify(e, atLeast(1)).getProcessInstanceId();
  }
}
