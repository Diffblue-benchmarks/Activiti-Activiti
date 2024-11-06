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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ActivitiListenerDiffblueTest {
  /**
   * Test {@link ActivitiListener#clone()}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) FieldExtensions is
   * {@code null}.</li>
   *   <li>Then return Instance is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiListener#clone()}
   */
  @Test
  public void testClone_givenActivitiListenerFieldExtensionsIsNull_thenReturnInstanceIsNull() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(null);

    // Act
    ActivitiListener actualCloneResult = activitiListener.clone();

    // Assert
    assertNull(actualCloneResult.getInstance());
    assertNull(actualCloneResult.getCustomPropertiesResolverImplementation());
    assertNull(actualCloneResult.getCustomPropertiesResolverImplementationType());
    assertNull(actualCloneResult.getEvent());
    assertNull(actualCloneResult.getImplementation());
    assertNull(actualCloneResult.getImplementationType());
    assertNull(actualCloneResult.getOnTransaction());
    assertNull(actualCloneResult.getId());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getFieldExtensions().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link ActivitiListener#clone()}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   *   <li>Then return Instance is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiListener#clone()}
   */
  @Test
  public void testClone_givenActivitiListener_thenReturnInstanceIsNull() {
    // Arrange and Act
    ActivitiListener actualCloneResult = (new ActivitiListener()).clone();

    // Assert
    assertNull(actualCloneResult.getInstance());
    assertNull(actualCloneResult.getCustomPropertiesResolverImplementation());
    assertNull(actualCloneResult.getCustomPropertiesResolverImplementationType());
    assertNull(actualCloneResult.getEvent());
    assertNull(actualCloneResult.getImplementation());
    assertNull(actualCloneResult.getImplementationType());
    assertNull(actualCloneResult.getOnTransaction());
    assertNull(actualCloneResult.getId());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getFieldExtensions().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link ActivitiListener#clone()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default
   * constructor).</li>
   *   <li>Then return FieldExtensions size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiListener#clone()}
   */
  @Test
  public void testClone_givenArrayListAddFieldExtension_thenReturnFieldExtensionsSizeIsOne() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    // Act and Assert
    List<FieldExtension> fieldExtensions2 = activitiListener.clone().getFieldExtensions();
    assertEquals(1, fieldExtensions2.size());
    FieldExtension getResult = fieldExtensions2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getExpression());
    assertNull(getResult.getFieldName());
    assertNull(getResult.getStringValue());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link ActivitiListener#setValues(ActivitiListener)} with
   * {@code otherListener}.
   * <ul>
   *   <li>Then calls {@link FieldExtension#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiListener#setValues(ActivitiListener)}
   */
  @Test
  public void testSetValuesWithOtherListener_thenCallsClone() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.clone()).thenReturn(new FieldExtension());

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ActivitiListener otherListener = new ActivitiListener();
    otherListener.setFieldExtensions(fieldExtensions);

    // Act
    activitiListener.setValues(otherListener);

    // Assert
    verify(fieldExtension).clone();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ActivitiListener}
   *   <li>
   * {@link ActivitiListener#setCustomPropertiesResolverImplementation(String)}
   *   <li>
   * {@link ActivitiListener#setCustomPropertiesResolverImplementationType(String)}
   *   <li>{@link ActivitiListener#setEvent(String)}
   *   <li>{@link ActivitiListener#setFieldExtensions(List)}
   *   <li>{@link ActivitiListener#setImplementation(String)}
   *   <li>{@link ActivitiListener#setImplementationType(String)}
   *   <li>{@link ActivitiListener#setInstance(Object)}
   *   <li>{@link ActivitiListener#setOnTransaction(String)}
   *   <li>{@link ActivitiListener#getCustomPropertiesResolverImplementation()}
   *   <li>{@link ActivitiListener#getCustomPropertiesResolverImplementationType()}
   *   <li>{@link ActivitiListener#getEvent()}
   *   <li>{@link ActivitiListener#getFieldExtensions()}
   *   <li>{@link ActivitiListener#getImplementation()}
   *   <li>{@link ActivitiListener#getImplementationType()}
   *   <li>{@link ActivitiListener#getInstance()}
   *   <li>{@link ActivitiListener#getOnTransaction()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ActivitiListener actualActivitiListener = new ActivitiListener();
    actualActivitiListener.setCustomPropertiesResolverImplementation("Custom Properties Resolver Implementation");
    actualActivitiListener
        .setCustomPropertiesResolverImplementationType("Custom Properties Resolver Implementation Type");
    actualActivitiListener.setEvent("Event");
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    actualActivitiListener.setFieldExtensions(fieldExtensions);
    actualActivitiListener.setImplementation("Implementation");
    actualActivitiListener.setImplementationType("Implementation Type");
    actualActivitiListener.setInstance("Instance");
    actualActivitiListener.setOnTransaction("On Transaction");
    String actualCustomPropertiesResolverImplementation = actualActivitiListener
        .getCustomPropertiesResolverImplementation();
    String actualCustomPropertiesResolverImplementationType = actualActivitiListener
        .getCustomPropertiesResolverImplementationType();
    String actualEvent = actualActivitiListener.getEvent();
    List<FieldExtension> actualFieldExtensions = actualActivitiListener.getFieldExtensions();
    String actualImplementation = actualActivitiListener.getImplementation();
    String actualImplementationType = actualActivitiListener.getImplementationType();
    Object actualInstance = actualActivitiListener.getInstance();

    // Assert that nothing has changed
    assertEquals("Custom Properties Resolver Implementation Type", actualCustomPropertiesResolverImplementationType);
    assertEquals("Custom Properties Resolver Implementation", actualCustomPropertiesResolverImplementation);
    assertEquals("Event", actualEvent);
    assertEquals("Implementation Type", actualImplementationType);
    assertEquals("Implementation", actualImplementation);
    assertEquals("Instance", actualInstance);
    assertEquals("On Transaction", actualActivitiListener.getOnTransaction());
    assertEquals(0, actualActivitiListener.getXmlColumnNumber());
    assertEquals(0, actualActivitiListener.getXmlRowNumber());
    assertTrue(actualFieldExtensions.isEmpty());
    assertTrue(actualActivitiListener.getAttributes().isEmpty());
    assertTrue(actualActivitiListener.getExtensionElements().isEmpty());
    assertSame(fieldExtensions, actualFieldExtensions);
  }
}
