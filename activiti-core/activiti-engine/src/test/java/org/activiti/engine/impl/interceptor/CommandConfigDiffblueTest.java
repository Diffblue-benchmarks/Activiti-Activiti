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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.cfg.TransactionPropagation;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CommandConfigDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link CommandConfig#CommandConfig(boolean, TransactionPropagation)}
   *   <li>{@link CommandConfig#getTransactionPropagation()}
   *   <li>{@link CommandConfig#isContextReusePossible()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CommandConfig.<init>(boolean, TransactionPropagation)",
    "TransactionPropagation CommandConfig.getTransactionPropagation()",
    "boolean CommandConfig.isContextReusePossible()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    CommandConfig actualCommandConfig = new CommandConfig(true, TransactionPropagation.REQUIRED);
    TransactionPropagation actualTransactionPropagation =
        actualCommandConfig.getTransactionPropagation();

    // Assert
    assertEquals(TransactionPropagation.REQUIRED, actualTransactionPropagation);
    assertTrue(actualCommandConfig.isContextReusePossible());
  }

  /**
   * Test {@link CommandConfig#CommandConfig()}.
   *
   * <p>Method under test: {@link CommandConfig#CommandConfig()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandConfig.<init>()"})
  public void testNewCommandConfig() {
    // Arrange and Act
    CommandConfig actualCommandConfig = new CommandConfig();

    // Assert
    assertEquals(TransactionPropagation.REQUIRED, actualCommandConfig.getTransactionPropagation());
    assertTrue(actualCommandConfig.isContextReusePossible());
  }

  /**
   * Test {@link CommandConfig#CommandConfig(boolean)}.
   *
   * <p>Method under test: {@link CommandConfig#CommandConfig(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandConfig.<init>(boolean)"})
  public void testNewCommandConfig2() {
    // Arrange and Act
    CommandConfig actualCommandConfig = new CommandConfig(true);

    // Assert
    assertEquals(TransactionPropagation.REQUIRED, actualCommandConfig.getTransactionPropagation());
    assertTrue(actualCommandConfig.isContextReusePossible());
  }

  /**
   * Test {@link CommandConfig#CommandConfig(CommandConfig)}.
   *
   * <ul>
   *   <li>Then return TransactionPropagation is {@code REQUIRED}.
   * </ul>
   *
   * <p>Method under test: {@link CommandConfig#CommandConfig(CommandConfig)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CommandConfig.<init>(CommandConfig)"})
  public void testNewCommandConfig_thenReturnTransactionPropagationIsRequired() {
    // Arrange and Act
    CommandConfig actualCommandConfig = new CommandConfig(new CommandConfig());

    // Assert
    assertEquals(TransactionPropagation.REQUIRED, actualCommandConfig.getTransactionPropagation());
    assertTrue(actualCommandConfig.isContextReusePossible());
  }

  /**
   * Test {@link CommandConfig#setContextReusePossible(boolean)}.
   *
   * <p>Method under test: {@link CommandConfig#setContextReusePossible(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CommandConfig CommandConfig.setContextReusePossible(boolean)"})
  public void testSetContextReusePossible() {
    // Arrange and Act
    CommandConfig actualSetContextReusePossibleResult =
        new CommandConfig().setContextReusePossible(true);

    // Assert
    assertEquals(
        TransactionPropagation.REQUIRED,
        actualSetContextReusePossibleResult.getTransactionPropagation());
    assertTrue(actualSetContextReusePossibleResult.isContextReusePossible());
  }

  /**
   * Test {@link CommandConfig#transactionRequired()}.
   *
   * <p>Method under test: {@link CommandConfig#transactionRequired()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CommandConfig CommandConfig.transactionRequired()"})
  public void testTransactionRequired() {
    // Arrange and Act
    CommandConfig actualTransactionRequiredResult = new CommandConfig().transactionRequired();

    // Assert
    assertEquals(
        TransactionPropagation.REQUIRED,
        actualTransactionRequiredResult.getTransactionPropagation());
    assertTrue(actualTransactionRequiredResult.isContextReusePossible());
  }

  /**
   * Test {@link CommandConfig#transactionRequiresNew()}.
   *
   * <p>Method under test: {@link CommandConfig#transactionRequiresNew()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CommandConfig CommandConfig.transactionRequiresNew()"})
  public void testTransactionRequiresNew() {
    // Arrange and Act
    CommandConfig actualTransactionRequiresNewResult = new CommandConfig().transactionRequiresNew();

    // Assert
    assertEquals(
        TransactionPropagation.REQUIRES_NEW,
        actualTransactionRequiresNewResult.getTransactionPropagation());
    assertFalse(actualTransactionRequiresNewResult.isContextReusePossible());
  }

  /**
   * Test {@link CommandConfig#transactionNotSupported()}.
   *
   * <p>Method under test: {@link CommandConfig#transactionNotSupported()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CommandConfig CommandConfig.transactionNotSupported()"})
  public void testTransactionNotSupported() {
    // Arrange and Act
    CommandConfig actualTransactionNotSupportedResult =
        new CommandConfig().transactionNotSupported();

    // Assert
    assertEquals(
        TransactionPropagation.NOT_SUPPORTED,
        actualTransactionNotSupportedResult.getTransactionPropagation());
    assertFalse(actualTransactionNotSupportedResult.isContextReusePossible());
  }
}
