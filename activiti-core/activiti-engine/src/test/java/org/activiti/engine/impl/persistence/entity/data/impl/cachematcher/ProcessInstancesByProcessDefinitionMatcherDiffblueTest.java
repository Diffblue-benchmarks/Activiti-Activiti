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
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ProcessInstancesByProcessDefinitionMatcherDiffblueTest {
  /**
   * Test {@link ProcessInstancesByProcessDefinitionMatcher#isRetained(ExecutionEntity, Object)}
   * with {@code ExecutionEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * ProcessInstancesByProcessDefinitionMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ProcessInstancesByProcessDefinitionMatcher.isRetained(ExecutionEntity, Object)"
  })
  public void testIsRetainedWithExecutionEntityObject() {
    // Arrange
    ProcessInstancesByProcessDefinitionMatcher processInstancesByProcessDefinitionMatcher =
        new ProcessInstancesByProcessDefinitionMatcher();

    // Act and Assert
    assertFalse(
        processInstancesByProcessDefinitionMatcher.isRetained(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections(), JSONObject.NULL));
  }

  /**
   * Test {@link ProcessInstancesByProcessDefinitionMatcher#isRetained(ExecutionEntity, Object)}
   * with {@code ExecutionEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * ProcessInstancesByProcessDefinitionMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ProcessInstancesByProcessDefinitionMatcher.isRetained(ExecutionEntity, Object)"
  })
  public void testIsRetainedWithExecutionEntityObject2() {
    // Arrange
    ProcessInstancesByProcessDefinitionMatcher processInstancesByProcessDefinitionMatcher =
        new ProcessInstancesByProcessDefinitionMatcher();

    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    entity.setParentId(null);
    entity.setProcessDefinitionId("Entity");

    // Act and Assert
    assertFalse(processInstancesByProcessDefinitionMatcher.isRetained(entity, JSONObject.NULL));
  }

  /**
   * Test {@link ProcessInstancesByProcessDefinitionMatcher#isRetained(ExecutionEntity, Object)}
   * with {@code ExecutionEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * ProcessInstancesByProcessDefinitionMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ProcessInstancesByProcessDefinitionMatcher.isRetained(ExecutionEntity, Object)"
  })
  public void testIsRetainedWithExecutionEntityObject3() {
    // Arrange
    ProcessInstancesByProcessDefinitionMatcher processInstancesByProcessDefinitionMatcher =
        new ProcessInstancesByProcessDefinitionMatcher();

    ExecutionEntityImpl entity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    entity.setParentId("Entity");
    entity.setProcessDefinitionId(null);

    // Act and Assert
    assertFalse(processInstancesByProcessDefinitionMatcher.isRetained(entity, JSONObject.NULL));
  }
}
