package com.example.foodorder.ui.viewmodel

import androidx.lifecycle.ViewModel

// Bu viewmodelde api isteği sonucu dönen yemek listesini alıp,ui'da göstereceğim
// Burda önce livedata ile deneyeceğim,sonrasında eğer güncelleme konusunda tekrara düşen bir kod yazarsam,stateflow kullanarak çözeceğim.
// Bu viewmodelin görevi,yemek listesini alıp ui'da göstermektir.
class ListViewModel : ViewModel()