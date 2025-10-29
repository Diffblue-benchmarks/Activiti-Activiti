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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import jakarta.el.BeanNameELResolver;
import jakarta.el.BeanNameResolver;
import jakarta.el.ELContext;
import jakarta.el.ELResolver;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ELContextBuilderDiffblueTest {
  /**
   * Method under test: {@link ELContextBuilder#withResolvers(ELResolver[])}
   */
  @Test
  void testWithResolvers() {
    // Arrange
    ELContextBuilder elContextBuilder = new ELContextBuilder();

    // Act and Assert
    assertSame(elContextBuilder, elContextBuilder.withResolvers(new JsonNodeELResolver()));
  }

  /**
   * Method under test: {@link ELContextBuilder#withResolvers(ELResolver[])}
   */
  @Test
  void testWithResolvers2() {
    // Arrange
    ELContextBuilder elContextBuilder = new ELContextBuilder();

    // Act and Assert
    assertSame(elContextBuilder, elContextBuilder.withResolvers(new BeanNameELResolver(mock(BeanNameResolver.class))));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ELContextBuilder#build()}
   *   <li>default or parameterless constructor of {@link ELContextBuilder}
   *   <li>{@link ELContextBuilder#withVariables(Map)}
   * </ul>
   */
  @Test
  void testBuild() {
    // Arrange
    ELContextBuilder elContextBuilder = new ELContextBuilder();
    ELContextBuilder withResolversResult = elContextBuilder.withResolvers(new JsonNodeELResolver());

    // Act
    ELContext actualBuildResult = withResolversResult.withVariables(new HashMap<>()).build();

    // Assert
    assertTrue(actualBuildResult instanceof ActivitiElContext);
    assertTrue(((ActivitiFunctionMapper) actualBuildResult.getFunctionMapper()).map.isEmpty());
  }
}
