/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.util;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import org.springframework.context.ApplicationContext;

public class RuleTask {
    private String agendaGroup;
    private Constituent constituent;
    private Gift gift;
    private ApplicationContext context;


    public RuleTask(ApplicationContext c, String string, Constituent constituent2, Gift gift2) {
        setContext(c);
        setAgendaGroup(string);
        setConstituent(constituent2);
        setGift(gift2);
    }

    public String getAgendaGroup() {
        return agendaGroup;
    }

    void setAgendaGroup(String agendaGroup) {
        this.agendaGroup = agendaGroup;
    }

    public Constituent getConstituent() {
        return constituent;
    }

    void setConstituent(Constituent constituent) {
        this.constituent = constituent;
    }

    public Gift getGift() {
        return gift;
    }

    void setGift(Gift gift) {
        this.gift = gift;
    }

    public ApplicationContext getContext() {
        return context;
    }

    void setContext(ApplicationContext context) {
        this.context = context;
    }

    public boolean equals(RuleTask task) {
        return this.agendaGroup.equals(task.agendaGroup)
                && this.constituent.equals(task.constituent)
                && this.gift.equals(task.gift)
                && this.context.equals(task.context);
    }
}
