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

package org.onehippo.forge.webarchiving.common.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.onehippo.cms7.services.SingletonService;
import org.onehippo.forge.webarchiving.common.error.WebArchiveUpdateException;
import org.onehippo.forge.webarchiving.common.model.WebArchiveUpdateJob;
import org.onehippo.forge.webarchiving.common.model.WebArchiveUpdateJobStatus;

import static java.util.Collections.singletonList;

/**
 * Web archive update manager, responsible for storing/reading web archive update jobs.
 */
@SingletonService
public interface WebArchiveUpdateJobsManager extends Serializable {

    /**
     * Create and save a web archive update job and return the created job identifier.
     *
     * @param webArchiveUpdateJob web archive update job
     * @return created web archive update job identifier
     * @throws WebArchiveUpdateException if a web archive update exception occurs
     */
    String storeWebArchiveUpdateJob(WebArchiveUpdateJob webArchiveUpdateJob) throws WebArchiveUpdateException;

    /**
     * Get the web archive update job by the given job identifier ({@code webArchiveUpdateJobId}).
     *
     * @param webArchiveUpdateJobId identifier
     * @return web archive update
     * @throws WebArchiveUpdateException if a web archive update exception occurs
     */
    WebArchiveUpdateJob getWebArchiveUpdateJobById(String webArchiveUpdateJobId) throws WebArchiveUpdateException;

    /**
     * Update a web archive update job.
     *
     * @param webArchiveUpdateJob a web archive update job
     * @throws WebArchiveUpdateException if a web archive update exception occurs
     */
    void updateWebArchiveUpdateJob(WebArchiveUpdateJob webArchiveUpdateJob) throws WebArchiveUpdateException;

    /**
     * Delete a web archive update job by {@code webArchiveUpdateJobId}.
     *
     * @param webArchiveUpdateJobId web archive update job identifier
     * @throws WebArchiveUpdateException if a web archive update exception occurs
     */
    void deleteWebArchiveUpdateJob(String webArchiveUpdateJobId) throws WebArchiveUpdateException;


    /**
     * Return the total size of web archive update jobs in the queue by {@code statuses}.
     *
     * @param statuses OR-ed statuses to be filtered on, a null value means no filtering
     * @return the total size of web archive update jobs by {@code statuses}
     * @throws WebArchiveUpdateException if a web archive update exception occurs
     */
    long getTotalSizeForWebArchiveUpdateJobs(List<WebArchiveUpdateJobStatus> statuses) throws WebArchiveUpdateException;


    /**
     * Search and return web archive update jobs by the given inputs.
     * <p>
     * {@code searchFilters} can normally contain <code>fieldName</code> - <code>fieldValue</code> pairs. An
     * implementations can use the <code>fieldValue</code> to compare the field with <code>Equals (e.g, '='
     * operator)</code> or <code>Contains (e.g, jcr:contains)</code> operator.
     *
     * @param statuses            OR-ed statuses to be filtered on, a null value means no filtering
     * @param searchFilters       AND-ed search filters having <code>fieldName</code> - <code>fieldValue</code> pairs
     * @param offset              offset of the result set
     * @param limit               size of the page of the result set
     * @param orderByPropertyHint order by property name. e.g., 'created', 'lastmodfied', 'creator', etc.
     * @param ascending           whether or not the sorting is based on ascending
     * @return search result of web archive update jobs
     * @throws WebArchiveUpdateException if a web archive update exception occurs
     */
    List<WebArchiveUpdateJob> searchForWebArchiveUpdateJobs(List<WebArchiveUpdateJobStatus> statuses, Map<String, String> searchFilters,
                                                            int offset, int limit, String orderByPropertyHint, boolean ascending) throws WebArchiveUpdateException;


    /**
     * Convenience method to get all web archive update jobs.
     *
     * @param searchLimit size of the page of the result set
     * @return up to {@code searchLimit} web archive update jobs
     * @throws WebArchiveUpdateException if a web archive update exception occurs
     */
    default List<WebArchiveUpdateJob> getAllWebArchiveUpdateJobs(int searchLimit) throws WebArchiveUpdateException {
        return searchForWebArchiveUpdateJobs(null, null, 0, searchLimit, null, true);
    }


    /**
     * Convenience method to get queued web archive update jobs.
     *
     * @return list of queued web archive update jobs
     * @throws WebArchiveUpdateException if a web archive update exception occurs
     */
    default List<WebArchiveUpdateJob> getQueuedWebArchiveUpdateJobs(int searchLimit) throws WebArchiveUpdateException {
        return searchForWebArchiveUpdateJobs(singletonList(WebArchiveUpdateJobStatus.QUEUED), null, 0, searchLimit, null, true);
    }

    /**
     * Convenience method to get aborted web archive update jobs.
     *
     * @return list of aborted web archive update jobs
     * @throws WebArchiveUpdateException if a web archive update exception occurs
     */
    default List<WebArchiveUpdateJob> getAbortedWebArchiveUpdateJobs(int searchLimit) throws WebArchiveUpdateException {
        return searchForWebArchiveUpdateJobs(singletonList(WebArchiveUpdateJobStatus.ABORTED), null, 0, searchLimit, null, true);
    }


}
