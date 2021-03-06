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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DefendingPlayerControlsCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterArtifactPermanent;

/**
 *
 * @author ayratn
 */
public class ScrapdiverSerpent extends CardImpl {

    public ScrapdiverSerpent (UUID ownerId) {
        super(ownerId, 41, "Scrapdiver Serpent", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Serpent");

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Scrapdiver Serpent can't be blocked as long as defending player controls an artifact
        Effect effect = new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(),
                new DefendingPlayerControlsCondition(new FilterArtifactPermanent()));
        effect.setText("{this} can't be blocked as long as defending player controls an artifact");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public ScrapdiverSerpent (final ScrapdiverSerpent card) {
        super(card);
    }

    @Override
    public ScrapdiverSerpent copy() {
        return new ScrapdiverSerpent(this);
    }

}
