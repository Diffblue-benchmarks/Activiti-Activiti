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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DelegatingXMLStreamWriterDiffblueTest {
  /**
   * Test {@link DelegatingXMLStreamWriter#writeStartElement(String)} with {@code localName}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeStartElement(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeStartElement(String)}
   */
  @Test
  @DisplayName(
      "Test writeStartElement(String) with 'localName'; then calls writeStartElement(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeStartElement(String)"})
  void testWriteStartElementWithLocalName_thenCallsWriteStartElement() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeStartElement("Local Name");

    // Assert
    verify(writer).writeStartElement("Local Name");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeStartElement(String, String)} with {@code
   * namespaceURI}, {@code localName}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeStartElement(String, String)}
   */
  @Test
  @DisplayName(
      "Test writeStartElement(String, String) with 'namespaceURI', 'localName'; then calls writeStartElement(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeStartElement(String, String)"})
  void testWriteStartElementWithNamespaceURILocalName_thenCallsWriteStartElement()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).writeStartElement("Namespace URI", "Local Name");

    // Assert
    verify(writer).writeStartElement("Namespace URI", "Local Name");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeStartElement(String, String)} with {@code
   * namespaceURI}, {@code localName}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeStartElement(String, String)}
   */
  @Test
  @DisplayName(
      "Test writeStartElement(String, String) with 'namespaceURI', 'localName'; then calls writeStartElement(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeStartElement(String, String)"})
  void testWriteStartElementWithNamespaceURILocalName_thenCallsWriteStartElement2()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeStartElement("Namespace URI", "Local Name");

    // Assert
    verify(writer).writeStartElement("Namespace URI", "Local Name");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeStartElement(String, String, String)} with {@code
   * prefix}, {@code localName}, {@code namespaceURI}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeStartElement(String, String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeStartElement(String, String,
   * String)}
   */
  @Test
  @DisplayName(
      "Test writeStartElement(String, String, String) with 'prefix', 'localName', 'namespaceURI'; then calls writeStartElement(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeStartElement(String, String, String)"})
  void testWriteStartElementWithPrefixLocalNameNamespaceURI_thenCallsWriteStartElement()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2)
        .writeStartElement("Prefix", "Local Name", "Namespace URI");

    // Assert
    verify(writer).writeStartElement("Prefix", "Local Name", "Namespace URI");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeEmptyElement(String)} with {@code localName}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEmptyElement(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeEmptyElement(String)}
   */
  @Test
  @DisplayName(
      "Test writeEmptyElement(String) with 'localName'; then calls writeEmptyElement(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeEmptyElement(String)"})
  void testWriteEmptyElementWithLocalName_thenCallsWriteEmptyElement() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEmptyElement(Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).writeEmptyElement("Local Name");

    // Assert
    verify(writer).writeEmptyElement("Local Name");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeEmptyElement(String)} with {@code localName}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEmptyElement(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeEmptyElement(String)}
   */
  @Test
  @DisplayName(
      "Test writeEmptyElement(String) with 'localName'; then calls writeEmptyElement(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeEmptyElement(String)"})
  void testWriteEmptyElementWithLocalName_thenCallsWriteEmptyElement2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEmptyElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeEmptyElement("Local Name");

    // Assert
    verify(writer).writeEmptyElement("Local Name");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeEmptyElement(String, String)} with {@code
   * namespaceURI}, {@code localName}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEmptyElement(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeEmptyElement(String, String)}
   */
  @Test
  @DisplayName(
      "Test writeEmptyElement(String, String) with 'namespaceURI', 'localName'; then calls writeEmptyElement(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeEmptyElement(String, String)"})
  void testWriteEmptyElementWithNamespaceURILocalName_thenCallsWriteEmptyElement()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEmptyElement(Mockito.<String>any(), Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).writeEmptyElement("Namespace URI", "Local Name");

    // Assert
    verify(writer).writeEmptyElement("Namespace URI", "Local Name");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeEmptyElement(String, String)} with {@code
   * namespaceURI}, {@code localName}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEmptyElement(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeEmptyElement(String, String)}
   */
  @Test
  @DisplayName(
      "Test writeEmptyElement(String, String) with 'namespaceURI', 'localName'; then calls writeEmptyElement(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeEmptyElement(String, String)"})
  void testWriteEmptyElementWithNamespaceURILocalName_thenCallsWriteEmptyElement2()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEmptyElement(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeEmptyElement("Namespace URI", "Local Name");

    // Assert
    verify(writer).writeEmptyElement("Namespace URI", "Local Name");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeEmptyElement(String, String, String)} with {@code
   * prefix}, {@code localName}, {@code namespaceURI}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEmptyElement(String, String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeEmptyElement(String, String,
   * String)}
   */
  @Test
  @DisplayName(
      "Test writeEmptyElement(String, String, String) with 'prefix', 'localName', 'namespaceURI'; then calls writeEmptyElement(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeEmptyElement(String, String, String)"})
  void testWriteEmptyElementWithPrefixLocalNameNamespaceURI_thenCallsWriteEmptyElement()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeEmptyElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).writeEmptyElement("Prefix", "Local Name", "Namespace URI");

    // Assert
    verify(writer).writeEmptyElement("Prefix", "Local Name", "Namespace URI");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeEmptyElement(String, String, String)} with {@code
   * prefix}, {@code localName}, {@code namespaceURI}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEmptyElement(String, String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeEmptyElement(String, String,
   * String)}
   */
  @Test
  @DisplayName(
      "Test writeEmptyElement(String, String, String) with 'prefix', 'localName', 'namespaceURI'; then calls writeEmptyElement(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeEmptyElement(String, String, String)"})
  void testWriteEmptyElementWithPrefixLocalNameNamespaceURI_thenCallsWriteEmptyElement2()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeEmptyElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2)
        .writeEmptyElement("Prefix", "Local Name", "Namespace URI");

    // Assert
    verify(writer).writeEmptyElement("Prefix", "Local Name", "Namespace URI");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeEndDocument()}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEndDocument()}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeEndDocument()}
   */
  @Test
  @DisplayName("Test writeEndDocument(); then calls writeEndDocument()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeEndDocument()"})
  void testWriteEndDocument_thenCallsWriteEndDocument() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndDocument();

    // Act
    new IndentingXMLStreamWriter(writer).writeEndDocument();

    // Assert
    verify(writer).writeEndDocument();
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeEndDocument()}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEndDocument()}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeEndDocument()}
   */
  @Test
  @DisplayName("Test writeEndDocument(); then calls writeEndDocument()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeEndDocument()"})
  void testWriteEndDocument_thenCallsWriteEndDocument2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndDocument();
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeEndDocument();

    // Assert
    verify(writer).writeEndDocument();
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#close()}.
   *
   * <ul>
   *   <li>Given {@link IndentingXMLStreamWriter} {@link IndentingXMLStreamWriter#close()} does
   *       nothing.
   *   <li>Then calls {@link IndentingXMLStreamWriter#close()}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#close()}
   */
  @Test
  @DisplayName(
      "Test close(); given IndentingXMLStreamWriter close() does nothing; then calls close()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.close()"})
  void testClose_givenIndentingXMLStreamWriterCloseDoesNothing_thenCallsClose()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).close();

    // Act
    new IndentingXMLStreamWriter(writer).close();

    // Assert
    verify(writer).close();
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#close()}.
   *
   * <ul>
   *   <li>Given {@link IndentingXMLStreamWriter} {@link IndentingXMLStreamWriter#close()} does
   *       nothing.
   *   <li>Then calls {@link IndentingXMLStreamWriter#close()}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#close()}
   */
  @Test
  @DisplayName(
      "Test close(); given IndentingXMLStreamWriter close() does nothing; then calls close()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.close()"})
  void testClose_givenIndentingXMLStreamWriterCloseDoesNothing_thenCallsClose2()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).close();
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).close();

    // Assert
    verify(writer).close();
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#flush()}.
   *
   * <ul>
   *   <li>Given {@link IndentingXMLStreamWriter} {@link IndentingXMLStreamWriter#flush()} does
   *       nothing.
   *   <li>Then calls {@link IndentingXMLStreamWriter#flush()}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#flush()}
   */
  @Test
  @DisplayName(
      "Test flush(); given IndentingXMLStreamWriter flush() does nothing; then calls flush()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.flush()"})
  void testFlush_givenIndentingXMLStreamWriterFlushDoesNothing_thenCallsFlush()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).flush();

    // Act
    new IndentingXMLStreamWriter(writer).flush();

    // Assert
    verify(writer).flush();
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#flush()}.
   *
   * <ul>
   *   <li>Given {@link IndentingXMLStreamWriter} {@link IndentingXMLStreamWriter#flush()} does
   *       nothing.
   *   <li>Then calls {@link IndentingXMLStreamWriter#flush()}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#flush()}
   */
  @Test
  @DisplayName(
      "Test flush(); given IndentingXMLStreamWriter flush() does nothing; then calls flush()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.flush()"})
  void testFlush_givenIndentingXMLStreamWriterFlushDoesNothing_thenCallsFlush2()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).flush();
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).flush();

    // Assert
    verify(writer).flush();
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeAttribute(String, String)} with {@code localName},
   * {@code value}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}
   */
  @Test
  @DisplayName(
      "Test writeAttribute(String, String) with 'localName', 'value'; then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeAttribute(String, String)"})
  void testWriteAttributeWithLocalNameValue_thenCallsWriteAttribute() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).writeAttribute("Local Name", "42");

    // Assert
    verify(writer).writeAttribute("Local Name", "42");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeAttribute(String, String)} with {@code localName},
   * {@code value}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}
   */
  @Test
  @DisplayName(
      "Test writeAttribute(String, String) with 'localName', 'value'; then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeAttribute(String, String)"})
  void testWriteAttributeWithLocalNameValue_thenCallsWriteAttribute2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeAttribute("Local Name", "42");

    // Assert
    verify(writer).writeAttribute("Local Name", "42");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String)} with {@code
   * namespaceURI}, {@code localName}, {@code value}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test writeAttribute(String, String, String) with 'namespaceURI', 'localName', 'value'; then calls writeAttribute(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeAttribute(String, String, String)"})
  void testWriteAttributeWithNamespaceURILocalNameValue_thenCallsWriteAttribute()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).writeAttribute("Namespace URI", "Local Name", "42");

    // Assert
    verify(writer).writeAttribute("Namespace URI", "Local Name", "42");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String)} with {@code
   * namespaceURI}, {@code localName}, {@code value}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test writeAttribute(String, String, String) with 'namespaceURI', 'localName', 'value'; then calls writeAttribute(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeAttribute(String, String, String)"})
  void testWriteAttributeWithNamespaceURILocalNameValue_thenCallsWriteAttribute2()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeAttribute("Namespace URI", "Local Name", "42");

    // Assert
    verify(writer).writeAttribute("Namespace URI", "Local Name", "42");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String, String)} with
   * {@code prefix}, {@code namespaceURI}, {@code localName}, {@code value}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String,
   * String)}
   */
  @Test
  @DisplayName(
      "Test writeAttribute(String, String, String, String) with 'prefix', 'namespaceURI', 'localName', 'value'; then calls writeAttribute(String, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DelegatingXMLStreamWriter.writeAttribute(String, String, String, String)"
  })
  void testWriteAttributeWithPrefixNamespaceURILocalNameValue_thenCallsWriteAttribute()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer)
        .writeAttribute("Prefix", "Namespace URI", "Local Name", "42");

    // Assert
    verify(writer).writeAttribute("Prefix", "Namespace URI", "Local Name", "42");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeNamespace(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeNamespace(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeNamespace(String, String)}
   */
  @Test
  @DisplayName("Test writeNamespace(String, String); then calls writeNamespace(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeNamespace(String, String)"})
  void testWriteNamespace_thenCallsWriteNamespace() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).writeNamespace("Prefix", "Namespace URI");

    // Assert
    verify(writer).writeNamespace("Prefix", "Namespace URI");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeNamespace(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeNamespace(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeNamespace(String, String)}
   */
  @Test
  @DisplayName("Test writeNamespace(String, String); then calls writeNamespace(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeNamespace(String, String)"})
  void testWriteNamespace_thenCallsWriteNamespace2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeNamespace("Prefix", "Namespace URI");

    // Assert
    verify(writer).writeNamespace("Prefix", "Namespace URI");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeDefaultNamespace(String)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeDefaultNamespace(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeDefaultNamespace(String)}
   */
  @Test
  @DisplayName("Test writeDefaultNamespace(String); then calls writeDefaultNamespace(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeDefaultNamespace(String)"})
  void testWriteDefaultNamespace_thenCallsWriteDefaultNamespace() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeDefaultNamespace(Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).writeDefaultNamespace("Namespace URI");

    // Assert
    verify(writer).writeDefaultNamespace("Namespace URI");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeDefaultNamespace(String)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeDefaultNamespace(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeDefaultNamespace(String)}
   */
  @Test
  @DisplayName("Test writeDefaultNamespace(String); then calls writeDefaultNamespace(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeDefaultNamespace(String)"})
  void testWriteDefaultNamespace_thenCallsWriteDefaultNamespace2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeDefaultNamespace(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeDefaultNamespace("Namespace URI");

    // Assert
    verify(writer).writeDefaultNamespace("Namespace URI");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeComment(String)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeComment(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeComment(String)}
   */
  @Test
  @DisplayName("Test writeComment(String); then calls writeComment(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeComment(String)"})
  void testWriteComment_thenCallsWriteComment() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeComment(Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).writeComment("Data");

    // Assert
    verify(writer).writeComment("Data");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeComment(String)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeComment(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeComment(String)}
   */
  @Test
  @DisplayName("Test writeComment(String); then calls writeComment(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeComment(String)"})
  void testWriteComment_thenCallsWriteComment2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeComment(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeComment("Data");

    // Assert
    verify(writer).writeComment("Data");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String, String)} with {@code
   * target}, {@code data}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeProcessingInstruction(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String,
   * String)}
   */
  @Test
  @DisplayName(
      "Test writeProcessingInstruction(String, String) with 'target', 'data'; then calls writeProcessingInstruction(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeProcessingInstruction(String, String)"})
  void testWriteProcessingInstructionWithTargetData_thenCallsWriteProcessingInstruction()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeProcessingInstruction(Mockito.<String>any(), Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).writeProcessingInstruction("Target", "Data");

    // Assert
    verify(writer).writeProcessingInstruction("Target", "Data");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String, String)} with {@code
   * target}, {@code data}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeProcessingInstruction(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String,
   * String)}
   */
  @Test
  @DisplayName(
      "Test writeProcessingInstruction(String, String) with 'target', 'data'; then calls writeProcessingInstruction(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeProcessingInstruction(String, String)"})
  void testWriteProcessingInstructionWithTargetData_thenCallsWriteProcessingInstruction2()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeProcessingInstruction(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeProcessingInstruction("Target", "Data");

    // Assert
    verify(writer).writeProcessingInstruction("Target", "Data");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String)} with {@code target}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeProcessingInstruction(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String)}
   */
  @Test
  @DisplayName(
      "Test writeProcessingInstruction(String) with 'target'; then calls writeProcessingInstruction(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeProcessingInstruction(String)"})
  void testWriteProcessingInstructionWithTarget_thenCallsWriteProcessingInstruction()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeProcessingInstruction(Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).writeProcessingInstruction("Target");

    // Assert
    verify(writer).writeProcessingInstruction("Target");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String)} with {@code target}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeProcessingInstruction(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String)}
   */
  @Test
  @DisplayName(
      "Test writeProcessingInstruction(String) with 'target'; then calls writeProcessingInstruction(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeProcessingInstruction(String)"})
  void testWriteProcessingInstructionWithTarget_thenCallsWriteProcessingInstruction2()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeProcessingInstruction(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeProcessingInstruction("Target");

    // Assert
    verify(writer).writeProcessingInstruction("Target");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeCData(String)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCData(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeCData(String)}
   */
  @Test
  @DisplayName("Test writeCData(String); then calls writeCData(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeCData(String)"})
  void testWriteCData_thenCallsWriteCData() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCData(Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).writeCData("Data");

    // Assert
    verify(writer).writeCData("Data");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeCData(String)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCData(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeCData(String)}
   */
  @Test
  @DisplayName("Test writeCData(String); then calls writeCData(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeCData(String)"})
  void testWriteCData_thenCallsWriteCData2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCData(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeCData("Data");

    // Assert
    verify(writer).writeCData("Data");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeDTD(String)}.
   *
   * <ul>
   *   <li>Given {@link IndentingXMLStreamWriter} {@link IndentingXMLStreamWriter#writeDTD(String)}
   *       does nothing.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeDTD(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeDTD(String)}
   */
  @Test
  @DisplayName(
      "Test writeDTD(String); given IndentingXMLStreamWriter writeDTD(String) does nothing; then calls writeDTD(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeDTD(String)"})
  void testWriteDTD_givenIndentingXMLStreamWriterWriteDTDDoesNothing_thenCallsWriteDTD()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeDTD(Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).writeDTD("Dtd");

    // Assert
    verify(writer).writeDTD("Dtd");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeDTD(String)}.
   *
   * <ul>
   *   <li>Given {@link IndentingXMLStreamWriter} {@link IndentingXMLStreamWriter#writeDTD(String)}
   *       does nothing.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeDTD(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeDTD(String)}
   */
  @Test
  @DisplayName(
      "Test writeDTD(String); given IndentingXMLStreamWriter writeDTD(String) does nothing; then calls writeDTD(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeDTD(String)"})
  void testWriteDTD_givenIndentingXMLStreamWriterWriteDTDDoesNothing_thenCallsWriteDTD2()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeDTD(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeDTD("Dtd");

    // Assert
    verify(writer).writeDTD("Dtd");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeEntityRef(String)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEntityRef(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeEntityRef(String)}
   */
  @Test
  @DisplayName("Test writeEntityRef(String); then calls writeEntityRef(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeEntityRef(String)"})
  void testWriteEntityRef_thenCallsWriteEntityRef() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEntityRef(Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).writeEntityRef("Name");

    // Assert
    verify(writer).writeEntityRef("Name");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeEntityRef(String)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEntityRef(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeEntityRef(String)}
   */
  @Test
  @DisplayName("Test writeEntityRef(String); then calls writeEntityRef(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeEntityRef(String)"})
  void testWriteEntityRef_thenCallsWriteEntityRef2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEntityRef(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeEntityRef("Name");

    // Assert
    verify(writer).writeEntityRef("Name");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeStartDocument(String, String)} with {@code
   * encoding}, {@code version}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeStartDocument(String, String)}
   */
  @Test
  @DisplayName(
      "Test writeStartDocument(String, String) with 'encoding', 'version'; then calls writeCharacters(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeStartDocument(String, String)"})
  void testWriteStartDocumentWithEncodingVersion_thenCallsWriteCharacters()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeStartDocument(Mockito.<String>any(), Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).writeStartDocument("UTF-8", "1.0.2");

    // Assert
    verify(writer).writeCharacters("\n");
    verify(writer).writeStartDocument("UTF-8", "1.0.2");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeStartDocument(String, String)} with {@code
   * encoding}, {@code version}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeStartDocument(String, String)}
   */
  @Test
  @DisplayName(
      "Test writeStartDocument(String, String) with 'encoding', 'version'; then calls writeCharacters(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeStartDocument(String, String)"})
  void testWriteStartDocumentWithEncodingVersion_thenCallsWriteCharacters2()
      throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeStartDocument(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeStartDocument("UTF-8", "1.0.2");

    // Assert
    verify(writer, atLeast(1)).writeCharacters("\n");
    verify(writer).writeStartDocument("UTF-8", "1.0.2");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeStartDocument(String)} with {@code version}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeStartDocument(String)}
   */
  @Test
  @DisplayName("Test writeStartDocument(String) with 'version'; then calls writeCharacters(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeStartDocument(String)"})
  void testWriteStartDocumentWithVersion_thenCallsWriteCharacters() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeStartDocument(Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).writeStartDocument("1.0.2");

    // Assert
    verify(writer).writeCharacters("\n");
    verify(writer).writeStartDocument("1.0.2");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeStartDocument(String)} with {@code version}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeStartDocument(String)}
   */
  @Test
  @DisplayName("Test writeStartDocument(String) with 'version'; then calls writeCharacters(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeStartDocument(String)"})
  void testWriteStartDocumentWithVersion_thenCallsWriteCharacters2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeStartDocument(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeStartDocument("1.0.2");

    // Assert
    verify(writer, atLeast(1)).writeCharacters("\n");
    verify(writer).writeStartDocument("1.0.2");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeStartDocument()}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeStartDocument()}
   */
  @Test
  @DisplayName("Test writeStartDocument(); then calls writeCharacters(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeStartDocument()"})
  void testWriteStartDocument_thenCallsWriteCharacters() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeStartDocument();

    // Act
    new IndentingXMLStreamWriter(writer).writeStartDocument();

    // Assert
    verify(writer).writeCharacters("\n");
    verify(writer).writeStartDocument();
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeStartDocument()}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeStartDocument()}
   */
  @Test
  @DisplayName("Test writeStartDocument(); then calls writeCharacters(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeStartDocument()"})
  void testWriteStartDocument_thenCallsWriteCharacters2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeStartDocument();
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeStartDocument();

    // Assert
    verify(writer, atLeast(1)).writeCharacters("\n");
    verify(writer).writeStartDocument();
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeCharacters(char[], int, int)} with {@code text},
   * {@code start}, {@code len}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(char[], int, int)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeCharacters(char[], int, int)}
   */
  @Test
  @DisplayName(
      "Test writeCharacters(char[], int, int) with 'text', 'start', 'len'; then calls writeCharacters(char[], int, int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeCharacters(char[], int, int)"})
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
   * Test {@link DelegatingXMLStreamWriter#writeCharacters(String)} with {@code text}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeCharacters(String)}
   */
  @Test
  @DisplayName("Test writeCharacters(String) with 'text'; then calls writeCharacters(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeCharacters(String)"})
  void testWriteCharactersWithText_thenCallsWriteCharacters() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).writeCharacters("Text");

    // Assert
    verify(writer).writeCharacters("Text");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#writeCharacters(String)} with {@code text}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#writeCharacters(String)}
   */
  @Test
  @DisplayName("Test writeCharacters(String) with 'text'; then calls writeCharacters(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.writeCharacters(String)"})
  void testWriteCharactersWithText_thenCallsWriteCharacters2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).writeCharacters("Text");

    // Assert
    verify(writer).writeCharacters("Text");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#getPrefix(String)}.
   *
   * <ul>
   *   <li>Then return {@code Prefix}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#getPrefix(String)}
   */
  @Test
  @DisplayName("Test getPrefix(String); then return 'Prefix'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String DelegatingXMLStreamWriter.getPrefix(String)"})
  void testGetPrefix_thenReturnPrefix() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    when(writer.getPrefix(Mockito.<String>any())).thenReturn("Prefix");

    // Act
    String actualPrefix = new IndentingXMLStreamWriter(writer).getPrefix("Uri");

    // Assert
    verify(writer).getPrefix("Uri");
    assertEquals("Prefix", actualPrefix);
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#getPrefix(String)}.
   *
   * <ul>
   *   <li>Then return {@code Prefix}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#getPrefix(String)}
   */
  @Test
  @DisplayName("Test getPrefix(String); then return 'Prefix'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String DelegatingXMLStreamWriter.getPrefix(String)"})
  void testGetPrefix_thenReturnPrefix2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    when(writer.getPrefix(Mockito.<String>any())).thenReturn("Prefix");
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    String actualPrefix = new IndentingXMLStreamWriter(writer2).getPrefix("Uri");

    // Assert
    verify(writer).getPrefix("Uri");
    assertEquals("Prefix", actualPrefix);
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#setPrefix(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#setPrefix(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#setPrefix(String, String)}
   */
  @Test
  @DisplayName("Test setPrefix(String, String); then calls setPrefix(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.setPrefix(String, String)"})
  void testSetPrefix_thenCallsSetPrefix() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).setPrefix(Mockito.<String>any(), Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).setPrefix("Prefix", "Uri");

    // Assert
    verify(writer).setPrefix("Prefix", "Uri");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#setPrefix(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#setPrefix(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#setPrefix(String, String)}
   */
  @Test
  @DisplayName("Test setPrefix(String, String); then calls setPrefix(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.setPrefix(String, String)"})
  void testSetPrefix_thenCallsSetPrefix2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).setPrefix(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).setPrefix("Prefix", "Uri");

    // Assert
    verify(writer).setPrefix("Prefix", "Uri");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#setDefaultNamespace(String)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#setDefaultNamespace(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#setDefaultNamespace(String)}
   */
  @Test
  @DisplayName("Test setDefaultNamespace(String); then calls setDefaultNamespace(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.setDefaultNamespace(String)"})
  void testSetDefaultNamespace_thenCallsSetDefaultNamespace() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).setDefaultNamespace(Mockito.<String>any());

    // Act
    new IndentingXMLStreamWriter(writer).setDefaultNamespace("Uri");

    // Assert
    verify(writer).setDefaultNamespace("Uri");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#setDefaultNamespace(String)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#setDefaultNamespace(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#setDefaultNamespace(String)}
   */
  @Test
  @DisplayName("Test setDefaultNamespace(String); then calls setDefaultNamespace(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.setDefaultNamespace(String)"})
  void testSetDefaultNamespace_thenCallsSetDefaultNamespace2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).setDefaultNamespace(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).setDefaultNamespace("Uri");

    // Assert
    verify(writer).setDefaultNamespace("Uri");
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#setNamespaceContext(NamespaceContext)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#setNamespaceContext(NamespaceContext)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#setNamespaceContext(NamespaceContext)}
   */
  @Test
  @DisplayName(
      "Test setNamespaceContext(NamespaceContext); then calls setNamespaceContext(NamespaceContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.setNamespaceContext(NamespaceContext)"})
  void testSetNamespaceContext_thenCallsSetNamespaceContext() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).setNamespaceContext(Mockito.<NamespaceContext>any());

    // Act
    new IndentingXMLStreamWriter(writer).setNamespaceContext(null);

    // Assert
    verify(writer).setNamespaceContext(isNull());
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#setNamespaceContext(NamespaceContext)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#setNamespaceContext(NamespaceContext)}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#setNamespaceContext(NamespaceContext)}
   */
  @Test
  @DisplayName(
      "Test setNamespaceContext(NamespaceContext); then calls setNamespaceContext(NamespaceContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegatingXMLStreamWriter.setNamespaceContext(NamespaceContext)"})
  void testSetNamespaceContext_thenCallsSetNamespaceContext2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).setNamespaceContext(Mockito.<NamespaceContext>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    new IndentingXMLStreamWriter(writer2).setNamespaceContext(null);

    // Assert
    verify(writer).setNamespaceContext(isNull());
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#getNamespaceContext()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#getNamespaceContext()}
   */
  @Test
  @DisplayName("Test getNamespaceContext(); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"NamespaceContext DelegatingXMLStreamWriter.getNamespaceContext()"})
  void testGetNamespaceContext_thenReturnNull() {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    when(writer.getNamespaceContext()).thenReturn(null);

    // Act
    NamespaceContext actualNamespaceContext =
        new IndentingXMLStreamWriter(writer).getNamespaceContext();

    // Assert
    verify(writer).getNamespaceContext();
    assertNull(actualNamespaceContext);
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#getNamespaceContext()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#getNamespaceContext()}
   */
  @Test
  @DisplayName("Test getNamespaceContext(); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"NamespaceContext DelegatingXMLStreamWriter.getNamespaceContext()"})
  void testGetNamespaceContext_thenReturnNull2() {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    when(writer.getNamespaceContext()).thenReturn(null);
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    NamespaceContext actualNamespaceContext =
        new IndentingXMLStreamWriter(writer2).getNamespaceContext();

    // Assert
    verify(writer).getNamespaceContext();
    assertNull(actualNamespaceContext);
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#getProperty(String)}.
   *
   * <ul>
   *   <li>Then return {@code Property}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#getProperty(String)}
   */
  @Test
  @DisplayName("Test getProperty(String); then return 'Property'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object DelegatingXMLStreamWriter.getProperty(String)"})
  void testGetProperty_thenReturnProperty() throws IllegalArgumentException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    when(writer.getProperty(Mockito.<String>any())).thenReturn("Property");

    // Act
    Object actualProperty = new IndentingXMLStreamWriter(writer).getProperty("Name");

    // Assert
    verify(writer).getProperty("Name");
    assertEquals("Property", actualProperty);
  }

  /**
   * Test {@link DelegatingXMLStreamWriter#getProperty(String)}.
   *
   * <ul>
   *   <li>Then return {@code Property}.
   * </ul>
   *
   * <p>Method under test: {@link DelegatingXMLStreamWriter#getProperty(String)}
   */
  @Test
  @DisplayName("Test getProperty(String); then return 'Property'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object DelegatingXMLStreamWriter.getProperty(String)"})
  void testGetProperty_thenReturnProperty2() throws IllegalArgumentException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    when(writer.getProperty(Mockito.<String>any())).thenReturn("Property");
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    Object actualProperty = new IndentingXMLStreamWriter(writer2).getProperty("Name");

    // Assert
    verify(writer).getProperty("Name");
    assertEquals("Property", actualProperty);
  }
}
