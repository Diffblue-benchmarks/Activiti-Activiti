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
import org.activiti.engine.impl.persistence.entity.JobEntity;
import org.activiti.engine.impl.persistence.entity.JobEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AcquiredJobEntitiesDiffblueTest {
  /**
   * Test {@link AcquiredJobEntities#addJob(JobEntity)}.
   *
   * <ul>
   *   <li>When {@link JobEntityImpl} (default constructor).
   *   <li>Then {@link AcquiredJobEntities} (default constructor) Jobs size is one.
   * </ul>
   *
   * <p>Method under test: {@link AcquiredJobEntities#addJob(JobEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AcquiredJobEntities.addJob(JobEntity)"})
  public void testAddJob_whenJobEntityImpl_thenAcquiredJobEntitiesJobsSizeIsOne() {
    // Arrange
    AcquiredJobEntities acquiredJobEntities = new AcquiredJobEntities();
    JobEntityImpl job = new JobEntityImpl();

    // Act
    acquiredJobEntities.addJob(job);

    // Assert
    assertEquals(1, acquiredJobEntities.getJobs().size());
    Map<String, JobEntity> stringJobEntityMap = acquiredJobEntities.acquiredJobs;
    assertEquals(1, stringJobEntityMap.size());
    assertEquals(1, acquiredJobEntities.size());
    assertSame(job, stringJobEntityMap.get(null));
  }

  /**
   * Test {@link AcquiredJobEntities#getJobs()}.
   *
   * <p>Method under test: {@link AcquiredJobEntities#getJobs()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Collection AcquiredJobEntities.getJobs()"})
  public void testGetJobs() {
    // Arrange, Act and Assert
    assertTrue(new AcquiredJobEntities().getJobs().isEmpty());
  }

  /**
   * Test {@link AcquiredJobEntities#contains(String)}.
   *
   * <ul>
   *   <li>Given {@link AcquiredJobEntities} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AcquiredJobEntities#contains(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AcquiredJobEntities.contains(String)"})
  public void testContains_givenAcquiredJobEntities_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new AcquiredJobEntities().contains("42"));
  }

  /**
   * Test {@link AcquiredJobEntities#contains(String)}.
   *
   * <ul>
   *   <li>Given {@link JobEntityImpl} (default constructor) Id is {@code 42}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AcquiredJobEntities#contains(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AcquiredJobEntities.contains(String)"})
  public void testContains_givenJobEntityImplIdIs42_thenReturnTrue() {
    // Arrange
    JobEntityImpl job = new JobEntityImpl();
    job.setId("42");

    AcquiredJobEntities acquiredJobEntities = new AcquiredJobEntities();
    acquiredJobEntities.addJob(job);

    // Act and Assert
    assertTrue(acquiredJobEntities.contains("42"));
  }

  /**
   * Test {@link AcquiredJobEntities#size()}.
   *
   * <p>Method under test: {@link AcquiredJobEntities#size()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int AcquiredJobEntities.size()"})
  public void testSize() {
    // Arrange, Act and Assert
    assertEquals(0, new AcquiredJobEntities().size());
  }

  /**
   * Test new {@link AcquiredJobEntities} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link AcquiredJobEntities}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AcquiredJobEntities.<init>()"})
  public void testNewAcquiredJobEntities() {
    // Arrange, Act and Assert
    assertTrue(new AcquiredJobEntities().acquiredJobs.isEmpty());
  }
}
