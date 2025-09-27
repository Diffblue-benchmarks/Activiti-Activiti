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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class UnfinishedHistoricActivityInstanceMatcherDiffblueTest {
  /**
   * Test {@link
   * UnfinishedHistoricActivityInstanceMatcher#isRetained(HistoricActivityInstanceEntity, Object)}
   * with {@code HistoricActivityInstanceEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * UnfinishedHistoricActivityInstanceMatcher#isRetained(HistoricActivityInstanceEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean UnfinishedHistoricActivityInstanceMatcher.isRetained(HistoricActivityInstanceEntity, Object)"
  })
  public void testIsRetainedWithHistoricActivityInstanceEntityObject() {
    // Arrange
    UnfinishedHistoricActivityInstanceMatcher unfinishedHistoricActivityInstanceMatcher =
        new UnfinishedHistoricActivityInstanceMatcher();

    HistoricActivityInstanceEntityImpl entity = new HistoricActivityInstanceEntityImpl();
    entity.setExecutionId(null);
    entity.setActivityId(null);
    entity.setEndTime(null);

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("executionId", "Parameter");
    objectObjectMap.put("activityId", "Parameter");

    // Act and Assert
    assertFalse(unfinishedHistoricActivityInstanceMatcher.isRetained(entity, objectObjectMap));
  }

  /**
   * Test {@link
   * UnfinishedHistoricActivityInstanceMatcher#isRetained(HistoricActivityInstanceEntity, Object)}
   * with {@code HistoricActivityInstanceEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * UnfinishedHistoricActivityInstanceMatcher#isRetained(HistoricActivityInstanceEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean UnfinishedHistoricActivityInstanceMatcher.isRetained(HistoricActivityInstanceEntity, Object)"
  })
  public void testIsRetainedWithHistoricActivityInstanceEntityObject2() {
    // Arrange
    UnfinishedHistoricActivityInstanceMatcher unfinishedHistoricActivityInstanceMatcher =
        new UnfinishedHistoricActivityInstanceMatcher();

    HistoricActivityInstanceEntityImpl entity = new HistoricActivityInstanceEntityImpl();
    entity.setExecutionId("Parameter");
    entity.setActivityId(null);
    entity.setEndTime(null);

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("executionId", "Parameter");
    objectObjectMap.put("activityId", "Parameter");

    // Act and Assert
    assertFalse(unfinishedHistoricActivityInstanceMatcher.isRetained(entity, objectObjectMap));
  }

  /**
   * Test {@link
   * UnfinishedHistoricActivityInstanceMatcher#isRetained(HistoricActivityInstanceEntity, Object)}
   * with {@code HistoricActivityInstanceEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * UnfinishedHistoricActivityInstanceMatcher#isRetained(HistoricActivityInstanceEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean UnfinishedHistoricActivityInstanceMatcher.isRetained(HistoricActivityInstanceEntity, Object)"
  })
  public void testIsRetainedWithHistoricActivityInstanceEntityObject3() {
    // Arrange
    UnfinishedHistoricActivityInstanceMatcher unfinishedHistoricActivityInstanceMatcher =
        new UnfinishedHistoricActivityInstanceMatcher();

    HistoricActivityInstanceEntityImpl entity = new HistoricActivityInstanceEntityImpl();
    entity.setExecutionId("Parameter");
    entity.setActivityId("Parameter");
    entity.setEndTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("executionId", "Parameter");
    objectObjectMap.put("activityId", "Parameter");

    // Act and Assert
    assertFalse(unfinishedHistoricActivityInstanceMatcher.isRetained(entity, objectObjectMap));
  }

  /**
   * Test {@link
   * UnfinishedHistoricActivityInstanceMatcher#isRetained(HistoricActivityInstanceEntity, Object)}
   * with {@code HistoricActivityInstanceEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * UnfinishedHistoricActivityInstanceMatcher#isRetained(HistoricActivityInstanceEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean UnfinishedHistoricActivityInstanceMatcher.isRetained(HistoricActivityInstanceEntity, Object)"
  })
  public void testIsRetainedWithHistoricActivityInstanceEntityObject_given42() {
    // Arrange
    UnfinishedHistoricActivityInstanceMatcher unfinishedHistoricActivityInstanceMatcher =
        new UnfinishedHistoricActivityInstanceMatcher();

    HistoricActivityInstanceEntityImpl entity = new HistoricActivityInstanceEntityImpl();
    entity.setExecutionId("Parameter");
    entity.setActivityId("42");
    entity.setEndTime(null);

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("executionId", "Parameter");
    objectObjectMap.put("activityId", "Parameter");

    // Act and Assert
    assertFalse(unfinishedHistoricActivityInstanceMatcher.isRetained(entity, objectObjectMap));
  }

  /**
   * Test {@link
   * UnfinishedHistoricActivityInstanceMatcher#isRetained(HistoricActivityInstanceEntity, Object)}
   * with {@code HistoricActivityInstanceEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code Entity}.
   * </ul>
   *
   * <p>Method under test: {@link
   * UnfinishedHistoricActivityInstanceMatcher#isRetained(HistoricActivityInstanceEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean UnfinishedHistoricActivityInstanceMatcher.isRetained(HistoricActivityInstanceEntity, Object)"
  })
  public void testIsRetainedWithHistoricActivityInstanceEntityObject_givenEntity() {
    // Arrange
    UnfinishedHistoricActivityInstanceMatcher unfinishedHistoricActivityInstanceMatcher =
        new UnfinishedHistoricActivityInstanceMatcher();

    HistoricActivityInstanceEntityImpl entity = new HistoricActivityInstanceEntityImpl();
    entity.setExecutionId("Entity");
    entity.setActivityId(null);
    entity.setEndTime(null);

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("executionId", "Parameter");
    objectObjectMap.put("activityId", "Parameter");

    // Act and Assert
    assertFalse(unfinishedHistoricActivityInstanceMatcher.isRetained(entity, objectObjectMap));
  }

  /**
   * Test {@link
   * UnfinishedHistoricActivityInstanceMatcher#isRetained(HistoricActivityInstanceEntity, Object)}
   * with {@code HistoricActivityInstanceEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * UnfinishedHistoricActivityInstanceMatcher#isRetained(HistoricActivityInstanceEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean UnfinishedHistoricActivityInstanceMatcher.isRetained(HistoricActivityInstanceEntity, Object)"
  })
  public void testIsRetainedWithHistoricActivityInstanceEntityObject_thenReturnTrue() {
    // Arrange
    UnfinishedHistoricActivityInstanceMatcher unfinishedHistoricActivityInstanceMatcher =
        new UnfinishedHistoricActivityInstanceMatcher();

    HistoricActivityInstanceEntityImpl entity = new HistoricActivityInstanceEntityImpl();
    entity.setExecutionId("Parameter");
    entity.setActivityId("Parameter");
    entity.setEndTime(null);

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("executionId", "Parameter");
    objectObjectMap.put("activityId", "Parameter");

    // Act and Assert
    assertTrue(unfinishedHistoricActivityInstanceMatcher.isRetained(entity, objectObjectMap));
  }
}
