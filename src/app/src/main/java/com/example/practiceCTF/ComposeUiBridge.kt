package com.example.practiceCTF

import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.practiceCTF.ui.theme.PracticeCTFTheme

@Suppress("unused")
object ComposeUiBridge {
    @JvmStatic
    fun setHomeContent(activity: AppCompatActivity, onGitlabClick: Runnable, onLoginClick: Runnable) {
        activity.setContent {
            PracticeCTFTheme(dynamicColor = false) {
                HomeScreen(onGitlabClick = onGitlabClick, onLoginClick = onLoginClick)
            }
        }
    }

    @JvmStatic
    fun setLoginContent(
        activity: AppCompatActivity,
        authenticator: LoginAuthenticator,
        onAuthenticated: Runnable,
        onBackToHomeClick: Runnable
    ) {
        activity.setContent {
            PracticeCTFTheme(dynamicColor = false) {
                LoginScreen(
                    authenticator = authenticator,
                    onAuthenticated = onAuthenticated,
                    onBackToHomeClick = onBackToHomeClick
                )
            }
        }
    }

    @JvmStatic
    fun setWinContent(activity: AppCompatActivity, onBackToHomeClick: Runnable) {
        activity.setContent {
            PracticeCTFTheme(dynamicColor = false) {
                WinScreen(onBackToHomeClick = onBackToHomeClick)
            }
        }
    }

    @JvmStatic
    fun setHiddenContent(activity: AppCompatActivity) {
        activity.setContent {
            PracticeCTFTheme(dynamicColor = false) {
                HiddenScreen()
            }
        }
    }
}

@Composable
private fun HomeScreen(onGitlabClick: Runnable, onLoginClick: Runnable) {
    val gitlabUrl = stringResource(id = R.string.https_gitlab_com_wqyeo_ctf_practice_android)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.ctf_practice_android),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = gitlabUrl,
            style = MaterialTheme.typography.bodyLarge,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable { onGitlabClick.run() }
        )
        Button(
            onClick = { onLoginClick.run() },
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(text = stringResource(id = R.string.login))
        }
    }
}

@Composable
private fun LoginScreen(
    authenticator: LoginAuthenticator,
    onAuthenticated: Runnable,
    onBackToHomeClick: Runnable
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = stringResource(id = R.string.username_hint)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = stringResource(id = R.string.password_hint)) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )
        Button(
            onClick = {
                if (authenticator.authenticate(username, password)) {
                    onAuthenticated.run()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Text(text = stringResource(id = R.string.authenticate))
        }
        Button(
            onClick = { onBackToHomeClick.run() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Text(text = stringResource(id = R.string.back_to_home))
        }
    }
}

@Composable
private fun WinScreen(onBackToHomeClick: Runnable) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Successfully Logged In!",
            style = MaterialTheme.typography.headlineMedium
        )
        Button(
            onClick = { onBackToHomeClick.run() },
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(text = stringResource(id = R.string.back_to_home))
        }
    }
}

@Composable
private fun HiddenScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "You found the secret!",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

