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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CallActivityDiffblueTest {
  /**
   * Test {@link CallActivity#clone()}.
   * <ul>
   *   <li>Given {@link CallActivity} (default constructor).</li>
   *   <li>Then return IoSpecification is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_givenCallActivity_thenReturnIoSpecificationIsNull() {
    // Arrange and Act
    CallActivity actualCloneResult = (new CallActivity()).clone();

    // Assert
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
  }

  /**
   * Test {@link CallActivity#clone()}.
   * <ul>
   *   <li>Given {@link IOSpecification} (default constructor) DataOutputs is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_givenIOSpecificationDataOutputsIsNull() {
    // Arrange
    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity callActivity = new CallActivity();
    callActivity.setLoopCharacteristics(null);
    callActivity.setIoSpecification(ioSpecification);
    callActivity.setDataInputAssociations(null);
    callActivity.setDataOutputAssociations(null);
    callActivity.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    IOSpecification ioSpecification2 = callActivity.clone().getIoSpecification();
    assertNull(ioSpecification2.getId());
    assertEquals(0, ioSpecification2.getXmlColumnNumber());
    assertEquals(0, ioSpecification2.getXmlRowNumber());
    assertTrue(ioSpecification2.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataInputs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputs().isEmpty());
    assertTrue(ioSpecification2.getAttributes().isEmpty());
    assertTrue(ioSpecification2.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link CallActivity#clone()}.
   * <ul>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_thenReturnAttributesSizeIsOne() {
    // Arrange
    CallActivity callActivity = new CallActivity();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    callActivity.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = callActivity.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link CallActivity#clone()}.
   * <ul>
   *   <li>Then return Attributes size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    CallActivity callActivity = new CallActivity();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    callActivity.addAttribute(attribute);
    callActivity.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = callActivity.clone().getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    assertTrue(attributes.containsKey("Name"));
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link CallActivity#clone()}.
   * <ul>
   *   <li>Then return BoundaryEvents size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_thenReturnBoundaryEventsSizeIsOne() {
    // Arrange
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvents.add(boundaryEvent);

    CallActivity callActivity = new CallActivity();
    callActivity.setLoopCharacteristics(null);
    callActivity.setIoSpecification(null);
    callActivity.setDataInputAssociations(null);
    callActivity.setDataOutputAssociations(null);
    callActivity.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<BoundaryEvent> boundaryEvents2 = callActivity.clone().getBoundaryEvents();
    assertEquals(1, boundaryEvents2.size());
    assertSame(boundaryEvent, boundaryEvents2.get(0));
  }

  /**
   * Test {@link CallActivity#clone()}.
   * <ul>
   *   <li>Then return DataInputAssociations size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_thenReturnDataInputAssociationsSizeIsOne() {
    // Arrange
    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity callActivity = new CallActivity();
    callActivity.setLoopCharacteristics(null);
    callActivity.setIoSpecification(null);
    callActivity.setDataInputAssociations(dataInputAssociations);
    callActivity.setDataOutputAssociations(null);
    callActivity.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<DataAssociation> dataInputAssociations2 = callActivity.clone().getDataInputAssociations();
    assertEquals(1, dataInputAssociations2.size());
    DataAssociation getResult = dataInputAssociations2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult.getTransformation());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAssignments().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link CallActivity#clone()}.
   * <ul>
   *   <li>Then return DataOutputAssociations size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_thenReturnDataOutputAssociationsSizeIsOne() {
    // Arrange
    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity callActivity = new CallActivity();
    callActivity.setLoopCharacteristics(null);
    callActivity.setIoSpecification(null);
    callActivity.setDataInputAssociations(null);
    callActivity.setDataOutputAssociations(dataOutputAssociations);
    callActivity.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<DataAssociation> dataOutputAssociations2 = callActivity.clone().getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations2.size());
    DataAssociation getResult = dataOutputAssociations2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult.getTransformation());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAssignments().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link CallActivity#clone()}.
   * <ul>
   *   <li>Then return IoSpecification DataOutputs size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_thenReturnIoSpecificationDataOutputsSizeIsOne() {
    // Arrange
    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity callActivity = new CallActivity();
    callActivity.setLoopCharacteristics(null);
    callActivity.setIoSpecification(ioSpecification);
    callActivity.setDataInputAssociations(null);
    callActivity.setDataOutputAssociations(null);
    callActivity.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<DataSpec> dataOutputs2 = callActivity.clone().getIoSpecification().getDataOutputs();
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
   * Test {@link CallActivity#clone()}.
   * <ul>
   *   <li>Then return IoSpecification Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_thenReturnIoSpecificationIdIsNull() {
    // Arrange
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity callActivity = new CallActivity();
    callActivity.setLoopCharacteristics(null);
    callActivity.setIoSpecification(new IOSpecification());
    callActivity.setDataInputAssociations(null);
    callActivity.setDataOutputAssociations(null);
    callActivity.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    IOSpecification ioSpecification = callActivity.clone().getIoSpecification();
    assertNull(ioSpecification.getId());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    assertTrue(ioSpecification.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(ioSpecification.getAttributes().isEmpty());
    assertTrue(ioSpecification.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link CallActivity#clone()}.
   * <ul>
   *   <li>Then return LoopCharacteristics Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_thenReturnLoopCharacteristicsIdIsNull() {
    // Arrange
    CallActivity callActivity = new CallActivity();
    callActivity.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    callActivity.addAttribute(new ExtensionAttribute("Name"));

    // Act
    CallActivity actualCloneResult = callActivity.clone();

    // Assert
    MultiInstanceLoopCharacteristics loopCharacteristics = actualCloneResult.getLoopCharacteristics();
    assertNull(loopCharacteristics.getId());
    assertNull(loopCharacteristics.getCompletionCondition());
    assertNull(loopCharacteristics.getElementIndexVariable());
    assertNull(loopCharacteristics.getElementVariable());
    assertNull(loopCharacteristics.getInputDataItem());
    assertNull(loopCharacteristics.getLoopCardinality());
    assertNull(loopCharacteristics.getLoopDataOutputRef());
    assertNull(loopCharacteristics.getOutputDataItem());
    assertEquals(0, loopCharacteristics.getXmlColumnNumber());
    assertEquals(0, loopCharacteristics.getXmlRowNumber());
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.hasMultiInstanceLoopCharacteristics());
  }

  /**
   * Test {@link CallActivity#setValues(CallActivity)} with {@code CallActivity}.
   * <ul>
   *   <li>Then calls {@link MultiInstanceLoopCharacteristics#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CallActivity#setValues(CallActivity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CallActivity.setValues(CallActivity)"})
  public void testSetValuesWithCallActivity_thenCallsClone() {
    // Arrange
    CallActivity callActivity = new CallActivity();
    MultiInstanceLoopCharacteristics loopCharacteristics = mock(MultiInstanceLoopCharacteristics.class);
    when(loopCharacteristics.clone()).thenReturn(new MultiInstanceLoopCharacteristics());

    CallActivity otherElement = new CallActivity();
    otherElement.setLoopCharacteristics(loopCharacteristics);
    otherElement.addAttribute(new ExtensionAttribute("Name"));

    // Act
    callActivity.setValues(otherElement);

    // Assert
    verify(loopCharacteristics).clone();
  }

  /**
   * Test {@link CallActivity#setValues(CallActivity)} with {@code CallActivity}.
   * <ul>
   *   <li>Then calls {@link IOSpecification#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CallActivity#setValues(CallActivity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CallActivity.setValues(CallActivity)"})
  public void testSetValuesWithCallActivity_thenCallsClone2() {
    // Arrange
    CallActivity callActivity = new CallActivity();
    IOSpecification ioSpecification = mock(IOSpecification.class);
    when(ioSpecification.clone()).thenReturn(new IOSpecification());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity otherElement = new CallActivity();
    otherElement.setLoopCharacteristics(null);
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    callActivity.setValues(otherElement);

    // Assert
    verify(ioSpecification).clone();
  }

  /**
   * Test {@link CallActivity#setValues(CallActivity)} with {@code CallActivity}.
   * <ul>
   *   <li>Then calls {@link DataAssociation#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CallActivity#setValues(CallActivity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CallActivity.setValues(CallActivity)"})
  public void testSetValuesWithCallActivity_thenCallsClone3() {
    // Arrange
    CallActivity callActivity = new CallActivity();
    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(dataAssociation);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity otherElement = new CallActivity();
    otherElement.setLoopCharacteristics(null);
    otherElement.setIoSpecification(null);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(dataOutputAssociations);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    callActivity.setValues(otherElement);

    // Assert
    verify(dataAssociation).clone();
  }

  /**
   * Test {@link CallActivity#setValues(CallActivity)} with {@code CallActivity}.
   * <ul>
   *   <li>Then calls {@link DataAssociation#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CallActivity#setValues(CallActivity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CallActivity.setValues(CallActivity)"})
  public void testSetValuesWithCallActivity_thenCallsClone4() {
    // Arrange
    CallActivity callActivity = new CallActivity();
    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(dataAssociation);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity otherElement = new CallActivity();
    otherElement.setLoopCharacteristics(null);
    otherElement.setIoSpecification(null);
    otherElement.setDataInputAssociations(dataInputAssociations);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    callActivity.setValues(otherElement);

    // Assert
    verify(dataAssociation).clone();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CallActivity}
   *   <li>{@link CallActivity#setBusinessKey(String)}
   *   <li>{@link CallActivity#setCalledElement(String)}
   *   <li>{@link CallActivity#setInParameters(List)}
   *   <li>{@link CallActivity#setInheritBusinessKey(boolean)}
   *   <li>{@link CallActivity#setInheritVariables(boolean)}
   *   <li>{@link CallActivity#setOutParameters(List)}
   *   <li>{@link CallActivity#getBusinessKey()}
   *   <li>{@link CallActivity#getCalledElement()}
   *   <li>{@link CallActivity#getInParameters()}
   *   <li>{@link CallActivity#getOutParameters()}
   *   <li>{@link CallActivity#isInheritBusinessKey()}
   *   <li>{@link CallActivity#isInheritVariables()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CallActivity.<init>()", "String CallActivity.getBusinessKey()",
      "String CallActivity.getCalledElement()", "List CallActivity.getInParameters()",
      "List CallActivity.getOutParameters()", "boolean CallActivity.isInheritBusinessKey()",
      "boolean CallActivity.isInheritVariables()", "void CallActivity.setBusinessKey(String)",
      "void CallActivity.setCalledElement(String)", "void CallActivity.setInParameters(List)",
      "void CallActivity.setInheritBusinessKey(boolean)", "void CallActivity.setInheritVariables(boolean)",
      "void CallActivity.setOutParameters(List)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    CallActivity actualCallActivity = new CallActivity();
    actualCallActivity.setBusinessKey("Business Key");
    actualCallActivity.setCalledElement("Called Element");
    ArrayList<IOParameter> inParameters = new ArrayList<>();
    actualCallActivity.setInParameters(inParameters);
    actualCallActivity.setInheritBusinessKey(true);
    actualCallActivity.setInheritVariables(true);
    ArrayList<IOParameter> outParameters = new ArrayList<>();
    actualCallActivity.setOutParameters(outParameters);
    String actualBusinessKey = actualCallActivity.getBusinessKey();
    String actualCalledElement = actualCallActivity.getCalledElement();
    List<IOParameter> actualInParameters = actualCallActivity.getInParameters();
    List<IOParameter> actualOutParameters = actualCallActivity.getOutParameters();
    boolean actualIsInheritBusinessKeyResult = actualCallActivity.isInheritBusinessKey();
    boolean actualIsInheritVariablesResult = actualCallActivity.isInheritVariables();

    // Assert
    assertEquals("Business Key", actualBusinessKey);
    assertEquals("Called Element", actualCalledElement);
    assertNull(actualCallActivity.getBehavior());
    assertNull(actualCallActivity.getDefaultFlow());
    assertNull(actualCallActivity.getFailedJobRetryTimeCycleValue());
    assertNull(actualCallActivity.getId());
    assertNull(actualCallActivity.getDocumentation());
    assertNull(actualCallActivity.getName());
    assertNull(actualCallActivity.getParentContainer());
    assertNull(actualCallActivity.getIoSpecification());
    assertNull(actualCallActivity.getLoopCharacteristics());
    assertEquals(0, actualCallActivity.getXmlColumnNumber());
    assertEquals(0, actualCallActivity.getXmlRowNumber());
    assertFalse(actualCallActivity.isForCompensation());
    assertFalse(actualCallActivity.isAsynchronous());
    assertFalse(actualCallActivity.isNotExclusive());
    assertTrue(actualCallActivity.getBoundaryEvents().isEmpty());
    assertTrue(actualCallActivity.getDataInputAssociations().isEmpty());
    assertTrue(actualCallActivity.getDataOutputAssociations().isEmpty());
    assertTrue(actualCallActivity.getMapExceptions().isEmpty());
    assertTrue(actualInParameters.isEmpty());
    assertTrue(actualOutParameters.isEmpty());
    assertTrue(actualCallActivity.getExecutionListeners().isEmpty());
    assertTrue(actualCallActivity.getIncomingFlows().isEmpty());
    assertTrue(actualCallActivity.getOutgoingFlows().isEmpty());
    assertTrue(actualCallActivity.getAttributes().isEmpty());
    assertTrue(actualCallActivity.getExtensionElements().isEmpty());
    assertTrue(actualIsInheritBusinessKeyResult);
    assertTrue(actualIsInheritVariablesResult);
    assertSame(inParameters, actualInParameters);
    assertSame(outParameters, actualOutParameters);
  }
}
