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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AcquiredTimerJobEntitiesDiffblueTest {
  @InjectMocks
  private AcquiredTimerJobEntities acquiredTimerJobEntities;

  /**
   * Method under test: {@link AcquiredTimerJobEntities#addJob(TimerJobEntity)}
   */
  @Test
  public void testAddJob() {
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
   * Method under test: {@link AcquiredTimerJobEntities#addJob(TimerJobEntity)}
   */
  @Test
  public void testAddJob2() {
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
   * Method under test: {@link AcquiredTimerJobEntities#getJobs()}
   */
  @Test
  public void testGetJobs() {
    // Arrange, Act and Assert
    assertTrue((new AcquiredTimerJobEntities()).getJobs().isEmpty());
  }

  /**
   * Method under test: {@link AcquiredTimerJobEntities#getJobs()}
   */
  @Test
  public void testGetJobs2() {
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
   * Method under test: {@link AcquiredTimerJobEntities#contains(String)}
   */
  @Test
  public void testContains() {
    // Arrange, Act and Assert
    assertFalse(acquiredTimerJobEntities.contains("42"));
  }

  /**
   * Method under test: {@link AcquiredTimerJobEntities#size()}
   */
  @Test
  public void testSize() {
    // Arrange, Act and Assert
    assertEquals(0, (new AcquiredTimerJobEntities()).size());
  }

  /**
   * Method under test: {@link AcquiredTimerJobEntities#size()}
   */
  @Test
  public void testSize2() {
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
   * Method under test: default or parameterless constructor of
   * {@link AcquiredTimerJobEntities}
   */
  @Test
  public void testNewAcquiredTimerJobEntities() {
    // Arrange, Act and Assert
    assertTrue((new AcquiredTimerJobEntities()).acquiredJobs.isEmpty());
  }
}
