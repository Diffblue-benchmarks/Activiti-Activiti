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
package org.activiti.engine.delegate.event;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ActivitiEntityEventDiffblueTest {
  /**
   * Test {@link ActivitiEntityEvent#getReason()}.
   * <p>
   * Method under test: {@link ActivitiEntityEvent#getReason()}
   */
  @Test
  public void testGetReason() {
    // Arrange, Act and Assert
    assertNull((new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .getReason());
  }

  /**
   * Test {@link ActivitiEntityEvent#getReason()}.
   * <ul>
   *   <li>Given
   * {@link ActivitiProcessCancelledEventImpl#ActivitiProcessCancelledEventImpl(ProcessInstance)}
   * with {@link ProcessInstance}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiEntityEvent#getReason()}
   */
  @Test
  public void testGetReason_givenActivitiProcessCancelledEventImplWithProcessInstance() {
    // Arrange, Act and Assert
    assertNull((new ActivitiProcessCancelledEventImpl(mock(ProcessInstance.class))).getReason());
  }
}
