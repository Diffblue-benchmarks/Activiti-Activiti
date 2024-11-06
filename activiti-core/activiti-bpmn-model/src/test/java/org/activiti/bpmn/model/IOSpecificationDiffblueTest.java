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
package org.activiti.bpmn.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class IOSpecificationDiffblueTest {
  /**
   * Test {@link IOSpecification#clone()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link DataSpec} (default
   * constructor).</li>
   *   <li>Then return not DataOutputs first Collection.</li>
   * </ul>
   * <p>
   * Method under test: {@link IOSpecification#clone()}
   */
  @Test
  public void testClone_givenArrayListAddDataSpec_thenReturnNotDataOutputsFirstCollection() {
    // Arrange
    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(dataOutputs);

    // Act and Assert
    List<DataSpec> dataOutputs2 = ioSpecification.clone().getDataOutputs();
    assertEquals(1, dataOutputs2.size());
    DataSpec getResult = dataOutputs2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getItemSubjectRef());
    assertNull(getResult.getName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertFalse(getResult.isCollection());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link IOSpecification#clone()}.
   * <ul>
   *   <li>Given {@link DataSpec} (default constructor) Collection is
   * {@code true}.</li>
   *   <li>Then return DataOutputs first Collection.</li>
   * </ul>
   * <p>
   * Method under test: {@link IOSpecification#clone()}
   */
  @Test
  public void testClone_givenDataSpecCollectionIsTrue_thenReturnDataOutputsFirstCollection() {
    // Arrange
    DataSpec dataSpec = new DataSpec();
    dataSpec.setCollection(true);

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(dataSpec);

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(dataOutputs);

    // Act and Assert
    List<DataSpec> dataOutputs2 = ioSpecification.clone().getDataOutputs();
    assertEquals(1, dataOutputs2.size());
    DataSpec getResult = dataOutputs2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getItemSubjectRef());
    assertNull(getResult.getName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(getResult.isCollection());
  }

  /**
   * Test {@link IOSpecification#clone()}.
   * <ul>
   *   <li>Given {@link IOSpecification} (default constructor) DataOutputs is
   * {@code null}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IOSpecification#clone()}
   */
  @Test
  public void testClone_givenIOSpecificationDataOutputsIsNull_thenReturnIdIsNull() {
    // Arrange
    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);

    // Act
    IOSpecification actualCloneResult = ioSpecification.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getDataInputRefs().isEmpty());
    assertTrue(actualCloneResult.getDataInputs().isEmpty());
    assertTrue(actualCloneResult.getDataOutputRefs().isEmpty());
    assertTrue(actualCloneResult.getDataOutputs().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link IOSpecification#clone()}.
   * <ul>
   *   <li>Given {@link IOSpecification} (default constructor).</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IOSpecification#clone()}
   */
  @Test
  public void testClone_givenIOSpecification_thenReturnIdIsNull() {
    // Arrange and Act
    IOSpecification actualCloneResult = (new IOSpecification()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getDataInputRefs().isEmpty());
    assertTrue(actualCloneResult.getDataInputs().isEmpty());
    assertTrue(actualCloneResult.getDataOutputRefs().isEmpty());
    assertTrue(actualCloneResult.getDataOutputs().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link IOSpecification#clone()}.
   * <ul>
   *   <li>Then return DataInputs size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link IOSpecification#clone()}
   */
  @Test
  public void testClone_thenReturnDataInputsSizeIsOne() {
    // Arrange
    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(null);

    // Act and Assert
    List<DataSpec> dataInputs2 = ioSpecification.clone().getDataInputs();
    assertEquals(1, dataInputs2.size());
    DataSpec getResult = dataInputs2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getItemSubjectRef());
    assertNull(getResult.getName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertFalse(getResult.isCollection());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link IOSpecification#setValues(IOSpecification)} with
   * {@code otherSpec}.
   * <ul>
   *   <li>Given {@link DataSpec} {@link DataSpec#clone()} return {@link DataSpec}
   * (default constructor).</li>
   *   <li>Then calls {@link DataSpec#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IOSpecification#setValues(IOSpecification)}
   */
  @Test
  public void testSetValuesWithOtherSpec_givenDataSpecCloneReturnDataSpec_thenCallsClone() {
    // Arrange
    IOSpecification ioSpecification = new IOSpecification();
    DataSpec dataSpec = mock(DataSpec.class);
    when(dataSpec.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(dataSpec);

    IOSpecification otherSpec = new IOSpecification();
    otherSpec.setDataInputs(null);
    otherSpec.setDataOutputs(dataOutputs);

    // Act
    ioSpecification.setValues(otherSpec);

    // Assert
    verify(dataSpec).clone();
  }

  /**
   * Test {@link IOSpecification#setValues(IOSpecification)} with
   * {@code otherSpec}.
   * <ul>
   *   <li>Given {@link DataSpec} {@link DataSpec#clone()} return {@link DataSpec}
   * (default constructor).</li>
   *   <li>Then calls {@link DataSpec#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IOSpecification#setValues(IOSpecification)}
   */
  @Test
  public void testSetValuesWithOtherSpec_givenDataSpecCloneReturnDataSpec_thenCallsClone2() {
    // Arrange
    IOSpecification ioSpecification = new IOSpecification();
    DataSpec dataSpec = mock(DataSpec.class);
    when(dataSpec.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(dataSpec);

    IOSpecification otherSpec = new IOSpecification();
    otherSpec.setDataInputs(dataInputs);
    otherSpec.setDataOutputs(null);

    // Act
    ioSpecification.setValues(otherSpec);

    // Assert
    verify(dataSpec).clone();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link IOSpecification}
   *   <li>{@link IOSpecification#setDataInputRefs(List)}
   *   <li>{@link IOSpecification#setDataInputs(List)}
   *   <li>{@link IOSpecification#setDataOutputRefs(List)}
   *   <li>{@link IOSpecification#setDataOutputs(List)}
   *   <li>{@link IOSpecification#getDataInputRefs()}
   *   <li>{@link IOSpecification#getDataInputs()}
   *   <li>{@link IOSpecification#getDataOutputRefs()}
   *   <li>{@link IOSpecification#getDataOutputs()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    IOSpecification actualIoSpecification = new IOSpecification();
    ArrayList<String> dataInputRefs = new ArrayList<>();
    actualIoSpecification.setDataInputRefs(dataInputRefs);
    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    actualIoSpecification.setDataInputs(dataInputs);
    ArrayList<String> dataOutputRefs = new ArrayList<>();
    actualIoSpecification.setDataOutputRefs(dataOutputRefs);
    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    actualIoSpecification.setDataOutputs(dataOutputs);
    List<String> actualDataInputRefs = actualIoSpecification.getDataInputRefs();
    List<DataSpec> actualDataInputs = actualIoSpecification.getDataInputs();
    List<String> actualDataOutputRefs = actualIoSpecification.getDataOutputRefs();
    List<DataSpec> actualDataOutputs = actualIoSpecification.getDataOutputs();

    // Assert that nothing has changed
    assertEquals(0, actualIoSpecification.getXmlColumnNumber());
    assertEquals(0, actualIoSpecification.getXmlRowNumber());
    assertTrue(actualDataInputRefs.isEmpty());
    assertTrue(actualDataInputs.isEmpty());
    assertTrue(actualDataOutputRefs.isEmpty());
    assertTrue(actualDataOutputs.isEmpty());
    assertTrue(actualIoSpecification.getAttributes().isEmpty());
    assertTrue(actualIoSpecification.getExtensionElements().isEmpty());
    assertSame(dataInputRefs, actualDataInputRefs);
    assertSame(dataInputs, actualDataInputs);
    assertSame(dataOutputRefs, actualDataOutputRefs);
    assertSame(dataOutputs, actualDataOutputs);
  }
}
