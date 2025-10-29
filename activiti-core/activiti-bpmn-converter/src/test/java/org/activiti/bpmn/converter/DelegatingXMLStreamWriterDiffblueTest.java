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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DelegatingXMLStreamWriterDiffblueTest {
  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeStartElement(String)}
   */
  @Test
  void testWriteStartElement() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeStartElement("Local Name");

    // Assert
    verify(writer).writeStartElement(eq("Local Name"));
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeStartElement(String, String)}
   */
  @Test
  void testWriteStartElement2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeStartElement("Namespace URI", "Local Name");

    // Assert
    verify(writer).writeStartElement(eq("Namespace URI"), eq("Local Name"));
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeStartElement(String, String)}
   */
  @Test
  void testWriteStartElement3() throws XMLStreamException {
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
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeStartElement(String, String, String)}
   */
  @Test
  void testWriteStartElement4() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeStartElement("Prefix", "Local Name", "Namespace URI");

    // Assert
    verify(writer).writeStartElement(eq("Prefix"), eq("Local Name"), eq("Namespace URI"));
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeEmptyElement(String)}
   */
  @Test
  void testWriteEmptyElement() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEmptyElement(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeEmptyElement("Local Name");

    // Assert
    verify(writer).writeEmptyElement(eq("Local Name"));
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeEmptyElement(String, String)}
   */
  @Test
  void testWriteEmptyElement2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEmptyElement(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeEmptyElement("Namespace URI", "Local Name");

    // Assert
    verify(writer).writeEmptyElement(eq("Namespace URI"), eq("Local Name"));
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeEmptyElement(String, String)}
   */
  @Test
  void testWriteEmptyElement3() throws XMLStreamException {
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
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeEmptyElement(String, String, String)}
   */
  @Test
  void testWriteEmptyElement4() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEmptyElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeEmptyElement("Prefix", "Local Name", "Namespace URI");

    // Assert
    verify(writer).writeEmptyElement(eq("Prefix"), eq("Local Name"), eq("Namespace URI"));
  }

  /**
   * Method under test: {@link DelegatingXMLStreamWriter#writeEndDocument()}
   */
  @Test
  void testWriteEndDocument() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndDocument();

    // Act
    (new IndentingXMLStreamWriter(writer)).writeEndDocument();

    // Assert that nothing has changed
    verify(writer).writeEndDocument();
  }

  /**
   * Method under test: {@link DelegatingXMLStreamWriter#writeEndDocument()}
   */
  @Test
  void testWriteEndDocument2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndDocument();

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).writeEndDocument();

    // Assert that nothing has changed
    verify(writer).writeEndDocument();
  }

  /**
   * Method under test: {@link DelegatingXMLStreamWriter#close()}
   */
  @Test
  void testClose() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).close();

    // Act
    (new IndentingXMLStreamWriter(writer)).close();

    // Assert that nothing has changed
    verify(writer).close();
  }

  /**
   * Method under test: {@link DelegatingXMLStreamWriter#close()}
   */
  @Test
  void testClose2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).close();

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).close();

    // Assert that nothing has changed
    verify(writer).close();
  }

  /**
   * Method under test: {@link DelegatingXMLStreamWriter#flush()}
   */
  @Test
  void testFlush() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).flush();

    // Act
    (new IndentingXMLStreamWriter(writer)).flush();

    // Assert that nothing has changed
    verify(writer).flush();
  }

  /**
   * Method under test: {@link DelegatingXMLStreamWriter#flush()}
   */
  @Test
  void testFlush2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).flush();

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).flush();

    // Assert that nothing has changed
    verify(writer).flush();
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}
   */
  @Test
  void testWriteAttribute() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeAttribute("Local Name", "42");

    // Assert that nothing has changed
    verify(writer).writeAttribute(eq("Local Name"), eq("42"));
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}
   */
  @Test
  void testWriteAttribute2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).writeAttribute("Local Name", "42");

    // Assert that nothing has changed
    verify(writer).writeAttribute(eq("Local Name"), eq("42"));
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String)}
   */
  @Test
  void testWriteAttribute3() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeAttribute("Namespace URI", "Local Name", "42");

    // Assert that nothing has changed
    verify(writer).writeAttribute(eq("Namespace URI"), eq("Local Name"), eq("42"));
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String, String)}
   */
  @Test
  void testWriteAttribute4() throws XMLStreamException {
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
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeNamespace(String, String)}
   */
  @Test
  void testWriteNamespace() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeNamespace("Prefix", "Namespace URI");

    // Assert that nothing has changed
    verify(writer).writeNamespace(eq("Prefix"), eq("Namespace URI"));
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeNamespace(String, String)}
   */
  @Test
  void testWriteNamespace2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).writeNamespace("Prefix", "Namespace URI");

    // Assert that nothing has changed
    verify(writer).writeNamespace(eq("Prefix"), eq("Namespace URI"));
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeDefaultNamespace(String)}
   */
  @Test
  void testWriteDefaultNamespace() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeDefaultNamespace(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeDefaultNamespace("Namespace URI");

    // Assert that nothing has changed
    verify(writer).writeDefaultNamespace(eq("Namespace URI"));
  }

  /**
   * Method under test: {@link DelegatingXMLStreamWriter#writeComment(String)}
   */
  @Test
  void testWriteComment() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeComment(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeComment("Data");

    // Assert that nothing has changed
    verify(writer).writeComment(eq("Data"));
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String)}
   */
  @Test
  void testWriteProcessingInstruction() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeProcessingInstruction(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeProcessingInstruction("Target");

    // Assert that nothing has changed
    verify(writer).writeProcessingInstruction(eq("Target"));
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String, String)}
   */
  @Test
  void testWriteProcessingInstruction2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeProcessingInstruction(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeProcessingInstruction("Target", "Data");

    // Assert that nothing has changed
    verify(writer).writeProcessingInstruction(eq("Target"), eq("Data"));
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeProcessingInstruction(String, String)}
   */
  @Test
  void testWriteProcessingInstruction3() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeProcessingInstruction(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).writeProcessingInstruction("Target", "Data");

    // Assert that nothing has changed
    verify(writer).writeProcessingInstruction(eq("Target"), eq("Data"));
  }

  /**
   * Method under test: {@link DelegatingXMLStreamWriter#writeCData(String)}
   */
  @Test
  void testWriteCData() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCData(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeCData("Data");

    // Assert
    verify(writer).writeCData(eq("Data"));
  }

  /**
   * Method under test: {@link DelegatingXMLStreamWriter#writeDTD(String)}
   */
  @Test
  void testWriteDTD() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeDTD(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeDTD("Dtd");

    // Assert that nothing has changed
    verify(writer).writeDTD(eq("Dtd"));
  }

  /**
   * Method under test: {@link DelegatingXMLStreamWriter#writeEntityRef(String)}
   */
  @Test
  void testWriteEntityRef() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEntityRef(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeEntityRef("Name");

    // Assert that nothing has changed
    verify(writer).writeEntityRef(eq("Name"));
  }

  /**
   * Method under test: {@link DelegatingXMLStreamWriter#writeStartDocument()}
   */
  @Test
  void testWriteStartDocument() throws XMLStreamException {
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
   * Method under test: {@link DelegatingXMLStreamWriter#writeStartDocument()}
   */
  @Test
  void testWriteStartDocument2() throws XMLStreamException {
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
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeStartDocument(String)}
   */
  @Test
  void testWriteStartDocument3() throws XMLStreamException {
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
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeStartDocument(String, String)}
   */
  @Test
  void testWriteStartDocument4() throws XMLStreamException {
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
   * Method under test: {@link DelegatingXMLStreamWriter#writeCharacters(String)}
   */
  @Test
  void testWriteCharacters() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).writeCharacters("Text");

    // Assert
    verify(writer).writeCharacters(eq("Text"));
  }

  /**
   * Method under test: {@link DelegatingXMLStreamWriter#writeCharacters(String)}
   */
  @Test
  void testWriteCharacters2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).writeCharacters("Text");

    // Assert
    verify(writer).writeCharacters(eq("Text"));
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#writeCharacters(char[], int, int)}
   */
  @Test
  void testWriteCharacters3() throws XMLStreamException {
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
   * Method under test: {@link DelegatingXMLStreamWriter#getPrefix(String)}
   */
  @Test
  void testGetPrefix() throws XMLStreamException {
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
   * Method under test:
   * {@link DelegatingXMLStreamWriter#setPrefix(String, String)}
   */
  @Test
  void testSetPrefix() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).setPrefix(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).setPrefix("Prefix", "Uri");

    // Assert that nothing has changed
    verify(writer).setPrefix(eq("Prefix"), eq("Uri"));
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#setPrefix(String, String)}
   */
  @Test
  void testSetPrefix2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).setPrefix(Mockito.<String>any(), Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).setPrefix("Prefix", "Uri");

    // Assert that nothing has changed
    verify(writer).setPrefix(eq("Prefix"), eq("Uri"));
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#setDefaultNamespace(String)}
   */
  @Test
  void testSetDefaultNamespace() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).setDefaultNamespace(Mockito.<String>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).setDefaultNamespace("Uri");

    // Assert that nothing has changed
    verify(writer).setDefaultNamespace(eq("Uri"));
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#setNamespaceContext(NamespaceContext)}
   */
  @Test
  void testSetNamespaceContext() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).setNamespaceContext(Mockito.<NamespaceContext>any());

    // Act
    (new IndentingXMLStreamWriter(writer)).setNamespaceContext(null);

    // Assert that nothing has changed
    verify(writer).setNamespaceContext(isNull());
  }

  /**
   * Method under test:
   * {@link DelegatingXMLStreamWriter#setNamespaceContext(NamespaceContext)}
   */
  @Test
  void testSetNamespaceContext2() throws XMLStreamException {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).setNamespaceContext(Mockito.<NamespaceContext>any());

    // Act
    (new IndentingXMLStreamWriter(new IndentingXMLStreamWriter(writer))).setNamespaceContext(null);

    // Assert that nothing has changed
    verify(writer).setNamespaceContext(isNull());
  }

  /**
   * Method under test: {@link DelegatingXMLStreamWriter#getNamespaceContext()}
   */
  @Test
  void testGetNamespaceContext() {
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
   * Method under test: {@link DelegatingXMLStreamWriter#getNamespaceContext()}
   */
  @Test
  void testGetNamespaceContext2() {
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
   * Method under test: {@link DelegatingXMLStreamWriter#getProperty(String)}
   */
  @Test
  void testGetProperty() throws IllegalArgumentException {
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
