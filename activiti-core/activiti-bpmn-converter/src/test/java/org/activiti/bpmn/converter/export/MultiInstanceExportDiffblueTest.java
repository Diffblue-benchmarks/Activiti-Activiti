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
import com.diffblue.cover.annotations.MethodsUnderTest;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MultiInstanceExportDiffblueTest {
  /**
   * Test {@link MultiInstanceExport#writeMultiInstance(Activity, XMLStreamWriter)}.
   * <p>
   * Method under test: {@link MultiInstanceExport#writeMultiInstance(Activity, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeMultiInstance(Activity, XMLStreamWriter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MultiInstanceExport.writeMultiInstance(Activity, XMLStreamWriter)"})
  void testWriteMultiInstance() throws Exception {
    // Arrange
    MultiInstanceLoopCharacteristics loopCharacteristics = mock(MultiInstanceLoopCharacteristics.class);
    when(loopCharacteristics.isSequential()).thenReturn(true);
    when(loopCharacteristics.getCompletionCondition()).thenReturn("Completion Condition");
    when(loopCharacteristics.getElementVariable()).thenReturn("Element Variable");
    when(loopCharacteristics.getInputDataItem()).thenReturn("Input Data Item");
    when(loopCharacteristics.getLoopCardinality()).thenReturn("Loop Cardinality");
    when(loopCharacteristics.getLoopDataOutputRef()).thenReturn("Loop Data Output Ref");
    when(loopCharacteristics.getOutputDataItem()).thenReturn("Output Data Item");

    AdhocSubProcess activity = new AdhocSubProcess();
    activity.setLoopCharacteristics(loopCharacteristics);
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    MultiInstanceExport.writeMultiInstance(activity, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(loopCharacteristics, atLeast(1)).getCompletionCondition();
    verify(loopCharacteristics, atLeast(1)).getElementVariable();
    verify(loopCharacteristics, atLeast(1)).getInputDataItem();
    verify(loopCharacteristics, atLeast(1)).getLoopCardinality();
    verify(loopCharacteristics, atLeast(1)).getLoopDataOutputRef();
    verify(loopCharacteristics, atLeast(1)).getOutputDataItem();
    verify(loopCharacteristics).isSequential();
  }

  /**
   * Test {@link MultiInstanceExport#writeMultiInstance(Activity, XMLStreamWriter)}.
   * <p>
   * Method under test: {@link MultiInstanceExport#writeMultiInstance(Activity, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeMultiInstance(Activity, XMLStreamWriter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MultiInstanceExport.writeMultiInstance(Activity, XMLStreamWriter)"})
  void testWriteMultiInstance2() throws Exception {
    // Arrange
    MultiInstanceLoopCharacteristics loopCharacteristics = mock(MultiInstanceLoopCharacteristics.class);
    when(loopCharacteristics.isSequential()).thenReturn(true);
    when(loopCharacteristics.getCompletionCondition()).thenReturn("");
    when(loopCharacteristics.getElementVariable()).thenReturn("Element Variable");
    when(loopCharacteristics.getInputDataItem()).thenReturn("Input Data Item");
    when(loopCharacteristics.getLoopCardinality()).thenReturn("Loop Cardinality");
    when(loopCharacteristics.getLoopDataOutputRef()).thenReturn("Loop Data Output Ref");
    when(loopCharacteristics.getOutputDataItem()).thenReturn("Output Data Item");

    AdhocSubProcess activity = new AdhocSubProcess();
    activity.setLoopCharacteristics(loopCharacteristics);
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    MultiInstanceExport.writeMultiInstance(activity, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(loopCharacteristics).getCompletionCondition();
    verify(loopCharacteristics, atLeast(1)).getElementVariable();
    verify(loopCharacteristics, atLeast(1)).getInputDataItem();
    verify(loopCharacteristics, atLeast(1)).getLoopCardinality();
    verify(loopCharacteristics, atLeast(1)).getLoopDataOutputRef();
    verify(loopCharacteristics, atLeast(1)).getOutputDataItem();
    verify(loopCharacteristics).isSequential();
  }

  /**
   * Test {@link MultiInstanceExport#writeMultiInstance(Activity, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link MultiInstanceLoopCharacteristics} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link MultiInstanceExport#writeMultiInstance(Activity, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeMultiInstance(Activity, XMLStreamWriter); given MultiInstanceLoopCharacteristics (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MultiInstanceExport.writeMultiInstance(Activity, XMLStreamWriter)"})
  void testWriteMultiInstance_givenMultiInstanceLoopCharacteristics() throws Exception {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    activity.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    MultiInstanceExport.writeMultiInstance(activity, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeAttribute(eq("isSequential"), eq("false"));
    verify(writer).writeEndElement();
    verify(writer).writeStartElement(eq("multiInstanceLoopCharacteristics"));
  }
}
