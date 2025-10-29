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
package org.activiti.engine.impl.bpmn.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import org.activiti.engine.delegate.DelegateExecution;
import org.junit.Test;
import org.mockito.Mockito;

public class IOSpecificationDiffblueTest {
  /**
   * Method under test: {@link IOSpecification#initialize(DelegateExecution)}
   */
  @Test
  public void testInitialize() {
    // Arrange
    ItemDefinition definition = mock(ItemDefinition.class);
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));

    when(definition.createInstance())
        .thenReturn(new ItemInstance(item, new FieldBaseStructureInstance(new SimpleStructureDefinition("42"))));
    Data data = new Data("42", "Name", definition);

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.addInput(data);
    DelegateExecution execution = mock(DelegateExecution.class);
    doNothing().when(execution).setVariable(Mockito.<String>any(), Mockito.<Object>any());

    // Act
    ioSpecification.initialize(execution);

    // Assert
    verify(execution).setVariable(eq("Name"), isA(Object.class));
    verify(definition).createInstance();
  }

  /**
   * Method under test: {@link IOSpecification#initialize(DelegateExecution)}
   */
  @Test
  public void testInitialize2() {
    // Arrange
    ItemDefinition definition = mock(ItemDefinition.class);
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));

    when(definition.createInstance())
        .thenReturn(new ItemInstance(item, new FieldBaseStructureInstance(new SimpleStructureDefinition("42"))));
    Data data = new Data("42", "Name", definition);

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.addOutput(new Data("42", "Name", new ItemDefinition("42", new SimpleStructureDefinition("42"))));
    ioSpecification.addInput(data);
    DelegateExecution execution = mock(DelegateExecution.class);
    doNothing().when(execution).setVariable(Mockito.<String>any(), Mockito.<Object>any());

    // Act
    ioSpecification.initialize(execution);

    // Assert
    verify(execution, atLeast(1)).setVariable(eq("Name"), Mockito.<Object>any());
    verify(definition).createInstance();
  }

  /**
   * Method under test: {@link IOSpecification#initialize(DelegateExecution)}
   */
  @Test
  public void testInitialize3() {
    // Arrange
    ItemDefinition definition = mock(ItemDefinition.class);
    ItemDefinition item = new ItemDefinition("42", new SimpleStructureDefinition("42"));

    when(definition.createInstance())
        .thenReturn(new ItemInstance(item, new FieldBaseStructureInstance(new SimpleStructureDefinition("42"))));
    Data data = new Data("42", "Name", definition);

    ItemDefinition definition2 = mock(ItemDefinition.class);
    ItemDefinition item2 = new ItemDefinition("42", new SimpleStructureDefinition("42"));

    when(definition2.createInstance())
        .thenReturn(new ItemInstance(item2, new FieldBaseStructureInstance(new SimpleStructureDefinition("42"))));
    Data data2 = new Data("42", "Name", definition2);

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.addOutput(data2);
    ioSpecification.addInput(data);
    DelegateExecution execution = mock(DelegateExecution.class);
    doNothing().when(execution).setVariable(Mockito.<String>any(), Mockito.<Object>any());

    // Act
    ioSpecification.initialize(execution);

    // Assert
    verify(execution, atLeast(1)).setVariable(eq("Name"), Mockito.<Object>any());
    verify(definition2).createInstance();
    verify(definition).createInstance();
  }

  /**
   * Method under test: {@link IOSpecification#getDataInputs()}
   */
  @Test
  public void testGetDataInputs() {
    // Arrange, Act and Assert
    assertTrue((new IOSpecification()).getDataInputs().isEmpty());
  }

  /**
   * Method under test: {@link IOSpecification#getDataOutputs()}
   */
  @Test
  public void testGetDataOutputs() {
    // Arrange, Act and Assert
    assertTrue((new IOSpecification()).getDataOutputs().isEmpty());
  }

  /**
   * Method under test: {@link IOSpecification#addInput(Data)}
   */
  @Test
  public void testAddInput() {
    // Arrange
    IOSpecification ioSpecification = new IOSpecification();
    Data data = new Data("42", "Name", new ItemDefinition("42", new SimpleStructureDefinition("42")));

    // Act
    ioSpecification.addInput(data);

    // Assert
    assertEquals("Name", ioSpecification.getFirstDataInputName());
    List<Data> dataInputs = ioSpecification.getDataInputs();
    assertEquals(1, dataInputs.size());
    List<Data> dataList = ioSpecification.dataInputs;
    assertEquals(1, dataList.size());
    assertSame(data, dataInputs.get(0));
    assertSame(data, dataList.get(0));
  }

  /**
   * Method under test: {@link IOSpecification#addOutput(Data)}
   */
  @Test
  public void testAddOutput() {
    // Arrange
    IOSpecification ioSpecification = new IOSpecification();
    Data data = new Data("42", "Name", new ItemDefinition("42", new SimpleStructureDefinition("42")));

    // Act
    ioSpecification.addOutput(data);

    // Assert
    assertEquals("Name", ioSpecification.getFirstDataOutputName());
    List<Data> dataOutputs = ioSpecification.getDataOutputs();
    assertEquals(1, dataOutputs.size());
    List<Data> dataList = ioSpecification.dataOutputs;
    assertEquals(1, dataList.size());
    assertSame(data, dataOutputs.get(0));
    assertSame(data, dataList.get(0));
  }

  /**
   * Method under test: {@link IOSpecification#addInputRef(DataRef)}
   */
  @Test
  public void testAddInputRef() {
    // Arrange
    IOSpecification ioSpecification = new IOSpecification();
    DataRef dataRef = new DataRef("Id Ref");

    // Act
    ioSpecification.addInputRef(dataRef);

    // Assert
    List<DataRef> dataRefList = ioSpecification.dataInputRefs;
    assertEquals(1, dataRefList.size());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(ioSpecification.dataOutputRefs.isEmpty());
    assertTrue(ioSpecification.dataOutputs.isEmpty());
    assertSame(dataRef, dataRefList.get(0));
  }

  /**
   * Method under test: {@link IOSpecification#addOutputRef(DataRef)}
   */
  @Test
  public void testAddOutputRef() {
    // Arrange
    IOSpecification ioSpecification = new IOSpecification();
    DataRef dataRef = new DataRef("Id Ref");

    // Act
    ioSpecification.addOutputRef(dataRef);

    // Assert
    List<DataRef> dataRefList = ioSpecification.dataOutputRefs;
    assertEquals(1, dataRefList.size());
    assertSame(dataRef, dataRefList.get(0));
  }

  /**
   * Method under test: {@link IOSpecification#getFirstDataInputName()}
   */
  @Test
  public void testGetFirstDataInputName() {
    // Arrange
    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.addInput(new Data("42", "Name", new ItemDefinition("42", new SimpleStructureDefinition("42"))));

    // Act and Assert
    assertEquals("Name", ioSpecification.getFirstDataInputName());
  }

  /**
   * Method under test: {@link IOSpecification#getFirstDataOutputName()}
   */
  @Test
  public void testGetFirstDataOutputName() {
    // Arrange, Act and Assert
    assertNull((new IOSpecification()).getFirstDataOutputName());
  }

  /**
   * Method under test: {@link IOSpecification#getFirstDataOutputName()}
   */
  @Test
  public void testGetFirstDataOutputName2() {
    // Arrange
    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.addOutput(new Data("42", "Name", new ItemDefinition("42", new SimpleStructureDefinition("42"))));

    // Act and Assert
    assertEquals("Name", ioSpecification.getFirstDataOutputName());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link IOSpecification}
   */
  @Test
  public void testNewIOSpecification() {
    // Arrange and Act
    IOSpecification actualIoSpecification = new IOSpecification();

    // Assert
    assertTrue(actualIoSpecification.dataInputRefs.isEmpty());
    assertTrue(actualIoSpecification.dataInputs.isEmpty());
    assertTrue(actualIoSpecification.dataOutputRefs.isEmpty());
    assertTrue(actualIoSpecification.dataOutputs.isEmpty());
  }
}
