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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class HistoricDetailEntityImplDiffblueTest {
  /**
   * Test {@link HistoricDetailEntityImpl#getProcessInstanceId()}.
   *
   * <p>Method under test: {@link HistoricDetailEntityImpl#getProcessInstanceId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricDetailEntityImpl.getProcessInstanceId()"})
  public void testGetProcessInstanceId() {
    // Arrange, Act and Assert
    assertNull(new HistoricDetailAssignmentEntityImpl().getProcessInstanceId());
  }

  /**
   * Test {@link HistoricDetailEntityImpl#setProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link HistoricDetailEntityImpl#setProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricDetailEntityImpl.setProcessInstanceId(String)"})
  public void testSetProcessInstanceId() {
    // Arrange
    HistoricDetailAssignmentEntityImpl historicDetailAssignmentEntityImpl =
        new HistoricDetailAssignmentEntityImpl();

    // Act
    historicDetailAssignmentEntityImpl.setProcessInstanceId("42");

    // Assert
    assertEquals("42", historicDetailAssignmentEntityImpl.getProcessInstanceId());
  }

  /**
   * Test {@link HistoricDetailEntityImpl#getActivityInstanceId()}.
   *
   * <p>Method under test: {@link HistoricDetailEntityImpl#getActivityInstanceId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricDetailEntityImpl.getActivityInstanceId()"})
  public void testGetActivityInstanceId() {
    // Arrange, Act and Assert
    assertNull(new HistoricDetailAssignmentEntityImpl().getActivityInstanceId());
  }

  /**
   * Test {@link HistoricDetailEntityImpl#setActivityInstanceId(String)}.
   *
   * <p>Method under test: {@link HistoricDetailEntityImpl#setActivityInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricDetailEntityImpl.setActivityInstanceId(String)"})
  public void testSetActivityInstanceId() {
    // Arrange
    HistoricDetailAssignmentEntityImpl historicDetailAssignmentEntityImpl =
        new HistoricDetailAssignmentEntityImpl();

    // Act
    historicDetailAssignmentEntityImpl.setActivityInstanceId("42");

    // Assert
    assertEquals("42", historicDetailAssignmentEntityImpl.getActivityInstanceId());
  }

  /**
   * Test {@link HistoricDetailEntityImpl#getTaskId()}.
   *
   * <p>Method under test: {@link HistoricDetailEntityImpl#getTaskId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricDetailEntityImpl.getTaskId()"})
  public void testGetTaskId() {
    // Arrange, Act and Assert
    assertNull(new HistoricDetailAssignmentEntityImpl().getTaskId());
  }

  /**
   * Test {@link HistoricDetailEntityImpl#setTaskId(String)}.
   *
   * <p>Method under test: {@link HistoricDetailEntityImpl#setTaskId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricDetailEntityImpl.setTaskId(String)"})
  public void testSetTaskId() {
    // Arrange
    HistoricDetailAssignmentEntityImpl historicDetailAssignmentEntityImpl =
        new HistoricDetailAssignmentEntityImpl();

    // Act
    historicDetailAssignmentEntityImpl.setTaskId("42");

    // Assert
    assertEquals("42", historicDetailAssignmentEntityImpl.getTaskId());
  }

  /**
   * Test {@link HistoricDetailEntityImpl#getExecutionId()}.
   *
   * <p>Method under test: {@link HistoricDetailEntityImpl#getExecutionId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricDetailEntityImpl.getExecutionId()"})
  public void testGetExecutionId() {
    // Arrange, Act and Assert
    assertNull(new HistoricDetailAssignmentEntityImpl().getExecutionId());
  }

  /**
   * Test {@link HistoricDetailEntityImpl#setExecutionId(String)}.
   *
   * <p>Method under test: {@link HistoricDetailEntityImpl#setExecutionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricDetailEntityImpl.setExecutionId(String)"})
  public void testSetExecutionId() {
    // Arrange
    HistoricDetailAssignmentEntityImpl historicDetailAssignmentEntityImpl =
        new HistoricDetailAssignmentEntityImpl();

    // Act
    historicDetailAssignmentEntityImpl.setExecutionId("42");

    // Assert
    assertEquals("42", historicDetailAssignmentEntityImpl.getExecutionId());
  }

  /**
   * Test {@link HistoricDetailEntityImpl#getTime()}.
   *
   * <p>Method under test: {@link HistoricDetailEntityImpl#getTime()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date HistoricDetailEntityImpl.getTime()"})
  public void testGetTime() {
    // Arrange, Act and Assert
    assertNull(new HistoricDetailAssignmentEntityImpl().getTime());
  }

  /**
   * Test {@link HistoricDetailEntityImpl#setTime(Date)}.
   *
   * <p>Method under test: {@link HistoricDetailEntityImpl#setTime(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricDetailEntityImpl.setTime(Date)"})
  public void testSetTime() {
    // Arrange
    HistoricDetailAssignmentEntityImpl historicDetailAssignmentEntityImpl =
        new HistoricDetailAssignmentEntityImpl();
    Date time =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    historicDetailAssignmentEntityImpl.setTime(time);

    // Assert
    assertSame(time, historicDetailAssignmentEntityImpl.getTime());
  }

  /**
   * Test {@link HistoricDetailEntityImpl#getDetailType()}.
   *
   * <p>Method under test: {@link HistoricDetailEntityImpl#getDetailType()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricDetailEntityImpl.getDetailType()"})
  public void testGetDetailType() {
    // Arrange, Act and Assert
    assertNull(new HistoricDetailAssignmentEntityImpl().getDetailType());
  }

  /**
   * Test {@link HistoricDetailEntityImpl#setDetailType(String)}.
   *
   * <p>Method under test: {@link HistoricDetailEntityImpl#setDetailType(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricDetailEntityImpl.setDetailType(String)"})
  public void testSetDetailType() {
    // Arrange
    HistoricDetailAssignmentEntityImpl historicDetailAssignmentEntityImpl =
        new HistoricDetailAssignmentEntityImpl();

    // Act
    historicDetailAssignmentEntityImpl.setDetailType("Detail Type");

    // Assert
    assertEquals("Detail Type", historicDetailAssignmentEntityImpl.getDetailType());
  }
}
