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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FailedJobRetryCountExportDiffblueTest {
  /**
   * Test {@link FailedJobRetryCountExport#writeFailedJobRetryCount(Activity, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.
   * </ul>
   *
   * <p>Method under test: {@link FailedJobRetryCountExport#writeFailedJobRetryCount(Activity,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeFailedJobRetryCount(Activity, XMLStreamWriter); given '42'; then calls writeCharacters(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void FailedJobRetryCountExport.writeFailedJobRetryCount(Activity, XMLStreamWriter)"
  })
  void testWriteFailedJobRetryCount_given42_thenCallsWriteCharacters() throws Exception {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    activity.setFailedJobRetryTimeCycleValue("42");

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    FailedJobRetryCountExport.writeFailedJobRetryCount(
        activity, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeCharacters("42");
    verify(writer).writeEndElement();
    verify(writer)
        .writeStartElement("activiti", "failedJobRetryTimeCycle", "http://activiti.org/bpmn");
  }
}
