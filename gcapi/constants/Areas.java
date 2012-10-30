package gcapi.constants;

import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public final class Areas {

	public static final Area DUEL_ARENA = new Area(new Tile[] {
			new Tile(3361, 3265, 0), new Tile(3370, 3271, 0) });

	public static final Area SEERS_BANK = new Area(new Tile[] {
			new Tile(2727, 3489, 0), new Tile(2727, 3486, 0),
			new Tile(2723, 3486, 0), new Tile(2723, 3489, 0),
			new Tile(2720, 3489, 0), new Tile(2720, 3493, 0),
			new Tile(2730, 3493, 0), new Tile(2730, 3489, 0) });

	public static final Area ROCK_CRABS_EAST = new Area(new Tile[] {
			new Tile(2700, 3731, 0), new Tile(2697, 3728, 0),
			new Tile(2691, 3726, 0), new Tile(2691, 3714, 0),
			new Tile(2704, 3711, 0), new Tile(2713, 3711, 0),
			new Tile(2717, 3717, 0), new Tile(2718, 3728, 0),
			new Tile(2718, 3733, 0), new Tile(2713, 3733, 0),
			new Tile(2709, 3729, 0), new Tile(2705, 3727, 0) });
	
	public static final Area WARRIORS_GUILD_BANK_AREA = new Area(new Tile[] {
			new Tile(2843, 3536, 0), new Tile(2844, 3539, 0) });
	
	public static final Area WARRIORS_GUILD_FIRST_FLOOR = new Area(new Tile[] {
			new Tile(2838, 3533, 0), new Tile(2877, 3530, 0) });
	
	public static final Area WARRIORS_GUILD_SECOND_FLOOR = new Area(new Tile[] {
			new Tile(2838, 3533, 1), new Tile(2877, 3530, 1) });
	
	public static final Area WARRIORS_GUILD_THIRD_FLOOR = new Area(new Tile[] {
			new Tile(2838, 3533, 2), new Tile(2877, 3530, 2) });

	public static final Area WARRIORS_GUILD_CYCLOPS_AREA = new Area(new Tile[] {
			new Tile(2838, 3539, 2), new Tile(2838, 3551, 2),
			new Tile(2875, 3551, 2), new Tile(2875, 3533, 2),
			new Tile(2859, 3533, 2), new Tile(2859, 3530, 2),
			new Tile(2849, 3530, 2), new Tile(2848, 3533, 2),
			new Tile(2847, 3534, 2), new Tile(2847, 3539, 2) });

}
