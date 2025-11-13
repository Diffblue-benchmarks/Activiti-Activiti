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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IndentingXMLStreamWriterDiffblueTest {
  @InjectMocks private IndentingXMLStreamWriter indentingXMLStreamWriter;

  @Mock private XMLStreamWriter xMLStreamWriter;

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
   * <p>Method under test: {@link IndentingXMLStreamWriter#getIndentStep()}
   */
  @Test
  @DisplayName("Test getIndentStep()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int IndentingXMLStreamWriter.getIndentStep()"})
  void testGetIndentStep() {
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

  /**
   * Test {@link IndentingXMLStreamWriter#writeStartDocument(String, String)} with {@code encoding},
   * {@code version}.
   *
   * <ul>
   *   <li>Then calls {@link XMLStreamWriter#writeCharacters(String)}.
   * </ul>
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeStartDocument(String, String)}
   */
  @Test
  @DisplayName(
      "Test writeStartDocument(String, String) with 'encoding', 'version'; then calls writeCharacters(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeStartDocument(String, String)"})
  void testWriteStartDocumentWithEncodingVersion_thenCallsWriteCharacters()
      throws XMLStreamException {
    // Arrange
    doNothing().when(xMLStreamWriter).writeCharacters(Mockito.<String>any());
    doNothing()
        .when(xMLStreamWriter)
        .writeStartDocument(Mockito.<String>any(), Mockito.<String>any());

    // Act
    indentingXMLStreamWriter.writeStartDocument("UTF-8", "1.0.2");

    // Assert
    verify(xMLStreamWriter).writeCharacters("\n");
    verify(xMLStreamWriter).writeStartDocument("UTF-8", "1.0.2");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeStartDocument(String, String)} with {@code encoding},
   * {@code version}.
   *
   * <ul>
   *   <li>Then throw {@link XMLStreamException}.
   * </ul>
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeStartDocument(String, String)}
   */
  @Test
  @DisplayName(
      "Test writeStartDocument(String, String) with 'encoding', 'version'; then throw XMLStreamException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeStartDocument(String, String)"})
  void testWriteStartDocumentWithEncodingVersion_thenThrowXMLStreamException()
      throws XMLStreamException {
    // Arrange
    doThrow(new XMLStreamException())
        .when(xMLStreamWriter)
        .writeStartDocument(Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () -> indentingXMLStreamWriter.writeStartDocument("UTF-8", "1.0.2"));
    verify(xMLStreamWriter).writeStartDocument("UTF-8", "1.0.2");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeStartDocument(String)} with {@code version}.
   *
   * <ul>
   *   <li>Then calls {@link XMLStreamWriter#writeCharacters(String)}.
   * </ul>
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeStartDocument(String)}
   */
  @Test
  @DisplayName("Test writeStartDocument(String) with 'version'; then calls writeCharacters(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeStartDocument(String)"})
  void testWriteStartDocumentWithVersion_thenCallsWriteCharacters() throws XMLStreamException {
    // Arrange
    doNothing().when(xMLStreamWriter).writeCharacters(Mockito.<String>any());
    doNothing().when(xMLStreamWriter).writeStartDocument(Mockito.<String>any());

    // Act
    indentingXMLStreamWriter.writeStartDocument("1.0.2");

    // Assert
    verify(xMLStreamWriter).writeCharacters("\n");
    verify(xMLStreamWriter).writeStartDocument("1.0.2");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeStartDocument(String)} with {@code version}.
   *
   * <ul>
   *   <li>Then throw {@link XMLStreamException}.
   * </ul>
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeStartDocument(String)}
   */
  @Test
  @DisplayName("Test writeStartDocument(String) with 'version'; then throw XMLStreamException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeStartDocument(String)"})
  void testWriteStartDocumentWithVersion_thenThrowXMLStreamException() throws XMLStreamException {
    // Arrange
    doThrow(new XMLStreamException())
        .when(xMLStreamWriter)
        .writeStartDocument(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class, () -> indentingXMLStreamWriter.writeStartDocument("1.0.2"));
    verify(xMLStreamWriter).writeStartDocument("1.0.2");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeStartDocument()}.
   *
   * <ul>
   *   <li>Then calls {@link XMLStreamWriter#writeCharacters(String)}.
   * </ul>
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeStartDocument()}
   */
  @Test
  @DisplayName("Test writeStartDocument(); then calls writeCharacters(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeStartDocument()"})
  void testWriteStartDocument_thenCallsWriteCharacters() throws XMLStreamException {
    // Arrange
    doNothing().when(xMLStreamWriter).writeCharacters(Mockito.<String>any());
    doNothing().when(xMLStreamWriter).writeStartDocument();

    // Act
    indentingXMLStreamWriter.writeStartDocument();

    // Assert
    verify(xMLStreamWriter).writeCharacters("\n");
    verify(xMLStreamWriter).writeStartDocument();
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeStartDocument()}.
   *
   * <ul>
   *   <li>Then throw {@link XMLStreamException}.
   * </ul>
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeStartDocument()}
   */
  @Test
  @DisplayName("Test writeStartDocument(); then throw XMLStreamException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeStartDocument()"})
  void testWriteStartDocument_thenThrowXMLStreamException() throws XMLStreamException {
    // Arrange
    doThrow(new XMLStreamException()).when(xMLStreamWriter).writeStartDocument();

    // Act and Assert
    assertThrows(XMLStreamException.class, () -> indentingXMLStreamWriter.writeStartDocument());
    verify(xMLStreamWriter).writeStartDocument();
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeStartElement(String)} with {@code localName}.
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeStartElement(String)}
   */
  @Test
  @DisplayName("Test writeStartElement(String) with 'localName'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeStartElement(String)"})
  void testWriteStartElementWithLocalName() throws XMLStreamException {
    // Arrange
    doNothing().when(xMLStreamWriter).writeStartElement(Mockito.<String>any());

    // Act
    indentingXMLStreamWriter.writeStartElement("Local Name");

    // Assert
    verify(xMLStreamWriter).writeStartElement("Local Name");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeStartElement(String)} with {@code localName}.
   *
   * <ul>
   *   <li>Then throw {@link XMLStreamException}.
   * </ul>
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeStartElement(String)}
   */
  @Test
  @DisplayName("Test writeStartElement(String) with 'localName'; then throw XMLStreamException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeStartElement(String)"})
  void testWriteStartElementWithLocalName_thenThrowXMLStreamException() throws XMLStreamException {
    // Arrange
    doThrow(new XMLStreamException())
        .when(xMLStreamWriter)
        .writeStartElement(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class, () -> indentingXMLStreamWriter.writeStartElement("Local Name"));
    verify(xMLStreamWriter).writeStartElement("Local Name");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeStartElement(String, String)} with {@code
   * namespaceURI}, {@code localName}.
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeStartElement(String, String)}
   */
  @Test
  @DisplayName("Test writeStartElement(String, String) with 'namespaceURI', 'localName'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeStartElement(String, String)"})
  void testWriteStartElementWithNamespaceURILocalName() throws XMLStreamException {
    // Arrange
    doNothing()
        .when(xMLStreamWriter)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any());

    // Act
    indentingXMLStreamWriter.writeStartElement("Namespace URI", "Local Name");

    // Assert
    verify(xMLStreamWriter).writeStartElement("Namespace URI", "Local Name");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeStartElement(String, String)} with {@code
   * namespaceURI}, {@code localName}.
   *
   * <ul>
   *   <li>Then throw {@link XMLStreamException}.
   * </ul>
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeStartElement(String, String)}
   */
  @Test
  @DisplayName(
      "Test writeStartElement(String, String) with 'namespaceURI', 'localName'; then throw XMLStreamException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeStartElement(String, String)"})
  void testWriteStartElementWithNamespaceURILocalName_thenThrowXMLStreamException()
      throws XMLStreamException {
    // Arrange
    doThrow(new XMLStreamException())
        .when(xMLStreamWriter)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () -> indentingXMLStreamWriter.writeStartElement("Namespace URI", "Local Name"));
    verify(xMLStreamWriter).writeStartElement("Namespace URI", "Local Name");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeStartElement(String, String, String)} with {@code
   * prefix}, {@code localName}, {@code namespaceURI}.
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeStartElement(String, String,
   * String)}
   */
  @Test
  @DisplayName(
      "Test writeStartElement(String, String, String) with 'prefix', 'localName', 'namespaceURI'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeStartElement(String, String, String)"})
  void testWriteStartElementWithPrefixLocalNameNamespaceURI() throws XMLStreamException {
    // Arrange
    doNothing()
        .when(xMLStreamWriter)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    indentingXMLStreamWriter.writeStartElement("Prefix", "Local Name", "Namespace URI");

    // Assert
    verify(xMLStreamWriter).writeStartElement("Prefix", "Local Name", "Namespace URI");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeStartElement(String, String, String)} with {@code
   * prefix}, {@code localName}, {@code namespaceURI}.
   *
   * <ul>
   *   <li>Then throw {@link XMLStreamException}.
   * </ul>
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeStartElement(String, String,
   * String)}
   */
  @Test
  @DisplayName(
      "Test writeStartElement(String, String, String) with 'prefix', 'localName', 'namespaceURI'; then throw XMLStreamException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeStartElement(String, String, String)"})
  void testWriteStartElementWithPrefixLocalNameNamespaceURI_thenThrowXMLStreamException()
      throws XMLStreamException {
    // Arrange
    doThrow(new XMLStreamException())
        .when(xMLStreamWriter)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () -> indentingXMLStreamWriter.writeStartElement("Prefix", "Local Name", "Namespace URI"));
    verify(xMLStreamWriter).writeStartElement("Prefix", "Local Name", "Namespace URI");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeEmptyElement(String)} with {@code localName}.
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeEmptyElement(String)}
   */
  @Test
  @DisplayName("Test writeEmptyElement(String) with 'localName'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeEmptyElement(String)"})
  void testWriteEmptyElementWithLocalName() throws XMLStreamException {
    // Arrange
    doNothing().when(xMLStreamWriter).writeEmptyElement(Mockito.<String>any());

    // Act
    indentingXMLStreamWriter.writeEmptyElement("Local Name");

    // Assert
    verify(xMLStreamWriter).writeEmptyElement("Local Name");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeEmptyElement(String)} with {@code localName}.
   *
   * <ul>
   *   <li>Then throw {@link XMLStreamException}.
   * </ul>
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeEmptyElement(String)}
   */
  @Test
  @DisplayName("Test writeEmptyElement(String) with 'localName'; then throw XMLStreamException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeEmptyElement(String)"})
  void testWriteEmptyElementWithLocalName_thenThrowXMLStreamException() throws XMLStreamException {
    // Arrange
    doThrow(new XMLStreamException())
        .when(xMLStreamWriter)
        .writeEmptyElement(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class, () -> indentingXMLStreamWriter.writeEmptyElement("Local Name"));
    verify(xMLStreamWriter).writeEmptyElement("Local Name");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeEmptyElement(String, String)} with {@code
   * namespaceURI}, {@code localName}.
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeEmptyElement(String, String)}
   */
  @Test
  @DisplayName("Test writeEmptyElement(String, String) with 'namespaceURI', 'localName'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeEmptyElement(String, String)"})
  void testWriteEmptyElementWithNamespaceURILocalName() throws XMLStreamException {
    // Arrange
    doNothing()
        .when(xMLStreamWriter)
        .writeEmptyElement(Mockito.<String>any(), Mockito.<String>any());

    // Act
    indentingXMLStreamWriter.writeEmptyElement("Namespace URI", "Local Name");

    // Assert
    verify(xMLStreamWriter).writeEmptyElement("Namespace URI", "Local Name");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeEmptyElement(String, String)} with {@code
   * namespaceURI}, {@code localName}.
   *
   * <ul>
   *   <li>Then throw {@link XMLStreamException}.
   * </ul>
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeEmptyElement(String, String)}
   */
  @Test
  @DisplayName(
      "Test writeEmptyElement(String, String) with 'namespaceURI', 'localName'; then throw XMLStreamException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeEmptyElement(String, String)"})
  void testWriteEmptyElementWithNamespaceURILocalName_thenThrowXMLStreamException()
      throws XMLStreamException {
    // Arrange
    doThrow(new XMLStreamException())
        .when(xMLStreamWriter)
        .writeEmptyElement(Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () -> indentingXMLStreamWriter.writeEmptyElement("Namespace URI", "Local Name"));
    verify(xMLStreamWriter).writeEmptyElement("Namespace URI", "Local Name");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeEmptyElement(String, String, String)} with {@code
   * prefix}, {@code localName}, {@code namespaceURI}.
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeEmptyElement(String, String,
   * String)}
   */
  @Test
  @DisplayName(
      "Test writeEmptyElement(String, String, String) with 'prefix', 'localName', 'namespaceURI'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeEmptyElement(String, String, String)"})
  void testWriteEmptyElementWithPrefixLocalNameNamespaceURI() throws XMLStreamException {
    // Arrange
    doNothing()
        .when(xMLStreamWriter)
        .writeEmptyElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    indentingXMLStreamWriter.writeEmptyElement("Prefix", "Local Name", "Namespace URI");

    // Assert
    verify(xMLStreamWriter).writeEmptyElement("Prefix", "Local Name", "Namespace URI");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeEmptyElement(String, String, String)} with {@code
   * prefix}, {@code localName}, {@code namespaceURI}.
   *
   * <ul>
   *   <li>Then throw {@link XMLStreamException}.
   * </ul>
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeEmptyElement(String, String,
   * String)}
   */
  @Test
  @DisplayName(
      "Test writeEmptyElement(String, String, String) with 'prefix', 'localName', 'namespaceURI'; then throw XMLStreamException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeEmptyElement(String, String, String)"})
  void testWriteEmptyElementWithPrefixLocalNameNamespaceURI_thenThrowXMLStreamException()
      throws XMLStreamException {
    // Arrange
    doThrow(new XMLStreamException())
        .when(xMLStreamWriter)
        .writeEmptyElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () -> indentingXMLStreamWriter.writeEmptyElement("Prefix", "Local Name", "Namespace URI"));
    verify(xMLStreamWriter).writeEmptyElement("Prefix", "Local Name", "Namespace URI");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeCharacters(char[], int, int)} with {@code text},
   * {@code start}, {@code len}.
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeCharacters(char[], int, int)}
   */
  @Test
  @DisplayName("Test writeCharacters(char[], int, int) with 'text', 'start', 'len'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeCharacters(char[], int, int)"})
  void testWriteCharactersWithTextStartLen() throws XMLStreamException {
    // Arrange
    doNothing().when(xMLStreamWriter).writeCharacters(Mockito.<char[]>any(), anyInt(), anyInt());

    // Act
    indentingXMLStreamWriter.writeCharacters("AZAZ".toCharArray(), 1, 3);

    // Assert
    verify(xMLStreamWriter).writeCharacters(isA(char[].class), eq(1), eq(3));
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeCharacters(char[], int, int)} with {@code text},
   * {@code start}, {@code len}.
   *
   * <ul>
   *   <li>Then throw {@link XMLStreamException}.
   * </ul>
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeCharacters(char[], int, int)}
   */
  @Test
  @DisplayName(
      "Test writeCharacters(char[], int, int) with 'text', 'start', 'len'; then throw XMLStreamException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeCharacters(char[], int, int)"})
  void testWriteCharactersWithTextStartLen_thenThrowXMLStreamException() throws XMLStreamException {
    // Arrange
    doThrow(new XMLStreamException())
        .when(xMLStreamWriter)
        .writeCharacters(Mockito.<char[]>any(), anyInt(), anyInt());

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () -> indentingXMLStreamWriter.writeCharacters("AZAZ".toCharArray(), 1, 3));
    verify(xMLStreamWriter).writeCharacters(isA(char[].class), eq(1), eq(3));
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeCharacters(String)} with {@code text}.
   *
   * <ul>
   *   <li>Given {@link XMLStreamWriter} {@link XMLStreamWriter#writeCharacters(String)} does
   *       nothing.
   * </ul>
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeCharacters(String)}
   */
  @Test
  @DisplayName(
      "Test writeCharacters(String) with 'text'; given XMLStreamWriter writeCharacters(String) does nothing")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeCharacters(String)"})
  void testWriteCharactersWithText_givenXMLStreamWriterWriteCharactersDoesNothing()
      throws XMLStreamException {
    // Arrange
    doNothing().when(xMLStreamWriter).writeCharacters(Mockito.<String>any());

    // Act
    indentingXMLStreamWriter.writeCharacters("Text");

    // Assert
    verify(xMLStreamWriter).writeCharacters("Text");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeCharacters(String)} with {@code text}.
   *
   * <ul>
   *   <li>Then throw {@link XMLStreamException}.
   * </ul>
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeCharacters(String)}
   */
  @Test
  @DisplayName("Test writeCharacters(String) with 'text'; then throw XMLStreamException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeCharacters(String)"})
  void testWriteCharactersWithText_thenThrowXMLStreamException() throws XMLStreamException {
    // Arrange
    doThrow(new XMLStreamException()).when(xMLStreamWriter).writeCharacters(Mockito.<String>any());

    // Act and Assert
    assertThrows(XMLStreamException.class, () -> indentingXMLStreamWriter.writeCharacters("Text"));
    verify(xMLStreamWriter).writeCharacters("Text");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeCData(String)}.
   *
   * <ul>
   *   <li>Given {@link XMLStreamWriter} {@link XMLStreamWriter#writeCData(String)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeCData(String)}
   */
  @Test
  @DisplayName("Test writeCData(String); given XMLStreamWriter writeCData(String) does nothing")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeCData(String)"})
  void testWriteCData_givenXMLStreamWriterWriteCDataDoesNothing() throws XMLStreamException {
    // Arrange
    doNothing().when(xMLStreamWriter).writeCData(Mockito.<String>any());

    // Act
    indentingXMLStreamWriter.writeCData("Data");

    // Assert
    verify(xMLStreamWriter).writeCData("Data");
  }

  /**
   * Test {@link IndentingXMLStreamWriter#writeCData(String)}.
   *
   * <ul>
   *   <li>Then throw {@link XMLStreamException}.
   * </ul>
   *
   * <p>Method under test: {@link IndentingXMLStreamWriter#writeCData(String)}
   */
  @Test
  @DisplayName("Test writeCData(String); then throw XMLStreamException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void IndentingXMLStreamWriter.writeCData(String)"})
  void testWriteCData_thenThrowXMLStreamException() throws XMLStreamException {
    // Arrange
    doThrow(new XMLStreamException()).when(xMLStreamWriter).writeCData(Mockito.<String>any());

    // Act and Assert
    assertThrows(XMLStreamException.class, () -> indentingXMLStreamWriter.writeCData("Data"));
    verify(xMLStreamWriter).writeCData("Data");
  }
}
