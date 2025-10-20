package com.therxmv.napoleon.data.repository.info

import com.therxmv.napoleon.data.repository.info.model.InfoLinksModel

interface InfoRepository {
    fun getLinks(): InfoLinksModel
}