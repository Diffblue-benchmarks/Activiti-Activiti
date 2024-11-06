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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.Test;

public class DataSpecDiffblueTest {
  /**
   * Test {@link DataSpec#clone()}.
   * <ul>
   *   <li>Given {@link DataSpec} (default constructor) Collection is
   * {@code true}.</li>
   *   <li>Then return Collection.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataSpec#clone()}
   */
  @Test
  public void testClone_givenDataSpecCollectionIsTrue_thenReturnCollection() {
    // Arrange
    DataSpec dataSpec = new DataSpec();
    dataSpec.setCollection(true);

    // Act
    DataSpec actualCloneResult = dataSpec.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isCollection());
  }

  /**
   * Test {@link DataSpec#clone()}.
   * <ul>
   *   <li>Given {@link DataSpec} (default constructor).</li>
   *   <li>Then return not Collection.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataSpec#clone()}
   */
  @Test
  public void testClone_givenDataSpec_thenReturnNotCollection() {
    // Arrange and Act
    DataSpec actualCloneResult = (new DataSpec()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isCollection());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link DataSpec#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   *   <li>Then return not Collection.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataSpec#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction_thenReturnNotCollection() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    DataSpec dataSpec = new DataSpec();
    dataSpec.setExtensionElements(extensionElements);

    // Act
    DataSpec actualCloneResult = dataSpec.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isCollection());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link DataSpec#setValues(DataSpec)} with {@code otherDataSpec}.
   * <ul>
   *   <li>Then calls {@link ExtensionElement#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataSpec#setValues(DataSpec)}
   */
  @Test
  public void testSetValuesWithOtherDataSpec_thenCallsGetName() {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getName()).thenReturn("Name");

    DataSpec dataSpec = new DataSpec();
    dataSpec.addExtensionElement(extensionElement);

    // Act
    dataSpec.setValues(new DataSpec());

    // Assert
    verify(extensionElement, atLeast(1)).getName();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DataSpec}
   *   <li>{@link DataSpec#setCollection(boolean)}
   *   <li>{@link DataSpec#setItemSubjectRef(String)}
   *   <li>{@link DataSpec#setName(String)}
   *   <li>{@link DataSpec#getItemSubjectRef()}
   *   <li>{@link DataSpec#getName()}
   *   <li>{@link DataSpec#isCollection()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    DataSpec actualDataSpec = new DataSpec();
    actualDataSpec.setCollection(true);
    actualDataSpec.setItemSubjectRef("Hello from the Dreaming Spires");
    actualDataSpec.setName("Name");
    String actualItemSubjectRef = actualDataSpec.getItemSubjectRef();
    String actualName = actualDataSpec.getName();
    boolean actualIsCollectionResult = actualDataSpec.isCollection();

    // Assert that nothing has changed
    assertEquals("Hello from the Dreaming Spires", actualItemSubjectRef);
    assertEquals("Name", actualName);
    assertEquals(0, actualDataSpec.getXmlColumnNumber());
    assertEquals(0, actualDataSpec.getXmlRowNumber());
    assertTrue(actualDataSpec.getAttributes().isEmpty());
    assertTrue(actualDataSpec.getExtensionElements().isEmpty());
    assertTrue(actualIsCollectionResult);
  }
}
