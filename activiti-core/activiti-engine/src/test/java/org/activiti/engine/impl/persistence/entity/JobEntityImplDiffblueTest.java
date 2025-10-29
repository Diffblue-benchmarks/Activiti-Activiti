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
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class JobEntityImplDiffblueTest {
  /**
   * Method under test: {@link JobEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState() {
    // Arrange and Act
    Object actualPersistentState = (new JobEntityImpl()).getPersistentState();

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
   * Method under test: {@link JobEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState2() {
    // Arrange
    JobEntityImpl jobEntityImpl = new JobEntityImpl();
    jobEntityImpl.setDeleted(true);
    Date duedate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    jobEntityImpl.setDuedate(duedate);
    jobEntityImpl.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntityImpl.setExceptionMessage("An error occurred");
    jobEntityImpl.setExclusive(true);
    jobEntityImpl.setExecutionId("42");
    jobEntityImpl.setId("42");
    jobEntityImpl.setInserted(true);
    jobEntityImpl.setJobHandlerConfiguration("Job Handler Configuration");
    jobEntityImpl.setJobHandlerType("Job Handler Type");
    jobEntityImpl.setJobType("Job Type");
    Date claimedUntil = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    jobEntityImpl.setLockExpirationTime(claimedUntil);
    jobEntityImpl.setLockOwner("Claimed By");
    jobEntityImpl.setMaxIterations(3);
    jobEntityImpl.setProcessDefinitionId("42");
    jobEntityImpl.setProcessInstanceId("42");
    jobEntityImpl.setRepeat("Repeat");
    jobEntityImpl.setRetries(1);
    jobEntityImpl.setRevision(1);
    jobEntityImpl.setTenantId("42");
    jobEntityImpl.setUpdated(true);
    jobEntityImpl.setExceptionStacktrace(null);

    // Act
    Object actualPersistentState = jobEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(6, ((Map<String, Object>) actualPersistentState).size());
    assertEquals("An error occurred", ((Map<String, Object>) actualPersistentState).get("exceptionMessage"));
    assertEquals("Claimed By", ((Map<String, Object>) actualPersistentState).get("lockOwner"));
    assertNull(((Map<String, Object>) actualPersistentState).get("exceptionByteArrayId"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("retries"));
    assertSame(duedate, ((Map<String, Object>) actualPersistentState).get("duedate"));
    assertSame(claimedUntil, ((Map<String, Object>) actualPersistentState).get("lockExpirationTime"));
  }

  /**
   * Method under test: {@link JobEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState3() {
    // Arrange
    JobEntityImpl jobEntityImpl = new JobEntityImpl();
    jobEntityImpl.setLockExpirationTime(mock(java.sql.Date.class));

    // Act
    Object actualPersistentState = jobEntityImpl.getPersistentState();

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
   * Method under test: {@link JobEntityImpl#setExecution(ExecutionEntity)}
   */
  @Test
  public void testSetExecution() {
    // Arrange
    JobEntityImpl jobEntityImpl = new JobEntityImpl();
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    jobEntityImpl.setExecution(execution);

    // Assert
    assertNull(jobEntityImpl.getLockExpirationTime());
    List<JobEntity> jobEntityList = execution.jobs;
    assertEquals(1, jobEntityList.size());
    assertSame(jobEntityImpl, jobEntityList.get(0));
    List<JobEntity> expectedJobs = execution.jobs;
    assertSame(expectedJobs, execution.getJobs());
  }

  /**
   * Method under test: {@link JobEntityImpl#setExecution(ExecutionEntity)}
   */
  @Test
  public void testSetExecution2() {
    // Arrange
    JobEntityImpl jobEntityImpl = new JobEntityImpl();
    jobEntityImpl.setLockExpirationTime(mock(java.sql.Date.class));
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    jobEntityImpl.setExecution(execution);

    // Assert
    List<JobEntity> jobEntityList = execution.jobs;
    assertEquals(1, jobEntityList.size());
    assertSame(jobEntityImpl, jobEntityList.get(0));
    List<JobEntity> expectedJobs = execution.jobs;
    assertSame(expectedJobs, execution.getJobs());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link JobEntityImpl}
   *   <li>{@link JobEntityImpl#setLockExpirationTime(Date)}
   *   <li>{@link JobEntityImpl#setLockOwner(String)}
   *   <li>{@link JobEntityImpl#toString()}
   *   <li>{@link JobEntityImpl#getLockExpirationTime()}
   *   <li>{@link JobEntityImpl#getLockOwner()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    JobEntityImpl actualJobEntityImpl = new JobEntityImpl();
    Date claimedUntil = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualJobEntityImpl.setLockExpirationTime(claimedUntil);
    actualJobEntityImpl.setLockOwner("Claimed By");
    String actualToStringResult = actualJobEntityImpl.toString();
    Date actualLockExpirationTime = actualJobEntityImpl.getLockExpirationTime();
    String actualLockOwner = actualJobEntityImpl.getLockOwner();

    // Assert that nothing has changed
    assertEquals("", actualJobEntityImpl.getTenantId());
    assertEquals("Claimed By", actualLockOwner);
    assertEquals("JobEntity [id=null]", actualToStringResult);
    assertEquals(0, actualJobEntityImpl.getMaxIterations());
    assertEquals(0, actualJobEntityImpl.getRetries());
    assertEquals(1, actualJobEntityImpl.getRevision());
    assertFalse(actualJobEntityImpl.isDeleted());
    assertFalse(actualJobEntityImpl.isInserted());
    assertFalse(actualJobEntityImpl.isUpdated());
    assertTrue(actualJobEntityImpl.isExclusive());
    assertSame(claimedUntil, actualLockExpirationTime);
  }
}
