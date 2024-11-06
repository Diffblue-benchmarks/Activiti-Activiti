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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserTaskXMLConverterDiffblueTest {
  /**
   * Test CustomIdentityLinkParser getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link UserTaskXMLConverter.CustomIdentityLinkParser#CustomIdentityLinkParser(UserTaskXMLConverter)}
   *   <li>{@link UserTaskXMLConverter.CustomIdentityLinkParser#getElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test CustomIdentityLinkParser getters and setters")
  void testCustomIdentityLinkParserGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("customResource", ((new UserTaskXMLConverter()).new CustomIdentityLinkParser()).getElementName());
  }

  /**
   * Test HumanPerformerParser getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link UserTaskXMLConverter.HumanPerformerParser#HumanPerformerParser(UserTaskXMLConverter)}
   *   <li>{@link UserTaskXMLConverter.HumanPerformerParser#getElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test HumanPerformerParser getters and setters")
  void testHumanPerformerParserGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("humanPerformer", ((new UserTaskXMLConverter()).new HumanPerformerParser()).getElementName());
  }

  /**
   * Test new {@link UserTaskXMLConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link UserTaskXMLConverter}
   */
  @Test
  @DisplayName("Test new UserTaskXMLConverter (default constructor)")
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

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link UserTaskXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)}
   *   <li>{@link UserTaskXMLConverter#getBpmnElementType()}
   *   <li>{@link UserTaskXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
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
   * Test PotentialOwnerParser getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link UserTaskXMLConverter.PotentialOwnerParser#PotentialOwnerParser(UserTaskXMLConverter)}
   *   <li>{@link UserTaskXMLConverter.PotentialOwnerParser#getElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test PotentialOwnerParser getters and setters")
  void testPotentialOwnerParserGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("potentialOwner", ((new UserTaskXMLConverter()).new PotentialOwnerParser()).getElementName());
  }

  /**
   * Test
   * {@link UserTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link CustomProperty} (default constructor) SimpleValue is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UserTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); given CustomProperty (default constructor) SimpleValue is empty string")
  void testWriteExtensionChildElements_givenCustomPropertySimpleValueIsEmptyString() throws Exception {
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
   * Test
   * {@link UserTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UserTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); then return 'true'")
  void testWriteExtensionChildElements_thenReturnTrue() throws Exception {
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
   * Test
   * {@link UserTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UserTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); when 'false'; then return 'false'")
  void testWriteExtensionChildElements_whenFalse_thenReturnFalse() throws Exception {
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
   * Test
   * {@link UserTaskXMLConverter#writeCustomIdentities(BaseElement, boolean, XMLStreamWriter)}
   * with {@code element}, {@code didWriteExtensionStartElement}, {@code xtw}.
   * <p>
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCustomIdentities(BaseElement, boolean, XMLStreamWriter) with 'element', 'didWriteExtensionStartElement', 'xtw'")
  void testWriteCustomIdentitiesWithElementDidWriteExtensionStartElementXtw() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();

    UserTask element = new UserTask();
    element.setCustomUserIdentityLinks(new HashMap<>());
    element.setCustomGroupIdentityLinks(new HashMap<>());

    // Act and Assert
    assertFalse(userTaskXMLConverter.writeCustomIdentities(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link UserTaskXMLConverter#writeCustomIdentities(BaseElement, boolean, XMLStreamWriter)}
   * with {@code element}, {@code didWriteExtensionStartElement}, {@code xtw}.
   * <p>
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCustomIdentities(BaseElement, boolean, XMLStreamWriter) with 'element', 'didWriteExtensionStartElement', 'xtw'")
  void testWriteCustomIdentitiesWithElementDidWriteExtensionStartElementXtw2() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();

    UserTask element = new UserTask();
    element.setCustomUserIdentityLinks(new HashMap<>());
    element.setCustomGroupIdentityLinks(new HashMap<>());

    // Act and Assert
    assertTrue(userTaskXMLConverter.writeCustomIdentities(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   * with {@code userTask}, {@code identityType}, {@code users}, {@code groups},
   * {@code xtw}.
   * <p>
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter) with 'userTask', 'identityType', 'users', 'groups', 'xtw'")
  void testWriteCustomIdentitiesWithUserTaskIdentityTypeUsersGroupsXtw() throws Exception {
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
   * Test
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   * with {@code userTask}, {@code identityType}, {@code users}, {@code groups},
   * {@code xtw}.
   * <p>
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter) with 'userTask', 'identityType', 'users', 'groups', 'xtw'")
  void testWriteCustomIdentitiesWithUserTaskIdentityTypeUsersGroupsXtw2() throws Exception {
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
   * Test
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   * with {@code userTask}, {@code identityType}, {@code users}, {@code groups},
   * {@code xtw}.
   * <p>
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter) with 'userTask', 'identityType', 'users', 'groups', 'xtw'")
  void testWriteCustomIdentitiesWithUserTaskIdentityTypeUsersGroupsXtw3() throws Exception {
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
   * Test
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   * with {@code userTask}, {@code identityType}, {@code users}, {@code groups},
   * {@code xtw}.
   * <p>
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter) with 'userTask', 'identityType', 'users', 'groups', 'xtw'")
  void testWriteCustomIdentitiesWithUserTaskIdentityTypeUsersGroupsXtw4() throws Exception {
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
   * Test
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   * with {@code userTask}, {@code identityType}, {@code users}, {@code groups},
   * {@code xtw}.
   * <p>
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter) with 'userTask', 'identityType', 'users', 'groups', 'xtw'")
  void testWriteCustomIdentitiesWithUserTaskIdentityTypeUsersGroupsXtw5() throws Exception {
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
   * Test
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   * with {@code userTask}, {@code identityType}, {@code users}, {@code groups},
   * {@code xtw}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter) with 'userTask', 'identityType', 'users', 'groups', 'xtw'; when empty string")
  void testWriteCustomIdentitiesWithUserTaskIdentityTypeUsersGroupsXtw_whenEmptyString() throws Exception {
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
   * Test
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   * with {@code userTask}, {@code identityType}, {@code users}, {@code groups},
   * {@code xtw}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter) with 'userTask', 'identityType', 'users', 'groups', 'xtw'; when 'null'")
  void testWriteCustomIdentitiesWithUserTaskIdentityTypeUsersGroupsXtw_whenNull() throws Exception {
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
   * Test
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   * with {@code userTask}, {@code identityType}, {@code users}, {@code groups},
   * {@code xtw}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter) with 'userTask', 'identityType', 'users', 'groups', 'xtw'; when 'null'")
  void testWriteCustomIdentitiesWithUserTaskIdentityTypeUsersGroupsXtw_whenNull2() throws Exception {
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
}
