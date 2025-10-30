package com.features.user_details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.model.User
import com.features.user_details.R

@Composable
fun DetailsContent(user: User) {
    val scrollState = rememberScrollState()
    Card(modifier = Modifier.fillMaxWidth().padding(24.dp)
        .verticalScroll(scrollState)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(modifier = Modifier.fillMaxWidth(), text = stringResource(R.string.name) + user.name, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(modifier = Modifier.fillMaxWidth(), text =  stringResource(R.string.company) + user.company, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(modifier = Modifier.fillMaxWidth(), text = stringResource(R.string.email) + user.email, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(modifier = Modifier.fillMaxWidth(), text = stringResource(R.string.phone) + user.phone, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(modifier = Modifier.fillMaxWidth(), text = stringResource(R.string.address) + user.address, fontSize = 14.sp)
        }
    }
}