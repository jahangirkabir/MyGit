# MyGit - Github users list and user details

Demo Project with Jetpack Libraries showing Github users list and user details.


## Technologies

#### Architecture

- MVVM
- [Clean-Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
<img align="right" src="./img/app.gif" width="25%"></img> 

#### App Libraries

- [Kotlin](https://kotlinlang.org/) - %100 Kotlin
- [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous operations
- [StateFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/)
- [Lifecycle-ktx](https://developer.android.com/kotlin/ktx)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Navigation](https://developer.android.com/guide/navigation)
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) for paging in user list
- [Data Store](https://developer.android.com/topic/libraries/architecture/datastore) to store key-value pairs 
- [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for Dependency Injection
- [Retrofit](https://github.com/square/retrofit) for network operations
- [Glide](https://github.com/bumptech/glide) for image loading
- [Github Api](https://docs.github.com/en/rest/users/users) for users

#### Test Libraries
- [JUnit4](https://mockk.io/) for mocking in Unit testing
- [MocKK](https://mockk.io/) for mocking in Unit testing
- [Espresso](https://developer.android.com/training/testing/espresso) for Android UI testing
- [JaCoCo](https://github.com/jacoco/jacoco) for testing code coverage report 
- [LeakCanary](https://square.github.io/leakcanary/) for memory leak detection


## How to use

- Create a [Giithub App](https://docs.github.com/en/developers/apps/getting-started-with-apps/about-apps) first and get your keys then 
 add these lines in your local.properties and use it with [Secrets Gradle Plugin](https://github.com/google/secrets-gradle-plugin)

```
apiUrl=https://api.github.com/
domainUrl=https://github.com/login/oauth/access_token
oauthLoginURL = https://github.com/login/oauth/authorize
redirectHost = YOUR_HOST   (example: if your redirect Uri of github app is like myhost://myscheme)
redirectScheme = YOUR_SCHEME
clientID = YOUR_CLIENT_ID
clientSecret = YOUR_CLIENT_SECRET
```

- Now Build your project and Run

## Future Upgrades

- Update the UI to [Jetpack Compose](https://developer.android.com/jetpack/compose/documentation)
- Add load state in paging list
- Add more details of user
- Add sliding panel for user details
- Make the app multi module
- Make the app offline first
- Upgrade test coverage
