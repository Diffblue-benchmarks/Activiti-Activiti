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
package org.activiti.bpmn.converter.export;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Error;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ErrorExportDiffblueTest {
  /**
   * Method under test: {@link ErrorExport#writeError(BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteError() throws Exception {
    // Arrange
    BpmnModel model = mock(BpmnModel.class);
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    ErrorExport.writeError(model, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(model).getErrors();
  }

  /**
   * Method under test: {@link ErrorExport#writeError(BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteError2() throws Exception {
    // Arrange
    HashMap<String, Error> stringErrorMap = new HashMap<>();
    stringErrorMap.putIfAbsent("foo", new Error("42", "Name", "An error occurred"));
    BpmnModel model = mock(BpmnModel.class);
    when(model.getErrors()).thenReturn(stringErrorMap);
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    ErrorExport.writeError(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeEndElement();
    verify(writer).writeStartElement(eq("error"));
    verify(model).getErrors();
  }
}
