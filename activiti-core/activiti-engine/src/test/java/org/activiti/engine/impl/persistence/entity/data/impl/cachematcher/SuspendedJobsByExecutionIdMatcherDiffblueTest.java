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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.impl.persistence.entity.SuspendedJobEntity;
import org.activiti.engine.impl.persistence.entity.SuspendedJobEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class SuspendedJobsByExecutionIdMatcherDiffblueTest {
  /**
   * Test {@link SuspendedJobsByExecutionIdMatcher#isRetained(SuspendedJobEntity, Object)} with
   * {@code SuspendedJobEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SuspendedJobsByExecutionIdMatcher#isRetained(SuspendedJobEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SuspendedJobsByExecutionIdMatcher.isRetained(SuspendedJobEntity, Object)"
  })
  public void testIsRetainedWithSuspendedJobEntityObject_givenTrue() {
    // Arrange
    SuspendedJobsByExecutionIdMatcher suspendedJobsByExecutionIdMatcher =
        new SuspendedJobsByExecutionIdMatcher();

    SuspendedJobEntityImpl jobEntity = new SuspendedJobEntityImpl();
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
    assertFalse(suspendedJobsByExecutionIdMatcher.isRetained(jobEntity, JSONObject.NULL));
  }

  /**
   * Test {@link SuspendedJobsByExecutionIdMatcher#isRetained(SuspendedJobEntity, Object)} with
   * {@code SuspendedJobEntity}, {@code Object}.
   *
   * <ul>
   *   <li>When {@link SuspendedJobEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SuspendedJobsByExecutionIdMatcher#isRetained(SuspendedJobEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SuspendedJobsByExecutionIdMatcher.isRetained(SuspendedJobEntity, Object)"
  })
  public void testIsRetainedWithSuspendedJobEntityObject_whenSuspendedJobEntityImpl() {
    // Arrange
    SuspendedJobsByExecutionIdMatcher suspendedJobsByExecutionIdMatcher =
        new SuspendedJobsByExecutionIdMatcher();

    // Act and Assert
    assertFalse(
        suspendedJobsByExecutionIdMatcher.isRetained(
            new SuspendedJobEntityImpl(), JSONObject.NULL));
  }
}
