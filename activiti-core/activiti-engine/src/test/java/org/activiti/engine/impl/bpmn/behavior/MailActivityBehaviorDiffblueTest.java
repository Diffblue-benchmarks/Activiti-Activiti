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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.activation.DataSource;
import javax.mail.internet.InternetAddress;
import javax.mail.util.ByteArrayDataSource;
import javax.naming.NamingException;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class MailActivityBehaviorDiffblueTest {
  /**
   * Test {@link MailActivityBehavior#execute(DelegateExecution)}.
   * <p>
   * Method under test: {@link MailActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.execute(DelegateExecution)"})
  public void testExecute() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> mailActivityBehavior.execute(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MailActivityBehavior#createEmail(String, String, boolean)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Email MailActivityBehavior.createEmail(String, String, boolean)"})
  public void testCreateEmail_whenEmptyString_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new MailActivityBehavior()).createEmail("", null, true));
  }

  /**
   * Test {@link MailActivityBehavior#createEmail(String, String, boolean)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Email MailActivityBehavior.createEmail(String, String, boolean)"})
  public void testCreateEmail_whenEmptyString_thenThrowActivitiException2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new MailActivityBehavior()).createEmail("", null, false));
  }

  /**
   * Test {@link MailActivityBehavior#createEmail(String, String, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Email MailActivityBehavior.createEmail(String, String, boolean)"})
  public void testCreateEmail_whenFalse_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new MailActivityBehavior()).createEmail(null, null, false));
  }

  /**
   * Test {@link MailActivityBehavior#createEmail(String, String, boolean)}.
   * <ul>
   *   <li>When {@code Html}.</li>
   *   <li>Then return {@link HtmlEmail}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Email MailActivityBehavior.createEmail(String, String, boolean)"})
  public void testCreateEmail_whenHtml_thenReturnHtmlEmail() {
    // Arrange and Act
    Email actualCreateEmailResult = (new MailActivityBehavior()).createEmail("Text", "Html", true);

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
   * Test {@link MailActivityBehavior#createEmail(String, String, boolean)}.
   * <ul>
   *   <li>When {@code Html}.</li>
   *   <li>Then return {@link HtmlEmail}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Email MailActivityBehavior.createEmail(String, String, boolean)"})
  public void testCreateEmail_whenHtml_thenReturnHtmlEmail2() {
    // Arrange and Act
    Email actualCreateEmailResult = (new MailActivityBehavior()).createEmail(null, "Html", false);

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
   * Test {@link MailActivityBehavior#createEmail(String, String, boolean)}.
   * <ul>
   *   <li>When {@code Html}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Email MailActivityBehavior.createEmail(String, String, boolean)"})
  public void testCreateEmail_whenHtml_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new MailActivityBehavior()).createEmail("", "Html", true));
  }

  /**
   * Test {@link MailActivityBehavior#createEmail(String, String, boolean)}.
   * <ul>
   *   <li>When {@code Text}.</li>
   *   <li>Then return {@link MultiPartEmail}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Email MailActivityBehavior.createEmail(String, String, boolean)"})
  public void testCreateEmail_whenText_thenReturnMultiPartEmail() {
    // Arrange and Act
    Email actualCreateEmailResult = (new MailActivityBehavior()).createEmail("Text", null, true);

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
   * Test {@link MailActivityBehavior#createEmail(String, String, boolean)}.
   * <ul>
   *   <li>When {@code Text}.</li>
   *   <li>Then return {@link SimpleEmail}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Email MailActivityBehavior.createEmail(String, String, boolean)"})
  public void testCreateEmail_whenText_thenReturnSimpleEmail() {
    // Arrange and Act
    Email actualCreateEmailResult = (new MailActivityBehavior()).createEmail("Text", null, false);

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
   * Test {@link MailActivityBehavior#createEmail(String, String, boolean)}.
   * <ul>
   *   <li>When {@code Text}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Email MailActivityBehavior.createEmail(String, String, boolean)"})
  public void testCreateEmail_whenText_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new MailActivityBehavior()).createEmail("Text", "", true));
  }

  /**
   * Test {@link MailActivityBehavior#createHtmlEmail(String, String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#createHtmlEmail(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HtmlEmail MailActivityBehavior.createHtmlEmail(String, String)"})
  public void testCreateHtmlEmail_whenEmptyString_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new MailActivityBehavior()).createHtmlEmail("", "Html"));
  }

  /**
   * Test {@link MailActivityBehavior#createHtmlEmail(String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return SmtpPort is {@code 25}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#createHtmlEmail(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HtmlEmail MailActivityBehavior.createHtmlEmail(String, String)"})
  public void testCreateHtmlEmail_whenNull_thenReturnSmtpPortIs25() {
    // Arrange and Act
    HtmlEmail actualCreateHtmlEmailResult = (new MailActivityBehavior()).createHtmlEmail(null, "Html");

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
   * Test {@link MailActivityBehavior#createHtmlEmail(String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#createHtmlEmail(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HtmlEmail MailActivityBehavior.createHtmlEmail(String, String)"})
  public void testCreateHtmlEmail_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new MailActivityBehavior()).createHtmlEmail(null, null));
  }

  /**
   * Test {@link MailActivityBehavior#createHtmlEmail(String, String)}.
   * <ul>
   *   <li>When {@code Text}.</li>
   *   <li>Then return SmtpPort is {@code 25}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#createHtmlEmail(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HtmlEmail MailActivityBehavior.createHtmlEmail(String, String)"})
  public void testCreateHtmlEmail_whenText_thenReturnSmtpPortIs25() {
    // Arrange and Act
    HtmlEmail actualCreateHtmlEmailResult = (new MailActivityBehavior()).createHtmlEmail("Text", "Html");

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
   * Test {@link MailActivityBehavior#createTextOnlyEmail(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#createTextOnlyEmail(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SimpleEmail MailActivityBehavior.createTextOnlyEmail(String)"})
  public void testCreateTextOnlyEmail_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new MailActivityBehavior()).createTextOnlyEmail(null));
  }

  /**
   * Test {@link MailActivityBehavior#createTextOnlyEmail(String)}.
   * <ul>
   *   <li>When {@code Text}.</li>
   *   <li>Then return SmtpPort is {@code 25}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#createTextOnlyEmail(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SimpleEmail MailActivityBehavior.createTextOnlyEmail(String)"})
  public void testCreateTextOnlyEmail_whenText_thenReturnSmtpPortIs25() {
    // Arrange and Act
    SimpleEmail actualCreateTextOnlyEmailResult = (new MailActivityBehavior()).createTextOnlyEmail("Text");

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
   * Test {@link MailActivityBehavior#createMultiPartEmail(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#createMultiPartEmail(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"MultiPartEmail MailActivityBehavior.createMultiPartEmail(String)"})
  public void testCreateMultiPartEmail_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new MailActivityBehavior()).createMultiPartEmail(null));
  }

  /**
   * Test {@link MailActivityBehavior#createMultiPartEmail(String)}.
   * <ul>
   *   <li>When {@code Text}.</li>
   *   <li>Then return SmtpPort is {@code 25}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#createMultiPartEmail(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"MultiPartEmail MailActivityBehavior.createMultiPartEmail(String)"})
  public void testCreateMultiPartEmail_whenText_thenReturnSmtpPortIs25() {
    // Arrange and Act
    MultiPartEmail actualCreateMultiPartEmailResult = (new MailActivityBehavior()).createMultiPartEmail("Text");

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
   * Test {@link MailActivityBehavior#addTo(Email, String)}.
   * <ul>
   *   <li>Given {@link EmailException#EmailException(String)} with msg is {@code ,}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#addTo(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.addTo(Email, String)"})
  public void testAddTo_givenEmailExceptionWithMsgIsComma() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.addTo(Mockito.<String>any())).thenThrow(new EmailException(","));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.addTo(email, "alice.liddell@example.org"));
    verify(email).addTo(eq("alice.liddell@example.org"));
  }

  /**
   * Test {@link MailActivityBehavior#addTo(Email, String)}.
   * <ul>
   *   <li>Given {@link HtmlEmail} (default constructor).</li>
   *   <li>When {@link HtmlEmail} {@link Email#addTo(String)} return {@link HtmlEmail} (default constructor).</li>
   *   <li>Then calls {@link Email#addTo(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#addTo(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.addTo(Email, String)"})
  public void testAddTo_givenHtmlEmail_whenHtmlEmailAddToReturnHtmlEmail_thenCallsAddTo() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.addTo(Mockito.<String>any())).thenReturn(new HtmlEmail());

    // Act
    mailActivityBehavior.addTo(email, "alice.liddell@example.org");

    // Assert
    verify(email).addTo(eq("alice.liddell@example.org"));
  }

  /**
   * Test {@link MailActivityBehavior#addTo(Email, String)}.
   * <ul>
   *   <li>When {@link HtmlEmail} (default constructor).</li>
   *   <li>Then {@link HtmlEmail} (default constructor) ToAddresses size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#addTo(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.addTo(Email, String)"})
  public void testAddTo_whenHtmlEmail_thenHtmlEmailToAddressesSizeIsOne() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
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
   * Test {@link MailActivityBehavior#addTo(Email, String)}.
   * <ul>
   *   <li>When {@code No recipient could be found for sending email}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#addTo(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.addTo(Email, String)"})
  public void testAddTo_whenNoRecipientCouldBeFoundForSendingEmail_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> mailActivityBehavior.addTo(new HtmlEmail(), "No recipient could be found for sending email"));
  }

  /**
   * Test {@link MailActivityBehavior#addTo(Email, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#addTo(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.addTo(Email, String)"})
  public void testAddTo_whenNull_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.addTo(new HtmlEmail(), null));
  }

  /**
   * Test {@link MailActivityBehavior#addTo(Email, String)}.
   * <ul>
   *   <li>When {@code To}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#addTo(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.addTo(Email, String)"})
  public void testAddTo_whenTo_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.addTo(new HtmlEmail(), "To"));
  }

  /**
   * Test {@link MailActivityBehavior#setFrom(Email, String, String)}.
   * <ul>
   *   <li>Given {@link EmailException#EmailException(String)} with {@code Msg}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#setFrom(Email, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.setFrom(Email, String, String)"})
  public void testSetFrom_givenEmailExceptionWithMsg() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.setFrom(Mockito.<String>any())).thenThrow(new EmailException("Msg"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.setFrom(email, "jane.doe@example.org", "42"));
    verify(email).setFrom(eq("jane.doe@example.org"));
  }

  /**
   * Test {@link MailActivityBehavior#setFrom(Email, String, String)}.
   * <ul>
   *   <li>Given {@link HtmlEmail} (default constructor).</li>
   *   <li>When {@link HtmlEmail} {@link Email#setFrom(String)} return {@link HtmlEmail} (default constructor).</li>
   *   <li>Then calls {@link Email#setFrom(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#setFrom(Email, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.setFrom(Email, String, String)"})
  public void testSetFrom_givenHtmlEmail_whenHtmlEmailSetFromReturnHtmlEmail_thenCallsSetFrom() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.setFrom(Mockito.<String>any())).thenReturn(new HtmlEmail());

    // Act
    mailActivityBehavior.setFrom(email, "jane.doe@example.org", "42");

    // Assert
    verify(email).setFrom(eq("jane.doe@example.org"));
  }

  /**
   * Test {@link MailActivityBehavior#setFrom(Email, String, String)}.
   * <ul>
   *   <li>Given {@code UTF-8}.</li>
   *   <li>When empty string.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#setFrom(Email, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.setFrom(Email, String, String)"})
  public void testSetFrom_givenUtf8_whenEmptyString_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    HtmlEmail email = new HtmlEmail();
    email.setCharset("UTF-8");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.setFrom(email, "", null));
  }

  /**
   * Test {@link MailActivityBehavior#setFrom(Email, String, String)}.
   * <ul>
   *   <li>Given {@code UTF-8}.</li>
   *   <li>When {@code From}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#setFrom(Email, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.setFrom(Email, String, String)"})
  public void testSetFrom_givenUtf8_whenFrom_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    HtmlEmail email = new HtmlEmail();
    email.setCharset("UTF-8");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.setFrom(email, "From", null));
  }

  /**
   * Test {@link MailActivityBehavior#setFrom(Email, String, String)}.
   * <ul>
   *   <li>When {@link HtmlEmail} (default constructor).</li>
   *   <li>Then {@link HtmlEmail} (default constructor) FromAddress Address is {@code jane.doe@example.org}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#setFrom(Email, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.setFrom(Email, String, String)"})
  public void testSetFrom_whenHtmlEmail_thenHtmlEmailFromAddressAddressIsJaneDoeExampleOrg() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
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
   * Test {@link MailActivityBehavior#addCc(Email, String)}.
   * <ul>
   *   <li>Given {@link EmailException#EmailException(String)} with msg is {@code ,}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#addCc(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.addCc(Email, String)"})
  public void testAddCc_givenEmailExceptionWithMsgIsComma() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.addCc(Mockito.<String>any())).thenThrow(new EmailException(","));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.addCc(email, "ada.lovelace@example.org"));
    verify(email).addCc(eq("ada.lovelace@example.org"));
  }

  /**
   * Test {@link MailActivityBehavior#addCc(Email, String)}.
   * <ul>
   *   <li>Given {@link HtmlEmail} (default constructor).</li>
   *   <li>When {@link HtmlEmail} {@link Email#addCc(String)} return {@link HtmlEmail} (default constructor).</li>
   *   <li>Then calls {@link Email#addCc(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#addCc(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.addCc(Email, String)"})
  public void testAddCc_givenHtmlEmail_whenHtmlEmailAddCcReturnHtmlEmail_thenCallsAddCc() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.addCc(Mockito.<String>any())).thenReturn(new HtmlEmail());

    // Act
    mailActivityBehavior.addCc(email, "ada.lovelace@example.org");

    // Assert
    verify(email).addCc(eq("ada.lovelace@example.org"));
  }

  /**
   * Test {@link MailActivityBehavior#addCc(Email, String)}.
   * <ul>
   *   <li>When {@code Cc}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#addCc(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.addCc(Email, String)"})
  public void testAddCc_whenCc_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.addCc(new HtmlEmail(), "Cc"));
  }

  /**
   * Test {@link MailActivityBehavior#addCc(Email, String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#addCc(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.addCc(Email, String)"})
  public void testAddCc_whenEmptyString_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.addCc(new HtmlEmail(), ""));
  }

  /**
   * Test {@link MailActivityBehavior#addCc(Email, String)}.
   * <ul>
   *   <li>When {@link HtmlEmail} (default constructor).</li>
   *   <li>Then {@link HtmlEmail} (default constructor) CcAddresses size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#addCc(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.addCc(Email, String)"})
  public void testAddCc_whenHtmlEmail_thenHtmlEmailCcAddressesSizeIsOne() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
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
   * Test {@link MailActivityBehavior#addCc(Email, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link HtmlEmail} (default constructor) CcAddresses Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#addCc(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.addCc(Email, String)"})
  public void testAddCc_whenNull_thenHtmlEmailCcAddressesEmpty() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = new HtmlEmail();

    // Act
    mailActivityBehavior.addCc(email, null);

    // Assert that nothing has changed
    assertTrue(email.getCcAddresses().isEmpty());
  }

  /**
   * Test {@link MailActivityBehavior#addBcc(Email, String)}.
   * <ul>
   *   <li>Given {@link EmailException#EmailException(String)} with msg is {@code ,}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#addBcc(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.addBcc(Email, String)"})
  public void testAddBcc_givenEmailExceptionWithMsgIsComma() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.addBcc(Mockito.<String>any())).thenThrow(new EmailException(","));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.addBcc(email, "ada.lovelace@example.org"));
    verify(email).addBcc(eq("ada.lovelace@example.org"));
  }

  /**
   * Test {@link MailActivityBehavior#addBcc(Email, String)}.
   * <ul>
   *   <li>Given {@link HtmlEmail} (default constructor).</li>
   *   <li>When {@link HtmlEmail} {@link Email#addBcc(String)} return {@link HtmlEmail} (default constructor).</li>
   *   <li>Then calls {@link Email#addBcc(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#addBcc(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.addBcc(Email, String)"})
  public void testAddBcc_givenHtmlEmail_whenHtmlEmailAddBccReturnHtmlEmail_thenCallsAddBcc() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.addBcc(Mockito.<String>any())).thenReturn(new HtmlEmail());

    // Act
    mailActivityBehavior.addBcc(email, "ada.lovelace@example.org");

    // Assert
    verify(email).addBcc(eq("ada.lovelace@example.org"));
  }

  /**
   * Test {@link MailActivityBehavior#addBcc(Email, String)}.
   * <ul>
   *   <li>When {@code Bcc}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#addBcc(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.addBcc(Email, String)"})
  public void testAddBcc_whenBcc_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.addBcc(new HtmlEmail(), "Bcc"));
  }

  /**
   * Test {@link MailActivityBehavior#addBcc(Email, String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#addBcc(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.addBcc(Email, String)"})
  public void testAddBcc_whenEmptyString_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.addBcc(new HtmlEmail(), ""));
  }

  /**
   * Test {@link MailActivityBehavior#addBcc(Email, String)}.
   * <ul>
   *   <li>When {@link HtmlEmail} (default constructor).</li>
   *   <li>Then {@link HtmlEmail} (default constructor) BccAddresses size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#addBcc(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.addBcc(Email, String)"})
  public void testAddBcc_whenHtmlEmail_thenHtmlEmailBccAddressesSizeIsOne() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
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
  }

  /**
   * Test {@link MailActivityBehavior#addBcc(Email, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link HtmlEmail} (default constructor) BccAddresses Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#addBcc(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.addBcc(Email, String)"})
  public void testAddBcc_whenNull_thenHtmlEmailBccAddressesEmpty() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = new HtmlEmail();

    // Act
    mailActivityBehavior.addBcc(email, null);

    // Assert that nothing has changed
    assertTrue(email.getBccAddresses().isEmpty());
  }

  /**
   * Test {@link MailActivityBehavior#attach(Email, List, List)}.
   * <ul>
   *   <li>Given {@link ByteArrayDataSource#ByteArrayDataSource(String, String)} with {@code Data} and {@code Type} Name is {@code Data Sources}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#attach(Email, List, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.attach(Email, List, List)"})
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
   *   <li>Then not {@link MultiPartEmail} (default constructor) BoolHasAttachments.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#attach(Email, List, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.attach(Email, List, List)"})
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
   *   <li>Then {@link MultiPartEmail} (default constructor) BoolHasAttachments.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#attach(Email, List, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.attach(Email, List, List)"})
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.attach(Email, List, List)"})
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
   *   <li>Then not {@link MultiPartEmail} (default constructor) BoolHasAttachments.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#attach(Email, List, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.attach(Email, List, List)"})
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
   * Test {@link MailActivityBehavior#setSubject(Email, String)}.
   * <ul>
   *   <li>Given {@link HtmlEmail} (default constructor).</li>
   *   <li>Then calls {@link Email#setSubject(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#setSubject(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.setSubject(Email, String)"})
  public void testSetSubject_givenHtmlEmail_thenCallsSetSubject() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = mock(HtmlEmail.class);
    when(email.setSubject(Mockito.<String>any())).thenReturn(new HtmlEmail());

    // Act
    mailActivityBehavior.setSubject(email, "Hello from the Dreaming Spires");

    // Assert
    verify(email).setSubject(eq("Hello from the Dreaming Spires"));
  }

  /**
   * Test {@link MailActivityBehavior#setSubject(Email, String)}.
   * <ul>
   *   <li>When {@link HtmlEmail} (default constructor).</li>
   *   <li>Then {@link HtmlEmail} (default constructor) Subject is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#setSubject(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.setSubject(Email, String)"})
  public void testSetSubject_whenHtmlEmail_thenHtmlEmailSubjectIsEmptyString() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = new HtmlEmail();

    // Act
    mailActivityBehavior.setSubject(email, null);

    // Assert
    assertEquals("", email.getSubject());
  }

  /**
   * Test {@link MailActivityBehavior#setSubject(Email, String)}.
   * <ul>
   *   <li>When {@link HtmlEmail} (default constructor).</li>
   *   <li>Then {@link HtmlEmail} (default constructor) Subject is {@code Hello from the Dreaming Spires}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#setSubject(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.setSubject(Email, String)"})
  public void testSetSubject_whenHtmlEmail_thenHtmlEmailSubjectIsHelloFromTheDreamingSpires() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = new HtmlEmail();

    // Act
    mailActivityBehavior.setSubject(email, "Hello from the Dreaming Spires");

    // Assert
    assertEquals("Hello from the Dreaming Spires", email.getSubject());
  }

  /**
   * Test {@link MailActivityBehavior#setEmailSession(Email, String)}.
   * <ul>
   *   <li>Given {@link NamingException#NamingException()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#setEmailSession(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.setEmailSession(Email, String)"})
  public void testSetEmailSession_givenNamingException() throws NamingException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = mock(HtmlEmail.class);
    doThrow(new NamingException()).when(email).setMailSessionFromJNDI(Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.setEmailSession(email, "Mail Session Jndi"));
    verify(email).setMailSessionFromJNDI(eq("Mail Session Jndi"));
  }

  /**
   * Test {@link MailActivityBehavior#setEmailSession(Email, String)}.
   * <ul>
   *   <li>When {@link HtmlEmail} {@link Email#setMailSessionFromJNDI(String)} does nothing.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#setEmailSession(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.setEmailSession(Email, String)"})
  public void testSetEmailSession_whenHtmlEmailSetMailSessionFromJNDIDoesNothing() throws NamingException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = mock(HtmlEmail.class);
    doNothing().when(email).setMailSessionFromJNDI(Mockito.<String>any());

    // Act
    mailActivityBehavior.setEmailSession(email, "Mail Session Jndi");

    // Assert
    verify(email).setMailSessionFromJNDI(eq("Mail Session Jndi"));
  }

  /**
   * Test {@link MailActivityBehavior#setEmailSession(Email, String)}.
   * <ul>
   *   <li>When {@link HtmlEmail} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#setEmailSession(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.setEmailSession(Email, String)"})
  public void testSetEmailSession_whenHtmlEmail_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> mailActivityBehavior.setEmailSession(new HtmlEmail(), "Mail Session Jndi"));
  }

  /**
   * Test {@link MailActivityBehavior#setEmailSession(Email, String)}.
   * <ul>
   *   <li>When {@code java:}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#setEmailSession(Email, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.setEmailSession(Email, String)"})
  public void testSetEmailSession_whenJava_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.setEmailSession(new HtmlEmail(), "java:"));
  }

  /**
   * Test {@link MailActivityBehavior#splitAndTrim(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#splitAndTrim(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String[] MailActivityBehavior.splitAndTrim(String)"})
  public void testSplitAndTrim_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new MailActivityBehavior()).splitAndTrim(null));
  }

  /**
   * Test {@link MailActivityBehavior#splitAndTrim(String)}.
   * <ul>
   *   <li>When {@code Str}.</li>
   *   <li>Then return array of {@link String} with {@code Str}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#splitAndTrim(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String[] MailActivityBehavior.splitAndTrim(String)"})
  public void testSplitAndTrim_whenStr_thenReturnArrayOfStringWithStr() {
    // Arrange, Act and Assert
    assertArrayEquals(new String[]{"Str"}, (new MailActivityBehavior()).splitAndTrim("Str"));
  }

  /**
   * Test {@link MailActivityBehavior#getStringFromField(Expression, DelegateExecution)}.
   * <ul>
   *   <li>When {@link FixedValue#FixedValue(Object)} with value is {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#getStringFromField(Expression, DelegateExecution)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String MailActivityBehavior.getStringFromField(Expression, DelegateExecution)"})
  public void testGetStringFromField_whenFixedValueWithValueIsNull_thenReturnNull() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    FixedValue expression = new FixedValue(JSONObject.NULL);

    // Act and Assert
    assertEquals("null", mailActivityBehavior.getStringFromField(expression,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MailActivityBehavior#getStringFromField(Expression, DelegateExecution)}.
   * <ul>
   *   <li>When {@link FixedValue#FixedValue(Object)} with value is {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#getStringFromField(Expression, DelegateExecution)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String MailActivityBehavior.getStringFromField(Expression, DelegateExecution)"})
  public void testGetStringFromField_whenFixedValueWithValueIsNull_thenReturnNull2() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    FixedValue expression = new FixedValue(null);

    // Act and Assert
    assertNull(mailActivityBehavior.getStringFromField(expression,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MailActivityBehavior#getStringFromField(Expression, DelegateExecution)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#getStringFromField(Expression, DelegateExecution)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String MailActivityBehavior.getStringFromField(Expression, DelegateExecution)"})
  public void testGetStringFromField_whenNull_thenReturnNull() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertNull(
        mailActivityBehavior.getStringFromField(null, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MailActivityBehavior#fileExists(File)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#fileExists(File)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean MailActivityBehavior.fileExists(File)"})
  public void testFileExists_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean MailActivityBehavior.fileExists(File)"})
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean MailActivityBehavior.fileExists(File)"})
  public void testFileExists_whenPropertyIsJavaIoTmpdirIsTestTxtToFile() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertFalse(mailActivityBehavior.fileExists(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  /**
   * Test {@link MailActivityBehavior#handleException(DelegateExecution, String, Exception, boolean, String)}.
   * <ul>
   *   <li>Then calls {@link VariableScope#setVariable(String, Object)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#handleException(DelegateExecution, String, Exception, boolean, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void MailActivityBehavior.handleException(DelegateExecution, String, Exception, boolean, String)"})
  public void testHandleException_thenCallsSetVariable() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    DelegateExecution execution = mock(DelegateExecution.class);
    doNothing().when(execution).setVariable(Mockito.<String>any(), Mockito.<Object>any());

    // Act
    mailActivityBehavior.handleException(execution, "Msg", new Exception("foo"), true, "Exception Variable");

    // Assert
    verify(execution).setVariable(eq("Exception Variable"), isA(Object.class));
  }

  /**
   * Test {@link MailActivityBehavior#handleException(DelegateExecution, String, Exception, boolean, String)}.
   * <ul>
   *   <li>When {@link DelegateExecution}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MailActivityBehavior#handleException(DelegateExecution, String, Exception, boolean, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void MailActivityBehavior.handleException(DelegateExecution, String, Exception, boolean, String)"})
  public void testHandleException_whenDelegateExecution_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    DelegateExecution execution = mock(DelegateExecution.class);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.handleException(execution, "Msg",
        new Exception("foo"), false, "Exception Variable"));
  }

  /**
   * Test new {@link MailActivityBehavior} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link MailActivityBehavior}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MailActivityBehavior.<init>()"})
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
