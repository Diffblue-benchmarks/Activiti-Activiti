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
package org.activiti.engine.impl.cfg.standalone;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.activiti.engine.impl.cfg.TransactionContext;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;

public class StandaloneMybatisTransactionContextFactoryDiffblueTest {
  /**
   * Test
   * {@link StandaloneMybatisTransactionContextFactory#openTransactionContext(CommandContext)}.
   * <ul>
   *   <li>Then return {@link StandaloneMybatisTransactionContext}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StandaloneMybatisTransactionContextFactory#openTransactionContext(CommandContext)}
   */
  @Test
  public void testOpenTransactionContext_thenReturnStandaloneMybatisTransactionContext() {
    // Arrange and Act
    TransactionContext actualOpenTransactionContextResult = (new StandaloneMybatisTransactionContextFactory())
        .openTransactionContext(null);

    // Assert
    assertTrue(actualOpenTransactionContextResult instanceof StandaloneMybatisTransactionContext);
    assertNull(((StandaloneMybatisTransactionContext) actualOpenTransactionContextResult).stateTransactionListeners);
    assertNull(((StandaloneMybatisTransactionContext) actualOpenTransactionContextResult).commandContext);
  }
}
