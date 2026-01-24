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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.EnumSet;
import java.util.List;
import org.activiti.core.el.juel.tree.FunctionNode;
import org.activiti.core.el.juel.tree.Tree;
import org.activiti.core.el.juel.tree.TreeBuilderException;
import org.activiti.core.el.juel.tree.impl.Builder.Feature;
import org.activiti.core.el.juel.tree.impl.ast.AstText;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BuilderDiffblueTest {
  /**
   * Test {@link Builder#Builder(Feature[])}.
   *
   * <ul>
   *   <li>Then return {@link Builder#features} Empty.
   * </ul>
   *
   * <p>Method under test: {@link Builder#Builder(Feature[])}
   */
  @Test
  @DisplayName("Test new Builder(Feature[]); then return features Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void Builder.<init>(Feature[])"})
  void testNewBuilder_thenReturnFeaturesEmpty() {
    // Arrange and Act
    Builder actualBuilder = new Builder(new Feature[] {});

    // Assert
    assertTrue(actualBuilder.features.isEmpty());
  }

  /**
   * Test {@link Builder#Builder(Feature[])}.
   *
   * <ul>
   *   <li>When {@code METHOD_INVOCATIONS} and {@code METHOD_INVOCATIONS}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#Builder(Feature[])}
   */
  @Test
  @DisplayName("Test new Builder(Feature[]); when 'METHOD_INVOCATIONS' and 'METHOD_INVOCATIONS'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void Builder.<init>(Feature[])"})
  void testNewBuilder_whenMethodInvocationsAndMethodInvocations() {
    // Arrange, Act and Assert
    EnumSet<Feature> featureSet =
        new Builder(Feature.METHOD_INVOCATIONS, Feature.METHOD_INVOCATIONS).features;
    assertEquals(1, featureSet.size());
    assertTrue(featureSet.contains(Feature.METHOD_INVOCATIONS));
  }

  /**
   * Test {@link Builder#Builder(Feature[])}.
   *
   * <ul>
   *   <li>When {@code METHOD_INVOCATIONS}.
   *   <li>Then return {@link Builder#features} size is one.
   * </ul>
   *
   * <p>Method under test: {@link Builder#Builder(Feature[])}
   */
  @Test
  @DisplayName(
      "Test new Builder(Feature[]); when 'METHOD_INVOCATIONS'; then return features size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void Builder.<init>(Feature[])"})
  void testNewBuilder_whenMethodInvocations_thenReturnFeaturesSizeIsOne() {
    // Arrange, Act and Assert
    EnumSet<Feature> featureSet = new Builder(Feature.METHOD_INVOCATIONS).features;
    assertEquals(1, featureSet.size());
    assertTrue(featureSet.contains(Feature.METHOD_INVOCATIONS));
  }

  /**
   * Test {@link Builder#Builder(Feature[])}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@link Builder#features} Empty.
   * </ul>
   *
   * <p>Method under test: {@link Builder#Builder(Feature[])}
   */
  @Test
  @DisplayName("Test new Builder(Feature[]); when 'null'; then return features Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void Builder.<init>(Feature[])"})
  void testNewBuilder_whenNull_thenReturnFeaturesEmpty() {
    // Arrange, Act and Assert
    assertTrue(new Builder(null).features.isEmpty());
  }

  /**
   * Test {@link Builder#isEnabled(Feature)}.
   *
   * <ul>
   *   <li>Given {@link Builder#Builder(Feature[])} with features is {@code METHOD_INVOCATIONS}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#isEnabled(Feature)}
   */
  @Test
  @DisplayName(
      "Test isEnabled(Feature); given Builder(Feature[]) with features is 'METHOD_INVOCATIONS'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Builder.isEnabled(Feature)"})
  void testIsEnabled_givenBuilderWithFeaturesIsMethodInvocations_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(new Builder(Feature.METHOD_INVOCATIONS).isEnabled(Feature.METHOD_INVOCATIONS));
  }

  /**
   * Test {@link Builder#isEnabled(Feature)}.
   *
   * <ul>
   *   <li>Given {@link Builder#Builder()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link Builder#isEnabled(Feature)}
   */
  @Test
  @DisplayName("Test isEnabled(Feature); given Builder(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Builder.isEnabled(Feature)"})
  void testIsEnabled_givenBuilder_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new Builder().isEnabled(Feature.METHOD_INVOCATIONS));
  }

  /**
   * Test {@link Builder#build(String)}.
   *
   * <p>Method under test: {@link Builder#build(String)}
   */
  @Test
  @DisplayName("Test build(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tree Builder.build(String)"})
  void testBuild() throws TreeBuilderException {
    // Arrange and Act
    Tree actualTree = new Builder().build("Expression");

    // Assert
    Iterable<FunctionNode> functionNodes = actualTree.getFunctionNodes();
    assertTrue(functionNodes instanceof List);
    assertTrue(actualTree.getRoot() instanceof AstText);
    assertFalse(actualTree.isDeferred());
    assertSame(functionNodes, actualTree.getIdentifierNodes());
  }

  /**
   * Test {@link Builder#createParser(String)}.
   *
   * <p>Method under test: {@link Builder#createParser(String)}
   */
  @Test
  @DisplayName("Test createParser(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Parser Builder.createParser(String)"})
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
    EnumSet<Feature> featureSet = actualCreateParserResult.context.features;
    assertTrue(featureSet.isEmpty());
    List<FunctionNode> functions = actualCreateParserResult.getFunctions();
    assertTrue(functions.isEmpty());
    assertTrue(actualCreateParserResult.extensions.isEmpty());
    assertSame(functions, actualCreateParserResult.getIdentifiers());
    assertSame(builder.features, featureSet);
  }

  /**
   * Test {@link Builder#equals(Object)}, and {@link Builder#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Builder#equals(Object)}
   *   <li>{@link Builder#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Builder.equals(Object)", "int Builder.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Builder builder = new Builder();
    Builder builder2 = new Builder();

    // Act and Assert
    assertEquals(builder, builder2);
    assertEquals(builder.hashCode(), builder2.hashCode());
  }

  /**
   * Test {@link Builder#equals(Object)}, and {@link Builder#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Builder#equals(Object)}
   *   <li>{@link Builder#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Builder.equals(Object)", "int Builder.hashCode()"})
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
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Builder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Builder.equals(Object)", "int Builder.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Builder builder = new Builder(Feature.METHOD_INVOCATIONS);

    // Act and Assert
    assertNotEquals(builder, new Builder());
  }

  /**
   * Test {@link Builder#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Builder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Builder.equals(Object)", "int Builder.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Builder(), null);
  }

  /**
   * Test {@link Builder#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Builder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Builder.equals(Object)", "int Builder.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Builder(), "Different type to Builder");
  }
}
