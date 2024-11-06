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
package org.activiti.engine.impl.bpmn.behavior;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class BoundaryEventActivityBehaviorDiffblueTest {
  /**
   * Test {@link BoundaryEventActivityBehavior#BoundaryEventActivityBehavior()}.
   * <p>
   * Method under test:
   * {@link BoundaryEventActivityBehavior#BoundaryEventActivityBehavior()}
   */
  @Test
  public void testNewBoundaryEventActivityBehavior() {
    // Arrange, Act and Assert
    assertFalse((new BoundaryEventActivityBehavior()).isInterrupting());
    assertTrue((new BoundaryEventActivityBehavior(true)).isInterrupting());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BoundaryEventActivityBehavior#setInterrupting(boolean)}
   *   <li>{@link BoundaryEventActivityBehavior#execute(DelegateExecution)}
   *   <li>{@link BoundaryEventActivityBehavior#isInterrupting()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    BoundaryEventActivityBehavior boundaryEventActivityBehavior = new BoundaryEventActivityBehavior();

    // Act
    boundaryEventActivityBehavior.setInterrupting(true);
    boundaryEventActivityBehavior.execute(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert that nothing has changed
    assertTrue(boundaryEventActivityBehavior.isInterrupting());
  }
}
