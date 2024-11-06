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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BuilderDiffblueTest {
  /**
   * Test {@link Builder#Builder()}.
   * <p>
   * Method under test: {@link Builder#Builder()}
   */
  @Test
  @DisplayName("Test new Builder()")
  void testNewBuilder() {
    // Arrange, Act and Assert
    assertTrue((new Builder()).features.isEmpty());
  }

  /**
   * Test {@link Builder#Builder(Feature[])}.
   * <ul>
   *   <li>Then return {@link Builder#features} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#Builder(Builder.Feature[])}
   */
  @Test
  @DisplayName("Test new Builder(Feature[]); then return features Empty")
  void testNewBuilder_thenReturnFeaturesEmpty() {
    // Arrange, Act and Assert
    assertTrue((new Builder()).features.isEmpty());
  }

  /**
   * Test {@link Builder#Builder(Feature[])}.
   * <ul>
   *   <li>When {@code METHOD_INVOCATIONS} and {@code METHOD_INVOCATIONS}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#Builder(Builder.Feature[])}
   */
  @Test
  @DisplayName("Test new Builder(Feature[]); when 'METHOD_INVOCATIONS' and 'METHOD_INVOCATIONS'")
  void testNewBuilder_whenMethodInvocationsAndMethodInvocations() {
    // Arrange, Act and Assert
    EnumSet<Builder.Feature> featureSet = (new Builder(Builder.Feature.METHOD_INVOCATIONS,
        Builder.Feature.METHOD_INVOCATIONS)).features;
    assertEquals(1, featureSet.size());
    assertTrue(featureSet.contains(Builder.Feature.METHOD_INVOCATIONS));
  }

  /**
   * Test {@link Builder#Builder(Feature[])}.
   * <ul>
   *   <li>When {@code METHOD_INVOCATIONS}.</li>
   *   <li>Then return {@link Builder#features} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#Builder(Builder.Feature[])}
   */
  @Test
  @DisplayName("Test new Builder(Feature[]); when 'METHOD_INVOCATIONS'; then return features size is one")
  void testNewBuilder_whenMethodInvocations_thenReturnFeaturesSizeIsOne() {
    // Arrange, Act and Assert
    EnumSet<Builder.Feature> featureSet = (new Builder(Builder.Feature.METHOD_INVOCATIONS)).features;
    assertEquals(1, featureSet.size());
    assertTrue(featureSet.contains(Builder.Feature.METHOD_INVOCATIONS));
  }

  /**
   * Test {@link Builder#Builder(Feature[])}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link Builder#features} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#Builder(Builder.Feature[])}
   */
  @Test
  @DisplayName("Test new Builder(Feature[]); when 'null'; then return features Empty")
  void testNewBuilder_whenNull_thenReturnFeaturesEmpty() {
    // Arrange, Act and Assert
    assertTrue((new Builder(null)).features.isEmpty());
  }

  /**
   * Test {@link Builder#isEnabled(Feature)}.
   * <ul>
   *   <li>Given {@link Builder#Builder(Feature[])} with features is
   * {@code METHOD_INVOCATIONS}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#isEnabled(Builder.Feature)}
   */
  @Test
  @DisplayName("Test isEnabled(Feature); given Builder(Feature[]) with features is 'METHOD_INVOCATIONS'; then return 'true'")
  void testIsEnabled_givenBuilderWithFeaturesIsMethodInvocations_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new Builder(Builder.Feature.METHOD_INVOCATIONS)).isEnabled(Builder.Feature.METHOD_INVOCATIONS));
  }

  /**
   * Test {@link Builder#isEnabled(Feature)}.
   * <ul>
   *   <li>Given {@link Builder#Builder()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#isEnabled(Builder.Feature)}
   */
  @Test
  @DisplayName("Test isEnabled(Feature); given Builder(); then return 'false'")
  void testIsEnabled_givenBuilder_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new Builder()).isEnabled(Builder.Feature.METHOD_INVOCATIONS));
  }

  /**
   * Test {@link Builder#build(String)}.
   * <p>
   * Method under test: {@link Builder#build(String)}
   */
  @Test
  @DisplayName("Test build(String)")
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
   * Test {@link Builder#createParser(String)}.
   * <p>
   * Method under test: {@link Builder#createParser(String)}
   */
  @Test
  @DisplayName("Test createParser(String)")
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
   * Test {@link Builder#equals(Object)}, and {@link Builder#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Builder#equals(Object)}
   *   <li>{@link Builder#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
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
   * Test {@link Builder#equals(Object)}, and {@link Builder#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Builder#equals(Object)}
   *   <li>{@link Builder#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Builder builder = new Builder();

    // Act and Assert
    assertEquals(builder, builder);
    int expectedHashCodeResult = builder.hashCode();
    assertEquals(expectedHashCodeResult, builder.hashCode());
  }

  /**
   * Test {@link Builder#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Builder builder = new Builder(Builder.Feature.METHOD_INVOCATIONS);

    // Act and Assert
    assertNotEquals(builder, new Builder());
  }

  /**
   * Test {@link Builder#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Builder(), null);
  }

  /**
   * Test {@link Builder#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Builder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Builder(), "Different type to Builder");
  }
}
