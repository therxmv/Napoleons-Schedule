package com.therxmv.napoleon.ui.editprofile.component

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.therxmv.leonui.input.LeonDropdownInputData
import com.therxmv.napoleon.Res
import com.therxmv.napoleon.base.state.BaseState
import com.therxmv.napoleon.data.repository.analytics.AnalyticsRepository
import com.therxmv.napoleon.data.repository.faculty.FacultyRepository
import com.therxmv.napoleon.data.repository.faculty.model.FacultiesModel
import com.therxmv.napoleon.data.repository.faculty.model.FacultyModel
import com.therxmv.napoleon.data.repository.faculty.model.SpecialtyModel
import com.therxmv.napoleon.data.repository.faculty.model.YearsModel
import com.therxmv.napoleon.data.repository.profile.ProfileRepository
import com.therxmv.napoleon.data.repository.profile.model.ProfileModel
import com.therxmv.napoleon.data.source.remote.result.Result
import com.therxmv.napoleon.navigation.destination.child.ChildDestination
import com.therxmv.napoleon.navigation.destination.child.ChildDestination.BottomNav
import com.therxmv.napoleon.navigation.destination.child.ChildDestination.FullScreen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Stable
class EditProfileComponent(
    componentContext: ComponentContext,
    private val facultyRepository: FacultyRepository,
    private val profileRepository: ProfileRepository,
    private val currentDestination: ChildDestination,
    private val navigateTo: (ChildDestination) -> Unit,
    private val goBack: () -> Unit,
    private val analyticsRepository: AnalyticsRepository,
    private val mainDispatcher: CoroutineDispatcher,
) : ComponentContext by componentContext {
    private val scope = coroutineScope(SupervisorJob())

    private val _uiState = MutableStateFlow<BaseState<EditProfileUiData>>(BaseState.Idle)
    val uiState = _uiState.asStateFlow()

    private var cachedFaculties: List<FacultyModel> = emptyList()

    init {
        loadData()
    }

    fun onEvent(event: EditProfileUiEvent) {
        when (event) {
            EditProfileUiEvent.SaveProfile -> saveProfileAndNavigate()
        }
    }

    private fun loadData() {
        scope.launch {
            _uiState.update { BaseState.Loading }

            val profile = profileRepository.getProfile()
            loadAllFaculties(profile)
            tryPrepopulateData(profile)
        }
    }

    private suspend fun loadAllFaculties(profile: ProfileModel? = null) {
        val result = facultyRepository.getFaculties()

        _uiState.update {
            when (result) {
                is Result.Success<FacultiesModel> -> {
                    cachedFaculties = result.data.faculties
                    val faculties = cachedFaculties.map { it.facultyName }

                    val prepopulatedFaculty = faculties.find { it == profile?.facultyName }
                    val data = createInitialData(facultyItems = faculties, facultyName = prepopulatedFaculty)

                    BaseState.Ready(
                        data = data,
                        cacheReason = result.reason?.message,
                    )
                }

                is Result.Failure -> BaseState.Error(result.reason.message, ::loadData)
            }
        }
    }

    private suspend fun loadAllYears(profile: ProfileModel? = null) {
        val facultyName = _uiState.getReadyData()?.facultyDropdown?.value
        val facultyPath = cachedFaculties.find { it.facultyName == facultyName }?.folderName ?: return

        val result = facultyRepository.getYears(facultyPath)

        when (result) {
            is Result.Success<YearsModel> -> {
                val prepopulatedYear = result.data.years.find { it == profile?.year }

                _uiState.updateReady(result.reason?.message) { data ->
                    data.copy(
                        yearDropdown = data.yearDropdown.copy(
                            items = result.data.years,
                            value = prepopulatedYear,
                        ),
                        specialtyDropdown = data.specialtyDropdown.copy(
                            items = emptyList(),
                            value = null,
                        ),
                    )
                }
            }

            is Result.Failure -> {
                _uiState.update {
                    BaseState.Error(result.reason.message, ::loadData)
                }
            }
        }
    }

    private fun loadAllSpecialties(profile: ProfileModel? = null) {
        val year = _uiState.getReadyData()?.yearDropdown?.value ?: return

        val result = facultyRepository.getSpecialties(year)

        when (result) {
            is Result.Success<List<SpecialtyModel>> -> {
                val specialties = result.data.map { it.specialtyName }

                val prepopulatedSpecialty = specialties.find { it == profile?.specialtyName }

                _uiState.updateReady(result.reason?.message) { data ->
                    data.copy(
                        specialtyDropdown = data.specialtyDropdown.copy(
                            items = specialties,
                            value = prepopulatedSpecialty,
                        ),
                    )
                }
            }

            is Result.Failure -> {
                _uiState.update {
                    BaseState.Error(result.reason.message, ::loadData)
                }
            }
        }
    }

    private suspend fun tryPrepopulateData(profile: ProfileModel?) {
        profile?.let {
            loadAllYears(it)
            loadAllSpecialties(it)
        }
    }

    private fun createInitialData(facultyItems: List<String>, facultyName: String?): EditProfileUiData =
        EditProfileUiData(
            facultyDropdown = LeonDropdownInputData(
                placeholder = Res.string.edit_profile_faculty_placeholder,
                value = facultyName,
                items = facultyItems,
                onClick = ::onFacultyClick,
            ),
            yearDropdown = LeonDropdownInputData(
                placeholder = Res.string.edit_profile_year_placeholder,
                onClick = ::onYearClick,
            ),
            specialtyDropdown = LeonDropdownInputData(
                placeholder = Res.string.edit_profile_specialty_placeholder,
                onClick = ::onSpecialtyClick,
            ),
            saveLabel = Res.string.edit_profile_save_button,
        )

    private fun onFacultyClick(value: String) {
        val currentName = _uiState.getReadyData()?.facultyDropdown?.value
        if (currentName == value) return

        scope.launch {
            _uiState.updateReady { state ->
                state.copy(
                    facultyDropdown = state.facultyDropdown.copy(
                        value = value,
                    ),
                )
            }
            loadAllYears()
        }
    }

    private fun onYearClick(value: String) {
        val currentName = _uiState.getReadyData()?.yearDropdown?.value
        if (currentName == value) return

        scope.launch {
            _uiState.updateReady { state ->
                state.copy(
                    yearDropdown = state.yearDropdown.copy(
                        value = value,
                    ),
                )
            }
            loadAllSpecialties()
        }
    }

    private fun onSpecialtyClick(value: String) {
        val currentName = _uiState.getReadyData()?.specialtyDropdown?.value
        if (currentName == value) return

        _uiState.updateReady { state ->
            state.copy(
                specialtyDropdown = state.specialtyDropdown.copy(
                    value = value,
                ),
            )
        }
    }

    private fun saveProfileAndNavigate() {
        val data = _uiState.getReadyData() ?: return
        val faculty = cachedFaculties.find { it.facultyName == data.facultyDropdown.value } ?: return
        val year = data.yearDropdown.value ?: return
        val specialty = data.specialtyDropdown.value ?: return

        scope.launch {
            profileRepository.setProfile(
                year = year,
                facultyPath = faculty.folderName,
                facultyName = faculty.facultyName,
                specialtyName = specialty,
            )

            analyticsRepository.reportSpecialtySaved(faculty.facultyName, specialty)

            navigateAfterSave()
        }
    }

    private suspend fun navigateAfterSave() {
        withContext(mainDispatcher) {
            when (currentDestination) {
                FullScreen.CreateProfile -> navigateTo(BottomNav.Dashboard)
                else -> goBack()
            }
        }
    }

    private fun MutableStateFlow<BaseState<EditProfileUiData>>.getReadyData(): EditProfileUiData? {
        val state = this.value as? BaseState.Ready<*>
        val data = (state?.data as? EditProfileUiData)

        return data
    }

    private fun MutableStateFlow<BaseState<EditProfileUiData>>.updateReady(cacheReason: String? = null, dataCreator: (EditProfileUiData) -> EditProfileUiData) {
        update { state ->
            if (state !is BaseState.Ready<*>) return@update state

            val data = (state.data as? EditProfileUiData) ?: return@update state

            BaseState.Ready(
                data = dataCreator(data),
                cacheReason = cacheReason,
            )
        }
    }
}