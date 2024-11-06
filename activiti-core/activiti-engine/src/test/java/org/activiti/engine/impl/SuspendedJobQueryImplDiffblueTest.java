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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SuspendedJobQueryImplDiffblueTest {
  @InjectMocks
  private SuspendedJobQueryImpl suspendedJobQueryImpl;

  /**
   * Test {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()}.
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()}
   */
  @Test
  public void testNewSuspendedJobQueryImpl() {
    // Arrange and Act
    SuspendedJobQueryImpl actualSuspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualSuspendedJobQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualSuspendedJobQueryImpl.getOrderByColumns());
    assertNull(actualSuspendedJobQueryImpl.getDatabaseType());
    assertNull(actualSuspendedJobQueryImpl.getExceptionMessage());
    assertNull(actualSuspendedJobQueryImpl.getExecutionId());
    assertNull(actualSuspendedJobQueryImpl.getId());
    assertNull(actualSuspendedJobQueryImpl.getProcessDefinitionId());
    assertNull(actualSuspendedJobQueryImpl.getProcessInstanceId());
    assertNull(actualSuspendedJobQueryImpl.getTenantId());
    assertNull(actualSuspendedJobQueryImpl.getTenantIdLike());
    assertNull(actualSuspendedJobQueryImpl.orderBy);
    assertNull(actualSuspendedJobQueryImpl.getDuedateHigherThan());
    assertNull(actualSuspendedJobQueryImpl.getDuedateHigherThanOrEqual());
    assertNull(actualSuspendedJobQueryImpl.getDuedateLowerThan());
    assertNull(actualSuspendedJobQueryImpl.getDuedateLowerThanOrEqual());
    assertNull(actualSuspendedJobQueryImpl.nullHandlingOnOrder);
    assertNull(actualSuspendedJobQueryImpl.resultType);
    assertNull(actualSuspendedJobQueryImpl.commandContext);
    assertNull(actualSuspendedJobQueryImpl.commandExecutor);
    assertNull(actualSuspendedJobQueryImpl.orderProperty);
    assertEquals(0, actualSuspendedJobQueryImpl.getFirstResult());
    assertEquals(1, actualSuspendedJobQueryImpl.getFirstRow());
    assertFalse(actualSuspendedJobQueryImpl.getExecutable());
    assertFalse(actualSuspendedJobQueryImpl.getRetriesLeft());
    assertFalse(actualSuspendedJobQueryImpl.isNoRetriesLeft());
    assertFalse(actualSuspendedJobQueryImpl.isOnlyMessages());
    assertFalse(actualSuspendedJobQueryImpl.isOnlyTimers());
    assertFalse(actualSuspendedJobQueryImpl.isWithException());
    assertFalse(actualSuspendedJobQueryImpl.isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, actualSuspendedJobQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualSuspendedJobQueryImpl.getMaxResults());
    assertSame(actualSuspendedJobQueryImpl, actualSuspendedJobQueryImpl.getParameter());
  }

  /**
   * Test {@link SuspendedJobQueryImpl#jobId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link SuspendedJobQueryImpl} Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#jobId(String)}
   */
  @Test
  public void testJobId_when42_thenSuspendedJobQueryImplIdIs42() {
    // Arrange and Act
    SuspendedJobQueryImpl actualJobIdResult = suspendedJobQueryImpl.jobId("42");

    // Assert
    assertEquals("42", suspendedJobQueryImpl.getId());
    assertSame(suspendedJobQueryImpl, actualJobIdResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#jobId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#jobId(String)}
   */
  @Test
  public void testJobId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> suspendedJobQueryImpl.jobId(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#processInstanceId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link SuspendedJobQueryImpl} ProcessInstanceId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId_when42_thenSuspendedJobQueryImplProcessInstanceIdIs42() {
    // Arrange and Act
    SuspendedJobQueryImpl actualProcessInstanceIdResult = suspendedJobQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", suspendedJobQueryImpl.getProcessInstanceId());
    assertSame(suspendedJobQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#processInstanceId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> suspendedJobQueryImpl.processInstanceId(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#processDefinitionId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link SuspendedJobQueryImpl} ProcessDefinitionId is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId_when42_thenSuspendedJobQueryImplProcessDefinitionIdIs42() {
    // Arrange and Act
    SuspendedJobQueryImpl actualProcessDefinitionIdResult = suspendedJobQueryImpl.processDefinitionId("42");

    // Assert
    assertEquals("42", suspendedJobQueryImpl.getProcessDefinitionId());
    assertSame(suspendedJobQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#processDefinitionId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> suspendedJobQueryImpl.processDefinitionId(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#executionId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link SuspendedJobQueryImpl} ExecutionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId_when42_thenSuspendedJobQueryImplExecutionIdIs42() {
    // Arrange and Act
    SuspendedJobQueryImpl actualExecutionIdResult = suspendedJobQueryImpl.executionId("42");

    // Assert
    assertEquals("42", suspendedJobQueryImpl.getExecutionId());
    assertSame(suspendedJobQueryImpl, actualExecutionIdResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#executionId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> suspendedJobQueryImpl.executionId(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#withRetriesLeft()}.
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#withRetriesLeft()}
   */
  @Test
  public void testWithRetriesLeft() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualWithRetriesLeftResult = suspendedJobQueryImpl.withRetriesLeft();

    // Assert
    assertTrue(suspendedJobQueryImpl.getRetriesLeft());
    assertSame(suspendedJobQueryImpl, actualWithRetriesLeftResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#executable()}.
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#executable()}
   */
  @Test
  public void testExecutable() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualExecutableResult = suspendedJobQueryImpl.executable();

    // Assert
    assertTrue(suspendedJobQueryImpl.getExecutable());
    assertSame(suspendedJobQueryImpl, actualExecutableResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#timers()}.
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#timers()}
   */
  @Test
  public void testTimers() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualTimersResult = suspendedJobQueryImpl.timers();

    // Assert
    assertTrue(suspendedJobQueryImpl.isOnlyTimers());
    assertSame(suspendedJobQueryImpl, actualTimersResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#messages()}.
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#messages()}
   */
  @Test
  public void testMessages() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualMessagesResult = suspendedJobQueryImpl.messages();

    // Assert
    assertTrue(suspendedJobQueryImpl.isOnlyMessages());
    assertSame(suspendedJobQueryImpl, actualMessagesResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateHigherThan(Date)}.
   * <ul>
   *   <li>Then return {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateHigherThan(Date)}
   */
  @Test
  public void testDuedateHigherThan_thenReturnSuspendedJobQueryImpl() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(suspendedJobQueryImpl, suspendedJobQueryImpl.duedateHigherThan(date));
    assertSame(date, suspendedJobQueryImpl.getDuedateHigherThan());
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateHigherThan(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateHigherThan(Date)}
   */
  @Test
  public void testDuedateHigherThan_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new SuspendedJobQueryImpl()).duedateHigherThan(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateLowerThan(Date)}.
   * <ul>
   *   <li>Then return {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateLowerThan(Date)}
   */
  @Test
  public void testDuedateLowerThan_thenReturnSuspendedJobQueryImpl() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(suspendedJobQueryImpl, suspendedJobQueryImpl.duedateLowerThan(date));
    assertSame(date, suspendedJobQueryImpl.getDuedateLowerThan());
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateLowerThan(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateLowerThan(Date)}
   */
  @Test
  public void testDuedateLowerThan_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new SuspendedJobQueryImpl()).duedateLowerThan(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateHigherThen(Date)}.
   * <ul>
   *   <li>Then return {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateHigherThen(Date)}
   */
  @Test
  public void testDuedateHigherThen_thenReturnSuspendedJobQueryImpl() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(suspendedJobQueryImpl, suspendedJobQueryImpl.duedateHigherThen(date));
    assertSame(date, suspendedJobQueryImpl.getDuedateHigherThan());
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateHigherThen(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateHigherThen(Date)}
   */
  @Test
  public void testDuedateHigherThen_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new SuspendedJobQueryImpl()).duedateHigherThen(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateHigherThenOrEquals(Date)}.
   * <ul>
   *   <li>Then return {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SuspendedJobQueryImpl#duedateHigherThenOrEquals(Date)}
   */
  @Test
  public void testDuedateHigherThenOrEquals_thenReturnSuspendedJobQueryImpl() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(suspendedJobQueryImpl, suspendedJobQueryImpl.duedateHigherThenOrEquals(date));
    assertSame(date, suspendedJobQueryImpl.getDuedateHigherThanOrEqual());
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateHigherThenOrEquals(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SuspendedJobQueryImpl#duedateHigherThenOrEquals(Date)}
   */
  @Test
  public void testDuedateHigherThenOrEquals_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new SuspendedJobQueryImpl()).duedateHigherThenOrEquals(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateLowerThen(Date)}.
   * <ul>
   *   <li>Then return {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateLowerThen(Date)}
   */
  @Test
  public void testDuedateLowerThen_thenReturnSuspendedJobQueryImpl() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(suspendedJobQueryImpl, suspendedJobQueryImpl.duedateLowerThen(date));
    assertSame(date, suspendedJobQueryImpl.getDuedateLowerThan());
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateLowerThen(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#duedateLowerThen(Date)}
   */
  @Test
  public void testDuedateLowerThen_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new SuspendedJobQueryImpl()).duedateLowerThen(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateLowerThenOrEquals(Date)}.
   * <ul>
   *   <li>Then return {@link SuspendedJobQueryImpl#SuspendedJobQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SuspendedJobQueryImpl#duedateLowerThenOrEquals(Date)}
   */
  @Test
  public void testDuedateLowerThenOrEquals_thenReturnSuspendedJobQueryImpl() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(suspendedJobQueryImpl, suspendedJobQueryImpl.duedateLowerThenOrEquals(date));
    assertSame(date, suspendedJobQueryImpl.getDuedateLowerThanOrEqual());
  }

  /**
   * Test {@link SuspendedJobQueryImpl#duedateLowerThenOrEquals(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SuspendedJobQueryImpl#duedateLowerThenOrEquals(Date)}
   */
  @Test
  public void testDuedateLowerThenOrEquals_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new SuspendedJobQueryImpl()).duedateLowerThenOrEquals(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#noRetriesLeft()}.
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#noRetriesLeft()}
   */
  @Test
  public void testNoRetriesLeft() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualNoRetriesLeftResult = suspendedJobQueryImpl.noRetriesLeft();

    // Assert
    assertTrue(suspendedJobQueryImpl.isNoRetriesLeft());
    assertSame(suspendedJobQueryImpl, actualNoRetriesLeftResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#withException()}.
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#withException()}
   */
  @Test
  public void testWithException() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualWithExceptionResult = suspendedJobQueryImpl.withException();

    // Assert
    assertTrue(suspendedJobQueryImpl.isWithException());
    assertSame(suspendedJobQueryImpl, actualWithExceptionResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#exceptionMessage(String)}.
   * <ul>
   *   <li>Then {@link SuspendedJobQueryImpl} ExceptionMessage is
   * {@code Exception Message}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#exceptionMessage(String)}
   */
  @Test
  public void testExceptionMessage_thenSuspendedJobQueryImplExceptionMessageIsExceptionMessage() {
    // Arrange and Act
    SuspendedJobQueryImpl actualExceptionMessageResult = suspendedJobQueryImpl.exceptionMessage("Exception Message");

    // Assert
    assertEquals("Exception Message", suspendedJobQueryImpl.getExceptionMessage());
    assertSame(suspendedJobQueryImpl, actualExceptionMessageResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#exceptionMessage(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#exceptionMessage(String)}
   */
  @Test
  public void testExceptionMessage_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> suspendedJobQueryImpl.exceptionMessage(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#jobTenantId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link SuspendedJobQueryImpl} TenantId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#jobTenantId(String)}
   */
  @Test
  public void testJobTenantId_when42_thenSuspendedJobQueryImplTenantIdIs42() {
    // Arrange and Act
    SuspendedJobQueryImpl actualJobTenantIdResult = suspendedJobQueryImpl.jobTenantId("42");

    // Assert
    assertEquals("42", suspendedJobQueryImpl.getTenantId());
    assertSame(suspendedJobQueryImpl, actualJobTenantIdResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#jobTenantId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#jobTenantId(String)}
   */
  @Test
  public void testJobTenantId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> suspendedJobQueryImpl.jobTenantId(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#jobTenantIdLike(String)}.
   * <ul>
   *   <li>Then {@link SuspendedJobQueryImpl} TenantIdLike is
   * {@code Tenant Id Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#jobTenantIdLike(String)}
   */
  @Test
  public void testJobTenantIdLike_thenSuspendedJobQueryImplTenantIdLikeIsTenantIdLike() {
    // Arrange and Act
    SuspendedJobQueryImpl actualJobTenantIdLikeResult = suspendedJobQueryImpl.jobTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", suspendedJobQueryImpl.getTenantIdLike());
    assertSame(suspendedJobQueryImpl, actualJobTenantIdLikeResult);
  }

  /**
   * Test {@link SuspendedJobQueryImpl#jobTenantIdLike(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#jobTenantIdLike(String)}
   */
  @Test
  public void testJobTenantIdLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> suspendedJobQueryImpl.jobTenantIdLike(null));
  }

  /**
   * Test {@link SuspendedJobQueryImpl#jobWithoutTenantId()}.
   * <p>
   * Method under test: {@link SuspendedJobQueryImpl#jobWithoutTenantId()}
   */
  @Test
  public void testJobWithoutTenantId() {
    // Arrange
    SuspendedJobQueryImpl suspendedJobQueryImpl = new SuspendedJobQueryImpl();

    // Act
    SuspendedJobQueryImpl actualJobWithoutTenantIdResult = suspendedJobQueryImpl.jobWithoutTenantId();

    // Assert
    assertTrue(suspendedJobQueryImpl.isWithoutTenantId());
    assertSame(suspendedJobQueryImpl, actualJobWithoutTenantIdResult);
  }
}
