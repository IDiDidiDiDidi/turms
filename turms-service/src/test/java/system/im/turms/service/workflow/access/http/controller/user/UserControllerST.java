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

package system.im.turms.service.workflow.access.http.controller.user;

import helper.SpringAwareIntegrationTest;
import im.turms.server.common.access.http.dto.response.ResponseDTO;
import im.turms.server.common.dao.domain.User;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerST extends SpringAwareIntegrationTest {

    @Test
    void queryUsers_shouldReturnNotEmptyData() {
        ResponseDTO<Collection<User>> response = getResponse(webClient.get()
                .uri(builder -> builder.path("/users")
                        .queryParam("registrationDateStart", Instant.EPOCH)
                        .build()));
        assertThat(response.getData()).isNotEmpty();
    }

}
