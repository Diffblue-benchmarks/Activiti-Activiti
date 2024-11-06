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
package org.activiti.engine.impl.bpmn.behavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContext;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.junit.Test;

public class MailActivityBehaviorDiffblueTest {
  /**
   * Test {@link MailActivityBehavior#execute(DelegateExecution)}.
   * <p>
   * Method under test: {@link MailActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  public void testExecute() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> mailActivityBehavior.execute(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MailActivityBehavior#attach(Email, List, List)}.
   * <ul>
   *   <li>Given {@link ByteArrayDataSource#ByteArrayDataSource(String, String)}
   * with {@code Data} and {@code Type} Name is {@code Data Sources}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#attach(Email, List, List)}
   */
  @Test
  public void testAttach_givenByteArrayDataSourceWithDataAndTypeNameIsDataSources() throws IOException, EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    MultiPartEmail email = new MultiPartEmail();
    ArrayList<File> files = new ArrayList<>();

    ByteArrayDataSource byteArrayDataSource = new ByteArrayDataSource("Data", "Type");
    byteArrayDataSource.setName("Data Sources");

    ArrayList<DataSource> dataSources = new ArrayList<>();
    dataSources.add(byteArrayDataSource);

    // Act
    mailActivityBehavior.attach(email, files, dataSources);

