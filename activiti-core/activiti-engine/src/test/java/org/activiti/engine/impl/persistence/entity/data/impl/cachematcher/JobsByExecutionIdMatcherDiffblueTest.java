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
import org.activiti.engine.impl.persistence.entity.JobEntity;
import org.activiti.engine.impl.persistence.entity.JobEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class JobsByExecutionIdMatcherDiffblueTest {
  /**
   * Test {@link JobsByExecutionIdMatcher#isRetained(JobEntity, Object)} with
   * {@code JobEntity}, {@code Object}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JobsByExecutionIdMatcher#isRetained(JobEntity, Object)}
   */
  @Test
  public void testIsRetainedWithJobEntityObject_given42_when42_thenReturnTrue() {
    // Arrange
    JobsByExecutionIdMatcher jobsByExecutionIdMatcher = new JobsByExecutionIdMatcher();
    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualIsRetainedResult = jobsByExecutionIdMatcher.isRetained(jobEntity, "42");

    // Assert
    verify(jobEntity, atLeast(1)).getExecutionId();
    assertTrue(actualIsRetainedResult);
  }

  /**
   * Test {@link JobsByExecutionIdMatcher#isRetained(JobEntity, Object)} with
   * {@code JobEntity}, {@code Object}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@code Parameter}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JobsByExecutionIdMatcher#isRetained(JobEntity, Object)}
   */
  @Test
  public void testIsRetainedWithJobEntityObject_given42_whenParameter_thenReturnFalse() {
    // Arrange
    JobsByExecutionIdMatcher jobsByExecutionIdMatcher = new JobsByExecutionIdMatcher();
    JobEntity jobEntity = mock(JobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualIsRetainedResult = jobsByExecutionIdMatcher.isRetained(jobEntity, "Parameter");

    // Assert
    verify(jobEntity, atLeast(1)).getExecutionId();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link JobsByExecutionIdMatcher#isRetained(JobEntity, Object)} with
   * {@code JobEntity}, {@code Object}.
   * <ul>
   *   <li>When {@link JobEntityImpl} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JobsByExecutionIdMatcher#isRetained(JobEntity, Object)}
   */
  @Test
  public void testIsRetainedWithJobEntityObject_whenJobEntityImpl_thenReturnFalse() {
    // Arrange
    JobsByExecutionIdMatcher jobsByExecutionIdMatcher = new JobsByExecutionIdMatcher();

    // Act and Assert
    assertFalse(jobsByExecutionIdMatcher.isRetained(new JobEntityImpl(), JSONObject.NULL));
  }
}
