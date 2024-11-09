package com.kmp.weather.feature.screen.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmp.weather.SharedFileReader
import com.kmp.weather.domain.dto.CityModel
import com.kmp.weather.utils.FileReadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class SearchViewModel: ViewModel() {

    private val _getCityListFromJsonFile = MutableStateFlow<List<CityModel>>(emptyList())
    val getCityListFromJsonFile: StateFlow<List<CityModel>> = _getCityListFromJsonFile

    private val _searchKeyword = MutableStateFlow("")
    var searchKeyword: StateFlow<String> = _searchKeyword

    private val _loadingState = MutableStateFlow<FileReadState<List<CityModel>>>(FileReadState.Idle)
    val loadingState: StateFlow<FileReadState<List<CityModel>>> = _loadingState

    // 필터링된 도시 리스트
    val filteredCityList: StateFlow<List<CityModel>> = combine(
        getCityListFromJsonFile,
        searchKeyword
    ) { cityList, keyword ->
        if (keyword.isEmpty()) {
            cityList // 키워드가 비어있으면 전체 리스트 반환
        } else {
            cityList.filter { it.name.contains(keyword, ignoreCase = true) } // 대소문자 구분 X
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        getCityDataFromFile("")
    }

    // reduced_citylist.json 파일로부터 도시 정보 파싱
    private fun getCityDataFromFile(keyword: String){
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.value = FileReadState.Loading
            try {

                val aaa= loadFile()
                    _getCityListFromJsonFile.emit(aaa)
                    _loadingState.value = FileReadState.Success(aaa)
            }catch (e: Exception){
                println("Error: ${e.message}")
                println("Error: ${e.printStackTrace()}")
                _loadingState.value = FileReadState.Error()
            }
        }
    }

    // 검색 키워드 업데이트
    fun updateKeyword(keyword: String) {
        _searchKeyword.value = keyword
    }
}

private val sharedFileReader = SharedFileReader()
fun loadFile(): List<CityModel> {
    val jsonString = sharedFileReader.loadFile("")
    val a = Json.decodeFromString<List<CityModel>>(jsonString)

    return try {
        a
    }catch (e: Exception){
        emptyList()
    }
}

