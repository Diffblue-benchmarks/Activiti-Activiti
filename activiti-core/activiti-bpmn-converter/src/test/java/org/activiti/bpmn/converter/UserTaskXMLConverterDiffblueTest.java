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
package org.activiti.bpmn.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.child.BaseChildElementParser;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CustomProperty;
import org.activiti.bpmn.model.UserTask;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserTaskXMLConverterDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link UserTaskXMLConverter.CustomIdentityLinkParser#CustomIdentityLinkParser(UserTaskXMLConverter)}
   *   <li>{@link UserTaskXMLConverter.CustomIdentityLinkParser#getElementName()}
   * </ul>
   */
  @Test
  void testCustomIdentityLinkParserGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("customResource", ((new UserTaskXMLConverter()).new CustomIdentityLinkParser()).getElementName());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link UserTaskXMLConverter.HumanPerformerParser#HumanPerformerParser(UserTaskXMLConverter)}
   *   <li>{@link UserTaskXMLConverter.HumanPerformerParser#getElementName()}
   * </ul>
   */
  @Test
  void testHumanPerformerParserGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("humanPerformer", ((new UserTaskXMLConverter()).new HumanPerformerParser()).getElementName());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link UserTaskXMLConverter.PotentialOwnerParser#PotentialOwnerParser(UserTaskXMLConverter)}
   *   <li>{@link UserTaskXMLConverter.PotentialOwnerParser#getElementName()}
   * </ul>
   */
  @Test
  void testPotentialOwnerParserGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("potentialOwner", ((new UserTaskXMLConverter()).new PotentialOwnerParser()).getElementName());
  }

  /**
   * Method under test:
   * {@link UserTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionChildElements() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();

    CustomProperty customProperty = new CustomProperty();
    customProperty.setSimpleValue(null);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    UserTask element = new UserTask();
    element.setCustomProperties(customProperties);
    element.setCustomUserIdentityLinks(new HashMap<>());
    element.setCustomGroupIdentityLinks(new HashMap<>());

    // Act and Assert
    assertTrue(userTaskXMLConverter.writeExtensionChildElements(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link UserTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionChildElements2() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();

    CustomProperty customProperty = new CustomProperty();
    customProperty.setSimpleValue("");

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    UserTask element = new UserTask();
    element.setCustomProperties(customProperties);
    element.setCustomUserIdentityLinks(new HashMap<>());
    element.setCustomGroupIdentityLinks(new HashMap<>());

    // Act and Assert
    assertTrue(userTaskXMLConverter.writeExtensionChildElements(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link UserTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionChildElements3() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();

    CustomProperty customProperty = new CustomProperty();
    customProperty.setSimpleValue(null);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    UserTask element = new UserTask();
    element.setCustomProperties(customProperties);
    element.setCustomUserIdentityLinks(new HashMap<>());
    element.setCustomGroupIdentityLinks(new HashMap<>());

    // Act and Assert
    assertFalse(userTaskXMLConverter.writeExtensionChildElements(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link UserTaskXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)}
   *   <li>{@link UserTaskXMLConverter#getBpmnElementType()}
   *   <li>{@link UserTaskXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();
    ActivitiListener element = new ActivitiListener();
    BpmnModel model = new BpmnModel();

    // Act
    userTaskXMLConverter.writeAdditionalChildElements(element, model, new IndentingXMLStreamWriter(null));
    Class<? extends BaseElement> actualBpmnElementType = userTaskXMLConverter.getBpmnElementType();

    // Assert that nothing has changed
    assertEquals("userTask", userTaskXMLConverter.getXMLElementName());
    Class<UserTask> expectedBpmnElementType = UserTask.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }

  /**
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteCustomIdentities() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();

    UserTask element = new UserTask();
    element.setCustomUserIdentityLinks(new HashMap<>());
    element.setCustomGroupIdentityLinks(new HashMap<>());

    // Act and Assert
    assertFalse(userTaskXMLConverter.writeCustomIdentities(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteCustomIdentities2() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();

    UserTask element = new UserTask();
    element.setCustomUserIdentityLinks(new HashMap<>());
    element.setCustomGroupIdentityLinks(new HashMap<>());

    // Act and Assert
    assertTrue(userTaskXMLConverter.writeCustomIdentities(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   */
  @Test
  void testWriteCustomIdentities3() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();
    UserTask userTask = new UserTask();
    HashSet<String> users = new HashSet<>();
    HashSet<String> groups = new HashSet<>();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    userTaskXMLConverter.writeCustomIdentities(userTask, "Identity Type", users, groups, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("name"), eq("Identity Type"));
    verify(xtw).writeCharacters(eq(""));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("activiti"), eq("customResource"), eq("http://activiti.org/bpmn"));
  }

  /**
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   */
  @Test
  void testWriteCustomIdentities4() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();
    UserTask userTask = new UserTask();
    HashSet<String> users = new HashSet<>();
    HashSet<String> groups = new HashSet<>();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    userTaskXMLConverter.writeCustomIdentities(userTask, "null", users, groups, xtw);

    // Assert that nothing has changed
    verify(xtw).writeCharacters(eq(""));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("activiti"), eq("customResource"), eq("http://activiti.org/bpmn"));
  }

  /**
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   */
  @Test
  void testWriteCustomIdentities5() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();
    UserTask userTask = new UserTask();
    HashSet<String> users = new HashSet<>();
    HashSet<String> groups = new HashSet<>();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    userTaskXMLConverter.writeCustomIdentities(userTask, null, users, groups, xtw);

    // Assert that nothing has changed
    verify(xtw).writeCharacters(eq(""));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("activiti"), eq("customResource"), eq("http://activiti.org/bpmn"));
  }

  /**
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   */
  @Test
  void testWriteCustomIdentities6() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();
    UserTask userTask = new UserTask();
    HashSet<String> users = new HashSet<>();
    HashSet<String> groups = new HashSet<>();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    userTaskXMLConverter.writeCustomIdentities(userTask, "", users, groups, xtw);

    // Assert that nothing has changed
    verify(xtw).writeCharacters(eq(""));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("activiti"), eq("customResource"), eq("http://activiti.org/bpmn"));
  }

  /**
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   */
  @Test
  void testWriteCustomIdentities7() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();
    UserTask userTask = new UserTask();

    HashSet<String> users = new HashSet<>();
    users.add("customResource");
    HashSet<String> groups = new HashSet<>();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    userTaskXMLConverter.writeCustomIdentities(userTask, "Identity Type", users, groups, xtw);

    // Assert
    verify(xtw).writeAttribute(eq("name"), eq("Identity Type"));
    verify(xtw).writeCharacters(eq("user(customResource)"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("activiti"), eq("customResource"), eq("http://activiti.org/bpmn"));
  }

  /**
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   */
  @Test
  void testWriteCustomIdentities8() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();
    UserTask userTask = new UserTask();
    HashSet<String> users = new HashSet<>();

    HashSet<String> groups = new HashSet<>();
    groups.add("customResource");
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    userTaskXMLConverter.writeCustomIdentities(userTask, "Identity Type", users, groups, xtw);

    // Assert
    verify(xtw).writeAttribute(eq("name"), eq("Identity Type"));
    verify(xtw).writeCharacters(eq("group(customResource)"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("activiti"), eq("customResource"), eq("http://activiti.org/bpmn"));
  }

  /**
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   */
  @Test
  void testWriteCustomIdentities9() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();
    UserTask userTask = new UserTask();

    HashSet<String> users = new HashSet<>();
    users.add("http://activiti.org/bpmn");
    users.add("customResource");
    HashSet<String> groups = new HashSet<>();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    userTaskXMLConverter.writeCustomIdentities(userTask, "Identity Type", users, groups, xtw);

    // Assert
    verify(xtw).writeAttribute(eq("name"), eq("Identity Type"));
    verify(xtw).writeCharacters(eq("user(http://activiti.org/bpmn),user(customResource)"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("activiti"), eq("customResource"), eq("http://activiti.org/bpmn"));
  }

  /**
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   */
  @Test
  void testWriteCustomIdentities10() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();
    UserTask userTask = new UserTask();
    HashSet<String> users = new HashSet<>();

    HashSet<String> groups = new HashSet<>();
    groups.add("http://activiti.org/bpmn");
    groups.add("customResource");
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    userTaskXMLConverter.writeCustomIdentities(userTask, "Identity Type", users, groups, xtw);

    // Assert
    verify(xtw).writeAttribute(eq("name"), eq("Identity Type"));
    verify(xtw).writeCharacters(eq("group(http://activiti.org/bpmn),group(customResource)"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("activiti"), eq("customResource"), eq("http://activiti.org/bpmn"));
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link UserTaskXMLConverter}
   */
  @Test
  void testNewUserTaskXMLConverter() {
    // Arrange and Act
    UserTaskXMLConverter actualUserTaskXMLConverter = new UserTaskXMLConverter();

    // Assert
    Map<String, BaseChildElementParser> stringBaseChildElementParserMap = actualUserTaskXMLConverter.childParserMap;
    assertEquals(3, stringBaseChildElementParserMap.size());
    BaseChildElementParser getResult = stringBaseChildElementParserMap.get("customResource");
    assertTrue(getResult instanceof UserTaskXMLConverter.CustomIdentityLinkParser);
    BaseChildElementParser getResult2 = stringBaseChildElementParserMap.get("humanPerformer");
    assertTrue(getResult2 instanceof UserTaskXMLConverter.HumanPerformerParser);
    BaseChildElementParser getResult3 = stringBaseChildElementParserMap.get("potentialOwner");
    assertTrue(getResult3 instanceof UserTaskXMLConverter.PotentialOwnerParser);
    assertEquals("customResource", getResult.getElementName());
    assertEquals("humanPerformer", getResult2.getElementName());
    assertEquals("potentialOwner", getResult3.getElementName());
    assertEquals("userTask", actualUserTaskXMLConverter.getXMLElementName());
    Class<UserTask> expectedBpmnElementType = UserTask.class;
    assertEquals(expectedBpmnElementType, actualUserTaskXMLConverter.getBpmnElementType());
  }
}
