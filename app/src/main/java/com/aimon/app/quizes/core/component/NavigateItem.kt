package com.aimon.app.quizes.core.component

sealed class NavigateItem(
    var route:String,
    var title: String
){
    object home: NavigateItem(
        route ="home",
        title = "home"
    )
    object quiz: NavigateItem(
        route = "quiz",
        title ="quiz"
    )
    object score: NavigateItem(
        route="score",
        title = "score"
    )
    object login:NavigateItem(
        route="login",
        title = "login"
    )
    object signUp: NavigateItem(
        route = "signUp",
        title = "signUp"
    )
    object leaderBoard: NavigateItem(
        route = "leaderBoard",
        title = "leaderBoard"
    )
}