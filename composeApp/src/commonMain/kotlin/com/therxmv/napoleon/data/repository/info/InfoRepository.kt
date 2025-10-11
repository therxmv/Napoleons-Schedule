package com.therxmv.napoleon.data.repository.info

import com.therxmv.napoleon.data.repository.model.InfoLinksModel

interface InfoRepository {
    fun getLinks(): InfoLinksModel
}