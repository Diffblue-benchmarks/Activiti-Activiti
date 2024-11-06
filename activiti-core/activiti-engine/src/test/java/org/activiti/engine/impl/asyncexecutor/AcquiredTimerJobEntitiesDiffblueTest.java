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
import org.activiti.engine.impl.persistence.entity.TimerJobEntity;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityImpl;
import org.activiti.engine.runtime.Job;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AcquiredTimerJobEntitiesDiffblueTest {
  @InjectMocks
  private AcquiredTimerJobEntities acquiredTimerJobEntities;

  /**
   * Test {@link AcquiredTimerJobEntities#addJob(TimerJobEntity)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then {@link AcquiredTimerJobEntities} (default constructor)
   * {@link AcquiredTimerJobEntities#acquiredJobs} {@code 42} is
   * {@link TimerJobEntity}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AcquiredTimerJobEntities#addJob(TimerJobEntity)}
   */
  @Test
  public void testAddJob_given42_thenAcquiredTimerJobEntitiesAcquiredJobs42IsTimerJobEntity() {
    // Arrange
    AcquiredTimerJobEntities acquiredTimerJobEntities = new AcquiredTimerJobEntities();
    TimerJobEntity job = mock(TimerJobEntity.class);
    when(job.getId()).thenReturn("42");

    // Act
    acquiredTimerJobEntities.addJob(job);

    // Assert
    verify(job).getId();
    assertEquals(1, acquiredTimerJobEntities.getJobs().size());
    Map<String, TimerJobEntity> stringTimerJobEntityMap = acquiredTimerJobEntities.acquiredJobs;
    assertEquals(1, stringTimerJobEntityMap.size());
    assertEquals(1, acquiredTimerJobEntities.size());
    assertSame(job, stringTimerJobEntityMap.get("42"));
  }

  /**
   * Test {@link AcquiredTimerJobEntities#addJob(TimerJobEntity)}.
   * <ul>
   *   <li>Then {@link AcquiredTimerJobEntities} (default constructor)
   * {@link AcquiredTimerJobEntities#acquiredJobs} {@code null} is
   * {@link TimerJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AcquiredTimerJobEntities#addJob(TimerJobEntity)}
   */
  @Test
  public void testAddJob_thenAcquiredTimerJobEntitiesAcquiredJobsNullIsTimerJobEntityImpl() {
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
   * <ul>
   *   <li>Given {@link AcquiredTimerJobEntities} (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AcquiredTimerJobEntities#getJobs()}
   */
  @Test
  public void testGetJobs_givenAcquiredTimerJobEntities_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new AcquiredTimerJobEntities()).getJobs().isEmpty());
  }

  /**
   * Test {@link AcquiredTimerJobEntities#getJobs()}.
   * <ul>
   *   <li>Given {@link TimerJobEntity} {@link Job#getId()} return {@code 42}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link AcquiredTimerJobEntities#getJobs()}
   */
  @Test
  public void testGetJobs_givenTimerJobEntityGetIdReturn42_thenReturnSizeIsOne() {
    // Arrange
    TimerJobEntity job = mock(TimerJobEntity.class);
    when(job.getId()).thenReturn("42");

    AcquiredTimerJobEntities acquiredTimerJobEntities = new AcquiredTimerJobEntities();
    acquiredTimerJobEntities.addJob(job);

    // Act
    Collection<TimerJobEntity> actualJobs = acquiredTimerJobEntities.getJobs();

    // Assert
    verify(job).getId();
    assertEquals(1, actualJobs.size());
  }

  /**
   * Test {@link AcquiredTimerJobEntities#contains(String)}.
   * <p>
   * Method under test: {@link AcquiredTimerJobEntities#contains(String)}
   */
  @Test
  public void testContains() {
    // Arrange, Act and Assert
    assertFalse(acquiredTimerJobEntities.contains("42"));
  }

  /**
   * Test {@link AcquiredTimerJobEntities#size()}.
   * <ul>
   *   <li>Given {@link AcquiredTimerJobEntities} (default constructor).</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link AcquiredTimerJobEntities#size()}
   */
  @Test
  public void testSize_givenAcquiredTimerJobEntities_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new AcquiredTimerJobEntities()).size());
  }

  /**
   * Test {@link AcquiredTimerJobEntities#size()}.
   * <ul>
   *   <li>Given {@link TimerJobEntity} {@link Job#getId()} return {@code 42}.</li>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link AcquiredTimerJobEntities#size()}
   */
  @Test
  public void testSize_givenTimerJobEntityGetIdReturn42_thenReturnOne() {
    // Arrange
    TimerJobEntity job = mock(TimerJobEntity.class);
    when(job.getId()).thenReturn("42");

    AcquiredTimerJobEntities acquiredTimerJobEntities = new AcquiredTimerJobEntities();
    acquiredTimerJobEntities.addJob(job);

    // Act
    int actualSizeResult = acquiredTimerJobEntities.size();

    // Assert
    verify(job).getId();
    assertEquals(1, actualSizeResult);
  }

  /**
   * Test new {@link AcquiredTimerJobEntities} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link AcquiredTimerJobEntities}
   */
  @Test
  public void testNewAcquiredTimerJobEntities() {
    // Arrange, Act and Assert
    assertTrue((new AcquiredTimerJobEntities()).acquiredJobs.isEmpty());
  }
}
