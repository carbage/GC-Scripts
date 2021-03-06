package gcapi.constants.interfaces;

public final class Windows {

    /*
     * Duel arena IDs
     */

    // Send duel request interface
    public static final int DUEL_REQUEST_INTERFACE_PARENT = 640;
    public static final int DUEL_REQUEST_INTERFACE_MAIN = 0;
    public static final int DUEL_REQUEST_INTERFACE_FRIENDLY = 18;
    public static final int DUEL_REQUEST_INTERFACE_STAKED = 19;
    public static final int DUEL_REQUEST_INTERFACE_REQUEST = 20;

    // First stake window
    public static final int STAKE_INTERFACE_FIRST_PARENT = 631;
    public static final int STAKE_INTERFACE_FIRST_MAIN = 0;
    public static final int STAKE_INTERFACE_ACCEPT = 46;
    public static final int STAKE_INTERFACE_DECLINE = 55;
    public static final int STAKE_INTERFACE_POUCH = 53;
    public static final int STAKE_INTERFACE_PLAYER_OFFER_PARENT = 49;
    public static final int STAKE_INTERFACE_OPPONENT_OFFER_PARENT = 47;
    public static final int STAKE_INTERFACE_INFO = 41;

    // Staking rules
    public static final int STAKE_INTERFACE_RULE_HELM = 21;
    public static final int STAKE_INTERFACE_RULE_CAPE = 22;
    public static final int STAKE_INTERFACE_RULE_AMULET = 23;
    public static final int STAKE_INTERFACE_RULE_WEAPON = 24;
    public static final int STAKE_INTERFACE_RULE_BODY = 25;
    public static final int STAKE_INTERFACE_RULE_SHIELD = 26;
    public static final int STAKE_INTERFACE_RULE_LEGS = 27;
    public static final int STAKE_INTERFACE_RULE_GLOVES = 28;
    public static final int STAKE_INTERFACE_RULE_BOOTS = 29;
    public static final int STAKE_INTERFACE_RULE_RING = 30;
    public static final int STAKE_INTERFACE_RULE_ARROWS = 31;
    public static final int STAKE_INTERFACE_RULE_RANGED = 56;
    public static final int STAKE_INTERFACE_RULE_MELEE = 57;
    public static final int STAKE_INTERFACE_RULE_MAGIC = 58;
    public static final int STAKE_INTERFACE_RULE_FUN_WEAPONS = 59;
    public static final int STAKE_INTERFACE_RULE_FORFEIT = 60;
    public static final int STAKE_INTERFACE_RULE_DRINKS = 61;
    public static final int STAKE_INTERFACE_RULE_FOOD = 62;
    public static final int STAKE_INTERFACE_RULE_PRAYER = 63;
    public static final int STAKE_INTERFACE_RULE_MOVEMENT = 64;
    public static final int STAKE_INTERFACE_RULE_OBSTACLES = 65;
    public static final int STAKE_INTERFACE_RULE_SUMMONING = 66;
    public static final int STAKE_INTERFACE_RULE_SPECIALS = 67;

    public static final int STAKE_INTERFACE_LAST_OPTIONS = 69;

    // Second stake window
    public static final int STAKE_INTERFACE_SECOND_PARENT = 626;
    public static final int STAKE_INTERFACE_SECOND_MAIN = 0;
    public static final int STAKE_INTERFACE_SECOND_ACCEPT = 43;
    public static final int STAKE_INTERFACE_SECOND_DECLINE = 44;

    // Stake victory interface
    public static final int STAKE_INTERFACE_VICTORY_PARENT = 634;
    public static final int STAKE_INTERFACE_VICTORY_MAIN = 0;
    public static final int STAKE_INTERFACE_VICTORY_CLAIM = 17;

    // Duel arena full message
    public static final int DUEL_ARENA_FULL_MESSAGE_PARENT = 1186;
    public static final int DUEL_ARENA_FULL_MESSAGE_TEXT = 1;
    public static final int DUEL_ARENA_FULL_MESSAGE_CONTINUE = 8;

    /*
     * Bank pin interface IDs
     */
    public static final int BANK_PIN_PARENT = 13;
    public static final int BANK_PIN_MAIN = 0;

    /*
     * Lodestone interface IDs
     */
    public static final int LODESTONE_INTERFACE_PARENT = 1092;
    public static final int LODESTONE_LUNAR_ISLE = 39;
    public static final int LODESTONE_AL_KHARID = 40;
    public static final int LODESTONE_ARDOUGNE = 41;
    public static final int LODESTONE_BURTHORPE = 42;
    public static final int LODESTONE_CATHERBY = 43;
    public static final int LODESTONE_DRAYNORE = 44;
    public static final int LODESTONE_EDGEVILLE = 45;
    public static final int LODESTONE_FALADOR = 46;
    public static final int LODESTONE_LUMBRIDGE = 47;
    public static final int LODESTONE_PORT_SARIM = 48;
    public static final int LODESTONE_SEERS_VILLAGE = 49;
    public static final int LODESTONE_TAVERLY = 50;
    public static final int LODESTONE_VARROCK = 51;
    public static final int LODESTONE_YANILLE = 52;

    /*
     * Trade interface IDs
     */
    public static final int TRADE_INTERFACE_PARENT = 335;
    public static final int TRADE_INTERFACE_MONEY_POUCH = 1;
    public static final int TRADE_INTERFACE_ACCEPT = 19;
    public static final int TRADE_INTERFACE_DECLINE = 21;

    public static final int TRADE_INTERFACE_SECOND_PARENT = 334;
    public static final int TRADE_INTERFACE_SECOND_ACCEPT = 21;
    public static final int TRADE_INTERFACE_SECOND_DECLINE = 22;
    
    /*
     * Warriors' Guild interface IDs
     */
    public static final int WARRIORS_GUILD_TOKENS_PARENT = 1057;
    public static final int WARRIORS_GUILD_TOKENS_STRENGTH = 16;
    
    public static final int WARRIORS_GUILD_CYCLOPES_PARENT = 1058;
    public static final int WARRIORS_GUILD_CYCLOPES_SINGLE_TYPE = 36;
    public static final int WARRIORS_GUILD_CYCLOPES_SINGLE_TYPE_SELECTED_TEXTURE = 4464;
    public static final int WARRIORS_GUILD_CYCLOPES_STRENGTH_TOKEN = 17;
	public static final int WARRIORS_GUILD_CYCLOPES_STRENGTH_TOKEN_SELECTED_TEXTURE = 4460;
	public static final int WARRIORS_GUILD_CYCLOPES_ACCEPT = 31;

}
