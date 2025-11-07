package com.aimon.app.quizes.core.component
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aimon.app.quizes.fetures.authentication.ui.LoginScreen
import com.aimon.app.quizes.fetures.authentication.ui.SignUpScreen
import com.aimon.app.quizes.fetures.homescreen.ui.HomeScreen
import com.aimon.app.quizes.fetures.leaderboard.ui.LeaderBoardScreen
import com.aimon.app.quizes.fetures.quizscreen.ui.QuizScreen
import com.aimon.app.quizes.fetures.score.ScoreScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    val isLoggedIn = FirebaseAuth.getInstance().currentUser != null

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if(isLoggedIn) "home" else "login"
    ) {
        composable(route="home"){
           HomeScreen(navController)

        }
        composable(route="leaderBoard"){
            LeaderBoardScreen(navController)

        }

        composable(route="login"){
            LoginScreen(navController)

        }
        composable(route="signup"){
            SignUpScreen(navController)
        }
        composable(
            route = "QuizScreen/{cat}/{no}/{dif}/{type}",
            arguments = listOf(
                navArgument(name = "cat") {
                    type = NavType.StringType
                },
                navArgument(name = "no") {
                    type = NavType.IntType
                },
                navArgument(name="dif"){
                    type=NavType.StringType
                },
                navArgument(name = "type") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val category = backStackEntry.arguments?.getString("cat") ?: ""
            val number = backStackEntry.arguments?.getInt("no") ?: 0
            val difficulty = backStackEntry.arguments?.getString("dif") ?: ""
            val type = backStackEntry.arguments?.getString("type") ?: ""
            QuizScreen(
                cat = category,
                no = number,
                dif = difficulty,
                type = type,
                navController = navController,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route ="ScoreScreen/{id}/{no}",
            arguments = listOf(
                navArgument(name = "id"){
                    type= NavType.IntType
                },
                navArgument(name = "no"){
                    type= NavType.IntType
                }
            )

        ){backstackEntry->
            val num=backstackEntry.arguments?.getInt("id")?:0
            val total=backstackEntry.arguments?.getInt("no")?:0
            ScoreScreen(
                num=num,
                total=total,
                navController= navController)
        }
    }
}
