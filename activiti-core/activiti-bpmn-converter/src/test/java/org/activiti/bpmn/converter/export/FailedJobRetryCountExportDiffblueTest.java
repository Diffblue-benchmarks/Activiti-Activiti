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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FailedJobRetryCountExportDiffblueTest {
  /**
   * Test
   * {@link FailedJobRetryCountExport#writeFailedJobRetryCount(Activity, XMLStreamWriter)}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FailedJobRetryCountExport#writeFailedJobRetryCount(Activity, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeFailedJobRetryCount(Activity, XMLStreamWriter); given empty string")
  void testWriteFailedJobRetryCount_givenEmptyString() throws Exception {
    // Arrange
    AdhocSubProcess activity = mock(AdhocSubProcess.class);
    when(activity.getFailedJobRetryTimeCycleValue()).thenReturn("");

    // Act
    FailedJobRetryCountExport.writeFailedJobRetryCount(activity, mock(IndentingXMLStreamWriter.class));

    // Assert that nothing has changed
    verify(activity).getFailedJobRetryTimeCycleValue();
  }

  /**
   * Test
   * {@link FailedJobRetryCountExport#writeFailedJobRetryCount(Activity, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FailedJobRetryCountExport#writeFailedJobRetryCount(Activity, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeFailedJobRetryCount(Activity, XMLStreamWriter); then calls writeCharacters(String)")
  void testWriteFailedJobRetryCount_thenCallsWriteCharacters() throws Exception {
    // Arrange
    AdhocSubProcess activity = mock(AdhocSubProcess.class);
    when(activity.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    FailedJobRetryCountExport.writeFailedJobRetryCount(activity, xtw);

    // Assert that nothing has changed
    verify(xtw).writeCharacters(eq("42"));
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("activiti"), eq("failedJobRetryTimeCycle"), eq("http://activiti.org/bpmn"));
    verify(activity).getFailedJobRetryTimeCycleValue();
  }
}
