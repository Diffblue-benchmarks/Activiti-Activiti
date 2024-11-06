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
package org.activiti.engine.impl.variable;

import static org.junit.Assert.assertFalse;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;
import org.junit.Test;

public class EntityManagerSessionImplDiffblueTest {
  /**
   * Test {@link EntityManagerSessionImpl#isTransactionActive()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntityManagerSessionImpl#isTransactionActive()}
   */
  @Test
  public void testIsTransactionActive_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(
        (new EntityManagerSessionImpl(new SessionFactoryDelegatingImpl(null), false, true)).isTransactionActive());
  }
}
