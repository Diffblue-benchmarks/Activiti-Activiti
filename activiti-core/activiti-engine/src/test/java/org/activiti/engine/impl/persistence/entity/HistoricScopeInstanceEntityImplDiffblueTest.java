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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class HistoricScopeInstanceEntityImplDiffblueTest {
  /**
   * Test {@link HistoricScopeInstanceEntityImpl#getProcessInstanceId()}.
   *
   * <p>Method under test: {@link HistoricScopeInstanceEntityImpl#getProcessInstanceId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricScopeInstanceEntityImpl.getProcessInstanceId()"})
  public void testGetProcessInstanceId() {
    // Arrange, Act and Assert
    assertNull(new HistoricActivityInstanceEntityImpl().getProcessInstanceId());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#getProcessDefinitionId()}.
   *
   * <p>Method under test: {@link HistoricScopeInstanceEntityImpl#getProcessDefinitionId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricScopeInstanceEntityImpl.getProcessDefinitionId()"})
  public void testGetProcessDefinitionId() {
    // Arrange, Act and Assert
    assertNull(new HistoricActivityInstanceEntityImpl().getProcessDefinitionId());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#getStartTime()}.
   *
   * <p>Method under test: {@link HistoricScopeInstanceEntityImpl#getStartTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date HistoricScopeInstanceEntityImpl.getStartTime()"})
  public void testGetStartTime() {
    // Arrange, Act and Assert
    assertNull(new HistoricActivityInstanceEntityImpl().getStartTime());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#getEndTime()}.
   *
   * <p>Method under test: {@link HistoricScopeInstanceEntityImpl#getEndTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date HistoricScopeInstanceEntityImpl.getEndTime()"})
  public void testGetEndTime() {
    // Arrange, Act and Assert
    assertNull(new HistoricActivityInstanceEntityImpl().getEndTime());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#getDurationInMillis()}.
   *
   * <p>Method under test: {@link HistoricScopeInstanceEntityImpl#getDurationInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Long HistoricScopeInstanceEntityImpl.getDurationInMillis()"})
  public void testGetDurationInMillis() {
    // Arrange, Act and Assert
    assertNull(new HistoricActivityInstanceEntityImpl().getDurationInMillis());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#setProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link HistoricScopeInstanceEntityImpl#setProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricScopeInstanceEntityImpl.setProcessInstanceId(String)"})
  public void testSetProcessInstanceId() {
    // Arrange
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl =
        new HistoricActivityInstanceEntityImpl();

    // Act
    historicActivityInstanceEntityImpl.setProcessInstanceId("42");

    // Assert
    assertEquals("42", historicActivityInstanceEntityImpl.getProcessInstanceId());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#setProcessDefinitionId(String)}.
   *
   * <p>Method under test: {@link HistoricScopeInstanceEntityImpl#setProcessDefinitionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricScopeInstanceEntityImpl.setProcessDefinitionId(String)"})
  public void testSetProcessDefinitionId() {
    // Arrange
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl =
        new HistoricActivityInstanceEntityImpl();

    // Act
    historicActivityInstanceEntityImpl.setProcessDefinitionId("42");

    // Assert
    assertEquals("42", historicActivityInstanceEntityImpl.getProcessDefinitionId());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#setStartTime(Date)}.
   *
   * <p>Method under test: {@link HistoricScopeInstanceEntityImpl#setStartTime(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricScopeInstanceEntityImpl.setStartTime(Date)"})
  public void testSetStartTime() {
    // Arrange
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl =
        new HistoricActivityInstanceEntityImpl();
    Date startTime =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    historicActivityInstanceEntityImpl.setStartTime(startTime);

    // Assert
    assertSame(startTime, historicActivityInstanceEntityImpl.getTime());
    assertSame(startTime, historicActivityInstanceEntityImpl.getStartTime());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#setEndTime(Date)}.
   *
   * <p>Method under test: {@link HistoricScopeInstanceEntityImpl#setEndTime(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricScopeInstanceEntityImpl.setEndTime(Date)"})
  public void testSetEndTime() {
    // Arrange
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl =
        new HistoricActivityInstanceEntityImpl();
    Date endTime =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    historicActivityInstanceEntityImpl.setEndTime(endTime);

    // Assert
    Object persistentState = historicActivityInstanceEntityImpl.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertEquals(5, ((Map<String, Date>) persistentState).size());
    assertTrue(((Map<String, Date>) persistentState).containsKey("assignee"));
    assertTrue(((Map<String, Date>) persistentState).containsKey("deleteReason"));
    assertTrue(((Map<String, Date>) persistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, Date>) persistentState).containsKey("executionId"));
    assertSame(endTime, ((Map<String, Date>) persistentState).get("endTime"));
    assertSame(endTime, historicActivityInstanceEntityImpl.getEndTime());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#setDurationInMillis(Long)}.
   *
   * <p>Method under test: {@link HistoricScopeInstanceEntityImpl#setDurationInMillis(Long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricScopeInstanceEntityImpl.setDurationInMillis(Long)"})
  public void testSetDurationInMillis() {
    // Arrange
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl =
        new HistoricActivityInstanceEntityImpl();

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
   *
   * <p>Method under test: {@link HistoricScopeInstanceEntityImpl#getDeleteReason()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricScopeInstanceEntityImpl.getDeleteReason()"})
  public void testGetDeleteReason() {
    // Arrange, Act and Assert
    assertNull(new HistoricActivityInstanceEntityImpl().getDeleteReason());
  }

  /**
   * Test {@link HistoricScopeInstanceEntityImpl#setDeleteReason(String)}.
   *
   * <p>Method under test: {@link HistoricScopeInstanceEntityImpl#setDeleteReason(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricScopeInstanceEntityImpl.setDeleteReason(String)"})
  public void testSetDeleteReason() {
    // Arrange
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl =
        new HistoricActivityInstanceEntityImpl();

    // Act
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
