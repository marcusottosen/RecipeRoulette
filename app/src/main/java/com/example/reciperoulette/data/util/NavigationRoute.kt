package com.example.reciperoulette.data.util

sealed class NavigationRoute(var route: String) {

    object Homepage                 : NavigationRoute("homepage")
    object Recipe                   : NavigationRoute("recipe")
    object Loadingpage              : NavigationRoute("loading")
    object FilterPage             : NavigationRoute("filterPage")
    object TestPage             : NavigationRoute("testPage")
}