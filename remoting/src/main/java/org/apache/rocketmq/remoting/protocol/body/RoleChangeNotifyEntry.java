/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.rocketmq.remoting.protocol.body;


import org.apache.rocketmq.remoting.protocol.RemotingCommand;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;
import org.apache.rocketmq.remoting.protocol.header.controller.ElectMasterResponseHeader;

public class RoleChangeNotifyEntry {

    private final BrokerMemberGroup brokerMemberGroup;

    private final String masterAddress;

    private final Long masterBrokerId;

    private final int masterEpoch;

    private final int syncStateSetEpoch;

    public RoleChangeNotifyEntry(BrokerMemberGroup brokerMemberGroup, String masterAddress, Long masterBrokerId, int masterEpoch, int syncStateSetEpoch) {
        this.brokerMemberGroup = brokerMemberGroup;
        this.masterAddress = masterAddress;
        this.masterEpoch = masterEpoch;
        this.syncStateSetEpoch = syncStateSetEpoch;
        this.masterBrokerId = masterBrokerId;
    }

    public static RoleChangeNotifyEntry convert(RemotingCommand electMasterResponse) {
        final ElectMasterResponseHeader header = (ElectMasterResponseHeader) electMasterResponse.readCustomHeader();
        BrokerMemberGroup brokerMemberGroup = null;
        if (electMasterResponse.getBody() != null && electMasterResponse.getBody().length > 0) {
            brokerMemberGroup = RemotingSerializable.decode(electMasterResponse.getBody(), BrokerMemberGroup.class);
        }
        return new RoleChangeNotifyEntry(brokerMemberGroup, header.getMasterAddress(), header.getMasterBrokerId(), header.getMasterEpoch(), header.getSyncStateSetEpoch());
    }


    public BrokerMemberGroup getBrokerMemberGroup() {
        return brokerMemberGroup;
    }

    public String getMasterAddress() {
        return masterAddress;
    }

    public int getMasterEpoch() {
        return masterEpoch;
    }

    public int getSyncStateSetEpoch() {
        return syncStateSetEpoch;
    }

    public Long getMasterBrokerId() {
        return masterBrokerId;
    }
}
