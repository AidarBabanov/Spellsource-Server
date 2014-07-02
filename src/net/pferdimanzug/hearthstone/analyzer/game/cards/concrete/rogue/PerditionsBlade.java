package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ComboSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class PerditionsBlade extends WeaponCard {

	public PerditionsBlade() {
		super("Perdition's Blade", Rarity.RARE, HeroClass.ROGUE, 3);
		setDescription("Battlecry: Deal 1 damage. Combo: Deal 2 instead.");
	}

	@Override
	public Weapon getWeapon() {
		Weapon perditionsBlade = createWeapon(2, 2);
		Spell noCombo = new DamageSpell(1);
		Spell combo = new DamageSpell(2);
		Battlecry battlecry = Battlecry.createBattlecry(new ComboSpell(noCombo, combo), TargetSelection.ANY);
		perditionsBlade.setBattlecry(battlecry);
		return perditionsBlade;
	}
}
