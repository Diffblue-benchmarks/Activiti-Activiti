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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoricScopeInstanceEntityImplDiffblueTest {
  @InjectMocks
  private HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl;

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#getProcessInstanceId()}.
   * <ul>
   *   <li>Given {@link HistoricActivityInstanceEntityImpl} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricScopeInstanceEntityImpl#getProcessInstanceId()}
   */
  @Test
  public void testGetProcessInstanceId_givenHistoricActivityInstanceEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new HistoricActivityInstanceEntityImpl()).getProcessInstanceId());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#getProcessInstanceId()}.
   * <ul>
   *   <li>Given {@link HistoricActivityInstanceEntityImpl} (default constructor)
   * StartTime is {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricScopeInstanceEntityImpl#getProcessInstanceId()}
   */
  @Test
  public void testGetProcessInstanceId_givenHistoricActivityInstanceEntityImplStartTimeIsDate() {
    // Arrange
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl = new HistoricActivityInstanceEntityImpl();
    historicActivityInstanceEntityImpl.setStartTime(mock(Date.class));

    // Act and Assert
    assertNull(historicActivityInstanceEntityImpl.getProcessInstanceId());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#getProcessDefinitionId()}.
   * <p>
   * Method under test:
   * {@link HistoricScopeInstanceEntityImpl#getProcessDefinitionId()}
   */
  @Test
  public void testGetProcessDefinitionId() {
    // Arrange
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl = new HistoricActivityInstanceEntityImpl();
    historicActivityInstanceEntityImpl.setStartTime(mock(Date.class));

    // Act and Assert
    assertNull(historicActivityInstanceEntityImpl.getProcessDefinitionId());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#getProcessDefinitionId()}.
   * <ul>
   *   <li>Given {@link HistoricActivityInstanceEntityImpl} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricScopeInstanceEntityImpl#getProcessDefinitionId()}
   */
  @Test
  public void testGetProcessDefinitionId_givenHistoricActivityInstanceEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new HistoricActivityInstanceEntityImpl()).getProcessDefinitionId());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#getStartTime()}.
   * <ul>
   *   <li>Given {@link HistoricActivityInstanceEntityImpl} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricScopeInstanceEntityImpl#getStartTime()}
   */
  @Test
  public void testGetStartTime_givenHistoricActivityInstanceEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new HistoricActivityInstanceEntityImpl()).getStartTime());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#getStartTime()}.
   * <ul>
   *   <li>Then return {@link HistoricActivityInstanceEntityImpl} (default
   * constructor) {@link HistoricScopeInstanceEntityImpl#startTime}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricScopeInstanceEntityImpl#getStartTime()}
   */
  @Test
  public void testGetStartTime_thenReturnHistoricActivityInstanceEntityImplStartTime() {
    // Arrange
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl = new HistoricActivityInstanceEntityImpl();
    historicActivityInstanceEntityImpl.setStartTime(mock(java.sql.Date.class));

    // Act and Assert
    assertSame(historicActivityInstanceEntityImpl.startTime, historicActivityInstanceEntityImpl.getStartTime());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#getEndTime()}.
   * <ul>
   *   <li>Given {@link HistoricActivityInstanceEntityImpl} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricScopeInstanceEntityImpl#getEndTime()}
   */
  @Test
  public void testGetEndTime_givenHistoricActivityInstanceEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new HistoricActivityInstanceEntityImpl()).getEndTime());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#getEndTime()}.
   * <ul>
   *   <li>Given {@link HistoricActivityInstanceEntityImpl} (default constructor)
   * StartTime is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricScopeInstanceEntityImpl#getEndTime()}
   */
  @Test
  public void testGetEndTime_givenHistoricActivityInstanceEntityImplStartTimeIsDate() {
    // Arrange
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl = new HistoricActivityInstanceEntityImpl();
    historicActivityInstanceEntityImpl.setStartTime(mock(java.sql.Date.class));

    // Act and Assert
    assertNull(historicActivityInstanceEntityImpl.getEndTime());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#getDurationInMillis()}.
   * <ul>
   *   <li>Given {@link HistoricActivityInstanceEntityImpl} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricScopeInstanceEntityImpl#getDurationInMillis()}
   */
  @Test
  public void testGetDurationInMillis_givenHistoricActivityInstanceEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new HistoricActivityInstanceEntityImpl()).getDurationInMillis());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#getDurationInMillis()}.
   * <ul>
   *   <li>Given {@link HistoricActivityInstanceEntityImpl} (default constructor)
   * StartTime is {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricScopeInstanceEntityImpl#getDurationInMillis()}
   */
  @Test
  public void testGetDurationInMillis_givenHistoricActivityInstanceEntityImplStartTimeIsDate() {
    // Arrange
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl = new HistoricActivityInstanceEntityImpl();
    historicActivityInstanceEntityImpl.setStartTime(mock(Date.class));

    // Act and Assert
    assertNull(historicActivityInstanceEntityImpl.getDurationInMillis());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#setProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricScopeInstanceEntityImpl#setProcessInstanceId(String)}
   */
  @Test
  public void testSetProcessInstanceId() {
    // Arrange and Act
    historicActivityInstanceEntityImpl.setProcessInstanceId("42");

    // Assert
    assertEquals("42", historicActivityInstanceEntityImpl.getProcessInstanceId());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#setProcessDefinitionId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricScopeInstanceEntityImpl#setProcessDefinitionId(String)}
   */
  @Test
  public void testSetProcessDefinitionId() {
    // Arrange and Act
    historicActivityInstanceEntityImpl.setProcessDefinitionId("42");

    // Assert
    assertEquals("42", historicActivityInstanceEntityImpl.getProcessDefinitionId());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#setStartTime(Date)}.
   * <p>
   * Method under test: {@link HistoricScopeInstanceEntityImpl#setStartTime(Date)}
   */
  @Test
  public void testSetStartTime() {
    // Arrange
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl = new HistoricActivityInstanceEntityImpl();
    java.util.Date startTime = java.util.Date
        .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    historicActivityInstanceEntityImpl.setStartTime(startTime);

    // Assert
    assertSame(startTime, historicActivityInstanceEntityImpl.getTime());
    assertSame(startTime, historicActivityInstanceEntityImpl.getStartTime());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#setStartTime(Date)}.
   * <ul>
   *   <li>When {@link java.sql.Date}.</li>
   *   <li>Then {@link HistoricActivityInstanceEntityImpl} (default constructor)
   * Time is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricScopeInstanceEntityImpl#setStartTime(java.util.Date)}
   */
  @Test
  public void testSetStartTime_whenDate_thenHistoricActivityInstanceEntityImplTimeIsDate() {
    // Arrange
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl = new HistoricActivityInstanceEntityImpl();
    java.sql.Date startTime = mock(java.sql.Date.class);

    // Act
    historicActivityInstanceEntityImpl.setStartTime(startTime);

    // Assert
    assertSame(startTime, historicActivityInstanceEntityImpl.getTime());
    assertSame(startTime, historicActivityInstanceEntityImpl.getStartTime());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#setEndTime(Date)}.
   * <p>
   * Method under test: {@link HistoricScopeInstanceEntityImpl#setEndTime(Date)}
   */
  @Test
  public void testSetEndTime() {
    // Arrange
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl = new HistoricActivityInstanceEntityImpl();
    java.util.Date endTime = java.util.Date
        .from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    historicActivityInstanceEntityImpl.setEndTime(endTime);

    // Assert
    Object persistentState = historicActivityInstanceEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(5, ((Map<String, java.util.Date>) persistentState).size());
    assertTrue(((Map<String, java.util.Date>) persistentState).containsKey("assignee"));
    assertTrue(((Map<String, java.util.Date>) persistentState).containsKey("deleteReason"));
    assertTrue(((Map<String, java.util.Date>) persistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, java.util.Date>) persistentState).containsKey("executionId"));
    assertSame(endTime, ((Map<String, java.util.Date>) persistentState).get("endTime"));
    assertSame(endTime, historicActivityInstanceEntityImpl.getEndTime());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#setDurationInMillis(Long)}.
   * <ul>
   *   <li>Given {@link HistoricActivityInstanceEntityImpl} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricScopeInstanceEntityImpl#setDurationInMillis(Long)}
   */
  @Test
  public void testSetDurationInMillis_givenHistoricActivityInstanceEntityImpl() {
    // Arrange
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl = new HistoricActivityInstanceEntityImpl();

    // Act
    historicActivityInstanceEntityImpl.setDurationInMillis(1L);

    // Assert
    Object persistentState = historicActivityInstanceEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(5, ((Map<String, Long>) persistentState).size());
    assertEquals(1L, ((Map<String, Long>) persistentState).get("durationInMillis").longValue());
    assertEquals(1L, historicActivityInstanceEntityImpl.getDurationInMillis().longValue());
    assertTrue(((Map<String, Long>) persistentState).containsKey("assignee"));
    assertTrue(((Map<String, Long>) persistentState).containsKey("deleteReason"));
    assertTrue(((Map<String, Long>) persistentState).containsKey("endTime"));
    assertTrue(((Map<String, Long>) persistentState).containsKey("executionId"));
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#setDurationInMillis(Long)}.
   * <ul>
   *   <li>Given {@link HistoricActivityInstanceEntityImpl} (default constructor)
   * StartTime is {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricScopeInstanceEntityImpl#setDurationInMillis(Long)}
   */
  @Test
  public void testSetDurationInMillis_givenHistoricActivityInstanceEntityImplStartTimeIsDate() {
    // Arrange
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl = new HistoricActivityInstanceEntityImpl();
    historicActivityInstanceEntityImpl.setStartTime(mock(Date.class));

    // Act
    historicActivityInstanceEntityImpl.setDurationInMillis(1L);

    // Assert
    Object persistentState = historicActivityInstanceEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(5, ((Map<String, Long>) persistentState).size());
    assertEquals(1L, ((Map<String, Long>) persistentState).get("durationInMillis").longValue());
    assertEquals(1L, historicActivityInstanceEntityImpl.getDurationInMillis().longValue());
    assertTrue(((Map<String, Long>) persistentState).containsKey("assignee"));
    assertTrue(((Map<String, Long>) persistentState).containsKey("deleteReason"));
    assertTrue(((Map<String, Long>) persistentState).containsKey("endTime"));
    assertTrue(((Map<String, Long>) persistentState).containsKey("executionId"));
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#getDeleteReason()}.
   * <ul>
   *   <li>Given {@link HistoricActivityInstanceEntityImpl} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricScopeInstanceEntityImpl#getDeleteReason()}
   */
  @Test
  public void testGetDeleteReason_givenHistoricActivityInstanceEntityImpl() {
    // Arrange, Act and Assert
    assertNull((new HistoricActivityInstanceEntityImpl()).getDeleteReason());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#getDeleteReason()}.
   * <ul>
   *   <li>Given {@link HistoricActivityInstanceEntityImpl} (default constructor)
   * StartTime is {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricScopeInstanceEntityImpl#getDeleteReason()}
   */
  @Test
  public void testGetDeleteReason_givenHistoricActivityInstanceEntityImplStartTimeIsDate() {
    // Arrange
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl = new HistoricActivityInstanceEntityImpl();
    historicActivityInstanceEntityImpl.setStartTime(mock(Date.class));

    // Act and Assert
    assertNull(historicActivityInstanceEntityImpl.getDeleteReason());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#setDeleteReason(String)}.
   * <p>
   * Method under test:
   * {@link HistoricScopeInstanceEntityImpl#setDeleteReason(String)}
   */
  @Test
  public void testSetDeleteReason() {
    // Arrange and Act
    historicActivityInstanceEntityImpl.setDeleteReason("Just cause");

    // Assert
    Object persistentState = historicActivityInstanceEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(5, ((Map<String, String>) persistentState).size());
    assertEquals("Just cause", ((Map<String, String>) persistentState).get("deleteReason"));
    assertEquals("Just cause", historicActivityInstanceEntityImpl.getDeleteReason());
    assertTrue(((Map<String, String>) persistentState).containsKey("assignee"));
    assertTrue(((Map<String, String>) persistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, String>) persistentState).containsKey("endTime"));
    assertTrue(((Map<String, String>) persistentState).containsKey("executionId"));
  }
}
