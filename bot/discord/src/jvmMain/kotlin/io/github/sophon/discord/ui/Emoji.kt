package io.github.sophon.discord.ui

enum class Emoji(val id: String) {
    ABILITY_POINT("<:ability_point:1493764994066546830>"),
    BULLET_DAMAGE("<:bullet_damage:1493761208518705162>"),
    BULLET_SPEED("<:bullet_speed:1493762810558021754>"),
    BULLETS("<:bullets:1493762811699138601>"),
    CHARGE("<:charge:1493764995266379927>"),
    CLIP_SIZE("<:clip_size:1493761209537794240>"),
    COOLDOWN("<:cooldown:1493762813225734216>"),
    COOLDOWN_ACTIVE("<:cooldown_active:1493764996524408973>"),
    DAMAGE_RESISTANCE("<:damage_resistance:1493764997686362163>"),
    DASH("<:dash:1493762814135767151>"),
    DPS("<:dps:1493762815406637229>"),
    HEAL("<:heal:1493764999208898640>"),
    HEALTH("<:health:1493761210540228702>"),
    HP_REGEN("<:hp_regen:1493761136984719411>"),
    MELEE("<:melee:1493762816459673760>"),
    MOVE_SLOW("<:move_slow:1493765000358006794>"),
    MOVE_SPEED("<:move_speed:1493761206467428442>"),
    RANGE("<:range:1493765001436201031>"),
    RELOAD_TIME("<:reload_time:1493761211551191233>"),
    SOULS("<:souls:1493765002459615443>"),
    SPIRIT_POWER("<:spirit_power:1493765003734552756>"),
    SPRINT_SPEED("<:sprint_speed:1493761207432249485>"),
    STUN("<:stun:1493765004770414753>");

    override fun toString(): String {
        return id
    }
}
