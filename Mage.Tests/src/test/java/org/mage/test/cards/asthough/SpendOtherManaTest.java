/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.cards.asthough;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SpendOtherManaTest extends CardTestPlayerBase {

    /**
     * Mycosynth Lattice doesn't work for floating mana with activated abillites
     * I was trying to activate Sydri, Galvanic Genius with a floating {C}
     * targeting a mountain when I clicked on the <> icon it wouldn't spend the
     * mana.
     */
    @Test
    public void testColorlessCanBeUsed() {
        // All permanents are artifacts in addition to their other types.
        // All cards that aren't on the battlefield, spells, and permanents are colorless.
        // Players may spend mana as though it were mana of any color.
        addCard(Zone.BATTLEFIELD, playerA, "Mycosynth Lattice");

        // {U}: Target noncreature artifact becomes an artifact creature with power and toughness each equal to its converted mana cost until end of turn.
        // {W}{B}: Target artifact creature gains deathtouch and lifelink until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Sydri, Galvanic Genius");
        //{T}: Add {C} to your mana pool. ( represents colorless mana.)
        // {1}, {T}: Add one mana of any color to your mana pool.
        addCard(Zone.BATTLEFIELD, playerA, "Unknown Shores");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C} to your mana pool");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}: Target noncreature artifact becomes an artifact creature with power and toughness", "Mountain");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped("Unknown Shores", true);

        assertPermanentCount(playerB, "Mountain", 0);
    }

}
