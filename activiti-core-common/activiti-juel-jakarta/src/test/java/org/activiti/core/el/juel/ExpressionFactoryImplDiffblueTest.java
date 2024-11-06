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
package org.activiti.core.el.juel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import java.util.List;
import java.util.Properties;
import org.activiti.core.el.juel.ExpressionFactoryImpl.Profile;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.misc.TypeConverterImpl;
import org.activiti.core.el.juel.tree.ExpressionNode;
import org.activiti.core.el.juel.tree.FunctionNode;
import org.activiti.core.el.juel.tree.Tree;
import org.activiti.core.el.juel.tree.TreeBuilder;
import org.activiti.core.el.juel.tree.TreeBuilderException;
import org.activiti.core.el.juel.tree.TreeStore;
import org.activiti.core.el.juel.tree.impl.Builder;
import org.activiti.core.el.juel.tree.impl.Cache;
import org.activiti.core.el.juel.tree.impl.ast.AstText;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExpressionFactoryImplDiffblueTest {
  /**
   * Test
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(TreeStore, TypeConverter)}.
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(TreeStore, TypeConverter)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(TreeStore, TypeConverter)")
  void testNewExpressionFactoryImpl() {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);

    // Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(new TreeStore(builder, new Cache(3)),
        mock(TypeConverter.class));

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test {@link ExpressionFactoryImpl#ExpressionFactoryImpl()}.
   * <p>
   * Method under test: {@link ExpressionFactoryImpl#ExpressionFactoryImpl()}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl()")
  void testNewExpressionFactoryImpl2() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl();

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test {@link ExpressionFactoryImpl#ExpressionFactoryImpl(TreeStore)}.
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(TreeStore)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(TreeStore)")
  void testNewExpressionFactoryImpl3() {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);

    // Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(new TreeStore(builder, new Cache(3)));

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link Properties#Properties()}
   * {@link ExpressionFactoryImpl#PROP_CACHE_SIZE} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(Properties); given '42'; when Properties() PROP_CACHE_SIZE is '42'")
  void testNewExpressionFactoryImpl_given42_whenPropertiesProp_cache_sizeIs42() {
    // Arrange
    Properties properties = new Properties();
    properties.put(ExpressionFactoryImpl.PROP_CACHE_SIZE, "42");

    // Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(properties);

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties, TypeConverter)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link Properties#Properties()}
   * {@link ExpressionFactoryImpl#PROP_CACHE_SIZE} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties, TypeConverter)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(Properties, TypeConverter); given '42'; when Properties() PROP_CACHE_SIZE is '42'")
  void testNewExpressionFactoryImpl_given42_whenPropertiesProp_cache_sizeIs422() {
    // Arrange
    Properties properties = new Properties();
    properties.put(ExpressionFactoryImpl.PROP_CACHE_SIZE, "42");

    // Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(properties,
        mock(TypeConverter.class));

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties)}.
   * <ul>
   *   <li>Given {@link ExpressionFactoryImpl#PROP_METHOD_INVOCATIONS}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(Properties); given PROP_METHOD_INVOCATIONS")
  void testNewExpressionFactoryImpl_givenProp_method_invocations() {
    // Arrange
    Properties properties = new Properties();
    properties.put(ExpressionFactoryImpl.PROP_METHOD_INVOCATIONS, "Properties");

    // Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(properties);

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties, TypeConverter)}.
   * <ul>
   *   <li>Given {@link ExpressionFactoryImpl#PROP_METHOD_INVOCATIONS}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties, TypeConverter)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(Properties, TypeConverter); given PROP_METHOD_INVOCATIONS")
  void testNewExpressionFactoryImpl_givenProp_method_invocations2() {
    // Arrange
    Properties properties = new Properties();
    properties.put(ExpressionFactoryImpl.PROP_METHOD_INVOCATIONS, "Properties");

    // Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(properties,
        mock(TypeConverter.class));

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties)}.
   * <ul>
   *   <li>Given {@link ExpressionFactoryImpl#PROP_VAR_ARGS}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(Properties); given PROP_VAR_ARGS")
  void testNewExpressionFactoryImpl_givenProp_var_args() {
    // Arrange
    Properties properties = new Properties();
    properties.put(ExpressionFactoryImpl.PROP_VAR_ARGS, "Properties");

    // Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(properties);

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties, TypeConverter)}.
   * <ul>
   *   <li>Given {@link ExpressionFactoryImpl#PROP_VAR_ARGS}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties, TypeConverter)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(Properties, TypeConverter); given PROP_VAR_ARGS")
  void testNewExpressionFactoryImpl_givenProp_var_args2() {
    // Arrange
    Properties properties = new Properties();
    properties.put(ExpressionFactoryImpl.PROP_VAR_ARGS, "Properties");

    // Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(properties,
        mock(TypeConverter.class));

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties)}.
   * <ul>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(Properties); then throw ELException")
  void testNewExpressionFactoryImpl_thenThrowELException() {
    // Arrange
    Properties properties = new Properties();
    properties.put(ExpressionFactoryImpl.PROP_CACHE_SIZE, "Properties");

    // Act and Assert
    assertThrows(ELException.class, () -> new ExpressionFactoryImpl(properties));
  }

  /**
   * Test
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties, TypeConverter)}.
   * <ul>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties, TypeConverter)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(Properties, TypeConverter); then throw ELException")
  void testNewExpressionFactoryImpl_thenThrowELException2() {
    // Arrange
    Properties properties = new Properties();
    properties.put(ExpressionFactoryImpl.PROP_CACHE_SIZE, "Properties");

    // Act and Assert
    assertThrows(ELException.class, () -> new ExpressionFactoryImpl(properties, mock(TypeConverter.class)));

  }

  /**
   * Test {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Profile)}.
   * <ul>
   *   <li>When {@code JEE5}.</li>
   *   <li>Then return StreamELResolver is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(ExpressionFactoryImpl.Profile)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(Profile); when 'JEE5'; then return StreamELResolver is 'null'")
  void testNewExpressionFactoryImpl_whenJee5_thenReturnStreamELResolverIsNull() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(ExpressionFactoryImpl.Profile.JEE5);

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Profile, Properties)}.
   * <ul>
   *   <li>When {@code JEE5}.</li>
   *   <li>Then return StreamELResolver is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(ExpressionFactoryImpl.Profile, Properties)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(Profile, Properties); when 'JEE5'; then return StreamELResolver is 'null'")
  void testNewExpressionFactoryImpl_whenJee5_thenReturnStreamELResolverIsNull2() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(ExpressionFactoryImpl.Profile.JEE5,
        new Properties());

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Profile, Properties, TypeConverter)}.
   * <ul>
   *   <li>When {@code JEE5}.</li>
   *   <li>Then return StreamELResolver is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(ExpressionFactoryImpl.Profile, Properties, TypeConverter)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(Profile, Properties, TypeConverter); when 'JEE5'; then return StreamELResolver is 'null'")
  void testNewExpressionFactoryImpl_whenJee5_thenReturnStreamELResolverIsNull3() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(ExpressionFactoryImpl.Profile.JEE5,
        new Properties(), mock(TypeConverter.class));

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Profile)}.
   * <ul>
   *   <li>When {@code JEE6}.</li>
   *   <li>Then return StreamELResolver is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(ExpressionFactoryImpl.Profile)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(Profile); when 'JEE6'; then return StreamELResolver is 'null'")
  void testNewExpressionFactoryImpl_whenJee6_thenReturnStreamELResolverIsNull() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(ExpressionFactoryImpl.Profile.JEE6);

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Profile, Properties)}.
   * <ul>
   *   <li>When {@code JEE6}.</li>
   *   <li>Then return StreamELResolver is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(ExpressionFactoryImpl.Profile, Properties)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(Profile, Properties); when 'JEE6'; then return StreamELResolver is 'null'")
  void testNewExpressionFactoryImpl_whenJee6_thenReturnStreamELResolverIsNull2() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(ExpressionFactoryImpl.Profile.JEE6,
        new Properties());

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Profile, Properties, TypeConverter)}.
   * <ul>
   *   <li>When {@code JEE6}.</li>
   *   <li>Then return StreamELResolver is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(ExpressionFactoryImpl.Profile, Properties, TypeConverter)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(Profile, Properties, TypeConverter); when 'JEE6'; then return StreamELResolver is 'null'")
  void testNewExpressionFactoryImpl_whenJee6_thenReturnStreamELResolverIsNull3() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(ExpressionFactoryImpl.Profile.JEE6,
        new Properties(), mock(TypeConverter.class));

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return StreamELResolver is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(Properties); when 'null'; then return StreamELResolver is 'null'")
  void testNewExpressionFactoryImpl_whenNull_thenReturnStreamELResolverIsNull() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl((Properties) null);

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties, TypeConverter)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return StreamELResolver is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties, TypeConverter)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(Properties, TypeConverter); when 'null'; then return StreamELResolver is 'null'")
  void testNewExpressionFactoryImpl_whenNull_thenReturnStreamELResolverIsNull2() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl((Properties) null,
        mock(TypeConverter.class));

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties)}.
   * <ul>
   *   <li>When {@link Properties#Properties()}.</li>
   *   <li>Then return StreamELResolver is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(Properties); when Properties(); then return StreamELResolver is 'null'")
  void testNewExpressionFactoryImpl_whenProperties_thenReturnStreamELResolverIsNull() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(new Properties());

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties, TypeConverter)}.
   * <ul>
   *   <li>When {@link Properties#Properties()}.</li>
   *   <li>Then return StreamELResolver is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties, TypeConverter)}
   */
  @Test
  @DisplayName("Test new ExpressionFactoryImpl(Properties, TypeConverter); when Properties(); then return StreamELResolver is 'null'")
  void testNewExpressionFactoryImpl_whenProperties_thenReturnStreamELResolverIsNull2() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(new Properties(),
        mock(TypeConverter.class));

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Test {@link ExpressionFactoryImpl#createTreeStore(int, Profile, Properties)}.
   * <ul>
   *   <li>When {@code JEE5}.</li>
   *   <li>Then Builder return {@link Builder}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#createTreeStore(int, ExpressionFactoryImpl.Profile, Properties)}
   */
  @Test
  @DisplayName("Test createTreeStore(int, Profile, Properties); when 'JEE5'; then Builder return Builder")
  void testCreateTreeStore_whenJee5_thenBuilderReturnBuilder() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();

    // Act and Assert
    assertTrue(expressionFactoryImpl.createTreeStore(3, ExpressionFactoryImpl.Profile.JEE5, new Properties())
        .getBuilder() instanceof Builder);
  }

  /**
   * Test {@link ExpressionFactoryImpl#createTreeStore(int, Profile, Properties)}.
   * <ul>
   *   <li>When {@code JEE6}.</li>
   *   <li>Then Builder return {@link Builder}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#createTreeStore(int, ExpressionFactoryImpl.Profile, Properties)}
   */
  @Test
  @DisplayName("Test createTreeStore(int, Profile, Properties); when 'JEE6'; then Builder return Builder")
  void testCreateTreeStore_whenJee6_thenBuilderReturnBuilder() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();

    // Act and Assert
    assertTrue(expressionFactoryImpl.createTreeStore(3, ExpressionFactoryImpl.Profile.JEE6, new Properties())
        .getBuilder() instanceof Builder);
  }

  /**
   * Test {@link ExpressionFactoryImpl#createTreeStore(int, Profile, Properties)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then Builder return {@link Builder}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#createTreeStore(int, ExpressionFactoryImpl.Profile, Properties)}
   */
  @Test
  @DisplayName("Test createTreeStore(int, Profile, Properties); when zero; then Builder return Builder")
  void testCreateTreeStore_whenZero_thenBuilderReturnBuilder() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();

    // Act and Assert
    assertTrue(expressionFactoryImpl.createTreeStore(0, ExpressionFactoryImpl.Profile.JEE5, new Properties())
        .getBuilder() instanceof Builder);
  }

  /**
   * Test {@link ExpressionFactoryImpl#createTypeConverter(Properties)}.
   * <ul>
   *   <li>Given {@link ExpressionFactoryImpl#ExpressionFactoryImpl()}.</li>
   *   <li>Then return {@link TypeConverterImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#createTypeConverter(Properties)}
   */
  @Test
  @DisplayName("Test createTypeConverter(Properties); given ExpressionFactoryImpl(); then return TypeConverterImpl")
  void testCreateTypeConverter_givenExpressionFactoryImpl_thenReturnTypeConverterImpl() throws ELException {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();

    // Act
    TypeConverter actualCreateTypeConverterResult = expressionFactoryImpl.createTypeConverter(new Properties());
    Class<Object> type = Object.class;

    // Assert
    assertTrue(actualCreateTypeConverterResult instanceof TypeConverterImpl);
    assertEquals("Value", actualCreateTypeConverterResult.convert("Value", type));
  }

  /**
   * Test {@link ExpressionFactoryImpl#createTypeConverter(Properties)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link TypeConverterImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#createTypeConverter(Properties)}
   */
  @Test
  @DisplayName("Test createTypeConverter(Properties); when 'null'; then return TypeConverterImpl")
  void testCreateTypeConverter_whenNull_thenReturnTypeConverterImpl() throws ELException {
    // Arrange and Act
    TypeConverter actualCreateTypeConverterResult = (new ExpressionFactoryImpl()).createTypeConverter(null);
    Class<Object> type = Object.class;

    // Assert
    assertTrue(actualCreateTypeConverterResult instanceof TypeConverterImpl);
    assertEquals("Value", actualCreateTypeConverterResult.convert("Value", type));
  }

  /**
   * Test {@link ExpressionFactoryImpl#createTreeBuilder(Properties, Feature[])}.
   * <ul>
   *   <li>Then build empty string Root return {@link AstText}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#createTreeBuilder(Properties, Builder.Feature[])}
   */
  @Test
  @DisplayName("Test createTreeBuilder(Properties, Feature[]); then build empty string Root return AstText")
  void testCreateTreeBuilder_thenBuildEmptyStringRootReturnAstText() throws TreeBuilderException {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();

    // Act
    TreeBuilder actualCreateTreeBuilderResult = expressionFactoryImpl.createTreeBuilder(new Properties(),
        Builder.Feature.METHOD_INVOCATIONS);
    Tree actualBuildResult = actualCreateTreeBuilderResult.build("");

    // Assert
    Tree buildResult = actualCreateTreeBuilderResult.build("Expression");
    Iterable<FunctionNode> functionNodes = buildResult.getFunctionNodes();
    assertTrue(functionNodes instanceof List);
    assertTrue(actualCreateTreeBuilderResult instanceof Builder);
    ExpressionNode root = actualBuildResult.getRoot();
    assertTrue(root instanceof AstText);
    ExpressionNode root2 = buildResult.getRoot();
    assertTrue(root2 instanceof AstText);
    assertEquals(0, root.getCardinality());
    assertEquals(0, root2.getCardinality());
    assertFalse(root.isLeftValue());
    assertFalse(root2.isLeftValue());
    assertFalse(root.isMethodInvocation());
    assertFalse(root2.isMethodInvocation());
    assertFalse(actualBuildResult.isDeferred());
    assertFalse(buildResult.isDeferred());
    assertTrue(((List<FunctionNode>) functionNodes).isEmpty());
    assertTrue(root.isLiteralText());
    assertTrue(root2.isLiteralText());
    assertTrue(((Builder) actualCreateTreeBuilderResult).isEnabled(Builder.Feature.METHOD_INVOCATIONS));
    assertSame(functionNodes, actualBuildResult.getFunctionNodes());
    assertSame(functionNodes, actualBuildResult.getIdentifierNodes());
    assertSame(functionNodes, buildResult.getIdentifierNodes());
  }

  /**
   * Test {@link ExpressionFactoryImpl#createTreeBuilder(Properties, Feature[])}.
   * <ul>
   *   <li>Then return build {@code Expression} FunctionNodes.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#createTreeBuilder(Properties, Builder.Feature[])}
   */
  @Test
  @DisplayName("Test createTreeBuilder(Properties, Feature[]); then return build 'Expression' FunctionNodes")
  void testCreateTreeBuilder_thenReturnBuildExpressionFunctionNodes() throws TreeBuilderException {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();

    // Act
    TreeBuilder actualCreateTreeBuilderResult = expressionFactoryImpl.createTreeBuilder(new Properties(),
        Builder.Feature.METHOD_INVOCATIONS);
    Tree actualBuildResult = actualCreateTreeBuilderResult.build("Expression");

    // Assert
    Tree buildResult = actualCreateTreeBuilderResult.build("Expression");
    Iterable<FunctionNode> functionNodes = buildResult.getFunctionNodes();
    assertTrue(functionNodes instanceof List);
    assertTrue(actualCreateTreeBuilderResult instanceof Builder);
    ExpressionNode root = buildResult.getRoot();
    assertTrue(root instanceof AstText);
    ExpressionNode root2 = actualBuildResult.getRoot();
    assertTrue(root2 instanceof AstText);
    assertEquals(0, root.getCardinality());
    assertEquals(0, root2.getCardinality());
    assertFalse(root.isLeftValue());
    assertFalse(root2.isLeftValue());
    assertFalse(root.isMethodInvocation());
    assertFalse(root2.isMethodInvocation());
    assertFalse(buildResult.isDeferred());
    assertFalse(actualBuildResult.isDeferred());
    assertTrue(((List<FunctionNode>) functionNodes).isEmpty());
    assertTrue(root.isLiteralText());
    assertTrue(root2.isLiteralText());
    assertTrue(((Builder) actualCreateTreeBuilderResult).isEnabled(Builder.Feature.METHOD_INVOCATIONS));
    assertSame(functionNodes, actualBuildResult.getFunctionNodes());
    assertSame(functionNodes, buildResult.getIdentifierNodes());
    assertSame(functionNodes, actualBuildResult.getIdentifierNodes());
  }

  /**
   * Test {@link ExpressionFactoryImpl#coerceToType(Object, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then return {@code Obj}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionFactoryImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'java.lang.Object'; then return 'Obj'")
  void testCoerceToType_whenJavaLangObject_thenReturnObj() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();
    Class<Object> targetType = Object.class;

    // Act and Assert
    assertEquals("Obj", expressionFactoryImpl.coerceToType("Obj", targetType));
  }

  /**
   * Test
   * {@link ExpressionFactoryImpl#createValueExpression(ELContext, String, Class)}
   * with {@code context}, {@code expression}, {@code expectedType}.
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#createValueExpression(ELContext, String, Class)}
   */
  @Test
  @DisplayName("Test createValueExpression(ELContext, String, Class) with 'context', 'expression', 'expectedType'")
  void testCreateValueExpressionWithContextExpressionExpectedType() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();
    SimpleContext context = new SimpleContext();
    Class<Object> expectedType = Object.class;

    // Act
    TreeValueExpression actualCreateValueExpressionResult = expressionFactoryImpl.createValueExpression(context,
        "Expression", expectedType);

    // Assert
    assertEquals("Expression", actualCreateValueExpressionResult.getExpressionString());
    assertFalse(actualCreateValueExpressionResult.isDeferred());
    assertFalse(actualCreateValueExpressionResult.isLeftValue());
    assertTrue(actualCreateValueExpressionResult.isLiteralText());
    Class<Object> expectedExpectedType = Object.class;
    assertEquals(expectedExpectedType, actualCreateValueExpressionResult.getExpectedType());
  }

  /**
   * Test
   * {@link ExpressionFactoryImpl#createValueExpression(ELContext, String, Class)}
   * with {@code context}, {@code expression}, {@code expectedType}.
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#createValueExpression(ELContext, String, Class)}
   */
  @Test
  @DisplayName("Test createValueExpression(ELContext, String, Class) with 'context', 'expression', 'expectedType'")
  void testCreateValueExpressionWithContextExpressionExpectedType2() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();
    SimpleContext context = new SimpleContext();
    Class<Object> expectedType = Object.class;

    // Act
    TreeValueExpression actualCreateValueExpressionResult = expressionFactoryImpl.createValueExpression(context, "",
        expectedType);

    // Assert
    assertEquals("", actualCreateValueExpressionResult.getExpressionString());
    assertFalse(actualCreateValueExpressionResult.isDeferred());
    assertFalse(actualCreateValueExpressionResult.isLeftValue());
    assertTrue(actualCreateValueExpressionResult.isLiteralText());
    Class<Object> expectedExpectedType = Object.class;
    assertEquals(expectedExpectedType, actualCreateValueExpressionResult.getExpectedType());
  }

  /**
   * Test
   * {@link ExpressionFactoryImpl#createValueExpression(ELContext, String, Class)}
   * with {@code context}, {@code expression}, {@code expectedType}.
   * <ul>
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#createValueExpression(ELContext, String, Class)}
   */
  @Test
  @DisplayName("Test createValueExpression(ELContext, String, Class) with 'context', 'expression', 'expectedType'; given 'Name'")
  void testCreateValueExpressionWithContextExpressionExpectedType_givenName() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();

    SimpleContext context = new SimpleContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));
    Class<Object> expectedType = Object.class;

    // Act
    TreeValueExpression actualCreateValueExpressionResult = expressionFactoryImpl.createValueExpression(context,
        "Expression", expectedType);

    // Assert
    assertEquals("Expression", actualCreateValueExpressionResult.getExpressionString());
    assertFalse(actualCreateValueExpressionResult.isDeferred());
    assertFalse(actualCreateValueExpressionResult.isLeftValue());
    assertTrue(actualCreateValueExpressionResult.isLiteralText());
    Class<Object> expectedExpectedType = Object.class;
    assertEquals(expectedExpectedType, actualCreateValueExpressionResult.getExpectedType());
  }

  /**
   * Test {@link ExpressionFactoryImpl#createValueExpression(Object, Class)} with
   * {@code instance}, {@code expectedType}.
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#createValueExpression(Object, Class)}
   */
  @Test
  @DisplayName("Test createValueExpression(Object, Class) with 'instance', 'expectedType'")
  void testCreateValueExpressionWithInstanceExpectedType() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();
    Class<Object> expectedType = Object.class;

    // Act
    ObjectValueExpression actualCreateValueExpressionResult = expressionFactoryImpl.createValueExpression("Instance",
        expectedType);

    // Assert
    assertNull(actualCreateValueExpressionResult.getExpressionString());
    assertFalse(actualCreateValueExpressionResult.isLiteralText());
    Class<Object> expectedExpectedType = Object.class;
    assertEquals(expectedExpectedType, actualCreateValueExpressionResult.getExpectedType());
  }

  /**
   * Test
   * {@link ExpressionFactoryImpl#createMethodExpression(ELContext, String, Class, Class[])}.
   * <ul>
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#createMethodExpression(ELContext, String, Class, Class[])}
   */
  @Test
  @DisplayName("Test createMethodExpression(ELContext, String, Class, Class[]); given 'Name'")
  void testCreateMethodExpression_givenName() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();

    SimpleContext context = new SimpleContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));
    Class<Object> expectedReturnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] expectedParamTypes = new Class[]{forNameResult};

    // Act
    TreeMethodExpression actualCreateMethodExpressionResult = expressionFactoryImpl.createMethodExpression(context,
        "Expression", expectedReturnType, expectedParamTypes);

    // Assert
    assertEquals("Expression", actualCreateMethodExpressionResult.getExpressionString());
    assertEquals(1, expectedParamTypes.length);
    assertFalse(actualCreateMethodExpressionResult.isDeferred());
    assertFalse(actualCreateMethodExpressionResult.isParametersProvided());
    assertTrue(actualCreateMethodExpressionResult.isLiteralText());
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, expectedParamTypes[0]);
  }

  /**
   * Test
   * {@link ExpressionFactoryImpl#createMethodExpression(ELContext, String, Class, Class[])}.
   * <ul>
   *   <li>Then return ExpressionString is empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#createMethodExpression(ELContext, String, Class, Class[])}
   */
  @Test
  @DisplayName("Test createMethodExpression(ELContext, String, Class, Class[]); then return ExpressionString is empty string")
  void testCreateMethodExpression_thenReturnExpressionStringIsEmptyString() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();
    SimpleContext context = new SimpleContext();
    Class<Object> expectedReturnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] expectedParamTypes = new Class[]{forNameResult};

    // Act
    TreeMethodExpression actualCreateMethodExpressionResult = expressionFactoryImpl.createMethodExpression(context, "",
        expectedReturnType, expectedParamTypes);

    // Assert
    assertEquals("", actualCreateMethodExpressionResult.getExpressionString());
    assertEquals(1, expectedParamTypes.length);
    assertFalse(actualCreateMethodExpressionResult.isDeferred());
    assertFalse(actualCreateMethodExpressionResult.isParametersProvided());
    assertTrue(actualCreateMethodExpressionResult.isLiteralText());
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, expectedParamTypes[0]);
  }

  /**
   * Test
   * {@link ExpressionFactoryImpl#createMethodExpression(ELContext, String, Class, Class[])}.
   * <ul>
   *   <li>Then return ExpressionString is {@code Expression}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl#createMethodExpression(ELContext, String, Class, Class[])}
   */
  @Test
  @DisplayName("Test createMethodExpression(ELContext, String, Class, Class[]); then return ExpressionString is 'Expression'")
  void testCreateMethodExpression_thenReturnExpressionStringIsExpression() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();
    SimpleContext context = new SimpleContext();
    Class<Object> expectedReturnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] expectedParamTypes = new Class[]{forNameResult};

    // Act
    TreeMethodExpression actualCreateMethodExpressionResult = expressionFactoryImpl.createMethodExpression(context,
        "Expression", expectedReturnType, expectedParamTypes);

    // Assert
    assertEquals("Expression", actualCreateMethodExpressionResult.getExpressionString());
    assertEquals(1, expectedParamTypes.length);
    assertFalse(actualCreateMethodExpressionResult.isDeferred());
    assertFalse(actualCreateMethodExpressionResult.isParametersProvided());
    assertTrue(actualCreateMethodExpressionResult.isLiteralText());
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, expectedParamTypes[0]);
  }

  /**
   * Test Profile {@link Profile#contains(Feature)}.
   * <ul>
   *   <li>Given {@code JEE5}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl.Profile#contains(Builder.Feature)}
   */
  @Test
  @DisplayName("Test Profile contains(Feature); given 'JEE5'; then return 'false'")
  void testProfileContains_givenJee5_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(ExpressionFactoryImpl.Profile.JEE5.contains(Builder.Feature.METHOD_INVOCATIONS));
  }

  /**
   * Test Profile {@link Profile#contains(Feature)}.
   * <ul>
   *   <li>Given {@code JEE6}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionFactoryImpl.Profile#contains(Builder.Feature)}
   */
  @Test
  @DisplayName("Test Profile contains(Feature); given 'JEE6'; then return 'true'")
  void testProfileContains_givenJee6_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(ExpressionFactoryImpl.Profile.JEE6.contains(Builder.Feature.METHOD_INVOCATIONS));
  }

  /**
   * Test Profile {@link Profile#features()}.
   * <p>
   * Method under test: {@link ExpressionFactoryImpl.Profile#features()}
   */
  @Test
  @DisplayName("Test Profile features()")
  void testProfileFeatures() {
    // Arrange, Act and Assert
    assertEquals(0, ExpressionFactoryImpl.Profile.JEE5.features().length);
  }
}
