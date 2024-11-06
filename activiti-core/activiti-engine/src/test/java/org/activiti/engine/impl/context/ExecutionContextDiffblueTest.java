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
package org.activiti.engine.impl.context;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import java.sql.Date;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class ExecutionContextDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExecutionContext#ExecutionContext(ExecutionEntity)}
   *   <li>{@link ExecutionContext#getExecution()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertSame(execution, (new ExecutionContext(execution)).getExecution());
  }

  /**
   * Test {@link ExecutionContext#getProcessInstance()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections LockTime is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionContext#getProcessInstance()}
   */
  @Test
  public void testGetProcessInstance_givenCreateWithEmptyRelationshipCollectionsLockTimeIsDate() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setLockTime(mock(Date.class));

    // Act and Assert
    assertNull((new ExecutionContext(execution)).getProcessInstance());
  }

  /**
   * Test {@link ExecutionContext#getProcessInstance()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionContext#getProcessInstance()}
   */
  @Test
  public void testGetProcessInstance_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        (new ExecutionContext(ExecutionEntityImpl.createWithEmptyRelationshipCollections())).getProcessInstance());
  }
}
