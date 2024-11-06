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
import static org.junit.Assert.assertSame;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.Test;

public class EventLogEntryEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link EventLogEntryEntityImpl}
   *   <li>{@link EventLogEntryEntityImpl#setData(byte[])}
   *   <li>{@link EventLogEntryEntityImpl#setExecutionId(String)}
   *   <li>{@link EventLogEntryEntityImpl#setLockOwner(String)}
   *   <li>{@link EventLogEntryEntityImpl#setLockTime(String)}
   *   <li>{@link EventLogEntryEntityImpl#setLogNumber(long)}
   *   <li>{@link EventLogEntryEntityImpl#setProcessDefinitionId(String)}
   *   <li>{@link EventLogEntryEntityImpl#setProcessInstanceId(String)}
   *   <li>{@link EventLogEntryEntityImpl#setProcessed(int)}
   *   <li>{@link EventLogEntryEntityImpl#setTaskId(String)}
   *   <li>{@link EventLogEntryEntityImpl#setTimeStamp(Date)}
   *   <li>{@link EventLogEntryEntityImpl#setType(String)}
   *   <li>{@link EventLogEntryEntityImpl#setUserId(String)}
   *   <li>{@link EventLogEntryEntityImpl#toString()}
   *   <li>{@link EventLogEntryEntityImpl#getData()}
   *   <li>{@link EventLogEntryEntityImpl#getExecutionId()}
   *   <li>{@link EventLogEntryEntityImpl#getLockOwner()}
   *   <li>{@link EventLogEntryEntityImpl#getLockTime()}
   *   <li>{@link EventLogEntryEntityImpl#getLogNumber()}
   *   <li>{@link EventLogEntryEntityImpl#getPersistentState()}
   *   <li>{@link EventLogEntryEntityImpl#getProcessDefinitionId()}
   *   <li>{@link EventLogEntryEntityImpl#getProcessInstanceId()}
   *   <li>{@link EventLogEntryEntityImpl#getProcessed()}
   *   <li>{@link EventLogEntryEntityImpl#getTaskId()}
   *   <li>{@link EventLogEntryEntityImpl#getTimeStamp()}
   *   <li>{@link EventLogEntryEntityImpl#getType()}
   *   <li>{@link EventLogEntryEntityImpl#getUserId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() throws UnsupportedEncodingException {
    // Arrange and Act
    EventLogEntryEntityImpl actualEventLogEntryEntityImpl = new EventLogEntryEntityImpl();
    byte[] data = "AXAXAXAX".getBytes("UTF-8");
    actualEventLogEntryEntityImpl.setData(data);
    actualEventLogEntryEntityImpl.setExecutionId("42");
    actualEventLogEntryEntityImpl.setLockOwner("Lock Owner");
    actualEventLogEntryEntityImpl.setLockTime("Lock Time");
    actualEventLogEntryEntityImpl.setLogNumber(1L);
    actualEventLogEntryEntityImpl.setProcessDefinitionId("42");
    actualEventLogEntryEntityImpl.setProcessInstanceId("42");
    actualEventLogEntryEntityImpl.setProcessed(1);
    actualEventLogEntryEntityImpl.setTaskId("42");
    Date timeStamp = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualEventLogEntryEntityImpl.setTimeStamp(timeStamp);
    actualEventLogEntryEntityImpl.setType("Type");
    actualEventLogEntryEntityImpl.setUserId("42");
    actualEventLogEntryEntityImpl.toString();
    byte[] actualData = actualEventLogEntryEntityImpl.getData();
    String actualExecutionId = actualEventLogEntryEntityImpl.getExecutionId();
    String actualLockOwner = actualEventLogEntryEntityImpl.getLockOwner();
    String actualLockTime = actualEventLogEntryEntityImpl.getLockTime();
    long actualLogNumber = actualEventLogEntryEntityImpl.getLogNumber();
    actualEventLogEntryEntityImpl.getPersistentState();
    String actualProcessDefinitionId = actualEventLogEntryEntityImpl.getProcessDefinitionId();
    String actualProcessInstanceId = actualEventLogEntryEntityImpl.getProcessInstanceId();
    int actualProcessed = actualEventLogEntryEntityImpl.getProcessed();
    String actualTaskId = actualEventLogEntryEntityImpl.getTaskId();
    Date actualTimeStamp = actualEventLogEntryEntityImpl.getTimeStamp();
    String actualType = actualEventLogEntryEntityImpl.getType();

    // Assert that nothing has changed
    assertEquals("42", actualExecutionId);
    assertEquals("42", actualProcessDefinitionId);
    assertEquals("42", actualProcessInstanceId);
    assertEquals("42", actualTaskId);
    assertEquals("42", actualEventLogEntryEntityImpl.getUserId());
    assertEquals("Lock Owner", actualLockOwner);
    assertEquals("Lock Time", actualLockTime);
    assertEquals("Type", actualType);
    assertEquals(1, actualProcessed);
    assertEquals(1L, actualLogNumber);
    assertFalse(actualEventLogEntryEntityImpl.isDeleted());
    assertFalse(actualEventLogEntryEntityImpl.isInserted());
    assertFalse(actualEventLogEntryEntityImpl.isUpdated());
    assertSame(data, actualData);
    assertSame(timeStamp, actualTimeStamp);
  }
}
