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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.runtime.JobQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JobQueryImplDiffblueTest {
  @InjectMocks
  private JobQueryImpl jobQueryImpl;

  /**
   * Method under test: {@link JobQueryImpl#jobId(String)}
   */
  @Test
  public void testJobId() {
    // Arrange and Act
    JobQuery actualJobIdResult = jobQueryImpl.jobId("42");

    // Assert
    assertEquals("42", jobQueryImpl.getId());
    assertSame(jobQueryImpl, actualJobIdResult);
  }

  /**
   * Method under test: {@link JobQueryImpl#jobId(String)}
   */
  @Test
  public void testJobId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> jobQueryImpl.jobId(null));
  }

  /**
   * Method under test: {@link JobQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId() {
    // Arrange and Act
    JobQueryImpl actualProcessInstanceIdResult = jobQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", jobQueryImpl.getProcessInstanceId());
    assertSame(jobQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Method under test: {@link JobQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> jobQueryImpl.processInstanceId(null));
  }

  /**
   * Method under test: {@link JobQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId() {
    // Arrange and Act
    JobQueryImpl actualProcessDefinitionIdResult = jobQueryImpl.processDefinitionId("42");

    // Assert
    assertEquals("42", jobQueryImpl.getProcessDefinitionId());
    assertSame(jobQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Method under test: {@link JobQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> jobQueryImpl.processDefinitionId(null));
  }

  /**
   * Method under test: {@link JobQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId() {
    // Arrange and Act
    JobQueryImpl actualExecutionIdResult = jobQueryImpl.executionId("42");

    // Assert
    assertEquals("42", jobQueryImpl.getExecutionId());
    assertSame(jobQueryImpl, actualExecutionIdResult);
  }

  /**
   * Method under test: {@link JobQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> jobQueryImpl.executionId(null));
  }

  /**
   * Method under test: {@link JobQueryImpl#timers()}
   */
  @Test
  public void testTimers() {
    // Arrange
    JobQueryImpl jobQueryImpl = new JobQueryImpl();

    // Act
    JobQuery actualTimersResult = jobQueryImpl.timers();

    // Assert
    assertTrue(jobQueryImpl.isOnlyTimers());
    assertSame(jobQueryImpl, actualTimersResult);
  }

  /**
   * Method under test: {@link JobQueryImpl#messages()}
   */
  @Test
  public void testMessages() {
    // Arrange
    JobQueryImpl jobQueryImpl = new JobQueryImpl();

    // Act
    JobQuery actualMessagesResult = jobQueryImpl.messages();

    // Assert
    assertTrue(jobQueryImpl.isOnlyMessages());
    assertSame(jobQueryImpl, actualMessagesResult);
  }

  /**
   * Method under test: {@link JobQueryImpl#duedateHigherThan(Date)}
   */
  @Test
  public void testDuedateHigherThan() {
    // Arrange
    JobQueryImpl jobQueryImpl = new JobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(jobQueryImpl, jobQueryImpl.duedateHigherThan(date));
    assertSame(date, jobQueryImpl.getDuedateHigherThan());
  }

  /**
   * Method under test: {@link JobQueryImpl#duedateHigherThan(Date)}
   */
  @Test
  public void testDuedateHigherThan2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new JobQueryImpl()).duedateHigherThan(null));
  }

  /**
   * Method under test: {@link JobQueryImpl#duedateLowerThan(Date)}
   */
  @Test
  public void testDuedateLowerThan() {
    // Arrange
    JobQueryImpl jobQueryImpl = new JobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(jobQueryImpl, jobQueryImpl.duedateLowerThan(date));
    assertSame(date, jobQueryImpl.getDuedateLowerThan());
  }

  /**
   * Method under test: {@link JobQueryImpl#duedateLowerThan(Date)}
   */
  @Test
  public void testDuedateLowerThan2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new JobQueryImpl()).duedateLowerThan(null));
  }

  /**
   * Method under test: {@link JobQueryImpl#duedateHigherThen(Date)}
   */
  @Test
  public void testDuedateHigherThen() {
    // Arrange
    JobQueryImpl jobQueryImpl = new JobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(jobQueryImpl, jobQueryImpl.duedateHigherThen(date));
    assertSame(date, jobQueryImpl.getDuedateHigherThan());
  }

  /**
   * Method under test: {@link JobQueryImpl#duedateHigherThen(Date)}
   */
  @Test
  public void testDuedateHigherThen2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new JobQueryImpl()).duedateHigherThen(null));
  }

  /**
   * Method under test: {@link JobQueryImpl#duedateHigherThenOrEquals(Date)}
   */
  @Test
  public void testDuedateHigherThenOrEquals() {
    // Arrange
    JobQueryImpl jobQueryImpl = new JobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(jobQueryImpl, jobQueryImpl.duedateHigherThenOrEquals(date));
    assertSame(date, jobQueryImpl.getDuedateHigherThanOrEqual());
  }

  /**
   * Method under test: {@link JobQueryImpl#duedateHigherThenOrEquals(Date)}
   */
  @Test
  public void testDuedateHigherThenOrEquals2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new JobQueryImpl()).duedateHigherThenOrEquals(null));
  }

  /**
   * Method under test: {@link JobQueryImpl#duedateLowerThen(Date)}
   */
  @Test
  public void testDuedateLowerThen() {
    // Arrange
    JobQueryImpl jobQueryImpl = new JobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(jobQueryImpl, jobQueryImpl.duedateLowerThen(date));
    assertSame(date, jobQueryImpl.getDuedateLowerThan());
  }

  /**
   * Method under test: {@link JobQueryImpl#duedateLowerThen(Date)}
   */
  @Test
  public void testDuedateLowerThen2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new JobQueryImpl()).duedateLowerThen(null));
  }

  /**
   * Method under test: {@link JobQueryImpl#duedateLowerThenOrEquals(Date)}
   */
  @Test
  public void testDuedateLowerThenOrEquals() {
    // Arrange
    JobQueryImpl jobQueryImpl = new JobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(jobQueryImpl, jobQueryImpl.duedateLowerThenOrEquals(date));
    assertSame(date, jobQueryImpl.getDuedateLowerThanOrEqual());
  }

  /**
   * Method under test: {@link JobQueryImpl#duedateLowerThenOrEquals(Date)}
   */
  @Test
  public void testDuedateLowerThenOrEquals2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new JobQueryImpl()).duedateLowerThenOrEquals(null));
  }

  /**
   * Method under test: {@link JobQueryImpl#exceptionMessage(String)}
   */
  @Test
  public void testExceptionMessage() {
    // Arrange and Act
    JobQuery actualExceptionMessageResult = jobQueryImpl.exceptionMessage("Exception Message");

    // Assert
    assertEquals("Exception Message", jobQueryImpl.getExceptionMessage());
    assertSame(jobQueryImpl, actualExceptionMessageResult);
  }

  /**
   * Method under test: {@link JobQueryImpl#exceptionMessage(String)}
   */
  @Test
  public void testExceptionMessage2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> jobQueryImpl.exceptionMessage(null));
  }

  /**
   * Method under test: {@link JobQueryImpl#jobTenantId(String)}
   */
  @Test
  public void testJobTenantId() {
    // Arrange and Act
    JobQuery actualJobTenantIdResult = jobQueryImpl.jobTenantId("42");

    // Assert
    assertEquals("42", jobQueryImpl.getTenantId());
    assertSame(jobQueryImpl, actualJobTenantIdResult);
  }

  /**
   * Method under test: {@link JobQueryImpl#jobTenantId(String)}
   */
  @Test
  public void testJobTenantId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> jobQueryImpl.jobTenantId(null));
  }

  /**
   * Method under test: {@link JobQueryImpl#jobTenantIdLike(String)}
   */
  @Test
  public void testJobTenantIdLike() {
    // Arrange and Act
    JobQuery actualJobTenantIdLikeResult = jobQueryImpl.jobTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", jobQueryImpl.getTenantIdLike());
    assertSame(jobQueryImpl, actualJobTenantIdLikeResult);
  }

  /**
   * Method under test: {@link JobQueryImpl#jobTenantIdLike(String)}
   */
  @Test
  public void testJobTenantIdLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> jobQueryImpl.jobTenantIdLike(null));
  }

  /**
   * Method under test: {@link JobQueryImpl#JobQueryImpl()}
   */
  @Test
  public void testNewJobQueryImpl() {
    // Arrange and Act
    JobQueryImpl actualJobQueryImpl = new JobQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualJobQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualJobQueryImpl.getOrderByColumns());
    assertNull(actualJobQueryImpl.getDatabaseType());
    assertNull(actualJobQueryImpl.getExceptionMessage());
    assertNull(actualJobQueryImpl.getExecutionId());
    assertNull(actualJobQueryImpl.getId());
    assertNull(actualJobQueryImpl.getProcessDefinitionId());
    assertNull(actualJobQueryImpl.getProcessInstanceId());
    assertNull(actualJobQueryImpl.getTenantId());
    assertNull(actualJobQueryImpl.getTenantIdLike());
    assertNull(actualJobQueryImpl.orderBy);
    assertNull(actualJobQueryImpl.getDuedateHigherThan());
    assertNull(actualJobQueryImpl.getDuedateHigherThanOrEqual());
    assertNull(actualJobQueryImpl.getDuedateLowerThan());
    assertNull(actualJobQueryImpl.getDuedateLowerThanOrEqual());
    assertNull(actualJobQueryImpl.nullHandlingOnOrder);
    assertNull(actualJobQueryImpl.resultType);
    assertNull(actualJobQueryImpl.commandContext);
    assertNull(actualJobQueryImpl.commandExecutor);
    assertNull(actualJobQueryImpl.orderProperty);
    assertEquals(0, actualJobQueryImpl.getFirstResult());
    assertEquals(1, actualJobQueryImpl.getFirstRow());
    assertFalse(actualJobQueryImpl.getExecutable());
    assertFalse(actualJobQueryImpl.getRetriesLeft());
    assertFalse(actualJobQueryImpl.isNoRetriesLeft());
    assertFalse(actualJobQueryImpl.isOnlyLocked());
    assertFalse(actualJobQueryImpl.isOnlyMessages());
    assertFalse(actualJobQueryImpl.isOnlyTimers());
    assertFalse(actualJobQueryImpl.isOnlyUnlocked());
    assertFalse(actualJobQueryImpl.isWithException());
    assertFalse(actualJobQueryImpl.isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, actualJobQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualJobQueryImpl.getMaxResults());
    assertSame(actualJobQueryImpl, actualJobQueryImpl.getParameter());
  }
}
