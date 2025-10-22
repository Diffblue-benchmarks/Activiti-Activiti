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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Pool;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CollaborationExportDiffblueTest {
  /**
   * Test {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link Pool} {@link Pool#getName()} return empty string.</li>
   *   <li>Then calls {@link BaseElement#getId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writePools(BpmnModel, XMLStreamWriter); given Pool getName() return empty string; then calls getId()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CollaborationExport.writePools(BpmnModel, XMLStreamWriter)"})
  void testWritePools_givenPoolGetNameReturnEmptyString_thenCallsGetId() throws Exception {
    // Arrange
    Pool pool = mock(Pool.class);
    when(pool.getId()).thenReturn("42");
    when(pool.getName()).thenReturn("");
    when(pool.getProcessRef()).thenReturn("Process Ref");
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setProcessRef(null);
    pool.setName(null);

    ArrayList<Pool> pools = new ArrayList<>();
    pools.add(pool);

    BpmnModel model = new BpmnModel();
    model.setPools(pools);
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    CollaborationExport.writePools(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(pool).getId();
    verify(pool).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName(isNull());
    verify(pool).setProcessRef(isNull());
  }

  /**
   * Test {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link Pool} {@link Pool#getName()} return {@code Name}.</li>
   *   <li>Then calls {@link BaseElement#getId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writePools(BpmnModel, XMLStreamWriter); given Pool getName() return 'Name'; then calls getId()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CollaborationExport.writePools(BpmnModel, XMLStreamWriter)"})
  void testWritePools_givenPoolGetNameReturnName_thenCallsGetId() throws Exception {
    // Arrange
    Pool pool = mock(Pool.class);
    when(pool.getId()).thenReturn("42");
    when(pool.getName()).thenReturn("Name");
    when(pool.getProcessRef()).thenReturn("Process Ref");
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setProcessRef(null);
    pool.setName(null);

    ArrayList<Pool> pools = new ArrayList<>();
    pools.add(pool);

    BpmnModel model = new BpmnModel();
    model.setPools(pools);
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    CollaborationExport.writePools(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(pool).getId();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
    verify(pool).setName(isNull());
    verify(pool).setProcessRef(isNull());
  }
}
