/*
 * Copyright (C) 2019 The Turms Project
 * https://github.com/turms-im/turms
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package unit.im.turms.server.common.rpc.codec.request;

import im.turms.common.constant.DeviceType;
import im.turms.common.model.dto.request.TurmsRequest;
import im.turms.server.common.dto.ServiceRequest;
import im.turms.server.common.rpc.codec.request.HandleServiceRequestCodec;
import im.turms.server.common.rpc.request.HandleServiceRequest;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;
import unit.im.turms.server.common.rpc.codec.BaseCodecTest;

import static org.assertj.core.api.Assertions.assertThat;

class HandleServiceRequestCodecTests extends BaseCodecTest {

    @Test
    void shouldGetTheSameRequest_afterWriteAndRead_forLegalRequest() {
        byte[] expectedTurmsRequestBytes = {1, 2, 3, 4};
        ServiceRequest expectedRequest = new ServiceRequest(
                new byte[] {11, 22, 33, 44},
                1L,
                DeviceType.ANDROID,
                1L,
                TurmsRequest.KindCase.CREATE_MESSAGE_REQUEST,
                Unpooled.wrappedBuffer(expectedTurmsRequestBytes));
        HandleServiceRequest actualRequest = writeRequestAndReadBuffer(new HandleServiceRequestCodec(),
                new HandleServiceRequest(expectedRequest));
        ServiceRequest request = actualRequest.getServiceRequest();

        assertThat(request.getIp()).isEqualTo(expectedRequest.getIp());
        assertThat(request.getUserId()).isEqualTo(expectedRequest.getUserId());
        assertThat(request.getDeviceType()).isEqualTo(expectedRequest.getDeviceType());
        assertThat(request.getType()).isNull();
        assertThat(ByteBufUtil.getBytes(request.getTurmsRequestBuffer())).isEqualTo(expectedTurmsRequestBytes);
    }

}
