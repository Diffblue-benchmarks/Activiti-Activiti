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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.BpmnModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ErrorExportDiffblueTest {
  /**
   * Test {@link ErrorExport#writeError(BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then calls {@link BpmnModel#getErrors()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorExport#writeError(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeError(BpmnModel, XMLStreamWriter); given HashMap(); then calls getErrors()")
  void testWriteError_givenHashMap_thenCallsGetErrors() throws Exception {
    // Arrange
    BpmnModel model = mock(BpmnModel.class);
    when(model.getErrors()).thenReturn(new HashMap<>());

    // Act
    ErrorExport.writeError(model, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(model).getErrors();
  }
}
