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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.activiti.engine.impl.cfg.TransactionPropagation;
import org.junit.Test;

public class CommandConfigDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CommandConfig#CommandConfig(boolean, TransactionPropagation)}
   *   <li>{@link CommandConfig#getTransactionPropagation()}
   *   <li>{@link CommandConfig#isContextReusePossible()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    CommandConfig actualCommandConfig = new CommandConfig(true, TransactionPropagation.REQUIRED);
    TransactionPropagation actualTransactionPropagation = actualCommandConfig.getTransactionPropagation();

    // Assert
    assertEquals(TransactionPropagation.REQUIRED, actualTransactionPropagation);
    assertTrue(actualCommandConfig.isContextReusePossible());
  }

  /**
   * Test {@link CommandConfig#CommandConfig()}.
   * <p>
   * Method under test: {@link CommandConfig#CommandConfig()}
   */
  @Test
  public void testNewCommandConfig() {
    // Arrange and Act
    CommandConfig actualCommandConfig = new CommandConfig();

    // Assert
    assertEquals(TransactionPropagation.REQUIRED, actualCommandConfig.getTransactionPropagation());
    assertTrue(actualCommandConfig.isContextReusePossible());
  }

  /**
   * Test {@link CommandConfig#CommandConfig(boolean)}.
   * <p>
   * Method under test: {@link CommandConfig#CommandConfig(boolean)}
   */
  @Test
  public void testNewCommandConfig2() {
    // Arrange and Act
    CommandConfig actualCommandConfig = new CommandConfig(true);

    // Assert
    assertEquals(TransactionPropagation.REQUIRED, actualCommandConfig.getTransactionPropagation());
    assertTrue(actualCommandConfig.isContextReusePossible());
  }

  /**
   * Test {@link CommandConfig#CommandConfig(CommandConfig)}.
   * <ul>
   *   <li>Then return TransactionPropagation is {@code REQUIRED}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommandConfig#CommandConfig(CommandConfig)}
   */
  @Test
  public void testNewCommandConfig_thenReturnTransactionPropagationIsRequired() {
    // Arrange and Act
    CommandConfig actualCommandConfig = new CommandConfig(new CommandConfig());

    // Assert
    assertEquals(TransactionPropagation.REQUIRED, actualCommandConfig.getTransactionPropagation());
    assertTrue(actualCommandConfig.isContextReusePossible());
  }

  /**
   * Test {@link CommandConfig#setContextReusePossible(boolean)}.
   * <p>
   * Method under test: {@link CommandConfig#setContextReusePossible(boolean)}
   */
  @Test
  public void testSetContextReusePossible() {
    // Arrange and Act
    CommandConfig actualSetContextReusePossibleResult = (new CommandConfig()).setContextReusePossible(true);

    // Assert
    assertEquals(TransactionPropagation.REQUIRED, actualSetContextReusePossibleResult.getTransactionPropagation());
    assertTrue(actualSetContextReusePossibleResult.isContextReusePossible());
  }

  /**
   * Test {@link CommandConfig#transactionRequired()}.
   * <p>
   * Method under test: {@link CommandConfig#transactionRequired()}
   */
  @Test
  public void testTransactionRequired() {
    // Arrange and Act
    CommandConfig actualTransactionRequiredResult = (new CommandConfig()).transactionRequired();

    // Assert
    assertEquals(TransactionPropagation.REQUIRED, actualTransactionRequiredResult.getTransactionPropagation());
    assertTrue(actualTransactionRequiredResult.isContextReusePossible());
  }

  /**
   * Test {@link CommandConfig#transactionRequiresNew()}.
   * <p>
   * Method under test: {@link CommandConfig#transactionRequiresNew()}
   */
  @Test
  public void testTransactionRequiresNew() {
    // Arrange and Act
    CommandConfig actualTransactionRequiresNewResult = (new CommandConfig()).transactionRequiresNew();

    // Assert
    assertEquals(TransactionPropagation.REQUIRES_NEW, actualTransactionRequiresNewResult.getTransactionPropagation());
    assertFalse(actualTransactionRequiresNewResult.isContextReusePossible());
  }

  /**
   * Test {@link CommandConfig#transactionNotSupported()}.
   * <p>
   * Method under test: {@link CommandConfig#transactionNotSupported()}
   */
  @Test
  public void testTransactionNotSupported() {
    // Arrange and Act
    CommandConfig actualTransactionNotSupportedResult = (new CommandConfig()).transactionNotSupported();

    // Assert
    assertEquals(TransactionPropagation.NOT_SUPPORTED, actualTransactionNotSupportedResult.getTransactionPropagation());
    assertFalse(actualTransactionNotSupportedResult.isContextReusePossible());
  }
}
