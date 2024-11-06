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
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MultiInstanceExportDiffblueTest {
  /**
   * Test
   * {@link MultiInstanceExport#writeMultiInstance(Activity, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link MultiInstanceExport#writeMultiInstance(Activity, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeMultiInstance(Activity, XMLStreamWriter)")
  void testWriteMultiInstance() throws Exception {
    // Arrange
    AdhocSubProcess activity = mock(AdhocSubProcess.class);
    when(activity.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    MultiInstanceExport.writeMultiInstance(activity, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("isSequential"), eq("false"));
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("multiInstanceLoopCharacteristics"));
    verify(activity, atLeast(1)).getLoopCharacteristics();
  }

  /**
   * Test
   * {@link MultiInstanceExport#writeMultiInstance(Activity, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link MultiInstanceExport#writeMultiInstance(Activity, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeMultiInstance(Activity, XMLStreamWriter)")
  void testWriteMultiInstance2() throws Exception {
    // Arrange
    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = mock(MultiInstanceLoopCharacteristics.class);
    when(multiInstanceLoopCharacteristics.isSequential()).thenReturn(true);
    when(multiInstanceLoopCharacteristics.getCompletionCondition()).thenReturn("Completion Condition");
    when(multiInstanceLoopCharacteristics.getElementVariable()).thenReturn("Element Variable");
    when(multiInstanceLoopCharacteristics.getInputDataItem()).thenReturn("Input Data Item");
    when(multiInstanceLoopCharacteristics.getLoopCardinality()).thenReturn("Loop Cardinality");
    when(multiInstanceLoopCharacteristics.getLoopDataOutputRef()).thenReturn("Loop Data Output Ref");
    when(multiInstanceLoopCharacteristics.getOutputDataItem()).thenReturn("Output Data Item");
    AdhocSubProcess activity = mock(AdhocSubProcess.class);
    when(activity.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    MultiInstanceExport.writeMultiInstance(activity, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(activity, atLeast(1)).getLoopCharacteristics();
    verify(multiInstanceLoopCharacteristics, atLeast(1)).getCompletionCondition();
    verify(multiInstanceLoopCharacteristics, atLeast(1)).getElementVariable();
    verify(multiInstanceLoopCharacteristics, atLeast(1)).getInputDataItem();
    verify(multiInstanceLoopCharacteristics, atLeast(1)).getLoopCardinality();
    verify(multiInstanceLoopCharacteristics, atLeast(1)).getLoopDataOutputRef();
    verify(multiInstanceLoopCharacteristics, atLeast(1)).getOutputDataItem();
    verify(multiInstanceLoopCharacteristics).isSequential();
  }

  /**
   * Test
   * {@link MultiInstanceExport#writeMultiInstance(Activity, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link MultiInstanceExport#writeMultiInstance(Activity, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeMultiInstance(Activity, XMLStreamWriter)")
  void testWriteMultiInstance3() throws Exception {
    // Arrange
    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = mock(MultiInstanceLoopCharacteristics.class);
    when(multiInstanceLoopCharacteristics.isSequential()).thenReturn(true);
    when(multiInstanceLoopCharacteristics.getCompletionCondition()).thenReturn("");
    when(multiInstanceLoopCharacteristics.getElementVariable()).thenReturn("Element Variable");
    when(multiInstanceLoopCharacteristics.getInputDataItem()).thenReturn("Input Data Item");
    when(multiInstanceLoopCharacteristics.getLoopCardinality()).thenReturn("Loop Cardinality");
    when(multiInstanceLoopCharacteristics.getLoopDataOutputRef()).thenReturn("Loop Data Output Ref");
    when(multiInstanceLoopCharacteristics.getOutputDataItem()).thenReturn("Output Data Item");
    AdhocSubProcess activity = mock(AdhocSubProcess.class);
    when(activity.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    MultiInstanceExport.writeMultiInstance(activity, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(activity, atLeast(1)).getLoopCharacteristics();
    verify(multiInstanceLoopCharacteristics).getCompletionCondition();
    verify(multiInstanceLoopCharacteristics, atLeast(1)).getElementVariable();
    verify(multiInstanceLoopCharacteristics, atLeast(1)).getInputDataItem();
    verify(multiInstanceLoopCharacteristics, atLeast(1)).getLoopCardinality();
    verify(multiInstanceLoopCharacteristics, atLeast(1)).getLoopDataOutputRef();
    verify(multiInstanceLoopCharacteristics, atLeast(1)).getOutputDataItem();
    verify(multiInstanceLoopCharacteristics).isSequential();
  }
}
