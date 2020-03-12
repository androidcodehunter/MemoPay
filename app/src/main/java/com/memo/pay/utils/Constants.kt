package com.memo.pay.utils

object Constants {
    private const val ACCOUNT_NUMBER_SHARIF = "1111111111"
    private const val ACCOUNT_NUMBER_IMAD = "1111111112"
    /*TODO switch your account number globally*/
    const val CURRENT_ACCOUNT_NUMBER = ACCOUNT_NUMBER_SHARIF

    const val ACCOUNT_TYPE_SENT = "sent"
    const val ACCOUNT_TYPE_RECEIVED = "received"
    const val CURRENCY_AED = "AED"


    fun getIntroImages(): MutableList<String> {
        val images = mutableListOf<String>()
        images.add("https://lh3.googleusercontent.com/9IJvKdil38V0bGC4HbzgXXoDmW42bdNVmWQy-htvRIjl8X_kWflY3BMEXVMeH68yKS3t3YLh59x8heMDtgo-Rn_mBcoEBs-S1rI8iYlLphUyZhuPJEHoWShR33aZxEsEy-Y25I03XsEk4HmS2akf2QufttBQFWI58Z2JoLEp-86zGXH4jzj-_usAo40TcF4Dud50_QL8RoO2sS-r9-eVzD4CyZ178xZUM1hsfnWgV6ait-HV4T3dj6bBBi6tv13yMTQoFDJ7ROpna1KvZ1mHSjmPkZkaFcmf6N9FHYu8q9LzFP4pPa3QFlZxJIp_AxGBLNyN2fGLwe0tOkdOcYt0qV5SWNVnJ-zSd1yH88g-jInsjM4igtMPYcOljHXjpmUrihC-m62GNyl3ez0YV4UUdbulj7LMZztUPET_iHcOva0GKp_6DKAX0iZtg5xPRh77InnXZHL-eEMByPs3z0RrVSBuwhII2ONwBVvySBaVrUwWIhS6ZfxlouyJsq1buEPTWvoGJEYC9JqWc5KwXmEOG0OB-r3FAANOcZLIVAwVbkpmwg_tQy8_tGABXnAU00pOKo4klBXjF_XZ2_1eWLgTMeNS2ZGAvqa1k9kKUiLW_oqJDCPAuDK51mR8vgQ26rJ2w6J-DNtuE3UygW0afS_9vaIyDIuyOIYke2DhvjS_yFi8CVs=w375-h320-no")
        images.add("https://lh3.googleusercontent.com/nBkAtNoT8jiTLd-wYOIfo4smAi6X7byz4QdzZ59OwnZT_jARJ7lvbHYj_rZtxJpRfhUYY_1dkoJ2DumlabJ1erqx9B0qaFT_Y-TJ20YUKn7Zwb_nq64qQOLBHGmP6cDMrPTb4-MIL9nVPQJPwo-7uzf6_ExP9pwriwnNaXGIbVYpnPI9255ynEvBbtbxPJNmHZGmn4kvih27AhUxy3QX-M16bMMxgExxCdYs_K7fCXqvTpwVXvkuCq-aad9pbuc8wShD_EsE7JbznLUMep0oJJzfA3SuXLGixoN4C98Aa9cHjTXRH7WdRYoWODixKW-GFfHplE9-lZ9JAQ_yv7oUBq9PolMvnedoSWBT53cZUfm9bVKkom4F5J2maaL7sZcruWF8Z_LSSd8ueO1DQE2zh16eUmLa-Ec_r24M_Tfvy7eAKYlxEKfp8089RQEhmUoKVsXuM85k9kSojlTMwFA20_47BZzgFqk3TlKCPxQYh50S9WLKleE0UW902LcU94z8BDomNwnlulwbKp6b6xU6YNYAqQuQdNagb8yDkZPzm0q7SL41Q-ehhD2NktGqWJCqjo_LXigcdDqxEaJwvhfq1JTLE4tBjES2jabE3xdqKVOV_Gqk6Af0KA_HF4wyWGxmq1wOUoz97ibrmAdT3GgQA_7DvTUARxaspvUV77GD2vfhJ7o=w375-h320-no")
        images.add("https://lh3.googleusercontent.com/Rl7LQcjYNh20Id231XAmaRgEZ_NsulOPkm2vDks-N2L8G134i2WHIccMhsP7dZ_WHxuFxnwivkgpnugxVtypb-w4muM7J_yM0EIVS-mK5sd3JbKm1zcCMh9PclyaC7Mv74uZggs7=w375-h320-no")
        return images
    }

}