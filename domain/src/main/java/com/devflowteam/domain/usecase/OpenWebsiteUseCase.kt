package com.devflowteam.domain.usecase

import com.devflowteam.domain.repository.WebsiteNavigatorRepository

class OpenWebsiteUseCase(
    private val websiteNavigatorRepository: WebsiteNavigatorRepository
) {
    operator fun invoke(url: String) = websiteNavigatorRepository.openWebsite(url)
}