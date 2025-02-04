/*
 * Copyright (C) 2019 The Turms Project
 * https://github.com/turms-im/turms
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package unit.im.turms.server.common.cluster.exception;

import im.turms.server.common.cluster.service.rpc.RpcErrorCode;
import im.turms.server.common.cluster.service.rpc.exception.RpcException;
import im.turms.server.common.constant.TurmsStatusCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author James Chen
 */
class RpcExceptionTests {

    @Test
    void get_shouldEqual_ifParamsAreSame() {
        RpcErrorCode errorCode = RpcErrorCode.UNKNOWN_ERROR;
        TurmsStatusCode statusCode = TurmsStatusCode.SERVER_INTERNAL_ERROR;
        String message = "My error message";
        RpcException exception = RpcException.get(errorCode, statusCode, message);

        assertThat(errorCode).isEqualTo(exception.getErrorCode());
        assertThat(statusCode).isEqualTo(exception.getStatusCode());
        assertThat(message).isEqualTo(exception.getDescription());
    }

}
