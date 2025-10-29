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
package org.activiti.core.el.juel.tree.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.EnumSet;
import java.util.List;
import org.activiti.core.el.juel.tree.FunctionNode;
import org.activiti.core.el.juel.tree.Tree;
import org.activiti.core.el.juel.tree.TreeBuilderException;
import org.activiti.core.el.juel.tree.impl.ast.AstText;
import org.junit.jupiter.api.Test;

class BuilderDiffblueTest {
  /**
   * Method under test: {@link Builder#isEnabled(Builder.Feature)}
   */
  @Test
  void testIsEnabled() {
    // Arrange, Act and Assert
    assertFalse((new Builder()).isEnabled(Builder.Feature.METHOD_INVOCATIONS));
    assertTrue((new Builder(Builder.Feature.METHOD_INVOCATIONS)).isEnabled(Builder.Feature.METHOD_INVOCATIONS));
  }

  /**
   * Method under test: {@link Builder#build(String)}
   */
  @Test
  void testBuild() throws TreeBuilderException {
    // Arrange and Act
    Tree actualBuildResult = (new Builder()).build("Expression");

    // Assert
    Iterable<FunctionNode> functionNodes = actualBuildResult.getFunctionNodes();
    assertTrue(functionNodes instanceof List);
    assertTrue(actualBuildResult.getRoot() instanceof AstText);
    assertFalse(actualBuildResult.isDeferred());
    assertSame(functionNodes, actualBuildResult.getIdentifierNodes());
  }

  /**
   * Method under test: {@link Builder#createParser(String)}
   */
  @Test
  void testCreateParser() {
    // Arrange
    Builder builder = new Builder();

    // Act
    Parser actualCreateParserResult = builder.createParser("Expression");

    // Assert
    Scanner scanner = actualCreateParserResult.scanner;
    assertEquals("", scanner.builder.toString());
    assertEquals("Expression", scanner.getInput());
    assertNull(actualCreateParserResult.getToken());
    assertNull(scanner.getToken());
    assertEquals(0, scanner.getPosition());
    assertFalse(scanner.isEval());
    EnumSet<Builder.Feature> featureSet = actualCreateParserResult.context.features;
    assertTrue(featureSet.isEmpty());
    List<FunctionNode> functions = actualCreateParserResult.getFunctions();
    assertTrue(functions.isEmpty());
    assertTrue(actualCreateParserResult.extensions.isEmpty());
    assertSame(functions, actualCreateParserResult.getIdentifiers());
    assertSame(builder.features, featureSet);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Builder#equals(Object)}
   *   <li>{@link Builder#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Builder builder = new Builder();
    Builder builder2 = new Builder();

    // Act and Assert
    assertEquals(builder, builder2);
    int expectedHashCodeResult = builder.hashCode();
    assertEquals(expectedHashCodeResult, builder2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Builder#equals(Object)}
   *   <li>{@link Builder#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Builder builder = new Builder();

    // Act and Assert
    assertEquals(builder, builder);
    int expectedHashCodeResult = builder.hashCode();
    assertEquals(expectedHashCodeResult, builder.hashCode());
  }

  /**
   * Method under test: {@link Builder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Builder builder = new Builder(Builder.Feature.METHOD_INVOCATIONS);

    // Act and Assert
    assertNotEquals(builder, new Builder());
  }

  /**
   * Method under test: {@link Builder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Builder(), null);
  }

  /**
   * Method under test: {@link Builder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Builder(), "Different type to Builder");
  }

  /**
   * Method under test: {@link Builder#Builder()}
   */
  @Test
  void testNewBuilder() {
    // Arrange, Act and Assert
    assertTrue((new Builder()).features.isEmpty());
    assertTrue((new Builder(null)).features.isEmpty());
    assertTrue((new Builder()).features.isEmpty());
  }

  /**
   * Method under test: {@link Builder#Builder(Builder.Feature[])}
   */
  @Test
  void testNewBuilder2() {
    // Arrange, Act and Assert
    EnumSet<Builder.Feature> featureSet = (new Builder(Builder.Feature.METHOD_INVOCATIONS)).features;
    assertEquals(1, featureSet.size());
    assertTrue(featureSet.contains(Builder.Feature.METHOD_INVOCATIONS));
  }

  /**
   * Method under test: {@link Builder#Builder(Builder.Feature[])}
   */
  @Test
  void testNewBuilder3() {
    // Arrange, Act and Assert
    EnumSet<Builder.Feature> featureSet = (new Builder(Builder.Feature.METHOD_INVOCATIONS,
        Builder.Feature.METHOD_INVOCATIONS)).features;
    assertEquals(1, featureSet.size());
    assertTrue(featureSet.contains(Builder.Feature.METHOD_INVOCATIONS));
  }
}
