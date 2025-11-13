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
package org.activiti.engine.impl.persistence.entity.data.impl.cachematcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HistoricVariableInstanceByTaskIdMatcherDiffblueTest {
  /**
   * Test {@link HistoricVariableInstanceByTaskIdMatcher#isRetained(HistoricVariableInstanceEntity,
   * Object)} with {@code HistoricVariableInstanceEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceByTaskIdMatcher#isRetained(HistoricVariableInstanceEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean HistoricVariableInstanceByTaskIdMatcher.isRetained(HistoricVariableInstanceEntity, Object)"
  })
  public void testIsRetainedWithHistoricVariableInstanceEntityObject() {
    // Arrange
    HistoricVariableInstanceByTaskIdMatcher historicVariableInstanceByTaskIdMatcher =
        new HistoricVariableInstanceByTaskIdMatcher();

    // Act and Assert
    assertFalse(
        historicVariableInstanceByTaskIdMatcher.isRetained(
            new HistoricVariableInstanceEntityImpl(), JSONObject.NULL));
  }

  /**
   * Test {@link HistoricVariableInstanceByTaskIdMatcher#isRetained(HistoricVariableInstanceEntity,
   * Object)} with {@code HistoricVariableInstanceEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceByTaskIdMatcher#isRetained(HistoricVariableInstanceEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean HistoricVariableInstanceByTaskIdMatcher.isRetained(HistoricVariableInstanceEntity, Object)"
  })
  public void testIsRetainedWithHistoricVariableInstanceEntityObject_givenNull() {
    // Arrange
    HistoricVariableInstanceByTaskIdMatcher historicVariableInstanceByTaskIdMatcher =
        new HistoricVariableInstanceByTaskIdMatcher();

    HistoricVariableInstanceEntityImpl historicVariableInstanceEntity =
        new HistoricVariableInstanceEntityImpl();
    historicVariableInstanceEntity.setCachedValue(JSONObject.NULL);
    historicVariableInstanceEntity.setCreateTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntity.setDeleted(true);
    historicVariableInstanceEntity.setDoubleValue(10.0d);
    historicVariableInstanceEntity.setExecutionId("42");
    historicVariableInstanceEntity.setId("42");
    historicVariableInstanceEntity.setInserted(true);
    historicVariableInstanceEntity.setLastUpdatedTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    historicVariableInstanceEntity.setLongValue(42L);
    historicVariableInstanceEntity.setName("Name");
    historicVariableInstanceEntity.setProcessInstanceId("42");
    historicVariableInstanceEntity.setRevision(1);
    historicVariableInstanceEntity.setTextValue("42");
    historicVariableInstanceEntity.setTextValue2("42");
    historicVariableInstanceEntity.setUpdated(true);
    historicVariableInstanceEntity.setVariableType(new BigDecimalType());
    historicVariableInstanceEntity.setTaskId("Historic Variable Instance Entity");

    // Act and Assert
    assertFalse(
        historicVariableInstanceByTaskIdMatcher.isRetained(
            historicVariableInstanceEntity, "Parameter"));
  }

  /**
   * Test {@link HistoricVariableInstanceByTaskIdMatcher#isRetained(HistoricVariableInstanceEntity,
   * Object)} with {@code HistoricVariableInstanceEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceByTaskIdMatcher#isRetained(HistoricVariableInstanceEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean HistoricVariableInstanceByTaskIdMatcher.isRetained(HistoricVariableInstanceEntity, Object)"
  })
  public void testIsRetainedWithHistoricVariableInstanceEntityObject_thenReturnTrue() {
    // Arrange
    HistoricVariableInstanceByTaskIdMatcher historicVariableInstanceByTaskIdMatcher =
        new HistoricVariableInstanceByTaskIdMatcher();

    HistoricVariableInstanceEntity historicVariableInstanceEntity =
        mock(HistoricVariableInstanceEntity.class);
    when(historicVariableInstanceEntity.getTaskId()).thenReturn("42");

    // Act
    boolean actualIsRetainedResult =
        historicVariableInstanceByTaskIdMatcher.isRetained(historicVariableInstanceEntity, "42");

    // Assert
    verify(historicVariableInstanceEntity, atLeast(1)).getTaskId();
    assertTrue(actualIsRetainedResult);
  }
}
