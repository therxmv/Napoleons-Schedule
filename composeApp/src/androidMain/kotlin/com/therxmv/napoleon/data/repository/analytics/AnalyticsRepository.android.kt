package com.therxmv.napoleon.data.repository.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent

class AnalyticsRepositoryImpl(
    private val firebaseAnalytics: FirebaseAnalytics,
) : AnalyticsRepository {

    override fun reportEvent(name: String) {
        firebaseAnalytics.logEvent(name) {}
    }

    override fun reportSpecialtySaved(faculty: String, specialty: String) {
        firebaseAnalytics.logEvent(AnalyticsEvents.SPECIALTY_SAVED_EVENT) {
            param(AnalyticsEvents.FACULTY_PARAM, faculty)
            param(AnalyticsEvents.SPECIALTY_PARAM, specialty)
        }
    }

    override fun reportScheduleOpened(faculty: String, specialty: String) {
        firebaseAnalytics.logEvent(AnalyticsEvents.SCHEDULE_OPENED_EVENT) {
            param(AnalyticsEvents.SELECTED_FACULTY_PARAM, faculty)
            param(AnalyticsEvents.SELECTED_SPECIALTY_PARAM, specialty)
        }
    }

    override fun reportNavigation(destination: String) {
        firebaseAnalytics.logEvent(AnalyticsEvents.NAVIGATION_EVENT) {
            param(AnalyticsEvents.DESTINATION_PARAM, destination)
        }
    }
}