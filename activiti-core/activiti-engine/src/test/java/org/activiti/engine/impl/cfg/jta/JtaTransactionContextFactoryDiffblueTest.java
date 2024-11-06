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
package org.activiti.engine.impl.cfg.jta;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import jakarta.transaction.TransactionManager;
import org.activiti.engine.impl.cfg.TransactionContext;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.junit.Test;

public class JtaTransactionContextFactoryDiffblueTest {
  /**
   * Test
   * {@link JtaTransactionContextFactory#openTransactionContext(CommandContext)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link JtaTransactionContext}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JtaTransactionContextFactory#openTransactionContext(CommandContext)}
   */
  @Test
  public void testOpenTransactionContext_whenNull_thenReturnJtaTransactionContext() {
    // Arrange and Act
    TransactionContext actualOpenTransactionContextResult = (new JtaTransactionContextFactory(
        mock(TransactionManager.class))).openTransactionContext(null);

    // Assert
    assertTrue(actualOpenTransactionContextResult instanceof JtaTransactionContext);
    assertNull(((JtaTransactionContext) actualOpenTransactionContextResult).getTransaction());
  }
}
