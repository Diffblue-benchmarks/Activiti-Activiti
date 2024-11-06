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
import jakarta.transaction.TransactionManager;
import org.junit.Test;

public class AbstractCommandInterceptorDiffblueTest {
  /**
   * Test {@link AbstractCommandInterceptor#getNext()}.
   * <p>
   * Method under test: {@link AbstractCommandInterceptor#getNext()}
   */
  @Test
  public void testGetNext() {
    // Arrange, Act and Assert
    assertNull((new CommandContextInterceptor()).getNext());
  }

  /**
   * Test {@link AbstractCommandInterceptor#setNext(CommandInterceptor)}.
   * <p>
   * Method under test:
   * {@link AbstractCommandInterceptor#setNext(CommandInterceptor)}
   */
  @Test
  public void testSetNext() {
    // Arrange
    CommandContextInterceptor commandContextInterceptor = new CommandContextInterceptor();
    JtaRetryInterceptor next = new JtaRetryInterceptor(mock(TransactionManager.class));

    // Act
    commandContextInterceptor.setNext(next);

    // Assert
    assertSame(next, commandContextInterceptor.getNext());
  }

  /**
   * Test {@link AbstractCommandInterceptor#setNext(CommandInterceptor)}.
   * <ul>
   *   <li>Then {@link CommandContextInterceptor#CommandContextInterceptor()} Next
   * is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractCommandInterceptor#setNext(CommandInterceptor)}
   */
  @Test
  public void testSetNext_thenCommandContextInterceptorNextIsNull() {
    // Arrange
    CommandContextInterceptor commandContextInterceptor = new CommandContextInterceptor();
    CommandContextInterceptor next = new CommandContextInterceptor();

    // Act
    commandContextInterceptor.setNext(next);

    // Assert
    assertNull(next.getNext());
  }
}
