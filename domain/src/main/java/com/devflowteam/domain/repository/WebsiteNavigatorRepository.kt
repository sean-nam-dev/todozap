package com.devflowteam.domain.repository

interface WebsiteNavigatorRepository {
    fun openWebsite(url: String): Boolean
}