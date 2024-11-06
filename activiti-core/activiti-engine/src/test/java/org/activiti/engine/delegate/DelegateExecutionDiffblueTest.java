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
package org.activiti.engine.delegate;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import java.sql.Date;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class DelegateExecutionDiffblueTest {
  /**
   * Test {@link DelegateExecution#getEngineServices()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegateExecution#getEngineServices()}
   */
  @Test
  public void testGetEngineServices_givenCreateWithEmptyRelationshipCollections() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getEngineServices());
  }

  /**
   * Test {@link DelegateExecution#getEngineServices()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections LockTime is
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegateExecution#getEngineServices()}
   */
  @Test
  public void testGetEngineServices_givenCreateWithEmptyRelationshipCollectionsLockTimeIsDate() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(Date.class));

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getEngineServices());
  }
}
