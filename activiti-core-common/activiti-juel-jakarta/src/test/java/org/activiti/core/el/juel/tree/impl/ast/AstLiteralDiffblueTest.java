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
package org.activiti.core.el.juel.tree.impl.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AstLiteralDiffblueTest {
  /**
   * Test {@link AstLiteral#getCardinality()}.
   * <p>
   * Method under test: {@link AstLiteral#getCardinality()}
   */
  @Test
  @DisplayName("Test getCardinality()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"int AstLiteral.getCardinality()"})
  void testGetCardinality() {
    // Arrange, Act and Assert
    assertEquals(0, (new AstNull()).getCardinality());
  }

  /**
   * Test {@link AstLiteral#getChild(int)}.
   * <p>
   * Method under test: {@link AstLiteral#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"org.activiti.core.el.juel.tree.impl.ast.AstNode AstLiteral.getChild(int)"})
  void testGetChild() {
    // Arrange, Act and Assert
    assertNull((new AstNull()).getChild(1));
  }
}
