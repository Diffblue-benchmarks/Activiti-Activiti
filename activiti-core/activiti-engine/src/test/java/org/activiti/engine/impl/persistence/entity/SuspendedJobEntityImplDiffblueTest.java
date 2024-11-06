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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class SuspendedJobEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link SuspendedJobEntityImpl}
   *   <li>{@link SuspendedJobEntityImpl#toString()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    SuspendedJobEntityImpl actualSuspendedJobEntityImpl = new SuspendedJobEntityImpl();
    String actualToStringResult = actualSuspendedJobEntityImpl.toString();

    // Assert
    assertEquals("", actualSuspendedJobEntityImpl.getTenantId());
    assertEquals("SuspendedJobEntity [id=null]", actualToStringResult);
    assertNull(actualSuspendedJobEntityImpl.getId());
    assertNull(actualSuspendedJobEntityImpl.getExceptionMessage());
    assertNull(actualSuspendedJobEntityImpl.getExecutionId());
    assertNull(actualSuspendedJobEntityImpl.getJobHandlerConfiguration());
    assertNull(actualSuspendedJobEntityImpl.getJobHandlerType());
    assertNull(actualSuspendedJobEntityImpl.getJobType());
    assertNull(actualSuspendedJobEntityImpl.getProcessDefinitionId());
    assertNull(actualSuspendedJobEntityImpl.getProcessInstanceId());
    assertNull(actualSuspendedJobEntityImpl.getRepeat());
    assertNull(actualSuspendedJobEntityImpl.getDuedate());
    assertNull(actualSuspendedJobEntityImpl.getEndDate());
    assertNull(actualSuspendedJobEntityImpl.getExceptionByteArrayRef());
    assertEquals(0, actualSuspendedJobEntityImpl.getMaxIterations());
    assertEquals(0, actualSuspendedJobEntityImpl.getRetries());
    assertEquals(1, actualSuspendedJobEntityImpl.getRevision());
    assertFalse(actualSuspendedJobEntityImpl.isDeleted());
    assertFalse(actualSuspendedJobEntityImpl.isInserted());
    assertFalse(actualSuspendedJobEntityImpl.isUpdated());
    assertTrue(actualSuspendedJobEntityImpl.isExclusive());
  }
}
