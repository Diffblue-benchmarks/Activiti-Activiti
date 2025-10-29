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
public class DeadLetterJobQueryImplDiffblueTest {
  @InjectMocks
  private DeadLetterJobQueryImpl deadLetterJobQueryImpl;

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#jobId(String)}
   */
  @Test
  public void testJobId() {
    // Arrange and Act
    DeadLetterJobQueryImpl actualJobIdResult = deadLetterJobQueryImpl.jobId("42");

    // Assert
    assertEquals("42", deadLetterJobQueryImpl.getId());
    assertSame(deadLetterJobQueryImpl, actualJobIdResult);
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#jobId(String)}
   */
  @Test
  public void testJobId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.jobId(null));
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId() {
    // Arrange and Act
    DeadLetterJobQueryImpl actualProcessInstanceIdResult = deadLetterJobQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", deadLetterJobQueryImpl.getProcessInstanceId());
    assertSame(deadLetterJobQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.processInstanceId(null));
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId() {
    // Arrange and Act
    DeadLetterJobQueryImpl actualProcessDefinitionIdResult = deadLetterJobQueryImpl.processDefinitionId("42");

    // Assert
    assertEquals("42", deadLetterJobQueryImpl.getProcessDefinitionId());
    assertSame(deadLetterJobQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.processDefinitionId(null));
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId() {
    // Arrange and Act
    DeadLetterJobQueryImpl actualExecutionIdResult = deadLetterJobQueryImpl.executionId("42");

    // Assert
    assertEquals("42", deadLetterJobQueryImpl.getExecutionId());
    assertSame(deadLetterJobQueryImpl, actualExecutionIdResult);
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.executionId(null));
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#executable()}
   */
  @Test
  public void testExecutable() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    DeadLetterJobQueryImpl actualExecutableResult = deadLetterJobQueryImpl.executable();

    // Assert
    assertTrue(deadLetterJobQueryImpl.getExecutable());
    assertSame(deadLetterJobQueryImpl, actualExecutableResult);
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#timers()}
   */
  @Test
  public void testTimers() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    DeadLetterJobQueryImpl actualTimersResult = deadLetterJobQueryImpl.timers();

    // Assert
    assertTrue(deadLetterJobQueryImpl.isOnlyTimers());
    assertSame(deadLetterJobQueryImpl, actualTimersResult);
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#messages()}
   */
  @Test
  public void testMessages() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    DeadLetterJobQueryImpl actualMessagesResult = deadLetterJobQueryImpl.messages();

    // Assert
    assertTrue(deadLetterJobQueryImpl.isOnlyMessages());
    assertSame(deadLetterJobQueryImpl, actualMessagesResult);
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#duedateHigherThan(Date)}
   */
  @Test
  public void testDuedateHigherThan() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(deadLetterJobQueryImpl, deadLetterJobQueryImpl.duedateHigherThan(date));
    assertSame(date, deadLetterJobQueryImpl.getDuedateHigherThan());
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#duedateHigherThan(Date)}
   */
  @Test
  public void testDuedateHigherThan2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new DeadLetterJobQueryImpl()).duedateHigherThan(null));
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#duedateLowerThan(Date)}
   */
  @Test
  public void testDuedateLowerThan() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(deadLetterJobQueryImpl, deadLetterJobQueryImpl.duedateLowerThan(date));
    assertSame(date, deadLetterJobQueryImpl.getDuedateLowerThan());
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#duedateLowerThan(Date)}
   */
  @Test
  public void testDuedateLowerThan2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new DeadLetterJobQueryImpl()).duedateLowerThan(null));
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#duedateHigherThen(Date)}
   */
  @Test
  public void testDuedateHigherThen() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(deadLetterJobQueryImpl, deadLetterJobQueryImpl.duedateHigherThen(date));
    assertSame(date, deadLetterJobQueryImpl.getDuedateHigherThan());
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#duedateHigherThen(Date)}
   */
  @Test
  public void testDuedateHigherThen2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new DeadLetterJobQueryImpl()).duedateHigherThen(null));
  }

  /**
   * Method under test:
   * {@link DeadLetterJobQueryImpl#duedateHigherThenOrEquals(Date)}
   */
  @Test
  public void testDuedateHigherThenOrEquals() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(deadLetterJobQueryImpl, deadLetterJobQueryImpl.duedateHigherThenOrEquals(date));
    assertSame(date, deadLetterJobQueryImpl.getDuedateHigherThanOrEqual());
  }

  /**
   * Method under test:
   * {@link DeadLetterJobQueryImpl#duedateHigherThenOrEquals(Date)}
   */
  @Test
  public void testDuedateHigherThenOrEquals2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeadLetterJobQueryImpl()).duedateHigherThenOrEquals(null));
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#duedateLowerThen(Date)}
   */
  @Test
  public void testDuedateLowerThen() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(deadLetterJobQueryImpl, deadLetterJobQueryImpl.duedateLowerThen(date));
    assertSame(date, deadLetterJobQueryImpl.getDuedateLowerThan());
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#duedateLowerThen(Date)}
   */
  @Test
  public void testDuedateLowerThen2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new DeadLetterJobQueryImpl()).duedateLowerThen(null));
  }

  /**
   * Method under test:
   * {@link DeadLetterJobQueryImpl#duedateLowerThenOrEquals(Date)}
   */
  @Test
  public void testDuedateLowerThenOrEquals() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();
    Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(deadLetterJobQueryImpl, deadLetterJobQueryImpl.duedateLowerThenOrEquals(date));
    assertSame(date, deadLetterJobQueryImpl.getDuedateLowerThanOrEqual());
  }

  /**
   * Method under test:
   * {@link DeadLetterJobQueryImpl#duedateLowerThenOrEquals(Date)}
   */
  @Test
  public void testDuedateLowerThenOrEquals2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeadLetterJobQueryImpl()).duedateLowerThenOrEquals(null));
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#withException()}
   */
  @Test
  public void testWithException() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    DeadLetterJobQueryImpl actualWithExceptionResult = deadLetterJobQueryImpl.withException();

    // Assert
    assertTrue(deadLetterJobQueryImpl.isWithException());
    assertSame(deadLetterJobQueryImpl, actualWithExceptionResult);
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#exceptionMessage(String)}
   */
  @Test
  public void testExceptionMessage() {
    // Arrange and Act
    DeadLetterJobQueryImpl actualExceptionMessageResult = deadLetterJobQueryImpl.exceptionMessage("Exception Message");

    // Assert
    assertEquals("Exception Message", deadLetterJobQueryImpl.getExceptionMessage());
    assertSame(deadLetterJobQueryImpl, actualExceptionMessageResult);
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#exceptionMessage(String)}
   */
  @Test
  public void testExceptionMessage2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.exceptionMessage(null));
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#jobTenantId(String)}
   */
  @Test
  public void testJobTenantId() {
    // Arrange and Act
    DeadLetterJobQueryImpl actualJobTenantIdResult = deadLetterJobQueryImpl.jobTenantId("42");

    // Assert
    assertEquals("42", deadLetterJobQueryImpl.getTenantId());
    assertSame(deadLetterJobQueryImpl, actualJobTenantIdResult);
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#jobTenantId(String)}
   */
  @Test
  public void testJobTenantId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.jobTenantId(null));
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#jobTenantIdLike(String)}
   */
  @Test
  public void testJobTenantIdLike() {
    // Arrange and Act
    DeadLetterJobQueryImpl actualJobTenantIdLikeResult = deadLetterJobQueryImpl.jobTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", deadLetterJobQueryImpl.getTenantIdLike());
    assertSame(deadLetterJobQueryImpl, actualJobTenantIdLikeResult);
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#jobTenantIdLike(String)}
   */
  @Test
  public void testJobTenantIdLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deadLetterJobQueryImpl.jobTenantIdLike(null));
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#jobWithoutTenantId()}
   */
  @Test
  public void testJobWithoutTenantId() {
    // Arrange
    DeadLetterJobQueryImpl deadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Act
    DeadLetterJobQueryImpl actualJobWithoutTenantIdResult = deadLetterJobQueryImpl.jobWithoutTenantId();

    // Assert
    assertTrue(deadLetterJobQueryImpl.isWithoutTenantId());
    assertSame(deadLetterJobQueryImpl, actualJobWithoutTenantIdResult);
  }

  /**
   * Method under test: {@link DeadLetterJobQueryImpl#DeadLetterJobQueryImpl()}
   */
  @Test
  public void testNewDeadLetterJobQueryImpl() {
    // Arrange and Act
    DeadLetterJobQueryImpl actualDeadLetterJobQueryImpl = new DeadLetterJobQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualDeadLetterJobQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualDeadLetterJobQueryImpl.getOrderByColumns());
    assertNull(actualDeadLetterJobQueryImpl.getDatabaseType());
    assertNull(actualDeadLetterJobQueryImpl.getExceptionMessage());
    assertNull(actualDeadLetterJobQueryImpl.getExecutionId());
    assertNull(actualDeadLetterJobQueryImpl.getId());
    assertNull(actualDeadLetterJobQueryImpl.getProcessDefinitionId());
    assertNull(actualDeadLetterJobQueryImpl.getProcessInstanceId());
    assertNull(actualDeadLetterJobQueryImpl.getTenantId());
    assertNull(actualDeadLetterJobQueryImpl.getTenantIdLike());
    assertNull(actualDeadLetterJobQueryImpl.orderBy);
    assertNull(actualDeadLetterJobQueryImpl.getDuedateHigherThan());
    assertNull(actualDeadLetterJobQueryImpl.getDuedateHigherThanOrEqual());
    assertNull(actualDeadLetterJobQueryImpl.getDuedateLowerThan());
    assertNull(actualDeadLetterJobQueryImpl.getDuedateLowerThanOrEqual());
    assertNull(actualDeadLetterJobQueryImpl.nullHandlingOnOrder);
    assertNull(actualDeadLetterJobQueryImpl.resultType);
    assertNull(actualDeadLetterJobQueryImpl.commandContext);
    assertNull(actualDeadLetterJobQueryImpl.commandExecutor);
    assertNull(actualDeadLetterJobQueryImpl.orderProperty);
    assertEquals(0, actualDeadLetterJobQueryImpl.getFirstResult());
    assertEquals(1, actualDeadLetterJobQueryImpl.getFirstRow());
    assertFalse(actualDeadLetterJobQueryImpl.getExecutable());
    assertFalse(actualDeadLetterJobQueryImpl.isOnlyMessages());
    assertFalse(actualDeadLetterJobQueryImpl.isOnlyTimers());
    assertFalse(actualDeadLetterJobQueryImpl.isWithException());
    assertFalse(actualDeadLetterJobQueryImpl.isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, actualDeadLetterJobQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualDeadLetterJobQueryImpl.getMaxResults());
    assertSame(actualDeadLetterJobQueryImpl, actualDeadLetterJobQueryImpl.getParameter());
  }
}
