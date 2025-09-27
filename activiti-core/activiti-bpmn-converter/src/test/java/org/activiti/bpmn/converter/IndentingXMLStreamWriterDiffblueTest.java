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
package org.activiti.bpmn.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import javax.xml.stream.XMLStreamWriter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class IndentingXMLStreamWriterDiffblueTest {
  /**
   * Test {@link IndentingXMLStreamWriter#IndentingXMLStreamWriter(XMLStreamWriter)}.
   *
   * <p>Method under test: {@link
   * IndentingXMLStreamWriter#IndentingXMLStreamWriter(XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test new IndentingXMLStreamWriter(XMLStreamWriter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.<init>(XMLStreamWriter)"})
  void testNewIndentingXMLStreamWriter() {
    // Arrange, Act and Assert
    assertEquals(2, new IndentingXMLStreamWriter(null).getIndentStep());
  }

  /**
   * Test {@link IndentingXMLStreamWriter#getIndentStep()}.
   *
   * <ul>
   *   <li>Given {@link IndentingXMLStreamWriter#IndentingXMLStreamWriter(XMLStreamWriter)} with
   *       writer is {@code null}.
   *   <li>Then return two.
   * </ul>
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#getIndentStep()}
   */
  @Test
  @DisplayName(
      "Test getIndentStep(); given IndentingXMLStreamWriter(XMLStreamWriter) with writer is 'null'; then return two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int IndentingXMLStreamWriter.getIndentStep()"})
  void testGetIndentStep_givenIndentingXMLStreamWriterWithWriterIsNull_thenReturnTwo() {
    // Arrange, Act and Assert
    assertEquals(2, new IndentingXMLStreamWriter(null).getIndentStep());
  }

  /**
   * Test {@link IndentingXMLStreamWriter#setIndentStep(int)} with {@code indentStep}.
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#setIndentStep(int)}
   */
  @Test
  @DisplayName("Test setIndentStep(int) with 'indentStep'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.setIndentStep(int)"})
  void testSetIndentStepWithIndentStep() {
    // Arrange
    IndentingXMLStreamWriter indentingXMLStreamWriter = new IndentingXMLStreamWriter(null);

    // Act
    indentingXMLStreamWriter.setIndentStep(1);

    // Assert
    assertEquals(1, indentingXMLStreamWriter.getIndentStep());
  }
}
