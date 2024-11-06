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
package org.activiti.engine.impl.jobexecutor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AcquiredJobsDiffblueTest {
  @InjectMocks
  private AcquiredJobs acquiredJobs;

  /**
   * Test {@link AcquiredJobs#addJobIdBatch(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then {@link AcquiredJobs} (default constructor)
   * {@link AcquiredJobs#acquiredJobs} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link AcquiredJobs#addJobIdBatch(List)}
   */
  @Test
  public void testAddJobIdBatch_given42_thenAcquiredJobsAcquiredJobsSizeIsTwo() {
    // Arrange
    AcquiredJobs acquiredJobs = new AcquiredJobs();

    ArrayList<String> jobIds = new ArrayList<>();
    jobIds.add("42");
    jobIds.add("foo");

    // Act
    acquiredJobs.addJobIdBatch(jobIds);

    // Assert
    Set<String> stringSet = acquiredJobs.acquiredJobs;
    assertEquals(2, stringSet.size());
    assertEquals(2, acquiredJobs.size());
    assertTrue(stringSet.contains("42"));
    assertTrue(stringSet.contains("foo"));
  }

  /**
   * Test {@link AcquiredJobs#addJobIdBatch(List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then {@link AcquiredJobs} (default constructor)
   * {@link AcquiredJobs#acquiredJobs} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link AcquiredJobs#addJobIdBatch(List)}
   */
  @Test
  public void testAddJobIdBatch_givenFoo_thenAcquiredJobsAcquiredJobsSizeIsOne() {
    // Arrange
    AcquiredJobs acquiredJobs = new AcquiredJobs();

    ArrayList<String> jobIds = new ArrayList<>();
    jobIds.add("foo");

    // Act
    acquiredJobs.addJobIdBatch(jobIds);

    // Assert
    List<List<String>> jobIdBatches = acquiredJobs.getJobIdBatches();
    assertEquals(1, jobIdBatches.size());
    Set<String> stringSet = acquiredJobs.acquiredJobs;
    assertEquals(1, stringSet.size());
    assertEquals(1, acquiredJobs.size());
    assertTrue(stringSet.contains("foo"));
    assertSame(jobIds, jobIdBatches.get(0));
  }

  /**
   * Test {@link AcquiredJobs#addJobIdBatch(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link AcquiredJobs} (default constructor) size is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link AcquiredJobs#addJobIdBatch(List)}
   */
  @Test
  public void testAddJobIdBatch_whenArrayList_thenAcquiredJobsSizeIsZero() {
    // Arrange
    AcquiredJobs acquiredJobs = new AcquiredJobs();
    ArrayList<String> jobIds = new ArrayList<>();

    // Act
    acquiredJobs.addJobIdBatch(jobIds);

    // Assert
    assertEquals(0, acquiredJobs.size());
    List<List<String>> jobIdBatches = acquiredJobs.getJobIdBatches();
    assertEquals(1, jobIdBatches.size());
    assertTrue(acquiredJobs.acquiredJobs.isEmpty());
    assertSame(jobIds, jobIdBatches.get(0));
  }

  /**
   * Test {@link AcquiredJobs#contains(String)}.
   * <p>
   * Method under test: {@link AcquiredJobs#contains(String)}
   */
  @Test
  public void testContains() {
    // Arrange, Act and Assert
    assertFalse(acquiredJobs.contains("42"));
  }

  /**
   * Test {@link AcquiredJobs#size()}.
   * <p>
   * Method under test: {@link AcquiredJobs#size()}
   */
  @Test
  public void testSize() {
    // Arrange, Act and Assert
    assertEquals(0, (new AcquiredJobs()).size());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link AcquiredJobs}
   *   <li>{@link AcquiredJobs#getJobIdBatches()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    AcquiredJobs actualAcquiredJobs = new AcquiredJobs();

    // Assert
    assertTrue(actualAcquiredJobs.getJobIdBatches().isEmpty());
    assertTrue(actualAcquiredJobs.acquiredJobs.isEmpty());
  }
}
