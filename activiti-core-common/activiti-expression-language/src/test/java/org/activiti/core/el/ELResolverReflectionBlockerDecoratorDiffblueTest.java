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

import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.CompositeELResolver;
import jakarta.el.ELContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ELResolverReflectionBlockerDecoratorDiffblueTest {
  /**
   * Test {@link ELResolverReflectionBlockerDecorator#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <p>
   * Method under test: {@link ELResolverReflectionBlockerDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[])")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "Object ELResolverReflectionBlockerDecorator.invoke(ELContext, Object, Object, Class[], Object[])"})
  void testInvoke() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());
    ActivitiElContext context = new ActivitiElContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.invoke(context, "Base", "Method", new Class[]{forNameResult},
        new Object[]{"Params"}));
  }

  /**
   * Test {@link ELResolverReflectionBlockerDecorator#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <p>
   * Method under test: {@link ELResolverReflectionBlockerDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[])")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "Object ELResolverReflectionBlockerDecorator.invoke(ELContext, Object, Object, Class[], Object[])"})
  void testInvoke2() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new CompositeELResolver());
    ActivitiElContext context = new ActivitiElContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.invoke(context, "Base", "Method", new Class[]{forNameResult},
        new Object[]{"Params"}));
  }

  /**
   * Test {@link ELResolverReflectionBlockerDecorator#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <p>
   * Method under test: {@link ELResolverReflectionBlockerDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[])")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "Object ELResolverReflectionBlockerDecorator.invoke(ELContext, Object, Object, Class[], Object[])"})
  void testInvoke3() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new ELResolverReflectionBlockerDecorator(new JsonNodeELResolver()));
    ActivitiElContext context = new ActivitiElContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.invoke(context, "Base", "Method", new Class[]{forNameResult},
        new Object[]{"Params"}));
  }

  /**
   * Test {@link ELResolverReflectionBlockerDecorator#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <p>
   * Method under test: {@link ELResolverReflectionBlockerDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[])")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "Object ELResolverReflectionBlockerDecorator.invoke(ELContext, Object, Object, Class[], Object[])"})
  void testInvoke4() {
    // Arrange
    CompositeELResolver resolver = new CompositeELResolver();
    resolver.add(new JsonNodeELResolver());
    resolver.add(new ELResolverReflectionBlockerDecorator(new JsonNodeELResolver()));
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        resolver);
    ActivitiElContext context = new ActivitiElContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.invoke(context, "Base", "Method", new Class[]{forNameResult},
        new Object[]{"Params"}));
  }

  /**
   * Test {@link ELResolverReflectionBlockerDecorator#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <ul>
   *   <li>Given {@link CompositeELResolver} (default constructor) add {@link JsonNodeELResolver#JsonNodeELResolver()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ELResolverReflectionBlockerDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[]); given CompositeELResolver (default constructor) add JsonNodeELResolver(); then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "Object ELResolverReflectionBlockerDecorator.invoke(ELContext, Object, Object, Class[], Object[])"})
  void testInvoke_givenCompositeELResolverAddJsonNodeELResolver_thenReturnNull() {
    // Arrange
    CompositeELResolver resolver = new CompositeELResolver();
    resolver.add(new JsonNodeELResolver());
    resolver.add(new JsonNodeELResolver());
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        resolver);
    ActivitiElContext context = new ActivitiElContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.invoke(context, "Base", "Method", new Class[]{forNameResult},
        new Object[]{"Params"}));
  }
}
