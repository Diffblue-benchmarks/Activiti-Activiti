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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collection;
import java.util.Map;
import org.activiti.engine.impl.persistence.entity.JobEntity;
import org.activiti.engine.impl.persistence.entity.JobEntityImpl;
import org.activiti.engine.runtime.Job;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AcquiredJobEntitiesDiffblueTest {
  @InjectMocks
  private AcquiredJobEntities acquiredJobEntities;

  /**
   * Test {@link AcquiredJobEntities#addJob(JobEntity)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then {@link AcquiredJobEntities} (default constructor)
   * {@link AcquiredJobEntities#acquiredJobs} {@code 42} is
   * {@link JobEntity}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AcquiredJobEntities#addJob(JobEntity)}
   */
  @Test
  public void testAddJob_given42_thenAcquiredJobEntitiesAcquiredJobs42IsJobEntity() {
    // Arrange
    AcquiredJobEntities acquiredJobEntities = new AcquiredJobEntities();
    JobEntity job = mock(JobEntity.class);
    when(job.getId()).thenReturn("42");

    // Act
    acquiredJobEntities.addJob(job);

    // Assert
    verify(job).getId();
    assertEquals(1, acquiredJobEntities.getJobs().size());
    Map<String, JobEntity> stringJobEntityMap = acquiredJobEntities.acquiredJobs;
    assertEquals(1, stringJobEntityMap.size());
    assertEquals(1, acquiredJobEntities.size());
    assertSame(job, stringJobEntityMap.get("42"));
  }

  /**
   * Test {@link AcquiredJobEntities#addJob(JobEntity)}.
   * <ul>
   *   <li>Then {@link AcquiredJobEntities} (default constructor)
   * {@link AcquiredJobEntities#acquiredJobs} {@code null} is
   * {@link JobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AcquiredJobEntities#addJob(JobEntity)}
   */
  @Test
  public void testAddJob_thenAcquiredJobEntitiesAcquiredJobsNullIsJobEntityImpl() {
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
   * <ul>
   *   <li>Given {@link AcquiredJobEntities} (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AcquiredJobEntities#getJobs()}
   */
  @Test
  public void testGetJobs_givenAcquiredJobEntities_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new AcquiredJobEntities()).getJobs().isEmpty());
  }

  /**
   * Test {@link AcquiredJobEntities#getJobs()}.
   * <ul>
   *   <li>Given {@link JobEntity} {@link Job#getId()} return {@code 42}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link AcquiredJobEntities#getJobs()}
   */
  @Test
  public void testGetJobs_givenJobEntityGetIdReturn42_thenReturnSizeIsOne() {
    // Arrange
    JobEntity job = mock(JobEntity.class);
    when(job.getId()).thenReturn("42");

    AcquiredJobEntities acquiredJobEntities = new AcquiredJobEntities();
    acquiredJobEntities.addJob(job);

    // Act
    Collection<JobEntity> actualJobs = acquiredJobEntities.getJobs();

    // Assert
    verify(job).getId();
    assertEquals(1, actualJobs.size());
  }

  /**
   * Test {@link AcquiredJobEntities#contains(String)}.
   * <p>
   * Method under test: {@link AcquiredJobEntities#contains(String)}
   */
  @Test
  public void testContains() {
    // Arrange, Act and Assert
    assertFalse(acquiredJobEntities.contains("42"));
  }

  /**
   * Test {@link AcquiredJobEntities#size()}.
   * <ul>
   *   <li>Given {@link AcquiredJobEntities} (default constructor).</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link AcquiredJobEntities#size()}
   */
  @Test
  public void testSize_givenAcquiredJobEntities_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new AcquiredJobEntities()).size());
  }

  /**
   * Test {@link AcquiredJobEntities#size()}.
   * <ul>
   *   <li>Given {@link JobEntity} {@link Job#getId()} return {@code 42}.</li>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link AcquiredJobEntities#size()}
   */
  @Test
  public void testSize_givenJobEntityGetIdReturn42_thenReturnOne() {
    // Arrange
    JobEntity job = mock(JobEntity.class);
    when(job.getId()).thenReturn("42");

    AcquiredJobEntities acquiredJobEntities = new AcquiredJobEntities();
    acquiredJobEntities.addJob(job);

    // Act
    int actualSizeResult = acquiredJobEntities.size();

    // Assert
    verify(job).getId();
    assertEquals(1, actualSizeResult);
  }

  /**
   * Test new {@link AcquiredJobEntities} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link AcquiredJobEntities}
   */
  @Test
  public void testNewAcquiredJobEntities() {
    // Arrange, Act and Assert
    assertTrue((new AcquiredJobEntities()).acquiredJobs.isEmpty());
  }
}
