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
package org.activiti.engine.impl.interceptor;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.cfg.TransactionContextFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TransactionContextInterceptorDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TransactionContextInterceptor#TransactionContextInterceptor()}
   *   <li>{@link TransactionContextInterceptor#setTransactionContextFactory(TransactionContextFactory)}
   *   <li>{@link TransactionContextInterceptor#getTransactionContextFactory()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void TransactionContextInterceptor.<init>()",
      "void TransactionContextInterceptor.<init>(TransactionContextFactory)",
      "TransactionContextFactory TransactionContextInterceptor.getTransactionContextFactory()",
      "void TransactionContextInterceptor.setTransactionContextFactory(TransactionContextFactory)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    TransactionContextInterceptor actualTransactionContextInterceptor = new TransactionContextInterceptor();
    TransactionContextFactory transactionContextFactory = mock(TransactionContextFactory.class);
    actualTransactionContextInterceptor.setTransactionContextFactory(transactionContextFactory);
    TransactionContextFactory actualTransactionContextFactory = actualTransactionContextInterceptor
        .getTransactionContextFactory();

    // Assert
    assertNull(actualTransactionContextInterceptor.getNext());
    assertSame(transactionContextFactory, actualTransactionContextFactory);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@link TransactionContextFactory}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TransactionContextInterceptor#TransactionContextInterceptor(TransactionContextFactory)}
   *   <li>{@link TransactionContextInterceptor#setTransactionContextFactory(TransactionContextFactory)}
   *   <li>{@link TransactionContextInterceptor#getTransactionContextFactory()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void TransactionContextInterceptor.<init>()",
      "void TransactionContextInterceptor.<init>(TransactionContextFactory)",
      "TransactionContextFactory TransactionContextInterceptor.getTransactionContextFactory()",
      "void TransactionContextInterceptor.setTransactionContextFactory(TransactionContextFactory)"})
  public void testGettersAndSetters_whenTransactionContextFactory() {
    // Arrange and Act
    TransactionContextInterceptor actualTransactionContextInterceptor = new TransactionContextInterceptor(
        mock(TransactionContextFactory.class));
    TransactionContextFactory transactionContextFactory = mock(TransactionContextFactory.class);
    actualTransactionContextInterceptor.setTransactionContextFactory(transactionContextFactory);
    TransactionContextFactory actualTransactionContextFactory = actualTransactionContextInterceptor
        .getTransactionContextFactory();

    // Assert
    assertNull(actualTransactionContextInterceptor.getNext());
    assertSame(transactionContextFactory, actualTransactionContextFactory);
  }
}
