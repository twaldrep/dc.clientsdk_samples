/*
 * Copyright 2019 Actian Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.actian.dc.clientsdk.samples;

/**
 * Base interface implemented by all execution samples
 */
public interface ConnectionUser
{    
    /**
     * Does this sample support local execution?
     * @return true if the sample supports local execution, false otherwise
     */
    boolean supportsLocal();
    
    /**
     * Called to create, and track completion of a job using the provided ConnectionBuilder
     * instance
     * @param cxnBuilder instance of ConnectionBuilder
     * @return true if the job completed successfully, false otherwise
     */
    boolean useConnection(ConnectionBuilder cxnBuilder);
}
