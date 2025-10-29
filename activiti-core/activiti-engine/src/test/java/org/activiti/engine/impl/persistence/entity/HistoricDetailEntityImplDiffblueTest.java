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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoricDetailEntityImplDiffblueTest {
  @InjectMocks
  private HistoricDetailAssignmentEntityImpl historicDetailAssignmentEntityImpl;

  /**
   * Method under test: {@link HistoricDetailEntityImpl#getProcessInstanceId()}
   */
  @Test
  public void testGetProcessInstanceId() {
    // Arrange, Act and Assert
    assertNull((new HistoricDetailAssignmentEntityImpl()).getProcessInstanceId());
  }

  /**
   * Method under test: {@link HistoricDetailEntityImpl#getProcessInstanceId()}
   */
  @Test
  public void testGetProcessInstanceId2() {
    // Arrange
    HistoricDetailAssignmentEntityImpl historicDetailAssignmentEntityImpl = new HistoricDetailAssignmentEntityImpl();
    historicDetailAssignmentEntityImpl.setTime(mock(Date.class));

    // Act and Assert
    assertNull(historicDetailAssignmentEntityImpl.getProcessInstanceId());
  }

  /**
   * Method under test:
   * {@link HistoricDetailEntityImpl#setProcessInstanceId(String)}
   */
  @Test
  public void testSetProcessInstanceId() {
    // Arrange and Act
    historicDetailAssignmentEntityImpl.setProcessInstanceId("42");

    // Assert
    assertEquals("42", historicDetailAssignmentEntityImpl.getProcessInstanceId());
  }

  /**
   * Method under test: {@link HistoricDetailEntityImpl#getActivityInstanceId()}
   */
  @Test
  public void testGetActivityInstanceId() {
    // Arrange, Act and Assert
    assertNull((new HistoricDetailAssignmentEntityImpl()).getActivityInstanceId());
  }

  /**
   * Method under test: {@link HistoricDetailEntityImpl#getActivityInstanceId()}
   */
  @Test
  public void testGetActivityInstanceId2() {
    // Arrange
    HistoricDetailAssignmentEntityImpl historicDetailAssignmentEntityImpl = new HistoricDetailAssignmentEntityImpl();
    historicDetailAssignmentEntityImpl.setTime(mock(Date.class));

    // Act and Assert
    assertNull(historicDetailAssignmentEntityImpl.getActivityInstanceId());
  }

  /**
   * Method under test:
   * {@link HistoricDetailEntityImpl#setActivityInstanceId(String)}
   */
  @Test
  public void testSetActivityInstanceId() {
    // Arrange and Act
    historicDetailAssignmentEntityImpl.setActivityInstanceId("42");

    // Assert
    assertEquals("42", historicDetailAssignmentEntityImpl.getActivityInstanceId());
  }

  /**
   * Method under test: {@link HistoricDetailEntityImpl#getTaskId()}
   */
  @Test
  public void testGetTaskId() {
    // Arrange, Act and Assert
    assertNull((new HistoricDetailAssignmentEntityImpl()).getTaskId());
  }

  /**
   * Method under test: {@link HistoricDetailEntityImpl#getTaskId()}
   */
  @Test
  public void testGetTaskId2() {
    // Arrange
    HistoricDetailAssignmentEntityImpl historicDetailAssignmentEntityImpl = new HistoricDetailAssignmentEntityImpl();
    historicDetailAssignmentEntityImpl.setTime(mock(Date.class));

    // Act and Assert
    assertNull(historicDetailAssignmentEntityImpl.getTaskId());
  }

  /**
   * Method under test: {@link HistoricDetailEntityImpl#setTaskId(String)}
   */
  @Test
  public void testSetTaskId() {
    // Arrange and Act
    historicDetailAssignmentEntityImpl.setTaskId("42");

    // Assert
    assertEquals("42", historicDetailAssignmentEntityImpl.getTaskId());
  }

  /**
   * Method under test: {@link HistoricDetailEntityImpl#getExecutionId()}
   */
  @Test
  public void testGetExecutionId() {
    // Arrange, Act and Assert
    assertNull((new HistoricDetailAssignmentEntityImpl()).getExecutionId());
  }

  /**
   * Method under test: {@link HistoricDetailEntityImpl#getExecutionId()}
   */
  @Test
  public void testGetExecutionId2() {
    // Arrange
    HistoricDetailAssignmentEntityImpl historicDetailAssignmentEntityImpl = new HistoricDetailAssignmentEntityImpl();
    historicDetailAssignmentEntityImpl.setTime(mock(Date.class));

    // Act and Assert
    assertNull(historicDetailAssignmentEntityImpl.getExecutionId());
  }

  /**
   * Method under test: {@link HistoricDetailEntityImpl#setExecutionId(String)}
   */
  @Test
  public void testSetExecutionId() {
    // Arrange and Act
    historicDetailAssignmentEntityImpl.setExecutionId("42");

    // Assert
    assertEquals("42", historicDetailAssignmentEntityImpl.getExecutionId());
  }

  /**
   * Method under test: {@link HistoricDetailEntityImpl#getTime()}
   */
  @Test
  public void testGetTime() {
    // Arrange, Act and Assert
    assertNull((new HistoricDetailAssignmentEntityImpl()).getTime());
  }

  /**
   * Method under test: {@link HistoricDetailEntityImpl#getTime()}
   */
  @Test
  public void testGetTime2() {
    // Arrange
    HistoricDetailAssignmentEntityImpl historicDetailAssignmentEntityImpl = new HistoricDetailAssignmentEntityImpl();
    historicDetailAssignmentEntityImpl.setTime(mock(java.sql.Date.class));

    // Act and Assert
    assertSame(historicDetailAssignmentEntityImpl.time, historicDetailAssignmentEntityImpl.getTime());
  }

  /**
   * Method under test: {@link HistoricDetailEntityImpl#setTime(Date)}
   */
  @Test
  public void testSetTime() {
    // Arrange
    HistoricDetailAssignmentEntityImpl historicDetailAssignmentEntityImpl = new HistoricDetailAssignmentEntityImpl();
    java.util.Date time = java.util.Date
        .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    historicDetailAssignmentEntityImpl.setTime(time);

    // Assert
    assertSame(time, historicDetailAssignmentEntityImpl.getTime());
  }

  /**
   * Method under test: {@link HistoricDetailEntityImpl#setTime(java.util.Date)}
   */
  @Test
  public void testSetTime2() {
    // Arrange
    HistoricDetailAssignmentEntityImpl historicDetailAssignmentEntityImpl = new HistoricDetailAssignmentEntityImpl();
    java.sql.Date time = mock(java.sql.Date.class);

    // Act
    historicDetailAssignmentEntityImpl.setTime(time);

    // Assert
    assertSame(time, historicDetailAssignmentEntityImpl.getTime());
  }

  /**
   * Method under test: {@link HistoricDetailEntityImpl#getDetailType()}
   */
  @Test
  public void testGetDetailType() {
    // Arrange, Act and Assert
    assertNull((new HistoricDetailAssignmentEntityImpl()).getDetailType());
  }

  /**
   * Method under test: {@link HistoricDetailEntityImpl#getDetailType()}
   */
  @Test
  public void testGetDetailType2() {
    // Arrange
    HistoricDetailAssignmentEntityImpl historicDetailAssignmentEntityImpl = new HistoricDetailAssignmentEntityImpl();
    historicDetailAssignmentEntityImpl.setTime(mock(Date.class));

    // Act and Assert
    assertNull(historicDetailAssignmentEntityImpl.getDetailType());
  }

  /**
   * Method under test: {@link HistoricDetailEntityImpl#setDetailType(String)}
   */
  @Test
  public void testSetDetailType() {
    // Arrange and Act
    historicDetailAssignmentEntityImpl.setDetailType("Detail Type");

    // Assert
    assertEquals("Detail Type", historicDetailAssignmentEntityImpl.getDetailType());
  }
}
