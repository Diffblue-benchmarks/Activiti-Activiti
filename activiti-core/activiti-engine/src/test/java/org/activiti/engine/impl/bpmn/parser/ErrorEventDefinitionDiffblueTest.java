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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ErrorEventDefinitionDiffblueTest {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ErrorEventDefinition.<init>(String)", "String ErrorEventDefinition.getErrorCode()",
      "String ErrorEventDefinition.getHandlerActivityId()", "void ErrorEventDefinition.setErrorCode(String)",
      "void ErrorEventDefinition.setPrecedence(Integer)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    ErrorEventDefinition actualErrorEventDefinition = new ErrorEventDefinition("42");
    actualErrorEventDefinition.setErrorCode("An error occurred");
    actualErrorEventDefinition.setPrecedence(1);
    String actualErrorCode = actualErrorEventDefinition.getErrorCode();

    // Assert
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Integer ErrorEventDefinition.getPrecedence()"})
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Integer ErrorEventDefinition.getPrecedence()"})
  public void testGetPrecedence_thenReturnIntValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new ErrorEventDefinition("42")).getPrecedence().intValue());
  }

  /**
   * Test {@link ErrorEventDefinition#catches(String)}.
   * <p>
   * Method under test: {@link ErrorEventDefinition#catches(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ErrorEventDefinition.catches(String)"})
  public void testCatches() {
    // Arrange
    ErrorEventDefinition errorEventDefinition = new ErrorEventDefinition("42");
    errorEventDefinition.setErrorCode("An error occurred");

    // Act and Assert
    assertTrue(errorEventDefinition.catches("An error occurred"));
  }

  /**
   * Test {@link ErrorEventDefinition#catches(String)}.
   * <ul>
   *   <li>Given {@link ErrorEventDefinition#ErrorEventDefinition(String)} with handlerActivityId is {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorEventDefinition#catches(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ErrorEventDefinition.catches(String)"})
  public void testCatches_givenErrorEventDefinitionWithHandlerActivityIdIs42_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new ErrorEventDefinition("42")).catches("An error occurred"));
  }

  /**
   * Test {@link ErrorEventDefinition#catches(String)}.
   * <ul>
   *   <li>Given {@link ErrorEventDefinition#ErrorEventDefinition(String)} with handlerActivityId is {@code 42}.</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorEventDefinition#catches(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ErrorEventDefinition.catches(String)"})
  public void testCatches_givenErrorEventDefinitionWithHandlerActivityIdIs42_whenNull() {
    // Arrange, Act and Assert
    assertTrue((new ErrorEventDefinition("42")).catches(null));
  }

  /**
   * Test {@link ErrorEventDefinition#catches(String)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorEventDefinition#catches(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ErrorEventDefinition.catches(String)"})
  public void testCatches_thenReturnFalse() {
    // Arrange
    ErrorEventDefinition errorEventDefinition = new ErrorEventDefinition("42");
    errorEventDefinition.setErrorCode("Error Code");

    // Act and Assert
    assertFalse(errorEventDefinition.catches("An error occurred"));
  }
}
