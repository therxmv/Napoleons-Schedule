package com.therxmv.napoleon.data.repository.analytics

class AnalyticsRepositoryImpl : AnalyticsRepository {

    override fun reportEvent(name: String) {}

    override fun reportSpecialtySaved(faculty: String, specialty: String) {}

    override fun reportScheduleOpened(faculty: String, specialty: String) {}

    override fun reportNavigation(destination: String) {}
}