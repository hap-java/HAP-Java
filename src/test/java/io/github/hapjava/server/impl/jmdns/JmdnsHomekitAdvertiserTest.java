package io.github.hapjava.server.impl.jmdns;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.net.UnknownHostException;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class JmdnsHomekitAdvertiserTest {

  JmdnsHomekitAdvertiser subject;
  JmDNS jmdns;

  @Before
  public void setup() throws UnknownHostException, IOException {
    jmdns = mock(JmDNS.class);
    subject = new JmdnsHomekitAdvertiser(jmdns);
  }

  @Test
  public void testAdvertiseTwiceFails() throws Exception {
    advertise();
    assertThatThrownBy(() -> advertise()).isNotNull();
  }

  /*
   * Verify that the unregister call is for the initial registered service
   * when changing discoverability causes advertising to be toggled.
   */
  @Test
  public void testSetDiscoverableAfterAdvertise() throws Exception {
    subject.setDiscoverable(false);
    advertise();
    subject.setDiscoverable(true);
    assertThat(getArgumentFromUnregister().getPropertyString("sf")).isEqualTo("0");
  }

  /*
   * Verify that the unregister call is for the initial registered service
   * when changing the config index causes advertising to be toggled.
   */
  @Test
  public void testSetConfigurationIndex() throws Exception {
    subject.setConfigurationIndex(1);
    advertise();
    subject.setConfigurationIndex(2);
    assertThat(getArgumentFromUnregister().getPropertyString("c#")).isEqualTo("1");
  }

  private ServiceInfo getArgumentFromUnregister() {
    ArgumentCaptor<ServiceInfo> serviceInfoCaptor = ArgumentCaptor.forClass(ServiceInfo.class);
    verify(jmdns).unregisterService(serviceInfoCaptor.capture());
    return serviceInfoCaptor.getValue();
  }

  private void advertise() throws Exception {
    subject.advertise("test", "00:00:00:00:00:00", 1234, 1, "1");
  }
}
