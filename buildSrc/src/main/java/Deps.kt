object Deps {
    private const val kotlin_version = "1.3.40"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"

    const val appcompat = "androidx.appcompat:appcompat:1.0.2"
    const val core_ktx = "androidx.core:core-ktx:1.0.2"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val material = "com.google.android.material:material:1.0.0"

    private const val coroutines_version = "1.2.0"
    const val coroutines_core_common = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutines_version"
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"

    const val rxandroid = "io.reactivex.rxjava2:rxandroid:2.1.0"
    const val rxkotlin = "io.reactivex.rxjava2:rxkotlin:2.3.0"

    private const val koin_version = "2.0.1"
    const val koin_android = "org.koin:koin-android:$koin_version"
    const val koin_androidx_scope = "org.koin:koin-androidx-scope:$koin_version"
    const val koin_androidx_viewmodel = "org.koin:koin-androidx-viewmodel:$koin_version"

    private const val groupie_version = "2.3.0"
    const val groupie = "com.xwray:groupie:$groupie_version"
    const val groupie_extensions = "com.xwray:groupie-kotlin-android-extensions:$groupie_version"
    const val groupie_databinding = "com.xwray:groupie-databinding:$groupie_version"

    private const val room_version = "2.2.0"
    const val room_runtime = "androidx.room:room-runtime:2.1.0-alpha01"
    const val room_rxjava2 = "androidx.room:room-rxjava2:2.1.0-alpha01"
    const val room_compiler = "androidx.room:room-compiler:2.1.0-alpha01"

    private val nav_version = "2.1.0-beta01"
    val navigation_fragment = "androidx.navigation:navigation-fragment:$nav_version"
    val navigation_fragment_ktx = "androidx.navigation:navigation-fragment-ktx:$nav_version"
    val navigation_ui = "androidx.navigation:navigation-ui:$nav_version"
    val navigation_ui_ktx = "androidx.navigation:navigation-ui-ktx:$nav_version"
    val navigation_safe_args_plugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version"

    const val junit = "junit:junit:4.12"
    const val robolectric = "org.robolectric:robolectric:4.3"
    const val mockk = "io.mockk:mockk:1.9.3"
    const val truth = "com.google.truth:truth:1.0"

    const val ext_junit = "androidx.test.ext:junit:1.1.1"
    const val espresso_core = "androidx.test.espresso:espresso-core:3.2.0"
    const val espresso_contrib = "androidx.test.espresso:espresso-contrib:3.2.0"
    const val espresso_intents = "androidx.test.espresso:espresso-intents:3.2.0"
}
