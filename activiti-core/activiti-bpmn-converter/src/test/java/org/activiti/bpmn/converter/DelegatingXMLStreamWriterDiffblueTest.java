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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DelegatingXMLStreamWriterDiffblueTest {
  /**
   * Test {@link DelegatingXMLStreamWriter#writeStartElement(String)} with
   * {@code localName}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeStartElement(String)}
   */
  @Test
  @DisplayName("Test writeStartElement(String) with 'localName'; then calls writeStartElement(String)")
  void testWriteStartElementWithLocalName_thenCallsWriteStartElement() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeStartElement("Local Name");

    // Assert
    verify(writer).writeStartElement(eq("Local Name"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeStartElement(String, String)} with
   * {@code namespaceURI}, {@code localName}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeStartElement(String, String)}
   */
  @Test
  @DisplayName("Test writeStartElement(String, String) with 'namespaceURI', 'localName'; then calls writeStartElement(String, String)")
  void testWriteStartElementWithNamespaceURILocalName_thenCallsWriteStartElement() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeStartElement("Namespace URI", "Local Name");

    // Assert
    verify(writer).writeStartElement(eq("Namespace URI"), eq("Local Name"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeStartElement(String, String)} with
   * {@code namespaceURI}, {@code localName}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeStartElement(String, String)}
   */
  @Test
  @DisplayName("Test writeStartElement(String, String) with 'namespaceURI', 'localName'; then calls writeStartElement(String, String)")
  void testWriteStartElementWithNamespaceURILocalName_thenCallsWriteStartElement2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).writeStartElement("Namespace URI",
        "Local Name");

    // Assert
    verify(writer).writeStartElement(eq("Namespace URI"), eq("Local Name"));
  }

  /**
   * Test
   * {@link DelegatingXMLStreamWriter#writeStartElement(String, String, String)}
   * with {@code prefix}, {@code localName}, {@code namespaceURI}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String, String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeStartElement(String, String, String)}
   */
  @Test
  @DisplayName("Test writeStartElement(String, String, String) with 'prefix', 'localName', 'namespaceURI'; then calls writeStartElement(String, String, String)")
  void testWriteStartElementWithPrefixLocalNameNamespaceURI_thenCallsWriteStartElement() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeStartElement("Prefix", "Local Name", "Namespace URI");

    // Assert
    verify(writer).writeStartElement(eq("Prefix"), eq("Local Name"), eq("Namespace URI"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeEmptyElement(String)} with
   * {@code localName}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeEmptyElement(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeEmptyElement(String)}
   */
  @Test
  @DisplayName("Test writeEmptyElement(String) with 'localName'; then calls writeEmptyElement(String)")
  void testWriteEmptyElementWithLocalName_thenCallsWriteEmptyElement() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEmptyElement(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeEmptyElement("Local Name");

    // Assert
    verify(writer).writeEmptyElement(eq("Local Name"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeEmptyElement(String, String)} with
   * {@code namespaceURI}, {@code localName}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeEmptyElement(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeEmptyElement(String, String)}
   */
  @Test
  @DisplayName("Test writeEmptyElement(String, String) with 'namespaceURI', 'localName'; then calls writeEmptyElement(String, String)")
  void testWriteEmptyElementWithNamespaceURILocalName_thenCallsWriteEmptyElement() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEmptyElement(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeEmptyElement("Namespace URI", "Local Name");

    // Assert
    verify(writer).writeEmptyElement(eq("Namespace URI"), eq("Local Name"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeEmptyElement(String, String)} with
   * {@code namespaceURI}, {@code localName}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeEmptyElement(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeEmptyElement(String, String)}
   */
  @Test
  @DisplayName("Test writeEmptyElement(String, String) with 'namespaceURI', 'localName'; then calls writeEmptyElement(String, String)")
  void testWriteEmptyElementWithNamespaceURILocalName_thenCallsWriteEmptyElement2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEmptyElement(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).writeEmptyElement("Namespace URI",
        "Local Name");

    // Assert
    verify(writer).writeEmptyElement(eq("Namespace URI"), eq("Local Name"));
  }

  /**
   * Test
   * {@link DelegatingXMLStreamWriter#writeEmptyElement(String, String, String)}
   * with {@code prefix}, {@code localName}, {@code namespaceURI}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeEmptyElement(String, String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeEmptyElement(String, String, String)}
   */
  @Test
  @DisplayName("Test writeEmptyElement(String, String, String) with 'prefix', 'localName', 'namespaceURI'; then calls writeEmptyElement(String, String, String)")
  void testWriteEmptyElementWithPrefixLocalNameNamespaceURI_thenCallsWriteEmptyElement() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEmptyElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeEmptyElement("Prefix", "Local Name", "Namespace URI");

    // Assert
    verify(writer).writeEmptyElement(eq("Prefix"), eq("Local Name"), eq("Namespace URI"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeEndDocument()}.
   * <ul>
   *   <li>Then calls {@link DelegatingXMLStreamWriter#writeEndDocument()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegatingXMLStreamWriter#writeEndDocument()}
   */
  @Test
  @DisplayName("Test writeEndDocument(); then calls writeEndDocument()")
  void testWriteEndDocument_thenCallsWriteEndDocument() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndDocument();

    // Act
    (new IndentingXMLStreamWriter(writer)).writeEndDocument();

    // Assert that nothing has changed
    verify(writer).writeEndDocument();
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeEndDocument()}.
   * <ul>
   *   <li>Then calls {@link DelegatingXMLStreamWriter#writeEndDocument()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegatingXMLStreamWriter#writeEndDocument()}
   */
  @Test
  @DisplayName("Test writeEndDocument(); then calls writeEndDocument()")
  void testWriteEndDocument_thenCallsWriteEndDocument2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndDocument();

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).writeEndDocument();

    // Assert that nothing has changed
    verify(writer).writeEndDocument();
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#close()}.
   * <ul>
   *   <li>Given {@link IndentingXMLStreamWriter}
   * {@link DelegatingXMLStreamWriter#close()} does nothing.</li>
   *   <li>Then calls {@link DelegatingXMLStreamWriter#close()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegatingXMLStreamWriter#close()}
   */
  @Test
  @DisplayName("Test close(); given IndentingXMLStreamWriter close() does nothing; then calls close()")
  void testClose_givenIndentingXMLStreamWriterCloseDoesNothing_thenCallsClose() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).close();

    // Act
    (new IndentingXMLStreamWriter(writer)).close();

    // Assert that nothing has changed
    verify(writer).close();
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#close()}.
   * <ul>
   *   <li>Given {@link IndentingXMLStreamWriter}
   * {@link DelegatingXMLStreamWriter#close()} does nothing.</li>
   *   <li>Then calls {@link DelegatingXMLStreamWriter#close()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegatingXMLStreamWriter#close()}
   */
  @Test
  @DisplayName("Test close(); given IndentingXMLStreamWriter close() does nothing; then calls close()")
  void testClose_givenIndentingXMLStreamWriterCloseDoesNothing_thenCallsClose2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).close();

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).close();

    // Assert that nothing has changed
    verify(writer).close();
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#flush()}.
   * <ul>
   *   <li>Given {@link IndentingXMLStreamWriter}
   * {@link DelegatingXMLStreamWriter#flush()} does nothing.</li>
   *   <li>Then calls {@link DelegatingXMLStreamWriter#flush()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegatingXMLStreamWriter#flush()}
   */
  @Test
  @DisplayName("Test flush(); given IndentingXMLStreamWriter flush() does nothing; then calls flush()")
  void testFlush_givenIndentingXMLStreamWriterFlushDoesNothing_thenCallsFlush() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).flush();

    // Act
    (new IndentingXMLStreamWriter(writer)).flush();

    // Assert that nothing has changed
    verify(writer).flush();
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#flush()}.
   * <ul>
   *   <li>Given {@link IndentingXMLStreamWriter}
   * {@link DelegatingXMLStreamWriter#flush()} does nothing.</li>
   *   <li>Then calls {@link DelegatingXMLStreamWriter#flush()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegatingXMLStreamWriter#flush()}
   */
  @Test
  @DisplayName("Test flush(); given IndentingXMLStreamWriter flush() does nothing; then calls flush()")
  void testFlush_givenIndentingXMLStreamWriterFlushDoesNothing_thenCallsFlush2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).flush();

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).flush();

    // Assert that nothing has changed
    verify(writer).flush();
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeAttribute(String, String)} with
   * {@code localName}, {@code value}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}
   */
  @Test
  @DisplayName("Test writeAttribute(String, String) with 'localName', 'value'; then calls writeAttribute(String, String)")
  void testWriteAttributeWithLocalNameValue_thenCallsWriteAttribute() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeAttribute("Local Name", "42");

    // Assert that nothing has changed
    verify(writer).writeAttribute(eq("Local Name"), eq("42"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeAttribute(String, String)} with
   * {@code localName}, {@code value}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}
   */
  @Test
  @DisplayName("Test writeAttribute(String, String) with 'localName', 'value'; then calls writeAttribute(String, String)")
  void testWriteAttributeWithLocalNameValue_thenCallsWriteAttribute2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).writeAttribute("Local Name", "42");

    // Assert that nothing has changed
    verify(writer).writeAttribute(eq("Local Name"), eq("42"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String)}
   * with {@code namespaceURI}, {@code localName}, {@code value}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String)}
   */
  @Test
  @DisplayName("Test writeAttribute(String, String, String) with 'namespaceURI', 'localName', 'value'; then calls writeAttribute(String, String, String)")
  void testWriteAttributeWithNamespaceURILocalNameValue_thenCallsWriteAttribute() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeAttribute("Namespace URI", "Local Name", "42");

    // Assert that nothing has changed
    verify(writer).writeAttribute(eq("Namespace URI"), eq("Local Name"), eq("42"));
  }

  /**
   * Test
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String, String)}
   * with {@code prefix}, {@code namespaceURI}, {@code localName}, {@code value}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String, String)}
   */
  @Test
  @DisplayName("Test writeAttribute(String, String, String, String) with 'prefix', 'namespaceURI', 'localName', 'value'; then calls writeAttribute(String, String, String, String)")
  void testWriteAttributeWithPrefixNamespaceURILocalNameValue_thenCallsWriteAttribute() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeAttribute("Prefix", "Namespace URI", "Local Name", "42");

    // Assert that nothing has changed
    verify(writer).writeAttribute(eq("Prefix"), eq("Namespace URI"), eq("Local Name"), eq("42"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeNamespace(String, String)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeNamespace(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeNamespace(String, String)}
   */
  @Test
  @DisplayName("Test writeNamespace(String, String); then calls writeNamespace(String, String)")
  void testWriteNamespace_thenCallsWriteNamespace() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeNamespace("Prefix", "Namespace URI");

    // Assert that nothing has changed
    verify(writer).writeNamespace(eq("Prefix"), eq("Namespace URI"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeNamespace(String, String)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeNamespace(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeNamespace(String, String)}
   */
  @Test
  @DisplayName("Test writeNamespace(String, String); then calls writeNamespace(String, String)")
  void testWriteNamespace_thenCallsWriteNamespace2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).writeNamespace("Prefix", "Namespace URI");

    // Assert that nothing has changed
    verify(writer).writeNamespace(eq("Prefix"), eq("Namespace URI"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeDefaultNamespace(String)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeDefaultNamespace(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeDefaultNamespace(String)}
   */
  @Test
  @DisplayName("Test writeDefaultNamespace(String); then calls writeDefaultNamespace(String)")
  void testWriteDefaultNamespace_thenCallsWriteDefaultNamespace() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeDefaultNamespace(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeDefaultNamespace("Namespace URI");

    // Assert that nothing has changed
    verify(writer).writeDefaultNamespace(eq("Namespace URI"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeComment(String)}.
   * <ul>
   *   <li>Then calls {@link DelegatingXMLStreamWriter#writeComment(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegatingXMLStreamWriter#writeComment(String)}
   */
  @Test
  @DisplayName("Test writeComment(String); then calls writeComment(String)")
  void testWriteComment_thenCallsWriteComment() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeComment(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeComment("Data");

    // Assert that nothing has changed
    verify(writer).writeComment(eq("Data"));
  }

  /**
   * Test
   * {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String, String)}
   * with {@code target}, {@code data}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String, String)}
   */
  @Test
  @DisplayName("Test writeProcessingInstruction(String, String) with 'target', 'data'; then calls writeProcessingInstruction(String, String)")
  void testWriteProcessingInstructionWithTargetData_thenCallsWriteProcessingInstruction() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeProcessingInstruction(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeProcessingInstruction("Target", "Data");

    // Assert that nothing has changed
    verify(writer).writeProcessingInstruction(eq("Target"), eq("Data"));
  }

  /**
   * Test
   * {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String, String)}
   * with {@code target}, {@code data}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String, String)}
   */
  @Test
  @DisplayName("Test writeProcessingInstruction(String, String) with 'target', 'data'; then calls writeProcessingInstruction(String, String)")
  void testWriteProcessingInstructionWithTargetData_thenCallsWriteProcessingInstruction2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeProcessingInstruction(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).writeProcessingInstruction("Target", "Data");

    // Assert that nothing has changed
    verify(writer).writeProcessingInstruction(eq("Target"), eq("Data"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String)}
   * with {@code target}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String)}
   */
  @Test
  @DisplayName("Test writeProcessingInstruction(String) with 'target'; then calls writeProcessingInstruction(String)")
  void testWriteProcessingInstructionWithTarget_thenCallsWriteProcessingInstruction() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeProcessingInstruction(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeProcessingInstruction("Target");

    // Assert that nothing has changed
    verify(writer).writeProcessingInstruction(eq("Target"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeCData(String)}.
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCData(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegatingXMLStreamWriter#writeCData(String)}
   */
  @Test
  @DisplayName("Test writeCData(String); then calls writeCData(String)")
  void testWriteCData_thenCallsWriteCData() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCData(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeCData("Data");

    // Assert
    verify(writer).writeCData(eq("Data"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeDTD(String)}.
   * <ul>
   *   <li>Given {@link IndentingXMLStreamWriter}
   * {@link DelegatingXMLStreamWriter#writeDTD(String)} does nothing.</li>
   *   <li>Then calls {@link DelegatingXMLStreamWriter#writeDTD(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegatingXMLStreamWriter#writeDTD(String)}
   */
  @Test
  @DisplayName("Test writeDTD(String); given IndentingXMLStreamWriter writeDTD(String) does nothing; then calls writeDTD(String)")
  void testWriteDTD_givenIndentingXMLStreamWriterWriteDTDDoesNothing_thenCallsWriteDTD() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeDTD(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeDTD("Dtd");

    // Assert that nothing has changed
    verify(writer).writeDTD(eq("Dtd"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeEntityRef(String)}.
   * <ul>
   *   <li>Then calls {@link DelegatingXMLStreamWriter#writeEntityRef(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegatingXMLStreamWriter#writeEntityRef(String)}
   */
  @Test
  @DisplayName("Test writeEntityRef(String); then calls writeEntityRef(String)")
  void testWriteEntityRef_thenCallsWriteEntityRef() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEntityRef(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeEntityRef("Name");

    // Assert that nothing has changed
    verify(writer).writeEntityRef(eq("Name"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeStartDocument(String, String)}
   * with {@code encoding}, {@code version}.
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeStartDocument(String, String)}
   */
  @Test
  @DisplayName("Test writeStartDocument(String, String) with 'encoding', 'version'; then calls writeCharacters(String)")
  void testWriteStartDocumentWithEncodingVersion_thenCallsWriteCharacters() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeStartDocument(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeStartDocument("UTF-8", "1.0.2");

    // Assert that nothing has changed
    verify(writer).writeCharacters(eq("\n"));
    verify(writer).writeStartDocument(eq("UTF-8"), eq("1.0.2"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeStartDocument(String)} with
   * {@code version}.
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeStartDocument(String)}
   */
  @Test
  @DisplayName("Test writeStartDocument(String) with 'version'; then calls writeCharacters(String)")
  void testWriteStartDocumentWithVersion_thenCallsWriteCharacters() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeStartDocument(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeStartDocument("1.0.2");

    // Assert that nothing has changed
    verify(writer).writeCharacters(eq("\n"));
    verify(writer).writeStartDocument(eq("1.0.2"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeStartDocument()}.
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegatingXMLStreamWriter#writeStartDocument()}
   */
  @Test
  @DisplayName("Test writeStartDocument(); then calls writeCharacters(String)")
  void testWriteStartDocument_thenCallsWriteCharacters() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeStartDocument();

    // Act
    (new IndentingXMLStreamWriter(writer)).writeStartDocument();

    // Assert that nothing has changed
    verify(writer).writeCharacters(eq("\n"));
    verify(writer).writeStartDocument();
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeStartDocument()}.
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegatingXMLStreamWriter#writeStartDocument()}
   */
  @Test
  @DisplayName("Test writeStartDocument(); then calls writeCharacters(String)")
  void testWriteStartDocument_thenCallsWriteCharacters2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeStartDocument();

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).writeStartDocument();

    // Assert
    verify(writer, atLeast(1)).writeCharacters(eq("\n"));
    verify(writer).writeStartDocument();
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeCharacters(char[], int, int)} with
   * {@code text}, {@code start}, {@code len}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeCharacters(char[], int, int)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeCharacters(char[], int, int)}
   */
  @Test
  @DisplayName("Test writeCharacters(char[], int, int) with 'text', 'start', 'len'; then calls writeCharacters(char[], int, int)")
  void testWriteCharactersWithTextStartLen_thenCallsWriteCharacters() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<char[]>any(), anyInt(), anyInt());
    IndentingXMLStreamWriter indentingXMLStreamWriter = new IndentingXMLStreamWriter(writer);

    // Act
    indentingXMLStreamWriter.writeCharacters("AZAZ".toCharArray(), 1, 3);

    // Assert
    verify(writer).writeCharacters(isA(char[].class), eq(1), eq(3));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeCharacters(String)} with
   * {@code text}.
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegatingXMLStreamWriter#writeCharacters(String)}
   */
  @Test
  @DisplayName("Test writeCharacters(String) with 'text'; then calls writeCharacters(String)")
  void testWriteCharactersWithText_thenCallsWriteCharacters() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeCharacters("Text");

    // Assert
    verify(writer).writeCharacters(eq("Text"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeCharacters(String)} with
   * {@code text}.
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegatingXMLStreamWriter#writeCharacters(String)}
   */
  @Test
  @DisplayName("Test writeCharacters(String) with 'text'; then calls writeCharacters(String)")
  void testWriteCharactersWithText_thenCallsWriteCharacters2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).writeCharacters("Text");

    // Assert
    verify(writer).writeCharacters(eq("Text"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#getPrefix(String)}.
   * <ul>
   *   <li>Then return {@code Prefix}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegatingXMLStreamWriter#getPrefix(String)}
   */
  @Test
  @DisplayName("Test getPrefix(String); then return 'Prefix'")
  void testGetPrefix_thenReturnPrefix() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    when(writer.getPrefix(Mockito.<String>any())).thenReturn("Prefix");

    // Act
    String actualPrefix = (new IndentingXMLStreamWriter(writer)).getPrefix("Uri");

    // Assert
    verify(writer).getPrefix(eq("Uri"));
    assertEquals("Prefix", actualPrefix);
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#setPrefix(String, String)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#setPrefix(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#setPrefix(String, String)}
   */
  @Test
  @DisplayName("Test setPrefix(String, String); then calls setPrefix(String, String)")
  void testSetPrefix_thenCallsSetPrefix() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).setPrefix(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).setPrefix("Prefix", "Uri");

    // Assert that nothing has changed
    verify(writer).setPrefix(eq("Prefix"), eq("Uri"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#setPrefix(String, String)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#setPrefix(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#setPrefix(String, String)}
   */
  @Test
  @DisplayName("Test setPrefix(String, String); then calls setPrefix(String, String)")
  void testSetPrefix_thenCallsSetPrefix2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).setPrefix(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).setPrefix("Prefix", "Uri");

    // Assert that nothing has changed
    verify(writer).setPrefix(eq("Prefix"), eq("Uri"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#setDefaultNamespace(String)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#setDefaultNamespace(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#setDefaultNamespace(String)}
   */
  @Test
  @DisplayName("Test setDefaultNamespace(String); then calls setDefaultNamespace(String)")
  void testSetDefaultNamespace_thenCallsSetDefaultNamespace() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).setDefaultNamespace(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).setDefaultNamespace("Uri");

    // Assert that nothing has changed
    verify(writer).setDefaultNamespace(eq("Uri"));
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#setNamespaceContext(NamespaceContext)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#setNamespaceContext(NamespaceContext)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#setNamespaceContext(NamespaceContext)}
   */
  @Test
  @DisplayName("Test setNamespaceContext(NamespaceContext); then calls setNamespaceContext(NamespaceContext)")
  void testSetNamespaceContext_thenCallsSetNamespaceContext() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).setNamespaceContext(Mockito.<NamespaceContext>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).setNamespaceContext(null);

    // Assert that nothing has changed
    verify(writer).setNamespaceContext(isNull());
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#setNamespaceContext(NamespaceContext)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#setNamespaceContext(NamespaceContext)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegatingXMLStreamWriter#setNamespaceContext(NamespaceContext)}
   */
  @Test
  @DisplayName("Test setNamespaceContext(NamespaceContext); then calls setNamespaceContext(NamespaceContext)")
  void testSetNamespaceContext_thenCallsSetNamespaceContext2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).setNamespaceContext(Mockito.<NamespaceContext>any());

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).setNamespaceContext(null);

    // Assert that nothing has changed
    verify(writer).setNamespaceContext(isNull());
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#getNamespaceContext()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegatingXMLStreamWriter#getNamespaceContext()}
   */
  @Test
  @DisplayName("Test getNamespaceContext(); then return 'null'")
  void testGetNamespaceContext_thenReturnNull() {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    when(writer.getNamespaceContext()).thenReturn(null);

    // Act
    NamespaceContext actualNamespaceContext = (new IndentingXMLStreamWriter(writer)).getNamespaceContext();

    // Assert
    verify(writer).getNamespaceContext();
    assertNull(actualNamespaceContext);
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#getNamespaceContext()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegatingXMLStreamWriter#getNamespaceContext()}
   */
  @Test
  @DisplayName("Test getNamespaceContext(); then return 'null'")
  void testGetNamespaceContext_thenReturnNull2() {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    when(writer.getNamespaceContext()).thenReturn(null);

    // Act
    NamespaceContext actualNamespaceContext = (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer)))
        .getNamespaceContext();

    // Assert
    verify(writer).getNamespaceContext();
    assertNull(actualNamespaceContext);
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#getProperty(String)}.
   * <ul>
   *   <li>Then return {@code Property}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegatingXMLStreamWriter#getProperty(String)}
   */
  @Test
  @DisplayName("Test getProperty(String); then return 'Property'")
  void testGetProperty_thenReturnProperty() throws IllegalArgumentException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    when(writer.getProperty(Mockito.<String>any())).thenReturn("Property");

    // Act
    Object actualProperty = (new IndentingXMLStreamWriter(writer)).getProperty("Name");

    // Assert
    verify(writer).getProperty(eq("Name"));
    assertEquals("Property", actualProperty);
  }
}
