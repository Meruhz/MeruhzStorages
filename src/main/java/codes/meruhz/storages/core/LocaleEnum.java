package codes.meruhz.storages.core;

import org.jetbrains.annotations.NotNull;

public enum LocaleEnum {

    AF_ZA("Afrikaans (South Africa)", "af_ZA"),
    AR_SA("العربية (Arabic)", "ar_SA"),
    AST_ES("Asturianu (Asturian)", "ast_ES"),
    AZ_AZ("Azərbaycanca (Azerbaijani)", "az_AZ"),
    BG_BG("Български (Bulgarian)", "bg_BG"),
    BR_FR("Brezhoneg (Breton)", "br_FR"),
    CA_ES("Català (Catalan)", "ca_ES"),
    CS_CZ("Čeština (Czech)", "cs_CZ"),
    CY_GB("Cymraeg (Welsh)", "cy_GB"),
    DA_DK("Dansk (Danish)", "da_DK"),
    DE_DE("Deutsch (German)", "de_DE"),
    EL_GR("Ελληνικά (Greek)", "el_GR"),
    EN_AU("English (Australia)", "en_AU"),
    EN_CA("English (Canada)", "en_CA"),
    EN_GB("English (United Kingdom)", "en_GB"),
    EN_NZ("English (New Zealand)", "en_NZ"),
    EN_PT("English (Pirate)", "en_PT"),
    EN_UD("English (Upside Down)", "en_UD"),
    EN_US("English (United States)", "en_US"),
    ENP_IR("Pirate Speak (English)", "enp_ir"),
    EO_UY("Esperanto (Esperanto)", "eo_UY"),
    ES_AR("Español (Argentina)", "es_AR"),
    ES_CL("Español (Chile)", "es_CL"),
    ES_ES("Español (Spain)", "es_ES"),
    ES_MX("Español (Mexico)", "es_MX"),
    ES_UY("Español (Uruguay)", "es_UY"),
    ES_VE("Español (Venezuela)", "es_VE"),
    ET_EE("Eesti (Estonian)", "et_EE"),
    EU_ES("Euskara (Basque)", "eu_ES"),
    FA_IR("فارسی (Persian)", "fa_IR"),
    FI_FI("Suomi (Finnish)", "fi_FI"),
    FIL_PH("Filipino (Filipino)", "fil_PH"),
    FR_CA("Français (Canada)", "fr_CA"),
    FR_FR("Français (France)", "fr_FR"),
    FY_NL("Frysk (Frisian)", "fy_NL"),
    GA_IE("Gaeilge (Irish)", "ga_IE"),
    GD_GB("Gàidhlig (Scottish Gaelic)", "gd_GB"),
    GL_ES("Galego (Galician)", "gl_ES"),
    GV_IM("Gaelg (Manx)", "gv_IM"),
    HE_IL("עברית (Hebrew)", "he_IL"),
    HI_IN("हिन्दी (Hindi)", "hi_IN"),
    HR_HR("Hrvatski (Croatian)", "hr_HR"),
    HU_HU("Magyar (Hungarian)", "hu_HU"),
    HY_AM("Հայերեն (Armenian)", "hy_AM"),
    ID_ID("Bahasa Indonesia (Indonesian)", "id_ID"),
    IS_IS("Íslenska (Icelandic)", "is_IS"),
    IT_IT("Italiano (Italian)", "it_IT"),
    JA_JP("日本語 (Japanese)", "ja_JP"),
    KA_GE("ქართული (Georgian)", "ka_GE"),
    KAB_KAB("Taqbaylit (Kabyle)", "kab_KAB"),
    KK_KZ("Қазақ (Kazakh)", "kk_KZ"),
    KL_GL("Kalaallisut (Greenlandic)", "kl_GL"),
    KM_KH("ភាសាខ្មែរ (Khmer)", "km_KH"),
    KO_KR("한국어 (Korean)", "ko_KR"),
    KU_TR("Kurdî (Kurdish)", "ku_TR"),
    LA_VA("Latina (Latin)", "la_VA"),
    LB_LU("Lëtzebuergesch (Luxembourgish)", "lb_LU"),
    LT_LT("Lietuvių (Lithuanian)", "lt_LT"),
    LV_LV("Latviešu (Latvian)", "lv_LV"),
    MI_NZ("Māori (Māori)", "mi_NZ"),
    MK_MK("Македонски (Macedonian)", "mk_MK"),
    MN_MN("Монгол (Mongolian)", "mn_MN"),
    MS_MY("Bahasa Malaysia (Malay)", "ms_MY"),
    MT_MT("Malti (Maltese)", "mt_MT"),
    NB_NO("Norsk (Norwegian)", "nb_NO"),
    NL_NL("Nederlands (Dutch)", "nl_NL"),
    NN_NO("Norsk nynorsk (Norwegian Nynorsk)", "nn_NO"),
    OC_FR("Occitan (Occitan)", "oc_FR"),
    PL_PL("Polski (Polish)", "pl_PL"),
    PT_BR("Português (Brasil)", "pt_BR"),
    PT_PT("Português (Portugal)", "pt_PT"),
    QUZ_PE("Runasimi (Quechua)", "quz_PE"),
    RO_RO("Română (Romanian)", "ro_RO"),
    RU_RU("Русский (Russian)", "ru_RU"),
    SK_SK("Slovenčina (Slovak)", "sk_SK"),
    SL_SI("Slovenščina (Slovenian)", "sl_SI"),
    SQ_AL("Shqip (Albanian)", "sq_AL"),
    SRB("Srpski (Serbian)", "srb"),
    SRP_RS("Српски (Serbian)", "srp_RS"),
    SV_SE("Svenska (Swedish)", "sv_SE"),
    SWG("Swahili (Swahili)", "swg"),
    TH_TH("ไทย (Thai)", "th_TH"),
    TLH("tlhIngan Hol (Klingon)", "tlh"),
    TR_TR("Türkçe (Turkish)", "tr_TR"),
    UG_CN("Uyghurche (Uighur)", "ug_CN"),
    UK_UA("Українська (Ukrainian)", "uk_UA"),
    UR_PK("اردو (Urdu)", "ur_PK"),
    VI_VN("Tiếng Việt (Vietnamese)", "vi_VN"),
    ZH_CN("中文 (Chinese)", "zh_CN"),
    ZH_TW("中文 (Taiwan)", "zh_TW"),
    ZU_ZA("isiZulu (Zulu)", "zu_ZA");

    private final @NotNull String name, code;

    LocaleEnum(@NotNull String name, @NotNull String code) {
        this.name = name;
        this.code = code;
    }

    public @NotNull String getName() {
        return this.name;
    }

    public @NotNull String getCode() {
        return this.code;
    }
}
