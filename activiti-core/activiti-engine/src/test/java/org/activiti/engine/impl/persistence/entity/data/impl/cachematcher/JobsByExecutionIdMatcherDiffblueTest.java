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
import org.activiti.engine.impl.persistence.entity.JobEntity;
import org.activiti.engine.impl.persistence.entity.JobEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JobsByExecutionIdMatcherDiffblueTest {
  /**
   * Test {@link JobsByExecutionIdMatcher#isRetained(JobEntity, Object)} with {@code JobEntity},
   * {@code Object}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>When {@link JobEntityImpl} (default constructor) Deleted is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JobsByExecutionIdMatcher#isRetained(JobEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobsByExecutionIdMatcher.isRetained(JobEntity, Object)"})
  public void testIsRetainedWithJobEntityObject_givenTrue_whenJobEntityImplDeletedIsTrue() {
    // Arrange
    JobsByExecutionIdMatcher jobsByExecutionIdMatcher = new JobsByExecutionIdMatcher();

    JobEntityImpl jobEntity = new JobEntityImpl();
    jobEntity.setDeleted(true);
    jobEntity.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setExceptionMessage("An error occurred");
    jobEntity.setExclusive(true);
    jobEntity.setId("42");
    jobEntity.setInserted(true);
    jobEntity.setJobHandlerConfiguration("Job Handler Configuration");
    jobEntity.setJobHandlerType("Job Handler Type");
    jobEntity.setJobType("Job Type");
    jobEntity.setLockExpirationTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntity.setLockOwner("Claimed By");
    jobEntity.setMaxIterations(3);
    jobEntity.setProcessDefinitionId("42");
    jobEntity.setProcessInstanceId("42");
    jobEntity.setRepeat("Repeat");
    jobEntity.setRetries(1);
    jobEntity.setRevision(1);
    jobEntity.setTenantId("42");
    jobEntity.setUpdated(true);
    jobEntity.setExecutionId("Job Entity");

    // Act and Assert
    assertFalse(jobsByExecutionIdMatcher.isRetained(jobEntity, "Parameter"));
  }

  /**
   * Test {@link JobsByExecutionIdMatcher#isRetained(JobEntity, Object)} with {@code JobEntity},
   * {@code Object}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JobsByExecutionIdMatcher#isRetained(JobEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobsByExecutionIdMatcher.isRetained(JobEntity, Object)"})
  public void testIsRetainedWithJobEntityObject_thenReturnTrue() {
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
   * Test {@link JobsByExecutionIdMatcher#isRetained(JobEntity, Object)} with {@code JobEntity},
   * {@code Object}.
   *
   * <ul>
   *   <li>When {@link JobEntityImpl} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JobsByExecutionIdMatcher#isRetained(JobEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JobsByExecutionIdMatcher.isRetained(JobEntity, Object)"})
  public void testIsRetainedWithJobEntityObject_whenJobEntityImpl_thenReturnFalse() {
    // Arrange
    JobsByExecutionIdMatcher jobsByExecutionIdMatcher = new JobsByExecutionIdMatcher();

    // Act and Assert
    assertFalse(jobsByExecutionIdMatcher.isRetained(new JobEntityImpl(), JSONObject.NULL));
  }
}