    // Assert
    assertTrue(email.isBoolHasAttachments());
  }

  /**
   * Test {@link MailActivityBehavior#attach(Email, List, List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then not {@link MultiPartEmail} (default constructor)
   * BoolHasAttachments.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#attach(Email, List, List)}
   */
  @Test
  public void testAttach_givenNull_whenArrayList_thenNotMultiPartEmailBoolHasAttachments() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    MultiPartEmail email = new MultiPartEmail();
    ArrayList<File> files = new ArrayList<>();

    ArrayList<DataSource> dataSources = new ArrayList<>();
    dataSources.add(null);

    // Act
    mailActivityBehavior.attach(email, files, dataSources);

    // Assert that nothing has changed
    assertFalse(email.isBoolHasAttachments());
  }

  /**
   * Test {@link MailActivityBehavior#attach(Email, List, List)}.
   * <ul>
   *   <li>Then {@link MultiPartEmail} (default constructor)
   * BoolHasAttachments.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#attach(Email, List, List)}
   */
  @Test
  public void testAttach_thenMultiPartEmailBoolHasAttachments() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    MultiPartEmail email = new MultiPartEmail();

    ArrayList<File> files = new ArrayList<>();
    files.add(Paths.get(System.getProperty("java.io.tmpdir"), "").toFile());

    ArrayList<DataSource> dataSources = new ArrayList<>();
    dataSources.add(null);

    // Act
    mailActivityBehavior.attach(email, files, dataSources);

    // Assert
    assertTrue(email.isBoolHasAttachments());
  }

  /**
   * Test {@link MailActivityBehavior#attach(Email, List, List)}.
   * <ul>
   *   <li>When {@link HtmlEmail} (default constructor).</li>
   *   <li>Then not {@link HtmlEmail} (default constructor) BoolHasAttachments.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#attach(Email, List, List)}
   */
  @Test
  public void testAttach_whenHtmlEmail_thenNotHtmlEmailBoolHasAttachments() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = new HtmlEmail();
    ArrayList<File> files = new ArrayList<>();

    // Act
    mailActivityBehavior.attach(email, files, new ArrayList<>());

    // Assert that nothing has changed
    assertFalse(email.isBoolHasAttachments());
  }

  /**
   * Test {@link MailActivityBehavior#attach(Email, List, List)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then not {@link MultiPartEmail} (default constructor)
   * BoolHasAttachments.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#attach(Email, List, List)}
   */
  @Test
  public void testAttach_whenNull_thenNotMultiPartEmailBoolHasAttachments() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    MultiPartEmail email = new MultiPartEmail();

    // Act
    mailActivityBehavior.attach(email, null, null);

    // Assert that nothing has changed
    assertFalse(email.isBoolHasAttachments());
  }

  /**
   * Test
   * {@link MailActivityBehavior#getStringFromField(Expression, DelegateExecution)}.
   * <ul>
   *   <li>When {@link FixedValue#FixedValue(Object)} with value is
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MailActivityBehavior#getStringFromField(Expression, DelegateExecution)}
   */
  @Test
  public void testGetStringFromField_whenFixedValueWithValueIsNull_thenReturnNull() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    FixedValue expression = new FixedValue(JSONObject.NULL);

    // Act and Assert
    assertEquals("null", mailActivityBehavior.getStringFromField(expression,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test
   * {@link MailActivityBehavior#getStringFromField(Expression, DelegateExecution)}.
   * <ul>
   *   <li>When {@link FixedValue#FixedValue(Object)} with value is
   * {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MailActivityBehavior#getStringFromField(Expression, DelegateExecution)}
   */
  @Test
  public void testGetStringFromField_whenFixedValueWithValueIsNull_thenReturnNull2() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    FixedValue expression = new FixedValue(null);

    // Act and Assert
    assertNull(mailActivityBehavior.getStringFromField(expression,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test
   * {@link MailActivityBehavior#getStringFromField(Expression, DelegateExecution)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MailActivityBehavior#getStringFromField(Expression, DelegateExecution)}
   */
  @Test
  public void testGetStringFromField_whenNull_thenReturnNull() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertNull(
        mailActivityBehavior.getStringFromField(null, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MailActivityBehavior#fileExists(File)}.
   * <p>
   * Method under test: {@link MailActivityBehavior#fileExists(File)}
   */
  @Test
  public void testFileExists() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    mailActivityBehavior
        .setMultiInstanceActivityBehavior(new ParallelMultiInstanceBehavior(activity,
            new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition,
                new DefaultMessageExecutionContext(messageEventDefinition2, new ExpressionManager(),
                    mock(MessagePayloadMappingProvider.class)))));

    // Act and Assert
    assertFalse(mailActivityBehavior.fileExists(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  /**
   * Test {@link MailActivityBehavior#fileExists(File)}.
   * <ul>
   *   <li>Given {@link MailActivityBehavior} (default constructor).</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#fileExists(File)}
   */
  @Test
  public void testFileExists_givenMailActivityBehavior_whenNull() {
    // Arrange, Act and Assert
    assertFalse((new MailActivityBehavior()).fileExists(null));
  }

  /**
   * Test {@link MailActivityBehavior#fileExists(File)}.
   * <ul>
   *   <li>When Property is {@code java.io.tmpdir} is empty string toFile.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#fileExists(File)}
   */
  @Test
  public void testFileExists_whenPropertyIsJavaIoTmpdirIsEmptyStringToFile() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertFalse(mailActivityBehavior.fileExists(Paths.get(System.getProperty("java.io.tmpdir"), "").toFile()));
  }

  /**
   * Test {@link MailActivityBehavior#fileExists(File)}.
   * <ul>
   *   <li>When Property is {@code java.io.tmpdir} is {@code test.txt} toFile.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#fileExists(File)}
   */
  @Test
  public void testFileExists_whenPropertyIsJavaIoTmpdirIsTestTxtToFile() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertFalse(mailActivityBehavior.fileExists(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  /**
   * Test new {@link MailActivityBehavior} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link MailActivityBehavior}
   */
  @Test
  public void testNewMailActivityBehavior() {
    // Arrange and Act
    MailActivityBehavior actualMailActivityBehavior = new MailActivityBehavior();

    // Assert
    assertNull(actualMailActivityBehavior.attachments);
    assertNull(actualMailActivityBehavior.bcc);
    assertNull(actualMailActivityBehavior.cc);
    assertNull(actualMailActivityBehavior.charset);
    assertNull(actualMailActivityBehavior.exceptionVariableName);
    assertNull(actualMailActivityBehavior.from);
    assertNull(actualMailActivityBehavior.html);
    assertNull(actualMailActivityBehavior.htmlVar);
    assertNull(actualMailActivityBehavior.ignoreException);
    assertNull(actualMailActivityBehavior.subject);
    assertNull(actualMailActivityBehavior.text);
    assertNull(actualMailActivityBehavior.textVar);
    assertNull(actualMailActivityBehavior.to);
    assertNull(actualMailActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualMailActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualMailActivityBehavior.hasMultiInstanceCharacteristics());
  }
}
