package io.github.hapjava.server.impl.dnssd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyShort;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import java.nio.ByteOrder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.Answer;

public class DnssdHomekitAdvertiserTest {

  private DnsSdLibrary dnsSd;
  private DnssdHomekitAdvertiser subject;
  private Pointer serviceRef;

  private final Answer<Integer> successRegister =
      invocation -> {
        PointerByReference refOut = invocation.getArgument(0);
        refOut.setValue(serviceRef);
        return DnsSdLibrary.kDNSServiceErr_NoError;
      };

  @Before
  public void setup() {
    dnsSd = mock(DnsSdLibrary.class);
    serviceRef = new Pointer(1L);
    subject = new DnssdHomekitAdvertiser(dnsSd);

    when(dnsSd.DNSServiceRegister(
            any(PointerByReference.class),
            anyInt(),
            anyInt(),
            anyString(),
            anyString(),
            anyString(),
            nullable(String.class),
            anyShort(),
            anyShort(),
            any(byte[].class),
            any(),
            any()))
        .thenAnswer(successRegister);

    when(dnsSd.DNSServiceProcessResult(any(Pointer.class)))
        .thenReturn(DnsSdLibrary.kDNSServiceErr_NoError + 1);
  }

  @Test
  public void advertiseRegistersHapService() throws Exception {
    advertise();

    ArgumentCaptor<Integer> ifIndex = ArgumentCaptor.forClass(Integer.class);
    ArgumentCaptor<String> type = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> host = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Short> port = ArgumentCaptor.forClass(Short.class);
    ArgumentCaptor<byte[]> txt = ArgumentCaptor.forClass(byte[].class);

    verify(dnsSd)
        .DNSServiceRegister(
            any(PointerByReference.class),
            anyInt(),
            ifIndex.capture(),
            anyString(),
            type.capture(),
            anyString(),
            host.capture(),
            port.capture(),
            anyShort(),
            txt.capture(),
            any(),
            any());

    assertThat(type.getValue()).isEqualTo("_hap._tcp");
    assertThat(ifIndex.getValue()).isEqualTo(DnssdHomekitAdvertiser.INTERFACE_INDEX_ANY);
    assertThat(host.getValue()).isNull();
    assertThat(port.getValue()).isEqualTo(DnssdHomekitAdvertiser.toNetworkByteOrderPort(9124));
    assertThat(txt.getValue()).isNotEmpty();
  }

  @Test
  public void advertisePassesConfiguredInterfaceIndexAndHost() throws Exception {
    // Advanced ctor may pass a real DNS hostname; raw IPs must not be used as DNS-SD host.
    subject = new DnssdHomekitAdvertiser(dnsSd, 4, "bridge.local");
    when(dnsSd.DNSServiceRegister(
            any(PointerByReference.class),
            anyInt(),
            anyInt(),
            anyString(),
            anyString(),
            anyString(),
            nullable(String.class),
            anyShort(),
            anyShort(),
            any(byte[].class),
            any(),
            any()))
        .thenAnswer(successRegister);

    advertise();

    ArgumentCaptor<Integer> ifIndex = ArgumentCaptor.forClass(Integer.class);
    ArgumentCaptor<String> host = ArgumentCaptor.forClass(String.class);
    verify(dnsSd)
        .DNSServiceRegister(
            any(PointerByReference.class),
            anyInt(),
            ifIndex.capture(),
            anyString(),
            anyString(),
            anyString(),
            host.capture(),
            anyShort(),
            anyShort(),
            any(byte[].class),
            any(),
            any());

    assertThat(ifIndex.getValue()).isEqualTo(4);
    assertThat(host.getValue()).isEqualTo("bridge.local");
  }

  @Test
  public void inetAddressConstructorLeavesHostNull() {
    // Package-visible mock ctor mirrors InetAddress behaviour: interface scoped, host=null.
    subject = new DnssdHomekitAdvertiser(dnsSd, 7, null);
    assertThat(subject.getInterfaceIndex()).isEqualTo(7);
    assertThat(subject.getHost()).isNull();
  }

  @Test
  public void advertiseTwiceFails() throws Exception {
    advertise();
    assertThatThrownBy(this::advertise).isInstanceOf(IllegalStateException.class);
  }

  @Test
  public void setDiscoverableReRegisters() throws Exception {
    advertise();
    subject.setDiscoverable(false);

    verify(dnsSd, atLeastOnce()).DNSServiceRefDeallocate(serviceRef);
    verify(dnsSd, times(2))
        .DNSServiceRegister(
            any(PointerByReference.class),
            anyInt(),
            anyInt(),
            anyString(),
            anyString(),
            anyString(),
            nullable(String.class),
            anyShort(),
            anyShort(),
            any(byte[].class),
            any(),
            any());
  }

  @Test
  public void stopDeallocatesServiceRef() throws Exception {
    advertise();
    subject.stop();
    verify(dnsSd).DNSServiceRefDeallocate(serviceRef);
  }

  @Test
  public void registerFailureSurfacesAsIoException() {
    when(dnsSd.DNSServiceRegister(
            any(PointerByReference.class),
            anyInt(),
            anyInt(),
            anyString(),
            anyString(),
            anyString(),
            nullable(String.class),
            anyShort(),
            anyShort(),
            any(byte[].class),
            any(),
            any()))
        .thenReturn(-65537);

    assertThatThrownBy(this::advertise)
        .isInstanceOf(java.io.IOException.class)
        .hasMessageContaining("DNSServiceRegister failed");
  }

  @Test
  public void toNetworkByteOrderPortMatchesDnsSdExpectation() {
    short net = DnssdHomekitAdvertiser.toNetworkByteOrderPort(9124);
    if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
      assertThat(net).isEqualTo(Short.reverseBytes((short) 9124));
    } else {
      assertThat(net).isEqualTo((short) 9124);
    }
  }

  private void advertise() throws Exception {
    subject.advertise("test", 2, "00:00:00:00:00:00", 9124, 1, "ABCD");
    Thread.sleep(50);
  }
}
