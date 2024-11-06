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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import org.junit.Test;

public class TimerJobEntityImplDiffblueTest {
  /**
   * Test {@link TimerJobEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Then return containsKey {@code lockExpirationTime}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerJobEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_thenReturnContainsKeyLockExpirationTime() {
    // Arrange
    TimerJobEntityImpl timerJobEntityImpl = new TimerJobEntityImpl();
    timerJobEntityImpl.setLockExpirationTime(mock(java.sql.Date.class));

    // Act
    Object actualPersistentState = timerJobEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(5, ((Map<String, Object>) actualPersistentState).size());
    assertNull(((Map<String, Object>) actualPersistentState).get("duedate"));
    assertNull(((Map<String, Object>) actualPersistentState).get("exceptionMessage"));
    assertNull(((Map<String, Object>) actualPersistentState).get("lockOwner"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("lockExpirationTime"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("retries"));
  }

  /**
   * Test {@link TimerJobEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Then return {@code lockExpirationTime} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerJobEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_thenReturnLockExpirationTimeIsNull() {
    // Arrange and Act
    Object actualPersistentState = (new TimerJobEntityImpl()).getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(5, ((Map<String, Integer>) actualPersistentState).size());
    assertNull(((Map<String, Integer>) actualPersistentState).get("duedate"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("exceptionMessage"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("lockExpirationTime"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("lockOwner"));
    assertEquals(0, ((Map<String, Integer>) actualPersistentState).get("retries").intValue());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link TimerJobEntityImpl}
   *   <li>{@link TimerJobEntityImpl#setLockExpirationTime(Date)}
   *   <li>{@link TimerJobEntityImpl#setLockOwner(String)}
   *   <li>{@link TimerJobEntityImpl#toString()}
   *   <li>{@link TimerJobEntityImpl#getLockExpirationTime()}
   *   <li>{@link TimerJobEntityImpl#getLockOwner()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    TimerJobEntityImpl actualTimerJobEntityImpl = new TimerJobEntityImpl();
    Date claimedUntil = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualTimerJobEntityImpl.setLockExpirationTime(claimedUntil);
    actualTimerJobEntityImpl.setLockOwner("Claimed By");
    String actualToStringResult = actualTimerJobEntityImpl.toString();
    Date actualLockExpirationTime = actualTimerJobEntityImpl.getLockExpirationTime();
    String actualLockOwner = actualTimerJobEntityImpl.getLockOwner();

    // Assert that nothing has changed
    assertEquals("", actualTimerJobEntityImpl.getTenantId());
    assertEquals("Claimed By", actualLockOwner);
    assertEquals("TimerJobEntity [id=null]", actualToStringResult);
    assertEquals(0, actualTimerJobEntityImpl.getMaxIterations());
    assertEquals(0, actualTimerJobEntityImpl.getRetries());
    assertEquals(1, actualTimerJobEntityImpl.getRevision());
    assertFalse(actualTimerJobEntityImpl.isDeleted());
    assertFalse(actualTimerJobEntityImpl.isInserted());
    assertFalse(actualTimerJobEntityImpl.isUpdated());
    assertTrue(actualTimerJobEntityImpl.isExclusive());
    assertSame(claimedUntil, actualLockExpirationTime);
  }
}
