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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Pool;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CollaborationExportDiffblueTest {
  /**
   * Test {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link Pool} {@link Pool#getName()} return empty string.
   *   <li>Then calls {@link Pool#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writePools(BpmnModel, XMLStreamWriter); given Pool getName() return empty string; then calls getId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CollaborationExport.writePools(BpmnModel, XMLStreamWriter)"})
  void testWritePools_givenPoolGetNameReturnEmptyString_thenCallsGetId() throws Exception {
    // Arrange
    Pool pool = mock(Pool.class);
    when(pool.getName()).thenReturn("");
    when(pool.getProcessRef()).thenReturn("");
    when(pool.getId()).thenReturn("42");
    doNothing().when(pool).setName(Mockito.<String>any());
    doNothing().when(pool).setProcessRef(Mockito.<String>any());
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> pools = new ArrayList<>();
    pools.add(pool);

    BpmnModel model = new BpmnModel();
    model.setPools(pools);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    CollaborationExport.writePools(model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(eq("id"), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(pool).getId();
    verify(pool).getName();
    verify(pool).getProcessRef();
    verify(pool).setName("not empty");
    verify(pool).setProcessRef("not empty");
  }

  /**
   * Test {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link Pool} (default constructor) Name is {@code not empty}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writePools(BpmnModel, XMLStreamWriter); given Pool (default constructor) Name is 'not empty'; then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CollaborationExport.writePools(BpmnModel, XMLStreamWriter)"})
  void testWritePools_givenPoolNameIsNotEmpty_thenCallsWriteAttribute() throws Exception {
    // Arrange
    Pool pool = new Pool();
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> pools = new ArrayList<>();
    pools.add(pool);

    BpmnModel model = new BpmnModel();
    model.setPools(pools);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    CollaborationExport.writePools(model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
  }

  /**
   * Test {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link Pool} (default constructor) Name is {@code not empty}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writePools(BpmnModel, XMLStreamWriter); given Pool (default constructor) Name is 'not empty'; then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CollaborationExport.writePools(BpmnModel, XMLStreamWriter)"})
  void testWritePools_givenPoolNameIsNotEmpty_thenCallsWriteAttribute2() throws Exception {
    // Arrange
    Pool pool = new Pool();
    pool.setName("not empty");
    pool.setProcessRef("not empty");

    ArrayList<Pool> pools = new ArrayList<>();
    pools.add(new Pool());
    pools.add(pool);

    BpmnModel model = new BpmnModel();
    model.setPools(pools);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    CollaborationExport.writePools(model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
  }
}
