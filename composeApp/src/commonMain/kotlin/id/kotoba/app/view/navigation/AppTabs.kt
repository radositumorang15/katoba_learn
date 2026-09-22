package id.kotoba.app.view.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition

// 1. Home Tab with nested Navigator & SlideTransition
object HomeTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = "Home"
        )

    @Composable
    override fun Content() {
        Navigator(HomeVoyagerScreen) { navigator ->
            SlideTransition(navigator)
        }
    }
}

// 2. Roadmap Tab with nested Navigator & SlideTransition
object RoadmapTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 1u,
            title = "Roadmap"
        )

    @Composable
    override fun Content() {
        Navigator(RoadmapVoyagerScreen) { navigator ->
            SlideTransition(navigator)
        }
    }
}

// 3. Learn / Flashcard Tab with nested Navigator & SlideTransition
object LearnTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 2u,
            title = "Flashcard"
        )

    @Composable
    override fun Content() {
        Navigator(LearningVoyagerScreen("lvl_1")) { navigator ->
            SlideTransition(navigator)
        }
    }
}

// 4. Glossary / Sensei Tab with nested Navigator & SlideTransition
object GlossaryTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 3u,
            title = "Sensei"
        )

    @Composable
    override fun Content() {
        Navigator(GlossaryVoyagerScreen) { navigator ->
            SlideTransition(navigator)
        }
    }
}

// 5. Profile Tab with nested Navigator & SlideTransition
object ProfileTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 4u,
            title = "Profil"
        )

    @Composable
    override fun Content() {
        Navigator(ProfileVoyagerScreen) { navigator ->
            SlideTransition(navigator)
        }
    }
}
