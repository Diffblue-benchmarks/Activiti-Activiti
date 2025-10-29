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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.activation.DataSource;
import javax.mail.internet.InternetAddress;
import javax.mail.util.ByteArrayDataSource;
import javax.naming.NamingException;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.engine.ActivitiException;
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
import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MailActivityBehaviorDiffblueTest {
  @InjectMocks
  private MailActivityBehavior mailActivityBehavior;

  /**
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
   * Method under test:
   * {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  public void testCreateEmail() {
    // Arrange and Act
    Email actualCreateEmailResult = mailActivityBehavior.createEmail("Text", "Html", true);

    // Assert
    assertTrue(actualCreateEmailResult instanceof HtmlEmail);
    assertEquals("25", actualCreateEmailResult.getSmtpPort());
    assertEquals("465", actualCreateEmailResult.getSslSmtpPort());
    assertNull(actualCreateEmailResult.getBounceAddress());
    assertNull(actualCreateEmailResult.getHostName());
    assertNull(actualCreateEmailResult.getSubject());
    assertNull(((HtmlEmail) actualCreateEmailResult).getSubType());
    assertNull(actualCreateEmailResult.getFromAddress());
    assertNull(actualCreateEmailResult.getMimeMessage());
    assertEquals(60000, actualCreateEmailResult.getSocketConnectionTimeout());
    assertEquals(60000, actualCreateEmailResult.getSocketTimeout());
    assertFalse(actualCreateEmailResult.isSSL());
    assertFalse(actualCreateEmailResult.isSSLCheckServerIdentity());
    assertFalse(actualCreateEmailResult.isSSLOnConnect());
    assertFalse(actualCreateEmailResult.isSendPartial());
    assertFalse(actualCreateEmailResult.isStartTLSEnabled());
    assertFalse(actualCreateEmailResult.isStartTLSRequired());
    assertFalse(actualCreateEmailResult.isTLS());
    assertFalse(((HtmlEmail) actualCreateEmailResult).isBoolHasAttachments());
    assertTrue(actualCreateEmailResult.getBccAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getCcAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getReplyToAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getToAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  public void testCreateEmail2() {
    // Arrange and Act
    Email actualCreateEmailResult = mailActivityBehavior.createEmail(null, "Html", true);

    // Assert
    assertTrue(actualCreateEmailResult instanceof HtmlEmail);
    assertEquals("25", actualCreateEmailResult.getSmtpPort());
    assertEquals("465", actualCreateEmailResult.getSslSmtpPort());
    assertNull(actualCreateEmailResult.getBounceAddress());
    assertNull(actualCreateEmailResult.getHostName());
    assertNull(actualCreateEmailResult.getSubject());
    assertNull(((HtmlEmail) actualCreateEmailResult).getSubType());
    assertNull(actualCreateEmailResult.getFromAddress());
    assertNull(actualCreateEmailResult.getMimeMessage());
    assertEquals(60000, actualCreateEmailResult.getSocketConnectionTimeout());
    assertEquals(60000, actualCreateEmailResult.getSocketTimeout());
    assertFalse(actualCreateEmailResult.isSSL());
    assertFalse(actualCreateEmailResult.isSSLCheckServerIdentity());
    assertFalse(actualCreateEmailResult.isSSLOnConnect());
    assertFalse(actualCreateEmailResult.isSendPartial());
    assertFalse(actualCreateEmailResult.isStartTLSEnabled());
    assertFalse(actualCreateEmailResult.isStartTLSRequired());
    assertFalse(actualCreateEmailResult.isTLS());
    assertFalse(((HtmlEmail) actualCreateEmailResult).isBoolHasAttachments());
    assertTrue(actualCreateEmailResult.getBccAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getCcAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getReplyToAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getToAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  public void testCreateEmail3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.createEmail("", "Html", true));
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  public void testCreateEmail4() {
    // Arrange and Act
    Email actualCreateEmailResult = mailActivityBehavior.createEmail("Text", null, true);

    // Assert
    assertTrue(actualCreateEmailResult instanceof MultiPartEmail);
    assertEquals("25", actualCreateEmailResult.getSmtpPort());
    assertEquals("465", actualCreateEmailResult.getSslSmtpPort());
    assertNull(actualCreateEmailResult.getBounceAddress());
    assertNull(actualCreateEmailResult.getHostName());
    assertNull(actualCreateEmailResult.getSubject());
    assertNull(((MultiPartEmail) actualCreateEmailResult).getSubType());
    assertNull(actualCreateEmailResult.getFromAddress());
    assertNull(actualCreateEmailResult.getMimeMessage());
    assertEquals(60000, actualCreateEmailResult.getSocketConnectionTimeout());
    assertEquals(60000, actualCreateEmailResult.getSocketTimeout());
    assertFalse(actualCreateEmailResult.isSSL());
    assertFalse(actualCreateEmailResult.isSSLCheckServerIdentity());
    assertFalse(actualCreateEmailResult.isSSLOnConnect());
    assertFalse(actualCreateEmailResult.isSendPartial());
    assertFalse(actualCreateEmailResult.isStartTLSEnabled());
    assertFalse(actualCreateEmailResult.isStartTLSRequired());
    assertFalse(actualCreateEmailResult.isTLS());
    assertFalse(((MultiPartEmail) actualCreateEmailResult).isBoolHasAttachments());
    assertTrue(actualCreateEmailResult.getBccAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getCcAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getReplyToAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getToAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  public void testCreateEmail5() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.createEmail("Text", "", true));
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  public void testCreateEmail6() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> mailActivityBehavior.createEmail(null, null, true));
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  public void testCreateEmail7() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.createEmail("", null, true));
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  public void testCreateEmail8() {
    // Arrange and Act
    Email actualCreateEmailResult = mailActivityBehavior.createEmail("Text", null, false);

    // Assert
    assertTrue(actualCreateEmailResult instanceof SimpleEmail);
    assertEquals("25", actualCreateEmailResult.getSmtpPort());
    assertEquals("465", actualCreateEmailResult.getSslSmtpPort());
    assertNull(actualCreateEmailResult.getBounceAddress());
    assertNull(actualCreateEmailResult.getHostName());
    assertNull(actualCreateEmailResult.getSubject());
    assertNull(actualCreateEmailResult.getFromAddress());
    assertNull(actualCreateEmailResult.getMimeMessage());
    assertEquals(60000, actualCreateEmailResult.getSocketConnectionTimeout());
    assertEquals(60000, actualCreateEmailResult.getSocketTimeout());
    assertFalse(actualCreateEmailResult.isSSL());
    assertFalse(actualCreateEmailResult.isSSLCheckServerIdentity());
    assertFalse(actualCreateEmailResult.isSSLOnConnect());
    assertFalse(actualCreateEmailResult.isSendPartial());
    assertFalse(actualCreateEmailResult.isStartTLSEnabled());
    assertFalse(actualCreateEmailResult.isStartTLSRequired());
    assertFalse(actualCreateEmailResult.isTLS());
    assertTrue(actualCreateEmailResult.getBccAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getCcAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getReplyToAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getToAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  public void testCreateEmail9() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.createEmail("", null, false));
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#createHtmlEmail(String, String)}
   */
  @Test
  public void testCreateHtmlEmail() {
    // Arrange and Act
    HtmlEmail actualCreateHtmlEmailResult = mailActivityBehavior.createHtmlEmail("Text", "Html");

    // Assert
    assertEquals("25", actualCreateHtmlEmailResult.getSmtpPort());
    assertEquals("465", actualCreateHtmlEmailResult.getSslSmtpPort());
    assertNull(actualCreateHtmlEmailResult.getBounceAddress());
    assertNull(actualCreateHtmlEmailResult.getHostName());
    assertNull(actualCreateHtmlEmailResult.getSubject());
    assertNull(actualCreateHtmlEmailResult.getSubType());
    assertNull(actualCreateHtmlEmailResult.getFromAddress());
    assertNull(actualCreateHtmlEmailResult.getMimeMessage());
    assertEquals(60000, actualCreateHtmlEmailResult.getSocketConnectionTimeout());
    assertEquals(60000, actualCreateHtmlEmailResult.getSocketTimeout());
    assertFalse(actualCreateHtmlEmailResult.isSSL());
    assertFalse(actualCreateHtmlEmailResult.isSSLCheckServerIdentity());
    assertFalse(actualCreateHtmlEmailResult.isSSLOnConnect());
    assertFalse(actualCreateHtmlEmailResult.isSendPartial());
    assertFalse(actualCreateHtmlEmailResult.isStartTLSEnabled());
    assertFalse(actualCreateHtmlEmailResult.isStartTLSRequired());
    assertFalse(actualCreateHtmlEmailResult.isTLS());
    assertFalse(actualCreateHtmlEmailResult.isBoolHasAttachments());
    assertTrue(actualCreateHtmlEmailResult.getBccAddresses().isEmpty());
    assertTrue(actualCreateHtmlEmailResult.getCcAddresses().isEmpty());
    assertTrue(actualCreateHtmlEmailResult.getReplyToAddresses().isEmpty());
    assertTrue(actualCreateHtmlEmailResult.getToAddresses().isEmpty());
    assertTrue(actualCreateHtmlEmailResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#createHtmlEmail(String, String)}
   */
  @Test
  public void testCreateHtmlEmail2() {
    // Arrange and Act
    HtmlEmail actualCreateHtmlEmailResult = mailActivityBehavior.createHtmlEmail(null, "Html");

    // Assert
    assertEquals("25", actualCreateHtmlEmailResult.getSmtpPort());
    assertEquals("465", actualCreateHtmlEmailResult.getSslSmtpPort());
    assertNull(actualCreateHtmlEmailResult.getBounceAddress());
    assertNull(actualCreateHtmlEmailResult.getHostName());
    assertNull(actualCreateHtmlEmailResult.getSubject());
    assertNull(actualCreateHtmlEmailResult.getSubType());
    assertNull(actualCreateHtmlEmailResult.getFromAddress());
    assertNull(actualCreateHtmlEmailResult.getMimeMessage());
    assertEquals(60000, actualCreateHtmlEmailResult.getSocketConnectionTimeout());
    assertEquals(60000, actualCreateHtmlEmailResult.getSocketTimeout());
    assertFalse(actualCreateHtmlEmailResult.isSSL());
    assertFalse(actualCreateHtmlEmailResult.isSSLCheckServerIdentity());
    assertFalse(actualCreateHtmlEmailResult.isSSLOnConnect());
    assertFalse(actualCreateHtmlEmailResult.isSendPartial());
    assertFalse(actualCreateHtmlEmailResult.isStartTLSEnabled());
    assertFalse(actualCreateHtmlEmailResult.isStartTLSRequired());
    assertFalse(actualCreateHtmlEmailResult.isTLS());
    assertFalse(actualCreateHtmlEmailResult.isBoolHasAttachments());
    assertTrue(actualCreateHtmlEmailResult.getBccAddresses().isEmpty());
    assertTrue(actualCreateHtmlEmailResult.getCcAddresses().isEmpty());
    assertTrue(actualCreateHtmlEmailResult.getReplyToAddresses().isEmpty());
    assertTrue(actualCreateHtmlEmailResult.getToAddresses().isEmpty());
    assertTrue(actualCreateHtmlEmailResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#createHtmlEmail(String, String)}
   */
  @Test
  public void testCreateHtmlEmail3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.createHtmlEmail("", "Html"));
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#createHtmlEmail(String, String)}
   */
  @Test
  public void testCreateHtmlEmail4() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.createHtmlEmail("Text", null));
  }

  /**
   * Method under test: {@link MailActivityBehavior#createTextOnlyEmail(String)}
   */
  @Test
  public void testCreateTextOnlyEmail() {
    // Arrange and Act
    SimpleEmail actualCreateTextOnlyEmailResult = mailActivityBehavior.createTextOnlyEmail("Text");

    // Assert
    assertEquals("25", actualCreateTextOnlyEmailResult.getSmtpPort());
    assertEquals("465", actualCreateTextOnlyEmailResult.getSslSmtpPort());
    assertNull(actualCreateTextOnlyEmailResult.getBounceAddress());
    assertNull(actualCreateTextOnlyEmailResult.getHostName());
    assertNull(actualCreateTextOnlyEmailResult.getSubject());
    assertNull(actualCreateTextOnlyEmailResult.getFromAddress());
    assertNull(actualCreateTextOnlyEmailResult.getMimeMessage());
    assertEquals(60000, actualCreateTextOnlyEmailResult.getSocketConnectionTimeout());
    assertEquals(60000, actualCreateTextOnlyEmailResult.getSocketTimeout());
    assertFalse(actualCreateTextOnlyEmailResult.isSSL());
    assertFalse(actualCreateTextOnlyEmailResult.isSSLCheckServerIdentity());
    assertFalse(actualCreateTextOnlyEmailResult.isSSLOnConnect());
    assertFalse(actualCreateTextOnlyEmailResult.isSendPartial());
    assertFalse(actualCreateTextOnlyEmailResult.isStartTLSEnabled());
    assertFalse(actualCreateTextOnlyEmailResult.isStartTLSRequired());
    assertFalse(actualCreateTextOnlyEmailResult.isTLS());
    assertTrue(actualCreateTextOnlyEmailResult.getBccAddresses().isEmpty());
    assertTrue(actualCreateTextOnlyEmailResult.getCcAddresses().isEmpty());
    assertTrue(actualCreateTextOnlyEmailResult.getReplyToAddresses().isEmpty());
    assertTrue(actualCreateTextOnlyEmailResult.getToAddresses().isEmpty());
    assertTrue(actualCreateTextOnlyEmailResult.getHeaders().isEmpty());
  }

  /**
   * Method under test: {@link MailActivityBehavior#createTextOnlyEmail(String)}
   */
  @Test
  public void testCreateTextOnlyEmail2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.createTextOnlyEmail(""));
  }

  /**
   * Method under test: {@link MailActivityBehavior#createMultiPartEmail(String)}
   */
  @Test
  public void testCreateMultiPartEmail() {
    // Arrange and Act
    MultiPartEmail actualCreateMultiPartEmailResult = mailActivityBehavior.createMultiPartEmail("Text");

    // Assert
    assertEquals("25", actualCreateMultiPartEmailResult.getSmtpPort());
    assertEquals("465", actualCreateMultiPartEmailResult.getSslSmtpPort());
    assertNull(actualCreateMultiPartEmailResult.getBounceAddress());
    assertNull(actualCreateMultiPartEmailResult.getHostName());
    assertNull(actualCreateMultiPartEmailResult.getSubject());
    assertNull(actualCreateMultiPartEmailResult.getSubType());
    assertNull(actualCreateMultiPartEmailResult.getFromAddress());
    assertNull(actualCreateMultiPartEmailResult.getMimeMessage());
    assertEquals(60000, actualCreateMultiPartEmailResult.getSocketConnectionTimeout());
    assertEquals(60000, actualCreateMultiPartEmailResult.getSocketTimeout());
    assertFalse(actualCreateMultiPartEmailResult.isSSL());
    assertFalse(actualCreateMultiPartEmailResult.isSSLCheckServerIdentity());
    assertFalse(actualCreateMultiPartEmailResult.isSSLOnConnect());
    assertFalse(actualCreateMultiPartEmailResult.isSendPartial());
    assertFalse(actualCreateMultiPartEmailResult.isStartTLSEnabled());
    assertFalse(actualCreateMultiPartEmailResult.isStartTLSRequired());
    assertFalse(actualCreateMultiPartEmailResult.isTLS());
    assertFalse(actualCreateMultiPartEmailResult.isBoolHasAttachments());
    assertTrue(actualCreateMultiPartEmailResult.getBccAddresses().isEmpty());
    assertTrue(actualCreateMultiPartEmailResult.getCcAddresses().isEmpty());
    assertTrue(actualCreateMultiPartEmailResult.getReplyToAddresses().isEmpty());
    assertTrue(actualCreateMultiPartEmailResult.getToAddresses().isEmpty());
    assertTrue(actualCreateMultiPartEmailResult.getHeaders().isEmpty());
  }

  /**
   * Method under test: {@link MailActivityBehavior#createMultiPartEmail(String)}
   */
  @Test
  public void testCreateMultiPartEmail2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.createMultiPartEmail(""));
  }

  /**
   * Method under test: {@link MailActivityBehavior#addTo(Email, String)}
   */
  @Test
  public void testAddTo() {
    // Arrange
    HtmlEmail email = new HtmlEmail();

    // Act
    mailActivityBehavior.addTo(email, "alice.liddell@example.org");

    // Assert
    List<InternetAddress> toAddresses = email.getToAddresses();
    assertEquals(1, toAddresses.size());
    InternetAddress getResult = toAddresses.get(0);
    assertEquals("alice.liddell@example.org", getResult.getAddress());
    assertEquals("rfc822", getResult.getType());
    assertNull(getResult.getPersonal());
  }

  /**
   * Method under test: {@link MailActivityBehavior#addTo(Email, String)}
   */
  @Test
  public void testAddTo2() throws EmailException {
    // Arrange
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.addTo(Mockito.<String>any())).thenReturn(new HtmlEmail());

    // Act
    mailActivityBehavior.addTo(email, "alice.liddell@example.org");

    // Assert
    verify(email).addTo(eq("alice.liddell@example.org"));
  }

  /**
   * Method under test: {@link MailActivityBehavior#addTo(Email, String)}
   */
  @Test
  public void testAddTo3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.addTo(mock(HtmlEmail.class), null));
  }

  /**
   * Method under test: {@link MailActivityBehavior#addTo(Email, String)}
   */
  @Test
  public void testAddTo4() throws EmailException {
    // Arrange
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.addTo(Mockito.<String>any())).thenThrow(new EmailException(","));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.addTo(email, "alice.liddell@example.org"));
    verify(email).addTo(eq("alice.liddell@example.org"));
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#setFrom(Email, String, String)}
   */
  @Test
  public void testSetFrom() {
    // Arrange
    HtmlEmail email = new HtmlEmail();

    // Act
    mailActivityBehavior.setFrom(email, "jane.doe@example.org", "42");

    // Assert
    InternetAddress fromAddress = email.getFromAddress();
    assertEquals("jane.doe@example.org", fromAddress.getAddress());
    assertEquals("rfc822", fromAddress.getType());
    assertNull(fromAddress.getPersonal());
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#setFrom(Email, String, String)}
   */
  @Test
  public void testSetFrom2() throws EmailException {
    // Arrange
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.setFrom(Mockito.<String>any())).thenReturn(new HtmlEmail());

    // Act
    mailActivityBehavior.setFrom(email, "jane.doe@example.org", "42");

    // Assert that nothing has changed
    verify(email).setFrom(eq("jane.doe@example.org"));
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#setFrom(Email, String, String)}
   */
  @Test
  public void testSetFrom3() throws EmailException {
    // Arrange
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.setFrom(Mockito.<String>any())).thenThrow(new EmailException("Msg"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.setFrom(email, "jane.doe@example.org", "42"));
    verify(email).setFrom(eq("jane.doe@example.org"));
  }

  /**
   * Method under test: {@link MailActivityBehavior#addCc(Email, String)}
   */
  @Test
  public void testAddCc() {
    // Arrange
    HtmlEmail email = new HtmlEmail();

    // Act
    mailActivityBehavior.addCc(email, "ada.lovelace@example.org");

    // Assert
    List<InternetAddress> ccAddresses = email.getCcAddresses();
    assertEquals(1, ccAddresses.size());
    InternetAddress getResult = ccAddresses.get(0);
    assertEquals("ada.lovelace@example.org", getResult.getAddress());
    assertEquals("rfc822", getResult.getType());
    assertNull(getResult.getPersonal());
  }

  /**
   * Method under test: {@link MailActivityBehavior#addCc(Email, String)}
   */
  @Test
  public void testAddCc2() throws EmailException {
    // Arrange
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.addCc(Mockito.<String>any())).thenReturn(new HtmlEmail());

    // Act
    mailActivityBehavior.addCc(email, "ada.lovelace@example.org");

    // Assert
    verify(email).addCc(eq("ada.lovelace@example.org"));
  }

  /**
   * Method under test: {@link MailActivityBehavior#addCc(Email, String)}
   */
  @Test
  public void testAddCc3() throws EmailException {
    // Arrange
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.addCc(Mockito.<String>any())).thenThrow(new EmailException(","));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.addCc(email, "ada.lovelace@example.org"));
    verify(email).addCc(eq("ada.lovelace@example.org"));
  }

  /**
   * Method under test: {@link MailActivityBehavior#addBcc(Email, String)}
   */
  @Test
  public void testAddBcc() {
    // Arrange
    HtmlEmail email = new HtmlEmail();

    // Act
    mailActivityBehavior.addBcc(email, "ada.lovelace@example.org");

    // Assert
    List<InternetAddress> bccAddresses = email.getBccAddresses();
    assertEquals(1, bccAddresses.size());
    InternetAddress getResult = bccAddresses.get(0);
    assertEquals("ada.lovelace@example.org", getResult.getAddress());
    assertEquals("rfc822", getResult.getType());
    assertNull(getResult.getPersonal());
    assertTrue(email.getReplyToAddresses().isEmpty());
    assertTrue(email.getToAddresses().isEmpty());
  }

  /**
   * Method under test: {@link MailActivityBehavior#addBcc(Email, String)}
   */
  @Test
  public void testAddBcc2() throws EmailException {
    // Arrange
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.addBcc(Mockito.<String>any())).thenReturn(new HtmlEmail());

    // Act
    mailActivityBehavior.addBcc(email, "ada.lovelace@example.org");

    // Assert
    verify(email).addBcc(eq("ada.lovelace@example.org"));
  }

  /**
   * Method under test: {@link MailActivityBehavior#addBcc(Email, String)}
   */
  @Test
  public void testAddBcc3() throws EmailException {
    // Arrange
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.addBcc(Mockito.<String>any())).thenThrow(new EmailException(","));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.addBcc(email, "ada.lovelace@example.org"));
    verify(email).addBcc(eq("ada.lovelace@example.org"));
  }

  /**
   * Method under test: {@link MailActivityBehavior#attach(Email, List, List)}
   */
  @Test
  public void testAttach() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    MultiPartEmail email = new MultiPartEmail();

    // Act
    mailActivityBehavior.attach(email, null, null);

    // Assert that nothing has changed
    assertFalse(email.isBoolHasAttachments());
  }

  /**
   * Method under test: {@link MailActivityBehavior#attach(Email, List, List)}
   */
  @Test
  public void testAttach2() throws EmailException {
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
   * Method under test: {@link MailActivityBehavior#attach(Email, List, List)}
   */
  @Test
  public void testAttach3() throws IOException, EmailException {
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
   * Method under test: {@link MailActivityBehavior#attach(Email, List, List)}
   */
  @Test
  public void testAttach4() throws EmailException {
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
   * Method under test: {@link MailActivityBehavior#setSubject(Email, String)}
   */
  @Test
  public void testSetSubject() {
    // Arrange
    HtmlEmail email = new HtmlEmail();

    // Act
    mailActivityBehavior.setSubject(email, "Hello from the Dreaming Spires");

    // Assert
    assertEquals("Hello from the Dreaming Spires", email.getSubject());
  }

  /**
   * Method under test: {@link MailActivityBehavior#setSubject(Email, String)}
   */
  @Test
  public void testSetSubject2() {
    // Arrange
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.setSubject(Mockito.<String>any())).thenReturn(new HtmlEmail());

    // Act
    mailActivityBehavior.setSubject(email, "Hello from the Dreaming Spires");

    // Assert that nothing has changed
    verify(email).setSubject(eq("Hello from the Dreaming Spires"));
  }

  /**
   * Method under test: {@link MailActivityBehavior#setSubject(Email, String)}
   */
  @Test
  public void testSetSubject3() {
    // Arrange
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.setSubject(Mockito.<String>any())).thenReturn(new HtmlEmail());

    // Act
    mailActivityBehavior.setSubject(email, null);

    // Assert that nothing has changed
    verify(email).setSubject(eq(""));
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#setEmailSession(Email, String)}
   */
  @Test
  public void testSetEmailSession() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> mailActivityBehavior.setEmailSession(new HtmlEmail(), "Mail Session Jndi"));
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#setEmailSession(Email, String)}
   */
  @Test
  public void testSetEmailSession2() throws NamingException {
    // Arrange
    HtmlEmail email = mock(HtmlEmail.class);
    doNothing().when(email).setMailSessionFromJNDI(Mockito.<String>any());

    // Act
    mailActivityBehavior.setEmailSession(email, "Mail Session Jndi");

    // Assert that nothing has changed
    verify(email).setMailSessionFromJNDI(eq("Mail Session Jndi"));
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#setEmailSession(Email, String)}
   */
  @Test
  public void testSetEmailSession3() throws NamingException {
    // Arrange
    HtmlEmail email = mock(HtmlEmail.class);
    doThrow(new NamingException()).when(email).setMailSessionFromJNDI(Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.setEmailSession(email, "Mail Session Jndi"));
    verify(email).setMailSessionFromJNDI(eq("Mail Session Jndi"));
  }

  /**
   * Method under test: {@link MailActivityBehavior#splitAndTrim(String)}
   */
  @Test
  public void testSplitAndTrim() {
    // Arrange, Act and Assert
    assertArrayEquals(new String[]{"Str"}, mailActivityBehavior.splitAndTrim("Str"));
    assertNull(mailActivityBehavior.splitAndTrim(null));
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#getStringFromField(Expression, DelegateExecution)}
   */
  @Test
  public void testGetStringFromField() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    FixedValue expression = new FixedValue(JSONObject.NULL);

    // Act and Assert
    assertEquals("null", mailActivityBehavior.getStringFromField(expression,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#getStringFromField(Expression, DelegateExecution)}
   */
  @Test
  public void testGetStringFromField2() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertNull(
        mailActivityBehavior.getStringFromField(null, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#getStringFromField(Expression, DelegateExecution)}
   */
  @Test
  public void testGetStringFromField3() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    FixedValue expression = new FixedValue(null);

    // Act and Assert
    assertNull(mailActivityBehavior.getStringFromField(expression,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test: {@link MailActivityBehavior#fileExists(File)}
   */
  @Test
  public void testFileExists() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertFalse(mailActivityBehavior.fileExists(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  /**
   * Method under test: {@link MailActivityBehavior#fileExists(File)}
   */
  @Test
  public void testFileExists2() {
    // Arrange, Act and Assert
    assertFalse((new MailActivityBehavior()).fileExists(null));
  }

  /**
   * Method under test: {@link MailActivityBehavior#fileExists(File)}
   */
  @Test
  public void testFileExists3() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertFalse(mailActivityBehavior.fileExists(Paths.get(System.getProperty("java.io.tmpdir"), "").toFile()));
  }

  /**
   * Method under test: {@link MailActivityBehavior#fileExists(File)}
   */
  @Test
  public void testFileExists4() {
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
   * Method under test:
   * {@link MailActivityBehavior#handleException(DelegateExecution, String, Exception, boolean, String)}
   */
  @Test
  public void testHandleException() {
    // Arrange
    DelegateExecution execution = mock(DelegateExecution.class);
    doNothing().when(execution).setVariable(Mockito.<String>any(), Mockito.<Object>any());

    // Act
    mailActivityBehavior.handleException(execution, "Msg", new Exception("foo"), true, "Exception Variable");

    // Assert
    verify(execution).setVariable(eq("Exception Variable"), isA(Object.class));
  }

  /**
   * Method under test:
   * {@link MailActivityBehavior#handleException(DelegateExecution, String, Exception, boolean, String)}
   */
  @Test
  public void testHandleException2() {
    // Arrange
    DelegateExecution execution = mock(DelegateExecution.class);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.handleException(execution, "Msg",
        new Exception("foo"), false, "Exception Variable"));
  }

  /**
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
