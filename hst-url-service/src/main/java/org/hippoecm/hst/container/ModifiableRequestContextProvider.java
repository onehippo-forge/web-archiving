/*
 * Copyright 2018 BloomReach Inc. (http://www.bloomreach.com)
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

package org.hippoecm.hst.container;

import org.hippoecm.hst.core.request.HstRequestContext;

public final class ModifiableRequestContextProvider {
    private ModifiableRequestContextProvider() {
    }

    public static HstRequestContext get() {
        return RequestContextProvider.get();
    }

    public static void set(HstRequestContext requestContext) {
        new RequestContextProvider.ModifiableRequestContextProvider().set(requestContext);
    }

    public static void clear() {
        new RequestContextProvider.ModifiableRequestContextProvider().clear();
    }
}