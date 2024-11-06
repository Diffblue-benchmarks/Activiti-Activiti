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
import org.junit.Test;

public class TransactionActivityBehaviorDiffblueTest {
  /**
   * Test new {@link TransactionActivityBehavior} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link TransactionActivityBehavior}
   */
  @Test
  public void testNewTransactionActivityBehavior() {
    // Arrange and Act
    TransactionActivityBehavior actualTransactionActivityBehavior = new TransactionActivityBehavior();

    // Assert
    assertNull(actualTransactionActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualTransactionActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualTransactionActivityBehavior.hasMultiInstanceCharacteristics());
  }
}
