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
import static org.junit.Assert.assertNull;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class IntermediateCatchEventActivityBehaviorDiffblueTest {
  /**
   * Test
   * {@link IntermediateCatchEventActivityBehavior#getPrecedingEventBasedGateway(DelegateExecution)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateCatchEventActivityBehavior#getPrecedingEventBasedGateway(DelegateExecution)}
   */
  @Test
  public void testGetPrecedingEventBasedGateway_thenReturnNull() {
    // Arrange
    IntermediateCatchEventActivityBehavior intermediateCatchEventActivityBehavior = new IntermediateCatchEventActivityBehavior();

    // Act and Assert
    assertNull(intermediateCatchEventActivityBehavior
        .getPrecedingEventBasedGateway(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test new {@link IntermediateCatchEventActivityBehavior} (default
   * constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link IntermediateCatchEventActivityBehavior}
   */
  @Test
  public void testNewIntermediateCatchEventActivityBehavior() {
    // Arrange and Act
    IntermediateCatchEventActivityBehavior actualIntermediateCatchEventActivityBehavior = new IntermediateCatchEventActivityBehavior();

    // Assert
    assertNull(actualIntermediateCatchEventActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualIntermediateCatchEventActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualIntermediateCatchEventActivityBehavior.hasMultiInstanceCharacteristics());
  }
}
