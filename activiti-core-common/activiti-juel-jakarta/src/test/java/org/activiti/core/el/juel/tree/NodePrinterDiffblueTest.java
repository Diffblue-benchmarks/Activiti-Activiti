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
package org.activiti.core.el.juel.tree;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.activiti.core.el.juel.tree.impl.ast.AstBinary;
import org.activiti.core.el.juel.tree.impl.ast.AstBinary.Operator;
import org.activiti.core.el.juel.tree.impl.ast.AstNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class NodePrinterDiffblueTest {
  /**
   * Test {@link NodePrinter#dump(PrintWriter, Node)} with {@code writer}, {@code node}.
   *
   * <p>Method under test: {@link NodePrinter#dump(PrintWriter, Node)}
   */
  @Test
  @DisplayName("Test dump(PrintWriter, Node) with 'writer', 'node'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void NodePrinter.dump(PrintWriter, Node)"})
  void testDumpWithWriterNode() {
    // Arrange
    PrintWriter writer = new PrintWriter(new StringWriter());

    AstBinary node = mock(AstBinary.class);
    AstNull left = new AstNull();
    AstBinary astBinary = new AstBinary(left, new AstNull(), mock(Operator.class));
    when(node.getChild(anyInt())).thenReturn(astBinary);
    when(node.getCardinality()).thenReturn(1);

    // Act
    NodePrinter.dump(writer, node);

    // Assert
    verify(node, atLeast(1)).getCardinality();
    verify(node, atLeast(1)).getChild(0);
  }

  /**
   * Test {@link NodePrinter#dump(PrintWriter, Node)} with {@code writer}, {@code node}.
   *
   * <p>Method under test: {@link NodePrinter#dump(PrintWriter, Node)}
   */
  @Test
  @DisplayName("Test dump(PrintWriter, Node) with 'writer', 'node'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void NodePrinter.dump(PrintWriter, Node)"})
  void testDumpWithWriterNode2() {
    // Arrange
    PrintWriter writer = new PrintWriter(new StringWriter());

    AstBinary node = mock(AstBinary.class);
    AstNull left = new AstNull();
    AstBinary left2 = new AstBinary(left, new AstNull(), mock(Operator.class));
    AstBinary astBinary = new AstBinary(left2, new AstNull(), mock(Operator.class));
    when(node.getChild(anyInt())).thenReturn(astBinary);
    when(node.getCardinality()).thenReturn(1);

    // Act
    NodePrinter.dump(writer, node);

    // Assert
    verify(node, atLeast(1)).getCardinality();
    verify(node, atLeast(1)).getChild(0);
  }
}
