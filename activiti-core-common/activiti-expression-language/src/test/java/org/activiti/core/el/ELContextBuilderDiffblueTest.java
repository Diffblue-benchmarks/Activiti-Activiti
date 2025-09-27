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
package org.activiti.core.el;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELContext;
import jakarta.el.ELResolver;
import jakarta.el.FunctionMapper;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ELContextBuilderDiffblueTest {
  /**
   * Test {@link ELContextBuilder#withResolvers(ELResolver[])}.
   *
   * <ul>
   *   <li>When {@link JsonNodeELResolver#JsonNodeELResolver()}.
   *   <li>Then return {@link ELContextBuilder} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ELContextBuilder#withResolvers(ELResolver[])}
   */
  @Test
  @DisplayName(
      "Test withResolvers(ELResolver[]); when JsonNodeELResolver(); then return ELContextBuilder (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ELContextBuilder ELContextBuilder.withResolvers(ELResolver[])"})
  void testWithResolvers_whenJsonNodeELResolver_thenReturnELContextBuilder() {
    // Arrange
    ELContextBuilder elContextBuilder = new ELContextBuilder();

    // Act
    ELContextBuilder actualWithResolversResult =
        elContextBuilder.withResolvers(new JsonNodeELResolver());

    // Assert
    assertSame(elContextBuilder, actualWithResolversResult);
  }

  /**
   * Test {@link ELContextBuilder#buildWithCustomFunctions(List)}.
   *
   * <p>Method under test: {@link ELContextBuilder#buildWithCustomFunctions(List)}
   */
  @Test
  @DisplayName("Test buildWithCustomFunctions(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ELContext ELContextBuilder.buildWithCustomFunctions(List)"})
  void testBuildWithCustomFunctions() {
    // Arrange
    ELContextBuilder elContextBuilder = new ELContextBuilder();
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    elContextBuilder.withResolvers(jsonNodeELResolver, new JsonNodeELResolver());

    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    // Act
    ELContext actualBuildWithCustomFunctionsResult =
        elContextBuilder.buildWithCustomFunctions(customFunctionProviders);

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualBuildWithCustomFunctionsResult instanceof ActivitiElContext);
    FunctionMapper functionMapper = actualBuildWithCustomFunctionsResult.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    Map<String, Method> stringMethodMap = ((ActivitiFunctionMapper) functionMapper).map;
    assertEquals(2, stringMethodMap.size());
    assertEquals(1, stringMethodMap.get(":list").getParameterTypes().length);
  }

  /**
   * Test {@link ELContextBuilder#buildWithCustomFunctions(List)}.
   *
   * <ul>
   *   <li>Given array of {@link ELResolver} with {@link JsonNodeELResolver#JsonNodeELResolver()}.
   * </ul>
   *
   * <p>Method under test: {@link ELContextBuilder#buildWithCustomFunctions(List)}
   */
  @Test
  @DisplayName(
      "Test buildWithCustomFunctions(List); given array of ELResolver with JsonNodeELResolver()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ELContext ELContextBuilder.buildWithCustomFunctions(List)"})
  void testBuildWithCustomFunctions_givenArrayOfELResolverWithJsonNodeELResolver() {
    // Arrange
    ELContextBuilder elContextBuilder = new ELContextBuilder();
    elContextBuilder.withResolvers(new JsonNodeELResolver());

    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    // Act
    ELContext actualBuildWithCustomFunctionsResult =
        elContextBuilder.buildWithCustomFunctions(customFunctionProviders);

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualBuildWithCustomFunctionsResult instanceof ActivitiElContext);
    FunctionMapper functionMapper = actualBuildWithCustomFunctionsResult.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    Map<String, Method> stringMethodMap = ((ActivitiFunctionMapper) functionMapper).map;
    assertEquals(2, stringMethodMap.size());
    assertEquals(1, stringMethodMap.get(":list").getParameterTypes().length);
  }

  /**
   * Test {@link ELContextBuilder#buildWithCustomFunctions(List)}.
   *
   * <ul>
   *   <li>Given {@link ELContextBuilder} (default constructor) withVariables {@link
   *       HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link ELContextBuilder#buildWithCustomFunctions(List)}
   */
  @Test
  @DisplayName(
      "Test buildWithCustomFunctions(List); given ELContextBuilder (default constructor) withVariables HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ELContext ELContextBuilder.buildWithCustomFunctions(List)"})
  void testBuildWithCustomFunctions_givenELContextBuilderWithVariablesHashMap() {
    // Arrange
    ELContextBuilder elContextBuilder = new ELContextBuilder();
    elContextBuilder.withVariables(new HashMap<>());

    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    // Act
    ELContext actualBuildWithCustomFunctionsResult =
        elContextBuilder.buildWithCustomFunctions(customFunctionProviders);

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualBuildWithCustomFunctionsResult instanceof ActivitiElContext);
    FunctionMapper functionMapper = actualBuildWithCustomFunctionsResult.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    Map<String, Method> stringMethodMap = ((ActivitiFunctionMapper) functionMapper).map;
    assertEquals(2, stringMethodMap.size());
    assertEquals(1, stringMethodMap.get(":list").getParameterTypes().length);
  }

  /**
   * Test {@link ELContextBuilder#buildWithCustomFunctions(List)}.
   *
   * <ul>
   *   <li>Given {@link ELContextBuilder} (default constructor).
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.
   * </ul>
   *
   * <p>Method under test: {@link ELContextBuilder#buildWithCustomFunctions(List)}
   */
  @Test
  @DisplayName(
      "Test buildWithCustomFunctions(List); given ELContextBuilder (default constructor); then calls addCustomFunctions(ActivitiElContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ELContext ELContextBuilder.buildWithCustomFunctions(List)"})
  void testBuildWithCustomFunctions_givenELContextBuilder_thenCallsAddCustomFunctions() {
    // Arrange
    ELContextBuilder elContextBuilder = new ELContextBuilder();

    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);

    // Act
    ELContext actualBuildWithCustomFunctionsResult =
        elContextBuilder.buildWithCustomFunctions(customFunctionProviders);

    // Assert
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualBuildWithCustomFunctionsResult instanceof ActivitiElContext);
    FunctionMapper functionMapper = actualBuildWithCustomFunctionsResult.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    Map<String, Method> stringMethodMap = ((ActivitiFunctionMapper) functionMapper).map;
    assertEquals(2, stringMethodMap.size());
    assertEquals(1, stringMethodMap.get(":list").getParameterTypes().length);
  }

  /**
   * Test {@link ELContextBuilder#buildWithCustomFunctions(List)}.
   *
   * <ul>
   *   <li>Given {@link ELContextBuilder} (default constructor).
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.
   * </ul>
   *
   * <p>Method under test: {@link ELContextBuilder#buildWithCustomFunctions(List)}
   */
  @Test
  @DisplayName(
      "Test buildWithCustomFunctions(List); given ELContextBuilder (default constructor); then calls addCustomFunctions(ActivitiElContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ELContext ELContextBuilder.buildWithCustomFunctions(List)"})
  void testBuildWithCustomFunctions_givenELContextBuilder_thenCallsAddCustomFunctions2() {
    // Arrange
    ELContextBuilder elContextBuilder = new ELContextBuilder();

    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    // Act
    ELContext actualBuildWithCustomFunctionsResult =
        elContextBuilder.buildWithCustomFunctions(customFunctionProviders);

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualBuildWithCustomFunctionsResult instanceof ActivitiElContext);
    FunctionMapper functionMapper = actualBuildWithCustomFunctionsResult.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    Map<String, Method> stringMethodMap = ((ActivitiFunctionMapper) functionMapper).map;
    assertEquals(2, stringMethodMap.size());
    assertEquals(1, stringMethodMap.get(":list").getParameterTypes().length);
  }

  /**
   * Test {@link ELContextBuilder#buildWithCustomFunctions(List)}.
   *
   * <ul>
   *   <li>Given {@link ELContextBuilder} (default constructor).
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ELContextBuilder#buildWithCustomFunctions(List)}
   */
  @Test
  @DisplayName(
      "Test buildWithCustomFunctions(List); given ELContextBuilder (default constructor); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ELContext ELContextBuilder.buildWithCustomFunctions(List)"})
  void testBuildWithCustomFunctions_givenELContextBuilder_whenArrayList() {
    // Arrange
    ELContextBuilder elContextBuilder = new ELContextBuilder();

    // Act
    ELContext actualBuildWithCustomFunctionsResult =
        elContextBuilder.buildWithCustomFunctions(new ArrayList<>());

    // Assert
    assertTrue(actualBuildWithCustomFunctionsResult instanceof ActivitiElContext);
    FunctionMapper functionMapper = actualBuildWithCustomFunctionsResult.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    Map<String, Method> stringMethodMap = ((ActivitiFunctionMapper) functionMapper).map;
    assertEquals(2, stringMethodMap.size());
    assertEquals(1, stringMethodMap.get(":list").getParameterTypes().length);
  }

  /**
   * Test {@link ELContextBuilder#buildWithCustomFunctions(List)}.
   *
   * <ul>
   *   <li>Given {@link ELContextBuilder} (default constructor).
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ELContextBuilder#buildWithCustomFunctions(List)}
   */
  @Test
  @DisplayName(
      "Test buildWithCustomFunctions(List); given ELContextBuilder (default constructor); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ELContext ELContextBuilder.buildWithCustomFunctions(List)"})
  void testBuildWithCustomFunctions_givenELContextBuilder_whenNull() {
    // Arrange and Act
    ELContext actualBuildWithCustomFunctionsResult =
        new ELContextBuilder().buildWithCustomFunctions(null);

    // Assert
    assertTrue(actualBuildWithCustomFunctionsResult instanceof ActivitiElContext);
    FunctionMapper functionMapper = actualBuildWithCustomFunctionsResult.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    Map<String, Method> stringMethodMap = ((ActivitiFunctionMapper) functionMapper).map;
    assertEquals(2, stringMethodMap.size());
    assertEquals(1, stringMethodMap.get(":list").getParameterTypes().length);
  }

  /**
   * Test {@link ELContextBuilder#build()}.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ELContextBuilder#build()}
   *   <li>default or parameterless constructor of {@link ELContextBuilder}
   *   <li>{@link ELContextBuilder#withVariables(Map)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ELContextBuilder.<init>()",
    "ELContext ELContextBuilder.build()",
    "ELContextBuilder ELContextBuilder.withVariables(Map)"
  })
  void testBuild() {
    // Arrange and Act
    ELContextBuilder actualElContextBuilder = new ELContextBuilder();
    ELContextBuilder actualWithResolversResult =
        actualElContextBuilder.withResolvers(new JsonNodeELResolver());
    ELContext actualElContext = actualWithResolversResult.withVariables(new HashMap<>()).build();

    // Assert
    assertTrue(actualElContext instanceof ActivitiElContext);
    assertTrue(((ActivitiFunctionMapper) actualElContext.getFunctionMapper()).map.isEmpty());
  }
}
