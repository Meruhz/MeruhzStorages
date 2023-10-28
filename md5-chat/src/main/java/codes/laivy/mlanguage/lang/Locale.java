package codes.laivy.mlanguage.lang;

import codes.meruhz.storages.core.utils.MessageUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public enum Locale {

    AF_ZA("af_za", "Afrikaans (Suid-Afrika)"),
    AR_SA("ar_sa", "Arabic"),
    AST_ES("ast_es", "Asturian"),
    AZ_AZ("az_az", "Azerbaijani"),
    BA_RU("ba_ru", "Bashkir"),
    BAR("bar", "Bavarian"),
    BE_BY("be_by", "Belarusian"),
    BG_BG("bg_bg","Bulgarian"),
    BR_FR("br_fr", "Breton"),
    BRB("brb", "Brabantian"),
    BS_BA("bs_ba", "Bosnian"),
    CA_SE("ca_es", "Catalan"),
    CS_CZ("cs_cz", "Czech"),
    CY_GB("cy_gb", "Welsh"),
    DA_DK("da_dk", "Danish"),
    DE_AT("de_at", "Austrian German"),
    DE_CH("de_ch", "Swiss German"),
    DE_DE("de_de", "German"),
    EL_GR("el_gr", "Greek"),
    EN_AU("en_au", "Australian English"),
    EN_CA("en_ca", "Canadian English"),
    EN_GB("en_gb", "British English"),
    EN_NZ("en_nz", "New Zealand English"),
    EN_PT("en_pt", "Pirate English"),
    EN_UD("en_ud", "Upside down British English"),
    EN_US("en_us", "American English"),
    ENP("enp", "Modern Englsih minus borrowed words"),
    EN_WS("en_ws", "Early Modern English"),
    EO_UY("eo_uy", "Esperanto"),
    ES_AR("es_ar", "Argentinian Spanish"),
    ES_CL("es_cl", "Chilean Spanish"),
    ES_CS("es_cs", "Ecuadorian Spanish"),
    ES_ES("es_es", "European Spanish"),
    ES_MX("es_mx", "Mexican Spanish"),
    ES_UY("es_uy", "Uruguayan Spanish"),
    ES_VE("es_ve", "Venezuelan Spanish"),
    ES_AN("es_an", "Andalusian"),
    ET_EE("et_ee", "Estoanian"),
    EU_ES("eu_es", "Basque"),
    FA_IR("fa_ir", "Persian"),
    FI_FI("fi_fi", "Finnish"),
    FIL_PH("fil_ph", "Filipino"),
    FO_FO("fo_fo", "Faroese"),
    FR_CA("fr_ca", "Canadian French"),
    FR_FR("fr_fr", "European French"),
    FRA_DE("fra_de", "East Franconian"),
    FUR_IT("fur_it", "Friulian"),
    FY_NL("fy_nl", "Frisian"),
    GA_IE("ga_ie", "Irish"),
    GD_GB("gd_gb", "Scottish Gaelic"),
    GL_ES("gl_es", "Galician"),
    HAW_US("haw_us", "Hawaiian"),
    HE_IL("he_il", "Hebrew"),
    HI_IN("hi_in", "Hindi"),
    HR_HR("hr_hr", "Croatian"),
    HU_HU("hu_hu", "Hungarian"),
    HY_AM("hy_am", "Armenian"),
    ID_ID("id_id", "Indonesian"),
    IG_NG("ig_ng", "Igbo"),
    IO_EN("io_en", "Ido"),
    IS_IS("is_is", "Icelandic"),
    ISV("isv", "Interslavic"),
    IT_IT("it_it", "Italian"),
    JA_JP("ja_jp", "Japanese"),
    JBO_EN("jbo_en", "Lojban"),
    KA_GE("ka_ge", "Georgian"),
    KK_KZ("kk_kz", "Kazakh"),
    KN_IN("kn_in", "Kannada"),
    KO_KR("ko_kr", "Korean"),
    KSH("ksh", "Kölsch/Ripuarian"),
    KW_GB("kw_gb", "Cornish"),
    LA_LA("la_la", "Latin"),
    LB_LU("lb_lu", "Luxembourgish"),
    LI_LI("li_li", "Limburgish"),
    LMO("lmo", "Lombard"),
    LO_LA("lo_la", "Lao"),
    LOL_US("lol_us", "LOLCAT"),
    LT_LT("lt_lt", "Lithuanian"),
    LV_LV("lv_lv", "Latvian"),
    IZH("izh", "Classical Chinese"),
    MK_MK("mk_mk", "Macedonian"),
    MN_MN("mn_mn", "Mongolian"),
    MS_MY("ms_my", "Malay"),
    MT_MT("mt_mt", "Maltese"),
    NAH("nah", "Nahuatl"),
    NDS_DE("nds_de", "Low German"),
    NL_BE("nl_be", "Dutch, Flemish"),
    NL_NL("nl_nl", "Dutch"),
    NN_NO("nn_no", "Norwegian Nynorsk"),
    NO_NO("no_no", "Norwegian Bokmål"),
    NB_NO("nb_no", "Norwegian Bokmål"),
    OC_FR("oc_fr", "Occitan"),
    OVD("ovd", "Elfdalian"),
    PL_PL("pl_pl", "Polish"),
    PT_BR("pt_br", "Brazilian Portuguese"),
    PT_PT("pt_pt", "European Portuguese"),
    QYA_AA("qya_aa", "Quenya (Form of Elvish from LOTR)"),
    RO_RO("ro_ro", "Romanian"),
    RPR("rpr", "Russian (Pre-revolutionary)"),
    RU_RU("ru_ru", "Russian"),
    RY_UA("ry_ua", "Rusyn"),
    SAH_SAH("sah_sah", "Yakut"),
    SE_NO("se_no", "Northern Sami"),
    SK_SK("sk_sk", "Slovak"),
    SL_SI("sl_si", "Slovenian"),
    SO_SO("so_so", "Somali"),
    SQ_AL("sq_al", "Albanian"),
    SR_CS("sr_cs", "Serbian (Latin)"),
    SR_SP("sr_sp", "Serbian (Cyrillic)"),
    SV_SE("sv_se", "Swedish"),
    SXU("sxu", "Upper Saxon German"),
    SZL("szl", "Silesian"),
    TA_IN("ta_in", "Tamil"),
    TH_TH("th_th", "Thai"),
    TL_PH("tl_ph", "Tagalog"),
    TLH_AA("tlh_aa", "Klingon"),
    TOK("tok", "Toki Pona"),
    TR_TR("tr_tr", "Turkish"),
    TT_RU("tt_ru", "Tatar"),
    UK_UA("uk_ua", "Ukrainian"),
    VAL_ES("val_es", "Valencian"),
    VEC_IT("vec_it", "Venetian"),
    VI_VN("vi_vn", "Vietnamese"),
    YI_DE("yi_de", "Yiddish"),
    YO_NG("yo_ng", "Yoruba"),
    ZH_CN("zh_cn", "Chinese Simplified (China; Mandarin)"),
    ZH_HK("zh_hk", "Chinese Traditional (Hong Kong; Mix)"),
    ZH_TW("zh_tw", "Chinese Traditional (Taiwan; Mandarin)"),
    ZLM_ARAB("zlm_arab", "Malay (Jawi)");

    private final @NotNull String code, name;

    Locale(@NotNull String code, @NotNull String name) {
        this.code = code;
        this.name = name;
    }

    @Contract(pure = true)
    public @NotNull String getCode() {
        return this.code;
    }

    @Contract(pure = true)
    public @NotNull String getName() {
        return this.name;
    }

    @Contract(pure = true)
    public static @NotNull Locale getByCode(@NotNull String code) {
        return Arrays.stream(Locale.values()).filter(locale -> locale.getCode().equalsIgnoreCase(code)).findFirst().orElseThrow(() -> new NullPointerException("Could not find a locale with code '" + code + "'"));
    }

    @Contract(pure = true)
    public static @NotNull Locale fromJavaLocale(@NotNull java.util.Locale javaLocale) {
        return Locale.valueOf(MessageUtils.serializeLocale(javaLocale));
    }
}