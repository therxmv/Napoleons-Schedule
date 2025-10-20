package com.therxmv.napoleon.data.repository.info

import com.therxmv.napoleon.data.repository.info.model.InfoLinksModel
import com.therxmv.napoleon.data.repository.profile.ProfileRepository

class InfoRepositoryImpl(
    private val profileRepository: ProfileRepository,
) : InfoRepository {

    companion object {
        private const val FMI_EXCEL_URL = "https://drive.google.com/drive/folders/1pcaDQ4vOiWyAk_DJhyVViK5U-a45n8qn"
        private const val PPF_EXCEL_URL = "https://docs.google.com/file/d/1n8bbRLQTwMAeTPrxx3xMcCuMDCulI84i/edit"
        private const val FIPMV_EXCEL_URL = "https://docs.google.com/spreadsheets/d/1pNzGUnvTK3K-qhd0cuSVBSr0hZv-yp4x/edit?gid=1764189439#gid=1764189439"

        private const val TG_CHANNEL_URL = "https://t.me/eRSHU_Updates"
        private const val TG_BOT_URL = "https://t.me/rdgufmi_bot"

        private const val MAIN_SITE_URL = "https://www.rshu.edu.ua/"
        private const val MAIN_STUDY_PROCESS_URL = "https://www.rshu.edu.ua/navchannia/orhanizatsiia-osvitnoho-protsesu"
    }

    override fun getLinks(): InfoLinksModel {
        val profile = profileRepository.getNotNullProfileSync()

        val excelUrl = when {
            profile.isFmi -> FMI_EXCEL_URL
            profile.isPpf -> PPF_EXCEL_URL
            profile.isFipmv -> FIPMV_EXCEL_URL
            else -> null
        }

        return InfoLinksModel(
            excelSchedule = excelUrl,
            telegramChannel = TG_CHANNEL_URL,
            telegramBot = TG_BOT_URL,
            mainSite = MAIN_SITE_URL,
            studyProcess = MAIN_STUDY_PROCESS_URL,
        )
    }
}