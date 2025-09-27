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
package org.activiti.engine.impl.asyncexecutor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import org.activiti.engine.impl.persistence.entity.TimerJobEntity;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AcquiredTimerJobEntitiesDiffblueTest {
  /**
   * Test {@link AcquiredTimerJobEntities#addJob(TimerJobEntity)}.
   *
   * <ul>
   *   <li>When {@link TimerJobEntityImpl} (default constructor).
   *   <li>Then {@link AcquiredTimerJobEntities} (default constructor) Jobs size is one.
   * </ul>
   *
   * <p>Method under test: {@link AcquiredTimerJobEntities#addJob(TimerJobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AcquiredTimerJobEntities.addJob(TimerJobEntity)"})
  public void testAddJob_whenTimerJobEntityImpl_thenAcquiredTimerJobEntitiesJobsSizeIsOne() {
    // Arrange
    AcquiredTimerJobEntities acquiredTimerJobEntities = new AcquiredTimerJobEntities();
    TimerJobEntityImpl job = new TimerJobEntityImpl();

    // Act
    acquiredTimerJobEntities.addJob(job);

    // Assert
    assertEquals(1, acquiredTimerJobEntities.getJobs().size());
    Map<String, TimerJobEntity> stringTimerJobEntityMap = acquiredTimerJobEntities.acquiredJobs;
    assertEquals(1, stringTimerJobEntityMap.size());
    assertEquals(1, acquiredTimerJobEntities.size());
    assertSame(job, stringTimerJobEntityMap.get(null));
  }

  /**
   * Test {@link AcquiredTimerJobEntities#getJobs()}.
   *
   * <p>Method under test: {@link AcquiredTimerJobEntities#getJobs()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Collection AcquiredTimerJobEntities.getJobs()"})
  public void testGetJobs() {
    // Arrange, Act and Assert
    assertTrue(new AcquiredTimerJobEntities().getJobs().isEmpty());
  }

  /**
   * Test {@link AcquiredTimerJobEntities#contains(String)}.
   *
   * <ul>
   *   <li>Given {@link AcquiredTimerJobEntities} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AcquiredTimerJobEntities#contains(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AcquiredTimerJobEntities.contains(String)"})
  public void testContains_givenAcquiredTimerJobEntities_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new AcquiredTimerJobEntities().contains("42"));
  }

  /**
   * Test {@link AcquiredTimerJobEntities#contains(String)}.
   *
   * <ul>
   *   <li>Given {@link TimerJobEntityImpl} (default constructor) Id is {@code 42}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AcquiredTimerJobEntities#contains(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AcquiredTimerJobEntities.contains(String)"})
  public void testContains_givenTimerJobEntityImplIdIs42_thenReturnTrue() {
    // Arrange
    TimerJobEntityImpl job = new TimerJobEntityImpl();
    job.setId("42");

    AcquiredTimerJobEntities acquiredTimerJobEntities = new AcquiredTimerJobEntities();
    acquiredTimerJobEntities.addJob(job);

    // Act and Assert
    assertTrue(acquiredTimerJobEntities.contains("42"));
  }

  /**
   * Test {@link AcquiredTimerJobEntities#size()}.
   *
   * <p>Method under test: {@link AcquiredTimerJobEntities#size()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int AcquiredTimerJobEntities.size()"})
  public void testSize() {
    // Arrange, Act and Assert
    assertEquals(0, new AcquiredTimerJobEntities().size());
  }

  /**
   * Test new {@link AcquiredTimerJobEntities} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link AcquiredTimerJobEntities}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AcquiredTimerJobEntities.<init>()"})
  public void testNewAcquiredTimerJobEntities() {
    // Arrange, Act and Assert
    assertTrue(new AcquiredTimerJobEntities().acquiredJobs.isEmpty());
  }
}
