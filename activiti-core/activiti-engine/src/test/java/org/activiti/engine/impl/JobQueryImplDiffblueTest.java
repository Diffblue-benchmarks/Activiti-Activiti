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
   * Test {@link JobQueryImpl#JobQueryImpl()}.
   * <p>
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

  /**
   * Test {@link JobQueryImpl#jobId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link JobQueryImpl} Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#jobId(String)}
   */
  @Test
  public void testJobId_when42_thenJobQueryImplIdIs42() {
    // Arrange and Act
    JobQuery actualJobIdResult = jobQueryImpl.jobId("42");

    // Assert
    assertEquals("42", jobQueryImpl.getId());
    assertSame(jobQueryImpl, actualJobIdResult);
  }

  /**
   * Test {@link JobQueryImpl#jobId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#jobId(String)}
   */
  @Test
  public void testJobId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> jobQueryImpl.jobId(null));
  }

  /**
   * Test {@link JobQueryImpl#processInstanceId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link JobQueryImpl} ProcessInstanceId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId_when42_thenJobQueryImplProcessInstanceIdIs42() {
    // Arrange and Act
    JobQueryImpl actualProcessInstanceIdResult = jobQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", jobQueryImpl.getProcessInstanceId());
    assertSame(jobQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Test {@link JobQueryImpl#processInstanceId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> jobQueryImpl.processInstanceId(null));
  }

  /**
   * Test {@link JobQueryImpl#processDefinitionId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link JobQueryImpl} ProcessDefinitionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId_when42_thenJobQueryImplProcessDefinitionIdIs42() {
    // Arrange and Act
    JobQueryImpl actualProcessDefinitionIdResult = jobQueryImpl.processDefinitionId("42");

    // Assert
    assertEquals("42", jobQueryImpl.getProcessDefinitionId());
    assertSame(jobQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Test {@link JobQueryImpl#processDefinitionId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> jobQueryImpl.processDefinitionId(null));
  }

  /**
   * Test {@link JobQueryImpl#executionId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link JobQueryImpl} ExecutionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId_when42_thenJobQueryImplExecutionIdIs42() {
    // Arrange and Act
    JobQueryImpl actualExecutionIdResult = jobQueryImpl.executionId("42");

    // Assert
    assertEquals("42", jobQueryImpl.getExecutionId());
    assertSame(jobQueryImpl, actualExecutionIdResult);
  }

  /**
   * Test {@link JobQueryImpl#executionId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> jobQueryImpl.executionId(null));
  }

  /**
   * Test {@link JobQueryImpl#timers()}.
   * <p>
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
   * Test {@link JobQueryImpl#messages()}.
   * <p>
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
   * Test {@link JobQueryImpl#duedateHigherThan(Date)}.
   * <ul>
   *   <li>Then return {@link JobQueryImpl#JobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#duedateHigherThan(Date)}
   */
  @Test
  public void testDuedateHigherThan_thenReturnJobQueryImpl() {
    // Arrange
    JobQueryImpl jobQueryImpl = new JobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(jobQueryImpl, jobQueryImpl.duedateHigherThan(date));
    assertSame(date, jobQueryImpl.getDuedateHigherThan());
  }

  /**
   * Test {@link JobQueryImpl#duedateHigherThan(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#duedateHigherThan(Date)}
   */
  @Test
  public void testDuedateHigherThan_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new JobQueryImpl()).duedateHigherThan(null));
  }

  /**
   * Test {@link JobQueryImpl#duedateLowerThan(Date)}.
   * <ul>
   *   <li>Then return {@link JobQueryImpl#JobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#duedateLowerThan(Date)}
   */
  @Test
  public void testDuedateLowerThan_thenReturnJobQueryImpl() {
    // Arrange
    JobQueryImpl jobQueryImpl = new JobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(jobQueryImpl, jobQueryImpl.duedateLowerThan(date));
    assertSame(date, jobQueryImpl.getDuedateLowerThan());
  }

  /**
   * Test {@link JobQueryImpl#duedateLowerThan(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#duedateLowerThan(Date)}
   */
  @Test
  public void testDuedateLowerThan_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new JobQueryImpl()).duedateLowerThan(null));
  }

  /**
   * Test {@link JobQueryImpl#duedateHigherThen(Date)}.
   * <ul>
   *   <li>Then return {@link JobQueryImpl#JobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#duedateHigherThen(Date)}
   */
  @Test
  public void testDuedateHigherThen_thenReturnJobQueryImpl() {
    // Arrange
    JobQueryImpl jobQueryImpl = new JobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(jobQueryImpl, jobQueryImpl.duedateHigherThen(date));
    assertSame(date, jobQueryImpl.getDuedateHigherThan());
  }

  /**
   * Test {@link JobQueryImpl#duedateHigherThen(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#duedateHigherThen(Date)}
   */
  @Test
  public void testDuedateHigherThen_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new JobQueryImpl()).duedateHigherThen(null));
  }

  /**
   * Test {@link JobQueryImpl#duedateHigherThenOrEquals(Date)}.
   * <ul>
   *   <li>Then return {@link JobQueryImpl#JobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#duedateHigherThenOrEquals(Date)}
   */
  @Test
  public void testDuedateHigherThenOrEquals_thenReturnJobQueryImpl() {
    // Arrange
    JobQueryImpl jobQueryImpl = new JobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(jobQueryImpl, jobQueryImpl.duedateHigherThenOrEquals(date));
    assertSame(date, jobQueryImpl.getDuedateHigherThanOrEqual());
  }

  /**
   * Test {@link JobQueryImpl#duedateHigherThenOrEquals(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#duedateHigherThenOrEquals(Date)}
   */
  @Test
  public void testDuedateHigherThenOrEquals_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new JobQueryImpl()).duedateHigherThenOrEquals(null));
  }

  /**
   * Test {@link JobQueryImpl#duedateLowerThen(Date)}.
   * <ul>
   *   <li>Then return {@link JobQueryImpl#JobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#duedateLowerThen(Date)}
   */
  @Test
  public void testDuedateLowerThen_thenReturnJobQueryImpl() {
    // Arrange
    JobQueryImpl jobQueryImpl = new JobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(jobQueryImpl, jobQueryImpl.duedateLowerThen(date));
    assertSame(date, jobQueryImpl.getDuedateLowerThan());
  }

  /**
   * Test {@link JobQueryImpl#duedateLowerThen(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#duedateLowerThen(Date)}
   */
  @Test
  public void testDuedateLowerThen_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new JobQueryImpl()).duedateLowerThen(null));
  }

  /**
   * Test {@link JobQueryImpl#duedateLowerThenOrEquals(Date)}.
   * <ul>
   *   <li>Then return {@link JobQueryImpl#JobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#duedateLowerThenOrEquals(Date)}
   */
  @Test
  public void testDuedateLowerThenOrEquals_thenReturnJobQueryImpl() {
    // Arrange
    JobQueryImpl jobQueryImpl = new JobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(jobQueryImpl, jobQueryImpl.duedateLowerThenOrEquals(date));
    assertSame(date, jobQueryImpl.getDuedateLowerThanOrEqual());
  }

  /**
   * Test {@link JobQueryImpl#duedateLowerThenOrEquals(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#duedateLowerThenOrEquals(Date)}
   */
  @Test
  public void testDuedateLowerThenOrEquals_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new JobQueryImpl()).duedateLowerThenOrEquals(null));
  }

  /**
   * Test {@link JobQueryImpl#exceptionMessage(String)}.
   * <ul>
   *   <li>Then {@link JobQueryImpl} ExceptionMessage is
   * {@code Exception Message}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#exceptionMessage(String)}
   */
  @Test
  public void testExceptionMessage_thenJobQueryImplExceptionMessageIsExceptionMessage() {
    // Arrange and Act
    JobQuery actualExceptionMessageResult = jobQueryImpl.exceptionMessage("Exception Message");

    // Assert
    assertEquals("Exception Message", jobQueryImpl.getExceptionMessage());
    assertSame(jobQueryImpl, actualExceptionMessageResult);
  }

  /**
   * Test {@link JobQueryImpl#exceptionMessage(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#exceptionMessage(String)}
   */
  @Test
  public void testExceptionMessage_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> jobQueryImpl.exceptionMessage(null));
  }

  /**
   * Test {@link JobQueryImpl#jobTenantId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link JobQueryImpl} TenantId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#jobTenantId(String)}
   */
  @Test
  public void testJobTenantId_when42_thenJobQueryImplTenantIdIs42() {
    // Arrange and Act
    JobQuery actualJobTenantIdResult = jobQueryImpl.jobTenantId("42");

    // Assert
    assertEquals("42", jobQueryImpl.getTenantId());
    assertSame(jobQueryImpl, actualJobTenantIdResult);
  }

  /**
   * Test {@link JobQueryImpl#jobTenantId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#jobTenantId(String)}
   */
  @Test
  public void testJobTenantId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> jobQueryImpl.jobTenantId(null));
  }

  /**
   * Test {@link JobQueryImpl#jobTenantIdLike(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#jobTenantIdLike(String)}
   */
  @Test
  public void testJobTenantIdLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> jobQueryImpl.jobTenantIdLike(null));
  }

  /**
   * Test {@link JobQueryImpl#jobTenantIdLike(String)}.
   * <ul>
   *   <li>When {@code Tenant Id Like}.</li>
   *   <li>Then {@link JobQueryImpl} TenantIdLike is {@code Tenant Id Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JobQueryImpl#jobTenantIdLike(String)}
   */
  @Test
  public void testJobTenantIdLike_whenTenantIdLike_thenJobQueryImplTenantIdLikeIsTenantIdLike() {
    // Arrange and Act
    JobQuery actualJobTenantIdLikeResult = jobQueryImpl.jobTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", jobQueryImpl.getTenantIdLike());
    assertSame(jobQueryImpl, actualJobTenantIdLikeResult);
  }
}
