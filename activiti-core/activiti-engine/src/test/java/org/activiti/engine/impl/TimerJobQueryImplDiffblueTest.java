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
public class TimerJobQueryImplDiffblueTest {
  @InjectMocks
  private TimerJobQueryImpl timerJobQueryImpl;

  /**
   * Method under test: {@link TimerJobQueryImpl#jobId(String)}
   */
  @Test
  public void testJobId() {
    // Arrange and Act
    TimerJobQueryImpl actualJobIdResult = timerJobQueryImpl.jobId("42");

    // Assert
    assertEquals("42", timerJobQueryImpl.getId());
    assertSame(timerJobQueryImpl, actualJobIdResult);
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#jobId(String)}
   */
  @Test
  public void testJobId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> timerJobQueryImpl.jobId(null));
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId() {
    // Arrange and Act
    TimerJobQueryImpl actualProcessInstanceIdResult = timerJobQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", timerJobQueryImpl.getProcessInstanceId());
    assertSame(timerJobQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> timerJobQueryImpl.processInstanceId(null));
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId() {
    // Arrange and Act
    TimerJobQueryImpl actualProcessDefinitionIdResult = timerJobQueryImpl.processDefinitionId("42");

    // Assert
    assertEquals("42", timerJobQueryImpl.getProcessDefinitionId());
    assertSame(timerJobQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> timerJobQueryImpl.processDefinitionId(null));
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId() {
    // Arrange and Act
    TimerJobQueryImpl actualExecutionIdResult = timerJobQueryImpl.executionId("42");

    // Assert
    assertEquals("42", timerJobQueryImpl.getExecutionId());
    assertSame(timerJobQueryImpl, actualExecutionIdResult);
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> timerJobQueryImpl.executionId(null));
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#executable()}
   */
  @Test
  public void testExecutable() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();

    // Act
    TimerJobQueryImpl actualExecutableResult = timerJobQueryImpl.executable();

    // Assert
    assertTrue(timerJobQueryImpl.getExecutable());
    assertSame(timerJobQueryImpl, actualExecutableResult);
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#timers()}
   */
  @Test
  public void testTimers() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();

    // Act
    TimerJobQueryImpl actualTimersResult = timerJobQueryImpl.timers();

    // Assert
    assertTrue(timerJobQueryImpl.isOnlyTimers());
    assertSame(timerJobQueryImpl, actualTimersResult);
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#messages()}
   */
  @Test
  public void testMessages() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();

    // Act
    TimerJobQueryImpl actualMessagesResult = timerJobQueryImpl.messages();

    // Assert
    assertTrue(timerJobQueryImpl.isOnlyMessages());
    assertSame(timerJobQueryImpl, actualMessagesResult);
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#duedateHigherThan(Date)}
   */
  @Test
  public void testDuedateHigherThan() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(timerJobQueryImpl, timerJobQueryImpl.duedateHigherThan(date));
    assertSame(date, timerJobQueryImpl.getDuedateHigherThan());
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#duedateHigherThan(Date)}
   */
  @Test
  public void testDuedateHigherThan2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TimerJobQueryImpl()).duedateHigherThan(null));
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#duedateLowerThan(Date)}
   */
  @Test
  public void testDuedateLowerThan() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(timerJobQueryImpl, timerJobQueryImpl.duedateLowerThan(date));
    assertSame(date, timerJobQueryImpl.getDuedateLowerThan());
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#duedateLowerThan(Date)}
   */
  @Test
  public void testDuedateLowerThan2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TimerJobQueryImpl()).duedateLowerThan(null));
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#duedateHigherThen(Date)}
   */
  @Test
  public void testDuedateHigherThen() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(timerJobQueryImpl, timerJobQueryImpl.duedateHigherThen(date));
    assertSame(date, timerJobQueryImpl.getDuedateHigherThan());
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#duedateHigherThen(Date)}
   */
  @Test
  public void testDuedateHigherThen2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TimerJobQueryImpl()).duedateHigherThen(null));
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#duedateHigherThenOrEquals(Date)}
   */
  @Test
  public void testDuedateHigherThenOrEquals() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(timerJobQueryImpl, timerJobQueryImpl.duedateHigherThenOrEquals(date));
    assertSame(date, timerJobQueryImpl.getDuedateHigherThanOrEqual());
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#duedateHigherThenOrEquals(Date)}
   */
  @Test
  public void testDuedateHigherThenOrEquals2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new TimerJobQueryImpl()).duedateHigherThenOrEquals(null));
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#duedateLowerThen(Date)}
   */
  @Test
  public void testDuedateLowerThen() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(timerJobQueryImpl, timerJobQueryImpl.duedateLowerThen(date));
    assertSame(date, timerJobQueryImpl.getDuedateLowerThan());
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#duedateLowerThen(Date)}
   */
  @Test
  public void testDuedateLowerThen2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new TimerJobQueryImpl()).duedateLowerThen(null));
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#duedateLowerThenOrEquals(Date)}
   */
  @Test
  public void testDuedateLowerThenOrEquals() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(timerJobQueryImpl, timerJobQueryImpl.duedateLowerThenOrEquals(date));
    assertSame(date, timerJobQueryImpl.getDuedateLowerThanOrEqual());
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#duedateLowerThenOrEquals(Date)}
   */
  @Test
  public void testDuedateLowerThenOrEquals2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new TimerJobQueryImpl()).duedateLowerThenOrEquals(null));
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#withException()}
   */
  @Test
  public void testWithException() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();

    // Act
    TimerJobQueryImpl actualWithExceptionResult = timerJobQueryImpl.withException();

    // Assert
    assertTrue(timerJobQueryImpl.isWithException());
    assertSame(timerJobQueryImpl, actualWithExceptionResult);
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#exceptionMessage(String)}
   */
  @Test
  public void testExceptionMessage() {
    // Arrange and Act
    TimerJobQueryImpl actualExceptionMessageResult = timerJobQueryImpl.exceptionMessage("Exception Message");

    // Assert
    assertEquals("Exception Message", timerJobQueryImpl.getExceptionMessage());
    assertSame(timerJobQueryImpl, actualExceptionMessageResult);
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#exceptionMessage(String)}
   */
  @Test
  public void testExceptionMessage2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> timerJobQueryImpl.exceptionMessage(null));
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#jobTenantId(String)}
   */
  @Test
  public void testJobTenantId() {
    // Arrange and Act
    TimerJobQueryImpl actualJobTenantIdResult = timerJobQueryImpl.jobTenantId("42");

    // Assert
    assertEquals("42", timerJobQueryImpl.getTenantId());
    assertSame(timerJobQueryImpl, actualJobTenantIdResult);
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#jobTenantId(String)}
   */
  @Test
  public void testJobTenantId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> timerJobQueryImpl.jobTenantId(null));
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#jobTenantIdLike(String)}
   */
  @Test
  public void testJobTenantIdLike() {
    // Arrange and Act
    TimerJobQueryImpl actualJobTenantIdLikeResult = timerJobQueryImpl.jobTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", timerJobQueryImpl.getTenantIdLike());
    assertSame(timerJobQueryImpl, actualJobTenantIdLikeResult);
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#jobTenantIdLike(String)}
   */
  @Test
  public void testJobTenantIdLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> timerJobQueryImpl.jobTenantIdLike(null));
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#jobWithoutTenantId()}
   */
  @Test
  public void testJobWithoutTenantId() {
    // Arrange
    TimerJobQueryImpl timerJobQueryImpl = new TimerJobQueryImpl();

    // Act
    TimerJobQueryImpl actualJobWithoutTenantIdResult = timerJobQueryImpl.jobWithoutTenantId();

    // Assert
    assertTrue(timerJobQueryImpl.isWithoutTenantId());
    assertSame(timerJobQueryImpl, actualJobWithoutTenantIdResult);
  }

  /**
   * Method under test: {@link TimerJobQueryImpl#TimerJobQueryImpl()}
   */
  @Test
  public void testNewTimerJobQueryImpl() {
    // Arrange and Act
    TimerJobQueryImpl actualTimerJobQueryImpl = new TimerJobQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualTimerJobQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualTimerJobQueryImpl.getOrderByColumns());
    assertNull(actualTimerJobQueryImpl.getDatabaseType());
    assertNull(actualTimerJobQueryImpl.getExceptionMessage());
    assertNull(actualTimerJobQueryImpl.getExecutionId());
    assertNull(actualTimerJobQueryImpl.getId());
    assertNull(actualTimerJobQueryImpl.getProcessDefinitionId());
    assertNull(actualTimerJobQueryImpl.getProcessInstanceId());
    assertNull(actualTimerJobQueryImpl.getTenantId());
    assertNull(actualTimerJobQueryImpl.getTenantIdLike());
    assertNull(actualTimerJobQueryImpl.orderBy);
    assertNull(actualTimerJobQueryImpl.getDuedateHigherThan());
    assertNull(actualTimerJobQueryImpl.getDuedateHigherThanOrEqual());
    assertNull(actualTimerJobQueryImpl.getDuedateLowerThan());
    assertNull(actualTimerJobQueryImpl.getDuedateLowerThanOrEqual());
    assertNull(actualTimerJobQueryImpl.nullHandlingOnOrder);
    assertNull(actualTimerJobQueryImpl.resultType);
    assertNull(actualTimerJobQueryImpl.commandContext);
    assertNull(actualTimerJobQueryImpl.commandExecutor);
    assertNull(actualTimerJobQueryImpl.orderProperty);
    assertEquals(0, actualTimerJobQueryImpl.getFirstResult());
    assertEquals(1, actualTimerJobQueryImpl.getFirstRow());
    assertFalse(actualTimerJobQueryImpl.getExecutable());
    assertFalse(actualTimerJobQueryImpl.getRetriesLeft());
    assertFalse(actualTimerJobQueryImpl.isNoRetriesLeft());
    assertFalse(actualTimerJobQueryImpl.isOnlyMessages());
    assertFalse(actualTimerJobQueryImpl.isOnlyTimers());
    assertFalse(actualTimerJobQueryImpl.isWithException());
    assertFalse(actualTimerJobQueryImpl.isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, actualTimerJobQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualTimerJobQueryImpl.getMaxResults());
    assertSame(actualTimerJobQueryImpl, actualTimerJobQueryImpl.getParameter());
  }
}
