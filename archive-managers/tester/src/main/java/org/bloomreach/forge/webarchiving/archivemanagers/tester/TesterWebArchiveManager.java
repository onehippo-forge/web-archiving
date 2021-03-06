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

package org.bloomreach.forge.webarchiving.archivemanagers.tester;

import java.util.Map;

import org.bloomreach.forge.webarchiving.cms.util.Discoverable;
import org.bloomreach.forge.webarchiving.cms.util.PlatformManaged;
import org.bloomreach.forge.webarchiving.common.api.WebArchiveManager;
import org.bloomreach.forge.webarchiving.common.error.WebArchiveUpdateException;
import org.bloomreach.forge.webarchiving.common.model.WebArchiveUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TesterWebArchiveManager implements WebArchiveManager, PlatformManaged, Discoverable {
    private static final Logger log = LoggerFactory.getLogger(TesterWebArchiveManager.class);

    private int testerSleepTimeInSeconds = 10;

    @Override
    public synchronized void initialize(final Map<String, String> props) {
        final String sleepSeconds = props.get("tester.sleep.seconds");
        if (sleepSeconds != null) {
            try {
                testerSleepTimeInSeconds = Integer.parseInt(sleepSeconds);
            } catch (NumberFormatException nfe) {
                log.error("Initializing {}: cannot parse property tester.sleep.seconds='{}' into integer", this.getClass().getName(), sleepSeconds);
            }
        }
        log.info("Initialized {}. Simulating a Web Archive service with conf: sleep seconds: {}", this.getClass().getName(), testerSleepTimeInSeconds);
    }

    @Override
    public void destroy() {
        log.debug("Destroying {}", this.getClass().getName());
    }

    @Override
    public synchronized void requestUpdate(final WebArchiveUpdate update) throws WebArchiveUpdateException {
        log.info("\n====================   Received update: ====================\n{}\n\n" +
            "========================================\n\n", update);

        try {
            Thread.sleep(testerSleepTimeInSeconds * 1000);
        } catch (InterruptedException e) {
            log.warn("Can't even sleep for {} seconds", testerSleepTimeInSeconds);
        }

        if (System.currentTimeMillis() % 2 != 0) {
            throw new WebArchiveUpdateException("Web archive is closed now");
        }
    }

    @Override
    public String getArchiveManagerInfo() {
        return "Tester Web Archive Manager [" + this.getClass().getName() + "]";
    }
}
