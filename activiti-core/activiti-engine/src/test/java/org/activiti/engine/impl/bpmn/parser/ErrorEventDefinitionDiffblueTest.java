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
package org.activiti.engine.impl.bpmn.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ErrorEventDefinitionDiffblueTest {
  @InjectMocks
  private ErrorEventDefinition errorEventDefinition;

  @InjectMocks
  private String string;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ErrorEventDefinition#ErrorEventDefinition(String)}
   *   <li>{@link ErrorEventDefinition#setErrorCode(String)}
   *   <li>{@link ErrorEventDefinition#setPrecedence(Integer)}
   *   <li>{@link ErrorEventDefinition#getErrorCode()}
   *   <li>{@link ErrorEventDefinition#getHandlerActivityId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ErrorEventDefinition actualErrorEventDefinition = new ErrorEventDefinition("42");
    actualErrorEventDefinition.setErrorCode("An error occurred");
    actualErrorEventDefinition.setPrecedence(1);
    String actualErrorCode = actualErrorEventDefinition.getErrorCode();

    // Assert that nothing has changed
    assertEquals("42", actualErrorEventDefinition.getHandlerActivityId());
    assertEquals("An error occurred", actualErrorCode);
    assertEquals(1, actualErrorEventDefinition.precedence.intValue());
  }

  /**
   * Test {@link ErrorEventDefinition#getPrecedence()}.
   * <ul>
   *   <li>Then return intValue is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorEventDefinition#getPrecedence()}
   */
  @Test
  public void testGetPrecedence_thenReturnIntValueIsOne() {
    // Arrange
    ErrorEventDefinition errorEventDefinition = new ErrorEventDefinition("42");
    errorEventDefinition.setErrorCode("foo");

    // Act and Assert
    assertEquals(1, errorEventDefinition.getPrecedence().intValue());
  }

  /**
   * Test {@link ErrorEventDefinition#getPrecedence()}.
   * <ul>
   *   <li>Then return intValue is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorEventDefinition#getPrecedence()}
   */
  @Test
  public void testGetPrecedence_thenReturnIntValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new ErrorEventDefinition("42")).getPrecedence().intValue());
  }

  /**
   * Test {@link ErrorEventDefinition#catches(String)}.
   * <ul>
   *   <li>When {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorEventDefinition#catches(String)}
   */
  @Test
  public void testCatches_whenAnErrorOccurred() {
    // Arrange, Act and Assert
    assertTrue(errorEventDefinition.catches("An error occurred"));
  }

  /**
   * Test {@link ErrorEventDefinition#catches(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorEventDefinition#catches(String)}
   */
  @Test
  public void testCatches_whenNull() {
    // Arrange, Act and Assert
    assertTrue(errorEventDefinition.catches(null));
  }
}
